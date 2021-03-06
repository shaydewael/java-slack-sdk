package samples;

import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.util.JsonOps;
import util.ResourceLoader;
import util.TestSlackAppServer;

public class AttachmentActionSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        // src/test/resources/attachments/message1.json
        String firstMessage = ResourceLoader.load("attachments/message1.json");
        app.command("/view", (req, ctx) -> {
            ctx.respond(JsonOps.fromJson(firstMessage, SlashCommandResponse.class));
            return ctx.ack();
        });

        // src/test/resources/action_response/message1.json
        String secondMessage = ResourceLoader.load("action_response/message1.json");
        app.attachmentAction("wopr_game", (req, ctx) -> {
            ctx.logger.info("attachment action - {}", req.getPayload());
            ctx.respond(JsonOps.fromJson(secondMessage, ActionResponse.class));
            return ctx.ack();
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}

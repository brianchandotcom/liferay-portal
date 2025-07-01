package com.liferay.mcp.server;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.ServerCapabilities;
import io.modelcontextprotocol.spec.McpSchema.Tool;

@Component(immediate = true, service = LiferayMcpServer.class)
public class LiferayMcpServer {
    private static final Logger LOG = LoggerFactory.getLogger(LiferayMcpServer.class);

    @Reference
    private LiferayMcpServerHttpServlet liferayMcpServerHttpServlet;

    private McpSyncServer mcpSyncServer;

    @Activate
    void activate() {
        LOG.atInfo().log("Activating MCP server");

        mcpSyncServer = McpServer.sync(liferayMcpServerHttpServlet)
                .capabilities(ServerCapabilities.builder()
                        .tools(true).build())
                .build();

        var helloWorldTool = new Tool("hello-world", "returns the \"hello world\" message", """
                {
                    "type" : "object",
                    "properties" : {}
                }
                """);

        var helloWorldToolSpec = new McpServerFeatures.SyncToolSpecification(helloWorldTool, (exchange, arguments) -> {
            return new CallToolResult("Hello World", false);
        });

        mcpSyncServer.addTool(helloWorldToolSpec);
    }

    @Deactivate
    void deactivate() {
        LOG.atInfo().log("Deactivating MCP server");

        if (mcpSyncServer != null) {
            mcpSyncServer.close();
            mcpSyncServer = null;
        }
    }
}

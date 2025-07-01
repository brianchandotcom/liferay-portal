package com.liferay.mcp.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.modelcontextprotocol.spec.McpError;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpServerSession;
import io.modelcontextprotocol.spec.McpServerTransport;
import io.modelcontextprotocol.spec.McpServerTransportProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.AsyncContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Leandro Aguiar
 */
@Component(immediate = true, property = { "osgi.http.whiteboard.context.path=/",
        "osgi.http.whiteboard.servlet.pattern=/mcp" }, service = {LiferayMcpServerHttpServlet.class, Servlet.class})
public class LiferayMcpServerHttpServlet extends HttpServlet implements McpServerTransportProvider {
    private static final Logger LOG = LoggerFactory.getLogger(LiferayMcpServerHttpServlet.class);

    private static final String TEXT_EVENT_STREAM = "text/event-stream";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ENDPOINT = "/o/mcp";
    private static final String MESSAGE_EVENT_TYPE = "message";
    private static final String ENDPOINT_EVENT_TYPE = "endpoint";
    private static final String UTF_8 = "UTF-8";

    private final AtomicBoolean isClosing = new AtomicBoolean(false);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, McpServerSession> sessions = new ConcurrentHashMap<>();

    private McpServerSession.Factory sessionFactory;

    @Override
    public Mono<Void> closeGracefully() {
        isClosing.set(true);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Initiating graceful shutdown with " + sessions.size() + " active sessions");
        }

        return Flux.fromIterable(sessions.values()).flatMap(McpServerSession::closeGracefully).then();
    }

    @Override
    public void destroy() {
        closeGracefully().block();

        super.destroy();
    }

    @Override
    public Mono<Void> notifyClients(String method, Object params) {
        if (sessions.isEmpty()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("No active sessions to broadcast message to");
            }
            return Mono.empty();
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Attempting to broadcast message to " + sessions.size() + " active sessions");
        }

        return Flux.fromIterable(sessions.values())
                .flatMap(
                        session -> session.sendNotification(method, params).doOnError(
                                e -> LOG.error("Failed to send message to session " + session.getId(), e))
                                .onErrorComplete())
                .then();
    }

    @Override
    public void setSessionFactory(McpServerSession.Factory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        if (isClosing.get()) {
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Server is shutting down");

            return;
        }

        if (sessionFactory == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server is not initialized");

            return;
        }

        LOG.debug("Processing GET request");

        String sessionId = UUID.randomUUID().toString();

        response.setContentType(TEXT_EVENT_STREAM);
        response.setCharacterEncoding(UTF_8);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Mcp-Session-Id", sessionId);

        AsyncContext asyncContext = request.startAsync();

        asyncContext.setTimeout(0);

        PrintWriter writer = response.getWriter();

        // Create a new session transport

        LiferayMcpSessionTransport sessionTransport = new LiferayMcpSessionTransport(
                sessionId, asyncContext, writer);

        // Create a new session using the session factory

        McpServerSession session = sessionFactory.create(sessionTransport);

        sessions.put(sessionId, session);

        // Send initial endpoint event

        sendEvent(writer, ENDPOINT_EVENT_TYPE, ENDPOINT);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        if (isClosing.get()) {
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Server is shutting down");

            return;
        }

        LOG.debug("Processing POST request");

        // Get the session ID from the request parameter
        String sessionId = request.getHeader("Mcp-Session-Id");

        if (sessionId == null) {
            response.setContentType(APPLICATION_JSON);
            response.setCharacterEncoding(UTF_8);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            String jsonError = objectMapper.writeValueAsString(new McpError("Session ID missing in message endpoint"));

            PrintWriter writer = response.getWriter();

            writer.write(jsonError);
            writer.flush();

            return;
        }

        // Get the session from the sessions map

        McpServerSession session = sessions.get(sessionId);

        if (session == null) {
            response.setContentType(APPLICATION_JSON);
            response.setCharacterEncoding(UTF_8);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            String jsonError = objectMapper.writeValueAsString(new McpError("Session not found: " + sessionId));

            PrintWriter writer = response.getWriter();

            writer.write(jsonError);
            writer.flush();

            return;
        }

        try (BufferedReader reader = request.getReader()) {
            StringBuilder body = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                body.append(line);
            }

            McpSchema.JSONRPCMessage message = McpSchema.deserializeJsonRpcMessage(objectMapper, body.toString());

            // Process the message through the session's handle method

            session.handle(message).block(); // Block for Servlet compatibility

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            LOG.error("Error processing message", e);

            try {
                McpError mcpError = new McpError(e.getMessage());

                response.setContentType(APPLICATION_JSON);
                response.setCharacterEncoding(UTF_8);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                String jsonError = objectMapper.writeValueAsString(mcpError);

                PrintWriter writer = response.getWriter();

                writer.write(jsonError);
                writer.flush();
            } catch (IOException ex) {
                LOG.error("Failed to send error response", ex);

                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing message");
            }
        }
    }

    private void sendEvent(PrintWriter writer, String eventType, String data) throws IOException {
        writer.write("event: " + eventType + "\n");
        writer.write("data: " + data + "\n\n");
        writer.flush();

        if (writer.checkError()) {
            throw new IOException("Client disconnected");
        }
    }

    /**
     * Implementation of McpServerTransport for HttpServlet SSE sessions. This class
     * handles the transport-level communication for a specific client session.
     */
    private class LiferayMcpSessionTransport implements McpServerTransport {
        private final AsyncContext asyncContext;
        private final String sessionId;
        private final PrintWriter writer;

        @Override
        public void close() {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Closing session transport: " + sessionId);
            }

            try {
                sessions.remove(sessionId);

                asyncContext.complete();

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Successfully completed async context for session " + sessionId);
                }
            } catch (Exception e) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("Failed to complete async context for session " + sessionId, e);
                }
            }
        }

        @Override
        public Mono<Void> closeGracefully() {
            return Mono.fromRunnable(() -> close());
        }

        /**
         * Sends a JSON-RPC message to the client through the SSE connection.
         *
         * @param message The JSON-RPC message to send
         * @return A Mono that completes when the message has been sent
         */
        @Override
        public Mono<Void> sendMessage(McpSchema.JSONRPCMessage message) {
            return Mono.fromRunnable(
                    () -> {
                        try {
                            String jsonText = objectMapper.writeValueAsString(message);

                            sendEvent(writer, MESSAGE_EVENT_TYPE, jsonText);

                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Message sent to session " + sessionId);
                            }
                        } catch (Exception e) {
                            LOG.error("Failed to send message to session " + sessionId, e);

                            sessions.remove(sessionId);

                            asyncContext.complete();
                        }
                    });
        }

        @Override
        public <T> T unmarshalFrom(Object data, TypeReference<T> typeRef) {
            return objectMapper.convertValue(data, typeRef);
        }

        /**
         * Creates a new session transport with the specified ID and SSE writer.
         *
         * @param sessionId    The unique identifier for this session
         * @param asyncContext The async context for the session
         * @param writer       The writer for sending server events to the client
         */
        LiferayMcpSessionTransport(String sessionId, AsyncContext asyncContext, PrintWriter writer) {
            this.sessionId = sessionId;
            this.asyncContext = asyncContext;
            this.writer = writer;

            if (LOG.isDebugEnabled()) {
                LOG.debug("Session transport " + sessionId + " initialized with SSE writer");
            }
        }
    }
}
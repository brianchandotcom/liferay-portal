/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.axis.transport.http;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.axis.AxisEngine;
import org.apache.axis.AxisFault;
import org.apache.axis.ConfigurationException;
import org.apache.axis.Constants;
import org.apache.axis.Handler;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.SimpleTargetedChain;
import org.apache.axis.client.Service;
import org.apache.axis.management.ServiceAdmin;
import org.apache.axis.components.logger.LogFactory;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ServiceDesc;
import org.apache.axis.handlers.soap.SOAPService;
import org.apache.axis.security.servlet.ServletSecurityProvider;
import org.apache.axis.utils.JavaUtils;
import org.apache.axis.utils.Messages;
import org.apache.axis.utils.XMLUtils;
import org.apache.commons.logging.Log;
import org.w3c.dom.Element;

/**
 *
 * @author Doug Davis (dug@us.ibm.com)
 * @author Steve Loughran
 * xdoclet tags are not active yet; keep web.xml in sync.
 * To change the location of the services, change url-pattern in web.xml and
 * set parameter axis.servicesPath in server-config.wsdd. For more information see
 * <a href="http://ws.apache.org/axis/java/reference.html">Axis Reference Guide</a>.
 *
 * @web.servlet name="AxisServlet"  display-name="Apache-Axis Servlet"
 * @web.servlet-mapping url-pattern="/servlet/AxisServlet"
 * @web.servlet-mapping url-pattern="*.jws"
 * @web.servlet-mapping url-pattern="/services/*"
  */
public class AxisServlet extends AxisServletBase {
    protected static Log log =
            LogFactory.getLog(AxisServlet.class.getName());

    /**
     * this log is for timing
     */
    private static Log tlog =
            LogFactory.getLog(Constants.TIME_LOG_CATEGORY);

    /**
     * a separate log for exceptions lets users route them
     * differently from general low level debug info
     */
    private static Log exceptionLog =
            LogFactory.getLog(Constants.EXCEPTION_LOG_CATEGORY);

    public static final String INIT_PROPERTY_TRANSPORT_NAME =
            "transport.name";

    public static final String INIT_PROPERTY_USE_SECURITY =
            "use-servlet-security";
    public static final String INIT_PROPERTY_ENABLE_LIST =
            "axis.enableListQuery";

    public static final String INIT_PROPERTY_JWS_CLASS_DIR =
            "axis.jws.servletClassDir";

    // This will turn off the list of available services
    public static final String INIT_PROPERTY_DISABLE_SERVICES_LIST =
            "axis.disableServiceList";

    // Location of the services as defined by the servlet-mapping in web.xml
    public static final String INIT_PROPERTY_SERVICES_PATH =
            "axis.servicesPath";

    // These have default values.
    private String transportName;

    private Handler transport;

    private ServletSecurityProvider securityProvider = null;

    private String servicesPath;

    /**
     * cache of logging debug option; only evaluated at init time.
     * So no dynamic switching of logging options with this servlet.
     */
    private static boolean isDebug = false;

    /**
     * Should we enable the "?list" functionality on GETs?  (off by
     * default because deployment information is a potential security
     * hole)
     */
    private boolean enableList = false;

    /**
     * Should we turn off the list of services when we receive a GET
     * at the servlet root?
     */
    private boolean disableServicesList = false;

    /**
     * Cached path to JWS output directory
     */
    private String jwsClassDir = null;
    protected String getJWSClassDir() {return jwsClassDir;
    }


    /**
     * create a new servlet instance
     */
    public AxisServlet() {
    }

    /**
     * Initialization method.
     */
    public void init() throws javax.servlet.ServletException {
        super.init();
        ServletContext context = getServletConfig().getServletContext();

        isDebug = log.isDebugEnabled();
        if (isDebug) {
            log.debug("In servlet init");
        }
        transportName = getOption(context,
                                  INIT_PROPERTY_TRANSPORT_NAME,
                                  HTTPTransport.DEFAULT_TRANSPORT_NAME);

        if (JavaUtils.isTrueExplicitly(getOption(context,
                                                 INIT_PROPERTY_USE_SECURITY, null))) {
            securityProvider = new ServletSecurityProvider();
        }

        enableList =
                JavaUtils.isTrueExplicitly(getOption(context,
                INIT_PROPERTY_ENABLE_LIST, null));

        jwsClassDir = getOption(context, INIT_PROPERTY_JWS_CLASS_DIR, null);

        // Should we list services?
        disableServicesList = JavaUtils.isTrue(getOption(context,
                INIT_PROPERTY_DISABLE_SERVICES_LIST, "false"));

        servicesPath = getOption(context, INIT_PROPERTY_SERVICES_PATH,
                                 "/services/");

        /**
         * There are DEFINATE problems here if
         * getHomeDir and/or getDefaultJWSClassDir return null
         * (as they could with WebLogic).
         * This needs to be reexamined in the future, but this
         * should fix any NPE's in the mean time.
         */
        if (jwsClassDir != null) {
            if (getHomeDir() != null) {
                jwsClassDir = getHomeDir() + jwsClassDir;
            }
        } else {
            jwsClassDir = getDefaultJWSClassDir();
        }

        initQueryStringHandlers();
    }


    /**
     * Process GET requests. This includes handoff of pseudo-SOAP requests
     *
     * @param request request in
     * @param response request out
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        if (isDebug) {
            log.debug("Enter: doGet()");

        }
        PrintWriter writer = new FilterPrintWriter(response);

        try {
            AxisEngine engine = getEngine();
            ServletContext servletContext =
                    getServletConfig().getServletContext();

            String pathInfo = request.getPathInfo();
            String realpath = servletContext.getRealPath(request.getServletPath());
            if (realpath == null) {
                realpath = request.getServletPath();
            }

            //JWS pages are special; they are the servlet path and there
            //is no pathinfo...we map the pathinfo to the servlet path to keep
            //it happy
            boolean isJWSPage = request.getRequestURI().endsWith(".jws");
            if (isJWSPage) {
                pathInfo = request.getServletPath();
            }

            // Try to execute a query string plugin and return upon success.

            if (processQuery(request, response, writer) == true) {
                return;
            }

            boolean hasNoPath = (pathInfo == null || pathInfo.equals(""));
            if (!disableServicesList) {
                if(hasNoPath) {
                    // If the user requested the servlet (i.e. /axis/servlet/AxisServlet)
                    // with no service name, present the user with a list of deployed
                    // services to be helpful
                    // Don't do this if has been turned off
                    reportAvailableServices(response, writer, request);
                } else if (realpath != null) {
                    // We have a pathname, so now we perform WSDL or list operations

                    // get message context w/ various properties set
                    MessageContext msgContext = createMessageContext(engine,
                            request, response);

                    // NOTE:  HttpUtils.getRequestURL has been deprecated.
                    // This line SHOULD be:
                    //    String url = req.getRequestURL().toString()
                    // HOWEVER!!!!  DON'T REPLACE IT!  There's a bug in
                    // req.getRequestURL that is not in HttpUtils.getRequestURL
                    // req.getRequestURL returns "localhost" in the remote
                    // scenario rather than the actual host name.
                    //
                    // But more importantly, getRequestURL() is a servlet 2.3
                    // API and to support servlet 2.2 (aka WebSphere 4)
                    // we need to leave this in for a while longer. tomj 10/14/2004
                    //
                    String url = HttpUtils.getRequestURL(request).toString();

                    msgContext.setProperty(MessageContext.TRANS_URL, url);

                    // See if we can locate the desired service.  If we
                    // can't, return a 404 Not Found.  Otherwise, just
                    // print the placeholder message.

                    String serviceName;
                    if (pathInfo.startsWith("/")) {
                        serviceName = pathInfo.substring(1);
                    } else {
                        serviceName = pathInfo;
                    }

                    SOAPService s = engine.getService(serviceName);
                    if (s == null) {
                        //no service: report it
                        if (isJWSPage) {
                            reportCantGetJWSService(request, response, writer);
                        } else {
                            reportCantGetAxisService(request, response, writer);
                        }

                    } else {
                        //print a snippet of service info.
                        reportServiceInfo(response, writer, s, serviceName);
                    }
                }
            } else {
                // We didn't have a real path in the request, so just
                // print a message informing the user that they reached
                // the servlet.

                response.setContentType("text/html; charset=utf-8");
                writer.println("<html><h1>Axis HTTP Servlet</h1>");
                writer.println(Messages.getMessage("reachedServlet00"));

                writer.println("<p>" +
                               Messages.getMessage("transportName00",
                        "<b>" + transportName + "</b>"));
                writer.println("</html>");
            }
        } catch (AxisFault fault) {
            reportTroubleInGet(fault, response, writer);
        } catch (Exception e) {
            reportTroubleInGet(e, response, writer);
        } finally {
            writer.close();
            if (isDebug) {
                log.debug("Exit: doGet()");
            }
        }
    }

    /**
     * when we get an exception or an axis fault in a GET, we handle
     * it almost identically: we go 'something went wrong', set the response
     * code to 500 and then dump info. But we dump different info for an axis fault
     * or subclass thereof.
     * @param exception what went wrong
     * @param response current response
     * @param writer open writer to response
     */
    private void reportTroubleInGet(Throwable exception,
                                    HttpServletResponse response,
                                    PrintWriter writer) {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        writer.println("<h2>" +
                       Messages.getMessage("error00") +
                       "</h2>");
        writer.println("<p>" +
                       Messages.getMessage("somethingWrong00") +
                       "</p>");
        if (exception instanceof AxisFault) {
            AxisFault fault = (AxisFault) exception;
            processAxisFault(fault);
            writeFault(writer, fault);
        } else {
            logException(exception);
            writer.println("<pre>Exception - " + exception + "<br>");
            //dev systems only give fault dumps
            if (isDevelopment()) {
                writer.println(JavaUtils.stackToString(exception));
            }
            writer.println("</pre>");
        }
    }

    /**
     * routine called whenever an axis fault is caught; where they
     * are logged and any other business. The method may modify the fault
     * in the process
     * @param fault what went wrong.
     */
    protected void processAxisFault(AxisFault fault) {
        //log the fault
        Element runtimeException = fault.lookupFaultDetail(
                Constants.QNAME_FAULTDETAIL_RUNTIMEEXCEPTION);
        if (runtimeException != null) {
            exceptionLog.info(Messages.getMessage("axisFault00"), fault);
            //strip runtime details
            fault.removeFaultDetail(Constants.
                                    QNAME_FAULTDETAIL_RUNTIMEEXCEPTION);
        } else if (exceptionLog.isDebugEnabled()) {
            exceptionLog.debug(Messages.getMessage("axisFault00"), fault);
        }
        //dev systems only give fault dumps
        if (!isDevelopment()) {
            //strip out the stack trace
            fault.removeFaultDetail(Constants.QNAME_FAULTDETAIL_STACKTRACE);
        }
    }

    /**
     * log any exception to our output log, at our chosen level
     * @param e what went wrong
     */
    protected void logException(Throwable e) {
        exceptionLog.info(Messages.getMessage("exception00"), e);
    }

    /**
     * this method writes a fault out to an HTML stream. This includes
     * escaping the strings to defend against cross-site scripting attacks
     * @param writer
     * @param axisFault
     */
    private void writeFault(PrintWriter writer, AxisFault axisFault) {
        String localizedMessage = XMLUtils.xmlEncodeString(axisFault.
                getLocalizedMessage());
        writer.println("<pre>Fault - " + localizedMessage + "<br>");
        writer.println(axisFault.dumpToString());
        writer.println("</pre>");
    }

    /**
     * print a snippet of service info.
     * @param service service
     * @param writer output channel
     * @param serviceName where to put stuff
     */

    protected void reportServiceInfo(HttpServletResponse response,
                                     PrintWriter writer, SOAPService service,
                                     String serviceName) {
        response.setContentType("text/html; charset=utf-8");

        writer.println("<h1>"
                       + service.getName()
                       + "</h1>");
        writer.println(
                "<p>" +
                Messages.getMessage("axisService00") +
                "</p>");
        writer.println(
                "<i>" +
                Messages.getMessage("perhaps00") +
                "</i>");
    }

    /**
     * report that we have no WSDL
     *
     * This method was moved to the querystring handler QSWSDLHandler. The
     * method reportNoWSDL in AxisServlet is never called. Perhaps the method
     * is overwritten in subclasses of AxisServlet so the method wasn't
     * removed. See the discussion in
     *
     * http://nagoya.apache.org/bugzilla/show_bug.cgi?id=23845
     *
     * @param res
     * @param writer
     * @param moreDetailCode optional name of a message to provide more detail
     * @param axisFault optional fault string, for extra info at debug time only
     */
    protected void reportNoWSDL(HttpServletResponse res, PrintWriter writer,
                                String moreDetailCode, AxisFault axisFault) {
    }


    /**
     * This method lists the available services; it is called when there is
     * nothing to execute on a GET
     * @param response
     * @param writer
     * @param request
     * @throws ConfigurationException
     * @throws AxisFault
     */
    protected void reportAvailableServices(HttpServletResponse response,
                                           PrintWriter writer,
                                           HttpServletRequest request) throws
            ConfigurationException, AxisFault {
        AxisEngine engine = getEngine();

        response.setContentType("text/html; charset=utf-8");
        writer.println("<h2>And now... Some Services</h2>");

        Iterator i;
        try {
            i = engine.getConfig().getDeployedServices();
        } catch (ConfigurationException configException) {
            //turn any internal configuration exceptions back into axis faults
            //if that is what they are
            if (configException.getContainedException() instanceof AxisFault) {
                throw (AxisFault) configException.getContainedException();
            } else {
                throw configException;
            }
        }
        // baseURL may change if <endpointURL> tag is used for
        // custom deployment at a different location
        String defaultBaseURL = getWebappBase(request) + servicesPath;
        writer.println("<ul>");
        while (i.hasNext()) {
            ServiceDesc sd = (ServiceDesc) i.next();
            StringBuffer sb = new StringBuffer();
            sb.append("<li>");
            String name = sd.getName();
            sb.append(name);
            sb.append(" <a href=\"");
            String endpointURL = sd.getEndpointURL();
            String baseURL = (endpointURL == null) ? defaultBaseURL :
                             endpointURL;
            sb.append(baseURL);
            sb.append(name);
            sb.append("?wsdl\"><i>(wsdl)</i></a></li>");
            writer.println(sb.toString());
            ArrayList operations = sd.getOperations();
            if (!operations.isEmpty()) {
                writer.println("<ul>");
                for (Iterator it = operations.iterator(); it.hasNext(); ) {
                    OperationDesc desc = (OperationDesc) it.next();
                    writer.println("<li>" + desc.getName());
                }
                writer.println("</ul>");
            }
        }
        writer.println("</ul>");
    }

    /**
     * generate the error response to indicate that there is apparently no endpoint there
     * @param request the request that didnt have an edpoint
     * @param response response we are generating
     * @param writer open writer for the request
     */
    protected void reportCantGetAxisService(HttpServletRequest request,
                                            HttpServletResponse response,
                                            PrintWriter writer) {
        // no such service....
        response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
        response.setContentType("text/html; charset=utf-8");
        writer.println("<h2>" +
                       Messages.getMessage("error00") + "</h2>");
        writer.println("<p>" +
                       Messages.getMessage("noService06") +
                       "</p>");
    }

    /**
     * probe for a JWS page and report 'no service' if one is not found there
     * @param request the request that didnt have an edpoint
     * @param response response we are generating
     * @param writer open writer for the request
     */
    protected void reportCantGetJWSService(HttpServletRequest request,
                                           HttpServletResponse response,
                                           PrintWriter writer) {
        // first look to see if there is a service
        // requestPath is a work around to support serving .jws web services
        // from services URL - see AXIS-843 for more information
        String requestPath = request.getServletPath() + ((request.getPathInfo() != null) ?
                request.getPathInfo() : "");
        String realpath = getServletConfig().getServletContext()
                          .getRealPath(requestPath);
        log.debug("JWS real path: " + realpath);
        boolean foundJWSFile = (new File(realpath).exists()) &&
                               (realpath.endsWith(Constants.
                                                  JWS_DEFAULT_FILE_EXTENSION));
        response.setContentType("text/html; charset=utf-8");
        if (foundJWSFile) {
            response.setStatus(HttpURLConnection.HTTP_OK);
            writer.println(Messages.getMessage("foundJWS00") + "<p>");
            String url = request.getRequestURI();
            String urltext = Messages.getMessage("foundJWS01");
            writer.println("<a href='" + url + "?wsdl'>" + urltext + "</a>");
        } else {
            response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
            writer.println(Messages.getMessage("noService06"));
        }
    }


    /**
     * Process a POST to the servlet by handing it off to the Axis Engine.
     * Here is where SOAP messages are received
     * @param req posted request
     * @param res respose
     * @throws ServletException trouble
     * @throws IOException different trouble
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        long t0 = 0, t1 = 0, t2 = 0, t3 = 0, t4 = 0;
        String soapAction = null;
        MessageContext msgContext = null;
        if (isDebug) {
            log.debug("Enter: doPost()");
        }
        if (tlog.isDebugEnabled()) {
            t0 = System.currentTimeMillis();
        }

        Message responseMsg = null;
        String contentType = null;

        try {
            AxisEngine engine = getEngine();

            if (engine == null) {
                // !!! should return a SOAP fault...
                ServletException se =
                        new ServletException(Messages.getMessage("noEngine00"));
                log.debug("No Engine!", se);
                throw se;
            }

            res.setBufferSize(1024 * 8); // provide performance boost.

            /** get message context w/ various properties set
             */
            msgContext = createMessageContext(engine, req, res);

            // ? OK to move this to 'getMessageContext',
            // ? where it would also be picked up for 'doGet()' ?
            if (securityProvider != null) {
                if (isDebug) {
                    log.debug("securityProvider:" + securityProvider);
                }
                msgContext.setProperty(MessageContext.SECURITY_PROVIDER,
                                       securityProvider);
            }

            /* Get request message
             */
            Message requestMsg =
                    new Message(req.getInputStream(),
                                false,
                                req.getHeader(HTTPConstants.HEADER_CONTENT_TYPE),
                                req.getHeader(HTTPConstants.
                                              HEADER_CONTENT_LOCATION));
            // Transfer HTTP headers to MIME headers for request message.
            MimeHeaders requestMimeHeaders = requestMsg.getMimeHeaders();
            for (Enumeration e = req.getHeaderNames(); e.hasMoreElements(); ) {
                String headerName = (String) e.nextElement();
                for (Enumeration f = req.getHeaders(headerName);
                                     f.hasMoreElements(); ) {
                    String headerValue = (String) f.nextElement();
                    requestMimeHeaders.addHeader(headerName, headerValue);
                }
            }

            if (isDebug) {
                log.debug("Request Message:" + requestMsg);

                /* Set the request(incoming) message field in the context */
                /**********************************************************/
            }
            msgContext.setRequestMessage(requestMsg);
            String url = HttpUtils.getRequestURL(req).toString();
            msgContext.setProperty(MessageContext.TRANS_URL, url);
            // put character encoding of request to message context
            // in order to reuse it during the whole process.
            String requestEncoding;
            try {
                requestEncoding = (String) requestMsg.getProperty(SOAPMessage.
                        CHARACTER_SET_ENCODING);
                if (requestEncoding != null) {
                    msgContext.setProperty(SOAPMessage.CHARACTER_SET_ENCODING,
                                           requestEncoding);
                }
            } catch (SOAPException e1) {
            }

            try {
                /**
                 * Save the SOAPAction header in the MessageContext bag.
                 * This will be used to tell the Axis Engine which service
                 * is being invoked.  This will save us the trouble of
                 * having to parse the Request message - although we will
                 * need to double-check later on that the SOAPAction header
                 * does in fact match the URI in the body.
                 */
                // (is this last stmt true??? (I don't think so - Glen))
                /********************************************************/
                soapAction = getSoapAction(req);

                if (soapAction != null) {
                    msgContext.setUseSOAPAction(true);
                    msgContext.setSOAPActionURI(soapAction);
                }

                // Create a Session wrapper for the HTTP session.
                // These can/should be pooled at some point.
                // (Sam is Watching! :-)
                msgContext.setSession(new AxisHttpSession(req));

                if (tlog.isDebugEnabled()) {
                    t1 = System.currentTimeMillis();
                }
                /* Invoke the Axis engine... */
                /*****************************/
                if (isDebug) {
                    log.debug("Invoking Axis Engine.");
                    //here we run the message by the engine
                }
                engine.invoke(msgContext);
                if (isDebug) {
                    log.debug("Return from Axis Engine.");
                }
                if (tlog.isDebugEnabled()) {
                    t2 = System.currentTimeMillis();
                }
                responseMsg = msgContext.getResponseMessage();

                // We used to throw exceptions on null response messages.
                // They are actually OK in certain situations (asynchronous
                // services), so fall through here and return an ACCEPTED
                // status code below.  Might want to install a configurable
                // error check for this later.
            } catch (AxisFault fault) {
                //log and sanitize
                processAxisFault(fault);
                configureResponseFromAxisFault(res, fault);
                responseMsg = msgContext.getResponseMessage();
                if (responseMsg == null) {
                    responseMsg = new Message(fault);
                    ((org.apache.axis.SOAPPart) responseMsg.getSOAPPart()).
                            getMessage().setMessageContext(msgContext);
                }
            } catch (Exception e) {
                //other exceptions are internal trouble
                responseMsg = msgContext.getResponseMessage();
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                responseMsg = convertExceptionToAxisFault(e, responseMsg);
                ((org.apache.axis.SOAPPart) responseMsg.getSOAPPart()).
                        getMessage().setMessageContext(msgContext);
            } catch (Throwable t) {
                logException(t);
                //other exceptions are internal trouble
                responseMsg = msgContext.getResponseMessage();
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                responseMsg = new Message(new AxisFault(t.toString(),t));
                ((org.apache.axis.SOAPPart) responseMsg.getSOAPPart()).
                        getMessage().setMessageContext(msgContext);
            }
        } catch (AxisFault fault) {
            processAxisFault(fault);
            configureResponseFromAxisFault(res, fault);
            responseMsg = msgContext.getResponseMessage();
            if (responseMsg == null) {
                responseMsg = new Message(fault);
                ((org.apache.axis.SOAPPart) responseMsg.getSOAPPart()).
                        getMessage().setMessageContext(msgContext);
            }
        }

        if (tlog.isDebugEnabled()) {
            t3 = System.currentTimeMillis();
        }

        /* Send response back along the wire...  */
        /***********************************/
        if (responseMsg != null) {
            // Transfer MIME headers to HTTP headers for response message.
            MimeHeaders responseMimeHeaders = responseMsg.getMimeHeaders();
            for (Iterator i = responseMimeHeaders.getAllHeaders(); i.hasNext(); ) {
                MimeHeader responseMimeHeader = (MimeHeader) i.next();
                res.addHeader(responseMimeHeader.getName(),
                              responseMimeHeader.getValue());
            }
            // synchronize the character encoding of request and response
            String responseEncoding = (String) msgContext.getProperty(
                    SOAPMessage.CHARACTER_SET_ENCODING);
            if (responseEncoding != null) {
                try {
                    responseMsg.setProperty(SOAPMessage.CHARACTER_SET_ENCODING,
                                            responseEncoding);
                } catch (SOAPException e) {
                }
            }
            //determine content type from message response
            contentType = responseMsg.getContentType(msgContext.
                    getSOAPConstants());
            sendResponse(contentType, res, responseMsg);
        } else {
            // No content, so just indicate accepted
            res.setStatus(202);
        }
        
        if (isDebug) {
            log.debug("Response sent.");
            log.debug("Exit: doPost()");
        }
        if (tlog.isDebugEnabled()) {
            t4 = System.currentTimeMillis();
            tlog.debug("axisServlet.doPost: " + soapAction +
                       " pre=" + (t1 - t0) +
                       " invoke=" + (t2 - t1) +
                       " post=" + (t3 - t2) +
                       " send=" + (t4 - t3) +
                       " " + msgContext.getTargetService() + "." +
                       ((msgContext.getOperation() == null) ?
                        "" : msgContext.getOperation().getName()));
        }

    }

    /**
     * Configure the servlet response status code and maybe other headers
     * from the fault info.
     * @param response response to configure
     * @param fault what went wrong
     */
    private void configureResponseFromAxisFault(HttpServletResponse response,
                                                AxisFault fault) {
        // then get the status code
        // It's been suggested that a lack of SOAPAction
        // should produce some other error code (in the 400s)...
        int status = getHttpServletResponseStatus(fault);
        if (status == HttpServletResponse.SC_UNAUTHORIZED) {
            // unauth access results in authentication request
            // TODO: less generic realm choice?
            response.setHeader("WWW-Authenticate", "Basic realm=\"AXIS\"");
        }
        response.setStatus(status);
    }

    /**
     * turn any Exception into an AxisFault, log it, set the response
     * status code according to what the specifications say and
     * return a response message for posting. This will be the response
     * message passed in if non-null; one generated from the fault otherwise.
     *
     * @param exception what went wrong
     * @param responseMsg what response we have (if any)
     * @return a response message to send to the user
     */
    private Message convertExceptionToAxisFault(Exception exception,
                                                Message responseMsg) {
        logException(exception);
        if (responseMsg == null) {
            AxisFault fault = AxisFault.makeFault(exception);
            processAxisFault(fault);
            responseMsg = new Message(fault);
        }
        return responseMsg;
    }

    /**
     * Extract information from AxisFault and map it to a HTTP Status code.
     *
     * @param af Axis Fault
     * @return HTTP Status code.
     */
    protected int getHttpServletResponseStatus(AxisFault af) {
        // TODO: Should really be doing this with explicit AxisFault
        // subclasses... --Glen
        return af.getFaultCode().getLocalPart().startsWith("Server.Unauth")
                ? HttpServletResponse.SC_UNAUTHORIZED
                : HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        // This will raise a 401 for both
        // "Unauthenticated" & "Unauthorized"...
    }

    /**
     * write a message to the response, set appropriate headers for content
     * type..etc.
     * @param res   response
     * @param responseMsg message to write
     * @throws AxisFault
     * @throws IOException if the response stream can not be written to
     */
    private void sendResponse(String contentType,
                              HttpServletResponse res,
                              Message responseMsg) throws AxisFault,
            IOException {
        if (responseMsg == null) {
            res.setStatus(HttpServletResponse.SC_NO_CONTENT);
            if (isDebug) {
                log.debug("NO AXIS MESSAGE TO RETURN!");
                //String resp = Messages.getMessage("noData00");
                //res.setContentLength((int) resp.getBytes().length);
                //res.getWriter().print(resp);
            }
        } else {
            if (isDebug) {
                log.debug("Returned Content-Type:" +
                          contentType);
                // log.debug("Returned Content-Length:" +
                //          responseMsg.getContentLength());
            }

            try {
                res.setContentType(contentType);

                /* My understand of Content-Length
                 * HTTP 1.0
                 *   -Required for requests, but optional for responses.
                 * HTTP 1.1
                 *  - Either Content-Length or HTTP Chunking is required.
                 *   Most servlet engines will do chunking if content-length is not specified.
                 *
                 *
                 */

                //if(clientVersion == HTTPConstants.HEADER_PROTOCOL_V10) //do chunking if necessary.
                //     res.setContentLength(responseMsg.getContentLength());

                responseMsg.writeTo(res.getOutputStream());
            } catch (SOAPException e) {
                logException(e);
            }
        }

        if (!res.isCommitted()) {
            res.flushBuffer(); // Force it right now.
        }
    }

    /**
     * Place the Request message in the MessagContext object - notice
     * that we just leave it as a 'ServletRequest' object and let the
     * Message processing routine convert it - we don't do it since we
     * don't know how it's going to be used - perhaps it might not
     * even need to be parsed.
     * @return a message context
     */
    private MessageContext createMessageContext(AxisEngine engine,
                                                HttpServletRequest req,
                                                HttpServletResponse res) {
        MessageContext msgContext = new MessageContext(engine);

        String requestPath = getRequestPath(req);

        if (isDebug) {
            log.debug("MessageContext:" + msgContext);
            log.debug("HEADER_CONTENT_TYPE:" +
                      req.getHeader(HTTPConstants.HEADER_CONTENT_TYPE));
            log.debug("HEADER_CONTENT_LOCATION:" +
                      req.getHeader(HTTPConstants.HEADER_CONTENT_LOCATION));
            log.debug("Constants.MC_HOME_DIR:" + String.valueOf(getHomeDir()));
            log.debug("Constants.MC_RELATIVE_PATH:" + requestPath);
            log.debug("HTTPConstants.MC_HTTP_SERVLETLOCATION:" +
                      String.valueOf(getWebInfPath()));
            log.debug("HTTPConstants.MC_HTTP_SERVLETPATHINFO:" +
                      req.getPathInfo());
            log.debug("HTTPConstants.HEADER_AUTHORIZATION:" +
                      req.getHeader(HTTPConstants.HEADER_AUTHORIZATION));
            log.debug("Constants.MC_REMOTE_ADDR:" + req.getRemoteAddr());
            log.debug("configPath:" + String.valueOf(getWebInfPath()));
        }

        /* Set the Transport */
        /*********************/
        msgContext.setTransportName(transportName);

        /* Save some HTTP specific info in the bag in case someone needs it */
        /********************************************************************/
        msgContext.setProperty(Constants.MC_JWS_CLASSDIR, jwsClassDir);
        msgContext.setProperty(Constants.MC_HOME_DIR, getHomeDir());
        msgContext.setProperty(Constants.MC_RELATIVE_PATH, requestPath);
        msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLET, this);
        msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST, req);
        msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETRESPONSE, res);
        msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETLOCATION,
                               getWebInfPath());
        msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETPATHINFO,
                               req.getPathInfo());
        msgContext.setProperty(HTTPConstants.HEADER_AUTHORIZATION,
                               req.getHeader(HTTPConstants.HEADER_AUTHORIZATION));
        msgContext.setProperty(Constants.MC_REMOTE_ADDR, req.getRemoteAddr());

        // Set up a javax.xml.rpc.server.ServletEndpointContext
        ServletEndpointContextImpl sec = new ServletEndpointContextImpl();

        msgContext.setProperty(Constants.MC_SERVLET_ENDPOINT_CONTEXT, sec);
        /* Save the real path */
        /**********************/
        String realpath = getServletConfig().getServletContext()
                          .getRealPath(requestPath);

        if (realpath != null) {
            msgContext.setProperty(Constants.MC_REALPATH, realpath);
        }

        msgContext.setProperty(Constants.MC_CONFIGPATH, getWebInfPath());

        return msgContext;
    }

    /**
     * Extract the SOAPAction header.
     * if SOAPAction is null then we'll we be forced to scan the body for it.
     * if SOAPAction is "" then use the URL
     * @param req incoming request
     * @return the action
     * @throws AxisFault
     */
    private String getSoapAction(HttpServletRequest req) throws AxisFault {
        String soapAction = req.getHeader(HTTPConstants.HEADER_SOAP_ACTION);
        if (soapAction == null) {
            String contentType = req.getHeader(HTTPConstants.HEADER_CONTENT_TYPE);
            if(contentType != null) {
                int index = contentType.indexOf("action");
                if(index != -1){
                    soapAction = contentType.substring(index + 7);
                }
            }
        }

        if (isDebug) {
            log.debug("HEADER_SOAP_ACTION:" + soapAction);

            /**
             * Technically, if we don't find this header, we should probably fault.
             * It's required in the SOAP HTTP binding.
             */
        }
        if (soapAction == null) {
            AxisFault af = new AxisFault("Client.NoSOAPAction",
                                         Messages.getMessage("noHeader00",
                    "SOAPAction"),
                                         null, null);

            exceptionLog.error(Messages.getMessage("genFault00"), af);

            throw af;
        }
        // the SOAP 1.1 spec & WS-I 1.0 says:
        // soapaction    = "SOAPAction" ":" [ <"> URI-reference <"> ]
        // some implementations leave off the quotes
        // we strip them if they are present
        if (soapAction.startsWith("\"") && soapAction.endsWith("\"")
            && soapAction.length() >= 2) {
            int end = soapAction.length() - 1;
            soapAction = soapAction.substring(1, end);
        }

        if (soapAction.length() == 0) {
            soapAction = req.getContextPath(); // Is this right?

        }
        return soapAction;
    }

    /**
     * Provided to allow overload of default JWSClassDir
     * by derived class.
     * @return directory for JWS files
     */
    protected String getDefaultJWSClassDir() {
        return (getWebInfPath() == null)
                ? null // ??? what is a good FINAL default for WebLogic?
                : getWebInfPath() + File.separator + "jwsClasses";
    }

    /**
     * Initialize a Handler for the transport defined in the Axis server config.
     * This includes optionally filling in query string handlers.
     */

    public void initQueryStringHandlers() {
        try {
            this.transport = getEngine().getTransport(this.transportName);

            if (this.transport == null) {
                // No transport by this name is defined.  Therefore, fill in default
                // query string handlers.

                this.transport = new SimpleTargetedChain();

                this.transport.setOption("qs.list",
                                         "org.apache.axis.transport.http.QSListHandler");
                this.transport.setOption("qs.method",
                                         "org.apache.axis.transport.http.QSMethodHandler");
                this.transport.setOption("qs.wsdl",
                                         "org.apache.axis.transport.http.QSWSDLHandler");

                return;
            }

            else {
                // See if we should use the default query string handlers.
                // By default, set this to true (for backwards compatibility).

                boolean defaultQueryStrings = true;
                String useDefaults = (String)this.transport.getOption(
                        "useDefaultQueryStrings");

                if ((useDefaults != null) &&
                    useDefaults.toLowerCase().equals("false")) {
                    defaultQueryStrings = false;
                }

                if (defaultQueryStrings == true) {
                    // We should use defaults, so fill them in.

                    this.transport.setOption("qs.list",
                                             "org.apache.axis.transport.http.QSListHandler");
                    this.transport.setOption("qs.method",
                                             "org.apache.axis.transport.http.QSMethodHandler");
                    this.transport.setOption("qs.wsdl",
                                             "org.apache.axis.transport.http.QSWSDLHandler");
                }
            }
        }

        catch (AxisFault e) {
            // Some sort of problem occurred, let's just make a default transport.

            this.transport = new SimpleTargetedChain();

            this.transport.setOption("qs.list",
                                     "org.apache.axis.transport.http.QSListHandler");
            this.transport.setOption("qs.method",
                                     "org.apache.axis.transport.http.QSMethodHandler");
            this.transport.setOption("qs.wsdl",
                                     "org.apache.axis.transport.http.QSWSDLHandler");

            return;
        }
    }

    /**
     * Attempts to invoke a plugin for the query string supplied in the URL.
     *
     * @param request the servlet's HttpServletRequest object.
     * @param response the servlet's HttpServletResponse object.
     * @param writer the servlet's PrintWriter object.
     */

    private boolean processQuery(HttpServletRequest request,
                                 HttpServletResponse response,
                                 PrintWriter writer) throws AxisFault {
        // Attempt to instantiate a plug-in handler class for the query string
        // handler classes defined in the HTTP transport.

        String path = request.getServletPath();
        String queryString = request.getQueryString();
        String serviceName;
        AxisEngine engine = getEngine();
        Iterator i = this.transport.getOptions().keySet().iterator();

        if (queryString == null) {
            return false;
        }

        String servletURI = request.getContextPath() + path;
        String reqURI = request.getRequestURI();
        // chop off '/'.
        if (servletURI.length() + 1 < reqURI.length()) {
            serviceName = reqURI.substring(servletURI.length() + 1);
        } else {
            serviceName = "";
        } while (i.hasNext() == true) {
            String queryHandler = (String) i.next();

            if (queryHandler.startsWith("qs.") == true) {
                // Only attempt to match the query string with transport
                // parameters prefixed with "qs:".

                String handlerName = queryHandler.substring
                                     (queryHandler.indexOf(".") + 1).
                                     toLowerCase();

                // Determine the name of the plugin to invoke by using all text
                // in the query string up to the first occurence of &, =, or the
                // whole string if neither is present.

                int length = 0;
                boolean firstParamFound = false;

                while (firstParamFound == false && length < queryString.length()) {
                    char ch = queryString.charAt(length++);

                    if (ch == '&' || ch == '=') {
                        firstParamFound = true;

                        --length;
                    }
                }

                if (length < queryString.length()) {
                    queryString = queryString.substring(0, length);
                }

                if (queryString.toLowerCase().equals(handlerName) == true) {
                    // Query string matches a defined query string handler name.

                    // If the defined class name for this query string handler is blank,
                    // just return (the handler is "turned off" in effect).

                    if (this.transport.getOption(queryHandler).equals("")) {
                        return false;
                    }

                    try {
                        // Attempt to dynamically load the query string handler
                        // and its "invoke" method.

                        MessageContext msgContext = createMessageContext(engine,
                                request, response);
                        Class plugin = Class.forName((String)this.transport.
                                getOption(queryHandler));
                        Method pluginMethod = plugin.getDeclaredMethod("invoke",
                                new Class[] {msgContext.getClass()});
                        String url = HttpUtils.getRequestURL(request).toString();

                        // Place various useful servlet-related objects in
                        // the MessageContext object being delivered to the
                        // plugin.
                        msgContext.setProperty(MessageContext.TRANS_URL, url);
                        msgContext.setProperty(HTTPConstants.
                                               PLUGIN_SERVICE_NAME, serviceName);
                        msgContext.setProperty(HTTPConstants.PLUGIN_NAME,
                                               handlerName);
                        msgContext.setProperty(HTTPConstants.
                                               PLUGIN_IS_DEVELOPMENT,
                                               new Boolean(isDevelopment()));
                        msgContext.setProperty(HTTPConstants.PLUGIN_ENABLE_LIST,
                                               new Boolean(enableList));
                        msgContext.setProperty(HTTPConstants.PLUGIN_ENGINE,
                                               engine);
                        msgContext.setProperty(HTTPConstants.PLUGIN_WRITER,
                                               writer);
                        msgContext.setProperty(HTTPConstants.PLUGIN_LOG, log);
                        msgContext.setProperty(HTTPConstants.
                                               PLUGIN_EXCEPTION_LOG,
                                               exceptionLog);

                        // Invoke the plugin.

                        pluginMethod.invoke(plugin.newInstance(),
                                            new Object[] {msgContext});

                        writer.close();

                        return true;
                    } catch (InvocationTargetException ie) {
                        reportTroubleInGet(ie.getTargetException(), response,
                                           writer);
                        // return true to prevent any further processing
                        return true;
                    } catch (Exception e) {
                        reportTroubleInGet(e, response, writer);
                        // return true to prevent any further processing
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * getRequestPath a returns request path for web service padded with
     * request.getPathInfo for web services served from /services directory.
     * This is a required to support serving .jws web services from /services
     * URL. See AXIS-843 for more information.
     *
     * @param request HttpServletRequest
     * @return String
     */
    private static String getRequestPath(HttpServletRequest request) {
        return request.getServletPath() + ((request.getPathInfo() != null) ?
                                           request.getPathInfo() : "");
    }
}
/* @generated */
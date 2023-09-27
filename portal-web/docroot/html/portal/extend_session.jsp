<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<%
String requestedSessionId = request.getRequestedSessionId();

if (Validator.isNotNull(requestedSessionId)) {
	if (CompoundSessionIdSplitterUtil.hasSessionDelimiter()) {
		requestedSessionId = CompoundSessionIdSplitterUtil.parseSessionId(requestedSessionId);
	}

	if (!StringUtil.equals(requestedSessionId, session.getId())) {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		if (_log.isWarnEnabled()) {
			_log.warn("Unable to extend the HTTP session.");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("The requested session " + requestedSessionId + " is not the same as session " + session.getId());
		}
	}
}

for (String servletContextName : ServletContextPool.keySet()) {
	ServletContext servletContext = ServletContextPool.get(servletContextName);

	if (Validator.isNull(servletContextName) || servletContextName.equals(PortalUtil.getPathContext())) {
		continue;
	}

	PortletApp portletApp = PortletLocalServiceUtil.getPortletApp(servletContextName);

	for (Portlet portlet : portletApp.getPortlets()) {
		PortletConfig portletConfig = PortletConfigFactoryUtil.create(portlet, servletContext);

		String invokerPortletName = portletConfig.getInitParameter(InvokerPortlet.INIT_INVOKER_PORTLET_NAME);

		if (invokerPortletName == null) {
			invokerPortletName = portletConfig.getPortletName();
		}

		String path = StringBundler.concat(StringPool.SLASH, invokerPortletName, "/invoke");

		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);

		if (requestDispatcher == null) {
			continue;
		}

		request.setAttribute(WebKeys.EXTEND_SESSION, Boolean.TRUE);

		try {
			requestDispatcher.include(request, response);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to extend session for " + servletContextName);
			}
		}
	}
}
%>

<%!
private static final Log _log = LogFactoryUtil.getLog("portal_web.docroot.html.portal.extend_session_jsp");
%>
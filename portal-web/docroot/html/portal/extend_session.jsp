<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<%
for (String servletContextName : ServletContextPool.keySet()) {
	
	ServletContext servletContext = ServletContextPool.get(servletContextName);

	if (Validator.isNotNull(servletContextName) &&
		!servletContextName.equals(PortalUtil.getPathContext())) {
		
		PortletApp portletApp = PortletLocalServiceUtil.getPortletApp(servletContextName);

		List<Portlet> portlets = portletApp.getPortlets();

		for (Portlet portlet : portlets) {
			String path = StringPool.SLASH + portlet.getPortletName() + "/invoke";

			RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);

			request.setAttribute(WebKeys.EXTEND_SESSION, Boolean.TRUE);

			try {
				requestDispatcher.include(request, response);
			}
			catch (Exception e) {
				_log.warn("Unable to extend session for: " + servletContextName);
			}
		}
	}
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portal.extend_session_jsp");
%>
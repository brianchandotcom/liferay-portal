<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/init.jsp" %>

<%
try {
	xmlUrl = StringUtil.replace(xmlUrl, new String[] {"@portal_url@", "@portlet_context_url@"}, new String[] {themeDisplay.getPortalURL(), themeDisplay.getPortalURL() + request.getContextPath()});
	xslUrl = StringUtil.replace(xslUrl, new String[] {"@portal_url@", "@portlet_context_url@"}, new String[] {themeDisplay.getPortalURL(), themeDisplay.getPortalURL() + request.getContextPath()});

	XSLContentConfiguration xslContentConfiguration = (XSLContentConfiguration)request.getAttribute(XSLContentConfiguration.class.getName());

	String content = XSLContentUtil.transform(xslContentConfiguration, new URL(xmlUrl), new URL(xslUrl));
%>

	<%= content %>

<%
}
catch (Exception e) {
	_log.error(e.getMessage());
%>

	<div class="alert alert-danger">
		<liferay-ui:message key="an-error-occurred-while-processing-your-xml-and-xsl" />
	</div>

<%
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_xsl_content_web.view_jsp");
%>
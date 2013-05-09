<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="init.jsp" %>

<%@ page import="com.liferay.portal.model.Portlet" %>
<%@ page import="com.liferay.portal.service.PortletLocalServiceUtil" %>

<%@ page import="java.util.Set" %>

<%
Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());

Set<String> paths = application.getResourcePaths("/WEB-INF/jsp/" + portlet.getFriendlyURLMapping() + "/controllers/");

for (String path : paths) {
	int x = path.lastIndexOf("/");
	int y = path.indexOf("_controller.jsp");

	if (y == -1) {
		continue;
	}

	String controller = path.substring(x + 1, y);
%>

	<portlet:resourceURL var="resourceURL">
		<portlet:param name="controller" value="<%= controller %>" />
		<portlet:param name="action" value="touch" />
	</portlet:resourceURL>

	<iframe height="0" src="<%= resourceURL %>" style="display: none; visibility: hidden;" width="0"></iframe>

<%
}
%>
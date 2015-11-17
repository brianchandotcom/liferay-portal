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

<%@ include file="/panel_app/init.jsp" %>

<%
boolean active = GetterUtil.getBoolean(request.getAttribute("liferay-application-list:panel-app:active"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-application-list:panel-app:data");
String id = (String)request.getAttribute("liferay-application-list:panel-app:id");
String label = (String)request.getAttribute("liferay-application-list:panel-app:label");
PanelApp panelApp = (PanelApp)request.getAttribute("liferay-application-list:panel-app:panelApp");
String url = (String)request.getAttribute("liferay-application-list:panel-app:url");
%>

<c:if test="<%= Validator.isNotNull(url) %>">
	<li
		aria-selected="<%= active ? "true" : StringPool.BLANK %>"
		class="<%= active ? "active" : StringPool.BLANK %>"
		role="presentation"
	>
		<aui:a
			ariaRole="menuitem"
			data="<%= data %>"
			href="<%= url %>"
			id="<%= id %>"
		>
			<%= label %>
			<c:if test="<%= (panelApp != null) && (panelApp.getNotificationsCount() > 0) %>">
				<span class="badge badge-sm badge-warning pull-right"><%= panelApp.getNotificationsCount() %></span>
			</c:if>
		</aui:a>
	</li>
</c:if>
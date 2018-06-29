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
String tabs1 = ParamUtil.getString(request, "tabs1");

String displayStyle = ParamUtil.getString(request, "displayStyle", "descriptive");
String navigation = ParamUtil.getString(request, "navigation", "all");
String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");
String searchContainerId = ParamUtil.getString(request, "searchContainerId");
%>

<liferay-ui:success key="localStagingEnabled" message="local-staging-is-successfully-enabled" />

<liferay-ui:success key="remoteStagingEnabled" message="remote-staging-is-successfully-enabled" />

<div id="<portlet:namespace />publishProcessesSearchContainer">
	<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
		<liferay-util:param name="mvcRenderCommandName" value="viewPublishLayouts" />
		<liferay-util:param name="tabs1" value="<%= tabs1 %>" />
		<liferay-util:param name="displayStyle" value="<%= displayStyle %>" />
		<liferay-util:param name="navigation" value="<%= navigation %>" />
		<liferay-util:param name="orderByCol" value="<%= orderByCol %>" />
		<liferay-util:param name="orderByType" value="<%= orderByType %>" />
		<liferay-util:param name="searchContainerId" value="<%= searchContainerId %>" />
	</liferay-util:include>

	<div class="container-fluid-1280" id="<portlet:namespace />processesContainer">
		<liferay-util:include page="/processes_list/publish_layouts_processes.jsp" servletContext="<%= application %>" />
	</div>
</div>
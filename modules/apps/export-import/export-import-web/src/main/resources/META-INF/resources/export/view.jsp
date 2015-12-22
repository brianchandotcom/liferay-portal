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

<%@ include file="/export/init.jsp" %>

<liferay-staging:defineObjects />

<%
if (liveGroup == null) {
	liveGroup = group;
	liveGroupId = groupId;
}
%>

<liferay-util:include page="/export/navigation.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280" id="<portlet:namespace />processesContainer">
	<liferay-util:include page="/export/processes_list/export_layouts_processes.jsp" servletContext="<%= application %>">
		<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		<liferay-util:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
		<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
	</liferay-util:include>
</div>

<portlet:renderURL var="addNewExportProcessURL">
	<portlet:param name="mvcPath" value="/export/new_export/export_layouts.jsp" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
	<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
</portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "export") %>' url="<%= addNewExportProcessURL %>" />
</liferay-frontend:add-menu>

<aui:script use="liferay-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="exportLayouts" var="exportProcessesURL">
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
	</liferay-portlet:resourceURL>

	new Liferay.ExportImport(
		{
			exportLAR: true,
			incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
			locale: '<%= locale.toLanguageTag() %>',
			namespace: '<portlet:namespace />',
			processesNode: '#exportProcessesSearchContainer',
			processesResourceURL: '<%= exportProcessesURL.toString() %>',
		}
	);
</aui:script>
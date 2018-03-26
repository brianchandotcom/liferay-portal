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

<%
long groupId = ParamUtil.getLong(request, "groupId");
long liveGroupId = ParamUtil.getLong(request, "liveGroupId");
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
String displayStyle = ParamUtil.getString(request, "displayStyle");

List<ExportImportConfiguration> exportImportConfigurations = ExportImportConfigurationLocalServiceUtil.getExportImportConfigurations(liveGroupId, ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT);
%>

<liferay-frontend:add-menu
	inline="<%= true %>"
>

	<%
	for (ExportImportConfiguration exportImportConfiguration : exportImportConfigurations) {
		Map<String, Serializable> settingsMap = exportImportConfiguration.getSettingsMap();
	%>

		<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="addNewExportProcessURL">
			<portlet:param name="mvcPath" value="/export/new_export/export_layouts.jsp" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
			<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
			<portlet:param name="privateLayout" value='<%= MapUtil.getString(settingsMap, "privateLayout") %>' />
			<portlet:param name="displayStyle" value="<%= displayStyle %>" />
		</portlet:renderURL>

		<liferay-frontend:add-menu-item
			title="<%= exportImportConfiguration.getName() %>"
			url="<%= addNewExportProcessURL %>"
		/>

	<%
	}
	%>

	<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="addNewCustomExportProcessURL">
		<portlet:param name="mvcPath" value="/export/new_export/export_layouts.jsp" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
		<portlet:param name="displayStyle" value="<%= displayStyle %>" />
	</liferay-portlet:renderURL>

	<liferay-frontend:add-menu-item
		title='<%= LanguageUtil.get(request, "custom-export") %>'
		url="<%= addNewCustomExportProcessURL %>"
	/>
</liferay-frontend:add-menu>
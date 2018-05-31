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
int configurationType = ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL;
boolean localPublishing = true;

if (stagingGroup.isStagedRemotely()) {
	configurationType = ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE;
	localPublishing = false;
}

List<ExportImportConfiguration> exportImportConfigurations = ExportImportConfigurationLocalServiceUtil.getExportImportConfigurations(stagingGroupId, configurationType);
%>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, stagingGroupId, ActionKeys.PUBLISH_STAGING) %>">
	<liferay-frontend:add-menu
		inline="<%= true %>"
	>

		<%
		for (ExportImportConfiguration exportImportConfiguration : exportImportConfigurations) {
		%>

			<portlet:renderURL var="addNewProcessURL">
				<portlet:param name="mvcRenderCommandName" value="publishLayouts" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />
				<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu-item
				title="<%= exportImportConfiguration.getName() %>"
				url="<%= addNewProcessURL %>"
			/>

		<%
		}
		%>

		<portlet:renderURL var="addNewCustomProcessURL">
			<portlet:param name="mvcRenderCommandName" value="publishLayouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
			<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
		</portlet:renderURL>

		<liferay-frontend:add-menu-item
			title='<%= LanguageUtil.get(request, "custom-publication") %>'
			url="<%= addNewCustomProcessURL %>"
		/>
	</liferay-frontend:add-menu>
</c:if>
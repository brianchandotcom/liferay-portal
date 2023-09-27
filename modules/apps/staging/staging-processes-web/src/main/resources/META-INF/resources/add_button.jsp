<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	<liferay-ui:icon-menu
		icon="plus"
	>

		<%
		for (ExportImportConfiguration exportImportConfiguration : exportImportConfigurations) {
		%>

			<portlet:renderURL var="addNewProcessURL">
				<portlet:param name="mvcRenderCommandName" value="/staging_processes/publish_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />
				<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				message="<%= exportImportConfiguration.getName() %>"
				url="<%= addNewProcessURL %>"
			/>

		<%
		}
		%>

		<portlet:renderURL var="addNewCustomProcessURL">
			<portlet:param name="mvcRenderCommandName" value="/staging_processes/publish_layouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
			<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message='<%= LanguageUtil.get(request, "custom-publish-process") %>'
			url="<%= addNewCustomProcessURL %>"
		/>
	</liferay-ui:icon-menu>
</c:if>
<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long layoutSetBranchId = GetterUtil.getLong(request.getAttribute("view.jsp-layoutSetBranchId"));
String layoutSetBranchName = GetterUtil.getString(request.getAttribute("view.jsp-layoutSetBranchName"));

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

ExportImportConfiguration exportImportConfiguration = (ExportImportConfiguration)row.getObject();

boolean localPublishing = true;

if (exportImportConfiguration.getType() == ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE) {
	localPublishing = false;
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="newPublishProcessURL">
		<portlet:param name="mvcRenderCommandName" value="/staging_processes/publish_layouts" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />
		<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="publish"
		url="<%= newPublishProcessURL %>"
	/>

	<portlet:renderURL var="deleteRedirectURL">
		<portlet:param name="mvcRenderCommandName" value="/staging_processes/view_publish_configurations" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
		<portlet:param name="layoutSetBranchName" value="<%= layoutSetBranchName %>" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
	</portlet:renderURL>

	<portlet:actionURL name="/staging_processes/edit_publish_configuration" var="deletePublishConfigurationURL">
		<portlet:param name="mvcRenderCommandName" value="/staging_processes/edit_publish_configuration" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= trashHelper.isTrashEnabled(groupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= deleteRedirectURL %>" />
		<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		trash="<%= trashHelper.isTrashEnabled(groupId) %>"
		url="<%= deletePublishConfigurationURL %>"
	/>
</liferay-ui:icon-menu>
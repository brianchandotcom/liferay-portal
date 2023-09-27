<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/export/init.jsp" %>

<%
long groupId = GetterUtil.getLong(request.getAttribute("view.jsp-groupId"));
long liveGroupId = GetterUtil.getLong(request.getAttribute("view.jsp-liveGroupId"));
boolean privateLayout = GetterUtil.getBoolean(request.getAttribute("view.jsp-privateLayout"));

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

ExportImportConfiguration exportImportConfiguration = (ExportImportConfiguration)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="newExportProcessURL">
		<portlet:param name="mvcPath" value="/export/new_export/export_layouts.jsp" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
		<portlet:param name="backURL" value="<%= String.valueOf(currentURL) %>" />
		<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="export"
		url="<%= newExportProcessURL %>"
	/>

	<portlet:renderURL var="deleteRedirectURL">
		<portlet:param name="mvcRenderCommandName" value="/export_import/view_export_configurations" />
	</portlet:renderURL>

	<portlet:actionURL name="/export_import/edit_export_configuration" var="deleteExportConfigurationURL">
		<portlet:param name="mvcRenderCommandName" value="/export_import/edit_export_configuration" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= trashHelper.isTrashEnabled(liveGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= deleteRedirectURL %>" />
		<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		trash="<%= trashHelper.isTrashEnabled(liveGroupId) %>"
		url="<%= deleteExportConfigurationURL %>"
	/>
</liferay-ui:icon-menu>
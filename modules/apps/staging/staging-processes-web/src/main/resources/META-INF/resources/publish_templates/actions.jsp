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
		<portlet:param name="mvcRenderCommandName" value="publishLayouts" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />
		<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="publish"
		url="<%= newPublishProcessURL %>"
	/>

	<portlet:renderURL var="deleteRedirectURL">
		<portlet:param name="mvcRenderCommandName" value="viewPublishConfigurations" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
		<portlet:param name="layoutSetBranchName" value="<%= layoutSetBranchName %>" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
	</portlet:renderURL>

	<portlet:actionURL name="editPublishConfiguration" var="deletePublishConfigurationURL">
		<portlet:param name="mvcRenderCommandName" value="editPublishConfiguration" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= trashHelper.isTrashEnabled(groupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= deleteRedirectURL %>" />
		<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		trash="<%= trashHelper.isTrashEnabled(groupId) %>"
		url="<%= deletePublishConfigurationURL %>"
	/>
</liferay-ui:icon-menu>
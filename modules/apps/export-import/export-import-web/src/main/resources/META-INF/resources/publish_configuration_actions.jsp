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
long groupId = ParamUtil.getLong(request, "groupId");
long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");
String layoutSetBranchName = ParamUtil.getString(request, "layoutSetBranchName");
boolean localPublishing = ParamUtil.getBoolean(request, "localPublishing");
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

ExportImportConfiguration exportImportConfiguration = (ExportImportConfiguration)row.getObject();
%>

<portlet:renderURL var="deleteRedirectURL">
	<portlet:param name="mvcRenderCommandName" value="publishLayouts" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />
	<portlet:param name="publishConfigurationButtons" value="saved" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
	<portlet:param name="layoutSetBranchName" value="<%= layoutSetBranchName %>" />
	<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
</portlet:renderURL>

<portlet:actionURL name="editPublishConfiguration" var="deletePublishConfigurationURL">
	<portlet:param name="mvcRenderCommandName" value="editPublishConfiguration" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(groupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
	<portlet:param name="redirect" value="<%= deleteRedirectURL %>" />
	<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
</portlet:actionURL>

<portlet:renderURL var="publishRedirectURL">
	<portlet:param name="mvcRenderCommandName" value="publishLayouts" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.PUBLISH_TO_LIVE %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
	<portlet:param name="layoutSetBranchName" value="<%= layoutSetBranchName %>" />
	<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
</portlet:renderURL>

<portlet:renderURL var="publishByExportImportConfigurationURL">
	<portlet:param name="mvcRenderCommandName" value="confirmation" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />
	<portlet:param name="redirect" value="<%= publishRedirectURL %>" />
	<portlet:param name="backURL" value="<%= deleteRedirectURL %>" />
	<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
</portlet:renderURL>

<liferay-ui:icon
	iconCssClass="icon-share-alt"
	message='<%= localPublishing ? "publish-to-live" : "publish-to-remote" %>'
	url="<%= publishByExportImportConfigurationURL %>"
/>

<liferay-ui:icon-delete
	trash="<%= TrashUtil.isTrashEnabled(groupId) %>"
	url="<%= deletePublishConfigurationURL %>"
/>
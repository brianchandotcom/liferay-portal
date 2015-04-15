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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");
long liveGroupId = ParamUtil.getLong(request, "liveGroupId");
String rootNodeName = ParamUtil.getString(request, "rootNodeName");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

ExportImportConfiguration exportImportConfiguration = (ExportImportConfiguration)row.getObject();
%>

<portlet:renderURL var="deleteRedirectURL">
	<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
	<portlet:param name="tabs2" value="new-export-process" />
	<portlet:param name="exportConfigurationButtons" value="saved" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
	<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
</portlet:renderURL>

<portlet:actionURL var="deleteExportConfigurationURL">
	<portlet:param name="struts_action" value="/layouts_admin/edit_export_configuration" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(liveGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
	<portlet:param name="redirect" value="<%= deleteRedirectURL %>" />
	<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
</portlet:actionURL>

<portlet:renderURL var="exportRedirectURL">
	<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
	<portlet:param name="tabs2" value="current-and-previous" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
	<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
</portlet:renderURL>

<portlet:renderURL var="exportByExportImportConfigurationURL">
	<portlet:param name="struts_action" value="/layouts_admin/confirmation" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
	<portlet:param name="redirect" value="<%= exportRedirectURL %>" />
	<portlet:param name="backURL" value="<%= deleteRedirectURL %>" />
	<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
</portlet:renderURL>

<liferay-ui:icon
	iconCssClass="icon-arrow-up"
	message="export"
	url="<%= exportByExportImportConfigurationURL %>"
/>

<liferay-ui:icon-delete
	trash="<%= TrashUtil.isTrashEnabled(liveGroupId) %>"
	url="<%= deleteExportConfigurationURL %>"
/>
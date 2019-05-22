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
	<c:if test="<%= !StagingUtil.isChangeTrackingEnabled(company.getCompanyId()) %>">
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
			<portlet:param name="mvcRenderCommandName" value="viewExportConfigurations" />
		</portlet:renderURL>

		<portlet:actionURL name="editExportConfiguration" var="deleteExportConfigurationURL">
			<portlet:param name="mvcRenderCommandName" value="editExportConfiguration" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= trashHelper.isTrashEnabled(liveGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= deleteRedirectURL %>" />
			<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			trash="<%= trashHelper.isTrashEnabled(liveGroupId) %>"
			url="<%= deleteExportConfigurationURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>
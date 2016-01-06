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
long liveGroupId = GetterUtil.getLong(request.getAttribute("view.jsp-liveGroupId"));

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

ExportImportConfiguration exportImportConfiguration = (ExportImportConfiguration)row.getObject();
%>

<portlet:renderURL var="deleteRedirectURL">
	<portlet:param name="mvcRenderCommandName" value="viewExportConfigurations" />
</portlet:renderURL>

<portlet:actionURL name="editExportConfiguration" var="deleteExportConfigurationURL">
	<portlet:param name="mvcRenderCommandName" value="editExportConfiguration" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(liveGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
	<portlet:param name="redirect" value="<%= deleteRedirectURL %>" />
	<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
</portlet:actionURL>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<liferay-ui:icon-delete
		trash="<%= TrashUtil.isTrashEnabled(liveGroupId) %>"
		url="<%= deleteExportConfigurationURL %>"
	/>
</liferay-ui:icon-menu>
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
long exportImportConfigurationId = (Long)request.getAttribute(ExportImportWebKeys.EXPORT_IMPORT_CONFIGURATION_ID);

ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);
%>

<liferay-ui:panel-container
	extended="<%= true %>"
	id="exportImportConfigurationPanelContainer"
	persistState="<%= false %>"
>
	<liferay-ui:panel
		collapsible="<%= false %>"
		extended="<%= true %>"
		title="template-type"
	>
		<liferay-ui:message key="<%= ExportImportConfigurationConstants.getTypeLabel(exportImportConfiguration.getType()) %>" />
	</liferay-ui:panel>

	<liferay-ui:panel
		collapsible="<%= false %>"
		extended="<%= true %>"
		title="created-by"
	>
		<liferay-ui:message key="<%= exportImportConfiguration.getUserName() %>" />
	</liferay-ui:panel>

	<liferay-ui:panel
		collapsible="<%= false %>"
		extended="<%= true %>"
		title="description"
	>
		<liferay-ui:message key="<%= HtmlUtil.escape(exportImportConfiguration.getDescription()) %>" />
	</liferay-ui:panel>
</liferay-ui:panel-container>
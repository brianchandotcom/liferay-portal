<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
String rootNodeName = ParamUtil.getString(request, "rootNodeName");
%>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
	<portlet:param name="tabs2" value="new-export-process" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
	<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
	<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
</liferay-portlet:renderURL>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-export-templates"
	iteratorURL="<%= portletURL %>"
	total="<%= ExportImportConfigurationLocalServiceUtil.getExportImportConfigurationsCount(groupId, ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT) %>"
>
	<liferay-ui:search-container-results
		results="<%= ExportImportConfigurationLocalServiceUtil.getExportImportConfigurations(groupId, ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.ExportImportConfiguration"
		keyProperty="exportImportConfigurationId"
		modelVar="exportImportConfiguration"
	>
		<liferay-ui:search-container-column-text
			name="user"
		>
			<liferay-ui:message key="<%= PortalUtil.getUserName(exportImportConfiguration) %>" />
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="name"
		>
			<liferay-ui:message key="<%= HtmlUtil.escape(exportImportConfiguration.getName()) %>" />
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="description"
		>
			<liferay-ui:message key="<%= HtmlUtil.escape(exportImportConfiguration.getDescription()) %>" />
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-date
			name="create-date"
			value="<%= exportImportConfiguration.getCreateDate() %>"
		/>

		<liferay-ui:search-container-column-jsp
			path="/html/portlet/layouts_admin/configuration_actions.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>
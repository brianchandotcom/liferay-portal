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
boolean localPublishing = ParamUtil.getBoolean(request, "localPublishing");
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
%>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="struts_action" value="/layouts_admin/publish_layouts" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.PUBLISH %>" />
	<portlet:param name="tabs2" value="new-publication-process" />
	<portlet:param name="publishConfigurationButtons" value="saved" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
</liferay-portlet:renderURL>

<%
int exportImportConfigurationType = localPublishing ? ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL : ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE;
%>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-saved-publish-templates"
	iteratorURL="<%= portletURL %>"
	total="<%= ExportImportConfigurationLocalServiceUtil.getExportImportConfigurationsCount(groupId, exportImportConfigurationType) %>"
>
	<liferay-ui:search-container-results
		results="<%= ExportImportConfigurationLocalServiceUtil.getExportImportConfigurations(groupId, exportImportConfigurationType, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.ExportImportConfiguration"
		keyProperty="exportImportConfigurationId"
		modelVar="exportImportConfiguration"
	>
		<liferay-ui:search-container-column-text
			cssClass="export-configuration-user-column"
			name="user"
		>
			<liferay-ui:user-display
				displayStyle="3"
				height="30"
				userId="<%= exportImportConfiguration.getUserId() %>"
				width="30"
			/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="name"
			value="<%= HtmlUtil.escape(exportImportConfiguration.getName()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="description"
			value="<%= HtmlUtil.escape(exportImportConfiguration.getDescription()) %>"
		/>

		<liferay-ui:search-container-column-date
			name="create-date"
			value="<%= exportImportConfiguration.getCreateDate() %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>
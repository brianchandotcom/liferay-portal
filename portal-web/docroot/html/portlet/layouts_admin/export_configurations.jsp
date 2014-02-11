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

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/layouts_admin/export_layouts");
portletURL.setParameter("tabs2", "new-export-process");
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("liveGroupId", String.valueOf(liveGroupId));
portletURL.setParameter("privateLayout", String.valueOf(privateLayout));
portletURL.setParameter("rootNodeName", rootNodeName);
%>

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

		<%
		User configurationsOwnerUser = UserLocalServiceUtil.getUser(exportImportConfiguration.getUserId());
		String portraitURL = HtmlUtil.escape(configurationsOwnerUser.getPortraitURL(themeDisplay));
		%>

		<liferay-ui:search-container-column-text
			name="user"
		>

			<c:choose>
				<c:when test="<%= BrowserSnifferUtil.isIe(request) && (BrowserSnifferUtil.getMajorVersion(request) < 9) %>">
					<img class="user-avatar-image" src="<%= portraitURL %>" />
					<liferay-ui:message key="<%= configurationsOwnerUser.getFullName() %>" />
				</c:when>
				<c:otherwise>
					<span class="user-avatar-image" style="display: inline-block; background-image: url('<%= portraitURL %>');"></span>
					<liferay-ui:message key="<%= configurationsOwnerUser.getFullName() %>" />
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="configuration"
		>

			<span class="configuration-name">
				<liferay-ui:message key="<%= HtmlUtil.escape(exportImportConfiguration.getName()) %>" />
			</span>

			<span class="configuration-description">
				<liferay-ui:message key="<%= HtmlUtil.escape(exportImportConfiguration.getDescription()) %>" />
			</span>

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
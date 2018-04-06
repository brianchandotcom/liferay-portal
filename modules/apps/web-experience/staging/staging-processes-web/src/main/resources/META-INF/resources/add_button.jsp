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
int configurationType = ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL;
boolean localPublishing = true;

if (stagingGroup.isStagedRemotely()) {
	configurationType = ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE;
	localPublishing = false;
}

List<ExportImportConfiguration> exportImportConfigurations = ExportImportConfigurationLocalServiceUtil.getExportImportConfigurations(stagingGroupId, configurationType);
%>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, stagingGroupId, ActionKeys.PUBLISH_STAGING) %>">
	<liferay-frontend:add-menu
		inline="<%= true %>"
	>

		<%
		for (ExportImportConfiguration exportImportConfiguration : exportImportConfigurations) {
			PortletURL addNewProcessURL = liferayPortletResponse.createRenderURL();

			addNewProcessURL.setParameter("mvcRenderCommandName", "publishLayouts");
			addNewProcessURL.setParameter(Constants.CMD, (localPublishing) ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE);
			addNewProcessURL.setParameter("exportImportConfigurationId", String.valueOf(exportImportConfiguration.getExportImportConfigurationId()));
			addNewProcessURL.setParameter("groupId", String.valueOf(stagingGroupId));
		%>

			<liferay-frontend:add-menu-item
				title="<%= exportImportConfiguration.getName() %>"
				url="<%= addNewProcessURL.toString() %>"
			/>

		<%
		}
		%>

		<%
		PortletURL addNewCustomProcessURL = liferayPortletResponse.createRenderURL();

		addNewCustomProcessURL.setParameter("mvcRenderCommandName", "publishLayouts");
		addNewCustomProcessURL.setParameter(Constants.CMD, (localPublishing) ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE);
		addNewCustomProcessURL.setParameter("groupId", String.valueOf(stagingGroupId));
		addNewCustomProcessURL.setParameter("privateLayout", Boolean.FALSE.toString());
		%>

		<liferay-frontend:add-menu-item
			title='<%= LanguageUtil.get(request, "custom-publication") %>'
			url="<%= addNewCustomProcessURL.toString() %>"
		/>
	</liferay-frontend:add-menu>
</c:if>
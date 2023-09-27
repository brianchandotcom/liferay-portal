<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/export_import_entity_management_bar_button/init.jsp" %>

<%
scopeGroup = themeDisplay.getScopeGroup();
%>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroup, ActionKeys.EXPORT_IMPORT_PORTLET_INFO) && (Objects.equals(cmd, Constants.EXPORT) || (Objects.equals(cmd, Constants.PUBLISH) && (scopeGroup.isStagingGroup() || scopeGroup.isStagedRemotely()) && scopeGroup.isStagedPortlet(portletDisplay.getId()))) %>">

	<%
	String taglibURL = "javascript:Liferay.fire('" + liferayPortletResponse.getNamespace() + cmd + "'); void(0);";
	%>

	<liferay-ui:icon
		icon="import-export"
		label="<%= false %>"
		linkCssClass="btn btn-monospaced btn-outline-secondary"
		markupView="lexicon"
		message="<%= cmd %>"
		url="<%= taglibURL %>"
	/>

	<%
	PortletURL portletURL = PortletURLBuilder.create(
		PortletURLFactoryUtil.create(request, ChangesetPortletKeys.CHANGESET, PortletRequest.ACTION_PHASE)
	).setActionName(
		"exportImportEntity"
	).setMVCRenderCommandName(
		"exportImportEntity"
	).setCMD(
		cmd
	).setBackURL(
		themeDisplay.getURLCurrent()
	).setParameter(
		"portletId", portletDisplay.getId()
	).buildPortletURL();
	%>

	<aui:script use="liferay-export-import-management-bar-button">
		var exportImportManagementBarButton = new Liferay.ExportImportManagementBarButton(
			{
				actionNamespace:
					'<%= PortalUtil.getPortletNamespace(ChangesetPortletKeys.CHANGESET) %>',
				cmd: '<%= cmd %>',
				exportImportEntityUrl: '<%= portletURL.toString() %>',
				namespace: '<portlet:namespace />',
				searchContainerId: '<%= searchContainerId %>',
				searchContainerMappingId: '<%= searchContainerMappingId %>',
			}
		);
	</aui:script>
</c:if>
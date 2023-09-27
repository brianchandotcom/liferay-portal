<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/designer/init.jsp" %>

<aui:script>
	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />deleteDefinition',
		() => {
			<portlet:namespace />confirmDeleteDefinition(
				'<%=
					PortletURLBuilder.create(
						PortalUtil.getControlPanelPortletURL(renderRequest, KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW, PortletRequest.ACTION_PHASE)
					).setActionName(
						"/portal_workflow/delete_workflow_definition"
					).setParameter(
						"name", renderRequest.getParameter("name")
					).setParameter(
						"version", renderRequest.getParameter("draftVersion")
					).buildString()
		%>'
			);
		}
	);
</aui:script>
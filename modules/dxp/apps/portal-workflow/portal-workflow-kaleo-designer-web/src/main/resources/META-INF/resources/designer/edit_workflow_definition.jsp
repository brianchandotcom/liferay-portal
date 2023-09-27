<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/designer/init.jsp" %>

<%
KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)request.getAttribute(KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(
	PortletURLBuilder.create(
		PortalUtil.getControlPanelPortletURL(renderRequest, KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW, PortletRequest.RENDER_PHASE)
	).setMVCPath(
		"/view.jsp"
	).buildString());

boolean view = Objects.equals(request.getParameter(WorkflowWebKeys.WORKFLOW_JSP_STATE), "view");

String titleKey = "new-workflow-definition";

if (kaleoDefinitionVersion != null) {
	titleKey = "edit-workflow-definition";

	if (view) {
		titleKey = "view-workflow-definition";
	}
}

renderResponse.setTitle(LanguageUtil.get(request, titleKey));
%>

<react:component
	module="designer/js/definition-builder/DefinitionBuilder"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"accountEntryId", ParamUtil.getLong(liferayPortletRequest, "accountEntryId")
		).put(
			"definitionName", (kaleoDefinitionVersion == null) ? null : kaleoDefinitionVersion.getName()
		).put(
			"displayNames", LocaleUtil.toDisplayNames(LanguageUtil.getAvailableLocales(), locale)
		).put(
			"functionActionExecutors", kaleoDesignerDisplayContext.getFunctionActionExecutorsJSONArray()
		).put(
			"isView", view || !kaleoDesignerDisplayContext.canPublishWorkflowDefinition()
		).put(
			"languageIds", LocaleUtil.toLanguageIds(LanguageUtil.getAvailableLocales())
		).put(
			"portletNamespace", PortalUtil.getPortletNamespace(KaleoDesignerPortletKeys.KALEO_DESIGNER)
		).put(
			"statuses", kaleoDesignerDisplayContext.getStatusesJSONArray()
		).put(
			"title", (kaleoDefinitionVersion == null) ? LanguageUtil.get(request, "new-workflow") : kaleoDefinitionVersion.getTitle(locale)
		).put(
			"translations", (kaleoDefinitionVersion == null) ? new HashMap<>() : kaleoDefinitionVersion.getTitleMap()
		).put(
			"version", (kaleoDefinitionVersion == null) ? "0" : kaleoDefinitionVersion.getVersion()
		).build()
	%>'
/>
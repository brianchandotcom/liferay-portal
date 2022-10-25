<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/designer/init.jsp" %>

<liferay-util:buffer
	var="onClickFn"
>
	<portlet:namespace />confirmDeleteDefinition('<%=
		PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(renderRequest, KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW, PortletRequest.ACTION_PHASE)
		).setActionName(
			"/portal_workflow/delete_workflow_definition"
		).setParameter(
			"name", renderRequest.getParameter("name")
		).setParameter(
			"version", renderRequest.getParameter("draftVersion")
		).buildString()
	%>');
</liferay-util:buffer>

<liferay-ui:icon
	message="delete"
	onClick="<%= onClickFn %>"
	url="javascript:void(0);"
/>
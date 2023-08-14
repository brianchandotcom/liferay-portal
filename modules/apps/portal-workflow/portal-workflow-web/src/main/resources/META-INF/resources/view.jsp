<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
WorkflowNavigationDisplayContext workflowNavigationDisplayContext = (WorkflowNavigationDisplayContext)request.getAttribute(WorkflowWebKeys.WORKFLOW_NAVIGATION_DISPLAY_CONTEXT);

String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}
%>

<liferay-ui:success key='<%= portletName + "requestProcessed" %>' message="your-request-completed-successfully" />

<c:if test="<%= workflowPortletTabs.size() != 1 %>">
	<clay:navigation-bar
		inverted="<%= false %>"
		navigationItems="<%= workflowNavigationDisplayContext.getNavigationItems(selectedWorkflowPortletTab, workflowPortletTabs) %>"
	/>
</c:if>

<%
selectedWorkflowPortletTab.include(request, PipingServletResponseFactory.createPipingServletResponse(pageContext), null);
%>
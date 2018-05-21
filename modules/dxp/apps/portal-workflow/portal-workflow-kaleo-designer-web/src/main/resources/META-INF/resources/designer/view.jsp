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

<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" varImpl="portletURL">
	<portlet:param name="mvcPath" value="/designer/view.jsp" />
</liferay-portlet:renderURL>

<%
String definitionsNavigation = ParamUtil.getString(request, "definitionsNavigation");

int displayedStatus = KaleoDefinitionVersionConstants.STATUS_ALL;

if (StringUtil.equals(definitionsNavigation, "published")) {
	displayedStatus = KaleoDefinitionVersionConstants.STATUS_PUBLISHED;
}
else if (StringUtil.equals(definitionsNavigation, "not-published")) {
	displayedStatus = KaleoDefinitionVersionConstants.STATUS_NOT_PUBLISHED;
}

String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL navigationPortletURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);

if (delta > 0) {
	navigationPortletURL.setParameter("delta", String.valueOf(delta));
}

navigationPortletURL.setParameter("orderByCol", orderByCol);
navigationPortletURL.setParameter("orderByType", orderByType);

PortletURL displayStyleURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

if (cur > 0) {
	displayStyleURL.setParameter("cur", String.valueOf(cur));
}
%>

<liferay-ui:error exception="<%= RequiredWorkflowDefinitionException.class %>">

	<%
	RequiredWorkflowDefinitionException rwde = (RequiredWorkflowDefinitionException)errorException;
	%>

	<liferay-ui:message arguments="<%= kaleoDesignerDisplayContext.getMessageArguments(rwde.getWorkflowDefinitionLinks(), request) %>" key="<%= kaleoDesignerDisplayContext.getMessageKey(rwde.getWorkflowDefinitionLinks()) %>" translateArguments="<%= false %>" />
</liferay-ui:error>

<c:choose>
	<c:when test="<%= WorkflowEngineManagerUtil.isDeployed() %>">
		<liferay-util:include page="/designer/navigation_bar.jsp" servletContext="<%= application %>" />

		<liferay-util:include page="/designer/view_workflow_definitions.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="no-workflow-engine-is-deployed" />
		</div>
	</c:otherwise>
</c:choose>
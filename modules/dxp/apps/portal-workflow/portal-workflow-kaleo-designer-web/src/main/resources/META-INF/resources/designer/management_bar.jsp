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

<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" portletName="<%= KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW %>" varImpl="portletURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</liferay-portlet:renderURL>

<%
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);

String definitionsNavigation = ParamUtil.getString(request, "definitionsNavigation");

portletURL.setParameter("definitionsNavigation", definitionsNavigation);

String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL navigationPortletURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

if (delta > 0) {
	navigationPortletURL.setParameter("delta", String.valueOf(delta));
}

navigationPortletURL.setParameter("orderByCol", orderByCol);
navigationPortletURL.setParameter("orderByType", orderByType);

PortletURL displayStyleURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

if (cur > 0) {
	displayStyleURL.setParameter("cur", String.valueOf(cur));
}
%>

<liferay-frontend:management-bar
	searchContainerId="kaleoDefinitionVersions"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= displayStyleURL %>"
			selectedDisplayStyle="list"
		/>

		<liferay-util:include page="/designer/add_button.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "published", "not-published"} %>'
			navigationParam="definitionsNavigation"
			portletURL="<%= navigationPortletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"title", "last-modified"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>
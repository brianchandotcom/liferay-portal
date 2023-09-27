<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CalendarResourceDisplayTerms displayTerms = new CalendarResourceDisplayTerms(renderRequest);
%>

<clay:management-toolbar
	clearResultsURL="<%= calendarDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= calendarDisplayContext.getCreationMenu() %>"
	disabled="<%= calendarDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= calendarDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= calendarDisplayContext.getTotalItems() %>"
	searchActionURL="<%= calendarDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= calendarDisplayContext.getSearchContainerId() %>"
	searchFormName="fm"
	selectable="<%= false %>"
	sortingOrder="<%= calendarDisplayContext.getOrderByType() %>"
	sortingURL="<%= calendarDisplayContext.getSortingURL() %>"
/>

<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
	<portlet:param name="tabs1" value="resources" />
</liferay-portlet:renderURL>

<clay:container-fluid>
	<c:choose>
		<c:when test="<%= displayTerms.getScope() == themeDisplay.getCompanyGroupId() %>">
			<h3><liferay-ui:message key="users" /></h3>

			<%@ include file="/calendar_resource_user_search_container.jspf" %>

			<h3><liferay-ui:message key="sites" /></h3>

			<%@ include file="/calendar_resource_group_search_container.jspf" %>
		</c:when>
		<c:otherwise>
			<%@ include file="/calendar_resource_search_container.jspf" %>
		</c:otherwise>
	</c:choose>
</clay:container-fluid>
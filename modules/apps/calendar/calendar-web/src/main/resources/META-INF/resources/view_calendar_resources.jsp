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
CalendarResourceDisplayTerms displayTerms = new CalendarResourceDisplayTerms(renderRequest);
%>

<clay:management-toolbar
	clearResultsURL="<%= calendarDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= calendarDisplayContext.getCreationMenu() %>"
	disabled="<%= calendarDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= calendarDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= calendarDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= calendarDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= calendarDisplayContext.getSearchContainerId() %>"
	searchFormName="fm"
	selectable="false"
/>

<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
	<portlet:param name="tabs1" value="resources" />
</liferay-portlet:renderURL>

<div class="container-fluid-1280">
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
</div>
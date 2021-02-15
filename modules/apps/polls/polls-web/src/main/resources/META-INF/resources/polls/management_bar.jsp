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

<%@ include file="/polls/init.jsp" %>

<portlet:actionURL name="/polls/delete_question" var="deleteQuestionURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<clay:management-toolbar
	actionDropdownItems="<%= pollsDisplayContext.getActionItemsDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteQuestionURL", deleteQuestionURL.toString()
		).build()
	%>'
	clearResultsURL="<%= pollsDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= pollsDisplayContext.getCreationMenu() %>"
	disabled="<%= pollsDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= pollsDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= pollsDisplayContext.getTotalItems() %>"
	propsTransformer="polls/js/PollsManagementToolbarPropsTransformer"
	searchActionURL="<%= pollsDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= pollsDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	sortingOrder="<%= pollsDisplayContext.getOrderByType() %>"
	sortingURL="<%= pollsDisplayContext.getSortingURL() %>"
/>
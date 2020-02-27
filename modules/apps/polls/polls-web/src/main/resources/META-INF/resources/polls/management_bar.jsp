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

<clay:management-toolbar
	actionDropdownItems="<%= pollsDisplayContext.getActionItemsDropdownItems() %>"
	clearResultsURL="<%= pollsDisplayContext.getClearResultsURL() %>"
	componentId="pollsManagementToolbar"
	creationMenu="<%= pollsDisplayContext.getCreationMenu() %>"
	disabled="<%= pollsDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= pollsDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= pollsDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= pollsDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= pollsDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	sortingOrder="<%= pollsDisplayContext.getOrderByType() %>"
	sortingURL="<%= pollsDisplayContext.getSortingURL() %>"
/>

<aui:script>
	var deleteQuestions = function() {
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
			)
		) {
			var searchContainer = document.getElementById(
				'<portlet:namespace />poll'
			);

			if (searchContainer) {
				Liferay.Util.postForm(document.<portlet:namespace />fm, {
					data: {
						deleteQuestionIds: Liferay.Util.listCheckedExcept(
							searchContainer,
							'<portlet:namespace />allRowIds'
						),
					},

					<portlet:actionURL name="/polls/deleteQuestion" var="deleteQuestionURL">
						<portlet:param name="mvcPath" value="/view.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
					</portlet:actionURL>

					url: '<%= deleteQuestionURL %>',
				});
			}
		}
	};

	var ACTIONS = {
		deleteQuestions: deleteQuestions,
	};

	Liferay.componentReady('pollsManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on(['actionItemClicked'], function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>
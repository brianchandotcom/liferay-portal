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

<%@ include file="/admin/init.jsp" %>

<clay:management-toolbar
	actionDropdownItems="<%= kaleoFormsAdminDisplayContext.getActionItemsDropdownItems() %>"
	clearResultsURL="<%= kaleoFormsAdminDisplayContext.getClearResultsURL() %>"
	componentId="kaleoFormsManagementToolbar"
	creationMenu="<%= kaleoFormsAdminDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= kaleoFormsAdminDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= kaleoFormsAdminDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= kaleoFormsAdminDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= kaleoFormsAdminDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	sortingOrder="<%= kaleoFormsAdminDisplayContext.getOrderByType() %>"
	sortingURL="<%= kaleoFormsAdminDisplayContext.getSortingURL() %>"
/>

<aui:script sandbox="<%= true %>">
	var deleteKaleoProcess = function() {
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
			)
		) {
			var form = document.<portlet:namespace />fm;

			var searchContainer = form.querySelector(
				'#<portlet:namespace />kaleoProcess'
			);

			form.setAttribute('method', 'post');
			form.querySelector(
				'#<portlet:namespace />kaleoProcessIds'
			).value = Liferay.Util.listCheckedExcept(
				searchContainer,
				'<portlet:namespace />allRowIds'
			);

			submitForm(
				form,
				'<portlet:actionURL name="deleteKaleoProcess"><portlet:param name="mvcPath" value="/admin/view.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>'
			);
		}
	};

	var ACTIONS = {
		deleteKaleoProcess: deleteKaleoProcess
	};

	Liferay.componentReady('kaleoFormsManagementToolbar').then(function(
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
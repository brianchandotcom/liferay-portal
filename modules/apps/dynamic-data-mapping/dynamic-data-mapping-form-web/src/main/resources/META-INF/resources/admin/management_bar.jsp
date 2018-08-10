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

<%@ include file="/admin/init.jsp" %>

<%
String currentTab = ParamUtil.getString(request, "currentTab", "forms");
%>

<clay:management-toolbar
	actionDropdownItems="<%= ddmFormAdminDisplayContext.getActionItemsDropdownItems() %>"
	clearResultsURL="<%= ddmFormAdminDisplayContext.getClearResultsURL() %>"
	componentId="ddmFormManagementToolbar"
	creationMenu="<%= ddmFormAdminDisplayContext.getCreationMenu() %>"
	disabled="<%= ddmFormAdminDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= ddmFormAdminDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddmFormAdminDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= ddmFormAdminDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= ddmFormAdminDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	sortingOrder="<%= ddmFormAdminDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddmFormAdminDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= ddmFormAdminDisplayContext.getViewTypesItems() %>"
/>

<aui:script sandbox="<%= true %>">
	var deleteFormInstances = function() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />searchContainerForm);

			var searchContainer = AUI.$('#<portlet:namespace /><%= ddmFormAdminDisplayContext.getSearchContainerId() %>', form);

			form.attr('method', 'post');
			form.fm('deleteFormInstanceIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteFormInstance"><portlet:param name="mvcPath" value="/admin/view.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	};

	var deleteStructures = function() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />searchContainerForm);

			var searchContainer = AUI.$('#<portlet:namespace /><%= ddmFormAdminDisplayContext.getSearchContainerId() %>', form);

			form.attr('method', 'post');
			form.fm('deleteStructureIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteStructure"><portlet:param name="mvcPath" value="/admin/view.jsp" /><portlet:param name="currentTab" value="element-set" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	};

	var ACTIONS = {
		'deleteFormInstances': deleteFormInstances,
		'deleteStructures': deleteStructures
	};

	Liferay.componentReady('ddmFormManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on(
				['actionItemClicked'],
				function(event) {
					var itemData = event.data.item.data;

					if (itemData && itemData.action && ACTIONS[itemData.action]) {
						ACTIONS[itemData.action]();
					}
				}
			);
		}
	);
</aui:script>
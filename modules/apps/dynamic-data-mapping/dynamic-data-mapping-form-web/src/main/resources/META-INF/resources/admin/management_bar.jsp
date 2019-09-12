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
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
			)
		) {
			var searchContainer = document.getElementById(
				'<portlet:namespace /><%= ddmFormAdminDisplayContext.getSearchContainerId() %>'
			);

			if (searchContainer) {
				Liferay.Util.postForm(
					document.<portlet:namespace />searchContainerForm,
					{
						data: {
							deleteFormInstanceIds: Liferay.Util.listCheckedExcept(
								searchContainer,
								'<portlet:namespace />allRowIds'
							)
						},

						<portlet:actionURL name="deleteFormInstance" var="deleteFormInstanceURL">
							<portlet:param name="mvcPath" value="/admin/view.jsp" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:actionURL>

						url: '<%= deleteFormInstanceURL %>'
					}
				);
			}
		}
	};

	var deleteStructures = function() {
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
			)
		) {
			var searchContainer = document.getElementById(
				'<portlet:namespace /><%= ddmFormAdminDisplayContext.getSearchContainerId() %>'
			);

			if (searchContainer) {
				Liferay.Util.postForm(
					document.<portlet:namespace />searchContainerForm,
					{
						data: {
							deleteStructureIds: Liferay.Util.listCheckedExcept(
								searchContainer,
								'<portlet:namespace />allRowIds'
							)
						},

						<portlet:actionURL name="deleteStructure" var="deleteStructureURL">
							<portlet:param name="mvcPath" value="/admin/view.jsp" />
							<portlet:param name="currentTab" value="element-set" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:actionURL>

						url: '<%= deleteStructureURL %>'
					}
				);
			}
		}
	};

	var ACTIONS = {
		deleteFormInstances: deleteFormInstances,
		deleteStructures: deleteStructures
	};

	Liferay.componentReady('ddmFormManagementToolbar').then(function(
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
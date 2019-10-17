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
PortletURL portletURL = renderResponse.createRenderURL();
%>

<clay:management-toolbar
	actionDropdownItems="<%= ddlDisplayContext.getActionItemsDropdownItems() %>"
	clearResultsURL="<%= ddlDisplayContext.getClearResultsURL() %>"
	componentId="ddlManagementToolbar"
	creationMenu="<%= ddlDisplayContext.getCreationMenu() %>"
	disabled="<%= ddlDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= ddlDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddlDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= portletURL.toString() %>"
	searchContainerId="<%= ddlDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	sortingOrder="<%= ddlDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddlDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= ddlDisplayContext.getViewTypesItems() %>"
/>

<aui:script sandbox="<%= true %>">
	var deleteRecordSets = function() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />'
			)
		) {
			var form = document.getElementById('<portlet:namespace />fm');

			if (form) {
				var searchContainer = form.querySelector(
					'#<portlet:namespace /><%= ddlDisplayContext.getSearchContainerId() %>'
				);

				if (searchContainer) {
					form.setAttribute('method', 'post');

					var recordSetIds = form.querySelector(
						'#<portlet:namespace />recordSetIds'
					);

					if (recordSetIds) {
						recordSetIds.setAttribute(
							'value',
							Liferay.Util.listCheckedExcept(
								searchContainer,
								'<portlet:namespace />allRowIds'
							)
						);

						submitForm(
							form,
							'<portlet:actionURL name="deleteRecordSet"><portlet:param name="mvcPath" value="/view.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>'
						);
					}
				}
			}
		}
	};

	var ACTIONS = {
		deleteRecordSets: deleteRecordSets
	};

	Liferay.componentReady('ddlManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on('actionItemClicked', function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>
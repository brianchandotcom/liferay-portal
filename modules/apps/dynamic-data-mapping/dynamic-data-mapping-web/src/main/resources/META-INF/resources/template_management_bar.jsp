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
boolean includeCheckBox = ParamUtil.getBoolean(request, "includeCheckBox", true);
%>

<clay:management-toolbar
	actionDropdownItems='<%= ddmDisplayContext.getActionItemsDropdownItems("deleteTemplates") %>'
	clearResultsURL="<%= ddmDisplayContext.getClearResultsURL() %>"
	componentId="ddmTemplateManagementToolbar"
	creationMenu="<%= ddmDisplayContext.getTemplateCreationMenu() %>"
	disabled="<%= ddmDisplayContext.isDisabledManagementBar(DDMWebKeys.DYNAMIC_DATA_MAPPING_TEMPLATE) %>"
	filterDropdownItems="<%= ddmDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddmDisplayContext.getTotalItems(DDMWebKeys.DYNAMIC_DATA_MAPPING_TEMPLATE) %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= ddmDisplayContext.getTemplateSearchActionURL() %>"
	searchContainerId="<%= ddmDisplayContext.getTemplateSearchContainerId() %>"
	searchFormName="fm1"
	selectable="<%= includeCheckBox && !user.isDefaultUser() %>"
	sortingOrder="<%= ddmDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddmDisplayContext.getSortingURL() %>"
/>

<aui:script sandbox="<%= true %>">
	var deleteTemplates = function() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			var searchContainer = AUI.$('#<portlet:namespace />entriesContainer', form);

			form.attr('method', 'post');
			form.fm('deleteTemplateIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteTemplate"><portlet:param name="mvcPath" value="/view_template.jsp" /></portlet:actionURL>');
		}
	};

	var ACTIONS = {
		'deleteTemplates': deleteTemplates
	};

	Liferay.componentReady('ddmTemplateManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on(
				'actionItemClicked',
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
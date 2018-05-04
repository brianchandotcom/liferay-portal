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
PortletURL portletURL = ddmDataProviderDisplayContext.getPortletURL();
%>

<clay:management-toolbar
	actionDropdownItems="<%= ddmDataProviderDisplayContext.getActionItemsDropdownItems() %>"
	clearResultsURL="<%= ddmDataProviderDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= ddmDataProviderDisplayContext.getCreationMenu() %>"
	disabled="<%= ddmDataProviderDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= ddmDataProviderDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddmDataProviderDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= portletURL.toString() %>"
	searchContainerId="<%= ddmDataProviderDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	sortingOrder="<%= ddmDataProviderDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddmDataProviderDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= ddmDataProviderDisplayContext.getViewTypesItems() %>"
/>

<aui:script>
	function <portlet:namespace />deleteDataProviderInstances() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />searchContainerForm);

			var searchContainer = AUI.$('#<portlet:namespace />dataProviderInstance', form);

			form.attr('method', 'post');
			form.fm('deleteDataProviderInstanceIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteDataProvider"><portlet:param name="mvcPath" value="/view.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	}
</aui:script>
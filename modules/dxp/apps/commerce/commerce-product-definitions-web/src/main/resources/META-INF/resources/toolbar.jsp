<%@ taglib prefix="commerce-product" uri="http://liferay.com/tld/commerce-product" %>
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
String searchContainerId = ParamUtil.getString(request, "searchContainerId", "cpDefinitions");

CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-portlet:resourceURL id="cpDefinitionsFacets" var="cpDefinitionsFacetsURL" />

<commerce-product:management-toolbar-with-extra-filters
    actionItems="<%= cpDefinitionsDisplayContext.getActionDropdownItems() %>"
    categorySelectorURL='<%= cpDefinitionsDisplayContext.getCategorySelectorURL(renderResponse.getNamespace() + "selectCategory") %>'
    clearResultsURL="<%= cpDefinitionsDisplayContext.getClearResultsURL() %>"
    cpDefinitionsFacetsURL="<%= cpDefinitionsFacetsURL.toString() %>"
    creationMenu="<%= cpDefinitionsDisplayContext.getCreationMenu() %>"
    filterItems="<%= cpDefinitionsDisplayContext.getOrderByDropdownItems() %>"
    groupIds="<%= String.valueOf(themeDisplay.getScopeGroupId()) %>"
	infoPanelId="infoPanelId"
    namespace="<%= renderResponse.getNamespace() %>"
    searchContainerId="<%= searchContainerId %>"
    searchActionURL="<%= String.valueOf(cpDefinitionsDisplayContext.getPortletURL()) %>"
    searchFormName="searchFm"
    sortingOrder="<%= cpDefinitionsDisplayContext.getOrderByType() %>"
    sortingURL="<%= cpDefinitionsDisplayContext.getSortingURL() %>"
    portletURL="<%= cpDefinitionsDisplayContext.getPortletURL().toString() %>"
    totalItems="<%= cpDefinitionsDisplayContext.getTotalItems() %>"
    viewTypes="<%= cpDefinitionsDisplayContext.getViewTypeItems() %>"
    vocabularyIds="<%= cpDefinitionsDisplayContext.getVocabularyIds() %>"
/>

<aui:script>
	function <portlet:namespace />deleteCPDefinitions() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-products" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');
			form.fm('deleteCPDefinitionIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editProductDefinition" />');
		}
	}
</aui:script>
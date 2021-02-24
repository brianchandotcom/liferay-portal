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

<portlet:actionURL name="/dynamic_data_mapping/delete_template" var="deleteTemplatesURL">
	<portlet:param name="mvcPath" value="/view_template.jsp" />
</portlet:actionURL>

<clay:management-toolbar
	actionDropdownItems='<%= ddmDisplayContext.getActionItemsDropdownItems("deleteTemplates") %>'
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteTemplatesURL", deleteTemplatesURL.toString()
		).build()
	%>'
	clearResultsURL="<%= ddmDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= ddmDisplayContext.getTemplateCreationMenu() %>"
	disabled="<%= ddmDisplayContext.isDisabledManagementBar(DDMWebKeys.DYNAMIC_DATA_MAPPING_TEMPLATE) %>"
	filterDropdownItems="<%= ddmDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddmDisplayContext.getTotalItems(DDMWebKeys.DYNAMIC_DATA_MAPPING_TEMPLATE) %>"
	propsTransformer="js/DDMTemplateManagementToolbarPropsTransformer"
	searchActionURL="<%= ddmDisplayContext.getTemplateSearchActionURL() %>"
	searchContainerId="<%= ddmDisplayContext.getTemplateSearchContainerId() %>"
	searchFormName="fm1"
	selectable="<%= includeCheckBox && !user.isDefaultUser() %>"
	sortingOrder="<%= ddmDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddmDisplayContext.getSortingURL() %>"
/>
<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	itemsTotal="<%= ddmDisplayContext.getTotalItems(DDMWebKeys.DYNAMIC_DATA_MAPPING_TEMPLATE) %>"
	orderDropdownItems="<%= ddmDisplayContext.getOrderItemsDropdownItems() %>"
	propsTransformer="{DDMTemplateManagementToolbarPropsTransformer} from dynamic-data-mapping-web"
	searchActionURL="<%= ddmDisplayContext.getTemplateSearchActionURL() %>"
	searchContainerId="<%= ddmDisplayContext.getTemplateSearchContainerId() %>"
	searchFormName="fm1"
	selectable="<%= includeCheckBox && !user.isGuestUser() %>"
	sortingOrder="<%= ddmDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddmDisplayContext.getSortingURL() %>"
/>
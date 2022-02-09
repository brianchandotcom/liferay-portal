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
String catalogNavigationItem = ParamUtil.getString(request, "catalogNavigationItem", "view-all-product-definitions");

CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

request.setAttribute("view.jsp-portletURL", cpDefinitionsDisplayContext.getPortletURL());
%>

<%@ include file="/navbar_definitions.jspf" %>

<div id="<portlet:namespace />productDefinitionsContainer">
	<aui:form action="<%= cpDefinitionsDisplayContext.getPortletURL() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= String.valueOf(cpDefinitionsDisplayContext.getPortletURL()) %>" />
		<aui:input name="deleteCPDefinitionIds" type="hidden" />

		<clay:headless-data-set-display
			apiURL="/o/headless-commerce-admin-catalog/v1.0/products?nestedFields=skus,catalog"
			bulkActionDropdownItems="<%= cpDefinitionsDisplayContext.getBulkActionDropdownItems() %>"
			clayDataSetActionDropdownItems="<%= cpDefinitionsDisplayContext.getClayDataSetActionDropdownItems() %>"
			creationMenu="<%= cpDefinitionsDisplayContext.getCreationMenu() %>"
			formName="fm"
			id="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITIONS %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= cpDefinitionsDisplayContext.getPortletURL() %>"
			selectedItemsKey="id"
			selectionType="multiple"
			style="fluid"
		/>
	</aui:form>
</div>
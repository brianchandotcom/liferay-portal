<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceInventoryWarehousesDisplayContext commerceInventoryWarehousesDisplayContext = (CommerceInventoryWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse" var="editCommerceInventoryWarehouseActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-warehouse") %>'
>
	<aui:form method="post" name="fm">
		<aui:input bean="<%= commerceInventoryWarehousesDisplayContext.getCommerceInventoryWarehouse() %>" label="name" model="<%= CommerceInventoryWarehouse.class %>" name="name" required="<%= true %>" />
	</aui:form>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"defaultLanguageId", themeDisplay.getLanguageId()
			).put(
				"editCommerceInventoryWarehousePortletURL", String.valueOf(commerceInventoryWarehousesDisplayContext.getEditCommerceWarehouseRenderURL())
			).build()
		%>'
		module="js/addCommerceInventoryWarehouse"
	/>
</commerce-ui:modal-content>
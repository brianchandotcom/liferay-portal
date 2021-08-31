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
CommerceShipmentDisplayContext commerceShipmentDisplayContext = (CommerceShipmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShipment commerceShipment = commerceShipmentDisplayContext.getCommerceShipment();
%>

<portlet:actionURL name="/commerce_shipment/edit_commerce_shipment" var="editCommerceShipmentURL" />

<commerce-ui:modal-content
	contentCssClasses="p-0"
	showSubmitButton="<%= true %>"
	title='<%= LanguageUtil.get(request, "add-shipment-items") %>'
>
	<aui:form action="<%= editCommerceShipmentURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="addShipmentItems" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipment.getCommerceShipmentId() %>" />

		<clay:data-set-display
			bulkActionDropdownItems="<%= commerceShipmentDisplayContext.getShipmentItemBulkActions() %>"
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commerceShipmentId", String.valueOf(commerceShipment.getCommerceShipmentId())
				).build()
			%>'
			dataProviderKey="<%= CommerceShipmentDataSetConstants.COMMERCE_DATA_SET_KEY_SHIPPABLE_ORDER_ITEMS %>"
			formId='<%= liferayPortletResponse.getNamespace() + "fm" %>'
			id="<%= CommerceShipmentDataSetConstants.COMMERCE_DATA_SET_KEY_SHIPPABLE_ORDER_ITEMS %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= currentURLObj %>"
			selectedItemsKey="orderItemId"
			selectionType="multiple"
			showManagementBar="<%= false %>"
		/>
	</aui:form>
</commerce-ui:modal-content>
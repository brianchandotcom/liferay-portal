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
CommerceShipmentItemDisplayContext commerceShipmentItemDisplayContext = (CommerceShipmentItemDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShipmentItem commerceShipmentItem = commerceShipmentItemDisplayContext.getCommerceShipmentItem();

CommerceOrderItem commerceOrderItem = commerceShipmentItemDisplayContext.getCommerceOrderItem();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	redirect = currentURL;
}

portletDisplay.setURLBack(redirect);
%>

<portlet:actionURL name="/commerce_shipment/edit_commerce_shipment_item" var="editCommerceShipmentItemActionURL" />

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.format(request, "edit-x", commerceOrderItem.getSku()) %>'
>
	<liferay-ui:error embed="<%= false %>" exception="<%= DuplicateCommerceShipmentItemException.class %>" message="please-enter-a-unique-external-reference-code" />

	<aui:form action="<%= editCommerceShipmentItemActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipmentItem.getCommerceShipmentId() %>" />
		<aui:input name="commerceShipmentItemId" type="hidden" value="<%= commerceShipmentItem.getCommerceShipmentItemId() %>" />
		<aui:input name="commerceOrderItemId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderItemId() %>" />

		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "external-reference-code") %>'
		>
			<aui:input label='<%= LanguageUtil.get(request, "erc") %>' name="externalReferenceCode" value="<%= commerceShipmentItem.getExternalReferenceCode() %>" />
		</commerce-ui:panel>

		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "warehouse-availability") %>'
		>
			<div class="row text-center">
				<div class="col-sm-6">
					<liferay-ui:message key="outstanding-quantity" />: <%= commerceOrderItem.getQuantity() - commerceOrderItem.getShippedQuantity() %>
				</div>

				<div class="col-sm-6">
					<liferay-ui:message key="quantity-in-shipment" />: <%= commerceShipmentItemDisplayContext.getToSendQuantity() %>
				</div>
			</div>

			<hr class="mt-0" />

			<frontend-data-set:classic-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"commerceOrderItemId", String.valueOf(commerceOrderItem.getCommerceOrderItemId())
					).put(
						"commerceShipmentId", String.valueOf(commerceShipmentItem.getCommerceShipmentId())
					).put(
						"commerceShipmentItemId", String.valueOf(commerceShipmentItem.getCommerceShipmentItemId())
					).build()
				%>'
				dataProviderKey="<%= CommerceShipmentFDSNames.INVENTORY_WAREHOUSE_ITEM %>"
				formId="fm"
				id="<%= CommerceShipmentFDSNames.INVENTORY_WAREHOUSE_ITEM %>"
				itemsPerPage="<%= 10 %>"
				showManagementBar="<%= false %>"
			/>
		</commerce-ui:panel>

		<aui:button-row>
			<aui:button type="submit" value="save" />
			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</liferay-frontend:side-panel-content>
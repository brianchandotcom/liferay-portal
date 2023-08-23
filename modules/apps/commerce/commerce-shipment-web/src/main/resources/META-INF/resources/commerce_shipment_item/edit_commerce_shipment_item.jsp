<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

					<%
					BigDecimal quantity = commerceOrderItem.getQuantity();
					BigDecimal shippedQuantity = commerceOrderItem.getShippedQuantity();
					%>

					<liferay-ui:message key="outstanding-quantity" />: <%= quantity.intValue() - shippedQuantity.intValue() %>
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
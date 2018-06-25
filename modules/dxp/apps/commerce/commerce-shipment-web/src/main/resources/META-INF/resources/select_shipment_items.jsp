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
long commerceOrderId = ParamUtil.getLong(request, "commerceOrderId");

CommerceShipmentDisplayContext commerceShipmentDisplayContext = (CommerceShipmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShipment commerceShipment = commerceShipmentDisplayContext.getCommerceShipment();
long commerceShipmentId = commerceShipmentDisplayContext.getCommerceShipmentId();
List<CommerceOrderItem> commerceOrderItems = commerceShipmentDisplayContext.getCommerceOrderItems(commerceOrderId);
List<CommerceWarehouse> commerceWarehouses = commerceShipmentDisplayContext.getCommerceWarehouses();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(shipmentsURL);
%>

<portlet:actionURL name="editCommerceShipment" var="editCommerceShipmentActionURL" />

<div class="container-fluid-1280 sheet">
	<h1 class="p-3"><liferay-ui:message key="select-shipment-items" /></h1>

	<aui:form action="<%= editCommerceShipmentActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="selectCommerceShipmentItems" />
		<aui:input name="redirect" type="hidden" value="<%= shipmentsURL %>" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= String.valueOf(commerceOrderId) %>" />
		<aui:input name="commerceShipmentId" type="hidden" value="<%= String.valueOf(commerceShipmentId) %>" />

		<liferay-ui:error exception="<%= CommerceShipmentItemQuantityException.class %>" message="please-enter-a-valid-quantity" />

		<aui:model-context bean="<%= commerceShipment %>" model="<%= CommerceShipment.class %>" />

		<c:choose>
			<c:when test="<%= commerceOrderItems.isEmpty() %>">
				<div class="alert alert-info">
					<liferay-ui:message key="there-are-no-available-items-to-ship-in-the-selected-order" />
				</div>
			</c:when>
			<c:otherwise>
				<table class="table table-autofit table-sm">
					<thead>
						<tr>
							<th class="table-cell-content"><liferay-ui:message key="product" /></th>
							<th class="text-center"><liferay-ui:message key="ordered-quantity" /></th>

							<%
							for (CommerceWarehouse commerceWarehouse : commerceWarehouses) {
							%>

								<th><%= HtmlUtil.escape(commerceWarehouse.getName()) %></th>

							<%
							}
							%>

						</tr>
					</thead>

					<tbody>

						<%
						for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
							int remainedQuantity = commerceOrderItem.getQuantity() - commerceOrderItem.getShippedQuantity();
						%>

							<tr>
								<aui:input name="commerceOrderItemId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderItemId() %>" />

								<td>
									<%= HtmlUtil.escape(commerceOrderItem.getName(languageId)) %>
								</td>
								<td class="text-center">
									<%= remainedQuantity %>
								</td>

								<%
								for (CommerceWarehouse commerceWarehouse : commerceWarehouses) {
									int maxQuantityAllowed;

									int commerceWarehouseItemQuantity = commerceShipmentDisplayContext.getCommerceWarehouseItemQuantity(commerceOrderItem.getCommerceOrderItemId(), commerceWarehouse.getCommerceWarehouseId());

									if (remainedQuantity > commerceWarehouseItemQuantity) {
										maxQuantityAllowed = commerceWarehouseItemQuantity;
									}
									else {
										maxQuantityAllowed = remainedQuantity;
									}
								%>

									<td>
										<aui:input name='<%= commerceOrderItem.getCommerceOrderItemId() + "_warehouse" %>' type="hidden" value="<%= commerceWarehouse.getCommerceWarehouseId() %>" />

										<aui:input label="" name='<%= commerceOrderItem.getCommerceOrderItemId() + "_" + commerceWarehouse.getCommerceWarehouseId() + "_quantity" %>' placeholder='<%= LanguageUtil.format(request, "x-available", commerceWarehouseItemQuantity, false) %>' title="" type="number" wrapperCssClass="m-0">
											<aui:validator name="max"><%= maxQuantityAllowed %></aui:validator>
											<aui:validator name="min">0</aui:validator>
											<aui:validator name="number" />
										</aui:input>
									</td>

								<%
								}
								%>

							</tr>

						<%
						}
						%>

					</tbody>
				</table>
			</c:otherwise>
		</c:choose>

		<aui:button-row>
			<aui:button cssClass="btn-lg" disabled="<%= commerceOrderItems.isEmpty() %>" name="saveButton" type="submit" value="save" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>
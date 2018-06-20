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

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(shipmentsURL);
%>

<portlet:actionURL name="editCommerceShipment" var="editCommerceShipmentActionURL" />

<aui:form action="<%= editCommerceShipmentActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="selectCommerceShipmentItems" />
	<aui:input name="redirect" type="hidden" value="<%= shipmentsURL %>" />
	<aui:input name="commerceOrderId" type="hidden" value="<%= String.valueOf(commerceOrderId) %>" />
	<aui:input name="commerceShipmentId" type="hidden" value="<%= String.valueOf(commerceShipmentId) %>" />

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
						<th><liferay-ui:message key="order-quantity" /></th>
						<th class="table-cell-content"><liferay-ui:message key="name" /></th>
						<th><liferay-ui:message key="warehouse" /></th>
						<th><liferay-ui:message key="shipment-quantity" /></th>
						<th><liferay-ui:message key="available-quantity" /></th>
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
								<%= remainedQuantity %>
							</td>
							<td>
								<%= HtmlUtil.escape(commerceOrderItem.getName(languageId)) %>
							</td>

							<%@ include file="/order_item_warehouse_quantities.jspf" %>
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
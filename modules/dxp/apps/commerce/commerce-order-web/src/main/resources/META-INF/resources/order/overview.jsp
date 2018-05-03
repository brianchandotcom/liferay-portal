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
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

int[] availableOrderStatuses = commerceOrderEditDisplayContext.getAvailableOrderStatuses();
CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();
long commerceOrderId = commerceOrderEditDisplayContext.getCommerceOrderId();

int orderStatus = BeanParamUtil.getInteger(commerceOrder, request, "orderStatus");
%>

<liferay-portlet:actionURL name="editCommerceOrder" var="editCommerceOrderURL" />

<aui:fieldset-group markupView="lexicon">
	<aui:container>
		<aui:row>
			<aui:col width="<%= 50 %>">
				<aui:form action="<%= editCommerceOrderURL %>" method="post" name="totalsFm">
					<aui:input name="<%= Constants.CMD %>" type="hidden" value="totals" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderId %>" />

					<aui:model-context bean="<%= commerceOrder %>" model="<%= CommerceOrder.class %>" />

					<aui:fieldset disabled="">
						<aui:input name="subtotal" suffix="<%= commerceOrderEditDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= commerceOrderEditDisplayContext.format(commerceOrder.getSubtotal()) %>" />

						<aui:input name="shippingPrice" suffix="<%= commerceOrderEditDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= commerceOrderEditDisplayContext.format(commerceOrder.getShippingPrice()) %>" />

						<aui:input name="total" suffix="<%= commerceOrderEditDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= commerceOrderEditDisplayContext.format(commerceOrder.getTotal()) %>" />

						<aui:button-row>
							<aui:icon cssClass="edit-form-link" image="edit" label="edit-totals" url="javascript:;" />

							<div class="edit-form-buttons hide">
								<aui:button type="submit" />

								<aui:button cssClass="cancel-form-button" type="cancel" />
							</div>
						</aui:button-row>
					</aui:fieldset>
				</aui:form>
			</aui:col>

			<aui:col width="<%= 50 %>">
				<aui:form action="<%= editCommerceOrderURL %>" method="post" name="orderStatusFm">
					<aui:input name="<%= Constants.CMD %>" type="hidden" value="orderStatus" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderId %>" />

					<aui:model-context bean="<%= commerceOrder %>" model="<%= CommerceOrder.class %>" />

					<aui:fieldset disabled="">
						<aui:select label="order-status" name="orderStatus">

							<%
							for (int availableOrderStatus : availableOrderStatuses) {
							%>

								<aui:option label="<%= CommerceOrderConstants.getOrderStatusLabel(availableOrderStatus) %>" selected="<%= availableOrderStatus == orderStatus %>" value="<%= availableOrderStatus %>" />

							<%
							}
							%>

						</aui:select>

						<aui:input label="created" name="createDate" readonly="<%= true %>" type="textbox" value="<%= commerceOrderEditDisplayContext.getCommerceOrderDateTime() %>" />

						<aui:button-row>
							<aui:icon cssClass="edit-form-link" image="edit" label="edit-order-status" url="javascript:;" />

							<div class="edit-form-buttons hide">
								<aui:button type="submit" />

								<aui:button cssClass="cancel-form-button" type="cancel" />
							</div>
						</aui:button-row>
					</aui:fieldset>
				</aui:form>
			</aui:col>
		</aui:row>
	</aui:container>
</aui:fieldset-group>
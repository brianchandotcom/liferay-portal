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
<!-- test order_details.jsp -->

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();
CommerceOrderItem commerceOrderItem = commerceOrderEditDisplayContext.getCommerceOrderItem();

CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

Date requestedDeliveryDate = commerceOrderItem.getRequestedDeliveryDate();
%>

<portlet:actionURL name="/commerce_order/edit_commerce_order_item" var="editCommerceOrderItemActionURL" />

<commerce-ui:panel
	title='<%= LanguageUtil.get(request, "detail") %>'
>
	<aui:form action="<%= editCommerceOrderItemActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderId() %>" />
		<aui:input name="commerceOrderItemId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderItemId() %>" />

		<liferay-ui:error exception="<%= CommerceOrderItemRequestedDeliveryDateException.class %>" message="please-enter-a-valid-requested-delivery-date" />

		<liferay-ui:error exception="<%= CommerceOrderValidatorException.class %>">

			<%
			CommerceOrderValidatorException commerceOrderValidatorException = (CommerceOrderValidatorException)errorException;
			%>

			<c:if test="<%= commerceOrderValidatorException != null %>">

				<%
				for (CommerceOrderValidatorResult commerceOrderValidatorResult : commerceOrderValidatorException.getCommerceOrderValidatorResults()) {
				%>

					<liferay-ui:message key="<%= HtmlUtil.escape(commerceOrderValidatorResult.getLocalizedMessage()) %>" />

				<%
				}
				%>

			</c:if>
		</liferay-ui:error>

		<aui:input bean="<%= commerceOrderItem %>" model="<%= CommerceOrderItem.class %>" name="quantity">
			<aui:validator name="min">1</aui:validator>
			<aui:validator name="number" />
		</aui:input>

		<c:if test="<%= !commerceOrder.isOpen() %>">
			<aui:input name="price" suffix="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" type="text" value="<%= commerceCurrency.round(commerceOrderItem.getUnitPrice()) %>">
				<aui:validator name="min">0</aui:validator>
				<aui:validator name="number" />
			</aui:input>
		</c:if>

		<%
		int requestedDeliveryDay = 0;
		int requestedDeliveryMonth = -1;
		int requestedDeliveryYear = 0;

		if (requestedDeliveryDate != null) {
			Calendar calendar = CalendarFactoryUtil.getCalendar(requestedDeliveryDate.getTime());

			requestedDeliveryDay = calendar.get(Calendar.DAY_OF_MONTH);
			requestedDeliveryMonth = calendar.get(Calendar.MONTH);
			requestedDeliveryYear = calendar.get(Calendar.YEAR);
		}
		%>

		<div class="form-group input-date-wrapper">
			<label for="requestedDeliveryDate"><liferay-ui:message key="requested-delivery-date" /></label>

			<liferay-ui:input-date
				dayParam="requestedDeliveryDateDay"
				dayValue="<%= requestedDeliveryDay %>"
				disabled="<%= false %>"
				monthParam="requestedDeliveryDateMonth"
				monthValue="<%= requestedDeliveryMonth %>"
				name="requestedDeliveryDate"
				nullable="<%= true %>"
				showDisableCheckbox="<%= false %>"
				yearParam="requestedDeliveryDateYear"
				yearValue="<%= requestedDeliveryYear %>"
			/>
		</div>

		<aui:input bean="<%= commerceOrderItem %>" model="<%= CommerceOrderItem.class %>" name="deliveryGroup" />

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />
		</aui:button-row>
	</aui:form>
</commerce-ui:panel>
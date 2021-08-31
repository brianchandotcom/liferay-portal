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

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();

Date requestedDeliveryDate = commerceOrder.getRequestedDeliveryDate();
%>

<portlet:actionURL name="/commerce_order/edit_commerce_order" var="editCommerceOrderRequesedDeliveryDateActionURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCommerceOrderRequesedDeliveryDateActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="requestedDeliveryDate" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrder.getCommerceOrderId() %>" />

		<liferay-ui:error exception="<%= CommerceOrderRequestedDeliveryDateException.class %>" message="please-enter-a-valid-requested-delivery-date" />

		<aui:model-context bean="<%= commerceOrder %>" model="<%= CommerceOrder.class %>" />

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
	</aui:form>
</commerce-ui:modal-content>
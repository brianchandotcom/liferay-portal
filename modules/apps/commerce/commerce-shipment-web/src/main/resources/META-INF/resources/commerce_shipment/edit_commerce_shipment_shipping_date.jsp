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

Date shippingDate = commerceShipment.getShippingDate();

int shippingDay = 0;
int shippingMonth = -1;
int shippingYear = 0;

if (shippingDate != null) {
	Calendar calendar = CalendarFactoryUtil.getCalendar(shippingDate.getTime());

	shippingDay = calendar.get(Calendar.DAY_OF_MONTH);
	shippingMonth = calendar.get(Calendar.MONTH);
	shippingYear = calendar.get(Calendar.YEAR);
}
%>

<portlet:actionURL name="/commerce_shipment/edit_commerce_shipment" var="editCommerceShipmentURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.format(request, "edit-x", "estimated-shipping-date") %>'
>
	<liferay-ui:error exception="<%= CommerceShipmentShippingDateException.class %>" />

	<aui:form action="<%= editCommerceShipmentURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="shippingDate" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipment.getCommerceShipmentId() %>" />

		<liferay-ui:input-date
			dayParam="shippingDateDay"
			dayValue="<%= shippingDay %>"
			disabled="<%= false %>"
			monthParam="shippingDateMonth"
			monthValue="<%= shippingMonth %>"
			name="shippingDate"
			nullable="<%= true %>"
			showDisableCheckbox="<%= false %>"
			yearParam="shippingDateYear"
			yearValue="<%= shippingYear %>"
		/>
	</aui:form>
</commerce-ui:modal-content>
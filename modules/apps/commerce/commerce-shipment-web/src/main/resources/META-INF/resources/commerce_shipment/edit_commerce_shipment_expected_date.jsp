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

Date expectedDate = commerceShipment.getExpectedDate();

int expectedDay = 0;
int expectedMonth = -1;
int expectedYear = 0;

if (expectedDate != null) {
	Calendar calendar = CalendarFactoryUtil.getCalendar(expectedDate.getTime());

	expectedDay = calendar.get(Calendar.DAY_OF_MONTH);
	expectedMonth = calendar.get(Calendar.MONTH);
	expectedYear = calendar.get(Calendar.YEAR);
}
%>

<portlet:actionURL name="/commerce_shipment/edit_commerce_shipment" var="editCommerceShipmentURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.format(request, "edit-x", "estimated-delivery-date") %>'
>
	<liferay-ui:error exception="<%= CommerceShipmentExpectedDateException.class %>" />

	<aui:form action="<%= editCommerceShipmentURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="expectedDate" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipment.getCommerceShipmentId() %>" />

		<liferay-ui:input-date
			dayParam="expectedDateDay"
			dayValue="<%= expectedDay %>"
			disabled="<%= false %>"
			monthParam="expectedDateMonth"
			monthValue="<%= expectedMonth %>"
			name="expectedDeliveryDate"
			nullable="<%= true %>"
			showDisableCheckbox="<%= false %>"
			yearParam="expectedDateYear"
			yearValue="<%= expectedYear %>"
		/>
	</aui:form>
</commerce-ui:modal-content>
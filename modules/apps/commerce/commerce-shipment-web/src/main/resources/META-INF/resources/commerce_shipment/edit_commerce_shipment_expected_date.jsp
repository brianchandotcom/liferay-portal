<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
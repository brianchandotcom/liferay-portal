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

String expectedDateString = null;

Date expectedDate = commerceShipment.getExpectedDate();

if (expectedDate != null) {
	Calendar calendar = CalendarFactoryUtil.getCalendar(expectedDate.getTime());

	Format format = FastDateFormatFactoryUtil.getDate(DateFormat.MEDIUM, locale, user.getTimeZone());

	expectedDateString = format.format(calendar.getTime());
}
%>

<portlet:actionURL name="/commerce_shipment/edit_commerce_shipment" var="editCommerceShipmentURL" />

<liferay-ui:error exception="<%= CommerceShipmentExpectedDateException.class %>" />

<aui:form action="<%= editCommerceShipmentURL %>" cssClass="container-fluid container-fluid-max-xl p-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="expectedDate" />
	<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipment.getCommerceShipmentId() %>" />
	<aui:input label="expected-delivery-date" name="expectedDate" type="date" value="<%= expectedDateString %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="requestProcessed" type="hidden" value='<%= SessionMessages.contains(renderRequest, "requestProcessed") %>' />
</aui:form>
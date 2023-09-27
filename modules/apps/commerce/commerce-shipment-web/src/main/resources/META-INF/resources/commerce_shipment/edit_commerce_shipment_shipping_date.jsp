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
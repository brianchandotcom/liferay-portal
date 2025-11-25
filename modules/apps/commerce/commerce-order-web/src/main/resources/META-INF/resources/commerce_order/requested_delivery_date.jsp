<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();

Date requestedDeliveryDate = commerceOrder.getRequestedDeliveryDate();
%>

<portlet:actionURL name="/commerce_order/edit_commerce_order" var="editCommerceOrderRequesedDeliveryDateActionURL" />

<div class="container-fluid container-fluid-max-xl p-4">
	<aui:form action="<%= editCommerceOrderRequesedDeliveryDateActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="requestedDeliveryDate" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrder.getCommerceOrderId() %>" />

		<liferay-ui:error exception="<%= CommerceOrderRequestedDeliveryDateException.class %>" message="please-enter-a-valid-requested-delivery-date" />

		<aui:model-context bean="<%= commerceOrder %>" model="<%= CommerceOrder.class %>" />

		<%
		String requestedDeliveryDateString = null;

		if (requestedDeliveryDate != null) {
			Calendar calendar = CalendarFactoryUtil.getCalendar(requestedDeliveryDate.getTime());

			Format format = FastDateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd", locale);

			requestedDeliveryDateString = format.format(calendar.getTime());
		}
		%>

		<div class="form-group input-date-wrapper">
			<aui:input name="requestedDeliveryDate" type="date" value="<%= requestedDeliveryDateString %>" />
		</div>
	</aui:form>
</div>
<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrder commerceOrder = commerceOrderContentDisplayContext.getCommerceOrder();

Date requestedDeliveryDate = commerceOrder.getRequestedDeliveryDate();
%>

<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order" var="editCommerceOrderRequestedDeliveryDateActionURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCommerceOrderRequestedDeliveryDateActionURL %>" method="post" name="fm">
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
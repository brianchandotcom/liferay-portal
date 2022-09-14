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
CommerceOrder commerceOrder = commerceOrderContentDisplayContext.getCommerceOrder();

long paymentCommerceTermEntryId = commerceOrder.getPaymentCommerceTermEntryId();
%>

<commerce-ui:modal-content
	showSubmitButton="<%= false %>"
	title='<%= LanguageUtil.get(request, "payment-terms") %>'
>
	<label class="control-label <%= (paymentCommerceTermEntryId == 0) ? " d-none" : "" %>" id="name-label"><liferay-ui:message key="name" /></label>

	<div>
		<%= commerceOrder.getPaymentCommerceTermEntryName() %>
	</div>

	<label class="control-label <%= (paymentCommerceTermEntryId == 0) ? " d-none" : "" %>" id="description-label"><liferay-ui:message key="description" /></label>

	<div id="description-container">
		<%= commerceOrder.getPaymentCommerceTermEntryDescription() %>
	</div>
</commerce-ui:modal-content>
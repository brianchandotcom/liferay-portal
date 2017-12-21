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
WorldpayCommercePaymentEngineGroupServiceConfiguration worldpayCommercePaymentEngineGroupServiceConfiguration = (WorldpayCommercePaymentEngineGroupServiceConfiguration)request.getAttribute(WorldpayCommercePaymentEngineGroupServiceConfiguration.class.getName());

String[] paymentMethodCodes = StringUtil.split(worldpayCommercePaymentEngineGroupServiceConfiguration.paymentMethodCodes());
%>

<aui:fieldset>
	<aui:input label="installation-id" name="settings--installationId--" value="<%= worldpayCommercePaymentEngineGroupServiceConfiguration.installationId() %>" />

	<aui:input label="service-key" name="settings--serviceKey--" value="<%= worldpayCommercePaymentEngineGroupServiceConfiguration.serviceKey() %>" />

	<aui:field-wrapper label="payment-methods">

		<%
		for (String paymentMethodCode : WorldpayCommercePaymentEngineConstants.PAYMENT_METHOD_CODES) {
		%>

			<div>
				<aui:input checked="<%= ArrayUtil.contains(paymentMethodCodes, paymentMethodCode) %>" label="<%= WorldpayCommercePaymentEngineConstants.getPaymentMethodLabel(paymentMethodCode) %>" name="settings--paymentMethodCodes--" type="checkbox" value="<%= paymentMethodCode %>" />
			</div>

		<%
		}
		%>

	</aui:field-wrapper>
</aui:fieldset>
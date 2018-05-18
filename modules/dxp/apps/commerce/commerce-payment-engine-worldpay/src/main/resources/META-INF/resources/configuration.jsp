<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
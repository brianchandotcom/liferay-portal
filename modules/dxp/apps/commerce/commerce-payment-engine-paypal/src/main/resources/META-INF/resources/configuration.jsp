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
PayPalCommercePaymentEngineGroupServiceConfiguration paypalCommercePaymentEngineGroupServiceConfiguration = (PayPalCommercePaymentEngineGroupServiceConfiguration)request.getAttribute(PayPalCommercePaymentEngineGroupServiceConfiguration.class.getName());
%>

<aui:fieldset>
	<div class="alert alert-info">
		<%= LanguageUtil.format(resourceBundle, "paypal-configuration-help", new Object[] {"<a href=\"https://developer.paypal.com/developer/applications/create\" target=\"_blank\">", "</a>"}, false) %>
	</div>

	<aui:input label="client-id" name="settings--clientId--" value="<%= paypalCommercePaymentEngineGroupServiceConfiguration.clientId() %>" />

	<aui:input label="client-secret" name="settings--clientSecret--" value="<%= paypalCommercePaymentEngineGroupServiceConfiguration.clientSecret() %>" />

	<aui:select name="settings--mode--">

		<%
		for (String mode : PayPalCommercePaymentEngineConstants.MODES) {
		%>

			<aui:option label="<%= mode %>" selected="<%= mode.equals(paypalCommercePaymentEngineGroupServiceConfiguration.mode()) %>" value="<%= mode %>" />

		<%
		}
		%>

	</aui:select>
</aui:fieldset>
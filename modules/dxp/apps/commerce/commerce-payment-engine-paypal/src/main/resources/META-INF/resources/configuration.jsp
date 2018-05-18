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
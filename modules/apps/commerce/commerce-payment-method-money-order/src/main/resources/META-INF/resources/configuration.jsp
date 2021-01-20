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
MoneyOrderGroupServiceConfiguration moneyOrderGroupServiceConfiguration = (MoneyOrderGroupServiceConfiguration)request.getAttribute(MoneyOrderGroupServiceConfiguration.class.getName());

String messageAsLocalizedXML = moneyOrderGroupServiceConfiguration.messageAsLocalizedXML();
%>

<portlet:actionURL name="/commerce_payment_methods/edit_money_order_commerce_payment_method_configuration" var="editCommercePaymentMethodActionURL" />

<aui:form action="<%= editCommercePaymentMethodActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="commerceChannelId" type="hidden" value='<%= ParamUtil.getLong(request, "commerceChannelId") %>' />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<commerce-ui:panel>
		<aui:input helpMessage="this-toggles-whether-the-money-order-message-page-is-shown-as-a-checkout-step-or-not" label="show-message-page" labelOff="no" labelOn="yes" name="settings--showMessagePage--" type="toggle-switch" value="<%= moneyOrderGroupServiceConfiguration.showMessagePage() %>" />

		<div id="<portlet:namespace />message">
			<aui:field-wrapper label="message">
				<liferay-ui:input-localized
					fieldPrefix="settings"
					fieldPrefixSeparator="--"
					name="messageAsLocalizedXML"
					type="editor"
					xml="<%= messageAsLocalizedXML %>"
				/>
			</aui:field-wrapper>
		</div>
	</commerce-ui:panel>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.toggleBoxes(
		'<portlet:namespace />showMessagePage',
		'<portlet:namespace />message'
	);
</aui:script>
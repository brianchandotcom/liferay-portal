<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
FunctionCommercePaymentIntegrationConfigurationDisplayContext functionCommercePaymentIntegrationConfigurationDisplayContext = (FunctionCommercePaymentIntegrationConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_payment/edit_function_commerce_payment_integration_configuration" var="editFunctionCommercePaymentIntegrationActionURL" />

<aui:form action="<%= editFunctionCommercePaymentIntegrationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value='<%= ParamUtil.getLong(request, "commerceChannelId") %>' />
	<aui:input name="commercePaymentIntegrationKey" type="hidden" value="<%= functionCommercePaymentIntegrationConfigurationDisplayContext.getCommercePaymentIntegrationKey() %>" />

	<c:if test="<%= functionCommercePaymentIntegrationConfigurationDisplayContext.isDefaultPaymentIntegrationUnicodeProperties() %>">
		<div class="alert alert-info">
			<liferay-ui:message key="use-default-values" />
		</div>
	</c:if>

	<commerce-ui:panel>
		<aui:input autoSize="<%= true %>" id="payment-integration-type-settings" label="type-settings" name="settings--paymentIntegrationTypeSettings--" style="min-height: 600px;" type="textarea" value="<%= functionCommercePaymentIntegrationConfigurationDisplayContext.getPaymentIntegrationUnicodeProperties() %>" />
	</commerce-ui:panel>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href='<%= ParamUtil.getString(request, "redirect") %>' type="cancel" />
	</aui:button-row>
</aui:form>
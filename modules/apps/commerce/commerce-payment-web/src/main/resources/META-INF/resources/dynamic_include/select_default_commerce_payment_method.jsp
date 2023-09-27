<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceChannelAccountEntryRelDisplayContext commerceChannelAccountEntryRelDisplayContext = (CommerceChannelAccountEntryRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "save") %>'
	title='<%= LanguageUtil.get(request, "edit-payment-method") %>'
>
	<portlet:actionURL name="/commerce_payment/edit_account_entry_default_commerce_payment_method" var="editAccountEntryPaymentActionURL" />

	<aui:form action="<%= editAccountEntryPaymentActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="accountEntryId" type="hidden" value="<%= commerceChannelAccountEntryRelDisplayContext.getAccountEntryId() %>" />
		<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelAccountEntryRelDisplayContext.getCommerceChannelId() %>" />

		<aui:model-context bean="<%= commerceChannelAccountEntryRelDisplayContext.getCommercePaymentMethodGroupRel() %>" model="<%= CommercePaymentMethodGroupRel.class %>" />

		<aui:input checked="<%= commerceChannelAccountEntryRelDisplayContext.isCommercePaymentChecked(StringPool.BLANK) %>" label='<%= LanguageUtil.get(request, "use-priority-settings") %>' name="commercePaymentMethodGroupRelId" type="radio" value="0" />

		<%
		for (CommercePaymentMethodGroupRel commercePaymentMethodGroupRel : commerceChannelAccountEntryRelDisplayContext.getCommercePaymentMethodGroupRels()) {
		%>

			<aui:input checked="<%= commerceChannelAccountEntryRelDisplayContext.isCommercePaymentChecked(commercePaymentMethodGroupRel.getPaymentIntegrationKey()) %>" label="<%= commercePaymentMethodGroupRel.getName(locale) %>" name="commercePaymentMethodGroupRelId" type="radio" value="<%= commercePaymentMethodGroupRel.getCommercePaymentMethodGroupRelId() %>" />

		<%
		}
		%>

	</aui:form>
</commerce-ui:modal-content>
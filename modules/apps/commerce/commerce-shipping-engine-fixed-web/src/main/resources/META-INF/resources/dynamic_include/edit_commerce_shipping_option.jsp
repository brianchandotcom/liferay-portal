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
CommerceChannelAccountEntryRelDisplayContext commerceChannelAccountEntryRelDisplayContext = (CommerceChannelAccountEntryRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "save") %>'
	title='<%= LanguageUtil.get(request, "edit-shipping-option") %>'
>
	<portlet:actionURL name="/commerce_shipping_engine_fixed/edit_account_entry_default_commerce_shipping_fixed_option" var="editAccountEntryShippingOptionActionURL" />

	<aui:form action="<%= editAccountEntryShippingOptionActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="accountEntryId" type="hidden" value="<%= commerceChannelAccountEntryRelDisplayContext.getAccountEntryId() %>" />
		<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelAccountEntryRelDisplayContext.getCommerceChannelId() %>" />

		<aui:model-context bean="<%= commerceChannelAccountEntryRelDisplayContext.getCommerceShippingOptionAccountEntryRel() %>" model="<%= CommerceShippingOptionAccountEntryRel.class %>" />

		<aui:input checked="<%= commerceChannelAccountEntryRelDisplayContext.isCommerceShippingFixedOptionChecked(StringPool.BLANK) %>" label='<%= LanguageUtil.get(request, "use-priority-settings") %>' name="commerceShippingFixedOptionId" type="radio" value="0" />

		<%
		for (CommerceShippingFixedOption commerceShippingFixedOption : commerceChannelAccountEntryRelDisplayContext.getCommerceShippingFixedOptions()) {
		%>

			<aui:input checked="<%= commerceChannelAccountEntryRelDisplayContext.isCommerceShippingFixedOptionChecked(commerceShippingFixedOption.getKey()) %>" label="<%= commerceChannelAccountEntryRelDisplayContext.getCommerceShippingOptionLabel(commerceShippingFixedOption) %>" name="commerceShippingFixedOptionId" type="radio" value="<%= commerceShippingFixedOption.getCommerceShippingFixedOptionId() %>" />

		<%
		}
		%>

	</aui:form>
</commerce-ui:modal-content>
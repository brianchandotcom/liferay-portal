<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAccountDisplayContext commerceAccountDisplayContext = (CommerceAccountDisplayContext)request.getAttribute(CommerceAccountWebKeys.COMMERCE_ACCOUNT_DISPLAY_CONTEXT);
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "save") %>'
	title='<%= LanguageUtil.get(request, "edit-shipping-option") %>'
>
	<portlet:actionURL name="/account_entries_admin/edit_account_entry_commerce_shipping_option" var="editAccountEntryShippingOptionActionURL" />

	<aui:form action="<%= editAccountEntryShippingOptionActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="accountEntryId" type="hidden" value="<%= commerceAccountDisplayContext.getAccountEntryId() %>" />
		<aui:input name="commerceChannelId" type="hidden" value="<%= commerceAccountDisplayContext.getCommerceChannelId() %>" />

		<aui:model-context bean="<%= commerceAccountDisplayContext.getCommerceShippingOptionAccountEntryRel() %>" model="<%= CommerceShippingOptionAccountEntryRel.class %>" />

		<aui:input checked="<%= commerceAccountDisplayContext.isCommerceShippingFixedOptionChecked(StringPool.BLANK) %>" label='<%= LanguageUtil.get(request, "use-priority-settings") %>' name="commerceShippingFixedOptionId" type="radio" value="0" />

		<%
		for (CommerceShippingFixedOption commerceShippingFixedOption : commerceAccountDisplayContext.getCommerceShippingFixedOptions()) {
		%>

			<aui:input checked="<%= commerceAccountDisplayContext.isCommerceShippingFixedOptionChecked(commerceShippingFixedOption.getKey()) %>" label="<%= commerceAccountDisplayContext.getCommerceShippingOptionLabel(commerceShippingFixedOption) %>" name="commerceShippingFixedOptionId" type="radio" value="<%= commerceShippingFixedOption.getCommerceShippingFixedOptionId() %>" />

		<%
		}
		%>

	</aui:form>
</commerce-ui:modal-content>
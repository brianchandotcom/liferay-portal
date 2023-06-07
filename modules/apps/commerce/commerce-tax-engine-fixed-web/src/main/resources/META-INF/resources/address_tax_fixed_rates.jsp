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
CommerceTaxFixedRateAddressRelsDisplayContext commerceTaxFixedRateAddressRelsDisplayContext = (CommerceTaxFixedRateAddressRelsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_tax_methods/edit_commerce_tax_fixed_rate_address_rel" var="editCommerceTaxFixedRateAddressRelActionURL" />

<aui:form action="<%= editCommerceTaxFixedRateAddressRelActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateConfiguration" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceTaxFixedRateAddressRelsDisplayContext.getCommerceChannelId() %>" />
	<aui:input name="commerceTaxMethodId" type="hidden" value="<%= commerceTaxFixedRateAddressRelsDisplayContext.getCommerceTaxMethodId() %>" />

	<commerce-ui:panel
		title='<%= LanguageUtil.get(resourceBundle, "taxed-address") %>'
	>
		<aui:select id="applyTaxTo" name="applyTaxTo">
			<aui:option label="shipping-address" selected="<%= commerceTaxFixedRateAddressRelsDisplayContext.isTaxAppliedToShippingAddress() %>" value="<%= true %>" />
			<aui:option label="billing-address" selected="<%= !commerceTaxFixedRateAddressRelsDisplayContext.isTaxAppliedToShippingAddress() %>" value="<%= false %>" />
		</aui:select>
	</commerce-ui:panel>

	<commerce-ui:panel
		bodyClasses="p-0"
	>
		<frontend-data-set:classic-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commerceChannelId", String.valueOf(commerceTaxFixedRateAddressRelsDisplayContext.getCommerceChannelId())
				).put(
					"commerceTaxMethodId", String.valueOf(commerceTaxFixedRateAddressRelsDisplayContext.getCommerceTaxMethodId())
				).build()
			%>'
			creationMenu="<%= commerceTaxFixedRateAddressRelsDisplayContext.getCreationMenu() %>"
			dataProviderKey="<%= CommerceTaxRateSettingFDSNames.TAX_RATE_SETTING %>"
			id="<%= commerceTaxFixedRateAddressRelsDisplayContext.getFDSName() %>"
			itemsPerPage="<%= 10 %>"
			selectedItemsKey="taxRateSettingId"
		/>
	</commerce-ui:panel>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>

<liferay-frontend:component
	module="js/addressTaxFixedRates"
/>
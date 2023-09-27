<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAvalaraTaxRateRelsDisplayContext commerceAvalaraTaxRateRelsDisplayContext = (CommerceAvalaraTaxRateRelsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_tax_methods/edit_commerce_tax_fixed_rate_address_rel" var="editCommerceTaxFixedRateAddressRelActionURL" />

<aui:form action="<%= editCommerceTaxFixedRateAddressRelActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateConfiguration" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceAvalaraTaxRateRelsDisplayContext.getCommerceChannelId() %>" />
	<aui:input name="commerceTaxMethodId" type="hidden" value="<%= commerceAvalaraTaxRateRelsDisplayContext.getCommerceTaxMethodId() %>" />

	<frontend-data-set:classic-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"commerceChannelId", String.valueOf(commerceAvalaraTaxRateRelsDisplayContext.getCommerceChannelId())
			).put(
				"commerceTaxMethodId", String.valueOf(commerceAvalaraTaxRateRelsDisplayContext.getCommerceTaxMethodId())
			).build()
		%>'
		dataProviderKey='<%= CommercePortletKeys.COMMERCE_TAX_METHODS + "-taxRateSetting" %>'
		id='<%= CommercePortletKeys.COMMERCE_TAX_METHODS + "-taxRateSetting" %>'
		itemsPerPage="<%= 10 %>"
		namespace="<%= liferayPortletResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= commerceAvalaraTaxRateRelsDisplayContext.getPortletURL() %>"
	/>
</aui:form>
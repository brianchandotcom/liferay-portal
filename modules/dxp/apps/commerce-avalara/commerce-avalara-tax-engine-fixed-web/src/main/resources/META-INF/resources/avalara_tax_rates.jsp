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
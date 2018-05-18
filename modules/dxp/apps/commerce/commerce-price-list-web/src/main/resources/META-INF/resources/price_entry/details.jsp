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
CommercePriceEntry commercePriceEntry = null;

if (portletName.equals(CommercePriceListPortletKeys.COMMERCE_PRICE_LIST)) {
	CommercePriceEntryDisplayContext commercePriceEntryDisplayContext = (CommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

	commercePriceEntry = commercePriceEntryDisplayContext.getCommercePriceEntry();
}
else {
	CPInstanceCommercePriceEntryDisplayContext cpInstanceCommercePriceEntryDisplayContext = (CPInstanceCommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

	commercePriceEntry = cpInstanceCommercePriceEntryDisplayContext.getCommercePriceEntry();
}

CommercePriceList commercePriceList = commercePriceEntry.getCommercePriceList();

CommerceMoney priceCommerceMoney = commercePriceEntry.getPriceMoney(commercePriceList.getCommerceCurrencyId());
CommerceMoney promoPriceCommerceMoney = commercePriceEntry.getPromoPriceMoney(commercePriceList.getCommerceCurrencyId());

CommerceCurrency commerceCurrency = commercePriceList.getCommerceCurrency();

String currencyCode = commerceCurrency.getCode();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<aui:model-context bean="<%= commercePriceEntry %>" model="<%= CommercePriceEntry.class %>" />

<aui:fieldset>
	<aui:input name="price" suffix="<%= currencyCode %>" type="text" value="<%= priceCommerceMoney.toString() %>" />

	<aui:input name="promoPrice" suffix="<%= currencyCode %>" type="text" value="<%= promoPriceCommerceMoney.toString() %>" />
</aui:fieldset>
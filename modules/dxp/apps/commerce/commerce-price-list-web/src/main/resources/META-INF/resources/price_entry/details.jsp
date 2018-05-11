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
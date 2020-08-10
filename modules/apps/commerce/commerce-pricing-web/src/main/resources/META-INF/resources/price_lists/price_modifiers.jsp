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
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commercePriceListId = commercePriceListDisplayContext.getCommercePriceListId();
%>

<c:if test="<%= commercePriceListDisplayContext.hasPermission(commercePriceListId, ActionKeys.UPDATE) %>">

	<%
	Map<String, String> contextParams = new HashMap<>();

	contextParams.put("commercePriceListId", String.valueOf(commercePriceListId));
	%>

	<div class="pt-4">
		<commerce-ui:dataset-display
			clayCreationMenu="<%= commercePriceListDisplayContext.getPriceModifiersClayCreationMenu() %>"
			contextParams="<%= contextParams %>"
			dataProviderKey="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRICE_MODIFIERS %>"
			formId="fm"
			id="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRICE_MODIFIERS %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= renderResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= currentURLObj %>"
			style="stacked"
		/>
	</div>
</c:if>
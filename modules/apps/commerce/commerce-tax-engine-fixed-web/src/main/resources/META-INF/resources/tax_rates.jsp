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
CommerceTaxFixedRatesDisplayContext commerceTaxFixedRatesDisplayContext = (CommerceTaxFixedRatesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<%
Map<String, String> contextParams = HashMapBuilder.<String, String>put(
	"commerceChannelId", String.valueOf(commerceTaxFixedRatesDisplayContext.getCommerceChannelId())
).put(
	"commerceTaxMethodId", String.valueOf(commerceTaxFixedRatesDisplayContext.getCommerceTaxMethodId())
).build();
%>

<clay:data-set-display
	contextParams="<%= contextParams %>"
	creationMenu="<%= commerceTaxFixedRatesDisplayContext.getCreationMenu() %>"
	dataProviderKey="<%= CommerceTaxRateClayTable.NAME %>"
	id="<%= CommerceTaxRateClayTable.NAME %>"
	itemsPerPage="<%= 10 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= commerceTaxFixedRatesDisplayContext.getPortletURL() %>"
	showSearch="<%= false %>"
/>
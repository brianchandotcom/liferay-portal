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
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceOrderItem> commerceOrderItemsSearchContainer = commerceOrderEditDisplayContext.getCommerceOrderItemsSearchContainer();

CommerceOrderItemDisplayTerms commerceOrderItemDisplayTerms = (CommerceOrderItemDisplayTerms)commerceOrderItemsSearchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= commerceOrderItemDisplayTerms %>"
	id="toggle_id_commerce_order_item_search"
	markupView="lexicon"
>
	<aui:fieldset>
		<aui:input name="sku" value="<%= commerceOrderItemDisplayTerms.getSku() %>" />

		<aui:input name="title" value="<%= commerceOrderItemDisplayTerms.getTitle() %>" />
	</aui:fieldset>
</liferay-ui:search-toggle>
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
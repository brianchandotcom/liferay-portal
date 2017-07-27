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
int type = ParamUtil.getInteger(request, "type", CommerceCartConstants.COMMERCE_CART_TYPE_CART);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceCartItem commerceCartItem = (CommerceCartItem)row.getObject();
%>

<portlet:actionURL name="editCommerceCartItem" var="editCommerceCartItemURL" />

<aui:form action="<%= editCommerceCartItemURL %>" method="post" name="editCommerceCartItemQuantityFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceCartItemId" type="hidden" value="<%= commerceCartItem.getCommerceCartItemId() %>" />
	<aui:input name="type" type="hidden" value="<%= type %>" />

	<aui:model-context bean="<%= commerceCartItem %>" model="<%= CommerceCartItem.class %>" />

	<aui:input cssClass="commerce-cart-item-quantity-input" label="<%= StringPool.BLANK %>" name="quantity" />

	<aui:button cssClass="commerce-cart-item-quantity-button" name="refreshButton" type="submit" value="refresh" />
</aui:form>
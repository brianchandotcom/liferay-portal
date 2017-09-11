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
CommerceWishListContentDisplayContext commerceWishListContentDisplayContext = (CommerceWishListContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceCartItem commerceCartItem = (CommerceCartItem)row.getObject();

request.setAttribute("cpDefinition", commerceCartItem.getCPDefinition());
request.setAttribute("cpInstance", commerceCartItem.fetchCPInstance());
%>

<%
if (commerceWishListContentDisplayContext.canSellWithoutOptionsCombination(commerceCartItem)) {
%>

	<liferay-util:dynamic-include key="com.liferay.commerce.product.content.web#/add_to_cart#" />

<%
}
else {
%>

	<aui:button
		cssClass="btn-lg btn-primary"
		href="<%= commerceWishListContentDisplayContext.getCPDefinitionURL(commerceCartItem.getCPDefinitionId(), themeDisplay) %>"
		name="selectOptions"
		type="button"
		value="select-options"
	/>

<%
}
%>

<portlet:actionURL name="editCommerceCartItem" var="deleteURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="commerceCartItemId" value="<%= String.valueOf(commerceCartItem.getCommerceCartItemId()) %>" />
	<portlet:param name="type" value="<%= String.valueOf(CommerceConstants.COMMERCE_CART_TYPE_WISH_LIST) %>" />
</portlet:actionURL>

<aui:button href="<%= deleteURL %>" name="removeItem" value="remove" />
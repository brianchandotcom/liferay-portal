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
CommerceWishListDisplayContext commerceWishListDisplayContext = (CommerceWishListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceWishListItem commerceWishListItem = (CommerceWishListItem)row.getObject();

CPInstance cpInstance = commerceWishListItem.fetchCPInstance();
CProduct cProduct = commerceWishListItem.getCProduct();

String addToCartId = PortalUtil.generateRandomKey(request, "add-to-cart");
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= commerceWishListDisplayContext.hasManageCommerceWishListsPermission() %>">
		<portlet:actionURL name="editCommerceWishListItem" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceWishListItemId" value="<%= String.valueOf(commerceWishListItem.getCommerceWishListItemId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>

<c:choose>
	<c:when test="<%= cpInstance != null %>">
		<commerce-ui:add-to-cart
			CPInstanceId="<%= cpInstance.getCPInstanceId() %>"
			id="<%= addToCartId %>"
		/>
	</c:when>
	<c:otherwise>
		<a class="commerce-button commerce-button--outline w-100" href="<%= commerceWishListDisplayContext.getCPDefinitionURL(cProduct.getPublishedCPDefinitionId(), themeDisplay) %>"><liferay-ui:message key="view-all-variants" /></a>
	</c:otherwise>
</c:choose>
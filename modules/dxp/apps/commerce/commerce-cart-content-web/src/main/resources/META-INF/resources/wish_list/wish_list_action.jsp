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

<c:choose>
	<c:when test="<%= commerceWishListContentDisplayContext.isIgnoreSKUCombinations(commerceCartItem) %>">
		<aui:button cssClass="btn-lg" value="add-to-cart" />
	</c:when>
	<c:otherwise>
		<aui:button
			cssClass="btn-primary"
			href="<%= commerceWishListContentDisplayContext.getCPDefinitionURL(commerceCartItem.getCPDefinitionId(), themeDisplay) %>"
			name="selectOptions"
			type="button"
			value="select-options"
		/>
	</c:otherwise>
</c:choose>
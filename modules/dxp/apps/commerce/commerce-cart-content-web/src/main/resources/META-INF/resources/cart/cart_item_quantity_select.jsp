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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceCartItem commerceCartItem = (CommerceCartItem)row.getObject();
%>

<portlet:actionURL name="editCommerceCartItem" var="editCommerceCartItemURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="commerceCartItemId" value="<%= String.valueOf(commerceCartItem.getCommerceCartItemId()) %>" />
</portlet:actionURL>

<aui:input bean="<%= commerceCartItem %>" label="<%= StringPool.BLANK %>" name="quantity" />

<aui:button href="<%= editCommerceCartItemURL %>" name="refreshQuantity" type="submit" value="refresh" />
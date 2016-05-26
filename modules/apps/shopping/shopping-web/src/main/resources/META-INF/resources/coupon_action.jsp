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

ShoppingCoupon coupon = (ShoppingCoupon)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="/shopping/edit_coupon" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="couponId" value="<%= String.valueOf(coupon.getCouponId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="/shopping/edit_coupon" var="deleteURL">
		<portlet:param name="mvcActionCommand" value="/shopping/edit_coupon" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="deleteCouponIds" value="<%= String.valueOf(coupon.getCouponId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%= deleteURL %>" />
</liferay-ui:icon-menu>
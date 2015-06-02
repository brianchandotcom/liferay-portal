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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "categories");

String tabs1Names = "categories,cart";

if (!user.isDefaultUser()) {
	tabs1Names += ",orders";
}

if (ShoppingPermission.contains(permissionChecker, scopeGroupId, ActionKeys.MANAGE_COUPONS)) {
	tabs1Names += ",coupons";
}

// View

PortletURL viewURL = renderResponse.createRenderURL();

viewURL.setParameter("struts_action", "/shopping/view");

// Cart

PortletURL cartURL = renderResponse.createRenderURL();

cartURL.setParameter("struts_action", "/shopping/cart");

if (!tabs1.equals("cart")) {
	cartURL.setParameter("redirect", currentURL);
}

// Back URL

String backURL = ParamUtil.getString(request, "backURL");
%>

<liferay-ui:tabs
	backURL="<%= backURL %>"
	names="<%= tabs1Names %>"
	url="<%= viewURL.toString() %>"
	urls="<%= new String[] {StringPool.BLANK, cartURL.toString()} %>"
/>
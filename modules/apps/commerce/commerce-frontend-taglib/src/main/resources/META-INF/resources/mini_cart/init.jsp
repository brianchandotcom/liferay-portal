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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<%@ page import="java.util.Map" %>

<liferay-theme:defineObjects />

<%
Map<String, String> cartViews = (Map<String, String>)request.getAttribute("liferay-commerce:cart:cartViews");
int itemsQuantity = (int)request.getAttribute("liferay-commerce:cart:itemsQuantity");
Map<String, String> labels = (Map<String, String>)request.getAttribute("liferay-commerce:cart:labels");
String checkoutURL = (String)request.getAttribute("liferay-commerce:cart:checkoutURL");
boolean displayDiscountLevels = (boolean)request.getAttribute("liferay-commerce:cart:displayDiscountLevels");
boolean displayTotalItemsQuantity = (boolean)request.getAttribute("liferay-commerce:cart:displayTotalItemsQuantity");
String orderDetailURL = (String)request.getAttribute("liferay-commerce:cart:orderDetailURL");
long orderId = (long)request.getAttribute("liferay-commerce:cart:orderId");
String siteDefaultURL = (String)request.getAttribute("liferay-commerce:cart:siteDefaultURL");
String spritemap = (String)request.getAttribute("liferay-commerce:cart:spritemap");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib") + StringPool.UNDERLINE;
boolean toggleable = (boolean)request.getAttribute("liferay-commerce:cart:toggleable");

String miniCartId = randomNamespace + "cart";
%>
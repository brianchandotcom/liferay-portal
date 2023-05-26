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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<%@ page import="java.util.Map" %>

<liferay-theme:defineObjects />

<%
long accountEntryId = (Long)request.getAttribute("liferay-commerce:cart:accountEntryId");
Map<String, String> cartViews = (Map<String, String>)request.getAttribute("liferay-commerce:cart:cartViews");
String checkoutURL = (String)request.getAttribute("liferay-commerce:cart:checkoutURL");
long commerceChannelGroupId = (Long)request.getAttribute("liferay-commerce:cart:commerceChannelGroupId");
long commerceChannelId = (Long)request.getAttribute("liferay-commerce:cart:commerceChannelId");
String commerceCurrencyCode = (String)request.getAttribute("liferay-commerce:cart:commerceCurrencyCode");
boolean displayDiscountLevels = (boolean)request.getAttribute("liferay-commerce:cart:displayDiscountLevels");
boolean displayTotalItemsQuantity = (boolean)request.getAttribute("liferay-commerce:cart:displayTotalItemsQuantity");
int itemsQuantity = (int)request.getAttribute("liferay-commerce:cart:itemsQuantity");
Map<String, String> labels = (Map<String, String>)request.getAttribute("liferay-commerce:cart:labels");
String orderDetailURL = (String)request.getAttribute("liferay-commerce:cart:orderDetailURL");
long orderId = (long)request.getAttribute("liferay-commerce:cart:orderId");
String productURLSeparator = (String)request.getAttribute("liferay-commerce:cart:productURLSeparator");
boolean requestCodeEnabled = (boolean)request.getAttribute("liferay-commerce:cart:requestQuoteEnabled");
String siteDefaultURL = (String)request.getAttribute("liferay-commerce:cart:siteDefaultURL");
boolean toggleable = (boolean)request.getAttribute("liferay-commerce:cart:toggleable");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib") + StringPool.UNDERLINE;

String miniCartId = randomNamespace + "cart";
%>
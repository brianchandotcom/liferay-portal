<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.frontend.model.ProductSettingsModel" %><%@
page import="com.liferay.commerce.product.model.CPInstanceUnitOfMeasure" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONSerializer" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<liferay-theme:defineObjects />

<%
String alignment = (String)request.getAttribute("liferay-commerce:add-to-cart:alignment");
String disabled = (String)request.getAttribute("liferay-commerce:add-to-cart:disabled");
String commerceAccountId = (String)request.getAttribute("liferay-commerce:add-to-cart:commerceAccountId");
String commerceChannelGroupId = (String)request.getAttribute("liferay-commerce:add-to-cart:commerceChannelGroupId");
String commerceChannelId = (String)request.getAttribute("liferay-commerce:add-to-cart:commerceChannelId");
String commerceCurrencyCode = (String)request.getAttribute("liferay-commerce:add-to-cart:commerceCurrencyCode");
String commerceOrderId = (String)request.getAttribute("liferay-commerce:add-to-cart:commerceOrderId");
String cpInstanceId = (String)request.getAttribute("liferay-commerce:add-to-cart:cpInstanceId");
CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = (CPInstanceUnitOfMeasure)request.getAttribute("liferay-commerce:add-to-cart:cpInstanceUnitOfMeasure");
String iconOnly = (String)request.getAttribute("liferay-commerce:add-to-cart:iconOnly");
String inCart = (String)request.getAttribute("liferay-commerce:add-to-cart:inCart");
String inline = (String)request.getAttribute("liferay-commerce:add-to-cart:inline");
String namespace = (String)request.getAttribute("liferay-commerce:add-to-cart:namespace");
String productId = (String)request.getAttribute("liferay-commerce:add-to-cart:productId");
ProductSettingsModel productSettingsModel = (ProductSettingsModel)request.getAttribute("liferay-commerce:add-to-cart:productSettingsModel");
String size = (String)request.getAttribute("liferay-commerce:add-to-cart:size");
String showOrderTypeModal = (String)request.getAttribute("liferay-commerce:add-to-cart:showOrderTypeModal");
String showOrderTypeModalURL = (String)request.getAttribute("liferay-commerce:add-to-cart:showOrderTypeModalURL");
String showUnitOfMeasureSelector = (String)request.getAttribute("liferay-commerce:add-to-cart:showUnitOfMeasureSelector");
String skuOptions = (String)request.getAttribute("liferay-commerce:add-to-cart:skuOptions");
String stockQuantity = (String)request.getAttribute("liferay-commerce:add-to-cart:stockQuantity");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib") + StringPool.UNDERLINE;

String addToCartId = randomNamespace + "add_to_cart";
%>
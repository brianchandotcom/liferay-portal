<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.frontend.model.ProductSettingsModel" %><%@
page import="com.liferay.commerce.product.model.CPInstanceUnitOfMeasure" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
String alignment = (String)request.getAttribute("liferay-commerce:add-to-cart:alignment");
boolean disabled = (boolean)request.getAttribute("liferay-commerce:add-to-cart:disabled");
long commerceAccountId = (long)request.getAttribute("liferay-commerce:add-to-cart:commerceAccountId");
long commerceChannelGroupId = (long)request.getAttribute("liferay-commerce:add-to-cart:commerceChannelGroupId");
long commerceChannelId = (long)request.getAttribute("liferay-commerce:add-to-cart:commerceChannelId");
String commerceCurrencyCode = (String)request.getAttribute("liferay-commerce:add-to-cart:commerceCurrencyCode");
long commerceOrderId = (long)request.getAttribute("liferay-commerce:add-to-cart:commerceOrderId");
long cpInstanceId = (long)request.getAttribute("liferay-commerce:add-to-cart:cpInstanceId");
CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure = (CPInstanceUnitOfMeasure)request.getAttribute("liferay-commerce:add-to-cart:cpInstanceUnitOfMeasure");
boolean iconOnly = (boolean)request.getAttribute("liferay-commerce:add-to-cart:iconOnly");
boolean inCart = (boolean)request.getAttribute("liferay-commerce:add-to-cart:inCart");
boolean inline = (boolean)request.getAttribute("liferay-commerce:add-to-cart:inline");
String namespace = (String)request.getAttribute("liferay-commerce:add-to-cart:namespace");
long productId = (long)request.getAttribute("liferay-commerce:add-to-cart:productId");
boolean published = (boolean)request.getAttribute("liferay-commerce:add-to-cart:published");
boolean purchasable = (boolean)request.getAttribute("liferay-commerce:add-to-cart:purchasable");
ProductSettingsModel productSettingsModel = (ProductSettingsModel)request.getAttribute("liferay-commerce:add-to-cart:productSettingsModel");
String size = (String)request.getAttribute("liferay-commerce:add-to-cart:size");
boolean showOrderTypeModal = (boolean)request.getAttribute("liferay-commerce:add-to-cart:showOrderTypeModal");
String showOrderTypeModalURL = (String)request.getAttribute("liferay-commerce:add-to-cart:showOrderTypeModalURL");
boolean showUnitOfMeasureSelector = (boolean)request.getAttribute("liferay-commerce:add-to-cart:showUnitOfMeasureSelector");
String skuOptions = (String)request.getAttribute("liferay-commerce:add-to-cart:skuOptions");
int stockQuantity = (int)request.getAttribute("liferay-commerce:add-to-cart:stockQuantity");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib") + StringPool.UNDERLINE;

String addToCartId = randomNamespace + "add_to_cart";
%>
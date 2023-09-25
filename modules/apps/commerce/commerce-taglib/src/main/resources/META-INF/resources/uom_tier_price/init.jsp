<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String commerceAccountId = (String)request.getAttribute("liferay-commerce:uom-tier-price:commerceAccountId");
String commerceChannelId = (String)request.getAttribute("liferay-commerce:uom-tier-price:commerceChannelId");
String cpInstanceId = (String)request.getAttribute("liferay-commerce:uom-tier-price:cpInstanceId");
String namespace = (String)request.getAttribute("liferay-commerce:uom-tier-price:namespace");
String productId = (String)request.getAttribute("liferay-commerce:uom-tier-price:productId");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib") + StringPool.UNDERLINE;

String uomTierPriceId = randomNamespace + "uom_tier_price";
%>
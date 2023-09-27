<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.commerce.product.catalog.CPCatalogEntry" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<liferay-theme:defineObjects />

<%
Long commerceChannelGroupId = (Long)request.getAttribute("liferay-commerce:compare-checkbox:commerceChannelGroupId");
CPCatalogEntry cpCatalogEntry = (CPCatalogEntry)request.getAttribute("liferay-commerce:compare-checkbox:cpCatalogEntry");
Boolean disabled = (Boolean)request.getAttribute("liferay-commerce:compare-checkbox:disabled");
Boolean inCompare = (Boolean)request.getAttribute("liferay-commerce:compare-checkbox:inCompare");
String label = (String)request.getAttribute("liferay-commerce:compare-checkbox:label");
String pictureUrl = (String)request.getAttribute("liferay-commerce:compare-checkbox:pictureUrl");
Boolean refreshOnRemove = (Boolean)request.getAttribute("liferay-commerce:compare-checkbox:refreshOnRemove");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_compare-checkbox") + StringPool.UNDERLINE;

String rootId = randomNamespace + "compare-checkbox";
%>
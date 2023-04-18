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

<liferay-theme:defineObjects />

<%
boolean disabled = (boolean)request.getAttribute("liferay-commerce:request-quote:disabled");
long commerceAccountId = (long)request.getAttribute("liferay-commerce:request-quote:commerceAccountId");
long commerceChannelId = (long)request.getAttribute("liferay-commerce:request-quote:commerceChannelId");
String commerceCurrencyCode = (String)request.getAttribute("liferay-commerce:request-quote:commerceCurrencyCode");
long cpDefinitionId = (long)request.getAttribute("liferay-commerce:request-quote:cpDefinitionId");
long cpInstanceId = (long)request.getAttribute("liferay-commerce:request-quote:cpInstanceId");
String namespace = (String)request.getAttribute("liferay-commerce:request-quote:namespace");
String orderDetailURL = (String)request.getAttribute("liferay-commerce:request-quote:orderDetailURL");
boolean priceOnApplication = (boolean)request.getAttribute("liferay-commerce:request-quote:priceOnApplication");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib") + StringPool.UNDERLINE;

String requestQuoteElementId = randomNamespace + "request_quote";

boolean requestQuoteEnabled = (boolean)request.getAttribute("liferay-commerce:request-quote:requestQuoteEnabled");
%>
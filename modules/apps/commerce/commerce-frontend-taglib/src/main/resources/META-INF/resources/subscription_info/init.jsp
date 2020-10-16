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

<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<liferay-theme:defineObjects />

<%
String durationPeriod = (String)request.getAttribute("liferay-commerce:subscription-info:durationPeriod");
String subscriptionPeriod = (String)request.getAttribute("liferay-commerce:subscription-info:subscriptionPeriod");
String deliveryDurationPeriod = (String)request.getAttribute("liferay-commerce:subscription-info:deliveryDurationPeriod");
String deliverySubscriptionPeriod = (String)request.getAttribute("liferay-commerce:subscription-info:deliverySubscriptionPeriod");
%>
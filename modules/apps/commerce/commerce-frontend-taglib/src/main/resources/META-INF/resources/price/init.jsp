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
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.frontend.model.PriceModel" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONSerializer" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<liferay-theme:defineObjects />

<%
boolean compact = (boolean)request.getAttribute("commerce-ui:price:compact");
boolean displayDiscountLevels = (boolean)request.getAttribute("commerce-ui:price:displayDiscountLevels");
String namespace = (String)request.getAttribute("commerce-ui:price:namespace");
boolean netPrice = (boolean)request.getAttribute("commerce-ui:price:netPrice");
PriceModel priceModel = (PriceModel)request.getAttribute("commerce-ui:price:priceModel");

JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
%>
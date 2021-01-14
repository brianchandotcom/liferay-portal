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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<liferay-theme:defineObjects />

<%
String commerceAccountId = (String)request.getAttribute("liferay-commerce:add-to-wish-list:commerceAccountId");
String cpDefinitionId = (String)request.getAttribute("liferay-commerce:add-to-wish-list:cpDefinitionId");
String large = (String)request.getAttribute("liferay-commerce:add-to-wish-list:large");
String inWishList = (String)request.getAttribute("liferay-commerce:add-to-wish-list:inWishList");
String skuId = (String)request.getAttribute("liferay-commerce:add-to-wish-list:skuId");
String spritemap = (String)request.getAttribute("liferay-commerce:add-to-wish-list:spritemap");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib") + StringPool.UNDERLINE;

String addToWishListId = randomNamespace + "add_to_wish_list";
%>
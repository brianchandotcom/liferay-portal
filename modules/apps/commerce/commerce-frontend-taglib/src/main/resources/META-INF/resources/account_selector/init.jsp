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

<%@ page import="com.liferay.commerce.frontend.taglib.internal.model.CurrentCommerceAccountModel" %><%@
page import="com.liferay.commerce.frontend.taglib.internal.model.CurrentCommerceOrderModel" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONSerializer" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<liferay-theme:defineObjects />

<%
String[] accountEntryAllowedTypes = (String[])request.getAttribute("liferay-commerce:account-selector:accountEntryAllowedTypes");
long commerceChannelId = (long)request.getAttribute("liferay-commerce:account-selector:commerceChannelId");
String createNewOrderURL = (String)request.getAttribute("liferay-commerce:account-selector:createNewOrderURL");
CurrentCommerceAccountModel currentCommerceAccount = (CurrentCommerceAccountModel)request.getAttribute("liferay-commerce:account-selector:currentCommerceAccount");
CurrentCommerceOrderModel currentCommerceOrder = (CurrentCommerceOrderModel)request.getAttribute("liferay-commerce:account-selector:currentCommerceOrder");
JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
String selectOrderURL = (String)request.getAttribute("liferay-commerce:account-selector:selectOrderURL");
String setCurrentAccountURL = (String)request.getAttribute("liferay-commerce:account-selector:setCurrentAccountURL");
Boolean showOrderTypeModal = (Boolean)request.getAttribute("liferay-commerce:account-selector:showOrderTypeModal");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_account_selector") + StringPool.UNDERLINE;

String accountSelectorId = randomNamespace + "account-selector";
%>
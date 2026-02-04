<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="java.util.Map" %>

<liferay-theme:defineObjects />

<%
Map<String, Object> actionContext = (Map<String, Object>)request.getAttribute("liferay-commerce:panel:actionContext");
String actionIcon = (String)request.getAttribute("liferay-commerce:panel:actionIcon");
String actionLabel = (String)request.getAttribute("liferay-commerce:panel:actionLabel");
String actionTargetId = (String)request.getAttribute("liferay-commerce:panel:actionTargetId");
String actionURL = (String)request.getAttribute("liferay-commerce:panel:actionURL");
String bodyClasses = (String)request.getAttribute("liferay-commerce:panel:bodyClasses");
boolean collapsed = (boolean)request.getAttribute("liferay-commerce:panel:collapsed");
String collapseLabel = (String)request.getAttribute("liferay-commerce:panel:collapseLabel");
String collapseSwitchName = (String)request.getAttribute("liferay-commerce:panel:collapseSwitchName");
boolean collapsible = (boolean)request.getAttribute("liferay-commerce:panel:collapsible");
String elementClasses = (String)request.getAttribute("liferay-commerce:panel:elementClasses");
String randomNamespace = (String)request.getAttribute("liferay-commerce:panel:randomNamespace");
String secondaryActionIcon = (String)request.getAttribute("liferay-commerce:panel:secondaryActionIcon");
String secondaryActionLabel = (String)request.getAttribute("liferay-commerce:panel:secondaryActionLabel");
String secondaryActionTargetId = (String)request.getAttribute("liferay-commerce:panel:secondaryActionTargetId");
String secondaryActionURL = (String)request.getAttribute("liferay-commerce:panel:secondaryActionURL");
String showMoreId = (String)request.getAttribute("liferay-commerce:panel:showMoreId");
String showMoreURL = (String)request.getAttribute("liferay-commerce:panel:showMoreURL");
String title = (String)request.getAttribute("liferay-commerce:panel:title");

String linkId = Validator.isNotNull(actionTargetId) ? actionTargetId : (randomNamespace + "header-link");
String secondaryLinkId = Validator.isNotNull(secondaryActionTargetId) ? secondaryActionTargetId : (randomNamespace + "header-link");
String showMoreButtonId = Validator.isNotNull(showMoreId) ? showMoreId : (randomNamespace + "show-more-button");
String showMoreButtonWrapperId = randomNamespace + "show-more-button-wrapper";
%>
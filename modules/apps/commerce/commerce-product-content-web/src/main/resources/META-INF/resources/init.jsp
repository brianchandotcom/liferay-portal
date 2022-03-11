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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/adaptive-media-image" prefix="liferay-adaptive-media" %><%@
taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/commerce" prefix="liferay-commerce" %><%@
taglib uri="http://liferay.com/tld/commerce-product" prefix="liferay-commerce-product" %><%@
taglib uri="http://liferay.com/tld/commerce-ui" prefix="commerce-ui" %><%@
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/soy" prefix="soy" %><%@
taglib uri="http://liferay.com/tld/template" prefix="liferay-template" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.asset.kernel.exception.DuplicateQueryRuleException" %><%@
page import="com.liferay.commerce.account.model.CommerceAccount" %><%@
page import="com.liferay.commerce.constants.CommerceWebKeys" %><%@
page import="com.liferay.commerce.context.CommerceContext" %><%@
page import="com.liferay.commerce.model.CommerceOrder" %><%@
page import="com.liferay.commerce.product.catalog.CPCatalogEntry" %><%@
page import="com.liferay.commerce.product.catalog.CPSku" %><%@
page import="com.liferay.commerce.product.constants.CPWebKeys" %><%@
page import="com.liferay.commerce.product.content.constants.CPContentWebKeys" %><%@
page import="com.liferay.commerce.product.content.render.CPContentRenderer" %><%@
page import="com.liferay.commerce.product.content.render.list.CPContentListRenderer" %><%@
page import="com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRenderer" %><%@
page import="com.liferay.commerce.product.content.util.CPCompareContentHelper" %><%@
page import="com.liferay.commerce.product.content.util.CPContentHelper" %><%@
page import="com.liferay.commerce.product.content.util.CPMedia" %><%@
page import="com.liferay.commerce.product.content.web.internal.constants.CPCompareContentConstants" %><%@
page import="com.liferay.commerce.product.content.web.internal.constants.CPCompareContentMiniConstants" %><%@
page import="com.liferay.commerce.product.content.web.internal.constants.CPContentPortletConstants" %><%@
page import="com.liferay.commerce.product.content.web.internal.constants.CPPublisherConstants" %><%@
page import="com.liferay.commerce.product.content.web.internal.display.context.CPCompareContentDisplayContext" %><%@
page import="com.liferay.commerce.product.content.web.internal.display.context.CPCompareContentMiniDisplayContext" %><%@
page import="com.liferay.commerce.product.content.web.internal.display.context.CPContentConfigurationDisplayContext" %><%@
page import="com.liferay.commerce.product.content.web.internal.display.context.CPPublisherConfigurationDisplayContext" %><%@
page import="com.liferay.commerce.product.content.web.internal.display.context.CPPublisherDisplayContext" %><%@
page import="com.liferay.commerce.product.content.web.internal.frontend.constants.CPContentDataSetConstants" %><%@
page import="com.liferay.commerce.product.content.web.internal.portlet.CPCompareContentMiniPortlet" %><%@
page import="com.liferay.commerce.product.content.web.internal.portlet.CPCompareContentPortlet" %><%@
page import="com.liferay.commerce.product.content.web.internal.portlet.CPContentPortlet" %><%@
page import="com.liferay.commerce.product.content.web.internal.portlet.CPPublisherPortlet" %><%@
page import="com.liferay.commerce.product.data.source.CPDataSource" %><%@
page import="com.liferay.commerce.product.data.source.CPDataSourceResult" %><%@
page import="com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue" %><%@
page import="com.liferay.commerce.product.model.CPInstance" %><%@
page import="com.liferay.commerce.product.model.CPOptionCategory" %><%@
page import="com.liferay.commerce.product.model.CPSpecificationOption" %><%@
page import="com.liferay.commerce.product.type.CPType" %><%@
page import="com.liferay.commerce.util.CommerceUtil" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.Collections" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String languageId = LanguageUtil.getLanguageId(locale);
%>
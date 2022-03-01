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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/commerce-ui" prefix="commerce-ui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.account.constants.CommerceAccountConstants" %><%@
page import="com.liferay.commerce.channel.web.internal.dao.search.AccountEntryCommercePaymentMethodDisplaySearchContainerFactory" %><%@
page import="com.liferay.commerce.channel.web.internal.display.CommerceAccountEntryDisplay" %><%@
page import="com.liferay.commerce.channel.web.internal.display.CommercePaymentMethodDisplay" %><%@
page import="com.liferay.commerce.channel.web.internal.display.context.CommerceChannelDisplayContext" %><%@
page import="com.liferay.commerce.channel.web.internal.display.context.SiteCommerceChannelTypeDisplayContext" %><%@
page import="com.liferay.commerce.channel.web.internal.frontend.CommerceChannelClayTable" %><%@
page import="com.liferay.commerce.channel.web.internal.frontend.CommerceChannelHealthCheckClayTable" %><%@
page import="com.liferay.commerce.channel.web.internal.frontend.CommercePaymentMethodClayTable" %><%@
page import="com.liferay.commerce.channel.web.internal.frontend.CommerceShippingMethodClayTable" %><%@
page import="com.liferay.commerce.channel.web.internal.frontend.CommerceTaxMethodClayTable" %><%@
page import="com.liferay.commerce.channel.web.internal.servlet.taglib.ui.constants.CommerceChannelScreenNavigationConstants" %><%@
page import="com.liferay.commerce.constants.CommerceOrderConstants" %><%@
page import="com.liferay.commerce.currency.model.CommerceCurrency" %><%@
page import="com.liferay.commerce.payment.method.CommercePaymentMethod" %><%@
page import="com.liferay.commerce.payment.method.CommercePaymentMethodRegistry" %><%@
page import="com.liferay.commerce.pricing.constants.CommercePricingConstants" %><%@
page import="com.liferay.commerce.product.channel.CommerceChannelType" %><%@
page import="com.liferay.commerce.product.exception.NoSuchChannelException" %><%@
page import="com.liferay.commerce.product.model.CPTaxCategory" %><%@
page import="com.liferay.commerce.product.model.CommerceChannel" %><%@
page import="com.liferay.document.library.kernel.exception.FileExtensionException" %><%@
page import="com.liferay.document.library.kernel.exception.InvalidFileException" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.WorkflowDefinitionLink" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinition" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Arrays" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String redirect = ParamUtil.getString(request, "redirect");
%>
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
taglib uri="http://liferay.com/tld/expando" prefix="liferay-expando" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.pricing.exception.NoSuchPricingClassException" %><%@
page import="com.liferay.commerce.pricing.model.CommercePricingClass" %><%@
page import="com.liferay.commerce.pricing.util.PricingNavigationItemRegistryUtil" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommercePricingClassCPDefinitionDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommercePricingClassDiscountDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommercePricingClassDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommercePricingClassPriceListDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingClassDataSetConstants" %><%@
page import="com.liferay.commerce.pricing.web.servlet.taglib.ui.CommercePricingClassScreenNavigationConstants" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.taglib.util.CustomAttributesUtil" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
	String redirect = ParamUtil.getString(request, "redirect");

	String backURL = ParamUtil.getString(request, "backURL", redirect);
%>
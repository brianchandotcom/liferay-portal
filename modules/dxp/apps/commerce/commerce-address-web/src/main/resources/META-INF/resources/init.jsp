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
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.address.web.internal.display.context.CommerceCountriesDisplayContext" %><%@
page import="com.liferay.commerce.address.web.internal.display.context.CommerceRegionsDisplayContext" %><%@
page import="com.liferay.commerce.address.web.internal.servlet.taglib.ui.CommerceCountryScreenNavigationConstants" %><%@
page import="com.liferay.commerce.address.web.internal.util.CountriesCommerceAdminModule" %><%@
page import="com.liferay.commerce.admin.constants.CommerceAdminPortletKeys" %><%@
page import="com.liferay.commerce.admin.constants.CommerceAdminWebKeys" %><%@
page import="com.liferay.commerce.constants.CommerceActionKeys" %><%@
page import="com.liferay.commerce.exception.CommerceCountryNameException" %><%@
page import="com.liferay.commerce.exception.CommerceCountryThreeLettersISOCodeException" %><%@
page import="com.liferay.commerce.exception.CommerceCountryTwoLettersISOCodeException" %><%@
page import="com.liferay.commerce.exception.CommerceRegionNameException" %><%@
page import="com.liferay.commerce.model.CommerceCountry" %><%@
page import="com.liferay.commerce.model.CommerceRegion" %><%@
page import="com.liferay.commerce.service.permission.CommercePermission" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String commerceAdminModuleKey = CountriesCommerceAdminModule.KEY;

String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

String lifecycle = (String)request.getAttribute(LiferayPortletRequest.LIFECYCLE_PHASE);

PortletURL countriesURLObj = PortalUtil.getControlPanelPortletURL(request, CommerceAdminPortletKeys.COMMERCE_ADMIN, lifecycle);

countriesURLObj.setParameter("commerceAdminModuleKey", CountriesCommerceAdminModule.KEY);

String countriesURL = countriesURLObj.toString();
%>
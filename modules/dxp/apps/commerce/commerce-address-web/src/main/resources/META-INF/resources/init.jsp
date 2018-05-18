<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.address.web.internal.admin.CountriesCommerceAdminModule" %><%@
page import="com.liferay.commerce.address.web.internal.display.context.CommerceCountriesDisplayContext" %><%@
page import="com.liferay.commerce.address.web.internal.display.context.CommerceRegionsDisplayContext" %><%@
page import="com.liferay.commerce.address.web.internal.servlet.taglib.ui.CommerceCountryScreenNavigationConstants" %><%@
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
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
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
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

<%@ page import="com.liferay.commerce.admin.constants.CommerceAdminWebKeys" %><%@
page import="com.liferay.commerce.currency.configuration.ExchangeRateProviderGroupServiceConfiguration" %><%@
page import="com.liferay.commerce.currency.constants.CommerceCurrencyActionKeys" %><%@
page import="com.liferay.commerce.currency.exception.CommerceCurrencyCodeException" %><%@
page import="com.liferay.commerce.currency.exception.CommerceCurrencyNameException" %><%@
page import="com.liferay.commerce.currency.model.CommerceCurrency" %><%@
page import="com.liferay.commerce.currency.service.permission.CommerceCurrencyPermission" %><%@
page import="com.liferay.commerce.currency.util.RoundingType" %><%@
page import="com.liferay.commerce.currency.web.internal.admin.CurrenciesCommerceAdminModule" %><%@
page import="com.liferay.commerce.currency.web.internal.display.context.CommerceCurrenciesDisplayContext" %><%@
page import="com.liferay.commerce.currency.web.internal.servlet.taglib.ui.CommerceCurrencyScreenNavigationConstants" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.math.BigDecimal" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String commerceAdminModuleKey = CurrenciesCommerceAdminModule.KEY;
%>
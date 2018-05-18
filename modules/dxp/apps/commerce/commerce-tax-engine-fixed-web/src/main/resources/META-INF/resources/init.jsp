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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.admin.constants.CommerceAdminWebKeys" %><%@
page import="com.liferay.commerce.constants.CommerceActionKeys" %><%@
page import="com.liferay.commerce.constants.CommerceConstants" %><%@
page import="com.liferay.commerce.model.CommerceCountry" %><%@
page import="com.liferay.commerce.model.CommerceRegion" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException" %><%@
page import="com.liferay.commerce.product.model.CPTaxCategory" %><%@
page import="com.liferay.commerce.service.permission.CommercePermission" %><%@
page import="com.liferay.commerce.tax.engine.fixed.exception.DuplicateCommerceTaxFixedRateException" %><%@
page import="com.liferay.commerce.tax.engine.fixed.exception.NoSuchTaxFixedRateException" %><%@
page import="com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate" %><%@
page import="com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel" %><%@
page import="com.liferay.commerce.tax.engine.fixed.web.internal.display.context.CommerceTaxFixedRateAddressRelsDisplayContext" %><%@
page import="com.liferay.commerce.tax.engine.fixed.web.internal.display.context.CommerceTaxFixedRatesDisplayContext" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String commerceAdminModuleKey = CommerceConstants.TAXES_COMMERCE_ADMIN_MODULE_KEY;

String languageId = LanguageUtil.getLanguageId(locale);
%>
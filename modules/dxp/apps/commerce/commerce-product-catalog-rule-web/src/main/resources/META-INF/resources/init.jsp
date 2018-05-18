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

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.product.catalog.rule.CPRuleType" %><%@
page import="com.liferay.commerce.product.catalog.rule.CPRuleTypeJSPContributor" %><%@
page import="com.liferay.commerce.product.catalog.rule.web.internal.display.context.CPCatalogRuleDisplayContext" %><%@
page import="com.liferay.commerce.product.catalog.rule.web.internal.servlet.taglib.ui.CPRuleScreenNavigationConstants" %><%@
page import="com.liferay.commerce.product.exception.CPRuleTypeException" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPRuleException" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException" %><%@
page import="com.liferay.commerce.product.model.CPRule" %><%@
page import="com.liferay.commerce.product.model.CPRuleUserSegmentRel" %><%@
page import="com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String redirect = ParamUtil.getString(request, "redirect");
%>
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
taglib uri="http://liferay.com/tld/expando" prefix="liferay-expando" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.asset.kernel.model.AssetCategory" %><%@
page import="com.liferay.commerce.discount.exception.CommerceDiscountRuleTypeException" %><%@
page import="com.liferay.commerce.discount.exception.NoSuchDiscountException" %><%@
page import="com.liferay.commerce.discount.exception.NoSuchDiscountRelException" %><%@
page import="com.liferay.commerce.discount.exception.NoSuchDiscountRuleException" %><%@
page import="com.liferay.commerce.discount.model.CommerceDiscount" %><%@
page import="com.liferay.commerce.discount.model.CommerceDiscountConstants" %><%@
page import="com.liferay.commerce.discount.model.CommerceDiscountRel" %><%@
page import="com.liferay.commerce.discount.model.CommerceDiscountRule" %><%@
page import="com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel" %><%@
page import="com.liferay.commerce.discount.rule.type.CommerceDiscountRuleType" %><%@
page import="com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeJSPContributor" %><%@
page import="com.liferay.commerce.discount.target.CommerceDiscountTarget" %><%@
page import="com.liferay.commerce.discount.web.internal.display.context.CommerceDiscountDisplayContext" %><%@
page import="com.liferay.commerce.discount.web.internal.display.context.CommerceDiscountRelDisplayContext" %><%@
page import="com.liferay.commerce.discount.web.internal.display.context.CommerceDiscountRuleDisplayContext" %><%@
page import="com.liferay.commerce.discount.web.internal.servlet.taglib.ui.CommerceDiscountScreenNavigationConstants" %><%@
page import="com.liferay.commerce.product.model.CPDefinition" %><%@
page import="com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %>

<%@ page import="java.math.BigDecimal" %>

<%@ page import="java.util.HashMap" %><%@
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
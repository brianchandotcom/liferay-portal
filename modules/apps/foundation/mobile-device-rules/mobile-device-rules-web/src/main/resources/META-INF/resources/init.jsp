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
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPNavigationItemList" %><%@
page import="com.liferay.mobile.device.rules.action.ActionHandler" %><%@
page import="com.liferay.mobile.device.rules.action.ActionHandlerManagerUtil" %><%@
page import="com.liferay.mobile.device.rules.constants.MDRPortletKeys" %><%@
page import="com.liferay.mobile.device.rules.exception.ActionTypeException" %><%@
page import="com.liferay.mobile.device.rules.exception.NoSuchActionException" %><%@
page import="com.liferay.mobile.device.rules.exception.NoSuchRuleException" %><%@
page import="com.liferay.mobile.device.rules.exception.NoSuchRuleGroupException" %><%@
page import="com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException" %><%@
page import="com.liferay.mobile.device.rules.model.MDRAction" %><%@
page import="com.liferay.mobile.device.rules.model.MDRRule" %><%@
page import="com.liferay.mobile.device.rules.model.MDRRuleGroup" %><%@
page import="com.liferay.mobile.device.rules.model.MDRRuleGroupInstance" %><%@
page import="com.liferay.mobile.device.rules.rule.RuleGroupProcessorUtil" %><%@
page import="com.liferay.mobile.device.rules.rule.UnknownRuleHandlerException" %><%@
page import="com.liferay.mobile.device.rules.service.MDRActionLocalServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.service.MDRRuleGroupLocalServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.service.MDRRuleLocalServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.util.comparator.RuleCreateDateComparator" %><%@
page import="com.liferay.mobile.device.rules.util.comparator.RuleGroupInstancePriorityComparator" %><%@
page import="com.liferay.mobile.device.rules.web.internal.constants.MDRWebKeys" %><%@
page import="com.liferay.mobile.device.rules.web.internal.display.context.MDRActionDisplayContext" %><%@
page import="com.liferay.mobile.device.rules.web.internal.rule.group.rule.SimpleRuleHandler" %><%@
page import="com.liferay.mobile.device.rules.web.internal.search.RuleGroupChecker" %><%@
page import="com.liferay.mobile.device.rules.web.internal.search.RuleGroupDisplayTerms" %><%@
page import="com.liferay.mobile.device.rules.web.internal.search.RuleGroupSearch" %><%@
page import="com.liferay.mobile.device.rules.web.internal.search.RuleGroupSearchTerms" %><%@
page import="com.liferay.mobile.device.rules.web.internal.security.permission.resource.MDRPermission" %><%@
page import="com.liferay.mobile.device.rules.web.internal.security.permission.resource.MDRRuleGroupInstancePermission" %><%@
page import="com.liferay.mobile.device.rules.web.internal.security.permission.resource.MDRRuleGroupPermission" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.mobile.device.DeviceDetectionUtil" %><%@
page import="com.liferay.portal.kernel.mobile.device.VersionableName" %><%@
page import="com.liferay.portal.kernel.model.ColorScheme" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.Layout" %><%@
page import="com.liferay.portal.kernel.model.LayoutConstants" %><%@
page import="com.liferay.portal.kernel.model.LayoutSet" %><%@
page import="com.liferay.portal.kernel.model.Theme" %><%@
page import="com.liferay.portal.kernel.plugin.PluginPackage" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLUtil" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.GroupServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.LayoutLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.LayoutServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.LayoutTemplateLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.ThemeLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparator" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.SetUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.util.PropsValues" %>

<%@ page import="java.util.Collection" %><%@
page import="java.util.Collections" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.LinkedHashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());
%>

<%@ include file="/init-ext.jsp" %>
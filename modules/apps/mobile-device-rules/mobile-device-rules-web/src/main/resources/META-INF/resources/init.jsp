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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.mobile.device.rules.action.ActionHandler" %><%@
page import="com.liferay.mobile.device.rules.action.ActionHandlerManagerUtil" %><%@
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
page import="com.liferay.mobile.device.rules.rule.group.rule.SimpleRuleHandler" %><%@
page import="com.liferay.mobile.device.rules.service.MDRActionLocalServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.service.MDRRuleGroupLocalServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.service.MDRRuleLocalServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.service.permission.MDRPermission" %><%@
page import="com.liferay.mobile.device.rules.service.permission.MDRRuleGroupInstancePermission" %><%@
page import="com.liferay.mobile.device.rules.service.permission.MDRRuleGroupPermission" %><%@
page import="com.liferay.mobile.device.rules.util.comparator.RuleGroupInstancePriorityComparator" %><%@
page import="com.liferay.mobile.device.rules.web.constants.MDRWebKeys" %><%@
page import="com.liferay.mobile.device.rules.web.search.RuleGroupChecker" %><%@
page import="com.liferay.mobile.device.rules.web.search.RuleGroupDisplayTerms" %><%@
page import="com.liferay.mobile.device.rules.web.search.RuleGroupSearch" %><%@
page import="com.liferay.mobile.device.rules.web.search.RuleGroupSearchTerms" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.RowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.mobile.device.DeviceDetectionUtil" %><%@
page import="com.liferay.portal.kernel.mobile.device.VersionableName" %><%@
page import="com.liferay.portal.kernel.plugin.PluginPackage" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.SetUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.model.ColorScheme" %><%@
page import="com.liferay.portal.model.Group" %><%@
page import="com.liferay.portal.model.Layout" %><%@
page import="com.liferay.portal.model.LayoutSet" %><%@
page import="com.liferay.portal.model.Theme" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.service.GroupServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutSetLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutTemplateLocalServiceUtil" %><%@
page import="com.liferay.portal.service.ThemeLocalServiceUtil" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portlet.PortletURLUtil" %>

<%@ page import="java.util.Collection" %><%@
page import="java.util.Collections" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.LinkedHashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());
%>

<%@ include file="/init-ext.jsp" %>
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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.ImageTypeException" %><%@
page import="com.liferay.portal.LayoutFriendlyURLException" %><%@
page import="com.liferay.portal.LayoutFriendlyURLsException" %><%@
page import="com.liferay.portal.LayoutNameException" %><%@
page import="com.liferay.portal.LayoutTypeException" %><%@
page import="com.liferay.portal.NoSuchGroupException" %><%@
page import="com.liferay.portal.NoSuchLayoutSetBranchException" %><%@
page import="com.liferay.portal.RequiredLayoutException" %><%@
page import="com.liferay.portal.SitemapChangeFrequencyException" %><%@
page import="com.liferay.portal.SitemapIncludeException" %><%@
page import="com.liferay.portal.SitemapPagePriorityException" %><%@
page import="com.liferay.portal.kernel.servlet.DynamicServletRequest" %><%@
page import="com.liferay.portal.util.LayoutTypeControllerTracker" %><%@
page import="com.liferay.portlet.layoutsadmin.context.LayoutsAdminDisplayContext" %><%@
page import="com.liferay.portlet.mobiledevicerules.model.MDRAction" %><%@
page import="com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup" %><%@
page import="com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.MDRActionLocalServiceUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.permission.MDRPermissionUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.permission.MDRRuleGroupInstancePermissionUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.util.RuleGroupInstancePriorityComparator" %>

<%
LayoutsAdminDisplayContext layoutsAdminDisplayContext = new LayoutsAdminDisplayContext(request, liferayPortletResponse);
%>

<%@ include file="/init-ext.jsp" %>
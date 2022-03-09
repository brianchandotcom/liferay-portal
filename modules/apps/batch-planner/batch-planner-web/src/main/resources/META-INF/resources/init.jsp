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
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.batch.planner.constants.BatchPlannerLogConstants" %><%@
page import="com.liferay.batch.planner.model.BatchPlannerPlan" %><%@
page import="com.liferay.batch.planner.web.internal.display.BatchPlannerLogDisplay" %><%@
page import="com.liferay.batch.planner.web.internal.display.context.BatchPlannerLogDisplayContext" %><%@
page import="com.liferay.batch.planner.web.internal.display.context.BatchPlannerLogManagementToolbarDisplayContext" %><%@
page import="com.liferay.batch.planner.web.internal.display.context.BatchPlannerPlanDisplayContext" %><%@
page import="com.liferay.batch.planner.web.internal.display.context.BatchPlannerPlanManagementToolbarDisplayContext" %><%@
page import="com.liferay.batch.planner.web.internal.display.context.EditBatchPlannerPlanDisplayContext" %><%@
page import="com.liferay.batch.planner.web.internal.security.permission.resource.BatchPlannerPlanPermission" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption" %><%@
page import="com.liferay.petra.portlet.url.builder.ActionURLBuilder" %><%@
page import="com.liferay.petra.portlet.url.builder.PortletURLBuilder" %><%@
page import="com.liferay.petra.portlet.url.builder.ResourceURLBuilder" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.petra.string.StringUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.Arrays" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/batch-planner-web/css/main.css") %>" rel="stylesheet" />
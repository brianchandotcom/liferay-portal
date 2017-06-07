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
taglib uri="http://liferay.com/tld/staging" prefix="liferay-staging" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames" %><%@
page import="com.liferay.exportimport.kernel.staging.LayoutStagingUtil" %><%@
page import="com.liferay.exportimport.kernel.staging.StagingUtil" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.exception.LayoutBranchNameException" %><%@
page import="com.liferay.portal.kernel.exception.LayoutSetBranchNameException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.Layout" %><%@
page import="com.liferay.portal.kernel.model.LayoutBranch" %><%@
page import="com.liferay.portal.kernel.model.LayoutRevision" %><%@
page import="com.liferay.portal.kernel.model.LayoutRevisionConstants" %><%@
page import="com.liferay.portal.kernel.model.LayoutSetBranch" %><%@
page import="com.liferay.portal.kernel.model.LayoutSetBranchConstants" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.auth.AuthException" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.service.LayoutBranchLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.LayoutRevisionLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.GroupPermissionUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.LayoutBranchPermissionUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.LayoutPermissionUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.LayoutSetBranchPermissionUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionErrors" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PortletKeys" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.util.comparator.LayoutRevisionCreateDateComparator" %><%@
page import="com.liferay.portal.kernel.util.comparator.LayoutRevisionIdComparator" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowTask" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.staging.bar.web.internal.display.context.LayoutBranchDisplayContext" %><%@
page import="com.liferay.staging.bar.web.internal.display.context.LayoutSetBranchDisplayContext" %><%@
page import="com.liferay.staging.constants.StagingProcessesWebKeys" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.List" %>

<%@ page import="javax.portlet.PortletMode" %><%@
page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-staging:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
group = (Group)renderRequest.getAttribute(WebKeys.GROUP);
layout = (Layout)renderRequest.getAttribute(WebKeys.LAYOUT);
privateLayout = (boolean)renderRequest.getAttribute(WebKeys.PRIVATE_LAYOUT);

LayoutBranchDisplayContext layoutBranchDisplayContext = new LayoutBranchDisplayContext(request);
LayoutSetBranchDisplayContext layoutSetBranchDisplayContext = new LayoutSetBranchDisplayContext(request);
%>

<%@ include file="/init-ext.jsp" %>
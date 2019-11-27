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

<%@ page import="com.liferay.frontend.taglib.servlet.taglib.util.EmptyResultMessageKeys" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.constants.LayoutPageTemplateAdminWebKeys" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.display.context.DisplayPageDisplayContext" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.display.context.DisplayPageManagementToolbarDisplayContext" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.display.context.LayoutPageTemplateCollectionsDisplayContext" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.display.context.LayoutPageTemplateCollectionsManagementToolbarDisplayContext" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.display.context.LayoutPageTemplateDisplayContext" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.display.context.LayoutPageTemplateManagementToolbarDisplayContext" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.display.context.LayoutPageTemplatesAdminDisplayContext" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.display.context.LayoutPrototypeDisplayContext" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.display.context.LayoutPrototypeManagementToolbarDisplayContext" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.display.context.MasterPageDisplayContext" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.display.context.MasterPageManagementToolbarDisplayContext" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.security.permission.resource.LayoutPageTemplateCollectionPermission" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.servlet.taglib.clay.DisplayPageVerticalCard" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.servlet.taglib.clay.LayoutPageTemplateEntryVerticalCard" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.servlet.taglib.clay.LayoutPrototypeVerticalCard" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.servlet.taglib.clay.MasterPageVerticalCard" %><%@
page import="com.liferay.layout.page.template.admin.web.internal.servlet.taglib.clay.SelectMasterPageVerticalCard" %><%@
page import="com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys" %><%@
page import="com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants" %><%@
page import="com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateCollectionException" %><%@
page import="com.liferay.layout.page.template.exception.LayoutPageTemplateCollectionNameException" %><%@
page import="com.liferay.layout.page.template.exception.RequiredLayoutPageTemplateEntryException" %><%@
page import="com.liferay.layout.page.template.model.LayoutPageTemplateCollection" %><%@
page import="com.liferay.layout.page.template.model.LayoutPageTemplateEntry" %><%@
page import="com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalServiceUtil" %><%@
page import="com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil" %><%@
page import="com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.exception.PortalException" %><%@
page import="com.liferay.portal.kernel.exception.RequiredLayoutPrototypeException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.LayoutPrototype" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.service.LayoutPrototypeServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.model.impl.LayoutPrototypeImpl" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.sites.kernel.util.SitesUtil" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
LayoutPageTemplatesAdminDisplayContext layoutPageTemplatesAdminDisplayContext = new LayoutPageTemplatesAdminDisplayContext(liferayPortletRequest, liferayPortletResponse);
%>

<%@ include file="/init-ext.jsp" %>
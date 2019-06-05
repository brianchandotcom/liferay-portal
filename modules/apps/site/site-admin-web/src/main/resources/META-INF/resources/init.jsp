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

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/expando" prefix="liferay-expando" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/map" prefix="liferay-map" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/site-navigation" prefix="liferay-site-navigation" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPNavigationItemList" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem" %><%@
page import="com.liferay.map.constants.MapProviderWebKeys" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.exception.DuplicateGroupException" %><%@
page import="com.liferay.portal.kernel.exception.GroupFriendlyURLException" %><%@
page import="com.liferay.portal.kernel.exception.GroupInheritContentException" %><%@
page import="com.liferay.portal.kernel.exception.GroupKeyException" %><%@
page import="com.liferay.portal.kernel.exception.GroupNameException" %><%@
page import="com.liferay.portal.kernel.exception.GroupParentException" %><%@
page import="com.liferay.portal.kernel.exception.LayoutSetVirtualHostException" %><%@
page import="com.liferay.portal.kernel.exception.LocaleException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchGroupException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchLayoutException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchLayoutSetException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchRoleException" %><%@
page import="com.liferay.portal.kernel.exception.PendingBackgroundTaskException" %><%@
page import="com.liferay.portal.kernel.exception.RequiredGroupException" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.GroupConstants" %><%@
page import="com.liferay.portal.kernel.model.Layout" %><%@
page import="com.liferay.portal.kernel.model.LayoutSet" %><%@
page import="com.liferay.portal.kernel.model.LayoutSetPrototype" %><%@
page import="com.liferay.portal.kernel.model.MembershipRequest" %><%@
page import="com.liferay.portal.kernel.model.Organization" %><%@
page import="com.liferay.portal.kernel.model.Portlet" %><%@
page import="com.liferay.portal.kernel.model.Role" %><%@
page import="com.liferay.portal.kernel.model.SiteConstants" %><%@
page import="com.liferay.portal.kernel.model.Team" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.model.role.RoleConstants" %><%@
page import="com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.LayoutLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.LayoutSetPrototypeServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.OrganizationLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.RoleLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.TeamLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserGroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.PortalPermissionUtil" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePairComparator" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.PropertiesParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.liveusers.LiveUsers" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.ratings.kernel.RatingsType" %><%@
page import="com.liferay.ratings.kernel.display.context.CompanyPortletRatingsDefinitionDisplayContext" %><%@
page import="com.liferay.ratings.kernel.display.context.GroupPortletRatingsDefinitionDisplayContext" %><%@
page import="com.liferay.ratings.kernel.transformer.RatingsDataTransformerUtil" %><%@
page import="com.liferay.site.admin.web.internal.configuration.SiteAdminConfiguration" %><%@
page import="com.liferay.site.admin.web.internal.constants.SiteAdminPortletKeys" %><%@
page import="com.liferay.site.admin.web.internal.constants.SiteAdminWebKeys" %><%@
page import="com.liferay.site.admin.web.internal.display.context.SelectSiteInitializerDisplayContext" %><%@
page import="com.liferay.site.admin.web.internal.display.context.SiteAdminDisplayContext" %><%@
page import="com.liferay.site.admin.web.internal.display.context.SiteAdminManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.admin.web.internal.servlet.taglib.clay.SelectSiteInitializerVerticalCard" %><%@
page import="com.liferay.site.admin.web.internal.servlet.taglib.clay.SiteVerticalCard" %><%@
page import="com.liferay.sites.kernel.util.Sites" %><%@
page import="com.liferay.sites.kernel.util.SitesUtil" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletPreferences" %><%@
page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
SiteAdminConfiguration siteAdminConfiguration = ConfigurationProviderUtil.getSystemConfiguration(SiteAdminConfiguration.class);

SiteAdminDisplayContext siteAdminDisplayContext = new SiteAdminDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

SiteAdminManagementToolbarDisplayContext siteAdminManagementToolbarDisplayContext = new SiteAdminManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, siteAdminDisplayContext);

portletDisplay.setShowStagingIcon(false);
%>

<%@ include file="/init-ext.jsp" %>
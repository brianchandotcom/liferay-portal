<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.DuplicateGroupException" %>
<%@ page import="com.liferay.portal.DuplicateTeamException" %>
<%@ page import="com.liferay.portal.GroupFriendlyURLException" %>
<%@ page import="com.liferay.portal.GroupNameException" %>
<%@ page import="com.liferay.portal.ImageTypeException" %>
<%@ page import="com.liferay.portal.LARFileException" %>
<%@ page import="com.liferay.portal.LARTypeException" %>
<%@ page import="com.liferay.portal.LayoutFriendlyURLException" %>
<%@ page import="com.liferay.portal.LayoutImportException" %>
<%@ page import="com.liferay.portal.LayoutSetVirtualHostException" %>
<%@ page import="com.liferay.portal.MembershipRequestCommentsException" %>
<%@ page import="com.liferay.portal.NoSuchGroupException" %>
<%@ page import="com.liferay.portal.NoSuchLayoutException" %>
<%@ page import="com.liferay.portal.NoSuchLayoutSetException" %>
<%@ page import="com.liferay.portal.NoSuchRoleException" %>
<%@ page import="com.liferay.portal.RemoteExportException" %>
<%@ page import="com.liferay.portal.RemoteOptionsException" %>
<%@ page import="com.liferay.portal.RequiredGroupException" %>
<%@ page import="com.liferay.portal.RequiredLayoutException" %>
<%@ page import="com.liferay.portal.TeamNameException" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataException" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandler" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerBoolean" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerChoice" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerControl" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerKeys" %>
<%@ page import="com.liferay.portal.kernel.lar.UserIdStrategy" %>
<%@ page import="com.liferay.portal.kernel.plugin.PluginPackage" %>
<%@ page import="com.liferay.portal.kernel.scheduler.SchedulerEngineUtil" %>
<%@ page import="com.liferay.portal.kernel.scheduler.Trigger" %>
<%@ page import="com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse" %>
<%@ page import="com.liferay.portal.kernel.staging.StagingConstants" %>
<%@ page import="com.liferay.portal.kernel.staging.StagingUtil" %>
<%@ page import="com.liferay.portal.lar.LayoutExporter" %>
<%@ page import="com.liferay.portal.liveusers.LiveUsers" %>
<%@ page import="com.liferay.portal.plugin.PluginUtil" %>
<%@ page import="com.liferay.portal.service.permission.GroupPermissionUtil" %>
<%@ page import="com.liferay.portal.service.permission.TeamPermissionUtil" %>
<%@ page import="com.liferay.portal.util.CustomJspRegistryUtil" %>
<%@ page import="com.liferay.portal.util.LayoutLister" %>
<%@ page import="com.liferay.portal.util.LayoutView" %>
<%@ page import="com.liferay.portal.util.RobotsUtil" %>
<%@ page import="com.liferay.portal.security.permission.comparator.ActionComparator" %>
<%@ page import="com.liferay.portal.security.permission.comparator.ModelResourceComparator" %>
<%@ page import="com.liferay.portal.service.permission.PortalPermissionUtil" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.GroupRoleChecker" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.RoleSearch" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.RoleSearchTerms" %>
<%@ page import="com.liferay.portlet.rolesadmin.util.RolesAdminUtil" %>
<%@ page import="com.liferay.portlet.sites.action.ActionUtil" %>
<%@ page import="com.liferay.portlet.sites.search.UserGroupGroupRoleRoleChecker" %>
<%@ page import="com.liferay.portlet.sites.search.UserGroupGroupRoleUserGroupChecker" %>
<%@ page import="com.liferay.portlet.sites.search.UserGroupRoleRoleChecker" %>
<%@ page import="com.liferay.portlet.sites.search.UserGroupRoleUserChecker" %>
<%@ page import="com.liferay.portlet.sitesadmin.search.TeamDisplayTerms" %>
<%@ page import="com.liferay.portlet.sitesadmin.search.TeamSearch" %>
<%@ page import="com.liferay.portlet.sitesadmin.search.TeamSearchTerms" %>
<%@ page import="com.liferay.portlet.sitesadmin.search.UserGroupTeamChecker" %>
<%@ page import="com.liferay.portlet.sitesadmin.search.UserTeamChecker" %>
<%@ page import="com.liferay.portlet.usersadmin.search.GroupSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.GroupSearchTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.OrganizationGroupChecker" %>
<%@ page import="com.liferay.portlet.usersadmin.search.OrganizationSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.OrganizationSearchTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserGroupChecker" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserGroupGroupChecker" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserGroupSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserGroupSearchTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserSearchTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.util.UsersAdminUtil" %>

<%
boolean filterManageableGroups = true;

if (permissionChecker.isCompanyAdmin()) {
	filterManageableGroups = false;
}

long[] classNameIds = new long[] {PortalUtil.getClassNameId(Group.class), PortalUtil.getClassNameId(Organization.class)};

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/sites_admin/init-ext.jsp" %>
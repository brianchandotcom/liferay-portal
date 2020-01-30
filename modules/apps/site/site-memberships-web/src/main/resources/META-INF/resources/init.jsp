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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.exception.DuplicateGroupException" %><%@
page import="com.liferay.portal.kernel.exception.GroupKeyException" %><%@
page import="com.liferay.portal.kernel.exception.MembershipRequestCommentsException" %><%@
page import="com.liferay.portal.kernel.exception.RequiredGroupException" %><%@
page import="com.liferay.portal.kernel.exception.RequiredUserException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Company" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.GroupConstants" %><%@
page import="com.liferay.portal.kernel.model.MembershipRequest" %><%@
page import="com.liferay.portal.kernel.model.MembershipRequestConstants" %><%@
page import="com.liferay.portal.kernel.model.Organization" %><%@
page import="com.liferay.portal.kernel.model.OrganizationConstants" %><%@
page import="com.liferay.portal.kernel.model.Role" %><%@
page import="com.liferay.portal.kernel.model.Team" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.model.UserGroup" %><%@
page import="com.liferay.portal.kernel.model.UserGroupGroupRole" %><%@
page import="com.liferay.portal.kernel.model.UserGroupRole" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.service.CompanyLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.MembershipRequestLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.OrganizationLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.RoleLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.TeamLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.GroupPermissionUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.site.memberships.web.internal.constants.SiteMembershipWebKeys" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.OrganizationsDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.OrganizationsManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.RolesDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.RolesManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.SelectOrganizationsDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.SelectOrganizationsManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.SelectRolesDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.SelectRolesManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.SelectUserGroupsDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.SelectUserGroupsManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.SelectUsersDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.SelectUsersManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.SiteMembershipsDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.UserGroupRolesDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.UserGroupRolesManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.UserGroupsDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.UserGroupsManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.UserRolesDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.UserRolesManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.UsersDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.UsersManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.ViewMembershipRequestsDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.display.context.ViewMembershipRequestsManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.memberships.web.internal.servlet.taglib.clay.OrganizationsVerticalCard" %><%@
page import="com.liferay.site.memberships.web.internal.servlet.taglib.clay.RoleVerticalCard" %><%@
page import="com.liferay.site.memberships.web.internal.servlet.taglib.clay.SelectRoleVerticalCard" %><%@
page import="com.liferay.site.memberships.web.internal.servlet.taglib.clay.UsersUserCard" %><%@
page import="com.liferay.site.memberships.web.internal.servlet.taglib.clay.ViewMembershipRequestsPendingUserCard" %><%@
page import="com.liferay.site.memberships.web.internal.servlet.taglib.clay.ViewMembershipRequestsUserCard" %><%@
page import="com.liferay.site.memberships.web.internal.servlet.taglib.util.OrganizationActionDropdownItemsProvider" %><%@
page import="com.liferay.site.memberships.web.internal.servlet.taglib.util.UserActionDropdownItemsProvider" %><%@
page import="com.liferay.site.memberships.web.internal.util.GroupUtil" %><%@
page import="com.liferay.sites.kernel.util.SitesUtil" %><%@
page import="com.liferay.taglib.util.LexiconUtil" %><%@
page import="com.liferay.users.admin.kernel.util.UsersAdmin" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.LinkedHashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %><%@
page import="java.util.Set" %><%@
page import="java.util.TreeSet" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
SiteMembershipsDisplayContext siteMembershipsDisplayContext = new SiteMembershipsDisplayContext(request, liferayPortletResponse);

portletDisplay.setShowStagingIcon(false);
%>

<%@ include file="/init-ext.jsp" %>
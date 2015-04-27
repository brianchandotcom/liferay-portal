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

<%@ page import="com.liferay.portal.DuplicateGroupException" %><%@
page import="com.liferay.portal.GroupKeyException" %><%@
page import="com.liferay.portal.MembershipRequestCommentsException" %><%@
page import="com.liferay.portal.RequiredGroupException" %><%@
page import="com.liferay.portlet.rolesadmin.action.ActionUtil" %><%@
page import="com.liferay.portlet.rolesadmin.search.RoleSearch" %><%@
page import="com.liferay.portlet.rolesadmin.search.RoleSearchTerms" %><%@
page import="com.liferay.portlet.sites.search.OrganizationRoleUserChecker" %><%@
page import="com.liferay.portlet.sites.search.UserGroupGroupRoleRoleChecker" %><%@
page import="com.liferay.portlet.sites.search.UserGroupGroupRoleUserGroupChecker" %><%@
page import="com.liferay.portlet.sites.search.UserGroupRoleRoleChecker" %><%@
page import="com.liferay.portlet.sites.search.UserGroupRoleUserChecker" %><%@
page import="com.liferay.portlet.sitesadmin.search.SiteMembershipChecker" %><%@
page import="com.liferay.portlet.usergroupsadmin.search.UserGroupGroupChecker" %><%@
page import="com.liferay.portlet.usersadmin.search.OrganizationDisplayTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.OrganizationGroupChecker" %><%@
page import="com.liferay.portlet.usersadmin.search.UserDisplayTerms" %>

<%@ include file="/html/portlet/site_memberships/init-ext.jsp" %>
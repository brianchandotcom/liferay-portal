<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.DuplicateUserGroupException" %><%@
page import="com.liferay.portal.RequiredUserGroupException" %><%@
page import="com.liferay.portal.UserGroupNameException" %><%@
page import="com.liferay.portal.security.membershippolicy.UserGroupMembershipPolicyUtil" %><%@
page import="com.liferay.portal.service.permission.UserGroupPermissionUtil" %><%@
page import="com.liferay.portlet.usergroupsadmin.search.UserUserGroupChecker" %>

<%
boolean filterManageableOrganizations = true;

if (permissionChecker.hasPermission(scopeGroupId, User.class.getName(), company.getCompanyId(), ActionKeys.VIEW)) {
	filterManageableOrganizations = false;
}

if (portletName.equals(PortletKeys.MY_ACCOUNT) || permissionChecker.hasPermission(scopeGroupId, Organization.class.getName(), company.getCompanyId(), ActionKeys.VIEW)) {
	filterManageableOrganizations = false;
}

boolean filterManageableUserGroups = true;

if (portletName.equals(PortletKeys.MY_ACCOUNT) || permissionChecker.hasPermission(scopeGroupId, UserGroup.class.getName(), company.getCompanyId(), ActionKeys.VIEW)) {
	filterManageableUserGroups = false;
}
%>

<%@ include file="/html/portlet/user_groups_admin/init-ext.jsp" %>
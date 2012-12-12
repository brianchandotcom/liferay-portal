<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.AddressCityException" %><%@
page import="com.liferay.portal.AddressStreetException" %><%@
page import="com.liferay.portal.AddressZipException" %><%@
page import="com.liferay.portal.CompanyMaxUsersException" %><%@
page import="com.liferay.portal.ContactBirthdayException" %><%@
page import="com.liferay.portal.ContactFirstNameException" %><%@
page import="com.liferay.portal.ContactFullNameException" %><%@
page import="com.liferay.portal.ContactLastNameException" %><%@
page import="com.liferay.portal.DuplicateOpenIdException" %><%@
page import="com.liferay.portal.DuplicateOrganizationException" %><%@
page import="com.liferay.portal.DuplicateUserEmailAddressException" %><%@
page import="com.liferay.portal.DuplicateUserIdException" %><%@
page import="com.liferay.portal.DuplicateUserScreenNameException" %><%@
page import="com.liferay.portal.EmailAddressException" %><%@
page import="com.liferay.portal.GroupFriendlyURLException" %><%@
page import="com.liferay.portal.NoSuchCountryException" %><%@
page import="com.liferay.portal.NoSuchListTypeException" %><%@
page import="com.liferay.portal.NoSuchOrganizationException" %><%@
page import="com.liferay.portal.NoSuchRegionException" %><%@
page import="com.liferay.portal.NoSuchRoleException" %><%@
page import="com.liferay.portal.NoSuchUserGroupException" %><%@
page import="com.liferay.portal.OrganizationNameException" %><%@
page import="com.liferay.portal.OrganizationParentException" %><%@
page import="com.liferay.portal.PhoneNumberException" %><%@
page import="com.liferay.portal.RequiredOrganizationException" %><%@
page import="com.liferay.portal.RequiredUserException" %><%@
page import="com.liferay.portal.ReservedUserEmailAddressException" %><%@
page import="com.liferay.portal.ReservedUserIdException" %><%@
page import="com.liferay.portal.ReservedUserScreenNameException" %><%@
page import="com.liferay.portal.UserEmailAddressException" %><%@
page import="com.liferay.portal.UserIdException" %><%@
page import="com.liferay.portal.UserPasswordException" %><%@
page import="com.liferay.portal.UserPortraitSizeException" %><%@
page import="com.liferay.portal.UserScreenNameException" %><%@
page import="com.liferay.portal.UserSmsException" %><%@
page import="com.liferay.portal.WebsiteURLException" %><%@
page import="com.liferay.portal.service.permission.OrganizationPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.PortalPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.UserPermissionUtil" %><%@
page import="com.liferay.portlet.announcements.model.AnnouncementsDelivery" %><%@
page import="com.liferay.portlet.announcements.model.AnnouncementsEntryConstants" %><%@
page import="com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl" %><%@
page import="com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchFileException" %><%@
page import="com.liferay.portlet.rolesadmin.search.OrganizationRoleChecker" %><%@
page import="com.liferay.portlet.rolesadmin.search.UserGroupRoleChecker" %><%@
page import="com.liferay.portlet.rolesadmin.search.UserRoleChecker" %><%@
page import="com.liferay.portlet.rolesadmin.util.RolesAdminUtil" %><%@
page import="com.liferay.portlet.usergroupsadmin.search.UserGroupGroupChecker" %><%@
page import="com.liferay.portlet.usergroupsadmin.search.UserGroupSearch" %><%@
page import="com.liferay.portlet.usergroupsadmin.search.UserGroupSearchTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.GroupDisplayTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.GroupSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.OrganizationDisplayTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.OrganizationSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.OrganizationSearchTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.UserDisplayTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.UserOrganizationChecker" %><%@
page import="com.liferay.portlet.usersadmin.search.UserSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.UserSearchTerms" %><%@
page import="com.liferay.portlet.usersadmin.util.UsersAdminUtil" %>

<%
boolean showActiveUserSelect = true;

if (!(portletName.equals(PortletKeys.PASSWORD_POLICIES_ADMIN) || portletName.equals(PortletKeys.PORTAL_SETTINGS) || portletName.equals(PortletKeys.ROLES_ADMIN) || portletName.equals(PortletKeys.SITES_ADMIN) || portletName.equals(PortletKeys.USERS_ADMIN))) {
	showActiveUserSelect = false;
}

boolean filterManageableGroups = true;

boolean filterManageableOrganizations = true;

if (permissionChecker.hasPermission(0, Organization.class.getName(), company.getCompanyId(), ActionKeys.VIEW)) {
	filterManageableOrganizations = false;
}

boolean filterManageableRoles = true;
boolean filterManageableUserGroupRoles = true;
boolean filterManageableUserGroups = true;

if (portletName.equals(PortletKeys.MY_ACCOUNT)) {
	filterManageableGroups = false;
	filterManageableRoles = false;
	filterManageableUserGroupRoles = false;
	filterManageableUserGroups = false;
}
else if (permissionChecker.isCompanyAdmin()) {
	filterManageableGroups = false;
	filterManageableOrganizations = false;
	filterManageableUserGroups = false;
}
%>

<%@ include file="/html/portlet/users_admin/init-ext.jsp" %>
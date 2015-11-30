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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.AddressCityException" %><%@
page import="com.liferay.portal.AddressStreetException" %><%@
page import="com.liferay.portal.AddressZipException" %><%@
page import="com.liferay.portal.CompanyMaxUsersException" %><%@
page import="com.liferay.portal.ContactBirthdayException" %><%@
page import="com.liferay.portal.DuplicateOpenIdException" %><%@
page import="com.liferay.portal.DuplicateOrganizationException" %><%@
page import="com.liferay.portal.EmailAddressException" %><%@
page import="com.liferay.portal.GroupFriendlyURLException" %><%@
page import="com.liferay.portal.NoSuchCountryException" %><%@
page import="com.liferay.portal.NoSuchListTypeException" %><%@
page import="com.liferay.portal.NoSuchOrganizationException" %><%@
page import="com.liferay.portal.NoSuchRegionException" %><%@
page import="com.liferay.portal.NoSuchRoleException" %><%@
page import="com.liferay.portal.NoSuchUserException" %><%@
page import="com.liferay.portal.NoSuchUserGroupException" %><%@
page import="com.liferay.portal.OrganizationNameException" %><%@
page import="com.liferay.portal.OrganizationParentException" %><%@
page import="com.liferay.portal.PhoneNumberException" %><%@
page import="com.liferay.portal.PhoneNumberExtensionException" %><%@
page import="com.liferay.portal.RequiredOrganizationException" %><%@
page import="com.liferay.portal.RequiredUserException" %><%@
page import="com.liferay.portal.UserEmailAddressException" %><%@
page import="com.liferay.portal.UserFieldException" %><%@
page import="com.liferay.portal.UserIdException" %><%@
page import="com.liferay.portal.UserLockoutException" %><%@
page import="com.liferay.portal.UserPasswordException" %><%@
page import="com.liferay.portal.UserScreenNameException" %><%@
page import="com.liferay.portal.UserSmsException" %><%@
page import="com.liferay.portal.WebsiteURLException" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanPropertiesUtil" %><%@
page import="com.liferay.portal.kernel.configuration.Filter" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.RowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.LocalizationUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.model.Address" %><%@
page import="com.liferay.portal.model.Contact" %><%@
page import="com.liferay.portal.model.EmailAddress" %><%@
page import="com.liferay.portal.model.Group" %><%@
page import="com.liferay.portal.model.Layout" %><%@
page import="com.liferay.portal.model.LayoutConstants" %><%@
page import="com.liferay.portal.model.LayoutSet" %><%@
page import="com.liferay.portal.model.LayoutSetPrototype" %><%@
page import="com.liferay.portal.model.ListTypeConstants" %><%@
page import="com.liferay.portal.model.ModelHintsConstants" %><%@
page import="com.liferay.portal.model.OrgLabor" %><%@
page import="com.liferay.portal.model.Organization" %><%@
page import="com.liferay.portal.model.OrganizationConstants" %><%@
page import="com.liferay.portal.model.PasswordPolicy" %><%@
page import="com.liferay.portal.model.Phone" %><%@
page import="com.liferay.portal.model.ResourceConstants" %><%@
page import="com.liferay.portal.model.Role" %><%@
page import="com.liferay.portal.model.RoleConstants" %><%@
page import="com.liferay.portal.model.User" %><%@
page import="com.liferay.portal.model.UserConstants" %><%@
page import="com.liferay.portal.model.UserGroup" %><%@
page import="com.liferay.portal.model.UserGroupGroupRole" %><%@
page import="com.liferay.portal.model.UserGroupRole" %><%@
page import="com.liferay.portal.model.Website" %><%@
page import="com.liferay.portal.model.impl.AddressImpl" %><%@
page import="com.liferay.portal.model.impl.EmailAddressImpl" %><%@
page import="com.liferay.portal.model.impl.OrgLaborImpl" %><%@
page import="com.liferay.portal.model.impl.PhoneImpl" %><%@
page import="com.liferay.portal.model.impl.WebsiteImpl" %><%@
page import="com.liferay.portal.security.auth.ScreenNameValidator" %><%@
page import="com.liferay.portal.security.auth.ScreenNameValidatorFactory" %><%@
page import="com.liferay.portal.security.membershippolicy.OrganizationMembershipPolicyUtil" %><%@
page import="com.liferay.portal.security.membershippolicy.RoleMembershipPolicyUtil" %><%@
page import="com.liferay.portal.security.membershippolicy.SiteMembershipPolicyUtil" %><%@
page import="com.liferay.portal.security.membershippolicy.UserGroupMembershipPolicyUtil" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.service.AddressServiceUtil" %><%@
page import="com.liferay.portal.service.EmailAddressServiceUtil" %><%@
page import="com.liferay.portal.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutSetLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutSetPrototypeServiceUtil" %><%@
page import="com.liferay.portal.service.OrgLaborServiceUtil" %><%@
page import="com.liferay.portal.service.OrganizationLocalServiceUtil" %><%@
page import="com.liferay.portal.service.OrganizationServiceUtil" %><%@
page import="com.liferay.portal.service.PasswordPolicyLocalServiceUtil" %><%@
page import="com.liferay.portal.service.PhoneServiceUtil" %><%@
page import="com.liferay.portal.service.RoleLocalServiceUtil" %><%@
page import="com.liferay.portal.service.UserGroupGroupRoleLocalServiceUtil" %><%@
page import="com.liferay.portal.service.UserGroupLocalServiceUtil" %><%@
page import="com.liferay.portal.service.UserGroupRoleLocalServiceUtil" %><%@
page import="com.liferay.portal.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.service.WebsiteServiceUtil" %><%@
page import="com.liferay.portal.service.permission.GroupPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.OrganizationPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.PortalPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.PortletPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.UserPermissionUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.util.PropsUtil" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portal.webserver.WebServerServletTokenUtil" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.admin.util.PortalDirectoryApplicationType" %><%@
page import="com.liferay.portlet.admin.util.PortalMyAccountApplicationType" %><%@
page import="com.liferay.portlet.announcements.model.AnnouncementsDelivery" %><%@
page import="com.liferay.portlet.announcements.model.AnnouncementsEntryConstants" %><%@
page import="com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl" %><%@
page import="com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalServiceUtil" %><%@
page import="com.liferay.portlet.rolesadmin.search.GroupRoleChecker" %><%@
page import="com.liferay.portlet.rolesadmin.search.OrganizationRoleChecker" %><%@
page import="com.liferay.portlet.rolesadmin.search.UserGroupRoleChecker" %><%@
page import="com.liferay.portlet.rolesadmin.search.UserRoleChecker" %><%@
page import="com.liferay.portlet.rolesadmin.util.RolesAdminUtil" %><%@
page import="com.liferay.portlet.usergroupsadmin.search.UserGroupDisplayTerms" %><%@
page import="com.liferay.portlet.usergroupsadmin.search.UserGroupGroupChecker" %><%@
page import="com.liferay.portlet.usergroupsadmin.search.UserGroupSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.GroupSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.GroupSearchTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.OrganizationSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.OrganizationSearchTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.UserDisplayTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.UserOrganizationChecker" %><%@
page import="com.liferay.portlet.usersadmin.search.UserSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.UserSearchTerms" %><%@
page import="com.liferay.portlet.usersadmin.util.UsersAdmin" %><%@
page import="com.liferay.portlet.usersadmin.util.UsersAdminUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %><%@
page import="com.liferay.taglib.search.SearchEntry" %><%@
page import="com.liferay.users.admin.constants.UsersAdminPortletKeys" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Calendar" %><%@
page import="java.util.Collections" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.LinkedHashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

boolean filterManageableGroups = true;

boolean filterManageableOrganizations = true;

if (permissionChecker.hasPermission(0, Organization.class.getName(), company.getCompanyId(), ActionKeys.VIEW)) {
	filterManageableOrganizations = false;
}

boolean filterManageableRoles = true;
boolean filterManageableUserGroupRoles = true;
boolean filterManageableUserGroups = true;

String myAccountPortletId = PortletProviderUtil.getPortletId(PortalMyAccountApplicationType.MyAccount.CLASS_NAME, PortletProvider.Action.VIEW);

if (portletName.equals(myAccountPortletId)) {
	filterManageableGroups = false;
	filterManageableOrganizations = false;
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

<%@ include file="/init-ext.jsp" %>
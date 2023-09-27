/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class UsersAdminUtil {

	public static void addPortletBreadcrumbEntries(
			Organization organization, HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws Exception {

		_usersAdmin.addPortletBreadcrumbEntries(
			organization, httpServletRequest, renderResponse);
	}

	public static long[] addRequiredRoles(long userId, long[] roleIds)
		throws PortalException {

		return _usersAdmin.addRequiredRoles(userId, roleIds);
	}

	public static long[] addRequiredRoles(User user, long[] roleIds)
		throws PortalException {

		return _usersAdmin.addRequiredRoles(user, roleIds);
	}

	public static List<Role> filterGroupRoles(
			PermissionChecker permissionChecker, long groupId, List<Role> roles)
		throws PortalException {

		return _usersAdmin.filterGroupRoles(permissionChecker, groupId, roles);
	}

	public static List<Group> filterGroups(
			PermissionChecker permissionChecker, List<Group> groups)
		throws PortalException {

		return _usersAdmin.filterGroups(permissionChecker, groups);
	}

	public static List<Organization> filterOrganizations(
			PermissionChecker permissionChecker,
			List<Organization> organizations)
		throws PortalException {

		return _usersAdmin.filterOrganizations(
			permissionChecker, organizations);
	}

	public static List<Role> filterRoles(
		PermissionChecker permissionChecker, List<Role> roles) {

		return _usersAdmin.filterRoles(permissionChecker, roles);
	}

	public static long[] filterUnsetGroupUserIds(
			PermissionChecker permissionChecker, long groupId, long[] userIds)
		throws PortalException {

		return _usersAdmin.filterUnsetGroupUserIds(
			permissionChecker, groupId, userIds);
	}

	public static long[] filterUnsetOrganizationUserIds(
			PermissionChecker permissionChecker, long organizationId,
			long[] userIds)
		throws PortalException {

		return _usersAdmin.filterUnsetOrganizationUserIds(
			permissionChecker, organizationId, userIds);
	}

	public static List<UserGroupRole> filterUserGroupRoles(
			PermissionChecker permissionChecker,
			List<UserGroupRole> userGroupRoles)
		throws PortalException {

		return _usersAdmin.filterUserGroupRoles(
			permissionChecker, userGroupRoles);
	}

	public static List<UserGroup> filterUserGroups(
		PermissionChecker permissionChecker, List<UserGroup> userGroups) {

		return _usersAdmin.filterUserGroups(permissionChecker, userGroups);
	}

	public static List<Address> getAddresses(ActionRequest actionRequest) {
		return _usersAdmin.getAddresses(actionRequest);
	}

	public static List<Address> getAddresses(
		ActionRequest actionRequest, List<Address> defaultAddresses) {

		return _usersAdmin.getAddresses(actionRequest, defaultAddresses);
	}

	public static List<EmailAddress> getEmailAddresses(
		ActionRequest actionRequest) {

		return _usersAdmin.getEmailAddresses(actionRequest);
	}

	public static List<EmailAddress> getEmailAddresses(
		ActionRequest actionRequest, List<EmailAddress> defaultEmailAddresses) {

		return _usersAdmin.getEmailAddresses(
			actionRequest, defaultEmailAddresses);
	}

	public static long[] getGroupIds(PortletRequest portletRequest)
		throws PortalException {

		return _usersAdmin.getGroupIds(portletRequest);
	}

	public static OrderByComparator<Group> getGroupOrderByComparator(
		String orderByCol, String orderByType) {

		return _usersAdmin.getGroupOrderByComparator(orderByCol, orderByType);
	}

	public static Long[] getOrganizationIds(List<Organization> organizations) {
		return _usersAdmin.getOrganizationIds(organizations);
	}

	public static long[] getOrganizationIds(PortletRequest portletRequest)
		throws PortalException {

		return _usersAdmin.getOrganizationIds(portletRequest);
	}

	public static OrderByComparator<Organization>
		getOrganizationOrderByComparator(
			String orderByCol, String orderByType) {

		return _usersAdmin.getOrganizationOrderByComparator(
			orderByCol, orderByType);
	}

	public static List<Organization> getOrganizations(Hits hits)
		throws PortalException {

		return _usersAdmin.getOrganizations(hits);
	}

	public static List<OrgLabor> getOrgLabors(ActionRequest actionRequest) {
		return _usersAdmin.getOrgLabors(actionRequest);
	}

	public static List<Phone> getPhones(ActionRequest actionRequest) {
		return _usersAdmin.getPhones(actionRequest);
	}

	public static List<Phone> getPhones(
		ActionRequest actionRequest, List<Phone> defaultPhones) {

		return _usersAdmin.getPhones(actionRequest, defaultPhones);
	}

	public static long[] getRoleIds(PortletRequest portletRequest)
		throws PortalException {

		return _usersAdmin.getRoleIds(portletRequest);
	}

	public static OrderByComparator<Role> getRoleOrderByComparator(
		String orderByCol, String orderByType) {

		return _usersAdmin.getRoleOrderByComparator(orderByCol, orderByType);
	}

	public static <T> String getUserColumnText(
		Locale locale, List<? extends T> list, Accessor<T, String> accessor,
		int count) {

		return _usersAdmin.getUserColumnText(locale, list, accessor, count);
	}

	public static long[] getUserGroupIds(PortletRequest portletRequest)
		throws PortalException {

		return _usersAdmin.getUserGroupIds(portletRequest);
	}

	public static OrderByComparator<UserGroup> getUserGroupOrderByComparator(
		String orderByCol, String orderByType) {

		return _usersAdmin.getUserGroupOrderByComparator(
			orderByCol, orderByType);
	}

	public static List<UserGroupRole> getUserGroupRoles(
			PortletRequest portletRequest)
		throws PortalException {

		return _usersAdmin.getUserGroupRoles(portletRequest);
	}

	public static List<UserGroup> getUserGroups(Hits hits)
		throws PortalException {

		return _usersAdmin.getUserGroups(hits);
	}

	public static OrderByComparator<User> getUserOrderByComparator(
		String orderByCol, String orderByType) {

		return _usersAdmin.getUserOrderByComparator(orderByCol, orderByType);
	}

	public static List<User> getUsers(Hits hits) throws PortalException {
		return _usersAdmin.getUsers(hits);
	}

	public static UsersAdmin getUsersAdmin() {
		return _usersAdmin;
	}

	public static List<Website> getWebsites(ActionRequest actionRequest) {
		return _usersAdmin.getWebsites(actionRequest);
	}

	public static List<Website> getWebsites(
		ActionRequest actionRequest, List<Website> defaultWebsites) {

		return _usersAdmin.getWebsites(actionRequest, defaultWebsites);
	}

	public static boolean hasUpdateFieldPermission(
			PermissionChecker permissionChecker, User updatingUser,
			User updatedUser, String field)
		throws PortalException {

		return _usersAdmin.hasUpdateFieldPermission(
			permissionChecker, updatingUser, updatedUser, field);
	}

	public static long[] removeRequiredRoles(long userId, long[] roleIds)
		throws PortalException {

		return _usersAdmin.removeRequiredRoles(userId, roleIds);
	}

	public static long[] removeRequiredRoles(User user, long[] roleIds)
		throws PortalException {

		return _usersAdmin.removeRequiredRoles(user, roleIds);
	}

	public static void updateAddresses(
			String className, long classPK, List<Address> addresses)
		throws PortalException {

		_usersAdmin.updateAddresses(className, classPK, addresses);
	}

	public static void updateEmailAddresses(
			String className, long classPK, List<EmailAddress> emailAddresses)
		throws PortalException {

		_usersAdmin.updateEmailAddresses(className, classPK, emailAddresses);
	}

	public static void updateOrgLabors(long classPK, List<OrgLabor> orgLabors)
		throws PortalException {

		_usersAdmin.updateOrgLabors(classPK, orgLabors);
	}

	public static void updatePhones(
			String className, long classPK, List<Phone> phones)
		throws PortalException {

		_usersAdmin.updatePhones(className, classPK, phones);
	}

	public static void updateWebsites(
			String className, long classPK, List<Website> websites)
		throws PortalException {

		_usersAdmin.updateWebsites(className, classPK, websites);
	}

	public void setUsersAdmin(UsersAdmin usersAdmin) {
		_usersAdmin = usersAdmin;
	}

	private static UsersAdmin _usersAdmin;

}
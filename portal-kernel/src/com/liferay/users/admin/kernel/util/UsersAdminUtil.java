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

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             UsersAdmin#CUSTOM_QUESTION}
	 */
	@Deprecated
	public static final String CUSTOM_QUESTION = "write-my-own-question";

	public static void addPortletBreadcrumbEntries(
			Organization organization, HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws Exception {

		getUsersAdmin().addPortletBreadcrumbEntries(
			organization, httpServletRequest, renderResponse);
	}

	public static long[] addRequiredRoles(long userId, long[] roleIds)
		throws PortalException {

		return getUsersAdmin().addRequiredRoles(userId, roleIds);
	}

	public static long[] addRequiredRoles(User user, long[] roleIds)
		throws PortalException {

		return getUsersAdmin().addRequiredRoles(user, roleIds);
	}

	public static List<Role> filterGroupRoles(
			PermissionChecker permissionChecker, long groupId, List<Role> roles)
		throws PortalException {

		return getUsersAdmin().filterGroupRoles(
			permissionChecker, groupId, roles);
	}

	public static List<Group> filterGroups(
			PermissionChecker permissionChecker, List<Group> groups)
		throws PortalException {

		return getUsersAdmin().filterGroups(permissionChecker, groups);
	}

	public static List<Organization> filterOrganizations(
			PermissionChecker permissionChecker,
			List<Organization> organizations)
		throws PortalException {

		return getUsersAdmin().filterOrganizations(
			permissionChecker, organizations);
	}

	public static List<Role> filterRoles(
		PermissionChecker permissionChecker, List<Role> roles) {

		return getUsersAdmin().filterRoles(permissionChecker, roles);
	}

	public static long[] filterUnsetGroupUserIds(
			PermissionChecker permissionChecker, long groupId, long[] userIds)
		throws PortalException {

		return getUsersAdmin().filterUnsetGroupUserIds(
			permissionChecker, groupId, userIds);
	}

	public static long[] filterUnsetOrganizationUserIds(
			PermissionChecker permissionChecker, long organizationId,
			long[] userIds)
		throws PortalException {

		return getUsersAdmin().filterUnsetOrganizationUserIds(
			permissionChecker, organizationId, userIds);
	}

	public static List<UserGroupRole> filterUserGroupRoles(
			PermissionChecker permissionChecker,
			List<UserGroupRole> userGroupRoles)
		throws PortalException {

		return getUsersAdmin().filterUserGroupRoles(
			permissionChecker, userGroupRoles);
	}

	public static List<UserGroup> filterUserGroups(
		PermissionChecker permissionChecker, List<UserGroup> userGroups) {

		return getUsersAdmin().filterUserGroups(permissionChecker, userGroups);
	}

	public static List<Address> getAddresses(ActionRequest actionRequest) {
		return getUsersAdmin().getAddresses(actionRequest);
	}

	public static List<Address> getAddresses(
		ActionRequest actionRequest, List<Address> defaultAddresses) {

		return getUsersAdmin().getAddresses(actionRequest, defaultAddresses);
	}

	public static List<EmailAddress> getEmailAddresses(
		ActionRequest actionRequest) {

		return getUsersAdmin().getEmailAddresses(actionRequest);
	}

	public static List<EmailAddress> getEmailAddresses(
		ActionRequest actionRequest, List<EmailAddress> defaultEmailAddresses) {

		return getUsersAdmin().getEmailAddresses(
			actionRequest, defaultEmailAddresses);
	}

	public static long[] getGroupIds(PortletRequest portletRequest)
		throws PortalException {

		return getUsersAdmin().getGroupIds(portletRequest);
	}

	public static OrderByComparator<Group> getGroupOrderByComparator(
		String orderByCol, String orderByType) {

		return getUsersAdmin().getGroupOrderByComparator(
			orderByCol, orderByType);
	}

	public static Long[] getOrganizationIds(List<Organization> organizations) {
		return getUsersAdmin().getOrganizationIds(organizations);
	}

	public static long[] getOrganizationIds(PortletRequest portletRequest)
		throws PortalException {

		return getUsersAdmin().getOrganizationIds(portletRequest);
	}

	public static OrderByComparator<Organization>
		getOrganizationOrderByComparator(
			String orderByCol, String orderByType) {

		return getUsersAdmin().getOrganizationOrderByComparator(
			orderByCol, orderByType);
	}

	public static List<Organization> getOrganizations(Hits hits)
		throws PortalException {

		return getUsersAdmin().getOrganizations(hits);
	}

	public static List<OrgLabor> getOrgLabors(ActionRequest actionRequest) {
		return getUsersAdmin().getOrgLabors(actionRequest);
	}

	public static List<Phone> getPhones(ActionRequest actionRequest) {
		return getUsersAdmin().getPhones(actionRequest);
	}

	public static List<Phone> getPhones(
		ActionRequest actionRequest, List<Phone> defaultPhones) {

		return getUsersAdmin().getPhones(actionRequest, defaultPhones);
	}

	public static long[] getRoleIds(PortletRequest portletRequest)
		throws PortalException {

		return getUsersAdmin().getRoleIds(portletRequest);
	}

	public static OrderByComparator<Role> getRoleOrderByComparator(
		String orderByCol, String orderByType) {

		return getUsersAdmin().getRoleOrderByComparator(
			orderByCol, orderByType);
	}

	public static <T> String getUserColumnText(
		Locale locale, List<? extends T> list, Accessor<T, String> accessor,
		int count) {

		return getUsersAdmin().getUserColumnText(locale, list, accessor, count);
	}

	public static long[] getUserGroupIds(PortletRequest portletRequest)
		throws PortalException {

		return getUsersAdmin().getUserGroupIds(portletRequest);
	}

	public static OrderByComparator<UserGroup> getUserGroupOrderByComparator(
		String orderByCol, String orderByType) {

		return getUsersAdmin().getUserGroupOrderByComparator(
			orderByCol, orderByType);
	}

	public static List<UserGroupRole> getUserGroupRoles(
			PortletRequest portletRequest)
		throws PortalException {

		return getUsersAdmin().getUserGroupRoles(portletRequest);
	}

	public static List<UserGroup> getUserGroups(Hits hits)
		throws PortalException {

		return getUsersAdmin().getUserGroups(hits);
	}

	public static OrderByComparator<User> getUserOrderByComparator(
		String orderByCol, String orderByType) {

		return getUsersAdmin().getUserOrderByComparator(
			orderByCol, orderByType);
	}

	public static List<User> getUsers(Hits hits) throws PortalException {
		return getUsersAdmin().getUsers(hits);
	}

	public static UsersAdmin getUsersAdmin() {
		return _usersAdmin;
	}

	public static List<Website> getWebsites(ActionRequest actionRequest) {
		return getUsersAdmin().getWebsites(actionRequest);
	}

	public static List<Website> getWebsites(
		ActionRequest actionRequest, List<Website> defaultWebsites) {

		return getUsersAdmin().getWebsites(actionRequest, defaultWebsites);
	}

	public static boolean hasUpdateFieldPermission(
			PermissionChecker permissionChecker, User updatingUser,
			User updatedUser, String field)
		throws PortalException {

		return getUsersAdmin().hasUpdateFieldPermission(
			permissionChecker, updatingUser, updatedUser, field);
	}

	public static long[] removeRequiredRoles(long userId, long[] roleIds)
		throws PortalException {

		return getUsersAdmin().removeRequiredRoles(userId, roleIds);
	}

	public static long[] removeRequiredRoles(User user, long[] roleIds)
		throws PortalException {

		return getUsersAdmin().removeRequiredRoles(user, roleIds);
	}

	public static void updateAddresses(
			String className, long classPK, List<Address> addresses)
		throws PortalException {

		getUsersAdmin().updateAddresses(className, classPK, addresses);
	}

	public static void updateEmailAddresses(
			String className, long classPK, List<EmailAddress> emailAddresses)
		throws PortalException {

		getUsersAdmin().updateEmailAddresses(
			className, classPK, emailAddresses);
	}

	public static void updateOrgLabors(long classPK, List<OrgLabor> orgLabors)
		throws PortalException {

		getUsersAdmin().updateOrgLabors(classPK, orgLabors);
	}

	public static void updatePhones(
			String className, long classPK, List<Phone> phones)
		throws PortalException {

		getUsersAdmin().updatePhones(className, classPK, phones);
	}

	public static void updateWebsites(
			String className, long classPK, List<Website> websites)
		throws PortalException {

		getUsersAdmin().updateWebsites(className, classPK, websites);
	}

	public void setUsersAdmin(UsersAdmin usersAdmin) {
		_usersAdmin = usersAdmin;
	}

	private static UsersAdmin _usersAdmin;

}
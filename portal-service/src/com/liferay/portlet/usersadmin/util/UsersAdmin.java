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

package com.liferay.portlet.usersadmin.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.permission.PermissionChecker;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public interface UsersAdmin {

	public static final String CUSTOM_QUESTION = "write-my-own-question";

	public static final String[] ORGANIZATION_SELECTED_FIELD_NAMES =
		{Field.COMPANY_ID, Field.ORGANIZATION_ID, Field.UID};

	public void addPortletBreadcrumbEntries(
			Organization organization, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception;

	public long[] addRequiredRoles(long userId, long[] roleIds)
		throws PortalException, SystemException;

	public long[] addRequiredRoles(User user, long[] roleIds)
		throws PortalException, SystemException;

	public List<Role> filterGroupRoles(
			PermissionChecker permissionChecker, long groupId, List<Role> roles)
		throws PortalException, SystemException;

	public List<Group> filterGroups(
			PermissionChecker permissionChecker, List<Group> groups)
		throws PortalException, SystemException;

	public List<Organization> filterOrganizations(
			PermissionChecker permissionChecker,
			List<Organization> organizations)
		throws PortalException, SystemException;

	public List<Role> filterRoles(
		PermissionChecker permissionChecker, List<Role> roles);

	public long[] filterUnsetGroupUserIds(
			PermissionChecker permissionChecker, long groupId, long[] userIds)
		throws PortalException, SystemException;

	public long[] filterUnsetOrganizationUserIds(
			PermissionChecker permissionChecker, long organizationId,
			long[] userIds)
		throws PortalException, SystemException;

	public List<UserGroupRole> filterUserGroupRoles(
			PermissionChecker permissionChecker,
			List<UserGroupRole> userGroupRoles)
		throws PortalException, SystemException;

	public List<UserGroup> filterUserGroups(
		PermissionChecker permissionChecker, List<UserGroup> userGroups);

	public List<Address> getAddresses(ActionRequest actionRequest);

	public List<Address> getAddresses(
		ActionRequest actionRequest, List<Address> defaultAddresses);

	public List<EmailAddress> getEmailAddresses(ActionRequest actionRequest);

	public List<EmailAddress> getEmailAddresses(
		ActionRequest actionRequest, List<EmailAddress> defaultEmailAddresses);

	public OrderByComparator getGroupOrderByComparator(
		String orderByCol, String orderByType);

	public Long[] getOrganizationIds(List<Organization> organizations);

	public OrderByComparator getOrganizationOrderByComparator(
		String orderByCol, String orderByType);

	public List<Organization> getOrganizations(Hits hits)
		throws PortalException, SystemException;

	public List<OrgLabor> getOrgLabors(ActionRequest actionRequest);

	public List<Phone> getPhones(ActionRequest actionRequest);

	public List<Phone> getPhones(
		ActionRequest actionRequest, List<Phone> defaultPhones);

	public OrderByComparator getRoleOrderByComparator(
		String orderByCol, String orderByType);

	public OrderByComparator getUserGroupOrderByComparator(
		String orderByCol, String orderByType);

	public List<UserGroupRole> getUserGroupRoles(PortletRequest portletRequest)
		throws PortalException, SystemException;

	public List<UserGroup> getUserGroups(Hits hits)
		throws PortalException, SystemException;

	public OrderByComparator getUserOrderByComparator(
		String orderByCol, String orderByType);

	public List<User> getUsers(Hits hits)
		throws PortalException, SystemException;

	public List<Website> getWebsites(ActionRequest actionRequest);

	public List<Website> getWebsites(
		ActionRequest actionRequest, List<Website> defaultWebsites);

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #hasUpdateFieldPermission(User, String)}
	 */
	@Deprecated
	public boolean hasUpdateEmailAddress(
			PermissionChecker permissionChecker, User user)
		throws PortalException, SystemException;

	public boolean hasUpdateFieldPermission(User user, String field)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #hasUpdateFieldPermission(User, String)}
	 */
	@Deprecated
	public boolean hasUpdateScreenName(
			PermissionChecker permissionChecker, User user)
		throws PortalException, SystemException;

	public long[] removeRequiredRoles(long userId, long[] roleIds)
		throws PortalException, SystemException;

	public long[] removeRequiredRoles(User user, long[] roleIds)
		throws PortalException, SystemException;

	public void updateAddresses(
			String className, long classPK, List<Address> addresses)
		throws PortalException, SystemException;

	public void updateEmailAddresses(
			String className, long classPK, List<EmailAddress> emailAddresses)
		throws PortalException, SystemException;

	public void updateOrgLabors(long classPK, List<OrgLabor> orgLabors)
		throws PortalException, SystemException;

	public void updatePhones(String className, long classPK, List<Phone> phones)
		throws PortalException, SystemException;

	public void updateWebsites(
			String className, long classPK, List<Website> websites)
		throws PortalException, SystemException;

}
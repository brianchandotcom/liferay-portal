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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
@ProviderType
public interface UsersAdmin {

	public void addPortletBreadcrumbEntries(
			Organization organization, HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws Exception;

	public long[] addRequiredRoles(long userId, long[] roleIds)
		throws PortalException;

	public long[] addRequiredRoles(User user, long[] roleIds)
		throws PortalException;

	public List<Role> filterGroupRoles(
			PermissionChecker permissionChecker, long groupId, List<Role> roles)
		throws PortalException;

	public List<Group> filterGroups(
			PermissionChecker permissionChecker, List<Group> groups)
		throws PortalException;

	public List<Organization> filterOrganizations(
			PermissionChecker permissionChecker,
			List<Organization> organizations)
		throws PortalException;

	public List<Role> filterRoles(
		PermissionChecker permissionChecker, List<Role> roles);

	public long[] filterUnsetGroupUserIds(
			PermissionChecker permissionChecker, long groupId, long[] userIds)
		throws PortalException;

	public long[] filterUnsetOrganizationUserIds(
			PermissionChecker permissionChecker, long organizationId,
			long[] userIds)
		throws PortalException;

	public List<UserGroupRole> filterUserGroupRoles(
			PermissionChecker permissionChecker,
			List<UserGroupRole> userGroupRoles)
		throws PortalException;

	public List<UserGroup> filterUserGroups(
		PermissionChecker permissionChecker, List<UserGroup> userGroups);

	public List<Address> getAddresses(ActionRequest actionRequest);

	public List<Address> getAddresses(
		ActionRequest actionRequest, List<Address> defaultAddresses);

	public List<EmailAddress> getEmailAddresses(ActionRequest actionRequest);

	public List<EmailAddress> getEmailAddresses(
		ActionRequest actionRequest, List<EmailAddress> defaultEmailAddresses);

	public long[] getGroupIds(PortletRequest portletRequest)
		throws PortalException;

	public OrderByComparator<Group> getGroupOrderByComparator(
		String orderByCol, String orderByType);

	public Long[] getOrganizationIds(List<Organization> organizations);

	public long[] getOrganizationIds(PortletRequest portletRequest)
		throws PortalException;

	public OrderByComparator<Organization> getOrganizationOrderByComparator(
		String orderByCol, String orderByType);

	public List<Organization> getOrganizations(Hits hits)
		throws PortalException;

	public List<OrgLabor> getOrgLabors(ActionRequest actionRequest);

	public List<Phone> getPhones(ActionRequest actionRequest);

	public List<Phone> getPhones(
		ActionRequest actionRequest, List<Phone> defaultPhones);

	public long[] getRoleIds(PortletRequest portletRequest)
		throws PortalException;

	public OrderByComparator<Role> getRoleOrderByComparator(
		String orderByCol, String orderByType);

	public <T> String getUserColumnText(
		Locale locale, List<? extends T> list, Accessor<T, String> accessor,
		int count);

	public long[] getUserGroupIds(PortletRequest portletRequest)
		throws PortalException;

	public OrderByComparator<UserGroup> getUserGroupOrderByComparator(
		String orderByCol, String orderByType);

	public List<UserGroupRole> getUserGroupRoles(PortletRequest portletRequest)
		throws PortalException;

	public List<UserGroup> getUserGroups(Hits hits) throws PortalException;

	public OrderByComparator<User> getUserOrderByComparator(
		String orderByCol, String orderByType);

	public List<User> getUsers(Hits hits) throws PortalException;

	public List<Website> getWebsites(ActionRequest actionRequest);

	public List<Website> getWebsites(
		ActionRequest actionRequest, List<Website> defaultWebsites);

	public boolean hasUpdateFieldPermission(
			PermissionChecker permissionChecker, User updatingUser,
			User updatedUser, String field)
		throws PortalException;

	public long[] removeRequiredRoles(long userId, long[] roleIds)
		throws PortalException;

	public long[] removeRequiredRoles(User user, long[] roleIds)
		throws PortalException;

	public void updateAddresses(
			String className, long classPK, List<Address> addresses)
		throws PortalException;

	public void updateEmailAddresses(
			String className, long classPK, List<EmailAddress> emailAddresses)
		throws PortalException;

	public void updateOrgLabors(long classPK, List<OrgLabor> orgLabors)
		throws PortalException;

	public void updatePhones(String className, long classPK, List<Phone> phones)
		throws PortalException;

	public void updateWebsites(
			String className, long classPK, List<Website> websites)
		throws PortalException;

}
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

package com.liferay.portal.security.permission;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.Resource;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.security.permission.ResourceBlockIdsBag;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.security.permission.UserBagFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutSetPrototypePermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Wesley Gong
 * @author Connor McKay
 */
public class AdvancedPermissionChecker extends BasePermissionChecker {

	@Override
	public AdvancedPermissionChecker clone() {
		return new AdvancedPermissionChecker();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public ResourceBlockIdsBag getGuestResourceBlockIdsBag(
			long companyId, long groupId, String name)
		throws Exception {

		return null;
	}

	@Override
	public long[] getGuestUserRoleIds() {
		long[] roleIds = PermissionCacheUtil.getUserGroupRoleIds(
			defaultUserId, _guestGroupId);

		if (roleIds != null) {
			return roleIds;
		}

		try {
			List<Role> roles = RoleLocalServiceUtil.getUserRelatedRoles(
				defaultUserId, _guestGroupId);

			// Only use the guest group for deriving the roles for
			// unauthenticated users. Do not add the group to the permission bag
			// as this implies group membership which is incorrect in the case
			// of unauthenticated users.

			roleIds = ListUtil.toLongArray(roles, Role.ROLE_ID_ACCESSOR);

			Arrays.sort(roleIds);

			PermissionCacheUtil.putUserGroupRoleIds(
				defaultUserId, _guestGroupId, roleIds);
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserGroupRoleIds(
				defaultUserId, _guestGroupId);

			throw e;
		}

		return roleIds;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public List<Long> getOwnerResourceBlockIds(
		long companyId, long groupId, String name, String actionId) {

		return Collections.emptyList();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public ResourceBlockIdsBag getOwnerResourceBlockIdsBag(
		long companyId, long groupId, String name) {

		return null;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public List<Long> getResourceBlockIds(
		long companyId, long groupId, long userId, String name,
		String actionId) {

		return Collections.emptyList();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public ResourceBlockIdsBag getResourceBlockIdsBag(
			long companyId, long groupId, String name, long[] roleIds)
		throws Exception {

		return null;
	}

	@Override
	public long[] getRoleIds(long userId, long groupId) {
		try {
			return doGetRoleIds(userId, groupId);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return PermissionChecker.DEFAULT_ROLE_IDS;
		}
	}

	@Override
	public UserBag getUserBag() throws PortalException {
		return UserBagFactoryUtil.create(getUserId());
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, String primKey, long ownerId,
		String actionId) {

		if (ownerId != getUserId()) {
			return false;
		}

		boolean ownerIsDefaultUser = false;

		if (ownerId == defaultUserId) {
			ownerIsDefaultUser = true;
		}

		if (ownerIsDefaultUser) {
			List<String> guestUnsupportedActions;

			if (name.indexOf(CharPool.PERIOD) != -1) {
				guestUnsupportedActions =
					ResourceActionsUtil.getModelResourceGuestUnsupportedActions(
						name);
			}
			else {
				guestUnsupportedActions =
					ResourceActionsUtil.
						getPortletResourceGuestUnsupportedActions(name);
			}

			if (guestUnsupportedActions.contains(actionId)) {
				return false;
			}
		}

		try {
			long ownerRoleId = getOwnerRoleId();

			if (ownerIsDefaultUser) {
				Role guestRole = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.GUEST);

				ownerRoleId = guestRole.getRoleId();
			}

			return ResourcePermissionLocalServiceUtil.hasResourcePermission(
				companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey,
				ownerRoleId, actionId);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return false;
	}

	@Override
	public boolean hasPermission(
		Group group, String name, String primKey, String actionId) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		long groupId = 0;

		try {
			if (group != null) {

				// If the group is a scope group for a layout, check the
				// original group.

				if (group.isLayout()) {
					Layout layout = LayoutLocalServiceUtil.getLayout(
						group.getClassPK());

					group = layout.getGroup();
				}
				else if (group.isUserPersonalSite()) {
					return false;
				}

				// If the group is a personal site, check the "User Personal
				// Site" group.

				if (group.isUser() && (group.getClassPK() == getUserId())) {
					group = GroupLocalServiceUtil.getGroup(
						getCompanyId(), GroupConstants.USER_PERSONAL_SITE);
				}

				groupId = group.getGroupId();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		long[] roleIds = getRoleIds(getUserId(), groupId);

		Boolean value = PermissionCacheUtil.getPermission(
			groupId, name, primKey, roleIds, actionId);

		if (value != null) {
			return value;
		}

		try {
			value = _hasPermissionImpl(group, name, primKey, roleIds, actionId);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Checking permission for ", String.valueOf(groupId),
						" ", name, " ", primKey, " ", actionId, " takes ",
						String.valueOf(stopWatch.getTime()), " ms"));
			}

			PermissionCacheUtil.putPermission(
				groupId, name, primKey, roleIds, actionId, value);
		}
		catch (Exception e) {
			PermissionCacheUtil.removePermission(
				groupId, name, primKey, roleIds, actionId);

			throw e;
		}

		return value;
	}

	@Override
	public void init(User user) {
		super.init(user);

		try {
			Group guestGroup = GroupLocalServiceUtil.getGroup(
				user.getCompanyId(), GroupConstants.GUEST);

			_guestGroupId = guestGroup.getGroupId();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public boolean isCompanyAdmin() {
		try {
			return isCompanyAdminImpl(user.getCompanyId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	@Override
	public boolean isCompanyAdmin(long companyId) {
		try {
			return isCompanyAdminImpl(companyId);
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	@Override
	public boolean isContentReviewer(long companyId, long groupId) {
		try {
			return isContentReviewerImpl(companyId, groupId);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Override
	public boolean isGroupAdmin(long groupId) {
		try {
			Group group = null;

			if (groupId > 0) {
				group = GroupLocalServiceUtil.fetchGroup(groupId);
			}

			return _isGroupAdminImpl(group);
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	@Override
	public boolean isGroupMember(long groupId) {
		try {
			return isGroupMemberImpl(groupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	@Override
	public boolean isGroupOwner(long groupId) {
		try {
			return isGroupOwnerImpl(groupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	@Override
	public boolean isOrganizationAdmin(long organizationId) {
		try {
			return isOrganizationAdminImpl(organizationId);
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	@Override
	public boolean isOrganizationOwner(long organizationId) {
		try {
			return isOrganizationOwnerImpl(organizationId);
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	protected void addTeamRoles(long userId, Group group, Set<Long> roleIds)
		throws Exception {

		int count = TeamLocalServiceUtil.getGroupTeamsCount(group.getGroupId());

		if (count == 0) {
			return;
		}

		List<Role> roles = RoleLocalServiceUtil.getUserTeamRoles(
			userId, group.getGroupId());

		for (Role role : roles) {
			roleIds.add(role.getRoleId());
		}
	}

	protected boolean doCheckPermission(
			long companyId, long groupId, String name, String primKey,
			long[] roleIds, String actionId, StopWatch stopWatch)
		throws Exception {

		logHasUserPermission(groupId, name, primKey, actionId, stopWatch, 1);

		List<Resource> resources = getResources(
			companyId, groupId, name, primKey, actionId);

		resources = fixMissingResources(
			companyId, groupId, name, primKey, actionId, resources);

		logHasUserPermission(groupId, name, primKey, actionId, stopWatch, 3);

		// Check if user has access to perform the action on the given resource
		// scopes. The resources are scoped to check first for an individual
		// class, then for the group that the class may belong to, and then for
		// the company that the class belongs to.

		boolean value = ResourceLocalServiceUtil.hasUserPermissions(
			user.getUserId(), groupId, resources, actionId, roleIds);

		logHasUserPermission(groupId, name, primKey, actionId, stopWatch, 4);

		return value;
	}

	protected long[] doGetRoleIds(long userId, long groupId) throws Exception {
		if (!signedIn) {
			return getGuestUserRoleIds();
		}

		long[] roleIds = PermissionCacheUtil.getUserGroupRoleIds(
			userId, groupId);

		if (roleIds != null) {
			return roleIds;
		}

		try {
			Group group = null;

			long parentGroupId = 0;

			if (groupId > 0) {
				group = GroupLocalServiceUtil.getGroup(groupId);

				if (group.isLayout()) {
					parentGroupId = group.getParentGroupId();

					if (parentGroupId > 0) {
						group = GroupLocalServiceUtil.getGroup(parentGroupId);
					}
				}
			}

			UserBag userBag = getUserBag();

			Set<Long> roleIdsSet = SetUtil.fromArray(userBag.getRoleIds());

			List<UserGroupRole> userGroupRoles =
				UserGroupRoleLocalServiceUtil.getUserGroupRoles(
					userId, groupId);

			for (UserGroupRole userGroupRole : userGroupRoles) {
				roleIdsSet.add(userGroupRole.getRoleId());
			}

			if (parentGroupId > 0) {
				userGroupRoles =
					UserGroupRoleLocalServiceUtil.getUserGroupRoles(
						userId, parentGroupId);

				for (UserGroupRole userGroupRole : userGroupRoles) {
					roleIdsSet.add(userGroupRole.getRoleId());
				}
			}

			long[] userUserGroupIds = userBag.getUserUserGroupsIds();

			if (userUserGroupIds.length > 0) {
				List<UserGroupGroupRole> userGroupGroupRoles =
					UserGroupGroupRoleLocalServiceUtil.
						getUserGroupGroupRolesByUser(userId, groupId);

				for (UserGroupGroupRole userGroupGroupRole :
						userGroupGroupRoles) {

					roleIdsSet.add(userGroupGroupRole.getRoleId());
				}

				if (parentGroupId > 0) {
					userGroupGroupRoles =
						UserGroupGroupRoleLocalServiceUtil.
							getUserGroupGroupRoles(userId, parentGroupId);

					for (UserGroupGroupRole userGroupGroupRole :
							userGroupGroupRoles) {

						roleIdsSet.add(userGroupGroupRole.getRoleId());
					}
				}
			}

			if (group != null) {
				if (group.isOrganization() && userBag.hasUserOrgGroup(group)) {
					Role organizationUserRole = RoleLocalServiceUtil.getRole(
						group.getCompanyId(), RoleConstants.ORGANIZATION_USER);

					roleIdsSet.add(organizationUserRole.getRoleId());
				}

				if (group.isSite() &&
					(userBag.hasUserGroup(group) ||
					 userBag.hasUserOrgGroup(group))) {

					Role siteMemberRole = RoleLocalServiceUtil.getRole(
						group.getCompanyId(), RoleConstants.SITE_MEMBER);

					roleIdsSet.add(siteMemberRole.getRoleId());
				}

				if (group.isUserPersonalSite()) {
					Role powerUserRole = RoleLocalServiceUtil.getRole(
						getCompanyId(), RoleConstants.POWER_USER);

					if (userBag.hasRole(powerUserRole)) {
						Role siteMemberRole = RoleLocalServiceUtil.getRole(
							group.getCompanyId(), RoleConstants.SITE_MEMBER);

						roleIdsSet.add(siteMemberRole.getRoleId());
					}
				}

				if ((group.isOrganization() &&
					 userBag.hasUserOrgGroup(group)) ||
					(group.isSite() && userBag.hasUserGroup(group))) {

					addTeamRoles(userId, group, roleIdsSet);
				}
			}

			if (checkGuest) {
				for (long roleId : getGuestUserRoleIds()) {
					roleIdsSet.add(roleId);
				}
			}

			roleIds = ArrayUtil.toLongArray(roleIdsSet);

			Arrays.sort(roleIds);

			PermissionCacheUtil.putUserGroupRoleIds(userId, groupId, roleIds);

			return roleIds;
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserGroupRoleIds(userId, groupId);

			throw e;
		}
	}

	protected List<Resource> fixMissingResources(
			long companyId, long groupId, String name, String primKey,
			String actionId, List<Resource> resources)
		throws Exception {

		int count =
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		if (count > 0) {
			return resources;
		}

		String newIndividualResourcePrimKey = null;

		if (primKey.contains(PortletConstants.LAYOUT_SEPARATOR)) {

			// Using defaults because custom permissions defined for portlet
			// resource are not defined

			newIndividualResourcePrimKey = name;

			if (_log.isDebugEnabled()) {
				String message = StringBundler.concat(
					"Using defaults because custom permissions for portlet ",
					"resource ", name, " are not defined");

				_log.debug(message, new IllegalArgumentException(message));
			}
		}
		else if ((groupId > 0) &&
				 ResourceActionsUtil.isRootModelResource(name)) {

			// Using defaults because custom permissions defined for root model
			// resource are not defined

			newIndividualResourcePrimKey = name;

			if (_log.isDebugEnabled()) {
				String message = StringBundler.concat(
					"Using defaults because custom permissions for root model ",
					"resource ", name, " are not defined");

				_log.debug(message, new IllegalArgumentException(message));
			}
		}
		else if (primKey.equals("0") ||
				 primKey.equals(
					 String.valueOf(ResourceConstants.PRIMKEY_DNE)) ||
				 (primKey.equals(String.valueOf(companyId)) &&
				  !name.equals(Company.class.getName()))) {

			newIndividualResourcePrimKey = name;

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(9);

				sb.append("Using ");
				sb.append(name);
				sb.append(" as the primary key instead of the legacy primary ");
				sb.append("key ");
				sb.append(primKey);
				sb.append(" that was used for permission checking of ");
				sb.append(name);
				sb.append(" in company ");
				sb.append(companyId);

				_log.warn(
					sb.toString(), new IllegalArgumentException(sb.toString()));
			}
		}

		if (newIndividualResourcePrimKey != null) {
			Resource individualResource = resources.get(0);

			if (individualResource.getScope() !=
					ResourceConstants.SCOPE_INDIVIDUAL) {

				throw new IllegalArgumentException(
					"The first resource must be an individual scope");
			}

			individualResource.setPrimKey(name);
		}

		return resources;
	}

	/**
	 * Returns representations of the resource at each scope level.
	 *
	 * <p>
	 * For example, if the class name and primary key of a blog entry were
	 * passed to this method, it would return a resource for the blog entry
	 * itself (individual scope), a resource representing all blog entries
	 * within its group (group scope), a resource standing for all blog entries
	 * within a group the user has a suitable role in (group-template scope),
	 * and a resource signifying all blog entries within the company (company
	 * scope).
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupId the primary key of the group containing the resource
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key of the resource
	 * @param  actionId unused
	 * @return representations of the resource at each scope level
	 * @throws Exception if an exception occurred
	 */
	protected List<Resource> getResources(
			long companyId, long groupId, String name, String primKey,
			String actionId)
		throws Exception {

		// Individual

		List<Resource> resources = new ArrayList<>(4);

		Resource individualResource = ResourceLocalServiceUtil.getResource(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		resources.add(individualResource);

		// Group

		if (groupId > 0) {
			Resource groupResource = ResourceLocalServiceUtil.getResource(
				companyId, name, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId));

			resources.add(groupResource);
		}

		// Group template

		if (signedIn && (groupId > 0)) {
			Resource groupTemplateResource =
				ResourceLocalServiceUtil.getResource(
					companyId, name, ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID));

			resources.add(groupTemplateResource);
		}

		// Company

		Resource companyResource = ResourceLocalServiceUtil.getResource(
			companyId, name, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(companyId));

		resources.add(companyResource);

		return resources;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected boolean hasGuestPermission(
			long groupId, String name, String primKey, String actionId)
		throws Exception {

		Group group = null;

		if (groupId > 0) {
			group = GroupLocalServiceUtil.fetchGroup(groupId);
		}

		return _hasGuestPermission(group, name, primKey, actionId);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected boolean hasPermissionImpl(
		long groupId, String name, String primKey, long[] roleIds,
		String actionId) {

		Group group = null;

		if (groupId > 0) {
			group = GroupLocalServiceUtil.fetchGroup(groupId);
		}

		return _hasPermissionImpl(group, name, primKey, roleIds, actionId);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected boolean hasUserPermissionImpl(
			long groupId, String name, String primKey, long[] roleIds,
			String actionId)
		throws Exception {

		Group group = null;

		if (groupId > 0) {
			group = GroupLocalServiceUtil.fetchGroup(groupId);
		}

		return _hasUserPermissionImpl(group, name, primKey, roleIds, actionId);
	}

	protected boolean isCompanyAdminImpl(long companyId) throws Exception {
		if (!signedIn) {
			return false;
		}

		if (isOmniadmin()) {
			return true;
		}

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			getUserId(), companyId, RoleConstants.ADMINISTRATOR);

		try {
			if (value == null) {
				value = RoleLocalServiceUtil.hasUserRole(
					user.getUserId(), companyId, RoleConstants.ADMINISTRATOR,
					true);

				PermissionCacheUtil.putUserPrimaryKeyRole(
					getUserId(), companyId, RoleConstants.ADMINISTRATOR, value);
			}
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserPrimaryKeyRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR);

			throw e;
		}

		return value;
	}

	protected boolean isContentReviewerImpl(long groupId)
		throws PortalException {

		if (isCompanyAdmin() || isGroupAdmin(groupId)) {
			return true;
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (RoleLocalServiceUtil.hasUserRole(
				getUserId(), group.getCompanyId(),
				RoleConstants.PORTAL_CONTENT_REVIEWER, true)) {

			return true;
		}

		if (group.isSite()) {
			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					getUserId(), groupId, RoleConstants.SITE_CONTENT_REVIEWER,
					true)) {

				return true;
			}
		}

		return false;
	}

	protected boolean isContentReviewerImpl(long companyId, long groupId)
		throws Exception {

		if (!signedIn) {
			return false;
		}

		if (isOmniadmin()) {
			return true;
		}

		if (isCompanyAdmin(companyId)) {
			return true;
		}

		if (groupId <= 0) {
			return false;
		}

		if (isGroupAdmin(groupId)) {
			return true;
		}

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			getUserId(), groupId, RoleConstants.SITE_CONTENT_REVIEWER);

		try {
			if (value == null) {
				value = isContentReviewerImpl(groupId);

				PermissionCacheUtil.putUserPrimaryKeyRole(
					getUserId(), groupId, RoleConstants.SITE_CONTENT_REVIEWER,
					value);
			}
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserPrimaryKeyRole(
				getUserId(), groupId, RoleConstants.SITE_CONTENT_REVIEWER);

			throw e;
		}

		return value;
	}

	protected boolean isGroupAdminImpl(Group group) throws Exception {
		if (group.isLayout()) {
			long parentGroupId = group.getParentGroupId();

			if (parentGroupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
				return false;
			}

			group = GroupLocalServiceUtil.getGroup(parentGroupId);
		}

		if (group.isSite()) {
			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					getUserId(), group.getGroupId(),
					RoleConstants.SITE_ADMINISTRATOR, true) ||
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					getUserId(), group.getGroupId(), RoleConstants.SITE_OWNER,
					true)) {

				return true;
			}

			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			Group parentGroup = group;

			while (!parentGroup.isRoot()) {
				parentGroup = parentGroup.getParentGroup();

				long[] roleIds = getRoleIds(
					getUserId(), parentGroup.getGroupId());

				if (doCheckPermission(
						parentGroup.getCompanyId(), parentGroup.getGroupId(),
						Group.class.getName(),
						String.valueOf(parentGroup.getGroupId()), roleIds,
						ActionKeys.MANAGE_SUBGROUPS, stopWatch)) {

					return true;
				}
			}
		}

		if (group.isCompany()) {
			if (isCompanyAdmin()) {
				return true;
			}

			return false;
		}
		else if (group.isLayoutPrototype()) {
			if (LayoutPrototypePermissionUtil.contains(
					this, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}

			return false;
		}
		else if (group.isLayoutSetPrototype()) {
			if (LayoutSetPrototypePermissionUtil.contains(
					this, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}

			return false;
		}
		else if (group.isOrganization()) {
			long organizationId = group.getOrganizationId();

			while (organizationId !=
						OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				long organizationGroupId = organization.getGroupId();

				if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						getUserId(), organizationGroupId,
						RoleConstants.ORGANIZATION_ADMINISTRATOR, true) ||
					UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						getUserId(), organizationGroupId,
						RoleConstants.ORGANIZATION_OWNER, true)) {

					return true;
				}

				organizationId = organization.getParentOrganizationId();
			}

			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(
					group.getOrganizationId());

			while (!organization.isRoot()) {
				Organization parentOrganization =
					organization.getParentOrganization();

				Group parentGroup = parentOrganization.getGroup();

				long[] roleIds = getRoleIds(
					getUserId(), parentGroup.getGroupId());

				if (doCheckPermission(
						parentGroup.getCompanyId(), parentGroup.getGroupId(),
						Organization.class.getName(),
						String.valueOf(parentOrganization.getOrganizationId()),
						roleIds, ActionKeys.MANAGE_SUBORGANIZATIONS,
						stopWatch)) {

					return true;
				}

				organization = parentOrganization;
			}
		}

		return false;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected boolean isGroupAdminImpl(long groupId) throws Exception {
		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

		return _isGroupAdminImpl(group);
	}

	protected boolean isGroupMemberImpl(long groupId) throws Exception {
		if (!signedIn) {
			return false;
		}

		if (groupId <= 0) {
			return false;
		}

		long[] roleIds = getRoleIds(getUserId(), groupId);

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		Role role = RoleLocalServiceUtil.getRole(
			group.getCompanyId(), RoleConstants.SITE_MEMBER);

		if (Arrays.binarySearch(roleIds, role.getRoleId()) >= 0) {
			return true;
		}

		UserBag userBag = getUserBag();

		return userBag.hasUserGroup(group);
	}

	protected boolean isGroupOwnerImpl(Group group) throws PortalException {
		if (group.isSite()) {
			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					getUserId(), group.getGroupId(), RoleConstants.SITE_OWNER,
					true)) {

				return true;
			}
		}

		if (group.isLayoutPrototype()) {
			if (LayoutPrototypePermissionUtil.contains(
					this, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}

			return false;
		}
		else if (group.isLayoutSetPrototype()) {
			if (LayoutSetPrototypePermissionUtil.contains(
					this, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}

			return false;
		}
		else if (group.isOrganization()) {
			long organizationId = group.getOrganizationId();

			while (organizationId !=
						OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				long organizationGroupId = organization.getGroupId();

				if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						getUserId(), organizationGroupId,
						RoleConstants.ORGANIZATION_OWNER, true)) {

					return true;
				}

				organizationId = organization.getParentOrganizationId();
			}
		}
		else if (group.isUser()) {
			long groupUserId = group.getClassPK();

			if (getUserId() == groupUserId) {
				return true;
			}
		}

		return false;
	}

	protected boolean isGroupOwnerImpl(long groupId) throws Exception {
		if (!signedIn) {
			return false;
		}

		if (isOmniadmin()) {
			return true;
		}

		if (groupId <= 0) {
			return false;
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (isCompanyAdmin(group.getCompanyId())) {
			return true;
		}

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			getUserId(), group.getGroupId(), RoleConstants.SITE_OWNER);

		try {
			if (value == null) {
				value = isGroupOwnerImpl(group);

				PermissionCacheUtil.putUserPrimaryKeyRole(
					getUserId(), group.getGroupId(), RoleConstants.SITE_OWNER,
					value);
			}
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserPrimaryKeyRole(
				getUserId(), group.getGroupId(), RoleConstants.SITE_OWNER);

			throw e;
		}

		return value;
	}

	protected boolean isOrganizationAdminImpl(long organizationId)
		throws Exception {

		if (!signedIn) {
			return false;
		}

		if (isOmniadmin()) {
			return true;
		}

		if (organizationId <= 0) {
			return false;
		}

		Organization organization =
			OrganizationLocalServiceUtil.fetchOrganization(organizationId);

		if (organization == null) {
			return false;
		}

		if (isCompanyAdmin(organization.getCompanyId())) {
			return true;
		}

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			getUserId(), organization.getOrganizationId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		try {
			if (value == null) {
				value = isOrganizationAdminImpl(organization);

				PermissionCacheUtil.putUserPrimaryKeyRole(
					getUserId(), organization.getOrganizationId(),
					RoleConstants.ORGANIZATION_ADMINISTRATOR, value);
			}
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserPrimaryKeyRole(
				getUserId(), organization.getOrganizationId(),
				RoleConstants.ORGANIZATION_ADMINISTRATOR);

			throw e;
		}

		return value;
	}

	protected boolean isOrganizationAdminImpl(Organization organization)
		throws PortalException {

		while (organization != null) {
			long organizationGroupId = organization.getGroupId();

			long userId = getUserId();

			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, organizationGroupId,
					RoleConstants.ORGANIZATION_ADMINISTRATOR, true) ||
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, organizationGroupId,
					RoleConstants.ORGANIZATION_OWNER, true)) {

				return true;
			}

			organization = organization.getParentOrganization();
		}

		return false;
	}

	protected boolean isOrganizationOwnerImpl(long organizationId)
		throws Exception {

		if (!signedIn) {
			return false;
		}

		if (isOmniadmin()) {
			return true;
		}

		if (organizationId <= 0) {
			return false;
		}

		Organization organization =
			OrganizationLocalServiceUtil.fetchOrganization(organizationId);

		if (organization == null) {
			return false;
		}

		if (isCompanyAdmin(organization.getCompanyId())) {
			return true;
		}

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			getUserId(), organization.getOrganizationId(),
			RoleConstants.ORGANIZATION_OWNER);

		try {
			if (value == null) {
				value = isOrganizationOwnerImpl(organization);

				PermissionCacheUtil.putUserPrimaryKeyRole(
					getUserId(), organization.getOrganizationId(),
					RoleConstants.ORGANIZATION_OWNER, value);
			}
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserPrimaryKeyRole(
				getUserId(), organization.getOrganizationId(),
				RoleConstants.ORGANIZATION_OWNER);

			throw e;
		}

		return value;
	}

	protected boolean isOrganizationOwnerImpl(Organization organization)
		throws PortalException {

		while (organization != null) {
			long organizationGroupId = organization.getGroupId();

			long userId = getUserId();

			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, organizationGroupId,
					RoleConstants.ORGANIZATION_OWNER, true)) {

				return true;
			}

			organization = organization.getParentOrganization();
		}

		return false;
	}

	protected void logHasUserPermission(
		long groupId, String name, String primKey, String actionId,
		StopWatch stopWatch, int block) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			StringBundler.concat(
				"Checking user permission block ", String.valueOf(block),
				" for ", String.valueOf(groupId), " ", name, " ", primKey, " ",
				actionId, " takes ", String.valueOf(stopWatch.getTime()),
				" ms"));
	}

	/**
	 * @deprecated As of Bunyan (6.0.x)
	 */
	@Deprecated
	protected static final String RESULTS_SEPARATOR = "_RESULTS_SEPARATOR_";

	private boolean _hasGuestPermission(
		Group group, String name, String primKey, String actionId) {

		List<String> resourceActions = ResourceActionsUtil.getResourceActions(
			name);

		if (!resourceActions.contains(actionId)) {
			return false;
		}

		if (name.indexOf(CharPool.PERIOD) != -1) {

			// Check unsupported model actions

			List<String> actions =
				ResourceActionsUtil.getModelResourceGuestUnsupportedActions(
					name);

			if (actions.contains(actionId)) {
				return false;
			}
		}
		else {

			// Check unsupported portlet actions

			List<String> actions =
				ResourceActionsUtil.getPortletResourceGuestUnsupportedActions(
					name);

			if (actions.contains(actionId)) {
				return false;
			}
		}

		long companyId = user.getCompanyId();
		long groupId = 0;

		if (group != null) {
			companyId = group.getCompanyId();
			groupId = group.getGroupId();
		}

		try {
			List<Resource> resources = getResources(
				companyId, groupId, name, primKey, actionId);

			resources = fixMissingResources(
				companyId, groupId, name, primKey, actionId, resources);

			return ResourceLocalServiceUtil.hasUserPermissions(
				defaultUserId, groupId, resources, actionId,
				getGuestUserRoleIds());
		}
		catch (NoSuchResourcePermissionException nsrpe) {
			throw new IllegalArgumentException(
				"Someone may be trying to circumvent the permission checker: " +
					nsrpe.getMessage(),
				nsrpe);
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	private boolean _hasPermissionImpl(
		Group group, String name, String primKey, long[] roleIds,
		String actionId) {

		try {
			if (!signedIn) {
				return _hasGuestPermission(group, name, primKey, actionId);
			}

			return _hasUserPermissionImpl(
				group, name, primKey, roleIds, actionId);
		}
		catch (IllegalArgumentException iae) {
			throw iae;
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	private boolean _hasUserPermissionImpl(
			Group group, String name, String primKey, long[] roleIds,
			String actionId)
		throws Exception {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		long companyId = user.getCompanyId();
		long groupId = 0;

		if (group != null) {
			companyId = group.getCompanyId();
			groupId = group.getGroupId();
		}

		try {
			boolean hasPermission = doCheckPermission(
				companyId, groupId, name, primKey, roleIds, actionId,
				stopWatch);

			if (hasPermission) {
				return true;
			}
		}
		catch (NoSuchResourcePermissionException nsrpe) {
			throw new IllegalArgumentException(
				"Someone may be trying to circumvent the permission checker: " +
					nsrpe.getMessage(),
				nsrpe);
		}

		if (isOmniadmin()) {
			return true;
		}

		if (name.equals(Organization.class.getName())) {
			if (isOrganizationAdminImpl(GetterUtil.getLong(primKey))) {
				return true;
			}
		}

		if (isCompanyAdminImpl(companyId)) {
			return true;
		}

		if (_isGroupAdminImpl(group)) {
			boolean hasLayoutManagerPermission = true;

			// Check if the layout manager has permission to do this action for
			// the current portlet

			if (Validator.isNotNull(name) && Validator.isNotNull(primKey) &&
				primKey.contains(PortletConstants.LAYOUT_SEPARATOR)) {

				hasLayoutManagerPermission =
					PortletPermissionUtil.hasLayoutManagerPermission(
						name, actionId);
			}

			if (hasLayoutManagerPermission) {
				return true;
			}
		}

		// Allow read-only access to personal site assets. Group is user
		// personal site here only when current user is the personal site owner.

		if ((group != null) && group.isUserPersonalSite() &&
			ActionKeys.VIEW.equals(actionId)) {

			// The only check we can perform on top is for the Site Member role.
			// The Site Member role is derived from the Power User role. When a
			// user is missing the Power User role, then the Site Member role
			// is not granted and we do not check default actions granted to the
			// Site Member role. Hence, it is the only role left. All other
			// roles were already checked.

			Role siteMemberRole = RoleLocalServiceUtil.getRole(
				getCompanyId(), RoleConstants.SITE_MEMBER);

			if (!ArrayUtil.contains(roleIds, siteMemberRole.getRoleId())) {
				boolean hasPermission = doCheckPermission(
					companyId, groupId, name, primKey,
					new long[] {siteMemberRole.getRoleId()}, actionId,
					stopWatch);

				if (hasPermission) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean _isGroupAdminImpl(Group group) throws Exception {
		if (!signedIn) {
			return false;
		}

		if (isOmniadmin()) {
			return true;
		}

		if (group == null) {
			return false;
		}

		if (isCompanyAdmin(group.getCompanyId())) {
			return true;
		}

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			getUserId(), group.getGroupId(), RoleConstants.SITE_ADMINISTRATOR);

		try {
			if (value == null) {
				value = isGroupAdminImpl(group);

				PermissionCacheUtil.putUserPrimaryKeyRole(
					getUserId(), group.getGroupId(),
					RoleConstants.SITE_ADMINISTRATOR, value);
			}
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserPrimaryKeyRole(
				getUserId(), group.getGroupId(),
				RoleConstants.SITE_ADMINISTRATOR);

			throw e;
		}

		return value;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdvancedPermissionChecker.class);

	private long _guestGroupId;

}
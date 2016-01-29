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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.security.permission.ResourceBlockIdsBag;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.security.permission.UserBagFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceBlockConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.service.permission.LayoutSetPrototypePermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
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

	public ResourceBlockIdsBag getGuestResourceBlockIdsBag(
			long companyId, long groupId, String name)
		throws Exception {

		ResourceBlockIdsBag resourceBlockIdsBag =
			PermissionCacheUtil.getResourceBlockIdsBag(
				companyId, groupId, defaultUserId, name);

		if (resourceBlockIdsBag != null) {
			return resourceBlockIdsBag;
		}

		try {
			resourceBlockIdsBag =
				ResourceBlockLocalServiceUtil.getResourceBlockIdsBag(
					getCompanyId(), groupId, name, getGuestUserRoleIds());

			PermissionCacheUtil.putResourceBlockIdsBag(
				companyId, groupId, defaultUserId, name, resourceBlockIdsBag);

			return resourceBlockIdsBag;
		}
		catch (Exception e) {
			PermissionCacheUtil.removeResourceBlockIdsBag(
				getCompanyId(), groupId, defaultUserId, name);

			throw e;
		}
	}

	/**
	 * Returns the permission checker bag for the guest user.
	 *
	 * @return the permission checker bag for the guest user
	 * @throws Exception if an exception occurred
	 */
	public long[] getGuestUserRoleIds() throws Exception {
		Group guestGroup = GroupLocalServiceUtil.getGroup(
			getCompanyId(), GroupConstants.GUEST);

		long[] roleIds = PermissionCacheUtil.getUserGroupRoleIds(
			defaultUserId, guestGroup.getGroupId());

		if (roleIds != null) {
			return roleIds;
		}

		try {
			List<Role> roles = RoleLocalServiceUtil.getUserRelatedRoles(
				defaultUserId, Collections.singletonList(guestGroup));

			// Only use the guest group for deriving the roles for
			// unauthenticated users. Do not add the group to the permission bag
			// as this implies group membership which is incorrect in the case
			// of unauthenticated users.

			roleIds = ListUtil.toLongArray(roles, Role.ROLE_ID_ACCESSOR);

			Arrays.sort(roleIds);

			PermissionCacheUtil.putUserGroupRoleIds(
				defaultUserId, guestGroup.getGroupId(), roleIds);
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserGroupRoleIds(
				defaultUserId, guestGroup.getGroupId());

			throw e;
		}

		return roleIds;
	}

	@Override
	public List<Long> getOwnerResourceBlockIds(
		long companyId, long groupId, String name, String actionId) {

		try {
			ResourceBlockIdsBag resourceBlockIdsBag =
				getOwnerResourceBlockIdsBag(companyId, groupId, name);

			return ResourceBlockLocalServiceUtil.getResourceBlockIds(
				resourceBlockIdsBag, name, actionId);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return Collections.emptyList();
	}

	public ResourceBlockIdsBag getOwnerResourceBlockIdsBag(
		long companyId, long groupId, String name) {

		ResourceBlockIdsBag resourceBlockIdsBag =
			PermissionCacheUtil.getResourceBlockIdsBag(
				companyId, groupId, ResourceBlockConstants.OWNER_USER_ID, name);

		if (resourceBlockIdsBag != null) {
			return resourceBlockIdsBag;
		}

		try {
			long[] roleIds = {getOwnerRoleId()};

			resourceBlockIdsBag =
				ResourceBlockLocalServiceUtil.getResourceBlockIdsBag(
					getCompanyId(), groupId, name, roleIds);

			PermissionCacheUtil.putResourceBlockIdsBag(
				companyId, groupId, ResourceBlockConstants.OWNER_USER_ID, name,
				resourceBlockIdsBag);

			return resourceBlockIdsBag;
		}
		catch (Exception e) {
			PermissionCacheUtil.removeResourceBlockIdsBag(
				companyId, groupId, ResourceBlockConstants.OWNER_USER_ID, name);

			throw e;
		}
	}

	@Override
	public List<Long> getResourceBlockIds(
		long companyId, long groupId, long userId, String name,
		String actionId) {

		try {
			ResourceBlockIdsBag resourceBlockIdsBag = getResourceBlockIdsBag(
				companyId, groupId, name, getRoleIds(getUserId(), groupId));

			return ResourceBlockLocalServiceUtil.getResourceBlockIds(
				resourceBlockIdsBag, name, actionId);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return Collections.emptyList();
	}

	public ResourceBlockIdsBag getResourceBlockIdsBag(
			long companyId, long groupId, String name, long[] roleIds)
		throws Exception {

		ResourceBlockIdsBag resourceBlockIdsBag =
			PermissionCacheUtil.getResourceBlockIdsBag(
				companyId, groupId, getUserId(), name);

		if (resourceBlockIdsBag != null) {
			return resourceBlockIdsBag;
		}

		try {
			resourceBlockIdsBag =
				ResourceBlockLocalServiceUtil.getResourceBlockIdsBag(
					getCompanyId(), groupId, name, roleIds);

			PermissionCacheUtil.putResourceBlockIdsBag(
				companyId, groupId, getUserId(), name, resourceBlockIdsBag);

			return resourceBlockIdsBag;
		}
		catch (Exception e) {
			PermissionCacheUtil.removeResourceBlockIdsBag(
				companyId, groupId, getUserId(), name);

			throw e;
		}
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

		if (ownerId == defaultUserId) {
			if (actionId.equals(ActionKeys.VIEW)) {
				return true;
			}
			else {
				return false;
			}
		}

		try {
			if (ResourceBlockLocalServiceUtil.isSupported(name)) {
				PermissionedModel permissionedModel =
					ResourceBlockLocalServiceUtil.getPermissionedModel(
						name, GetterUtil.getLong(primKey));

				long groupId = 0;

				if (permissionedModel instanceof GroupedModel) {
					GroupedModel groupedModel = (GroupedModel)permissionedModel;

					groupId = groupedModel.getGroupId();
				}

				ResourceBlockIdsBag resourceBlockIdsBag =
					getOwnerResourceBlockIdsBag(companyId, groupId, name);

				return ResourceBlockLocalServiceUtil.hasPermission(
					name, permissionedModel, actionId, resourceBlockIdsBag);
			}

			return ResourcePermissionLocalServiceUtil.hasResourcePermission(
				companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey,
				getOwnerRoleId(), actionId);
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
		long groupId, String name, String primKey, String actionId) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		boolean checkOwnerPermission = false;
		Group group = null;

		try {
			if (groupId > 0) {
				group = GroupLocalServiceUtil.getGroup(groupId);

				// If the group is a scope group for a layout, check the
				// original group.

				if (group.isLayout() &&
					!ResourceBlockLocalServiceUtil.isSupported(name)) {

					Layout layout = LayoutLocalServiceUtil.getLayout(
						group.getClassPK());

					groupId = layout.getGroupId();

					group = GroupLocalServiceUtil.getGroup(groupId);
				}

				// If the group is a personal site, check the "User Personal
				// Site" group.

				if (group.isUser() && (group.getClassPK() == getUserId())) {
					checkOwnerPermission = true;

					group = GroupLocalServiceUtil.getGroup(
						getCompanyId(), GroupConstants.USER_PERSONAL_SITE);

					groupId = group.getGroupId();
				}
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
			if (checkOwnerPermission) {
				value = hasOwnerPermission(
					getCompanyId(), name, primKey, getUserId(), actionId);
			}

			if ((value == null) || !value) {
				value = hasPermissionImpl(
					groupId, name, primKey, roleIds, actionId);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Checking permission for " + groupId + " " + name +
						" " + primKey + " " + actionId + " takes " +
							stopWatch.getTime() + " ms");
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
			return isGroupAdminImpl(groupId);
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

		List<Team> userTeams = TeamLocalServiceUtil.getUserTeams(
			userId, group.getGroupId());

		for (Team team : userTeams) {
			Role role = RoleLocalServiceUtil.getTeamRole(
				team.getCompanyId(), team.getTeamId());

			roleIds.add(role.getRoleId());
		}

		LinkedHashMap<String, Object> teamParams = new LinkedHashMap<>();

		teamParams.put("usersUserGroups", userId);

		List<Team> userGroupTeams = TeamLocalServiceUtil.search(
			group.getGroupId(), null, null, teamParams, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (Team team : userGroupTeams) {
			Role role = RoleLocalServiceUtil.getTeamRole(
				team.getCompanyId(), team.getTeamId());

			roleIds.add(role.getRoleId());
		}
	}

	protected boolean doCheckPermission(
			long companyId, long groupId, String name, String primKey,
			long[] roleIds, String actionId, StopWatch stopWatch)
		throws Exception {

		logHasUserPermission(groupId, name, primKey, actionId, stopWatch, 1);

		if (ResourceBlockLocalServiceUtil.isSupported(name)) {
			ResourceBlockIdsBag resourceBlockIdsBag = getResourceBlockIdsBag(
				companyId, groupId, name, roleIds);

			boolean value = ResourceBlockLocalServiceUtil.hasPermission(
				name, GetterUtil.getLong(primKey), actionId,
				resourceBlockIdsBag);

			logHasUserPermission(
				groupId, name, primKey, actionId, stopWatch, 2);

			return value;
		}

		List<Resource> resources = getResources(
			companyId, groupId, name, primKey, actionId);

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

			List<Role> userGroupRoles = RoleLocalServiceUtil.getUserGroupRoles(
				userId, groupId);

			for (Role userGroupRole : userGroupRoles) {
				roleIdsSet.add(userGroupRole.getRoleId());
			}

			if (parentGroupId > 0) {
				userGroupRoles = RoleLocalServiceUtil.getUserGroupRoles(
					userId, parentGroupId);

				for (Role userGroupRole : userGroupRoles) {
					roleIdsSet.add(userGroupRole.getRoleId());
				}
			}

			List<Role> userGroupGroupRoles =
				RoleLocalServiceUtil.getUserGroupGroupRoles(userId, groupId);

			for (Role userGroupGroupRole : userGroupGroupRoles) {
				roleIdsSet.add(userGroupGroupRole.getRoleId());
			}

			if (parentGroupId > 0) {
				userGroupGroupRoles =
					RoleLocalServiceUtil.getUserGroupGroupRoles(
						userId, parentGroupId);

				for (Role userGroupGroupRole : userGroupGroupRoles) {
					roleIdsSet.add(userGroupGroupRole.getRoleId());
				}
			}

			if (group != null) {
				if (group.isOrganization() && userBag.hasUserOrgGroup(group)) {
					Role organizationUserRole = RoleLocalServiceUtil.getRole(
						group.getCompanyId(), RoleConstants.ORGANIZATION_USER);

					roleIdsSet.add(organizationUserRole.getRoleId());
				}

				if ((group.isSite() &&
					 (userBag.hasUserGroup(group) ||
					  userBag.hasUserOrgGroup(group))) ||
					group.isUserPersonalSite()) {

					Role siteMemberRole = RoleLocalServiceUtil.getRole(
						group.getCompanyId(), RoleConstants.SITE_MEMBER);

					roleIdsSet.add(siteMemberRole.getRoleId());
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

	protected boolean hasGuestPermission(
			long groupId, String name, String primKey, String actionId)
		throws Exception {

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

		List<Resource> resources = getResources(
			companyId, groupId, name, primKey, actionId);

		try {
			if (ResourceBlockLocalServiceUtil.isSupported(name)) {
				ResourceBlockIdsBag resourceBlockIdsBag =
					getGuestResourceBlockIdsBag(companyId, groupId, name);

				return ResourceBlockLocalServiceUtil.hasPermission(
					name, GetterUtil.getLong(primKey), actionId,
					resourceBlockIdsBag);
			}

			return ResourceLocalServiceUtil.hasUserPermissions(
				defaultUserId, groupId, resources, actionId,
				getGuestUserRoleIds());
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	protected boolean hasPermissionImpl(
		long groupId, String name, String primKey, long[] roleIds,
		String actionId) {

		try {
			if (!signedIn) {
				return hasGuestPermission(groupId, name, primKey, actionId);
			}

			if (ResourceBlockLocalServiceUtil.isSupported(name)) {
				return hasUserPermissionImpl(
					groupId, name, primKey, roleIds, actionId);
			}

			return hasUserPermissionImpl(
				groupId, name, primKey, roleIds, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	protected boolean hasUserPermissionImpl(
			long groupId, String name, String primKey, long[] roleIds,
			String actionId)
		throws Exception {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		long companyId = user.getCompanyId();

		if (groupId > 0) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			companyId = group.getCompanyId();
		}
		else if (name.equals(User.class.getName())) {
			User user = UserLocalServiceUtil.fetchUser(
				GetterUtil.getLong(primKey));

			if (user != null) {
				companyId = user.getCompanyId();
			}
		}

		boolean hasLayoutManagerPermission = true;

		// Check if the layout manager has permission to do this action for the
		// current portlet

		if (Validator.isNotNull(name) && Validator.isNotNull(primKey) &&
			primKey.contains(PortletConstants.LAYOUT_SEPARATOR)) {

			hasLayoutManagerPermission =
				PortletPermissionUtil.hasLayoutManagerPermission(
					name, actionId);
		}

		if (isCompanyAdminImpl(companyId)) {
			return true;
		}

		if (name.equals(Organization.class.getName())) {
			if (isOrganizationAdminImpl(GetterUtil.getLong(primKey))) {
				return true;
			}
		}
		else if (isGroupAdminImpl(groupId) && hasLayoutManagerPermission) {
			return true;
		}

		return doCheckPermission(
			companyId, groupId, name, primKey, roleIds, actionId, stopWatch);
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

			while (!group.isRoot()) {
				Group parentGroup = group.getParentGroup();

				long[] roleIds = getRoleIds(
					getUserId(), parentGroup.getGroupId());

				if (doCheckPermission(
						parentGroup.getCompanyId(), parentGroup.getGroupId(),
						Group.class.getName(),
						String.valueOf(parentGroup.getGroupId()), roleIds,
						ActionKeys.MANAGE_SUBGROUPS, stopWatch)) {

					return true;
				}

				group = parentGroup;
			}
		}

		if (group.isCompany()) {
			if (isCompanyAdmin()) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (group.isLayoutPrototype()) {
			if (LayoutPrototypePermissionUtil.contains(
					this, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}
			else {
				return false;
			}
		}
		else if (group.isLayoutSetPrototype()) {
			if (LayoutSetPrototypePermissionUtil.contains(
					this, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}
			else {
				return false;
			}
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

	protected boolean isGroupAdminImpl(long groupId) throws Exception {
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
			else {
				return false;
			}
		}
		else if (group.isLayoutSetPrototype()) {
			if (LayoutSetPrototypePermissionUtil.contains(
					this, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}
			else {
				return false;
			}
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
			"Checking user permission block " + block + " for " + groupId +
				" " + name + " " + primKey + " " + actionId + " takes " +
					stopWatch.getTime() + " ms");
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	protected static final String RESULTS_SEPARATOR = "_RESULTS_SEPARATOR_";

	private static final Log _log = LogFactoryUtil.getLog(
		AdvancedPermissionChecker.class);

}
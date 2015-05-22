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

package com.liferay.portal.search;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.AdvancedPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerBag;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Allen Chiang
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Amos Fong
 */
public class SearchPermissionCheckerImpl implements SearchPermissionChecker {

	@Override
	public void addPermissionFields(long companyId, Document document) {
		try {
			long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));

			String className = document.get(Field.ROOT_ENTRY_CLASS_NAME);

			String classPK = document.get(Field.ROOT_ENTRY_CLASS_PK);

			if (Validator.isNull(className) && Validator.isNull(classPK)) {
				className = document.get(Field.ENTRY_CLASS_NAME);

				classPK = document.get(Field.ENTRY_CLASS_PK);
			}

			boolean relatedEntry = GetterUtil.getBoolean(
				document.get(Field.RELATED_ENTRY));

			if (relatedEntry) {
				long classNameId = GetterUtil.getLong(
					document.get(Field.CLASS_NAME_ID));

				className = PortalUtil.getClassName(classNameId);

				classPK = document.get(Field.CLASS_PK);
			}

			if (Validator.isNull(className) || Validator.isNull(classPK)) {
				return;
			}

			Indexer indexer = IndexerRegistryUtil.getIndexer(className);

			if (!indexer.isPermissionAware()) {
				return;
			}

			doAddPermissionFields_6(
				companyId, groupId, className, classPK, document);
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsre, nsre);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public BooleanFilter getPermissionBooleanFilter(
		long companyId, long[] groupIds, long userId, String className,
		BooleanFilter booleanFilter, SearchContext searchContext) {

		try {
			booleanFilter = doGetPermissionBooleanFilter(
				companyId, groupIds, userId, className, booleanFilter,
				searchContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return booleanFilter;
	}

	@Override
	public void updatePermissionFields(
		String resourceName, String resourceClassPK) {

		try {
			doUpdatePermissionFields(resourceName, resourceClassPK);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void addRequiredMemberRole(
			Group group, TermsFilter groupRolesTermsFilter)
		throws Exception {

		if (group.isOrganization()) {
			Role organizationUserRole = RoleLocalServiceUtil.getRole(
				group.getCompanyId(), RoleConstants.ORGANIZATION_USER);

			groupRolesTermsFilter.addValue(
				group.getGroupId() + StringPool.DASH +
					organizationUserRole.getRoleId());
		}

		if (group.isSite()) {
			Role siteMemberRole = RoleLocalServiceUtil.getRole(
				group.getCompanyId(), RoleConstants.SITE_MEMBER);

			groupRolesTermsFilter.addValue(
				group.getGroupId() + StringPool.DASH +
					siteMemberRole.getRoleId());
		}
	}

	protected void doAddPermissionFields_6(
			long companyId, long groupId, String className, String classPK,
			Document doc)
		throws Exception {

		List<Role> roles = null;

		if (ResourceBlockLocalServiceUtil.isSupported(className)) {
			roles = ResourceBlockLocalServiceUtil.getRoles(
				className, Long.valueOf(classPK), ActionKeys.VIEW);
		}
		else {
			roles = ResourcePermissionLocalServiceUtil.getRoles(
				companyId, className, ResourceConstants.SCOPE_INDIVIDUAL,
				classPK, ActionKeys.VIEW);
		}

		if (roles.isEmpty()) {
			return;
		}

		List<Long> roleIds = new ArrayList<>();
		List<String> groupRoleIds = new ArrayList<>();

		for (Role role : roles) {
			if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) ||
				(role.getType() == RoleConstants.TYPE_SITE)) {

				groupRoleIds.add(groupId + StringPool.DASH + role.getRoleId());
			}
			else {
				roleIds.add(role.getRoleId());
			}
		}

		doc.addKeyword(
			Field.ROLE_ID, roleIds.toArray(new Long[roleIds.size()]));
		doc.addKeyword(
			Field.GROUP_ROLE_ID,
			groupRoleIds.toArray(new String[groupRoleIds.size()]));
	}

	protected BooleanFilter doGetPermissionBooleanFilter(
			long companyId, long[] groupIds, long userId, String className,
			BooleanFilter booleanFilter, SearchContext searchContext)
		throws Exception {

		Indexer indexer = IndexerRegistryUtil.getIndexer(className);

		if (!indexer.isPermissionAware()) {
			return booleanFilter;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		AdvancedPermissionChecker advancedPermissionChecker = null;

		if ((permissionChecker != null) &&
			(permissionChecker instanceof AdvancedPermissionChecker)) {

			advancedPermissionChecker =
				(AdvancedPermissionChecker)permissionChecker;
		}

		if (advancedPermissionChecker == null) {
			return booleanFilter;
		}

		PermissionCheckerBag permissionCheckerBag = getPermissionCheckerBag(
			advancedPermissionChecker, userId);

		if (permissionCheckerBag == null) {
			return booleanFilter;
		}

		Set<Group> groups = new LinkedHashSet<>();
		Set<Role> roles = new LinkedHashSet<>();
		Set<UserGroupRole> userGroupRoles = new LinkedHashSet<>();
		Map<Long, List<Role>> groupIdsToRoles = new HashMap<>();

		populate(
			companyId, groupIds, userId, advancedPermissionChecker,
			permissionCheckerBag, groups, roles, userGroupRoles,
			groupIdsToRoles);

		return doGetPermissionFilter_6(
			companyId, groupIds, userId, className, booleanFilter, groups,
			roles, userGroupRoles, groupIdsToRoles);
	}

	protected BooleanFilter doGetPermissionFilter_6(
			long companyId, long[] groupIds, long userId, String className,
			BooleanFilter booleanFilter, Set<Group> groups, Set<Role> roles,
			Set<UserGroupRole> userGroupRoles,
			Map<Long, List<Role>> groupIdsToRoles)
		throws Exception {

		BooleanFilter permissionBooleanFilter = new BooleanFilter();

		if (userId > 0) {
			permissionBooleanFilter.addTerm(Field.USER_ID, userId);
		}

		TermsFilter groupsTermsFilter = new TermsFilter(Field.GROUP_ID);
		TermsFilter groupRolesTermsFilter = new TermsFilter(
			Field.GROUP_ROLE_ID);
		TermsFilter rolesTermsFilter = new TermsFilter(Field.ROLE_ID);

		for (Role role : roles) {
			String roleName = role.getName();

			if (roleName.equals(RoleConstants.ADMINISTRATOR)) {
				return booleanFilter;
			}

			if (ResourcePermissionLocalServiceUtil.hasResourcePermission(
					companyId, className, ResourceConstants.SCOPE_COMPANY,
					String.valueOf(companyId), role.getRoleId(),
					ActionKeys.VIEW)) {

				return booleanFilter;
			}

			if ((role.getType() == RoleConstants.TYPE_REGULAR) &&
				ResourcePermissionLocalServiceUtil.hasResourcePermission(
					companyId, className,
					ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					role.getRoleId(), ActionKeys.VIEW)) {

				return booleanFilter;
			}

			for (Group group : groups) {
				if (ResourcePermissionLocalServiceUtil.hasResourcePermission(
						companyId, className, ResourceConstants.SCOPE_GROUP,
						String.valueOf(group.getGroupId()), role.getRoleId(),
						ActionKeys.VIEW)) {

					groupsTermsFilter.addValue(
						String.valueOf(group.getGroupId()));
				}

				if ((role.getType() != RoleConstants.TYPE_REGULAR) &&
					ResourcePermissionLocalServiceUtil.hasResourcePermission(
						companyId, className,
						ResourceConstants.SCOPE_GROUP_TEMPLATE,
						String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
						role.getRoleId(), ActionKeys.VIEW)) {

					List<Role> groupRoles = groupIdsToRoles.get(
						group.getGroupId());

					if (groupRoles.contains(role)) {
						groupsTermsFilter.addValue(
							String.valueOf(group.getGroupId()));
					}
				}

				if (group.isSite() &&
					!roleName.equals(RoleConstants.SITE_MEMBER) &&
					(role.getType() == RoleConstants.TYPE_SITE)) {

					groupRolesTermsFilter.addValue(
						group.getGroupId() + StringPool.DASH +
							role.getRoleId());
				}
			}

			if (ArrayUtil.isNotEmpty(groupIds)) {
				for (long groupId : groupIds) {
					if (ResourcePermissionLocalServiceUtil.
							hasResourcePermission(
								companyId, className,
								ResourceConstants.SCOPE_GROUP,
								String.valueOf(groupId), role.getRoleId(),
								ActionKeys.VIEW)) {

						groupsTermsFilter.addValue(String.valueOf(groupId));
					}
				}
			}

			rolesTermsFilter.addValue(String.valueOf(role.getRoleId()));
		}

		for (Group group : groups) {
			addRequiredMemberRole(group, groupRolesTermsFilter);
		}

		for (UserGroupRole userGroupRole : userGroupRoles) {
			groupRolesTermsFilter.addValue(
				userGroupRole.getGroupId() + StringPool.DASH +
					userGroupRole.getRoleId());
		}

		if (!groupsTermsFilter.isEmpty()) {
			permissionBooleanFilter.add(groupsTermsFilter);
		}

		if (!groupRolesTermsFilter.isEmpty()) {
			permissionBooleanFilter.add(groupRolesTermsFilter);
		}

		if (!rolesTermsFilter.isEmpty()) {
			permissionBooleanFilter.add(rolesTermsFilter);
		}

		if (!permissionBooleanFilter.hasClauses()) {
			return booleanFilter;
		}

		BooleanFilter fullBooleanFilter = new BooleanFilter();

		if ((booleanFilter != null) && booleanFilter.hasClauses()) {
			fullBooleanFilter.add(booleanFilter, BooleanClauseOccur.MUST);
		}

		fullBooleanFilter.add(permissionBooleanFilter, BooleanClauseOccur.MUST);

		return fullBooleanFilter;
	}

	protected void doUpdatePermissionFields(
			String resourceName, String resourceClassPK)
		throws Exception {

		Indexer indexer = IndexerRegistryUtil.getIndexer(resourceName);

		if (indexer != null) {
			indexer.reindex(resourceName, GetterUtil.getLong(resourceClassPK));
		}
	}

	protected PermissionCheckerBag getPermissionCheckerBag(
			AdvancedPermissionChecker advancedPermissionChecker, long userId)
		throws Exception {

		if (!advancedPermissionChecker.isSignedIn()) {
			return advancedPermissionChecker.getGuestUserBag();
		}
		else {
			return advancedPermissionChecker.getUserBag(userId, 0);
		}
	}

	protected void populate(
			long companyId, long[] groupIds, long userId,
			AdvancedPermissionChecker advancedPermissionChecker,
			PermissionCheckerBag permissionCheckerBag, Set<Group> groups,
			Set<Role> roles, Set<UserGroupRole> userGroupRoles,
			Map<Long, List<Role>> groupIdsToRoles)
		throws Exception {

		roles.addAll(permissionCheckerBag.getRoles());

		if (ArrayUtil.isEmpty(groupIds)) {
			groups.addAll(GroupLocalServiceUtil.getUserGroups(userId, true));
			groups.addAll(permissionCheckerBag.getGroups());

			userGroupRoles.addAll(
				UserGroupRoleLocalServiceUtil.getUserGroupRoles(userId));
		}
		else {
			for (long groupId : groupIds) {
				if (GroupLocalServiceUtil.hasUserGroup(userId, groupId)) {
					Group group = GroupLocalServiceUtil.getGroup(groupId);

					groups.add(group);
				}

				userGroupRoles.addAll(
					UserGroupRoleLocalServiceUtil.getUserGroupRoles(
						userId, groupId));
				userGroupRoles.addAll(
					UserGroupRoleLocalServiceUtil.
						getUserGroupRolesByUserUserGroupAndGroup(
							userId, groupId));
			}
		}

		if (advancedPermissionChecker.isSignedIn()) {
			roles.add(
				RoleLocalServiceUtil.getRole(companyId, RoleConstants.GUEST));
		}

		for (Group group : groups) {
			PermissionCheckerBag userBag = advancedPermissionChecker.getUserBag(
				userId, group.getGroupId());

			List<Role> groupRoles = ListUtil.fromCollection(userBag.getRoles());

			groupIdsToRoles.put(group.getGroupId(), groupRoles);

			roles.addAll(groupRoles);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchPermissionCheckerImpl.class);

}
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

package com.liferay.portal.search.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.NoSuchResourceException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.configuration.SearchPermissionCheckerConfiguration;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Allen Chiang
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Amos Fong
 * @author Preston Crary
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.SearchPermissionCheckerConfiguration",
	immediate = true, service = SearchPermissionChecker.class
)
public class SearchPermissionCheckerImpl implements SearchPermissionChecker {

	@Override
	public void addPermissionFields(long companyId, Document document) {
		try {
			long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));

			String className = document.get(Field.ENTRY_CLASS_NAME);
			String classPK = document.get(Field.ENTRY_CLASS_PK);

			if (Validator.isNull(className) && Validator.isNull(classPK)) {
				className = document.get(Field.ROOT_ENTRY_CLASS_NAME);
				classPK = document.get(Field.ROOT_ENTRY_CLASS_PK);
			}

			boolean relatedEntry = GetterUtil.getBoolean(
				document.get(Field.RELATED_ENTRY));

			if (relatedEntry) {
				long classNameId = GetterUtil.getLong(
					document.get(Field.CLASS_NAME_ID));

				className = _portal.getClassName(classNameId);

				classPK = document.get(Field.CLASS_PK);
			}

			if (Validator.isNull(className) || Validator.isNull(classPK)) {
				return;
			}

			Indexer<?> indexer = _indexerRegistry.nullSafeGetIndexer(className);

			if (!indexer.isPermissionAware()) {
				return;
			}

			String viewActionId = document.get(Field.VIEW_ACTION_ID);

			if (Validator.isNull(viewActionId)) {
				viewActionId = ActionKeys.VIEW;
			}

			_addPermissionFields(
				companyId, groupId, className, classPK, viewActionId, document);
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
			booleanFilter = _getPermissionBooleanFilter(
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
			Indexer<?> indexer = _indexerRegistry.nullSafeGetIndexer(
				resourceName);

			indexer.reindex(resourceName, GetterUtil.getLong(resourceClassPK));
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_searchPermissionCheckerConfiguration =
			ConfigurableUtil.createConfigurable(
				SearchPermissionCheckerConfiguration.class, properties);
	}

	private void _addGroup(
		Group group, List<Role> groupRoles,
		List<UsersGroupIdRoles> usersGroupIdsRoles) {

		if (group != null) {
			usersGroupIdsRoles.add(
				new UsersGroupIdRoles(group.getGroupId(), groupRoles));
		}
	}

	private void _addPermissionFields(
			long companyId, long groupId, String className, String classPK,
			String viewActionId, Document doc)
		throws Exception {

		List<Role> roles = _resourcePermissionLocalService.getRoles(
			companyId, className, ResourceConstants.SCOPE_INDIVIDUAL, classPK,
			viewActionId);

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

	private SearchPermissionContext _createSearchPermissionContext(
			long companyId, long userId, PermissionChecker permissionChecker)
		throws Exception {

		UserBag userBag = permissionChecker.getUserBag();

		if (userBag == null) {
			return null;
		}

		Set<Role> roles = new HashSet<>();

		if (permissionChecker.isSignedIn()) {
			roles.addAll(userBag.getRoles());

			roles.add(
				_roleLocalService.getRole(companyId, RoleConstants.GUEST));
		}
		else {
			roles.addAll(
				_roleLocalService.getRoles(
					permissionChecker.getGuestUserRoleIds()));
		}

		int termsCount = roles.size();

		int permissionTermsLimit =
			_searchPermissionCheckerConfiguration.permissionTermsLimit();

		if (termsCount > permissionTermsLimit) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Skipping presearch permission checking due to too ",
						"many roles: ", String.valueOf(termsCount), " > ",
						String.valueOf(permissionTermsLimit)));
			}

			return null;
		}

		Role organizationUserRole = _roleLocalService.getRole(
			companyId, RoleConstants.ORGANIZATION_USER);
		Role siteMemberRole = _roleLocalService.getRole(
			companyId, RoleConstants.SITE_MEMBER);

		Collection<Group> groups = userBag.getGroups();

		List<UsersGroupIdRoles> usersGroupIdsRoles = new ArrayList<>(
			groups.size());

		termsCount += groups.size();

		if (termsCount > permissionTermsLimit) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Skipping presearch permission checking due to too ",
						"many roles and groups: ", String.valueOf(termsCount),
						" > ", String.valueOf(permissionTermsLimit)));
			}

			return null;
		}

		for (Group group : groups) {
			long[] roleIds = permissionChecker.getRoleIds(
				userId, group.getGroupId());

			List<Role> groupRoles = _roleLocalService.getRoles(roleIds);

			roles.addAll(groupRoles);

			Iterator<Role> iterator = groupRoles.iterator();

			while (iterator.hasNext()) {
				Role groupRole = iterator.next();

				if ((groupRole.getType() != RoleConstants.TYPE_ORGANIZATION) &&
					(groupRole.getType() != RoleConstants.TYPE_SITE)) {

					iterator.remove();
				}
			}

			if (group.isOrganization() &&
				!groupRoles.contains(organizationUserRole)) {

				groupRoles.add(organizationUserRole);
			}

			if (group.isSite() && !groupRoles.contains(siteMemberRole)) {
				groupRoles.add(siteMemberRole);
			}

			_addGroup(group, groupRoles, usersGroupIdsRoles);

			_addGroup(group.getStagingGroup(), groupRoles, usersGroupIdsRoles);

			termsCount += groupRoles.size();

			if (termsCount > permissionTermsLimit) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Skipping presearch permission checking due to ",
							"too many roles, groups, and group roles: ",
							String.valueOf(termsCount), " > ",
							String.valueOf(permissionTermsLimit)));
				}

				return null;
			}
		}

		return new SearchPermissionContext(roles, usersGroupIdsRoles);
	}

	private BooleanFilter _getPermissionBooleanFilter(
			long companyId, long[] searchGroupIds, long userId,
			String className, BooleanFilter booleanFilter,
			SearchContext searchContext)
		throws Exception {

		Indexer<?> indexer = _indexerRegistry.getIndexer(className);

		if (!indexer.isPermissionAware()) {
			return booleanFilter;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		if ((user == null) || (user.getUserId() != userId)) {
			user = _userLocalService.fetchUser(userId);

			if (user == null) {
				return booleanFilter;
			}

			permissionChecker = PermissionCheckerFactoryUtil.create(user);
		}

		Object searchPermissionContextObject = searchContext.getAttribute(
			"searchPermissionContext");

		SearchPermissionContext searchPermissionContext = null;

		if (searchPermissionContextObject != null) {
			if (searchPermissionContextObject ==
					_NULL_SEARCH_PERMISSION_CONTEXT) {

				return booleanFilter;
			}

			searchPermissionContext =
				(SearchPermissionContext)searchPermissionContextObject;
		}
		else if (!permissionChecker.isCompanyAdmin(companyId)) {
			searchPermissionContext = _createSearchPermissionContext(
				companyId, userId, permissionChecker);
		}

		if (searchPermissionContext == null) {
			searchContext.setAttribute(
				"searchPermissionContext", _NULL_SEARCH_PERMISSION_CONTEXT);

			return booleanFilter;
		}

		searchContext.setAttribute(
			"searchPermissionContext", searchPermissionContext);

		return _getPermissionFilter(
			companyId, searchGroupIds, userId, permissionChecker, className,
			booleanFilter, searchPermissionContext);
	}

	private BooleanFilter _getPermissionFilter(
			long companyId, long[] searchGroupIds, long userId,
			PermissionChecker permissionChecker, String className,
			BooleanFilter booleanFilter,
			SearchPermissionContext searchPermissionContext)
		throws Exception {

		List<UsersGroupIdRoles> usersGroupIdsRoles =
			searchPermissionContext._usersGroupIdsRoles;

		BooleanFilter permissionBooleanFilter = new BooleanFilter();

		if (userId > 0) {
			permissionBooleanFilter.addTerm(Field.USER_ID, userId);
		}

		TermsFilter groupsTermsFilter = new TermsFilter(Field.GROUP_ID);
		TermsFilter groupRolesTermsFilter =
			searchPermissionContext._groupRolesTermsFilter;
		TermsFilter rolesTermsFilter =
			searchPermissionContext._rolesTermsFilter;

		long[] roleIds = searchPermissionContext._roleIds;
		long[] regularRoleIds = searchPermissionContext._regularRoleIds;

		if (_resourcePermissionLocalService.hasResourcePermission(
				companyId, className, ResourceConstants.SCOPE_COMPANY,
				String.valueOf(companyId), roleIds, ActionKeys.VIEW)) {

			return booleanFilter;
		}

		if (_resourcePermissionLocalService.hasResourcePermission(
				companyId, className, ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				regularRoleIds, ActionKeys.VIEW)) {

			return booleanFilter;
		}

		for (UsersGroupIdRoles usersGroupIdRoles : usersGroupIdsRoles) {
			long groupId = usersGroupIdRoles._groupId;
			List<Role> groupRoles = usersGroupIdRoles._groupRoles;

			if (permissionChecker.isGroupAdmin(groupId) ||
				_resourcePermissionLocalService.hasResourcePermission(
					companyId, className, ResourceConstants.SCOPE_GROUP,
					String.valueOf(groupId), roleIds, ActionKeys.VIEW) ||
				_resourcePermissionLocalService.hasResourcePermission(
					companyId, className,
					ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					ListUtil.toLongArray(groupRoles, Role.ROLE_ID_ACCESSOR),
					ActionKeys.VIEW)) {

				groupsTermsFilter.addValue(String.valueOf(groupId));
			}
		}

		if (ArrayUtil.isNotEmpty(searchGroupIds)) {
			for (long searchGroupId : searchGroupIds) {
				if (!searchPermissionContext.containsGroupId(searchGroupId) &&
					_resourcePermissionLocalService.hasResourcePermission(
						companyId, className, ResourceConstants.SCOPE_GROUP,
						String.valueOf(searchGroupId), roleIds,
						ActionKeys.VIEW)) {

					groupsTermsFilter.addValue(String.valueOf(searchGroupId));
				}
			}
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

		if (booleanFilter != null) {
			booleanFilter.add(permissionBooleanFilter, BooleanClauseOccur.MUST);

			return booleanFilter;
		}

		return permissionBooleanFilter;
	}

	private static final String _NULL_SEARCH_PERMISSION_CONTEXT =
		StringPool.BLANK;

	private static final Log _log = LogFactoryUtil.getLog(
		SearchPermissionCheckerImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	private volatile SearchPermissionCheckerConfiguration
		_searchPermissionCheckerConfiguration;

	@Reference
	private UserLocalService _userLocalService;

	private static class SearchPermissionContext implements Serializable {

		public boolean containsGroupId(long groupId) {
			for (UsersGroupIdRoles usersGroupIdRoles : _usersGroupIdsRoles) {
				if (groupId == usersGroupIdRoles._groupId) {
					return true;
				}
			}

			return false;
		}

		private SearchPermissionContext(
			Set<Role> roles, List<UsersGroupIdRoles> usersGroupIdsRoles) {

			_usersGroupIdsRoles = usersGroupIdsRoles;

			List<Long> roleIds = new ArrayList<>(roles.size());
			List<Long> regularRoleIds = new ArrayList<>();

			for (Role role : roles) {
				roleIds.add(role.getRoleId());

				if (role.getType() == RoleConstants.TYPE_REGULAR) {
					regularRoleIds.add(role.getRoleId());
				}

				_rolesTermsFilter.addValue(String.valueOf(role.getRoleId()));
			}

			_roleIds = ArrayUtil.toLongArray(roleIds);
			_regularRoleIds = ArrayUtil.toLongArray(regularRoleIds);

			for (UsersGroupIdRoles usersGroupIdRoles : _usersGroupIdsRoles) {
				long groupId = usersGroupIdRoles._groupId;
				List<Role> groupRoles = usersGroupIdRoles._groupRoles;

				for (Role groupRole : groupRoles) {
					_groupRolesTermsFilter.addValue(
						groupId + StringPool.DASH + groupRole.getRoleId());
				}
			}
		}

		private static final long serialVersionUID = 1L;

		private final TermsFilter _groupRolesTermsFilter = new TermsFilter(
			Field.GROUP_ROLE_ID);
		private final long[] _regularRoleIds;
		private final long[] _roleIds;
		private final TermsFilter _rolesTermsFilter = new TermsFilter(
			Field.ROLE_ID);
		private final List<UsersGroupIdRoles> _usersGroupIdsRoles;

	}

	private static class UsersGroupIdRoles implements Serializable {

		private UsersGroupIdRoles(long groupId, List<Role> groupRoles) {
			_groupId = groupId;
			_groupRoles = groupRoles;
		}

		private static final long serialVersionUID = 1L;

		private final long _groupId;
		private final List<Role> _groupRoles;

	}

}
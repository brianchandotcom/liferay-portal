/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.DuplicateRoleException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.RequiredRoleException;
import com.liferay.portal.RoleNameException;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCachable;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.spring.aop.Skip;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.base.RoleLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class RoleLocalServiceImpl extends RoleLocalServiceBaseImpl {

	public Role addRole(
			long userId, long companyId, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			int type)
		throws PortalException, SystemException {

		return addRole(
			userId, companyId, name, titleMap, descriptionMap, type, null, 0);
	}

	public Role addRole(
			long userId, long companyId, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			int type, String className, long classPK)
		throws PortalException, SystemException {

		// Role

		className = GetterUtil.getString(className);
		long classNameId = PortalUtil.getClassNameId(className);

		long roleId = counterLocalService.increment();

		if ((classNameId <= 0) || className.equals(Role.class.getName())) {
			classNameId = PortalUtil.getClassNameId(Role.class);
			classPK = roleId;
		}

		validate(0, companyId, classNameId, name);

		Role role = rolePersistence.create(roleId);

		role.setCompanyId(companyId);
		role.setClassNameId(classNameId);
		role.setClassPK(classPK);
		role.setName(name);
		role.setTitleMap(titleMap);
		role.setDescriptionMap(descriptionMap);
		role.setType(type);

		rolePersistence.update(role, false);

		// Resources

		if (userId > 0) {
			resourceLocalService.addResources(
				companyId, 0, userId, Role.class.getName(), role.getRoleId(),
				false, false, false);

			Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

			indexer.reindex(userId);
		}

		return role;
	}

	public void addUserRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		userPersistence.addRoles(userId, roleIds);

		Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

		indexer.reindex(userId);

		PermissionCacheUtil.clearCache();
	}

	public void checkSystemRoles() throws PortalException, SystemException {
		List<Company> companies = companyLocalService.getCompanies();

		for (Company company : companies) {
			checkSystemRoles(company.getCompanyId());
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkSystemRoles(long companyId)
		throws PortalException, SystemException {

		String companyIdHexString = StringUtil.toHexString(companyId);

		for (Role role : roleFinder.findBySystem(companyId)) {
			_systemRolesMap.put(
				companyIdHexString.concat(role.getName()), role);
		}

		// Regular roles

		String[] systemRoles = PortalUtil.getSystemRoles();

		for (String name : systemRoles) {
			String key =
				"system.role." +
					StringUtil.replace(name, CharPool.SPACE, CharPool.PERIOD) +
						".description";

			Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

			descriptionMap.put(LocaleUtil.getDefault(), PropsUtil.get(key));

			int type = RoleConstants.TYPE_REGULAR;

			checkSystemRole(companyId, name, descriptionMap, type);
		}

		// Organization roles

		String[] systemOrganizationRoles =
			PortalUtil.getSystemOrganizationRoles();

		for (String name : systemOrganizationRoles) {
			String key =
				"system.organization.role." +
					StringUtil.replace(name, CharPool.SPACE, CharPool.PERIOD) +
						".description";

			Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

			descriptionMap.put(LocaleUtil.getDefault(), PropsUtil.get(key));

			int type = RoleConstants.TYPE_ORGANIZATION;

			checkSystemRole(companyId, name, descriptionMap, type);
		}

		// Site roles

		String[] systemSiteRoles = PortalUtil.getSystemSiteRoles();

		for (String name : systemSiteRoles) {
			String key =
				"system.site.role." +
					StringUtil.replace(name, CharPool.SPACE, CharPool.PERIOD) +
						".description";

			Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

			descriptionMap.put(LocaleUtil.getDefault(), PropsUtil.get(key));

			int type = RoleConstants.TYPE_SITE;

			checkSystemRole(companyId, name, descriptionMap, type);
		}
	}

	@Override
	public void deleteRole(long roleId)
		throws PortalException, SystemException {

		Role role = rolePersistence.findByPrimaryKey(roleId);

		deleteRole(role);
	}

	@Override
	public void deleteRole(Role role)
		throws PortalException, SystemException {

		if (PortalUtil.isSystemRole(role.getName())) {
			throw new RequiredRoleException();
		}

		// Resources

		String className = role.getClassName();
		long classNameId = role.getClassNameId();

		if ((classNameId <= 0) || className.equals(Role.class.getName())) {
			resourceLocalService.deleteResource(
				role.getCompanyId(), Role.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, role.getRoleId());
		}

		if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) ||
			(role.getType() == RoleConstants.TYPE_SITE)) {

			userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
				role.getRoleId());

			userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByRoleId(
				role.getRoleId());
		}

		// Role

		rolePersistence.remove(role);

		// Permission cache

		PermissionCacheUtil.clearCache();
	}

	@Skip
	public Role fetchRole(long companyId, String name) throws SystemException {

		Role role = _systemRolesMap.get(
			StringUtil.toHexString(companyId).concat(name));

		if (role != null) {
			return role;
		}

		return roleLocalService.loadRoleFetch(companyId, name);
	}

	public Role getDefaultGroupRole(long groupId)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		if (group.isLayout()) {
			Layout layout = layoutLocalService.getLayout(group.getClassPK());

			group = layout.getGroup();
		}

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		Role role = null;

		if (group.isCompany()) {
			role = getRole(group.getCompanyId(), RoleConstants.USER);
		}
		else if (group.isLayoutPrototype() || group.isLayoutSetPrototype() ||
				 group.isRegularSite() || group.isSite()) {

			role = getRole(group.getCompanyId(), RoleConstants.SITE_MEMBER);
		}
		else if (group.isOrganization()) {
			role = getRole(
				group.getCompanyId(), RoleConstants.ORGANIZATION_USER);
		}
		else if (group.isUser() || group.isUserGroup()) {
			role = getRole(group.getCompanyId(), RoleConstants.POWER_USER);
		}
		else {
			role = getRole(group.getCompanyId(), RoleConstants.USER);
		}

		return role;
	}

	public List<Role> getGroupRoles(long groupId) throws SystemException {
		return groupPersistence.getRoles(groupId);
	}

	public Map<String, List<String>> getResourceRoles(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		return roleFinder.findByC_N_S_P(companyId, name, scope, primKey);
	}

	public List<Role> getResourceRoles(
			long companyId, String name, int scope, String primKey,
			String actionId)
		throws SystemException {

		return roleFinder.findByC_N_S_P_A(
			companyId, name, scope, primKey, actionId);
	}

	@Override
	public Role getRole(long roleId) throws PortalException, SystemException {
		return rolePersistence.findByPrimaryKey(roleId);
	}

	@Skip
	public Role getRole(long companyId, String name)
		throws PortalException, SystemException {

		Role role = _systemRolesMap.get(
			StringUtil.toHexString(companyId).concat(name));

		if (role != null) {
			return role;
		}

		return roleLocalService.loadRoleFind(companyId, name);
	}

	public List<Role> getRoles(int type, String subtype)
		throws SystemException {

		return rolePersistence.findByT_S(type, subtype);
	}

	public List<Role> getRoles(long companyId) throws SystemException {
		return rolePersistence.findByCompanyId(companyId);
	}

	public List<Role> getRoles(long[] roleIds)
		throws PortalException, SystemException {

		List<Role> roles = new ArrayList<Role>(roleIds.length);

		for (long roleId : roleIds) {
			Role role = getRole(roleId);

			roles.add(role);
		}

		return roles;
	}

	public List<Role> getSubtypeRoles(String subtype) throws SystemException {
		return rolePersistence.findBySubtype(subtype);
	}

	public int getSubtypeRolesCount(String subtype) throws SystemException {
		return rolePersistence.countBySubtype(subtype);
	}

	public Role getTeamRole(long companyId, long teamId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(Team.class);

		return rolePersistence.findByC_C_C(companyId, classNameId, teamId);
	}

	public List<Role> getUserGroupGroupRoles(long userId, long groupId)
		throws SystemException {

		return roleFinder.findByUserGroupGroupRole(userId, groupId);
	}

	public List<Role> getUserGroupRoles(long userId, long groupId)
		throws SystemException {

		return roleFinder.findByUserGroupRole(userId, groupId);
	}

	public List<Role> getUserRelatedRoles(long userId, List<Group> groups)
		throws SystemException {

		if ((groups == null) || groups.isEmpty()) {
			return Collections.emptyList();
		}

		return roleFinder.findByU_G(userId, groups);
	}

	public List<Role> getUserRelatedRoles(long userId, long groupId)
		throws SystemException {

		return roleFinder.findByU_G(userId, groupId);
	}

	public List<Role> getUserRelatedRoles(long userId, long[] groupIds)
		throws SystemException {

		return roleFinder.findByU_G(userId, groupIds);
	}

	public List<Role> getUserRoles(long userId) throws SystemException {
		return userPersistence.getRoles(userId);
	}

	public boolean hasUserRole(long userId, long roleId)
		throws SystemException {

		return userPersistence.containsRole(userId, roleId);
	}

	/**
	 * Returns <code>true</code> if the user has the regular role.
	 *
	 * @return <code>true</code> if the user has the regular role
	 */
	@ThreadLocalCachable
	public boolean hasUserRole(
			long userId, long companyId, String name, boolean inherited)
		throws PortalException, SystemException {

		Role role = rolePersistence.findByC_N(companyId, name);

		if (role.getType() != RoleConstants.TYPE_REGULAR) {
			throw new IllegalArgumentException(name + " is not a regular role");
		}

		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		if (userId == defaultUserId) {
			if (name.equals(RoleConstants.GUEST)) {
				return true;
			}
			else {
				return false;
			}
		}

		if (inherited) {
			if (userPersistence.containsRole(userId, role.getRoleId())) {
				return true;
			}

			ThreadLocalCache<Integer> threadLocalCache =
				ThreadLocalCacheManager.getThreadLocalCache(
					Lifecycle.REQUEST, _COUNT_BY_R_U_CACHE_NAME);

			String key = String.valueOf(role.getRoleId()).concat(
				String.valueOf(userId));

			Integer value = threadLocalCache.get(key);

			if (value == null) {
				value = roleFinder.countByR_U(role.getRoleId(), userId);

				threadLocalCache.put(key, value);
			}

			if (value > 0) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return userPersistence.containsRole(userId, role.getRoleId());
		}
	}

	/**
	 * Returns <code>true</code> if the user has any one of the specified
	 * regular roles.
	 *
	 * @return <code>true</code> if the user has the regular role
	 */
	public boolean hasUserRoles(
			long userId, long companyId, String[] names, boolean inherited)
		throws PortalException, SystemException {

		for (String name : names) {
			if (hasUserRole(userId, companyId, name, inherited)) {
				return true;
			}
		}

		return false;
	}

	public Role loadRoleFetch(long companyId, String name)
		throws SystemException {
		return rolePersistence.fetchByC_N(companyId, name);
	}

	public Role loadRoleFind(long companyId, String name)
		throws PortalException, SystemException {
		return rolePersistence.findByC_N(companyId, name);
	}

	public List<Role> search(
			long companyId, String keywords, Integer[] types, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		return search(
			companyId, keywords, types, new LinkedHashMap<String, Object>(),
			start, end, obc);
	}

	public List<Role> search(
			long companyId, String keywords, Integer[] types,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return roleFinder.findByKeywords(
			companyId, keywords, types, params, start, end, obc);
	}

	public List<Role> search(
			long companyId, String name, String description, Integer[] types,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		return search(
			companyId, name, description, types,
			new LinkedHashMap<String, Object>(), start, end, obc);
	}

	public List<Role> search(
			long companyId, String name, String description, Integer[] types,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return roleFinder.findByC_N_D_T(
			companyId, name, description, types, params, true, start, end, obc);
	}

	public int searchCount(
			long companyId, String keywords, Integer[] types)
		throws SystemException {

		return searchCount(
			companyId, keywords, types, new LinkedHashMap<String, Object>());
	}

	public int searchCount(
			long companyId, String keywords, Integer[] types,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		return roleFinder.countByKeywords(companyId, keywords, types, params);
	}

	public int searchCount(
			long companyId, String name, String description, Integer[] types)
		throws SystemException {

		return searchCount(
			companyId, name, description, types,
			new LinkedHashMap<String, Object>());
	}

	public int searchCount(
			long companyId, String name, String description, Integer[] types,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		return roleFinder.countByC_N_D_T(
			companyId, name, description, types, params, true);
	}

	public void setUserRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		roleIds = UsersAdminUtil.addRequiredRoles(userId, roleIds);

		userPersistence.setRoles(userId, roleIds);

		Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

		indexer.reindex(userId);

		PermissionCacheUtil.clearCache();
	}

	public void unsetUserRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		roleIds = UsersAdminUtil.removeRequiredRoles(userId, roleIds);

		userPersistence.removeRoles(userId, roleIds);

		Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

		indexer.reindex(userId);

		PermissionCacheUtil.clearCache();
	}

	public Role updateRole(
			long roleId, String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String subtype)
		throws PortalException, SystemException {

		Role role = rolePersistence.findByPrimaryKey(roleId);

		validate(roleId, role.getCompanyId(), role.getClassNameId(), name);

		if (PortalUtil.isSystemRole(role.getName())) {
			name = role.getName();
			subtype = null;
		}

		role.setName(name);
		role.setTitleMap(titleMap);
		role.setDescriptionMap(descriptionMap);
		role.setSubtype(subtype);

		rolePersistence.update(role, false);

		return role;
	}

	protected void checkSystemRole(
			long companyId, String name, Map<Locale, String> descriptionMap,
			int type)
		throws PortalException, SystemException {

		String companyIdHexString = StringUtil.toHexString(companyId);

		String roleCacheKey = companyIdHexString.concat(name);

		Role role = _systemRolesMap.get(roleCacheKey);

		try {
			if (role == null) {
				role = rolePersistence.findByC_N(companyId, name);
			}

			if (!role.getDescription().equals(descriptionMap)) {
				role.setDescriptionMap(descriptionMap);

				roleLocalService.updateRole(role, false);
			}
		}
		catch (NoSuchRoleException nsre) {
			role = roleLocalService.addRole(
				0, companyId, name, null, descriptionMap, type);

			if (name.equals(RoleConstants.USER)) {
				initPersonalControlPanelPortletsPermissions(role);
			}
		}

		_systemRolesMap.put(roleCacheKey, role);
	}

	protected String[] getDefaultControlPanelPortlets() {
		return new String[] {
			PortletKeys.MY_WORKFLOW_TASKS, PortletKeys.MY_WORKFLOW_INSTANCES
		};
	}

	protected void initPersonalControlPanelPortletsPermissions(Role role)
		throws PortalException, SystemException {

		for (String portletId : getDefaultControlPanelPortlets()) {
			setRolePermissions(
				role, portletId,
				new String[] {ActionKeys.ACCESS_IN_CONTROL_PANEL});
		}
	}

	protected void setRolePermissions(
			Role role, String name, String[] actionIds)
		throws PortalException, SystemException {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			if (resourceBlockLocalService.isSupported(name)) {
				resourceBlockLocalService.setCompanyScopePermissions(
					role.getCompanyId(), name, role.getRoleId(),
					Arrays.asList(actionIds));
			}
			else {
				resourcePermissionLocalService.setResourcePermissions(
					role.getCompanyId(), name, ResourceConstants.SCOPE_COMPANY,
					"0", role.getRoleId(), actionIds);
			}
		}
		else {
			permissionLocalService.setRolePermissions(
				role.getRoleId(), role.getCompanyId(), name,
				ResourceConstants.SCOPE_COMPANY, "0", actionIds);
		}
	}

	protected void validate(
			long roleId, long companyId, long classNameId, String name)
		throws PortalException, SystemException {

		if (classNameId == PortalUtil.getClassNameId(Role.class)) {
			if (Validator.isNull(name) ||
				(name.indexOf(CharPool.COMMA) != -1) ||
				(name.indexOf(CharPool.STAR) != -1)) {

				throw new RoleNameException();
			}

			if (Validator.isNumber(name) &&
				!PropsValues.ROLES_NAME_ALLOW_NUMERIC) {

				throw new RoleNameException();
			}
		}

		try {
			Role role = roleFinder.findByC_N(companyId, name);

			if (role.getRoleId() != roleId) {
				throw new DuplicateRoleException();
			}
		}
		catch (NoSuchRoleException nsge) {
		}
	}

	private static final String _COUNT_BY_R_U_CACHE_NAME = "COUNT_BY_R_U";

	private Map<String, Role> _systemRolesMap = new HashMap<String, Role>();

}
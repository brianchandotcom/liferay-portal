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

package com.liferay.portal.service.impl;

import com.liferay.admin.kernel.util.PortalMyAccountApplicationType;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCachable;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.DuplicateRoleException;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredRoleException;
import com.liferay.portal.kernel.exception.RoleNameException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.base.RoleLocalServiceBaseImpl;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Provides the local service for accessing, adding, checking, deleting, and
 * updating roles.
 *
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class RoleLocalServiceImpl extends RoleLocalServiceBaseImpl {

	/**
	 * Adds a role with additional parameters. The user is reindexed after role
	 * is added.
	 *
	 * @param  userId the primary key of the user
	 * @param  className the name of the class for which the role is created
	 *         (optionally <code>null</code>)
	 * @param  classPK the primary key of the class for which the role is
	 *         created (optionally <code>0</code>)
	 * @param  name the role's name
	 * @param  titleMap the role's localized titles (optionally
	 *         <code>null</code>)
	 * @param  descriptionMap the role's localized descriptions (optionally
	 *         <code>null</code>)
	 * @param  type the role's type (optionally <code>0</code>)
	 * @param  subtype the role's subtype (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set expando bridge attributes for the
	 *         role.
	 * @return the role
	 */
	@Override
	public Role addRole(
			long userId, String className, long classPK, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			int type, String subtype, ServiceContext serviceContext)
		throws PortalException {

		// Role

		User user = userPersistence.findByPrimaryKey(userId);

		className = GetterUtil.getString(className);

		long classNameId = classNameLocalService.getClassNameId(className);

		long roleId = counterLocalService.increment();

		if ((classNameId <= 0) || className.equals(Role.class.getName())) {
			classNameId = classNameLocalService.getClassNameId(Role.class);
			classPK = roleId;
		}

		validate(0, user.getCompanyId(), classNameId, name);

		Role role = rolePersistence.create(roleId);

		if (serviceContext != null) {
			role.setUuid(serviceContext.getUuid());
		}

		role.setCompanyId(user.getCompanyId());
		role.setUserId(user.getUserId());
		role.setUserName(user.getFullName());
		role.setClassNameId(classNameId);
		role.setClassPK(classPK);
		role.setName(name);
		role.setTitleMap(titleMap);
		role.setDescriptionMap(descriptionMap);
		role.setType(type);
		role.setSubtype(subtype);
		role.setExpandoBridgeAttributes(serviceContext);

		role = rolePersistence.update(role);

		// Resources

		long ownerId = userId;

		if (user.isDefaultUser()) {
			ownerId = 0;
		}

		resourceLocalService.addResources(
			user.getCompanyId(), 0, ownerId, Role.class.getName(),
			role.getRoleId(), false, false, false);

		if (!user.isDefaultUser()) {
			resourceLocalService.addResources(
				user.getCompanyId(), 0, userId, Role.class.getName(),
				role.getRoleId(), false, false, false);

			if (!ExportImportThreadLocal.isImportInProcess()) {
				reindex(userId);
			}
		}

		return role;
	}

	/**
	 * Adds the role to the user. The user is reindexed after the role is added.
	 *
	 * @param userId the primary key of the user
	 * @param roleId the primary key of the role
	 * @see   com.liferay.portal.kernel.service.persistence.UserPersistence#addRole(
	 *        long, long)
	 */
	@Override
	public void addUserRole(long userId, long roleId) throws PortalException {
		userPersistence.addRole(userId, roleId);

		reindex(userId);
	}

	/**
	 * Adds the role to the user. The user is reindexed after the role is added.
	 *
	 * @param userId the primary key of the user
	 * @param role the role
	 * @see   com.liferay.portal.kernel.service.persistence.UserPersistence#addRole(
	 *        long, Role)
	 */
	@Override
	public void addUserRole(long userId, Role role) throws PortalException {
		userPersistence.addRole(userId, role);

		reindex(userId);
	}

	/**
	 * Adds the roles to the user. The user is reindexed after the roles are
	 * added.
	 *
	 * @param userId the primary key of the user
	 * @param roles the roles
	 * @see   com.liferay.portal.kernel.service.persistence.UserPersistence#addRoles(
	 *        long, List)
	 */
	@Override
	public void addUserRoles(long userId, List<Role> roles)
		throws PortalException {

		userPersistence.addRoles(userId, roles);

		reindex(userId);
	}

	/**
	 * Adds the roles to the user. The user is reindexed after the roles are
	 * added.
	 *
	 * @param userId the primary key of the user
	 * @param roleIds the primary keys of the roles
	 * @see   com.liferay.portal.kernel.service.persistence.UserPersistence#addRoles(
	 *        long, long[])
	 */
	@Override
	public void addUserRoles(long userId, long[] roleIds)
		throws PortalException {

		userPersistence.addRoles(userId, roleIds);

		reindex(userId);
	}

	/**
	 * Checks to ensure that the system roles map has appropriate default roles
	 * in each company.
	 */
	@Override
	public void checkSystemRoles() throws PortalException {
		List<Company> companies = companyLocalService.getCompanies();

		for (Company company : companies) {
			checkSystemRoles(company.getCompanyId());
		}
	}

	/**
	 * Checks to ensure that the system roles map has appropriate default roles
	 * in the company.
	 *
	 * @param companyId the primary key of the company
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkSystemRoles(long companyId) throws PortalException {
		String companyIdHexString = StringUtil.toHexString(companyId);

		List<Role> roles = null;

		try {
			roles = roleFinder.findBySystem(companyId);
		}
		catch (Exception exception) {

			// LPS-34324

			runSQL("alter table Role_ add uuid_ VARCHAR(75) null");
			runSQL("alter table Role_ add userId LONG");
			runSQL("alter table Role_ add userName VARCHAR(75) null");
			runSQL("alter table Role_ add createDate DATE null");
			runSQL("alter table Role_ add modifiedDate DATE null");

			roles = roleFinder.findBySystem(companyId);
		}

		for (Role role : roles) {
			_systemRolesMap.put(
				companyIdHexString.concat(role.getName()), role);
		}

		// Regular roles

		String[] systemRoles = PortalUtil.getSystemRoles();

		for (String name : systemRoles) {
			Map<Locale, String> descriptionMap = HashMapBuilder.put(
				LocaleUtil.getDefault(),
				PropsUtil.get(
					StringBundler.concat(
						"system.role.",
						StringUtil.replace(
							name, CharPool.SPACE, CharPool.PERIOD),
						".description"))
			).build();

			int type = RoleConstants.TYPE_REGULAR;

			checkSystemRole(companyId, name, descriptionMap, type);
		}

		// Organization roles

		String[] systemOrganizationRoles =
			PortalUtil.getSystemOrganizationRoles();

		for (String name : systemOrganizationRoles) {
			Map<Locale, String> descriptionMap = HashMapBuilder.put(
				LocaleUtil.getDefault(),
				PropsUtil.get(
					StringBundler.concat(
						"system.organization.role.",
						StringUtil.replace(
							name, CharPool.SPACE, CharPool.PERIOD),
						".description"))
			).build();

			int type = RoleConstants.TYPE_ORGANIZATION;

			checkSystemRole(companyId, name, descriptionMap, type);
		}

		// Site roles

		String[] systemSiteRoles = PortalUtil.getSystemSiteRoles();

		for (String name : systemSiteRoles) {
			Map<Locale, String> descriptionMap = HashMapBuilder.put(
				LocaleUtil.getDefault(),
				PropsUtil.get(
					StringBundler.concat(
						"system.site.role.",
						StringUtil.replace(
							name, CharPool.SPACE, CharPool.PERIOD),
						".description"))
			).build();

			int type = RoleConstants.TYPE_SITE;

			checkSystemRole(companyId, name, descriptionMap, type);
		}

		String[] allSystemRoles = ArrayUtil.append(
			systemRoles, systemOrganizationRoles, systemSiteRoles);

		for (String roleName : allSystemRoles) {
			Role role = getRole(companyId, roleName);

			resourceLocalService.addResources(
				companyId, 0, 0, Role.class.getName(), role.getRoleId(), false,
				false, false);
		}

		// All users should be able to view all system roles

		Role userRole = getRole(companyId, RoleConstants.USER);

		for (String roleName : allSystemRoles) {
			Role role = getRole(companyId, roleName);

			resourcePermissionLocalService.setResourcePermissions(
				companyId, Role.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(role.getRoleId()), userRole.getRoleId(),
				new String[] {ActionKeys.VIEW});
		}
	}

	/**
	 * Removes every role from the user. The user is reindexed after the roles
	 * are removed.
	 *
	 * @param userId the primary key of the user
	 * @see   com.liferay.portal.kernel.service.persistence.UserPersistence#clearRoles(
	 *        long)
	 */
	@Override
	public void clearUserRoles(long userId) throws PortalException {
		userPersistence.clearRoles(userId);

		reindex(userId);
	}

	/**
	 * Deletes the role with the primary key and its associated permissions.
	 *
	 * @param  roleId the primary key of the role
	 * @return the deleted role
	 */
	@Override
	public Role deleteRole(long roleId) throws PortalException {
		Role role = rolePersistence.findByPrimaryKey(roleId);

		return roleLocalService.deleteRole(role);
	}

	/**
	 * Deletes the role and its associated permissions.
	 *
	 * @param  role the role
	 * @return the deleted role
	 */
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public Role deleteRole(Role role) throws PortalException {
		if (role.isSystem() && !CompanyThreadLocal.isDeleteInProcess()) {
			throw new RequiredRoleException();
		}

		// Resources

		List<ResourcePermission> resourcePermissions =
			resourcePermissionPersistence.findByRoleId(role.getRoleId());

		for (ResourcePermission resourcePermission : resourcePermissions) {
			resourcePermissionLocalService.deleteResourcePermission(
				resourcePermission);
		}

		String className = role.getClassName();
		long classNameId = role.getClassNameId();

		if ((classNameId <= 0) || className.equals(Role.class.getName())) {
			resourceLocalService.deleteResource(
				role.getCompanyId(), Role.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, role.getRoleId());
		}

		if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) ||
			(role.getType() == RoleConstants.TYPE_SITE)) {

			List<Group> groups = groupPersistence.findByC_S(
				role.getCompanyId(), true);

			for (Group group : groups) {
				UnicodeProperties typeSettingsProperties =
					group.getTypeSettingsProperties();

				List<Long> defaultSiteRoleIds = ListUtil.fromArray(
					StringUtil.split(
						typeSettingsProperties.getProperty(
							"defaultSiteRoleIds"),
						0L));

				if (defaultSiteRoleIds.contains(role.getRoleId())) {
					defaultSiteRoleIds.remove(role.getRoleId());

					typeSettingsProperties.setProperty(
						"defaultSiteRoleIds",
						ListUtil.toString(
							defaultSiteRoleIds, StringPool.BLANK));

					groupLocalService.updateGroup(
						group.getGroupId(), typeSettingsProperties.toString());
				}
			}

			userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
				role.getRoleId());

			userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByRoleId(
				role.getRoleId());
		}

		// Role

		rolePersistence.remove(role);

		// Expando

		expandoRowLocalService.deleteRows(role.getRoleId());

		return role;
	}

	/**
	 * Removes the role from the user. The user is reindexed after the role is
	 * removed.
	 *
	 * @param userId the primary key of the user
	 * @param roleId the primary key of the role
	 * @see   com.liferay.portal.kernel.service.persistence.UserPersistence#removeRole(
	 *        long, long)
	 */
	@Override
	public void deleteUserRole(long userId, long roleId)
		throws PortalException {

		userPersistence.removeRole(userId, roleId);

		reindex(userId);
	}

	/**
	 * Removes the role from the user. The user is reindexed after the role is
	 * removed.
	 *
	 * @param userId the primary key of the user
	 * @param role the role
	 * @see   com.liferay.portal.kernel.service.persistence.UserPersistence#removeRole(
	 *        long, Role)
	 */
	@Override
	public void deleteUserRole(long userId, Role role) throws PortalException {
		userPersistence.removeRole(userId, role);

		reindex(userId);
	}

	/**
	 * Removes the roles from the user. The user is reindexed after the roles
	 * are removed.
	 *
	 * @param userId the primary key of the user
	 * @param roles the roles
	 * @see   com.liferay.portal.kernel.service.persistence.UserPersistence#removeRoles(
	 *        long, List)
	 */
	@Override
	public void deleteUserRoles(long userId, List<Role> roles)
		throws PortalException {

		userPersistence.removeRoles(userId, roles);

		reindex(userId);
	}

	/**
	 * Removes the roles from the user. The user is reindexed after the roles
	 * are removed.
	 *
	 * @param userId the primary key of the user
	 * @param roleIds the primary keys of the roles
	 * @see   com.liferay.portal.kernel.service.persistence.UserPersistence#removeRoles(
	 *        long, long[])
	 */
	@Override
	public void deleteUserRoles(long userId, long[] roleIds)
		throws PortalException {

		userPersistence.removeRoles(userId, roleIds);

		reindex(userId);
	}

	/**
	 * Returns the role with the name in the company.
	 *
	 * <p>
	 * The method searches the system roles map first for default roles. If a
	 * role with the name is not found, then the method will query the database.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the role's name
	 * @return Returns the role with the name or <code>null</code> if a role
	 *         with the name could not be found in the company
	 */
	@Override
	@Transactional(enabled = false)
	public Role fetchRole(long companyId, String name) {
		String companyIdHexString = StringUtil.toHexString(companyId);

		Role role = _systemRolesMap.get(companyIdHexString.concat(name));

		if (role != null) {
			return role;
		}

		return roleLocalService.loadFetchRole(companyId, name);
	}

	@Override
	public int getAssigneesTotal(long roleId) throws PortalException {
		int assigneesTotal = 0;

		Role role = getRole(roleId);

		int type = role.getType();

		if (type == RoleConstants.TYPE_REGULAR) {
			assigneesTotal += groupLocalService.getRoleGroupsCount(roleId);
			assigneesTotal += userLocalService.getRoleUsersCount(roleId);
		}

		if (type == RoleConstants.TYPE_SITE) {
			DynamicQuery userGroupGroupRoleDynamicQuery =
				userGroupGroupRoleLocalService.dynamicQuery();

			Property property = PropertyFactoryUtil.forName(
				"primaryKey.roleId");

			userGroupGroupRoleDynamicQuery.add(property.eq(roleId));

			userGroupGroupRoleDynamicQuery.setProjection(
				ProjectionFactoryUtil.countDistinct("primaryKey.userGroupId"));

			List<?> list = userGroupRoleLocalService.dynamicQuery(
				userGroupGroupRoleDynamicQuery);

			Long count = (Long)list.get(0);

			assigneesTotal += count.intValue();
		}

		if ((type == RoleConstants.TYPE_SITE) ||
			(type == RoleConstants.TYPE_ORGANIZATION)) {

			DynamicQuery userGroupRoleDynamicQuery =
				userGroupRoleLocalService.dynamicQuery();

			Property property = PropertyFactoryUtil.forName(
				"primaryKey.roleId");

			userGroupRoleDynamicQuery.add(property.eq(roleId));

			userGroupRoleDynamicQuery.setProjection(
				ProjectionFactoryUtil.countDistinct("primaryKey.userId"));

			List<?> list = userGroupRoleLocalService.dynamicQuery(
				userGroupRoleDynamicQuery);

			Long count = (Long)list.get(0);

			assigneesTotal += count.intValue();
		}

		return assigneesTotal;
	}

	/**
	 * Returns the default role for the group with the primary key.
	 *
	 * <p>
	 * If the group is a site, then the default role is {@link
	 * RoleConstants#SITE_MEMBER}. If the group is an organization, then the
	 * default role is {@link RoleConstants#ORGANIZATION_USER}. If the group is
	 * a user or user group, then the default role is {@link
	 * RoleConstants#POWER_USER}. For all other group types, the default role is
	 * {@link RoleConstants#USER}.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @return the default role for the group with the primary key
	 */
	@Override
	public Role getDefaultGroupRole(long groupId) throws PortalException {
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
		else {
			role = getRole(group.getCompanyId(), RoleConstants.USER);
		}

		return role;
	}

	@Override
	public List<Role> getGroupRelatedRoles(long groupId)
		throws PortalException {

		List<Role> roles = new ArrayList<>();

		Group group = groupLocalService.getGroup(groupId);

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		int[] types = RoleConstants.TYPES_REGULAR;

		if (group.isOrganization()) {
			if (group.isSite()) {
				types = RoleConstants.TYPES_ORGANIZATION_AND_REGULAR_AND_SITE;
			}
			else {
				types = RoleConstants.TYPES_ORGANIZATION_AND_REGULAR;
			}
		}
		else if (group.isLayout() || group.isLayoutSetPrototype() ||
				 group.isSite() || group.isUser()) {

			types = RoleConstants.TYPES_REGULAR_AND_SITE;
		}

		roles.addAll(getRoles(group.getCompanyId(), types));

		roles.addAll(getTeamRoles(groupId));

		return roles;
	}

	@Override
	public List<Role> getGroupRolesAndTeamRoles(
		long companyId, String keywords, List<String> excludedNames,
		int[] types, long excludedTeamRoleId, long teamGroupId, int start,
		int end) {

		return roleFinder.findByGroupRoleAndTeamRole(
			companyId, keywords, excludedNames, types, excludedTeamRoleId,
			teamGroupId, start, end);
	}

	@Override
	public int getGroupRolesAndTeamRolesCount(
		long companyId, String keywords, List<String> excludedNames,
		int[] types, long excludedTeamRoleId, long teamGroupId) {

		return roleFinder.countByGroupRoleAndTeamRole(
			companyId, keywords, excludedNames, types, excludedTeamRoleId,
			teamGroupId);
	}

	/**
	 * Returns a map of role names to associated action IDs for the named
	 * resource in the company within the permission scope.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource name
	 * @param  scope the permission scope
	 * @param  primKey the primary key of the resource's class
	 * @return the role names and action IDs
	 * @see    com.liferay.portal.kernel.service.persistence.RoleFinder#findByC_N_S_P(
	 *         long, String, int, String)
	 */
	@Override
	public Map<String, List<String>> getResourceRoles(
		long companyId, String name, int scope, String primKey) {

		return roleFinder.findByC_N_S_P(companyId, name, scope, primKey);
	}

	/**
	 * Returns all the roles associated with the action ID in the company within
	 * the permission scope.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource name
	 * @param  scope the permission scope
	 * @param  primKey the primary key of the resource's class
	 * @param  actionId the name of the resource action
	 * @return the roles
	 * @see    com.liferay.portal.kernel.service.persistence.RoleFinder#findByC_N_S_P_A(
	 *         long, String, int, String, String)
	 */
	@Override
	public List<Role> getResourceRoles(
		long companyId, String name, int scope, String primKey,
		String actionId) {

		return roleFinder.findByC_N_S_P_A(
			companyId, name, scope, primKey, actionId);
	}

	/**
	 * Returns the role with the name in the company.
	 *
	 * <p>
	 * The method searches the system roles map first for default roles. If a
	 * role with the name is not found, then the method will query the database.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the role's name
	 * @return the role with the name
	 */
	@Override
	@Transactional(enabled = false)
	public Role getRole(long companyId, String name) throws PortalException {
		String companyIdHexString = StringUtil.toHexString(companyId);

		Role role = _systemRolesMap.get(companyIdHexString.concat(name));

		if (role != null) {
			return role;
		}

		return roleLocalService.loadGetRole(companyId, name);
	}

	/**
	 * Returns all the roles of the type and subtype.
	 *
	 * @param  type the role's type (optionally <code>0</code>)
	 * @param  subtype the role's subtype (optionally <code>null</code>)
	 * @return the roles of the type and subtype
	 */
	@Override
	public List<Role> getRoles(int type, String subtype) {
		return rolePersistence.findByT_S(type, subtype);
	}

	/**
	 * Returns all the roles in the company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the roles in the company
	 */
	@Override
	public List<Role> getRoles(long companyId) {
		return rolePersistence.findByCompanyId(companyId);
	}

	/**
	 * Returns all the roles with the types.
	 *
	 * @param  companyId the primary key of the company
	 * @param  types the role types (optionally <code>null</code>)
	 * @return the roles with the types
	 */
	@Override
	public List<Role> getRoles(long companyId, int[] types) {
		return rolePersistence.findByC_T(companyId, types);
	}

	@Override
	public List<Role> getRoles(
		long companyId, long classNameId, long[] classPKs, int type) {

		return rolePersistence.findByC_C_C_T(
			companyId, classNameId, classPKs, type);
	}

	/**
	 * Returns all the roles with the primary keys.
	 *
	 * @param  roleIds the primary keys of the roles
	 * @return the roles with the primary keys
	 */
	@Override
	public List<Role> getRoles(long[] roleIds) throws PortalException {
		List<Role> roles = new ArrayList<>(roleIds.length);

		for (long roleId : roleIds) {
			roles.add(getRole(roleId));
		}

		return roles;
	}

	/**
	 * Returns all the roles of the subtype.
	 *
	 * @param  subtype the role's subtype (optionally <code>null</code>)
	 * @return the roles of the subtype
	 */
	@Override
	public List<Role> getSubtypeRoles(String subtype) {
		return rolePersistence.findBySubtype(subtype);
	}

	/**
	 * Returns the number of roles of the subtype.
	 *
	 * @param  subtype the role's subtype (optionally <code>null</code>)
	 * @return the number of roles of the subtype
	 */
	@Override
	public int getSubtypeRolesCount(String subtype) {
		return rolePersistence.countBySubtype(subtype);
	}

	/**
	 * Returns the team role in the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  teamId the primary key of the team
	 * @return the team role in the company
	 */
	@Override
	public Role getTeamRole(long companyId, long teamId)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(Team.class);

		return rolePersistence.findByC_C_C(companyId, classNameId, teamId);
	}

	/**
	 * Returns the team role map for the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the team role map for the group
	 */
	@Override
	public Map<Team, Role> getTeamRoleMap(long groupId) throws PortalException {
		return getTeamRoleMap(groupId, null);
	}

	/**
	 * Returns the team roles in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the team roles in the group
	 */
	@Override
	public List<Role> getTeamRoles(long groupId) throws PortalException {
		return getTeamRoles(groupId, null);
	}

	/**
	 * Returns the team roles in the group, excluding the specified role IDs.
	 *
	 * @param  groupId the primary key of the group
	 * @param  excludedRoleIds the primary keys of the roles to exclude
	 *         (optionally <code>null</code>)
	 * @return the team roles in the group, excluding the specified role IDs
	 */
	@Override
	public List<Role> getTeamRoles(long groupId, long[] excludedRoleIds)
		throws PortalException {

		Map<Team, Role> teamRoleMap = getTeamRoleMap(groupId, excludedRoleIds);

		Collection<Role> roles = teamRoleMap.values();

		return ListUtil.fromCollection(roles);
	}

	/**
	 * Returns the team roles in the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  teamIds the primary keys of the teams
	 * @return the team roles in the company
	 */
	@Override
	public List<Role> getTeamsRoles(long companyId, long[] teamIds)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(Team.class);

		return rolePersistence.findByC_C_C(companyId, classNameId, teamIds);
	}

	/**
	 * Returns all the roles of the type.
	 *
	 * @param  type the role's type (optionally <code>0</code>)
	 * @return the range of the roles of the type
	 */
	@Override
	public List<Role> getTypeRoles(int type) {
		return rolePersistence.findByType(type);
	}

	/**
	 * Returns a range of all the roles of the type.
	 *
	 * @param  type the role's type (optionally <code>0</code>)
	 * @param  start the lower bound of the range of roles to return
	 * @param  end the upper bound of the range of roles to return (not
	 *         inclusive)
	 * @return the range of the roles of the type
	 */
	@Override
	public List<Role> getTypeRoles(int type, int start, int end) {
		return rolePersistence.findByType(type, start, end);
	}

	/**
	 * Returns the number of roles of the type.
	 *
	 * @param  type the role's type (optionally <code>0</code>)
	 * @return the number of roles of the type
	 */
	@Override
	public int getTypeRolesCount(int type) {
		return rolePersistence.countByType(type);
	}

	/**
	 * Returns all the user's roles within the user group.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return the user's roles within the user group
	 * @see    com.liferay.portal.kernel.service.persistence.RoleFinder#findByUserGroupGroupRole(
	 *         long, long)
	 */
	@Override
	public List<Role> getUserGroupGroupRoles(long userId, long groupId) {
		return roleFinder.findByUserGroupGroupRole(userId, groupId);
	}

	@Override
	public List<Role> getUserGroupGroupRoles(
		long userId, long groupId, int start, int end) {

		return roleFinder.findByUserGroupGroupRole(userId, groupId, start, end);
	}

	@Override
	public int getUserGroupGroupRolesCount(long userId, long groupId) {
		return roleFinder.countByUserGroupGroupRole(userId, groupId);
	}

	/**
	 * Returns all the user's roles within the user group.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return the user's roles within the user group
	 * @see    com.liferay.portal.kernel.service.persistence.RoleFinder#findByUserGroupRole(
	 *         long, long)
	 */
	@Override
	public List<Role> getUserGroupRoles(long userId, long groupId) {
		return roleFinder.findByUserGroupRole(userId, groupId);
	}

	/**
	 * Returns the union of all the user's roles within the groups.
	 *
	 * @param  userId the primary key of the user
	 * @param  groups the groups (optionally <code>null</code>)
	 * @return the union of all the user's roles within the groups
	 * @see    com.liferay.portal.kernel.service.persistence.RoleFinder#findByU_G(
	 *         long, List)
	 */
	@Override
	public List<Role> getUserRelatedRoles(long userId, List<Group> groups) {
		if ((groups == null) || groups.isEmpty()) {
			return Collections.emptyList();
		}

		return roleFinder.findByU_G(userId, groups);
	}

	/**
	 * Returns all the user's roles within the group.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return the user's roles within the group
	 * @see    com.liferay.portal.kernel.service.persistence.RoleFinder#findByU_G(
	 *         long, long)
	 */
	@Override
	public List<Role> getUserRelatedRoles(long userId, long groupId) {
		return roleFinder.findByU_G(userId, groupId);
	}

	/**
	 * Returns the union of all the user's roles within the groups.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupIds the primary keys of the groups
	 * @return the union of all the user's roles within the groups
	 * @see    com.liferay.portal.kernel.service.persistence.RoleFinder#findByU_G(
	 *         long, long[])
	 */
	@Override
	public List<Role> getUserRelatedRoles(long userId, long[] groupIds) {
		return roleFinder.findByU_G(userId, groupIds);
	}

	@Override
	public List<Role> getUserTeamRoles(long userId, long groupId) {
		return roleFinder.findByTeamsUser(userId, groupId);
	}

	/**
	 * Returns <code>true</code> if the user is associated with the named
	 * regular role.
	 *
	 * @param  userId the primary key of the user
	 * @param  companyId the primary key of the company
	 * @param  name the name of the role
	 * @param  inherited whether to include the user's inherited roles in the
	 *         search
	 * @return <code>true</code> if the user is associated with the regular
	 *         role; <code>false</code> otherwise
	 */
	@Override
	@ThreadLocalCachable
	public boolean hasUserRole(
			long userId, long companyId, String name, boolean inherited)
		throws PortalException {

		Role role = rolePersistence.fetchByC_N(companyId, name);

		if (role == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Role ", name, " with company ID ", companyId,
						" does not exist"));
			}

			return false;
		}

		if (role.getType() != RoleConstants.TYPE_REGULAR) {
			throw new IllegalArgumentException(name + " is not a regular role");
		}

		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		if (userId == defaultUserId) {
			if (name.equals(RoleConstants.GUEST)) {
				return true;
			}

			return false;
		}

		if (inherited) {
			if (userPersistence.containsRole(userId, role.getRoleId())) {
				return true;
			}

			ThreadLocalCache<Boolean> threadLocalCache =
				ThreadLocalCacheManager.getThreadLocalCache(
					Lifecycle.REQUEST, RoleLocalServiceImpl.class.getName());

			String roleId = String.valueOf(role.getRoleId());

			String key = roleId.concat(String.valueOf(userId));

			Boolean value = threadLocalCache.get(key);

			if (value != null) {
				return value;
			}

			value = PermissionCacheUtil.getUserRole(userId, role);

			if (value == null) {
				int count = roleFinder.countByR_U(role.getRoleId(), userId);

				if (count > 0) {
					value = true;
				}
				else {
					value = false;
				}

				PermissionCacheUtil.putUserRole(userId, role, value);
			}

			threadLocalCache.put(key, value);

			return value;
		}

		return userPersistence.containsRole(userId, role.getRoleId());
	}

	/**
	 * Returns <code>true</code> if the user has any one of the named regular
	 * roles.
	 *
	 * @param  userId the primary key of the user
	 * @param  companyId the primary key of the company
	 * @param  names the names of the roles
	 * @param  inherited whether to include the user's inherited roles in the
	 *         search
	 * @return <code>true</code> if the user has any one of the regular roles;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean hasUserRoles(
			long userId, long companyId, String[] names, boolean inherited)
		throws PortalException {

		for (String name : names) {
			if (hasUserRole(userId, companyId, name, inherited)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns a role with the name in the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the role's name (optionally <code>null</code>)
	 * @return the role with the name, or <code>null</code> if a role with the
	 *         name could not be found in the company
	 */
	@Override
	public Role loadFetchRole(long companyId, String name) {
		return rolePersistence.fetchByC_N(companyId, name);
	}

	/**
	 * Returns a role with the name in the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the role's name
	 * @return the role with the name in the company
	 */
	@Override
	public Role loadGetRole(long companyId, String name)
		throws PortalException {

		return rolePersistence.findByC_N(companyId, name);
	}

	/**
	 * Returns an ordered range of all the roles that match the keywords and
	 * types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         role's name or description (optionally <code>null</code>)
	 * @param  types the role types (optionally <code>null</code>)
	 * @param  start the lower bound of the range of roles to return
	 * @param  end the upper bound of the range of roles to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the roles (optionally
	 *         <code>null</code>)
	 * @return the ordered range of the matching roles, ordered by
	 *         <code>obc</code>
	 * @see    com.liferay.portal.kernel.service.persistence.RoleFinder
	 */
	@Override
	public List<Role> search(
		long companyId, String keywords, Integer[] types, int start, int end,
		OrderByComparator<Role> obc) {

		return search(
			companyId, keywords, types, new LinkedHashMap<String, Object>(),
			start, end, obc);
	}

	/**
	 * Returns an ordered range of all the roles that match the keywords, types,
	 * and params.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         role's name or description (optionally <code>null</code>)
	 * @param  types the role types (optionally <code>null</code>)
	 * @param  params the finder parameters. Can specify values for the
	 *         "usersRoles" key. For more information, see {@link
	 *         com.liferay.portal.kernel.service.persistence.RoleFinder}
	 * @param  start the lower bound of the range of roles to return
	 * @param  end the upper bound of the range of roles to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the roles (optionally
	 *         <code>null</code>)
	 * @return the ordered range of the matching roles, ordered by
	 *         <code>obc</code>
	 * @see    com.liferay.portal.kernel.service.persistence.RoleFinder
	 */
	@Override
	public List<Role> search(
		long companyId, String keywords, Integer[] types,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Role> obc) {

		return roleFinder.findByKeywords(
			companyId, keywords, types, params, start, end, obc);
	}

	/**
	 * Returns an ordered range of all the roles that match the name,
	 * description, and types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the role's name (optionally <code>null</code>)
	 * @param  description the role's description (optionally <code>null</code>)
	 * @param  types the role types (optionally <code>null</code>)
	 * @param  start the lower bound of the range of the roles to return
	 * @param  end the upper bound of the range of the roles to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the roles (optionally
	 *         <code>null</code>)
	 * @return the ordered range of the matching roles, ordered by
	 *         <code>obc</code>
	 * @see    com.liferay.portal.kernel.service.persistence.RoleFinder
	 */
	@Override
	public List<Role> search(
		long companyId, String name, String description, Integer[] types,
		int start, int end, OrderByComparator<Role> obc) {

		return search(
			companyId, name, description, types,
			new LinkedHashMap<String, Object>(), start, end, obc);
	}

	/**
	 * Returns an ordered range of all the roles that match the name,
	 * description, types, and params.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the role's name (optionally <code>null</code>)
	 * @param  description the role's description (optionally <code>null</code>)
	 * @param  types the role types (optionally <code>null</code>)
	 * @param  params the finder's parameters. Can specify values for the
	 *         "usersRoles" key. For more information, see {@link
	 *         com.liferay.portal.kernel.service.persistence.RoleFinder}
	 * @param  start the lower bound of the range of the roles to return
	 * @param  end the upper bound of the range of the roles to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the roles (optionally
	 *         <code>null</code>)
	 * @return the ordered range of the matching roles, ordered by
	 *         <code>obc</code>
	 * @see    com.liferay.portal.kernel.service.persistence.RoleFinder
	 */
	@Override
	public List<Role> search(
		long companyId, String name, String description, Integer[] types,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Role> obc) {

		return roleFinder.findByC_N_D_T(
			companyId, name, description, types, params, true, start, end, obc);
	}

	/**
	 * Returns the number of roles that match the keywords and types.
	 *
	 * @param  companyId the primary key of the company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         role's name or description (optionally <code>null</code>)
	 * @param  types the role types (optionally <code>null</code>)
	 * @return the number of matching roles
	 */
	@Override
	public int searchCount(long companyId, String keywords, Integer[] types) {
		return searchCount(
			companyId, keywords, types, new LinkedHashMap<String, Object>());
	}

	/**
	 * Returns the number of roles that match the keywords, types and params.
	 *
	 * @param  companyId the primary key of the company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         role's name or description (optionally <code>null</code>)
	 * @param  types the role types (optionally <code>null</code>)
	 * @param  params the finder parameters. For more information, see {@link
	 *         com.liferay.portal.kernel.service.persistence.RoleFinder}
	 * @return the number of matching roles
	 */
	@Override
	public int searchCount(
		long companyId, String keywords, Integer[] types,
		LinkedHashMap<String, Object> params) {

		return roleFinder.countByKeywords(companyId, keywords, types, params);
	}

	/**
	 * Returns the number of roles that match the name, description, and types.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the role's name (optionally <code>null</code>)
	 * @param  description the role's description (optionally <code>null</code>)
	 * @param  types the role types (optionally <code>null</code>)
	 * @return the number of matching roles
	 */
	@Override
	public int searchCount(
		long companyId, String name, String description, Integer[] types) {

		return searchCount(
			companyId, name, description, types,
			new LinkedHashMap<String, Object>());
	}

	/**
	 * Returns the number of roles that match the name, description, types, and
	 * params.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the role's name (optionally <code>null</code>)
	 * @param  description the role's description (optionally <code>null</code>)
	 * @param  types the role types (optionally <code>null</code>)
	 * @param  params the finder parameters. Can specify values for the
	 *         "usersRoles" key. For more information, see {@link
	 *         com.liferay.portal.kernel.service.persistence.RoleFinder}
	 * @return the number of matching roles
	 */
	@Override
	public int searchCount(
		long companyId, String name, String description, Integer[] types,
		LinkedHashMap<String, Object> params) {

		return roleFinder.countByC_N_D_T(
			companyId, name, description, types, params, true);
	}

	/**
	 * Sets the roles associated with the user, replacing the user's existing
	 * roles. The user is reindexed after the roles are set.
	 *
	 * @param userId the primary key of the user
	 * @param roleIds the primary keys of the roles
	 */
	@Override
	public void setUserRoles(long userId, long[] roleIds)
		throws PortalException {

		roleIds = UsersAdminUtil.addRequiredRoles(userId, roleIds);

		userPersistence.setRoles(userId, roleIds);

		reindex(userId);
	}

	/**
	 * Removes the matching roles associated with the user. The user is
	 * reindexed after the roles are removed.
	 *
	 * @param userId the primary key of the user
	 * @param roleIds the primary keys of the roles
	 */
	@Override
	public void unsetUserRoles(long userId, long[] roleIds)
		throws PortalException {

		roleIds = UsersAdminUtil.removeRequiredRoles(userId, roleIds);

		userPersistence.removeRoles(userId, roleIds);

		reindex(userId);
	}

	/**
	 * Updates the role with the primary key.
	 *
	 * @param  roleId the primary key of the role
	 * @param  name the role's new name
	 * @param  titleMap the new localized titles (optionally <code>null</code>)
	 *         to replace those existing for the role
	 * @param  descriptionMap the new localized descriptions (optionally
	 *         <code>null</code>) to replace those existing for the role
	 * @param  subtype the role's new subtype (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set expando bridge attributes for the
	 *         role.
	 * @return the role with the primary key
	 */
	@Override
	public Role updateRole(
			long roleId, String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String subtype,
			ServiceContext serviceContext)
		throws PortalException {

		Role role = rolePersistence.findByPrimaryKey(roleId);

		validate(roleId, role.getCompanyId(), role.getClassNameId(), name);

		if (role.isSystem()) {
			name = role.getName();
			subtype = null;
		}

		role.setName(name);
		role.setTitleMap(titleMap);
		role.setDescriptionMap(descriptionMap);
		role.setSubtype(subtype);
		role.setExpandoBridgeAttributes(serviceContext);

		return rolePersistence.update(role);
	}

	@Override
	public void validateName(String name) throws PortalException {
		String[] invalidCharacters = StringUtil.split(
			RoleConstants.NAME_INVALID_CHARACTERS, StringPool.SPACE);

		if (Validator.isNull(name) ||
			(StringUtil.indexOfAny(name, invalidCharacters) > -1)) {

			throw new RoleNameException();
		}

		if (!PropsValues.ROLES_NAME_ALLOW_NUMERIC && Validator.isNumber(name)) {
			throw new RoleNameException();
		}
	}

	protected void checkSystemRole(
			long companyId, String name, Map<Locale, String> descriptionMap,
			int type)
		throws PortalException {

		String companyIdHexString = StringUtil.toHexString(companyId);

		String key = companyIdHexString.concat(name);

		Role role = _systemRolesMap.get(key);

		try {
			if (role == null) {
				role = rolePersistence.findByC_N(companyId, name);
			}

			if (!descriptionMap.equals(role.getDescriptionMap())) {
				role.setDescriptionMap(descriptionMap);

				roleLocalService.updateRole(role);
			}
		}
		catch (NoSuchRoleException noSuchRoleException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchRoleException, noSuchRoleException);
			}

			User user = userLocalService.getDefaultUser(companyId);

			PermissionThreadLocal.setAddResource(false);

			try {
				role = roleLocalService.addRole(
					user.getUserId(), null, 0, name, null, descriptionMap, type,
					null, null);
			}
			finally {
				PermissionThreadLocal.setAddResource(true);
			}

			if (name.equals(RoleConstants.ANALYTICS_ADMINISTRATOR)) {
				initAnalyticsAdministratorViewPermissions(role);
			}
			else if (name.equals(RoleConstants.USER)) {
				initPersonalControlPanelPortletsPermissions(role);
			}
		}

		_systemRolesMap.put(key, role);
	}

	protected String[] getDefaultControlPanelPortlets() {
		String myAccountPortletId = PortletProviderUtil.getPortletId(
			PortalMyAccountApplicationType.MyAccount.CLASS_NAME,
			PortletProvider.Action.VIEW);

		return new String[] {
			myAccountPortletId, PortletKeys.MY_PAGES,
			PortletKeys.MY_WORKFLOW_INSTANCE, PortletKeys.MY_WORKFLOW_TASK
		};
	}

	protected Map<Team, Role> getTeamRoleMap(
			long groupId, long[] excludedRoleIds)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		if (group.isLayout()) {
			group = group.getParentGroup();
		}

		List<Team> teams = teamPersistence.findByGroupId(group.getGroupId());

		if (teams.isEmpty()) {
			return Collections.emptyMap();
		}

		Set<Long> roleIds = SetUtil.fromArray(excludedRoleIds);

		Map<Team, Role> teamRoleMap = new LinkedHashMap<>();

		for (Team team : teams) {
			Role role = getTeamRole(team.getCompanyId(), team.getTeamId());

			if (roleIds.contains(role.getRoleId())) {
				continue;
			}

			teamRoleMap.put(team, role);
		}

		return teamRoleMap;
	}

	protected void initAnalyticsAdministratorViewPermissions(Role role)
		throws PortalException {

		resourcePermissionLocalService.addResourcePermission(
			role.getCompanyId(), Group.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(role.getCompanyId()), role.getRoleId(),
			ActionKeys.VIEW);

		resourcePermissionLocalService.addResourcePermission(
			role.getCompanyId(), User.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(role.getCompanyId()), role.getRoleId(),
			ActionKeys.VIEW);

		resourcePermissionLocalService.addResourcePermission(
			role.getCompanyId(), Organization.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(role.getCompanyId()), role.getRoleId(),
			ActionKeys.VIEW_MEMBERS);

		resourcePermissionLocalService.addResourcePermission(
			role.getCompanyId(), UserGroup.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(role.getCompanyId()), role.getRoleId(),
			ActionKeys.VIEW_MEMBERS);
	}

	protected void initPersonalControlPanelPortletsPermissions(Role role)
		throws PortalException {

		for (String portletId : getDefaultControlPanelPortlets()) {
			int count = resourcePermissionPersistence.countByC_N_S_P_R(
				role.getCompanyId(), portletId, ResourceConstants.SCOPE_COMPANY,
				String.valueOf(role.getCompanyId()), role.getRoleId());

			if (count > 0) {
				continue;
			}

			ResourceAction resourceAction =
				resourceActionLocalService.fetchResourceAction(
					portletId, ActionKeys.ACCESS_IN_CONTROL_PANEL);

			if (resourceAction == null) {
				continue;
			}

			setRolePermissions(
				role, portletId,
				new String[] {ActionKeys.ACCESS_IN_CONTROL_PANEL});
		}
	}

	protected void reindex(long userId) throws SearchException {
		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class);

		User user = userLocalService.fetchUser(userId);

		indexer.reindex(user);
	}

	protected void setRolePermissions(
			Role role, String name, String[] actionIds)
		throws PortalException {

		resourcePermissionLocalService.setResourcePermissions(
			role.getCompanyId(), name, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(role.getCompanyId()), role.getRoleId(), actionIds);
	}

	protected void validate(
			long roleId, long companyId, long classNameId, String name)
		throws PortalException {

		if (classNameId == classNameLocalService.getClassNameId(Role.class)) {
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
				throw new DuplicateRoleException("{roleId=" + roleId + "}");
			}
		}
		catch (NoSuchRoleException noSuchRoleException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchRoleException, noSuchRoleException);
			}
		}

		if (name.equals(RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE)) {
			throw new RoleNameException(
				RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE +
					" is a temporary placeholder that must not be persisted");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RoleLocalServiceImpl.class);

	private final Map<String, Role> _systemRolesMap = new HashMap<>();

}
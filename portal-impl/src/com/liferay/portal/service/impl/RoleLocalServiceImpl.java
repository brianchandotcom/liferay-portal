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

package com.liferay.portal.service.impl;

import com.liferay.portal.DuplicateRoleException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.NoSuchShardException;
import com.liferay.portal.RequiredRoleException;
import com.liferay.portal.RoleNameException;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCachable;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
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
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Shard;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.RoleLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, checking, deleting, and
 * updating roles.
 *
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class RoleLocalServiceImpl extends RoleLocalServiceBaseImpl {

	/**
	 * Adds a role. The user is reindexed after role is added.
	 *
	 * @param      userId the primary key of the user
	 * @param      companyId the primary key of the company
	 * @param      name the role's name
	 * @param      titleMap the role's localized titles (optionally
	 *             <code>null</code>)
	 * @param      descriptionMap the role's localized descriptions (optionally
	 *             <code>null</code>)
	 * @param      type the role's type (optionally <code>0</code>)
	 * @return     the role
	 * @throws     PortalException if the class name or the role name were
	 *             invalid, if the role is a duplicate, or if a user with the
	 *             primary key could not be found
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addRole(long, String, long,
	 *             String, Map, Map, int, String, ServiceContext)}
	 */
	public Role addRole(
			long userId, long companyId, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			int type)
		throws PortalException, SystemException {

		return addRole(
			userId, null, 0, name, titleMap, descriptionMap, type, null, null);
	}

	/**
	 * Adds a role with additional parameters. The user is reindexed after role
	 * is added.
	 *
	 * @param      userId the primary key of the user
	 * @param      companyId the primary key of the company
	 * @param      name the role's name
	 * @param      titleMap the role's localized titles (optionally
	 *             <code>null</code>)
	 * @param      descriptionMap the role's localized descriptions (optionally
	 *             <code>null</code>)
	 * @param      type the role's type (optionally <code>0</code>)
	 * @param      className the name of the class for which the role is created
	 *             (optionally <code>null</code>)
	 * @param      classPK the primary key of the class for which the role is
	 *             created (optionally <code>0</code>)
	 * @return     the role
	 * @throws     PortalException if the class name or the role name were
	 *             invalid, if the role is a duplicate, or if a user with the
	 *             primary key could not be found
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addRole(long, String, long,
	 *             String, Map, Map, int, String, ServiceContext)}
	 */
	public Role addRole(
			long userId, long companyId, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			int type, String className, long classPK)
		throws PortalException, SystemException {

		return addRole(
			userId, className, classPK, name, titleMap, descriptionMap, type,
			null, null);
	}

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
	 * @param  subType the role's subtype (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set expando bridge attributes for the
	 *         role.
	 * @return the role
	 * @throws PortalException if the class name or the role name were invalid,
	 *         if the role is a duplicate, or if a user with the primary key
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role addRole(
			long userId, String className, long classPK, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			int type, String subType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Role

		User user = userPersistence.findByPrimaryKey(userId);
		className = GetterUtil.getString(className);
		long classNameId = PortalUtil.getClassNameId(className);

		long roleId = counterLocalService.increment();

		if ((classNameId <= 0) || className.equals(Role.class.getName())) {
			classNameId = PortalUtil.getClassNameId(Role.class);
			classPK = roleId;
		}

		Date now = new Date();

		validate(0, user.getCompanyId(), classNameId, name);

		Role role = rolePersistence.create(roleId);

		if (serviceContext != null) {
			role.setUuid(serviceContext.getUuid());
		}

		role.setCompanyId(user.getCompanyId());
		role.setUserId(user.getUserId());
		role.setUserName(user.getFullName());

		if (serviceContext != null) {
			role.setCreateDate(serviceContext.getCreateDate(now));
			role.setModifiedDate(serviceContext.getModifiedDate(now));
		}
		else {
			role.setCreateDate(now);
			role.setModifiedDate(now);
		}

		role.setClassNameId(classNameId);
		role.setClassPK(classPK);
		role.setName(name);
		role.setTitleMap(titleMap);
		role.setDescriptionMap(descriptionMap);
		role.setType(type);
		role.setSubtype(subType);
		role.setExpandoBridgeAttributes(serviceContext);

		rolePersistence.update(role);

		// Resources

		if (!user.isDefaultUser()) {
			resourceLocalService.addResources(
				user.getCompanyId(), 0, userId, Role.class.getName(),
				role.getRoleId(), false, false, false);

			if (!ExportImportThreadLocal.isImportInProcess()) {
				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					User.class);

				indexer.reindex(userId);
			}
		}

		return role;
	}

	/**
	 * Adds the roles to the user. The user is reindexed after the roles are
	 * added.
	 *
	 * @param  userId the primary key of the user
	 * @param  roleIds the primary keys of the roles
	 * @throws PortalException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.UserPersistence#addRoles(
	 *         long, long[])
	 */
	@Override
	public void addUserRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		userPersistence.addRoles(userId, roleIds);

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		indexer.reindex(userId);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Checks to ensure that the system roles map has appropriate default roles
	 * in each company.
	 *
	 * @throws PortalException if the current user did not have permission to
	 *         set applicable permissions on a role
	 * @throws SystemException if a system exception occurred
	 */
	public void checkSystemRoles() throws PortalException, SystemException {
		List<Company> companies = companyLocalService.getCompanies();

		String currentShardName = ShardUtil.getCurrentShardName();

		for (Company company : companies) {
			String shardName = null;

			try {
				shardName = company.getShardName();
			}
			catch (NoSuchShardException nsse) {
				Shard shard = shardLocalService.addShard(
					Company.class.getName(), company.getCompanyId(),
					PropsValues.SHARD_DEFAULT_NAME);

				shardName = shard.getName();
			}

			if (!ShardUtil.isEnabled() || shardName.equals(currentShardName)) {
				checkSystemRoles(company.getCompanyId());
			}
		}
	}

	/**
	 * Checks to ensure that the system roles map has appropriate default roles
	 * in the company.
	 *
	 * @param  companyId the primary key of the company
	 * @throws PortalException if the current user did not have permission to
	 *         set applicable permissions on a role
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Deletes the role with the primary key and its associated permissions.
	 *
	 * @param  roleId the primary key of the role
	 * @return the deleted role
	 * @throws PortalException if a role with the primary key could not be
	 *         found, if the role is a default system role, or if the role's
	 *         resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Role deleteRole(long roleId)
		throws PortalException, SystemException {

		Role role = rolePersistence.findByPrimaryKey(roleId);

		return deleteRole(role);
	}

	/**
	 * Deletes the role and its associated permissions.
	 *
	 * @param  role the role
	 * @return the deleted role
	 * @throws PortalException if the role is a default system role or if the
	 *         role's resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Role deleteRole(Role role) throws PortalException, SystemException {
		if (PortalUtil.isSystemRole(role.getName())) {
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

			userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
				role.getRoleId());

			userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByRoleId(
				role.getRoleId());
		}

		// Role

		rolePersistence.remove(role);

		// Expando

		expandoValueLocalService.deleteValues(
			Role.class.getName(), role.getRoleId());

		// Permission cache

		PermissionCacheUtil.clearCache();

		return role;
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
	 * @throws SystemException if a system exception occurred
	 */
	@Skip
	public Role fetchRole(long companyId, String name) throws SystemException {
		String companyIdHexString = StringUtil.toHexString(companyId);

		Role role = _systemRolesMap.get(companyIdHexString.concat(name));

		if (role != null) {
			return role;
		}

		return roleLocalService.loadFetchRole(companyId, name);
	}

	public Role fetchRoleByUuidAndCompanyId(String uuid, long companyId)
		throws SystemException {

		return rolePersistence.fetchByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns the default role for the group with the primary key.
	 *
	 * <p>
	 * If the group is a site, then the default role is {@link
	 * com.liferay.portal.model.RoleConstants#SITE_MEMBER}. If the group is an
	 * organization, then the default role is {@link
	 * com.liferay.portal.model.RoleConstants#ORGANIZATION_USER}. If the group
	 * is a user or user group, then the default role is {@link
	 * com.liferay.portal.model.RoleConstants#POWER_USER}. For all other group
	 * types, the default role is {@link
	 * com.liferay.portal.model.RoleConstants#USER}.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @return the default role for the group with the primary key
	 * @throws PortalException if a group with the primary key could not be
	 *         found, or if a default role could not be found for the group
	 * @throws SystemException if a system exception occurred
	 */
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

	public List<Role> getResourceBlockRoles(
			long resourceBlockId, String className, String actionId)
		throws SystemException {

		return roleFinder.findByR_N_A(resourceBlockId, className, actionId);
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
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.RoleFinder#findByC_N_S_P(
	 *         long, String, int, String)
	 */
	public Map<String, List<String>> getResourceRoles(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.RoleFinder#findByC_N_S_P_A(
	 *         long, String, int, String, String)
	 */
	public List<Role> getResourceRoles(
			long companyId, String name, int scope, String primKey,
			String actionId)
		throws SystemException {

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
	 * @throws PortalException if a role with the name could not be found in the
	 *         company
	 * @throws SystemException if a system exception occurred
	 */
	@Skip
	public Role getRole(long companyId, String name)
		throws PortalException, SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> getRoles(int type, String subtype)
		throws SystemException {

		return rolePersistence.findByT_S(type, subtype);
	}

	/**
	 * Returns all the roles in the company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the roles in the company
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> getRoles(long companyId) throws SystemException {
		return rolePersistence.findByCompanyId(companyId);
	}

	/**
	 * Returns all the roles with the primary keys.
	 *
	 * @param  roleIds the primary keys of the roles
	 * @return the roles with the primary keys
	 * @throws PortalException if any one of the roles with the primary keys
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> getRoles(long[] roleIds)
		throws PortalException, SystemException {

		List<Role> roles = new ArrayList<Role>(roleIds.length);

		for (long roleId : roleIds) {
			Role role = getRole(roleId);

			roles.add(role);
		}

		return roles;
	}

	/**
	 * Returns all the roles of the subtype.
	 *
	 * @param  subtype the role's subtype (optionally <code>null</code>)
	 * @return the roles of the subtype
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> getSubtypeRoles(String subtype) throws SystemException {
		return rolePersistence.findBySubtype(subtype);
	}

	/**
	 * Returns the number of roles of the subtype.
	 *
	 * @param  subtype the role's subtype (optionally <code>null</code>)
	 * @return the number of roles of the subtype
	 * @throws SystemException if a system exception occurred
	 */
	public int getSubtypeRolesCount(String subtype) throws SystemException {
		return rolePersistence.countBySubtype(subtype);
	}

	/**
	 * Returns the team role in the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  teamId the primary key of the team
	 * @return the team role in the company
	 * @throws PortalException if a role could not be found in the team and
	 *         company
	 * @throws SystemException if a system exception occurred
	 */
	public Role getTeamRole(long companyId, long teamId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(Team.class);

		return rolePersistence.findByC_C_C(companyId, classNameId, teamId);
	}

	/**
	 * Returns all the roles of the type.
	 *
	 * @param  type the role's type (optionally <code>0</code>)
	 * @return the range of the roles of the type
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> getTypeRoles(int type) throws SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> getTypeRoles(int type, int start, int end)
		throws SystemException {

		return rolePersistence.findByType(type, start, end);
	}

	/**
	 * Returns the number of roles of the type.
	 *
	 * @param  type the role's type (optionally <code>0</code>)
	 * @return the number of roles of the type
	 * @throws SystemException if a system exception occurred
	 */
	public int getTypeRolesCount(int type) throws SystemException {
		return rolePersistence.countByType(type);
	}

	/**
	 * Returns all the user's roles within the user group.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return the user's roles within the user group
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.RoleFinder#findByUserGroupGroupRole(
	 *         long, long)
	 */
	public List<Role> getUserGroupGroupRoles(long userId, long groupId)
		throws SystemException {

		return roleFinder.findByUserGroupGroupRole(userId, groupId);
	}

	/**
	 * Returns all the user's roles within the user group.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return the user's roles within the user group
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.RoleFinder#findByUserGroupRole(
	 *         long, long)
	 */
	public List<Role> getUserGroupRoles(long userId, long groupId)
		throws SystemException {

		return roleFinder.findByUserGroupRole(userId, groupId);
	}

	/**
	 * Returns the union of all the user's roles within the groups.
	 *
	 * @param  userId the primary key of the user
	 * @param  groups the groups (optionally <code>null</code>)
	 * @return the union of all the user's roles within the groups
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.RoleFinder#findByU_G(
	 *         long, List)
	 */
	public List<Role> getUserRelatedRoles(long userId, List<Group> groups)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.RoleFinder#findByU_G(
	 *         long, long)
	 */
	public List<Role> getUserRelatedRoles(long userId, long groupId)
		throws SystemException {

		return roleFinder.findByU_G(userId, groupId);
	}

	/**
	 * Returns the union of all the user's roles within the groups.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupIds the primary keys of the groups
	 * @return the union of all the user's roles within the groups
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.RoleFinder#findByU_G(
	 *         long, long[])
	 */
	public List<Role> getUserRelatedRoles(long userId, long[] groupIds)
		throws SystemException {

		return roleFinder.findByU_G(userId, groupIds);
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
	 * @throws PortalException if a default user for the company could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@ThreadLocalCachable
	public boolean hasUserRole(
			long userId, long companyId, String name, boolean inherited)
		throws PortalException, SystemException {

		Role role = rolePersistence.fetchByC_N(companyId, name);

		if (role == null) {
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
					Lifecycle.REQUEST, RoleLocalServiceImpl.class.getName());

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
	 * @throws PortalException if any one of the roles with the names could not
	 *         be found in the company or if the default user for the company
	 *         could not be found
	 * @throws SystemException if a system exception occurred
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

	/**
	 * Returns a role with the name in the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the role's name (optionally <code>null</code>)
	 * @return the role with the name, or <code>null</code> if a role with the
	 *         name could not be found in the company
	 * @throws SystemException if a system exception occurred
	 */
	public Role loadFetchRole(long companyId, String name)
		throws SystemException {

		return rolePersistence.fetchByC_N(companyId, name);
	}

	/**
	 * Returns a role with the name in the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the role's name
	 * @return the role with the name in the company
	 * @throws PortalException if a role with the name could not be found in the
	 *         company
	 * @throws SystemException if a system exception occurred
	 */
	public Role loadGetRole(long companyId, String name)
		throws PortalException, SystemException {

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
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.RoleFinder
	 */
	public List<Role> search(
			long companyId, String keywords, Integer[] types, int start,
			int end, OrderByComparator obc)
		throws SystemException {

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
	 *         com.liferay.portal.service.persistence.RoleFinder}
	 * @param  start the lower bound of the range of roles to return
	 * @param  end the upper bound of the range of roles to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the roles (optionally
	 *         <code>null</code>)
	 * @return the ordered range of the matching roles, ordered by
	 *         <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.RoleFinder
	 */
	public List<Role> search(
			long companyId, String keywords, Integer[] types,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.RoleFinder
	 */
	public List<Role> search(
			long companyId, String name, String description, Integer[] types,
			int start, int end, OrderByComparator obc)
		throws SystemException {

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
	 *         com.liferay.portal.service.persistence.RoleFinder}
	 * @param  start the lower bound of the range of the roles to return
	 * @param  end the upper bound of the range of the roles to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the roles (optionally
	 *         <code>null</code>)
	 * @return the ordered range of the matching roles, ordered by
	 *         <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.RoleFinder
	 */
	public List<Role> search(
			long companyId, String name, String description, Integer[] types,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(long companyId, String keywords, Integer[] types)
		throws SystemException {

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
	 *         com.liferay.portal.service.persistence.RoleFinder}
	 * @return the number of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, String keywords, Integer[] types,
			LinkedHashMap<String, Object> params)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, String name, String description, Integer[] types)
		throws SystemException {

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
	 *         com.liferay.portal.service.persistence.RoleFinder}
	 * @return the number of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, String name, String description, Integer[] types,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		return roleFinder.countByC_N_D_T(
			companyId, name, description, types, params, true);
	}

	/**
	 * Sets the roles associated with the user, replacing the user's existing
	 * roles. The user is reindexed after the roles are set.
	 *
	 * @param  userId the primary key of the user
	 * @param  roleIds the primary keys of the roles
	 * @throws PortalException if a user with the primary could not be found or
	 *         if any one of the roles with the primary keys could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setUserRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		roleIds = UsersAdminUtil.addRequiredRoles(userId, roleIds);

		userPersistence.setRoles(userId, roleIds);

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		indexer.reindex(userId);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Removes the matching roles associated with the user. The user is
	 * reindexed after the roles are removed.
	 *
	 * @param  userId the primary key of the user
	 * @param  roleIds the primary keys of the roles
	 * @throws PortalException if a user with the primary key could not be found
	 *         or if a role with any one of the primary keys could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void unsetUserRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		roleIds = UsersAdminUtil.removeRequiredRoles(userId, roleIds);

		userPersistence.removeRoles(userId, roleIds);

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		indexer.reindex(userId);

		PermissionCacheUtil.clearCache();
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
	 * @throws PortalException if a role with the primary could not be found or
	 *         if the role's name was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public Role updateRole(
			long roleId, String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String subtype,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Role role = rolePersistence.findByPrimaryKey(roleId);

		validate(roleId, role.getCompanyId(), role.getClassNameId(), name);

		if (PortalUtil.isSystemRole(role.getName())) {
			name = role.getName();
			subtype = null;
		}

		role.setModifiedDate(new Date());
		role.setName(name);
		role.setTitleMap(titleMap);
		role.setDescriptionMap(descriptionMap);
		role.setSubtype(subtype);
		role.setExpandoBridgeAttributes(serviceContext);

		rolePersistence.update(role);

		return role;
	}

	protected void checkSystemRole(
			long companyId, String name, Map<Locale, String> descriptionMap,
			int type)
		throws PortalException, SystemException {

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
		catch (NoSuchRoleException nsre) {
			User user = userLocalService.getDefaultUser(companyId);

			role = roleLocalService.addRole(
				user.getUserId(), null, 0, name, null, descriptionMap, type,
				null, null);

			if (name.equals(RoleConstants.USER)) {
				initPersonalControlPanelPortletsPermissions(role);
			}
		}

		_systemRolesMap.put(key, role);
	}

	protected String[] getDefaultControlPanelPortlets() {
		return new String[] {
			PortletKeys.MY_ACCOUNT, PortletKeys.MY_PAGES,
			PortletKeys.MY_WORKFLOW_INSTANCES, PortletKeys.MY_WORKFLOW_TASKS
		};
	}

	protected void initPersonalControlPanelPortletsPermissions(Role role)
		throws PortalException, SystemException {

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
				new String[] {
					ActionKeys.ACCESS_IN_CONTROL_PANEL
				});
		}
	}

	protected void setRolePermissions(
			Role role, String name, String[] actionIds)
		throws PortalException, SystemException {

		if (resourceBlockLocalService.isSupported(name)) {
			resourceBlockLocalService.setCompanyScopePermissions(
				role.getCompanyId(), name, role.getRoleId(),
				Arrays.asList(actionIds));
		}
		else {
			resourcePermissionLocalService.setResourcePermissions(
				role.getCompanyId(), name, ResourceConstants.SCOPE_COMPANY,
				String.valueOf(role.getCompanyId()), role.getRoleId(),
				actionIds);
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
		catch (NoSuchRoleException nsre) {
		}
	}

	private Map<String, Role> _systemRolesMap = new HashMap<String, Role>();

}
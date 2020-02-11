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

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link RoleLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RoleLocalService
 * @generated
 */
public class RoleLocalServiceWrapper
	implements RoleLocalService, ServiceWrapper<RoleLocalService> {

	public RoleLocalServiceWrapper(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Override
	public void addGroupRole(long groupId, long roleId) {
		_roleLocalService.addGroupRole(groupId, roleId);
	}

	@Override
	public void addGroupRole(
		long groupId, com.liferay.portal.kernel.model.Role role) {

		_roleLocalService.addGroupRole(groupId, role);
	}

	@Override
	public void addGroupRoles(
		long groupId,
		java.util.List<com.liferay.portal.kernel.model.Role> roles) {

		_roleLocalService.addGroupRoles(groupId, roles);
	}

	@Override
	public void addGroupRoles(long groupId, long[] roleIds) {
		_roleLocalService.addGroupRoles(groupId, roleIds);
	}

	/**
	 * Adds a role with additional parameters. The user is reindexed after role
	 * is added.
	 *
	 * @param userId the primary key of the user
	 * @param className the name of the class for which the role is created
	 (optionally <code>null</code>)
	 * @param classPK the primary key of the class for which the role is
	 created (optionally <code>0</code>)
	 * @param name the role's name
	 * @param titleMap the role's localized titles (optionally
	 <code>null</code>)
	 * @param descriptionMap the role's localized descriptions (optionally
	 <code>null</code>)
	 * @param type the role's type (optionally <code>0</code>)
	 * @param subtype the role's subtype (optionally <code>null</code>)
	 * @param serviceContext the service context to be applied (optionally
	 <code>null</code>). Can set expando bridge attributes for the
	 role.
	 * @return the role
	 */
	@Override
	public com.liferay.portal.kernel.model.Role addRole(
			long userId, java.lang.String className, long classPK,
			java.lang.String name,
			java.util.Map<java.util.Locale, java.lang.String> titleMap,
			java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
			int type, java.lang.String subtype, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.addRole(
			userId, className, classPK, name, titleMap, descriptionMap, type,
			subtype, serviceContext);
	}

	/**
	 * Adds the role to the database. Also notifies the appropriate model listeners.
	 *
	 * @param role the role
	 * @return the role that was added
	 */
	@Override
	public com.liferay.portal.kernel.model.Role addRole(
		com.liferay.portal.kernel.model.Role role) {

		return _roleLocalService.addRole(role);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void addUserRole(long userId, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.addUserRole(userId, roleId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void addUserRole(
			long userId, com.liferay.portal.kernel.model.Role role)
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.addUserRole(userId, role);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void addUserRoles(
			long userId,
			java.util.List<com.liferay.portal.kernel.model.Role> roles)
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.addUserRoles(userId, roles);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void addUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.addUserRoles(userId, roleIds);
	}

	/**
	 * Checks to ensure that the system roles map has appropriate default roles
	 * in each company.
	 */
	@Override
	public void checkSystemRoles()
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.checkSystemRoles();
	}

	/**
	 * Checks to ensure that the system roles map has appropriate default roles
	 * in the company.
	 *
	 * @param companyId the primary key of the company
	 */
	@Override
	public void checkSystemRoles(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.checkSystemRoles(companyId);
	}

	@Override
	public void clearGroupRoles(long groupId) {
		_roleLocalService.clearGroupRoles(groupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void clearUserRoles(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.clearUserRoles(userId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new role with the primary key. Does not add the role to the database.
	 *
	 * @param roleId the primary key for the new role
	 * @return the new role
	 */
	@Override
	public com.liferay.portal.kernel.model.Role createRole(long roleId) {
		return _roleLocalService.createRole(roleId);
	}

	@Override
	public void deleteGroupRole(long groupId, long roleId) {
		_roleLocalService.deleteGroupRole(groupId, roleId);
	}

	@Override
	public void deleteGroupRole(
		long groupId, com.liferay.portal.kernel.model.Role role) {

		_roleLocalService.deleteGroupRole(groupId, role);
	}

	@Override
	public void deleteGroupRoles(
		long groupId,
		java.util.List<com.liferay.portal.kernel.model.Role> roles) {

		_roleLocalService.deleteGroupRoles(groupId, roles);
	}

	@Override
	public void deleteGroupRoles(long groupId, long[] roleIds) {
		_roleLocalService.deleteGroupRoles(groupId, roleIds);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param roleId the primary key of the role
	 * @return the role that was removed
	 * @throws PortalException if a role with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Role deleteRole(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.deleteRole(roleId);
	}

	/**
	 * Deletes the role from the database. Also notifies the appropriate model listeners.
	 *
	 * @param role the role
	 * @return the role that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.Role deleteRole(
			com.liferay.portal.kernel.model.Role role)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.deleteRole(role);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void deleteUserRole(long userId, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.deleteUserRole(userId, roleId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void deleteUserRole(
			long userId, com.liferay.portal.kernel.model.Role role)
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.deleteUserRole(userId, role);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void deleteUserRoles(
			long userId,
			java.util.List<com.liferay.portal.kernel.model.Role> roles)
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.deleteUserRoles(userId, roles);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void deleteUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.deleteUserRoles(userId, roleIds);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _roleLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _roleLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RoleModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _roleLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RoleModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _roleLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _roleLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _roleLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.Role fetchRole(long roleId) {
		return _roleLocalService.fetchRole(roleId);
	}

	/**
	 * Returns the role with the name in the company.
	 *
	 * <p>
	 * The method searches the system roles map first for default roles. If a
	 * role with the name is not found, then the method will query the database.
	 * </p>
	 *
	 * @param companyId the primary key of the company
	 * @param name the role's name
	 * @return Returns the role with the name or <code>null</code> if a role
	 with the name could not be found in the company
	 */
	@Override
	public com.liferay.portal.kernel.model.Role fetchRole(
		long companyId, java.lang.String name) {

		return _roleLocalService.fetchRole(companyId, name);
	}

	/**
	 * Returns the role with the matching UUID and company.
	 *
	 * @param uuid the role's UUID
	 * @param companyId the primary key of the company
	 * @return the matching role, or <code>null</code> if a matching role could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Role fetchRoleByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {

		return _roleLocalService.fetchRoleByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _roleLocalService.getActionableDynamicQuery();
	}

	@Override
	public int getAssigneesTotal(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getAssigneesTotal(roleId);
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
	 * @param groupId the primary key of the group
	 * @return the default role for the group with the primary key
	 */
	@Override
	public com.liferay.portal.kernel.model.Role getDefaultGroupRole(
			long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getDefaultGroupRole(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _roleLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	/**
	 * Returns the groupIds of the groups associated with the role.
	 *
	 * @param roleId the roleId of the role
	 * @return long[] the groupIds of groups associated with the role
	 */
	@Override
	public long[] getGroupPrimaryKeys(long roleId) {
		return _roleLocalService.getGroupPrimaryKeys(roleId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role>
			getGroupRelatedRoles(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getGroupRelatedRoles(groupId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getGroupRoles(
		long groupId) {

		return _roleLocalService.getGroupRoles(groupId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getGroupRoles(
		long groupId, int start, int end) {

		return _roleLocalService.getGroupRoles(groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getGroupRoles(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Role> orderByComparator) {

		return _roleLocalService.getGroupRoles(
			groupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role>
		getGroupRolesAndTeamRoles(
			long companyId, java.lang.String keywords,
			java.util.List<java.lang.String> excludedNames, int[] types,
			long excludedTeamRoleId, long teamGroupId, int start, int end) {

		return _roleLocalService.getGroupRolesAndTeamRoles(
			companyId, keywords, excludedNames, types, excludedTeamRoleId,
			teamGroupId, start, end);
	}

	@Override
	public int getGroupRolesAndTeamRolesCount(
		long companyId, java.lang.String keywords,
		java.util.List<java.lang.String> excludedNames, int[] types,
		long excludedTeamRoleId, long teamGroupId) {

		return _roleLocalService.getGroupRolesAndTeamRolesCount(
			companyId, keywords, excludedNames, types, excludedTeamRoleId,
			teamGroupId);
	}

	@Override
	public int getGroupRolesCount(long groupId) {
		return _roleLocalService.getGroupRolesCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _roleLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _roleLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a map of role names to associated action IDs for the named
	 * resource in the company within the permission scope.
	 *
	 * @param companyId the primary key of the company
	 * @param name the resource name
	 * @param scope the permission scope
	 * @param primKey the primary key of the resource's class
	 * @return the role names and action IDs
	 * @see com.liferay.portal.kernel.service.persistence.RoleFinder#findByC_N_S_P(
	 long, String, int, String)
	 */
	@Override
	public java.util.Map<java.lang.String, java.util.List<java.lang.String>>
		getResourceRoles(
			long companyId, java.lang.String name, int scope,
			java.lang.String primKey) {

		return _roleLocalService.getResourceRoles(
			companyId, name, scope, primKey);
	}

	/**
	 * Returns all the roles associated with the action ID in the company within
	 * the permission scope.
	 *
	 * @param companyId the primary key of the company
	 * @param name the resource name
	 * @param scope the permission scope
	 * @param primKey the primary key of the resource's class
	 * @param actionId the name of the resource action
	 * @return the roles
	 * @see com.liferay.portal.kernel.service.persistence.RoleFinder#findByC_N_S_P_A(
	 long, String, int, String, String)
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role>
		getResourceRoles(
			long companyId, java.lang.String name, int scope,
			java.lang.String primKey, java.lang.String actionId) {

		return _roleLocalService.getResourceRoles(
			companyId, name, scope, primKey, actionId);
	}

	/**
	 * Returns the role with the primary key.
	 *
	 * @param roleId the primary key of the role
	 * @return the role
	 * @throws PortalException if a role with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Role getRole(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getRole(roleId);
	}

	/**
	 * Returns the role with the name in the company.
	 *
	 * <p>
	 * The method searches the system roles map first for default roles. If a
	 * role with the name is not found, then the method will query the database.
	 * </p>
	 *
	 * @param companyId the primary key of the company
	 * @param name the role's name
	 * @return the role with the name
	 */
	@Override
	public com.liferay.portal.kernel.model.Role getRole(
			long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getRole(companyId, name);
	}

	/**
	 * Returns the role with the matching UUID and company.
	 *
	 * @param uuid the role's UUID
	 * @param companyId the primary key of the company
	 * @return the matching role
	 * @throws PortalException if a matching role could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Role getRoleByUuidAndCompanyId(
			java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getRoleByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of roles
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getRoles(
		int start, int end) {

		return _roleLocalService.getRoles(start, end);
	}

	/**
	 * Returns all the roles of the type and subtype.
	 *
	 * @param type the role's type (optionally <code>0</code>)
	 * @param subtype the role's subtype (optionally <code>null</code>)
	 * @return the roles of the type and subtype
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getRoles(
		int type, java.lang.String subtype) {

		return _roleLocalService.getRoles(type, subtype);
	}

	/**
	 * Returns all the roles in the company.
	 *
	 * @param companyId the primary key of the company
	 * @return the roles in the company
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getRoles(
		long companyId) {

		return _roleLocalService.getRoles(companyId);
	}

	/**
	 * Returns all the roles with the types.
	 *
	 * @param companyId the primary key of the company
	 * @param types the role types (optionally <code>null</code>)
	 * @return the roles with the types
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getRoles(
		long companyId, int[] types) {

		return _roleLocalService.getRoles(companyId, types);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getRoles(
		long companyId, long classNameId, long[] classPKs, int type) {

		return _roleLocalService.getRoles(
			companyId, classNameId, classPKs, type);
	}

	/**
	 * Returns all the roles with the primary keys.
	 *
	 * @param roleIds the primary keys of the roles
	 * @return the roles with the primary keys
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getRoles(
			long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getRoles(roleIds);
	}

	/**
	 * Returns the number of roles.
	 *
	 * @return the number of roles
	 */
	@Override
	public int getRolesCount() {
		return _roleLocalService.getRolesCount();
	}

	/**
	 * Returns all the roles of the subtype.
	 *
	 * @param subtype the role's subtype (optionally <code>null</code>)
	 * @return the roles of the subtype
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getSubtypeRoles(
		java.lang.String subtype) {

		return _roleLocalService.getSubtypeRoles(subtype);
	}

	/**
	 * Returns the number of roles of the subtype.
	 *
	 * @param subtype the role's subtype (optionally <code>null</code>)
	 * @return the number of roles of the subtype
	 */
	@Override
	public int getSubtypeRolesCount(java.lang.String subtype) {
		return _roleLocalService.getSubtypeRolesCount(subtype);
	}

	/**
	 * Returns the team role in the company.
	 *
	 * @param companyId the primary key of the company
	 * @param teamId the primary key of the team
	 * @return the team role in the company
	 */
	@Override
	public com.liferay.portal.kernel.model.Role getTeamRole(
			long companyId, long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getTeamRole(companyId, teamId);
	}

	/**
	 * Returns the team role map for the group.
	 *
	 * @param groupId the primary key of the group
	 * @return the team role map for the group
	 */
	@Override
	public java.util.Map
		<com.liferay.portal.kernel.model.Team,
		 com.liferay.portal.kernel.model.Role> getTeamRoleMap(long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getTeamRoleMap(groupId);
	}

	/**
	 * Returns the team roles in the group.
	 *
	 * @param groupId the primary key of the group
	 * @return the team roles in the group
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getTeamRoles(
			long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getTeamRoles(groupId);
	}

	/**
	 * Returns the team roles in the group, excluding the specified role IDs.
	 *
	 * @param groupId the primary key of the group
	 * @param excludedRoleIds the primary keys of the roles to exclude
	 (optionally <code>null</code>)
	 * @return the team roles in the group, excluding the specified role IDs
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getTeamRoles(
			long groupId, long[] excludedRoleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getTeamRoles(groupId, excludedRoleIds);
	}

	/**
	 * Returns the team roles in the company.
	 *
	 * @param companyId the primary key of the company
	 * @param teamIds the primary keys of the teams
	 * @return the team roles in the company
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getTeamsRoles(
			long companyId, long[] teamIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.getTeamsRoles(companyId, teamIds);
	}

	/**
	 * Returns all the roles of the type.
	 *
	 * @param type the role's type (optionally <code>0</code>)
	 * @return the range of the roles of the type
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getTypeRoles(
		int type) {

		return _roleLocalService.getTypeRoles(type);
	}

	/**
	 * Returns a range of all the roles of the type.
	 *
	 * @param type the role's type (optionally <code>0</code>)
	 * @param start the lower bound of the range of roles to return
	 * @param end the upper bound of the range of roles to return (not
	 inclusive)
	 * @return the range of the roles of the type
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getTypeRoles(
		int type, int start, int end) {

		return _roleLocalService.getTypeRoles(type, start, end);
	}

	/**
	 * Returns the number of roles of the type.
	 *
	 * @param type the role's type (optionally <code>0</code>)
	 * @return the number of roles of the type
	 */
	@Override
	public int getTypeRolesCount(int type) {
		return _roleLocalService.getTypeRolesCount(type);
	}

	/**
	 * Returns all the user's roles within the user group.
	 *
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @return the user's roles within the user group
	 * @see com.liferay.portal.kernel.service.persistence.RoleFinder#findByUserGroupGroupRole(
	 long, long)
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role>
		getUserGroupGroupRoles(long userId, long groupId) {

		return _roleLocalService.getUserGroupGroupRoles(userId, groupId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role>
		getUserGroupGroupRoles(long userId, long groupId, int start, int end) {

		return _roleLocalService.getUserGroupGroupRoles(
			userId, groupId, start, end);
	}

	@Override
	public int getUserGroupGroupRolesCount(long userId, long groupId) {
		return _roleLocalService.getUserGroupGroupRolesCount(userId, groupId);
	}

	/**
	 * Returns all the user's roles within the user group.
	 *
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @return the user's roles within the user group
	 * @see com.liferay.portal.kernel.service.persistence.RoleFinder#findByUserGroupRole(
	 long, long)
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role>
		getUserGroupRoles(long userId, long groupId) {

		return _roleLocalService.getUserGroupRoles(userId, groupId);
	}

	/**
	 * Returns the userIds of the users associated with the role.
	 *
	 * @param roleId the roleId of the role
	 * @return long[] the userIds of users associated with the role
	 */
	@Override
	public long[] getUserPrimaryKeys(long roleId) {
		return _roleLocalService.getUserPrimaryKeys(roleId);
	}

	/**
	 * Returns the union of all the user's roles within the groups.
	 *
	 * @param userId the primary key of the user
	 * @param groups the groups (optionally <code>null</code>)
	 * @return the union of all the user's roles within the groups
	 * @see com.liferay.portal.kernel.service.persistence.RoleFinder#findByU_G(
	 long, List)
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role>
		getUserRelatedRoles(
			long userId,
			java.util.List<com.liferay.portal.kernel.model.Group> groups) {

		return _roleLocalService.getUserRelatedRoles(userId, groups);
	}

	/**
	 * Returns all the user's roles within the group.
	 *
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @return the user's roles within the group
	 * @see com.liferay.portal.kernel.service.persistence.RoleFinder#findByU_G(
	 long, long)
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role>
		getUserRelatedRoles(long userId, long groupId) {

		return _roleLocalService.getUserRelatedRoles(userId, groupId);
	}

	/**
	 * Returns the union of all the user's roles within the groups.
	 *
	 * @param userId the primary key of the user
	 * @param groupIds the primary keys of the groups
	 * @return the union of all the user's roles within the groups
	 * @see com.liferay.portal.kernel.service.persistence.RoleFinder#findByU_G(
	 long, long[])
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role>
		getUserRelatedRoles(long userId, long[] groupIds) {

		return _roleLocalService.getUserRelatedRoles(userId, groupIds);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getUserRoles(
		long userId) {

		return _roleLocalService.getUserRoles(userId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getUserRoles(
		long userId, int start, int end) {

		return _roleLocalService.getUserRoles(userId, start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> getUserRoles(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Role> orderByComparator) {

		return _roleLocalService.getUserRoles(
			userId, start, end, orderByComparator);
	}

	@Override
	public int getUserRolesCount(long userId) {
		return _roleLocalService.getUserRolesCount(userId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role>
		getUserTeamRoles(long userId, long groupId) {

		return _roleLocalService.getUserTeamRoles(userId, groupId);
	}

	@Override
	public boolean hasGroupRole(long groupId, long roleId) {
		return _roleLocalService.hasGroupRole(groupId, roleId);
	}

	@Override
	public boolean hasGroupRoles(long groupId) {
		return _roleLocalService.hasGroupRoles(groupId);
	}

	@Override
	public boolean hasUserRole(long userId, long roleId) {
		return _roleLocalService.hasUserRole(userId, roleId);
	}

	/**
	 * Returns <code>true</code> if the user is associated with the named
	 * regular role.
	 *
	 * @param userId the primary key of the user
	 * @param companyId the primary key of the company
	 * @param name the name of the role
	 * @param inherited whether to include the user's inherited roles in the
	 search
	 * @return <code>true</code> if the user is associated with the regular
	 role; <code>false</code> otherwise
	 */
	@Override
	public boolean hasUserRole(
			long userId, long companyId, java.lang.String name,
			boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.hasUserRole(
			userId, companyId, name, inherited);
	}

	@Override
	public boolean hasUserRoles(long userId) {
		return _roleLocalService.hasUserRoles(userId);
	}

	/**
	 * Returns <code>true</code> if the user has any one of the named regular
	 * roles.
	 *
	 * @param userId the primary key of the user
	 * @param companyId the primary key of the company
	 * @param names the names of the roles
	 * @param inherited whether to include the user's inherited roles in the
	 search
	 * @return <code>true</code> if the user has any one of the regular roles;
	 <code>false</code> otherwise
	 */
	@Override
	public boolean hasUserRoles(
			long userId, long companyId, java.lang.String[] names,
			boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.hasUserRoles(
			userId, companyId, names, inherited);
	}

	/**
	 * Returns a role with the name in the company.
	 *
	 * @param companyId the primary key of the company
	 * @param name the role's name (optionally <code>null</code>)
	 * @return the role with the name, or <code>null</code> if a role with the
	 name could not be found in the company
	 */
	@Override
	public com.liferay.portal.kernel.model.Role loadFetchRole(
		long companyId, java.lang.String name) {

		return _roleLocalService.loadFetchRole(companyId, name);
	}

	/**
	 * Returns a role with the name in the company.
	 *
	 * @param companyId the primary key of the company
	 * @param name the role's name
	 * @return the role with the name in the company
	 */
	@Override
	public com.liferay.portal.kernel.model.Role loadGetRole(
			long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.loadGetRole(companyId, name);
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
	 * @param companyId the primary key of the company
	 * @param keywords the keywords (space separated), which may occur in the
	 role's name or description (optionally <code>null</code>)
	 * @param types the role types (optionally <code>null</code>)
	 * @param start the lower bound of the range of roles to return
	 * @param end the upper bound of the range of roles to return (not
	 inclusive)
	 * @param obc the comparator to order the roles (optionally
	 <code>null</code>)
	 * @return the ordered range of the matching roles, ordered by
	 <code>obc</code>
	 * @see com.liferay.portal.kernel.service.persistence.RoleFinder
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> search(
		long companyId, java.lang.String keywords, java.lang.Integer[] types,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Role> obc) {

		return _roleLocalService.search(
			companyId, keywords, types, start, end, obc);
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
	 * @param companyId the primary key of the company
	 * @param keywords the keywords (space separated), which may occur in the
	 role's name or description (optionally <code>null</code>)
	 * @param types the role types (optionally <code>null</code>)
	 * @param params the finder parameters. Can specify values for the
	 "usersRoles" key. For more information, see {@link
	 com.liferay.portal.kernel.service.persistence.RoleFinder}
	 * @param start the lower bound of the range of roles to return
	 * @param end the upper bound of the range of roles to return (not
	 inclusive)
	 * @param obc the comparator to order the roles (optionally
	 <code>null</code>)
	 * @return the ordered range of the matching roles, ordered by
	 <code>obc</code>
	 * @see com.liferay.portal.kernel.service.persistence.RoleFinder
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> search(
		long companyId, java.lang.String keywords, java.lang.Integer[] types,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Role> obc) {

		return _roleLocalService.search(
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
	 * @param companyId the primary key of the company
	 * @param name the role's name (optionally <code>null</code>)
	 * @param description the role's description (optionally <code>null</code>)
	 * @param types the role types (optionally <code>null</code>)
	 * @param start the lower bound of the range of the roles to return
	 * @param end the upper bound of the range of the roles to return (not
	 inclusive)
	 * @param obc the comparator to order the roles (optionally
	 <code>null</code>)
	 * @return the ordered range of the matching roles, ordered by
	 <code>obc</code>
	 * @see com.liferay.portal.kernel.service.persistence.RoleFinder
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer[] types, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Role> obc) {

		return _roleLocalService.search(
			companyId, name, description, types, start, end, obc);
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
	 * @param companyId the primary key of the company
	 * @param name the role's name (optionally <code>null</code>)
	 * @param description the role's description (optionally <code>null</code>)
	 * @param types the role types (optionally <code>null</code>)
	 * @param params the finder's parameters. Can specify values for the
	 "usersRoles" key. For more information, see {@link
	 com.liferay.portal.kernel.service.persistence.RoleFinder}
	 * @param start the lower bound of the range of the roles to return
	 * @param end the upper bound of the range of the roles to return (not
	 inclusive)
	 * @param obc the comparator to order the roles (optionally
	 <code>null</code>)
	 * @return the ordered range of the matching roles, ordered by
	 <code>obc</code>
	 * @see com.liferay.portal.kernel.service.persistence.RoleFinder
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Role> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer[] types,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Role> obc) {

		return _roleLocalService.search(
			companyId, name, description, types, params, start, end, obc);
	}

	/**
	 * Returns the number of roles that match the keywords and types.
	 *
	 * @param companyId the primary key of the company
	 * @param keywords the keywords (space separated), which may occur in the
	 role's name or description (optionally <code>null</code>)
	 * @param types the role types (optionally <code>null</code>)
	 * @return the number of matching roles
	 */
	@Override
	public int searchCount(
		long companyId, java.lang.String keywords, java.lang.Integer[] types) {

		return _roleLocalService.searchCount(companyId, keywords, types);
	}

	/**
	 * Returns the number of roles that match the keywords, types and params.
	 *
	 * @param companyId the primary key of the company
	 * @param keywords the keywords (space separated), which may occur in the
	 role's name or description (optionally <code>null</code>)
	 * @param types the role types (optionally <code>null</code>)
	 * @param params the finder parameters. For more information, see {@link
	 com.liferay.portal.kernel.service.persistence.RoleFinder}
	 * @return the number of matching roles
	 */
	@Override
	public int searchCount(
		long companyId, java.lang.String keywords, java.lang.Integer[] types,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params) {

		return _roleLocalService.searchCount(
			companyId, keywords, types, params);
	}

	/**
	 * Returns the number of roles that match the name, description, and types.
	 *
	 * @param companyId the primary key of the company
	 * @param name the role's name (optionally <code>null</code>)
	 * @param description the role's description (optionally <code>null</code>)
	 * @param types the role types (optionally <code>null</code>)
	 * @return the number of matching roles
	 */
	@Override
	public int searchCount(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer[] types) {

		return _roleLocalService.searchCount(
			companyId, name, description, types);
	}

	/**
	 * Returns the number of roles that match the name, description, types, and
	 * params.
	 *
	 * @param companyId the primary key of the company
	 * @param name the role's name (optionally <code>null</code>)
	 * @param description the role's description (optionally <code>null</code>)
	 * @param types the role types (optionally <code>null</code>)
	 * @param params the finder parameters. Can specify values for the
	 "usersRoles" key. For more information, see {@link
	 com.liferay.portal.kernel.service.persistence.RoleFinder}
	 * @return the number of matching roles
	 */
	@Override
	public int searchCount(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer[] types,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params) {

		return _roleLocalService.searchCount(
			companyId, name, description, types, params);
	}

	@Override
	public void setGroupRoles(long groupId, long[] roleIds) {
		_roleLocalService.setGroupRoles(groupId, roleIds);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void setUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.setUserRoles(userId, roleIds);
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
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.unsetUserRoles(userId, roleIds);
	}

	/**
	 * Updates the role with the primary key.
	 *
	 * @param roleId the primary key of the role
	 * @param name the role's new name
	 * @param titleMap the new localized titles (optionally <code>null</code>)
	 to replace those existing for the role
	 * @param descriptionMap the new localized descriptions (optionally
	 <code>null</code>) to replace those existing for the role
	 * @param subtype the role's new subtype (optionally <code>null</code>)
	 * @param serviceContext the service context to be applied (optionally
	 <code>null</code>). Can set expando bridge attributes for the
	 role.
	 * @return the role with the primary key
	 */
	@Override
	public com.liferay.portal.kernel.model.Role updateRole(
			long roleId, java.lang.String name,
			java.util.Map<java.util.Locale, java.lang.String> titleMap,
			java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
			java.lang.String subtype, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _roleLocalService.updateRole(
			roleId, name, titleMap, descriptionMap, subtype, serviceContext);
	}

	/**
	 * Updates the role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param role the role
	 * @return the role that was updated
	 */
	@Override
	public com.liferay.portal.kernel.model.Role updateRole(
		com.liferay.portal.kernel.model.Role role) {

		return _roleLocalService.updateRole(role);
	}

	@Override
	public void validateName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		_roleLocalService.validateName(name);
	}

	@Override
	public RoleLocalService getWrappedService() {
		return _roleLocalService;
	}

	@Override
	public void setWrappedService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	private RoleLocalService _roleLocalService;

}
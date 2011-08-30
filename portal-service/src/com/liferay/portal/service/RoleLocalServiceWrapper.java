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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link RoleLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RoleLocalService
 * @generated
 */
public class RoleLocalServiceWrapper implements RoleLocalService {
	public RoleLocalServiceWrapper(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	/**
	* Adds the role to the database. Also notifies the appropriate model listeners.
	*
	* @param role the role
	* @return the role that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Role addRole(
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.addRole(role);
	}

	/**
	* Creates a new role with the primary key. Does not add the role to the database.
	*
	* @param roleId the primary key for the new role
	* @return the new role
	*/
	public com.liferay.portal.model.Role createRole(long roleId) {
		return _roleLocalService.createRole(roleId);
	}

	/**
	* Deletes the role with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param roleId the primary key of the role
	* @throws PortalException if a role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRole(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_roleLocalService.deleteRole(roleId);
	}

	/**
	* Deletes the role from the database. Also notifies the appropriate model listeners.
	*
	* @param role the role
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRole(com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_roleLocalService.deleteRole(role);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the role with the primary key.
	*
	* @param roleId the primary key of the role
	* @return the role
	* @throws PortalException if a role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Role getRole(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getRole(roleId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Role> getRoles(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getRoles(start, end);
	}

	/**
	* Returns the number of roles.
	*
	* @return the number of roles
	* @throws SystemException if a system exception occurred
	*/
	public int getRolesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getRolesCount();
	}

	/**
	* Updates the role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param role the role
	* @return the role that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Role updateRole(
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.updateRole(role);
	}

	/**
	* Updates the role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param role the role
	* @param merge whether to merge the role with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the role that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Role updateRole(
		com.liferay.portal.model.Role role, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.updateRole(role, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _roleLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_roleLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.Role addRole(long userId, long companyId,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.addRole(userId, companyId, name, titleMap,
			descriptionMap, type);
	}

	public com.liferay.portal.model.Role addRole(long userId, long companyId,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int type, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.addRole(userId, companyId, name, titleMap,
			descriptionMap, type, className, classPK);
	}

	public void addUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_roleLocalService.addUserRoles(userId, roleIds);
	}

	public void checkSystemRoles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_roleLocalService.checkSystemRoles();
	}

	public void checkSystemRoles(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_roleLocalService.checkSystemRoles(companyId);
	}

	public com.liferay.portal.model.Role fetchRole(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.fetchRole(companyId, name);
	}

	public com.liferay.portal.model.Role getDefaultGroupRole(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getDefaultGroupRole(groupId);
	}

	public java.util.List<com.liferay.portal.model.Role> getGroupRoles(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getGroupRoles(groupId);
	}

	public java.util.Map<java.lang.String, java.util.List<java.lang.String>> getResourceRoles(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getResourceRoles(companyId, name, scope,
			primKey);
	}

	public java.util.List<com.liferay.portal.model.Role> getResourceRoles(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getResourceRoles(companyId, name, scope,
			primKey, actionId);
	}

	public com.liferay.portal.model.Role getRole(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getRole(companyId, name);
	}

	public java.util.List<com.liferay.portal.model.Role> getRoles(int type,
		java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getRoles(type, subtype);
	}

	public java.util.List<com.liferay.portal.model.Role> getRoles(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getRoles(companyId);
	}

	public java.util.List<com.liferay.portal.model.Role> getRoles(
		long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getRoles(roleIds);
	}

	public java.util.List<com.liferay.portal.model.Role> getSubtypeRoles(
		java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getSubtypeRoles(subtype);
	}

	public int getSubtypeRolesCount(java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getSubtypeRolesCount(subtype);
	}

	public com.liferay.portal.model.Role getTeamRole(long companyId, long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getTeamRole(companyId, teamId);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserGroupGroupRoles(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getUserGroupGroupRoles(userId, groupId);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserGroupRoles(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getUserGroupRoles(userId, groupId);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getUserRelatedRoles(userId, groups);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getUserRelatedRoles(userId, groupId);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getUserRelatedRoles(userId, groupIds);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserRoles(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.getUserRoles(userId);
	}

	public boolean hasUserRole(long userId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.hasUserRole(userId, roleId);
	}

	/**
	* Returns <code>true</code> if the user has the regular role.
	*
	* @return <code>true</code> if the user has the regular role
	*/
	public boolean hasUserRole(long userId, long companyId,
		java.lang.String name, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.hasUserRole(userId, companyId, name, inherited);
	}

	/**
	* Returns <code>true</code> if the user has any one of the specified
	* regular roles.
	*
	* @return <code>true</code> if the user has the regular role
	*/
	public boolean hasUserRoles(long userId, long companyId,
		java.lang.String[] names, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.hasUserRoles(userId, companyId, names,
			inherited);
	}

	public com.liferay.portal.model.Role loadRoleFetch(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.loadRoleFetch(companyId, name);
	}

	public com.liferay.portal.model.Role loadRoleFind(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.loadRoleFind(companyId, name);
	}

	public java.util.List<com.liferay.portal.model.Role> search(
		long companyId, java.lang.String keywords, java.lang.Integer[] types,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.search(companyId, keywords, types, start, end,
			obc);
	}

	public java.util.List<com.liferay.portal.model.Role> search(
		long companyId, java.lang.String keywords, java.lang.Integer[] types,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.search(companyId, keywords, types, params,
			start, end, obc);
	}

	public java.util.List<com.liferay.portal.model.Role> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer[] types, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.search(companyId, name, description, types,
			start, end, obc);
	}

	public java.util.List<com.liferay.portal.model.Role> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer[] types,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.search(companyId, name, description, types,
			params, start, end, obc);
	}

	public int searchCount(long companyId, java.lang.String keywords,
		java.lang.Integer[] types)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.searchCount(companyId, keywords, types);
	}

	public int searchCount(long companyId, java.lang.String keywords,
		java.lang.Integer[] types,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.searchCount(companyId, keywords, types, params);
	}

	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer[] types)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.searchCount(companyId, name, description, types);
	}

	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer[] types,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.searchCount(companyId, name, description,
			types, params);
	}

	public void setUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_roleLocalService.setUserRoles(userId, roleIds);
	}

	public void unsetUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_roleLocalService.unsetUserRoles(userId, roleIds);
	}

	public com.liferay.portal.model.Role updateRole(long roleId,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _roleLocalService.updateRole(roleId, name, titleMap,
			descriptionMap, subtype);
	}

	public RoleLocalService getWrappedRoleLocalService() {
		return _roleLocalService;
	}

	public void setWrappedRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	private RoleLocalService _roleLocalService;
}
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the local service interface for Role. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see RoleLocalServiceUtil
 * @see com.liferay.portal.service.base.RoleLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.RoleLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface RoleLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RoleLocalServiceUtil} to access the role local service. Add custom service methods to {@link com.liferay.portal.service.impl.RoleLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the role to the database. Also notifies the appropriate model listeners.
	*
	* @param role the role
	* @return the role that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Role addRole(
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new role with the primary key. Does not add the role to the database.
	*
	* @param roleId the primary key for the new role
	* @return the new role
	*/
	public com.liferay.portal.model.Role createRole(long roleId);

	/**
	* Deletes the role with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param roleId the primary key of the role
	* @return the role that was removed
	* @throws PortalException if a role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Role deleteRole(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the role from the database. Also notifies the appropriate model listeners.
	*
	* @param role the role
	* @return the role that was removed
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Role deleteRole(
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

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
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Role fetchRole(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the role with the primary key.
	*
	* @param roleId the primary key of the role
	* @return the role
	* @throws PortalException if a role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Role getRole(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of roles
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getRoles(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of roles.
	*
	* @return the number of roles
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRolesCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param role the role
	* @return the role that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Role updateRole(
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addGroupRole(long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addGroupRole(long groupId, com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addGroupRoles(long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addGroupRoles(long groupId,
		java.util.List<com.liferay.portal.model.Role> Roles)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void clearGroupRoles(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteGroupRole(long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteGroupRole(long groupId, com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteGroupRoles(long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteGroupRoles(long groupId,
		java.util.List<com.liferay.portal.model.Role> Roles)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getGroupRoles(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getGroupRoles(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getGroupRoles(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupRolesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasGroupRole(long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasGroupRoles(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void setGroupRoles(long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addUserRole(long userId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addUserRole(long userId, com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void addUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void addUserRoles(long userId,
		java.util.List<com.liferay.portal.model.Role> Roles)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void clearUserRoles(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteUserRole(long userId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteUserRole(long userId, com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteUserRoles(long userId,
		java.util.List<com.liferay.portal.model.Role> Roles)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getUserRoles(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getUserRoles(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getUserRoles(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserRolesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserRole(long userId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserRoles(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void setUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* Adds a role. The user is reindexed after role is added.
	*
	* @param userId the primary key of the user
	* @param companyId the primary key of the company
	* @param name the role's name
	* @param titleMap the role's localized titles (optionally
	<code>null</code>)
	* @param descriptionMap the role's localized descriptions (optionally
	<code>null</code>)
	* @param type the role's type (optionally <code>0</code>)
	* @return the role
	* @throws PortalException if the class name or the role name were
	invalid, if the role is a duplicate, or if a user with the
	primary key could not be found
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #addRole(long, String, long,
	String, Map, Map, int, String, ServiceContext)}
	*/
	public com.liferay.portal.model.Role addRole(long userId, long companyId,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds a role with additional parameters. The user is reindexed after role
	* is added.
	*
	* @param userId the primary key of the user
	* @param companyId the primary key of the company
	* @param name the role's name
	* @param titleMap the role's localized titles (optionally
	<code>null</code>)
	* @param descriptionMap the role's localized descriptions (optionally
	<code>null</code>)
	* @param type the role's type (optionally <code>0</code>)
	* @param className the name of the class for which the role is created
	(optionally <code>null</code>)
	* @param classPK the primary key of the class for which the role is
	created (optionally <code>0</code>)
	* @return the role
	* @throws PortalException if the class name or the role name were
	invalid, if the role is a duplicate, or if a user with the
	primary key could not be found
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #addRole(long, String, long,
	String, Map, Map, int, String, ServiceContext)}
	*/
	public com.liferay.portal.model.Role addRole(long userId, long companyId,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int type, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	* @throws PortalException if the class name or the role name were invalid,
	if the role is a duplicate, or if a user with the primary key
	could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Role addRole(long userId,
		java.lang.String className, long classPK, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int type, java.lang.String subtype,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Checks to ensure that the system roles map has appropriate default roles
	* in each company.
	*
	* @throws PortalException if the current user did not have permission to
	set applicable permissions on a role
	* @throws SystemException if a system exception occurred
	*/
	public void checkSystemRoles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Checks to ensure that the system roles map has appropriate default roles
	* in the company.
	*
	* @param companyId the primary key of the company
	* @throws PortalException if the current user did not have permission to
	set applicable permissions on a role
	* @throws SystemException if a system exception occurred
	*/
	public void checkSystemRoles(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Role fetchRole(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Role fetchRoleByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @param groupId the primary key of the group
	* @return the default role for the group with the primary key
	* @throws PortalException if a group with the primary key could not be
	found, or if a default role could not be found for the group
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Role getDefaultGroupRole(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getResourceBlockRoles(
		long resourceBlockId, java.lang.String className,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a map of role names to associated action IDs for the named
	* resource in the company within the permission scope.
	*
	* @param companyId the primary key of the company
	* @param name the resource name
	* @param scope the permission scope
	* @param primKey the primary key of the resource's class
	* @return the role names and action IDs
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.RoleFinder#findByC_N_S_P(
	long, String, int, String)
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.Map<java.lang.String, java.util.List<java.lang.String>> getResourceRoles(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.RoleFinder#findByC_N_S_P_A(
	long, String, int, String, String)
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getResourceRoles(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @throws PortalException if a role with the name could not be found in the
	company
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Role getRole(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the roles of the type and subtype.
	*
	* @param type the role's type (optionally <code>0</code>)
	* @param subtype the role's subtype (optionally <code>null</code>)
	* @return the roles of the type and subtype
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getRoles(int type,
		java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the roles in the company.
	*
	* @param companyId the primary key of the company
	* @return the roles in the company
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getRoles(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the roles with the primary keys.
	*
	* @param roleIds the primary keys of the roles
	* @return the roles with the primary keys
	* @throws PortalException if any one of the roles with the primary keys
	could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getRoles(
		long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the roles of the subtype.
	*
	* @param subtype the role's subtype (optionally <code>null</code>)
	* @return the roles of the subtype
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getSubtypeRoles(
		java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of roles of the subtype.
	*
	* @param subtype the role's subtype (optionally <code>null</code>)
	* @return the number of roles of the subtype
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSubtypeRolesCount(java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the team role in the company.
	*
	* @param companyId the primary key of the company
	* @param teamId the primary key of the team
	* @return the team role in the company
	* @throws PortalException if a role could not be found in the team and
	company
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Role getTeamRole(long companyId, long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the roles of the type.
	*
	* @param type the role's type (optionally <code>0</code>)
	* @return the range of the roles of the type
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getTypeRoles(int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the roles of the type.
	*
	* @param type the role's type (optionally <code>0</code>)
	* @param start the lower bound of the range of roles to return
	* @param end the upper bound of the range of roles to return (not
	inclusive)
	* @return the range of the roles of the type
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getTypeRoles(
		int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of roles of the type.
	*
	* @param type the role's type (optionally <code>0</code>)
	* @return the number of roles of the type
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTypeRolesCount(int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the user's roles within the user group.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @return the user's roles within the user group
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.RoleFinder#findByUserGroupGroupRole(
	long, long)
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getUserGroupGroupRoles(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the user's roles within the user group.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @return the user's roles within the user group
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.RoleFinder#findByUserGroupRole(
	long, long)
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getUserGroupRoles(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the union of all the user's roles within the groups.
	*
	* @param userId the primary key of the user
	* @param groups the groups (optionally <code>null</code>)
	* @return the union of all the user's roles within the groups
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.RoleFinder#findByU_G(
	long, List)
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the user's roles within the group.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @return the user's roles within the group
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.RoleFinder#findByU_G(
	long, long)
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the union of all the user's roles within the groups.
	*
	* @param userId the primary key of the user
	* @param groupIds the primary keys of the groups
	* @return the union of all the user's roles within the groups
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.RoleFinder#findByU_G(
	long, long[])
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @throws PortalException if a default user for the company could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserRole(long userId, long companyId,
		java.lang.String name, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	* @throws PortalException if any one of the roles with the names could not
	be found in the company or if the default user for the company
	could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserRoles(long userId, long companyId,
		java.lang.String[] names, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a role with the name in the company.
	*
	* @param companyId the primary key of the company
	* @param name the role's name (optionally <code>null</code>)
	* @return the role with the name, or <code>null</code> if a role with the
	name could not be found in the company
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Role loadFetchRole(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a role with the name in the company.
	*
	* @param companyId the primary key of the company
	* @param name the role's name
	* @return the role with the name in the company
	* @throws PortalException if a role with the name could not be found in the
	company
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Role loadGetRole(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.RoleFinder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> search(
		long companyId, java.lang.String keywords, java.lang.Integer[] types,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	com.liferay.portal.service.persistence.RoleFinder}
	* @param start the lower bound of the range of roles to return
	* @param end the upper bound of the range of roles to return (not
	inclusive)
	* @param obc the comparator to order the roles (optionally
	<code>null</code>)
	* @return the ordered range of the matching roles, ordered by
	<code>obc</code>
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.RoleFinder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> search(
		long companyId, java.lang.String keywords, java.lang.Integer[] types,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.RoleFinder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer[] types, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	com.liferay.portal.service.persistence.RoleFinder}
	* @param start the lower bound of the range of the roles to return
	* @param end the upper bound of the range of the roles to return (not
	inclusive)
	* @param obc the comparator to order the roles (optionally
	<code>null</code>)
	* @return the ordered range of the matching roles, ordered by
	<code>obc</code>
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.RoleFinder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Role> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer[] types,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of roles that match the keywords and types.
	*
	* @param companyId the primary key of the company
	* @param keywords the keywords (space separated), which may occur in the
	role's name or description (optionally <code>null</code>)
	* @param types the role types (optionally <code>null</code>)
	* @return the number of matching roles
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, java.lang.String keywords,
		java.lang.Integer[] types)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of roles that match the keywords, types and params.
	*
	* @param companyId the primary key of the company
	* @param keywords the keywords (space separated), which may occur in the
	role's name or description (optionally <code>null</code>)
	* @param types the role types (optionally <code>null</code>)
	* @param params the finder parameters. For more information, see {@link
	com.liferay.portal.service.persistence.RoleFinder}
	* @return the number of matching roles
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, java.lang.String keywords,
		java.lang.Integer[] types,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of roles that match the name, description, and types.
	*
	* @param companyId the primary key of the company
	* @param name the role's name (optionally <code>null</code>)
	* @param description the role's description (optionally <code>null</code>)
	* @param types the role types (optionally <code>null</code>)
	* @return the number of matching roles
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer[] types)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	com.liferay.portal.service.persistence.RoleFinder}
	* @return the number of matching roles
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer[] types,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the matching roles associated with the user. The user is
	* reindexed after the roles are removed.
	*
	* @param userId the primary key of the user
	* @param roleIds the primary keys of the roles
	* @throws PortalException if a user with the primary key could not be found
	or if a role with any one of the primary keys could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void unsetUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	* @throws PortalException if a role with the primary could not be found or
	if the role's name was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Role updateRole(long roleId,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String subtype,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}
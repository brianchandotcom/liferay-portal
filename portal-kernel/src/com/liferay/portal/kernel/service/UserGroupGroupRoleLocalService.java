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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.persistence.UserGroupGroupRolePK;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for UserGroupGroupRole. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRoleLocalServiceUtil
 * @see com.liferay.portal.service.base.UserGroupGroupRoleLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.UserGroupGroupRoleLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface UserGroupGroupRoleLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserGroupGroupRoleLocalServiceUtil} to access the user group group role local service. Add custom service methods to {@link com.liferay.portal.service.impl.UserGroupGroupRoleLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserGroupGroupRole(long userGroupId, long groupId,
		java.lang.String roleName) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserGroupGroupRole(long userGroupId, long groupId,
		long roleId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Adds the user group group role to the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupGroupRole the user group group role
	* @return the user group group role that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public UserGroupGroupRole addUserGroupGroupRole(
		UserGroupGroupRole userGroupGroupRole);

	/**
	* Creates a new user group group role with the primary key. Does not add the user group group role to the database.
	*
	* @param userGroupGroupRolePK the primary key for the new user group group role
	* @return the new user group group role
	*/
	public UserGroupGroupRole createUserGroupGroupRole(
		UserGroupGroupRolePK userGroupGroupRolePK);

	/**
	* Deletes the user group group role from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupGroupRole the user group group role
	* @return the user group group role that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public UserGroupGroupRole deleteUserGroupGroupRole(
		UserGroupGroupRole userGroupGroupRole);

	/**
	* Deletes the user group group role with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupGroupRolePK the primary key of the user group group role
	* @return the user group group role that was removed
	* @throws PortalException if a user group group role with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public UserGroupGroupRole deleteUserGroupGroupRole(
		UserGroupGroupRolePK userGroupGroupRolePK) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserGroupGroupRole fetchUserGroupGroupRole(
		UserGroupGroupRolePK userGroupGroupRolePK);

	/**
	* Returns the user group group role with the primary key.
	*
	* @param userGroupGroupRolePK the primary key of the user group group role
	* @return the user group group role
	* @throws PortalException if a user group group role with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserGroupGroupRole getUserGroupGroupRole(
		UserGroupGroupRolePK userGroupGroupRolePK) throws PortalException;

	/**
	* Updates the user group group role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param userGroupGroupRole the user group group role
	* @return the user group group role that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public UserGroupGroupRole updateUserGroupGroupRole(
		UserGroupGroupRole userGroupGroupRole);

	/**
	* Returns the number of user group group roles.
	*
	* @return the number of user group group roles
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserGroupGroupRolesCount();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns a range of all the user group group roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @return the range of user group group roles
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserGroupGroupRole> getUserGroupGroupRoles(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserGroupGroupRole> getUserGroupGroupRoles(long userGroupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserGroupGroupRole> getUserGroupGroupRoles(long userGroupId,
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserGroupGroupRole> getUserGroupGroupRolesByGroupAndRole(
		long groupId, long roleId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserGroupGroupRole> getUserGroupGroupRolesByUser(long userId);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public void addUserGroupGroupRoles(long userGroupId, long groupId,
		long[] roleIds);

	public void addUserGroupGroupRoles(long[] userGroupIds, long groupId,
		long roleId);

	public void deleteUserGroupGroupRoles(long groupId, int roleType);

	public void deleteUserGroupGroupRoles(long userGroupId, long groupId,
		long[] roleIds);

	public void deleteUserGroupGroupRoles(long userGroupId, long[] groupIds);

	public void deleteUserGroupGroupRoles(long[] userGroupIds, long groupId);

	public void deleteUserGroupGroupRoles(long[] userGroupIds, long groupId,
		long roleId);

	public void deleteUserGroupGroupRolesByGroupId(long groupId);

	public void deleteUserGroupGroupRolesByRoleId(long roleId);

	public void deleteUserGroupGroupRolesByUserGroupId(long userGroupId);
}
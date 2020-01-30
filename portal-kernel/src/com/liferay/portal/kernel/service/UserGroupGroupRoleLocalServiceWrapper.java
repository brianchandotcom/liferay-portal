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
 * Provides a wrapper for {@link UserGroupGroupRoleLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRoleLocalService
 * @generated
 */
public class UserGroupGroupRoleLocalServiceWrapper
	implements ServiceWrapper<UserGroupGroupRoleLocalService>,
			   UserGroupGroupRoleLocalService {

	public UserGroupGroupRoleLocalServiceWrapper(
		UserGroupGroupRoleLocalService userGroupGroupRoleLocalService) {

		_userGroupGroupRoleLocalService = userGroupGroupRoleLocalService;
	}

	/**
	 * Adds the user group group role to the database. Also notifies the appropriate model listeners.
	 *
	 * @param userGroupGroupRole the user group group role
	 * @return the user group group role that was added
	 */
	@Override
	public com.liferay.portal.kernel.model.UserGroupGroupRole
		addUserGroupGroupRole(
			com.liferay.portal.kernel.model.UserGroupGroupRole
				userGroupGroupRole) {

		return _userGroupGroupRoleLocalService.addUserGroupGroupRole(
			userGroupGroupRole);
	}

	@Override
	public void addUserGroupGroupRoles(
		long userGroupId, long groupId, long[] roleIds) {

		_userGroupGroupRoleLocalService.addUserGroupGroupRoles(
			userGroupId, groupId, roleIds);
	}

	@Override
	public void addUserGroupGroupRoles(
		long[] userGroupIds, long groupId, long roleId) {

		_userGroupGroupRoleLocalService.addUserGroupGroupRoles(
			userGroupIds, groupId, roleId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _userGroupGroupRoleLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new user group group role with the primary key. Does not add the user group group role to the database.
	 *
	 * @param userGroupGroupRoleId the primary key for the new user group group role
	 * @return the new user group group role
	 */
	@Override
	public com.liferay.portal.kernel.model.UserGroupGroupRole
		createUserGroupGroupRole(long userGroupGroupRoleId) {

		return _userGroupGroupRoleLocalService.createUserGroupGroupRole(
			userGroupGroupRoleId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _userGroupGroupRoleLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the user group group role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userGroupGroupRoleId the primary key of the user group group role
	 * @return the user group group role that was removed
	 * @throws PortalException if a user group group role with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.UserGroupGroupRole
			deleteUserGroupGroupRole(long userGroupGroupRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _userGroupGroupRoleLocalService.deleteUserGroupGroupRole(
			userGroupGroupRoleId);
	}

	/**
	 * Deletes the user group group role from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userGroupGroupRole the user group group role
	 * @return the user group group role that was removed
	 */
	@Override
	public com.liferay.portal.kernel.model.UserGroupGroupRole
		deleteUserGroupGroupRole(
			com.liferay.portal.kernel.model.UserGroupGroupRole
				userGroupGroupRole) {

		return _userGroupGroupRoleLocalService.deleteUserGroupGroupRole(
			userGroupGroupRole);
	}

	@Override
	public void deleteUserGroupGroupRoles(long groupId, int roleType) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
			groupId, roleType);
	}

	@Override
	public void deleteUserGroupGroupRoles(
		long userGroupId, long groupId, long[] roleIds) {

		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
			userGroupId, groupId, roleIds);
	}

	@Override
	public void deleteUserGroupGroupRoles(long userGroupId, long[] groupIds) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
			userGroupId, groupIds);
	}

	@Override
	public void deleteUserGroupGroupRoles(long[] userGroupIds, long groupId) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
			userGroupIds, groupId);
	}

	@Override
	public void deleteUserGroupGroupRoles(
		long[] userGroupIds, long groupId, long roleId) {

		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
			userGroupIds, groupId, roleId);
	}

	@Override
	public void deleteUserGroupGroupRolesByGroupId(long groupId) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByGroupId(
			groupId);
	}

	@Override
	public void deleteUserGroupGroupRolesByRoleId(long roleId) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByRoleId(
			roleId);
	}

	@Override
	public void deleteUserGroupGroupRolesByUserGroupId(long userGroupId) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByUserGroupId(
			userGroupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _userGroupGroupRoleLocalService.dynamicQuery();
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

		return _userGroupGroupRoleLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl</code>.
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

		return _userGroupGroupRoleLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl</code>.
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

		return _userGroupGroupRoleLocalService.dynamicQuery(
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

		return _userGroupGroupRoleLocalService.dynamicQueryCount(dynamicQuery);
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

		return _userGroupGroupRoleLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.UserGroupGroupRole
		fetchUserGroupGroupRole(long userGroupGroupRoleId) {

		return _userGroupGroupRoleLocalService.fetchUserGroupGroupRole(
			userGroupGroupRoleId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _userGroupGroupRoleLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _userGroupGroupRoleLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _userGroupGroupRoleLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _userGroupGroupRoleLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the user group group role with the primary key.
	 *
	 * @param userGroupGroupRoleId the primary key of the user group group role
	 * @return the user group group role
	 * @throws PortalException if a user group group role with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.UserGroupGroupRole
			getUserGroupGroupRole(long userGroupGroupRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _userGroupGroupRoleLocalService.getUserGroupGroupRole(
			userGroupGroupRoleId);
	}

	/**
	 * Returns a range of all the user group group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of user group group roles
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.UserGroupGroupRole>
		getUserGroupGroupRoles(int start, int end) {

		return _userGroupGroupRoleLocalService.getUserGroupGroupRoles(
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.UserGroupGroupRole>
		getUserGroupGroupRoles(long userGroupId) {

		return _userGroupGroupRoleLocalService.getUserGroupGroupRoles(
			userGroupId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.UserGroupGroupRole>
		getUserGroupGroupRoles(long userGroupId, long groupId) {

		return _userGroupGroupRoleLocalService.getUserGroupGroupRoles(
			userGroupId, groupId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.UserGroupGroupRole>
		getUserGroupGroupRolesByGroupAndRole(long groupId, long roleId) {

		return _userGroupGroupRoleLocalService.
			getUserGroupGroupRolesByGroupAndRole(groupId, roleId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.UserGroupGroupRole>
		getUserGroupGroupRolesByUser(long userId) {

		return _userGroupGroupRoleLocalService.getUserGroupGroupRolesByUser(
			userId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.UserGroupGroupRole>
		getUserGroupGroupRolesByUser(long userId, long groupId) {

		return _userGroupGroupRoleLocalService.getUserGroupGroupRolesByUser(
			userId, groupId);
	}

	/**
	 * Returns the number of user group group roles.
	 *
	 * @return the number of user group group roles
	 */
	@Override
	public int getUserGroupGroupRolesCount() {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRolesCount();
	}

	@Override
	public boolean hasUserGroupGroupRole(
		long userGroupId, long groupId, long roleId) {

		return _userGroupGroupRoleLocalService.hasUserGroupGroupRole(
			userGroupId, groupId, roleId);
	}

	@Override
	public boolean hasUserGroupGroupRole(
			long userGroupId, long groupId, java.lang.String roleName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _userGroupGroupRoleLocalService.hasUserGroupGroupRole(
			userGroupId, groupId, roleName);
	}

	/**
	 * Updates the user group group role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param userGroupGroupRole the user group group role
	 * @return the user group group role that was updated
	 */
	@Override
	public com.liferay.portal.kernel.model.UserGroupGroupRole
		updateUserGroupGroupRole(
			com.liferay.portal.kernel.model.UserGroupGroupRole
				userGroupGroupRole) {

		return _userGroupGroupRoleLocalService.updateUserGroupGroupRole(
			userGroupGroupRole);
	}

	@Override
	public UserGroupGroupRoleLocalService getWrappedService() {
		return _userGroupGroupRoleLocalService;
	}

	@Override
	public void setWrappedService(
		UserGroupGroupRoleLocalService userGroupGroupRoleLocalService) {

		_userGroupGroupRoleLocalService = userGroupGroupRoleLocalService;
	}

	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;

}
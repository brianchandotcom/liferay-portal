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

/**
 * Provides a wrapper for {@link UserLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       UserLocalService
 * @generated
 */
public class UserLocalServiceWrapper implements UserLocalService,
	ServiceWrapper<UserLocalService> {
	public UserLocalServiceWrapper(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	/**
	* Adds the user to the database. Also notifies the appropriate model listeners.
	*
	* @param user the user
	* @return the user that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User addUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.addUser(user);
	}

	/**
	* Creates a new user with the primary key. Does not add the user to the database.
	*
	* @param userId the primary key for the new user
	* @return the new user
	*/
	public com.liferay.portal.model.User createUser(long userId) {
		return _userLocalService.createUser(userId);
	}

	/**
	* Deletes the user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userId the primary key of the user
	* @return the user that was removed
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User deleteUser(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.deleteUser(userId);
	}

	/**
	* Deletes the user from the database. Also notifies the appropriate model listeners.
	*
	* @param user the user
	* @return the user that was removed
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User deleteUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.deleteUser(user);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _userLocalService.dynamicQuery();
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
		return _userLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _userLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _userLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _userLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.User fetchUser(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.fetchUser(userId);
	}

	/**
	* Returns the user with the primary key.
	*
	* @param userId the primary key of the user
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User getUser(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUser(userId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUsers(start, end);
	}

	/**
	* Returns the number of users.
	*
	* @return the number of users
	* @throws SystemException if a system exception occurred
	*/
	public int getUsersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUsersCount();
	}

	/**
	* Updates the user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param user the user
	* @return the user that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateUser(user);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addGroupUser(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addGroupUser(groupId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addGroupUser(long groupId, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addGroupUser(groupId, user);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void addGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addGroupUsers(groupId, userIds);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void addGroupUsers(long groupId,
		java.util.List<com.liferay.portal.model.User> Users)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addGroupUsers(groupId, Users);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void clearGroupUsers(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.clearGroupUsers(groupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteGroupUser(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteGroupUser(groupId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteGroupUser(long groupId, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteGroupUser(groupId, user);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteGroupUsers(groupId, userIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteGroupUsers(long groupId,
		java.util.List<com.liferay.portal.model.User> Users)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteGroupUsers(groupId, Users);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getGroupUsers(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getGroupUsers(groupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getGroupUsers(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getGroupUsers(groupId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getGroupUsers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getGroupUsers(groupId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public int getGroupUsersCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getGroupUsersCount(groupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasGroupUser(groupId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasGroupUsers(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasGroupUsers(groupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void setGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.setGroupUsers(groupId, userIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addOrganizationUser(long organizationId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addOrganizationUser(organizationId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addOrganizationUser(long organizationId,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addOrganizationUser(organizationId, user);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void addOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addOrganizationUsers(organizationId, userIds);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void addOrganizationUsers(long organizationId,
		java.util.List<com.liferay.portal.model.User> Users)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addOrganizationUsers(organizationId, Users);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void clearOrganizationUsers(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.clearOrganizationUsers(organizationId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteOrganizationUser(long organizationId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteOrganizationUser(organizationId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteOrganizationUser(long organizationId,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteOrganizationUser(organizationId, user);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteOrganizationUsers(organizationId, userIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteOrganizationUsers(long organizationId,
		java.util.List<com.liferay.portal.model.User> Users)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteOrganizationUsers(organizationId, Users);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getOrganizationUsers(
		long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getOrganizationUsers(organizationId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getOrganizationUsers(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getOrganizationUsers(organizationId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getOrganizationUsers(
		long organizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getOrganizationUsers(organizationId, start,
			end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public int getOrganizationUsersCount(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getOrganizationUsersCount(organizationId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasOrganizationUser(long organizationId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasOrganizationUser(organizationId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasOrganizationUsers(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasOrganizationUsers(organizationId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void setOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.setOrganizationUsers(organizationId, userIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addRoleUser(long roleId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addRoleUser(roleId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addRoleUser(long roleId, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addRoleUser(roleId, user);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void addRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addRoleUsers(roleId, userIds);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void addRoleUsers(long roleId,
		java.util.List<com.liferay.portal.model.User> Users)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addRoleUsers(roleId, Users);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void clearRoleUsers(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.clearRoleUsers(roleId);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRoleUser(long roleId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteRoleUser(roleId, userId);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRoleUser(long roleId, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteRoleUser(roleId, user);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteRoleUsers(roleId, userIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRoleUsers(long roleId,
		java.util.List<com.liferay.portal.model.User> Users)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteRoleUsers(roleId, Users);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getRoleUsers(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getRoleUsers(roleId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getRoleUsers(
		long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getRoleUsers(roleId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getRoleUsers(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getRoleUsers(roleId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public int getRoleUsersCount(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getRoleUsersCount(roleId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasRoleUser(long roleId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasRoleUser(roleId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasRoleUsers(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasRoleUsers(roleId);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void setRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.setRoleUsers(roleId, userIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addTeamUser(long teamId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addTeamUser(teamId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addTeamUser(long teamId, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addTeamUser(teamId, user);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void addTeamUsers(long teamId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addTeamUsers(teamId, userIds);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void addTeamUsers(long teamId,
		java.util.List<com.liferay.portal.model.User> Users)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addTeamUsers(teamId, Users);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void clearTeamUsers(long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.clearTeamUsers(teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteTeamUser(long teamId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteTeamUser(teamId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteTeamUser(long teamId, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteTeamUser(teamId, user);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteTeamUsers(long teamId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteTeamUsers(teamId, userIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteTeamUsers(long teamId,
		java.util.List<com.liferay.portal.model.User> Users)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteTeamUsers(teamId, Users);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getTeamUsers(
		long teamId) throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getTeamUsers(teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getTeamUsers(
		long teamId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getTeamUsers(teamId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getTeamUsers(
		long teamId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getTeamUsers(teamId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public int getTeamUsersCount(long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getTeamUsersCount(teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasTeamUser(long teamId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasTeamUser(teamId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasTeamUsers(long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasTeamUsers(teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void setTeamUsers(long teamId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.setTeamUsers(teamId, userIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addUserGroupUser(long userGroupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addUserGroupUser(userGroupId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addUserGroupUser(long userGroupId,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addUserGroupUser(userGroupId, user);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void addUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addUserGroupUsers(userGroupId, userIds);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void addUserGroupUsers(long userGroupId,
		java.util.List<com.liferay.portal.model.User> Users)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addUserGroupUsers(userGroupId, Users);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void clearUserGroupUsers(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.clearUserGroupUsers(userGroupId);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void deleteUserGroupUser(long userGroupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteUserGroupUser(userGroupId, userId);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void deleteUserGroupUser(long userGroupId,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteUserGroupUser(userGroupId, user);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteUserGroupUsers(userGroupId, userIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteUserGroupUsers(long userGroupId,
		java.util.List<com.liferay.portal.model.User> Users)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteUserGroupUsers(userGroupId, Users);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getUserGroupUsers(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserGroupUsers(userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getUserGroupUsers(
		long userGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserGroupUsers(userGroupId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getUserGroupUsers(
		long userGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserGroupUsers(userGroupId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public int getUserGroupUsersCount(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserGroupUsersCount(userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasUserGroupUser(long userGroupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasUserGroupUser(userGroupId, userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasUserGroupUsers(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasUserGroupUsers(userGroupId);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void setUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.setUserGroupUsers(userGroupId, userIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _userLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_userLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds a default admin user for the company.
	*
	* @param companyId the primary key of the user's company
	* @param screenName the user's screen name
	* @param emailAddress the user's email address
	* @param locale the user's locale
	* @param firstName the user's first name
	* @param middleName the user's middle name
	* @param lastName the user's last name
	* @return the new default admin user
	* @throws PortalException n if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User addDefaultAdminUser(long companyId,
		java.lang.String screenName, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.addDefaultAdminUser(companyId, screenName,
			emailAddress, locale, firstName, middleName, lastName);
	}

	/**
	* Adds the user to the default groups, unless the user is already in these
	* groups. The default groups can be specified in
	* <code>portal.properties</code> with the key
	* <code>admin.default.group.names</code>.
	*
	* @param userId the primary key of the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void addDefaultGroups(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addDefaultGroups(userId);
	}

	/**
	* Adds the user to the default roles, unless the user already has these
	* roles. The default roles can be specified in
	* <code>portal.properties</code> with the key
	* <code>admin.default.role.names</code>.
	*
	* @param userId the primary key of the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void addDefaultRoles(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addDefaultRoles(userId);
	}

	/**
	* Adds the user to the default user groups, unless the user is already in
	* these user groups. The default user groups can be specified in
	* <code>portal.properties</code> with the property
	* <code>admin.default.user.group.names</code>.
	*
	* @param userId the primary key of the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void addDefaultUserGroups(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addDefaultUserGroups(userId);
	}

	/**
	* Assigns the password policy to the users, removing any other currently
	* assigned password policies.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param userIds the primary keys of the users
	* @throws SystemException if a system exception occurred
	*/
	public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	/**
	* Adds a user.
	*
	* <p>
	* This method handles the creation and bookkeeping of the user including
	* its resources, metadata, and internal data structures. It is not
	* necessary to make subsequent calls to any methods to setup default
	* groups, resources, etc.
	* </p>
	*
	* @param creatorUserId the primary key of the creator
	* @param companyId the primary key of the user's company
	* @param autoPassword whether a password should be automatically generated
	for the user
	* @param password1 the user's password
	* @param password2 the user's password confirmation
	* @param autoScreenName whether a screen name should be automatically
	generated for the user
	* @param screenName the user's screen name
	* @param emailAddress the user's email address
	* @param facebookId the user's facebook ID
	* @param openId the user's OpenID
	* @param locale the user's locale
	* @param firstName the user's first name
	* @param middleName the user's middle name
	* @param lastName the user's last name
	* @param prefixId the user's name prefix ID
	* @param suffixId the user's name suffix ID
	* @param male whether the user is male
	* @param birthdayMonth the user's birthday month (0-based, meaning 0 for
	January)
	* @param birthdayDay the user's birthday day
	* @param birthdayYear the user's birthday year
	* @param jobTitle the user's job title
	* @param groupIds the primary keys of the user's groups
	* @param organizationIds the primary keys of the user's organizations
	* @param roleIds the primary keys of the roles this user possesses
	* @param userGroupIds the primary keys of the user's user groups
	* @param sendEmail whether to send the user an email notification about
	their new account
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the UUID (with the <code>uuid</code>
	attribute), asset category IDs, asset tag names, and expando
	bridge attributes for the user.
	* @return the new user
	* @throws PortalException if the user's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User addUser(long creatorUserId,
		long companyId, boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		long facebookId, java.lang.String openId, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds, boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.addUser(creatorUserId, companyId,
			autoPassword, password1, password2, autoScreenName, screenName,
			emailAddress, facebookId, openId, locale, firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, jobTitle, groupIds, organizationIds, roleIds,
			userGroupIds, sendEmail, serviceContext);
	}

	/**
	* Adds a user with workflow.
	*
	* <p>
	* This method handles the creation and bookkeeping of the user including
	* its resources, metadata, and internal data structures. It is not
	* necessary to make subsequent calls to any methods to setup default
	* groups, resources, etc.
	* </p>
	*
	* @param creatorUserId the primary key of the creator
	* @param companyId the primary key of the user's company
	* @param autoPassword whether a password should be automatically generated
	for the user
	* @param password1 the user's password
	* @param password2 the user's password confirmation
	* @param autoScreenName whether a screen name should be automatically
	generated for the user
	* @param screenName the user's screen name
	* @param emailAddress the user's email address
	* @param facebookId the user's facebook ID
	* @param openId the user's OpenID
	* @param locale the user's locale
	* @param firstName the user's first name
	* @param middleName the user's middle name
	* @param lastName the user's last name
	* @param prefixId the user's name prefix ID
	* @param suffixId the user's name suffix ID
	* @param male whether the user is male
	* @param birthdayMonth the user's birthday month (0-based, meaning 0 for
	January)
	* @param birthdayDay the user's birthday day
	* @param birthdayYear the user's birthday year
	* @param jobTitle the user's job title
	* @param groupIds the primary keys of the user's groups
	* @param organizationIds the primary keys of the user's organizations
	* @param roleIds the primary keys of the roles this user possesses
	* @param userGroupIds the primary keys of the user's user groups
	* @param sendEmail whether to send the user an email notification about
	their new account
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the UUID (with the <code>uuid</code>
	attribute), asset category IDs, asset tag names, and expando
	bridge attributes for the user.
	* @return the new user
	* @throws PortalException if the user's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User addUserWithWorkflow(
		long creatorUserId, long companyId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean autoScreenName, java.lang.String screenName,
		java.lang.String emailAddress, long facebookId,
		java.lang.String openId, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds, boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.addUserWithWorkflow(creatorUserId, companyId,
			autoPassword, password1, password2, autoScreenName, screenName,
			emailAddress, facebookId, openId, locale, firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, jobTitle, groupIds, organizationIds, roleIds,
			userGroupIds, sendEmail, serviceContext);
	}

	/**
	* Attempts to authenticate the user by their email address and password,
	* while using the AuthPipeline.
	*
	* @param companyId the primary key of the user's company
	* @param emailAddress the user's email address
	* @param password the user's password
	* @param headerMap the header map from the authentication request
	* @param parameterMap the parameter map from the authentication request
	* @param resultsMap the map of authentication results (may be nil). After
	a succesful authentication the user's primary key will be placed
	under the key <code>userId</code>.
	* @return the authentication status. This can be {@link
	com.liferay.portal.security.auth.Authenticator#FAILURE}
	indicating that the user's credentials are invalid, {@link
	com.liferay.portal.security.auth.Authenticator#SUCCESS}
	indicating a successful login, or {@link
	com.liferay.portal.security.auth.Authenticator#DNE} indicating
	that a user with that login does not exist.
	* @throws PortalException if <code>emailAddress</code> or
	<code>password</code> was <code>null</code>
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.security.auth.AuthPipeline
	*/
	public int authenticateByEmailAddress(long companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map<java.lang.String, java.lang.String[]> headerMap,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Map<java.lang.String, java.lang.Object> resultsMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.authenticateByEmailAddress(companyId,
			emailAddress, password, headerMap, parameterMap, resultsMap);
	}

	/**
	* Attempts to authenticate the user by their screen name and password,
	* while using the AuthPipeline.
	*
	* @param companyId the primary key of the user's company
	* @param screenName the user's screen name
	* @param password the user's password
	* @param headerMap the header map from the authentication request
	* @param parameterMap the parameter map from the authentication request
	* @param resultsMap the map of authentication results (may be nil). After
	a succesful authentication the user's primary key will be placed
	under the key <code>userId</code>.
	* @return the authentication status. This can be {@link
	com.liferay.portal.security.auth.Authenticator#FAILURE}
	indicating that the user's credentials are invalid, {@link
	com.liferay.portal.security.auth.Authenticator#SUCCESS}
	indicating a successful login, or {@link
	com.liferay.portal.security.auth.Authenticator#DNE} indicating
	that a user with that login does not exist.
	* @throws PortalException if <code>screenName</code> or
	<code>password</code> was <code>null</code>
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.security.auth.AuthPipeline
	*/
	public int authenticateByScreenName(long companyId,
		java.lang.String screenName, java.lang.String password,
		java.util.Map<java.lang.String, java.lang.String[]> headerMap,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Map<java.lang.String, java.lang.Object> resultsMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.authenticateByScreenName(companyId,
			screenName, password, headerMap, parameterMap, resultsMap);
	}

	/**
	* Attempts to authenticate the user by their primary key and password,
	* while using the AuthPipeline.
	*
	* @param companyId the primary key of the user's company
	* @param userId the user's primary key
	* @param password the user's password
	* @param headerMap the header map from the authentication request
	* @param parameterMap the parameter map from the authentication request
	* @param resultsMap the map of authentication results (may be nil). After
	a succesful authentication the user's primary key will be placed
	under the key <code>userId</code>.
	* @return the authentication status. This can be {@link
	com.liferay.portal.security.auth.Authenticator#FAILURE}
	indicating that the user's credentials are invalid, {@link
	com.liferay.portal.security.auth.Authenticator#SUCCESS}
	indicating a successful login, or {@link
	com.liferay.portal.security.auth.Authenticator#DNE} indicating
	that a user with that login does not exist.
	* @throws PortalException if <code>userId</code> or <code>password</code>
	was <code>null</code>
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.security.auth.AuthPipeline
	*/
	public int authenticateByUserId(long companyId, long userId,
		java.lang.String password,
		java.util.Map<java.lang.String, java.lang.String[]> headerMap,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Map<java.lang.String, java.lang.Object> resultsMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.authenticateByUserId(companyId, userId,
			password, headerMap, parameterMap, resultsMap);
	}

	/**
	* Attempts to authenticate the user using HTTP basic access authentication,
	* without using the AuthPipeline. Primarily used for authenticating users
	* of <code>tunnel-web</code>.
	*
	* <p>
	* Authentication type specifies what <code>login</code> contains.The valid
	* values are:
	* </p>
	*
	* <ul>
	* <li>
	* <code>CompanyConstants.AUTH_TYPE_EA</code> - <code>login</code> is the
	* user's email address
	* </li>
	* <li>
	* <code>CompanyConstants.AUTH_TYPE_SN</code> - <code>login</code> is the
	* user's screen name
	* </li>
	* <li>
	* <code>CompanyConstants.AUTH_TYPE_ID</code> - <code>login</code> is the
	* user's primary key
	* </li>
	* </ul>
	*
	* @param companyId the primary key of the user's company
	* @param authType the type of authentication to perform
	* @param login either the user's email address, screen name, or primary
	key depending on the value of <code>authType</code>
	* @param password the user's password
	* @return the authentication status. This can be {@link
	com.liferay.portal.security.auth.Authenticator#FAILURE}
	indicating that the user's credentials are invalid, {@link
	com.liferay.portal.security.auth.Authenticator#SUCCESS}
	indicating a successful login, or {@link
	com.liferay.portal.security.auth.Authenticator#DNE} indicating
	that a user with that login does not exist.
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public long authenticateForBasic(long companyId, java.lang.String authType,
		java.lang.String login, java.lang.String password)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.authenticateForBasic(companyId, authType,
			login, password);
	}

	/**
	* Attempts to authenticate the user using HTTP digest access
	* authentication, without using the AuthPipeline. Primarily used for
	* authenticating users of <code>tunnel-web</code>.
	*
	* @param companyId the primary key of the user's company
	* @param username either the user's email address, screen name, or primary
	key
	* @param realm unused
	* @param nonce the number used once
	* @param method the request method
	* @param uri the request URI
	* @param response the authentication response hash
	* @return the user's primary key if authentication is succesful;
	<code>0</code> otherwise
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public long authenticateForDigest(long companyId,
		java.lang.String username, java.lang.String realm,
		java.lang.String nonce, java.lang.String method, java.lang.String uri,
		java.lang.String response)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.authenticateForDigest(companyId, username,
			realm, nonce, method, uri, response);
	}

	/**
	* Attempts to authenticate the user using JAAS credentials, without using
	* the AuthPipeline.
	*
	* @param userId the primary key of the user
	* @param encPassword the encrypted password
	* @return <code>true</code> if authentication is successful;
	<code>false</code> otherwise
	*/
	public boolean authenticateForJAAS(long userId, java.lang.String encPassword) {
		return _userLocalService.authenticateForJAAS(userId, encPassword);
	}

	/**
	* Checks if the user is currently locked out based on the password policy,
	* and performs maintenance on the user's lockout and failed login data.
	*
	* @param user the user
	* @throws PortalException if the user was determined to still be locked out
	* @throws SystemException if a system exception occurred
	*/
	public void checkLockout(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.checkLockout(user);
	}

	/**
	* Adds a failed login attempt to the user and updates the user's last
	* failed login date.
	*
	* @param user the user
	* @throws SystemException if a system exception occurred
	*/
	public void checkLoginFailure(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.checkLoginFailure(user);
	}

	/**
	* Adds a failed login attempt to the user with the email address and
	* updates the user's last failed login date.
	*
	* @param companyId the primary key of the user's company
	* @param emailAddress the user's email address
	* @throws PortalException if a user with the email address could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public void checkLoginFailureByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.checkLoginFailureByEmailAddress(companyId,
			emailAddress);
	}

	/**
	* Adds a failed login attempt to the user and updates the user's last
	* failed login date.
	*
	* @param userId the primary key of the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void checkLoginFailureById(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.checkLoginFailureById(userId);
	}

	/**
	* Adds a failed login attempt to the user with the screen name and updates
	* the user's last failed login date.
	*
	* @param companyId the primary key of the user's company
	* @param screenName the user's screen name
	* @throws PortalException if a user with the screen name could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void checkLoginFailureByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.checkLoginFailureByScreenName(companyId, screenName);
	}

	/**
	* Checks if the user's password is expired based on the password policy,
	* and performs maintenance on the user's grace login and password reset
	* data.
	*
	* @param user the user
	* @throws PortalException if the user's password has expired and the grace
	login limit has been exceeded
	* @throws SystemException if a system exception occurred
	*/
	public void checkPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.checkPasswordExpired(user);
	}

	/**
	* Completes the user's registration by generating a password and sending
	* the confirmation email.
	*
	* @param user the user
	* @param serviceContext the service context to be applied. Can set whether
	a password should be generated (with the
	<code>autoPassword</code> attribute) and whether the confirmation
	email should be sent (with the <code>sendEmail</code> attribute)
	for the user.
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public void completeUserRegistration(com.liferay.portal.model.User user,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.completeUserRegistration(user, serviceContext);
	}

	/**
	* Decrypts the user's primary key and password from their encrypted forms.
	* Used for decrypting a user's credentials from the values stored in an
	* automatic login cookie.
	*
	* @param companyId the primary key of the user's company
	* @param name the encrypted primary key of the user
	* @param password the encrypted password of the user
	* @return the user's primary key and password
	* @throws PortalException if a user with the primary key could not be found
	or if the user's password was incorrect
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.util.KeyValuePair decryptUserId(
		long companyId, java.lang.String name, java.lang.String password)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.decryptUserId(companyId, name, password);
	}

	/**
	* Deletes the user's portrait image.
	*
	* @param userId the primary key of the user
	* @throws PortalException if a user with the primary key could not be found
	or if the user's portrait could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deletePortrait(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deletePortrait(userId);
	}

	/**
	* Encrypts the primary key of the user. Used when encrypting the user's
	* credentials for storage in an automatic login cookie.
	*
	* @param name the primary key of the user
	* @return the user's encrypted primary key
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String encryptUserId(java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.encryptUserId(name);
	}

	/**
	* Returns the user with the email address.
	*
	* @param companyId the primary key of the user's company
	* @param emailAddress the user's email address
	* @return the user with the email address, or <code>null</code> if a user
	with the email address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchUserByEmailAddress(
		long companyId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.fetchUserByEmailAddress(companyId, emailAddress);
	}

	/**
	* Returns the user with the primary key.
	*
	* @param userId the primary key of the user
	* @return the user with the primary key, or <code>null</code> if a user
	with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchUserById(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.fetchUserById(userId);
	}

	/**
	* Returns the user with the screen name.
	*
	* @param companyId the primary key of the user's company
	* @param screenName the user's screen name
	* @return the user with the screen name, or <code>null</code> if a user
	with the screen name could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchUserByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.fetchUserByScreenName(companyId, screenName);
	}

	/**
	* Returns a range of all the users belonging to the company.
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
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of users belonging to the company
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getCompanyUsers(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getCompanyUsers(companyId, start, end);
	}

	/**
	* Returns the number of users belonging to the company.
	*
	* @param companyId the primary key of the company
	* @return the number of users belonging to the company
	* @throws SystemException if a system exception occurred
	*/
	public int getCompanyUsersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getCompanyUsersCount(companyId);
	}

	/**
	* Returns the default user for the company.
	*
	* @param companyId the primary key of the company
	* @return the default user for the company
	* @throws PortalException if a default user for the company could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User getDefaultUser(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getDefaultUser(companyId);
	}

	/**
	* Returns the primary key of the default user for the company.
	*
	* @param companyId the primary key of the company
	* @return the primary key of the default user for the company
	* @throws PortalException if a default user for the company could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public long getDefaultUserId(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getDefaultUserId(companyId);
	}

	/**
	* Returns the primary keys of all the users belonging to the group.
	*
	* @param groupId the primary key of the group
	* @return the primary keys of the users belonging to the group
	* @throws SystemException if a system exception occurred
	*/
	public long[] getGroupUserIds(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getGroupUserIds(groupId);
	}

	/**
	* Returns the number of users with the status belonging to the group.
	*
	* @param groupId the primary key of the group
	* @param status the workflow status
	* @return the number of users with the status belonging to the group
	* @throws PortalException if a group with the primary key could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public int getGroupUsersCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getGroupUsersCount(groupId, status);
	}

	/**
	* Returns all the users who have not had any announcements of the type
	* delivered, excluding the default user.
	*
	* @param type the type of announcement
	* @return the users who have not had any annoucements of the type delivered
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getNoAnnouncementsDeliveries(
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getNoAnnouncementsDeliveries(type);
	}

	/**
	* Returns all the users who do not have any contacts.
	*
	* @return the users who do not have any contacts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getNoContacts()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getNoContacts();
	}

	/**
	* Returns all the users who do not belong to any groups, excluding the
	* default user.
	*
	* @return the users who do not belong to any groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getNoGroups()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getNoGroups();
	}

	/**
	* Returns the primary keys of all the users belonging to the organization.
	*
	* @param organizationId the primary key of the organization
	* @return the primary keys of the users belonging to the organization
	* @throws SystemException if a system exception occurred
	*/
	public long[] getOrganizationUserIds(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getOrganizationUserIds(organizationId);
	}

	/**
	* Returns the number of users with the status belonging to the
	* organization.
	*
	* @param organizationId the primary key of the organization
	* @param status the workflow status
	* @return the number of users with the status belonging to the organization
	* @throws PortalException if an organization with the primary key could not
	be found
	* @throws SystemException if a system exception occurred
	*/
	public int getOrganizationUsersCount(long organizationId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getOrganizationUsersCount(organizationId,
			status);
	}

	/**
	* Returns the primary keys of all the users belonging to the role.
	*
	* @param roleId the primary key of the role
	* @return the primary keys of the users belonging to the role
	* @throws SystemException if a system exception occurred
	*/
	public long[] getRoleUserIds(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getRoleUserIds(roleId);
	}

	/**
	* Returns the number of users with the status belonging to the role.
	*
	* @param roleId the primary key of the role
	* @param status the workflow status
	* @return the number of users with the status belonging to the role
	* @throws PortalException if an role with the primary key could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public int getRoleUsersCount(long roleId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getRoleUsersCount(roleId, status);
	}

	/**
	* Returns an ordered range of all the users with a social relation of the
	* type with the user.
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
	* @param userId the primary key of the user
	* @param type the type of social relation. The possible types can be found
	in {@link
	com.liferay.portlet.social.model.SocialRelationConstants}.
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param obc the comparator to order the users by (optionally
	<code>null</code>)
	* @return the ordered range of users with a social relation of the type
	with the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsers(userId, type, start, end, obc);
	}

	/**
	* Returns an ordered range of all the users with a social relation with the
	* user.
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
	* @param userId the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param obc the comparator to order the users by (optionally
	<code>null</code>)
	* @return the ordered range of users with a social relation with the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsers(userId, start, end, obc);
	}

	/**
	* Returns an ordered range of all the users with a mutual social relation
	* of the type with both of the given users.
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
	* @param userId1 the primary key of the first user
	* @param userId2 the primary key of the second user
	* @param type the type of social relation. The possible types can be found
	in {@link
	com.liferay.portlet.social.model.SocialRelationConstants}.
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param obc the comparator to order the users by (optionally
	<code>null</code>)
	* @return the ordered range of users with a mutual social relation of the
	type with the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId1, long userId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsers(userId1, userId2, type, start,
			end, obc);
	}

	/**
	* Returns an ordered range of all the users with a mutual social relation
	* with both of the given users.
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
	* @param userId1 the primary key of the first user
	* @param userId2 the primary key of the second user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param obc the comparator to order the users by (optionally
	<code>null</code>)
	* @return the ordered range of users with a mutual social relation with the
	user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId1, long userId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsers(userId1, userId2, start, end,
			obc);
	}

	/**
	* Returns the number of users with a social relation with the user.
	*
	* @param userId the primary key of the user
	* @return the number of users with a social relation with the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialUsersCount(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsersCount(userId);
	}

	/**
	* Returns the number of users with a social relation of the type with the
	* user.
	*
	* @param userId the primary key of the user
	* @param type the type of social relation. The possible types can be found
	in {@link
	com.liferay.portlet.social.model.SocialRelationConstants}.
	* @return the number of users with a social relation of the type with the
	user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialUsersCount(long userId, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsersCount(userId, type);
	}

	/**
	* Returns the number of users with a mutual social relation with both of
	* the given users.
	*
	* @param userId1 the primary key of the first user
	* @param userId2 the primary key of the second user
	* @return the number of users with a mutual social relation with the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialUsersCount(long userId1, long userId2)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsersCount(userId1, userId2);
	}

	/**
	* Returns the number of users with a mutual social relation of the type
	* with both of the given users.
	*
	* @param userId1 the primary key of the first user
	* @param userId2 the primary key of the second user
	* @param type the type of social relation. The possible types can be found
	in {@link
	com.liferay.portlet.social.model.SocialRelationConstants}.
	* @return the number of users with a mutual social relation of the type
	with the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialUsersCount(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsersCount(userId1, userId2, type);
	}

	/**
	* Returns the user with the contact ID.
	*
	* @param contactId the user's contact ID
	* @return the user with the contact ID
	* @throws PortalException if a user with the contact ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User getUserByContactId(long contactId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByContactId(contactId);
	}

	/**
	* Returns the user with the email address.
	*
	* @param companyId the primary key of the user's company
	* @param emailAddress the user's email address
	* @return the user with the email address
	* @throws PortalException if a user with the email address could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User getUserByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByEmailAddress(companyId, emailAddress);
	}

	/**
	* Returns the user with the Facebook ID.
	*
	* @param companyId the primary key of the user's company
	* @param facebookId the user's Facebook ID
	* @return the user with the Facebook ID
	* @throws PortalException if a user with the Facebook ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User getUserByFacebookId(long companyId,
		long facebookId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByFacebookId(companyId, facebookId);
	}

	/**
	* Returns the user with the primary key.
	*
	* @param userId the primary key of the user
	* @return the user with the primary key
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User getUserById(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserById(userId);
	}

	/**
	* Returns the user with the primary key from the company.
	*
	* @param companyId the primary key of the user's company
	* @param userId the primary key of the user
	* @return the user with the primary key
	* @throws PortalException if a user with the primary key from the company
	could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User getUserById(long companyId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserById(companyId, userId);
	}

	/**
	* Returns the user with the OpenID.
	*
	* @param companyId the primary key of the user's company
	* @param openId the user's OpenID
	* @return the user with the OpenID
	* @throws PortalException if a user with the OpenID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User getUserByOpenId(long companyId,
		java.lang.String openId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByOpenId(companyId, openId);
	}

	/**
	* Returns the user with the portrait ID.
	*
	* @param portraitId the user's portrait ID
	* @return the user with the portrait ID
	* @throws PortalException if a user with the portrait ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User getUserByPortraitId(long portraitId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByPortraitId(portraitId);
	}

	/**
	* Returns the user with the screen name.
	*
	* @param companyId the primary key of the user's company
	* @param screenName the user's screen name
	* @return the user with the screen name
	* @throws PortalException if a user with the screen name could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User getUserByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByScreenName(companyId, screenName);
	}

	/**
	* Returns the user with the UUID.
	*
	* @param uuid the user's UUID
	* @return the user with the UUID
	* @throws PortalException if a user with the UUID could not be found
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link
	#getUserByUuidAndCompanyId(String, long)}
	*/
	public com.liferay.portal.model.User getUserByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByUuid(uuid);
	}

	/**
	* Returns the user with the UUID.
	*
	* @param uuid the user's UUID
	* @param companyId the primary key of the user's company
	* @return the user with the UUID
	* @throws PortalException if a user with the UUID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User getUserByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns the number of users with the status belonging to the user group.
	*
	* @param userGroupId the primary key of the user group
	* @param status the workflow status
	* @return the number of users with the status belonging to the user group
	* @throws PortalException if a user group with the primary key could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public int getUserGroupUsersCount(long userGroupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserGroupUsersCount(userGroupId, status);
	}

	/**
	* Returns the primary key of the user with the email address.
	*
	* @param companyId the primary key of the user's company
	* @param emailAddress the user's email address
	* @return the primary key of the user with the email address
	* @throws PortalException if a user with the email address could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public long getUserIdByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserIdByEmailAddress(companyId, emailAddress);
	}

	/**
	* Returns the primary key of the user with the screen name.
	*
	* @param companyId the primary key of the user's company
	* @param screenName the user's screen name
	* @return the primary key of the user with the screen name
	* @throws PortalException if a user with the screen name could not be found
	* @throws SystemException if a system exception occurred
	*/
	public long getUserIdByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserIdByScreenName(companyId, screenName);
	}

	/**
	* Returns <code>true</code> if the password policy has been assigned to the
	* user.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param userId the primary key of the user
	* @return <code>true</code> if the password policy is assigned to the user;
	<code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasPasswordPolicyUser(long passwordPolicyId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasPasswordPolicyUser(passwordPolicyId, userId);
	}

	/**
	* Returns <code>true</code> if the user has the role with the name,
	* optionally through inheritance.
	*
	* @param companyId the primary key of the role's company
	* @param name the name of the role (must be a regular role, not an
	organization, site or provider role)
	* @param userId the primary key of the user
	* @param inherited whether to include roles inherited from organizations,
	sites, etc.
	* @return <code>true</code> if the user has the role; <code>false</code>
	otherwise
	* @throws PortalException if a role with the name could not be found
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasRoleUser(long companyId, java.lang.String name,
		long userId, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasRoleUser(companyId, name, userId, inherited);
	}

	/**
	* Returns <code>true</code> if the user's password is expired.
	*
	* @param user the user
	* @return <code>true</code> if the user's password is expired;
	<code>false</code> otherwise
	* @throws PortalException if the password policy for the user could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public boolean isPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.isPasswordExpired(user);
	}

	/**
	* Returns <code>true</code> if the password policy is configured to warn
	* the user that his password is expiring and the remaining time until
	* expiration is equal or less than the configured warning time.
	*
	* @param user the user
	* @return <code>true</code> if the user's password is expiring soon;
	<code>false</code> otherwise
	* @throws PortalException if the password policy for the user could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public boolean isPasswordExpiringSoon(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.isPasswordExpiringSoon(user);
	}

	/**
	* Returns the default user for the company.
	*
	* @param companyId the primary key of the company
	* @return the default user for the company
	* @throws PortalException if the user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User loadGetDefaultUser(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.loadGetDefaultUser(companyId);
	}

	/**
	* Returns an ordered range of all the users who match the keywords and
	* status, without using the indexer. It is preferable to use the indexed
	* version {@link #search(long, String, int, LinkedHashMap, int, int, Sort)}
	* instead of this method wherever possible for performance reasons.
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
	* @param companyId the primary key of the user's company
	* @param keywords the keywords (space separated), which may occur in the
	user's first name, middle name, last name, screen name, or email
	address
	* @param status the workflow status
	* @param params the finder parameters (optionally <code>null</code>). For
	more information see {@link
	com.liferay.portal.service.persistence.UserFinder}.
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param obc the comparator to order the users by (optionally
	<code>null</code>)
	* @return the matching users
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.UserFinder
	*/
	public java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String keywords, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.search(companyId, keywords, status, params,
			start, end, obc);
	}

	/**
	* Returns an ordered range of all the users who match the keywords and
	* status, using the indexer. It is preferable to use this method instead of
	* the non-indexed version whenever possible for performance reasons.
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
	* @param companyId the primary key of the user's company
	* @param keywords the keywords (space separated), which may occur in the
	user's first name, middle name, last name, screen name, or email
	address
	* @param status the workflow status
	* @param params the indexer parameters (optionally <code>null</code>). For
	more information see {@link
	com.liferay.portlet.usersadmin.util.UserIndexer}.
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param sort the field and direction to sort by (optionally
	<code>null</code>)
	* @return the matching users
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portlet.usersadmin.util.UserIndexer
	*/
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String keywords, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.search(companyId, keywords, status, params,
			start, end, sort);
	}

	/**
	* Returns an ordered range of all the users with the status, and whose
	* first name, middle name, last name, screen name, and email address match
	* the keywords specified for them, without using the indexer. It is
	* preferable to use the indexed version {@link #search(long, String,
	* String, String, String, String, int, LinkedHashMap, boolean, int, int,
	* Sort)} instead of this method wherever possible for performance reasons.
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
	* @param companyId the primary key of the user's company
	* @param firstName the first name keywords (space separated)
	* @param middleName the middle name keywords
	* @param lastName the last name keywords
	* @param screenName the screen name keywords
	* @param emailAddress the email address keywords
	* @param status the workflow status
	* @param params the finder parameters (optionally <code>null</code>). For
	more information see {@link
	com.liferay.portal.service.persistence.UserFinder}.
	* @param andSearch whether every field must match its keywords, or just
	one field. For example, &quot;users with the first name 'bob' and
	last name 'smith'&quot; vs &quot;users with the first name 'bob'
	or the last name 'smith'&quot;.
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param obc the comparator to order the users by (optionally
	<code>null</code>)
	* @return the matching users
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.UserFinder
	*/
	public java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.search(companyId, firstName, middleName,
			lastName, screenName, emailAddress, status, params, andSearch,
			start, end, obc);
	}

	/**
	* Returns an ordered range of all the users with the status, and whose
	* first name, middle name, last name, screen name, and email address match
	* the keywords specified for them, using the indexer. It is preferable to
	* use this method instead of the non-indexed version whenever possible for
	* performance reasons.
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
	* @param companyId the primary key of the user's company
	* @param firstName the first name keywords (space separated)
	* @param middleName the middle name keywords
	* @param lastName the last name keywords
	* @param screenName the screen name keywords
	* @param emailAddress the email address keywords
	* @param status the workflow status
	* @param params the indexer parameters (optionally <code>null</code>). For
	more information see {@link
	com.liferay.portlet.usersadmin.util.UserIndexer}.
	* @param andSearch whether every field must match its keywords, or just
	one field. For example, &quot;users with the first name 'bob' and
	last name 'smith'&quot; vs &quot;users with the first name 'bob'
	or the last name 'smith'&quot;.
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param sort the field and direction to sort by (optionally
	<code>null</code>)
	* @return the matching users
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portlet.usersadmin.util.UserIndexer
	*/
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String screenName,
		java.lang.String emailAddress, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.search(companyId, firstName, middleName,
			lastName, screenName, emailAddress, status, params, andSearch,
			start, end, sort);
	}

	/**
	* Returns the number of users who match the keywords and status.
	*
	* @param companyId the primary key of the user's company
	* @param keywords the keywords (space separated), which may occur in the
	user's first name, middle name, last name, screen name, or email
	address
	* @param status the workflow status
	* @param params the finder parameters (optionally <code>null</code>). For
	more information see {@link
	com.liferay.portal.service.persistence.UserFinder}.
	* @return the number matching users
	* @throws SystemException if a system exception occurred
	*/
	public int searchCount(long companyId, java.lang.String keywords,
		int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.searchCount(companyId, keywords, status, params);
	}

	/**
	* Returns the number of users with the status, and whose first name, middle
	* name, last name, screen name, and email address match the keywords
	* specified for them.
	*
	* @param companyId the primary key of the user's company
	* @param firstName the first name keywords (space separated)
	* @param middleName the middle name keywords
	* @param lastName the last name keywords
	* @param screenName the screen name keywords
	* @param emailAddress the email address keywords
	* @param status the workflow status
	* @param params the finder parameters (optionally <code>null</code>). For
	more information see {@link
	com.liferay.portal.service.persistence.UserFinder}.
	* @param andSearch whether every field must match its keywords, or just
	one field. For example, &quot;users with the first name 'bob' and
	last name 'smith'&quot; vs &quot;users with the first name 'bob'
	or the last name 'smith'&quot;.
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int searchCount(long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.searchCount(companyId, firstName, middleName,
			lastName, screenName, emailAddress, status, params, andSearch);
	}

	/**
	* Sends an email address verification to the user.
	*
	* @param user the verification email recipient
	* @param emailAddress the recipient's email address
	* @param serviceContext the service context to be applied. Must set the
	portal URL, main path, primary key of the layout, remote address,
	remote host, and agent for the user.
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public void sendEmailAddressVerification(
		com.liferay.portal.model.User user, java.lang.String emailAddress,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.sendEmailAddressVerification(user, emailAddress,
			serviceContext);
	}

	/**
	* Sends the password email to the user with the email address. The content
	* of this email can be specified in <code>portal.properties</code> with the
	* <code>admin.email.password</code> keys.
	*
	* @param companyId the primary key of the user's company
	* @param emailAddress the user's email address
	* @param fromName the name of the individual that the email should be from
	* @param fromAddress the address of the individual that the email should
	be from
	* @param subject the email subject. If <code>null</code>, the subject
	specified in <code>portal.properties</code> will be used.
	* @param body the email body. If <code>null</code>, the body specified in
	<code>portal.properties</code> will be used.
	* @param serviceContext the service context to be applied
	* @throws PortalException if a user with the email address could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public void sendPassword(long companyId, java.lang.String emailAddress,
		java.lang.String fromName, java.lang.String fromAddress,
		java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.sendPassword(companyId, emailAddress, fromName,
			fromAddress, subject, body, serviceContext);
	}

	/**
	* Removes the users from the teams of a group.
	*
	* @param groupId the primary key of the group
	* @param userIds the primary keys of the users
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public void unsetGroupTeamsUsers(long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetGroupTeamsUsers(groupId, userIds);
	}

	/**
	* Removes the users from the group.
	*
	* @param groupId the primary key of the group
	* @param userIds the primary keys of the users
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>)
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public void unsetGroupUsers(long groupId, long[] userIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetGroupUsers(groupId, userIds, serviceContext);
	}

	/**
	* Removes the users from the organization.
	*
	* @param organizationId the primary key of the organization
	* @param userIds the primary keys of the users
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetOrganizationUsers(organizationId, userIds);
	}

	/**
	* Removes the users from the password policy.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param userIds the primary keys of the users
	* @throws SystemException if a system exception occurred
	*/
	public void unsetPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	/**
	* Removes the users from the role.
	*
	* @param roleId the primary key of the role
	* @param users the users
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public void unsetRoleUsers(long roleId,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetRoleUsers(roleId, users);
	}

	/**
	* Removes the users from the role.
	*
	* @param roleId the primary key of the role
	* @param userIds the primary keys of the users
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public void unsetRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetRoleUsers(roleId, userIds);
	}

	/**
	* Removes the users from the team.
	*
	* @param teamId the primary key of the team
	* @param userIds the primary keys of the users
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public void unsetTeamUsers(long teamId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetTeamUsers(teamId, userIds);
	}

	/**
	* Removes the users from the user group.
	*
	* @param userGroupId the primary key of the user group
	* @param userIds the primary keys of the users
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetUserGroupUsers(userGroupId, userIds);
	}

	/**
	* Updates whether the user has agreed to the terms of use.
	*
	* @param userId the primary key of the user
	* @param agreedToTermsOfUse whether the user has agreet to the terms of
	use
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateAgreedToTermsOfUse(long userId,
		boolean agreedToTermsOfUse)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateAgreedToTermsOfUse(userId,
			agreedToTermsOfUse);
	}

	/**
	* Updates the user's asset with the new asset categories and tag names,
	* removing and adding asset categories and tag names as necessary.
	*
	* @param userId the primary key of the user
	* @param user ID the primary key of the user
	* @param assetCategoryIds the primary key's of the new asset categories
	* @param assetTagNames the new asset tag names
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void updateAsset(long userId, com.liferay.portal.model.User user,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.updateAsset(userId, user, assetCategoryIds,
			assetTagNames);
	}

	/**
	* Updates the user's creation date.
	*
	* @param userId the primary key of the user
	* @param createDate the new creation date
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateCreateDate(long userId,
		java.util.Date createDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateCreateDate(userId, createDate);
	}

	/**
	* Updates the user's email address.
	*
	* @param userId the primary key of the user
	* @param password the user's password
	* @param emailAddress1 the user's new email address
	* @param emailAddress2 the user's new email address confirmation
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateEmailAddress(long userId,
		java.lang.String password, java.lang.String emailAddress1,
		java.lang.String emailAddress2)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateEmailAddress(userId, password,
			emailAddress1, emailAddress2);
	}

	/**
	* Updates the user's email address or sends verification email.
	*
	* @param userId the primary key of the user
	* @param password the user's password
	* @param emailAddress1 the user's new email address
	* @param emailAddress2 the user's new email address confirmation
	* @param serviceContext the service context to be applied. Must set the
	portal URL, main path, primary key of the layout, remote address,
	remote host, and agent for the user.
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateEmailAddress(long userId,
		java.lang.String password, java.lang.String emailAddress1,
		java.lang.String emailAddress2,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateEmailAddress(userId, password,
			emailAddress1, emailAddress2, serviceContext);
	}

	/**
	* Updates whether the user has verified email address.
	*
	* @param userId the primary key of the user
	* @param emailAddressVerified whether the user has verified email address
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateEmailAddressVerified(
		long userId, boolean emailAddressVerified)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateEmailAddressVerified(userId,
			emailAddressVerified);
	}

	/**
	* Updates the user's Facebook ID.
	*
	* @param userId the primary key of the user
	* @param facebookId the user's new Facebook ID
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateFacebookId(long userId,
		long facebookId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateFacebookId(userId, facebookId);
	}

	/**
	* Sets the groups the user is in, removing and adding groups as necessary.
	*
	* @param userId the primary key of the user
	* @param newGroupIds the primary keys of the groups
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>)
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public void updateGroups(long userId, long[] newGroupIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.updateGroups(userId, newGroupIds, serviceContext);
	}

	/**
	* Updates a user account that was automatically created when a guest user
	* participated in an action (e.g. posting a comment) and only provided his
	* name and email address.
	*
	* @param creatorUserId the primary key of the creator
	* @param companyId the primary key of the user's company
	* @param autoPassword whether a password should be automatically generated
	for the user
	* @param password1 the user's password
	* @param password2 the user's password confirmation
	* @param autoScreenName whether a screen name should be automatically
	generated for the user
	* @param screenName the user's screen name
	* @param emailAddress the user's email address
	* @param facebookId the user's facebook ID
	* @param openId the user's OpenID
	* @param locale the user's locale
	* @param firstName the user's first name
	* @param middleName the user's middle name
	* @param lastName the user's last name
	* @param prefixId the user's name prefix ID
	* @param suffixId the user's name suffix ID
	* @param male whether the user is male
	* @param birthdayMonth the user's birthday month (0-based, meaning 0 for
	January)
	* @param birthdayDay the user's birthday day
	* @param birthdayYear the user's birthday year
	* @param jobTitle the user's job title
	* @param updateUserInformation whether to update the user's information
	* @param sendEmail whether to send the user an email notification about
	their new account
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set expando bridge attributes for the
	user.
	* @return the user
	* @throws PortalException if the user's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateIncompleteUser(
		long creatorUserId, long companyId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean autoScreenName, java.lang.String screenName,
		java.lang.String emailAddress, long facebookId,
		java.lang.String openId, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, boolean updateUserInformation,
		boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateIncompleteUser(creatorUserId, companyId,
			autoPassword, password1, password2, autoScreenName, screenName,
			emailAddress, facebookId, openId, locale, firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, jobTitle, updateUserInformation, sendEmail,
			serviceContext);
	}

	/**
	* Updates the user's job title.
	*
	* @param userId the primary key of the user
	* @param jobTitle the user's job title
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	or if a contact could not be found matching the user's contact ID
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateJobTitle(long userId,
		java.lang.String jobTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateJobTitle(userId, jobTitle);
	}

	/**
	* Updates the user's last login with the current time and the IP address.
	*
	* @param userId the primary key of the user
	* @param loginIP the IP address the user logged in from
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateLastLogin(long userId,
		java.lang.String loginIP)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateLastLogin(userId, loginIP);
	}

	/**
	* Updates whether the user is locked out from logging in.
	*
	* @param user the user
	* @param lockout whether the user is locked out
	* @return the user
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateLockout(
		com.liferay.portal.model.User user, boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateLockout(user, lockout);
	}

	/**
	* Updates whether the user is locked out from logging in.
	*
	* @param companyId the primary key of the user's company
	* @param emailAddress the user's email address
	* @param lockout whether the user is locked out
	* @return the user
	* @throws PortalException if a user with the email address could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateLockoutByEmailAddress(
		long companyId, java.lang.String emailAddress, boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateLockoutByEmailAddress(companyId,
			emailAddress, lockout);
	}

	/**
	* Updates whether the user is locked out from logging in.
	*
	* @param userId the primary key of the user
	* @param lockout whether the user is locked out
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateLockoutById(long userId,
		boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateLockoutById(userId, lockout);
	}

	/**
	* Updates whether the user is locked out from logging in.
	*
	* @param companyId the primary key of the user's company
	* @param screenName the user's screen name
	* @param lockout whether the user is locked out
	* @return the user
	* @throws PortalException if a user with the screen name could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateLockoutByScreenName(
		long companyId, java.lang.String screenName, boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateLockoutByScreenName(companyId,
			screenName, lockout);
	}

	/**
	* Updates the user's modified date.
	*
	* @param userId the primary key of the user
	* @param modifiedDate the new modified date
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateModifiedDate(long userId,
		java.util.Date modifiedDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateModifiedDate(userId, modifiedDate);
	}

	/**
	* Updates the user's OpenID.
	*
	* @param userId the primary key of the user
	* @param openId the new OpenID
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateOpenId(long userId,
		java.lang.String openId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateOpenId(userId, openId);
	}

	/**
	* Sets the organizations that the user is in, removing and adding
	* organizations as necessary.
	*
	* @param userId the primary key of the user
	* @param newOrganizationIds the primary keys of the organizations
	* @param serviceContext the service context to be applied. Must set
	whether user indexing is enabled.
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void updateOrganizations(long userId, long[] newOrganizationIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.updateOrganizations(userId, newOrganizationIds,
			serviceContext);
	}

	/**
	* Updates the user's password without tracking or validation of the change.
	*
	* @param userId the primary key of the user
	* @param password1 the user's new password
	* @param password2 the user's new password confirmation
	* @param passwordReset whether the user should be asked to reset their
	password the next time they log in
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updatePassword(userId, password1, password2,
			passwordReset);
	}

	/**
	* Updates the user's password, optionally with tracking and validation of
	* the change.
	*
	* @param userId the primary key of the user
	* @param password1 the user's new password
	* @param password2 the user's new password confirmation
	* @param passwordReset whether the user should be asked to reset their
	password the next time they login
	* @param silentUpdate whether the password should be updated without being
	tracked, or validated. Primarily used for password imports.
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, boolean silentUpdate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updatePassword(userId, password1, password2,
			passwordReset, silentUpdate);
	}

	/**
	* Updates the user's password with manually input information. This method
	* should only be used when performing maintenance.
	*
	* @param userId the primary key of the user
	* @param password the user's new password
	* @param passwordEncrypted the user's new encrypted password
	* @param passwordReset whether the user should be asked to reset their
	password the next time they login
	* @param passwordModifiedDate the new password modified date
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updatePasswordManually(long userId,
		java.lang.String password, boolean passwordEncrypted,
		boolean passwordReset, java.util.Date passwordModifiedDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updatePasswordManually(userId, password,
			passwordEncrypted, passwordReset, passwordModifiedDate);
	}

	/**
	* Updates whether the user should be asked to reset their password the next
	* time they login.
	*
	* @param userId the primary key of the user
	* @param passwordReset whether the user should be asked to reset their
	password the next time they login
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updatePasswordReset(long userId,
		boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updatePasswordReset(userId, passwordReset);
	}

	/**
	* Updates the user's portrait image.
	*
	* @param userId the primary key of the user
	* @param bytes the new portrait image data
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	or if the new portrait was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updatePortrait(long userId,
		byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updatePortrait(userId, bytes);
	}

	/**
	* Updates the user's password reset question and answer.
	*
	* @param userId the primary key of the user
	* @param question the user's new password reset question
	* @param answer the user's new password reset answer
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	or if the new question or answer were invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateReminderQuery(long userId,
		java.lang.String question, java.lang.String answer)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateReminderQuery(userId, question, answer);
	}

	/**
	* Updates the user's screen name.
	*
	* @param userId the primary key of the user
	* @param screenName the user's new screen name
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	or if the new screen name was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateScreenName(long userId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateScreenName(userId, screenName);
	}

	/**
	* Updates the user's workflow status.
	*
	* @param userId the primary key of the user
	* @param status the user's new workflow status
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateStatus(long userId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateStatus(userId, status);
	}

	/**
	* Updates the user.
	*
	* @param userId the primary key of the user
	* @param oldPassword the user's old password
	* @param newPassword1 the user's new password (optionally
	<code>null</code>)
	* @param newPassword2 the user's new password confirmation (optionally
	<code>null</code>)
	* @param passwordReset whether the user should be asked to reset their
	password the next time they login
	* @param reminderQueryQuestion the user's new password reset question
	* @param reminderQueryAnswer the user's new password reset answer
	* @param screenName the user's new screen name
	* @param emailAddress the user's new email address
	* @param facebookId the user's new Facebook ID
	* @param openId the user's new OpenID
	* @param languageId the user's new language ID
	* @param timeZoneId the user's new time zone ID
	* @param greeting the user's new greeting
	* @param comments the user's new comments
	* @param firstName the user's new first name
	* @param middleName the user's new middle name
	* @param lastName the user's new last name
	* @param prefixId the user's new name prefix ID
	* @param suffixId the user's new name suffix ID
	* @param male whether user is male
	* @param birthdayMonth the user's new birthday month (0-based, meaning 0
	for January)
	* @param birthdayDay the user's new birthday day
	* @param birthdayYear the user's birthday year
	* @param smsSn the user's new SMS screen name
	* @param aimSn the user's new AIM screen name
	* @param facebookSn the user's new Facebook screen name
	* @param icqSn the user's new ICQ screen name
	* @param jabberSn the user's new Jabber screen name
	* @param msnSn the user's new MSN screen name
	* @param mySpaceSn the user's new MySpace screen name
	* @param skypeSn the user's new Skype screen name
	* @param twitterSn the user's new Twitter screen name
	* @param ymSn the user's new Yahoo! Messenger screen name
	* @param jobTitle the user's new job title
	* @param groupIds the primary keys of the user's groups
	* @param organizationIds the primary keys of the user's organizations
	* @param roleIds the primary keys of the user's roles
	* @param userGroupRoles the user user's group roles
	* @param userGroupIds the primary keys of the user's user groups
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the UUID (with the <code>uuid</code>
	attribute), asset category IDs, asset tag names, and expando
	bridge attributes for the user.
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	or if the new information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, long facebookId,
		java.lang.String openId, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String comments, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds,
		java.util.List<com.liferay.portal.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateUser(userId, oldPassword, newPassword1,
			newPassword2, passwordReset, reminderQueryQuestion,
			reminderQueryAnswer, screenName, emailAddress, facebookId, openId,
			languageId, timeZoneId, greeting, comments, firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, smsSn, aimSn, facebookSn, icqSn, jabberSn, msnSn,
			mySpaceSn, skypeSn, twitterSn, ymSn, jobTitle, groupIds,
			organizationIds, roleIds, userGroupRoles, userGroupIds,
			serviceContext);
	}

	/**
	* Verifies the email address of the ticket.
	*
	* @param ticketKey the ticket key
	* @throws PortalException if a ticket matching the ticket key could not be
	found, if the ticket has expired, if the ticket is an email
	address ticket, or if the email address is invalid
	* @throws SystemException if a system exception occurred
	*/
	public void verifyEmailAddress(java.lang.String ticketKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.verifyEmailAddress(ticketKey);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public UserLocalService getWrappedUserLocalService() {
		return _userLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	public UserLocalService getWrappedService() {
		return _userLocalService;
	}

	public void setWrappedService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private UserLocalService _userLocalService;
}
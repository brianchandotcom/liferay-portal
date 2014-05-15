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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link UserGroupLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupLocalService
 * @generated
 */
@ProviderType
public class UserGroupLocalServiceWrapper implements UserGroupLocalService,
	ServiceWrapper<UserGroupLocalService> {
	public UserGroupLocalServiceWrapper(
		UserGroupLocalService userGroupLocalService) {
		_userGroupLocalService = userGroupLocalService;
	}

	/**
	* Adds the user group to the database. Also notifies the appropriate model listeners.
	*
	* @param userGroup the user group
	* @return the user group that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.UserGroup addUserGroup(
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.addUserGroup(userGroup);
	}

	/**
	* Creates a new user group with the primary key. Does not add the user group to the database.
	*
	* @param userGroupId the primary key for the new user group
	* @return the new user group
	*/
	@Override
	public com.liferay.portal.model.UserGroup createUserGroup(long userGroupId) {
		return _userGroupLocalService.createUserGroup(userGroupId);
	}

	/**
	* Deletes the user group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupId the primary key of the user group
	* @return the user group that was removed
	* @throws PortalException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.UserGroup deleteUserGroup(long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.deleteUserGroup(userGroupId);
	}

	/**
	* Deletes the user group from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroup the user group
	* @return the user group that was removed
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.UserGroup deleteUserGroup(
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.deleteUserGroup(userGroup);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _userGroupLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.model.UserGroup fetchUserGroup(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.fetchUserGroup(userGroupId);
	}

	/**
	* Returns the user group with the matching UUID and company.
	*
	* @param uuid the user group's UUID
	* @param companyId the primary key of the company
	* @return the matching user group, or <code>null</code> if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.UserGroup fetchUserGroupByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.fetchUserGroupByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns the user group with the primary key.
	*
	* @param userGroupId the primary key of the user group
	* @return the user group
	* @throws PortalException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.UserGroup getUserGroup(long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getUserGroup(userGroupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the user group with the matching UUID and company.
	*
	* @param uuid the user group's UUID
	* @param companyId the primary key of the company
	* @return the matching user group
	* @throws PortalException if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.UserGroup getUserGroupByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getUserGroupByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of all the user groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of user groups
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getUserGroups(start, end);
	}

	/**
	* Returns the number of user groups.
	*
	* @return the number of user groups
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getUserGroupsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getUserGroupsCount();
	}

	/**
	* Updates the user group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param userGroup the user group
	* @return the user group that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.UserGroup updateUserGroup(
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.updateUserGroup(userGroup);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addGroupUserGroup(long groupId, long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.addGroupUserGroup(groupId, userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addGroupUserGroup(long groupId,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.addGroupUserGroup(groupId, userGroup);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.addGroupUserGroups(groupId, userGroupIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addGroupUserGroups(long groupId,
		java.util.List<com.liferay.portal.model.UserGroup> UserGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.addGroupUserGroups(groupId, UserGroups);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void clearGroupUserGroups(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.clearGroupUserGroups(groupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteGroupUserGroup(long groupId, long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteGroupUserGroup(groupId, userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteGroupUserGroup(long groupId,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteGroupUserGroup(groupId, userGroup);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteGroupUserGroups(long groupId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteGroupUserGroups(groupId, userGroupIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteGroupUserGroups(long groupId,
		java.util.List<com.liferay.portal.model.UserGroup> UserGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteGroupUserGroups(groupId, UserGroups);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getGroupUserGroups(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getGroupUserGroups(groupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getGroupUserGroups(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getGroupUserGroups(groupId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getGroupUserGroups(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getGroupUserGroups(groupId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getGroupUserGroupsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getGroupUserGroupsCount(groupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasGroupUserGroup(long groupId, long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.hasGroupUserGroup(groupId, userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasGroupUserGroups(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.hasGroupUserGroups(groupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void setGroupUserGroups(long groupId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.setGroupUserGroups(groupId, userGroupIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addTeamUserGroup(long teamId, long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.addTeamUserGroup(teamId, userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addTeamUserGroup(long teamId,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.addTeamUserGroup(teamId, userGroup);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addTeamUserGroups(long teamId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.addTeamUserGroups(teamId, userGroupIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addTeamUserGroups(long teamId,
		java.util.List<com.liferay.portal.model.UserGroup> UserGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.addTeamUserGroups(teamId, UserGroups);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void clearTeamUserGroups(long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.clearTeamUserGroups(teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteTeamUserGroup(long teamId, long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteTeamUserGroup(teamId, userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteTeamUserGroup(long teamId,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteTeamUserGroup(teamId, userGroup);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteTeamUserGroups(long teamId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteTeamUserGroups(teamId, userGroupIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteTeamUserGroups(long teamId,
		java.util.List<com.liferay.portal.model.UserGroup> UserGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteTeamUserGroups(teamId, UserGroups);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getTeamUserGroups(
		long teamId) throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getTeamUserGroups(teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getTeamUserGroups(
		long teamId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getTeamUserGroups(teamId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getTeamUserGroups(
		long teamId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getTeamUserGroups(teamId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getTeamUserGroupsCount(long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getTeamUserGroupsCount(teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasTeamUserGroup(long teamId, long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.hasTeamUserGroup(teamId, userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasTeamUserGroups(long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.hasTeamUserGroups(teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void setTeamUserGroups(long teamId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.setTeamUserGroups(teamId, userGroupIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addUserUserGroup(long userId, long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.addUserUserGroup(userId, userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addUserUserGroup(long userId,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.addUserUserGroup(userId, userGroup);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addUserUserGroups(long userId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.addUserUserGroups(userId, userGroupIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addUserUserGroups(long userId,
		java.util.List<com.liferay.portal.model.UserGroup> UserGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.addUserUserGroups(userId, UserGroups);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void clearUserUserGroups(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.clearUserUserGroups(userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteUserUserGroup(long userId, long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteUserUserGroup(userId, userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteUserUserGroup(long userId,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteUserUserGroup(userId, userGroup);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteUserUserGroups(long userId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteUserUserGroups(userId, userGroupIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteUserUserGroups(long userId,
		java.util.List<com.liferay.portal.model.UserGroup> UserGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteUserUserGroups(userId, UserGroups);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getUserUserGroups(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getUserUserGroups(userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getUserUserGroups(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getUserUserGroups(userId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getUserUserGroups(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getUserUserGroups(userId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getUserUserGroupsCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getUserUserGroupsCount(userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasUserUserGroup(long userId, long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.hasUserUserGroup(userId, userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasUserUserGroups(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.hasUserUserGroups(userId);
	}

	/**
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void setUserUserGroups(long userId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.setUserUserGroups(userId, userGroupIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _userGroupLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_userGroupLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds a user group.
	*
	* <p>
	* This method handles the creation and bookkeeping of the user group,
	* including its resources, metadata, and internal data structures. It is
	* not necessary to make subsequent calls to setup default groups and
	* resources for the user group.
	* </p>
	*
	* @param userId the primary key of the user
	* @param companyId the primary key of the user group's company
	* @param name the user group's name
	* @param description the user group's description
	* @return the user group
	* @throws PortalException if the user group's information was invalid
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #addUserGroup(long, long,
	String, String, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.UserGroup addUserGroup(long userId,
		long companyId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.addUserGroup(userId, companyId, name,
			description);
	}

	/**
	* Adds a user group.
	*
	* <p>
	* This method handles the creation and bookkeeping of the user group,
	* including its resources, metadata, and internal data structures. It is
	* not necessary to make subsequent calls to setup default groups and
	* resources for the user group.
	* </p>
	*
	* @param userId the primary key of the user
	* @param companyId the primary key of the user group's company
	* @param name the user group's name
	* @param description the user group's description
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set expando bridge attributes for the
	user group.
	* @return the user group
	* @throws PortalException if the user group's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.UserGroup addUserGroup(long userId,
		long companyId, java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.addUserGroup(userId, companyId, name,
			description, serviceContext);
	}

	/**
	* Copies the user group's layout to the user.
	*
	* @param userGroupId the primary key of the user group
	* @param userId the primary key of the user
	* @throws PortalException if a user with the primary key could not be
	found or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0
	*/
	@Deprecated
	@Override
	public void copyUserGroupLayouts(long userGroupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.copyUserGroupLayouts(userGroupId, userId);
	}

	/**
	* Copies the user group's layouts to the users who are not already members
	* of the user group.
	*
	* @param userGroupId the primary key of the user group
	* @param userIds the primary keys of the users
	* @throws PortalException if any one of the users could not be found or
	if a portal exception occurred
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.1.0
	*/
	@Deprecated
	@Override
	public void copyUserGroupLayouts(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.copyUserGroupLayouts(userGroupId, userIds);
	}

	/**
	* Copies the user groups' layouts to the user.
	*
	* @param userGroupIds the primary keys of the user groups
	* @param userId the primary key of the user
	* @throws PortalException if a user with the primary key could not be
	found or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.1.0
	*/
	@Deprecated
	@Override
	public void copyUserGroupLayouts(long[] userGroupIds, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.copyUserGroupLayouts(userGroupIds, userId);
	}

	@Override
	public void deleteUserGroups(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.deleteUserGroups(companyId);
	}

	@Override
	public com.liferay.portal.model.UserGroup fetchUserGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.fetchUserGroup(companyId, name);
	}

	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getGroupUserUserGroups(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getGroupUserUserGroups(groupId, userId);
	}

	/**
	* Returns the user group with the name.
	*
	* @param companyId the primary key of the user group's company
	* @param name the user group's name
	* @return Returns the user group with the name
	* @throws PortalException if a user group with the name could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.UserGroup getUserGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getUserGroup(companyId, name);
	}

	/**
	* Returns all the user groups belonging to the company.
	*
	* @param companyId the primary key of the user groups' company
	* @return the user groups belonging to the company
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getUserGroups(companyId);
	}

	/**
	* Returns all the user groups with the primary keys.
	*
	* @param userGroupIds the primary keys of the user groups
	* @return the user groups with the primary keys
	* @throws PortalException if any one of the user groups could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.getUserGroups(userGroupIds);
	}

	/**
	* Returns an ordered range of all the user groups that match the keywords.
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
	* @param companyId the primary key of the user group's company
	* @param keywords the keywords (space separated), which may occur in the
	user group's name or description (optionally <code>null</code>)
	* @param params the finder params (optionally <code>null</code>). For more
	information see {@link
	com.liferay.portal.service.persistence.UserGroupFinder}
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not
	inclusive)
	* @param obc the comparator to order the user groups (optionally
	<code>null</code>)
	* @return the matching user groups ordered by comparator <code>obc</code>
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.UserGroupFinder
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> search(
		long companyId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.search(companyId, keywords, params,
			start, end, obc);
	}

	/**
	* Returns an ordered range of all the user groups that match the keywords,
	* using the indexer. It is preferable to use this method instead of the
	* non-indexed version whenever possible for performance reasons.
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
	* @param companyId the primary key of the user group's company
	* @param keywords the keywords (space separated), which may occur in the
	user group's name or description (optionally <code>null</code>)
	* @param params the finder params (optionally <code>null</code>). For more
	information see {@link
	com.liferay.portlet.usergroupsadmin.util.UserGroupIndexer}
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not
	inclusive)
	* @param sort the field and direction by which to sort (optionally
	<code>null</code>)
	* @return the matching user groups ordered by sort
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portlet.usergroupsadmin.util.UserGroupIndexer
	*/
	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.search(companyId, keywords, params,
			start, end, sort);
	}

	/**
	* Returns an ordered range of all the user groups that match the name and
	* description.
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
	* @param companyId the primary key of the user group's company
	* @param name the user group's name (optionally <code>null</code>)
	* @param description the user group's description (optionally
	<code>null</code>)
	* @param params the finder params (optionally <code>null</code>). For more
	information see {@link
	com.liferay.portal.service.persistence.UserGroupFinder}
	* @param andOperator whether every field must match its keywords or just
	one field
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not
	inclusive)
	* @param obc the comparator to order the user groups (optionally
	<code>null</code>)
	* @return the matching user groups ordered by comparator <code>obc</code>
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.UserGroupFinder
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.search(companyId, name, description,
			params, andOperator, start, end, obc);
	}

	/**
	* Returns an ordered range of all the user groups that match the name and
	* description. It is preferable to use this method instead of the
	* non-indexed version whenever possible for performance reasons.
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
	* @param companyId the primary key of the user group's company
	* @param name the user group's name (optionally <code>null</code>)
	* @param description the user group's description (optionally
	<code>null</code>)
	* @param params the finder params (optionally <code>null</code>). For more
	information see {@link
	com.liferay.portlet.usergroupsadmin.util.UserGroupIndexer}
	* @param andSearch whether every field must match its keywords or just one
	field
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not
	inclusive)
	* @param sort the field and direction by which to sort (optionally
	<code>null</code>)
	* @return the matching user groups ordered by sort
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.UserGroupFinder
	*/
	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.search(companyId, name, description,
			params, andSearch, start, end, sort);
	}

	/**
	* Returns the number of user groups that match the keywords
	*
	* @param companyId the primary key of the user group's company
	* @param keywords the keywords (space separated), which may occur in the
	user group's name or description (optionally <code>null</code>)
	* @param params the finder params (optionally <code>null</code>). For more
	information see {@link
	com.liferay.portal.service.persistence.UserGroupFinder}
	* @return the number of matching user groups
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.UserGroupFinder
	*/
	@Override
	public int searchCount(long companyId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.searchCount(companyId, keywords, params);
	}

	/**
	* Returns the number of user groups that match the name and description.
	*
	* @param companyId the primary key of the user group's company
	* @param name the user group's name (optionally <code>null</code>)
	* @param description the user group's description (optionally
	<code>null</code>)
	* @param params the finder params (optionally <code>null</code>). For more
	information see {@link
	com.liferay.portal.service.persistence.UserGroupFinder}
	* @param andOperator whether every field must match its keywords or just
	one field
	* @return the number of matching user groups
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.service.persistence.UserGroupFinder
	*/
	@Override
	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.searchCount(companyId, name, description,
			params, andOperator);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.model.UserGroup> searchUserGroups(
		long companyId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.searchUserGroups(companyId, keywords,
			params, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.model.UserGroup> searchUserGroups(
		long companyId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.searchUserGroups(companyId, name,
			description, params, andSearch, start, end, sort);
	}

	/**
	* Removes the user groups from the group.
	*
	* @param groupId the primary key of the group
	* @param userGroupIds the primary keys of the user groups
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.unsetGroupUserGroups(groupId, userGroupIds);
	}

	/**
	* Removes the user groups from the team.
	*
	* @param teamId the primary key of the team
	* @param userGroupIds the primary keys of the user groups
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void unsetTeamUserGroups(long teamId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupLocalService.unsetTeamUserGroups(teamId, userGroupIds);
	}

	/**
	* Updates the user group.
	*
	* @param companyId the primary key of the user group's company
	* @param userGroupId the primary key of the user group
	* @param name the user group's name
	* @param description the user group's description
	* @return the user group
	* @throws PortalException if a user group with the primary key could
	not be found or if the new information was invalid
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #updateUserGroup(long, long,
	String, String, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.UserGroup updateUserGroup(long companyId,
		long userGroupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.updateUserGroup(companyId, userGroupId,
			name, description);
	}

	/**
	* Updates the user group.
	*
	* @param companyId the primary key of the user group's company
	* @param userGroupId the primary key of the user group
	* @param name the user group's name
	* @param description the user group's description
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set expando bridge attributes for the
	user group.
	* @return the user group
	* @throws PortalException if a user group with the primary key could not be
	found or if the new information was invalid
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.UserGroup updateUserGroup(long companyId,
		long userGroupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupLocalService.updateUserGroup(companyId, userGroupId,
			name, description, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public UserGroupLocalService getWrappedUserGroupLocalService() {
		return _userGroupLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedUserGroupLocalService(
		UserGroupLocalService userGroupLocalService) {
		_userGroupLocalService = userGroupLocalService;
	}

	@Override
	public UserGroupLocalService getWrappedService() {
		return _userGroupLocalService;
	}

	@Override
	public void setWrappedService(UserGroupLocalService userGroupLocalService) {
		_userGroupLocalService = userGroupLocalService;
	}

	private UserGroupLocalService _userGroupLocalService;
}
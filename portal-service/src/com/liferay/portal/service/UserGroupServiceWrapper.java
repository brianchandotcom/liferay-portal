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
 * Provides a wrapper for {@link UserGroupService}.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupService
 * @generated
 */
@ProviderType
public class UserGroupServiceWrapper implements UserGroupService,
	ServiceWrapper<UserGroupService> {
	public UserGroupServiceWrapper(UserGroupService userGroupService) {
		_userGroupService = userGroupService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _userGroupService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_userGroupService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds the user groups to the group.
	*
	* @param groupId the primary key of the group
	* @param userGroupIds the primary keys of the user groups
	* @throws PortalException if a group or user group with the primary key
	could not be found, or if the user did not have permission to
	assign group members
	*/
	@Override
	public void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userGroupService.addGroupUserGroups(groupId, userGroupIds);
	}

	/**
	* Adds the user groups to the team
	*
	* @param teamId the primary key of the team
	* @param userGroupIds the primary keys of the user groups
	* @throws PortalException if a team or user group with the primary key
	could not be found, or if the user did not have permission to
	assign team members
	*/
	@Override
	public void addTeamUserGroups(long teamId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userGroupService.addTeamUserGroups(teamId, userGroupIds);
	}

	/**
	* Adds a user group.
	*
	* <p>
	* This method handles the creation and bookkeeping of the user group,
	* including its resources, metadata, and internal data structures.
	* </p>
	*
	* @param name the user group's name
	* @param description the user group's description
	* @return the user group
	* @throws PortalException if the user group's information was invalid
	or if the user did not have permission to add the user group
	* @deprecated As of 6.2.0, replaced by {@link #addUserGroup(String, String,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.UserGroup addUserGroup(
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupService.addUserGroup(name, description);
	}

	/**
	* Adds a user group.
	*
	* <p>
	* This method handles the creation and bookkeeping of the user group,
	* including its resources, metadata, and internal data structures.
	* </p>
	*
	* @param name the user group's name
	* @param description the user group's description
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set expando bridge attributes for the
	user group.
	* @return the user group
	* @throws PortalException if the user group's information was invalid or if
	the user did not have permission to add the user group
	*/
	@Override
	public com.liferay.portal.model.UserGroup addUserGroup(
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupService.addUserGroup(name, description, serviceContext);
	}

	/**
	* Deletes the user group.
	*
	* @param userGroupId the primary key of the user group
	* @throws PortalException if a user group with the primary key could not be
	found, if the user did not have permission to delete the user
	group, or if the user group had a workflow in approved status
	*/
	@Override
	public void deleteUserGroup(long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userGroupService.deleteUserGroup(userGroupId);
	}

	/**
	* Returns the user group with the primary key.
	*
	* @param userGroupId the primary key of the user group
	* @return Returns the user group with the primary key
	* @throws PortalException if a user group with the primary key could not be
	found or if the user did not have permission to view the user
	group
	*/
	@Override
	public com.liferay.portal.model.UserGroup getUserGroup(long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupService.getUserGroup(userGroupId);
	}

	/**
	* Returns the user group with the name.
	*
	* @param name the user group's name
	* @return Returns the user group with the name
	* @throws PortalException if a user group with the name could not be found
	or if the user did not have permission to view the user group
	*/
	@Override
	public com.liferay.portal.model.UserGroup getUserGroup(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupService.getUserGroup(name);
	}

	/**
	* Returns all the user groups to which the user belongs.
	*
	* @param userId the primary key of the user
	* @return the user groups to which the user belongs
	* @throws PortalException if the current user did not have permission to
	view the user or any one of the user group members
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getUserUserGroups(
		long userId) throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupService.getUserUserGroups(userId);
	}

	/**
	* Removes the user groups from the group.
	*
	* @param groupId the primary key of the group
	* @param userGroupIds the primary keys of the user groups
	* @throws PortalException if the user did not have permission to assign
	group members
	*/
	@Override
	public void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userGroupService.unsetGroupUserGroups(groupId, userGroupIds);
	}

	/**
	* Removes the user groups from the team.
	*
	* @param teamId the primary key of the team
	* @param userGroupIds the primary keys of the user groups
	* @throws PortalException if the user did not have permission to assign
	team members
	*/
	@Override
	public void unsetTeamUserGroups(long teamId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userGroupService.unsetTeamUserGroups(teamId, userGroupIds);
	}

	/**
	* Updates the user group.
	*
	* @param userGroupId the primary key of the user group
	* @param name the user group's name
	* @param description the the user group's description
	* @return the user group
	* @throws PortalException if a user group with the primary key was not
	found, if the new information was invalid, or if the user did
	not have permission to update the user group information
	* @deprecated As of 6.2.0, replaced by {@link #updateUserGroup(long,
	String, String, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.UserGroup updateUserGroup(
		long userGroupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupService.updateUserGroup(userGroupId, name, description);
	}

	/**
	* Updates the user group.
	*
	* @param userGroupId the primary key of the user group
	* @param name the user group's name
	* @param description the the user group's description
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set expando bridge attributes for the
	user group.
	* @return the user group
	* @throws PortalException if a user group with the primary key was not
	found, if the new information was invalid, or if the user did not
	have permission to update the user group information
	*/
	@Override
	public com.liferay.portal.model.UserGroup updateUserGroup(
		long userGroupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupService.updateUserGroup(userGroupId, name,
			description, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public UserGroupService getWrappedUserGroupService() {
		return _userGroupService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedUserGroupService(UserGroupService userGroupService) {
		_userGroupService = userGroupService;
	}

	@Override
	public UserGroupService getWrappedService() {
		return _userGroupService;
	}

	@Override
	public void setWrappedService(UserGroupService userGroupService) {
		_userGroupService = userGroupService;
	}

	private UserGroupService _userGroupService;
}
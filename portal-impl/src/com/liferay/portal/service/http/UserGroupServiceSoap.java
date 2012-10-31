/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.UserGroupServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portal.service.UserGroupServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.UserGroupSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.UserGroup}, that is translated to a
 * {@link com.liferay.portal.model.UserGroupSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupServiceHttp
 * @see       com.liferay.portal.model.UserGroupSoap
 * @see       com.liferay.portal.service.UserGroupServiceUtil
 * @generated
 */
public class UserGroupServiceSoap {
	/**
	* Adds the user groups to the group.
	*
	* @param groupId the primary key of the group
	* @param userGroupIds the primary keys of the user groups
	* @throws PortalException if a group or user group with the primary key
	could not be found, or if the user did not have permission to
	assign group members
	* @throws SystemException if a system exception occurred
	*/
	public static void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws RemoteException {
		try {
			UserGroupServiceUtil.addGroupUserGroups(groupId, userGroupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Adds the user groups to the team
	*
	* @param teamId the primary key of the team
	* @param userGroupIds the primary keys of the user groups
	* @throws PortalException if a team or user group with the primary key
	could not be found, or if the user did not have permission to
	assign team members
	* @throws SystemException if a system exception occurred
	*/
	public static void addTeamUserGroups(long teamId, long[] userGroupIds)
		throws RemoteException {
		try {
			UserGroupServiceUtil.addTeamUserGroups(teamId, userGroupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	* @throws SystemException if a system exception occurred
	* @deprecated {@link #addUserGroup(String, String, serviceContext)}
	*/
	public static com.liferay.portal.model.UserGroupSoap addUserGroup(
		java.lang.String name, java.lang.String description)
		throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.addUserGroup(name,
					description);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	* @param serviceContext the user group's service context (optionally
	<code>null</code>). Can set expando bridge attributes for the
	user group.
	* @return the user group
	* @throws PortalException if the user group's information was invalid or if
	the user did not have permission to add the user group
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroupSoap addUserGroup(
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.addUserGroup(name,
					description, serviceContext);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the user group.
	*
	* @param userGroupId the primary key of the user group
	* @throws PortalException if a user group with the primary key could not be
	found, if the user did not have permission to delete the user
	group, or if the user group had a workflow in approved status
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUserGroup(long userGroupId)
		throws RemoteException {
		try {
			UserGroupServiceUtil.deleteUserGroup(userGroupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the user group with the primary key.
	*
	* @param userGroupId the primary key of the user group
	* @return Returns the user group with the primary key
	* @throws PortalException if a user group with the primary key could not be
	found or if the user did not have permission to view the user
	group
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroupSoap getUserGroup(
		long userGroupId) throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.getUserGroup(userGroupId);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the user group with the name.
	*
	* @param name the user group's name
	* @return Returns the user group with the name
	* @throws PortalException if a user group with the name could not be found
	or if the user did not have permission to view the user group
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroupSoap getUserGroup(
		java.lang.String name) throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.getUserGroup(name);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns all the user groups to which the user belongs.
	*
	* @param userId the primary key of the user
	* @return the user groups to which the user belongs
	* @throws PortalException if the current user did not have permission to
	view the user or any one of the user group members
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroupSoap[] getUserUserGroups(
		long userId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.UserGroup> returnValue = UserGroupServiceUtil.getUserUserGroups(userId);

			return com.liferay.portal.model.UserGroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Removes the user groups from the group.
	*
	* @param groupId the primary key of the group
	* @param userGroupIds the primary keys of the user groups
	* @throws PortalException if the user did not have permission to assign
	group members
	* @throws SystemException if a system exception occurred
	*/
	public static void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws RemoteException {
		try {
			UserGroupServiceUtil.unsetGroupUserGroups(groupId, userGroupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Removes the user groups from the team.
	*
	* @param teamId the primary key of the team
	* @param userGroupIds the primary keys of the user groups
	* @throws PortalException if the user did not have permission to assign
	team members
	* @throws SystemException if a system exception occurred
	*/
	public static void unsetTeamUserGroups(long teamId, long[] userGroupIds)
		throws RemoteException {
		try {
			UserGroupServiceUtil.unsetTeamUserGroups(teamId, userGroupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	* @throws SystemException if a system exception occurred
	* @deprecated {@link #updateUserGroup(long, String, String,
	serviceContext)}
	*/
	public static com.liferay.portal.model.UserGroupSoap updateUserGroup(
		long userGroupId, java.lang.String name, java.lang.String description)
		throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.updateUserGroup(userGroupId,
					name, description);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the user group.
	*
	* @param userGroupId the primary key of the user group
	* @param name the user group's name
	* @param description the the user group's description
	* @param serviceContext the user group's service context (optionally
	<code>null</code>). Can set expando bridge attributes for the
	user group.
	* @return the user group
	* @throws PortalException if a user group with the primary key was not
	found, if the new information was invalid, or if the user did not
	have permission to update the user group information
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroupSoap updateUserGroup(
		long userGroupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.updateUserGroup(userGroupId,
					name, description, serviceContext);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UserGroupServiceSoap.class);
}
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
import com.liferay.portal.service.GroupServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portal.service.GroupServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.GroupSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.Group}, that is translated to a
 * {@link com.liferay.portal.model.GroupSoap}. Methods that SOAP cannot
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
 * @see       GroupServiceHttp
 * @see       com.liferay.portal.model.GroupSoap
 * @see       com.liferay.portal.service.GroupServiceUtil
 * @generated
 */
public class GroupServiceSoap {
	/**
	* Adds a group.
	*
	* @param parentGroupId the primary key of the parent group
	* @param liveGroupId the primary key of the live group
	* @param name the entity's name
	* @param description the group's description (optionally
	<code>null</code>)
	* @param type the group's type. For more information see {@link
	com.liferay.portal.model.GroupConstants}
	* @param friendlyURL the group's friendlyURL (optionally
	<code>null</code>)
	* @param site whether the group is to be associated with a main site
	* @param active whether the group is active
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the asset category IDs and asset tag
	names for the group, and can set whether the group is for staging
	* @return the group
	* @throws PortalException if the user did not have permission to add the
	group, if a creator could not be found, if the group's
	information was invalid, if a layout could not be found, or if a
	valid friendly URL could not be created for the group
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap addGroup(
		long parentGroupId, long liveGroupId, java.lang.String name,
		java.lang.String description, int type, java.lang.String friendlyURL,
		boolean site, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.addGroup(parentGroupId,
					liveGroupId, name, description, type, friendlyURL, site,
					active, serviceContext);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Adds the group using the group default live group ID.
	*
	* @param parentGroupId the primary key of the parent group
	* @param name the entity's name
	* @param description the group's description (optionally
	<code>null</code>)
	* @param type the group's type. For more information see {@link
	com.liferay.portal.model.GroupConstants}
	* @param friendlyURL the group's friendlyURL
	* @param site whether the group is to be associated with a main site
	* @param active whether the group is active
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs and asset tag
	names for the group, and can set whether the group is for
	staging
	* @return the group
	* @throws PortalException if the user did not have permission to add
	the group, if a creator could not be found, if the group's
	information was invalid, if a layout could not be found, or
	if a valid friendly URL could not be created for the group
	* @throws SystemException if a system exception occurred
	* @deprecated {@link #addGroup(long, long, String, String, int, String,
	boolean, boolean, ServiceContext)}
	*/
	public static com.liferay.portal.model.GroupSoap addGroup(
		long parentGroupId, java.lang.String name,
		java.lang.String description, int type, java.lang.String friendlyURL,
		boolean site, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.addGroup(parentGroupId,
					name, description, type, friendlyURL, site, active,
					serviceContext);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated {@link #addGroup(long, String, String, int, String, boolean,
	boolean, ServiceContext)}
	*/
	public static com.liferay.portal.model.GroupSoap addGroup(
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean site, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.addGroup(name,
					description, type, friendlyURL, site, active, serviceContext);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Adds the groups to the role.
	*
	* @param roleId the primary key of the role
	* @param groupIds the primary keys of the groups
	* @throws PortalException if the user did not have permission to update the
	role
	* @throws SystemException if a system exception occurred
	*/
	public static void addRoleGroups(long roleId, long[] groupIds)
		throws RemoteException {
		try {
			GroupServiceUtil.addRoleGroups(roleId, groupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the group.
	*
	* <p>
	* The group is unstaged and its assets and resources including layouts,
	* membership requests, subscriptions, teams, blogs, bookmarks, calendar
	* events, image gallery, journals, message boards, polls, shopping related
	* entities, software catalog, and wikis are also deleted.
	* </p>
	*
	* @param groupId the primary key of the group
	* @throws PortalException if the user did not have permission to delete the
	group or its assets or resources, if a group with the primary key
	could not be found, or if the group was a system group
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteGroup(long groupId) throws RemoteException {
		try {
			GroupServiceUtil.deleteGroup(groupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the group with the primary key.
	*
	* @param groupId the primary key of the group
	* @return the group with the primary key
	* @throws PortalException if a group with the primary key could not be
	found or if the current user did not have permission to view the
	group
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap getGroup(long groupId)
		throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.getGroup(groupId);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the group with the name.
	*
	* @param companyId the primary key of the company
	* @param name the group's name
	* @return the group with the name
	* @throws PortalException if a matching group could not be found or if the
	current user did not have permission to view the group
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap getGroup(long companyId,
		java.lang.String name) throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.getGroup(companyId,
					name);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns a range of all the site groups for which the user has control
	* panel access.
	*
	* @param portlets the portlets to manage
	* @param max the upper bound of the range of groups to consider (not
	inclusive)
	* @return the range of site groups for which the user has control panel
	access
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap[] getManageableSites(
		java.util.Collection<com.liferay.portal.model.Portlet> portlets, int max)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getManageableSites(portlets,
					max);

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the groups associated with the organizations.
	*
	* @param organizations the organizations
	* @return the groups associated with the organizations
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap[] getOrganizationsGroups(
		com.liferay.portal.model.OrganizationSoap[] organizations)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getOrganizationsGroups(com.liferay.portal.model.impl.OrganizationModelImpl.toModels(
						organizations));

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the group associated with the user.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @return the group associated with the user
	* @throws PortalException if a matching group could not be found or if the
	current user did not have permission to view the group
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap getUserGroup(
		long companyId, long userId) throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.getUserGroup(companyId,
					userId);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the groups associated with the user groups.
	*
	* @param userGroups the user groups
	* @return the groups associated with the user groups
	* @throws PortalException if any one of the user group's group could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap[] getUserGroupsGroups(
		com.liferay.portal.model.UserGroupSoap[] userGroups)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getUserGroupsGroups(com.liferay.portal.model.impl.UserGroupModelImpl.toModels(
						userGroups));

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the range of all groups associated with the user's organization
	* groups, including the ancestors of the organization groups, unless portal
	* property <code>organizations.membership.strict</code> is set to
	* <code>true</code>.
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
	* @param start the lower bound of the range of groups to consider
	* @param end the upper bound of the range of groups to consider (not
	inclusive)
	* @return the range of groups associated with the user's organizations
	* @throws PortalException if a user with the primary key could not be found
	or if another portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap[] getUserOrganizationsGroups(
		long userId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getUserOrganizationsGroups(userId,
					start, end);

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap[] getUserPlaces(
		long userId, java.lang.String[] classNames,
		boolean includeControlPanel, int max) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getUserPlaces(userId,
					classNames, includeControlPanel, max);

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the user's group &quot;places&quot; associated with the group
	* entity class names, including the control panel group if the user is
	* permitted to view the control panel.
	*
	* <p>
	* <ul> <li> Class name &quot;User&quot; includes the user's layout set
	* group. </li> <li> Class name &quot;Organization&quot; includes the user's
	* immediate organization groups and inherited organization groups. </li>
	* <li> Class name &quot;Group&quot; includes the user's immediate
	* organization groups and site groups. </li> <li> A <code>classNames</code>
	* value of <code>null</code> includes the user's layout set group,
	* organization groups, inherited organization groups, and site groups.
	* </li> </ul>
	* </p>
	*
	* @param userId the primary key of the user
	* @param classNames the group entity class names (optionally
	<code>null</code>). For more information see {@link
	#getUserPlaces(long, String[], int)}
	* @param max the maximum number of groups to return
	* @return the user's group &quot;places&quot;
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap[] getUserPlaces(
		long userId, java.lang.String[] classNames, int max)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getUserPlaces(userId,
					classNames, max);

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.GroupSoap[] getUserPlaces(
		long userId, java.lang.String[] classNames, java.lang.String name,
		boolean active, boolean includeControlPanel, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getUserPlaces(userId,
					classNames, name, active, includeControlPanel, start, end);

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the guest or current user's group &quot;places&quot; associated
	* with the group entity class names, including the control panel group if
	* the user is permitted to view the control panel.
	*
	* <p>
	* <ul> <li> Class name &quot;User&quot; includes the user's layout set
	* group. </li> <li> Class name &quot;Organization&quot; includes the user's
	* immediate organization groups and inherited organization groups. </li>
	* <li> Class name &quot;Group&quot; includes the user's immediate
	* organization groups and site groups. </li> <li> A <code>classNames</code>
	* value of <code>null</code> includes the user's layout set group,
	* organization groups, inherited organization groups, and site groups.
	* </li> </ul>
	* </p>
	*
	* @param classNames the group entity class names (optionally
	<code>null</code>). For more information see {@link
	#getUserPlaces(String[], int)}
	* @param max the maximum number of groups to return
	* @return the user's group &quot;places&quot;
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap[] getUserPlaces(
		java.lang.String[] classNames, int max) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getUserPlaces(classNames,
					max);

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of the guest or current user's group
	* &quot;places&quot; associated with the group entity class names,
	* including the control panel group if the user is permitted to view the
	* control panel.
	*
	* @return the number of user's group &quot;places&quot;
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static int getUserPlacesCount() throws RemoteException {
		try {
			int returnValue = GroupServiceUtil.getUserPlacesCount();

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the guest or current user's layout set group, organization
	* groups, inherited organization groups, and site groups.
	*
	* @return the user's layout set group, organization groups, and inherited
	organization groups, and site groups
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap[] getUserSites()
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.getUserSites();

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns <code>true</code> if the user is associated with the group,
	* including the user's inherited organizations and user groups. System and
	* staged groups are not included.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @return <code>true</code> if the user is associated with the group;
	<code>false</code> otherwise
	* @throws PortalException if the current user did not have permission to
	view the user or group members
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasUserGroup(long userId, long groupId)
		throws RemoteException {
		try {
			boolean returnValue = GroupServiceUtil.hasUserGroup(userId, groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns an ordered range of all the site groups and organization groups
	* that match the name and description, optionally including the user's
	* inherited organization groups and user groups. System and staged groups
	* are not included.
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
	* @param name the group's name (optionally <code>null</code>)
	* @param description the group's description (optionally
	<code>null</code>)
	* @param params the finder params (optionally <code>null</code>). To
	include the user's inherited organizations and user groups in the
	search, add entries having &quot;usersGroups&quot; and
	&quot;inherit&quot; as keys mapped to the the user's ID. For more
	information see {@link
	com.liferay.portal.service.persistence.GroupFinder}
	* @param start the lower bound of the range of groups to return
	* @param end the upper bound of the range of groups to return (not
	inclusive)
	* @return the matching groups ordered by name
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap[] search(long companyId,
		java.lang.String name, java.lang.String description,
		java.lang.String[] params, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Group> returnValue = GroupServiceUtil.search(companyId,
					name, description, params, start, end);

			return com.liferay.portal.model.GroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of groups and organization groups that match the name
	* and description, optionally including the user's inherited organizations
	* and user groups. System and staged groups are not included.
	*
	* @param companyId the primary key of the company
	* @param name the group's name (optionally <code>null</code>)
	* @param description the group's description (optionally
	<code>null</code>)
	* @param params the finder params (optionally <code>null</code>). To
	include the user's inherited organizations and user groups in the
	search, add entries having &quot;usersGroups&quot; and
	&quot;inherit&quot; as keys mapped to the the user's ID. For more
	information see {@link
	com.liferay.portal.service.persistence.GroupFinder}
	* @return the number of matching groups
	* @throws SystemException if a system exception occurred
	*/
	public static int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.String[] params)
		throws RemoteException {
		try {
			int returnValue = GroupServiceUtil.searchCount(companyId, name,
					description, params);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Sets the groups associated with the role, removing and adding
	* associations as necessary.
	*
	* @param roleId the primary key of the role
	* @param groupIds the primary keys of the groups
	* @throws PortalException if the user did not have permission to update
	update the role
	* @throws SystemException if a system exception occurred
	*/
	public static void setRoleGroups(long roleId, long[] groupIds)
		throws RemoteException {
		try {
			GroupServiceUtil.setRoleGroups(roleId, groupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Removes the groups from the role.
	*
	* @param roleId the primary key of the role
	* @param groupIds the primary keys of the groups
	* @throws PortalException if the user did not have permission to update the
	role
	* @throws SystemException if a system exception occurred
	*/
	public static void unsetRoleGroups(long roleId, long[] groupIds)
		throws RemoteException {
		try {
			GroupServiceUtil.unsetRoleGroups(roleId, groupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the group's friendly URL.
	*
	* @param groupId the primary key of the group
	* @param friendlyURL the group's new friendlyURL (optionally
	<code>null</code>)
	* @return the group
	* @throws PortalException if the user did not have permission to update the
	group, if a group with the primary key could not be found, or if
	a valid friendly URL could not be created for the group
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap updateFriendlyURL(
		long groupId, java.lang.String friendlyURL) throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.updateFriendlyURL(groupId,
					friendlyURL);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the group.
	*
	* @param groupId the primary key of the group
	* @param parentGroupId the primary key of the parent group
	* @param name the group's new name
	* @param description the group's new description (optionally
	<code>null</code>)
	* @param type the group's new type. For more information see {@link
	com.liferay.portal.model.GroupConstants}
	* @param friendlyURL the group's new friendlyURL (optionally
	<code>null</code>)
	* @param active whether the group is active
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the asset category IDs and asset tag
	names for the group.
	* @return the group
	* @throws PortalException if the user did not have permission to update the
	group, if a group with the primary key could not be found, if the
	friendly URL was invalid or could one not be created
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap updateGroup(long groupId,
		long parentGroupId, java.lang.String name,
		java.lang.String description, int type, java.lang.String friendlyURL,
		boolean active, com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.updateGroup(groupId,
					parentGroupId, name, description, type, friendlyURL,
					active, serviceContext);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the group's type settings.
	*
	* @param groupId the primary key of the group
	* @param typeSettings the group's new type settings (optionally
	<code>null</code>)
	* @return the group
	* @throws PortalException if the user did not have permission to update the
	group or if a group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.GroupSoap updateGroup(long groupId,
		java.lang.String typeSettings) throws RemoteException {
		try {
			com.liferay.portal.model.Group returnValue = GroupServiceUtil.updateGroup(groupId,
					typeSettings);

			return com.liferay.portal.model.GroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(GroupServiceSoap.class);
}
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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>GroupServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.kernel.model.GroupSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.kernel.model.Group</code>, that is translated to a
 * <code>com.liferay.portal.kernel.model.GroupSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
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
 * @author Brian Wing Shun Chan
 * @see GroupServiceHttp
 * @generated
 */
public class GroupServiceSoap {

	public static com.liferay.portal.kernel.model.GroupSoap addGroup(
			long parentGroupId, long liveGroupId, String[] nameMapLanguageIds,
			String[] nameMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, int type, boolean manualMembership,
			int membershipRestriction, String friendlyURL, boolean site,
			boolean inheritContent, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.portal.kernel.model.Group returnValue =
				GroupServiceUtil.addGroup(
					parentGroupId, liveGroupId, nameMap, descriptionMap, type,
					manualMembership, membershipRestriction, friendlyURL, site,
					inheritContent, active, serviceContext);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.GroupSoap addGroup(
			long parentGroupId, long liveGroupId, String[] nameMapLanguageIds,
			String[] nameMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, int type, boolean manualMembership,
			int membershipRestriction, String friendlyURL, boolean site,
			boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.portal.kernel.model.Group returnValue =
				GroupServiceUtil.addGroup(
					parentGroupId, liveGroupId, nameMap, descriptionMap, type,
					manualMembership, membershipRestriction, friendlyURL, site,
					active, serviceContext);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModel(
				returnValue);
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
	 * @throws PortalException if a portal exception occurred
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
	 * Checks that the current user is permitted to use the group for Remote
	 * Staging.
	 *
	 * @param groupId the primary key of the group
	 * @throws PortalException if a portal exception occurred
	 */
	public static void checkRemoteStagingGroup(long groupId)
		throws RemoteException {

		try {
			GroupServiceUtil.checkRemoteStagingGroup(groupId);
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
	 * events, image gallery, journals, message boards, polls, and wikis are
	 * also deleted.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @throws PortalException if a portal exception occurred
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

	public static void disableStaging(long groupId) throws RemoteException {
		try {
			GroupServiceUtil.disableStaging(groupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void enableStaging(long groupId) throws RemoteException {
		try {
			GroupServiceUtil.enableStaging(groupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the company group.
	 *
	 * @param companyId the primary key of the company
	 * @return the group associated with the company
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap getCompanyGroup(
			long companyId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Group returnValue =
				GroupServiceUtil.getCompanyGroup(companyId);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModel(
				returnValue);
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
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap getGroup(
			long groupId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Group returnValue =
				GroupServiceUtil.getGroup(groupId);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModel(
				returnValue);
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
	 * @param groupKey the group key
	 * @return the group with the group key
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap getGroup(
			long companyId, String groupKey)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Group returnValue =
				GroupServiceUtil.getGroup(companyId, groupKey);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the group's display URL.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout set is private to the group
	 * @param secureConnection whether the generated URL uses a secure
	 connection
	 * @return the group's display URL
	 * @throws PortalException if a group with the primary key could not be
	 found or if a portal exception occurred
	 */
	public static String getGroupDisplayURL(
			long groupId, boolean privateLayout, boolean secureConnection)
		throws RemoteException {

		try {
			String returnValue = GroupServiceUtil.getGroupDisplayURL(
				groupId, privateLayout, secureConnection);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns all the groups that are direct children of the parent group.
	 *
	 * @param companyId the primary key of the company
	 * @param parentGroupId the primary key of the parent group
	 * @param site whether the group is to be associated with a main site
	 * @return the matching groups, or <code>null</code> if no matches were
	 found
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap[] getGroups(
			long companyId, long parentGroupId, boolean site)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Group> returnValue =
				GroupServiceUtil.getGroups(companyId, parentGroupId, site);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns all the groups that are direct children of the parent group.
	 *
	 * @param companyId the primary key of the company
	 * @param parentGroupId the primary key of the parent group
	 * @param site whether the group is to be associated with a main site
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the matching groups, or <code>null</code> if no matches were
	 found
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap[] getGroups(
			long companyId, long parentGroupId, boolean site, int start,
			int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Group> returnValue =
				GroupServiceUtil.getGroups(
					companyId, parentGroupId, site, start, end);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.GroupSoap[] getGroups(
			long companyId, long parentGroupId, String name, boolean site,
			int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Group> returnValue =
				GroupServiceUtil.getGroups(
					companyId, parentGroupId, name, site, start, end);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the number of groups that are direct children of the parent
	 * group.
	 *
	 * @param companyId the primary key of the company
	 * @param parentGroupId the primary key of the parent group
	 * @param site whether the group is to be associated with a main site
	 * @return the number of matching groups
	 */
	public static int getGroupsCount(
			long companyId, long parentGroupId, boolean site)
		throws RemoteException {

		try {
			int returnValue = GroupServiceUtil.getGroupsCount(
				companyId, parentGroupId, site);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupsCount(
			long companyId, long parentGroupId, String name, boolean site)
		throws RemoteException {

		try {
			int returnValue = GroupServiceUtil.getGroupsCount(
				companyId, parentGroupId, name, site);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the number of groups that are direct children of the parent group
	 * with the matching className.
	 *
	 * @param companyId the primary key of the company
	 * @param className the class name of the group
	 * @param parentGroupId the primary key of the parent group
	 * @return the number of matching groups
	 */
	public static int getGroupsCount(
			long companyId, String className, long parentGroupId)
		throws RemoteException {

		try {
			int returnValue = GroupServiceUtil.getGroupsCount(
				companyId, className, parentGroupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.GroupSoap[] getGtGroups(
			long gtGroupId, long companyId, long parentGroupId, boolean site,
			int size)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Group> returnValue =
				GroupServiceUtil.getGtGroups(
					gtGroupId, companyId, parentGroupId, site, size);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModels(
				returnValue);
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
	 * @return the range of site groups for which the user has Control Panel
	 access
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap[]
			getManageableSiteGroups(
				java.util.Collection<com.liferay.portal.kernel.model.Portlet>
					portlets,
				int max)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Group> returnValue =
				GroupServiceUtil.getManageableSiteGroups(portlets, max);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModels(
				returnValue);
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
	 */
	public static com.liferay.portal.kernel.model.GroupSoap[]
			getOrganizationsGroups(
				com.liferay.portal.kernel.model.OrganizationSoap[]
					organizations)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Group> returnValue =
				GroupServiceUtil.getOrganizationsGroups(
					com.liferay.portal.model.impl.OrganizationModelImpl.
						toModels(organizations));

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the group directly associated with the user.
	 *
	 * @param companyId the primary key of the company
	 * @param userId the primary key of the user
	 * @return the group directly associated with the user
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap getUserGroup(
			long companyId, long userId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Group returnValue =
				GroupServiceUtil.getUserGroup(companyId, userId);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModel(
				returnValue);
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
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap[]
			getUserGroupsGroups(
				com.liferay.portal.kernel.model.UserGroupSoap[] userGroups)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Group> returnValue =
				GroupServiceUtil.getUserGroupsGroups(
					com.liferay.portal.model.impl.UserGroupModelImpl.toModels(
						userGroups));

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModels(
				returnValue);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param userId the primary key of the user
	 * @param start the lower bound of the range of groups to consider
	 * @param end the upper bound of the range of groups to consider (not
	 inclusive)
	 * @return the range of groups associated with the user's organizations
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap[]
			getUserOrganizationsGroups(long userId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Group> returnValue =
				GroupServiceUtil.getUserOrganizationsGroups(userId, start, end);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.GroupSoap[]
			getUserSitesGroups()
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Group> returnValue =
				GroupServiceUtil.getUserSitesGroups();

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the user's groups &quot;sites&quot; associated with the group
	 * entity class names, including the Control Panel group if the user is
	 * permitted to view the Control Panel.
	 *
	 * <ul>
	 * <li>
	 * Class name &quot;User&quot; includes the user's layout set
	 * group.
	 * </li>
	 * <li>
	 * Class name &quot;Organization&quot; includes the user's
	 * immediate organization groups and inherited organization groups.
	 * </li>
	 * <li>
	 * Class name &quot;Group&quot; includes the user's immediate
	 * organization groups and site groups.
	 * </li>
	 * <li>
	 * A <code>classNames</code>
	 * value of <code>null</code> includes the user's layout set group,
	 * organization groups, inherited organization groups, and site groups.
	 * </li>
	 * </ul>
	 *
	 * @param userId the primary key of the user
	 * @param classNames the group entity class names (optionally
	 <code>null</code>). For more information see {@link
	 #getUserSitesGroups(long, String[], int)}.
	 * @param max the maximum number of groups to return
	 * @return the user's groups &quot;sites&quot;
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap[]
			getUserSitesGroups(long userId, String[] classNames, int max)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Group> returnValue =
				GroupServiceUtil.getUserSitesGroups(userId, classNames, max);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the guest or current user's groups &quot;sites&quot; associated
	 * with the group entity class names, including the Control Panel group if
	 * the user is permitted to view the Control Panel.
	 *
	 * <ul>
	 * <li>
	 * Class name &quot;User&quot; includes the user's layout set
	 * group.
	 * </li>
	 * <li>
	 * Class name &quot;Organization&quot; includes the user's
	 * immediate organization groups and inherited organization groups.
	 * </li>
	 * <li>
	 * Class name &quot;Group&quot; includes the user's immediate
	 * organization groups and site groups.
	 * </li>
	 * <li>
	 * A <code>classNames</code>
	 * value of <code>null</code> includes the user's layout set group,
	 * organization groups, inherited organization groups, and site groups.
	 * </li>
	 * </ul>
	 *
	 * @param classNames the group entity class names (optionally
	 <code>null</code>). For more information see {@link
	 #getUserSitesGroups(long, String[], int)}.
	 * @param max the maximum number of groups to return
	 * @return the user's groups &quot;sites&quot;
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap[]
			getUserSitesGroups(String[] classNames, int max)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Group> returnValue =
				GroupServiceUtil.getUserSitesGroups(classNames, max);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the number of the guest or current user's groups
	 * &quot;sites&quot; associated with the group entity class names, including
	 * the Control Panel group if the user is permitted to view the Control
	 * Panel.
	 *
	 * @return the number of user's groups &quot;sites&quot;
	 * @throws PortalException if a portal exception occurred
	 */
	public static int getUserSitesGroupsCount() throws RemoteException {
		try {
			int returnValue = GroupServiceUtil.getUserSitesGroupsCount();

			return returnValue;
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
	 * @throws PortalException if a portal exception occurred
	 */
	public static boolean hasUserGroup(long userId, long groupId)
		throws RemoteException {

		try {
			boolean returnValue = GroupServiceUtil.hasUserGroup(
				userId, groupId);

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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 com.liferay.portal.kernel.service.persistence.GroupFinder}.
	 * @param start the lower bound of the range of groups to return
	 * @param end the upper bound of the range of groups to return (not
	 inclusive)
	 * @return the matching groups ordered by name
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap[] search(
			long companyId, String name, String description, String[] params,
			int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Group> returnValue =
				GroupServiceUtil.search(
					companyId, name, description, params, start, end);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModels(
				returnValue);
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
	 com.liferay.portal.kernel.service.persistence.GroupFinder}.
	 * @return the number of matching groups
	 */
	public static int searchCount(
			long companyId, String name, String description, String[] params)
		throws RemoteException {

		try {
			int returnValue = GroupServiceUtil.searchCount(
				companyId, name, description, params);

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
	 * @throws PortalException if a portal exception occurred
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
	 * @throws PortalException if a portal exception occurred
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
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap updateFriendlyURL(
			long groupId, String friendlyURL)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Group returnValue =
				GroupServiceUtil.updateFriendlyURL(groupId, friendlyURL);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.GroupSoap updateGroup(
			long groupId, long parentGroupId, String[] nameMapLanguageIds,
			String[] nameMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, int type, boolean manualMembership,
			int membershipRestriction, String friendlyURL,
			boolean inheritContent, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.portal.kernel.model.Group returnValue =
				GroupServiceUtil.updateGroup(
					groupId, parentGroupId, nameMap, descriptionMap, type,
					manualMembership, membershipRestriction, friendlyURL,
					inheritContent, active, serviceContext);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModel(
				returnValue);
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
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.GroupSoap updateGroup(
			long groupId, String typeSettings)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Group returnValue =
				GroupServiceUtil.updateGroup(groupId, typeSettings);

			return com.liferay.portal.kernel.model.GroupSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(GroupServiceSoap.class);

}
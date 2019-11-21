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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the remote service utility for Group. This utility wraps
 * <code>com.liferay.portal.service.impl.GroupServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see GroupService
 * @generated
 */
public class GroupServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.GroupServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link GroupServiceUtil} to access the group remote service. Add custom service methods to <code>com.liferay.portal.service.impl.GroupServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.portal.kernel.model.Group addGroup(
			long parentGroupId, long liveGroupId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean site, boolean inheritContent,
			boolean active, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addGroup(
			parentGroupId, liveGroupId, nameMap, descriptionMap, type,
			manualMembership, membershipRestriction, friendlyURL, site,
			inheritContent, active, serviceContext);
	}

	public static com.liferay.portal.kernel.model.Group addGroup(
			long parentGroupId, long liveGroupId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean site, boolean active,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addGroup(
			parentGroupId, liveGroupId, nameMap, descriptionMap, type,
			manualMembership, membershipRestriction, friendlyURL, site, active,
			serviceContext);
	}

	/**
	 * Adds the groups to the role.
	 *
	 * @param roleId the primary key of the role
	 * @param groupIds the primary keys of the groups
	 * @throws PortalException if a portal exception occurred
	 */
	public static void addRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addRoleGroups(roleId, groupIds);
	}

	/**
	 * Checks that the current user is permitted to use the group for Remote
	 * Staging.
	 *
	 * @param groupId the primary key of the group
	 * @throws PortalException if a portal exception occurred
	 */
	public static void checkRemoteStagingGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkRemoteStagingGroup(groupId);
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
	public static void deleteGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteGroup(groupId);
	}

	public static void disableStaging(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().disableStaging(groupId);
	}

	public static void enableStaging(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().enableStaging(groupId);
	}

	/**
	 * Returns the company group.
	 *
	 * @param companyId the primary key of the company
	 * @return the group associated with the company
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.Group getCompanyGroup(
			long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCompanyGroup(companyId);
	}

	/**
	 * Returns the group with the primary key.
	 *
	 * @param groupId the primary key of the group
	 * @return the group with the primary key
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.Group getGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroup(groupId);
	}

	/**
	 * Returns the group with the name.
	 *
	 * @param companyId the primary key of the company
	 * @param groupKey the group key
	 * @return the group with the group key
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.Group getGroup(
			long companyId, String groupKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroup(companyId, groupKey);
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
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupDisplayURL(
			groupId, privateLayout, secureConnection);
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
	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getGroups(long companyId, long parentGroupId, boolean site)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroups(companyId, parentGroupId, site);
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
	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getGroups(
				long companyId, long parentGroupId, boolean site, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroups(
			companyId, parentGroupId, site, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getGroups(
				long companyId, long parentGroupId, String name, boolean site,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroups(
			companyId, parentGroupId, name, site, start, end);
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
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupsCount(companyId, parentGroupId, site);
	}

	public static int getGroupsCount(
			long companyId, long parentGroupId, String name, boolean site)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupsCount(
			companyId, parentGroupId, name, site);
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
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupsCount(companyId, className, parentGroupId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getGtGroups(
				long gtGroupId, long companyId, long parentGroupId,
				boolean site, int size)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGtGroups(
			gtGroupId, companyId, parentGroupId, site, size);
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
	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getManageableSiteGroups(
				java.util.Collection<com.liferay.portal.kernel.model.Portlet>
					portlets,
				int max)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getManageableSiteGroups(portlets, max);
	}

	/**
	 * Returns the groups associated with the organizations.
	 *
	 * @param organizations the organizations
	 * @return the groups associated with the organizations
	 * @throws PortalException if a portal exception occurred
	 */
	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getOrganizationsGroups(
				java.util.List<com.liferay.portal.kernel.model.Organization>
					organizations)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getOrganizationsGroups(organizations);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * Returns the group directly associated with the user.
	 *
	 * @param companyId the primary key of the company
	 * @param userId the primary key of the user
	 * @return the group directly associated with the user
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.portal.kernel.model.Group getUserGroup(
			long companyId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserGroup(companyId, userId);
	}

	/**
	 * Returns the groups associated with the user groups.
	 *
	 * @param userGroups the user groups
	 * @return the groups associated with the user groups
	 * @throws PortalException if a portal exception occurred
	 */
	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getUserGroupsGroups(
				java.util.List<com.liferay.portal.kernel.model.UserGroup>
					userGroups)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserGroupsGroups(userGroups);
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
	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getUserOrganizationsGroups(long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserOrganizationsGroups(userId, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getUserSitesGroups()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserSitesGroups();
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
	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getUserSitesGroups(long userId, String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserSitesGroups(userId, classNames, max);
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
	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getUserSitesGroups(String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserSitesGroups(classNames, max);
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
	public static int getUserSitesGroupsCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserSitesGroupsCount();
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
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().hasUserGroup(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> search(
			long companyId, long[] classNameIds, String keywords,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Group> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(
			companyId, classNameIds, keywords, params, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> search(
			long companyId, long[] classNameIds, String name,
			String description, java.util.LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Group> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(
			companyId, classNameIds, name, description, params, andOperator,
			start, end, obc);
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
	public static java.util.List<com.liferay.portal.kernel.model.Group> search(
			long companyId, String name, String description, String[] params,
			int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(
			companyId, name, description, params, start, end);
	}

	public static int searchCount(
		long companyId, long[] classNameIds, String keywords,
		java.util.LinkedHashMap<String, Object> params) {

		return getService().searchCount(
			companyId, classNameIds, keywords, params);
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
		long companyId, String name, String description, String[] params) {

		return getService().searchCount(companyId, name, description, params);
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
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().setRoleGroups(roleId, groupIds);
	}

	/**
	 * Removes the groups from the role.
	 *
	 * @param roleId the primary key of the role
	 * @param groupIds the primary keys of the groups
	 * @throws PortalException if a portal exception occurred
	 */
	public static void unsetRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().unsetRoleGroups(roleId, groupIds);
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
	public static com.liferay.portal.kernel.model.Group updateFriendlyURL(
			long groupId, String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFriendlyURL(groupId, friendlyURL);
	}

	public static com.liferay.portal.kernel.model.Group updateGroup(
			long groupId, long parentGroupId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean inheritContent, boolean active,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateGroup(
			groupId, parentGroupId, nameMap, descriptionMap, type,
			manualMembership, membershipRestriction, friendlyURL,
			inheritContent, active, serviceContext);
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
	public static com.liferay.portal.kernel.model.Group updateGroup(
			long groupId, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateGroup(groupId, typeSettings);
	}

	public static void updateStagedPortlets(
			long groupId, java.util.Map<String, String> stagedPortletIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateStagedPortlets(groupId, stagedPortletIds);
	}

	public static GroupService getService() {
		if (_service == null) {
			_service = (GroupService)PortalBeanLocatorUtil.locate(
				GroupService.class.getName());
		}

		return _service;
	}

	private static GroupService _service;

}
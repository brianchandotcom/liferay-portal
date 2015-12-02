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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.UserBag;
import com.liferay.portal.security.permission.UserBagFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.GroupServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.permission.RolePermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.exportimport.staging.StagingUtil;
import com.liferay.portlet.ratings.transformer.RatingsDataTransformerUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * groups. Its methods include permission checks. Groups are mostly used in
 * Liferay as a resource container for permissioning and content scoping
 * purposes.
 *
 * @author Brian Wing Shun Chan
 * @see    com.liferay.portal.service.impl.GroupLocalServiceImpl
 */
public class GroupServiceImpl extends GroupServiceBaseImpl {

	@Override
	public Group addGroup(
			long parentGroupId, long liveGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean site, boolean inheritContent,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		if (parentGroupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			PortalPermissionUtil.check(
				getPermissionChecker(), ActionKeys.ADD_COMMUNITY);
		}
		else {
			GroupPermissionUtil.check(
				getPermissionChecker(), parentGroupId,
				ActionKeys.ADD_COMMUNITY);
		}

		Group group = groupLocalService.addGroup(
			getUserId(), parentGroupId, null, 0, liveGroupId, nameMap,
			descriptionMap, type, manualMembership, membershipRestriction,
			friendlyURL, site, inheritContent, active, serviceContext);

		if (site) {
			SiteMembershipPolicyUtil.verifyPolicy(group);
		}

		return group;
	}

	@Override
	public Group addGroup(
			long parentGroupId, long liveGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean site, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return addGroup(
			parentGroupId, liveGroupId, nameMap, descriptionMap, type,
			manualMembership, membershipRestriction, friendlyURL, site, false,
			active, serviceContext);
	}

	/**
	 * Adds a group.
	 *
	 * @param      parentGroupId the primary key of the parent group
	 * @param      liveGroupId the primary key of the live group
	 * @param      name the entity's name
	 * @param      description the group's description (optionally
	 *             <code>null</code>)
	 * @param      type the group's type. For more information see {@link
	 *             GroupConstants}.
	 * @param      manualMembership whether manual membership is allowed for the
	 *             group
	 * @param      membershipRestriction the group's membership restriction. For
	 *             more information see {@link GroupConstants}.
	 * @param      friendlyURL the group's friendlyURL (optionally
	 *             <code>null</code>)
	 * @param      site whether the group is to be associated with a main site
	 * @param      active whether the group is active
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set the asset category IDs and asset
	 *             tag names for the group, and can set whether the group is for
	 *             staging
	 * @return     the group
	 * @deprecated As of 7.0.0, replaced by {@link #addGroup(long, long, Map,
	 *             Map, int, boolean, int, String, boolean, boolean,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public Group addGroup(
			long parentGroupId, long liveGroupId, String name,
			String description, int type, boolean manualMembership,
			int membershipRestriction, String friendlyURL, boolean site,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		return addGroup(
			parentGroupId, liveGroupId, getLocalizationMap(name),
			getLocalizationMap(description), type, manualMembership,
			membershipRestriction, friendlyURL, site, false, active,
			serviceContext);
	}

	/**
	 * Adds the group using the group default live group ID.
	 *
	 * @param      parentGroupId the primary key of the parent group
	 * @param      name the entity's name
	 * @param      description the group's description (optionally
	 *             <code>null</code>)
	 * @param      type the group's type. For more information see {@link
	 *             GroupConstants}.
	 * @param      friendlyURL the group's friendlyURL
	 * @param      site whether the group is to be associated with a main site
	 * @param      active whether the group is active
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs and asset tag
	 *             names for the group, and can set whether the group is for
	 *             staging
	 * @return     the group
	 * @deprecated As of 6.2.0, replaced by {@link #addGroup(long, long, Map,
	 *             Map, int, boolean, int, String, boolean, boolean,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public Group addGroup(
			long parentGroupId, String name, String description, int type,
			String friendlyURL, boolean site, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return addGroup(
			parentGroupId, GroupConstants.DEFAULT_LIVE_GROUP_ID,
			getLocalizationMap(name), getLocalizationMap(description), type,
			true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURL,
			site, active, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addGroup(long, String,
	 *             String, int, String, boolean, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Group addGroup(
			String name, String description, int type, String friendlyURL,
			boolean site, boolean active, ServiceContext serviceContext)
		throws PortalException {

		return addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, name, description, type,
			friendlyURL, site, active, serviceContext);
	}

	/**
	 * Adds the groups to the role.
	 *
	 * @param roleId the primary key of the role
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void addRoleGroups(long roleId, long[] groupIds)
		throws PortalException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

		groupLocalService.addRoleGroups(roleId, groupIds);
	}

	/**
	 * Checks that the current user is permitted to use the group for Remote
	 * Staging.
	 *
	 * @param groupId the primary key of the group
	 */
	@Override
	public void checkRemoteStagingGroup(long groupId) throws PortalException {
		Group group = getGroup(groupId);

		PermissionChecker permissionChecker = getPermissionChecker();

		if (group.getCompanyId() != permissionChecker.getCompanyId()) {
			throw new NoSuchGroupException(
				"Group " + groupId + " does not belong in company " +
					permissionChecker.getCompanyId());
		}
	}

	/**
	 * Deletes the group.
	 *
	 * <p>
	 * The group is unstaged and its assets and resources including layouts,
	 * membership requests, subscriptions, teams, blogs, bookmarks, calendar
	 * events, image gallery, journals, message boards, polls, shopping related
	 * entities, and wikis are also deleted.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 */
	@Override
	public void deleteGroup(long groupId) throws PortalException {
		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.DELETE);

		groupLocalService.deleteGroup(groupId);
	}

	@Override
	public void disableStaging(long groupId) throws PortalException {
		Group group = groupLocalService.getGroup(groupId);

		GroupPermissionUtil.check(
			getPermissionChecker(), group, ActionKeys.UPDATE);

		groupLocalService.disableStaging(groupId);
	}

	@Override
	public void enableStaging(long groupId) throws PortalException {
		Group group = groupLocalService.getGroup(groupId);

		GroupPermissionUtil.check(
			getPermissionChecker(), group, ActionKeys.UPDATE);

		groupLocalService.enableStaging(groupId);
	}

	/**
	 * Returns the company group.
	 *
	 * @param  companyId the primary key of the company
	 * @return the group associated with the company
	 */
	@Override
	public Group getCompanyGroup(long companyId) throws PortalException {
		Group group = groupLocalService.getCompanyGroup(companyId);

		GroupPermissionUtil.check(
			getPermissionChecker(), group, ActionKeys.VIEW);

		return group;
	}

	/**
	 * Returns the group with the primary key.
	 *
	 * @param  groupId the primary key of the group
	 * @return the group with the primary key
	 */
	@Override
	public Group getGroup(long groupId) throws PortalException {
		Group group = groupLocalService.getGroup(groupId);

		GroupPermissionUtil.check(
			getPermissionChecker(), group, ActionKeys.VIEW);

		return group;
	}

	/**
	 * Returns the group with the name.
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupKey the group key
	 * @return the group with the group key
	 */
	@Override
	public Group getGroup(long companyId, String groupKey)
		throws PortalException {

		Group group = groupLocalService.getGroup(companyId, groupKey);

		GroupPermissionUtil.check(
			getPermissionChecker(), group, ActionKeys.VIEW);

		return group;
	}

	/**
	 * Returns all the groups that are direct children of the parent group.
	 *
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  site whether the group is to be associated with a main site
	 * @return the matching groups, or <code>null</code> if no matches were
	 *         found
	 */
	@Override
	public List<Group> getGroups(
			long companyId, long parentGroupId, boolean site)
		throws PortalException {

		return filterGroups(
			groupLocalService.getGroups(companyId, parentGroupId, site));
	}

	/**
	 * Returns a range of all the site groups for which the user has control
	 * panel access.
	 *
	 * @param  portlets the portlets to manage
	 * @param  max the upper bound of the range of groups to consider (not
	 *         inclusive)
	 * @return the range of site groups for which the user has Control Panel
	 *         access
	 */
	@Override
	public List<Group> getManageableSiteGroups(
			Collection<Portlet> portlets, int max)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			LinkedHashMap<String, Object> params = new LinkedHashMap<>();

			params.put("site", Boolean.TRUE);

			return ListUtil.unique(
				groupLocalService.search(permissionChecker.getCompanyId(), null,
				null, null, params, true, 0, max));
		}

		Set<Group> groups = new LinkedHashSet<>();

		List<Group> userSitesGroups = getUserSitesGroups(null, max);

		Iterator<Group> itr = userSitesGroups.iterator();

		while (itr.hasNext()) {
			Group group = itr.next();

			if (group.isSite() &&
				PortletPermissionUtil.hasControlPanelAccessPermission(
					permissionChecker, group.getGroupId(), portlets)) {

				groups.add(group);
			}
		}

		return new ArrayList<>(groups);
	}

	/**
	 * Returns a range of all the site groups for which the user has control
	 * panel access.
	 *
	 * @param      portlets the portlets to manage
	 * @param      max the upper bound of the range of groups to consider (not
	 *             inclusive)
	 * @return     the range of site groups for which the user has Control Panel
	 *             access
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getManageableSiteGroups(Collection, int)}
	 */
	@Deprecated
	@Override
	public List<Group> getManageableSites(Collection<Portlet> portlets, int max)
		throws PortalException {

		return getManageableSiteGroups(portlets, max);
	}

	/**
	 * Returns the groups associated with the organizations.
	 *
	 * @param  organizations the organizations
	 * @return the groups associated with the organizations
	 */
	@Override
	public List<Group> getOrganizationsGroups(List<Organization> organizations)
		throws PortalException {

		List<Group> groups = groupLocalService.getOrganizationsGroups(
			organizations);

		return filterGroups(groups);
	}

	/**
	 * Returns the group directly associated with the user.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @return the group directly associated with the user
	 */
	@Override
	public Group getUserGroup(long companyId, long userId)
		throws PortalException {

		Group group = groupLocalService.getUserGroup(companyId, userId);

		GroupPermissionUtil.check(
			getPermissionChecker(), group, ActionKeys.VIEW);

		return group;
	}

	/**
	 * Returns the groups associated with the user groups.
	 *
	 * @param  userGroups the user groups
	 * @return the groups associated with the user groups
	 */
	@Override
	public List<Group> getUserGroupsGroups(List<UserGroup> userGroups)
		throws PortalException {

		List<Group> groups = groupLocalService.getUserGroupsGroups(userGroups);

		return filterGroups(groups);
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
	 * @param  userId the primary key of the user
	 * @param  start the lower bound of the range of groups to consider
	 * @param  end the upper bound of the range of groups to consider (not
	 *         inclusive)
	 * @return the range of groups associated with the user's organizations
	 */
	@Override
	public List<Group> getUserOrganizationsGroups(
			long userId, int start, int end)
		throws PortalException {

		List<Group> groups = groupLocalService.getUserOrganizationsGroups(
			userId, start, end);

		return filterGroups(groups);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getUserSitesGroups(long,
	 *             String[], int)}
	 */
	@Deprecated
	@Override
	public List<Group> getUserPlaces(
			long userId, String[] classNames, boolean includeControlPanel,
			int max)
		throws PortalException {

		return getUserSitesGroups(userId, classNames, max);
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
	 * @param      userId the primary key of the user
	 * @param      classNames the group entity class names (optionally
	 *             <code>null</code>). For more information see {@link
	 *             #getUserSitesGroups(long, String[], int)}.
	 * @param      max the maximum number of groups to return
	 * @return     the user's groups &quot;sites&quot;
	 * @deprecated As of 6.2.0, replaced by {@link #getUserSitesGroups(long,
	 *             String[], int)}
	 */
	@Deprecated
	@Override
	public List<Group> getUserPlaces(long userId, String[] classNames, int max)
		throws PortalException {

		return getUserSitesGroups(userId, classNames, max);
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
	 * @param      classNames the group entity class names (optionally
	 *             <code>null</code>). For more information see {@link
	 *             #getUserSitesGroups(String[], int)}.
	 * @param      max the maximum number of groups to return
	 * @return     the user's groups &quot;sites&quot;
	 * @deprecated As of 6.2.0, replaced by {@link #getUserSitesGroups(String[],
	 *             int)}
	 */
	@Deprecated
	@Override
	public List<Group> getUserPlaces(String[] classNames, int max)
		throws PortalException {

		return getUserSitesGroups(classNames, max);
	}

	/**
	 * Returns the number of the guest or current user's groups
	 * &quot;sites&quot; associated with the group entity class names, including
	 * the Control Panel group if the user is permitted to view the Control
	 * Panel.
	 *
	 * @return     the number of user's groups &quot;sites&quot;
	 * @deprecated As of 6.2.0, replaced by {@link #getUserSitesGroupsCount()}
	 */
	@Deprecated
	@Override
	public int getUserPlacesCount() throws PortalException {
		return getUserSitesGroupsCount();
	}

	/**
	 * Returns the guest or current user's layout set group, organization
	 * groups, inherited organization groups, and site groups.
	 *
	 * @return     the user's layout set group, organization groups, and
	 *             inherited organization groups, and site groups
	 * @deprecated As of 6.2.0, replaced by {@link #getUserSitesGroups}
	 */
	@Deprecated
	@Override
	public List<Group> getUserSites() throws PortalException {
		return getUserSitesGroups();
	}

	@Override
	public List<Group> getUserSitesGroups() throws PortalException {
		return getUserSitesGroups(null, QueryUtil.ALL_POS);
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
	 * @param  userId the primary key of the user
	 * @param  classNames the group entity class names (optionally
	 *         <code>null</code>). For more information see {@link
	 *         #getUserSitesGroups(long, String[], int)}.
	 * @param  max the maximum number of groups to return
	 * @return the user's groups &quot;sites&quot;
	 */
	@Override
	public List<Group> getUserSitesGroups(
			long userId, String[] classNames, int max)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return Collections.emptyList();
		}

		Set<Group> userSiteGroups = new LinkedHashSet<>();

		if (classNames == null) {
			classNames = new String[] {
				Company.class.getName(), Group.class.getName(),
				Organization.class.getName(), User.class.getName()
			};
		}

		if (ArrayUtil.contains(classNames, User.class.getName())) {
			if (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED ||
				PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {

				userSiteGroups.add(user.getGroup());

				if (userSiteGroups.size() == max) {
					return new ArrayList<>(userSiteGroups);
				}
			}
		}

		if (ArrayUtil.contains(classNames, Company.class.getName())) {
			userSiteGroups.add(
				groupLocalService.getCompanyGroup(user.getCompanyId()));

			if (userSiteGroups.size() == max) {
				return new ArrayList<>(userSiteGroups);
			}
		}

		if (ArrayUtil.contains(classNames, Group.class.getName()) ||
			ArrayUtil.contains(classNames, Organization.class.getName())) {

			UserBag userBag = UserBagFactoryUtil.create(userId);

			if (ArrayUtil.contains(classNames, Group.class.getName())) {
				for (Group group : userBag.getUserGroups()) {
					if (group.isActive() && group.isSite()) {
						if (userSiteGroups.add(group) &&
							(userSiteGroups.size() == max)) {

							return new ArrayList<>(userSiteGroups);
						}
					}
				}
			}

			if (ArrayUtil.contains(classNames, Organization.class.getName())) {
				for (Group group : userBag.getUserOrgGroups()) {
					if (group.isActive() && group.isSite()) {
						if (userSiteGroups.add(group) &&
							(userSiteGroups.size() == max)) {

							return new ArrayList<>(userSiteGroups);
						}
					}
				}
			}
		}

		return new ArrayList<>(userSiteGroups);
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
	 * @param  classNames the group entity class names (optionally
	 *         <code>null</code>). For more information see {@link
	 *         #getUserSitesGroups(long, String[], int)}.
	 * @param  max the maximum number of groups to return
	 * @return the user's groups &quot;sites&quot;
	 */
	@Override
	public List<Group> getUserSitesGroups(String[] classNames, int max)
		throws PortalException {

		return getUserSitesGroups(getGuestOrUserId(), classNames, max);
	}

	/**
	 * Returns the number of the guest or current user's groups
	 * &quot;sites&quot; associated with the group entity class names, including
	 * the Control Panel group if the user is permitted to view the Control
	 * Panel.
	 *
	 * @return the number of user's groups &quot;sites&quot;
	 */
	@Override
	public int getUserSitesGroupsCount() throws PortalException {
		List<Group> userSitesGroups = getUserSitesGroups(
			getGuestOrUserId(), null, QueryUtil.ALL_POS);

		return userSitesGroups.size();
	}

	/**
	 * Returns <code>true</code> if the user is associated with the group,
	 * including the user's inherited organizations and user groups. System and
	 * staged groups are not included.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return <code>true</code> if the user is associated with the group;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean hasUserGroup(long userId, long groupId)
		throws PortalException {

		try {
			UserPermissionUtil.check(
				getPermissionChecker(), userId, ActionKeys.VIEW);
		}
		catch (PrincipalException pe) {
			GroupPermissionUtil.check(
				getPermissionChecker(), groupId, ActionKeys.VIEW_MEMBERS);
		}

		return groupLocalService.hasUserGroup(userId, groupId);
	}

	@Override
	public List<Group> search(
			long companyId, long[] classNameIds, String keywords,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator<Group> obc)
		throws PortalException {

		List<Group> groups = groupLocalService.search(
			companyId, classNameIds, keywords, params, start, end, obc);

		return filterGroups(groups);
	}

	@Override
	public List<Group> search(
			long companyId, long[] classNameIds, String name,
			String description, LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end,
			OrderByComparator<Group> obc)
		throws PortalException {

		List<Group> groups = groupLocalService.search(
			companyId, classNameIds, name, description, params, andOperator,
			start, end, obc);

		return filterGroups(groups);
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
	 * @param  companyId the primary key of the company
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organizations and user groups in the
	 *         search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 */
	@Override
	public List<Group> search(
			long companyId, String name, String description, String[] params,
			int start, int end)
		throws PortalException {

		if (params == null) {
			params = new String[0];
		}

		LinkedHashMap<String, Object> paramsObj = MapUtil.toLinkedHashMap(
			params);

		List<Group> groups = groupLocalService.search(
			companyId, name, description, paramsObj, true, start, end);

		return filterGroups(groups);
	}

	/**
	 * Returns the number of groups and organization groups that match the name
	 * and description, optionally including the user's inherited organizations
	 * and user groups. System and staged groups are not included.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organizations and user groups in the
	 *         search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @return the number of matching groups
	 */
	@Override
	public int searchCount(
		long companyId, String name, String description, String[] params) {

		if (params == null) {
			params = new String[0];
		}

		LinkedHashMap<String, Object> paramsObj = MapUtil.toLinkedHashMap(
			params);

		return groupLocalService.searchCount(
			companyId, name, description, paramsObj, true);
	}

	/**
	 * Sets the groups associated with the role, removing and adding
	 * associations as necessary.
	 *
	 * @param roleId the primary key of the role
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void setRoleGroups(long roleId, long[] groupIds)
		throws PortalException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

		groupLocalService.setRoleGroups(roleId, groupIds);
	}

	/**
	 * Removes the groups from the role.
	 *
	 * @param roleId the primary key of the role
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void unsetRoleGroups(long roleId, long[] groupIds)
		throws PortalException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

		groupLocalService.unsetRoleGroups(roleId, groupIds);
	}

	/**
	 * Updates the group's friendly URL.
	 *
	 * @param  groupId the primary key of the group
	 * @param  friendlyURL the group's new friendlyURL (optionally
	 *         <code>null</code>)
	 * @return the group
	 */
	@Override
	public Group updateFriendlyURL(long groupId, String friendlyURL)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		return groupLocalService.updateFriendlyURL(groupId, friendlyURL);
	}

	@Override
	public Group updateGroup(
			long groupId, long parentGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean inheritContent, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		GroupPermissionUtil.check(
			getPermissionChecker(), group, ActionKeys.UPDATE);

		if (group.getParentGroupId() != parentGroupId) {
			if (parentGroupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
				PortalPermissionUtil.check(
					getPermissionChecker(), ActionKeys.ADD_COMMUNITY);
			}
			else {
				GroupPermissionUtil.check(
					getPermissionChecker(), parentGroupId,
					ActionKeys.ADD_COMMUNITY);
			}
		}

		if (group.isSite()) {
			Group oldGroup = group;

			List<AssetCategory> oldAssetCategories =
				assetCategoryLocalService.getCategories(
					Group.class.getName(), groupId);

			List<AssetTag> oldAssetTags = assetTagLocalService.getTags(
				Group.class.getName(), groupId);

			ExpandoBridge oldExpandoBridge = oldGroup.getExpandoBridge();

			Map<String, Serializable> oldExpandoAttributes =
				oldExpandoBridge.getAttributes();

			group = groupLocalService.updateGroup(
				groupId, parentGroupId, nameMap, descriptionMap, type,
				manualMembership, membershipRestriction, friendlyURL,
				inheritContent, active, serviceContext);

			SiteMembershipPolicyUtil.verifyPolicy(
				group, oldGroup, oldAssetCategories, oldAssetTags,
				oldExpandoAttributes, null);

			return group;
		}
		else {
			return groupLocalService.updateGroup(
				groupId, parentGroupId, nameMap, descriptionMap, type,
				manualMembership, membershipRestriction, friendlyURL,
				inheritContent, active, serviceContext);
		}
	}

	/**
	 * Updates the group.
	 *
	 * @param      groupId the primary key of the group
	 * @param      parentGroupId the primary key of the parent group
	 * @param      name the group's name
	 * @param      description the group's new description (optionally
	 *             <code>null</code>)
	 * @param      type the group's new type. For more information see {@link
	 *             GroupConstants}.
	 * @param      manualMembership whether manual membership is allowed for the
	 *             group
	 * @param      membershipRestriction the group's membership restriction. For
	 *             more information see {@link GroupConstants}.
	 * @param      friendlyURL the group's new friendlyURL (optionally
	 *             <code>null</code>)
	 * @param      active whether the group is active
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set the asset category IDs and asset
	 *             tag names for the group.
	 * @return     the group
	 * @deprecated As of 7.0.0, replaced by {@link #updateGroup(long, long, Map,
	 *             Map, int, boolean, int, String, boolean, boolean,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public Group updateGroup(
			long groupId, long parentGroupId, String name, String description,
			int type, boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean inheritContent, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return updateGroup(
			groupId, parentGroupId, getLocalizationMap(name),
			getLocalizationMap(description), type, manualMembership,
			membershipRestriction, friendlyURL, inheritContent, active,
			serviceContext);
	}

	/**
	 * Updates the group's type settings.
	 *
	 * @param  groupId the primary key of the group
	 * @param  typeSettings the group's new type settings (optionally
	 *         <code>null</code>)
	 * @return the group
	 */
	@Override
	public Group updateGroup(long groupId, String typeSettings)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		GroupPermissionUtil.check(
			getPermissionChecker(), group, ActionKeys.UPDATE);

		if (group.isSite()) {
			Group oldGroup = group;

			UnicodeProperties oldTypeSettingsProperties =
				oldGroup.getTypeSettingsProperties();

			group = groupLocalService.updateGroup(groupId, typeSettings);

			RatingsDataTransformerUtil.transformGroupRatingsData(
				groupId, oldTypeSettingsProperties,
				group.getTypeSettingsProperties());

			SiteMembershipPolicyUtil.verifyPolicy(
				group, oldGroup, null, null, null, oldTypeSettingsProperties);

			return group;
		}
		else {
			return groupLocalService.updateGroup(groupId, typeSettings);
		}
	}

	@Override
	public void updateStagedPortlets(
			long groupId, Map<String, String> stagedPortletIds)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		GroupPermissionUtil.check(
			getPermissionChecker(), group, ActionKeys.UPDATE);

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		for (String stagedPortletId : stagedPortletIds.keySet()) {
			typeSettingsProperties.setProperty(
				StagingUtil.getStagedPortletId(stagedPortletId),
				stagedPortletIds.get(stagedPortletId));
		}

		groupLocalService.updateGroup(group);
	}

	protected List<Group> filterGroups(List<Group> groups)
		throws PortalException {

		List<Group> filteredGroups = new ArrayList<>();

		for (Group group : groups) {
			if (GroupPermissionUtil.contains(
					getPermissionChecker(), group, ActionKeys.VIEW)) {

				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

	protected Map<Locale, String> getLocalizationMap(String value) {
		Map<Locale, String> map = new HashMap<>();

		map.put(LocaleUtil.getDefault(), value);

		return map;
	}

}
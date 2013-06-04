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

package com.liferay.portal.service.impl;

import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.GroupNameException;
import com.liferay.portal.GroupParentException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.RequiredGroupException;
import com.liferay.portal.kernel.cache.ThreadLocalCachable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.spring.aop.Skip;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserPersonalSite;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.GroupLocalServiceBaseImpl;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletCategoryKeys;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.GroupNameComparator;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.journal.model.JournalArticle;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * groups. Groups are mostly used in Liferay as a resource container for
 * permissioning and content scoping purposes.
 *
 * <p>
 * Groups are also the entity to which LayoutSets are generally associated.
 * Since LayoutSets are the parent entities of Layouts (i.e. pages), no entity
 * can have associated pages without also having an associated Group. This
 * relationship can be depicted as ... Layout -> LayoutSet -> Group[type] [->
 * Entity]. Note, the Entity part is optional.
 * </p>
 *
 * <p>
 * Group has a "type" definition that is typically identified by two fields of
 * the entity - <code>String className</code>, and <code>int type </code>.
 * </p>
 *
 * <p>
 * The <code>className</code> field helps create the group's association with
 * other entities (e.g. Organization, User, Company, UserGroup, ... etc.). The
 * value of <code>className</code> is the full name of the entity's class and
 * the primary key of the associated entity instance. A site has
 * <code>className="Group"</code> and has no associated entity.
 * </p>
 *
 * <p>
 * The <code>type</code> field helps distinguish between a group used strictly
 * for scoping and a group that also has pages (in which case the type is
 * <code>SITE</code>). For a list of types, see {@link
 * com.liferay.portal.model.GroupConstants}.
 * </p>
 *
 * <p>
 * Here is a listing of how Group is related to some portal entities ...
 * </p>
 *
 * <ul>
 * <li>
 * Site is a Group with <code>className="Group"</code>
 * </li>
 * <li>
 * Company has 1 Group (this is the global scope, but never has pages)
 * </li>
 * <li>
 * User has 1 Group (pages are optional based on the behavior configuration for
 * personal pages)
 * </li>
 * <li>
 * Layout Template (<code>LayoutPrototype</code>) has 1 Group which uses only 1
 * of it's 2 LayoutSets to store a single page which can later be used to
 * derive a single page in any Site
 * </li>
 * <li>
 * Site Template (<code>LayoutSetPrototype</code>) has 1 Group which uses only
 * 1 of it's 2 LayoutSets to store many pages which can later be used to derive
 * entire Sites or pulled into an existing Site
 * </li>
 * <li>
 * Organization has 1 Group, but can also be associated to a Site at any point
 * in it's life cycle in order to support having pages
 * </li>
 * <li>
 * UserGroup has 1 Group that can have pages in both of the group's LayoutSets
 * which are later inherited by users assigned to the UserGroup
 * </li>
 * </ul>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Bruno Farache
 * @author Wesley Gong
 * @see    com.liferay.portal.model.impl.GroupImpl
 */
public class GroupLocalServiceImpl extends GroupLocalServiceBaseImpl {

	public static final String ORGANIZATION_NAME_SUFFIX = " LFR_ORGANIZATION";

	/**
	 * Constructs a group local service.
	 */
	public GroupLocalServiceImpl() {
		initImportLARFile();
	}

	/**
	 * Adds a group.
	 *
	 * @param  userId the primary key of the group's creator/owner
	 * @param  parentGroupId the primary key of the parent group
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity's instance
	 * @param  liveGroupId the primary key of the live group
	 * @param  name the entity's name
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  type the group's type. For more information see {@link
	 *         com.liferay.portal.model.GroupConstants}
	 * @param  friendlyURL the group's friendlyURL (optionally
	 *         <code>null</code>)
	 * @param  site whether the group is to be associated with a main site
	 * @param  active whether the group is active
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set asset category IDs and asset tag
	 *         names for the group, and whether the group is for staging.
	 * @return the group
	 * @throws PortalException if a creator could not be found, if the group's
	 *         information was invalid, if a layout could not be found, or if a
	 *         valid friendly URL could not be created for the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group addGroup(
			long userId, long parentGroupId, String className, long classPK,
			long liveGroupId, String name, String description, int type,
			String friendlyURL, boolean site, boolean active,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Group

		User user = userPersistence.findByPrimaryKey(userId);
		className = GetterUtil.getString(className);
		long classNameId = PortalUtil.getClassNameId(className);
		String friendlyName = name;

		long groupId = 0;

		while (true) {
			groupId = counterLocalService.increment();

			User screenNameUser = userPersistence.fetchByC_SN(
				user.getCompanyId(), String.valueOf(groupId));

			if (screenNameUser == null) {
				break;
			}
		}

		boolean staging = isStaging(serviceContext);

		long groupClassNameId = PortalUtil.getClassNameId(Group.class);

		if ((classNameId <= 0) || className.equals(Group.class.getName())) {
			className = Group.class.getName();
			classNameId = groupClassNameId;
			classPK = groupId;
		}
		else if (className.equals(Organization.class.getName())) {
			name = getOrgGroupName(name);
		}
		else if (!GroupConstants.USER_PERSONAL_SITE.equals(name)) {
			name = String.valueOf(classPK);
		}

		if (className.equals(Organization.class.getName()) && staging) {
			classPK = liveGroupId;
		}

		if (className.equals(Layout.class.getName())) {
			Layout layout = layoutLocalService.getLayout(classPK);

			parentGroupId = layout.getGroupId();
		}

		friendlyURL = getFriendlyURL(
			user.getCompanyId(), groupId, classNameId, classPK, friendlyName,
			friendlyURL);

		if (staging) {
			name = name.concat(" (Staging)");
			friendlyURL = friendlyURL.concat("-staging");
		}

		if (className.equals(Group.class.getName())) {
			if (!site && (liveGroupId == 0) &&
				!name.equals(GroupConstants.CONTROL_PANEL)) {

				throw new IllegalArgumentException();
			}
		}
		else if (!className.equals(Company.class.getName()) &&
				 !className.equals(Organization.class.getName()) &&
				 className.startsWith("com.liferay.portal.model.")) {

			if (site) {
				throw new IllegalArgumentException();
			}
		}

		if ((classNameId <= 0) || className.equals(Group.class.getName())) {
			validateName(groupId, user.getCompanyId(), name, site);
		}

		validateFriendlyURL(
			user.getCompanyId(), groupId, classNameId, classPK, friendlyURL);

		validateParentGroup(groupId, parentGroupId, type);

		Group group = groupPersistence.create(groupId);

		if (serviceContext != null) {
			group.setUuid(serviceContext.getUuid());
		}

		group.setCompanyId(user.getCompanyId());
		group.setCreatorUserId(userId);
		group.setClassNameId(classNameId);
		group.setClassPK(classPK);
		group.setParentGroupId(parentGroupId);
		group.setLiveGroupId(liveGroupId);
		group.setTreePath(group.buildTreePath());
		group.setName(name);
		group.setDescription(description);
		group.setType(type);
		group.setFriendlyURL(friendlyURL);
		group.setSite(site);
		group.setActive(active);

		if ((serviceContext != null) && (classNameId == groupClassNameId) &&
			!user.isDefaultUser()) {

			group.setExpandoBridgeAttributes(serviceContext);
		}

		groupPersistence.update(group);

		// Layout sets

		layoutSetLocalService.addLayoutSet(groupId, true);

		layoutSetLocalService.addLayoutSet(groupId, false);

		if ((classNameId == groupClassNameId) && !user.isDefaultUser()) {

			// Resources

			resourceLocalService.addResources(
				group.getCompanyId(), 0, 0, Group.class.getName(),
				group.getGroupId(), false, false, false);

			// Site roles

			Role role = roleLocalService.getRole(
				group.getCompanyId(), RoleConstants.SITE_OWNER);

			userGroupRoleLocalService.addUserGroupRoles(
				userId, groupId, new long[] {role.getRoleId()});

			// User

			userLocalService.addGroupUsers(
				group.getGroupId(), new long[] {userId});

			// Asset

			if (serviceContext != null) {
				updateAsset(
					userId, group, serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames());
			}
		}
		else if (className.equals(Organization.class.getName()) &&
				 !user.isDefaultUser()) {

			// Resources

			resourceLocalService.addResources(
				group.getCompanyId(), 0, 0, Group.class.getName(),
				group.getGroupId(), false, false, false);
		}

		return group;
	}

	/**
	 * Adds the group using the default live group.
	 *
	 * @param      userId the primary key of the group's creator/owner
	 * @param      parentGroupId the primary key of the parent group
	 * @param      className the entity's class name
	 * @param      classPK the primary key of the entity's instance
	 * @param      name the entity's name
	 * @param      description the group's description (optionally
	 *             <code>null</code>)
	 * @param      type the group's type. For more information see {@link
	 *             com.liferay.portal.model.GroupConstants}
	 * @param      friendlyURL the group's friendlyURL
	 * @param      site whether the group is to be associated with a main site
	 * @param      active whether the group is active
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs and asset tag
	 *             names for the group, and whether the group is for staging.
	 * @return     the group
	 * @throws     PortalException if a creator could not be found, if the
	 *             group's information was invalid, if a layout could not be
	 *             found, or if a valid friendly URL could not be created for
	 *             the group
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addGroup(long, long, String,
	 *             long, long, String, String, int, String, boolean, boolean,
	 *             ServiceContext)}
	 */
	@Override
	public Group addGroup(
			long userId, long parentGroupId, String className, long classPK,
			String name, String description, int type, String friendlyURL,
			boolean site, boolean active, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addGroup(
			userId, parentGroupId, className, classPK,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name, description, type,
			friendlyURL, site, active, serviceContext);
	}

	/**
	 * Adds a group.
	 *
	 * @param      userId the primary key of the group's creator/owner
	 * @param      className the entity's class name
	 * @param      classPK the primary key of the entity's instance
	 * @param      liveGroupId the primary key of the live group
	 * @param      name the entity's name
	 * @param      description the group's description (optionally
	 *             <code>null</code>)
	 * @param      type the group's type. For more information see {@link
	 *             com.liferay.portal.model.GroupConstants}
	 * @param      friendlyURL the group's friendlyURL (optionally
	 *             <code>null</code>)
	 * @param      site whether the group is to be associated with a main site
	 * @param      active whether the group is active
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs and asset tag
	 *             names for the group, and whether the group is for staging.
	 * @return     the group
	 * @throws     PortalException if a creator could not be found, if the
	 *             group's information was invalid, if a layout could not be
	 *             found, or if a valid friendly URL could not be created for
	 *             the group
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addGroup(long, long, String,
	 *             long, long, String, String, int, String, boolean, boolean,
	 *             ServiceContext)}
	 */
	@Override
	public Group addGroup(
			long userId, String className, long classPK, long liveGroupId,
			String name, String description, int type, String friendlyURL,
			boolean site, boolean active, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, className, classPK,
			liveGroupId, name, description, type, friendlyURL, site, active,
			serviceContext);
	}

	/**
	 * Adds the group using the default live group.
	 *
	 * @param      userId the primary key of the group's creator/owner
	 * @param      className the entity's class name
	 * @param      classPK the primary key of the entity's instance
	 * @param      name the entity's name
	 * @param      description the group's description (optionally
	 *             <code>null</code>)
	 * @param      type the group's type. For more information see {@link
	 *             com.liferay.portal.model.GroupConstants}
	 * @param      friendlyURL the group's friendlyURL
	 * @param      site whether the group is to be associated with a main site
	 * @param      active whether the group is active
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs and asset tag
	 *             names for the group, and whether the group is for staging.
	 * @return     the group
	 * @throws     PortalException if a creator could not be found, if the
	 *             group's information was invalid, if a layout could not be
	 *             found, or if a valid friendly URL could not be created for
	 *             the group
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addGroup(long, long, String,
	 *             long, long, String, String, int, String, boolean, boolean,
	 *             ServiceContext)}
	 */
	@Override
	public Group addGroup(
			long userId, String className, long classPK, String name,
			String description, int type, String friendlyURL, boolean site,
			boolean active, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, className, classPK,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name, description, type,
			friendlyURL, site, active, serviceContext);
	}

	/**
	 * Adds the groups to the role.
	 *
	 * @param  roleId the primary key of the role
	 * @param  groupIds the primary keys of the groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addRoleGroups(long roleId, long[] groupIds)
		throws SystemException {

		rolePersistence.addGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Adds the user to the groups.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupIds the primary keys of the groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addUserGroups(long userId, long[] groupIds)
		throws SystemException {

		userPersistence.addGroups(userId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Adds a company group if it does not exist. This method is typically used
	 * when a virtual host is added.
	 *
	 * @param  companyId the primary key of the company
	 * @throws PortalException if a default user for the company could not be
	 *         found, if the group's information was invalid, if a layout could
	 *         not be found, or if a valid friendly URL could not be created for
	 *         the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkCompanyGroup(long companyId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(Company.class);

		int count = groupPersistence.countByC_C_C(
			companyId, classNameId, companyId);

		if (count == 0) {
			long defaultUserId = userLocalService.getDefaultUserId(companyId);

			groupLocalService.addGroup(
				defaultUserId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
				Company.class.getName(), companyId,
				GroupConstants.DEFAULT_LIVE_GROUP_ID, GroupConstants.GLOBAL,
				null, 0, GroupConstants.GLOBAL_FRIENDLY_URL, true, true, null);
		}
	}

	/**
	 * Creates systems groups and other related data needed by the system on the
	 * very first startup. Also takes care of creating the Control Panel groups
	 * and layouts.
	 *
	 * @param  companyId the primary key of the company
	 * @throws PortalException if a new system group could not be created
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkSystemGroups(long companyId)
		throws PortalException, SystemException {

		String companyIdHexString = StringUtil.toHexString(companyId);

		for (Group group : groupFinder.findBySystem(companyId)) {
			_systemGroupsMap.put(
				companyIdHexString.concat(group.getName()), group);
		}

		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		String[] systemGroups = PortalUtil.getSystemGroups();

		for (String name : systemGroups) {
			String groupCacheKey = companyIdHexString.concat(name);

			Group group = _systemGroupsMap.get(groupCacheKey);

			if (group == null) {
				group = groupPersistence.fetchByC_N(companyId, name);
			}

			if (group == null) {
				String className = null;
				long classPK = 0;
				int type = GroupConstants.TYPE_SITE_OPEN;
				String friendlyURL = null;
				boolean site = true;

				if (name.equals(GroupConstants.CONTROL_PANEL)) {
					type = GroupConstants.TYPE_SITE_PRIVATE;
					friendlyURL = GroupConstants.CONTROL_PANEL_FRIENDLY_URL;
					site = false;
				}
				else if (name.equals(GroupConstants.GUEST)) {
					friendlyURL = "/guest";
				}
				else if (name.equals(GroupConstants.USER_PERSONAL_SITE)) {
					className = UserPersonalSite.class.getName();
					classPK = defaultUserId;
					type = GroupConstants.TYPE_SITE_PRIVATE;
					friendlyURL =
						GroupConstants.USER_PERSONAL_SITE_FRIENDLY_URL;
					site = false;
				}

				group = groupLocalService.addGroup(
					defaultUserId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
					className, classPK, GroupConstants.DEFAULT_LIVE_GROUP_ID,
					name, null, type, friendlyURL, site, true, null);

				if (name.equals(GroupConstants.USER_PERSONAL_SITE)) {
					initUserPersonalSitePermissions(group);
				}
			}

			if (group.isControlPanel()) {
				LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(
					group.getGroupId(), true);

				if (layoutSet.getPageCount() == 0) {
					addControlPanelLayouts(group);
				}
			}

			if (group.getName().equals(GroupConstants.GUEST)) {
				LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(
					group.getGroupId(), false);

				if (layoutSet.getPageCount() == 0) {
					addDefaultGuestPublicLayouts(group);
				}
			}

			_systemGroupsMap.put(groupCacheKey, group);
		}
	}

	/**
	 * Deletes the group and its associated data.
	 *
	 * <p>
	 * The group is unstaged and its assets and resources including layouts,
	 * membership requests, subscriptions, teams, blogs, bookmarks, calendar
	 * events, image gallery, journals, message boards, polls, shopping related
	 * entities, software catalog, and wikis are also deleted.
	 * </p>
	 *
	 * @param  group the group
	 * @return the deleted group
	 * @throws PortalException if the group was a system group, or if the user
	 *         did not have permission to delete the group or its assets or its
	 *         resources
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group deleteGroup(Group group)
		throws PortalException, SystemException {

		if (group.isCompany() || PortalUtil.isSystemGroup(group.getName())) {
			throw new RequiredGroupException(
				String.valueOf(group.getGroupId()),
				RequiredGroupException.SYSTEM_GROUP);
		}

		if (groupPersistence.countByC_P_S(
				group.getCompanyId(), group.getGroupId(), true) > 0) {

			throw new RequiredGroupException(
				String.valueOf(group.getGroupId()),
				RequiredGroupException.PARENT_GROUP);
		}

		// Layout set branches

		layoutSetBranchLocalService.deleteLayoutSetBranches(
			group.getGroupId(), true, true);

		layoutSetBranchLocalService.deleteLayoutSetBranches(
			group.getGroupId(), false, true);

		// Layout sets

		ServiceContext serviceContext = new ServiceContext();

		try {
			layoutSetLocalService.deleteLayoutSet(
				group.getGroupId(), true, serviceContext);
		}
		catch (NoSuchLayoutSetException nslse) {
		}

		try {
			layoutSetLocalService.deleteLayoutSet(
				group.getGroupId(), false, serviceContext);
		}
		catch (NoSuchLayoutSetException nslse) {
		}

		// Group roles

		userGroupRoleLocalService.deleteUserGroupRolesByGroupId(
			group.getGroupId());

		// User group roles

		userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByGroupId(
			group.getGroupId());

		// Membership requests

		membershipRequestLocalService.deleteMembershipRequests(
			group.getGroupId());

		// Portlet preferences

		portletPreferencesLocalService.deletePortletPreferences(
			group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
			PortletKeys.PREFS_PLID_SHARED);

		// Repositories

		repositoryLocalService.deleteRepositories(group.getGroupId());

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			group.getCompanyId(), BlogsEntry.class.getName(),
			group.getGroupId());
		subscriptionLocalService.deleteSubscriptions(
			group.getCompanyId(), JournalArticle.class.getName(),
			group.getGroupId());

		// Teams

		teamLocalService.deleteTeams(group.getGroupId());

		// Staging

		unscheduleStaging(group);

		if (group.hasStagingGroup()) {
			try {
				StagingUtil.disableStaging(group, serviceContext);
			}
			catch (Exception e) {
				_log.error(
					"Unable to disable staging for group " +
						group.getGroupId());
			}
		}

		// Themes

		ThemeLoader themeLoader = ThemeLoaderFactory.getDefaultThemeLoader();

		if (themeLoader != null) {
			String themePath =
				themeLoader.getFileStorage() + StringPool.SLASH +
					group.getGroupId();

			FileUtil.deltree(themePath + "-private");
			FileUtil.deltree(themePath + "-public");
		}

		// Portlet data

		deletePortletData(group);

		// Asset

		if (group.isRegularSite()) {
			assetEntryLocalService.deleteEntry(
				Group.class.getName(), group.getGroupId());
		}

		assetVocabularyLocalService.deleteVocabularies(group.getGroupId());

		// Shopping

		shoppingCartLocalService.deleteGroupCarts(group.getGroupId());
		shoppingCategoryLocalService.deleteCategories(group.getGroupId());
		shoppingCouponLocalService.deleteCoupons(group.getGroupId());
		shoppingOrderLocalService.deleteOrders(group.getGroupId());

		// Social

		socialActivityLocalService.deleteActivities(group.getGroupId());
		socialActivitySettingLocalService.deleteActivitySettings(
			group.getGroupId());

		// Software catalog

		scFrameworkVersionLocalService.deleteFrameworkVersions(
			group.getGroupId());
		scProductEntryLocalService.deleteProductEntries(group.getGroupId());

		// Resources

		List<ResourcePermission> resourcePermissions =
			resourcePermissionPersistence.findByC_LikeP(
				group.getCompanyId(), String.valueOf(group.getGroupId()));

		for (ResourcePermission resourcePermission : resourcePermissions) {
			resourcePermissionLocalService.deleteResourcePermission(
				resourcePermission);
		}

		if (!group.isStagingGroup() &&
			(group.isOrganization() || group.isRegularSite())) {

			resourceLocalService.deleteResource(
				group.getCompanyId(), Group.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, group.getGroupId());
		}

		// Group

		if (!group.isStagingGroup() && group.isOrganization() &&
			group.isSite()) {

			group.setSite(false);

			groupPersistence.update(group);
		}
		else {
			groupPersistence.remove(group);
		}

		// Permission cache

		PermissionCacheUtil.clearCache();

		return group;
	}

	/**
	 * Deletes the group and its associated data.
	 *
	 * <p>
	 * The group is unstaged and its assets and resources including layouts,
	 * membership requests, subscriptions, teams, blogs, bookmarks, calendar
	 * events, image gallery, journals, message boards, polls, shopping related
	 * entities, software catalog, and wikis are also deleted.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @return the deleted group
	 * @throws PortalException if a group with the primary key could not be
	 *         found, if the group was a system group, or if the user did not
	 *         have permission to delete the group, its assets, or its resources
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group deleteGroup(long groupId)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		return deleteGroup(group);
	}

	/**
	 * Returns the group with the matching friendly URL.
	 *
	 * @param  companyId the primary key of the company
	 * @param  friendlyURL the friendly URL
	 * @return the group with the friendly URL, or <code>null</code> if a
	 *         matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchFriendlyURLGroup(long companyId, String friendlyURL)
		throws SystemException {

		if (Validator.isNull(friendlyURL)) {
			return null;
		}

		friendlyURL = getFriendlyURL(friendlyURL);

		return groupPersistence.fetchByC_F(companyId, friendlyURL);
	}

	/**
	 * Returns the group with the matching group name by first searching the
	 * system groups and then using the finder cache.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the group's name
	 * @return the group with the name and associated company, or
	 *         <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@Skip
	public Group fetchGroup(long companyId, String name)
		throws SystemException {

		Group group = _systemGroupsMap.get(
			StringUtil.toHexString(companyId).concat(name));

		if (group != null) {
			return group;
		}

		return groupLocalService.loadFetchGroup(companyId, name);
	}

	/**
	 * Returns the group with the matching UUID and company.
	 *
	 * @param  uuid the UUID
	 * @param  companyId the primary key of the company
	 * @return the matching group, or <code>null</code> if a matching group
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchGroupByUuidandCompanyId(String uuid, long companyId)
		throws SystemException {

		return groupPersistence.fetchByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns the company group.
	 *
	 * @param  companyId the primary key of the company
	 * @return the group associated with the company
	 * @throws PortalException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group getCompanyGroup(long companyId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(Company.class);

		return groupPersistence.findByC_C_C(companyId, classNameId, companyId);
	}

	/**
	 * Returns a range of all the groups associated with the company.
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
	 * @param  companyId the primary key of the company
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the range of groups associated with the company
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getCompanyGroups(long companyId, int start, int end)
		throws SystemException {

		return groupPersistence.findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns the number of groups associated with the company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the number of groups associated with the company
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getCompanyGroupsCount(long companyId) throws SystemException {
		return groupPersistence.countByCompanyId(companyId);
	}

	/**
	 * Returns the group with the matching friendly URL.
	 *
	 * @param  companyId the primary key of the company
	 * @param  friendlyURL the group's friendlyURL
	 * @return the group with the friendly URL
	 * @throws PortalException if a matching group could not be found, or if the
	 *         friendly URL was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group getFriendlyURLGroup(long companyId, String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			throw new NoSuchGroupException();
		}

		friendlyURL = getFriendlyURL(friendlyURL);

		return groupPersistence.findByC_F(companyId, friendlyURL);
	}

	/**
	 * Returns the group with the matching primary key.
	 *
	 * @param  groupId the primary key of the group
	 * @return the group with the primary key
	 * @throws PortalException if a group with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@ThreadLocalCachable
	public Group getGroup(long groupId)
		throws PortalException, SystemException {

		return groupPersistence.findByPrimaryKey(groupId);
	}

	/**
	 * Returns the group with the matching group name.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the group's name
	 * @return the group with the name
	 * @throws PortalException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@Skip
	public Group getGroup(long companyId, String name)
		throws PortalException, SystemException {

		Group group = _systemGroupsMap.get(
			StringUtil.toHexString(companyId).concat(name));

		if (group != null) {
			return group;
		}

		return groupLocalService.loadGetGroup(companyId, name);
	}

	@Override
	public String getGroupDescriptiveName(Group group, Locale locale)
		throws PortalException, SystemException {

		String name = group.getName();

		if (group.isCompany()) {
			name = LanguageUtil.get(locale, "global");
		}
		else if (group.isControlPanel()) {
			name = LanguageUtil.get(locale, "control-panel");
		}
		else if (group.isLayout()) {
			Layout layout = layoutLocalService.getLayout(group.getClassPK());

			name = layout.getName(locale);
		}
		else if (group.isLayoutPrototype()) {
			LayoutPrototype layoutPrototype =
				layoutPrototypeLocalService.getLayoutPrototype(
					group.getClassPK());

			name = layoutPrototype.getName(locale);
		}
		else if (group.isLayoutSetPrototype()) {
			LayoutSetPrototype layoutSetPrototype =
				layoutSetPrototypePersistence.findByPrimaryKey(
					group.getClassPK());

			name = layoutSetPrototype.getName(locale);
		}
		else if (group.isOrganization()) {
			long organizationId = group.getOrganizationId();

			Organization organization =
				organizationPersistence.findByPrimaryKey(organizationId);

			name = organization.getName();

			Group organizationGroup = organization.getGroup();

			if (organizationGroup.isStaged() && group.isStagingGroup()) {
				name = name + " (" + LanguageUtil.get(locale, "staging") + ")";
			}
		}
		else if (group.isUser()) {
			long userId = group.getClassPK();

			User user = userPersistence.findByPrimaryKey(userId);

			name = user.getFullName();
		}
		else if (group.isUserGroup()) {
			long userGroupId = group.getClassPK();

			UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
				userGroupId);

			name = userGroup.getName();
		}
		else if (group.isUserPersonalSite()) {
			name = LanguageUtil.get(locale, "user-personal-site");
		}
		else if (name.equals(GroupConstants.GUEST)) {
			Company company = companyPersistence.findByPrimaryKey(
				group.getCompanyId());

			Account account = company.getAccount();

			name = account.getName();
		}

		return name;
	}

	@Override
	public String getGroupDescriptiveName(long groupId, Locale locale)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		return getGroupDescriptiveName(group, locale);
	}

	/**
	 * Returns all the groups that are direct children of the parent group.
	 *
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  site whether the group is to be associated with a main site
	 * @return the matching groups, or <code>null</code> if no matches were
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getGroups(
			long companyId, long parentGroupId, boolean site)
		throws SystemException {

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			return groupPersistence.findByC_S(companyId, site);
		}

		return groupPersistence.findByC_P_S(companyId, parentGroupId, site);
	}

	/**
	 * Returns all the groups that are direct children of the parent group with
	 * the matching className.
	 *
	 * @param  companyId the primary key of the company
	 * @param  className the class name of the group
	 * @param  parentGroupId the primary key of the parent group
	 * @return the matching groups, or <code>null</code> if no matches were
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getGroups(
			long companyId, String className, long parentGroupId)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return groupPersistence.findByC_C_P(
			companyId, classNameId, parentGroupId);
	}

	/**
	 * Returns a range of all the groups that are direct children of the parent
	 * group with the matching className.
	 *
	 * @param  companyId the primary key of the company
	 * @param  className the class name of the group
	 * @param  parentGroupId the primary key of the parent group
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getGroups(
			long companyId, String className, long parentGroupId, int start,
			int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return groupPersistence.findByC_C_P(
			companyId, classNameId, parentGroupId, start, end);
	}

	/**
	 * Returns the groups with the matching primary keys.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @return the groups with the primary keys
	 * @throws PortalException if any one of the groups could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getGroups(long[] groupIds)
		throws PortalException, SystemException {

		List<Group> groups = new ArrayList<Group>(groupIds.length);

		for (long groupId : groupIds) {
			Group group = getGroup(groupId);

			groups.add(group);
		}

		return groups;
	}

	/**
	 * Returns the number of groups that are direct children of the parent
	 * group.
	 *
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  site whether the group is to be associated with a main site
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getGroupsCount(long companyId, long parentGroupId, boolean site)
		throws SystemException {

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			return groupPersistence.countByC_S(companyId, site);
		}

		return groupPersistence.countByC_P_S(companyId, parentGroupId, site);
	}

	/**
	 * Returns the number of groups that are direct children of the parent group
	 * with the matching className.
	 *
	 * @param  companyId the primary key of the company
	 * @param  className the class name of the group
	 * @param  parentGroupId the primary key of the parent group
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getGroupsCount(
			long companyId, String className, long parentGroupId)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return groupPersistence.countByC_C_P(
			companyId, classNameId, parentGroupId);
	}

	/**
	 * Returns the group associated with the layout.
	 *
	 * @param  companyId the primary key of the company
	 * @param  plid the primary key of the layout
	 * @return the group associated with the layout
	 * @throws PortalException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group getLayoutGroup(long companyId, long plid)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(Layout.class);

		return groupPersistence.findByC_C_C(companyId, classNameId, plid);
	}

	/**
	 * Returns the group associated with the layout prototype.
	 *
	 * @param  companyId the primary key of the company
	 * @param  layoutPrototypeId the primary key of the layout prototype
	 * @return the group associated with the layout prototype
	 * @throws PortalException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group getLayoutPrototypeGroup(long companyId, long layoutPrototypeId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(LayoutPrototype.class);

		return groupPersistence.findByC_C_C(
			companyId, classNameId, layoutPrototypeId);
	}

	/**
	 * Returns the group associated with the layout set prototype.
	 *
	 * @param  companyId the primary key of the company
	 * @param  layoutSetPrototypeId the primary key of the layout set prototype
	 * @return the group associated with the layout set prototype
	 * @throws PortalException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group getLayoutSetPrototypeGroup(
			long companyId, long layoutSetPrototypeId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(LayoutSetPrototype.class);

		return groupPersistence.findByC_C_C(
			companyId, classNameId, layoutSetPrototypeId);
	}

	/**
	 * Returns a range of all groups that are children of the parent group and
	 * that have at least one layout.
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
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  site whether the group is to be associated with a main site
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getLayoutsGroups(
			long companyId, long parentGroupId, boolean site, int start,
			int end)
		throws SystemException {

		return groupFinder.findByLayouts(
			companyId, parentGroupId, site, start, end);
	}

	/**
	 * Returns the number of groups that are children or the parent group and
	 * that have at least one layout
	 *
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  site whether the group is to be associated with a main site
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getLayoutsGroupsCount(
			long companyId, long parentGroupId, boolean site)
		throws SystemException {

		return groupFinder.countByLayouts(companyId, parentGroupId, site);
	}

	/**
	 * Returns all live groups.
	 *
	 * @return all live groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getLiveGroups() throws SystemException {
		return groupFinder.findByLiveGroups();
	}

	/**
	 * Returns a range of all non-system groups of a specified type (className)
	 * that have no layouts.
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
	 * @param  className the entity's class name
	 * @param  privateLayout whether to include groups with private layout sets
	 *         or non-private layout sets
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getNoLayoutsGroups(
			String className, boolean privateLayout, int start, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return groupFinder.findByNoLayouts(
			classNameId, privateLayout, start, end);
	}

	/**
	 * Returns all non-system groups having <code>null</code> or empty friendly
	 * URLs.
	 *
	 * @return the non-system groups having <code>null</code> or empty friendly
	 *         URLs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getNullFriendlyURLGroups() throws SystemException {
		return groupFinder.findByNullFriendlyURL();
	}

	/**
	 * Returns the specified organization group.
	 *
	 * @param  companyId the primary key of the company
	 * @param  organizationId the primary key of the organization
	 * @return the group associated with the organization
	 * @throws PortalException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group getOrganizationGroup(long companyId, long organizationId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(Organization.class);

		return groupPersistence.findByC_C_C(
			companyId, classNameId, organizationId);
	}

	/**
	 * Returns the specified organization groups.
	 *
	 * @param  organizations the organizations
	 * @return the groups associated with the organizations
	 */
	@Override
	public List<Group> getOrganizationsGroups(
		List<Organization> organizations) {

		List<Group> organizationGroups = new ArrayList<Group>();

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = organizations.get(i);

			Group group = organization.getGroup();

			organizationGroups.add(group);
		}

		return organizationGroups;
	}

	/**
	 * Returns all the groups related to the organizations.
	 *
	 * @param  organizations the organizations
	 * @return the groups related to the organizations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getOrganizationsRelatedGroups(
			List<Organization> organizations)
		throws SystemException {

		List<Group> organizationGroups = new ArrayList<Group>();

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = organizations.get(i);

			List<Group> groups = organizationPersistence.getGroups(
				organization.getOrganizationId());

			organizationGroups.addAll(groups);
		}

		return organizationGroups;
	}

	/**
	 * Returns the group followed by all its parent groups ordered by closest
	 * ancestor.
	 *
	 * @param  groupId the primary key of the group
	 * @return the group followed by all its parent groups ordered by closest
	 *         ancestor
	 * @throws PortalException if a group with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getParentGroups(long groupId)
		throws PortalException, SystemException {

		if (groupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			return new ArrayList<Group>();
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		return group.getAncestors();
	}

	/**
	 * Returns the staging group.
	 *
	 * @param  liveGroupId the primary key of the live group
	 * @return the staging group
	 * @throws PortalException if a matching staging group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group getStagingGroup(long liveGroupId)
		throws PortalException, SystemException {

		return groupPersistence.findByLiveGroupId(liveGroupId);
	}

	/**
	 * Returns the group associated with the user.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @return the group associated with the user
	 * @throws PortalException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group getUserGroup(long companyId, long userId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(User.class);

		return groupPersistence.findByC_C_C(companyId, classNameId, userId);
	}

	/**
	 * Returns the specified "user group" group. That is, the group that
	 * represents the {@link com.liferay.portal.model.UserGroup} entity.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userGroupId the primary key of the user group
	 * @return the group associated with the user group
	 * @throws PortalException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group getUserGroupGroup(long companyId, long userGroupId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(UserGroup.class);

		return groupPersistence.findByC_C_C(
			companyId, classNameId, userGroupId);
	}

	/**
	 * Returns all the user's site groups and immediate organization groups,
	 * optionally including the user's inherited organization groups and user
	 * groups. System and staged groups are not included.
	 *
	 * @param  userId the primary key of the user
	 * @param  inherit whether to include the user's inherited organization
	 *         groups and user groups
	 * @return the user's groups and immediate organization groups
	 * @throws PortalException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getUserGroups(long userId, boolean inherit)
		throws PortalException, SystemException {

		return getUserGroups(
			userId, inherit, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns an ordered range of all the user's site groups and immediate
	 * organization groups, optionally including the user's inherited
	 * organization groups and user groups. System and staged groups are not
	 * included.
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
	 * @param  userId the primary key of the user
	 * @param  inherit whether to include the user's inherited organization
	 *         groups and user groups
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the range of the user's groups and immediate organization groups
	 *         ordered by name
	 * @throws PortalException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getUserGroups(
			long userId, boolean inherit, int start, int end)
		throws PortalException, SystemException {

		if (inherit) {
			User user = userPersistence.findByPrimaryKey(userId);

			LinkedHashMap<String, Object> groupParams =
				new LinkedHashMap<String, Object>();

			groupParams.put("usersGroups", new Long(userId));

			return search(
				user.getCompanyId(), null, null, groupParams, start, end);
		}
		else {
			return userPersistence.getGroups(userId, start, end);
		}
	}

	/**
	 * Returns the groups associated with the user groups.
	 *
	 * @param  userGroups the user groups
	 * @return the groups associated with the user groups
	 * @throws PortalException if any one of the user group's group could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getUserGroupsGroups(List<UserGroup> userGroups)
		throws PortalException, SystemException {

		List<Group> userGroupGroups = new ArrayList<Group>();

		for (int i = 0; i < userGroups.size(); i++) {
			UserGroup userGroup = userGroups.get(i);

			Group group = userGroup.getGroup();

			userGroupGroups.add(group);
		}

		return userGroupGroups;
	}

	/**
	 * Returns all the groups related to the user groups.
	 *
	 * @param  userGroups the user groups
	 * @return the groups related to the user groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getUserGroupsRelatedGroups(List<UserGroup> userGroups)
		throws SystemException {

		List<Group> userGroupGroups = new ArrayList<Group>();

		for (int i = 0; i < userGroups.size(); i++) {
			UserGroup userGroup = userGroups.get(i);

			List<Group> groups = userGroupPersistence.getGroups(
				userGroup.getUserGroupId());

			userGroupGroups.addAll(groups);
		}

		return userGroupGroups;
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
	 * @param  userId the primary key of the user
	 * @param  start the lower bound of the range of groups to consider
	 * @param  end the upper bound of the range of groups to consider (not
	 *         inclusive)
	 * @return the range of groups associated with the user's organization
	 *         groups
	 * @throws PortalException if a user with the primary key could not be found
	 *         or if another portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> getUserOrganizationsGroups(
			long userId, int start, int end)
		throws PortalException, SystemException {

		List<Group> userOrgsGroups = new UniqueList<Group>();

		List<Organization> userOrgs =
			organizationLocalService.getUserOrganizations(userId, start, end);

		for (Organization organization : userOrgs) {
			userOrgsGroups.add(0, organization.getGroup());

			if (!PropsValues.ORGANIZATIONS_MEMBERSHIP_STRICT) {
				for (Organization ancestorOrganization :
						organization.getAncestors()) {

					userOrgsGroups.add(0, ancestorOrganization.getGroup());
				}
			}
		}

		return userOrgsGroups;
	}

	@Override
	public Group getUserPersonalSiteGroup(long companyId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(UserPersonalSite.class);
		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		return groupPersistence.findByC_C_C(
			companyId, classNameId, defaultUserId);
	}

	/**
	 * Returns <code>true</code> if the live group has a staging group.
	 *
	 * @param  liveGroupId the primary key of the live group
	 * @return <code>true</code> if the live group has a staging group;
	 *         <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean hasStagingGroup(long liveGroupId) throws SystemException {
		if (groupPersistence.fetchByLiveGroupId(liveGroupId) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the user is immediately associated with the
	 * group, or associated with the group via the user's organizations,
	 * inherited organizations, or user groups.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return <code>true</code> if the user is associated with the group;
	 *         <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean hasUserGroup(long userId, long groupId)
		throws SystemException {

		return hasUserGroup(userId, groupId, true);
	}

	/**
	 * Returns <code>true</code> if the user is immediately associated with the
	 * group, or optionally if the user is associated with the group via the
	 * user's organizations, inherited organizations, or user groups.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @param  inherit whether to include organization groups and user groups to
	 *         which the user belongs in the determination
	 * @return <code>true</code> if the user is associated with the group;
	 *         <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean hasUserGroup(long userId, long groupId, boolean inherit)
		throws SystemException {

		if (groupFinder.countByG_U(groupId, userId, inherit) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns the group with the matching group name by first searching the
	 * system groups and then using the finder cache.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the group's name
	 * @return the group with the name and associated company, or
	 *         <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group loadFetchGroup(long companyId, String name)
		throws SystemException {

		return groupPersistence.fetchByC_N(companyId, name);
	}

	/**
	 * Returns the group with the matching group name.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the group's name
	 * @return the group with the name and associated company
	 * @throws PortalException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group loadGetGroup(long companyId, String name)
		throws PortalException, SystemException {

		return groupPersistence.findByC_N(companyId, name);
	}

	/**
	 * Rebuilds the group tree.
	 *
	 * <p>
	 * Only call this method if the tree has become stale through operations
	 * other than normal CRUD. Under normal circumstances the tree is
	 * automatically rebuilt whenever necessary.
	 * </p>
	 *
	 * @param  companyId the primary key of the group's company
	 * @throws PortalException if a group with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void rebuildTree(long companyId)
		throws PortalException, SystemException {

		List<Group> groups = groupPersistence.findByCompanyId(companyId);

		for (Group group : groups) {
			group.setTreePath(group.buildTreePath());

			groupPersistence.update(group);
		}
	}

	/**
	 * Returns an ordered range of all the company's groups, optionally
	 * including the user's inherited organization groups and user groups.
	 * System and staged groups are not included.
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
	 * @param  companyId the primary key of the company
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include a user's organizations, inherited organizations, and user
	 *         groups in the search, add an entry with key
	 *         &quot;usersGroups&quot; mapped to the user's ID and an entry with
	 *         key &quot;inherit&quot; mapped to a non-<code>null</code> object.
	 *         For more information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, LinkedHashMap<String, Object> params, int start,
			int end)
		throws SystemException {

		return groupFinder.findByCompanyId(
			companyId, params, start, end, new GroupNameComparator(true));
	}

	/**
	 * Returns an ordered range of all the groups belonging to the parent group
	 * that match the keywords, optionally including the user's inherited
	 * organization groups and user groups. System and staged groups are not
	 * included.
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
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         sites's name, or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organizations and user groups in the
	 *         search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, long parentGroupId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end)
		throws SystemException {

		return search(
			companyId, parentGroupId, keywords, params, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups belonging to the parent group
	 * that match the keywords, optionally including the user's inherited
	 * organization groups and user groups. System and staged groups are not
	 * included.
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
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         sites's name, or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organizations and user groups in the
	 *         search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, long parentGroupId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		String parentGroupIdComparator = StringPool.EQUAL;

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			parentGroupIdComparator = StringPool.NOT_EQUAL;
		}

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		return groupFinder.findByKeywords(
			companyId, parentGroupId, parentGroupIdComparator, keywords, params,
			start, end, obc);
	}

	/**
	 * Returns an ordered range of all the site groups belonging to the parent
	 * group and organization groups that match the name and description,
	 * optionally including the user's inherited organization groups and user
	 * groups. System and staged groups are not included.
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
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organizations and user groups in the
	 *         search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, long parentGroupId, String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end)
		throws SystemException {

		return search(
			companyId, parentGroupId, name, description, params, andOperator,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the site groups belonging to the parent
	 * group and organization groups that match the name and description,
	 * optionally including the user's inherited organization groups and user
	 * groups. System and staged groups are not included.
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
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organizations and user groups in the
	 *         search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, long parentGroupId, String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		String parentGroupIdComparator = StringPool.EQUAL;

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			parentGroupIdComparator = StringPool.NOT_EQUAL;
		}

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		String realName = getRealName(companyId, name);

		return groupFinder.findByC_PG_N_D(
			companyId, parentGroupId, parentGroupIdComparator, name, realName,
			description, params, andOperator, start, end, obc);
	}

	/**
	 * Returns an ordered range of all the groups belonging to the parent group
	 * that match the class name IDs and keywords, optionally including the
	 * user's inherited organization groups and user groups. System and staged
	 * groups are not included.
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
	 * @param  companyId the primary key of the company
	 * @param  classNameIds the primary keys of the class names of the entities
	 *         the groups are related to (optionally <code>null</code>)
	 * @param  parentGroupId the primary key of the parent group
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         sites's name, or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include a user's organizations, inherited organizations, and user
	 *         groups in the search, add an entry with key
	 *         &quot;usersGroups&quot; mapped to the user's ID and an entry with
	 *         key &quot;inherit&quot; mapped to a non-<code>null</code> object.
	 *         For more information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, long[] classNameIds, long parentGroupId,
			String keywords, LinkedHashMap<String, Object> params, int start,
			int end)
		throws SystemException {

		return search(
			companyId, classNameIds, parentGroupId, keywords, params, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the groups belonging to the parent group
	 * that match the class name IDs and keywords, optionally including the
	 * user's inherited organization groups and user groups. System and staged
	 * groups are not included.
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
	 * @param  companyId the primary key of the company
	 * @param  classNameIds the primary keys of the class names of the entities
	 *         the groups are related to (optionally <code>null</code>)
	 * @param  parentGroupId the primary key of the parent group
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         sites's name, or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include a user's organizations, inherited organizations, and user
	 *         groups in the search, add an entry with key
	 *         &quot;usersGroups&quot; mapped to the user's ID and an entry with
	 *         key &quot;inherit&quot; mapped to a non-<code>null</code> object.
	 *         For more information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, long[] classNameIds, long parentGroupId,
			String keywords, LinkedHashMap<String, Object> params, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		String parentGroupIdComparator = StringPool.EQUAL;

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			parentGroupIdComparator = StringPool.NOT_EQUAL;
		}

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		return groupFinder.findByKeywords(
			companyId, classNameIds, parentGroupId, parentGroupIdComparator,
			keywords, params, start, end, obc);
	}

	/**
	 * Returns an ordered range of all the groups belonging to the parent group
	 * that match the class name IDs, name, and description, optionally
	 * including the user's inherited organization groups and user groups.
	 * System and staged groups are not included.
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
	 * @param  companyId the primary key of the company
	 * @param  classNameIds the primary keys of the class names of the entities
	 *         the groups are related to (optionally <code>null</code>)
	 * @param  parentGroupId the primary key of the parent group
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include a user's organizations, inherited organizations, and user
	 *         groups in the search, add an entry with key
	 *         &quot;usersGroups&quot; mapped to the user's ID and an entry with
	 *         key &quot;inherit&quot; mapped to a non-<code>null</code> object.
	 *         For more information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, long[] classNameIds, long parentGroupId,
			String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end)
		throws SystemException {

		return search(
			companyId, classNameIds, parentGroupId, name, description, params,
			andOperator, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups belonging to the parent group
	 * that match the class name IDs, name, and description, optionally
	 * including the user's inherited organization groups and user groups.
	 * System and staged groups are not included.
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
	 * @param  companyId the primary key of the company
	 * @param  classNameIds the primary keys of the class names of the entities
	 *         the groups are related to (optionally <code>null</code>)
	 * @param  parentGroupId the primary key of the parent group
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include a user's organizations, inherited organizations, and user
	 *         groups in the search, add an entry with key
	 *         &quot;usersGroups&quot; mapped to the user's ID and an entry with
	 *         key &quot;inherit&quot; mapped to a non-<code>null</code> object.
	 *         For more information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, long[] classNameIds, long parentGroupId,
			String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		String parentGroupIdComparator = StringPool.EQUAL;

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			parentGroupIdComparator = StringPool.NOT_EQUAL;
		}

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		String realName = getRealName(companyId, name);

		return groupFinder.findByC_C_PG_N_D(
			companyId, classNameIds, parentGroupId, parentGroupIdComparator,
			name, realName, description, params, andOperator, start, end, obc);
	}

	/**
	 * Returns an ordered range of all the groups that match the class name IDs
	 * and keywords, optionally including the user's inherited organization
	 * groups and user groups. System and staged groups are not included.
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
	 * @param  companyId the primary key of the company
	 * @param  classNameIds the primary keys of the class names of the entities
	 *         the groups are related to (optionally <code>null</code>)
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         sites's name, or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include a user's organizations, inherited organizations, and user
	 *         groups in the search, add an entry with key
	 *         &quot;usersGroups&quot; mapped to the user's ID and an entry with
	 *         key &quot;inherit&quot; mapped to a non-<code>null</code> object.
	 *         For more information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, long[] classNameIds, String keywords,
			LinkedHashMap<String, Object> params, int start, int end)
		throws SystemException {

		return search(
			companyId, classNameIds, keywords, params, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups that match the class name IDs
	 * and keywords, optionally including the user's inherited organization
	 * groups and user groups. System and staged groups are not included.
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
	 * @param  companyId the primary key of the company
	 * @param  classNameIds the primary keys of the class names of the entities
	 *         the groups are related to (optionally <code>null</code>)
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         sites's name, or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include a user's organizations, inherited organizations, and user
	 *         groups in the search, add an entry with key
	 *         &quot;usersGroups&quot; mapped to the user's ID and an entry with
	 *         key &quot;inherit&quot; mapped to a non-<code>null</code> object.
	 *         For more information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, long[] classNameIds, String keywords,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		return groupFinder.findByKeywords(
			companyId, classNameIds, keywords, params, start, end, obc);
	}

	/**
	 * Returns an ordered range of all the groups that match the class name IDs,
	 * name, and description, optionally including the user's inherited
	 * organization groups and user groups. System and staged groups are not
	 * included.
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
	 * @param  companyId the primary key of the company
	 * @param  classNameIds the primary keys of the class names of the entities
	 *         the groups are related to (optionally <code>null</code>)
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include a user's organizations, inherited organizations, and user
	 *         groups in the search, add an entry with key
	 *         &quot;usersGroups&quot; mapped to the user's ID and an entry with
	 *         key &quot;inherit&quot; mapped to a non-<code>null</code> object.
	 *         For more information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, long[] classNameIds, String name,
			String description, LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end)
		throws SystemException {

		return search(
			companyId, classNameIds, name, description, params, andOperator,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups that match the class name IDs,
	 * name, and description, optionally including the user's inherited
	 * organization groups and user groups. System and staged groups are not
	 * included.
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
	 * @param  companyId the primary key of the company
	 * @param  classNameIds the primary keys of the class names of the entities
	 *         the groups are related to (optionally <code>null</code>)
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include a user's organizations, inherited organizations, and user
	 *         groups in the search, add an entry with key
	 *         &quot;usersGroups&quot; mapped to the user's ID and an entry with
	 *         key &quot;inherit&quot; mapped to a non-<code>null</code> object.
	 *         For more information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, long[] classNameIds, String name,
			String description, LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		String realName = getRealName(companyId, name);

		return groupFinder.findByC_C_N_D(
			companyId, classNameIds, name, realName, description, params,
			andOperator, start, end, obc);
	}

	/**
	 * Returns an ordered range of all the groups that match the keywords,
	 * optionally including the user's inherited organization groups and user
	 * groups. System and staged groups are not included.
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
	 * @param  companyId the primary key of the company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         sites's name, or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organizations and user groups in the
	 *         search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end)
		throws SystemException {

		return search(companyId, keywords, params, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups that match the keywords,
	 * optionally including the user's inherited organization groups and user
	 * groups. System and staged groups are not included.
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
	 * @param  companyId the primary key of the company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         sites's name, or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organizations and user groups in the
	 *         search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		return groupFinder.findByKeywords(
			companyId, keywords, params, start, end, obc);
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
	 * @param  companyId the primary key of the company
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organizations and user groups in the
	 *         search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end)
		throws SystemException {

		return search(
			companyId, name, description, params, andOperator, start, end,
			null);
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
	 * @param  companyId the primary key of the company
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organizations and user groups in the
	 *         search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> search(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		String realName = getRealName(companyId, name);

		return groupFinder.findByC_N_D(
			companyId, name, realName, description, params, andOperator, start,
			end, obc);
	}

	/**
	 * Returns the number of groups belonging to the parent group that match the
	 * keywords, optionally including the user's inherited organization groups
	 * and user groups. System and staged groups are not included.
	 *
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         sites's name, or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organization groups and user groups
	 *         in the search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
			long companyId, long parentGroupId, String keywords,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		String parentGroupIdComparator = StringPool.EQUAL;

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			parentGroupIdComparator = StringPool.NOT_EQUAL;
		}

		return groupFinder.countByKeywords(
			companyId, parentGroupId, parentGroupIdComparator, keywords,
			params);
	}

	/**
	 * Returns the number of groups belonging to the parent group and immediate
	 * organization groups that match the name and description, optionally
	 * including the user's inherited organization groups and user groups.
	 * System and staged groups are not included.
	 *
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organization groups and user groups
	 *         in the search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
			long companyId, long parentGroupId, String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator)
		throws SystemException {

		String parentGroupIdComparator = StringPool.EQUAL;

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			parentGroupIdComparator = StringPool.NOT_EQUAL;
		}

		String realName = getRealName(companyId, name);

		return groupFinder.countByC_PG_N_D(
			companyId, parentGroupId, parentGroupIdComparator, name, realName,
			description, params, andOperator);
	}

	/**
	 * Returns the number of groups belonging to the parent group that match the
	 * class name IDs, and keywords, optionally including the user's inherited
	 * organization groups and user groups. System and staged groups are not
	 * included.
	 *
	 * @param  companyId the primary key of the company
	 * @param  classNameIds the primary keys of the class names of the entities
	 *         the groups are related to (optionally <code>null</code>)
	 * @param  parentGroupId the primary key of the parent group
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         sites's name, or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organization groups and user groups
	 *         in the search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
			long companyId, long[] classNameIds, long parentGroupId,
			String keywords, LinkedHashMap<String, Object> params)
		throws SystemException {

		String parentGroupIdComparator = StringPool.EQUAL;

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			parentGroupIdComparator = StringPool.NOT_EQUAL;
		}

		return groupFinder.countByKeywords(
			companyId, classNameIds, parentGroupId, parentGroupIdComparator,
			keywords, params);
	}

	/**
	 * Returns the number of groups belonging to the parent group that match the
	 * class name IDs, name, and description, optionally including the user's
	 * inherited organization groups and user groups. System and staged groups
	 * are not included.
	 *
	 * @param  companyId the primary key of the company
	 * @param  classNameIds the primary keys of the class names of the entities
	 *         the groups are related to (optionally <code>null</code>)
	 * @param  parentGroupId the primary key of the parent group
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organization groups and user groups
	 *         in the search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
			long companyId, long[] classNameIds, long parentGroupId,
			String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator)
		throws SystemException {

		String parentGroupIdComparator = StringPool.EQUAL;

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			parentGroupIdComparator = StringPool.NOT_EQUAL;
		}

		String realName = getRealName(companyId, name);

		return groupFinder.countByC_C_PG_N_D(
			companyId, classNameIds, parentGroupId, parentGroupIdComparator,
			name, realName, description, params, andOperator);
	}

	/**
	 * Returns the number of groups that match the class name IDs, and keywords,
	 * optionally including the user's inherited organization groups and user
	 * groups. System and staged groups are not included.
	 *
	 * @param  companyId the primary key of the company
	 * @param  classNameIds the primary keys of the class names of the entities
	 *         the groups are related to (optionally <code>null</code>)
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         sites's name, or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organization groups and user groups
	 *         in the search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
			long companyId, long[] classNameIds, String keywords,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		return groupFinder.countByKeywords(
			companyId, classNameIds, keywords, params);
	}

	/**
	 * Returns the number of groups that match the class name IDs, name, and
	 * description, optionally including the user's inherited organization
	 * groups and user groups. System and staged groups are not included.
	 *
	 * @param  companyId the primary key of the company
	 * @param  classNameIds the primary keys of the class names of the entities
	 *         the groups are related to (optionally <code>null</code>)
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organization groups and user groups
	 *         in the search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
			long companyId, long[] classNameIds, String name,
			String description, LinkedHashMap<String, Object> params,
			boolean andOperator)
		throws SystemException {

		String realName = getRealName(companyId, name);

		return groupFinder.countByC_C_N_D(
			companyId, classNameIds, name, realName, description, params,
			andOperator);
	}

	/**
	 * Returns the number of groups that match the keywords, optionally
	 * including the user's inherited organization groups and user groups.
	 * System and staged groups are not included.
	 *
	 * @param  companyId the primary key of the company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         sites's name, or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organization groups and user groups
	 *         in the search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		return groupFinder.countByKeywords(companyId, keywords, params);
	}

	/**
	 * Returns the number of groups and immediate organization groups that match
	 * the name and description, optionally including the user's inherited
	 * organization groups and user groups. System and staged groups are not
	 * included.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the group's name (optionally <code>null</code>)
	 * @param  description the group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). To
	 *         include the user's inherited organization groups and user groups
	 *         in the search, add entries having &quot;usersGroups&quot; and
	 *         &quot;inherit&quot; as keys mapped to the the user's ID. For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.GroupFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator)
		throws SystemException {

		String realName = getRealName(companyId, name);

		return groupFinder.countByC_N_D(
			companyId, name, realName, description, params, andOperator);
	}

	/**
	 * Sets the groups associated with the role, removing and adding
	 * associations as necessary.
	 *
	 * @param  roleId the primary key of the role
	 * @param  groupIds the primary keys of the groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setRoleGroups(long roleId, long[] groupIds)
		throws SystemException {

		rolePersistence.setGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Removes the groups from the role.
	 *
	 * @param  roleId the primary key of the role
	 * @param  groupIds the primary keys of the groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void unsetRoleGroups(long roleId, long[] groupIds)
		throws SystemException {

		rolePersistence.removeGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Removes the user from the groups.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupIds the primary keys of the groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void unsetUserGroups(long userId, long[] groupIds)
		throws SystemException {

		userGroupRoleLocalService.deleteUserGroupRoles(userId, groupIds);

		userPersistence.removeGroups(userId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Updates the group's asset replacing categories and tag names.
	 *
	 * @param  userId the primary key of the user
	 * @param  group the group
	 * @param  assetCategoryIds the primary keys of the asset categories
	 *         (optionally <code>null</code>)
	 * @param  assetTagNames the asset tag names (optionally <code>null</code>)
	 * @throws PortalException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void updateAsset(
			long userId, Group group, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		Company company = companyPersistence.findByPrimaryKey(
			user.getCompanyId());

		Group companyGroup = company.getGroup();

		assetEntryLocalService.updateEntry(
			userId, companyGroup.getGroupId(), null, null,
			Group.class.getName(), group.getGroupId(), null, 0,
			assetCategoryIds, assetTagNames, false, null, null, null, null,
			group.getDescriptiveName(), group.getDescription(), null, null,
			null, 0, 0, null, false);
	}

	/**
	 * Updates the group's friendly URL.
	 *
	 * @param  groupId the primary key of the group
	 * @param  friendlyURL the group's new friendlyURL (optionally
	 *         <code>null</code>)
	 * @return the group
	 * @throws PortalException if a group with the primary key could not be
	 *         found or if a valid friendly URL could not be created for the
	 *         group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group updateFriendlyURL(long groupId, String friendlyURL)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		if (group.isUser()) {
			User user = userPersistence.findByPrimaryKey(group.getClassPK());

			friendlyURL = StringPool.SLASH + user.getScreenName();

			if (group.getFriendlyURL().equals(friendlyURL)) {
				return group;
			}
		}

		friendlyURL = getFriendlyURL(
			group.getCompanyId(), groupId, group.getClassNameId(),
			group.getClassPK(), StringPool.BLANK, friendlyURL);

		validateFriendlyURL(
			group.getCompanyId(), group.getGroupId(), group.getClassNameId(),
			group.getClassPK(), friendlyURL);

		group.setFriendlyURL(friendlyURL);

		groupPersistence.update(group);

		return group;
	}

	/**
	 * Updates the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  parentGroupId the primary key of the parent group
	 * @param  name the group's new name
	 * @param  description the group's new description (optionally
	 *         <code>null</code>)
	 * @param  type the group's new type. For more information see {@link
	 *         com.liferay.portal.model.GroupConstants}
	 * @param  friendlyURL the group's new friendlyURL (optionally
	 *         <code>null</code>)
	 * @param  active whether the group is active
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set asset category IDs and asset tag
	 *         names for the group.
	 * @return the group
	 * @throws PortalException if a group with the primary key could not be
	 *         found or if the friendly URL was invalid or could one not be
	 *         created
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group updateGroup(
			long groupId, long parentGroupId, String name, String description,
			int type, String friendlyURL, boolean active,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		String className = group.getClassName();
		long classNameId = group.getClassNameId();
		long classPK = group.getClassPK();
		friendlyURL = getFriendlyURL(
			group.getCompanyId(), groupId, classNameId, classPK,
			StringPool.BLANK, friendlyURL);

		if ((classNameId <= 0) || className.equals(Group.class.getName())) {
			validateName(
				group.getGroupId(), group.getCompanyId(), name, group.isSite());
		}
		else if (className.equals(Organization.class.getName())) {
			Organization organization =
				organizationPersistence.findByPrimaryKey(classPK);

			name = getOrgGroupName(organization.getName());
		}
		else if (!GroupConstants.USER_PERSONAL_SITE.equals(name)) {
			name = String.valueOf(classPK);
		}

		if (PortalUtil.isSystemGroup(group.getName()) &&
			!name.equals(group.getName())) {

			throw new RequiredGroupException(
				String.valueOf(group.getGroupId()),
				RequiredGroupException.SYSTEM_GROUP);
		}

		validateFriendlyURL(
			group.getCompanyId(), group.getGroupId(), group.getClassNameId(),
			group.getClassPK(), friendlyURL);

		validateParentGroup(group.getGroupId(), parentGroupId, type);

		group.setParentGroupId(parentGroupId);
		group.setTreePath(group.buildTreePath());
		group.setName(name);
		group.setDescription(description);
		group.setType(type);
		group.setFriendlyURL(friendlyURL);
		group.setActive(active);

		if ((serviceContext != null) && group.isSite()) {
			group.setExpandoBridgeAttributes(serviceContext);
		}

		groupPersistence.update(group);

		// Asset

		if ((serviceContext == null) || !group.isSite()) {
			return group;
		}

		User user = null;

		try {
			user = userPersistence.findByPrimaryKey(group.getCreatorUserId());

		}
		catch (NoSuchUserException nsue1) {
			try {
				user = userPersistence.findByPrimaryKey(
					serviceContext.getUserId());
			}
			catch (NoSuchUserException nsue2) {
				user = userLocalService.getDefaultUser(group.getCompanyId());
			}
		}

		updateAsset(
			user.getUserId(), group, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		return group;
	}

	/**
	 * Updates the group's type settings.
	 *
	 * @param  groupId the primary key of the group
	 * @param  typeSettings the group's new type settings (optionally
	 *         <code>null</code>)
	 * @return the group
	 * @throws PortalException if a group with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group updateGroup(long groupId, String typeSettings)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		group.setTypeSettings(typeSettings);

		groupPersistence.update(group);

		return group;
	}

	/**
	 * Associates the group with a main site if the group is an organization.
	 *
	 * @param  groupId the primary key of the group
	 * @param  site whether the group is to be associated with a main site
	 * @return the group
	 * @throws PortalException if a group with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group updateSite(long groupId, boolean site)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		if (!group.isOrganization()) {
			return group;
		}

		group.setSite(site);

		groupPersistence.update(group);

		return group;
	}

	protected void addControlPanelLayouts(Group group)
		throws PortalException, SystemException {

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		String friendlyURL = getFriendlyURL(
			PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		ServiceContext serviceContext = new ServiceContext();

		layoutLocalService.addLayout(
			defaultUserId, group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			PropsValues.CONTROL_PANEL_LAYOUT_NAME, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_CONTROL_PANEL, false,
			friendlyURL, serviceContext);
	}

	protected void addDefaultGuestPublicLayoutByProperties(Group group)
		throws PortalException, SystemException {

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());
		String friendlyURL = getFriendlyURL(
			PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_FRIENDLY_URL);

		ServiceContext serviceContext = new ServiceContext();

		Layout layout = layoutLocalService.addLayout(
			defaultUserId, group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_NAME, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false, friendlyURL,
			serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			0, PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_TEMPLATE_ID, false);

		for (int i = 0; i < 10; i++) {
			String columnId = "column-" + i;
			String portletIds = PropsUtil.get(
				PropsKeys.DEFAULT_GUEST_PUBLIC_LAYOUT_COLUMN + i);

			layoutTypePortlet.addPortletIds(
				0, StringUtil.split(portletIds), columnId, false);
		}

		layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		boolean updateLayoutSet = false;

		LayoutSet layoutSet = layout.getLayoutSet();

		if (Validator.isNotNull(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_THEME_ID)) {

			layoutSet.setThemeId(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_THEME_ID);

			updateLayoutSet = true;
		}

		if (Validator.isNotNull(
				PropsValues.
					DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_COLOR_SCHEME_ID)) {

			layoutSet.setColorSchemeId(
				PropsValues.
					DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_COLOR_SCHEME_ID);

			updateLayoutSet = true;
		}

		if (Validator.isNotNull(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_WAP_THEME_ID)) {

			layoutSet.setWapThemeId(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_WAP_THEME_ID);

			updateLayoutSet = true;
		}

		if (Validator.isNotNull(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_WAP_COLOR_SCHEME_ID)) {

			layoutSet.setWapColorSchemeId(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_WAP_COLOR_SCHEME_ID);

			updateLayoutSet = true;
		}

		if (updateLayoutSet) {
			layoutSetLocalService.updateLayoutSet(layoutSet);
		}
	}

	protected void addDefaultGuestPublicLayouts(Group group)
		throws PortalException, SystemException {

		if (publicLARFile != null) {
			addDefaultGuestPublicLayoutsByLAR(group, publicLARFile);
		}
		else {
			addDefaultGuestPublicLayoutByProperties(group);
		}
	}

	protected void addDefaultGuestPublicLayoutsByLAR(Group group, File larFile)
		throws PortalException, SystemException {

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});

		layoutLocalService.importLayouts(
			defaultUserId, group.getGroupId(), false, parameterMap, larFile);
	}

	protected void deletePortletData(Group group)
		throws PortalException, SystemException {

		List<Portlet> portlets = portletLocalService.getPortlets(
			group.getCompanyId());

		for (Portlet portlet : portlets) {
			if (!portlet.isActive()) {
				continue;
			}

			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			if ((portletDataHandler == null) ||
				portletDataHandler.isDataPortalLevel()) {

				continue;
			}

			PortletDataContext portletDataContext =
				PortletDataContextFactoryUtil.createExportPortletDataContext(
					group.getCompanyId(), group.getGroupId(),
					(Map<String, String[]>)null, (Date)null, (Date)null,
					(ZipWriter)null);

			// For now, we are going to throw an exception if one portlet data
			// handler has an exception to ensure that the transaction is rolled
			// back for data integrity. We may decide that this is not the best
			// behavior in the future because a bad plugin could disallow
			// deletion of groups.

			//try {
				portletDataHandler.deleteData(
					portletDataContext, portlet.getPortletId(), null);
			/*}
			catch (Exception e) {
				_log.error(
					"Unable to delete data for portlet " +
						portlet.getPortletId() + " in group " +
							group.getGroupId());
			}*/
		}
	}

	protected String getFriendlyURL(
			long companyId, long groupId, long classNameId, long classPK,
			String friendlyName, String friendlyURL)
		throws PortalException, SystemException {

		friendlyURL = getFriendlyURL(friendlyURL);

		if (Validator.isNotNull(friendlyURL)) {
			return friendlyURL;
		}

		friendlyURL = StringPool.SLASH + getFriendlyURL(friendlyName);

		String originalFriendlyURL = friendlyURL;

		for (int i = 1;; i++) {
			try {
				validateFriendlyURL(
					companyId, groupId, classNameId, classPK, friendlyURL);

				break;
			}
			catch (GroupFriendlyURLException gfurle) {
				int type = gfurle.getType();

				if (type == GroupFriendlyURLException.DUPLICATE) {
					friendlyURL = originalFriendlyURL + i;
				}
				else {
					friendlyURL = StringPool.SLASH + classPK;

					break;
				}
			}
		}

		return friendlyURL;
	}

	protected String getFriendlyURL(String friendlyURL) {
		return FriendlyURLNormalizerUtil.normalize(friendlyURL);
	}

	protected String getOrgGroupName(String name) {
		return name + ORGANIZATION_NAME_SUFFIX;
	}

	protected String getRealName(long companyId, String name)
		throws SystemException {

		if (Validator.isNull(name)) {
			return name;
		}

		String realName = name;

		try {
			Company company = companyLocalService.getCompany(companyId);

			Account account = company.getAccount();

			String companyName = account.getName();

			name = StringUtil.replace(
				name, StringPool.PERCENT, StringPool.BLANK);

			if (companyName.contains(name)) {
				realName =
					StringPool.PERCENT + GroupConstants.GUEST +
						StringPool.PERCENT;
			}
		}
		catch (PortalException pe) {
		}

		return realName;
	}

	protected void initImportLARFile() {
		String publicLARFileName = PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUTS_LAR;

		if (_log.isDebugEnabled()) {
			_log.debug("Reading public LAR file " + publicLARFileName);
		}

		if (Validator.isNotNull(publicLARFileName)) {
			publicLARFile = new File(publicLARFileName);

			if (!publicLARFile.exists()) {
				_log.error(
					"Public LAR file " + publicLARFile + " does not exist");

				publicLARFile = null;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Using public LAR file " + publicLARFileName);
				}
			}
		}
	}

	protected void initUserPersonalSitePermissions(Group group)
		throws PortalException, SystemException {

		// User role

		Role role = roleLocalService.getRole(
			group.getCompanyId(), RoleConstants.USER);

		List<Portlet> portlets = portletLocalService.getPortlets(
			group.getCompanyId(), false, false);

		for (Portlet portlet : portlets) {
			setRolePermissions(
				group, role, portlet.getPortletId(),
				new String[] {ActionKeys.VIEW});
		}

		setRolePermissions(
			group, role, Layout.class.getName(),
			new String[] {ActionKeys.VIEW});

		setRolePermissions(
			group, role, "com.liferay.portlet.blogs",
			new String[] {
				ActionKeys.ADD_ENTRY, ActionKeys.PERMISSIONS,
				ActionKeys.SUBSCRIBE});

		setRolePermissions(
			group, role, "com.liferay.portlet.calendar",
			new String[] {
				ActionKeys.ADD_EVENT, ActionKeys.EXPORT_ALL_EVENTS,
				ActionKeys.PERMISSIONS});

		// Power User role

		role = roleLocalService.getRole(
			group.getCompanyId(), RoleConstants.POWER_USER);

		for (Portlet portlet : portlets) {
			List<String> actions =
				ResourceActionsUtil.getPortletResourceActions(
					portlet.getPortletId());

			String controlPanelEntryCategory = GetterUtil.getString(
				portlet.getControlPanelEntryCategory());

			if (actions.contains(ActionKeys.ACCESS_IN_CONTROL_PANEL) &&
				controlPanelEntryCategory.startsWith(
					PortletCategoryKeys.SITE_ADMINISTRATION)) {

				setRolePermissions(
					group, role, portlet.getPortletId(),
					new String[] {ActionKeys.ACCESS_IN_CONTROL_PANEL});
			}
		}

		setRolePermissions(
			group, role, Group.class.getName(),
			new String[] {ActionKeys.MANAGE_LAYOUTS});

		setRolePermissions(group, role, "com.liferay.portlet.asset");
		setRolePermissions(group, role, "com.liferay.portlet.blogs");
		setRolePermissions(group, role, "com.liferay.portlet.bookmarks");
		setRolePermissions(group, role, "com.liferay.portlet.calendar");
		setRolePermissions(group, role, "com.liferay.portlet.documentlibrary");
		setRolePermissions(group, role, "com.liferay.portlet.imagegallery");
		setRolePermissions(group, role, "com.liferay.portlet.messageboards");
		setRolePermissions(group, role, "com.liferay.portlet.polls");
		setRolePermissions(group, role, "com.liferay.portlet.wiki");
	}

	protected boolean isParentGroup(long parentGroupId, long groupId)
			throws PortalException, SystemException {

		// Return true if parentGroupId is among the parent groups of groupId

		if (groupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			return false;
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		String treePath = group.getTreePath();

		if (treePath.contains(
				StringPool.SLASH + parentGroupId + StringPool.SLASH)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isStaging(ServiceContext serviceContext) {
		if (serviceContext != null) {
			return ParamUtil.getBoolean(serviceContext, "staging");
		}

		return false;
	}

	protected void setCompanyPermissions(
			Role role, String name, String[] actionIds)
		throws PortalException, SystemException {

		if (resourceBlockLocalService.isSupported(name)) {
			resourceBlockLocalService.setCompanyScopePermissions(
				role.getCompanyId(), name, role.getRoleId(),
				Arrays.asList(actionIds));
		}
		else {
			resourcePermissionLocalService.setResourcePermissions(
				role.getCompanyId(), name, ResourceConstants.SCOPE_COMPANY,
				String.valueOf(role.getCompanyId()), role.getRoleId(),
				actionIds);
		}
	}

	protected void setRolePermissions(Group group, Role role, String name)
		throws PortalException, SystemException {

		List<String> actions = ResourceActionsUtil.getModelResourceActions(
			name);

		setRolePermissions(
			group, role, name, actions.toArray(new String[actions.size()]));
	}

	protected void setRolePermissions(
			Group group, Role role, String name, String[] actionIds)
		throws PortalException, SystemException {

		if (resourceBlockLocalService.isSupported(name)) {
			resourceBlockLocalService.setGroupScopePermissions(
				role.getCompanyId(), group.getGroupId(), name, role.getRoleId(),
				Arrays.asList(actionIds));
		}
		else {
			resourcePermissionLocalService.setResourcePermissions(
				group.getCompanyId(), name, ResourceConstants.SCOPE_GROUP,
				String.valueOf(group.getGroupId()), role.getRoleId(),
				actionIds);
		}
	}

	protected void unscheduleStaging(Group group) {
		try {

			// Remote publishing

			String groupName = StagingUtil.getSchedulerGroupName(
				DestinationNames.LAYOUTS_REMOTE_PUBLISHER, group.getGroupId());

			SchedulerEngineHelperUtil.delete(groupName, StorageType.PERSISTED);

			long liveGroupId = 0;
			long stagingGroupId = 0;

			if (group.isStagingGroup()) {
				liveGroupId = group.getLiveGroupId();

				stagingGroupId = group.getGroupId();
			}
			else if (group.hasStagingGroup()) {
				liveGroupId = group.getGroupId();

				stagingGroupId = group.getStagingGroup().getGroupId();
			}

			if ((liveGroupId != 0) && (stagingGroupId != 0)) {

				// Publish to live

				groupName = StagingUtil.getSchedulerGroupName(
					DestinationNames.LAYOUTS_LOCAL_PUBLISHER, liveGroupId);

				SchedulerEngineHelperUtil.delete(
					groupName, StorageType.PERSISTED);

				// Copy from live

				groupName = StagingUtil.getSchedulerGroupName(
					DestinationNames.LAYOUTS_LOCAL_PUBLISHER, stagingGroupId);

				SchedulerEngineHelperUtil.delete(
					groupName, StorageType.PERSISTED);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to unschedule events for group: " + group.getGroupId());
		}
	}

	protected void validateFriendlyURL(
			long companyId, long groupId, long classNameId, long classPK,
			String friendlyURL)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		if (company.isSystem()) {
			return;
		}

		if (Validator.isNull(friendlyURL)) {
			return;
		}

		int exceptionType = LayoutImpl.validateFriendlyURL(friendlyURL);

		if (exceptionType != -1) {
			throw new GroupFriendlyURLException(exceptionType);
		}

		Group group = groupPersistence.fetchByC_F(companyId, friendlyURL);

		if ((group != null) && (group.getGroupId() != groupId)) {
			throw new GroupFriendlyURLException(
				GroupFriendlyURLException.DUPLICATE);
		}

		String groupIdFriendlyURL = friendlyURL.substring(1);

		if (Validator.isNumber(groupIdFriendlyURL)) {
			long groupClassNameId = PortalUtil.getClassNameId(Group.class);

			if (((classNameId != groupClassNameId) &&
				 !groupIdFriendlyURL.equals(String.valueOf(classPK)) &&
				 !PropsValues.USERS_SCREEN_NAME_ALLOW_NUMERIC) ||
				((classNameId == groupClassNameId) &&
				 !groupIdFriendlyURL.equals(String.valueOf(groupId)))) {

				GroupFriendlyURLException gfurle =
					new GroupFriendlyURLException(
						GroupFriendlyURLException.POSSIBLE_DUPLICATE);

				gfurle.setKeywordConflict(groupIdFriendlyURL);

				throw gfurle;
			}
		}

		String screenName = friendlyURL.substring(1);

		User user = userPersistence.fetchByC_SN(companyId, screenName);

		if (user != null) {
			long userClassNameId = PortalUtil.getClassNameId(User.class);

			if ((classNameId == userClassNameId) &&
				(classPK == user.getUserId())) {
			}
			else {
				throw new GroupFriendlyURLException(
					GroupFriendlyURLException.DUPLICATE);
			}
		}

		if (StringUtil.count(friendlyURL, StringPool.SLASH) > 1) {
			throw new GroupFriendlyURLException(
				GroupFriendlyURLException.TOO_DEEP);
		}
	}

	protected void validateName(
			long groupId, long companyId, String name, boolean site)
		throws PortalException, SystemException {

		if (Validator.isNull(name) || Validator.isNumber(name) ||
			name.contains(StringPool.STAR) ||
			name.contains(ORGANIZATION_NAME_SUFFIX)) {

			throw new GroupNameException();
		}

		try {
			Group group = groupFinder.findByC_N(companyId, name);

			if ((groupId <= 0) || (group.getGroupId() != groupId)) {
				throw new DuplicateGroupException();
			}
		}
		catch (NoSuchGroupException nsge) {
		}

		if (site) {
			Company company = companyLocalService.getCompany(companyId);

			if (name.equals(company.getName())) {
				throw new DuplicateGroupException();
			}
		}
	}

	protected void validateParentGroup(
			long groupId, long parentGroupId, int type)
		throws PortalException, SystemException {

		if ((type == GroupConstants.TYPE_SITE_LIMITED_TO_PARENT_SITE_MEMBERS) &&
			(parentGroupId == GroupConstants.DEFAULT_PARENT_GROUP_ID)) {

			throw new GroupParentException(GroupParentException.MISSING_PARENT);
		}

		if (parentGroupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			return;
		}

		if (groupId == parentGroupId) {
			throw new GroupParentException(
				GroupParentException.SELF_DESCENDANT);
		}

		Group group = groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return;
		}

		if ((groupId > 0) &&
			(parentGroupId != GroupConstants.DEFAULT_PARENT_GROUP_ID)) {

			// Prevent circular groupal references

			if (isParentGroup(groupId, parentGroupId)) {
				throw new GroupParentException(
					GroupParentException.CHILD_DESCENDANT);
			}
		}

		Group parentGroup = groupLocalService.getGroup(parentGroupId);

		if (group.isStagingGroup()) {
			Group stagingGroup = parentGroup.getStagingGroup();

			if (groupId == stagingGroup.getGroupId()) {
				throw new GroupParentException(
					GroupParentException.STAGING_DESCENDANT);
			}
		}
	}

	protected File publicLARFile;

	private static Log _log = LogFactoryUtil.getLog(
		GroupLocalServiceImpl.class);

	private Map<String, Group> _systemGroupsMap = new HashMap<String, Group>();

}
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

import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.GroupInheritContentException;
import com.liferay.portal.GroupKeyException;
import com.liferay.portal.GroupParentException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.PendingBackgroundTaskException;
import com.liferay.portal.RequiredGroupException;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCachable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.spring.aop.Skip;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeModelTasksAdapter;
import com.liferay.portal.kernel.util.TreePathUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.UserPersonalSite;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.security.permission.RolePermissions;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.GroupLocalServiceBaseImpl;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletCategoryKeys;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.GroupIdComparator;
import com.liferay.portal.util.comparator.GroupNameComparator;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataContextFactoryUtil;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.staging.StagingConstants;
import com.liferay.portlet.exportimport.staging.StagingUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
 * <code>SITE</code>). For a list of types, see {@link GroupConstants}.
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

	public static final String ORGANIZATION_STAGING_SUFFIX = " (Staging)";

	/**
	 * Constructs a group local service.
	 */
	public GroupLocalServiceImpl() {
		initImportLARFile();
	}

	@Override
	public Group addGroup(
			long userId, long parentGroupId, String className, long classPK,
			long liveGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean site, boolean inheritContent,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		// Group

		User user = userPersistence.findByPrimaryKey(userId);
		className = GetterUtil.getString(className);
		long classNameId = classNameLocalService.getClassNameId(className);

		String groupKey = StringPool.BLANK;
		String friendlyName = StringPool.BLANK;

		if (nameMap != null) {
			groupKey = nameMap.get(LocaleUtil.getDefault());
			friendlyName = nameMap.get(LocaleUtil.getDefault());
		}

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

		long groupClassNameId = classNameLocalService.getClassNameId(
			Group.class);

		if ((classNameId <= 0) || className.equals(Group.class.getName()) ||
			(className.equals(Company.class.getName()) && staging)) {

			className = Group.class.getName();
			classNameId = groupClassNameId;
			classPK = groupId;
		}
		else if (className.equals(Organization.class.getName())) {
			groupKey = getOrgGroupName(groupKey);
		}
		else if (!GroupConstants.USER_PERSONAL_SITE.equals(groupKey)) {
			groupKey = String.valueOf(classPK);
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
			groupKey = groupKey.concat("-staging");

			for (Locale locale : nameMap.keySet()) {
				String name = nameMap.get(locale);

				if (Validator.isNull(name)) {
					continue;
				}

				nameMap.put(locale, name.concat(ORGANIZATION_STAGING_SUFFIX));
			}

			friendlyURL = getFriendlyURL(friendlyURL.concat("-staging"));
		}

		if (parentGroupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			membershipRestriction =
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;
		}

		if (className.equals(Group.class.getName())) {
			if (!site && (liveGroupId == 0) &&
				!groupKey.equals(GroupConstants.CONTROL_PANEL)) {

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
			validateGroupKey(groupId, user.getCompanyId(), groupKey, site);
		}

		validateInheritContent(parentGroupId, inheritContent);

		validateFriendlyURL(
			user.getCompanyId(), groupId, classNameId, classPK, friendlyURL);

		validateParentGroup(groupId, parentGroupId);

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
		group.setGroupKey(groupKey);
		group.setNameMap(nameMap);
		group.setDescriptionMap(descriptionMap);
		group.setType(type);
		group.setManualMembership(manualMembership);
		group.setMembershipRestriction(membershipRestriction);
		group.setFriendlyURL(friendlyURL);
		group.setInheritContent(inheritContent);
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

		// Resources

		resourceLocalService.addResources(
			group.getCompanyId(), 0, 0, Group.class.getName(),
			group.getGroupId(), false, false, false);

		if ((classNameId == groupClassNameId) && !user.isDefaultUser()) {

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

		addPortletDefaultData(group);

		return group;
	}

	@Override
	public Group addGroup(
			long userId, long parentGroupId, String className, long classPK,
			long liveGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean site, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return addGroup(
			userId, parentGroupId, className, classPK, liveGroupId, nameMap,
			descriptionMap, type, manualMembership, membershipRestriction,
			friendlyURL, site, false, active, serviceContext);
	}

	/**
	 * Adds a group.
	 *
	 * @param      userId the primary key of the group's creator/owner
	 * @param      parentGroupId the primary key of the parent group
	 * @param      className the entity's class name
	 * @param      classPK the primary key of the entity's instance
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
	 *             <code>null</code>). Can set asset category IDs and asset tag
	 *             names for the group, and whether the group is for staging.
	 * @return     the group
	 * @throws     PortalException if a portal exception occured
	 * @deprecated As of 7.0.0, replaced by {@link #addGroup(long, long, String,
	 *             long, long, Map, Map, int, boolean, int, String, boolean,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Group addGroup(
			long userId, long parentGroupId, String className, long classPK,
			long liveGroupId, String name, String description, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean site, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return addGroup(
			userId, parentGroupId, className, classPK, liveGroupId,
			getLocalizationMap(name), getLocalizationMap(description), type,
			manualMembership, membershipRestriction, friendlyURL, site, false,
			active, serviceContext);
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
	 *             GroupConstants}.
	 * @param      friendlyURL the group's friendlyURL
	 * @param      site whether the group is to be associated with a main site
	 * @param      active whether the group is active
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs and asset tag
	 *             names for the group, and whether the group is for staging.
	 * @return     the group
	 * @throws     PortalException if a portal exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addGroup(long, long, String,
	 *             long, long, Map, Map, int, boolean, int, String, boolean,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Group addGroup(
			long userId, long parentGroupId, String className, long classPK,
			String name, String description, int type, String friendlyURL,
			boolean site, boolean active, ServiceContext serviceContext)
		throws PortalException {

		return addGroup(
			userId, parentGroupId, className, classPK,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, getLocalizationMap(name),
			getLocalizationMap(description), type, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURL, site,
			active, serviceContext);
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
	 *             GroupConstants}.
	 * @param      friendlyURL the group's friendlyURL (optionally
	 *             <code>null</code>)
	 * @param      site whether the group is to be associated with a main site
	 * @param      active whether the group is active
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs and asset tag
	 *             names for the group, and whether the group is for staging.
	 * @return     the group
	 * @throws     PortalException if a portal exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addGroup(long, long, String,
	 *             long, long, Map, Map, int, boolean, int, String, boolean,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Group addGroup(
			long userId, String className, long classPK, long liveGroupId,
			String name, String description, int type, String friendlyURL,
			boolean site, boolean active, ServiceContext serviceContext)
		throws PortalException {

		return addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, className, classPK,
			liveGroupId, getLocalizationMap(name),
			getLocalizationMap(description), type, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURL, site,
			active, serviceContext);
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
	 *             GroupConstants}.
	 * @param      friendlyURL the group's friendlyURL
	 * @param      site whether the group is to be associated with a main site
	 * @param      active whether the group is active
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs and asset tag
	 *             names for the group, and whether the group is for staging.
	 * @return     the group
	 * @throws     PortalException if a portal exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addGroup(long, long, String,
	 *             long, long, Map, Map, int, boolean, int, String, boolean,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Group addGroup(
			long userId, String className, long classPK, String name,
			String description, int type, String friendlyURL, boolean site,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		return addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, className, classPK,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, getLocalizationMap(name),
			getLocalizationMap(description), type, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURL, site,
			active, serviceContext);
	}

	/**
	 * Adds the groups to the role.
	 *
	 * @param roleId the primary key of the role
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void addRoleGroups(long roleId, long[] groupIds) {
		rolePersistence.addGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Adds the user to the groups.
	 *
	 * @param userId the primary key of the user
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void addUserGroups(long userId, long[] groupIds) {
		userPersistence.addGroups(userId, groupIds);

		PermissionCacheUtil.clearCache(userId);
	}

	/**
	 * Adds a company group if it does not exist. This method is typically used
	 * when a virtual host is added.
	 *
	 * @param  companyId the primary key of the company
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkCompanyGroup(long companyId) throws PortalException {
		long classNameId = classNameLocalService.getClassNameId(Company.class);

		int count = groupPersistence.countByC_C_C(
			companyId, classNameId, companyId);

		if (count == 0) {
			long defaultUserId = userLocalService.getDefaultUserId(companyId);

			groupLocalService.addGroup(
				defaultUserId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
				Company.class.getName(), companyId,
				GroupConstants.DEFAULT_LIVE_GROUP_ID,
				getLocalizationMap(GroupConstants.GLOBAL), null, 0, true,
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
				GroupConstants.GLOBAL_FRIENDLY_URL, true, true, null);
		}
	}

	/**
	 * Creates systems groups and other related data needed by the system on the
	 * very first startup. Also takes care of creating the Control Panel groups
	 * and layouts.
	 *
	 * @param  companyId the primary key of the company
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkSystemGroups(long companyId) throws PortalException {
		String companyIdHexString = StringUtil.toHexString(companyId);

		for (Group group : groupFinder.findBySystem(companyId)) {
			_systemGroupsMap.put(
				companyIdHexString.concat(group.getGroupKey()), group);
		}

		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		String[] systemGroups = PortalUtil.getSystemGroups();

		for (String groupKey : systemGroups) {
			String groupCacheKey = companyIdHexString.concat(groupKey);

			Group group = _systemGroupsMap.get(groupCacheKey);

			if (group == null) {
				group = groupPersistence.fetchByC_GK(companyId, groupKey);
			}

			if (group == null) {
				String className = null;
				long classPK = 0;
				int type = GroupConstants.TYPE_SITE_OPEN;
				String friendlyURL = null;
				boolean site = true;

				if (groupKey.equals(GroupConstants.CONTROL_PANEL)) {
					type = GroupConstants.TYPE_SITE_PRIVATE;
					friendlyURL = GroupConstants.CONTROL_PANEL_FRIENDLY_URL;
					site = false;
				}
				else if (groupKey.equals(GroupConstants.GUEST)) {
					friendlyURL = "/guest";
				}
				else if (groupKey.equals(GroupConstants.USER_PERSONAL_SITE)) {
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
					getLocalizationMap(groupKey), null, type, true,
					GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURL,
					site, true, null);

				if (groupKey.equals(GroupConstants.USER_PERSONAL_SITE)) {
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

			if (group.isGuest()) {
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
	 * membership requests, subscriptions, teams, blogs, bookmarks, events,
	 * image gallery, journals, message boards, polls, shopping related
	 * entities, software catalog, and wikis are also deleted.
	 * </p>
	 *
	 * @param  group the group
	 * @return the deleted group
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group deleteGroup(Group group) throws PortalException {
		boolean deleteInProcess = GroupThreadLocal.isDeleteInProcess();

		try {
			GroupThreadLocal.setDeleteInProcess(true);

			if (((group.isCompany() && !group.isCompanyStagingGroup()) ||
				 PortalUtil.isSystemGroup(group.getGroupKey())) &&
				!CompanyThreadLocal.isDeleteInProcess()) {

				throw new RequiredGroupException.MustNotDeleteSystemGroup(
					group.getGroupId());
			}

			if (groupPersistence.countByC_P_S(
					group.getCompanyId(), group.getGroupId(), true) > 0) {

				throw new RequiredGroupException.MustNotDeleteGroupThatHasChild(
					group.getGroupId());
			}

			List<BackgroundTask> backgroundTasks =
				BackgroundTaskManagerUtil.getBackgroundTasks(
					group.getGroupId(),
					BackgroundTaskConstants.STATUS_IN_PROGRESS);

			if (!backgroundTasks.isEmpty()) {
				throw new PendingBackgroundTaskException(
					"Unable to delete group with pending background tasks");
			}

			// Background tasks

			BackgroundTaskManagerUtil.deleteGroupBackgroundTasks(
				group.getGroupId());

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

			// Membership requests

			membershipRequestLocalService.deleteMembershipRequests(
				group.getGroupId());

			// Portlet preferences

			portletPreferencesLocalService.deletePortletPreferences(
				group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				PortletKeys.PREFS_PLID_SHARED);

			// Repositories

			dlAppLocalService.deleteAllRepositories(group.getGroupId());

			// Teams

			teamLocalService.deleteTeams(group.getGroupId());

			// Staging

			exportImportConfigurationLocalService.
				deleteExportImportConfigurations(group.getGroupId());

			unscheduleStaging(group);

			if (group.hasStagingGroup()) {
				try {
					stagingLocalService.disableStaging(group, serviceContext);
				}
				catch (Exception e) {
					_log.error(
						"Unable to disable staging for group " +
							group.getGroupId());
				}
			}

			// Themes

			ThemeLoader themeLoader =
				ThemeLoaderFactory.getDefaultThemeLoader();

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

			assetEntryLocalService.deleteGroupEntries(group.getGroupId());

			assetTagLocalService.deleteGroupTags(group.getGroupId());

			assetVocabularyLocalService.deleteVocabularies(group.getGroupId());

			// Expando

			expandoRowLocalService.deleteRows(group.getGroupId());

			// Social

			socialActivityLocalService.deleteActivities(group.getGroupId());
			socialActivitySettingLocalService.deleteActivitySettings(
				group.getGroupId());
			socialRequestLocalService.deleteRequests(
				classNameLocalService.getClassNameId(Group.class),
				group.getGroupId());

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

			// Trash

			trashEntryLocalService.deleteEntries(group.getGroupId());

			// Workflow

			List<WorkflowHandler<?>> scopeableWorkflowHandlers =
				WorkflowHandlerRegistryUtil.getScopeableWorkflowHandlers();

			for (WorkflowHandler<?> scopeableWorkflowHandler :
					scopeableWorkflowHandlers) {

				if (!scopeableWorkflowHandler.isVisible()) {
					continue;
				}

				WorkflowDefinitionLink workflowDefinitionLink =
					workflowDefinitionLinkLocalService.
						fetchWorkflowDefinitionLink(
							group.getCompanyId(), group.getGroupId(),
							scopeableWorkflowHandler.getClassName(), 0, 0,
							true);

				if (workflowDefinitionLink == null) {
					continue;
				}

				workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
					workflowDefinitionLink);
			}

			// Group

			if (!group.isStagingGroup() && group.isOrganization() &&
				group.isSite()) {

				group.setSite(false);

				groupPersistence.update(group);

				// Group roles

				userGroupRoleLocalService.deleteUserGroupRoles(
					group.getGroupId(), RoleConstants.TYPE_SITE);

				// User group roles

				userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
					group.getGroupId(), RoleConstants.TYPE_SITE);
			}
			else {
				groupPersistence.remove(group);

				// Group roles

				userGroupRoleLocalService.deleteUserGroupRolesByGroupId(
					group.getGroupId());

				// User group roles

				userGroupGroupRoleLocalService.
					deleteUserGroupGroupRolesByGroupId(group.getGroupId());
			}

			// Permission cache

			PermissionCacheUtil.clearCache();

			return group;
		}
		finally {
			GroupThreadLocal.setDeleteInProcess(deleteInProcess);
		}
	}

	/**
	 * Deletes the group and its associated data.
	 *
	 * <p>
	 * The group is unstaged and its assets and resources including layouts,
	 * membership requests, subscriptions, teams, blogs, bookmarks, events,
	 * image gallery, journals, message boards, polls, shopping related
	 * entities, software catalog, and wikis are also deleted.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @return the deleted group
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group deleteGroup(long groupId) throws PortalException {
		Group group = groupPersistence.findByPrimaryKey(groupId);

		return deleteGroup(group);
	}

	@Override
	public synchronized void disableStaging(long groupId)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		int stagingGroupCount = group.getRemoteStagingGroupCount();

		if (stagingGroupCount > 0) {
			stagingGroupCount = stagingGroupCount - 1;

			group.setRemoteStagingGroupCount(stagingGroupCount);

			if (stagingGroupCount == 0) {
				UnicodeProperties typeSettingsProperties =
					group.getTypeSettingsProperties();

				List<String> keys = new ArrayList<>();

				for (String key : typeSettingsProperties.keySet()) {
					if (key.startsWith(StagingConstants.STAGED_PORTLET)) {
						keys.add(key);
					}
				}

				for (String key : keys) {
					typeSettingsProperties.remove(key);
				}

				group.setTypeSettingsProperties(typeSettingsProperties);
			}

			groupPersistence.update(group);
		}
	}

	@Override
	public synchronized void enableStaging(long groupId)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		int stagingGroupCount = group.getRemoteStagingGroupCount() + 1;

		group.setRemoteStagingGroupCount(stagingGroupCount);

		groupPersistence.update(group);
	}

	/**
	 * Returns the company's group.
	 *
	 * @param  companyId the primary key of the company
	 * @return the company's group, or <code>null</code> if a matching group
	 *         could not be found
	 */
	@Override
	public Group fetchCompanyGroup(long companyId) {
		long classNameId = classNameLocalService.getClassNameId(Company.class);

		return groupPersistence.fetchByC_C_C(companyId, classNameId, companyId);
	}

	/**
	 * Returns the group with the matching friendly URL.
	 *
	 * @param  companyId the primary key of the company
	 * @param  friendlyURL the friendly URL
	 * @return the group with the friendly URL, or <code>null</code> if a
	 *         matching group could not be found
	 */
	@Override
	public Group fetchFriendlyURLGroup(long companyId, String friendlyURL) {
		if (Validator.isNull(friendlyURL)) {
			return null;
		}

		friendlyURL = getFriendlyURL(friendlyURL);

		return groupPersistence.fetchByC_F(companyId, friendlyURL);
	}

	/**
	 * Returns the group with the matching group key by first searching the
	 * system groups and then using the finder cache.
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupKey the group key
	 * @return the group with the group key and associated company, or
	 *         <code>null</code> if a matching group could not be found
	 */
	@Override
	@Skip
	public Group fetchGroup(long companyId, String groupKey) {
		Group group = _systemGroupsMap.get(
			StringUtil.toHexString(companyId).concat(groupKey));

		if (group != null) {
			return group;
		}

		return groupLocalService.loadFetchGroup(companyId, groupKey);
	}

	@Override
	public Group fetchUserGroup(long companyId, long userId) {
		long classNameId = classNameLocalService.getClassNameId(User.class);

		return groupPersistence.fetchByC_C_C(companyId, classNameId, userId);
	}

	/**
	 * Returns the default user's personal site group.
	 *
	 * @param  companyId the primary key of the company
	 * @return the default user's personal site group, or <code>null</code> if a
	 *         matching group could not be found
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group fetchUserPersonalSiteGroup(long companyId)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(
			UserPersonalSite.class);
		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		return groupPersistence.fetchByC_C_C(
			companyId, classNameId, defaultUserId);
	}

	/**
	 * Returns all the active or inactive groups associated with the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  active whether to return only active groups, or only inactive
	 *         groups
	 * @return the active or inactive groups associated with the company
	 */
	@Override
	public List<Group> getActiveGroups(long companyId, boolean active) {
		return groupPersistence.findByC_A(companyId, active);
	}

	/**
	 * Returns the company group.
	 *
	 * @param  companyId the primary key of the company
	 * @return the group associated with the company
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group getCompanyGroup(long companyId) throws PortalException {
		long classNameId = classNameLocalService.getClassNameId(Company.class);

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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the range of groups associated with the company
	 */
	@Override
	public List<Group> getCompanyGroups(long companyId, int start, int end) {
		return groupPersistence.findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns the number of groups associated with the company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the number of groups associated with the company
	 */
	@Override
	public int getCompanyGroupsCount(long companyId) {
		return groupPersistence.countByCompanyId(companyId);
	}

	/**
	 * Returns the group with the matching friendly URL.
	 *
	 * @param  companyId the primary key of the company
	 * @param  friendlyURL the group's friendlyURL
	 * @return the group with the friendly URL
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group getFriendlyURLGroup(long companyId, String friendlyURL)
		throws PortalException {

		if (Validator.isNull(friendlyURL)) {
			StringBundler sb = new StringBundler(5);

			sb.append("{companyId=");
			sb.append(companyId);
			sb.append(", friendlyURL=");
			sb.append(friendlyURL);
			sb.append("}");

			throw new NoSuchGroupException(sb.toString());
		}

		friendlyURL = getFriendlyURL(friendlyURL);

		return groupPersistence.findByC_F(companyId, friendlyURL);
	}

	/**
	 * Returns the group with the matching primary key.
	 *
	 * @param  groupId the primary key of the group
	 * @return the group with the primary key
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	@ThreadLocalCachable
	public Group getGroup(long groupId) throws PortalException {
		return groupPersistence.findByPrimaryKey(groupId);
	}

	/**
	 * Returns the group with the matching group key.
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupKey the group key
	 * @return the group with the group key
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	@Skip
	public Group getGroup(long companyId, String groupKey)
		throws PortalException {

		Group group = _systemGroupsMap.get(
			StringUtil.toHexString(companyId).concat(groupKey));

		if (group != null) {
			return group;
		}

		return groupLocalService.loadGetGroup(companyId, groupKey);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             Group#getDescriptiveName(Locale)}
	 */
	@Deprecated
	@Override
	public String getGroupDescriptiveName(Group group, Locale locale)
		throws PortalException {

		return group.getDescriptiveName(locale);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             Group#getDescriptiveName(Locale)}
	 */
	@Deprecated
	@Override
	public String getGroupDescriptiveName(long groupId, Locale locale)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		return group.getDescriptiveName(locale);
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
		long companyId, long parentGroupId, boolean site) {

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			return groupPersistence.findByC_S(companyId, site);
		}

		return groupPersistence.findByC_P_S(companyId, parentGroupId, site);
	}

	@Override
	public List<Group> getGroups(
		long companyId, long parentGroupId, boolean site,
		boolean inheritContent) {

		return groupPersistence.findByC_P_S_I(
			companyId, parentGroupId, site, inheritContent);
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
	 */
	@Override
	public List<Group> getGroups(
		long companyId, String className, long parentGroupId) {

		long classNameId = classNameLocalService.getClassNameId(className);

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
	 */
	@Override
	public List<Group> getGroups(
		long companyId, String className, long parentGroupId, int start,
		int end) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return groupPersistence.findByC_C_P(
			companyId, classNameId, parentGroupId, start, end);
	}

	/**
	 * Returns the groups with the matching primary keys.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @return the groups with the primary keys
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<Group> getGroups(long[] groupIds) throws PortalException {
		List<Group> groups = new ArrayList<>(groupIds.length);

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
	 */
	@Override
	public int getGroupsCount(
		long companyId, long parentGroupId, boolean site) {

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
	 */
	@Override
	public int getGroupsCount(
		long companyId, String className, long parentGroupId) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return groupPersistence.countByC_C_P(
			companyId, classNameId, parentGroupId);
	}

	/**
	 * Returns the group associated with the layout.
	 *
	 * @param  companyId the primary key of the company
	 * @param  plid the primary key of the layout
	 * @return the group associated with the layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group getLayoutGroup(long companyId, long plid)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(Layout.class);

		return groupPersistence.findByC_C_C(companyId, classNameId, plid);
	}

	/**
	 * Returns the group associated with the layout prototype.
	 *
	 * @param  companyId the primary key of the company
	 * @param  layoutPrototypeId the primary key of the layout prototype
	 * @return the group associated with the layout prototype
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group getLayoutPrototypeGroup(long companyId, long layoutPrototypeId)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(
			LayoutPrototype.class);

		return groupPersistence.findByC_C_C(
			companyId, classNameId, layoutPrototypeId);
	}

	/**
	 * Returns the group associated with the layout set prototype.
	 *
	 * @param  companyId the primary key of the company
	 * @param  layoutSetPrototypeId the primary key of the layout set prototype
	 * @return the group associated with the layout set prototype
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group getLayoutSetPrototypeGroup(
			long companyId, long layoutSetPrototypeId)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(
			LayoutSetPrototype.class);

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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param      companyId the primary key of the company
	 * @param      parentGroupId the primary key of the parent group
	 * @param      site whether the group is to be associated with a main site
	 * @param      start the lower bound of the range of groups to return
	 * @param      end the upper bound of the range of groups to return (not
	 *             inclusive)
	 * @return     the range of matching groups
	 * @deprecated As of 6.2.0, replaced by {@link #getLayoutsGroups(long, long,
	 *             boolean, int, int, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public List<Group> getLayoutsGroups(
		long companyId, long parentGroupId, boolean site, int start, int end) {

		return getLayoutsGroups(
			companyId, parentGroupId, site, start, end, null);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  site whether the group is to be associated with a main site
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the range of matching groups ordered by comparator
	 *         <code>obc</code>
	 */
	@Override
	public List<Group> getLayoutsGroups(
		long companyId, long parentGroupId, boolean site, int start, int end,
		OrderByComparator<Group> obc) {

		return groupFinder.findByLayouts(
			companyId, parentGroupId, site, start, end, obc);
	}

	/**
	 * Returns the number of groups that are children or the parent group and
	 * that have at least one layout
	 *
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  site whether the group is to be associated with a main site
	 * @return the number of matching groups
	 */
	@Override
	public int getLayoutsGroupsCount(
		long companyId, long parentGroupId, boolean site) {

		return groupFinder.countByLayouts(companyId, parentGroupId, site);
	}

	/**
	 * Returns all live groups.
	 *
	 * @return all live groups
	 */
	@Override
	public List<Group> getLiveGroups() {
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 */
	@Override
	public List<Group> getNoLayoutsGroups(
		String className, boolean privateLayout, int start, int end) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return groupFinder.findByNoLayouts(
			classNameId, privateLayout, start, end);
	}

	/**
	 * Returns all non-system groups having <code>null</code> or empty friendly
	 * URLs.
	 *
	 * @return the non-system groups having <code>null</code> or empty friendly
	 *         URLs
	 */
	@Override
	public List<Group> getNullFriendlyURLGroups() {
		return groupFinder.findByNullFriendlyURL();
	}

	/**
	 * Returns the specified organization group.
	 *
	 * @param  companyId the primary key of the company
	 * @param  organizationId the primary key of the organization
	 * @return the group associated with the organization
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group getOrganizationGroup(long companyId, long organizationId)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(
			Organization.class);

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

		List<Group> organizationGroups = new ArrayList<>();

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
	 */
	@Override
	public List<Group> getOrganizationsRelatedGroups(
		List<Organization> organizations) {

		List<Group> organizationGroups = new ArrayList<>();

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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<Group> getParentGroups(long groupId) throws PortalException {
		if (groupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			return new ArrayList<>();
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		return group.getAncestors();
	}

	/**
	 * Returns the staging group.
	 *
	 * @param  liveGroupId the primary key of the live group
	 * @return the staging group
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group getStagingGroup(long liveGroupId) throws PortalException {
		return groupPersistence.findByLiveGroupId(liveGroupId);
	}

	/**
	 * Returns the group directly associated with the user.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @return the group directly associated with the user
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group getUserGroup(long companyId, long userId)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(User.class);

		return groupPersistence.findByC_C_C(companyId, classNameId, userId);
	}

	/**
	 * Returns the specified "user group" group. That is, the group that
	 * represents the {@link UserGroup} entity.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userGroupId the primary key of the user group
	 * @return the group associated with the user group
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group getUserGroupGroup(long companyId, long userGroupId)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(
			UserGroup.class);

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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<Group> getUserGroups(long userId, boolean inherit)
		throws PortalException {

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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<Group> getUserGroups(
			long userId, boolean inherit, int start, int end)
		throws PortalException {

		if (inherit) {
			User user = userPersistence.findByPrimaryKey(userId);

			LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

			groupParams.put("usersGroups", Long.valueOf(userId));

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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<Group> getUserGroupsGroups(List<UserGroup> userGroups)
		throws PortalException {

		List<Group> userGroupGroups = new ArrayList<>();

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
	 */
	@Override
	public List<Group> getUserGroupsRelatedGroups(List<UserGroup> userGroups) {
		List<Group> userGroupGroups = new ArrayList<>();

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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  start the lower bound of the range of groups to consider
	 * @param  end the upper bound of the range of groups to consider (not
	 *         inclusive)
	 * @return the range of groups associated with the user's organization
	 *         groups
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<Group> getUserOrganizationsGroups(
			long userId, int start, int end)
		throws PortalException {

		List<Group> userOrgsGroups = new ArrayList<>();

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

		return ListUtil.unique(userOrgsGroups);
	}

	/**
	 * Returns the default user's personal site group.
	 *
	 * @param  companyId the primary key of the company
	 * @return the default user's personal site group
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group getUserPersonalSiteGroup(long companyId)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(
			UserPersonalSite.class);
		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		return groupPersistence.findByC_C_C(
			companyId, classNameId, defaultUserId);
	}

	@Override
	public List<Group> getUserSitesGroups(long userId) throws PortalException {
		User user = userPersistence.findByPrimaryKey(userId);

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("inherit", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);
		groupParams.put("usersGroups", userId);

		return groupFinder.findByCompanyId(
			user.getCompanyId(), groupParams, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new GroupNameComparator(true));
	}

	@Override
	public List<Group> getUserSitesGroups(
			long userId, boolean includeAdministrative)
		throws PortalException {

		if (!includeAdministrative) {
			return getUserSitesGroups(userId);
		}

		Set<Group> sites = new HashSet<>();

		List<UserGroupRole> userGroupRoles =
			userGroupRoleLocalService.getUserGroupRoles(userId);

		for (UserGroupRole userGroupRole : userGroupRoles) {
			Role role = userGroupRole.getRole();

			String roleName = role.getName();

			if (roleName.equals(RoleConstants.SITE_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.SITE_OWNER)) {

				Group group = userGroupRole.getGroup();

				sites.add(group);
			}
		}

		sites.addAll(getUserSitesGroups(userId));

		return new ArrayList<>(sites);
	}

	/**
	 * Returns <code>true</code> if the live group has a staging group.
	 *
	 * @param  liveGroupId the primary key of the live group
	 * @return <code>true</code> if the live group has a staging group;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean hasStagingGroup(long liveGroupId) {
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
	 */
	@Override
	public boolean hasUserGroup(long userId, long groupId) {
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
	 */
	@Override
	public boolean hasUserGroup(long userId, long groupId, boolean inherit) {
		if (groupFinder.countByG_U(groupId, userId, inherit) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns the group with the matching group key by first searching the
	 * system groups and then using the finder cache.
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupKey the group key
	 * @return the group with the group key and associated company, or
	 *         <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group loadFetchGroup(long companyId, String groupKey) {
		return groupPersistence.fetchByC_GK(companyId, groupKey);
	}

	/**
	 * Returns the group with the matching group key.
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupKey the group key
	 * @return the group with the group key and associated company
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group loadGetGroup(long companyId, String groupKey)
		throws PortalException {

		return groupPersistence.findByC_GK(companyId, groupKey);
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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void rebuildTree(long companyId) throws PortalException {
		TreePathUtil.rebuildTree(
			companyId, GroupConstants.DEFAULT_PARENT_GROUP_ID, StringPool.SLASH,
			new TreeModelTasksAdapter<Group>() {

				@Override
				public List<Group> findTreeModels(
					long previousId, long companyId, long parentPrimaryKey,
					int size) {

					return groupPersistence.findByG_C_P(
						previousId, companyId, parentPrimaryKey,
						QueryUtil.ALL_POS, size, new GroupIdComparator(true));
				}

			}
		);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 */
	@Override
	public List<Group> search(
		long companyId, LinkedHashMap<String, Object> params, int start,
		int end) {

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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 */
	@Override
	public List<Group> search(
		long companyId, long parentGroupId, String keywords,
		LinkedHashMap<String, Object> params, int start, int end) {

		return search(
			companyId, getClassNameIds(), parentGroupId, keywords, params,
			start, end, null);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 */
	@Override
	public List<Group> search(
		long companyId, long parentGroupId, String keywords,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Group> obc) {

		return search(
			companyId, getClassNameIds(), parentGroupId, keywords, params,
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 */
	@Override
	public List<Group> search(
		long companyId, long parentGroupId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end) {

		return search(
			companyId, getClassNameIds(), parentGroupId, name, description,
			params, andOperator, start, end, null);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 */
	@Override
	public List<Group> search(
		long companyId, long parentGroupId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<Group> obc) {

		return search(
			companyId, getClassNameIds(), parentGroupId, name, description,
			params, andOperator, start, end, obc);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 */
	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, long parentGroupId,
		String keywords, LinkedHashMap<String, Object> params, int start,
		int end) {

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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 */
	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, long parentGroupId,
		String keywords, LinkedHashMap<String, Object> params, int start,
		int end, OrderByComparator<Group> obc) {

		String[] keywordsArray = getSearchNames(companyId, keywords);

		boolean andOperator = false;

		if (Validator.isNull(keywords)) {
			andOperator = true;
		}

		if (isUseComplexSQL(classNameIds)) {
			return groupFinder.findByC_C_PG_N_D(
				companyId, classNameIds, parentGroupId, keywordsArray,
				keywordsArray, params, andOperator, start, end, obc);
		}

		Collection<Group> groups = doSearch(
			companyId, classNameIds, parentGroupId, keywordsArray,
			keywordsArray, params, andOperator);

		return sort(groups, start, end, obc);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 */
	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, long parentGroupId, String name,
		String description, LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end) {

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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 */
	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, long parentGroupId, String name,
		String description, LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end, OrderByComparator<Group> obc) {

		String[] names = getSearchNames(companyId, name);
		String[] descriptions = CustomSQLUtil.keywords(description);

		if (isUseComplexSQL(classNameIds)) {
			return groupFinder.findByC_C_PG_N_D(
				companyId, classNameIds, parentGroupId, names, descriptions,
				params, andOperator, start, end, obc);
		}

		Collection<Group> groups = doSearch(
			companyId, classNameIds, parentGroupId, names, descriptions, params,
			andOperator);

		return sort(groups, start, end, obc);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 */
	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, String keywords,
		LinkedHashMap<String, Object> params, int start, int end) {

		return search(
			companyId, classNameIds, GroupConstants.ANY_PARENT_GROUP_ID,
			keywords, params, start, end, null);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 */
	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, String keywords,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Group> obc) {

		return search(
			companyId, classNameIds, GroupConstants.ANY_PARENT_GROUP_ID,
			keywords, params, start, end, obc);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 */
	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end) {

		return search(
			companyId, classNameIds, GroupConstants.ANY_PARENT_GROUP_ID, name,
			description, params, andOperator, start, end, null);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 */
	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<Group> obc) {

		return search(
			companyId, classNameIds, GroupConstants.ANY_PARENT_GROUP_ID, name,
			description, params, andOperator, start, end, obc);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 */
	@Override
	@ThreadLocalCachable
	public List<Group> search(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end) {

		return search(
			companyId, getClassNameIds(), GroupConstants.ANY_PARENT_GROUP_ID,
			keywords, params, start, end, null);
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 */
	@Override
	public List<Group> search(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, OrderByComparator<Group> obc) {

		return search(
			companyId, getClassNameIds(), GroupConstants.ANY_PARENT_GROUP_ID,
			keywords, params, start, end, obc);
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
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @return the matching groups ordered by name
	 */
	@Override
	public List<Group> search(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end) {

		return search(
			companyId, getClassNameIds(), GroupConstants.ANY_PARENT_GROUP_ID,
			name, description, params, andOperator, start, end, null);
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
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the matching groups ordered by comparator <code>obc</code>
	 */
	@Override
	public List<Group> search(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<Group> obc) {

		return search(
			companyId, getClassNameIds(), GroupConstants.ANY_PARENT_GROUP_ID,
			name, description, params, andOperator, start, end, obc);
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @return the number of matching groups
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
		long companyId, long parentGroupId, String keywords,
		LinkedHashMap<String, Object> params) {

		return searchCount(
			companyId, getClassNameIds(), parentGroupId, keywords, params);
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @return the number of matching groups
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
		long companyId, long parentGroupId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator) {

		return searchCount(
			companyId, getClassNameIds(), parentGroupId, name, description,
			params, andOperator);
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @return the number of matching groups
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
		long companyId, long[] classNameIds, long parentGroupId,
		String keywords, LinkedHashMap<String, Object> params) {

		String[] keywordsArray = getSearchNames(companyId, keywords);

		boolean andOperator = false;

		if (Validator.isNull(keywords)) {
			andOperator = true;
		}

		if (isUseComplexSQL(classNameIds)) {
			return groupFinder.countByC_C_PG_N_D(
				companyId, classNameIds, parentGroupId, keywordsArray,
				keywordsArray, params, andOperator);
		}

		Collection<Group> groups = doSearch(
			companyId, classNameIds, parentGroupId, keywordsArray,
			keywordsArray, params, andOperator);

		return groups.size();
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @return the number of matching groups
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
		long companyId, long[] classNameIds, long parentGroupId, String name,
		String description, LinkedHashMap<String, Object> params,
		boolean andOperator) {

		String[] names = getSearchNames(companyId, name);
		String[] descriptions = CustomSQLUtil.keywords(description);

		if (isUseComplexSQL(classNameIds)) {
			return groupFinder.countByC_C_PG_N_D(
				companyId, classNameIds, parentGroupId, names, descriptions,
				params, andOperator);
		}

		Collection<Group> groups = doSearch(
			companyId, classNameIds, parentGroupId, names, descriptions, params,
			andOperator);

		return groups.size();
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @return the number of matching groups
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
		long companyId, long[] classNameIds, String keywords,
		LinkedHashMap<String, Object> params) {

		return searchCount(
			companyId, classNameIds, GroupConstants.ANY_PARENT_GROUP_ID,
			keywords, params);
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @return the number of matching groups
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
		long companyId, long[] classNameIds, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator) {

		return searchCount(
			companyId, classNameIds, GroupConstants.ANY_PARENT_GROUP_ID, name,
			description, params, andOperator);
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @return the number of matching groups
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
		long companyId, String keywords, LinkedHashMap<String, Object> params) {

		return searchCount(
			companyId, getClassNameIds(), GroupConstants.ANY_PARENT_GROUP_ID,
			keywords, params);
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
	 *         com.liferay.portal.service.persistence.GroupFinder}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @return the number of matching groups
	 */
	@Override
	@ThreadLocalCachable
	public int searchCount(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator) {

		return searchCount(
			companyId, getClassNameIds(), GroupConstants.ANY_PARENT_GROUP_ID,
			name, description, params, andOperator);
	}

	/**
	 * Sets the groups associated with the role, removing and adding
	 * associations as necessary.
	 *
	 * @param roleId the primary key of the role
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void setRoleGroups(long roleId, long[] groupIds) {
		rolePersistence.setGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Removes the groups from the role.
	 *
	 * @param roleId the primary key of the role
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void unsetRoleGroups(long roleId, long[] groupIds) {
		rolePersistence.removeGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Removes the user from the groups.
	 *
	 * @param userId the primary key of the user
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void unsetUserGroups(long userId, long[] groupIds) {
		userGroupRoleLocalService.deleteUserGroupRoles(userId, groupIds);

		userPersistence.removeGroups(userId, groupIds);

		PermissionCacheUtil.clearCache(userId);
	}

	/**
	 * Updates the group's asset replacing categories and tag names.
	 *
	 * @param  userId the primary key of the user
	 * @param  group the group
	 * @param  assetCategoryIds the primary keys of the asset categories
	 *         (optionally <code>null</code>)
	 * @param  assetTagNames the asset tag names (optionally <code>null</code>)
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void updateAsset(
			long userId, Group group, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		Company company = companyPersistence.findByPrimaryKey(
			user.getCompanyId());

		Group companyGroup = company.getGroup();

		assetEntryLocalService.updateEntry(
			userId, companyGroup.getGroupId(), null, null,
			Group.class.getName(), group.getGroupId(), null, 0,
			assetCategoryIds, assetTagNames, false, null, null, null, null,
			group.getDescriptiveName(), group.getDescription(), null, null,
			null, 0, 0, null);
	}

	/**
	 * Updates the group's friendly URL.
	 *
	 * @param  groupId the primary key of the group
	 * @param  friendlyURL the group's new friendlyURL (optionally
	 *         <code>null</code>)
	 * @return the group
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group updateFriendlyURL(long groupId, String friendlyURL)
		throws PortalException {

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

	@Override
	public Group updateGroup(
			long groupId, long parentGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean inheritContent, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		String className = group.getClassName();
		long classNameId = group.getClassNameId();
		long classPK = group.getClassPK();

		String groupKey = group.getGroupKey();

		if ((nameMap != null) &&
			Validator.isNotNull(nameMap.get(LocaleUtil.getDefault()))) {

			groupKey = nameMap.get(LocaleUtil.getDefault());
		}

		friendlyURL = getFriendlyURL(
			group.getCompanyId(), groupId, classNameId, classPK,
			StringPool.BLANK, friendlyURL);

		if ((classNameId <= 0) || className.equals(Group.class.getName())) {
			validateGroupKey(
				group.getGroupId(), group.getCompanyId(), groupKey,
				group.isSite());
		}
		else if (className.equals(Organization.class.getName())) {
			Organization organization =
				organizationPersistence.findByPrimaryKey(classPK);

			groupKey = getOrgGroupName(organization.getName());
		}
		else if (!GroupConstants.USER_PERSONAL_SITE.equals(
					group.getGroupKey())) {

			groupKey = String.valueOf(classPK);
		}

		if (PortalUtil.isSystemGroup(group.getGroupKey()) &&
			!groupKey.equals(group.getGroupKey())) {

			throw new RequiredGroupException.MustNotDeleteSystemGroup(
				group.getGroupId());
		}

		validateFriendlyURL(
			group.getCompanyId(), group.getGroupId(), group.getClassNameId(),
			group.getClassPK(), friendlyURL);

		validateParentGroup(group.getGroupId(), parentGroupId);

		group.setParentGroupId(parentGroupId);
		group.setTreePath(group.buildTreePath());
		group.setGroupKey(groupKey);
		group.setNameMap(nameMap);
		group.setDescriptionMap(descriptionMap);
		group.setType(type);
		group.setManualMembership(manualMembership);
		group.setMembershipRestriction(membershipRestriction);
		group.setFriendlyURL(friendlyURL);
		group.setInheritContent(inheritContent);
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

		user = userPersistence.fetchByPrimaryKey(group.getCreatorUserId());

		if (user == null) {
			user = userPersistence.fetchByPrimaryKey(
				serviceContext.getUserId());
		}

		if (user == null) {
			user = userLocalService.getDefaultUser(group.getCompanyId());
		}

		updateAsset(
			user.getUserId(), group, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		return group;
	}

	/**
	 * Updates the group.
	 *
	 * @param      groupId the primary key of the group
	 * @param      parentGroupId the primary key of the parent group
	 * @param      name the name's key
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
	 * @param      inheritContent whether to inherit content from the parent
	 *             group
	 * @param      active whether the group is active
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs and asset tag
	 *             names for the group.
	 * @return     the group
	 * @throws     PortalException if a portal exception occurred
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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group updateGroup(long groupId, String typeSettings)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		UnicodeProperties oldTypeSettingsProperties =
			group.getTypeSettingsProperties();

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		String newLanguageIds = typeSettingsProperties.getProperty(
			PropsKeys.LOCALES);

		if (Validator.isNotNull(newLanguageIds)) {
			String oldLanguageIds = oldTypeSettingsProperties.getProperty(
				PropsKeys.LOCALES, StringPool.BLANK);

			String defaultLanguageId = typeSettingsProperties.getProperty(
				"languageId", LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

			validateLanguageIds(defaultLanguageId, newLanguageIds);

			if (!Validator.equals(oldLanguageIds, newLanguageIds)) {
				LanguageUtil.resetAvailableGroupLocales(groupId);
			}
		}

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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Group updateSite(long groupId, boolean site) throws PortalException {
		Group group = groupPersistence.findByPrimaryKey(groupId);

		if (!group.isOrganization()) {
			return group;
		}

		group.setSite(site);

		groupPersistence.update(group);

		return group;
	}

	protected void addControlPanelLayouts(Group group) throws PortalException {
		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		String friendlyURL = getFriendlyURL(
			PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		layoutLocalService.addLayout(
			defaultUserId, group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			PropsValues.CONTROL_PANEL_LAYOUT_NAME, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_CONTROL_PANEL, false,
			friendlyURL, serviceContext);
	}

	protected void addDefaultGuestPublicLayoutByProperties(Group group)
		throws PortalException {

		List<Portlet> portlets = portletLocalService.getPortlets(
			group.getCompanyId());

		if (portlets.isEmpty()) {

			// LPS-38457

			return;
		}

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

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		for (String columnId : layoutTemplate.getColumns()) {
			String keyPrefix = PropsKeys.DEFAULT_GUEST_PUBLIC_LAYOUT_PREFIX;

			String portletIds = PropsUtil.get(keyPrefix.concat(columnId));

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
		throws PortalException {

		if (publicLARFile != null) {
			addDefaultGuestPublicLayoutsByLAR(group, publicLARFile);
		}
		else {
			addDefaultGuestPublicLayoutByProperties(group);
		}
	}

	protected void addDefaultGuestPublicLayoutsByLAR(Group group, File larFile)
		throws PortalException {

		User defaultUser = userLocalService.getDefaultUser(
			group.getCompanyId());

		Map<String, String[]> parameterMap = new HashMap<>();

		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.TRUE.toString()});

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildImportLayoutSettingsMap(
					defaultUser, group.getGroupId(), false, null, parameterMap);

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					defaultUser.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		exportImportLocalService.importLayouts(
			exportImportConfiguration, larFile);
	}

	protected void addPortletDefaultData(Group group) throws PortalException {
		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createPreparePortletDataContext(
				group.getCompanyId(), group.getGroupId(), null, null);

		List<PortletDataHandler> portletDataHandlers = getPortletDataHandlers(
			group);

		for (PortletDataHandler portletDataHandler : portletDataHandlers) {
			try {
				portletDataHandler.addDefaultData(
					portletDataContext, portletDataHandler.getPortletId(),
					null);
			}
			catch (Exception e) {
				_log.error(
					"Unable to add default data for portlet " +
						portletDataHandler.getPortletId() + " in group " +
							group.getGroupId());

				if (portletDataHandler.isRollbackOnException()) {
					throw new SystemException(e);
				}
			}
		}
	}

	protected void deletePortletData(Group group) throws PortalException {
		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createPreparePortletDataContext(
				group.getCompanyId(), group.getGroupId(), null, null);

		List<PortletDataHandler> portletDataHandlers = getPortletDataHandlers(
			group);

		for (PortletDataHandler portletDataHandler : portletDataHandlers) {
			try {
				portletDataHandler.deleteData(
					portletDataContext, portletDataHandler.getPortletId(),
					null);
			}
			catch (Exception e) {
				_log.error(
					"Unable to delete data for portlet " +
						portletDataHandler.getPortletId() + " in group " +
							group.getGroupId());

				if (portletDataHandler.isRollbackOnException()) {
					throw new SystemException(e);
				}
			}
		}
	}

	protected Collection<Group> doSearch(
		long companyId, long[] classNameIds, long parentGroupId, String[] names,
		String[] descriptions, LinkedHashMap<String, Object> params,
		boolean andOperator) {

		boolean parentGroupIdEquals = true;

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			parentGroupIdEquals = false;
		}

		params = new LinkedHashMap<>(params);

		Boolean active = (Boolean)params.remove("active");
		List<Long> excludedGroupIds = (List<Long>)params.remove(
			"excludedGroupIds");
		List<Group> groupsTree = (List<Group>)params.remove("groupsTree");
		Boolean manualMembership = (Boolean)params.remove("manualMembership");
		Integer membershipRestriction = (Integer)params.remove(
			"membershipRestriction");
		Boolean site = (Boolean)params.remove("site");
		List<Integer> types = (List<Integer>)params.remove("types");

		Collection<Group> groups = new HashSet<>();

		Long userId = (Long)params.remove("usersGroups");

		for (long classNameId : classNameIds) {
			groups.addAll(groupPersistence.findByC_C(companyId, classNameId));
		}

		Iterator<Group> iterator = groups.iterator();

		while (iterator.hasNext()) {
			Group group = iterator.next();

			// Filter by live group ID

			long liveGroupId = group.getLiveGroupId();

			if (liveGroupId != 0) {
				iterator.remove();

				continue;
			}

			// Filter by parent group ID

			long groupParentGroupId = group.getParentGroupId();

			if ((parentGroupIdEquals &&
				 (groupParentGroupId != parentGroupId)) ||
				(!parentGroupIdEquals &&
				 (groupParentGroupId == parentGroupId))) {

				iterator.remove();

				continue;
			}

			// Filter by name and description

			String groupKey = group.getGroupKey();

			if (groupKey.equals(GroupConstants.CONTROL_PANEL)) {
				iterator.remove();

				continue;
			}

			boolean containsName = matches(group.getNameCurrentValue(), names);
			boolean containsDescription = matches(
				group.getDescriptionCurrentValue(), descriptions);

			if ((andOperator && (!containsName || !containsDescription)) ||
				(!andOperator && !containsName && !containsDescription)) {

				iterator.remove();

				continue;
			}

			// Filter by active

			if (active != null) {
				if (active != group.isActive()) {
					iterator.remove();

					continue;
				}
			}

			// Filter by excluded group IDs

			if ((excludedGroupIds != null) &&
				excludedGroupIds.contains(group.getGroupId())) {

				iterator.remove();

				continue;
			}

			// Filter by groups tree

			if (groupsTree != null) {
				String treePath = group.getTreePath();

				boolean matched = false;

				for (Group groupTree : groupsTree) {
					String groupTreePath = StringUtil.quote(
						String.valueOf(groupTree.getGroupId()),
						StringPool.SLASH);

					if (treePath.contains(groupTreePath)) {
						matched = true;

						break;
					}
				}

				if (!matched) {
					iterator.remove();

					continue;
				}
			}

			// Filter by manual membership

			if ((manualMembership != null) &&
				(manualMembership != group.isManualMembership())) {

				iterator.remove();

				continue;
			}

			// Filter by membership restriction

			if ((membershipRestriction != null) &&
				(membershipRestriction != group.getMembershipRestriction())) {

				iterator.remove();

				continue;
			}

			// Filter by site

			if (site != null) {
				if (site != group.isSite()) {
					iterator.remove();

					continue;
				}
			}

			// Filter by type and types

			int type = group.getType();

			if (type == 4) {
				iterator.remove();

				continue;
			}

			if ((types != null) && !types.contains(type)) {
				iterator.remove();

				continue;
			}
		}

		// Join by role permissions

		RolePermissions rolePermissions = (RolePermissions)params.remove(
			"rolePermissions");

		if (rolePermissions != null) {
			ResourceAction resourceAction =
				resourceActionLocalService.fetchResourceAction(
					rolePermissions.getName(), rolePermissions.getActionId());

			if (resourceAction != null) {
				Set<Group> rolePermissionsGroups = new HashSet<>();

				if (resourceBlockLocalService.isSupported(
						rolePermissions.getName())) {

					List<ResourceTypePermission> resourceTypePermissions =
						resourceTypePermissionPersistence.findByRoleId(
							rolePermissions.getRoleId());

					for (ResourceTypePermission resourceTypePermission :
							resourceTypePermissions) {

						if ((resourceTypePermission.getCompanyId() ==
								companyId) &&
							Validator.equals(
								rolePermissions.getName(),
								resourceTypePermission.getName()) &&
							resourceTypePermission.hasAction(resourceAction)) {

							Group group = groupPersistence.fetchByPrimaryKey(
								resourceTypePermission.getGroupId());

							if (group != null) {
								rolePermissionsGroups.add(group);
							}
						}
					}
				}
				else {
					List<ResourcePermission> resourcePermissions =
						resourcePermissionPersistence.findByC_N_S(
							companyId, rolePermissions.getName(),
							rolePermissions.getScope());

					for (ResourcePermission resourcePermission :
							resourcePermissions) {

						if ((resourcePermission.getRoleId() ==
								rolePermissions.getRoleId()) &&
							resourcePermission.hasAction(
								resourceAction)) {

							Group group = groupPersistence.fetchByPrimaryKey(
								GetterUtil.getLong(
									resourcePermission.getPrimKey()));

							if (group != null) {
								rolePermissionsGroups.add(group);
							}
						}
					}
				}

				groups.retainAll(rolePermissionsGroups);
			}
		}

		// Join by Groups_Roles

		Long roleId = (Long)params.remove("groupsRoles");

		if (roleId != null) {
			groups.retainAll(rolePersistence.getGroups(roleId));
		}

		if (userId == null) {
			return groups;
		}

		// Join by Users_Groups

		Set<Group> joinedGroups = new HashSet<>(
			userPersistence.getGroups(userId));

		boolean inherit = GetterUtil.getBoolean(params.remove("inherit"), true);

		if (inherit) {

			// Join by Users_Orgs

			long[] organizationIds = userPersistence.getOrganizationPrimaryKeys(
				userId);

			for (long organizationId : organizationIds) {
				for (Group group : groups) {
					if (organizationId == group.getClassPK()) {
						joinedGroups.add(group);
					}
				}
			}

			// Join by Groups_Orgs and Users_Orgs

			for (long organizationId : organizationIds) {
				joinedGroups.addAll(
					organizationPersistence.getGroups(organizationId));
			}

			// Join by Groups_UserGroups and Users_UserGroups

			long[] userGroupIds = userPersistence.getUserGroupPrimaryKeys(
				userId);

			for (long userGroupId : userGroupIds) {
				joinedGroups.addAll(
					userGroupPersistence.getGroups(userGroupId));
			}
		}

		if (_log.isDebugEnabled() && !params.isEmpty()) {
			_log.debug("Unprocessed parameters " + MapUtil.toString(params));
		}

		if (joinedGroups.size() > groups.size()) {
			groups.retainAll(joinedGroups);

			return groups;
		}
		else {
			joinedGroups.retainAll(groups);

			return joinedGroups;
		}
	}

	protected long[] getClassNameIds() {
		if (_classNameIds == null) {
			_classNameIds = new long[] {
				classNameLocalService.getClassNameId(Group.class),
				classNameLocalService.getClassNameId(Organization.class)
			};
		}

		return _classNameIds;
	}

	protected String getFriendlyURL(
			long companyId, long groupId, long classNameId, long classPK,
			String friendlyName, String friendlyURL)
		throws PortalException {

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

	protected List<PortletDataHandler> getPortletDataHandlers(Group group) {
		List<Portlet> portlets = portletLocalService.getPortlets(
			group.getCompanyId());

		List<PortletDataHandler> portletDataHandlers = new ArrayList<>(
			portlets.size());

		for (Portlet portlet : portlets) {
			if (!portlet.isActive()) {
				continue;
			}

			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			if ((portletDataHandler != null) &&
				!portletDataHandler.isDataPortalLevel()) {

				portletDataHandlers.add(portletDataHandler);
			}
		}

		return portletDataHandlers;
	}

	protected String[] getSearchNames(long companyId, String name) {
		if (Validator.isNull(name)) {
			return new String[] {null};
		}

		Company company = companyPersistence.fetchByPrimaryKey(companyId);

		if (company == null) {
			return CustomSQLUtil.keywords(name);
		}

		Account account = accountPersistence.fetchByPrimaryKey(
			company.getAccountId());

		if (account == null) {
			return CustomSQLUtil.keywords(name);
		}

		String companyName = account.getName();

		if (StringUtil.wildcardMatches(
				companyName, name, CharPool.UNDERLINE, CharPool.PERCENT,
				CharPool.BACK_SLASH, false)) {

			String[] searchNames = CustomSQLUtil.keywords(name);

			String guestName = StringUtil.quote(
				StringUtil.toLowerCase(GroupConstants.GUEST),
				StringPool.PERCENT);

			return ArrayUtil.append(searchNames, guestName);
		}

		return CustomSQLUtil.keywords(name);
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
		throws PortalException {

		// User role

		Role role = roleLocalService.getRole(
			group.getCompanyId(), RoleConstants.USER);

		setRolePermissions(
			group, role, Layout.class.getName(),
			new String[] {ActionKeys.VIEW});

		setRolePermissions(
			group, role, "com.liferay.portlet.blogs",
			new String[] {
				ActionKeys.ADD_ENTRY, ActionKeys.PERMISSIONS,
				ActionKeys.SUBSCRIBE
			});

		// Power User role

		role = roleLocalService.getRole(
			group.getCompanyId(), RoleConstants.POWER_USER);

		List<Portlet> portlets = portletLocalService.getPortlets(
			group.getCompanyId(), false, false);

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
			new String[] {
				ActionKeys.MANAGE_LAYOUTS, ActionKeys.VIEW_SITE_ADMINISTRATION
			});

		setRolePermissions(group, role, "com.liferay.portlet.asset");
		setRolePermissions(group, role, "com.liferay.portlet.blogs");
		setRolePermissions(group, role, "com.liferay.portlet.bookmarks");
		setRolePermissions(group, role, "com.liferay.portlet.documentlibrary");
		setRolePermissions(group, role, "com.liferay.portlet.imagegallery");
		setRolePermissions(group, role, "com.liferay.portlet.journal");
		setRolePermissions(group, role, "com.liferay.portlet.messageboards");
		setRolePermissions(group, role, "com.liferay.portlet.wiki");
	}

	protected boolean isParentGroup(long parentGroupId, long groupId)
		throws PortalException {

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

	protected boolean isUseComplexSQL(long[] classNameIds) {
		if (ArrayUtil.isEmpty(classNameIds)) {
			return true;
		}

		if (_complexSQLClassNameIds == null) {
			String[] complexSQLClassNames =
				PropsValues.GROUPS_COMPLEX_SQL_CLASS_NAMES;

			long[] complexSQLClassNameIds =
				new long[complexSQLClassNames.length];

			for (int i = 0; i < complexSQLClassNames.length; i++) {
				String complexSQLClassName = complexSQLClassNames[i];

				complexSQLClassNameIds[i] =
					classNameLocalService.getClassNameId(complexSQLClassName);
			}

			_complexSQLClassNameIds = complexSQLClassNameIds;
		}

		for (long classNameId : classNameIds) {
			if (ArrayUtil.contains(_complexSQLClassNameIds, classNameId)) {
				return true;
			}
		}

		return false;
	}

	protected boolean matches(String s, String[] keywords) {
		if ((keywords == null) ||
			((keywords.length == 1) && (keywords[0] == null))) {

			return true;
		}

		for (String keyword : keywords) {
			if (StringUtil.wildcardMatches(
					s, keyword, CharPool.UNDERLINE, CharPool.PERCENT,
					CharPool.BACK_SLASH, false)) {

				return true;
			}
		}

		return false;
	}

	protected void setCompanyPermissions(
			Role role, String name, String[] actionIds)
		throws PortalException {

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
		throws PortalException {

		List<String> actions = ResourceActionsUtil.getModelResourceActions(
			name);

		setRolePermissions(
			group, role, name, actions.toArray(new String[actions.size()]));
	}

	protected void setRolePermissions(
			Group group, Role role, String name, String[] actionIds)
		throws PortalException {

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

	protected List<Group> sort(
		Collection<Group> groups, int start, int end,
		OrderByComparator<Group> obc) {

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		List<Group> groupList = null;

		if (groups instanceof List) {
			groupList = (List<Group>)groups;
		}
		else {
			groupList = new ArrayList<>(groups);
		}

		Collections.sort(groupList, obc);

		return Collections.unmodifiableList(
			ListUtil.subList(groupList, start, end));
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
		throws PortalException {

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
			GroupFriendlyURLException gfurle = new GroupFriendlyURLException(
				GroupFriendlyURLException.DUPLICATE);

			gfurle.setDuplicateClassPK(group.getGroupId());
			gfurle.setDuplicateClassName(Group.class.getName());

			throw gfurle;
		}

		String groupIdFriendlyURL = friendlyURL.substring(1);

		if (Validator.isNumber(groupIdFriendlyURL)) {
			long groupClassNameId = classNameLocalService.getClassNameId(
				Group.class);

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
			long userClassNameId = classNameLocalService.getClassNameId(
				User.class);

			if ((classNameId == userClassNameId) &&
				(classPK == user.getUserId())) {
			}
			else {
				GroupFriendlyURLException gfurle =
					new GroupFriendlyURLException(
						GroupFriendlyURLException.DUPLICATE);

				gfurle.setDuplicateClassPK(user.getUserId());
				gfurle.setDuplicateClassName(User.class.getName());

				throw gfurle;
			}
		}

		if (StringUtil.count(friendlyURL, StringPool.SLASH) > 1) {
			throw new GroupFriendlyURLException(
				GroupFriendlyURLException.TOO_DEEP);
		}
	}

	protected void validateGroupKey(
			long groupId, long companyId, String groupKey, boolean site)
		throws PortalException {

		if (Validator.isNull(groupKey) || Validator.isNumber(groupKey) ||
			groupKey.contains(StringPool.STAR) ||
			groupKey.contains(ORGANIZATION_NAME_SUFFIX)) {

			throw new GroupKeyException();
		}

		try {
			Group group = groupFinder.findByC_GK(companyId, groupKey);

			if ((groupId <= 0) || (group.getGroupId() != groupId)) {
				throw new DuplicateGroupException("{groupId=" + groupId + "}");
			}
		}
		catch (NoSuchGroupException nsge) {
		}

		if (site) {
			Company company = companyLocalService.getCompany(companyId);

			if (groupKey.equals(company.getName())) {
				throw new DuplicateGroupException();
			}
		}
	}

	protected void validateInheritContent(
			long parentGroupId, boolean inheritContent)
		throws GroupInheritContentException {

		if (!inheritContent) {
			return;
		}

		if (parentGroupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			throw new GroupInheritContentException();
		}

		Group parentGroup = groupPersistence.fetchByPrimaryKey(parentGroupId);

		if (parentGroup.isInheritContent()) {
			throw new GroupInheritContentException();
		}
	}

	protected void validateLanguageIds(
			String defaultLanguageId, String languageIds)
		throws PortalException {

		String[] languageIdsArray = StringUtil.split(languageIds);

		for (String languageId : languageIdsArray) {
			if (!LanguageUtil.isAvailableLocale(
					LocaleUtil.fromLanguageId(languageId))) {

				LocaleException le = new LocaleException(
					LocaleException.TYPE_DISPLAY_SETTINGS);

				le.setSourceAvailableLocales(
					LanguageUtil.getAvailableLocales());
				le.setTargetAvailableLocales(
					Arrays.asList(
						LocaleUtil.fromLanguageIds(languageIdsArray)));

				throw le;
			}
		}

		if (!ArrayUtil.contains(languageIdsArray, defaultLanguageId)) {
			LocaleException le = new LocaleException(
				LocaleException.TYPE_DEFAULT);

			le.setSourceAvailableLocales(LanguageUtil.getAvailableLocales());
			le.setTargetAvailableLocales(
				Arrays.asList(LocaleUtil.fromLanguageIds(languageIdsArray)));

			throw le;
		}
	}

	protected void validateParentGroup(long groupId, long parentGroupId)
		throws PortalException {

		if (parentGroupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			return;
		}

		if (groupId == parentGroupId) {
			throw new GroupParentException.MustNotBeOwnParent(groupId);
		}

		Group group = groupPersistence.fetchByPrimaryKey(groupId);

		if (group == null) {
			return;
		}

		if ((groupId > 0) &&
			(parentGroupId != GroupConstants.DEFAULT_PARENT_GROUP_ID)) {

			// Prevent circular groupal references

			if (isParentGroup(groupId, parentGroupId)) {
				throw new GroupParentException.MustNotHaveChildParent(
					groupId, parentGroupId);
			}
		}

		Group parentGroup = groupPersistence.findByPrimaryKey(parentGroupId);

		if (group.isStagingGroup()) {
			long stagingGroupId = parentGroup.getStagingGroup().getGroupId();

			if (groupId == stagingGroupId) {
				throw new GroupParentException.MustNotHaveStagingParent(
					groupId, stagingGroupId);
			}
		}
	}

	protected File publicLARFile;

	private static final Log _log = LogFactoryUtil.getLog(
		GroupLocalServiceImpl.class);

	private volatile long[] _classNameIds;
	private volatile long[] _complexSQLClassNameIds;
	private final Map<String, Group> _systemGroupsMap = new HashMap<>();

}
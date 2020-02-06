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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.exception.RemoteExportException;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.exportimport.kernel.staging.StagingURLHelperUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCachable;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.DuplicateGroupException;
import com.liferay.portal.kernel.exception.GroupFriendlyURLException;
import com.liferay.portal.kernel.exception.GroupInheritContentException;
import com.liferay.portal.kernel.exception.GroupKeyException;
import com.liferay.portal.kernel.exception.GroupParentException;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchLayoutSetException;
import com.liferay.portal.kernel.exception.PendingBackgroundTaskException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RemoteOptionsException;
import com.liferay.portal.kernel.exception.RequiredGroupException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.model.Account;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.UserPersonalSite;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.RemoteAuthException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.security.permission.RolePermissions;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.tree.TreeModelTasksAdapter;
import com.liferay.portal.kernel.tree.TreePathUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.comparator.GroupIdComparator;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.base.GroupLocalServiceBaseImpl;
import com.liferay.portal.service.http.ClassNameServiceHttp;
import com.liferay.portal.service.http.GroupServiceHttp;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.io.File;
import java.io.Serializable;

import java.net.ConnectException;

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
import java.util.Objects;
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
 * relationship can be depicted as ... Layout -> LayoutSet -> Group[type] ->
 * [Entity]. Note, the Entity part is optional.
 * </p>
 *
 * <p>
 * Group has a "type" definition that is typically identified by two fields of
 * the entity - <code>String className</code>, and <code>int type</code>.
 * </p>
 *
 * <p>
 * The <code>className</code> field helps create the group's association with
 * other entities (e.g. Organization, User, Company, UserGroup, etc.). The value
 * of <code>className</code> is the full name of the entity's class and the
 * primary key of the associated entity instance. A site has
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
 * Here is a listing of how Group is related to some portal entities:
 * </p>
 *
 * <ul>
 * <li>
 * Site is a Group with <code>className="Group"</code>
 * </li>
 * <li>
 * Company has one Group (this is the global scope, but never has pages)
 * </li>
 * <li>
 * User has one Group (pages are optional based on the behavior configuration
 * for
 * personal pages)
 * </li>
 * <li>
 * Layout Template (<code>LayoutPrototype</code>) has 1 Group which uses only
 * one
 * of its two LayoutSets to store a single page which can later be used to
 * derive a single page in any Site
 * </li>
 * <li>
 * Site Template (<code>LayoutSetPrototype</code>) has 1 Group which uses only
 * one of its two LayoutSets to store many pages which can later be used to
 * derive
 * entire Sites or pulled into an existing Site
 * </li>
 * <li>
 * Organization has one Group, but can also be associated to a Site at any point
 * in its life cycle in order to support having pages
 * </li>
 * <li>
 * UserGroup has one Group that can have pages in both of the group's LayoutSets
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

			if (Validator.isNull(groupKey)) {
				Locale userLocale = user.getLocale();

				if (userLocale != null) {
					groupKey = nameMap.get(userLocale);
					friendlyName = nameMap.get(userLocale);
				}
			}

			if (Validator.isNull(groupKey)) {
				Locale mostRelevantLocale = LocaleUtil.getMostRelevantLocale();

				if (mostRelevantLocale != null) {
					groupKey = nameMap.get(mostRelevantLocale);
					friendlyName = nameMap.get(mostRelevantLocale);
				}
			}

			if (Validator.isNull(groupKey)) {
				groupKey = nameMap.get(LocaleUtil.US);
				friendlyName = nameMap.get(LocaleUtil.US);
			}
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
		else if ((type != GroupConstants.TYPE_DEPOT) &&
				 !GroupConstants.USER_PERSONAL_SITE.equals(groupKey)) {

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

			for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
				String name = entry.getValue();

				if (Validator.isNull(name)) {
					continue;
				}

				nameMap.put(
					entry.getKey(), name.concat(ORGANIZATION_STAGING_SUFFIX));
			}

			friendlyURL = getFriendlyURL(friendlyURL.concat("-staging"));

			friendlyURL = getValidatedFriendlyURL(
				user.getCompanyId(), groupId, classNameId, classPK,
				friendlyURL);
		}

		if (parentGroupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			membershipRestriction =
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;
		}

		if (className.equals(Group.class.getName())) {
			if (!site && (liveGroupId == 0) &&
				!(StringUtil.startsWith(groupKey, GroupConstants.APP) ||
				  groupKey.equals(GroupConstants.CONTROL_PANEL) ||
				  groupKey.equals(GroupConstants.FORMS))) {

				throw new IllegalArgumentException();
			}
		}
		else if (!className.equals(Company.class.getName()) &&
				 !className.equals(Organization.class.getName()) &&
				 className.startsWith("com.liferay.portal.kernel.model.")) {

			if (site) {
				throw new IllegalArgumentException();
			}
		}

		if ((classNameId <= 0) || (type == GroupConstants.TYPE_DEPOT) ||
			className.equals(Group.class.getName())) {

			validateGroupKey(
				groupId, user.getCompanyId(), groupKey, type, site);
		}

		validateInheritContent(parentGroupId, inheritContent);

		friendlyURL = getValidatedFriendlyURL(
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
		group.setSite(site);
		group.setInheritContent(inheritContent);
		group.setActive(active);

		if ((serviceContext != null) && (classNameId == groupClassNameId) &&
			!user.isDefaultUser()) {

			group.setExpandoBridgeAttributes(serviceContext);
		}

		group = groupPersistence.update(group);

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
	 * Adds the group to the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param group the group
	 */
	@Override
	public void addOrganizationGroup(long organizationId, Group group) {
		super.addOrganizationGroup(organizationId, group);

		try {
			reindexUsersInOrganization(organizationId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Adds the group to the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param groupId the primary key of the group
	 */
	@Override
	public void addOrganizationGroup(long organizationId, long groupId) {
		super.addOrganizationGroup(organizationId, groupId);

		try {
			reindexUsersInOrganization(organizationId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Adds the groups to the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param groups the groups
	 */
	@Override
	public void addOrganizationGroups(long organizationId, List<Group> groups) {
		super.addOrganizationGroups(organizationId, groups);

		try {
			reindexUsersInOrganization(organizationId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Adds the groups to the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void addOrganizationGroups(long organizationId, long[] groupIds) {
		super.addOrganizationGroups(organizationId, groupIds);

		try {
			reindexUsersInOrganization(organizationId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Adds the group to the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param group the group
	 */
	@Override
	public void addUserGroupGroup(long userGroupId, Group group) {
		super.addUserGroupGroup(userGroupId, group);

		try {
			reindexUsersInUserGroup(userGroupId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Adds the group to the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param groupId the primary key of the group
	 */
	@Override
	public void addUserGroupGroup(long userGroupId, long groupId) {
		super.addUserGroupGroup(userGroupId, groupId);

		try {
			reindexUsersInUserGroup(userGroupId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Adds the groups to the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param groups the groups
	 */
	@Override
	public void addUserGroupGroups(long userGroupId, List<Group> groups) {
		super.addUserGroupGroups(userGroupId, groups);

		try {
			reindexUsersInUserGroup(userGroupId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Adds the groups to the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void addUserGroupGroups(long userGroupId, long[] groupIds) {
		super.addUserGroupGroups(userGroupId, groupIds);

		try {
			reindexUsersInUserGroup(userGroupId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
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
			groupLocalService.addGroup(
				userLocalService.getDefaultUserId(companyId),
				GroupConstants.DEFAULT_PARENT_GROUP_ID, Company.class.getName(),
				companyId, GroupConstants.DEFAULT_LIVE_GROUP_ID,
				getLocalizationMap(GroupConstants.GLOBAL), null, 0, true,
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
				GroupConstants.GLOBAL_FRIENDLY_URL, true, true, null);
		}
	}

	@Override
	public Group checkScopeGroup(Layout layout, long userId)
		throws PortalException {

		if (layout.hasScopeGroup()) {
			return layout.getScopeGroup();
		}

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), String.valueOf(layout.getPlid())
		).build();

		return groupLocalService.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, null, 0, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);
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
				else if (groupKey.equals(GroupConstants.FORMS)) {
					type = GroupConstants.TYPE_SITE_PRIVATE;
					friendlyURL = GroupConstants.FORMS_FRIENDLY_URL;
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
	 * Clears the groups from the organization.
	 *
	 * @param organizationId the primary key of the organization
	 */
	@Override
	public void clearOrganizationGroups(long organizationId) {
		super.clearOrganizationGroups(organizationId);

		try {
			reindexUsersInOrganization(organizationId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Clears the groups from the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 */
	@Override
	public void clearUserGroupGroups(long userGroupId) {
		super.clearUserGroupGroups(userGroupId);

		try {
			reindexUsersInUserGroup(userGroupId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Deletes the group and its associated data.
	 *
	 * <p>
	 * The group is unstaged and its assets and resources including layouts,
	 * membership requests, subscriptions, teams, blogs, bookmarks, events,
	 * image gallery, journals, message boards, polls, and wikis are also
	 * deleted.
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

			int count = groupPersistence.countByC_P_S(
				group.getCompanyId(), group.getGroupId(), true);

			if (count > 0) {
				throw new RequiredGroupException.MustNotDeleteGroupThatHasChild(
					group.getGroupId());
			}

			List<BackgroundTask> backgroundTasks =
				BackgroundTaskManagerUtil.getBackgroundTasks(
					group.getGroupId(),
					BackgroundTaskConstants.STATUS_IN_PROGRESS);

			if (ListUtil.isNotNull(backgroundTasks)) {
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
			catch (NoSuchLayoutSetException noSuchLayoutSetException) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(
						noSuchLayoutSetException, noSuchLayoutSetException);
				}
			}

			try {
				layoutSetLocalService.deleteLayoutSet(
					group.getGroupId(), false, serviceContext);
			}
			catch (NoSuchLayoutSetException noSuchLayoutSetException) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(
						noSuchLayoutSetException, noSuchLayoutSetException);
				}
			}

			// Membership requests

			membershipRequestLocalService.deleteMembershipRequests(
				group.getGroupId());

			// Portlet preferences

			portletPreferencesLocalService.deletePortletPreferences(
				group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				PortletKeys.PREFS_PLID_SHARED);
			portletPreferencesLocalService.deletePortletPreferences(
				group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
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
				catch (Exception exception) {
					_log.error(
						"Unable to disable staging for group " +
							group.getGroupId());
				}
			}

			systemEventLocalService.deleteSystemEvents(group.getGroupId());

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
				resourcePermissionPersistence.findByC_S_P(
					group.getCompanyId(), ResourceConstants.SCOPE_GROUP,
					String.valueOf(group.getGroupId()));

			for (ResourcePermission resourcePermission : resourcePermissions) {
				resourcePermissionLocalService.deleteResourcePermission(
					resourcePermission);
			}

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

				if ((workflowDefinitionLink == null) ||
					(workflowDefinitionLink.getGroupId() ==
						group.getLiveGroupId())) {

					continue;
				}

				workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
					workflowDefinitionLink);
			}

			// Group

			if (!group.isStagingGroup() && group.isOrganization() &&
				group.isSite()) {

				group.setSite(false);

				group = groupPersistence.update(group);

				// Group roles

				userGroupRoleLocalService.deleteUserGroupRoles(
					group.getGroupId(), RoleConstants.TYPE_SITE);

				// User group roles

				userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
					group.getGroupId(), RoleConstants.TYPE_SITE);
			}
			else {

				// Group roles

				userGroupRoleLocalService.deleteUserGroupRolesByGroupId(
					group.getGroupId());

				// User group roles

				userGroupGroupRoleLocalService.
					deleteUserGroupGroupRolesByGroupId(group.getGroupId());

				// Resources

				try {
					resourceLocalService.deleteResource(
						group.getCompanyId(), Group.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL, group.getGroupId());
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"No resources found for group " +
								group.getGroupId());
					}
				}

				long companyId = group.getCompanyId();
				long[] userIds = getUserPrimaryKeys(group.getGroupId());

				if (ArrayUtil.isNotEmpty(userIds)) {
					TransactionCommitCallbackUtil.registerCallback(
						() -> {
							reindex(companyId, userIds);

							return null;
						});
				}

				groupPersistence.remove(group);
			}

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
	 * image gallery, journals, message boards, polls, and wikis are also
	 * deleted.
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

	/**
	 * Deletes the group from the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param group the group
	 */
	@Override
	public void deleteOrganizationGroup(long organizationId, Group group) {
		super.deleteOrganizationGroup(organizationId, group);

		try {
			reindexUsersInOrganization(organizationId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Deletes the group from the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param groupId the primary key of the group
	 */
	@Override
	public void deleteOrganizationGroup(long organizationId, long groupId) {
		super.deleteOrganizationGroup(organizationId, groupId);

		try {
			reindexUsersInOrganization(organizationId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Deletes the groups from the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param groups the groups
	 */
	@Override
	public void deleteOrganizationGroups(
		long organizationId, List<Group> groups) {

		super.deleteOrganizationGroups(organizationId, groups);

		try {
			reindexUsersInOrganization(organizationId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Deletes the groups from the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void deleteOrganizationGroups(long organizationId, long[] groupIds) {
		super.deleteOrganizationGroups(organizationId, groupIds);

		try {
			reindexUsersInOrganization(organizationId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Deletes the group from the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param group the group
	 */
	@Override
	public void deleteUserGroupGroup(long userGroupId, Group group) {
		super.deleteUserGroupGroup(userGroupId, group);

		try {
			reindexUsersInUserGroup(userGroupId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Deletes the group from the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param groupId the primary key of the group
	 */
	@Override
	public void deleteUserGroupGroup(long userGroupId, long groupId) {
		super.deleteUserGroupGroup(userGroupId, groupId);

		try {
			reindexUsersInUserGroup(userGroupId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Deletes the groups from the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param groups the groups
	 */
	@Override
	public void deleteUserGroupGroups(long userGroupId, List<Group> groups) {
		super.deleteUserGroupGroups(userGroupId, groups);

		try {
			reindexUsersInUserGroup(userGroupId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	/**
	 * Deletes the groups from the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void deleteUserGroupGroups(long userGroupId, long[] groupIds) {
		super.deleteUserGroupGroups(userGroupId, groupIds);

		try {
			reindexUsersInUserGroup(userGroupId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	@Override
	public synchronized void disableStaging(long groupId)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		int stagingGroupCount = group.getRemoteStagingGroupCount();

		if (stagingGroupCount == 0) {
			return;
		}

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

	@Override
	@ThreadLocalCachable
	public Group fetchGroup(long groupId) {
		return groupPersistence.fetchByPrimaryKey(groupId);
	}

	@Override
	public Group fetchGroup(long companyId, long classNameId, long classPK) {
		return groupPersistence.fetchByC_C_C(companyId, classNameId, classPK);
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
	@Transactional(enabled = false)
	public Group fetchGroup(long companyId, String groupKey) {
		String companyIdHexString = StringUtil.toHexString(companyId);

		Group group = _systemGroupsMap.get(companyIdHexString.concat(groupKey));

		if (group != null) {
			return group;
		}

		return groupLocalService.loadFetchGroup(companyId, groupKey);
	}

	@Override
	public Group fetchStagingGroup(long liveGroupId) {
		return groupPersistence.fetchByLiveGroupId(liveGroupId);
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

		return groupPersistence.fetchByC_C_C(
			companyId, classNameId,
			userLocalService.getDefaultUserId(companyId));
	}

	@Override
	public List<Long> getActiveGroupIds(long userId) {
		return groupFinder.findByActiveGroupIds(userId);
	}

	/**
	 * Returns all the active or inactive groups associated with the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  active whether to return only active groups or only inactive
	 *         groups
	 * @return the active or inactive groups
	 */
	@Override
	public List<Group> getActiveGroups(long companyId, boolean active) {
		return groupPersistence.findByC_A(companyId, active);
	}

	/**
	 * Returns the active or inactive groups associated with the company and,
	 * optionally, the main site.
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
	 * @param  site whether the group is associated with a main site
	 * @param  active whether to return only active groups or only inactive
	 *         groups
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the active or inactive groups
	 */
	@Override
	public List<Group> getActiveGroups(
		long companyId, boolean site, boolean active, int start, int end,
		OrderByComparator<Group> obc) {

		return groupPersistence.findByC_S_A(
			companyId, site, active, start, end, obc);
	}

	/**
	 * Returns the active or inactive groups associated with the company.
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
	 * @param  active whether to return only active groups or only inactive
	 *         groups
	 * @param  start the lower bound of the range of groups to return
	 * @param  end the upper bound of the range of groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the groups (optionally
	 *         <code>null</code>)
	 * @return the active or inactive groups
	 */
	@Override
	public List<Group> getActiveGroups(
		long companyId, boolean active, int start, int end,
		OrderByComparator<Group> obc) {

		return groupPersistence.findByC_A(companyId, active, start, end, obc);
	}

	/**
	 * Returns the number of active or inactive groups associated with the
	 * company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  active whether to count only active groups or only inactive
	 *         groups
	 * @return the number of active or inactive groups
	 */
	@Override
	public int getActiveGroupsCount(long companyId, boolean active) {
		return groupPersistence.countByC_A(companyId, active);
	}

	/**
	 * Returns the number of active or inactive groups associated with the
	 * company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  active whether to count only active groups or only inactive
	 *         groups
	 * @param  site whether the group is to be associated with a main site
	 * @return the number of active or inactive groups
	 */
	@Override
	public int getActiveGroupsCount(
		long companyId, boolean active, boolean site) {

		return groupPersistence.countByC_S_A(companyId, active, site);
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
	@Transactional(enabled = false)
	public Group getGroup(long companyId, String groupKey)
		throws PortalException {

		String companyIdHexString = StringUtil.toHexString(companyId);

		Group group = _systemGroupsMap.get(companyIdHexString.concat(groupKey));

		if (group != null) {
			return group;
		}

		return groupLocalService.loadGetGroup(companyId, groupKey);
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

	@Override
	public List<Group> getGroups(
		long companyId, long parentGroupId, boolean site, int start, int end) {

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			return groupPersistence.findByC_S(companyId, site, start, end);
		}

		return groupPersistence.findByC_P_S(
			companyId, parentGroupId, site, start, end);
	}

	@Override
	public List<Group> getGroups(
		long companyId, long parentGroupId, String name, boolean site,
		int start, int end) {

		if (Validator.isNull(name)) {
			return getGroups(companyId, parentGroupId, site, start, end);
		}

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			return groupPersistence.findByC_LikeN_S(
				companyId, name, site, start, end);
		}

		return groupPersistence.findByC_P_LikeN_S(
			companyId, parentGroupId, name, site, start, end);
	}

	@Override
	public List<Group> getGroups(
		long companyId, String treePath, boolean site) {

		return groupPersistence.findByC_T_S(companyId, treePath, site);
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

		return groupPersistence.findByC_C_P(
			companyId, classNameLocalService.getClassNameId(className),
			parentGroupId);
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

		return groupPersistence.findByC_C_P(
			companyId, classNameLocalService.getClassNameId(className),
			parentGroupId, start, end);
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
			groups.add(getGroup(groupId));
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

	@Override
	public int getGroupsCount(
		long companyId, long parentGroupId, String name, boolean site) {

		if (Validator.isNull(name)) {
			return getGroupsCount(companyId, parentGroupId, site);
		}

		if (parentGroupId == GroupConstants.ANY_PARENT_GROUP_ID) {
			return groupPersistence.countByC_LikeN_S(companyId, name, site);
		}

		return groupPersistence.countByC_P_LikeN_S(
			companyId, parentGroupId, name, site);
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

		return groupPersistence.countByC_C_P(
			companyId, classNameLocalService.getClassNameId(className),
			parentGroupId);
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
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  site whether the group is to be associated with a main site
	 * @param  active whether to return only active groups, or only inactive
	 *         groups
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
		long companyId, long parentGroupId, boolean site, boolean active,
		int start, int end, OrderByComparator<Group> obc) {

		return groupFinder.findByLayouts(
			companyId, parentGroupId, site, active, start, end, obc);
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
			companyId, parentGroupId, site, null, start, end, obc);
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
	 * Returns the number of groups that are children or the parent group and
	 * that have at least one layout
	 *
	 * @param  companyId the primary key of the company
	 * @param  parentGroupId the primary key of the parent group
	 * @param  site whether the group is to be associated with a main site
	 * @param  active whether to return only active groups, or only inactive
	 *         groups
	 * @return the number of matching groups
	 */
	@Override
	public int getLayoutsGroupsCount(
		long companyId, long parentGroupId, boolean site, boolean active) {

		return groupFinder.countByLayouts(
			companyId, parentGroupId, site, active);
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

		for (Organization organization : organizations) {
			organizationGroups.add(organization.getGroup());
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

		for (Organization organization : organizations) {
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

	@Override
	public List<Group> getStagedSites() {
		return groupFinder.findByL_TS_S_RSGC(0, "staged=true", true, 0);
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

			LinkedHashMap<String, Object> groupParams =
				LinkedHashMapBuilder.<String, Object>put(
					"usersGroups", Long.valueOf(userId)
				).build();

			return search(
				user.getCompanyId(), null, null, groupParams, start, end);
		}

		return userPersistence.getGroups(userId, start, end);
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

		for (UserGroup userGroup : userGroups) {
			userGroupGroups.add(userGroup.getGroup());
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

		for (UserGroup userGroup : userGroups) {
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

		return groupPersistence.findByC_C_C(
			companyId, classNameId,
			userLocalService.getDefaultUserId(companyId));
	}

	@Override
	public List<Group> getUserSitesGroups(long userId) throws PortalException {
		UserBag userBag = PermissionCacheUtil.getUserBag(userId);

		if (userBag == null) {
			User user = userPersistence.findByPrimaryKey(userId);

			LinkedHashMap<String, Object> groupParams =
				LinkedHashMapBuilder.<String, Object>put(
					"inherit", Boolean.TRUE
				).put(
					"site", Boolean.TRUE
				).put(
					"usersGroups", userId
				).build();

			return groupFinder.findByCompanyId(
				user.getCompanyId(), groupParams, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new GroupNameComparator(true));
		}

		Collection<Group> userGroups = userBag.getUserGroups();

		List<Group> userSiteGroups = new ArrayList<>(userGroups.size());

		for (Group group : userGroups) {
			if (group.isSite()) {
				userSiteGroups.add(group);
			}
		}

		userSiteGroups.sort(new GroupNameComparator(true));

		return userSiteGroups;
	}

	@Override
	public List<Group> getUserSitesGroups(
			long userId, boolean includeAdministrative)
		throws PortalException {

		if (!includeAdministrative) {
			return getUserSitesGroups(userId);
		}

		Set<Group> siteGroups = new HashSet<>();

		List<UserGroupRole> userGroupRoles =
			userGroupRoleLocalService.getUserGroupRoles(userId);

		for (UserGroupRole userGroupRole : userGroupRoles) {
			Role role = userGroupRole.getRole();

			String roleName = role.getName();

			if (roleName.equals(RoleConstants.SITE_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.SITE_OWNER)) {

				siteGroups.add(userGroupRole.getGroup());
			}
		}

		siteGroups.addAll(getUserSitesGroups(userId));

		return new ArrayList<>(siteGroups);
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

		return false;
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

		return false;
	}

	@Override
	@Transactional(enabled = false)
	public boolean isLiveGroupActive(Group group) {
		if (group == null) {
			return false;
		}

		if (!group.isStagingGroup()) {
			return group.isActive();
		}

		Group liveGroup = group.getLiveGroup();

		if (liveGroup == null) {
			return false;
		}

		return liveGroup.isActive();
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
		long classNameId = classNameLocalService.getClassNameId(Group.class);

		TreePathUtil.rebuildTree(
			companyId, GroupConstants.DEFAULT_PARENT_GROUP_ID, StringPool.SLASH,
			new TreeModelTasksAdapter<Group>() {

				@Override
				public List<Group> findTreeModels(
					long previousId, long companyId, long parentPrimaryKey,
					int size) {

					return groupPersistence.findByG_C_C_P(
						previousId, companyId, classNameId, parentPrimaryKey,
						QueryUtil.ALL_POS, size, new GroupIdComparator(true));
				}

			});
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 *         com.liferay.portal.kernel.service.persistence.GroupFinder}.
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
	 * Removes the groups from the role.
	 *
	 * @param roleId the primary key of the role
	 * @param groupIds the primary keys of the groups
	 */
	@Override
	public void unsetRoleGroups(long roleId, long[] groupIds) {
		rolePersistence.removeGroups(roleId, groupIds);
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
			assetCategoryIds, assetTagNames, true, false, null, null, null,
			null, null, group.getDescriptiveName(), group.getDescription(),
			null, null, null, 0, 0, null);
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

			if (friendlyURL.equals(group.getFriendlyURL())) {
				return group;
			}
		}

		friendlyURL = getFriendlyURL(
			group.getCompanyId(), groupId, group.getClassNameId(),
			group.getClassPK(), StringPool.BLANK, friendlyURL);

		if (group.isUser()) {
			friendlyURL = getValidatedFriendlyURL(
				group.getCompanyId(), group.getGroupId(),
				group.getClassNameId(), group.getClassPK(), friendlyURL);
		}
		else {
			validateFriendlyURL(
				group.getCompanyId(), group.getGroupId(),
				group.getClassNameId(), group.getClassPK(), friendlyURL);
		}

		group.setFriendlyURL(friendlyURL);

		return groupPersistence.update(group);
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

		List<String> names = new ArrayList<>(nameMap.values());

		if (ListUtil.isNull(names)) {
			throw new GroupKeyException();
		}

		if ((nameMap != null) &&
			Validator.isNotNull(
				nameMap.get(
					LocaleUtil.fromLanguageId(group.getDefaultLanguageId())))) {

			groupKey = nameMap.get(
				LocaleUtil.fromLanguageId(group.getDefaultLanguageId()));
		}

		friendlyURL = getFriendlyURL(
			group.getCompanyId(), groupId, classNameId, classPK,
			StringPool.BLANK, friendlyURL);

		if ((classNameId <= 0) || (type == GroupConstants.TYPE_DEPOT) ||
			className.equals(Group.class.getName())) {

			validateGroupKey(
				group.getGroupId(), group.getCompanyId(), groupKey,
				group.getType(), group.isSite());
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

		if (group.isUser()) {
			friendlyURL = getValidatedFriendlyURL(
				group.getCompanyId(), group.getGroupId(),
				group.getClassNameId(), group.getClassPK(), friendlyURL);
		}
		else {
			validateFriendlyURL(
				group.getCompanyId(), group.getGroupId(),
				group.getClassNameId(), group.getClassPK(), friendlyURL);
		}

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

		if (group.isActive() != active) {
			group.setActive(active);

			long companyId = group.getCompanyId();

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					reindex(companyId, getUserPrimaryKeys(groupId));

					return null;
				});
		}

		if ((serviceContext != null) && group.isSite()) {
			group.setExpandoBridgeAttributes(serviceContext);
		}

		group = groupPersistence.update(group);

		if (group.hasStagingGroup() && !group.isStagedRemotely()) {
			Group stagingGroup = group.getStagingGroup();

			stagingGroup.setParentGroupId(group.getParentGroupId());

			stagingGroup.setTreePath(stagingGroup.buildTreePath());

			groupPersistence.update(stagingGroup);
		}

		// Asset

		if ((serviceContext == null) || !group.isSite()) {
			return group;
		}

		User user = userPersistence.fetchByPrimaryKey(group.getCreatorUserId());

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

		_validateGroupKeyChange(groupId, typeSettings);

		Group group = groupPersistence.findByPrimaryKey(groupId);

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		String newLanguageIds = typeSettingsProperties.getProperty(
			PropsKeys.LOCALES);

		if (Validator.isNotNull(newLanguageIds)) {
			UnicodeProperties oldTypeSettingsProperties =
				group.getTypeSettingsProperties();

			String oldLanguageIds = oldTypeSettingsProperties.getProperty(
				PropsKeys.LOCALES, StringPool.BLANK);

			String defaultLanguageId = typeSettingsProperties.getProperty(
				"languageId", LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

			validateLanguageIds(defaultLanguageId, newLanguageIds);

			if (!Objects.equals(
					group.getDefaultLanguageId(), defaultLanguageId)) {

				Locale defaultLocale = LocaleUtil.fromLanguageId(
					defaultLanguageId);

				Map<Locale, String> oldNameMap = group.getNameMap();

				group.setNameMap(oldNameMap, defaultLocale);

				Map<Locale, String> oldDescriptionMap =
					group.getDescriptionMap();

				group.setDescriptionMap(oldDescriptionMap, defaultLocale);

				Map<Locale, String> nameMap = group.getNameMap();

				if ((nameMap != null) &&
					Validator.isNotNull(nameMap.get(defaultLocale))) {

					group.setGroupKey(nameMap.get(defaultLocale));
				}
			}

			if (!Objects.equals(oldLanguageIds, newLanguageIds)) {
				LanguageUtil.resetAvailableGroupLocales(groupId);
			}
		}

		group.setTypeSettings(typeSettings);

		return groupPersistence.update(group);
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

		return groupPersistence.update(group);
	}

	@Override
	public void validateRemote(
			long groupId, String remoteAddress, int remotePort,
			String remotePathContext, boolean secureConnection,
			long remoteGroupId)
		throws PortalException {

		RemoteOptionsException remoteOptionsException = null;

		if (!Validator.isDomain(remoteAddress) &&
			!Validator.isIPAddress(remoteAddress)) {

			remoteOptionsException = new RemoteOptionsException(
				RemoteOptionsException.REMOTE_ADDRESS);

			remoteOptionsException.setRemoteAddress(remoteAddress);

			throw remoteOptionsException;
		}

		if ((remotePort < 1) || (remotePort > 65535)) {
			remoteOptionsException = new RemoteOptionsException(
				RemoteOptionsException.REMOTE_PORT);

			remoteOptionsException.setRemotePort(remotePort);

			throw remoteOptionsException;
		}

		if (Validator.isNotNull(remotePathContext) &&
			(!remotePathContext.startsWith(StringPool.FORWARD_SLASH) ||
			 remotePathContext.endsWith(StringPool.FORWARD_SLASH))) {

			remoteOptionsException = new RemoteOptionsException(
				RemoteOptionsException.REMOTE_PATH_CONTEXT);

			remoteOptionsException.setRemotePathContext(remotePathContext);

			throw remoteOptionsException;
		}

		validateRemoteGroup(
			groupId, remoteGroupId, remoteAddress, remotePort,
			remotePathContext, secureConnection);
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

		Map<String, String[]> parameterMap = HashMapBuilder.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.TRUE.toString()}
		).build();

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
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
			catch (Exception exception) {
				_log.error(
					StringBundler.concat(
						"Unable to add default data for portlet ",
						portletDataHandler.getPortletId(), " in group ",
						group.getGroupId()));

				if (portletDataHandler.isRollbackOnException()) {
					throw new SystemException(exception);
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
			catch (Exception exception) {
				_log.error(
					StringBundler.concat(
						"Unable to delete data for portlet ",
						portletDataHandler.getPortletId(), " in group ",
						group.getGroupId()),
					exception);

				if (portletDataHandler.isRollbackOnException()) {
					throw exception;
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

			if (parentGroupIdEquals && (groupParentGroupId != parentGroupId)) {
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

			if (!containsName) {
				AssetEntry assetEntry = assetEntryPersistence.fetchByC_C(
					group.getClassNameId(), group.getGroupId());

				if (assetEntry != null) {
					containsName = matches(assetEntry.getTitle(), names);
				}
			}

			boolean containsDescription = matches(
				group.getDescriptionCurrentValue(), descriptions);

			if ((andOperator && (!containsName || !containsDescription)) ||
				(!andOperator && !containsName && !containsDescription)) {

				iterator.remove();

				continue;
			}

			// Filter by active

			if ((active != null) && (active != isLiveGroupActive(group))) {
				iterator.remove();

				continue;
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

			if ((site != null) && (site != group.isSite())) {
				iterator.remove();

				continue;
			}

			// Filter by type and types

			int type = group.getType();

			if (type == 4) {
				iterator.remove();

				continue;
			}

			if ((types != null) && !types.contains(type)) {
				iterator.remove();
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

				List<ResourcePermission> resourcePermissions =
					resourcePermissionPersistence.findByC_N_S_R(
						companyId, rolePermissions.getName(),
						rolePermissions.getScope(),
						rolePermissions.getRoleId());

				for (ResourcePermission resourcePermission :
						resourcePermissions) {

					if (resourcePermission.hasAction(resourceAction)) {
						Group group = groupPersistence.fetchByPrimaryKey(
							GetterUtil.getLong(
								resourcePermission.getPrimKey()));

						if (group != null) {
							rolePermissionsGroups.add(group);
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

		joinedGroups.retainAll(groups);

		return joinedGroups;
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

		String safeFriendlyName = StringUtil.removeChars(
			friendlyName, CharPool.PERCENT);

		friendlyURL = StringPool.SLASH + getFriendlyURL(safeFriendlyName);

		return getValidatedFriendlyURL(
			companyId, groupId, classNameId, classPK, friendlyURL);
	}

	protected String getFriendlyURL(String friendlyURL) {
		return FriendlyURLNormalizerUtil.normalizeWithEncoding(friendlyURL);
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

	protected String getValidatedFriendlyURL(
			long companyId, long groupId, long classNameId, long classPK,
			String friendlyURL)
		throws PortalException {

		int i = 0;

		while (true) {
			try {
				validateFriendlyURL(
					companyId, groupId, classNameId, classPK, friendlyURL);

				break;
			}
			catch (GroupFriendlyURLException groupFriendlyURLException) {
				int type = groupFriendlyURLException.getType();

				if (type == GroupFriendlyURLException.DUPLICATE) {
					if (friendlyURL.matches(".+-[0-9]+$")) {
						int end = friendlyURL.lastIndexOf(CharPool.DASH);

						long suffix = GetterUtil.getLong(
							friendlyURL.substring(end + 1));

						if (!(friendlyURL.contains("group") &&
							  (groupId == suffix))) {

							friendlyURL = friendlyURL.substring(0, end);
						}
					}

					if (StringUtil.endsWith(friendlyURL, CharPool.DASH)) {
						friendlyURL = friendlyURL + ++i;
					}
					else {
						friendlyURL = friendlyURL + CharPool.DASH + ++i;
					}
				}
				else if (type == GroupFriendlyURLException.ENDS_WITH_DASH) {
					friendlyURL = StringUtil.replaceLast(
						friendlyURL, CharPool.DASH, StringPool.BLANK);
				}
				else {
					friendlyURL = StringPool.SLASH + "group-" + classPK;
				}
			}
		}

		return friendlyURL;
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

		// Power User role

		role = roleLocalService.getRole(
			group.getCompanyId(), RoleConstants.POWER_USER);

		setRolePermissions(
			group, role, Group.class.getName(),
			new String[] {
				ActionKeys.MANAGE_LAYOUTS, ActionKeys.VIEW_SITE_ADMINISTRATION
			});
	}

	protected boolean isCompanyGroup(HttpPrincipal httpPrincipal, Group group) {
		ClassName className = ClassNameServiceHttp.fetchByClassNameId(
			httpPrincipal, group.getClassNameId());

		if (Objects.equals(className.getValue(), Company.class.getName())) {
			return true;
		}

		return false;
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

		return false;
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

	protected void reindex(long companyId, long[] userIds)
		throws PortalException {

		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class);

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			userLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property userId = PropertyFactoryUtil.forName("userId");

				dynamicQuery.add(userId.in(userIds));
			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(User user) -> {
				if (!user.isDefaultUser()) {
					try {
						indexableActionableDynamicQuery.addDocuments(
							indexer.getDocument(user));
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index user " + user.getUserId(),
								portalException);
						}
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(
			indexer.getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	protected void reindexUsersInOrganization(long organizationId)
		throws PortalException {

		Organization organization = organizationLocalService.getOrganization(
			organizationId);

		long companyId = organization.getCompanyId();

		long[] userIds = organizationLocalService.getUserPrimaryKeys(
			organizationId);

		if (ArrayUtil.isNotEmpty(userIds)) {
			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					reindex(companyId, userIds);

					return null;
				});
		}
	}

	protected void reindexUsersInUserGroup(long userGroupId)
		throws PortalException {

		UserGroup userGroup = userGroupLocalService.getUserGroup(userGroupId);

		long companyId = userGroup.getCompanyId();

		long[] userIds = organizationLocalService.getUserPrimaryKeys(
			userGroupId);

		if (ArrayUtil.isNotEmpty(userIds)) {
			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					reindex(companyId, userIds);

					return null;
				});
		}
	}

	protected void setCompanyPermissions(
			Role role, String name, String[] actionIds)
		throws PortalException {

		resourcePermissionLocalService.setResourcePermissions(
			role.getCompanyId(), name, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(role.getCompanyId()), role.getRoleId(), actionIds);
	}

	protected void setRolePermissions(Group group, Role role, String name)
		throws PortalException {

		List<String> actions = ResourceActionsUtil.getModelResourceActions(
			name);

		setRolePermissions(group, role, name, actions.toArray(new String[0]));
	}

	protected void setRolePermissions(
			Group group, Role role, String name, String[] actionIds)
		throws PortalException {

		resourcePermissionLocalService.setResourcePermissions(
			group.getCompanyId(), name, ResourceConstants.SCOPE_GROUP,
			String.valueOf(group.getGroupId()), role.getRoleId(), actionIds);
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

				Group stagingGroup = group.getStagingGroup();

				stagingGroupId = stagingGroup.getGroupId();
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
		catch (Exception exception) {
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
			GroupFriendlyURLException groupFriendlyURLException =
				new GroupFriendlyURLException(
					GroupFriendlyURLException.DUPLICATE);

			groupFriendlyURLException.setDuplicateClassPK(group.getGroupId());
			groupFriendlyURLException.setDuplicateClassName(
				Group.class.getName());

			throw groupFriendlyURLException;
		}

		String groupIdFriendlyURL = friendlyURL.substring(1);

		if (Validator.isNumber(groupIdFriendlyURL)) {
			long groupClassNameId = classNameLocalService.getClassNameId(
				Group.class);

			if (((classNameId != groupClassNameId) &&
				 !groupIdFriendlyURL.equals(String.valueOf(classPK)) &&
				 !PropsValues.USERS_SCREEN_NAME_ALLOW_NUMERIC) ||
				(classNameId == groupClassNameId)) {

				GroupFriendlyURLException groupFriendlyURLException =
					new GroupFriendlyURLException(
						GroupFriendlyURLException.POSSIBLE_DUPLICATE);

				groupFriendlyURLException.setKeywordConflict(
					groupIdFriendlyURL);

				throw groupFriendlyURLException;
			}
		}

		if (StringUtil.count(friendlyURL, CharPool.SLASH) > 1) {
			throw new GroupFriendlyURLException(
				GroupFriendlyURLException.TOO_DEEP);
		}

		if (StringUtil.endsWith(friendlyURL, CharPool.DASH)) {
			throw new GroupFriendlyURLException(
				GroupFriendlyURLException.ENDS_WITH_DASH);
		}

		if (StringUtil.equals(friendlyURL, "/.") ||
			StringUtil.equals(friendlyURL, "/..")) {

			throw new GroupFriendlyURLException(
				GroupFriendlyURLException.INVALID_CHARACTERS);
		}
	}

	protected void validateGroupKey(
			long groupId, long companyId, String groupKey, int type,
			boolean site)
		throws PortalException {

		int groupKeyMaxLength = ModelHintsUtil.getMaxLength(
			Group.class.getName(), "groupKey");

		if (Validator.isNull(groupKey) || Validator.isNumber(groupKey) ||
			groupKey.contains(StringPool.STAR) ||
			groupKey.contains(ORGANIZATION_NAME_SUFFIX) ||
			(groupKey.length() > groupKeyMaxLength)) {

			throw new GroupKeyException();
		}

		try {
			Group group = groupFinder.findByC_GK(companyId, groupKey);

			if ((groupId <= 0) || (group.getGroupId() != groupId)) {
				throw new DuplicateGroupException("{groupId=" + groupId + "}");
			}
		}
		catch (NoSuchGroupException noSuchGroupException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchGroupException, noSuchGroupException);
			}
		}

		if (site || (type == GroupConstants.TYPE_DEPOT)) {
			try {
				Company company = companyLocalService.getCompany(companyId);

				if (groupKey.equals(company.getName())) {
					throw new DuplicateGroupException();
				}
			}
			catch (NoSuchCompanyException noSuchCompanyException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchCompanyException, noSuchCompanyException);
				}
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

				LocaleException localeException = new LocaleException(
					LocaleException.TYPE_DISPLAY_SETTINGS);

				localeException.setSourceAvailableLocales(
					LanguageUtil.getAvailableLocales());
				localeException.setTargetAvailableLocales(
					Arrays.asList(
						LocaleUtil.fromLanguageIds(languageIdsArray)));

				throw localeException;
			}
		}

		if (!ArrayUtil.contains(languageIdsArray, defaultLanguageId)) {
			LocaleException localeException = new LocaleException(
				LocaleException.TYPE_DEFAULT);

			localeException.setSourceAvailableLocales(
				LanguageUtil.getAvailableLocales());
			localeException.setTargetAvailableLocales(
				Arrays.asList(LocaleUtil.fromLanguageIds(languageIdsArray)));

			throw localeException;
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

		if (group.isStagingGroup()) {
			Group parentGroup = groupPersistence.findByPrimaryKey(
				parentGroupId);

			Group stagingGroup = parentGroup.getStagingGroup();

			long stagingGroupId = stagingGroup.getGroupId();

			if (groupId == stagingGroupId) {
				throw new GroupParentException.MustNotHaveStagingParent(
					groupId, stagingGroupId);
			}
		}
	}

	protected void validateRemoteGroup(
			long groupId, long remoteGroupId, String remoteAddress,
			int remotePort, String remotePathContext, boolean secureConnection)
		throws PortalException {

		if (remoteGroupId <= 0) {
			RemoteOptionsException remoteOptionsException =
				new RemoteOptionsException(
					RemoteOptionsException.REMOTE_GROUP_ID);

			remoteOptionsException.setRemoteGroupId(remoteGroupId);

			throw remoteOptionsException;
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		String remoteURL = StagingURLHelperUtil.buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection);

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			remoteURL, user.getLogin(), user.getPassword(),
			user.isPasswordEncrypted());

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			// Ping the remote host and verify that the remote group exists in
			// the same company as the remote user

			try {
				MethodKey methodKey = new MethodKey(
					GroupService.class, "checkRemoteStagingGroup",
					_CHECK_REMOTE_STAGING_GROUP_PARAMETER_TYPES);

				MethodHandler methodHandler = new MethodHandler(
					methodKey, remoteGroupId);

				try {
					TunnelUtil.invoke(httpPrincipal, methodHandler);
				}
				catch (Exception exception) {
					if (exception instanceof PortalException) {
						throw (PortalException)exception;
					}

					throw new SystemException(exception);
				}
			}
			catch (SystemException systemException) {
				if (systemException.getCause() instanceof ConnectException) {
					_log.error(
						"Unable to connect to remote live: " +
							systemException.getMessage());

					if (_log.isDebugEnabled()) {
						_log.debug(systemException, systemException);
					}
				}
				else {
					_log.error(systemException, systemException);
				}

				throw systemException;
			}

			// Ensure that the local group and the remote group are not the same
			// group and that they are either both company groups or both not
			// company groups

			Group group = groupLocalService.getGroup(groupId);

			Group remoteGroup = GroupServiceHttp.getGroup(
				httpPrincipal, remoteGroupId);

			if (group.isCompany() ^
				isCompanyGroup(httpPrincipal, remoteGroup)) {

				RemoteExportException remoteExportException =
					new RemoteExportException(
						RemoteExportException.INVALID_GROUP);

				remoteExportException.setGroupId(remoteGroupId);

				throw remoteExportException;
			}
		}
		catch (NoSuchGroupException noSuchGroupException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchGroupException, noSuchGroupException);
			}

			RemoteExportException remoteExportException =
				new RemoteExportException(RemoteExportException.NO_GROUP);

			remoteExportException.setGroupId(remoteGroupId);

			throw remoteExportException;
		}
		catch (PrincipalException principalException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(principalException, principalException);
			}

			RemoteExportException remoteExportException =
				new RemoteExportException(RemoteExportException.NO_PERMISSIONS);

			remoteExportException.setGroupId(remoteGroupId);

			throw remoteExportException;
		}
		catch (RemoteAuthException remoteAuthException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(remoteAuthException, remoteAuthException);
			}

			remoteAuthException.setURL(remoteURL);

			throw remoteAuthException;
		}
		catch (SystemException systemException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(systemException, systemException);
			}

			RemoteExportException remoteExportException =
				new RemoteExportException(
					RemoteExportException.BAD_CONNECTION,
					systemException.getMessage());

			remoteExportException.setURL(remoteURL);

			throw remoteExportException;
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected File publicLARFile;

	private void _validateGroupKeyChange(long groupId, String typeSettings)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		if (!Objects.equals(group.getClassName(), Group.class.getName()) &&
			(group.getType() != GroupConstants.TYPE_DEPOT)) {

			return;
		}

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		String defaultLanguageId = typeSettingsProperties.getProperty(
			"languageId", LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		Map<Locale, String> nameMap = group.getNameMap();

		if ((nameMap != null) &&
			Validator.isNotNull(nameMap.get(defaultLocale)) &&
			(group.getCompanyId() > 0)) {

			validateGroupKey(
				group.getGroupId(), group.getCompanyId(),
				nameMap.get(defaultLocale), group.getType(), group.isSite());
		}
	}

	private static final Class<?>[]
		_CHECK_REMOTE_STAGING_GROUP_PARAMETER_TYPES = new Class<?>[] {
			long.class
		};

	private static final Log _log = LogFactoryUtil.getLog(
		GroupLocalServiceImpl.class);

	private volatile long[] _classNameIds;
	private volatile long[] _complexSQLClassNameIds;
	private final Map<String, Group> _systemGroupsMap = new HashMap<>();

}
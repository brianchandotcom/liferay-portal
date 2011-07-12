/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.DuplicateUserGroupException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.RequiredUserGroupException;
import com.liferay.portal.UserGroupNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupConstants;
import com.liferay.portal.security.ldap.LDAPUserGroupTransactionThreadLocal;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.base.UserGroupLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Charles May
 * @author Miguel Pastor
 */
public class UserGroupLocalServiceImpl extends UserGroupLocalServiceBaseImpl {

	public void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws SystemException {

		groupPersistence.addUserGroups(groupId, userGroupIds);

		PermissionCacheUtil.clearCache();
	}

	public void addTeamUserGroups(long teamId, long[] userGroupIds)
		throws SystemException {

		teamPersistence.addUserGroups(teamId, userGroupIds);

		PermissionCacheUtil.clearCache();
	}

	public UserGroup addUserGroup(
			long userId, long companyId, String name, String description,
			long publicLayoutSetPrototypeId, long privateLayoutSetPrototypeId)
		throws PortalException, SystemException {

		// User Group

		validate(0, companyId, name);

		long userGroupId = counterLocalService.increment();

		UserGroup userGroup = userGroupPersistence.create(userGroupId);

		userGroup.setCompanyId(companyId);
		userGroup.setParentUserGroupId(
			UserGroupConstants.DEFAULT_PARENT_USER_GROUP_ID);
		userGroup.setName(name);
		userGroup.setDescription(description);
		userGroup.setPublicLayoutSetPrototypeId(publicLayoutSetPrototypeId);
		userGroup.setPrivateLayoutSetPrototypeId(privateLayoutSetPrototypeId);
		userGroup.setAddedByLDAPImport(
			LDAPUserGroupTransactionThreadLocal.isOriginatesFromLDAP());

		userGroupPersistence.update(userGroup, false);

		// Group

		groupLocalService.addGroup(
			userId, UserGroup.class.getName(), userGroup.getUserGroupId(),
			String.valueOf(userGroupId), null, 0, null, false, true, null);

		// Resources

		resourceLocalService.addResources(
			companyId, 0, userId, UserGroup.class.getName(),
			userGroup.getUserGroupId(), false, false, false);

		return userGroup;
	}

	public void clearUserUserGroups(long userId) throws SystemException {
		userPersistence.clearUserGroups(userId);

		PermissionCacheUtil.clearCache();
	}

	public void copyUserGroupLayouts(long userGroupId, long userIds[])
		throws PortalException, SystemException {

		Map<String, String[]> parameterMap = getLayoutTemplatesParameters();

		File[] files = exportLayouts(userGroupId, parameterMap);

		try {
			for (long userId : userIds) {
				if (!userGroupPersistence.containsUser(userGroupId, userId)) {
					importLayouts(userId, parameterMap, files[0], files[1]);
				}
			}
		}
		finally {
			if (files[0] != null) {
				files[0].delete();
			}

			if (files[1] != null) {
				files[1].delete();
			}
		}
	}

	public void copyUserGroupLayouts(long userGroupIds[], long userId)
		throws PortalException, SystemException {

		for (long userGroupId : userGroupIds) {
			if (!userGroupPersistence.containsUser(userGroupId, userId)) {
				copyUserGroupLayouts(userGroupId, userId);
			}
		}
	}

	public void copyUserGroupLayouts(long userGroupId, long userId)
		throws PortalException, SystemException {

		Map<String, String[]> parameterMap = getLayoutTemplatesParameters();

		File[] files = exportLayouts(userGroupId, parameterMap);

		try {
			importLayouts(userId, parameterMap, files[0], files[1]);
		}
		finally {
			if (files[0] != null) {
				files[0].delete();
			}

			if (files[1] != null) {
				files[1].delete();
			}
		}
	}

	@Override
	public void deleteUserGroup(long userGroupId)
		throws PortalException, SystemException {

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		deleteUserGroup(userGroup);
	}

	@Override
	public void deleteUserGroup(UserGroup userGroup)
		throws PortalException, SystemException {

		int count = userLocalService.getUserGroupUsersCount(
			userGroup.getUserGroupId(), WorkflowConstants.STATUS_APPROVED);

		if (count > 0) {
			throw new RequiredUserGroupException();
		}

		// Users

		clearUserUserGroups(userGroup.getUserGroupId());

		// Group

		Group group = userGroup.getGroup();

		groupLocalService.deleteGroup(group);

		// User group roles

		userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByUserGroupId(
			userGroup.getUserGroupId());

		// Resources

		resourceLocalService.deleteResource(
			userGroup.getCompanyId(), UserGroup.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, userGroup.getUserGroupId());

		// User group

		userGroupPersistence.remove(userGroup);

		// Permission cache

		PermissionCacheUtil.clearCache();
	}

	@Override
	public UserGroup getUserGroup(long userGroupId)
		throws PortalException, SystemException {

		return userGroupPersistence.findByPrimaryKey(userGroupId);
	}

	public UserGroup getUserGroup(long companyId, String name)
		throws PortalException, SystemException {

		return userGroupPersistence.findByC_N(companyId, name);
	}

	public List<UserGroup> getUserGroups(long companyId)
		throws SystemException {

		return userGroupPersistence.findByCompanyId(companyId);
	}

	public List<UserGroup> getUserGroups(long[] userGroupIds)
		throws PortalException, SystemException {

		List<UserGroup> userGroups = new ArrayList<UserGroup>(
			userGroupIds.length);

		for (long userGroupId : userGroupIds) {
			UserGroup userGroup = getUserGroup(userGroupId);

			userGroups.add(userGroup);
		}

		return userGroups;
	}

	public List<UserGroup> getUserUserGroups(long userId)
		throws SystemException {

		return userPersistence.getUserGroups(userId);
	}

	public boolean hasGroupUserGroup(long groupId, long userGroupId)
		throws SystemException {

		return groupPersistence.containsUserGroup(groupId, userGroupId);
	}

	public boolean hasTeamUserGroup(long teamId, long userGroupId)
		throws SystemException {

		return teamPersistence.containsUserGroup(teamId, userGroupId);
	}

	public List<UserGroup> search(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return userGroupFinder.findByC_N_D(
			companyId, name, description, params, start, end, obc);
	}

	public int searchCount(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		return userGroupFinder.countByC_N_D(
			companyId, name, description, params);
	}

	public void setUserUserGroups(long userId, long[] userGroupIds)
		throws PortalException, SystemException {

		copyUserGroupLayouts(userGroupIds, userId);

		userPersistence.setUserGroups(userId, userGroupIds);

		Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

		indexer.reindex(userId);

		PermissionCacheUtil.clearCache();
	}

	public void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws SystemException {

		List<Team> teams = teamPersistence.findByGroupId(groupId);

		for (Team team : teams) {
			teamPersistence.removeUserGroups(team.getTeamId(), userGroupIds);
		}

		userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
			userGroupIds, groupId);

		groupPersistence.removeUserGroups(groupId, userGroupIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetTeamUserGroups(long teamId, long[] userGroupIds)
		throws SystemException {

		teamPersistence.removeUserGroups(teamId, userGroupIds);

		PermissionCacheUtil.clearCache();
	}

	public UserGroup updateUserGroup(
			long companyId, long userGroupId, String name, String description,
			long publicLayoutSetPrototypeId, long privateLayoutSetPrototypeId)
		throws PortalException, SystemException {

		validate(userGroupId, companyId, name);

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		userGroup.setName(name);
		userGroup.setDescription(description);
		userGroup.setPublicLayoutSetPrototypeId(publicLayoutSetPrototypeId);
		userGroup.setPrivateLayoutSetPrototypeId(privateLayoutSetPrototypeId);

		userGroupPersistence.update(userGroup, false);

		return userGroup;
	}

	protected File[] exportLayouts(
			long userGroupId, Map<String, String[]> parameterMap)
		throws PortalException, SystemException {

		File[] files = new File[2];

		UserGroup userGroup = userGroupLocalService.getUserGroup(userGroupId);

		if (userGroup.getPrivateLayoutSetPrototypeId() > 0) {
			LayoutSetPrototype layoutSetPrototype =
				layoutSetPrototypeLocalService.getLayoutSetPrototype(
					userGroup.getPrivateLayoutSetPrototypeId());

			Group group = layoutSetPrototype.getGroup();

			files[0] = layoutLocalService.exportLayoutsAsFile(
				group.getGroupId(), true, null, parameterMap, null, null);
		}

		if (userGroup.getPublicLayoutSetPrototypeId() > 0) {
			LayoutSetPrototype layoutSetPrototype =
				layoutSetPrototypeLocalService.getLayoutSetPrototype(
					userGroup.getPublicLayoutSetPrototypeId());

			Group group = layoutSetPrototype.getGroup();

			files[1] = layoutLocalService.exportLayoutsAsFile(
				group.getGroupId(), true, null, parameterMap, null, null);
		}

		return files;
	}

	protected Map<String, String[]> getLayoutTemplatesParameters() {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {PortletDataHandlerKeys.
				LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_NAME});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLETS_MERGE_MODE,
			new String[] {PortletDataHandlerKeys.
				PORTLETS_MERGE_MODE_ADD_TO_BOTTOM});
		parameterMap.put(
			PortletDataHandlerKeys.THEME,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});

		return parameterMap;
	}

	protected void importLayouts(
			long userId, Map<String, String[]> parameterMap,
			File privateLayoutsFile, File publicLayoutsFile)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		long groupId = user.getGroup().getGroupId();

		if (privateLayoutsFile != null) {
			layoutLocalService.importLayouts(
				userId, groupId, true, parameterMap, privateLayoutsFile);
		}

		if (publicLayoutsFile != null) {
			layoutLocalService.importLayouts(
				userId, groupId, false, parameterMap, publicLayoutsFile);
		}
	}

	protected void validate(long userGroupId, long companyId, String name)
		throws PortalException, SystemException {

		if ((Validator.isNull(name)) ||
			(name.indexOf(CharPool.COMMA) != -1) ||
			(name.indexOf(CharPool.STAR) != -1)) {

			throw new UserGroupNameException();
		}

		if (Validator.isNumber(name) &&
			!PropsValues.USER_GROUPS_NAME_ALLOW_NUMERIC) {

			throw new UserGroupNameException();
		}

		try {
			UserGroup userGroup = userGroupFinder.findByC_N(companyId, name);

			if (userGroup.getUserGroupId() != userGroupId) {
				throw new DuplicateUserGroupException();
			}
		}
		catch (NoSuchUserGroupException nsuge) {
		}
	}

}
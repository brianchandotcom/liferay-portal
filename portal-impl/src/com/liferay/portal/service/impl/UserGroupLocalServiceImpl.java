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

import com.liferay.portal.DuplicateUserGroupException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.RequiredUserGroupException;
import com.liferay.portal.UserGroupNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupConstants;
import com.liferay.portal.security.ldap.LDAPUserGroupTransactionThreadLocal;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.UserGroupLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.usersadmin.util.UsersAdmin;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, deleting, and updating user
 * groups.
 *
 * @author Charles May
 */
public class UserGroupLocalServiceImpl extends UserGroupLocalServiceBaseImpl {

	/**
	 * Adds the user groups to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  userGroupIds the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws SystemException {

		groupPersistence.addUserGroups(groupId, userGroupIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Adds the user groups to the team.
	 *
	 * @param  teamId the primary key of the team
	 * @param  userGroupIds the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addTeamUserGroups(long teamId, long[] userGroupIds)
		throws SystemException {

		teamPersistence.addUserGroups(teamId, userGroupIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Adds a user group.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the user group,
	 * including its resources, metadata, and internal data structures. It is
	 * not necessary to make subsequent calls to setup default groups and
	 * resources for the user group.
	 * </p>
	 *
	 * @param      userId the primary key of the user
	 * @param      companyId the primary key of the user group's company
	 * @param      name the user group's name
	 * @param      description the user group's description
	 * @return     the user group
	 * @throws     PortalException if the user group's information was invalid
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addUserGroup(long, long,
	 *             String, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public UserGroup addUserGroup(
			long userId, long companyId, String name, String description)
		throws PortalException, SystemException {

		return addUserGroup(userId, companyId, name, description, null);
	}

	/**
	 * Adds a user group.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the user group,
	 * including its resources, metadata, and internal data structures. It is
	 * not necessary to make subsequent calls to setup default groups and
	 * resources for the user group.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  companyId the primary key of the user group's company
	 * @param  name the user group's name
	 * @param  description the user group's description
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set expando bridge attributes for the
	 *         user group.
	 * @return the user group
	 * @throws PortalException if the user group's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserGroup addUserGroup(
			long userId, long companyId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// User group

		Date now = new Date();

		validate(0, companyId, name);

		User user = userPersistence.findByPrimaryKey(userId);

		long userGroupId = counterLocalService.increment();

		UserGroup userGroup = userGroupPersistence.create(userGroupId);

		if (serviceContext != null) {
			userGroup.setUuid(serviceContext.getUuid());
		}

		userGroup.setCompanyId(companyId);
		userGroup.setUserId(user.getUserId());
		userGroup.setUserName(user.getFullName());

		if (serviceContext != null) {
			userGroup.setCreateDate(serviceContext.getCreateDate(now));
			userGroup.setModifiedDate(serviceContext.getModifiedDate(now));
		}
		else {
			userGroup.setCreateDate(now);
			userGroup.setModifiedDate(now);
		}

		userGroup.setParentUserGroupId(
			UserGroupConstants.DEFAULT_PARENT_USER_GROUP_ID);
		userGroup.setName(name);
		userGroup.setDescription(description);
		userGroup.setAddedByLDAPImport(
			LDAPUserGroupTransactionThreadLocal.isOriginatesFromLDAP());
		userGroup.setExpandoBridgeAttributes(serviceContext);

		userGroupPersistence.update(userGroup);

		// Group

		groupLocalService.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			UserGroup.class.getName(), userGroup.getUserGroupId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, String.valueOf(userGroupId),
			null, 0, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null,
			false, true, null);

		// Resources

		resourceLocalService.addResources(
			companyId, 0, userId, UserGroup.class.getName(),
			userGroup.getUserGroupId(), false, false, false);

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			UserGroup.class);

		indexer.reindex(userGroup);

		return userGroup;
	}

	/**
	 * Clears all associations between the user and its user groups and clears
	 * the permissions cache.
	 *
	 * <p>
	 * This method is called from {@link #deleteUserGroup(UserGroup)}.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void clearUserUserGroups(long userId) throws SystemException {
		userPersistence.clearUserGroups(userId);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Copies the user group's layout to the user.
	 *
	 * @param      userGroupId the primary key of the user group
	 * @param      userId the primary key of the user
	 * @throws     PortalException if a user with the primary key could not be
	 *             found or if a portal exception occurred
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	@Override
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

	/**
	 * Copies the user group's layouts to the users who are not already members
	 * of the user group.
	 *
	 * @param      userGroupId the primary key of the user group
	 * @param      userIds the primary keys of the users
	 * @throws     PortalException if any one of the users could not be found or
	 *             if a portal exception occurred
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	@Override
	public void copyUserGroupLayouts(long userGroupId, long[] userIds)
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

	/**
	 * Copies the user groups' layouts to the user.
	 *
	 * @param      userGroupIds the primary keys of the user groups
	 * @param      userId the primary key of the user
	 * @throws     PortalException if a user with the primary key could not be
	 *             found or if a portal exception occurred
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	@Override
	public void copyUserGroupLayouts(long[] userGroupIds, long userId)
		throws PortalException, SystemException {

		for (long userGroupId : userGroupIds) {
			if (!userGroupPersistence.containsUser(userGroupId, userId)) {
				copyUserGroupLayouts(userGroupId, userId);
			}
		}
	}

	/**
	 * Deletes the user group.
	 *
	 * @param  userGroupId the primary key of the user group
	 * @return the deleted user group
	 * @throws PortalException if a user group with the primary key could not be
	 *         found or if the user group had a workflow in approved status
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserGroup deleteUserGroup(long userGroupId)
		throws PortalException, SystemException {

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		return userGroupLocalService.deleteUserGroup(userGroup);
	}

	/**
	 * Deletes the user group.
	 *
	 * @param  userGroup the user group
	 * @return the deleted user group
	 * @throws PortalException if the organization had a workflow in approved
	 *         status
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE)
	public UserGroup deleteUserGroup(UserGroup userGroup)
		throws PortalException, SystemException {

		int count = userLocalService.getUserGroupUsersCount(
			userGroup.getUserGroupId(), WorkflowConstants.STATUS_APPROVED);

		if (count > 0) {
			throw new RequiredUserGroupException();
		}

		// Expando

		expandoRowLocalService.deleteRows(userGroup.getUserGroupId());

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

		return userGroup;
	}

	@Override
	public void deleteUserGroups(long companyId)
		throws PortalException, SystemException {

		List<UserGroup> userGroups = userGroupPersistence.findByCompanyId(
			companyId);

		for (UserGroup userGroup : userGroups) {
			userGroupLocalService.deleteUserGroup(userGroup);
		}
	}

	@Override
	public UserGroup fetchUserGroup(long companyId, String name)
		throws SystemException {

		return userGroupPersistence.fetchByC_N(companyId, name);
	}

	/**
	 * Returns the user group with the name.
	 *
	 * @param  companyId the primary key of the user group's company
	 * @param  name the user group's name
	 * @return Returns the user group with the name
	 * @throws PortalException if a user group with the name could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserGroup getUserGroup(long companyId, String name)
		throws PortalException, SystemException {

		return userGroupPersistence.findByC_N(companyId, name);
	}

	/**
	 * Returns all the user groups belonging to the company.
	 *
	 * @param  companyId the primary key of the user groups' company
	 * @return the user groups belonging to the company
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<UserGroup> getUserGroups(long companyId)
		throws SystemException {

		return userGroupPersistence.findByCompanyId(companyId);
	}

	/**
	 * Returns all the user groups with the primary keys.
	 *
	 * @param  userGroupIds the primary keys of the user groups
	 * @return the user groups with the primary keys
	 * @throws PortalException if any one of the user groups could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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

	/**
	 * Returns an ordered range of all the user groups that match the keywords.
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
	 * @param  companyId the primary key of the user group's company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         user group's name or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.UserGroupFinder}
	 * @param  start the lower bound of the range of user groups to return
	 * @param  end the upper bound of the range of user groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the user groups (optionally
	 *         <code>null</code>)
	 * @return the matching user groups ordered by comparator <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.UserGroupFinder
	 */
	@Override
	public List<UserGroup> search(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return userGroupFinder.findByKeywords(
			companyId, keywords, params, start, end, obc);
	}

	/**
	 * Returns an ordered range of all the user groups that match the keywords,
	 * using the indexer. It is preferable to use this method instead of the
	 * non-indexed version whenever possible for performance reasons.
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
	 * @param  companyId the primary key of the user group's company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         user group's name or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). For more
	 *         information see {@link
	 *         com.liferay.portlet.usergroupsadmin.util.UserGroupIndexer}
	 * @param  start the lower bound of the range of user groups to return
	 * @param  end the upper bound of the range of user groups to return (not
	 *         inclusive)
	 * @param  sort the field and direction by which to sort (optionally
	 *         <code>null</code>)
	 * @return the matching user groups ordered by sort
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portlet.usergroupsadmin.util.UserGroupIndexer
	 */
	@Override
	public Hits search(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end, Sort sort)
		throws SystemException {

		String name = null;
		String description = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			name = keywords;
			description = keywords;
		}
		else {
			andOperator = true;
		}

		if (params != null) {
			params.put("keywords", keywords);
		}

		return search(
			companyId, name, description, params, andOperator, start, end,
			sort);
	}

	/**
	 * Returns an ordered range of all the user groups that match the name and
	 * description.
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
	 * @param  companyId the primary key of the user group's company
	 * @param  name the user group's name (optionally <code>null</code>)
	 * @param  description the user group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.UserGroupFinder}
	 * @param  andOperator whether every field must match its keywords or just
	 *         one field
	 * @param  start the lower bound of the range of user groups to return
	 * @param  end the upper bound of the range of user groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the user groups (optionally
	 *         <code>null</code>)
	 * @return the matching user groups ordered by comparator <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.UserGroupFinder
	 */
	@Override
	public List<UserGroup> search(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		return userGroupFinder.findByC_N_D(
			companyId, name, description, params, andOperator, start, end, obc);
	}

	/**
	 * Returns an ordered range of all the user groups that match the name and
	 * description. It is preferable to use this method instead of the
	 * non-indexed version whenever possible for performance reasons.
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
	 * @param  companyId the primary key of the user group's company
	 * @param  name the user group's name (optionally <code>null</code>)
	 * @param  description the user group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). For more
	 *         information see {@link
	 *         com.liferay.portlet.usergroupsadmin.util.UserGroupIndexer}
	 * @param  andSearch whether every field must match its keywords or just one
	 *         field
	 * @param  start the lower bound of the range of user groups to return
	 * @param  end the upper bound of the range of user groups to return (not
	 *         inclusive)
	 * @param  sort the field and direction by which to sort (optionally
	 *         <code>null</code>)
	 * @return the matching user groups ordered by sort
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.UserGroupFinder
	 */
	@Override
	public Hits search(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, boolean andSearch, int start,
			int end, Sort sort)
		throws SystemException {

		try {
			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				UserGroup.class);

			SearchContext searchContext = buildSearchContext(
				companyId, name, description, params, andSearch, start, end,
				sort);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Returns the number of user groups that match the keywords
	 *
	 * @param  companyId the primary key of the user group's company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         user group's name or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.UserGroupFinder}
	 * @return the number of matching user groups
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.UserGroupFinder
	 */
	@Override
	public int searchCount(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		return userGroupFinder.countByKeywords(companyId, keywords, params);
	}

	/**
	 * Returns the number of user groups that match the name and description.
	 *
	 * @param  companyId the primary key of the user group's company
	 * @param  name the user group's name (optionally <code>null</code>)
	 * @param  description the user group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). For more
	 *         information see {@link
	 *         com.liferay.portal.service.persistence.UserGroupFinder}
	 * @param  andOperator whether every field must match its keywords or just
	 *         one field
	 * @return the number of matching user groups
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.service.persistence.UserGroupFinder
	 */
	@Override
	public int searchCount(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, boolean andOperator)
		throws SystemException {

		return userGroupFinder.countByC_N_D(
			companyId, name, description, params, andOperator);
	}

	@Override
	public BaseModelSearchResult<UserGroup> searchUserGroups(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end, Sort sort)
		throws PortalException, SystemException {

		String name = null;
		String description = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			name = keywords;
			description = keywords;
		}
		else {
			andOperator = true;
		}

		if (params != null) {
			params.put("keywords", keywords);
		}

		return searchUserGroups(
			companyId, name, description, params, andOperator, start, end,
			sort);
	}

	@Override
	public BaseModelSearchResult<UserGroup> searchUserGroups(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, boolean andSearch, int start,
			int end, Sort sort)
		throws PortalException, SystemException {

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			UserGroup.class);

		SearchContext searchContext = buildSearchContext(
			companyId, name, description, params, andSearch, start, end, sort);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(
				searchContext, UsersAdmin.USER_GROUP_SELECTED_FIELD_NAMES);

			List<UserGroup> userGroups = UsersAdminUtil.getUserGroups(hits);

			if (userGroups != null) {
				return new BaseModelSearchResult<UserGroup>(
					userGroups, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	/**
	 * Sets the user groups associated with the user copying the user group
	 * layouts and removing and adding user group associations for the user as
	 * necessary.
	 *
	 * @param  userId the primary key of the user
	 * @param  userGroupIds the primary keys of the user groups
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setUserUserGroups(long userId, long[] userGroupIds)
		throws PortalException, SystemException {

		copyUserGroupLayouts(userGroupIds, userId);

		userPersistence.setUserGroups(userId, userGroupIds);

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		indexer.reindex(userId);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Removes the user groups from the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  userGroupIds the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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

	/**
	 * Removes the user groups from the team.
	 *
	 * @param  teamId the primary key of the team
	 * @param  userGroupIds the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void unsetTeamUserGroups(long teamId, long[] userGroupIds)
		throws SystemException {

		teamPersistence.removeUserGroups(teamId, userGroupIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Updates the user group.
	 *
	 * @param      companyId the primary key of the user group's company
	 * @param      userGroupId the primary key of the user group
	 * @param      name the user group's name
	 * @param      description the user group's description
	 * @return     the user group
	 * @throws     PortalException if a user group with the primary key could
	 *             not be found or if the new information was invalid
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #updateUserGroup(long, long,
	 *             String, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public UserGroup updateUserGroup(
			long companyId, long userGroupId, String name, String description)
		throws PortalException, SystemException {

		return updateUserGroup(companyId, userGroupId, name, description, null);
	}

	/**
	 * Updates the user group.
	 *
	 * @param  companyId the primary key of the user group's company
	 * @param  userGroupId the primary key of the user group
	 * @param  name the user group's name
	 * @param  description the user group's description
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set expando bridge attributes for the
	 *         user group.
	 * @return the user group
	 * @throws PortalException if a user group with the primary key could not be
	 *         found or if the new information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserGroup updateUserGroup(
			long companyId, long userGroupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// User group

		validate(userGroupId, companyId, name);

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		userGroup.setModifiedDate(new Date());
		userGroup.setName(name);
		userGroup.setDescription(description);
		userGroup.setExpandoBridgeAttributes(serviceContext);

		userGroupPersistence.update(userGroup);

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			UserGroup.class);

		indexer.reindex(userGroup);

		return userGroup;
	}

	protected SearchContext buildSearchContext(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andSearch, int start,
		int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(andSearch);

		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		attributes.put("description", description);
		attributes.put("name", name);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (params != null) {
			String keywords = (String)params.remove("keywords");

			if (Validator.isNotNull(keywords)) {
				searchContext.setKeywords(keywords);
			}
		}

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	protected File[] exportLayouts(
			long userGroupId, Map<String, String[]> parameterMap)
		throws PortalException, SystemException {

		File[] files = new File[2];

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		Group group = userGroup.getGroup();

		if (userGroup.hasPrivateLayouts()) {
			files[0] = layoutLocalService.exportLayoutsAsFile(
				group.getGroupId(), true, null, parameterMap, null, null);
		}

		if (userGroup.hasPublicLayouts()) {
			files[1] = layoutLocalService.exportLayoutsAsFile(
				group.getGroupId(), false, null, parameterMap, null, null);
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
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {
				PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_NAME
			});
		parameterMap.put(
			PortletDataHandlerKeys.LOGO,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLETS_MERGE_MODE,
			new String[] {
				PortletDataHandlerKeys.PORTLETS_MERGE_MODE_ADD_TO_BOTTOM
			});
		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});

		return parameterMap;
	}

	protected void importLayouts(
			long userId, Map<String, String[]> parameterMap,
			File privateLayoutsFile, File publicLayoutsFile)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		long groupId = user.getGroupId();

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

		if (Validator.isNull(name) ||
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
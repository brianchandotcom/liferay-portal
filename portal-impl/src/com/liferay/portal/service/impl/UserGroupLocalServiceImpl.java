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

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.DuplicateUserGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredUserGroupException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.exception.UserGroupNameException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.exportimport.UserGroupImportTransactionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.base.UserGroupLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.constants.UserGroupFinderConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides the local service for accessing, adding, deleting, and updating user
 * groups.
 *
 * @author Charles May
 */
public class UserGroupLocalServiceImpl extends UserGroupLocalServiceBaseImpl {

	/**
	 * Adds the user group to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userGroupId the primary key of the user group
	 */
	@Override
	public void addGroupUserGroup(long groupId, long userGroupId) {
		super.addGroupUserGroup(groupId, userGroupId);

		try {
			reindexUsers(userGroupId);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	/**
	 * Adds the user group to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userGroup the user group
	 */
	@Override
	public void addGroupUserGroup(long groupId, UserGroup userGroup) {
		super.addGroupUserGroup(groupId, userGroup);

		try {
			reindexUsers(userGroup);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	/**
	 * Adds the user groups to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userGroups the user groups
	 */
	@Override
	public void addGroupUserGroups(long groupId, List<UserGroup> userGroups) {
		super.addGroupUserGroups(groupId, userGroups);

		try {
			reindexUsers(userGroups);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	/**
	 * Adds the user groups to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userGroupIds the primary keys of the user groups
	 */
	@Override
	public void addGroupUserGroups(long groupId, long[] userGroupIds) {
		super.addGroupUserGroups(groupId, userGroupIds);

		try {
			reindexUsers(userGroupIds);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
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
	 */
	@Override
	public UserGroup addUserGroup(
			long userId, long companyId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// User group

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
		userGroup.setParentUserGroupId(
			UserGroupConstants.DEFAULT_PARENT_USER_GROUP_ID);
		userGroup.setName(name);
		userGroup.setDescription(description);
		userGroup.setAddedByLDAPImport(
			UserGroupImportTransactionThreadLocal.isOriginatesFromImport());
		userGroup.setExpandoBridgeAttributes(serviceContext);

		userGroupPersistence.update(userGroup);

		// Group

		groupLocalService.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			UserGroup.class.getName(), userGroup.getUserGroupId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			getLocalizationMap(String.valueOf(userGroupId)), null, 0, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);

		// Resources

		resourceLocalService.addResources(
			companyId, 0, userId, UserGroup.class.getName(),
			userGroup.getUserGroupId(), false, false, false);

		// Indexer

		Indexer<UserGroup> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			UserGroup.class);

		indexer.reindex(userGroup);

		return userGroup;
	}

	/**
	 * Copies the user group's layout to the user.
	 *
	 * @param      userGroupId the primary key of the user group
	 * @param      userId the primary key of the user
	 * @deprecated As of Paton (6.1.x)
	 */
	@Deprecated
	@Override
	public void copyUserGroupLayouts(long userGroupId, long userId)
		throws PortalException {

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
	 * @deprecated As of Newton (6.2.x)
	 */
	@Deprecated
	@Override
	public void copyUserGroupLayouts(long userGroupId, long[] userIds)
		throws PortalException {

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
	 * @deprecated As of Newton (6.2.x)
	 */
	@Deprecated
	@Override
	public void copyUserGroupLayouts(long[] userGroupIds, long userId)
		throws PortalException {

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
	 */
	@Override
	public UserGroup deleteUserGroup(long userGroupId) throws PortalException {
		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		return userGroupLocalService.deleteUserGroup(userGroup);
	}

	/**
	 * Deletes the user group.
	 *
	 * @param  userGroup the user group
	 * @return the deleted user group
	 */
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public UserGroup deleteUserGroup(UserGroup userGroup)
		throws PortalException {

		if (!CompanyThreadLocal.isDeleteInProcess()) {
			LinkedHashMap<String, Object> params =
				LinkedHashMapBuilder.<String, Object>put(
					"usersUserGroups", Long.valueOf(userGroup.getUserGroupId())
				).build();

			int count = userFinder.countByKeywords(
				userGroup.getCompanyId(), null,
				WorkflowConstants.STATUS_APPROVED, params);

			if (count > 0) {
				throw new RequiredUserGroupException();
			}
		}

		// Expando

		expandoRowLocalService.deleteRows(userGroup.getUserGroupId());

		// Group

		groupLocalService.deleteGroup(userGroup.getGroup());

		// User group roles

		userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByUserGroupId(
			userGroup.getUserGroupId());

		// Resources

		resourceLocalService.deleteResource(
			userGroup.getCompanyId(), UserGroup.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, userGroup.getUserGroupId());

		// User group

		userGroupPersistence.remove(userGroup);

		return userGroup;
	}

	@Override
	public void deleteUserGroups(long companyId) throws PortalException {
		List<UserGroup> userGroups = userGroupPersistence.findByCompanyId(
			companyId);

		for (UserGroup userGroup : userGroups) {
			userGroupLocalService.deleteUserGroup(userGroup);
		}
	}

	@Override
	public UserGroup fetchUserGroup(long companyId, String name) {
		return userGroupPersistence.fetchByC_N(companyId, name);
	}

	@Override
	public List<UserGroup> getGroupUserUserGroups(long groupId, long userId)
		throws PortalException {

		long[] groupUserGroupIds = groupPersistence.getUserGroupPrimaryKeys(
			groupId);

		if (groupUserGroupIds.length == 0) {
			return Collections.emptyList();
		}

		long[] userUserGroupIds = userPersistence.getUserGroupPrimaryKeys(
			userId);

		if (userUserGroupIds.length == 0) {
			return Collections.emptyList();
		}

		Set<Long> userGroupIds = SetUtil.intersect(
			groupUserGroupIds, userUserGroupIds);

		if (userGroupIds.isEmpty()) {
			return Collections.emptyList();
		}

		List<UserGroup> userGroups = new ArrayList<>(userGroupIds.size());

		for (Long userGroupId : userGroupIds) {
			userGroups.add(userGroupPersistence.findByPrimaryKey(userGroupId));
		}

		return userGroups;
	}

	/**
	 * Returns the user group with the name.
	 *
	 * @param  companyId the primary key of the user group's company
	 * @param  name the user group's name
	 * @return Returns the user group with the name
	 */
	@Override
	public UserGroup getUserGroup(long companyId, String name)
		throws PortalException {

		return userGroupPersistence.findByC_N(companyId, name);
	}

	/**
	 * Returns all the user groups belonging to the company.
	 *
	 * @param  companyId the primary key of the user groups' company
	 * @return the user groups belonging to the company
	 */
	@Override
	public List<UserGroup> getUserGroups(long companyId) {
		return userGroupPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<UserGroup> getUserGroups(
		long companyId, String name, int start, int end) {

		if (Validator.isNull(name)) {
			return userGroupPersistence.findByCompanyId(companyId, start, end);
		}

		return userGroupPersistence.findByC_LikeN(companyId, name, start, end);
	}

	/**
	 * Returns all the user groups with the primary keys.
	 *
	 * @param  userGroupIds the primary keys of the user groups
	 * @return the user groups with the primary keys
	 */
	@Override
	public List<UserGroup> getUserGroups(long[] userGroupIds)
		throws PortalException {

		List<UserGroup> userGroups = new ArrayList<>(userGroupIds.length);

		for (long userGroupId : userGroupIds) {
			userGroups.add(getUserGroup(userGroupId));
		}

		return userGroups;
	}

	@Override
	public int getUserGroupsCount(long companyId, String name) {
		if (Validator.isNull(name)) {
			return userGroupPersistence.countByCompanyId(companyId);
		}

		return userGroupPersistence.countByC_LikeN(companyId, name);
	}

	/**
	 * Returns an ordered range of all the user groups that match the keywords.
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
	 * @param  companyId the primary key of the user group's company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         user group's name or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). For more
	 *         information see {@link
	 *         com.liferay.portal.kernel.service.persistence.UserGroupFinder}
	 * @param  start the lower bound of the range of user groups to return
	 * @param  end the upper bound of the range of user groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the user groups (optionally
	 *         <code>null</code>)
	 * @return the matching user groups ordered by comparator <code>obc</code>
	 * @see    com.liferay.portal.kernel.service.persistence.UserGroupFinder
	 */
	@Override
	public List<UserGroup> search(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, OrderByComparator<UserGroup> obc) {

		if (isUseCustomSQL(params)) {
			return userGroupFinder.findByKeywords(
				companyId, keywords, params, start, end, obc);
		}

		String orderByCol = obc.getOrderByFields()[0];

		String orderByType = "asc";

		if (!obc.isAscending()) {
			orderByType = "desc";
		}

		Sort sort = SortFactoryUtil.getSort(
			UserGroup.class, orderByCol, orderByType);

		try {
			return UsersAdminUtil.getUserGroups(
				search(companyId, keywords, params, start, end, sort));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the user group's company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         user group's name or description (optionally <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). For more
	 *         information see {@link
	 *         com.liferay.user.groups.admin.web.search.UserGroupIndexer}
	 * @param  start the lower bound of the range of user groups to return
	 * @param  end the upper bound of the range of user groups to return (not
	 *         inclusive)
	 * @param  sort the field and direction by which to sort (optionally
	 *         <code>null</code>)
	 * @return the matching user groups ordered by sort
	 * @see    com.liferay.user.groups.admin.web.search.UserGroupIndexer
	 */
	@Override
	public Hits search(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, Sort sort) {

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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the user group's company
	 * @param  name the user group's name (optionally <code>null</code>)
	 * @param  description the user group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). For more
	 *         information see {@link
	 *         com.liferay.portal.kernel.service.persistence.UserGroupFinder}
	 * @param  andOperator whether every field must match its keywords or just
	 *         one field
	 * @param  start the lower bound of the range of user groups to return
	 * @param  end the upper bound of the range of user groups to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the user groups (optionally
	 *         <code>null</code>)
	 * @return the matching user groups ordered by comparator <code>obc</code>
	 * @see    com.liferay.portal.kernel.service.persistence.UserGroupFinder
	 */
	@Override
	public List<UserGroup> search(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<UserGroup> obc) {

		if (isUseCustomSQL(params)) {
			return userGroupFinder.findByC_N_D(
				companyId, name, description, params, andOperator, start, end,
				obc);
		}

		String orderByCol = obc.getOrderByFields()[0];

		String orderByType = "asc";

		if (!obc.isAscending()) {
			orderByType = "desc";
		}

		Sort sort = SortFactoryUtil.getSort(
			UserGroup.class, orderByCol, orderByType);

		try {
			return UsersAdminUtil.getUserGroups(
				search(
					companyId, name, description, params, andOperator, start,
					end, sort));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the user group's company
	 * @param  name the user group's name (optionally <code>null</code>)
	 * @param  description the user group's description (optionally
	 *         <code>null</code>)
	 * @param  params the finder params (optionally <code>null</code>). For more
	 *         information see {@link
	 *         com.liferay.user.groups.admin.web.search.UserGroupIndexer}
	 * @param  andSearch whether every field must match its keywords or just one
	 *         field
	 * @param  start the lower bound of the range of user groups to return
	 * @param  end the upper bound of the range of user groups to return (not
	 *         inclusive)
	 * @param  sort the field and direction by which to sort (optionally
	 *         <code>null</code>)
	 * @return the matching user groups ordered by sort
	 * @see    com.liferay.portal.kernel.service.persistence.UserGroupFinder
	 */
	@Override
	public Hits search(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andSearch, int start,
		int end, Sort sort) {

		try {
			Indexer<UserGroup> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
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
	 *         com.liferay.portal.kernel.service.persistence.UserGroupFinder}
	 * @return the number of matching user groups
	 * @see    com.liferay.portal.kernel.service.persistence.UserGroupFinder
	 */
	@Override
	public int searchCount(
		long companyId, String keywords, LinkedHashMap<String, Object> params) {

		if (isUseCustomSQL(params)) {
			return userGroupFinder.countByKeywords(companyId, keywords, params);
		}

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

		try {
			SearchContext searchContext = buildSearchContext(
				companyId, name, description, params, andOperator,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				UserGroup.class);

			return (int)indexer.searchCount(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
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
	 *         com.liferay.portal.kernel.service.persistence.UserGroupFinder}
	 * @param  andOperator whether every field must match its keywords or just
	 *         one field
	 * @return the number of matching user groups
	 * @see    com.liferay.portal.kernel.service.persistence.UserGroupFinder
	 */
	@Override
	public int searchCount(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator) {

		if (isUseCustomSQL(params)) {
			return userGroupFinder.countByC_N_D(
				companyId, name, description, params, andOperator);
		}

		try {
			SearchContext searchContext = buildSearchContext(
				companyId, name, description, params, true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				UserGroup.class);

			return (int)indexer.searchCount(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<UserGroup> searchUserGroups(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end, Sort sort)
		throws PortalException {

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
		throws PortalException {

		Indexer<UserGroup> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			UserGroup.class);

		SearchContext searchContext = buildSearchContext(
			companyId, name, description, params, andSearch, start, end, sort);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<UserGroup> userGroups = UsersAdminUtil.getUserGroups(hits);

			if (userGroups != null) {
				return new BaseModelSearchResult<>(
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
	 * @param userId the primary key of the user
	 * @param userGroupIds the primary keys of the user groups
	 */
	@Override
	public void setUserUserGroups(long userId, long[] userGroupIds)
		throws PortalException {

		if (PropsValues.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE) {
			copyUserGroupLayouts(userGroupIds, userId);
		}

		userPersistence.setUserGroups(userId, userGroupIds);

		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class);

		User user = userLocalService.fetchUser(userId);

		indexer.reindex(user);
	}

	/**
	 * Removes the user groups from the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userGroupIds the primary keys of the user groups
	 */
	@Override
	public void unsetGroupUserGroups(long groupId, long[] userGroupIds) {
		List<Team> teams = teamPersistence.findByGroupId(groupId);

		for (Team team : teams) {
			teamPersistence.removeUserGroups(team.getTeamId(), userGroupIds);
		}

		userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
			userGroupIds, groupId);

		groupPersistence.removeUserGroups(groupId, userGroupIds);

		try {
			reindexUsers(userGroupIds);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	/**
	 * Removes the user groups from the team.
	 *
	 * @param teamId the primary key of the team
	 * @param userGroupIds the primary keys of the user groups
	 */
	@Override
	public void unsetTeamUserGroups(long teamId, long[] userGroupIds) {
		teamPersistence.removeUserGroups(teamId, userGroupIds);
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
	 */
	@Override
	public UserGroup updateUserGroup(
			long companyId, long userGroupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// User group

		validate(userGroupId, companyId, name);

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		userGroup.setName(name);
		userGroup.setDescription(description);
		userGroup.setExpandoBridgeAttributes(serviceContext);

		userGroupPersistence.update(userGroup);

		// Indexer

		Indexer<UserGroup> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
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
			HashMapBuilder.<String, Serializable>put(
				"description", description
			).put(
				"name", name
			).build();

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (params != null) {
			String keywords = (String)params.remove("keywords");

			if (Validator.isNotNull(keywords)) {
				searchContext.setKeywords(keywords);
			}
		}

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected File[] exportLayouts(
			long userGroupId, Map<String, String[]> parameterMap)
		throws PortalException {

		File[] files = new File[2];

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		User user = userLocalService.getUser(
			GetterUtil.getLong(PrincipalThreadLocal.getName()));

		Group group = userGroup.getGroup();

		if (userGroup.hasPrivateLayouts()) {
			Map<String, Serializable> exportLayoutSettingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildExportLayoutSettingsMap(
						user, group.getGroupId(), true,
						ExportImportHelperUtil.getAllLayoutIds(
							group.getGroupId(), true),
						parameterMap);

			ExportImportConfiguration exportImportConfiguration =
				exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
						exportLayoutSettingsMap);

			files[0] = exportImportLocalService.exportLayoutsAsFile(
				exportImportConfiguration);
		}

		if (userGroup.hasPublicLayouts()) {
			Map<String, Serializable> exportLayoutSettingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildExportLayoutSettingsMap(
						user, group.getGroupId(), false,
						ExportImportHelperUtil.getAllLayoutIds(
							group.getGroupId(), false),
						parameterMap);

			ExportImportConfiguration exportImportConfiguration =
				exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
						exportLayoutSettingsMap);

			files[1] = exportImportLocalService.exportLayoutsAsFile(
				exportImportConfiguration);
		}

		return files;
	}

	protected Map<String, String[]> getLayoutTemplatesParameters() {
		return LinkedHashMapBuilder.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR}
		).put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {
				PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_NAME
			}
		).put(
			PortletDataHandlerKeys.LOGO, new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLETS_MERGE_MODE,
			new String[] {
				PortletDataHandlerKeys.PORTLETS_MERGE_MODE_ADD_TO_BOTTOM
			}
		).put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID}
		).build();
	}

	protected void importLayouts(
			long userId, Map<String, String[]> parameterMap,
			File privateLayoutsFile, File publicLayoutsFile)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		long groupId = user.getGroupId();

		if (privateLayoutsFile != null) {
			Map<String, Serializable> importLayoutSettingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildImportLayoutSettingsMap(
						user, groupId, true, null, parameterMap);

			ExportImportConfiguration exportImportConfiguration =
				exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
						importLayoutSettingsMap);

			exportImportLocalService.importLayouts(
				exportImportConfiguration, privateLayoutsFile);
		}

		if (publicLayoutsFile != null) {
			Map<String, Serializable> importLayoutSettingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildImportLayoutSettingsMap(
						user, groupId, false, null, parameterMap);

			ExportImportConfiguration exportImportConfiguration =
				exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
						importLayoutSettingsMap);

			exportImportLocalService.importLayouts(
				exportImportConfiguration, publicLayoutsFile);
		}
	}

	/**
	 * @see UserGroupServiceImpl#isUseCustomSQL
	 */
	protected boolean isUseCustomSQL(LinkedHashMap<String, Object> params) {
		if (MapUtil.isEmpty(params)) {
			return false;
		}

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			UserGroup.class);

		if (indexer.isIndexerEnabled() &&
			PropsValues.USER_GROUPS_SEARCH_WITH_INDEX &&
			MapUtil.isEmpty(params)) {

			return false;
		}

		for (String key : params.keySet()) {
			if (ArrayUtil.contains(UserGroupFinderConstants.PARAM_KEYS, key)) {
				return true;
			}
		}

		return false;
	}

	protected void reindex(long companyId, long[] userIds)
		throws PortalException {

		final Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class);

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
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
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index user " + user.getUserId(), pe);
						}
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(
			indexer.getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	protected void reindexUsers(List<UserGroup> userGroups)
		throws PortalException {

		for (UserGroup userGroup : userGroups) {
			reindexUsers(userGroup);
		}
	}

	protected void reindexUsers(long userGroupId) throws PortalException {
		reindexUsers(getUserGroup(userGroupId));
	}

	protected void reindexUsers(long[] userGroupIds) throws PortalException {
		for (long userGroupId : userGroupIds) {
			reindexUsers(userGroupId);
		}
	}

	protected void reindexUsers(UserGroup userGroup) throws PortalException {
		long companyId = userGroup.getCompanyId();

		long[] userIds = getUserPrimaryKeys(userGroup.getUserGroupId());

		if (ArrayUtil.isNotEmpty(userIds)) {
			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					reindex(companyId, userIds);

					return null;
				});
		}
	}

	protected void validate(long userGroupId, long companyId, String name)
		throws PortalException {

		if (Validator.isNull(name) || (name.indexOf(CharPool.COMMA) != -1) ||
			(name.indexOf(CharPool.STAR) != -1)) {

			throw new UserGroupNameException();
		}

		if (Validator.isNumber(name) &&
			!PropsValues.USER_GROUPS_NAME_ALLOW_NUMERIC) {

			throw new UserGroupNameException();
		}

		UserGroup userGroup = fetchUserGroup(companyId, name);

		if ((userGroup != null) &&
			(userGroup.getUserGroupId() != userGroupId)) {

			throw new DuplicateUserGroupException("{name=" + name + "}");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupLocalServiceImpl.class);

}
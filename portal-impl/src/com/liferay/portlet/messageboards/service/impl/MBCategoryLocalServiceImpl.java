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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.permission.ModelPermissions;
import com.liferay.portlet.messageboards.CategoryNameException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.service.base.MBCategoryLocalServiceBaseImpl;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashVersion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class MBCategoryLocalServiceImpl extends MBCategoryLocalServiceBaseImpl {

	@Override
	public MBCategory addCategory(
			long userId, long parentCategoryId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		return addCategory(
			userId, parentCategoryId, name, description,
			MBCategoryConstants.DEFAULT_DISPLAY_STYLE, null, null, null, 0,
			false, null, null, 0, null, false, null, 0, false, null, null,
			false, false, serviceContext);
	}

	@Override
	public MBCategory addCategory(
			long userId, long parentCategoryId, String name, String description,
			String displayStyle, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean allowAnonymous,
			boolean mailingListActive, ServiceContext serviceContext)
		throws PortalException {

		// Category

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		parentCategoryId = getParentCategoryId(groupId, parentCategoryId);

		validate(name);

		long categoryId = counterLocalService.increment();

		MBCategory category = mbCategoryPersistence.create(categoryId);

		category.setUuid(serviceContext.getUuid());
		category.setGroupId(groupId);
		category.setCompanyId(user.getCompanyId());
		category.setUserId(user.getUserId());
		category.setUserName(user.getFullName());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);
		category.setDisplayStyle(displayStyle);
		category.setExpandoBridgeAttributes(serviceContext);

		mbCategoryPersistence.update(category);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addCategoryResources(
				category, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addCategoryResources(
				category, serviceContext.getModelPermissions());
		}

		// Mailing list

		mbMailingListLocalService.addMailingList(
			userId, groupId, category.getCategoryId(), emailAddress, inProtocol,
			inServerName, inServerPort, inUseSSL, inUserName, inPassword,
			inReadInterval, outEmailAddress, outCustom, outServerName,
			outServerPort, outUseSSL, outUserName, outPassword, allowAnonymous,
			mailingListActive, serviceContext);

		return category;
	}

	@Override
	public void addCategoryResources(
			long categoryId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		addCategoryResources(
			category, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
			long categoryId, ModelPermissions modelPermissions)
		throws PortalException {

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		addCategoryResources(category, modelPermissions);
	}

	@Override
	public void addCategoryResources(
			MBCategory category, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), MBCategory.class.getName(),
			category.getCategoryId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
			MBCategory category, ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), MBCategory.class.getName(),
			category.getCategoryId(), modelPermissions);
	}

	@Override
	public void deleteCategories(long groupId) throws PortalException {
		List<MBCategory> categories = mbCategoryPersistence.findByGroupId(
			groupId);

		for (MBCategory category : categories) {
			mbCategoryLocalService.deleteCategory(category);
		}
	}

	@Override
	public void deleteCategory(long categoryId) throws PortalException {
		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		mbCategoryLocalService.deleteCategory(category);
	}

	@Override
	public void deleteCategory(MBCategory category) throws PortalException {
		mbCategoryLocalService.deleteCategory(category, true);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public void deleteCategory(
			MBCategory category, boolean includeTrashedEntries)
		throws PortalException {

		// Categories

		List<MBCategory> categories = mbCategoryPersistence.findByG_P(
			category.getGroupId(), category.getCategoryId());

		for (MBCategory curCategory : categories) {
			if (includeTrashedEntries || !curCategory.isInTrashExplicitly()) {
				mbCategoryLocalService.deleteCategory(
					curCategory, includeTrashedEntries);
			}
		}

		// Threads

		mbThreadLocalService.deleteThreads(
			category.getGroupId(), category.getCategoryId(),
			includeTrashedEntries);

		// Mailing list

		MBMailingList mbMailingList =
			mbMailingListLocalService.fetchCategoryMailingList(
				category.getGroupId(), category.getCategoryId());

		if (mbMailingList != null) {
			mbMailingListLocalService.deleteMailingList(mbMailingList);
		}

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			category.getCompanyId(), MBCategory.class.getName(),
			category.getCategoryId());

		// Expando

		expandoRowLocalService.deleteRows(category.getCategoryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			MBCategory.class.getName(), category.getCategoryId());

		// Resources

		resourceLocalService.deleteResource(
			category.getCompanyId(), MBCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, category.getCategoryId());

		// Trash

		if (category.isInTrashExplicitly()) {
			trashEntryLocalService.deleteEntry(
				MBCategory.class.getName(), category.getCategoryId());
		}
		else {
			trashVersionLocalService.deleteTrashVersion(
				MBCategory.class.getName(), category.getCategoryId());
		}

		// Category

		mbCategoryPersistence.remove(category);
	}

	@Override
	public List<MBCategory> getCategories(long groupId) {
		return mbCategoryPersistence.findByGroupId(groupId);
	}

	@Override
	public List<MBCategory> getCategories(long groupId, int status) {
		return mbCategoryPersistence.findByG_S(groupId, status);
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end) {

		return mbCategoryPersistence.findByG_P(
			groupId, parentCategoryId, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long parentCategoryId, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.findByG_P(
				groupId, parentCategoryId, start, end);
		}

		return mbCategoryPersistence.findByG_P_S(
			groupId, parentCategoryId, status, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long excludedCategoryId, long parentCategoryId,
		int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.findByNotC_G_P(
				excludedCategoryId, groupId, parentCategoryId, start, end);
		}

		return mbCategoryPersistence.findByNotC_G_P_S(
			excludedCategoryId, groupId, parentCategoryId, status, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int start, int end) {

		return mbCategoryPersistence.findByG_P(
			groupId, parentCategoryIds, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int status, int start,
		int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.findByG_P(
				groupId, parentCategoryIds, start, end);
		}

		return mbCategoryPersistence.findByG_P_S(
			groupId, parentCategoryIds, status, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
		long groupId, long[] excludedCategoryIds, long[] parentCategoryIds,
		int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.findByNotC_G_P(
				excludedCategoryIds, groupId, parentCategoryIds, start, end);
		}

		return mbCategoryPersistence.findByNotC_G_P_S(
			excludedCategoryIds, groupId, parentCategoryIds, status, start,
			end);
	}

	@Override
	public List<Object> getCategoriesAndThreads(long groupId, long categoryId) {
		List<Object> categoriesAndThreads = new ArrayList<>();

		List<MBCategory> categories = getCategories(
			groupId, categoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		categoriesAndThreads.addAll(categories);

		List<MBThread> threads = mbThreadLocalService.getThreads(
			groupId, categoryId, WorkflowConstants.STATUS_ANY,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		categoriesAndThreads.addAll(threads);

		return categoriesAndThreads;
	}

	@Override
	public int getCategoriesCount(long groupId) {
		return mbCategoryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getCategoriesCount(long groupId, int status) {
		return mbCategoryPersistence.countByG_S(groupId, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId) {
		return mbCategoryPersistence.countByG_P(groupId, parentCategoryId);
	}

	@Override
	public int getCategoriesCount(
		long groupId, long parentCategoryId, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.countByG_P(groupId, parentCategoryId);
		}

		return mbCategoryPersistence.countByG_P_S(
			groupId, parentCategoryId, status);
	}

	@Override
	public int getCategoriesCount(
		long groupId, long excludedCategoryId, long parentCategoryId,
		int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.countByNotC_G_P(
				excludedCategoryId, groupId, parentCategoryId);
		}

		return mbCategoryPersistence.countByNotC_G_P_S(
			excludedCategoryId, groupId, parentCategoryId, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long[] parentCategoryIds) {
		return mbCategoryPersistence.countByG_P(groupId, parentCategoryIds);
	}

	@Override
	public int getCategoriesCount(
		long groupId, long[] parentCategoryIds, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.countByG_P(groupId, parentCategoryIds);
		}

		return mbCategoryPersistence.countByG_P_S(
			groupId, parentCategoryIds, status);
	}

	@Override
	public int getCategoriesCount(
		long groupId, long[] excludedCategoryIds, long[] parentCategoryIds,
		int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.countByNotC_G_P(
				excludedCategoryIds, groupId, parentCategoryIds);
		}

		return mbCategoryPersistence.countByNotC_G_P_S(
			excludedCategoryIds, groupId, parentCategoryIds, status);
	}

	@Override
	public MBCategory getCategory(long categoryId) throws PortalException {
		MBCategory category = null;

		if ((categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(categoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			category = mbCategoryPersistence.findByPrimaryKey(categoryId);
		}
		else {
			category = new MBCategoryImpl();

			category.setCategoryId(categoryId);
			category.setParentCategoryId(categoryId);
		}

		return category;
	}

	@Override
	public List<MBCategory> getCompanyCategories(
		long companyId, int start, int end) {

		return mbCategoryPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public int getCompanyCategoriesCount(long companyId) {
		return mbCategoryPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<Long> getSubcategoryIds(
		List<Long> categoryIds, long groupId, long categoryId) {

		List<MBCategory> categories = mbCategoryPersistence.findByG_P(
			groupId, categoryId);

		for (MBCategory category : categories) {
			categoryIds.add(category.getCategoryId());

			getSubcategoryIds(
				categoryIds, category.getGroupId(), category.getCategoryId());
		}

		return categoryIds;
	}

	@Override
	public List<MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end) {

		QueryDefinition<MBCategory> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_ANY, start, end, null);

		return mbCategoryFinder.findByS_G_U_P(
			groupId, userId, null, queryDefinition);
	}

	@Override
	public int getSubscribedCategoriesCount(long groupId, long userId) {
		QueryDefinition<MBCategory> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_ANY);

		return mbCategoryFinder.countByS_G_U_P(
			groupId, userId, null, queryDefinition);
	}

	@Override
	public void moveCategoriesToTrash(long groupId, long userId)
		throws PortalException {

		List<MBCategory> categories = mbCategoryPersistence.findByGroupId(
			groupId);

		for (MBCategory category : categories) {
			moveCategoryToTrash(userId, category.getCategoryId());
		}
	}

	@Override
	public MBCategory moveCategory(
			long categoryId, long parentCategoryId,
			boolean mergeWithParentCategory)
		throws PortalException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		parentCategoryId = getParentCategoryId(category, parentCategoryId);

		if (mergeWithParentCategory && (categoryId != parentCategoryId) &&
			(parentCategoryId !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentCategoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			mergeCategories(category, parentCategoryId);

			return category;
		}

		category.setParentCategoryId(parentCategoryId);

		return mbCategoryPersistence.update(category);
	}

	@Override
	public MBCategory moveCategoryFromTrash(
			long userId, long categoryId, long newCategoryId)
		throws PortalException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		if (category.isInTrashExplicitly()) {
			restoreCategoryFromTrash(userId, categoryId);
		}
		else {

			// Category

			TrashVersion trashVersion = trashVersionLocalService.fetchVersion(
				MBCategory.class.getName(), category.getCategoryId());

			int status = WorkflowConstants.STATUS_APPROVED;

			if (trashVersion != null) {
				status = trashVersion.getStatus();
			}

			updateStatus(userId, categoryId, status);

			// Trash

			if (trashVersion != null) {
				trashVersionLocalService.deleteTrashVersion(trashVersion);
			}

			// Categories and threads

			User user = userPersistence.findByPrimaryKey(userId);

			List<Object> categoriesAndThreads = getCategoriesAndThreads(
				category.getGroupId(), categoryId);

			restoreDependentsFromTrash(user, categoriesAndThreads);
		}

		return moveCategory(categoryId, newCategoryId, false);
	}

	@Override
	public MBCategory moveCategoryToTrash(long userId, long categoryId)
		throws PortalException {

		// Category

		MBCategory category = updateStatus(
			userId, categoryId, WorkflowConstants.STATUS_IN_TRASH);

		// Trash

		TrashEntry trashEntry = trashEntryLocalService.addTrashEntry(
			userId, category.getGroupId(), MBCategory.class.getName(),
			categoryId, category.getUuid(), null,
			WorkflowConstants.STATUS_APPROVED, null, null);

		// Categories and threads

		User user = userPersistence.findByPrimaryKey(userId);

		List<Object> categoriesAndThreads = getCategoriesAndThreads(
			category.getGroupId(), categoryId);

		moveDependentsToTrash(
			user, categoriesAndThreads, trashEntry.getEntryId());

		return category;
	}

	@Override
	public void restoreCategoryFromTrash(long userId, long categoryId)
		throws PortalException {

		// Category

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			MBCategory.class.getName(), categoryId);

		MBCategory category = updateStatus(
			userId, categoryId, WorkflowConstants.STATUS_APPROVED);

		// Categories and threads

		User user = userPersistence.findByPrimaryKey(userId);

		List<Object> categoriesAndThreads = getCategoriesAndThreads(
			category.getGroupId(), categoryId);

		restoreDependentsFromTrash(user, categoriesAndThreads);

		// Trash

		trashEntryLocalService.deleteEntry(trashEntry.getEntryId());
	}

	@Override
	public void subscribeCategory(long userId, long groupId, long categoryId)
		throws PortalException {

		if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			categoryId = groupId;
		}

		subscriptionLocalService.addSubscription(
			userId, groupId, MBCategory.class.getName(), categoryId);
	}

	@Override
	public void unsubscribeCategory(long userId, long groupId, long categoryId)
		throws PortalException {

		if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			categoryId = groupId;
		}

		subscriptionLocalService.deleteSubscription(
			userId, MBCategory.class.getName(), categoryId);
	}

	@Override
	public MBCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, String displayStyle, String emailAddress,
			String inProtocol, String inServerName, int inServerPort,
			boolean inUseSSL, String inUserName, String inPassword,
			int inReadInterval, String outEmailAddress, boolean outCustom,
			String outServerName, int outServerPort, boolean outUseSSL,
			String outUserName, String outPassword, boolean allowAnonymous,
			boolean mailingListActive, boolean mergeWithParentCategory,
			ServiceContext serviceContext)
		throws PortalException {

		// Merge categories

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return null;
		}

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		parentCategoryId = getParentCategoryId(category, parentCategoryId);

		if (mergeWithParentCategory && (categoryId != parentCategoryId) &&
			(parentCategoryId !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentCategoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			mergeCategories(category, parentCategoryId);

			return category;
		}

		// Category

		validate(name);

		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		if (!displayStyle.equals(category.getDisplayStyle())) {
			category.setDisplayStyle(displayStyle);

			updateChildCategoriesDisplayStyle(category, displayStyle);
		}

		category.setExpandoBridgeAttributes(serviceContext);

		mbCategoryPersistence.update(category);

		// Mailing list

		MBMailingList mailingList = mbMailingListPersistence.fetchByG_C(
			category.getGroupId(), category.getCategoryId());

		if (mailingList != null) {
			mbMailingListLocalService.updateMailingList(
				mailingList.getMailingListId(), emailAddress, inProtocol,
				inServerName, inServerPort, inUseSSL, inUserName, inPassword,
				inReadInterval, outEmailAddress, outCustom, outServerName,
				outServerPort, outUseSSL, outUserName, outPassword,
				allowAnonymous, mailingListActive, serviceContext);
		}
		else {
			mbMailingListLocalService.addMailingList(
				category.getUserId(), category.getGroupId(),
				category.getCategoryId(), emailAddress, inProtocol,
				inServerName, inServerPort, inUseSSL, inUserName, inPassword,
				inReadInterval, outEmailAddress, outCustom, outServerName,
				outServerPort, outUseSSL, outUserName, outPassword,
				allowAnonymous, mailingListActive, serviceContext);
		}

		return category;
	}

	@Override
	public MBCategory updateMessageCount(long categoryId) {
		MBCategory mbCategory = mbCategoryPersistence.fetchByPrimaryKey(
			categoryId);

		if (mbCategory == null) {
			return null;
		}

		int messageCount = mbMessageLocalService.getCategoryMessagesCount(
			mbCategory.getGroupId(), mbCategory.getCategoryId(),
			WorkflowConstants.STATUS_APPROVED);

		mbCategory.setMessageCount(messageCount);

		return mbCategoryPersistence.update(mbCategory);
	}

	@Override
	public MBCategory updateStatistics(long categoryId) {
		MBCategory mbCategory = mbCategoryPersistence.fetchByPrimaryKey(
			categoryId);

		if (mbCategory == null) {
			return null;
		}

		int messageCount = mbMessageLocalService.getCategoryMessagesCount(
			mbCategory.getGroupId(), mbCategory.getCategoryId(),
			WorkflowConstants.STATUS_APPROVED);

		mbCategory.setMessageCount(messageCount);

		int threadCount = mbThreadLocalService.getCategoryThreadsCount(
			mbCategory.getGroupId(), mbCategory.getCategoryId(),
			WorkflowConstants.STATUS_APPROVED);

		mbCategory.setThreadCount(threadCount);

		return mbCategoryPersistence.update(mbCategory);
	}

	@Override
	public MBCategory updateStatus(long userId, long categoryId, int status)
		throws PortalException {

		// Category

		User user = userPersistence.findByPrimaryKey(userId);

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		category.setStatus(status);
		category.setStatusByUserId(user.getUserId());
		category.setStatusByUserName(user.getFullName());
		category.setStatusDate(new Date());

		mbCategoryPersistence.update(category);

		return category;
	}

	@Override
	public MBCategory updateThreadCount(long categoryId) {
		MBCategory mbCategory = mbCategoryPersistence.fetchByPrimaryKey(
			categoryId);

		if (mbCategory == null) {
			return null;
		}

		int threadCount = mbThreadLocalService.getCategoryThreadsCount(
			mbCategory.getGroupId(), mbCategory.getCategoryId(),
			WorkflowConstants.STATUS_APPROVED);

		mbCategory.setThreadCount(threadCount);

		return mbCategoryPersistence.update(mbCategory);
	}

	protected long getParentCategoryId(long groupId, long parentCategoryId) {
		if ((parentCategoryId !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentCategoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			MBCategory parentCategory = mbCategoryPersistence.fetchByPrimaryKey(
				parentCategoryId);

			if ((parentCategory == null) ||
				(groupId != parentCategory.getGroupId())) {

				parentCategoryId =
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;
			}
		}

		return parentCategoryId;
	}

	protected long getParentCategoryId(
		MBCategory category, long parentCategoryId) {

		if ((parentCategoryId ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(parentCategoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return parentCategoryId;
		}

		if (category.getCategoryId() == parentCategoryId) {
			return category.getParentCategoryId();
		}

		MBCategory parentCategory = mbCategoryPersistence.fetchByPrimaryKey(
			parentCategoryId);

		if ((parentCategory == null) ||
			(category.getGroupId() != parentCategory.getGroupId())) {

			return category.getParentCategoryId();
		}

		List<Long> subcategoryIds = new ArrayList<>();

		getSubcategoryIds(
			subcategoryIds, category.getGroupId(), category.getCategoryId());

		if (subcategoryIds.contains(parentCategoryId)) {
			return category.getParentCategoryId();
		}

		return parentCategoryId;
	}

	protected void mergeCategories(MBCategory fromCategory, long toCategoryId)
		throws PortalException {

		if ((toCategoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(toCategoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		List<MBCategory> categories = mbCategoryPersistence.findByG_P(
			fromCategory.getGroupId(), fromCategory.getCategoryId());

		for (MBCategory category : categories) {
			mergeCategories(category, toCategoryId);
		}

		List<MBThread> threads = mbThreadPersistence.findByG_C(
			fromCategory.getGroupId(), fromCategory.getCategoryId());

		for (MBThread thread : threads) {

			// Thread

			thread.setCategoryId(toCategoryId);

			mbThreadPersistence.update(thread);

			List<MBMessage> messages = mbMessagePersistence.findByThreadId(
				thread.getThreadId());

			for (MBMessage message : messages) {

				// Message

				message.setCategoryId(toCategoryId);

				mbMessagePersistence.update(message);

				// Indexer

				Indexer<MBMessage> indexer =
					IndexerRegistryUtil.nullSafeGetIndexer(MBMessage.class);

				indexer.reindex(message);
			}
		}

		MBCategory toCategory = mbCategoryPersistence.findByPrimaryKey(
			toCategoryId);

		toCategory.setThreadCount(
			fromCategory.getThreadCount() + toCategory.getThreadCount());
		toCategory.setMessageCount(
			fromCategory.getMessageCount() + toCategory.getMessageCount());

		mbCategoryPersistence.update(toCategory);

		mbCategoryLocalService.deleteCategory(fromCategory);
	}

	protected void moveDependentsToTrash(
			User user, List<Object> categoriesAndThreads, long trashEntryId)
		throws PortalException {

		for (Object object : categoriesAndThreads) {
			if (object instanceof MBThread) {

				// Thread

				MBThread thread = (MBThread)object;

				int oldStatus = thread.getStatus();

				if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
					continue;
				}

				thread.setStatus(WorkflowConstants.STATUS_IN_TRASH);

				mbThreadPersistence.update(thread);

				// Trash

				if (oldStatus != WorkflowConstants.STATUS_APPROVED) {
					trashVersionLocalService.addTrashVersion(
						trashEntryId, MBThread.class.getName(),
						thread.getThreadId(), oldStatus, null);
				}

				// Threads

				mbThreadLocalService.moveDependentsToTrash(
					thread.getGroupId(), thread.getThreadId(), trashEntryId);

				// Indexer

				Indexer<MBThread> indexer =
					IndexerRegistryUtil.nullSafeGetIndexer(MBThread.class);

				indexer.reindex(thread);
			}
			else if (object instanceof MBCategory) {

				// Category

				MBCategory category = (MBCategory)object;

				if (category.isInTrash()) {
					continue;
				}

				int oldStatus = category.getStatus();

				category.setStatus(WorkflowConstants.STATUS_IN_TRASH);

				mbCategoryPersistence.update(category);

				// Trash

				if (oldStatus != WorkflowConstants.STATUS_APPROVED) {
					trashVersionLocalService.addTrashVersion(
						trashEntryId, MBCategory.class.getName(),
						category.getCategoryId(), oldStatus, null);
				}

				// Categories and threads

				moveDependentsToTrash(
					user,
					getCategoriesAndThreads(
						category.getGroupId(), category.getCategoryId()),
					trashEntryId);
			}
		}
	}

	protected void restoreDependentsFromTrash(
			User user, List<Object> categoriesAndThreads)
		throws PortalException {

		for (Object object : categoriesAndThreads) {
			if (object instanceof MBThread) {

				// Thread

				MBThread thread = (MBThread)object;

				if (!thread.isInTrashImplicitly()) {
					continue;
				}

				TrashVersion trashVersion =
					trashVersionLocalService.fetchVersion(
						MBThread.class.getName(), thread.getThreadId());

				int oldStatus = WorkflowConstants.STATUS_APPROVED;

				if (trashVersion != null) {
					oldStatus = trashVersion.getStatus();
				}

				thread.setStatus(oldStatus);

				mbThreadPersistence.update(thread);

				// Threads

				mbThreadLocalService.restoreDependentsFromTrash(
					thread.getGroupId(), thread.getThreadId());

				// Trash

				if (trashVersion != null) {
					trashVersionLocalService.deleteTrashVersion(trashVersion);
				}

				// Indexer

				Indexer<MBThread> indexer =
					IndexerRegistryUtil.nullSafeGetIndexer(MBThread.class);

				indexer.reindex(thread);
			}
			else if (object instanceof MBCategory) {

				// Category

				MBCategory category = (MBCategory)object;

				if (!category.isInTrashImplicitly()) {
					continue;
				}

				TrashVersion trashVersion =
					trashVersionLocalService.fetchVersion(
						MBCategory.class.getName(), category.getCategoryId());

				int oldStatus = WorkflowConstants.STATUS_APPROVED;

				if (trashVersion != null) {
					oldStatus = trashVersion.getStatus();
				}

				category.setStatus(oldStatus);

				mbCategoryPersistence.update(category);

				// Categories and threads

				restoreDependentsFromTrash(
					user,
					getCategoriesAndThreads(
						category.getGroupId(), category.getCategoryId()));

				// Trash

				if (trashVersion != null) {
					trashVersionLocalService.deleteTrashVersion(trashVersion);
				}
			}
		}
	}

	protected void updateChildCategoriesDisplayStyle(
			MBCategory category, String displayStyle)
		throws PortalException {

		List<MBCategory> categories = getCategories(
			category.getGroupId(), category.getCategoryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (MBCategory curCategory : categories) {
			updateChildCategoriesDisplayStyle(curCategory, displayStyle);

			curCategory.setDisplayStyle(displayStyle);

			mbCategoryPersistence.update(curCategory);
		}
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new CategoryNameException("Name is null");
		}
	}

}
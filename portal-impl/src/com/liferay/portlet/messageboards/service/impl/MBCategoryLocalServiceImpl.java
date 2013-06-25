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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.CategoryNameException;
import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.service.base.MBCategoryLocalServiceBaseImpl;
import com.liferay.portlet.trash.model.TrashEntry;

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
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		// Category

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		parentCategoryId = getParentCategoryId(groupId, parentCategoryId);
		Date now = new Date();

		validate(name);

		long categoryId = counterLocalService.increment();

		MBCategory category = mbCategoryPersistence.create(categoryId);

		category.setUuid(serviceContext.getUuid());
		category.setGroupId(groupId);
		category.setCompanyId(user.getCompanyId());
		category.setUserId(user.getUserId());
		category.setUserName(user.getFullName());
		category.setCreateDate(serviceContext.getCreateDate(now));
		category.setModifiedDate(serviceContext.getModifiedDate(now));
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
				category, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
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
		throws PortalException, SystemException {

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
			long categoryId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		addCategoryResources(category, groupPermissions, guestPermissions);
	}

	@Override
	public void addCategoryResources(
			MBCategory category, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), MBCategory.class.getName(),
			category.getCategoryId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
			MBCategory category, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), MBCategory.class.getName(),
			category.getCategoryId(), groupPermissions, guestPermissions);
	}

	@Override
	public void deleteCategories(long groupId)
		throws PortalException, SystemException {

		List<MBCategory> categories = mbCategoryPersistence.findByG_P(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		for (MBCategory category : categories) {
			deleteCategory(category);
		}
	}

	@Override
	public void deleteCategory(long categoryId)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		deleteCategory(category);
	}

	@Override
	public void deleteCategory(MBCategory category)
		throws PortalException, SystemException {

		deleteCategory(category, true);
	}

	@Override
	public void deleteCategory(
			MBCategory category, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		// Categories

		List<MBCategory> categories = mbCategoryPersistence.findByG_P(
			category.getGroupId(), category.getCategoryId());

		for (MBCategory curCategory : categories) {
			if (includeTrashedEntries || !curCategory.isInTrash()) {
				deleteCategory(curCategory, includeTrashedEntries);
			}
		}

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			MBMessage.class);

		indexer.delete(category);

		// Threads

		mbThreadLocalService.deleteThreads(
			category.getGroupId(), category.getCategoryId(),
			includeTrashedEntries);

		// Mailing list

		try {
			mbMailingListLocalService.deleteCategoryMailingList(
				category.getGroupId(), category.getCategoryId());
		}
		catch (NoSuchMailingListException nsmle) {
		}

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			category.getCompanyId(), MBCategory.class.getName(),
			category.getCategoryId());

		// Expando

		expandoValueLocalService.deleteValues(
			MBCategory.class.getName(), category.getCategoryId());

		// Resources

		resourceLocalService.deleteResource(
			category.getCompanyId(), MBCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, category.getCategoryId());

		// System event

		systemEventLocalService.addSystemEvent(
			0, category.getGroupId(), MBCategory.class.getName(),
			category.getCategoryId(), category.getUuid(), null,
			SystemEventConstants.TYPE_DELETE, null);

		// Trash

		trashEntryLocalService.deleteEntry(
			MBCategory.class.getName(), category.getCategoryId());

		// Category

		mbCategoryPersistence.remove(category);
	}

	@Override
	public List<MBCategory> getCategories(long groupId) throws SystemException {
		return mbCategoryPersistence.findByGroupId(groupId);
	}

	@Override
	public List<MBCategory> getCategories(long groupId, int status)
		throws SystemException {

		return mbCategoryPersistence.findByG_S(groupId, status);
	}

	@Override
	public List<MBCategory> getCategories(
			long groupId, long parentCategoryId, int start, int end)
		throws SystemException {

		return mbCategoryPersistence.findByG_P(
			groupId, parentCategoryId, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
			long groupId, long parentCategoryId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.findByG_P(
				groupId, parentCategoryId, start, end);
		}

		return mbCategoryPersistence.findByG_P_S(
			groupId, parentCategoryId, status, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
			long groupId, long[] parentCategoryIds, int start, int end)
		throws SystemException {

		return mbCategoryPersistence.findByG_P(
			groupId, parentCategoryIds, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
			long groupId, long[] parentCategoryIds, int status, int start,
			int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.findByG_P(
				groupId, parentCategoryIds, start, end);
		}

		return mbCategoryPersistence.findByG_P_S(
			groupId, parentCategoryIds, status, start, end);
	}

	@Override
	public List<Object> getCategoriesAndThreads(long groupId, long categoryId)
		throws SystemException {

		List<Object> categoriesAndThreads = new ArrayList<Object>();

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
	public int getCategoriesCount(long groupId) throws SystemException {
		return mbCategoryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getCategoriesCount(long groupId, int status)
		throws SystemException {

		return mbCategoryPersistence.countByG_S(groupId, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId)
		throws SystemException {

		return mbCategoryPersistence.countByG_P(groupId, parentCategoryId);
	}

	@Override
	public int getCategoriesCount(
			long groupId, long parentCategoryId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.countByG_P(groupId, parentCategoryId);
		}

		return mbCategoryPersistence.countByG_P_S(
			groupId, parentCategoryId, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long[] parentCategoryIds)
		throws SystemException {

		return mbCategoryPersistence.countByG_P(groupId, parentCategoryIds);
	}

	@Override
	public int getCategoriesCount(
			long groupId, long[] parentCategoryIds, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.countByG_P(groupId, parentCategoryIds);
		}

		return mbCategoryPersistence.countByG_P_S(
			groupId, parentCategoryIds, status);
	}

	@Override
	public MBCategory getCategory(long categoryId)
		throws PortalException, SystemException {

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
			long companyId, int start, int end)
		throws SystemException {

		return mbCategoryPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public int getCompanyCategoriesCount(long companyId)
		throws SystemException {

		return mbCategoryPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<Long> getSubcategoryIds(
			List<Long> categoryIds, long groupId, long categoryId)
		throws SystemException {

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
			long groupId, long userId, int start, int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY, start, end, null);

		return mbCategoryFinder.findByS_G_U_P(
			groupId, userId, null, queryDefinition);
	}

	@Override
	public int getSubscribedCategoriesCount(long groupId, long userId)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY);

		return mbCategoryFinder.countByS_G_U_P(
			groupId, userId, null, queryDefinition);
	}

	@Override
	public void moveCategoriesToTrash(long groupId, long userId)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		parentCategoryId = getParentCategoryId(category, parentCategoryId);

		if (mergeWithParentCategory &&
			(categoryId != parentCategoryId) &&
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
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		if (category.isInTrash()) {
			restoreCategoryFromTrash(userId, categoryId);
		}
		else {
			updateStatus(userId, categoryId, category.getStatus());
		}

		return moveCategory(categoryId, newCategoryId, false);
	}

	@Override
	public MBCategory moveCategoryToTrash(long userId, long categoryId)
		throws PortalException, SystemException {

		return updateStatus(
			userId, categoryId, WorkflowConstants.STATUS_IN_TRASH);
	}

	@Override
	public void restoreCategoryFromTrash(long userId, long categoryId)
		throws PortalException, SystemException {

		// Category

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			MBCategory.class.getName(), categoryId);

		updateStatus(userId, categoryId, WorkflowConstants.STATUS_APPROVED);

		// Trash

		trashEntryLocalService.deleteEntry(trashEntry.getEntryId());
	}

	@Override
	public void subscribeCategory(long userId, long groupId, long categoryId)
		throws PortalException, SystemException {

		if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			categoryId = groupId;
		}

		subscriptionLocalService.addSubscription(
			userId, groupId, MBCategory.class.getName(), categoryId);
	}

	@Override
	public void unsubscribeCategory(long userId, long groupId, long categoryId)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		// Merge categories

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return null;
		}

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		parentCategoryId = getParentCategoryId(category, parentCategoryId);

		if (mergeWithParentCategory &&
			(categoryId != parentCategoryId) &&
			(parentCategoryId !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentCategoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			mergeCategories(category, parentCategoryId);

			return category;
		}

		// Category

		validate(name);

		category.setModifiedDate(serviceContext.getModifiedDate(null));
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
	public void updateDependentStatus(
			User user, List<Object> categoriesAndThreads, int status)
		throws PortalException, SystemException {

		for (Object object : categoriesAndThreads) {
			if (object instanceof MBThread) {
				MBThread thread = (MBThread)object;

				if ((status == WorkflowConstants.STATUS_APPROVED) &&
					(thread.getStatus() == WorkflowConstants.STATUS_IN_TRASH)) {

					continue;
				}

				mbThreadLocalService.updateStatus(
					user.getUserId(), thread.getThreadId(), status, status);
			}
			else if (object instanceof MBCategory) {
				MBCategory category = (MBCategory)object;

				if (category.isInTrash()) {
					continue;
				}

				updateDependentStatus(
					user,
					getCategoriesAndThreads(
						category.getGroupId(), category.getCategoryId()),
					status);
			}
		}
	}

	@Override
	public MBCategory updateStatus(long userId, long categoryId, int status)
		throws PortalException, SystemException {

		// Category

		User user = userPersistence.findByPrimaryKey(userId);

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		category.setStatus(status);
		category.setStatusByUserId(user.getUserId());
		category.setStatusByUserName(user.getFullName());
		category.setStatusDate(new Date());

		mbCategoryPersistence.update(category);

		// Categories and threads

		List<Object> categoriesAndThreads = getCategoriesAndThreads(
			category.getGroupId(), categoryId);

		updateDependentStatus(user, categoriesAndThreads, status);

		// Trash

		if (status == WorkflowConstants.STATUS_IN_TRASH) {
			trashEntryLocalService.addTrashEntry(
				userId, category.getGroupId(), MBCategory.class.getName(),
				categoryId, WorkflowConstants.STATUS_APPROVED, null, null);
		}

		return category;
	}

	protected long getParentCategoryId(long groupId, long parentCategoryId)
		throws SystemException {

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
			MBCategory category, long parentCategoryId)
		throws SystemException {

		if ((parentCategoryId ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(parentCategoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return parentCategoryId;
		}

		if (category.getCategoryId() == parentCategoryId) {
			return category.getParentCategoryId();
		}
		else {
			MBCategory parentCategory = mbCategoryPersistence.fetchByPrimaryKey(
				parentCategoryId);

			if ((parentCategory == null) ||
				(category.getGroupId() != parentCategory.getGroupId())) {

				return category.getParentCategoryId();
			}

			List<Long> subcategoryIds = new ArrayList<Long>();

			getSubcategoryIds(
				subcategoryIds, category.getGroupId(),
				category.getCategoryId());

			if (subcategoryIds.contains(parentCategoryId)) {
				return category.getParentCategoryId();
			}

			return parentCategoryId;
		}
	}

	protected void mergeCategories(MBCategory fromCategory, long toCategoryId)
		throws PortalException, SystemException {

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

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					MBMessage.class);

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

		deleteCategory(fromCategory);
	}

	protected void updateChildCategoriesDisplayStyle(
			MBCategory category, String displayStyle)
		throws PortalException, SystemException {

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
			throw new CategoryNameException();
		}
	}

}
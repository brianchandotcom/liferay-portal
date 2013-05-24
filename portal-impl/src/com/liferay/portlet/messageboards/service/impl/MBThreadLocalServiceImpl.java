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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.SplitThreadException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadConstants;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.messageboards.service.base.MBThreadLocalServiceBaseImpl;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.model.TrashEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class MBThreadLocalServiceImpl extends MBThreadLocalServiceBaseImpl {

	@Override
	public MBThread addThread(
			long categoryId, MBMessage message, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Thread

		Date now = new Date();

		long threadId = message.getThreadId();

		if (!message.isRoot() || (threadId <= 0)) {
			threadId = counterLocalService.increment();
		}

		MBThread thread = mbThreadPersistence.create(threadId);

		thread.setUuid(serviceContext.getUuid());
		thread.setGroupId(message.getGroupId());
		thread.setCompanyId(message.getCompanyId());
		thread.setUserId(message.getUserId());
		thread.setUserName(message.getUserName());
		thread.setCreateDate(serviceContext.getCreateDate(now));
		thread.setModifiedDate(serviceContext.getModifiedDate(now));
		thread.setCategoryId(categoryId);
		thread.setRootMessageId(message.getMessageId());
		thread.setRootMessageUserId(message.getUserId());

		if (message.isAnonymous()) {
			thread.setLastPostByUserId(0);
		}
		else {
			thread.setLastPostByUserId(message.getUserId());
		}

		thread.setLastPostDate(message.getCreateDate());

		if (message.getPriority() != MBThreadConstants.PRIORITY_NOT_GIVEN) {
			thread.setPriority(message.getPriority());
		}

		thread.setStatus(message.getStatus());
		thread.setStatusByUserId(message.getStatusByUserId());
		thread.setStatusByUserName(message.getStatusByUserName());
		thread.setStatusDate(message.getStatusDate());

		mbThreadPersistence.update(thread);

		// Asset

		if (categoryId >= 0) {
			assetEntryLocalService.updateEntry(
				message.getUserId(), message.getGroupId(),
				thread.getStatusDate(), thread.getLastPostDate(),
				MBThread.class.getName(), thread.getThreadId(),
				thread.getUuid(), 0, new long[0], new String[0], false, null,
				null, null, null, String.valueOf(thread.getRootMessageId()),
				null, null, null, null, 0, 0, null, false);
		}

		return thread;
	}

	@Override
	public void deleteThread(long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

		deleteThread(thread);
	}

	@Override
	public void deleteThread(MBThread thread)
		throws PortalException, SystemException {

		MBMessage rootMessage = mbMessagePersistence.findByPrimaryKey(
			thread.getRootMessageId());

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			MBMessage.class);

		indexer.delete(thread);

		// Attachments

		long folderId = thread.getAttachmentsFolderId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			PortletFileRepositoryUtil.deleteFolder(folderId);
		}

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			thread.getCompanyId(), MBThread.class.getName(),
			thread.getThreadId());

		// Thread flags

		mbThreadFlagPersistence.removeByThreadId(thread.getThreadId());

		// Messages

		List<MBMessage> messages = mbMessagePersistence.findByThreadId(
			thread.getThreadId());

		for (MBMessage message : messages) {

			// Ratings

			ratingsStatsLocalService.deleteStats(
				message.getWorkflowClassName(), message.getMessageId());

			// Asset

			assetEntryLocalService.deleteEntry(
				message.getWorkflowClassName(), message.getMessageId());

			// Resources

			if (!message.isDiscussion()) {
				resourceLocalService.deleteResource(
					message.getCompanyId(), message.getWorkflowClassName(),
					ResourceConstants.SCOPE_INDIVIDUAL, message.getMessageId());
			}

			// Message

			mbMessagePersistence.remove(message);

			// Statistics

			if (!message.isDiscussion()) {
				mbStatsUserLocalService.updateStatsUser(
					message.getGroupId(), message.getUserId());
			}

			// Workflow

			workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				message.getCompanyId(), message.getGroupId(),
				message.getWorkflowClassName(), message.getMessageId());
		}

		// Category

		if ((rootMessage.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(rootMessage.getCategoryId() !=
				MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			try {
				MBCategory category = mbCategoryPersistence.findByPrimaryKey(
					thread.getCategoryId());

				MBUtil.updateCategoryStatistics(
					category.getCompanyId(), category.getCategoryId());
			}
			catch (NoSuchCategoryException nsce) {
				if (!thread.isInTrash()) {
					throw nsce;
				}
			}
		}

		// Thread Asset

		assetEntryLocalService.deleteEntry(
			MBThread.class.getName(), thread.getThreadId());

		// Trash

		trashEntryLocalService.deleteEntry(
			MBThread.class.getName(), thread.getThreadId());

		// Thread

		mbThreadPersistence.remove(thread);
	}

	@Override
	public void deleteThreads(long groupId, long categoryId)
		throws PortalException, SystemException {

		deleteThreads(groupId, categoryId, true);
	}

	@Override
	public void deleteThreads(
			long groupId, long categoryId, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		List<MBThread> threads = mbThreadPersistence.findByG_C(
			groupId, categoryId);

		for (MBThread thread : threads) {
			if (includeTrashedEntries || !thread.isInTrash()) {
				deleteThread(thread);
			}
		}

		if (mbThreadPersistence.countByGroupId(groupId) == 0) {
			PortletFileRepositoryUtil.deletePortletRepository(
				groupId, PortletKeys.MESSAGE_BOARDS);
		}
	}

	@Override
	public MBThread fetchThread(long threadId) throws SystemException {
		return mbThreadPersistence.fetchByPrimaryKey(threadId);
	}

	@Override
	public int getCategoryThreadsCount(
			long groupId, long categoryId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbThreadPersistence.countByG_C(groupId, categoryId);
		}
		else {
			return mbThreadPersistence.countByG_C_S(
				groupId, categoryId, status);
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupThreads(long,
	 *             QueryDefinition)}
	 */
	@Override
	public List<MBThread> getGroupThreads(
			long groupId, int status, int start, int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return getGroupThreads(groupId, queryDefinition);
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, boolean subscribed,
			boolean includeAnonymous, QueryDefinition queryDefinition)
		throws SystemException {

		if (userId <= 0) {
			return getGroupThreads(groupId, queryDefinition);
		}
		else {
			if (subscribed) {
				return mbThreadFinder.findByS_G_U_C(
					groupId, userId, null, queryDefinition);
			}
			else {
				if (includeAnonymous) {
					return mbThreadFinder.findByG_U_C(
						groupId, userId, null, queryDefinition);
				}
				else {
					return mbThreadFinder.findByG_U_C_A(
						groupId, userId, null, false, queryDefinition);
				}
			}
		}
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, boolean subscribed,
			QueryDefinition queryDefinition)
		throws SystemException {

		return getGroupThreads(
			groupId, userId, subscribed, true, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupThreads(long, long,
	 *             boolean, boolean, QueryDefinition)}
	 */
	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			boolean includeAnonymous, int start, int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return getGroupThreads(
			groupId, userId, subscribed, includeAnonymous, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupThreads(long, long,
	 *             boolean, QueryDefinition)}
	 */
	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			int start, int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return getGroupThreads(groupId, userId, subscribed, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupThreads(long, long,
	 *             QueryDefinition)}
	 */
	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, int start, int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return getGroupThreads(groupId, userId, false, queryDefinition);
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, QueryDefinition queryDefinition)
		throws SystemException {

		return getGroupThreads(groupId, userId, false, queryDefinition);
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, QueryDefinition queryDefinition)
		throws SystemException {

		if (queryDefinition.isExcludeStatus()) {
			return mbThreadPersistence.findByG_NotC_NotS(
				groupId, MBCategoryConstants.DISCUSSION_CATEGORY_ID,
				queryDefinition.getStatus(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		else {
			return mbThreadPersistence.findByG_NotC_S(
				groupId, MBCategoryConstants.DISCUSSION_CATEGORY_ID,
				queryDefinition.getStatus(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupThreadsCount(long,
	 *             QueryDefinition)}
	 */
	@Override
	public int getGroupThreadsCount(long groupId, int status)
			throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return getGroupThreadsCount(groupId, queryDefinition);
	}

	@Override
	public int getGroupThreadsCount(
			long groupId, long userId, boolean subscribed,
			boolean includeAnonymous, QueryDefinition queryDefinition)
		throws SystemException {

		if (userId <= 0) {
			return getGroupThreadsCount(groupId, queryDefinition);
		}
		else {
			if (subscribed) {
				return mbThreadFinder.countByS_G_U_C(
					groupId, userId, null, queryDefinition);
			}
			else {
				if (includeAnonymous) {
					return mbThreadFinder.countByG_U_C(
						groupId, userId, null, queryDefinition);
				}
				else {
					return mbThreadFinder.countByG_U_C_A(
						groupId, userId, null, false, queryDefinition);
				}
			}
		}
	}

	@Override
	public int getGroupThreadsCount(
			long groupId, long userId, boolean subscribed,
			QueryDefinition queryDefinition)
		throws SystemException {

		return getGroupThreadsCount(
			groupId, userId, subscribed, true, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupThreadsCount(long,
	 *             long, QueryDefinition)}
	 */
	@Override
	public int getGroupThreadsCount(long groupId, long userId, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return getGroupThreadsCount(groupId, userId, false, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupThreadsCount(long,
	 *             long, boolean, QueryDefinition)}
	 */
	@Override
	public int getGroupThreadsCount(
			long groupId, long userId, int status, boolean subscribed)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return getGroupThreadsCount(
			groupId, userId, subscribed, true, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupThreadsCount(long,
	 *             long, boolean, boolean, QueryDefinition)}
	 */
	@Override
	public int getGroupThreadsCount(
			long groupId, long userId, int status, boolean subscribed,
			boolean includeAnonymous)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return getGroupThreadsCount(
			groupId, userId, subscribed, includeAnonymous, queryDefinition);
	}

	@Override
	public int getGroupThreadsCount(
			long groupId, long userId, QueryDefinition queryDefinition)
		throws SystemException {

		return getGroupThreadsCount(groupId, userId, false, queryDefinition);
	}

	@Override
	public int getGroupThreadsCount(
			long groupId, QueryDefinition queryDefinition)
		throws SystemException {

		if (queryDefinition.isExcludeStatus()) {
			return mbThreadPersistence.countByG_NotC_NotS(
				groupId, MBCategoryConstants.DISCUSSION_CATEGORY_ID,
				queryDefinition.getStatus());
		}
		else {
			return mbThreadPersistence.countByG_NotC_S(
				groupId, MBCategoryConstants.DISCUSSION_CATEGORY_ID,
				queryDefinition.getStatus());
		}
	}

	@Override
	public List<MBThread> getNoAssetThreads() throws SystemException {
		return mbThreadFinder.findByNoAssets();
	}

	@Override
	public List<MBThread> getPriorityThreads(long categoryId, double priority)
		throws PortalException, SystemException {

		return getPriorityThreads(categoryId, priority, false);
	}

	@Override
	public List<MBThread> getPriorityThreads(
			long categoryId, double priority, boolean inherit)
		throws PortalException, SystemException {

		if (!inherit) {
			return mbThreadPersistence.findByC_P(categoryId, priority);
		}

		List<MBThread> threads = new ArrayList<MBThread>();

		while ((categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			   (categoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			threads.addAll(
				0, mbThreadPersistence.findByC_P(categoryId, priority));

			MBCategory category = mbCategoryPersistence.findByPrimaryKey(
				categoryId);

			categoryId = category.getParentCategoryId();
		}

		return threads;
	}

	@Override
	public MBThread getThread(long threadId)
		throws PortalException, SystemException {

		return mbThreadPersistence.findByPrimaryKey(threadId);
	}

	@Override
	public List<MBThread> getThreads(
			long groupId, long categoryId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbThreadPersistence.findByG_C(
				groupId, categoryId, start, end);
		}
		else {
			return mbThreadPersistence.findByG_C_S(
				groupId, categoryId, status, start, end);
		}
	}

	@Override
	public int getThreadsCount(long groupId, long categoryId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbThreadPersistence.countByG_C(groupId, categoryId);
		}
		else {
			return mbThreadPersistence.countByG_C_S(
				groupId, categoryId, status);
		}
	}

	@Override
	public boolean hasAnswerMessage(long threadId) throws SystemException {
		int count = mbMessagePersistence.countByT_A(threadId, true);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@BufferedIncrement(
		configuration = "MBThread", incrementClass = NumberIncrement.class)
	@Override
	public MBThread incrementViewCounter(long threadId, int increment)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

		thread.setViewCount(thread.getViewCount() + increment);

		mbThreadPersistence.update(thread);

		return thread;
	}

	@Override
	public MBThread moveThread(long groupId, long categoryId, long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

		long oldCategoryId = thread.getCategoryId();

		MBCategory oldCategory = null;

		if (oldCategoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			oldCategory = mbCategoryPersistence.findByPrimaryKey(oldCategoryId);
		}

		MBCategory category = null;

		if (categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			category = mbCategoryPersistence.findByPrimaryKey(categoryId);
		}

		// Thread

		thread.setModifiedDate(new Date());
		thread.setCategoryId(categoryId);

		mbThreadPersistence.update(thread);

		// Messages

		List<MBMessage> messages = mbMessagePersistence.findByG_C_T(
			groupId, oldCategoryId, thread.getThreadId());

		for (MBMessage message : messages) {
			message.setCategoryId(categoryId);

			mbMessagePersistence.update(message);

			// Indexer

			if (!message.isDiscussion()) {
				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					MBMessage.class);

				indexer.reindex(message);
			}
		}

		// Category

		if ((oldCategory != null) && (categoryId != oldCategoryId)) {
			MBUtil.updateCategoryStatistics(
				oldCategory.getCompanyId(), oldCategory.getCategoryId());
		}

		if ((category != null) && (categoryId != oldCategoryId)) {
			MBUtil.updateCategoryStatistics(
				category.getCompanyId(), category.getCategoryId());
		}

		return thread;
	}

	@Override
	public MBThread moveThreadFromTrash(
			long userId, long categoryId, long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

		if (thread.isInTrash()) {
			restoreThreadFromTrash(userId, threadId);
		}
		else {
			updateStatus(
				userId, threadId, thread.getStatus(),
				WorkflowConstants.STATUS_ANY);
		}

		return moveThread(thread.getGroupId(), categoryId, threadId);
	}

	@Override
	public void moveThreadsToTrash(long groupId, long userId)
		throws PortalException, SystemException {

		List<MBThread> threads = mbThreadPersistence.findByGroupId(groupId);

		for (MBThread thread : threads) {
			moveThreadToTrash(userId, thread);
		}
	}

	@Override
	public MBThread moveThreadToTrash(long userId, long entryId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(entryId);

		return moveThreadToTrash(userId, thread);
	}

	@Override
	public MBThread moveThreadToTrash(long userId, MBThread thread)
		throws PortalException, SystemException {

		if (thread.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID) {

			return thread;
		}

		return updateStatus(
			userId, thread.getThreadId(), WorkflowConstants.STATUS_IN_TRASH,
			WorkflowConstants.STATUS_ANY);
	}

	@Override
	public void restoreThreadFromTrash(long userId, long threadId)
		throws PortalException, SystemException {

		MBThread thread = getThread(threadId);

		if (thread.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID) {

			return;
		}

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			MBThread.class.getName(), threadId);

		updateStatus(
			userId, threadId, trashEntry.getStatus(),
			WorkflowConstants.STATUS_ANY);
	}

	@Override
	public MBThread splitThread(
			long messageId, String subject, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		if (message.isRoot()) {
			throw new SplitThreadException();
		}

		MBCategory category = message.getCategory();
		MBThread oldThread = message.getThread();
		MBMessage rootMessage = mbMessagePersistence.findByPrimaryKey(
			oldThread.getRootMessageId());

		// Message flags

		mbMessageLocalService.updateAnswer(message, false, true);

		// Create new thread

		MBThread thread = addThread(
			message.getCategoryId(), message, serviceContext);

		oldThread.setModifiedDate(serviceContext.getModifiedDate(new Date()));

		mbThreadPersistence.update(oldThread);

		// Update messages

		if (Validator.isNotNull(subject)) {
			MBMessageDisplay messageDisplay =
				mbMessageService.getMessageDisplay(
					messageId, WorkflowConstants.STATUS_ANY,
					MBThreadConstants.THREAD_VIEW_TREE, false);

			MBTreeWalker treeWalker = messageDisplay.getTreeWalker();

			List<MBMessage> messages = treeWalker.getMessages();

			int[] range = treeWalker.getChildrenRange(message);

			for (int i = range[0]; i < range[1]; i++) {
				MBMessage curMessage = messages.get(i);

				String oldSubject = message.getSubject();
				String curSubject = curMessage.getSubject();

				if (oldSubject.startsWith("RE: ")) {
					curSubject = StringUtil.replace(
						curSubject, rootMessage.getSubject(), subject);
				}
				else {
					curSubject = StringUtil.replace(
						curSubject, oldSubject, subject);
				}

				curMessage.setSubject(curSubject);

				mbMessagePersistence.update(curMessage);
			}

			message.setSubject(subject);
		}

		message.setThreadId(thread.getThreadId());
		message.setRootMessageId(thread.getRootMessageId());
		message.setParentMessageId(0);

		mbMessagePersistence.update(message);

		// Indexer

		if (!message.isDiscussion()) {
			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				MBMessage.class);

			indexer.reindex(message);
		}

		// Update children

		moveChildrenMessages(message, category, oldThread.getThreadId());

		// Update new thread

		MBUtil.updateThreadMessageCount(
			thread.getCompanyId(), thread.getThreadId());

		// Update old thread

		MBUtil.updateThreadMessageCount(
			oldThread.getCompanyId(), oldThread.getThreadId());

		// Category

		if ((message.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(message.getCategoryId() !=
				MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			MBUtil.updateCategoryThreadCount(
				category.getCompanyId(), category.getCategoryId());
		}

		return thread;
	}

	@Override
	public void updateQuestion(long threadId, boolean question)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

		if (thread.isQuestion() == question) {
			return;
		}

		thread.setQuestion(question);

		mbThreadPersistence.update(thread);

		if (!question) {
			MBMessage message = mbMessagePersistence.findByPrimaryKey(
				thread.getRootMessageId());

			mbMessageLocalService.updateAnswer(message, false, true);
		}
	}

	@Override
	public MBThread updateStatus(
			long userId, long threadId, int status, int categoryStatus)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

		if (categoryStatus != WorkflowConstants.STATUS_IN_TRASH) {

			// Thread

			User user = userPersistence.findByPrimaryKey(userId);

			Date now = new Date();

			int oldStatus = thread.getStatus();

			if (oldStatus == WorkflowConstants.STATUS_PENDING) {
				MBMessage rootMessage = mbMessageLocalService.getMBMessage(
					thread.getRootMessageId());

				rootMessage.setStatus(WorkflowConstants.STATUS_DRAFT);

				mbMessagePersistence.update(rootMessage);
			}

			thread.setModifiedDate(now);
			thread.setStatus(status);
			thread.setStatusByUserId(user.getUserId());
			thread.setStatusByUserName(user.getFullName());
			thread.setStatusDate(now);

			mbThreadPersistence.update(thread);

			// Messages

			updateDependentStatus(thread.getGroupId(), threadId, status);

			if (thread.getCategoryId() !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				// Category

				MBCategory category = mbCategoryPersistence.findByPrimaryKey(
					thread.getCategoryId());

				MBUtil.updateCategoryStatistics(
					category.getCompanyId(), category.getCategoryId());
			}

			if (status == WorkflowConstants.STATUS_IN_TRASH) {

				// Social

				socialActivityLocalService.addActivity(
					userId, thread.getGroupId(), MBThread.class.getName(),
					thread.getThreadId(),
					SocialActivityConstants.TYPE_MOVE_TO_TRASH,
					StringPool.BLANK, 0);

				// Trash

				trashEntryLocalService.addTrashEntry(
					userId, thread.getGroupId(), MBThread.class.getName(),
					thread.getThreadId(), oldStatus, null, null);
			}
			else {

				// Social

				socialActivityLocalService.addActivity(
					userId, thread.getGroupId(), MBThread.class.getName(),
					thread.getThreadId(),
					SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
					StringPool.BLANK, 0);

				// Trash

				trashEntryLocalService.deleteEntry(
					MBThread.class.getName(), thread.getThreadId());
			}
		}
		else {
			updateDependentStatus(thread.getGroupId(), threadId, status);
		}

		return thread;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #incrementViewCounter(long,
	 *             int)}
	 */
	@Override
	public MBThread updateThread(long threadId, int viewCount)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

		thread.setViewCount(viewCount);

		mbThreadPersistence.update(thread);

		return thread;
	}

	protected void moveChildrenMessages(
			MBMessage parentMessage, MBCategory category, long oldThreadId)
		throws PortalException, SystemException {

		List<MBMessage> messages = mbMessagePersistence.findByT_P(
			oldThreadId, parentMessage.getMessageId());

		for (MBMessage message : messages) {
			message.setCategoryId(parentMessage.getCategoryId());
			message.setThreadId(parentMessage.getThreadId());
			message.setRootMessageId(parentMessage.getRootMessageId());

			mbMessagePersistence.update(message);

			if (!message.isDiscussion()) {
				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					MBMessage.class);

				indexer.reindex(message);
			}

			moveChildrenMessages(message, category, oldThreadId);
		}
	}

	protected void updateDependentStatus(
			long groupId, long threadId, int status)
		throws PortalException, SystemException {

		Set<Long> userIds = new HashSet<Long>();

		List<MBMessage> messages = mbMessageLocalService.getThreadMessages(
			threadId, WorkflowConstants.STATUS_ANY);

		for (MBMessage message : messages) {
			if (message.isDiscussion()) {
				continue;
			}

			userIds.add(message.getUserId());

			if (status == WorkflowConstants.STATUS_IN_TRASH) {

				// Asset

				if (message.getStatus() == WorkflowConstants.STATUS_APPROVED) {
					assetEntryLocalService.updateVisible(
						MBMessage.class.getName(), message.getMessageId(),
						false);
				}

				// Social

				socialActivityCounterLocalService.disableActivityCounters(
					MBMessage.class.getName(), message.getMessageId());

				// Index

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					MBMessage.class);

				indexer.reindex(message);

				// Workflow

				if (message.getStatus() == WorkflowConstants.STATUS_PENDING) {
					message.setStatus(WorkflowConstants.STATUS_DRAFT);

					mbMessagePersistence.update(message);

					workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
						message.getCompanyId(), message.getGroupId(),
						MBMessage.class.getName(), message.getMessageId());
				}
			}
			else {

				// Asset

				if (message.getStatus() == WorkflowConstants.STATUS_APPROVED) {
					assetEntryLocalService.updateVisible(
						MBMessage.class.getName(), message.getMessageId(),
						true);
				}

				// Social

				socialActivityCounterLocalService.enableActivityCounters(
					MBMessage.class.getName(), message.getMessageId());

				// Index

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					MBMessage.class);

				indexer.reindex(message);
			}
		}

		// Statistics

		for (long userId : userIds) {
			mbStatsUserLocalService.updateStatsUser(groupId, userId);
		}
	}

}
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

package com.liferay.message.boards.service.impl;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.exception.LockedThreadException;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.model.impl.MBThreadModelImpl;
import com.liferay.message.boards.service.base.MBThreadServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Jorge Ferrer
 * @author Deepak Gothe
 * @author Mika Koivisto
 * @author Shuyang Zhou
 */
public class MBThreadServiceImpl extends MBThreadServiceBaseImpl {

	@Override
	public void deleteThread(long threadId) throws PortalException {
		if (LockManagerUtil.isLocked(MBThread.class.getName(), threadId)) {
			StringBundler sb = new StringBundler(4);

			sb.append("Thread is locked for class name ");
			sb.append(MBThread.class.getName());
			sb.append(" and class PK ");
			sb.append(threadId);

			throw new LockedThreadException(sb.toString());
		}

		List<MBMessage> messages = mbMessageLocalService.getThreadMessages(
			threadId, WorkflowConstants.STATUS_ANY, null);

		for (MBMessage message : messages) {
			_messageModelResourcePermission.check(
				getPermissionChecker(), message.getMessageId(),
				ActionKeys.DELETE);
		}

		mbThreadLocalService.deleteThread(threadId);
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, Date modifiedDate,
			boolean includeAnonymous, int status, int start, int end)
		throws PortalException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
				status, start, end, null);

			return mbThreadFinder.findByG_U_LPD(
				groupId, userId, modifiedDate, queryDefinition);
		}

		long[] categoryIds = mbCategoryService.getCategoryIds(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		if (categoryIds.length == 0) {
			return Collections.emptyList();
		}

		List<Long> threadIds = null;

		if (includeAnonymous) {
			threadIds = mbMessageFinder.filterFindByG_U_MD_C_S(
				groupId, userId, modifiedDate, categoryIds, status, start, end);
		}
		else {
			threadIds = mbMessageFinder.filterFindByG_U_MD_C_A_S(
				groupId, userId, modifiedDate, categoryIds, false, status,
				start, end);
		}

		List<MBThread> threads = new ArrayList<>(threadIds.size());

		for (long threadId : threadIds) {
			MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

			threads.add(thread);
		}

		return threads;
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, Date modifiedDate, int status, int start,
			int end)
		throws PortalException {

		return mbThreadService.getGroupThreads(
			groupId, userId, modifiedDate, true, status, start, end);
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			boolean includeAnonymous, int start, int end)
		throws PortalException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return doGetGroupThreads(
				groupId, userId, status, subscribed, includeAnonymous, start,
				end);
		}

		long[] categoryIds = mbCategoryService.getCategoryIds(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		if (categoryIds.length == 0) {
			return Collections.emptyList();
		}

		List<Long> threadIds = null;

		if (userId <= 0) {
			threadIds = mbMessageFinder.filterFindByG_U_C_S(
				groupId, 0, categoryIds, status, start, end);
		}
		else {
			if (subscribed) {
				QueryDefinition<MBThread> queryDefinition =
					new QueryDefinition<>(status, start, end, null);

				return mbThreadFinder.filterFindByS_G_U_C(
					groupId, userId, categoryIds, queryDefinition);
			}
			else {
				if (includeAnonymous) {
					threadIds = mbMessageFinder.filterFindByG_U_C_S(
						groupId, userId, categoryIds, status, start, end);
				}
				else {
					threadIds = mbMessageFinder.filterFindByG_U_C_A_S(
						groupId, userId, categoryIds, false, status, start,
						end);
				}
			}
		}

		List<MBThread> threads = new ArrayList<>(threadIds.size());

		for (long threadId : threadIds) {
			MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

			threads.add(thread);
		}

		return threads;
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			int start, int end)
		throws PortalException {

		return getGroupThreads(
			groupId, userId, status, subscribed, true, start, end);
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, int start, int end)
		throws PortalException {

		return getGroupThreads(groupId, userId, status, false, start, end);
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, Date modifiedDate, boolean includeAnonymous,
		int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
				status);

			return mbThreadFinder.countByG_U_LPD(
				groupId, userId, modifiedDate, queryDefinition);
		}

		long[] categoryIds = mbCategoryService.getCategoryIds(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		if (categoryIds.length == 0) {
			return 0;
		}

		if (includeAnonymous) {
			return mbMessageFinder.filterCountByG_U_MD_C_S(
				groupId, userId, modifiedDate, categoryIds, status);
		}

		return mbMessageFinder.filterCountByG_U_MD_C_A_S(
			groupId, userId, modifiedDate, categoryIds, false, status);
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, Date modifiedDate, int status) {

		return mbThreadService.getGroupThreadsCount(
			groupId, userId, modifiedDate, true, status);
	}

	@Override
	public int getGroupThreadsCount(long groupId, long userId, int status) {
		return getGroupThreadsCount(groupId, userId, status, false);
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, int status, boolean subscribed) {

		return getGroupThreadsCount(groupId, userId, status, subscribed, true);
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, int status, boolean subscribed,
		boolean includeAnonymous) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return doGetGroupThreadsCount(
				groupId, userId, status, subscribed, includeAnonymous);
		}

		long[] categoryIds = mbCategoryService.getCategoryIds(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		if (categoryIds.length == 0) {
			return 0;
		}

		if (userId <= 0) {
			return mbMessageFinder.filterCountByG_U_C_S(
				groupId, 0, categoryIds, status);
		}
		else {
			if (subscribed) {
				QueryDefinition<MBThread> queryDefinition =
					new QueryDefinition<>(status);

				return mbThreadFinder.filterCountByS_G_U_C(
					groupId, userId, categoryIds, queryDefinition);
			}
			else {
				if (includeAnonymous) {
					return mbMessageFinder.filterCountByG_U_C_S(
						groupId, userId, categoryIds, status);
				}

				return mbMessageFinder.filterCountByG_U_C_A_S(
					groupId, userId, categoryIds, false, status);
			}
		}
	}

	@Override
	public List<MBThread> getThreads(
		long groupId, long categoryId, int status, int start, int end) {

		QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
			status, start, end, null);

		return mbThreadFinder.filterFindByG_C(
			groupId, categoryId, queryDefinition);
	}

	@Override
	public List<MBThread> getThreads(
			long groupId, long categoryId,
			QueryDefinition<MBThread> queryDefinition)
		throws PortalException {

		if (queryDefinition.isIncludeOwner() &&
			(queryDefinition.getOwnerUserId() != 0)) {

			queryDefinition.setOwnerUserId(getUserId());
		}

		return mbThreadFinder.filterFindByG_C(
			groupId, categoryId, queryDefinition);
	}

	@Override
	public int getThreadsCount(long groupId, long categoryId, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return mbThreadFinder.filterCountByG_C(groupId, categoryId);
		}
		else {
			QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
				status);

			return mbThreadFinder.filterCountByG_C(
				groupId, categoryId, queryDefinition);
		}
	}

	@Override
	public int getThreadsCount(
			long groupId, long categoryId,
			QueryDefinition<MBThread> queryDefinition)
		throws PortalException {

		if (queryDefinition.isIncludeOwner() &&
			(queryDefinition.getOwnerUserId() != 0)) {

			queryDefinition.setOwnerUserId(getUserId());
		}

		return mbThreadFinder.filterCountByG_C(
			groupId, categoryId, queryDefinition);
	}

	@Override
	public Lock lockThread(long threadId) throws PortalException {
		MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

		ModelResourcePermissionHelper.check(
			_categoryModelResourcePermission, getPermissionChecker(),
			thread.getGroupId(), thread.getCategoryId(),
			ActionKeys.LOCK_THREAD);

		return LockManagerUtil.lock(
			getUserId(), MBThread.class.getName(), threadId,
			String.valueOf(threadId), false,
			MBThreadModelImpl.LOCK_EXPIRATION_TIME);
	}

	@Override
	public MBThread moveThread(long categoryId, long threadId)
		throws PortalException {

		if (LockManagerUtil.isLocked(MBThread.class.getName(), threadId)) {
			StringBundler sb = new StringBundler(4);

			sb.append("Thread is locked for class name ");
			sb.append(MBThread.class.getName());
			sb.append(" and class PK ");
			sb.append(threadId);

			throw new LockedThreadException(sb.toString());
		}

		MBThread thread = mbThreadLocalService.getThread(threadId);

		ModelResourcePermissionHelper.check(
			_categoryModelResourcePermission, getPermissionChecker(),
			thread.getGroupId(), thread.getCategoryId(),
			ActionKeys.MOVE_THREAD);

		ModelResourcePermissionHelper.check(
			_categoryModelResourcePermission, getPermissionChecker(),
			thread.getGroupId(), categoryId, ActionKeys.MOVE_THREAD);

		return mbThreadLocalService.moveThread(
			thread.getGroupId(), categoryId, threadId);
	}

	@Override
	public MBThread moveThreadFromTrash(long categoryId, long threadId)
		throws PortalException {

		MBThread thread = mbThreadLocalService.getThread(threadId);

		ModelResourcePermissionHelper.check(
			_categoryModelResourcePermission, getPermissionChecker(),
			thread.getGroupId(), thread.getCategoryId(), ActionKeys.UPDATE);

		return mbThreadLocalService.moveThreadFromTrash(
			getUserId(), categoryId, threadId);
	}

	@Override
	public MBThread moveThreadToTrash(long threadId) throws PortalException {
		if (LockManagerUtil.isLocked(MBThread.class.getName(), threadId)) {
			StringBundler sb = new StringBundler(4);

			sb.append("Thread is locked for class name ");
			sb.append(MBThread.class.getName());
			sb.append(" and class PK ");
			sb.append(threadId);

			throw new LockedThreadException(sb.toString());
		}

		List<MBMessage> messages = mbMessageLocalService.getThreadMessages(
			threadId, WorkflowConstants.STATUS_ANY, null);

		for (MBMessage message : messages) {
			_messageModelResourcePermission.check(
				getPermissionChecker(), message.getMessageId(),
				ActionKeys.DELETE);
		}

		return mbThreadLocalService.moveThreadToTrash(getUserId(), threadId);
	}

	@Override
	public void restoreThreadFromTrash(long threadId) throws PortalException {
		List<MBMessage> messages = mbMessageLocalService.getThreadMessages(
			threadId, WorkflowConstants.STATUS_ANY, null);

		for (MBMessage message : messages) {
			_messageModelResourcePermission.check(
				getPermissionChecker(), message.getMessageId(),
				ActionKeys.DELETE);
		}

		mbThreadLocalService.restoreThreadFromTrash(getUserId(), threadId);
	}

	@Override
	public Hits search(
			long groupId, long creatorUserId, int status, int start, int end)
		throws PortalException {

		return mbThreadLocalService.search(
			groupId, getUserId(), creatorUserId, status, start, end);
	}

	@Override
	public Hits search(
			long groupId, long creatorUserId, long startDate, long endDate,
			int status, int start, int end)
		throws PortalException {

		return mbThreadLocalService.search(
			groupId, getUserId(), creatorUserId, startDate, endDate, status,
			start, end);
	}

	@Override
	public MBThread splitThread(
			long messageId, String subject, ServiceContext serviceContext)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		MBMessage message = mbMessageLocalService.getMessage(messageId);

		ModelResourcePermissionHelper.check(
			_categoryModelResourcePermission, getPermissionChecker(),
			message.getGroupId(), message.getCategoryId(),
			ActionKeys.MOVE_THREAD);

		_messageModelResourcePermission.check(
			permissionChecker, messageId, ActionKeys.VIEW);

		return mbThreadLocalService.splitThread(
			getUserId(), messageId, subject, serviceContext);
	}

	@Override
	public void unlockThread(long threadId) throws PortalException {
		MBThread thread = mbThreadLocalService.getThread(threadId);

		ModelResourcePermissionHelper.check(
			_categoryModelResourcePermission, getPermissionChecker(),
			thread.getGroupId(), thread.getCategoryId(),
			ActionKeys.LOCK_THREAD);

		LockManagerUtil.unlock(MBThread.class.getName(), threadId);
	}

	protected List<MBThread> doGetGroupThreads(
		long groupId, long userId, int status, boolean subscribed,
		boolean includeAnonymous, int start, int end) {

		if (userId <= 0) {
			if (status == WorkflowConstants.STATUS_ANY) {
				return mbThreadPersistence.findByGroupId(groupId, start, end);
			}

			return mbThreadPersistence.findByG_S(groupId, status, start, end);
		}

		QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
			status, start, end, null);

		if (subscribed) {
			return mbThreadFinder.findByS_G_U(groupId, userId, queryDefinition);
		}
		else if (includeAnonymous) {
			return mbThreadFinder.findByG_U(groupId, userId, queryDefinition);
		}

		return mbThreadFinder.findByG_U_A(
			groupId, userId, false, queryDefinition);
	}

	protected int doGetGroupThreadsCount(
		long groupId, long userId, int status, boolean subscribed,
		boolean includeAnonymous) {

		if (userId <= 0) {
			if (status == WorkflowConstants.STATUS_ANY) {
				return mbThreadPersistence.countByGroupId(groupId);
			}

			return mbThreadPersistence.countByG_S(groupId, status);
		}

		QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
			status);

		if (subscribed) {
			return mbThreadFinder.countByS_G_U(
				groupId, userId, queryDefinition);
		}
		else if (includeAnonymous) {
			return mbThreadFinder.countByG_U(groupId, userId, queryDefinition);
		}

		return mbThreadFinder.countByG_U_A(
			groupId, userId, false, queryDefinition);
	}

	private static volatile ModelResourcePermission<MBCategory>
		_categoryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				MBCategoryServiceImpl.class, "_categoryModelResourcePermission",
				MBCategory.class);
	private static volatile ModelResourcePermission<MBMessage>
		_messageModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				MBMessageServiceImpl.class, "_messageModelResourcePermission",
				MBMessage.class);

}
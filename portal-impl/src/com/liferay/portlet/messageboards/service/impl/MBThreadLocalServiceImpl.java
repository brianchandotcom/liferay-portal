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

import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portlet.messageboards.service.base.MBThreadLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.message.boards.service.impl.MBThreadLocalServiceImpl}
 */
@Deprecated
public class MBThreadLocalServiceImpl extends MBThreadLocalServiceBaseImpl {

	@Override
	public MBThread addThread(
			long categoryId, MBMessage message, ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public void deleteThread(long threadId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public void deleteThread(MBThread thread) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public void deleteThreads(long groupId, long categoryId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public void deleteThreads(
			long groupId, long categoryId, boolean includeTrashedEntries)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public MBThread fetchThread(long threadId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public int getCategoryThreadsCount(
		long groupId, long categoryId, int status) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public List<MBThread> getGroupThreads(
		long groupId, long userId, boolean subscribed, boolean includeAnonymous,
		QueryDefinition<MBThread> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public List<MBThread> getGroupThreads(
		long groupId, long userId, boolean subscribed,
		QueryDefinition<MBThread> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public List<MBThread> getGroupThreads(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public List<MBThread> getGroupThreads(
		long groupId, QueryDefinition<MBThread> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, boolean subscribed, boolean includeAnonymous,
		QueryDefinition<MBThread> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, boolean subscribed,
		QueryDefinition<MBThread> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, QueryDefinition<MBThread> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public List<MBThread> getNoAssetThreads() {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public List<MBThread> getPriorityThreads(long categoryId, double priority)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public List<MBThread> getPriorityThreads(
			long categoryId, double priority, boolean inherit)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public MBThread getThread(long threadId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public List<MBThread> getThreads(
		long groupId, long categoryId, int status, int start, int end) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public int getThreadsCount(long groupId, long categoryId, int status) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public boolean hasAnswerMessage(long threadId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@BufferedIncrement(
		configuration = "MBThread", incrementClass = NumberIncrement.class
	)
	@Override
	public void incrementViewCounter(long threadId, int increment)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public void moveDependentsToTrash(
			long groupId, long threadId, long trashEntryId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public MBThread moveThread(long groupId, long categoryId, long threadId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public MBThread moveThreadFromTrash(
			long userId, long categoryId, long threadId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public void moveThreadsToTrash(long groupId, long userId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public MBThread moveThreadToTrash(long userId, long threadId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public MBThread moveThreadToTrash(long userId, MBThread thread)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public void restoreDependentsFromTrash(long groupId, long threadId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #restoreDependentsFromTrash(long, long)}
	 */
	@Deprecated
	@Override
	public void restoreDependentsFromTrash(
			long groupId, long threadId, long trashEntryId)
		throws PortalException {

		mbThreadLocalService.restoreDependentsFromTrash(groupId, threadId);
	}

	@Override
	public void restoreThreadFromTrash(long userId, long threadId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public Hits search(
			long groupId, long userId, long creatorUserId, int status,
			int start, int end)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public Hits search(
			long groupId, long userId, long creatorUserId, long startDate,
			long endDate, int status, int start, int end)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public MBThread splitThread(
			long userId, long messageId, String subject,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public MBThread updateMessageCount(long threadId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public void updateQuestion(long threadId, boolean question)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	@Override
	public MBThread updateStatus(long userId, long threadId, int status)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	protected void moveAttachmentsFolders(
			MBMessage message, long oldAttachmentsFolderId, MBThread oldThread,
			MBThread newThread, ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

	protected void moveChildrenMessages(
			MBMessage parentMessage, MBCategory category, long oldThreadId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBThreadLocalServiceImpl");
	}

}
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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageDisplay;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.model.MBThreadConstants;
import com.liferay.message.boards.kernel.model.MBTreeWalker;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class MBMessageDisplayImpl implements MBMessageDisplay {

	public MBMessageDisplayImpl(
		long userId, MBMessage message, MBMessage parentMessage,
		MBCategory category, MBThread thread, int status,
		MBMessageLocalService messageLocalService,
		Comparator<MBMessage> comparator) {

		_message = message;
		_parentMessage = parentMessage;
		_category = category;
		_thread = thread;

		_treeWalker = new MBTreeWalkerImpl(
			userId, message.getThreadId(), status, messageLocalService,
			comparator);

		_previousThread = null;
		_nextThread = null;
		_threadView = MBThreadConstants.THREAD_VIEW_TREE;

		int dicussionMessagesCount = 0;

		if (message.isDiscussion() &&
			(PropsValues.DISCUSSION_MAX_COMMENTS > 0)) {

			dicussionMessagesCount =
				messageLocalService.getDiscussionMessagesCount(
					message.getClassName(), message.getClassPK(),
					WorkflowConstants.STATUS_APPROVED);
		}

		_discussionMessagesCount = dicussionMessagesCount;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #MBMessageDisplayImpl(long,
	 *             MBMessage, MBMessage, MBCategory, MBThread, int,
	 *             MBMessageLocalService, Comparator)}
	 */
	@Deprecated
	public MBMessageDisplayImpl(
		MBMessage message, MBMessage parentMessage, MBCategory category,
		MBThread thread, int status, MBMessageLocalService messageLocalService,
		Comparator<MBMessage> comparator) {

		this(
			0, message, parentMessage, category, thread, status,
			messageLocalService, comparator);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #MBMessageDisplayImpl(MBMessage, MBMessage, MBCategory,
	 *             MBThread, int, MBMessageLocalService, Comparator)}
	 */
	@Deprecated
	public MBMessageDisplayImpl(
		MBMessage message, MBMessage parentMessage, MBCategory category,
		MBThread thread, MBThread previousThread, MBThread nextThread,
		int status, String threadView,
		MBMessageLocalService messageLocalService,
		Comparator<MBMessage> comparator) {

		_message = message;
		_parentMessage = parentMessage;
		_category = category;
		_thread = thread;

		if (!threadView.equals(MBThreadConstants.THREAD_VIEW_FLAT)) {
			_treeWalker = new MBTreeWalkerImpl(
				message.getThreadId(), status, messageLocalService, comparator);
		}
		else {
			_treeWalker = null;
		}

		_previousThread = previousThread;
		_nextThread = nextThread;
		_threadView = threadView;

		int dicussionMessagesCount = 0;

		if (message.isDiscussion() &&
			(PropsValues.DISCUSSION_MAX_COMMENTS > 0)) {

			dicussionMessagesCount =
				messageLocalService.getDiscussionMessagesCount(
					message.getClassName(), message.getClassPK(),
					WorkflowConstants.STATUS_APPROVED);
		}

		_discussionMessagesCount = dicussionMessagesCount;
	}

	@Override
	public MBCategory getCategory() {
		return _category;
	}

	@Override
	public MBMessage getMessage() {
		return _message;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public MBThread getNextThread() {
		return _nextThread;
	}

	@Override
	public MBMessage getParentMessage() {
		return _parentMessage;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public MBThread getPreviousThread() {
		return _previousThread;
	}

	@Override
	public MBThread getThread() {
		return _thread;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String getThreadView() {
		return _threadView;
	}

	@Override
	public MBTreeWalker getTreeWalker() {
		return _treeWalker;
	}

	@Override
	public boolean isDiscussionMaxComments() {
		if (_message.isDiscussion() &&
			(PropsValues.DISCUSSION_MAX_COMMENTS > 0) &&
			(PropsValues.DISCUSSION_MAX_COMMENTS <= _discussionMessagesCount)) {

			return true;
		}

		return false;
	}

	private final MBCategory _category;
	private final int _discussionMessagesCount;
	private final MBMessage _message;
	private final MBThread _nextThread;
	private final MBMessage _parentMessage;
	private final MBThread _previousThread;
	private final MBThread _thread;
	private final String _threadView;
	private final MBTreeWalker _treeWalker;

}
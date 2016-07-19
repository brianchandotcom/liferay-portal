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

package com.liferay.message.boards.comment.internal;

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBTreeWalker;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionCommentIterator;
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.persistence.RatingsStatsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class MBDiscussionCommentImpl
	extends MBCommentImpl implements DiscussionComment, WorkflowableComment {

	public MBDiscussionCommentImpl(
		MBMessage message, MBTreeWalker treeWalker,
		List<RatingsEntry> ratingsEntries, List<RatingsStats> ratingsStats) {

		super(message);

		_treeWalker = treeWalker;
		_ratingsEntries = ratingsEntries;
		_ratingsStats = ratingsStats;
	}

	@Override
	public List<DiscussionComment> getDescendantComments() {
		List<DiscussionComment> discussionComments = new ArrayList<>();

		DiscussionCommentIterator discussionCommentIterator =
			getThreadDiscussionCommentIterator();

		while (discussionCommentIterator.hasNext()) {
			discussionComments.add(discussionCommentIterator.next());
		}

		return discussionComments;
	}

	@Override
	public int getDescendantCommentsCount() {
		List<MBMessage> messages = _treeWalker.getChildren(getMessage());

		return messages.size();
	}

	@Override
	public DiscussionComment getParentComment() throws PortalException {
		MBMessage message = getMessage();

		long parentMessageId = message.getParentMessageId();

		if (parentMessageId == 0) {
			return null;
		}

		MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(
			parentMessageId);

		return new MBDiscussionCommentImpl(
			parentMessage, _treeWalker, _ratingsEntries, _ratingsStats);
	}

	@Override
	public RatingsEntry getRatingsEntry() {
		long classPK = getCommentId();

		for (RatingsEntry ratingsEntry : _ratingsEntries) {
			if (ratingsEntry.getClassPK() == classPK) {
				return ratingsEntry;
			}
		}

		return null;
	}

	@Override
	public RatingsStats getRatingsStats() {
		long classPK = getCommentId();

		for (RatingsStats ratingsStats : _ratingsStats) {
			if (ratingsStats.getClassPK() == classPK) {
				return ratingsStats;
			}
		}

		return RatingsStatsUtil.create(0);
	}

	@Deprecated
	@Override
	public List<DiscussionComment> getThreadComments() {
		return getDescendantComments();
	}

	@Deprecated
	@Override
	public int getThreadCommentsCount() {
		return getDescendantCommentsCount();
	}

	@Override
	public DiscussionCommentIterator getThreadDiscussionCommentIterator() {
		List<MBMessage> messages = _treeWalker.getMessages();

		int[] range = _treeWalker.getChildrenRange(getMessage());

		return new MBDiscussionCommentIterator(
			messages, range[0], range[1], _treeWalker);
	}

	@Override
	public DiscussionCommentIterator getThreadDiscussionCommentIterator(
		int from) {

		List<MBMessage> messages = _treeWalker.getMessages();

		int[] range = _treeWalker.getChildrenRange(getMessage());

		return new MBDiscussionCommentIterator(
			messages, from + 1, range[1], _treeWalker);
	}

	@Override
	public boolean isRoot() {
		MBMessage message = getMessage();

		return message.isRoot();
	}

	private final List<RatingsEntry> _ratingsEntries;
	private final List<RatingsStats> _ratingsStats;
	private final MBTreeWalker _treeWalker;

	private class MBDiscussionCommentIterator
		implements DiscussionCommentIterator {

		public MBDiscussionCommentIterator(
			List<MBMessage> messages, int from, int to,
			MBTreeWalker treeWalker) {

			_messages = messages;
			_from = from;
			_to = to;
			_treeWalker = treeWalker;
		}

		@Override
		public int getIndexPage() {
			return _from;
		}

		@Override
		public boolean hasNext() {
			if (_from < _to) {
				return true;
			}

			return false;
		}

		@Override
		public DiscussionComment next() {
			DiscussionComment discussionComment = new MBDiscussionCommentImpl(
				_messages.get(_from), _treeWalker, _ratingsEntries,
				_ratingsStats);

			_from++;

			return discussionComment;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		private int _from;
		private final List<MBMessage> _messages;
		private final int _to;
		private final MBTreeWalker _treeWalker;

	}

}
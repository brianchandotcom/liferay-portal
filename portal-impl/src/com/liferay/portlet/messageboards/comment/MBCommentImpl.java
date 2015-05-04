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

package com.liferay.portlet.messageboards.comment;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentIterator;
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class MBCommentImpl implements Comment, WorkflowableComment {

	public MBCommentImpl(
		MBMessage message, MBTreeWalker treeWalker,
		List<RatingsEntry> ratingsEntries, List<RatingsStats> ratingsStats,
		String pathThemeImages) {

		_message = message;
		_treeWalker = treeWalker;
		_ratingsEntries = ratingsEntries;
		_ratingsStats = ratingsStats;
		_pathThemeImages = pathThemeImages;
	}

	@Override
	public String getBody() {
		return _message.getBody();
	}

	@Override
	public long getCommentId() {
		return _message.getMessageId();
	}

	@Override
	public long getCompanyId() {
		return _message.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _message.getCreateDate();
	}

	@Override
	public long getGroupId() {
		return _message.getGroupId();
	}

	public MBMessage getMessage() {
		return _message;
	}

	@Override
	public Class<?> getModelClass() {
		return MBMessage.class;
	}

	@Override
	public String getModelClassName() {
		return MBMessage.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return _message.getModifiedDate();
	}

	@Override
	public Comment getParentComment() throws PortalException {
		long parentMessageId = _message.getParentMessageId();

		if (parentMessageId == 0) {
			return null;
		}

		MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(
			parentMessageId);

		return new MBCommentImpl(
			parentMessage, _treeWalker, _ratingsEntries, _ratingsStats,
			_pathThemeImages);
	}

	@Override
	public long getParentCommentId() {
		return _message.getParentMessageId();
	}

	@Override
	public long getPrimaryKey() {
		return _message.getPrimaryKey();
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

	@Override
	public int getStatus() {
		return _message.getStatus();
	}

	@Override
	public List<Comment> getThreadComments() {
		List<Comment> comments = new ArrayList<>();

		CommentIterator commentIterator = getThreadCommentsIterator();

		while (commentIterator.hasNext()) {
			comments.add(commentIterator.next());
		}

		return comments;
	}

	@Override
	public int getThreadCommentsCount() {
		List<MBMessage> messages = _treeWalker.getMessages();

		return messages.size();
	}

	@Override
	public CommentIterator getThreadCommentsIterator() {
		List<MBMessage> messages = _treeWalker.getMessages();

		int[] range = _treeWalker.getChildrenRange(_message);

		return new MBCommentIterator(
			messages, range[0], range[1], _treeWalker, _pathThemeImages);
	}

	@Override
	public CommentIterator getThreadCommentsIterator(int from) {
		List<MBMessage> messages = _treeWalker.getMessages();

		int[] range = _treeWalker.getChildrenRange(_message);

		return new MBCommentIterator(
			messages, from, range[1], _treeWalker, _pathThemeImages);
	}

	@Override
	public String getTranslatedBody() {
		if (_message.isFormatBBCode()) {
			return MBUtil.getBBCodeHTML(getBody(), _pathThemeImages);
		}

		return getBody();
	}

	@Override
	public User getUser() {
		return UserLocalServiceUtil.fetchUser(getUserId());
	}

	@Override
	public long getUserId() {
		return _message.getUserId();
	}

	@Override
	public String getUserName() {
		return _message.getUserName();
	}

	@Override
	public boolean isRoot() {
		return _message.isRoot();
	}

	private final MBMessage _message;
	private final String _pathThemeImages;
	private final List<RatingsEntry> _ratingsEntries;
	private final List<RatingsStats> _ratingsStats;
	private final MBTreeWalker _treeWalker;

	private class MBCommentIterator implements CommentIterator {

		public MBCommentIterator(
			List<MBMessage> messages, int from, int to, MBTreeWalker treeWalker,
			String pathThemeImages) {

			_messages = messages;
			_from = from;
			_to = to;
			_treeWalker = treeWalker;
			_pathThemeImages = pathThemeImages;
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
		public Comment next() {
			Comment comment = new MBCommentImpl(
				_messages.get(_from), _treeWalker, _ratingsEntries,
				_ratingsStats, _pathThemeImages);

			_from++;

			return comment;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		private int _from;
		private final List<MBMessage> _messages;
		private final String _pathThemeImages;
		private final int _to;
		private final MBTreeWalker _treeWalker;

	}

}
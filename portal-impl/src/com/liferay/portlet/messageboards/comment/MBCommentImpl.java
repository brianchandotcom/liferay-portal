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
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.MBUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class MBCommentImpl implements Comment, WorkflowableComment {

	public MBCommentImpl(
		MBMessage message, MBTreeWalker treeWalker, String pathThemeImages) {

		_message = message;
		_treeWalker = treeWalker;
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
	public Date getModifiedDate() {
		return _message.getModifiedDate();
	}

	@Override
	public Comment getParentComment() throws PortalException {
		long parentMessageId = _message.getParentMessageId();

		MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(
			parentMessageId);

		return new MBCommentImpl(parentMessage, _treeWalker, _pathThemeImages);
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
	public int getStatus() {
		return _message.getStatus();
	}

	@Override
	public List<Comment> getThreadComments() {
		List<MBMessage> messages = _treeWalker.getMessages();

		int[] range = _treeWalker.getChildrenRange(_message);

		List<Comment> comments = new ArrayList<>();

		for (int i = range[0]; i < range[1]; i++) {
			MBMessage message = messages.get(i);

			comments.add(
				new MBCommentImpl(message, _treeWalker, _pathThemeImages));
		}

		return comments;
	}

	@Override
	public String getTranslatedBody() {
		if (_message.isFormatBBCode()) {
			return MBUtil.getBBCodeHTML(getBody(), _pathThemeImages);
		}

		return getBody();
	}

	@Override
	public User getUser() throws PortalException {
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
	private final MBTreeWalker _treeWalker;

}
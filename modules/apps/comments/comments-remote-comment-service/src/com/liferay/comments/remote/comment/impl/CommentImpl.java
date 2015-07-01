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

package com.liferay.comments.remote.comment.impl;

import com.liferay.comments.remote.comment.Comment;
import com.liferay.portal.kernel.comment.DiscussionComment;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class CommentImpl implements Comment {

	public CommentImpl() {
	}

	public CommentImpl(DiscussionComment discussionComment) {
		setBody(discussionComment.getBody());
		setCommentId(discussionComment.getCommentId());
		setCreateDate(discussionComment.getCreateDate());
		setModifiedDate(discussionComment.getModifiedDate());
		setUserId(discussionComment.getUserId());
		setUserName(discussionComment.getUserName());
	}

	@Override
	public String getBody() {
		return _body;
	}

	@Override
	public long getCommentId() {
		return _commentId;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public String getUserName() {
		return _userName;
	}

	public void setBody(String body) {
		_body = body;
	}

	public void setCommentId(long commentId) {
		_commentId = commentId;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	private String _body;
	private long _commentId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _userId;
	private String _userName;

}
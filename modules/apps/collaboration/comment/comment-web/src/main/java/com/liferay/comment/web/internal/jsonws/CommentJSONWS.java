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

package com.liferay.comment.web.internal.jsonws;

import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.json.JSON;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
@JSON(strict = true)
public class CommentJSONWS {

	public CommentJSONWS() {
	}

	public CommentJSONWS(DiscussionComment discussionComment) {
		setBody(discussionComment.getBody());
		setCommentId(discussionComment.getCommentId());
		setCreateDate(discussionComment.getCreateDate());
		setDescendantCommentsCount(
			discussionComment.getDescendantCommentsCount());
		setModifiedDate(discussionComment.getModifiedDate());
		setParentCommentId(discussionComment.getParentCommentId());
		setUserId(discussionComment.getUserId());
		setUserName(discussionComment.getUserName());
	}

	@JSON
	public String getBody() {
		return _body;
	}

	@JSON
	public long getCommentId() {
		return _commentId;
	}

	@JSON
	public Date getCreateDate() {
		return _createDate;
	}

	@JSON
	public int getDescendantCommentsCount() {
		return _descendantCommentsCount;
	}

	@JSON
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@JSON
	public long getParentCommentId() {
		return _parentCommentId;
	}

	@JSON
	public long getUserId() {
		return _userId;
	}

	@JSON
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

	public void setDescendantCommentsCount(int descendantCommentsCount) {
		_descendantCommentsCount = descendantCommentsCount;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setParentCommentId(long parentCommentId) {
		_parentCommentId = parentCommentId;
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
	private int _descendantCommentsCount;
	private Date _modifiedDate;
	private long _parentCommentId;
	private long _userId;
	private String _userName;

}
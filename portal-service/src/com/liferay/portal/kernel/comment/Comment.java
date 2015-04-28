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

package com.liferay.portal.kernel.comment;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.User;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;

import java.util.Date;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public interface Comment {

	public String getBody();

	public long getCommentId();

	public Date getCreateDate();

	public Date getModifiedDate();

	public Comment getParentComment() throws PortalException;

	public long getParentCommentId();

	public RatingsEntry getRatingsEntry();

	public RatingsStats getRatingsStats();

	public List<Comment> getThreadComments();

	public int getThreadCommentsCount();

	public CommentIterator getThreadCommentsIterator();

	public CommentIterator getThreadCommentsIterator(int from);

	public String getTranslatedBody();

	public User getUser() throws PortalException;

	public long getUserId();

	public String getUserName();

	public boolean isRoot();

}
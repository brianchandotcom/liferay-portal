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

package com.liferay.comments.remote.comment;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public interface CommentService {

	public long addComment(
			long groupId, String className, long classPK, String body)
		throws PortalException;

	public void deleteComment(long commentId) throws PortalException;

	public List<Comment> getComments(
			long groupId, String className, long classPK, int start, int end)
		throws PortalException;

	public int getCommentsCount(long groupId, String className, long classPK)
		throws PortalException;

	public boolean hasDiscussion(long groupId, String className, long classPK)
		throws PortalException;

	public void subscribeDiscussion(
			long groupId, String className, long classPK)
		throws PortalException;

	public void unsubscribeDiscussion(
			long groupId, String className, long classPK)
		throws PortalException;

	public long updateComment(
			String className, long classPK, long commentId, String subject,
			String body)
		throws PortalException;

}
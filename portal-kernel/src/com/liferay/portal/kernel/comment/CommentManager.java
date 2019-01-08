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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.function.Function;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface CommentManager {

	public long addComment(
			long userId, long groupId, String className, long classPK,
			String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException;

	public long addComment(
			long userId, long groupId, String className, long classPK,
			String userName, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException;

	public long addComment(
			long userId, String className, long classPK, String userName,
			long parentCommentId, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException;

	public void addDiscussion(
			long userId, long groupId, String className, long classPK,
			String userName)
		throws PortalException;

	public void deleteComment(long commentId) throws PortalException;

	public void deleteDiscussion(String className, long classPK)
		throws PortalException;

	public void deleteGroupComments(long groupId) throws PortalException;

	public Comment fetchComment(long commentId);

	public DiscussionComment fetchDiscussionComment(long userId, long commentId)
		throws PortalException;

	/**
	 * Returns a range of all the comments which parent is identified by the
	 * provided {@code parentCommentId} and with the given {@code status}.
	 *
	 * @param  parentCommentId the ID of the parent comment
	 * @param  status the status of the comments
	 * @param  start the lower bound of the range of comments
	 * @param  end the upper bound of the range of comments (not inclusive)
	 * @return the range of matching comments
	 * @review
	 */
	public List<Comment> getChildComments(
		long parentCommentId, int status, int start, int end);

	/**
	 * Returns the total count of comments which parent is identified by the
	 * provided {@code parentCommentId} and with the given {@code status}.
	 *
	 * @param  parentCommentId the ID of the parent comment
	 * @param  status the status of the comments
	 * @return the count of matching comments
	 * @review
	 */
	public int getChildCommentsCount(long parentCommentId, int status);

	public int getCommentsCount(String className, long classPK);

	public Discussion getDiscussion(
			long userId, long groupId, String className, long classPK,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException;

	public DiscussionPermission getDiscussionPermission(
		PermissionChecker permissionChecker);

	public DiscussionStagingHandler getDiscussionStagingHandler();

	/**
	 * Returns a range of all the root comments of a model identified by the
	 * provide combination of {@code className}-{@code classPK} and with the
	 * given {@code status}. This count includes only direct comments to the
	 * model, it does not include replies.
	 *
	 * @param  className the classname
	 * @param  classPK the model classPK
	 * @param  status the status of the comments
	 * @param  start the lower bound of the range of comments
	 * @param  end the upper bound of the range of comments (not inclusive)
	 * @return the range of matching comments
	 * @review
	 */
	public List<Comment> getRootComments(
			String className, long classPK, int status, int start, int end)
		throws PortalException;

	/**
	 * Returns the total count of root comments of a model identified by the
	 * provide combination of {@code className}-{@code classPK} and with the
	 * given {@code status}. This count includes only direct comments to the
	 * model, it does not include replies.
	 *
	 * @param  className the classname
	 * @param  classPK the model classPK
	 * @param  status the status of the comments
	 * @return the count of matching comments
	 * @review
	 */
	public int getRootCommentsCount(String className, long classPK, int status);

	public boolean hasDiscussion(String className, long classPK)
		throws PortalException;

	public void moveDiscussionToTrash(String className, long classPK);

	public void restoreDiscussionFromTrash(String className, long classPK);

	public void subscribeDiscussion(
			long userId, long groupId, String className, long classPK)
		throws PortalException;

	public void unsubscribeDiscussion(
			long userId, String className, long classPK)
		throws PortalException;

	public long updateComment(
			long userId, String className, long classPK, long commentId,
			String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException;

}
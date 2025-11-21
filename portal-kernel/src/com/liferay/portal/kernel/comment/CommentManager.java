/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.comment;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.function.Function;

import org.osgi.annotation.versioning.ProviderType;

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
			String externalReferenceCode, long userId, long groupId,
			String className, long classPK, String userName, String subject,
			String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException;

	public long addComment(
			String externalReferenceCode, long userId, String className,
			long classPK, String userName, long parentCommentId, String subject,
			String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException;

	public void addDiscussion(
			long userId, long groupId, String className, long classPK,
			String userName)
		throws PortalException;

	public Discussion copyDiscussion(
			long userId, long groupId, String className, long classPK,
			long newClassPK,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException;

	public void deleteComment(long commentId) throws PortalException;

	public void deleteDiscussion(String className, long classPK)
		throws PortalException;

	public void deleteGroupComments(long groupId) throws PortalException;

	public Comment fetchComment(long commentId);

	/**
	 * Returns a comment matching with the external reference code and the
	 * group ID
	 *
	 * @param groupId the primary key of the group
	 * @param externalReferenceCode the comment's external reference code
	 * @return the matching comment or null if it could not be found
	 */
	public Comment fetchComment(long groupId, String externalReferenceCode);

	public DiscussionComment fetchDiscussionComment(long userId, long commentId)
		throws PortalException;

	/**
	 * Returns a range of all the comments matching the parent comment ID and
	 * status.
	 *
	 * @param  parentCommentId the parent comment's ID
	 * @param  status the comments' status
	 * @param  start the lower bound of the range of comments
	 * @param  end the upper bound of the range of comments (not inclusive)
	 * @return the range of matching comments
	 */
	public List<Comment> getChildComments(
		long parentCommentId, int status, int start, int end);

	/**
	 * Returns the number of comments matching the parent comment ID and status.
	 *
	 * @param  parentCommentId the parent comment's ID
	 * @param  status the comments' status
	 * @return the number of matching comments
	 */
	public int getChildCommentsCount(long parentCommentId, int status);

	public Comment getComment(long groupId, String externalReferenceCode)
		throws PortalException;

	public int getCommentsCount(String className, long classPK);

	public Discussion getDiscussion(
			long userId, long groupId, String className, long classPK,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException;

	public DiscussionStagingHandler getDiscussionStagingHandler();

	/**
	 * Returns a range of all the model's root comments matching the class name,
	 * class primary key, and status. This count includes only direct comments
	 * to the model; it does not include replies.
	 *
	 * @param  className the class name
	 * @param  classPK the model class's primary key
	 * @param  status the comments' status
	 * @param  start the lower bound of the range of comments
	 * @param  end the upper bound of the range of comments (not inclusive)
	 * @return the range of matching comments
	 */
	public List<Comment> getRootComments(
			String className, long classPK, int status, int start, int end)
		throws PortalException;

	/**
	 * Returns the number of the model's root comments matching the class name,
	 * class primary key, and status. This count includes only direct comments
	 * to the model; it does not include replies.
	 *
	 * @param  className the class name
	 * @param  classPK the model class's primary key
	 * @param  status the comments' status
	 * @return the number of matching comments
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
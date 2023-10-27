/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.comment;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.function.Function;

/**
 * @author Adolfo Pérez
 */
public class CommentManagerUtil {

	public static long addComment(
			long userId, long groupId, String className, long classPK,
			String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		CommentManager commentManager = _commentManagerSnapshot.get();

		return commentManager.addComment(
			userId, groupId, className, classPK, body, serviceContextFunction);
	}

	public static long addComment(
			long userId, long groupId, String className, long classPK,
			String userName, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		CommentManager commentManager = _commentManagerSnapshot.get();

		return commentManager.addComment(
			null, userId, groupId, className, classPK, userName, subject, body,
			serviceContextFunction);
	}

	public static long addComment(
			long userId, String className, long classPK, String userName,
			long parentCommentId, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		CommentManager commentManager = _commentManagerSnapshot.get();

		return commentManager.addComment(
			null, userId, className, classPK, userName, parentCommentId,
			subject, body, serviceContextFunction);
	}

	public static void addDiscussion(
			long userId, long groupId, String className, long classPK,
			String userName)
		throws PortalException {

		CommentManager commentManager = _commentManagerSnapshot.get();

		commentManager.addDiscussion(
			userId, groupId, className, classPK, userName);
	}

	public static void deleteComment(long commentId) throws PortalException {
		CommentManager commentManager = _commentManagerSnapshot.get();

		commentManager.deleteComment(commentId);
	}

	public static void deleteDiscussion(String className, long classPK)
		throws PortalException {

		CommentManager commentManager = _commentManagerSnapshot.get();

		commentManager.deleteDiscussion(className, classPK);
	}

	public static void deleteGroupComments(long groupId)
		throws PortalException {

		CommentManager commentManager = _commentManagerSnapshot.get();

		commentManager.deleteGroupComments(groupId);
	}

	public static Comment fetchComment(long commentId) {
		CommentManager commentManager = _commentManagerSnapshot.get();

		return commentManager.fetchComment(commentId);
	}

	public static int getCommentsCount(String className, long classPK) {
		CommentManager commentManager = _commentManagerSnapshot.get();

		return commentManager.getCommentsCount(className, classPK);
	}

	public static Discussion getDiscussion(
			long userId, long groupId, String className, long classPK,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		CommentManager commentManager = _commentManagerSnapshot.get();

		return commentManager.getDiscussion(
			userId, groupId, className, classPK, serviceContextFunction);
	}

	public static DiscussionStagingHandler getDiscussionStagingHandler() {
		CommentManager commentManager = _commentManagerSnapshot.get();

		return commentManager.getDiscussionStagingHandler();
	}

	public static boolean hasDiscussion(String className, long classPK)
		throws PortalException {

		CommentManager commentManager = _commentManagerSnapshot.get();

		return commentManager.hasDiscussion(className, classPK);
	}

	public static void moveDiscussionToTrash(String className, long classPK) {
		CommentManager commentManager = _commentManagerSnapshot.get();

		commentManager.moveDiscussionToTrash(className, classPK);
	}

	public static void restoreDiscussionFromTrash(
		String className, long classPK) {

		CommentManager commentManager = _commentManagerSnapshot.get();

		commentManager.restoreDiscussionFromTrash(className, classPK);
	}

	public static void subscribeDiscussion(
			long userId, long groupId, String className, long classPK)
		throws PortalException {

		CommentManager commentManager = _commentManagerSnapshot.get();

		commentManager.subscribeDiscussion(userId, groupId, className, classPK);
	}

	public static void unsubscribeDiscussion(
			long userId, String className, long classPK)
		throws PortalException {

		CommentManager commentManager = _commentManagerSnapshot.get();

		commentManager.unsubscribeDiscussion(userId, className, classPK);
	}

	public static long updateComment(
			long userId, String className, long classPK, long commentId,
			String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		CommentManager commentManager = _commentManagerSnapshot.get();

		return commentManager.updateComment(
			userId, className, classPK, commentId, subject, body,
			serviceContextFunction);
	}

	private static final Snapshot<CommentManager> _commentManagerSnapshot =
		new Snapshot<>(CommentManagerUtil.class, CommentManager.class);

}
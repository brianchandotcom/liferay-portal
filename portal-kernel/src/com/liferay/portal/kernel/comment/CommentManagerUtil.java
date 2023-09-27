/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.comment;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

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

		return _commentManager.addComment(
			userId, groupId, className, classPK, body, serviceContextFunction);
	}

	public static long addComment(
			long userId, long groupId, String className, long classPK,
			String userName, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return _commentManager.addComment(
			null, userId, groupId, className, classPK, userName, subject, body,
			serviceContextFunction);
	}

	public static long addComment(
			long userId, String className, long classPK, String userName,
			long parentCommentId, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return _commentManager.addComment(
			null, userId, className, classPK, userName, parentCommentId,
			subject, body, serviceContextFunction);
	}

	public static void addDiscussion(
			long userId, long groupId, String className, long classPK,
			String userName)
		throws PortalException {

		_commentManager.addDiscussion(
			userId, groupId, className, classPK, userName);
	}

	public static void deleteComment(long commentId) throws PortalException {
		_commentManager.deleteComment(commentId);
	}

	public static void deleteDiscussion(String className, long classPK)
		throws PortalException {

		_commentManager.deleteDiscussion(className, classPK);
	}

	public static void deleteGroupComments(long groupId)
		throws PortalException {

		_commentManager.deleteGroupComments(groupId);
	}

	public static Comment fetchComment(long commentId) {
		return _commentManager.fetchComment(commentId);
	}

	public static int getCommentsCount(String className, long classPK) {
		return _commentManager.getCommentsCount(className, classPK);
	}

	public static Discussion getDiscussion(
			long userId, long groupId, String className, long classPK,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return _commentManager.getDiscussion(
			userId, groupId, className, classPK, serviceContextFunction);
	}

	public static DiscussionStagingHandler getDiscussionStagingHandler() {
		return _commentManager.getDiscussionStagingHandler();
	}

	public static boolean hasDiscussion(String className, long classPK)
		throws PortalException {

		return _commentManager.hasDiscussion(className, classPK);
	}

	public static void moveDiscussionToTrash(String className, long classPK) {
		_commentManager.moveDiscussionToTrash(className, classPK);
	}

	public static void restoreDiscussionFromTrash(
		String className, long classPK) {

		_commentManager.restoreDiscussionFromTrash(className, classPK);
	}

	public static void subscribeDiscussion(
			long userId, long groupId, String className, long classPK)
		throws PortalException {

		_commentManager.subscribeDiscussion(
			userId, groupId, className, classPK);
	}

	public static void unsubscribeDiscussion(
			long userId, String className, long classPK)
		throws PortalException {

		_commentManager.unsubscribeDiscussion(userId, className, classPK);
	}

	public static long updateComment(
			long userId, String className, long classPK, long commentId,
			String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return _commentManager.updateComment(
			userId, className, classPK, commentId, subject, body,
			serviceContextFunction);
	}

	private static volatile CommentManager _commentManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			CommentManager.class, CommentManagerUtil.class, "_commentManager",
			false);

}
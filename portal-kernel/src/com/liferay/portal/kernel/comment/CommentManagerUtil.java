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
import com.liferay.portal.kernel.security.permission.PermissionChecker;
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

		return _getCommentManager().addComment(
			userId, groupId, className, classPK, body, serviceContextFunction);
	}

	public static long addComment(
			long userId, long groupId, String className, long classPK,
			String userName, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return _getCommentManager().addComment(
			userId, groupId, className, classPK, userName, subject, body,
			serviceContextFunction);
	}

	public static long addComment(
			long userId, String className, long classPK, String userName,
			long parentCommentId, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return _getCommentManager().addComment(
			userId, className, classPK, userName, parentCommentId, subject,
			body, serviceContextFunction);
	}

	public static void addDiscussion(
			long userId, long groupId, String className, long classPK,
			String userName)
		throws PortalException {

		_getCommentManager().addDiscussion(
			userId, groupId, className, classPK, userName);
	}

	public static void deleteComment(long commentId) throws PortalException {
		_getCommentManager().deleteComment(commentId);
	}

	public static void deleteDiscussion(String className, long classPK)
		throws PortalException {

		_getCommentManager().deleteDiscussion(className, classPK);
	}

	public static void deleteGroupComments(long groupId)
		throws PortalException {

		_getCommentManager().deleteGroupComments(groupId);
	}

	public static Comment fetchComment(long commentId) {
		return _getCommentManager().fetchComment(commentId);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #_getCommentManager()}
	 */
	@Deprecated
	public static CommentManager getCommentManager() {
		return _getCommentManager();
	}

	public static int getCommentsCount(String className, long classPK) {
		return _getCommentManager().getCommentsCount(className, classPK);
	}

	public static Discussion getDiscussion(
			long userId, long groupId, String className, long classPK,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return _getCommentManager().getDiscussion(
			userId, groupId, className, classPK, serviceContextFunction);
	}

	public static DiscussionPermission getDiscussionPermission(
		PermissionChecker permissionChecker) {

		return _getCommentManager().getDiscussionPermission(permissionChecker);
	}

	public static DiscussionStagingHandler getDiscussionStagingHandler() {
		return _getCommentManager().getDiscussionStagingHandler();
	}

	public static boolean hasDiscussion(String className, long classPK)
		throws PortalException {

		return _getCommentManager().hasDiscussion(className, classPK);
	}

	public static void moveDiscussionToTrash(String className, long classPK) {
		_getCommentManager().moveDiscussionToTrash(className, classPK);
	}

	public static void restoreDiscussionFromTrash(
		String className, long classPK) {

		_getCommentManager().restoreDiscussionFromTrash(className, classPK);
	}

	public static void subscribeDiscussion(
			long userId, long groupId, String className, long classPK)
		throws PortalException {

		_getCommentManager().subscribeDiscussion(
			userId, groupId, className, classPK);
	}

	public static void unsubscribeDiscussion(
			long userId, String className, long classPK)
		throws PortalException {

		_getCommentManager().unsubscribeDiscussion(userId, className, classPK);
	}

	public static long updateComment(
			long userId, String className, long classPK, long commentId,
			String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return _getCommentManager().updateComment(
			userId, className, classPK, commentId, subject, body,
			serviceContextFunction);
	}

	private static CommentManager _getCommentManager() {
		return _commentManager;
	}

	private static volatile CommentManager _commentManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			CommentManager.class, CommentManagerUtil.class, "_commentManager",
			false);

}
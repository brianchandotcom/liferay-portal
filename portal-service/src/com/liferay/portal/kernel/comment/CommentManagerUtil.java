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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;

/**
 * @author Adolfo Pérez
 */
public class CommentManagerUtil {

	public static long addComment(
			long userId, long groupId, String className, long classPK,
			String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return getCommentManager().addComment(
			userId, groupId, className, classPK, body, serviceContextFunction);
	}

	public static long addComment(
			long userId, long groupId, String className, long classPK,
			String userName, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return getCommentManager().addComment(
			userId, groupId, className, classPK, userName, subject, body,
			serviceContextFunction);
	}

	public static long addComment(
			long userId, String className, long classPK, String userName,
			long parentCommentId, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return getCommentManager().addComment(
			userId, className, classPK, userName, parentCommentId, subject,
			body, serviceContextFunction);
	}

	public static void addDiscussion(
			long userId, long groupId, String className, long classPK,
			String userName)
		throws PortalException {

		getCommentManager().addDiscussion(
			userId, groupId, className, classPK, userName);
	}

	public static void deleteComment(long commentId) throws PortalException {
		getCommentManager().deleteComment(commentId);
	}

	public static void deleteDiscussion(String className, long classPK)
		throws PortalException {

		getCommentManager().deleteDiscussion(className, classPK);
	}

	public static Comment fetchComment(long commentId) {
		return getCommentManager().fetchComment(commentId);
	}

	public static CommentManager getCommentManager() {
		PortalRuntimePermission.checkGetBeanProperty(CommentManagerUtil.class);

		return _commentManager;
	}

	public static int getCommentsCount(String className, long classPK) {
		return getCommentManager().getCommentsCount(className, classPK);
	}

	public static Discussion getDiscussion(
			long userId, long groupId, String className, long classPK,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return getCommentManager().getDiscussion(
			userId, groupId, className, classPK, serviceContextFunction);
	}

	public static DiscussionPermission getDiscussionPermission(
		PermissionChecker permissionChecker) {

		return getCommentManager().getDiscussionPermission(permissionChecker);
	}

	public static DiscussionStagingHandler getDiscussionStagingHandler() {
		return getCommentManager().getDiscussionStagingHandler();
	}

	public static boolean hasDiscussion(String className, long classPK)
		throws PortalException {

		return getCommentManager().hasDiscussion(className, classPK);
	}

	public static void subscribeDiscussion(
			long userId, long groupId, String className, long classPK)
		throws PortalException {

		getCommentManager().subscribeDiscussion(
			userId, groupId, className, classPK);
	}

	public static void unsubscribeDiscussion(
			long userId, String className, long classPK)
		throws PortalException {

		getCommentManager().unsubscribeDiscussion(userId, className, classPK);
	}

	public static long updateComment(
			long userId, String className, long classPK, long commentId,
			String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		return getCommentManager().updateComment(
			userId, className, classPK, commentId, subject, body,
			serviceContextFunction);
	}

	public void setCommentManager(CommentManager commentManager) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_commentManager = commentManager;
	}

	private static CommentManager _commentManager;

}
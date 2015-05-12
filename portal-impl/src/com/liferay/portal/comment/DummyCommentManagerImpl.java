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

package com.liferay.portal.comment;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;

/**
 * @author André de Oliveira
 */
public class DummyCommentManagerImpl implements CommentManager {

	@Override
	public void addComment(
		long userId, long groupId, String className, long classPK, String body,
		Function<String, ServiceContext> serviceContextFunction) {
	}

	@Override
	public long addComment(
		long userId, long groupId, String className, long classPK,
		String userName, String subject, String body,
		Function<String, ServiceContext> serviceContextFunction) {

		return 0;
	}

	@Override
	public long addComment(
		long groupId, String className, long classPK,
		String permissionClassName, long permissionClassPK,
		long permissionOwnerId, long parentCommentId, String subject,
		String body,
		Function<String, ServiceContext> serviceContextFunction) {

		return 0;
	}

	@Override
	public void addDiscussion(
		long userId, long groupId, String className, long classPK,
		String userName) {
	}

	@Override
	public void deleteComment(long commentId) {
	}

	@Override
	public void deleteComment(
		long groupId, String className, long classPK,
		String permissionClassName, long permissionClassPK,
		long permissionOwnerId, long commentId) {
	}

	@Override
	public void deleteDiscussion(String className, long classPK) {
	}

	@Override
	public int getCommentsCount(String className, long classPK) {
		return 0;
	}

	@Override
	public Discussion getDiscussion(
		long userId, long groupId, String className, long classPK,
		Function<String, ServiceContext> serviceContextFunction) {

		return _discussion;
	}

	@Override
	public DiscussionPermission getDiscussionPermission(
		PermissionChecker permissionChecker) {

		return _discussionPermission;
	}

	@Override
	public void subscribeDiscussion(
		long userId, long groupId, String className, long classPK) {
	}

	@Override
	public void unsubscribeDiscussion(
		long userId, String className, long classPK) {
	}

	@Override
	public long updateComment(
		String className, long classPK, String permissionClassName,
		long permissionClassPK, long permissionOwnerId, long commentId,
		String subject, String body,
		Function<String, ServiceContext> serviceContextFunction) {

		return 0;
	}

	private static final Discussion _discussion = new Discussion() {

		@Override
		public boolean isMaxCommentsLimitExceeded() {
			return true;
		}

		@Override
		public Comment getRootComment() {
			return null;
		}

	};

	private static final DiscussionPermission _discussionPermission =
		new DiscussionPermission() {

			@Override
			public boolean hasAddPermission(
				long companyId, long groupId, String className, long classPK,
				long userId) {

				return false;
			}

			@Override
			public boolean hasDeletePermission(
				long companyId, long groupId, String className, long classPK,
				long commentId, long userId) {

				return false;
			}

			@Override
			public boolean hasUpdatePermission(
				long companyId, long groupId, String className, long classPK,
				long commentId, long userId) {

				return false;
			}

			@Override
			public boolean hasViewPermission(
				long companyId, long groupId, String className, long classPK,
				long userId) {

				return false;
			}

		};

}
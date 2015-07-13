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

import com.liferay.portal.kernel.comment.BaseDiscussionPermission;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.DiscussionStagingHandler;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.PortletDataContext;

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
		long userId, String className, long classPK, String userName,
		long parentCommentId, String subject, String body,
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
	public void deleteDiscussion(String className, long classPK) {
	}

	@Override
	public Comment fetchComment(long commentId) {
		return null;
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
	public DiscussionStagingHandler getDiscussionStagingHandler() {
		return _discussionStagingHandler;
	}

	@Override
	public boolean hasDiscussion(String className, long classPK) {
		return false;
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
		long userId, String className, long classPK, long commentId,
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
		public DiscussionComment getRootDiscussionComment() {
			return null;
		}

	};

	private static final DiscussionPermission _discussionPermission =
		new BaseDiscussionPermission() {

			@Override
			public boolean hasAddPermission(
				long companyId, long groupId, String className, long classPK) {

				return false;
			}

			@Override
			public boolean hasDeletePermission(long commentId) {

				return false;
			}

			@Override
			public boolean hasPermission(long commentId, String actionId) {
				return false;
			}

			@Override
			public boolean hasUpdatePermission(long commentId) {

				return false;
			}

			@Override
			public boolean hasViewPermission(
				long companyId, long groupId, String className, long classPK) {

				return false;
			}

		};

	private static final DiscussionStagingHandler _discussionStagingHandler =
		new DiscussionStagingHandler() {

			@Override
			public <T extends StagedModel> void exportReferenceDiscussions(
				PortletDataContext portletDataContext, T stagedModel) {
			}

			@Override
			public <T extends StagedModel> void importReferenceDiscussions(
				PortletDataContext portletDataContext, T stagedModel) {
			}

			@Override
			public String getClassName() {
				return StringPool.BLANK;
			}

			@Override
			public Class<? extends StagedModel> getStagedModelClass() {
				return null;
			}

		};

}
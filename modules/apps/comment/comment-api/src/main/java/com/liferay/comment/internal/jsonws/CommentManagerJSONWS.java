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

package com.liferay.comment.internal.jsonws;

import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionCommentIterator;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.ServiceContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@AccessControlled
@Component(
	immediate = true,
	property = {
		"json.web.service.context.name=comment",
		"json.web.service.context.path=Comment"
	},
	service = CommentManagerJSONWS.class
)
@JSONWebService
public class CommentManagerJSONWS extends BaseServiceImpl {

	public long addComment(
			long groupId, String className, long classPK, String body)
		throws PortalException {

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(getPermissionChecker());

		long companyId = getCompanyId(groupId);

		discussionPermission.checkAddPermission(
			companyId, groupId, className, classPK);

		return _commentManager.addComment(
			getUserId(), groupId, className, classPK, body,
			createServiceContextFunction(companyId));
	}

	public void deleteComment(long commentId) throws PortalException {
		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkDeletePermission(commentId);

		_commentManager.deleteComment(commentId);
	}

	public List<CommentJSONWS> getComments(
			long groupId, String className, long classPK, int start, int end)
		throws PortalException {

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkViewPermission(
			getCompanyId(groupId), groupId, className, classPK);

		Discussion discussion = _commentManager.getDiscussion(
			getUserId(), groupId, className, classPK,
			createServiceContextFunction());

		DiscussionComment rootDiscussionComment =
			discussion.getRootDiscussionComment();

		if (start == QueryUtil.ALL_POS) {
			start = 0;
		}

		DiscussionCommentIterator threadDiscussionCommentIterator =
			rootDiscussionComment.getThreadDiscussionCommentIterator(start);

		if (end == QueryUtil.ALL_POS) {
			return getAllComments(threadDiscussionCommentIterator);
		}

		int commentsCount = end - start;

		if (commentsCount <= 0) {
			return Collections.emptyList();
		}

		List<CommentJSONWS> commentJSONWSs = new ArrayList<>(commentsCount);

		while (threadDiscussionCommentIterator.hasNext() &&
			   (commentsCount > 0)) {

			CommentJSONWS commentJSONWS = new CommentJSONWS(
				threadDiscussionCommentIterator.next());

			commentJSONWSs.add(commentJSONWS);

			commentsCount--;
		}

		return commentJSONWSs;
	}

	public int getCommentsCount(long groupId, String className, long classPK)
		throws PortalException {

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkViewPermission(
			getCompanyId(groupId), groupId, className, classPK);

		return _commentManager.getCommentsCount(className, classPK);
	}

	public boolean hasDiscussion(long groupId, String className, long classPK)
		throws PortalException {

		BaseModelPermissionCheckerUtil.containsBaseModelPermission(
			getPermissionChecker(), groupId, className, classPK,
			ActionKeys.VIEW);

		return _commentManager.hasDiscussion(className, classPK);
	}

	public void subscribeDiscussion(
			long groupId, String className, long classPK)
		throws PortalException {

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkSubscribePermission(
			getCompanyId(groupId), groupId, className, classPK);

		_commentManager.subscribeDiscussion(
			getUserId(), groupId, className, classPK);
	}

	public void unsubscribeDiscussion(
			long groupId, String className, long classPK)
		throws PortalException {

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkSubscribePermission(
			getCompanyId(groupId), groupId, className, classPK);

		_commentManager.unsubscribeDiscussion(getUserId(), className, classPK);
	}

	public long updateComment(
			String className, long classPK, long commentId, String subject,
			String body)
		throws PortalException {

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkUpdatePermission(commentId);

		return _commentManager.updateComment(
			getUserId(), className, classPK, commentId, subject, body,
			createServiceContextFunction(WorkflowConstants.ACTION_PUBLISH));
	}

	protected Function<String, ServiceContext> createServiceContextFunction() {
		return new Function<String, ServiceContext>() {

			@Override
			public ServiceContext apply(String className) {
				return new ServiceContext();
			}

		};
	}

	protected Function<String, ServiceContext> createServiceContextFunction(
		final int workflowAction) {

		return new Function<String, ServiceContext>() {

			@Override
			public ServiceContext apply(String className) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setWorkflowAction(workflowAction);

				return serviceContext;
			}

		};
	}

	protected Function<String, ServiceContext> createServiceContextFunction(
		final long companyId) {

		return new Function<String, ServiceContext>() {

			@Override
			public ServiceContext apply(String className) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCompanyId(companyId);

				return serviceContext;
			}

		};
	}

	protected List<CommentJSONWS> getAllComments(
		DiscussionCommentIterator threadDiscussionCommentIterator) {

		List<CommentJSONWS> commentJSONWSs = new ArrayList<>();

		while (threadDiscussionCommentIterator.hasNext()) {
			CommentJSONWS commentJSONWS = new CommentJSONWS(
				threadDiscussionCommentIterator.next());

			commentJSONWSs.add(commentJSONWS);
		}

		return commentJSONWSs;
	}

	protected long getCompanyId(long groupId) throws PortalException {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	protected String getUserName() throws PortalException {
		User user = getUser();

		return user.getFullName();
	}

	@Reference(unbind = "-")
	protected void setCommentManager(CommentManager commentManager) {
		_commentManager = commentManager;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private volatile CommentManager _commentManager;
	private volatile GroupLocalService _groupLocalService;

}
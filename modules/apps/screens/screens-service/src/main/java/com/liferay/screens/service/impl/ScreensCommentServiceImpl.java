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

package com.liferay.screens.service.impl;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionCommentIterator;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.screens.service.base.ScreensCommentServiceBaseImpl;

import java.util.Locale;

/**
 * @author Alejandro Hernández Malillos
 */
public class ScreensCommentServiceImpl extends ScreensCommentServiceBaseImpl {

	@Override
	public JSONObject addComment(String className, long classPK, String body)
		throws PortalException {

		DiscussionPermission discussionPermission =
			commentManager.getDiscussionPermission(getPermissionChecker());

		long groupId = getGroupId(className, classPK);

		long companyId = getCompanyId(groupId);

		discussionPermission.checkAddPermission(
			companyId, groupId, className, classPK);

		long commentId = commentManager.addComment(
			getUserId(), groupId, className, classPK, getUser().getFullName(),
			StringPool.BLANK, body, createServiceContextFunction());

		Comment comment = commentManager.fetchComment(commentId);

		return getJSONObject(comment, discussionPermission);
	}

	@Override
	public JSONObject getComment(long commentId) throws PortalException {
		DiscussionPermission discussionPermission =
			commentManager.getDiscussionPermission(getPermissionChecker());

		Comment comment = commentManager.fetchComment(commentId);

		long groupId = getGroupId(comment.getClassName(), comment.getClassPK());

		long companyId = getCompanyId(groupId);

		discussionPermission.checkViewPermission(
			companyId, groupId, comment.getClassName(), comment.getClassPK());

		return getJSONObject(comment, discussionPermission);
	}

	@Override
	public JSONArray getComments(
			String className, long classPK, int start, int end)
		throws PortalException {

		DiscussionPermission discussionPermission =
			commentManager.getDiscussionPermission(getPermissionChecker());

		long groupId = getGroupId(className, classPK);

		long companyId = getCompanyId(groupId);

		discussionPermission.checkViewPermission(
			companyId, groupId, className, classPK);

		Discussion discussion = commentManager.getDiscussion(
			getUserId(), groupId, className, classPK,
			createServiceContextFunction());

		DiscussionComment rootDiscussionComment =
			discussion.getRootDiscussionComment();

		if (start == QueryUtil.ALL_POS) {
			start = 0;
		}

		DiscussionCommentIterator threadDiscussionCommentIterator =
			rootDiscussionComment.getThreadDiscussionCommentIterator(start);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (end == QueryUtil.ALL_POS) {
			while (threadDiscussionCommentIterator.hasNext()) {
				JSONObject jsonObject = getJSONObject
					(threadDiscussionCommentIterator.next(), discussionPermission);

				jsonArray.put(jsonObject);
			}
		} else {
			int commentsCount = end - start;

			while (threadDiscussionCommentIterator.hasNext() &&
				   (commentsCount > 0)) {

				JSONObject jsonObject = getJSONObject(

						threadDiscussionCommentIterator.next(),
						discussionPermission);

				jsonArray.put(jsonObject);

				commentsCount--;
			}
		}

		return jsonArray;
	}

	@Override
	public int getCommentsCount(String className, long classPK)
		throws PortalException {

		DiscussionPermission discussionPermission =
			commentManager.getDiscussionPermission(getPermissionChecker());

		long groupId = getGroupId(className, classPK);

		long companyId = getCompanyId(groupId);

		discussionPermission.checkViewPermission(
			companyId, groupId, className, classPK);

		return commentManager.getCommentsCount(className, classPK);
	}

	@Override
	public JSONObject updateComment(long commentId, String body)
		throws PortalException {

		DiscussionPermission discussionPermission =
			commentManager.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkUpdatePermission(commentId);

		Comment comment = commentManager.fetchComment(commentId);

		commentManager.updateComment(
			getUserId(), comment.getClassName(), comment.getClassPK(),
			commentId, StringPool.BLANK, body,
			createServiceContextFunction(WorkflowConstants.ACTION_PUBLISH));

		comment = commentManager.fetchComment(commentId);

		return getJSONObject(comment, discussionPermission);
	}

	protected Function<String, ServiceContext> createServiceContextFunction() {
		return (className) -> new ServiceContext();
	}

	protected Function<String, ServiceContext> createServiceContextFunction(
		int workflowAction) {

		return (className) -> {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setWorkflowAction(workflowAction);

			return serviceContext;
		};
	}

	protected JSONObject getJSONObject(
			Comment comment, DiscussionPermission discussionPermission)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("body", comment.getBody());
		jsonObject.put(
			"commentId",
			new Long(comment.getCommentId()));
		jsonObject.put(
			"createDate",
			new Long(comment.getCreateDate().getTime()));
		jsonObject.put(
			"deletePermission",
			discussionPermission.hasDeletePermission(comment.getCommentId()));
		jsonObject.put(
			"modifiedDate",
			new Long(comment.getModifiedDate().getTime()));
		jsonObject.put(
			"updatePermission",
			discussionPermission.hasUpdatePermission(comment.getCommentId()));
		jsonObject.put(
			"userId",
			new Long(comment.getUserId()));
		jsonObject.put("userName", comment.getUserName());

		return jsonObject;
	}

	@ServiceReference(type = CommentManager.class)
	protected CommentManager commentManager;

	@ServiceReference(type = GroupLocalService.class)
	protected GroupLocalService groupLocalService;

	private long getCompanyId(long groupId) throws PortalException {
		Group group = groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	private long getGroupId(String className, long classPK)
		throws PortalException {

		JSONObject assetEntry = screensAssetEntryService.getAssetEntry(
			className, classPK, Locale.getDefault());

		return assetEntry.getLong("groupId");
	}

}
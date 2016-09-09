/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.screens.service.impl;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
public class ScreensCommentServiceImpl
	extends ScreensCommentServiceBaseImpl {

	@Override
	public JSONObject addComment(
			String className, long classPK, String body)
			throws PortalException {

		JSONObject assetEntry =
			screensAssetEntryService.getAssetEntry(className, classPK,
				Locale.getDefault());

		long groupId = assetEntry.getLong("groupId");

		DiscussionPermission discussionPermission =
			commentManager.getDiscussionPermission(getPermissionChecker());

		long companyId = groupLocalService.getGroup(groupId).getCompanyId();

		discussionPermission.checkAddPermission(
			companyId, groupId, className, classPK);

		long commentId = commentManager.addComment(
			getUserId(), groupId, className, classPK, getUser().getFullName(),
			StringPool.BLANK, body, createServiceContextFunction());

		JSONObject jsonObject = getJSONObject(
			commentManager.fetchComment(commentId), discussionPermission);

		return jsonObject;
	}

	@Override
	public JSONObject updateComment(
		long commentId, String body)
		throws PortalException {

		Comment comment = commentManager.fetchComment(commentId);

		String className = comment.getClassName();
		long classPK = comment.getClassPK();

		DiscussionPermission discussionPermission =
			commentManager.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkUpdatePermission(commentId);

		commentManager.updateComment(
			getUserId(), className, classPK, commentId, StringPool.BLANK, body,
			createServiceContextFunction(WorkflowConstants.ACTION_PUBLISH));

		return getJSONObject(
			commentManager.fetchComment(commentId), discussionPermission);
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

	protected JSONObject getJSONObject(
			Comment comment, DiscussionPermission discussionPermission)
			throws PortalException {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("body", comment.getBody());
		jsonObject.put("commentId", comment.getCommentId());
		jsonObject.put("createDate", comment.getCreateDate().getTime());
		jsonObject.put("modifiedDate", comment.getModifiedDate().getTime());
		jsonObject.put("userId", comment.getUserId());
		jsonObject.put("userName", comment.getUserName());
		jsonObject.put("updatePermission",
			discussionPermission.hasUpdatePermission(comment.getCommentId()));
		jsonObject.put("deletePermission",
			discussionPermission.hasDeletePermission(comment.getCommentId()));

		return jsonObject;
	}

	@ServiceReference(type = CommentManager.class)
	protected CommentManager commentManager;

	@ServiceReference(type = GroupLocalService.class)
	protected GroupLocalService groupLocalService;
}
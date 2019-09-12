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

package com.liferay.layout.content.page.editor.web.internal.comment;

import com.liferay.layout.content.page.editor.web.internal.workflow.WorkflowUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.function.Function;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class CommentUtil {

	public static JSONObject getCommentJSONObject(
			Comment comment, HttpServletRequest httpServletRequest)
		throws PortalException {

		User commentUser = comment.getUser();

		String portraitURL = StringPool.BLANK;

		if (commentUser.getPortraitId() > 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			portraitURL = commentUser.getPortraitURL(themeDisplay);
		}

		Date createDate = comment.getCreateDate();

		String dateDescription = LanguageUtil.format(
			httpServletRequest, "x-ago",
			LanguageUtil.getTimeDescription(
				httpServletRequest,
				System.currentTimeMillis() - createDate.getTime(), true));

		Date modifiedDate = comment.getModifiedDate();

		String modifiedDateDescription = LanguageUtil.format(
			httpServletRequest, "x-ago",
			LanguageUtil.getTimeDescription(
				httpServletRequest,
				System.currentTimeMillis() - modifiedDate.getTime(), true));

		return JSONUtil.put(
			"author",
			JSONUtil.put(
				"fullName", commentUser.getFullName()
			).put(
				"portraitURL", portraitURL
			).put(
				"userId", commentUser.getUserId()
			)
		).put(
			"body", comment.getBody()
		).put(
			"commentId", comment.getCommentId()
		).put(
			"dateDescription", dateDescription
		).put(
			"edited", !createDate.equals(modifiedDate)
		).put(
			"modifiedDateDescription", modifiedDateDescription
		).put(
			"resolved", _isResolved(comment)
		);
	}

	public static Function<String, ServiceContext> getServiceContextFunction(
			ActionRequest actionRequest, ThemeDisplay themeDisplay)
		throws PortalException {

		Function<String, ServiceContext> serviceContextFunction =
			WorkflowUtil.getServiceContextFunction(
				_getWorkflowAction(actionRequest), actionRequest);

		String notificationRedirect = HttpUtil.setParameter(
			PortalUtil.getLayoutFullURL(themeDisplay), "p_l_mode",
			Constants.EDIT);

		serviceContextFunction = serviceContextFunction.andThen(
			serviceContext -> {
				serviceContext.setAttribute("contentURL", notificationRedirect);
				serviceContext.setAttribute("namespace", StringPool.BLANK);

				return serviceContext;
			});

		return serviceContextFunction;
	}

	private static int _getWorkflowAction(ActionRequest actionRequest) {
		boolean resolved = ParamUtil.getBoolean(actionRequest, "resolved");

		if (resolved) {
			return WorkflowConstants.ACTION_SAVE_DRAFT;
		}

		return WorkflowConstants.ACTION_PUBLISH;
	}

	private static boolean _isResolved(Comment comment) {
		if (comment instanceof WorkflowableComment) {
			WorkflowableComment workflowableComment =
				(WorkflowableComment)comment;

			if (workflowableComment.getStatus() ==
					WorkflowConstants.STATUS_DRAFT) {

				return true;
			}
		}

		return false;
	}

}
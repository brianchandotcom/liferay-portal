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

package com.liferay.comment.taglib.internal.action;

import com.liferay.message.boards.kernel.exception.DiscussionMaxCommentsException;
import com.liferay.message.boards.kernel.exception.MessageBodyException;
import com.liferay.message.boards.kernel.exception.NoSuchMessageException;
import com.liferay.message.boards.kernel.exception.RequiredMessageException;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFunction;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.servlet.NamespaceServletRequest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, property = "path=/portal/comment/discussion/edit",
	service = StrutsAction.class
)
public class EditDiscussionStrutsAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String namespace = ParamUtil.getString(request, "namespace");

		HttpServletRequest namespacedRequest = new NamespaceServletRequest(
			request, StringPool.BLANK, namespace);

		String cmd = ParamUtil.getString(namespacedRequest, Constants.CMD);

		try {
			String redirect = _portal.escapeRedirect(
				ParamUtil.getString(request, "redirect"));

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				long commentId = updateComment(namespacedRequest);

				boolean ajax = ParamUtil.getBoolean(request, "ajax", true);

				if (ajax) {
					String randomNamespace = ParamUtil.getString(
						namespacedRequest, "randomNamespace");

					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

					jsonObject.put("commentId", commentId);
					jsonObject.put("randomNamespace", randomNamespace);

					writeJSON(namespacedRequest, response, jsonObject);

					return null;
				}
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteComment(namespacedRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE_TO_COMMENTS)) {
				subscribeToComments(namespacedRequest, true);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE_FROM_COMMENTS)) {
				subscribeToComments(namespacedRequest, false);
			}

			if (Validator.isNotNull(redirect)) {
				response.sendRedirect(redirect);
			}
		}
		catch (DiscussionMaxCommentsException | MessageBodyException |
			   NoSuchMessageException | PrincipalException |
			   RequiredMessageException e) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.putException(e);

			writeJSON(namespacedRequest, response, jsonObject);
		}

		return null;
	}

	protected void deleteComment(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long commentId = ParamUtil.getLong(request, "commentId");

		DiscussionPermission discussionPermission = _getDiscussionPermission(
			themeDisplay);

		discussionPermission.checkDeletePermission(commentId);

		_commentManager.deleteComment(commentId);
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected void subscribeToComments(
			HttpServletRequest request, boolean subscribe)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String className = ParamUtil.getString(request, "className");
		long classPK = ParamUtil.getLong(request, "classPK");

		if (subscribe) {
			_commentManager.subscribeDiscussion(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				className, classPK);
		}
		else {
			_commentManager.unsubscribeDiscussion(
				themeDisplay.getUserId(), className, classPK);
		}
	}

	protected long updateComment(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long commentId = ParamUtil.getLong(request, "commentId");

		String className = ParamUtil.getString(request, "className");
		long classPK = ParamUtil.getLong(request, "classPK");
		long parentCommentId = ParamUtil.getLong(request, "parentCommentId");
		String subject = ParamUtil.getString(request, "subject");
		String body = ParamUtil.getString(request, "body");

		Function<String, ServiceContext> serviceContextFunction =
			new ServiceContextFunction(request);

		DiscussionPermission discussionPermission = _getDiscussionPermission(
			themeDisplay);

		if (commentId <= 0) {

			// Add message

			User user = null;

			if (themeDisplay.isSignedIn()) {
				user = themeDisplay.getUser();
			}
			else {
				String emailAddress = ParamUtil.getString(
					request, "emailAddress");

				user = _userLocalService.fetchUserByEmailAddress(
					themeDisplay.getCompanyId(), emailAddress);

				if ((user == null) ||
					(user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE)) {

					return 0;
				}
			}

			String name = PrincipalThreadLocal.getName();

			PrincipalThreadLocal.setName(user.getUserId());

			try {
				discussionPermission.checkAddPermission(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					className, classPK);

				commentId = _commentManager.addComment(
					user.getUserId(), className, classPK, user.getFullName(),
					parentCommentId, subject, body, serviceContextFunction);
			}
			finally {
				PrincipalThreadLocal.setName(name);
			}
		}
		else {

			// Update message

			if (Validator.isNull(className) || (classPK == 0)) {
				Comment comment = _commentManager.fetchComment(commentId);

				if (comment != null) {
					className = comment.getClassName();
					classPK = comment.getClassPK();
				}
			}

			discussionPermission.checkUpdatePermission(commentId);

			commentId = _commentManager.updateComment(
				themeDisplay.getUserId(), className, classPK, commentId,
				subject, body, serviceContextFunction);
		}

		// Subscription

		boolean subscribe = ParamUtil.getBoolean(request, "subscribe");

		if (subscribe) {
			_commentManager.subscribeDiscussion(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				className, classPK);
		}

		return commentId;
	}

	protected void writeJSON(
			HttpServletRequest request, HttpServletResponse response,
			Object jsonObj)
		throws IOException {

		response.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(response, jsonObj.toString());

		response.flushBuffer();
	}

	private DiscussionPermission _getDiscussionPermission(
			ThemeDisplay themeDisplay)
		throws PrincipalException {

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(
				themeDisplay.getPermissionChecker());

		if (discussionPermission == null) {
			throw new PrincipalException("Discussion permission is null");
		}

		return discussionPermission;
	}

	@Reference
	private CommentManager _commentManager;

	@Reference
	private Portal _portal;

	private UserLocalService _userLocalService;

}
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

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.kernel.comments.Comment;
import com.liferay.portal.kernel.comments.CommentManagerUtil;
import com.liferay.portal.kernel.comments.DiscussionPermission;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFunction;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messageboards.DiscussionMaxCommentsException;
import com.liferay.portlet.messageboards.MessageBodyException;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.RequiredMessageException;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 */
public class EditDiscussionAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			String redirect = PortalUtil.escapeRedirect(
				ParamUtil.getString(actionRequest, "redirect"));

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				long commentId = updateComment(actionRequest);

				boolean ajax = ParamUtil.getBoolean(actionRequest, "ajax");

				if (ajax) {
					String randomNamespace = ParamUtil.getString(
						actionRequest, "randomNamespace");

					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

					jsonObject.put("commentId", commentId);
					jsonObject.put("randomNamespace", randomNamespace);

					writeJSON(actionRequest, actionResponse, jsonObject);

					return;
				}
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteComment(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE_TO_COMMENTS)) {
				subscribeToComments(actionRequest, true);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE_FROM_COMMENTS)) {
				subscribeToComments(actionRequest, false);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (DiscussionMaxCommentsException | MessageBodyException |
				NoSuchMessageException | PrincipalException |
				RequiredMessageException e) {

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.putException(e);

				writeJSON(actionRequest, actionResponse, jsonObject);
		}
	}

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String className = ParamUtil.getString(resourceRequest, "className");
		long classPK = ParamUtil.getLong(resourceRequest, "classPK");
		boolean hideControls = ParamUtil.getBoolean(
			resourceRequest, "hideControls");
		boolean ratingsEnabled = ParamUtil.getBoolean(
			resourceRequest, "ratingsEnabled");
		long userId = ParamUtil.getLong(resourceRequest, "userId");

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);

		request.setAttribute("liferay-ui:discussion:className", className);
		request.setAttribute(
			"liferay-ui:discussion:classPK", String.valueOf(classPK));
		request.setAttribute(
			"liferay-ui:discussion:hideControls", String.valueOf(hideControls));
		request.setAttribute(
			"liferay-ui:discussion:ratingsEnabled",
			String.valueOf(ratingsEnabled));
		request.setAttribute(
			"liferay-ui:discussion:userId", String.valueOf(userId));

		int index = ParamUtil.getInteger(resourceRequest, "index");

		request.setAttribute(
			"liferay-ui:discussion:index", String.valueOf(index));

		String randomNamespace = ParamUtil.getString(
			resourceRequest, "randomNamespace");

		request.setAttribute(
			"liferay-ui:discussion:randomNamespace", randomNamespace);

		int rootIndexPage = ParamUtil.getInteger(
			resourceRequest, "rootIndexPage");

		request.setAttribute(
			"liferay-ui:discussion:rootIndexPage",
			String.valueOf(rootIndexPage));

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/taglib/ui/discussion/page_resources.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected void deleteComment(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long commentId = ParamUtil.getLong(actionRequest, "commentId");

		DiscussionPermission discussionPermission =
			CommentManagerUtil.getDiscussionPermission(
				themeDisplay.getPermissionChecker());

		discussionPermission.checkDeletePermission(commentId);

		CommentManagerUtil.deleteComment(commentId);
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void subscribeToComments(
			ActionRequest actionRequest, boolean subscribe)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		if (subscribe) {
			CommentManagerUtil.subscribeDiscussion(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				className, classPK);
		}
		else {
			CommentManagerUtil.unsubscribeDiscussion(
				themeDisplay.getUserId(), className, classPK);
		}
	}

	protected long updateComment(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long commentId = ParamUtil.getLong(actionRequest, "commentId");

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		long parentCommentId = ParamUtil.getLong(
			actionRequest, "parentCommentId");
		String subject = ParamUtil.getString(actionRequest, "subject");
		String body = ParamUtil.getString(actionRequest, "body");

		Function<String, ServiceContext> serviceContextFunction =
			new ServiceContextFunction(actionRequest);

		DiscussionPermission discussionPermission =
			CommentManagerUtil.getDiscussionPermission(
				themeDisplay.getPermissionChecker());

		if (commentId <= 0) {

			// Add message

			User user = null;

			if (themeDisplay.isSignedIn()) {
				user = themeDisplay.getUser();
			}
			else {
				String emailAddress = ParamUtil.getString(
					actionRequest, "emailAddress");

				user = UserLocalServiceUtil.fetchUserByEmailAddress(
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

				commentId = CommentManagerUtil.addComment(
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
				Comment comment = CommentManagerUtil.fetchComment(commentId);

				if (comment != null) {
					className = comment.getClassName();
					classPK = comment.getClassPK();
				}
			}

			discussionPermission.checkUpdatePermission(commentId);

			commentId = CommentManagerUtil.updateComment(
				themeDisplay.getUserId(), className, classPK, commentId,
				subject, body, serviceContextFunction);
		}

		// Subscription

		boolean subscribe = ParamUtil.getBoolean(actionRequest, "subscribe");

		if (subscribe) {
			CommentManagerUtil.subscribeDiscussion(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				className, classPK);
		}

		return commentId;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}
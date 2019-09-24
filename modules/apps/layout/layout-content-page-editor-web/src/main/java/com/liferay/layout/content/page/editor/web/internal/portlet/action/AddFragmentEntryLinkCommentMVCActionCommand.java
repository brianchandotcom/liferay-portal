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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.comment.CommentUtil;
import com.liferay.layout.content.page.editor.web.internal.workflow.WorkflowUtil;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.function.Function;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/add_fragment_entry_link_comment"
	},
	service = MVCActionCommand.class
)
public class AddFragmentEntryLinkCommentMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		LayoutPermissionUtil.check(
			themeDisplay.getPermissionChecker(), layout, ActionKeys.UPDATE);

		User user = themeDisplay.getUser();

		long fragmentEntryLinkId = ParamUtil.getLong(
			actionRequest, "fragmentEntryLinkId");
		long parentCommentId = ParamUtil.getLong(
			actionRequest, "parentCommentId");

		WorkflowUtil.withoutWorkflow(
			() -> {
				_commentManager.subscribeDiscussion(
					user.getUserId(), themeDisplay.getScopeGroupId(),
					FragmentEntryLink.class.getName(), fragmentEntryLinkId);

				long commentId = 0;

				Function<String, ServiceContext> serviceContextFunction =
					CommentUtil.getServiceContextFunction(
						actionRequest, themeDisplay);

				if (parentCommentId == 0) {
					_commentManager.subscribeDiscussion(
						layout.getUserId(), themeDisplay.getScopeGroupId(),
						FragmentEntryLink.class.getName(), fragmentEntryLinkId);

					commentId = _commentManager.addComment(
						themeDisplay.getUserId(),
						themeDisplay.getScopeGroupId(),
						FragmentEntryLink.class.getName(), fragmentEntryLinkId,
						user.getFullName(), String.valueOf(Math.random()),
						ParamUtil.getString(actionRequest, "body"),
						serviceContextFunction);
				}
				else {
					commentId = _commentManager.addComment(
						themeDisplay.getUserId(),
						FragmentEntryLink.class.getName(), fragmentEntryLinkId,
						user.getFullName(), parentCommentId,
						String.valueOf(Math.random()),
						ParamUtil.getString(actionRequest, "body"),
						serviceContextFunction);
				}

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse,
					CommentUtil.getCommentJSONObject(
						_commentManager.fetchComment(commentId),
						_portal.getHttpServletRequest(actionRequest)));
			});
	}

	@Reference
	private CommentManager _commentManager;

	@Reference
	private Portal _portal;

}
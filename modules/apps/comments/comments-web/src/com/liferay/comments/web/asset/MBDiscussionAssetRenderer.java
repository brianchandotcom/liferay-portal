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

package com.liferay.comments.web.asset;

import com.liferay.comments.web.constants.CommentsPortletKeys;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseJSPAssetRenderer;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jorge Ferrer
 * @author Sergio González
 */
public class MBDiscussionAssetRenderer
	extends BaseJSPAssetRenderer implements TrashRenderer {

	public MBDiscussionAssetRenderer(WorkflowableComment workflowableComment) {
		_workflowableComment = workflowableComment;
	}

	@Override
	public String getClassName() {
		return _workflowableComment.getModelClassName();
	}

	@Override
	public long getClassPK() {
		return _workflowableComment.getCommentId();
	}

	@Override
	public Date getDisplayDate() {
		return _workflowableComment.getModifiedDate();
	}

	@Override
	public long getGroupId() {
		return _workflowableComment.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/comments/asset/discussion_" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public String getSearchSummary(Locale locale) {
		return HtmlUtil.extractText(
			_workflowableComment.getTranslatedBody(StringPool.BLANK));
	}

	@Override
	public int getStatus() {
		return _workflowableComment.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return _workflowableComment.getBody();
	}

	@Override
	public String getThumbnailPath(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathThemeImages() +
			"/file_system/large/message.png";
	}

	@Override
	public String getTitle(Locale locale) {
		return StringUtil.shorten(getSearchSummary(locale));
	}

	@Override
	public String getType() {
		return MBDiscussionAssetRendererFactory.TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		HttpServletRequest request =
			liferayPortletRequest.getHttpServletRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL editPortletURL = PortletURLFactoryUtil.create(
			request, CommentsPortletKeys.COMMENTS,
			getControlPanelPlid(themeDisplay), PortletRequest.RENDER_PHASE);

		editPortletURL.setParameter("mvcPath", "/comments/edit_discussion.jsp");
		editPortletURL.setParameter(
			"commentId", String.valueOf(_workflowableComment.getCommentId()));

		return editPortletURL;
	}

	@Override
	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		PortletURL portletURL = assetRendererFactory.getURLView(
			liferayPortletResponse, windowState);

		portletURL.setParameter(
			"struts_action", "/message_boards/view_message");
		portletURL.setParameter(
			"messageId", String.valueOf(_workflowableComment.getCommentId()));
		portletURL.setWindowState(windowState);

		return portletURL;
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return null;
	}

	@Override
	public long getUserId() {
		return _workflowableComment.getUserId();
	}

	@Override
	public String getUserName() {
		return _workflowableComment.getUserName();
	}

	@Override
	public String getUuid() {
		return _workflowableComment.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		DiscussionPermission discussionPermission =
			CommentManagerUtil.getDiscussionPermission(permissionChecker);

		return discussionPermission.hasUpdatePermission(
			_workflowableComment.getCommentId());
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		DiscussionPermission discussionPermission =
			CommentManagerUtil.getDiscussionPermission(permissionChecker);

		return discussionPermission.hasPermission(
			_workflowableComment.getCommentId(), ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		Comment comment = CommentManagerUtil.fetchComment(
			_workflowableComment.getCommentId());

		request.setAttribute(WebKeys.COMMENT, comment);

		return super.include(request, response, template);
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/message.png";
	}

	private final WorkflowableComment _workflowableComment;

}
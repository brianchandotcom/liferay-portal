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

package com.liferay.comment.web.internal.asset.model;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.comment.web.internal.constants.CommentPortletKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

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
public class CommentAssetRenderer
	extends BaseJSPAssetRenderer<WorkflowableComment> implements TrashRenderer {

	public CommentAssetRenderer(
		WorkflowableComment workflowableComment,
		AssetRendererFactory<WorkflowableComment> assetRendererFactory) {

		_workflowableComment = workflowableComment;
		_assetRendererFactory = assetRendererFactory;
	}

	@Override
	public WorkflowableComment getAssetObject() {
		return _workflowableComment;
	}

	@Override
	public AssetRendererFactory<WorkflowableComment> getAssetRendererFactory() {
		return _assetRendererFactory;
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
	public long getGroupId() {
		return _workflowableComment.getGroupId();
	}

	@Override
	public String getJspPath(
		HttpServletRequest httpServletRequest, String template) {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/discussion_" + template + ".jsp";
		}

		return null;
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory<WorkflowableComment> assetRendererFactory =
			getAssetRendererFactory();

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
	public String getTitle(Locale locale) {
		return StringUtil.shorten(getSearchSummary(locale));
	}

	@Override
	public String getType() {
		return CommentAssetRendererFactory.TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(
			_workflowableComment.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				liferayPortletRequest, group, CommentPortletKeys.COMMENT, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/comment/edit_discussion"
		).setParameter(
			"commentId", _workflowableComment.getCommentId()
		).buildPortletURL();
	}

	@Override
	public String getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		AssetRendererFactory<WorkflowableComment> assetRendererFactory =
			getAssetRendererFactory();

		return PortletURLBuilder.create(
			assetRendererFactory.getURLView(liferayPortletResponse, windowState)
		).setMVCPath(
			"/view_comment.jsp"
		).setParameter(
			"commentId", _workflowableComment.getCommentId()
		).setWindowState(
			windowState
		).buildString();
	}

	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_workflowableComment.getClassName());

		if (assetRendererFactory == null) {
			return null;
		}

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			_workflowableComment.getClassPK());

		return assetRenderer.getURLViewInContext(
			liferayPortletRequest, liferayPortletResponse, noSuchEntryRedirect);
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
			_workflowableComment, ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		Comment comment = CommentManagerUtil.fetchComment(
			_workflowableComment.getCommentId());

		httpServletRequest.setAttribute(WebKeys.COMMENT, comment);

		return super.include(httpServletRequest, httpServletResponse, template);
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	private final AssetRendererFactory<WorkflowableComment>
		_assetRendererFactory;
	private final WorkflowableComment _workflowableComment;

}
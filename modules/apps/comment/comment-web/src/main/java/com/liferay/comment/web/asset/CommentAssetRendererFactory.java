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

package com.liferay.comment.web.asset;

import com.liferay.comment.web.constants.CommentPortletKeys;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentConstants;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + CommentPortletKeys.COMMENT},
	service = AssetRendererFactory.class
)
public class CommentAssetRendererFactory
	extends BaseAssetRendererFactory<WorkflowableComment> {

	public static final String TYPE = "discussion";

	public CommentAssetRendererFactory() {
		setCategorizable(false);
		setClassName(CommentConstants.getDiscussionClassName());
		setLinkable(true);
		setPortletId(CommentPortletKeys.COMMENT);
	}

	@Override
	public AssetRenderer<WorkflowableComment> getAssetRenderer(
			long classPK, int type)
		throws PortalException {

		Comment comment = CommentManagerUtil.fetchComment(classPK);

		if (!(comment instanceof WorkflowableComment)) {
			return null;
		}

		WorkflowableComment workflowableComment = (WorkflowableComment)comment;

		CommentAssetRenderer commentAssetRenderer = new CommentAssetRenderer(
			workflowableComment);

		commentAssetRenderer.setAssetRendererType(type);
		commentAssetRenderer.setServletContext(_servletContext);

		return commentAssetRenderer;
	}

	@Override
	public String getIconCssClass() {
		return "icon-comments";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLView(
		LiferayPortletResponse liferayPortletResponse,
		WindowState windowState) {

		LiferayPortletURL liferayPortletURL =
			liferayPortletResponse.createLiferayPortletURL(
				CommentPortletKeys.COMMENT, PortletRequest.RENDER_PHASE);

		try {
			liferayPortletURL.setWindowState(windowState);
		}
		catch (WindowStateException wse) {
		}

		return liferayPortletURL;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		DiscussionPermission discussionPermission =
			CommentManagerUtil.getDiscussionPermission(permissionChecker);

		return discussionPermission.hasPermission(classPK, actionId);
	}

	@Override
	public boolean isSelectable() {
		return _SELECTABLE;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/conversation.png";
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.comment.web)", unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final boolean _SELECTABLE = false;

	private volatile ServletContext _servletContext;

}
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

package com.liferay.blogs.web.internal.portlet.action;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.web.internal.display.context.BlogEntriesDisplayContext;
import com.liferay.blogs.web.internal.display.context.BlogImagesDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
		"mvc.command.name=/", "mvc.command.name=/blogs/search",
		"mvc.command.name=/blogs/view"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		if (Objects.equals(
				_getPortletId(renderRequest), BlogsPortletKeys.BLOGS)) {

			return "/blogs/view.jsp";
		}

		renderRequest.setAttribute(
			BlogEntriesDisplayContext.class.getName(),
			new BlogEntriesDisplayContext(
				_htmlParser, _portal, renderRequest, renderResponse,
				_trashHelper));

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		renderRequest.setAttribute(
			BlogImagesDisplayContext.class.getName(),
			new BlogImagesDisplayContext(
				_portal.getLiferayPortletRequest(renderRequest),
				new BlogsFileEntryModelResourcePermission(themeDisplay)));

		return "/blogs_admin/view.jsp";
	}

	private String _getPortletId(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getPortletName();
	}

	@Reference
	private HtmlParser _htmlParser;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private ModelResourcePermission<FileEntry> _modelResourcePermission;

	@Reference
	private Portal _portal;

	@Reference(target = "(resource.name=" + BlogsConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private TrashHelper _trashHelper;

	private class BlogsFileEntryModelResourcePermission
		implements ModelResourcePermission<FileEntry> {

		public BlogsFileEntryModelResourcePermission(
			ThemeDisplay themeDisplay) {

			_themeDisplay = themeDisplay;
		}

		@Override
		public void check(
				PermissionChecker permissionChecker, FileEntry fileEntry,
				String actionId)
			throws PortalException {

			if (_isActionDelegable(actionId)) {
				_portletResourcePermission.check(
					permissionChecker, _themeDisplay.getSiteGroupId(),
					ActionKeys.ADD_ENTRY);
			}
			else {
				_modelResourcePermission.check(
					permissionChecker, fileEntry, actionId);
			}
		}

		@Override
		public void check(
				PermissionChecker permissionChecker, long primaryKey,
				String actionId)
			throws PortalException {

			if (_isActionDelegable(actionId)) {
				_portletResourcePermission.check(
					permissionChecker, _themeDisplay.getSiteGroupId(),
					ActionKeys.ADD_ENTRY);
			}
			else {
				_modelResourcePermission.check(
					permissionChecker, primaryKey, actionId);
			}
		}

		@Override
		public boolean contains(
				PermissionChecker permissionChecker, FileEntry fileEntry,
				String actionId)
			throws PortalException {

			if (_isActionDelegable(actionId)) {
				return _portletResourcePermission.contains(
					permissionChecker, fileEntry.getGroupId(),
					ActionKeys.ADD_ENTRY);
			}

			return _modelResourcePermission.contains(
				permissionChecker, fileEntry, actionId);
		}

		@Override
		public boolean contains(
				PermissionChecker permissionChecker, long primaryKey,
				String actionId)
			throws PortalException {

			if (_isActionDelegable(actionId)) {
				return _portletResourcePermission.contains(
					permissionChecker, _themeDisplay.getSiteGroupId(),
					ActionKeys.ADD_ENTRY);
			}

			return _modelResourcePermission.contains(
				permissionChecker, primaryKey, actionId);
		}

		@Override
		public String getModelName() {
			return _modelResourcePermission.getModelName();
		}

		@Override
		public PortletResourcePermission getPortletResourcePermission() {
			return _modelResourcePermission.getPortletResourcePermission();
		}

		private boolean _isActionDelegable(String actionId) {
			if (actionId.equals(ActionKeys.DELETE) ||
				actionId.equals(ActionKeys.UPDATE)) {

				return true;
			}

			return false;
		}

		private final ThemeDisplay _themeDisplay;

	}

}
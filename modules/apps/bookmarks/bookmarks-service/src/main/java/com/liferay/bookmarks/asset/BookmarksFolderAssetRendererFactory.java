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

package com.liferay.bookmarks.asset;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.bookmarks.service.permission.BookmarksFolderPermissionChecker;
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
 * @author Alexander Chow
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + BookmarksPortletKeys.BOOKMARKS},
	service = AssetRendererFactory.class
)
public class BookmarksFolderAssetRendererFactory
	extends BaseAssetRendererFactory<BookmarksFolder> {

	public static final String TYPE = "bookmark_folder";

	public BookmarksFolderAssetRendererFactory() {
		setCategorizable(false);
		setClassName(BookmarksFolder.class.getName());
		setPortletId(BookmarksPortletKeys.BOOKMARKS);
		setSearchable(true);
	}

	@Override
	public AssetRenderer<BookmarksFolder> getAssetRenderer(
			long classPK, int type)
		throws PortalException {

		BookmarksFolder folder = _bookmarksFolderLocalService.getFolder(
			classPK);

		BookmarksFolderAssetRenderer bookmarksFolderAssetRenderer =
			new BookmarksFolderAssetRenderer(folder);

		bookmarksFolderAssetRenderer.setAssetRendererType(type);
		bookmarksFolderAssetRenderer.setServletContext(_servletContext);

		return bookmarksFolderAssetRenderer;
	}

	@Override
	public String getClassName() {
		return BookmarksFolder.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "icon-folder-close";
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
				BookmarksPortletKeys.BOOKMARKS, PortletRequest.RENDER_PHASE);

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

		BookmarksFolder folder = _bookmarksFolderLocalService.getFolder(
			classPK);

		return BookmarksFolderPermissionChecker.contains(
			permissionChecker, folder, actionId);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.bookmarks.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/folder.png";
	}

	@Reference(unbind = "-")
	protected void setBookmarksFolderLocalService(
		BookmarksFolderLocalService bookmarksFolderLocalService) {

		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	private volatile BookmarksFolderLocalService _bookmarksFolderLocalService;
	private volatile ServletContext _servletContext;

}
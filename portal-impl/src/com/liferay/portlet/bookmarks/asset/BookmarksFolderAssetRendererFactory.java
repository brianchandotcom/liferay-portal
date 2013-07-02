/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.permission.BookmarksFolderPermission;

/**
 * @author Alexander Chow
 */
public class BookmarksFolderAssetRendererFactory
	extends BaseAssetRendererFactory {

	public static final String TYPE = "bookmark_folder";

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException, SystemException {

		BookmarksFolder folder = BookmarksFolderLocalServiceUtil.getFolder(
			classPK);

		BookmarksFolderAssetRenderer bookmarksFolderAssetRenderer =
			new BookmarksFolderAssetRenderer(folder);

		bookmarksFolderAssetRenderer.setAssetRendererType(type);

		return bookmarksFolderAssetRenderer;
	}

	@Override
	public String getClassName() {
		return BookmarksFolder.class.getName();
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		BookmarksFolder folder = BookmarksFolderLocalServiceUtil.getFolder(
			classPK);

		return BookmarksFolderPermission.contains(
			permissionChecker, folder, actionId);
	}

	@Override
	public boolean isCategorizable() {
		return _CATEGORIZABLE;
	}

	@Override
	public boolean isLinkable() {
		return _LINKABLE;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/folder.png";
	}

	private static final boolean _CATEGORIZABLE = false;

	private static final boolean _LINKABLE = false;

}
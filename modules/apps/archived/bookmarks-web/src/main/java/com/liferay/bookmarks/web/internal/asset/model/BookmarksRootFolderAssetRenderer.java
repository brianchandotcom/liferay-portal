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

package com.liferay.bookmarks.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.trash.TrashRenderer;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín*
 */
public class BookmarksRootFolderAssetRenderer
	extends BaseJSPAssetRenderer<BookmarksFolder> implements TrashRenderer {

	public static final String TYPE = "bookmarks_folder";

	public BookmarksRootFolderAssetRenderer(Group group) {
		_group = group;
	}

	@Override
	public BookmarksFolder getAssetObject() {
		return null;
	}

	@Override
	public String getClassName() {
		return BookmarksFolder.class.getName();
	}

	@Override
	public long getClassPK() {
		return _group.getGroupId();
	}

	@Override
	public long getGroupId() {
		return _group.getGroupId();
	}

	@Override
	public String getJspPath(
		HttpServletRequest httpServletRequest, String template) {

		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			return "/bookmarks/asset/folder_" + template + ".jsp";
		}

		return null;
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory<BookmarksFolder> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return LanguageUtil.get(portletRequest.getLocale(), "bookmarks-home");
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "home");
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public long getUserId() {
		return _group.getCreatorUserId();
	}

	@Override
	public String getUserName() {
		return null;
	}

	@Override
	public String getUuid() {
		return null;
	}

	private final Group _group;

}
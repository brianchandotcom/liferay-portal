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
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.permission.BookmarksEntryPermission;
import com.liferay.portlet.bookmarks.service.permission.BookmarksPermission;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Raymond Augé
 * @author Sergio González
 */
public class BookmarksEntryAssetRendererFactory
	extends BaseAssetRendererFactory {

	public static final String TYPE = "bookmark";

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		BookmarksEntryAssetRenderer bookmarksEntryAssetRenderer =
			new BookmarksEntryAssetRenderer(entry);

		bookmarksEntryAssetRenderer.setAssetRendererType(type);

		return bookmarksEntryAssetRenderer;
	}

	@Override
	public String getClassName() {
		return BookmarksEntry.class.getName();
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!BookmarksPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY)) {

			return null;
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			liferayPortletRequest, PortletKeys.BOOKMARKS,
			getControlPanelPlid(themeDisplay), PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/bookmarks/edit_entry");
		portletURL.setParameter(
			"folderId",
			String.valueOf(
				AssetPublisherUtil.getRecentFolderId(
					liferayPortletRequest, getClassName())));

		return portletURL;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return BookmarksEntryPermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Override
	public boolean isLinkable() {
		return _LINKABLE;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/ratings/star_hover.png";
	}

	private static final boolean _LINKABLE = true;

}
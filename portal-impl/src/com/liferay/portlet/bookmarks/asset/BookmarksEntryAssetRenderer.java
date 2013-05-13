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

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.permission.BookmarksEntryPermission;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Sergio González
 */
public class BookmarksEntryAssetRenderer
	extends BaseAssetRenderer implements TrashRenderer {

	public BookmarksEntryAssetRenderer(BookmarksEntry entry) {
		_entry = entry;
	}

	@Override
	public String getClassName() {
		return BookmarksEntry.class.getName();
	}

	@Override
	public long getClassPK() {
		return _entry.getEntryId();
	}

	@Override
	public Date getDisplayDate() {
		return _entry.getModifiedDate();
	}

	@Override
	public long getGroupId() {
		return _entry.getGroupId();
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/ratings/star_hover.png";
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public String getSummary(Locale locale) {
		return HtmlUtil.stripHtml(_entry.getDescription());
	}

	@Override
	public String getThumbnailPath(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathThemeImages() +
			"/file_system/large/bookmark.png";
	}

	@Override
	public String getTitle(Locale locale) {
		return _entry.getName();
	}

	@Override
	public String getType() {
		return BookmarksEntryAssetRendererFactory.TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest), PortletKeys.BOOKMARKS,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/bookmarks/edit_entry");
		portletURL.setParameter(
			"folderId", String.valueOf(_entry.getFolderId()));
		portletURL.setParameter("entryId", String.valueOf(_entry.getEntryId()));

		return portletURL;
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return getURLViewInContext(
			liferayPortletRequest, noSuchEntryRedirect, "/bookmarks/find_entry",
			"entryId", _entry.getEntryId());
	}

	@Override
	public long getUserId() {
		return _entry.getUserId();
	}

	@Override
	public String getUserName() {
		return _entry.getUserName();
	}

	@Override
	public String getUuid() {
		return _entry.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		try {
			return BookmarksEntryPermission.contains(
				permissionChecker, _entry, ActionKeys.UPDATE);
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		try {
			return BookmarksEntryPermission.contains(
				permissionChecker, _entry, ActionKeys.VIEW);
		}
		catch (Exception e) {
		}

		return true;
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			renderRequest.setAttribute(WebKeys.BOOKMARKS_ENTRY, _entry);

			return "/html/portlet/bookmarks/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	private BookmarksEntry _entry;

}
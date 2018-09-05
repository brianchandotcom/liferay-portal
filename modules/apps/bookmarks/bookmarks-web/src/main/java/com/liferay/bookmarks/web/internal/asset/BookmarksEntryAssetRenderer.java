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

package com.liferay.bookmarks.web.internal.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.constants.BookmarksWebKeys;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Sergio González
 */
public class BookmarksEntryAssetRenderer
	extends BaseJSPAssetRenderer<BookmarksEntry> implements TrashRenderer {

	public BookmarksEntryAssetRenderer(
		BookmarksEntry entry,
		ModelResourcePermission<BookmarksEntry> modelResourcePermission) {

		_entry = entry;
		_bookmarksEntryModelResourcePermission = modelResourcePermission;
	}

	@Override
	public BookmarksEntry getAssetObject() {
		return _entry;
	}

	@Override
	public String getClassName() {
		return BookmarksEntry.class.getName();
	}

	@Override
	public long getClassPK() {
		return _entry.getEntryId();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public Date getDisplayDate() {
		return _entry.getModifiedDate();
	}

	@Override
	public long getGroupId() {
		return _entry.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			return "/bookmarks/asset/" + template + ".jsp";
		}

		return null;
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory<BookmarksEntry> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public int getStatus() {
		return _entry.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return _entry.getDescription();
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

		Group group = GroupLocalServiceUtil.fetchGroup(_entry.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, group, BookmarksPortletKeys.BOOKMARKS_ADMIN,
			0, 0, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/bookmarks/edit_entry");
		portletURL.setParameter(
			"folderId", String.valueOf(_entry.getFolderId()));
		portletURL.setParameter("entryId", String.valueOf(_entry.getEntryId()));
		portletURL.setParameter("showFolderSelector", Boolean.TRUE.toString());

		return portletURL;
	}

	@Override
	public String getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		return PortalUtil.getPathMain() + "/bookmarks/open_entry?entryId=" +
			_entry.getEntryId();
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
			return _bookmarksEntryModelResourcePermission.contains(
				permissionChecker, _entry, ActionKeys.UPDATE);
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		try {
			return _bookmarksEntryModelResourcePermission.contains(
				permissionChecker, _entry, ActionKeys.VIEW);
		}
		catch (Exception e) {
		}

		return true;
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(BookmarksWebKeys.BOOKMARKS_ENTRY, _entry);

		return super.include(request, response, template);
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	private final ModelResourcePermission<BookmarksEntry>
		_bookmarksEntryModelResourcePermission;
	private final BookmarksEntry _entry;

}
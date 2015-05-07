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

package com.liferay.portlet.imagegallerydisplay.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portlet.documentlibrary.display.context.logic.DLPortletInstanceSettingsHelper;
import com.liferay.portlet.documentlibrary.display.context.logic.UIItemsBuilder;
import com.liferay.portlet.imagegallerydisplay.display.context.util.IGRequestHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class DefaultIGViewFileVersionDisplayContext
	implements IGViewFileVersionDisplayContext {

	public DefaultIGViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileShortcut fileShortcut)
		throws PortalException {

		this(request, response, fileShortcut.getFileVersion(), fileShortcut);
	}

	public DefaultIGViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion)
		throws PortalException {

		this(request, response, fileVersion, null);
	}

	public DefaultIGViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion, FileShortcut fileShortcut)
		throws PortalException {

		_igRequestHelper = new IGRequestHelper(request);

		_dlPorletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(
			_igRequestHelper);

		if (fileShortcut == null) {
			_uiItemsBuilder = new UIItemsBuilder(
				request, response, fileVersion);
		}
		else {
			_uiItemsBuilder = new UIItemsBuilder(
				request, response, fileShortcut);
		}
	}

	@Override
	public List<MenuItem> getMenuItems() throws PortalException {
		List<MenuItem> menuItems = new ArrayList<>();

		if (_dlPorletInstanceSettingsHelper.isShowActions()) {
			_uiItemsBuilder.addDownloadMenuItem(menuItems);

			_uiItemsBuilder.addViewOriginalFileMenuItem(menuItems);

			_uiItemsBuilder.addEditMenuItem(menuItems);

			_uiItemsBuilder.addPermissionsMenuItem(menuItems);

			_uiItemsBuilder.addDeleteMenuItem(menuItems);
		}

		return menuItems;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	private static final UUID _UUID = UUID.fromString(
		"C04528F9-C005-4E21-A926-F068750B99DB");

	private final DLPortletInstanceSettingsHelper
		_dlPorletInstanceSettingsHelper;
	private final IGRequestHelper _igRequestHelper;
	private final UIItemsBuilder _uiItemsBuilder;

}
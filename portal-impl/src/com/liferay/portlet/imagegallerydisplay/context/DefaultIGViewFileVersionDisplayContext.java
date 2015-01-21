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

package com.liferay.portlet.imagegallerydisplay.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portlet.documentlibrary.context.BaseDefaultDLViewFileVersionDisplayContext;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class DefaultIGViewFileVersionDisplayContext
	extends BaseDefaultDLViewFileVersionDisplayContext
	implements IGViewFileVersionDisplayContext {

	public DefaultIGViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			DLFileShortcut dlFileShortcut)
		throws PortalException {

		super(request, response, dlFileShortcut);
	}

	public DefaultIGViewFileVersionDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		super(request, response, fileVersion);
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	protected void buildMenuItems(List<MenuItem> menuItems)
		throws PortalException {

		uiItemsBuilder.addDownloadMenuItem(menuItems);

		uiItemsBuilder.addViewOriginalFileMenuItem(menuItems);

		uiItemsBuilder.addEditMenuItem(menuItems);

		uiItemsBuilder.addPermissionsMenuItem(menuItems);

		uiItemsBuilder.addDeleteMenuItem(menuItems);
	}

	private static final UUID _UUID = UUID.fromString(
		"C04528F9-C005-4E21-A926-F068750B99DB");

}
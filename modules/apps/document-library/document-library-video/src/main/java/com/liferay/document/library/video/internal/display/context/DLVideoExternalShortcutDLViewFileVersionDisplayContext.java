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

package com.liferay.document.library.video.internal.display.context;

import com.liferay.document.library.display.context.BaseDLViewFileVersionDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.video.internal.constants.DLVideoConstants;
import com.liferay.document.library.video.internal.util.DLVideoExternalShortcutUIItemsUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 * @author Alejandro Tardín
 */
public class DLVideoExternalShortcutDLViewFileVersionDisplayContext
	extends BaseDLViewFileVersionDisplayContext {

	public DLVideoExternalShortcutDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion) {

		super(
			_UUID, parentDLDisplayContext, httpServletRequest,
			httpServletResponse, fileVersion);
	}

	@Override
	public List<DDMStructure> getDDMStructures() throws PortalException {
		List<DDMStructure> ddmStructures = super.getDDMStructures();

		Iterator<DDMStructure> iterator = ddmStructures.iterator();

		while (iterator.hasNext()) {
			DDMStructure ddmStructure = iterator.next();

			String ddmStructureKey = ddmStructure.getStructureKey();

			if (ddmStructureKey.equals(
					DLVideoConstants.
						DDM_STRUCTURE_KEY_DL_VIDEO_EXTERNAL_SHORTCUT)) {

				iterator.remove();

				break;
			}
		}

		return ddmStructures;
	}

	@Override
	public Menu getMenu() throws PortalException {
		Menu menu = super.getMenu();

		DLVideoExternalShortcutUIItemsUtil.processUIItems(menu.getMenuItems());

		return menu;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = super.getToolbarItems();

		DLVideoExternalShortcutUIItemsUtil.processUIItems(toolbarItems);

		return toolbarItems;
	}

	@Override
	public boolean hasPreview() {
		return false;
	}

	@Override
	public boolean isDownloadLinkVisible() {
		return false;
	}

	@Override
	public boolean isVersionInfoVisible() {
		return false;
	}

	private static final UUID _UUID = UUID.fromString(
		"7deb426a-96b9-4db6-88ac-9afbc7fc2151");

}
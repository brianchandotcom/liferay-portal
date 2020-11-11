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

package com.liferay.document.library.external.video.internal.display.context;

import com.liferay.document.library.display.context.BaseDLViewFileVersionDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoConstants;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoWebKeys;
import com.liferay.document.library.external.video.internal.util.DLExternalVideoMetadataHelper;
import com.liferay.document.library.external.video.internal.util.DLExternalVideoUIItemsUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 * @author Alejandro Tardín
 */
public class DLExternalVideoDLViewFileVersionDisplayContext
	extends BaseDLViewFileVersionDisplayContext {

	public DLExternalVideoDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion,
		DLExternalVideoMetadataHelper dlExternalVideoMetadataHelper,
		ServletContext servletContext) {

		super(
			_UUID, parentDLDisplayContext, httpServletRequest,
			httpServletResponse, fileVersion);

		_dlExternalVideoMetadataHelper = dlExternalVideoMetadataHelper;
		_servletContext = servletContext;
	}

	@Override
	public List<DDMStructure> getDDMStructures() throws PortalException {
		List<DDMStructure> ddmStructures = super.getDDMStructures();

		Iterator<DDMStructure> iterator = ddmStructures.iterator();

		while (iterator.hasNext()) {
			DDMStructure ddmStructure = iterator.next();

			String structureKey = ddmStructure.getStructureKey();

			if (structureKey.equals(
					DLExternalVideoConstants.
						DDM_STRUCTURE_KEY_EXTERNAL_VIDEO)) {

				iterator.remove();

				break;
			}
		}

		return ddmStructures;
	}

	@Override
	public Menu getMenu() throws PortalException {
		Menu menu = super.getMenu();

		DLExternalVideoUIItemsUtil.processUIItems(menu.getMenuItems());

		return menu;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = super.getToolbarItems();

		DLExternalVideoUIItemsUtil.processUIItems(toolbarItems);

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

	@Override
	public void renderPreview(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		if (_dlExternalVideoMetadataHelper.containsField(
				DLExternalVideoConstants.DDM_FIELD_NAME_HTML)) {

			request.setAttribute(
				DLExternalVideoWebKeys.EMBEDDABLE_HTML,
				_dlExternalVideoMetadataHelper.getFieldValue(
					DLExternalVideoConstants.DDM_FIELD_NAME_HTML));
		}

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/preview.jsp");

		requestDispatcher.include(request, response);
	}

	private static final UUID _UUID = UUID.fromString(
		"7deb426a-96b9-4db6-88ac-9afbc7fc2151");

	private final DLExternalVideoMetadataHelper _dlExternalVideoMetadataHelper;
	private final ServletContext _servletContext;

}
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

package com.liferay.document.library.google.docs.context;

import com.liferay.document.library.google.docs.util.GoogleDocsConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.context.BaseDLEditFileEntryDisplayContext;
import com.liferay.portlet.documentlibrary.context.DLEditFileEntryDisplayContext;
import com.liferay.portlet.documentlibrary.context.DLFilePicker;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivan Zaera
 */
public class GoogleDocsDLEditFileEntryDisplayContext
	extends BaseDLEditFileEntryDisplayContext {

	public GoogleDocsDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		DLFileEntryType dlFileEntryType) {

		super(
			_UUID, parentDLEditFileEntryDisplayContext, request, response,
			dlFileEntryType);
	}

	public GoogleDocsDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileEntry fileEntry) {

		super(
			_UUID, parentDLEditFileEntryDisplayContext, request, response,
			fileEntry);
	}

	@Override
	public DLFilePicker getDLFilePicker(String onFilePickCallback) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return new GoogleDocsDLFilePicker(
			portletDisplay.getNamespace(), onFilePickCallback, themeDisplay);
	}

	@Override
	public long getMaximumUploadSize() {
		return 0;
	}

	@Override
	public boolean isDDMStructureVisible(DDMStructure ddmStructure)
		throws PortalException {

		String ddmStructureKey = ddmStructure.getStructureKey();

		if (ddmStructureKey.equals(
				GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS)) {

			return false;
		}

		return super.isDDMStructureVisible(ddmStructure);
	}

	private static final UUID _UUID = UUID.fromString(
		"62BE5287-BEA3-4E3F-9731-15B1B901380D");

}
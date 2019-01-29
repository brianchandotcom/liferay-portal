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

package com.liferay.frontend.image.editor.integration.document.library.internal.display.context;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.frontend.image.editor.integration.document.library.internal.display.context.logic.ImageEditorDLDisplayContextHelper;
import com.liferay.image.gallery.display.kernel.display.context.BaseIGViewFileVersionDisplayContext;
import com.liferay.image.gallery.display.kernel.display.context.IGViewFileVersionDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ambrín Chaudhary
 */
public class ImageEditorIGViewFileVersionDisplayContext
	extends BaseIGViewFileVersionDisplayContext {

	public ImageEditorIGViewFileVersionDisplayContext(
		IGViewFileVersionDisplayContext parentIGDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion, ResourceBundle resourceBundle,
		DLURLHelper dlurlHelper) {

		super(_UUID, parentIGDisplayContext, request, response, fileVersion);

		_resourceBundle = resourceBundle;
		_dlurlHelper = dlurlHelper;

		try {
			FileEntry fileEntry = null;

			if (fileVersion != null) {
				fileEntry = fileVersion.getFileEntry();
			}

			_fileEntry = fileEntry;

			_imageEditorDLDisplayContextHelper =
				new ImageEditorDLDisplayContextHelper(
					fileVersion, request, dlurlHelper);
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to create image editor image gallery view file " +
					"version display context for file version " + fileVersion,
				pe);
		}
	}

	@Override
	public Menu getMenu() throws PortalException {
		Menu menu = super.getMenu();

		if (!_imageEditorDLDisplayContextHelper.isShowImageEditorAction()) {
			return menu;
		}

		List<MenuItem> menuItems = menu.getMenuItems();

		ImageEditorDLDisplayContextHelper imageEditorDLDisplayContextHelper =
			new ImageEditorDLDisplayContextHelper(
				fileVersion, request, _dlurlHelper);

		menuItems.add(
			imageEditorDLDisplayContextHelper.
				getJavacriptEditWithImageEditorMenuItem(_resourceBundle));

		return menu;
	}

	private static final UUID _UUID = UUID.fromString(
		"1cc61284-8baf-4904-8a65-b7b3845e64d7");

	private final DLURLHelper _dlurlHelper;
	private final FileEntry _fileEntry;
	private final ImageEditorDLDisplayContextHelper
		_imageEditorDLDisplayContextHelper;
	private final ResourceBundle _resourceBundle;

}
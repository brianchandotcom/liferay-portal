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

package com.liferay.document.library.google.docs.internal.display.context;

import com.liferay.document.library.display.context.BaseDLViewFileVersionDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.google.docs.internal.util.GoogleDocsConstants;
import com.liferay.document.library.google.docs.internal.util.GoogleDocsMetadataHelper;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class GoogleDocsDLViewFileVersionDisplayContext
	extends BaseDLViewFileVersionDisplayContext {

	public GoogleDocsDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion,
		GoogleDocsMetadataHelper googleDocsMetadataHelper) {

		super(
			_UUID, parentDLDisplayContext, httpServletRequest,
			httpServletResponse, fileVersion);

		_googleDocsMetadataHelper = googleDocsMetadataHelper;

		_googleDocsUIItemsProcessor = new GoogleDocsUIItemsProcessor(
			httpServletRequest, googleDocsMetadataHelper);
	}

	@Override
	public List<DDMStructure> getDDMStructures() throws PortalException {
		List<DDMStructure> ddmStructures = super.getDDMStructures();

		Iterator<DDMStructure> iterator = ddmStructures.iterator();

		while (iterator.hasNext()) {
			DDMStructure ddmStructure = iterator.next();

			String structureKey = ddmStructure.getStructureKey();

			if (structureKey.equals(
					GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS)) {

				iterator.remove();

				break;
			}
		}

		return ddmStructures;
	}

	@Override
	public Menu getMenu() throws PortalException {
		Menu menu = super.getMenu();

		if (!isActionsVisible()) {
			return menu;
		}

		// See LPS-79987

		if (Validator.isNull(
				_googleDocsMetadataHelper.getFieldValue(
					GoogleDocsConstants.DDM_FIELD_NAME_URL))) {

			return menu;
		}

		List<MenuItem> menuItems = menu.getMenuItems();

		menuItems.removeIf(
			menuItem -> Objects.equals(
				menuItem.getKey(), "#edit-with-image-editor"));

		_googleDocsUIItemsProcessor.processMenuItems(menuItems);

		return menu;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = super.getToolbarItems();

		_googleDocsUIItemsProcessor.processToolbarItems(toolbarItems);

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
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		if (_googleDocsMetadataHelper.containsField(
				GoogleDocsConstants.DDM_FIELD_NAME_EMBEDDABLE_URL)) {

			String previewURL = _googleDocsMetadataHelper.getFieldValue(
				GoogleDocsConstants.DDM_FIELD_NAME_EMBEDDABLE_URL);

			if (Validator.isNotNull(previewURL)) {
				printWriter.format(
					"<iframe frameborder=\"0\" height=\"664px\" src=\"%s\" " +
						"width=\"100%%\"></iframe>",
					previewURL);

				return;
			}
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", httpServletRequest.getLocale(), getClass());

		printWriter.write("<div class=\"alert alert-info\">");
		printWriter.write(
			LanguageUtil.get(
				resourceBundle,
				"google-docs-does-not-provide-a-preview-for-this-document"));
		printWriter.write("</div>");
	}

	private static final UUID _UUID = UUID.fromString(
		"7B61EA79-83AE-4FFD-A77A-1D47E06EBBE9");

	private final GoogleDocsMetadataHelper _googleDocsMetadataHelper;
	private final GoogleDocsUIItemsProcessor _googleDocsUIItemsProcessor;

}
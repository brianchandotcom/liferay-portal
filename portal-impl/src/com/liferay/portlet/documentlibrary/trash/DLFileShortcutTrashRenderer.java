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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.trash.BaseTrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Zsolt Berentey
 */
public class DLFileShortcutTrashRenderer extends BaseTrashRenderer {

	public static final String TYPE = "shortcut";

	public DLFileShortcutTrashRenderer(DLFileShortcut fileShortcut)
		throws PortalException {

		_fileShortcut = fileShortcut;

		_fileEntry = DLAppLocalServiceUtil.getFileEntry(
			fileShortcut.getToFileEntryId());
	}

	@Override
	public String getClassName() {
		return DLFileShortcut.class.getName();
	}

	@Override
	public long getClassPK() {
		return _fileShortcut.getPrimaryKey();
	}

	@Override
	public String getIconCssClass() {
		return DLUtil.getFileIconCssClass(_fileEntry.getExtension());
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/file_system/small/" +
			_fileEntry.getIcon() + ".png";
	}

	@Override
	public String getPortletId() {
		return PortletKeys.DOCUMENT_LIBRARY;
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return HtmlUtil.stripHtml(_fileEntry.getDescription());
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.format(
			locale, "shortcut-to-x", _fileShortcut.getToTitle(), false);
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(AssetRenderer.TEMPLATE_ABSTRACT) ||
			template.equals(AssetRenderer.TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, _fileEntry);

			return "/html/portlet/document_library/asset/" + template + ".jsp";
		}

		return null;
	}

	private final FileEntry _fileEntry;
	private final DLFileShortcut _fileShortcut;

}
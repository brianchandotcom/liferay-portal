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

package com.liferay.image.editor.document.library.integration.display.context;

import com.liferay.document.library.display.context.BaseDLViewFileVersionDisplayContext;
import com.liferay.document.library.display.context.DLUIItemKeys;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.kernel.util.DLUtil;
//import com.liferay.document.library.web.display.context.logic.FileEntryDisplayContextHelper;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * @author Ambrin Chaudhary
 */
public class ImageEditorDLViewFileVersionDisplayContext
	extends BaseDLViewFileVersionDisplayContext {

	public ImageEditorDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		super(_UUID, parentDLDisplayContext, request, response, fileVersion);

		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {

			FileEntry fileEntry = null;

			if (fileVersion != null) {
				fileEntry = fileVersion.getFileEntry();
			}

			_fileEntry = fileEntry;

			/*_fileEntryDisplayContextHelper = new FileEntryDisplayContextHelper(
				_themeDisplay.getPermissionChecker(), _fileEntry);*/

		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to build UIItemsBuilder for " + fileVersion, pe);
		}
	}

	@Override
	public Menu getMenu() throws PortalException {
		Menu menu = super.getMenu();

		List<MenuItem> menuItems = menu.getMenuItems();
/*
		if (!_fileEntryDisplayContextHelper.isEditActionAvailable()) {
			return menu;
		}*/

		if (!ArrayUtil.contains(
			PropsValues.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES,
			_fileEntry.getMimeType())) {

			return menu;
		}

		JavaScriptMenuItem javascriptMenuItem = getJavacriptEditWithImageEditorMenuItem();

		menuItems.add(javascriptMenuItem);

		return menu;
	}


	public JavaScriptMenuItem getJavacriptEditWithImageEditorMenuItem()
		throws PortalException {

		String imageEditorPortletId = PortletProviderUtil.getPortletId(
			Image.class.getName(), PortletProvider.Action.EDIT);

		PortletURL imageEditorURL = PortletURLFactoryUtil.create(
			request, imageEditorPortletId, _themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		imageEditorURL.setParameter(
			"mvcRenderCommandName", "/image_editor/view");

		try {
			imageEditorURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (Exception e) {
			throw new SystemException("Unable to set window state", e);
		}

		PortletURL editURL = _getLiferayPortletResponse().createActionURL(
			PortletKeys.DOCUMENT_LIBRARY_ADMIN);

		editURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/image_editor/edit_file_entry_image_editor");

		editURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		String fileEntryPreviewURL = DLUtil.getPreviewURL(
			_fileEntry, fileVersion, _themeDisplay, StringPool.BLANK);

		String onClick =
			_getLiferayPortletResponse().getNamespace() + "editWithImageEditor('" +
			imageEditorURL.toString() + "', '" +
			editURL.toString() + "', '" +
			_fileEntry.getFileName() + "', '" +
			fileEntryPreviewURL + "');";

		JavaScriptMenuItem javascriptMenuItem = new JavaScriptMenuItem();

		javascriptMenuItem.setKey("#edit-with-image-editor");
		javascriptMenuItem.setLabel(
			LanguageUtil.get(request, "edit-with-image-editor"));
		javascriptMenuItem.setOnClick(onClick);

		String javaScript =
			"/com/liferay/image/editor/document/library/integration/display/context/dependencies/" +
			"edit_with_image_editor_js.ftl";

		Class<?> clazz = getClass();

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			javaScript, clazz.getResource(javaScript));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, urlTemplateResource, false);

		template.put("namespace", _getLiferayPortletResponse().getNamespace());

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		javascriptMenuItem.setJavaScript(unsyncStringWriter.toString());

		return javascriptMenuItem;
	}


	private LiferayPortletResponse _getLiferayPortletResponse() {
		PortletResponse portletResponse =
			(PortletResponse) request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortalUtil.getLiferayPortletResponse(portletResponse);
	}

	private static final UUID _UUID = UUID.fromString(
		"ec0c6ec4-8671-4c9e-94a3-8c6bcca0437c");

	//private final FileEntryDisplayContextHelper _fileEntryDisplayContextHelper;
	private final FileEntry _fileEntry;
	private final ThemeDisplay _themeDisplay;
}
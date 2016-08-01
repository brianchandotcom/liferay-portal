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

package com.liferay.frontend.image.editor.integration.document.library.internal.display.context.logic;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptToolbarItem;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class ImageEditorDLDisplayContextHelper {

	public ImageEditorDLDisplayContextHelper(
		FileVersion fileVersion, HttpServletRequest request) {

		_fileVersion = fileVersion;
		_request = request;

		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			FileEntry fileEntry = null;

			if (fileVersion != null) {
				fileEntry = fileVersion.getFileEntry();
			}

			_fileEntry = fileEntry;
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to create image editor document library display " +
					"context helper for file version " + fileVersion,
				pe);
		}
	}

	public JavaScriptMenuItem getJavacriptEditWithImageEditorMenuItem(
			ResourceBundle resourceBundle)
		throws PortalException {

		JavaScriptMenuItem javascriptMenuItem = new JavaScriptMenuItem();

		javascriptMenuItem.setKey("#edit-with-image-editor");
		javascriptMenuItem.setLabel(
			LanguageUtil.get(resourceBundle, "edit-with-image-editor"));
		javascriptMenuItem.setOnClick(_getOnclickMethod());
		javascriptMenuItem.setJavaScript(_getJavaScript());

		return javascriptMenuItem;
	}

	public JavaScriptToolbarItem getJavacriptEditWithImageEditorToolbarItem(
			ResourceBundle resourceBundle)
		throws PortalException {

		JavaScriptToolbarItem javascriptToolbarItem =
			new JavaScriptToolbarItem();

		javascriptToolbarItem.setKey("#edit-with-image-editor");
		javascriptToolbarItem.setLabel(
			LanguageUtil.get(resourceBundle, "edit-with-image-editor"));
		javascriptToolbarItem.setOnClick(_getOnclickMethod());
		javascriptToolbarItem.setJavaScript(_getJavaScript());

		return javascriptToolbarItem;
	}

	public boolean isShowActions() throws PortalException {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
			return true;
		}

		Settings settings = SettingsFactoryUtil.getSettings(
			new PortletInstanceSettingsLocator(
				_themeDisplay.getLayout(), portletDisplay.getId()));

		TypedSettings typedSettings = new TypedSettings(settings);

		return typedSettings.getBooleanValue("showActions");
	}

	public boolean isShowImageEditorAction() throws PortalException {
		if (_isShowImageEditorAction != null) {
			return _isShowImageEditorAction;
		}

		if (!isShowActions()) {
			_isShowImageEditorAction = false;
		}
		else if (!DLFileEntryPermission.contains(
					_themeDisplay.getPermissionChecker(), _fileEntry,
					ActionKeys.UPDATE) ||
				 (_fileEntry.isCheckedOut() && !_fileEntry.hasLock())) {

			_isShowImageEditorAction = false;
		}
		else if (!ArrayUtil.contains(
					PropsValues.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES,
					_fileEntry.getMimeType())) {

			_isShowImageEditorAction = false;
		}
		else {
			_isShowImageEditorAction = true;
		}

		return _isShowImageEditorAction;
	}

	private String _getJavaScript() throws PortalException {
		String javaScript =
			"/com/liferay/frontend/image/editor/integration/document/library" +
				"/internal/display/context/dependencies" +
					"/edit_with_image_editor_js.ftl";

		Class<?> clazz = getClass();

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			javaScript, clazz.getResource(javaScript));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, urlTemplateResource, false);

		template.put("editLanguageKey", LanguageUtil.get(_request, "edit"));

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		template.put("namespace", liferayPortletResponse.getNamespace());

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	private LiferayPortletResponse _getLiferayPortletResponse() {
		PortletResponse portletResponse =
			(PortletResponse)_request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortalUtil.getLiferayPortletResponse(portletResponse);
	}

	private String _getOnclickMethod() {
		String imageEditorPortletId = PortletProviderUtil.getPortletId(
			Image.class.getName(), PortletProvider.Action.EDIT);

		PortletURL imageEditorURL = PortletURLFactoryUtil.create(
			_request, imageEditorPortletId, PortletRequest.RENDER_PHASE);

		imageEditorURL.setParameter(
			"mvcRenderCommandName", "/image_editor/view");

		try {
			imageEditorURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (Exception e) {
			throw new SystemException("Unable to set window state", e);
		}

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL editURL = liferayPortletResponse.createActionURL();

		editURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/document_library/edit_file_entry_with_image_editor");

		editURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		String fileEntryPreviewURL = DLUtil.getPreviewURL(
			_fileEntry, _fileVersion, _themeDisplay, StringPool.BLANK);

		StringBundler sb = new StringBundler(10);

		sb.append(liferayPortletResponse.getNamespace());
		sb.append("editWithImageEditor('");
		sb.append(imageEditorURL.toString());
		sb.append("', '");
		sb.append(editURL.toString());
		sb.append("', '");
		sb.append(_fileEntry.getFileName());
		sb.append("', '");
		sb.append(fileEntryPreviewURL);
		sb.append("');");

		return sb.toString();
	}

	private final FileEntry _fileEntry;
	private final FileVersion _fileVersion;
	private Boolean _isShowImageEditorAction;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}
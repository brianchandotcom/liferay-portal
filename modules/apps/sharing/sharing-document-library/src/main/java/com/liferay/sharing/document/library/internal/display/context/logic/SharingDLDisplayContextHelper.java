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

package com.liferay.sharing.document.library.internal.display.context.logic;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
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
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.model.SharingEntry;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class SharingDLDisplayContextHelper {

	public SharingDLDisplayContextHelper(
		FileEntry fileEntry, HttpServletRequest request) {

		_fileEntry = fileEntry;
		_request = request;

		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public JavaScriptMenuItem getJavacriptEditWithImageEditorMenuItem(
			ResourceBundle resourceBundle)
		throws PortalException {

		JavaScriptMenuItem javaScriptMenuItem = new JavaScriptMenuItem();

		javaScriptMenuItem.setKey("#share");
		javaScriptMenuItem.setLabel(LanguageUtil.get(resourceBundle, "share"));
		javaScriptMenuItem.setOnClick(_getOnclickMethod(resourceBundle));
		javaScriptMenuItem.setJavaScript(_getJavaScript());

		return javaScriptMenuItem;
	}

	public JavaScriptToolbarItem getJavacriptEditWithImageEditorToolbarItem(
			ResourceBundle resourceBundle)
		throws PortalException {

		JavaScriptToolbarItem javaScriptToolbarItem =
			new JavaScriptToolbarItem();

		javaScriptToolbarItem.setKey("#share");
		javaScriptToolbarItem.setLabel(
			LanguageUtil.get(resourceBundle, "share"));
		javaScriptToolbarItem.setOnClick(_getOnclickMethod(resourceBundle));
		javaScriptToolbarItem.setJavaScript(_getJavaScript());

		return javaScriptToolbarItem;
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

	public boolean isShowShareAction() throws PortalException {
		if (_showImageEditorAction != null) {
			return _showImageEditorAction;
		}

		if (!_themeDisplay.isSignedIn() || !isShowActions()) {
			_showImageEditorAction = false;
		}
		else {
			_showImageEditorAction = true;
		}

		return _showImageEditorAction;
	}

	private String _getJavaScript() throws PortalException {
		String javaScript =
			"/com/liferay/sharing/document/library/internal/display/context" +
				"/dependencies/sharing_js.ftl";

		Class<?> clazz = getClass();

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			javaScript, clazz.getResource(javaScript));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, urlTemplateResource, false);

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

	private String _getOnclickMethod(ResourceBundle resourceBundle) {
		String sharingPortletId = PortletProviderUtil.getPortletId(
			SharingEntry.class.getName(), PortletProvider.Action.EDIT);

		PortletURL sharingURL = PortletURLFactoryUtil.create(
			_request, sharingPortletId, PortletRequest.RENDER_PHASE);

		sharingURL.setParameter("mvcRenderCommandName", "/sharing/share");
		sharingURL.setParameter("classNameId", "22");
		sharingURL.setParameter(
			"classPK", String.valueOf(_fileEntry.getFileEntryId()));

		try {
			sharingURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (Exception e) {
			throw new SystemException("Unable to set window state", e);
		}

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		StringBundler sb = new StringBundler(6);

		sb.append(liferayPortletResponse.getNamespace());
		sb.append("sharing('");
		sb.append(sharingURL.toString());
		sb.append("', '");
		sb.append(LanguageUtil.get(resourceBundle, "share"));
		sb.append("');");

		return sb.toString();
	}

	private final FileEntry _fileEntry;
	private final HttpServletRequest _request;
	private Boolean _showImageEditorAction;
	private final ThemeDisplay _themeDisplay;

}
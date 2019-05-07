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

package com.liferay.portlet.configuration.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ArchivedSettingsActionDropdownItemsProvider {

	public ArchivedSettingsActionDropdownItemsProvider(
		ArchivedSettings archivedSettings, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_archivedSettings = archivedSettings;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(_getRestoreArchivedSetupActionUnsafeConsumer());
				add(_getDeleteArchivedSetupActionUnsafeConsumer());
			}
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteArchivedSetupActionUnsafeConsumer() {

		PortletURL deleteArchivedSetupsURL = _renderResponse.createActionURL();

		deleteArchivedSetupsURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteArchivedSetups");

		deleteArchivedSetupsURL.setParameter(
			"mvcPath", "/edit_configuration_templates.jsp");
		deleteArchivedSetupsURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteArchivedSetupsURL.setParameter(
			"portletConfiguration", Boolean.TRUE.toString());
		deleteArchivedSetupsURL.setParameter(
			"portletResource", _getPortletResource());
		deleteArchivedSetupsURL.setParameter(
			"name", _archivedSettings.getName());

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteArchivedSetups");
			dropdownItem.putData(
				"deleteArchivedSetupsURL", deleteArchivedSetupsURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private String _getPortletResource() {
		if (_portletResource != null) {
			return _portletResource;
		}

		_portletResource = ParamUtil.getString(
			_httpServletRequest, "portletResource");

		return _portletResource;
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getRestoreArchivedSetupActionUnsafeConsumer() {

		PortletURL restoreArchivedSetupURL = _renderResponse.createActionURL();

		restoreArchivedSetupURL.setParameter(
			ActionRequest.ACTION_NAME, "restoreArchivedSetup");

		restoreArchivedSetupURL.setParameter(
			"mvcPath", "/edit_configuration_templates.jsp");
		restoreArchivedSetupURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		restoreArchivedSetupURL.setParameter(
			"portletConfiguration", Boolean.TRUE.toString());
		restoreArchivedSetupURL.setParameter(
			"portletResource", _getPortletResource());
		restoreArchivedSetupURL.setParameter(
			"name", _archivedSettings.getName());

		return dropdownItem -> {
			dropdownItem.putData("action", "restoreArchivedSetup");
			dropdownItem.putData(
				"restoreArchivedSetupURL", restoreArchivedSetupURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "apply"));
		};
	}

	private final ArchivedSettings _archivedSettings;
	private final HttpServletRequest _httpServletRequest;
	private String _portletResource;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}
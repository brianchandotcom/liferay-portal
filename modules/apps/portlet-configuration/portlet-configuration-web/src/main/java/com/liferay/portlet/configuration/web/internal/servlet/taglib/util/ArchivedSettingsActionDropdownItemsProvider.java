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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

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
		return DropdownItemListBuilder.add(
			_getRestoreArchivedSetupActionUnsafeConsumer()
		).add(
			_getDeleteArchivedSetupActionUnsafeConsumer()
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteArchivedSetupActionUnsafeConsumer() {

		PortletURL deleteArchivedSetupsURL = PortletURLBuilder.createActionURL(
			_renderResponse
		).setMVCPath(
			"/edit_configuration_templates.jsp"
		).setActionName(
			"deleteArchivedSetups"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setParameter(
			"portletConfiguration", Boolean.TRUE.toString()
		).setParameter(
			"portletResource", _getPortletResource()
		).setParameter(
			"name", _archivedSettings.getName()
		).build();

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

		PortletURL restoreArchivedSetupURL = PortletURLBuilder.createActionURL(
			_renderResponse
		).setMVCPath(
			"/edit_configuration_templates.jsp"
		).setActionName(
			"restoreArchivedSetup"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setParameter(
			"portletConfiguration", Boolean.TRUE.toString()
		).setParameter(
			"portletResource", _getPortletResource()
		).setParameter(
			"name", _archivedSettings.getName()
		).build();

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
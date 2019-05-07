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

package com.liferay.portlet.configuration.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.configuration.web.internal.constants.PortletConfigurationWebKeys;
import com.liferay.portlet.configuration.web.internal.servlet.taglib.util.ArchivedSettingsActionDropdownItemsProvider;

import java.util.Date;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ArchivedSettingsVerticalCard implements VerticalCard {

	public ArchivedSettingsVerticalCard(
		ArchivedSettings archivedSettings, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_archivedSettings = archivedSettings;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		ArchivedSettingsActionDropdownItemsProvider
			archivedSettingsActionDropdownItemsProvider =
				new ArchivedSettingsActionDropdownItemsProvider(
					_archivedSettings, _renderRequest, _renderResponse);

		return archivedSettingsActionDropdownItemsProvider.
			getActionDropdownItems();
	}

	@Override
	public String getDefaultEventHandler() {
		return PortletConfigurationWebKeys.
			ARCHIVED_SETUPS_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getIcon() {
		return "archive";
	}

	@Override
	public String getSubtitle() {
		Date modifiedDate = _archivedSettings.getModifiedDate();

		String modifiedDateDescription = LanguageUtil.getTimeDescription(
			_themeDisplay.getLocale(),
			System.currentTimeMillis() - modifiedDate.getTime(), true);

		return LanguageUtil.format(
			_httpServletRequest, "x-ago-by-x",
			new String[] {
				modifiedDateDescription,
				HtmlUtil.escape(_archivedSettings.getUserName())
			});
	}

	@Override
	public String getTitle() {
		return _archivedSettings.getName();
	}

	private final ArchivedSettings _archivedSettings;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}
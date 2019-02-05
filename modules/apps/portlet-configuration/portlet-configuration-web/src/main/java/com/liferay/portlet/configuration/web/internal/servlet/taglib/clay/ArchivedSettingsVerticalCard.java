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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ArchivedSettingsVerticalCard implements VerticalCard {

	public ArchivedSettingsVerticalCard(
		ArchivedSettings archivedSettings, RenderRequest renderRequest) {

		_archivedSettings = archivedSettings;

		_request = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
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
			_request, "x-ago-by-x",
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
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}
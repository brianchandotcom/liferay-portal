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

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Máté Thurzó
 * @author Samuel Trong Tran
 */
public class PublicationsConfigurationDisplayContext {

	public PublicationsConfigurationDisplayContext(
		CTPreferencesLocalService ctPreferencesLocalService,
		HttpServletRequest httpServletRequest, Language language,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CTPreferences ctPreferences =
			ctPreferencesLocalService.fetchCTPreferences(
				themeDisplay.getCompanyId(), 0);

		if (ctPreferences != null) {
			_publicationsEnabled = true;
		}
		else {
			_publicationsEnabled = false;
		}

		_language = language;
		_renderResponse = renderResponse;
	}

	public String getActionURL() {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/change_tracking/update_global_publications_configuration"
		).buildString();
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		if (isPublicationsEnabled()) {
			_navigation = ParamUtil.getString(
				_httpServletRequest, "navigation", "global-settings");
		}
		else {
			_navigation = "global-settings";
		}

		return _navigation;
	}

	public boolean isPublicationsEnabled() {
		return _publicationsEnabled;
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private String _navigation;
	private final boolean _publicationsEnabled;
	private final RenderResponse _renderResponse;

}
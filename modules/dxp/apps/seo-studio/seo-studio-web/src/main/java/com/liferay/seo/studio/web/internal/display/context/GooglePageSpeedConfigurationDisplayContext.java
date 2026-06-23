/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.display.context;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Kiana Suetani
 */
public class GooglePageSpeedConfigurationDisplayContext {

	public GooglePageSpeedConfigurationDisplayContext(
		HttpServletRequest httpServletRequest, Language language) {

		_httpServletRequest = httpServletRequest;
		_language = language;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getViewProps() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"backURL", _getBackURL()
		).put(
			"description",
			_language.get(_httpServletRequest, "google-pagespeed-instructions")
		).put(
			"domainsURL", "/o/seo-studio/domains"
		).put(
			"instancesURL", "/o/seo-studio/instances"
		).put(
			"title", _language.get(_httpServletRequest, "google-pagespeed")
		).build();
	}

	private String _getBackURL() throws Exception {
		return PortalUtil.getLayoutFullURL(
			LayoutLocalServiceUtil.getLayoutByFriendlyURL(
				_themeDisplay.getScopeGroupId(), false, "/configurations"),
			_themeDisplay);
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final ThemeDisplay _themeDisplay;

}
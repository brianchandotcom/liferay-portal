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

package com.liferay.cookies.banner.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class CookiesBannerConfigurationDisplayContext
	extends BaseCookiesBannerDisplayContext {

	public CookiesBannerConfigurationDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(renderRequest, renderResponse);
	}

	public Map<String, Object> getContext(long groupId) throws Exception {
		return HashMapBuilder.<String, Object>put(
			"optionalConsentCookieTypeNames",
			getConsentCookieTypeNamesJSONArray(
				getOptionalConsentCookieTypes(groupId))
		).put(
			"requiredConsentCookieTypeNames",
			getConsentCookieTypeNamesJSONArray(
				getRequiredConsentCookieTypes(groupId))
		).put(
			"showButtons", isShowButtons()
		).build();
	}

	public String getCookiePolicyLink() {
		String cookiePolicyLink =
			cookiesConsentConfiguration.cookiePolicyLink();

		if (Validator.isNotNull(cookiePolicyLink)) {
			return cookiePolicyLink;
		}

		return StringPool.POUND;
	}

	public String getCookieTitle(
		String cookie, HttpServletRequest httpServletRequest) {

		return LanguageUtil.get(
			httpServletRequest, "cookies-title[" + cookie + "]");
	}

	public String getDescription(Locale locale) {
		LocalizedValuesMap description =
			cookiesConsentConfiguration.description();

		return description.get(locale);
	}

	public String getLinkDisplayText(Locale locale) {
		LocalizedValuesMap linkDisplayTextLocalizedValuesMap =
			cookiesConsentConfiguration.linkDisplayText();

		return linkDisplayTextLocalizedValuesMap.get(locale);
	}

	public boolean isShowButtons() {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isStatePopUp()) {
			return true;
		}

		return false;
	}

}
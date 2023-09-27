/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.banner.web.internal.display.context;

import com.liferay.cookies.configuration.CookiesConfigurationProvider;
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
		CookiesConfigurationProvider cookiesConfigurationProvider,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(cookiesConfigurationProvider, renderRequest, renderResponse);
	}

	public Map<String, Object> getContext() {
		return HashMapBuilder.<String, Object>put(
			"optionalConsentCookieTypeNames",
			getConsentCookieTypeNamesJSONArray(getOptionalConsentCookieTypes())
		).put(
			"requiredConsentCookieTypeNames",
			getConsentCookieTypeNamesJSONArray(getRequiredConsentCookieTypes())
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
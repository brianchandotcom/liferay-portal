/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.banner.web.internal.display.context;

import com.liferay.cookies.banner.web.internal.constants.ProductAnalyticsBannerConstants;
import com.liferay.cookies.configuration.CookiesConfigurationProvider;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.kernel.provider.LayoutUtilityPageEntryLayoutProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cookies.ConsentCookieType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Christopher Kian
 */
public class BaseProductAnalyticsDisplayContext
	extends BaseCookiesBannerDisplayContext {

	public BaseProductAnalyticsDisplayContext(
		CookiesConfigurationProvider cookiesConfigurationProvider,
		LayoutUtilityPageEntryLayoutProvider
			layoutUtilityPageEntryLayoutProvider,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(
			cookiesConfigurationProvider, layoutUtilityPageEntryLayoutProvider,
			renderRequest, renderResponse);
	}

	public String getCookieTitle(
		String cookie, HttpServletRequest httpServletRequest) {

		return LanguageUtil.get(
			httpServletRequest, "cookies-title[" + cookie + "]");
	}

	@Override
	public List<ConsentCookieType> getOptionalConsentCookieTypes() {
		if (_optionalConsentCookieTypes != null) {
			return _optionalConsentCookieTypes;
		}

		_optionalConsentCookieTypes = ListUtil.fromArray(
			_getConsentCookieType(
				false,
				ProductAnalyticsBannerConstants.
					NAME_PRODUCT_ANALYTICS_CONSENT_TYPE_FUNCTIONAL,
				false),
			_getConsentCookieType(
				false,
				ProductAnalyticsBannerConstants.
					NAME_PRODUCT_ANALYTICS_CONSENT_TYPE_PERFORMANCE,
				false),
			_getConsentCookieType(
				false,
				ProductAnalyticsBannerConstants.
					NAME_PRODUCT_ANALYTICS_CONSENT_TYPE_PERSONALIZATION,
				false),
			_getConsentCookieType(
				false,
				ProductAnalyticsBannerConstants.
					NAME_PRODUCT_ANALYTICS_CONSENT_TYPE_PRODUCT_ANALYTICS,
				false));

		return _optionalConsentCookieTypes;
	}

	public String getPrivacyPolicyLink() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout =
			layoutUtilityPageEntryLayoutProvider.
				getDefaultLayoutUtilityPageEntryLayout(
					themeDisplay.getScopeGroupId(),
					LayoutUtilityPageEntryConstants.TYPE_COOKIE_POLICY);

		if (layout != null) {
			return PortalUtil.getLayoutURL(layout, themeDisplay);
		}

		return StringPool.POUND;
	}

	@Override
	public List<ConsentCookieType> getRequiredConsentCookieTypes() {
		if (_requiredConsentCookieTypes != null) {
			return _requiredConsentCookieTypes;
		}

		_requiredConsentCookieTypes = ListUtil.fromArray(
			_getConsentCookieType(
				false,
				ProductAnalyticsBannerConstants.
					NAME_PRODUCT_ANALYTICS_CONSENT_TYPE_NECESSARY,
				true));

		return _requiredConsentCookieTypes;
	}

	private ConsentCookieType _getConsentCookieType(
		boolean hideFromEndUser, String name, boolean prechecked) {

		return new ConsentCookieType(
			new LocalizedValuesMap(
				LanguageUtil.get(
					renderRequest.getLocale(),
					"cookies-description[" + name + "]")),
			hideFromEndUser, name, prechecked);
	}

	private List<ConsentCookieType> _optionalConsentCookieTypes;
	private List<ConsentCookieType> _requiredConsentCookieTypes;

}
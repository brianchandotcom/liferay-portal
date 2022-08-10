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

import com.liferay.cookies.configuration.banner.CookiesBannerConfiguration;
import com.liferay.cookies.configuration.consent.CookiesConsentConfiguration;
import com.liferay.portal.kernel.cookies.ConsentCookieType;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Jürgen Kappler
 */
public class BaseCookiesBannerDisplayContext {

	public BaseCookiesBannerDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;

		cookiesBannerConfiguration = _getCookiesBannerConfiguration(
			renderRequest);
		cookiesConsentConfiguration = _getCookiesConfiguration(renderRequest);
	}

	public List<ConsentCookieType> getOptionalConsentCookieTypes(long groupId)
		throws Exception {

		if (_optionalConsentCookieTypes != null) {
			return _optionalConsentCookieTypes;
		}

		_optionalConsentCookieTypes =
			CookiesManagerUtil.getOptionalConsentCookieTypes(groupId);

		return _optionalConsentCookieTypes;
	}

	public List<ConsentCookieType> getRequiredConsentCookieTypes(long groupId)
		throws Exception {

		if (_requiredConsentCookieTypes != null) {
			return _requiredConsentCookieTypes;
		}

		_requiredConsentCookieTypes =
			CookiesManagerUtil.getRequiredConsentCookieTypes(groupId);

		return _requiredConsentCookieTypes;
	}

	public boolean isIncludeDeclineAllButton() {
		return cookiesBannerConfiguration.includeDeclineAllButton();
	}

	protected JSONArray getConsentCookieTypeNamesJSONArray(
		List<ConsentCookieType> consentCookieTypes) {

		JSONArray consentCookieTypeNamesJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (ConsentCookieType consentCookieType : consentCookieTypes) {
			consentCookieTypeNamesJSONArray.put(consentCookieType.getName());
		}

		return consentCookieTypeNamesJSONArray;
	}

	protected CookiesBannerConfiguration cookiesBannerConfiguration;
	protected CookiesConsentConfiguration cookiesConsentConfiguration;
	protected RenderRequest renderRequest;
	protected RenderResponse renderResponse;

	private CookiesBannerConfiguration _getCookiesBannerConfiguration(
		RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			return ConfigurationProviderUtil.getGroupConfiguration(
				CookiesBannerConfiguration.class,
				themeDisplay.getScopeGroupId());
		}
		catch (Exception exception) {
			_log.error("Unable to get cookies banner configuration", exception);
		}

		return null;
	}

	private CookiesConsentConfiguration _getCookiesConfiguration(
		RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			return ConfigurationProviderUtil.getGroupConfiguration(
				CookiesConsentConfiguration.class,
				themeDisplay.getScopeGroupId());
		}
		catch (Exception exception) {
			_log.error(
				"Unable to get cookies consent configuration", exception);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCookiesBannerDisplayContext.class);

	private List<ConsentCookieType> _optionalConsentCookieTypes;
	private List<ConsentCookieType> _requiredConsentCookieTypes;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.banner.web.internal.display.context;

import com.liferay.cookies.banner.web.internal.constants.CookiesBannerPortletKeys;
import com.liferay.cookies.configuration.CookiesConfigurationProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eduardo García
 */
public class CookiesBannerDisplayContext
	extends BaseCookiesBannerDisplayContext {

	public CookiesBannerDisplayContext(
		CookiesConfigurationProvider cookiesConfigurationProvider,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(cookiesConfigurationProvider, renderRequest, renderResponse);
	}

	public Object getConfigurationURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				PortalUtil.getLiferayPortletRequest(renderRequest));

		return PortletURLBuilder.create(
			requestBackedPortletURLFactory.createRenderURL(
				CookiesBannerPortletKeys.COOKIES_BANNER_CONFIGURATION)
		).setMVCPath(
			"/cookies_banner_configuration/view.jsp"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getContent(Locale locale) {
		LocalizedValuesMap contentLocalizedValuesMap =
			cookiesBannerConfiguration.content();

		return contentLocalizedValuesMap.get(locale);
	}

	public Map<String, Object> getContext(Locale locale) {
		LocalizedValuesMap titleLocalizedValuesMap =
			cookiesConsentConfiguration.title();

		return HashMapBuilder.<String, Object>put(
			"configurationNamespace",
			CookiesBannerPortletKeys.COOKIES_BANNER_CONFIGURATION
		).put(
			"configurationURL", getConfigurationURL()
		).put(
			"includeDeclineAllButton", isIncludeDeclineAllButton()
		).put(
			"optionalConsentCookieTypeNames",
			getConsentCookieTypeNamesJSONArray(getOptionalConsentCookieTypes())
		).put(
			"requiredConsentCookieTypeNames",
			getConsentCookieTypeNamesJSONArray(getRequiredConsentCookieTypes())
		).put(
			"title", titleLocalizedValuesMap.get(locale)
		).build();
	}

	public String getLinkDisplayText(Locale locale) {
		LocalizedValuesMap linkDisplayTextLocalizedValuesMap =
			cookiesBannerConfiguration.linkDisplayText();

		return linkDisplayTextLocalizedValuesMap.get(locale);
	}

	public String getPrivacyPolicyLink() {
		String privacyPolicyLink =
			cookiesBannerConfiguration.privacyPolicyLink();

		if (Validator.isNotNull(privacyPolicyLink)) {
			return privacyPolicyLink;
		}

		return StringPool.POUND;
	}

}
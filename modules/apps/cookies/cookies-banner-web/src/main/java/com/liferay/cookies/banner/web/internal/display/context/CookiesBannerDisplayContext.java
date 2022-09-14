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

import com.liferay.cookies.banner.web.internal.constants.CookiesBannerPortletKeys;
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
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(renderRequest, renderResponse);
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

	public Map<String, Object> getContext(Locale locale, long groupId)
		throws Exception {

		LocalizedValuesMap titleLocalizedValuesMap =
			cookiesConsentConfiguration.title();

		return HashMapBuilder.<String, Object>put(
			"configurationURL", getConfigurationURL()
		).put(
			"includeDeclineAllButton", isIncludeDeclineAllButton()
		).put(
			"optionalConsentCookieTypeNames",
			getConsentCookieTypeNamesJSONArray(
				getOptionalConsentCookieTypes(groupId))
		).put(
			"requiredConsentCookieTypeNames",
			getConsentCookieTypeNamesJSONArray(
				getRequiredConsentCookieTypes(groupId))
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
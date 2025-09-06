/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.banner.web.internal.display.context;

import com.liferay.cookies.configuration.CookiesConfigurationProvider;
import com.liferay.layout.utility.page.kernel.provider.LayoutUtilityPageEntryLayoutProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import java.util.Map;

/**
 * @author Christopher Kian
 */
public class ProductAnalyticsConsentPanelDisplayContext
	extends BaseProductAnalyticsDisplayContext {

	public ProductAnalyticsConsentPanelDisplayContext(
		CookiesConfigurationProvider cookiesConfigurationProvider,
		LayoutUtilityPageEntryLayoutProvider
			layoutUtilityPageEntryLayoutProvider,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(
			cookiesConfigurationProvider, layoutUtilityPageEntryLayoutProvider,
			renderRequest, renderResponse);
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

	public boolean isShowButtons() {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return !themeDisplay.isStatePopUp();
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.seo;

import com.liferay.layout.seo.contributor.LayoutSEOCanonicalURLContributor;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletPreferences;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
public abstract class BasePortletLayoutSEOCanonicalURLContributor
	implements LayoutSEOCanonicalURLContributor {

	@Override
	public Map<String, String[]> contributeURLParameters(
		HttpServletRequest httpServletRequest, long plid, String portletId) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!FeatureFlagManagerUtil.isEnabled(
				themeDisplay.getCompanyId(), "LPD-71164")) {

			return Collections.emptyMap();
		}

		SEOPortletPreferences seoPortletPreferences = getSEOPortletPreferences(
			portletPreferencesLocalService.fetchPreferences(
				themeDisplay.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId));

		if ((seoPortletPreferences == null) ||
			!seoPortletPreferences.isWebCrawlerIndexingEnabled()) {

			return Collections.emptyMap();
		}

		String parameterName = seoPortletPreferences.getSEOParameterName();

		if (Validator.isNull(parameterName)) {
			return Collections.emptyMap();
		}

		String[] values = httpServletRequest.getParameterValues(parameterName);

		if (values == null) {
			return Collections.emptyMap();
		}

		return HashMapBuilder.put(
			parameterName, values
		).build();
	}

	protected abstract SEOPortletPreferences getSEOPortletPreferences(
		PortletPreferences portletPreferences);

	@Reference
	protected PortletPreferencesLocalService portletPreferencesLocalService;

}
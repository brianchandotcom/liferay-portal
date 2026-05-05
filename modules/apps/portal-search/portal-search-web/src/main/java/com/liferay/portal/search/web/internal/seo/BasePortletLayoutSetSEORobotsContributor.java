/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.seo;

import com.liferay.layout.seo.contributor.LayoutSetSEORobotsContributor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
public abstract class BasePortletLayoutSetSEORobotsContributor
	implements LayoutSetSEORobotsContributor {

	@Override
	public Set<String> contributeDisallowURLEntries(LayoutSet layoutSet) {
		if (!FeatureFlagManagerUtil.isEnabled(
				layoutSet.getCompanyId(), "LPD-71164")) {

			return Collections.emptySet();
		}

		Set<String> disallowURLEntries = new HashSet<>();

		List<PortletPreferences> portletPreferencesList =
			portletPreferencesLocalService.getPortletPreferences(
				layoutSet.getCompanyId(), layoutSet.getGroupId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, getPortletId(),
				layoutSet.isPrivateLayout());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			Layout layout = layoutLocalService.fetchLayout(
				portletPreferences.getPlid());

			if ((layout == null) || layout.isDraftLayout() ||
				!layout.isPublished() || layout.isSystem()) {

				continue;
			}

			SEOPortletPreferences seoPortletPreferences =
				getSEOPortletPreferences(
					portletPreferencesLocalService.getPreferences(
						portletPreferences.getCompanyId(),
						portletPreferences.getOwnerId(),
						portletPreferences.getOwnerType(),
						portletPreferences.getPlid(),
						portletPreferences.getPortletId()));

			if ((seoPortletPreferences == null) ||
				seoPortletPreferences.isWebCrawlerIndexingEnabled()) {

				continue;
			}

			String parameterName = seoPortletPreferences.getSEOParameterName();

			if (Validator.isNull(parameterName)) {
				continue;
			}

			disallowURLEntries.add(
				StringBundler.concat(
					layout.getFriendlyURL(), "*&", parameterName,
					StringPool.EQUAL));
			disallowURLEntries.add(
				StringBundler.concat(
					layout.getFriendlyURL(), "*?", parameterName,
					StringPool.EQUAL));
		}

		return disallowURLEntries;
	}

	protected abstract String getPortletId();

	protected abstract SEOPortletPreferences getSEOPortletPreferences(
		jakarta.portlet.PortletPreferences portletPreferences);

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected PortletPreferencesLocalService portletPreferencesLocalService;

}
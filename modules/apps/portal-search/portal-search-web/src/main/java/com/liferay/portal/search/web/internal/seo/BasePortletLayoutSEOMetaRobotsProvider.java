/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.seo;

import com.liferay.layout.seo.provider.LayoutSEOMetaRobotsProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.web.internal.portlet.shared.task.helper.PortletSharedRequestHelper;

import jakarta.portlet.PortletPreferences;
import jakarta.portlet.RenderRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
public abstract class BasePortletLayoutSEOMetaRobotsProvider
	implements LayoutSEOMetaRobotsProvider {

	@Override
	public String getContent(RenderRequest renderRequest) {
		if (!FeatureFlagManagerUtil.isEnabled(
				portal.getCompanyId(renderRequest), "LPD-71164")) {

			return StringPool.BLANK;
		}

		SEOPortletPreferences seoPortletPreferences = getSEOPortletPreferences(
			renderRequest.getPreferences());

		if ((seoPortletPreferences == null) ||
			seoPortletPreferences.isWebCrawlerIndexingEnabled()) {

			return StringPool.BLANK;
		}

		String parameter = portletSharedRequestHelper.getParameter(
			seoPortletPreferences.getSEOParameterName(), renderRequest);

		if (parameter != null) {
			return "noindex, nofollow";
		}

		return StringPool.BLANK;
	}

	@Override
	public boolean providesContent(PortletPreferences portletPreferences) {
		SEOPortletPreferences seoPortletPreferences = getSEOPortletPreferences(
			portletPreferences);

		if ((seoPortletPreferences == null) ||
			seoPortletPreferences.isWebCrawlerIndexingEnabled()) {

			return false;
		}

		return true;
	}

	protected abstract SEOPortletPreferences getSEOPortletPreferences(
		PortletPreferences portletPreferences);

	@Reference
	protected Portal portal;

	@Reference
	protected PortletSharedRequestHelper portletSharedRequestHelper;

}
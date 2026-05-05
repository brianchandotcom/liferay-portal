/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.site.facet.portlet.seo;

import com.liferay.layout.seo.provider.LayoutSEOMetaRobotsProvider;
import com.liferay.portal.search.web.internal.seo.BasePortletLayoutSEOMetaRobotsProvider;
import com.liferay.portal.search.web.internal.seo.SEOPortletPreferences;
import com.liferay.portal.search.web.internal.site.facet.constants.SiteFacetPortletKeys;
import com.liferay.portal.search.web.internal.site.facet.portlet.SiteFacetPortletPreferencesImpl;

import jakarta.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Amos Fong
 */
@Component(
	property = "jakarta.portlet.name=" + SiteFacetPortletKeys.SITE_FACET,
	service = LayoutSEOMetaRobotsProvider.class
)
public class SiteFacetPortletLayoutSEOMetaRobotsProvider
	extends BasePortletLayoutSEOMetaRobotsProvider {

	@Override
	protected SEOPortletPreferences getSEOPortletPreferences(
		PortletPreferences portletPreferences) {

		return new SiteFacetPortletPreferencesImpl(portletPreferences);
	}

}
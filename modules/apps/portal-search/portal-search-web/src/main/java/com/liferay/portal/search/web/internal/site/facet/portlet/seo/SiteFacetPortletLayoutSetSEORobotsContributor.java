/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.site.facet.portlet.seo;

import com.liferay.layout.seo.contributor.LayoutSetSEORobotsContributor;
import com.liferay.portal.search.web.internal.seo.BasePortletLayoutSetSEORobotsContributor;
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
	service = LayoutSetSEORobotsContributor.class
)
public class SiteFacetPortletLayoutSetSEORobotsContributor
	extends BasePortletLayoutSetSEORobotsContributor {

	@Override
	protected String getPortletId() {
		return SiteFacetPortletKeys.SITE_FACET;
	}

	@Override
	protected SEOPortletPreferences getSEOPortletPreferences(
		PortletPreferences portletPreferences) {

		return new SiteFacetPortletPreferencesImpl(portletPreferences);
	}

}
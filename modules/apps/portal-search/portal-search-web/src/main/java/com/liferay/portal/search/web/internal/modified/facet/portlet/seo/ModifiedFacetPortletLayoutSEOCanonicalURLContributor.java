/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.modified.facet.portlet.seo;

import com.liferay.layout.seo.contributor.LayoutSEOCanonicalURLContributor;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;
import com.liferay.portal.search.web.internal.modified.facet.portlet.ModifiedFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.seo.BasePortletLayoutSEOCanonicalURLContributor;
import com.liferay.portal.search.web.internal.seo.SEOPortletPreferences;

import jakarta.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Amos Fong
 */
@Component(
	property = "jakarta.portlet.name=" + ModifiedFacetPortletKeys.MODIFIED_FACET,
	service = LayoutSEOCanonicalURLContributor.class
)
public class ModifiedFacetPortletLayoutSEOCanonicalURLContributor
	extends BasePortletLayoutSEOCanonicalURLContributor {

	@Override
	protected SEOPortletPreferences getSEOPortletPreferences(
		PortletPreferences portletPreferences) {

		return new ModifiedFacetPortletPreferencesImpl(portletPreferences);
	}

}
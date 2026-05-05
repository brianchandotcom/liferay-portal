/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.tag.facet.portlet.seo;

import com.liferay.layout.seo.contributor.LayoutSEOCanonicalURLContributor;
import com.liferay.portal.search.web.internal.seo.BasePortletLayoutSEOCanonicalURLContributor;
import com.liferay.portal.search.web.internal.seo.SEOPortletPreferences;
import com.liferay.portal.search.web.internal.tag.facet.constants.TagFacetPortletKeys;
import com.liferay.portal.search.web.internal.tag.facet.portlet.TagFacetPortletPreferencesImpl;

import jakarta.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Amos Fong
 */
@Component(
	property = "jakarta.portlet.name=" + TagFacetPortletKeys.TAG_FACET,
	service = LayoutSEOCanonicalURLContributor.class
)
public class TagFacetPortletLayoutSEOCanonicalURLContributor
	extends BasePortletLayoutSEOCanonicalURLContributor {

	@Override
	protected SEOPortletPreferences getSEOPortletPreferences(
		PortletPreferences portletPreferences) {

		return new TagFacetPortletPreferencesImpl(portletPreferences);
	}

}
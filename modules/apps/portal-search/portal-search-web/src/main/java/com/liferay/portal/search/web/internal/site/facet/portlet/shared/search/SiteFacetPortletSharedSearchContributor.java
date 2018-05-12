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

package com.liferay.portal.search.web.internal.site.facet.portlet.shared.search;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ScopeFacetFactory;
import com.liferay.portal.search.web.internal.site.facet.constants.SiteFacetPortletKeys;
import com.liferay.portal.search.web.internal.site.facet.portlet.ScopeFacetBuilder;
import com.liferay.portal.search.web.internal.site.facet.portlet.SiteFacetPortletPreferences;
import com.liferay.portal.search.web.internal.site.facet.portlet.SiteFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SiteFacetPortletKeys.SITE_FACET
)
public class SiteFacetPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SiteFacetPortletPreferences siteFacetPortletPreferences =
			new SiteFacetPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferences());

		Facet facet = buildFacet(
			siteFacetPortletPreferences, portletSharedSearchSettings);

		portletSharedSearchSettings.addFacet(facet);
	}

	protected Facet buildFacet(
		SiteFacetPortletPreferences siteFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		ScopeFacetBuilder scopeFacetBuilder = new ScopeFacetBuilder(
			scopeFacetFactory);

		scopeFacetBuilder.setFrequencyThreshold(
			siteFacetPortletPreferences.getFrequencyThreshold());
		scopeFacetBuilder.setMaxTerms(
			siteFacetPortletPreferences.getMaxTerms());
		scopeFacetBuilder.setSearchContext(
			portletSharedSearchSettings.getSearchContext());

		Optional<String[]> parameterValuesOptional =
			portletSharedSearchSettings.getParameterValues(
				siteFacetPortletPreferences.getParameterName());

		parameterValuesOptional.ifPresent(scopeFacetBuilder::setSelectedSites);

		return scopeFacetBuilder.build();
	}

	@Reference
	protected ScopeFacetFactory scopeFacetFactory;

}
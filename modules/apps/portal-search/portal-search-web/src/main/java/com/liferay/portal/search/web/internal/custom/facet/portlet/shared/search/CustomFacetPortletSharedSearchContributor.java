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

package com.liferay.portal.search.web.internal.custom.facet.portlet.shared.search;

import com.liferay.portal.search.facet.custom.CustomFacetSearchContributor;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.internal.custom.facet.constants.CustomFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferences;
import com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CustomFacetPortletKeys.CUSTOM_FACET,
	service = PortletSharedSearchContributor.class
)
public class CustomFacetPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		CustomFacetPortletPreferences customFacetPortletPreferences =
			new CustomFacetPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferencesOptional());

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				customFacetPortletPreferences.getFederatedSearchKeyOptional());

		Optional<String> fieldToAggregateOptional =
			customFacetPortletPreferences.getAggregationFieldOptional();

		fieldToAggregateOptional.ifPresent(
			fieldToAggregate -> customFacetSearchContributor.contribute(
				searchRequestBuilder,
				customFacetBuilder -> customFacetBuilder.aggregationName(
					portletSharedSearchSettings.getPortletId()
				).fieldToAggregate(
					fieldToAggregate
				).frequencyThreshold(
					customFacetPortletPreferences.getFrequencyThreshold()
				).maxTerms(
					customFacetPortletPreferences.getMaxTerms()
				).selectedValues(
					portletSharedSearchSettings.getParameterValues(
						getParameterName(customFacetPortletPreferences))
				)));
	}

	protected String getParameterName(
		CustomFacetPortletPreferences customFacetPortletPreferences) {

		Optional<String> optional = Stream.of(
			customFacetPortletPreferences.getParameterNameOptional(),
			customFacetPortletPreferences.getAggregationFieldOptional()
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).findFirst();

		return optional.orElse("customfield");
	}

	@Reference
	protected CustomFacetSearchContributor customFacetSearchContributor;

}
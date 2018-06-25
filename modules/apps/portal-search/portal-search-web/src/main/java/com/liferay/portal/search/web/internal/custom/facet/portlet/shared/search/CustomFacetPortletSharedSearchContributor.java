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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.search.facet.custom.CustomFacetFactory;
import com.liferay.portal.search.web.internal.custom.facet.builder.CustomFacetBuilder;
import com.liferay.portal.search.web.internal.custom.facet.constants.CustomFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferences;
import com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CustomFacetPortletKeys.CUSTOM_FACET
)
public class CustomFacetPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		CustomFacetPortletPreferences customFacetPortletPreferences =
			new CustomFacetPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferences());

		Optional<String> fieldToAggregateOptional =
			customFacetPortletPreferences.getAggregationFieldOptional();

		fieldToAggregateOptional.ifPresent(
			fieldToAggregate -> {
				Facet facet = buildFacet(
					fieldToAggregate, customFacetPortletPreferences,
					portletSharedSearchSettings);

				portletSharedSearchSettings.addFacet(facet);
			});
	}

	protected Facet buildFacet(
		String fieldToAggregate,
		CustomFacetPortletPreferences customFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		CustomFacetBuilder customFacetBuilder = new CustomFacetBuilder(
			customFacetFactory);

		customFacetBuilder.setAggregationName(
			getAggregationName(
				customFacetPortletPreferences,
				portletSharedSearchSettings.getPortletId()));
		customFacetBuilder.setFieldToAggregate(fieldToAggregate);
		customFacetBuilder.setSearchContext(
			portletSharedSearchSettings.getSearchContext());

		copy(
			() -> portletSharedSearchSettings.getParameterValues(
				getParameterName(customFacetPortletPreferences)),
			customFacetBuilder::setSelectedValues);

		return customFacetBuilder.build();
	}

	protected <T> void copy(Supplier<Optional<T>> from, Consumer<T> to) {
		Optional<T> optional = from.get();

		optional.ifPresent(to);
	}

	protected String getAggregationName(
		CustomFacetPortletPreferences customFacetPortletPreferences,
		String portletId) {

		return customFacetPortletPreferences.getAggregationFieldString() +
			StringPool.PERIOD + portletId;
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
	protected CustomFacetFactory customFacetFactory;

}
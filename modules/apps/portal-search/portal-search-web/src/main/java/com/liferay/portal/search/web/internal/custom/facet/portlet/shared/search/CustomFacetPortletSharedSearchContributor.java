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

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.facet.custom.CustomFacetSearchContributor;
import com.liferay.portal.search.facet.nested.NestedFacetSearchContributor;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.internal.custom.facet.constants.CustomFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferences;
import com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Locale;
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

		Optional<String> fieldToAggregateOptional =
			customFacetPortletPreferences.getAggregationFieldOptional();

		if (!fieldToAggregateOptional.isPresent()) {
			return;
		}

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				customFacetPortletPreferences.getFederatedSearchKeyOptional());

		String fieldToAggregate = fieldToAggregateOptional.get();

		if (!ddmIndexer.isLegacyDDMIndexFieldsEnabled() &&
			fieldToAggregate.startsWith(DDMIndexer.DDM_FIELD_PREFIX)) {

			_contributeWithNestedFacet(
				fieldToAggregate, searchRequestBuilder,
				portletSharedSearchSettings, customFacetPortletPreferences);
		}
		else {
			_contributeWithCustomFacet(
				fieldToAggregate, searchRequestBuilder,
				portletSharedSearchSettings, customFacetPortletPreferences);
		}
	}

	@Reference
	protected CustomFacetSearchContributor customFacetSearchContributor;

	@Reference
	protected DDMIndexer ddmIndexer;

	@Reference
	protected NestedFacetSearchContributor nestedFacetSearchContributor;

	private void _contributeWithCustomFacet(
		String fieldToAggregate, SearchRequestBuilder searchRequestBuilder,
		PortletSharedSearchSettings portletSharedSearchSettings,
		CustomFacetPortletPreferences customFacetPortletPreferences) {

		customFacetSearchContributor.contribute(
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
					_getParameterName(customFacetPortletPreferences))
			));
	}

	private void _contributeWithNestedFacet(
		String fieldToAggregate, SearchRequestBuilder searchRequestBuilder,
		PortletSharedSearchSettings portletSharedSearchSettings,
		CustomFacetPortletPreferences customFacetPortletPreferences) {

		String[] ddmStructureParts = StringUtil.split(
			fieldToAggregate, DDMIndexer.DDM_FIELD_SEPARATOR);

		String nestedFieldToAggregate = ddmIndexer.getValueFieldName(
			ddmStructureParts[1], _getSuffixLocale(ddmStructureParts[3]));

		nestedFacetSearchContributor.contribute(
			searchRequestBuilder,
			nestedFacetBuilder -> nestedFacetBuilder.aggregationName(
				portletSharedSearchSettings.getPortletId()
			).fieldToAggregate(
				StringBundler.concat(
					DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
					nestedFieldToAggregate)
			).filterField(
				StringBundler.concat(
					DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
					DDMIndexer.DDM_FIELD_NAME)
			).filterValue(
				fieldToAggregate
			).frequencyThreshold(
				customFacetPortletPreferences.getFrequencyThreshold()
			).maxTerms(
				customFacetPortletPreferences.getMaxTerms()
			).path(
				DDMIndexer.DDM_FIELD_ARRAY
			).selectedValues(
				portletSharedSearchSettings.getParameterValues(
					_getParameterName(customFacetPortletPreferences))
			));
	}

	private String _getParameterName(
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

	private Locale _getSuffixLocale(String string) {
		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			String availableLanguageId = LanguageUtil.getLanguageId(
				availableLocale);

			if (string.endsWith(availableLanguageId)) {
				return availableLocale;
			}
		}

		return null;
	}

}
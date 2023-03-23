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
import com.liferay.portal.kernel.language.Language;
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
				customFacetPortletPreferences.getFederatedSearchKey());

		String fieldToAggregate = fieldToAggregateOptional.get();

		if (!ddmIndexer.isLegacyDDMIndexFieldsEnabled() &&
			fieldToAggregate.startsWith(DDMIndexer.DDM_FIELD_ARRAY)) {

			String[] ddmStructureParts = StringUtil.split(
				fieldToAggregate, StringPool.PERIOD);

			if (ddmStructureParts.length != 3) {
				return;
			}

			_contributeWithNestedFieldFacet(
				customFacetPortletPreferences, ddmStructureParts[2],
				DDMIndexer.DDM_FIELD_NAME, ddmStructureParts[1],
				DDMIndexer.DDM_FIELD_ARRAY, portletSharedSearchSettings,
				searchRequestBuilder);
		}
		else if (!ddmIndexer.isLegacyDDMIndexFieldsEnabled() &&
				 fieldToAggregate.startsWith(DDMIndexer.DDM_FIELD_PREFIX)) {

			String[] ddmStructureParts = StringUtil.split(
				fieldToAggregate, DDMIndexer.DDM_FIELD_SEPARATOR);

			_contributeWithNestedFieldFacet(
				customFacetPortletPreferences,
				ddmIndexer.getValueFieldName(
					ddmStructureParts[1],
					_getSuffixLocale(ddmStructureParts[3])),
				DDMIndexer.DDM_FIELD_NAME, fieldToAggregate,
				DDMIndexer.DDM_FIELD_ARRAY, portletSharedSearchSettings,
				searchRequestBuilder);
		}
		else if (fieldToAggregate.startsWith(_OBJECT_FIELD_ARRAY)) {
			String[] objectStructureParts = StringUtil.split(
				fieldToAggregate, StringPool.PERIOD);

			if (objectStructureParts.length != 3) {
				return;
			}

			_contributeWithNestedFieldFacet(
				customFacetPortletPreferences, objectStructureParts[2],
				_OBJECT_FIELD_NAME, objectStructureParts[1],
				_OBJECT_FIELD_ARRAY, portletSharedSearchSettings,
				searchRequestBuilder);
		}
		else {
			_contributeWithCustomFacet(
				customFacetPortletPreferences, fieldToAggregate,
				portletSharedSearchSettings, searchRequestBuilder);
		}
	}

	@Reference
	protected CustomFacetSearchContributor customFacetSearchContributor;

	@Reference
	protected DDMIndexer ddmIndexer;

	@Reference
	protected NestedFacetSearchContributor nestedFacetSearchContributor;

	private void _contributeWithCustomFacet(
		CustomFacetPortletPreferences customFacetPortletPreferences,
		String fieldToAggregate,
		PortletSharedSearchSettings portletSharedSearchSettings,
		SearchRequestBuilder searchRequestBuilder) {

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

	private void _contributeWithNestedFieldFacet(
		CustomFacetPortletPreferences customFacetPortletPreferences,
		String fieldToAggregate, String filterField, String filterValue,
		String path, PortletSharedSearchSettings portletSharedSearchSettings,
		SearchRequestBuilder searchRequestBuilder) {

		nestedFacetSearchContributor.contribute(
			searchRequestBuilder,
			nestedFacetBuilder -> nestedFacetBuilder.aggregationName(
				portletSharedSearchSettings.getPortletId()
			).fieldToAggregate(
				StringBundler.concat(path, StringPool.PERIOD, fieldToAggregate)
			).filterField(
				StringBundler.concat(path, StringPool.PERIOD, filterField)
			).filterValue(
				filterValue
			).frequencyThreshold(
				customFacetPortletPreferences.getFrequencyThreshold()
			).maxTerms(
				customFacetPortletPreferences.getMaxTerms()
			).path(
				path
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
		for (Locale availableLocale : _language.getAvailableLocales()) {
			String availableLanguageId = _language.getLanguageId(
				availableLocale);

			if (string.endsWith(availableLanguageId)) {
				return availableLocale;
			}
		}

		return null;
	}

	private static final String _OBJECT_FIELD_ARRAY = "nestedFieldArray";

	private static final String _OBJECT_FIELD_NAME = "fieldName";

	@Reference
	private Language _language;

}
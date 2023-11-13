/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.facet.portlet.shared.search;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.bucket.Range;
import com.liferay.portal.search.facet.date.range.DateRangeFacetSearchContributor;
import com.liferay.portal.search.facet.nested.NestedFacetSearchContributor;
import com.liferay.portal.search.filter.DateRangeFilterBuilder;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.web.internal.date.facet.constants.DateFacetPortletKeys;
import com.liferay.portal.search.web.internal.date.facet.portlet.DateFacetPortletPreferences;
import com.liferay.portal.search.web.internal.date.facet.portlet.DateFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.date.range.BaseDateRangeFacetPortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	property = "javax.portlet.name=" + DateFacetPortletKeys.DATE_FACET,
	service = PortletSharedSearchContributor.class
)
public class DateFacetPortletSharedSearchContributor
	extends BaseDateRangeFacetPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		DateFacetPortletPreferences dateFacetPortletPreferences =
			new DateFacetPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferences());

		String aggregationField =
			dateFacetPortletPreferences.getAggregationField();

		if (Validator.isNull(aggregationField)) {
			return;
		}

		JSONArray rangesJSONArray = getRangesJSONArray(
			CalendarFactoryUtil.getCalendar(),
			dateFacetPortletPreferences.getRangesJSONArray());

		List<String> selectedRangeStrings = getSelectedRangeStrings(
			dateFacetPortletPreferences.getParameterName(),
			portletSharedSearchSettings, rangesJSONArray);

		String selectedCustomRangeString = getSelectedCustomRangeString(
			dateFacetPortletPreferences.getParameterName(),
			portletSharedSearchSettings);

		if (!Validator.isBlank(selectedCustomRangeString)) {
			addCustomRange(
				rangesJSONArray, selectedCustomRangeString,
				selectedRangeStrings);
		}

		if (!_ddmIndexer.isLegacyDDMIndexFieldsEnabled() &&
			aggregationField.startsWith(DDMIndexer.DDM_FIELD_ARRAY)) {

			_contributeWithDDMFieldArray(
				dateFacetPortletPreferences, portletSharedSearchSettings,
				rangesJSONArray, selectedRangeStrings);
		}
		else if (!_ddmIndexer.isLegacyDDMIndexFieldsEnabled() &&
				 aggregationField.startsWith(DDMIndexer.DDM_FIELD_PREFIX)) {

			_contributeWithDDMField(
				dateFacetPortletPreferences, portletSharedSearchSettings,
				rangesJSONArray, selectedRangeStrings);
		}
		else if (aggregationField.startsWith("nestedFieldArray")) {
			_contributeWithNestedFieldArray(
				dateFacetPortletPreferences, portletSharedSearchSettings,
				rangesJSONArray, selectedRangeStrings);
		}
		else {
			_contributeWithDateRangeFacet(
				dateFacetPortletPreferences, portletSharedSearchSettings,
				rangesJSONArray, selectedRangeStrings);
		}
	}

	private void _contributeWithDateRangeFacet(
		DateFacetPortletPreferences dateFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings,
		JSONArray rangesJSONArray, List<String> selectedRangeStrings) {

		_dateRangeFacetSearchContributor.contribute(
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				dateFacetPortletPreferences.getFederatedSearchKey()),
			dateRangeFacetBuilder -> dateRangeFacetBuilder.aggregationName(
				portletSharedSearchSettings.getPortletId()
			).field(
				dateFacetPortletPreferences.getAggregationField()
			).format(
				"yyyyMMddHHmmss"
			).frequencyThreshold(
				dateFacetPortletPreferences.getFrequencyThreshold()
			).order(
				dateFacetPortletPreferences.getOrder()
			).rangesJSONArray(
				rangesJSONArray
			).selectedRanges(
				selectedRangeStrings.toArray(new String[0])
			));
	}

	private void _contributeWithDDMField(
		DateFacetPortletPreferences dateFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings,
		JSONArray rangesJSONArray, List<String> selectedRangeStrings) {

		String[] ddmFieldParts = StringUtil.split(
			dateFacetPortletPreferences.getAggregationField(),
			DDMIndexer.DDM_FIELD_SEPARATOR);

		if (ddmFieldParts.length != 4) {
			return;
		}

		_contributeWithNestedFieldFacet(
			dateFacetPortletPreferences,
			_ddmIndexer.getValueFieldName(
				ddmFieldParts[1], _getLocaleFromSuffix(ddmFieldParts[3])),
			DDMIndexer.DDM_FIELD_NAME,
			dateFacetPortletPreferences.getAggregationField(),
			DDMIndexer.DDM_FIELD_ARRAY, portletSharedSearchSettings,
			rangesJSONArray, selectedRangeStrings);
	}

	private void _contributeWithDDMFieldArray(
		DateFacetPortletPreferences dateFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings,
		JSONArray rangesJSONArray, List<String> selectedRangeStrings) {

		String[] ddmFieldArrayParts = StringUtil.split(
			dateFacetPortletPreferences.getAggregationField(),
			StringPool.PERIOD);

		if (ddmFieldArrayParts.length != 3) {
			return;
		}

		_contributeWithNestedFieldFacet(
			dateFacetPortletPreferences, ddmFieldArrayParts[2],
			DDMIndexer.DDM_FIELD_NAME, ddmFieldArrayParts[1],
			DDMIndexer.DDM_FIELD_ARRAY, portletSharedSearchSettings,
			rangesJSONArray, selectedRangeStrings);
	}

	private void _contributeWithNestedFieldArray(
		DateFacetPortletPreferences dateFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings,
		JSONArray rangesJSONArray, List<String> selectedRangeStrings) {

		String[] fieldToAggregrateParts = StringUtil.split(
			dateFacetPortletPreferences.getAggregationField(),
			StringPool.PERIOD);

		if (fieldToAggregrateParts.length != 3) {
			return;
		}

		_contributeWithNestedFieldFacet(
			dateFacetPortletPreferences, fieldToAggregrateParts[2], "fieldName",
			fieldToAggregrateParts[1], "nestedFieldArray",
			portletSharedSearchSettings, rangesJSONArray, selectedRangeStrings);
	}

	private void _contributeWithNestedFieldFacet(
		DateFacetPortletPreferences dateFacetPortletPreferences,
		String fieldToAggregate, String filterField, String filterValue,
		String path, PortletSharedSearchSettings portletSharedSearchSettings,
		JSONArray rangesJSONArray, List<String> selectedRangeStrings) {

		String fieldToAggregateWithPath = StringBundler.concat(
			path, StringPool.PERIOD, fieldToAggregate);

		_nestedFacetSearchContributor.contribute(
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				dateFacetPortletPreferences.getFederatedSearchKey()),
			nestedFacetBuilder -> nestedFacetBuilder.aggregationName(
				portletSharedSearchSettings.getPortletId()
			).additionalFacetConfigurationData(
				JSONUtil.put("ranges", rangesJSONArray)
			).childAggregation(
				_getChildAggregation(
					fieldToAggregateWithPath, portletSharedSearchSettings,
					rangesJSONArray)
			).childAggregationValuesFilter(
				_getChildAggregationFilter(
					fieldToAggregateWithPath, selectedRangeStrings)
			).fieldToAggregate(
				fieldToAggregateWithPath
			).filterField(
				StringBundler.concat(path, StringPool.PERIOD, filterField)
			).filterValue(
				filterValue
			).frequencyThreshold(
				dateFacetPortletPreferences.getFrequencyThreshold()
			).path(
				path
			).selectedValues(
				selectedRangeStrings.toArray(new String[0])
			));
	}

	private Aggregation _getChildAggregation(
		String fieldToAggregate,
		PortletSharedSearchSettings portletSharedSearchSettings,
		JSONArray rangesJSONArray) {

		DateRangeAggregation dateRangeAggregation = _aggregations.dateRange(
			portletSharedSearchSettings.getPortletId(), fieldToAggregate);

		dateRangeAggregation.setFormat("yyyyMMddHHmmss");

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject rangeJSONObject = rangesJSONArray.getJSONObject(i);

			String label = rangeJSONObject.getString("label");

			if (Validator.isBlank(label)) {
				label = rangeJSONObject.getString("range");
			}

			String range = rangeJSONObject.getString("range");

			String[] rangeParts = RangeParserUtil.parserRange(range);

			dateRangeAggregation.addRange(
				new Range(label, rangeParts[0], rangeParts[1]));
		}

		return dateRangeAggregation;
	}

	private Filter _getChildAggregationFilter(
		String fieldToAggregate, List<String> selectedRangeStrings) {

		if (selectedRangeStrings.isEmpty()) {
			return null;
		}

		BooleanFilter booleanFilter = new BooleanFilter();

		for (String selection : selectedRangeStrings) {
			String[] rangeParts = RangeParserUtil.parserRange(selection);

			String from = rangeParts[0];
			String to = rangeParts[1];

			if (Validator.isNull(from) && Validator.isNull(to)) {
				continue;
			}

			DateRangeFilterBuilder dateRangeFilterBuilder =
				_filterBuilders.dateRangeFilterBuilder();

			dateRangeFilterBuilder.setFieldName(fieldToAggregate);
			dateRangeFilterBuilder.setFrom(from);
			dateRangeFilterBuilder.setIncludeLower(true);
			dateRangeFilterBuilder.setIncludeUpper(true);
			dateRangeFilterBuilder.setTo(to);

			booleanFilter.add(
				dateRangeFilterBuilder.build(), BooleanClauseOccur.SHOULD);
		}

		return booleanFilter;
	}

	private Locale _getLocaleFromSuffix(String string) {
		for (Locale availableLocale : _language.getAvailableLocales()) {
			String availableLanguageId = _language.getLanguageId(
				availableLocale);

			if (string.endsWith(availableLanguageId)) {
				return availableLocale;
			}
		}

		return null;
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private DateRangeFacetSearchContributor _dateRangeFacetSearchContributor;

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private FilterBuilders _filterBuilders;

	@Reference
	private Language _language;

	@Reference
	private NestedFacetSearchContributor _nestedFacetSearchContributor;

}
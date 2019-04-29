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

package com.liferay.portal.search.web.internal.modified.facet.builder;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactory;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.modified.ModifiedFacetFactory;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lino Alves
 */
public class ModifiedFacetBuilder {

	public ModifiedFacetBuilder(
		ModifiedFacetFactory modifiedFacetFactory,
		CalendarFactory calendarFactory, DateFormatFactory dateFormatFactory,
		JSONFactory jsonFactory) {

		_modifiedFacetFactory = modifiedFacetFactory;
		_calendarFactory = calendarFactory;
		_jsonFactory = jsonFactory;

		_dateRangeFactory = new DateRangeFactory(dateFormatFactory);
	}

	public Facet build() {
		Facet facet = _modifiedFacetFactory.newInstance(_searchContext);

		facet.setFacetConfiguration(buildFacetConfiguration(facet));

		String rangeString = _getSelectedRangeString(facet);

		if (!Validator.isBlank(rangeString)) {
			facet.select(rangeString);
		}

		return facet;
	}

	public void setCustomRangeFrom(String customRangeFrom) {
		_customRangeFrom = customRangeFrom;
	}

	public void setCustomRangeTo(String customRangeTo) {
		_customRangeTo = customRangeTo;
	}

	public void setRangesJSONArray(JSONArray rangesJSONArray) {
		_rangesJSONArray = rangesJSONArray;
	}

	public void setSearchContext(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	public void setSelectedRanges(String... selectedRanges) {
		_selectedRanges = selectedRanges;
	}

	protected FacetConfiguration buildFacetConfiguration(Facet facet) {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setDataJSONObject(_jsonFactory.createJSONObject());

		facetConfiguration.setFieldName(facet.getFieldName());
		facetConfiguration.setLabel("any-time");
		facetConfiguration.setOrder("OrderHitsDesc");
		facetConfiguration.setStatic(false);
		facetConfiguration.setWeight(1.0);

		ModifiedFacetConfiguration modifiedFacetConfiguration =
			new ModifiedFacetConfigurationImpl(facetConfiguration);

		modifiedFacetConfiguration.setRangesJSONArray(
			getRangesJSONArray(_calendarFactory.getCalendar()));

		return facetConfiguration;
	}

	protected JSONArray getDefaultRangesJSONArray(Calendar calendar) {
		JSONArray rangesJSONArray = _jsonFactory.createJSONArray();

		Map<String, String> map = _dateRangeFactory.getRangeStrings(calendar);

		map.forEach(
			(key, value) -> {
				JSONObject range = _jsonFactory.createJSONObject();

				range.put(
					"label", key
				).put(
					"range", value
				);

				rangesJSONArray.put(range);
			});

		return rangesJSONArray;
	}

	protected JSONArray getRangesJSONArray(Calendar calendar) {
		if (_rangesJSONArray == null) {
			_rangesJSONArray = getDefaultRangesJSONArray(calendar);
		}

		return _rangesJSONArray;
	}

	protected Map<String, String> getRangesMap(JSONArray rangesJSONArray) {
		Map<String, String> rangesMap = new HashMap<>();

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject rangeJSONObject = rangesJSONArray.getJSONObject(i);

			rangesMap.put(
				rangeJSONObject.getString("label"),
				rangeJSONObject.getString("range"));
		}

		return rangesMap;
	}

	private String _getSelectedRangeString(Facet facet) {
		if (!Validator.isBlank(_customRangeFrom) &&
			!Validator.isBlank(_customRangeTo)) {

			String rangeString = _dateRangeFactory.getRangeString(
				_customRangeFrom, _customRangeTo);

			_searchContext.setAttribute(facet.getFieldId(), rangeString);

			return rangeString;
		}

		if (!ArrayUtil.isEmpty(_selectedRanges)) {
			Map<String, String> rangesMap = getRangesMap(_rangesJSONArray);

			String selectedRange = _selectedRanges[_selectedRanges.length - 1];

			if (rangesMap.containsKey(selectedRange)) {
				return rangesMap.get(selectedRange);
			}

			return _dateRangeFactory.getRangeString(
				selectedRange, _calendarFactory.getCalendar());
		}

		return null;
	}

	private final CalendarFactory _calendarFactory;
	private String _customRangeFrom;
	private String _customRangeTo;
	private final DateRangeFactory _dateRangeFactory;
	private final JSONFactory _jsonFactory;
	private final ModifiedFacetFactory _modifiedFacetFactory;
	private JSONArray _rangesJSONArray;
	private SearchContext _searchContext;
	private String[] _selectedRanges;

}
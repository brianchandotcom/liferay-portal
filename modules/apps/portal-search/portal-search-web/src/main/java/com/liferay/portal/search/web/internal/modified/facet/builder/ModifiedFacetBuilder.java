/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.modified.facet.builder;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
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
		DateFormatFactory dateFormatFactory, JSONFactory jsonFactory) {

		_modifiedFacetFactory = modifiedFacetFactory;
		_jsonFactory = jsonFactory;

		_dateRangeFactory = new DateRangeFactory(dateFormatFactory);
	}

	public Facet build() {
		Facet facet = _modifiedFacetFactory.newInstance(_searchContext);

		facet.setFacetConfiguration(_buildFacetConfiguration(facet));

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

	public void setOrder(String order) {
		_order = order;
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

	protected JSONArray getRangesJSONArray(Calendar calendar) {
		if (_rangesJSONArray == null) {
			_rangesJSONArray = _getDefaultRangesJSONArray(calendar);
		}

		return _rangesJSONArray;
	}

	private FacetConfiguration _buildFacetConfiguration(Facet facet) {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setFieldName(facet.getFieldName());
		facetConfiguration.setLabel("any-time");
		facetConfiguration.setOrder(_order);
		facetConfiguration.setStatic(false);
		facetConfiguration.setWeight(1.0);

		JSONObject jsonObject = facetConfiguration.getData();

		jsonObject.put(
			"ranges", getRangesJSONArray(CalendarFactoryUtil.getCalendar()));

		return facetConfiguration;
	}

	private JSONArray _getDefaultRangesJSONArray(Calendar calendar) {
		JSONArray rangesJSONArray = _jsonFactory.createJSONArray();

		Map<String, String> map = _dateRangeFactory.getRangeStrings(calendar);

		map.forEach(
			(key, value) -> {
				JSONObject rangeJSONObject = _jsonFactory.createJSONObject();

				rangeJSONObject.put(
					"label", key
				).put(
					"range", value
				);

				rangesJSONArray.put(rangeJSONObject);
			});

		return rangesJSONArray;
	}

	private Map<String, String> _getRangesMap(JSONArray rangesJSONArray) {
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
			Map<String, String> rangesMap = _getRangesMap(_rangesJSONArray);

			String selectedRange = _selectedRanges[_selectedRanges.length - 1];

			if (rangesMap.containsKey(selectedRange)) {
				return rangesMap.get(selectedRange);
			}

			return _dateRangeFactory.getRangeString(
				selectedRange, CalendarFactoryUtil.getCalendar());
		}

		return null;
	}

	private String _customRangeFrom;
	private String _customRangeTo;
	private final DateRangeFactory _dateRangeFactory;
	private final JSONFactory _jsonFactory;
	private final ModifiedFacetFactory _modifiedFacetFactory;
	private String _order;
	private JSONArray _rangesJSONArray;
	private SearchContext _searchContext;
	private String[] _selectedRanges;

}
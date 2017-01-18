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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ModifiedFacetFactory;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactory;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;
import java.util.Map;

/**
 * @author Lino Alves
 */
public class ModifiedFacetBuilder {

	public ModifiedFacetBuilder(
		ModifiedFacetFactory modifiedFacetFactory,
		CalendarFactory calendarFactory, DateFormatFactory dateFormatFactory) {

		_modifiedFacetFactory = modifiedFacetFactory;
		_calendarFactory = calendarFactory;

		_dateRangeFactory = new DateRangeFactory(dateFormatFactory);
	}

	public Facet build() {
		Facet facet = _modifiedFacetFactory.newInstance(_searchContext);

		facet.setFacetConfiguration(buildFacetConfiguration(facet));

		String rangeString = _getSelectedRangeString();

		if (!Validator.isBlank(rangeString)) {
			_searchContext.setAttribute(facet.getFieldName(), rangeString);
		}

		return facet;
	}

	public void setCustomRangeFrom(String customRangeFrom) {
		_customRangeFrom = customRangeFrom;
	}

	public void setCustomRangeTo(String customRangeTo) {
		_customRangeTo = customRangeTo;
	}

	public void setSearchContext(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	public void setSelectedRanges(String... selectedRanges) {
		_selectedRanges = selectedRanges;
	}

	protected FacetConfiguration buildFacetConfiguration(Facet facet) {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

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

	protected JSONArray getRangesJSONArray(Calendar calendar) {
		JSONArray rangesJSONArray = JSONFactoryUtil.createJSONArray();

		Map<String, String> map = _dateRangeFactory.getRangeStrings(calendar);

		map.forEach(
			(key, value) -> {
				JSONObject range = JSONFactoryUtil.createJSONObject();

				range.put("label", key);
				range.put("range", value);

				rangesJSONArray.put(range);
			});

		return rangesJSONArray;
	}

	private String _getSelectedRangeString() {
		if (!Validator.isBlank(_customRangeFrom) &&
			!Validator.isBlank(_customRangeTo)) {

			return _dateRangeFactory.getRangeString(
				_customRangeFrom, _customRangeTo);
		}

		if (!ArrayUtil.isEmpty(_selectedRanges)) {
			return _dateRangeFactory.getRangeString(
				_selectedRanges[_selectedRanges.length - 1],
				_calendarFactory.getCalendar());
		}

		return null;
	}

	private final CalendarFactory _calendarFactory;
	private String _customRangeFrom;
	private String _customRangeTo;
	private final DateRangeFactory _dateRangeFactory;
	private final ModifiedFacetFactory _modifiedFacetFactory;
	private SearchContext _searchContext;
	private String[] _selectedRanges;

}
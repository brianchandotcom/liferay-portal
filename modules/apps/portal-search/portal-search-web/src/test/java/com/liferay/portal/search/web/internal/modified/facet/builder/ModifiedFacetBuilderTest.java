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

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.CalendarFactory;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.modified.ModifiedFacetFactory;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.internal.facet.modified.ModifiedFacetFactoryImpl;
import com.liferay.portal.search.internal.filter.FilterBuildersImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.DateFormatFactoryImpl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class ModifiedFacetBuilderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		calendarFactory = _createCalendarFactory();
		dateFormatFactory = new DateFormatFactoryImpl();
		filterBuilders = new FilterBuildersImpl();
		jsonFactory = new JSONFactoryImpl();
		searchContext = new SearchContext();
	}

	@Test
	public void testBuiltInNamedRange() {
		Mockito.doReturn(
			new GregorianCalendar(2018, Calendar.MARCH, 1, 15, 19, 23)
		).when(
			calendarFactory
		).getCalendar();

		ModifiedFacetBuilder modifiedFacetBuilder =
			_createModifiedFacetBuilder();

		modifiedFacetBuilder.setSelectedRanges("past-24-hours");

		_assertRange(
			"20180228151923", "20180301151923", modifiedFacetBuilder.build());
	}

	@Test
	public void testCustomRange() {
		ModifiedFacetBuilder modifiedFacetBuilder =
			_createModifiedFacetBuilder();

		modifiedFacetBuilder.setCustomRangeFrom("20180131");
		modifiedFacetBuilder.setCustomRangeTo("20180228");

		_assertRange(
			"20180131000000", "20180228235959", modifiedFacetBuilder.build());
	}

	@Test
	public void testCustomRangeSetsSearchContextAttribute() {
		ModifiedFacetBuilder modifiedFacetBuilder =
			_createModifiedFacetBuilder();

		modifiedFacetBuilder.setCustomRangeFrom("20180131");
		modifiedFacetBuilder.setCustomRangeTo("20180228");

		modifiedFacetBuilder.build();

		_assertRange("20180131000000", "20180228235959", searchContext);
	}

	@Test
	public void testSelectUserDefinedNamedRange() {
		ModifiedFacetBuilder modifiedFacetBuilder =
			_createModifiedFacetBuilder();

		JSONArray rangesJSONArray = _createRangesJSONArray(
			"eighties=[19800101000000 TO 19891231235959]");

		modifiedFacetBuilder.setRangesJSONArray(rangesJSONArray);

		modifiedFacetBuilder.setSelectedRanges("eighties");

		_assertRange(
			"19800101000000", "19891231235959", modifiedFacetBuilder.build());
	}

	@Test
	public void testUserDefinedNamedRanges() {
		ModifiedFacetBuilder modifiedFacetBuilder =
			_createModifiedFacetBuilder();

		JSONArray rangesJSONArray = _createRangesJSONArray(
			"past-hour    =[20180215120000 TO 20180215140000]",
			"past-24-hours=[20180214130000 TO 20180215140000]",
			"past-week    =[20180208130000 TO 20180215140000]",
			"past-month   =[20180115130000 TO 20180215140000]",
			"past-year    =[20170215130000 TO 20180215140000]");

		modifiedFacetBuilder.setRangesJSONArray(rangesJSONArray);

		_assertRangesJSONArray(rangesJSONArray, modifiedFacetBuilder.build());
	}

	protected CalendarFactory calendarFactory;
	protected DateFormatFactory dateFormatFactory;
	protected FilterBuilders filterBuilders;
	protected JSONFactory jsonFactory;
	protected SearchContext searchContext;

	private void _addRangeJSONObject(
		JSONArray jsonArray, String label, String range) {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		jsonObject.put(
			"label", StringUtil.trim(label)
		).put(
			"range", StringUtil.trim(range)
		);

		jsonArray.put(jsonObject);
	}

	private void _assertRange(String from, String to, Facet facet) {
		_assertRange(from, to, facet.getSelections()[0]);
	}

	private void _assertRange(
		String from, String to, SearchContext searchContext) {

		_assertRange(
			from, to, (String)searchContext.getAttribute(Field.MODIFIED_DATE));
	}

	private void _assertRange(String from, String to, String range) {
		List<String> calendars = _getRangeBounds(range);

		Assert.assertEquals(from, calendars.get(0));
		Assert.assertEquals(to, calendars.get(1));
	}

	private void _assertRangeJSONObjectEquals(
		JSONObject jsonObject1, JSONObject jsonObject2) {

		Assert.assertEquals(
			jsonObject1.getString("label"), jsonObject2.getString("label"));
		Assert.assertEquals(
			jsonObject1.getString("range"), jsonObject2.getString("range"));
	}

	private void _assertRangesJSONArray(
		JSONArray rangesJSONArray, Facet facet) {

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		JSONArray facetRangesJSONArray = dataJSONObject.getJSONArray("ranges");

		Assert.assertEquals(
			rangesJSONArray.length(), facetRangesJSONArray.length());

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			_assertRangeJSONObjectEquals(
				rangesJSONArray.getJSONObject(i),
				facetRangesJSONArray.getJSONObject(i));
		}
	}

	private CalendarFactory _createCalendarFactory() {
		CalendarFactory calendarFactory = Mockito.mock(CalendarFactory.class);

		Mockito.doReturn(
			Calendar.getInstance()
		).when(
			calendarFactory
		).getCalendar();

		return calendarFactory;
	}

	private ModifiedFacetBuilder _createModifiedFacetBuilder() {
		ModifiedFacetFactory modifiedFacetFactory =
			_createModifiedFacetFactory();

		ModifiedFacetBuilder modifiedFacetBuilder = new ModifiedFacetBuilder(
			modifiedFacetFactory, calendarFactory, dateFormatFactory,
			jsonFactory);

		modifiedFacetBuilder.setSearchContext(searchContext);

		return modifiedFacetBuilder;
	}

	private ModifiedFacetFactory _createModifiedFacetFactory() {
		FilterBuilders filterBuilders1 = filterBuilders;

		return new ModifiedFacetFactoryImpl() {
			{
				filterBuilders = filterBuilders1;
			}
		};
	}

	private JSONArray _createRangesJSONArray(String... labelsAndRanges) {
		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (String labelAndRange : labelsAndRanges) {
			String[] labelAndRangeArray = StringUtil.split(labelAndRange, '=');

			_addRangeJSONObject(
				jsonArray, labelAndRangeArray[0], labelAndRangeArray[1]);
		}

		return jsonArray;
	}

	private List<String> _getRangeBounds(String range) {
		String[] dateStrings = RangeParserUtil.parserRange(range);

		return Arrays.asList(dateStrings);
	}

}
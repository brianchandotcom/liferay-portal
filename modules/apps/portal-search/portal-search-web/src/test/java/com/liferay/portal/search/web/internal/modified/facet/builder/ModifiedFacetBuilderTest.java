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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ModifiedFacetFactory;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.CalendarFactory;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.DateFormatFactoryImpl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class ModifiedFacetBuilderTest {

	@Before
	public void setUp() {
		calendarFactory = createCalendarFactory();
		dateFormatFactory = new DateFormatFactoryImpl();

		setUpJSONFactoryUtil();
	}

	@Test
	public void testBoundedRange() {
		ModifiedFacetBuilder modifiedFacetBuilder =
			createModifiedFacetBuilder();

		modifiedFacetBuilder.setCustomRangeFrom("20180131");
		modifiedFacetBuilder.setCustomRangeTo("20180228");

		assertRange(
			"20180131000000", "20180228235959", modifiedFacetBuilder.build());
	}

	@Test
	public void testNamedRange() {
		Mockito.doReturn(
			new GregorianCalendar(2018, Calendar.MARCH, 1, 15, 19, 23)
		).when(
			calendarFactory
		).getCalendar();

		ModifiedFacetBuilder modifiedFacetBuilder =
			createModifiedFacetBuilder();

		modifiedFacetBuilder.setSelectedRanges("past-24-hours");

		assertRange(
			"20180228151923", "20180301151923", modifiedFacetBuilder.build());
	}

	protected void assertRange(String from, String to, Facet facet) {
		List<String> calendars = getRangeBounds(facet);

		Assert.assertEquals(from, calendars.get(0));
		Assert.assertEquals(to, calendars.get(1));
	}

	protected CalendarFactory createCalendarFactory() {
		CalendarFactory calendarFactory = Mockito.mock(CalendarFactory.class);

		Mockito.doReturn(
			Calendar.getInstance()
		).when(
			calendarFactory
		).getCalendar();

		return calendarFactory;
	}

	protected ModifiedFacetBuilder createModifiedFacetBuilder() {
		ModifiedFacetFactory modifiedFacetFactory = new ModifiedFacetFactory();

		ModifiedFacetBuilder modifiedFacetBuilder = new ModifiedFacetBuilder(
			modifiedFacetFactory, calendarFactory, dateFormatFactory);

		modifiedFacetBuilder.setSearchContext(new SearchContext());

		return modifiedFacetBuilder;
	}

	protected List<String> getRangeBounds(Facet facet) {
		SearchContext searchContext = facet.getSearchContext();

		String range = GetterUtil.getString(
			searchContext.getAttribute(facet.getFieldName()));

		String[] dateStrings = RangeParserUtil.parserRange(range);

		return Arrays.asList(dateStrings);
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected CalendarFactory calendarFactory;
	protected DateFormatFactory dateFormatFactory;

}
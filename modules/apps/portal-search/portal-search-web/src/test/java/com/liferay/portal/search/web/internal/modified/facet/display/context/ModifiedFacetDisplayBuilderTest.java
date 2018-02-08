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

package com.liferay.portal.search.web.internal.modified.facet.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.CalendarFactory;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.modified.facet.builder.DateRangeFactory;
import com.liferay.portal.util.CalendarFactoryImpl;
import com.liferay.portal.util.DateFormatFactoryImpl;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.HttpImpl;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Adam Brandizzi
 */
public class ModifiedFacetDisplayBuilderTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_calendarFactory = new CalendarFactoryImpl();
		_dateFormatFactory = new DateFormatFactoryImpl();

		_dateRangeFactory = new DateRangeFactory(_dateFormatFactory);

		_http = new HttpImpl();
		_jsonFactory = new JSONFactoryImpl();

		setUpHtmlUtil();
		setUpPortalUtil();

		Mockito.doReturn(
			_facetCollector
		).when(
			_facet
		).getFacetCollector();

		Mockito.doReturn(
			getFacetConfiguration()
		).when(
			_facet
		).getFacetConfiguration();
	}

	@Test
	public void testCustomRangeHasFrequency() {
		String from = "2018-01-01";
		String to = "2018-01-31";

		TermCollector termCollector = mockTermCollector(
			_dateRangeFactory.getRangeString(from, to));

		int frequency = RandomTestUtil.randomInt();

		mockTermCollectorFrequency(termCollector, frequency);

		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			createDisplayBuilder();

		modifiedFacetDisplayBuilder.setFromParameterValue(from);
		modifiedFacetDisplayBuilder.setToParameterValue(to);

		ModifiedFacetDisplayContext modifiedFacetDisplayContext =
			modifiedFacetDisplayBuilder.build();

		ModifiedFacetTermDisplayContext modifiedFacetTermDisplayContext =
			modifiedFacetDisplayContext.
				getCustomRangeModifiedFacetTermDisplayContext();

		Assert.assertEquals(
			frequency, modifiedFacetTermDisplayContext.getFrequency());
	}

	@Test
	public void testCustomRangeHasTermCollectorFrequency() {
		int frequency = RandomTestUtil.randomInt();
		TermCollector termCollector = mockTermCollector();

		mockTermCollectorFrequency(termCollector, frequency);

		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			createDisplayBuilder();

		modifiedFacetDisplayBuilder.setFromParameterValue("2018-01-01");
		modifiedFacetDisplayBuilder.setToParameterValue("2018-01-31");

		ModifiedFacetDisplayContext modifiedFacetDisplayContext =
			modifiedFacetDisplayBuilder.build();

		ModifiedFacetTermDisplayContext modifiedFacetTermDisplayContext =
			modifiedFacetDisplayContext.
				getCustomRangeModifiedFacetTermDisplayContext();

		Assert.assertEquals(
			frequency, modifiedFacetTermDisplayContext.getFrequency());
	}

	@Test
	public void testIsNothingSelected() {
		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			createDisplayBuilder();

		ModifiedFacetDisplayContext modifiedFacetDisplayContext =
			modifiedFacetDisplayBuilder.build();

		Assert.assertTrue(modifiedFacetDisplayContext.isNothingSelected());
	}

	@Test
	public void testIsNothingSelectedWithFromAndToAttributes() {
		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			createDisplayBuilder();

		modifiedFacetDisplayBuilder.setFromParameterValue("2018-01-01");
		modifiedFacetDisplayBuilder.setToParameterValue("2018-01-31");

		ModifiedFacetDisplayContext modifiedFacetDisplayContext =
			modifiedFacetDisplayBuilder.build();

		Assert.assertFalse(modifiedFacetDisplayContext.isNothingSelected());
	}

	@Test
	public void testIsNothingSelectedWithSelectedRange() {
		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			createDisplayBuilder();

		modifiedFacetDisplayBuilder.setParameterValues("past-24-hours");

		ModifiedFacetDisplayContext modifiedFacetDisplayContext =
			modifiedFacetDisplayBuilder.build();

		Assert.assertFalse(modifiedFacetDisplayContext.isNothingSelected());
	}

	@Test
	public void testIsRenderNothingFalseWithFromAndTo() {
		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			createDisplayBuilder();

		modifiedFacetDisplayBuilder.setFromParameterValue("2018-01-01");
		modifiedFacetDisplayBuilder.setToParameterValue("2018-01-31");
		modifiedFacetDisplayBuilder.setTotalHits(0);

		ModifiedFacetDisplayContext modifiedFacetDisplayContext =
			modifiedFacetDisplayBuilder.build();

		Assert.assertFalse(modifiedFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testIsRenderNothingFalseWithHits() {
		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			createDisplayBuilder();

		modifiedFacetDisplayBuilder.setTotalHits(1);

		ModifiedFacetDisplayContext modifiedFacetDisplayContext =
			modifiedFacetDisplayBuilder.build();

		Assert.assertFalse(modifiedFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testIsRenderNothingFalseWithSelectedRange() {
		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			createDisplayBuilder();

		modifiedFacetDisplayBuilder.setParameterValues("past-24-hours");
		modifiedFacetDisplayBuilder.setTotalHits(0);

		ModifiedFacetDisplayContext modifiedFacetDisplayContext =
			modifiedFacetDisplayBuilder.build();

		Assert.assertFalse(modifiedFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testIsRenderNothingTrueWithNoHits() {
		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			createDisplayBuilder();

		modifiedFacetDisplayBuilder.setTotalHits(0);

		ModifiedFacetDisplayContext modifiedFacetDisplayContext =
			modifiedFacetDisplayBuilder.build();

		Assert.assertTrue(modifiedFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testMissingFromAndToParameters() {
		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			createDisplayBuilder();

		modifiedFacetDisplayBuilder.setCurrentURL(
			"/?modifiedFrom=2018-01-01&modifiedTo=2018-01-31");

		ModifiedFacetDisplayContext modifiedFacetDisplayContext =
			modifiedFacetDisplayBuilder.build();

		assertTermDisplayContextsDoNotHaveFromAndToParameters(
			modifiedFacetDisplayContext.getModifiedFacetTermDisplayContexts());
	}

	protected void addRangeJSONObject(
		JSONArray jsonArray, String label, String range) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("label", label);
		jsonObject.put("range", range);

		jsonArray.put(jsonObject);
	}

	protected void assertDoesNotHasParameter(String url, String name) {
		Assert.assertTrue(
			Validator.isNull(_http.getParameter(url, name, false)));
	}

	protected void assertHasParameter(String url, String name) {
		Assert.assertTrue(
			Validator.isNotNull(_http.getParameter(url, name, false)));
	}

	protected void assertTermDisplayContextsDoNotHaveFromAndToParameters(
		List<ModifiedFacetTermDisplayContext> termDisplayContexts) {

		for (ModifiedFacetTermDisplayContext termDisplayContext :
				termDisplayContexts) {

			String label = termDisplayContext.getLabel();

			if (label.equals("custom-range")) {
				continue;
			}

			String rangeURL = termDisplayContext.getRangeURL();

			assertHasParameter(rangeURL, "modified");
			assertDoesNotHasParameter(rangeURL, "modifiedFrom");
			assertDoesNotHasParameter(rangeURL, "modifiedTo");
		}
	}

	protected ModifiedFacetDisplayBuilder createDisplayBuilder() {
		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			new ModifiedFacetDisplayBuilder(
				_calendarFactory, _dateFormatFactory, _http);

		modifiedFacetDisplayBuilder.setFacet(_facet);
		modifiedFacetDisplayBuilder.setRangesJSONArray(createRangesJSONArray());
		modifiedFacetDisplayBuilder.setLocale(LocaleUtil.getDefault());
		modifiedFacetDisplayBuilder.setTimeZone(TimeZoneUtil.getDefault());

		return modifiedFacetDisplayBuilder;
	}

	protected JSONArray createRangesJSONArray() {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		addRangeJSONObject(
			jsonArray, "past-hour", "[20180215120000 TO 20180215140000]");
		addRangeJSONObject(
			jsonArray, "past-24-hours", "[20180214130000 TO 20180215140000]");
		addRangeJSONObject(
			jsonArray, "past-week", "[20180208130000 TO 20180215140000]");
		addRangeJSONObject(
			jsonArray, "past-month", "[20180115130000 TO 20180215140000]");
		addRangeJSONObject(
			jsonArray, "past-year", "[20170215130000 TO 20180215140000]");

		return jsonArray;
	}

	protected FacetConfiguration getFacetConfiguration() {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		facetConfiguration.setDataJSONObject(jsonObject);

		return facetConfiguration;
	}

	protected TermCollector mockTermCollector() {
		TermCollector termCollector = Mockito.mock(TermCollector.class);

		Mockito.doReturn(
			termCollector
		).when(
			_facetCollector
		).getTermCollector(
			Mockito.anyString()
		);

		return termCollector;
	}

	protected TermCollector mockTermCollector(String term) {
		TermCollector termCollector = Mockito.mock(TermCollector.class);

		Mockito.doReturn(
			termCollector
		).when(
			_facetCollector
		).getTermCollector(
			term
		);

		return termCollector;
	}

	protected void mockTermCollectorFrequency(
		TermCollector termCollector, int frequency) {

		Mockito.doReturn(
			frequency
		).when(
			termCollector
		).getFrequency();
	}

	protected void setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	protected void setUpPortalUtil() {
		Mockito.doAnswer(
			invocation -> new String[] {
				invocation.getArgumentAt(0, String.class), StringPool.BLANK
			}
		).when(
			portal
		).stripURLAnchor(
			Mockito.anyString(), Mockito.anyString()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);
	}

	@Mock
	protected Portal portal;

	private CalendarFactory _calendarFactory;
	private DateFormatFactory _dateFormatFactory;
	private DateRangeFactory _dateRangeFactory;

	@Mock
	private Facet _facet;

	@Mock
	private FacetCollector _facetCollector;

	private HttpImpl _http;
	private JSONFactoryImpl _jsonFactory;

}
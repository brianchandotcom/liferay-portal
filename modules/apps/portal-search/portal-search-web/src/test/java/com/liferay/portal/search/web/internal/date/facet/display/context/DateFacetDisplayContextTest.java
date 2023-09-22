/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.facet.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.date.facet.configuration.DateFacetPortletInstanceConfiguration;
import com.liferay.portal.search.web.internal.date.facet.display.context.builder.DateFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.date.range.DateRangeFactory;
import com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.DateFormatFactoryImpl;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class DateFacetDisplayContextTest
	extends BaseFacetDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		_dateFormatFactory = new DateFormatFactoryImpl();

		_jsonFactoryImpl = new JSONFactoryImpl();

		_dateRangeFactory = new DateRangeFactory(
			_dateFormatFactory, _jsonFactoryImpl);

		_setUpPortalUtil();

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
	public void testBucketDisplayContexts() {
		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder();

		_mockFacetConfiguration(
			"past-hour=[20180515225959 TO 20180515235959]",
			"some-time-ago=[20180508235959 TO 20180514235959]");

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		List<BucketDisplayContext> bucketDisplayContexts =
			dateFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 2, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals("past-hour", bucketDisplayContext.getBucketText());

		bucketDisplayContext = bucketDisplayContexts.get(1);

		Assert.assertEquals(
			"some-time-ago", bucketDisplayContext.getBucketText());
	}

	@Test
	public void testCustomRangeHasFrequency() {
		String from = "2018-01-01";
		String to = "2018-01-31";

		TermCollector termCollector = _mockTermCollector(
			_dateRangeFactory.getRangeString(from, to, TimeZoneUtil.GMT));

		int frequency = RandomTestUtil.randomInt();

		_mockTermCollectorFrequency(termCollector, frequency);

		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder();

		dateFacetDisplayContextBuilder.setFromParameterValue(from);
		dateFacetDisplayContextBuilder.setToParameterValue(to);

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		BucketDisplayContext bucketDisplayContext =
			dateFacetDisplayContext.getCustomRangeBucketDisplayContext();

		Assert.assertEquals(frequency, bucketDisplayContext.getFrequency());
	}

	@Test
	public void testCustomRangeHasTermCollectorFrequency() {
		int frequency = RandomTestUtil.randomInt();
		TermCollector termCollector = _mockTermCollector();

		_mockTermCollectorFrequency(termCollector, frequency);

		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder();

		dateFacetDisplayContextBuilder.setFromParameterValue("2018-01-01");
		dateFacetDisplayContextBuilder.setToParameterValue("2018-01-31");

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		BucketDisplayContext bucketDisplayContext =
			dateFacetDisplayContext.getCustomRangeBucketDisplayContext();

		Assert.assertEquals(frequency, bucketDisplayContext.getFrequency());
	}

	@Override
	@Test
	public void testEmptySearchResults() throws Exception {
	}

	@Override
	@Test
	public void testEmptySearchResultsWithPreviousSelection() throws Exception {
	}

	@Test
	public void testIsNothingSelected() {
		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder();

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		Assert.assertTrue(dateFacetDisplayContext.isNothingSelected());
	}

	@Test
	public void testIsNothingSelectedWithFromAndToAttributes() {
		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder();

		dateFacetDisplayContextBuilder.setFromParameterValue("2018-01-01");
		dateFacetDisplayContextBuilder.setToParameterValue("2018-01-31");

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		Assert.assertFalse(dateFacetDisplayContext.isNothingSelected());
	}

	@Test
	public void testIsNothingSelectedWithSelectedRange() {
		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder();

		dateFacetDisplayContextBuilder.setParameterValues("past-24-hours");

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		Assert.assertFalse(dateFacetDisplayContext.isNothingSelected());
	}

	@Test
	public void testIsRenderNothingFalseWithFromAndTo() {
		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder();

		dateFacetDisplayContextBuilder.setFieldToAggregate("createDate");
		dateFacetDisplayContextBuilder.setFromParameterValue("2018-01-01");
		dateFacetDisplayContextBuilder.setToParameterValue("2018-01-31");
		dateFacetDisplayContextBuilder.setTotalHits(0);

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		Assert.assertFalse(dateFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testIsRenderNothingFalseWithHits() {
		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder();

		dateFacetDisplayContextBuilder.setFieldToAggregate("createDate");
		dateFacetDisplayContextBuilder.setTotalHits(1);

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		Assert.assertFalse(dateFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testIsRenderNothingFalseWithSelectedRange() {
		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder();

		dateFacetDisplayContextBuilder.setFieldToAggregate("createDate");
		dateFacetDisplayContextBuilder.setParameterValues("past-24-hours");
		dateFacetDisplayContextBuilder.setTotalHits(0);

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		Assert.assertFalse(dateFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testIsRenderNothingTrueWithEmptyFieldToAggregate() {
		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder();

		dateFacetDisplayContextBuilder.setTotalHits(1);

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		Assert.assertTrue(dateFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testIsRenderNothingTrueWithNoHits() {
		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder();

		dateFacetDisplayContextBuilder.setTotalHits(0);

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		Assert.assertTrue(dateFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testMissingFromAndToParameters() {
		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder();

		dateFacetDisplayContextBuilder.setCurrentURL(
			"/?dateFrom=2018-01-01&dateTo=2018-01-31");

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		_assertTermDisplayContextsDoNotHaveFromAndToParameters(
			dateFacetDisplayContext.getBucketDisplayContexts());
	}

	@Override
	@Test
	public void testOneTerm() throws Exception {
	}

	@Override
	@Test
	public void testOneTermWithPreviousSelection() throws Exception {
	}

	@Override
	@Test
	public void testOrderByTermFrequencyAscending() throws Exception {
		testOrderBy(
			new int[] {1, 3, 3, 4},
			new String[] {
				"past-24-hours", "past-month", "past-week", "past-hour"
			},
			new int[] {4, 3, 3, 1}, "count:asc",
			new String[] {
				"[20180515225959 TO 20180515235959]",
				"[20180508235959 TO 20180508235959]",
				"[20180508235959 TO 20180415235959]",
				"[20180508235959 TO 20180514235959]"
			});
	}

	@Override
	@Test
	public void testOrderByTermFrequencyDescending() throws Exception {
		testOrderBy(
			new int[] {3, 3, 2, 1},
			new String[] {
				"past-24-hours", "past-month", "past-week", "past-hour"
			},
			new int[] {1, 2, 3, 3}, "count:desc",
			new String[] {
				"[20180515225959 TO 20180515235959]",
				"[20180508235959 TO 20180508235959]",
				"[20180508235959 TO 20180415235959]",
				"[20180508235959 TO 20180514235959]"
			});
	}

	@Override
	@Test
	public void testOrderByTermValueAscending() throws Exception {
	}

	@Override
	@Test
	public void testOrderByTermValueDescending() throws Exception {
	}

	protected DateFacetDisplayContextBuilder createDisplayContextBuilder() {
		return createDisplayContextBuilder("OrderHitsDesc");
	}

	protected DateFacetDisplayContextBuilder createDisplayContextBuilder(
		String order) {

		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			_createDateFacetDisplayContextBuilder();

		_mockFacetConfiguration();

		dateFacetDisplayContextBuilder.setFacet(_facet);
		dateFacetDisplayContextBuilder.setLocale(LocaleUtil.getDefault());
		dateFacetDisplayContextBuilder.setOrder(order);
		dateFacetDisplayContextBuilder.setTimeZone(TimeZoneUtil.getDefault());

		return dateFacetDisplayContextBuilder;
	}

	protected FacetConfiguration getFacetConfiguration() {
		JSONObject jsonObject = _jsonFactoryImpl.createJSONObject();

		return getFacetConfiguration(jsonObject);
	}

	protected FacetConfiguration getFacetConfiguration(
		JSONObject dataJSONObject) {

		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setDataJSONObject(dataJSONObject);

		return facetConfiguration;
	}

	@Override
	protected void testOrderBy(
			int[] expectedFrequencies, String[] expectedTerms,
			int[] frequencies, String order, String[] terms)
		throws Exception {

		setUpTermCollectors(
			_facetCollector, getTermCollectors(terms, frequencies));

		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			createDisplayContextBuilder(order);

		_mockFacetConfiguration(
			"past-hour=[20180515225959 TO 20180515235959]",
			"past-week=[20180508235959 TO 20180508235959]",
			"past-month=[20180508235959 TO 20180415235959]",
			"past-24-hours=[20180508235959 TO 20180514235959]");

		dateFacetDisplayContextBuilder.setFromParameterValue("2018-01-01");
		dateFacetDisplayContextBuilder.setToParameterValue("2018-01-31");

		DateFacetDisplayContext dateFacetDisplayContext =
			dateFacetDisplayContextBuilder.build();

		assertFacetOrder(
			dateFacetDisplayContext.getBucketDisplayContexts(), expectedTerms,
			expectedFrequencies);
	}

	protected Portal portal = Mockito.mock(Portal.class);

	private void _addRangeJSONObject(
		JSONArray jsonArray, String label, String range) {

		JSONObject jsonObject = _jsonFactoryImpl.createJSONObject();

		jsonObject.put(
			"label", label
		).put(
			"range", range
		);

		jsonArray.put(jsonObject);
	}

	private void _assertDoesNotHasParameter(String url, String name) {
		Assert.assertTrue(
			Validator.isNull(
				HttpComponentsUtil.getParameter(url, name, false)));
	}

	private void _assertHasParameter(String url, String name) {
		Assert.assertTrue(
			Validator.isNotNull(
				HttpComponentsUtil.getParameter(url, name, false)));
	}

	private void _assertTermDisplayContextsDoNotHaveFromAndToParameters(
		List<BucketDisplayContext> termDisplayContexts) {

		for (BucketDisplayContext termDisplayContext : termDisplayContexts) {
			String label = termDisplayContext.getBucketText();

			if (label.equals("custom-range")) {
				continue;
			}

			String rangeURL = termDisplayContext.getFilterValue();

			_assertHasParameter(rangeURL, "date");
			_assertDoesNotHasParameter(rangeURL, "dateFrom");
			_assertDoesNotHasParameter(rangeURL, "dateTo");
		}
	}

	private JSONObject _createDataJSONObject(String... labelsAndRanges) {
		JSONObject dataJSONObject = _jsonFactoryImpl.createJSONObject();

		dataJSONObject.put("ranges", _createRangesJSONArray(labelsAndRanges));

		return dataJSONObject;
	}

	private DateFacetDisplayContextBuilder
		_createDateFacetDisplayContextBuilder() {

		configurationProviderUtilMockedStatic.when(
			() -> ConfigurationProviderUtil.getPortletInstanceConfiguration(
				Mockito.any(), Mockito.any())
		).thenReturn(
			Mockito.mock(DateFacetPortletInstanceConfiguration.class)
		);

		try {
			return new DateFacetDisplayContextBuilder(
				_dateFormatFactory, getRenderRequest());
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private JSONArray _createRangesJSONArray(String... labelsAndRanges) {
		JSONArray jsonArray = _jsonFactoryImpl.createJSONArray();

		for (String labelAndRange : labelsAndRanges) {
			String[] labelAndRangeArray = StringUtil.split(labelAndRange, '=');

			_addRangeJSONObject(
				jsonArray, labelAndRangeArray[0], labelAndRangeArray[1]);
		}

		return jsonArray;
	}

	private void _mockFacetConfiguration(String... labelsAndRanges) {
		Mockito.doReturn(
			getFacetConfiguration(_createDataJSONObject(labelsAndRanges))
		).when(
			_facet
		).getFacetConfiguration();
	}

	private TermCollector _mockTermCollector() {
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

	private TermCollector _mockTermCollector(String term) {
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

	private void _mockTermCollectorFrequency(
		TermCollector termCollector, int frequency) {

		Mockito.doReturn(
			frequency
		).when(
			termCollector
		).getFrequency();
	}

	private void _setUpPortalUtil() {
		Mockito.doAnswer(
			invocation -> new String[] {
				invocation.getArgument(0, String.class), StringPool.BLANK
			}
		).when(
			portal
		).stripURLAnchor(
			Mockito.anyString(), Mockito.anyString()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);
	}

	private DateFormatFactory _dateFormatFactory;
	private DateRangeFactory _dateRangeFactory;
	private final Facet _facet = Mockito.mock(Facet.class);
	private final FacetCollector _facetCollector = Mockito.mock(
		FacetCollector.class);
	private JSONFactoryImpl _jsonFactoryImpl;

}
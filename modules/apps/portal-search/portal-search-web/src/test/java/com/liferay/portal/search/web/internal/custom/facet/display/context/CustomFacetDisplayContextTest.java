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

package com.liferay.portal.search.web.internal.custom.facet.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.custom.facet.display.context.builder.CustomFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class CustomFacetDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		Mockito.doReturn(
			_facetCollector
		).when(
			_facet
		).getFacetCollector();
	}

	@Test
	public void testEmptyCustomDisplayCaption() throws Exception {
		String customDisplayCaption = "";
		String fieldToAggregate = "groupId";
		String parameterValue = "";

		CustomFacetDisplayContext customFacetDisplayContext =
			_createDisplayContext(
				customDisplayCaption, fieldToAggregate, parameterValue);

		List<BucketDisplayContext> bucketDisplayContexts =
			customFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 0, bucketDisplayContexts.size());

		Assert.assertTrue(customFacetDisplayContext.isNothingSelected());
		Assert.assertTrue(customFacetDisplayContext.isRenderNothing());
		Assert.assertEquals(
			fieldToAggregate, customFacetDisplayContext.getDisplayCaption());
	}

	@Test
	public void testEmptySearchResultsWithPreviousSelection() throws Exception {
		String fieldName = RandomTestUtil.randomString();

		String parameterValue = fieldName;

		CustomFacetDisplayContext customFacetDisplayContext =
			_createDisplayContext(
				"customDisplayCaption", "fieldToAggregate", parameterValue);

		List<BucketDisplayContext> bucketDisplayContexts =
			customFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(fieldName, bucketDisplayContext.getBucketText());
		Assert.assertEquals(fieldName, bucketDisplayContext.getFilterValue());
		Assert.assertEquals(0, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());
		Assert.assertTrue(bucketDisplayContext.isSelected());

		Assert.assertEquals(
			parameterValue, customFacetDisplayContext.getParameterValue());
		Assert.assertFalse(customFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(customFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTerm() throws Exception {
		String fieldName = RandomTestUtil.randomString();
		int count = RandomTestUtil.randomInt();

		_setUpOneTermCollector(fieldName, count);

		String parameterValue = "";

		CustomFacetDisplayContext customFacetDisplayContext =
			_createDisplayContext(
				"customDisplayCaption", "fieldToAggregate", parameterValue);

		List<BucketDisplayContext> bucketDisplayContexts =
			customFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(fieldName, bucketDisplayContext.getBucketText());
		Assert.assertEquals(fieldName, bucketDisplayContext.getFilterValue());
		Assert.assertEquals(count, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());
		Assert.assertFalse(bucketDisplayContext.isSelected());

		Assert.assertEquals(
			parameterValue, customFacetDisplayContext.getParameterValue());
		Assert.assertTrue(customFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(customFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTermWithPreviousSelection() throws Exception {
		String fieldName = RandomTestUtil.randomString();
		int count = RandomTestUtil.randomInt();

		_setUpOneTermCollector(fieldName, count);

		String parameterValue = fieldName;

		CustomFacetDisplayContext customFacetDisplayContext =
			_createDisplayContext(
				"customDisplayCaption", "fieldToAggregate", parameterValue);

		List<BucketDisplayContext> bucketDisplayContexts =
			customFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(fieldName, bucketDisplayContext.getBucketText());
		Assert.assertEquals(fieldName, bucketDisplayContext.getFilterValue());
		Assert.assertEquals(count, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());
		Assert.assertTrue(bucketDisplayContext.isSelected());

		Assert.assertEquals(
			parameterValue, customFacetDisplayContext.getParameterValue());
		Assert.assertFalse(customFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(customFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOrderByTermFrequencyAscending() throws Exception {
		List<TermCollector> termCollectors = _getTermCollectors(
			new String[] {"alpha", "delta", "bravo", "charlie"},
			new int[] {3, 4, 5, 6});

		_setUpMultipleTermCollectors(termCollectors);

		CustomFacetDisplayContext customSearchFacetDisplayContext =
			_createDisplayContext(
				"customDisplayCaption", "fieldToAggregate", StringPool.BLANK,
				"count:asc");

		List<BucketDisplayContext> bucketDisplayContexts =
			customSearchFacetDisplayContext.getBucketDisplayContexts();

		String nameFrequencyString = _buildNameFrequencyString(
			bucketDisplayContexts);

		Assert.assertEquals(
			bucketDisplayContexts.toString(),
			"alpha:3|delta:4|bravo:5|charlie:6", nameFrequencyString);

		termCollectors = _getTermCollectors(
			new String[] {"alpha", "delta", "bravo", "charlie"},
			new int[] {4, 5, 5, 6});

		_setUpMultipleTermCollectors(termCollectors);

		customSearchFacetDisplayContext = _createDisplayContext(
			"customDisplayCaption", "fieldToAggregate", StringPool.BLANK,
			"count:asc");

		bucketDisplayContexts =
			customSearchFacetDisplayContext.getBucketDisplayContexts();

		nameFrequencyString = _buildNameFrequencyString(bucketDisplayContexts);

		Assert.assertEquals(
			bucketDisplayContexts.toString(),
			"alpha:4|bravo:5|delta:5|charlie:6", nameFrequencyString);
	}

	@Test
	public void testOrderByTermFrequencyDescending() throws Exception {
		List<TermCollector> termCollectors = _getTermCollectors(
			new String[] {"alpha", "charlie", "bravo", "delta"},
			new int[] {3, 4, 5, 6});

		_setUpMultipleTermCollectors(termCollectors);

		CustomFacetDisplayContext customFacetDisplayContext =
			_createDisplayContext(
				"customDisplayCaption", "fieldToAggregate", StringPool.BLANK,
				"count:desc");

		List<BucketDisplayContext> bucketDisplayContexts =
			customFacetDisplayContext.getBucketDisplayContexts();

		String nameFrequencyString = _buildNameFrequencyString(
			bucketDisplayContexts);

		Assert.assertEquals(
			bucketDisplayContexts.toString(),
			"delta:6|bravo:5|charlie:4|alpha:3", nameFrequencyString);

		termCollectors = _getTermCollectors(
			new String[] {"alpha", "delta", "bravo", "charlie"},
			new int[] {4, 5, 5, 6});

		_setUpMultipleTermCollectors(termCollectors);

		customFacetDisplayContext = _createDisplayContext(
			"customDisplayCaption", "fieldToAggregate", StringPool.BLANK,
			"count:desc");

		bucketDisplayContexts =
			customFacetDisplayContext.getBucketDisplayContexts();

		nameFrequencyString = _buildNameFrequencyString(bucketDisplayContexts);

		Assert.assertEquals(
			bucketDisplayContexts.toString(),
			"charlie:6|bravo:5|delta:5|alpha:4", nameFrequencyString);
	}

	private String _buildNameFrequencyString(
			List<BucketDisplayContext> bucketDisplayContexts)
		throws Exception {

		StringBundler sb = new StringBundler(bucketDisplayContexts.size() * 4);

		for (BucketDisplayContext bucketDisplayContext :
				bucketDisplayContexts) {

			sb.append(bucketDisplayContext.getBucketText());
			sb.append(StringPool.COLON);
			sb.append(bucketDisplayContext.getFrequency());
			sb.append(StringPool.PIPE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private CustomFacetDisplayContext _createDisplayContext(
			String customDisplayCaption, String fieldToAggregate,
			String parameterValue)
		throws Exception {

		return _createDisplayContext(
			customDisplayCaption, fieldToAggregate, parameterValue,
			"count:desc");
	}

	private CustomFacetDisplayContext _createDisplayContext(
			String customDisplayCaption, String fieldToAggregate,
			String parameterValue, String order)
		throws Exception {

		CustomFacetDisplayContextBuilder customFacetDisplayContextBuilder =
			new CustomFacetDisplayContextBuilder(_getHttpServletRequest());

		customFacetDisplayContextBuilder.setFacet(_facet);
		customFacetDisplayContextBuilder.setParameterName("custom");
		customFacetDisplayContextBuilder.setParameterValue(parameterValue);
		customFacetDisplayContextBuilder.setFrequenciesVisible(true);

		customFacetDisplayContextBuilder.setFrequencyThreshold(0);
		customFacetDisplayContextBuilder.setMaxTerms(0);
		customFacetDisplayContextBuilder.setOrder(order);

		customFacetDisplayContextBuilder.setCustomDisplayCaption(
			Optional.ofNullable(customDisplayCaption));
		customFacetDisplayContextBuilder.setFieldToAggregate(fieldToAggregate);

		return customFacetDisplayContextBuilder.build();
	}

	private TermCollector _createTermCollector(String fieldName, int count) {
		TermCollector termCollector = Mockito.mock(TermCollector.class);

		Mockito.doReturn(
			count
		).when(
			termCollector
		).getFrequency();

		Mockito.doReturn(
			fieldName
		).when(
			termCollector
		).getTerm();

		return termCollector;
	}

	private HttpServletRequest _getHttpServletRequest() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.doReturn(
			_getThemeDisplay()
		).when(
			httpServletRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);

		return httpServletRequest;
	}

	private List<TermCollector> _getTermCollectors(String... fieldNames) {
		int[] frequencies = new int[fieldNames.length];

		for (int i = 0; i < fieldNames.length; i++) {
			frequencies[i] = i + 1;
		}

		return _getTermCollectors(fieldNames, frequencies);
	}

	private List<TermCollector> _getTermCollectors(
		String[] fieldNames, int[] frequencies) {

		List<TermCollector> termCollectors = new ArrayList<>();

		for (int i = 0; i < fieldNames.length; i++) {
			termCollectors.add(
				_createTermCollector(fieldNames[i], frequencies[i]));
		}

		return termCollectors;
	}

	private ThemeDisplay _getThemeDisplay() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			Mockito.mock(PortletDisplay.class)
		).when(
			themeDisplay
		).getPortletDisplay();

		return themeDisplay;
	}

	private void _setUpMultipleTermCollectors(
		List<TermCollector> termCollectors) {

		Mockito.doReturn(
			termCollectors
		).when(
			_facetCollector
		).getTermCollectors();
	}

	private void _setUpOneTermCollector(String fieldName, int count) {
		Mockito.doReturn(
			Collections.singletonList(_createTermCollector(fieldName, count))
		).when(
			_facetCollector
		).getTermCollectors();
	}

	private final Facet _facet = Mockito.mock(Facet.class);
	private final FacetCollector _facetCollector = Mockito.mock(
		FacetCollector.class);

}
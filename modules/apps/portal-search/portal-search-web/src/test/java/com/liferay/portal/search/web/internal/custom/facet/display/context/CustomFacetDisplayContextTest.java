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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.custom.facet.configuration.CustomFacetPortletInstanceConfiguration;
import com.liferay.portal.search.web.internal.custom.facet.display.context.builder.CustomFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class CustomFacetDisplayContextTest
	extends BaseFacetDisplayContextTestCase {

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

	@Override
	@Test
	public void testEmptySearchResults() throws Exception {
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

		setUpTermCollectors(
			_facetCollector,
			Collections.singletonList(createTermCollector(fieldName, count)));

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

		setUpTermCollectors(
			_facetCollector,
			Collections.singletonList(createTermCollector(fieldName, count)));

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

	@Override
	protected void testOrderBy(
			String[] terms, int[] frequencies, String order,
			String[] expectedTerms, int[] expectedFrequencies)
		throws Exception {

		setUpTermCollectors(
			_facetCollector, getTermCollectors(terms, frequencies));

		CustomFacetDisplayContext customFacetDisplayContext =
			_createDisplayContext(
				"customDisplayCaption", "fieldToAggregate", StringPool.BLANK,
				order);

		assertFacetOrder(
			customFacetDisplayContext.getBucketDisplayContexts(), expectedTerms,
			expectedFrequencies);
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
			new CustomFacetDisplayContextBuilder(
				getHttpServletRequest(
					CustomFacetPortletInstanceConfiguration.class));

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

	private final Facet _facet = Mockito.mock(Facet.class);
	private final FacetCollector _facetCollector = Mockito.mock(
		FacetCollector.class);

}
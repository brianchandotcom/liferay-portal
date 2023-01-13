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

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.facet.display.context.builder.AssetTagsSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.tag.facet.configuration.TagFacetPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class AssetTagsSearchFacetDisplayContextTest extends BaseFacetDisplayContextTestCase {

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
	public void testEmptySearchResults() throws Exception {
		String facetParam = "";

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<BucketDisplayContext> bucketDisplayContexts =
			assetTagsSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 0, bucketDisplayContexts.size());

		Assert.assertEquals(
			facetParam, assetTagsSearchFacetDisplayContext.getParameterValue());
		Assert.assertTrue(
			assetTagsSearchFacetDisplayContext.isNothingSelected());
		Assert.assertTrue(assetTagsSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testEmptySearchResultsWithPreviousSelection() throws Exception {
		String term = RandomTestUtil.randomString();

		String facetParam = term;

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<BucketDisplayContext> bucketDisplayContexts =
			assetTagsSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(term, bucketDisplayContext.getBucketText());
		Assert.assertEquals(term, bucketDisplayContext.getFilterValue());
		Assert.assertEquals(0, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isSelected());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			facetParam, assetTagsSearchFacetDisplayContext.getParameterValue());
		Assert.assertFalse(
			assetTagsSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(
			assetTagsSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTerm() throws Exception {
		String term = RandomTestUtil.randomString();
		int frequency = RandomTestUtil.randomInt();

		setUpTermCollector(
			_facetCollector, term, frequency);

		String facetParam = StringPool.BLANK;

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<BucketDisplayContext> bucketDisplayContexts =
			assetTagsSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(term, bucketDisplayContext.getBucketText());
		Assert.assertEquals(term, bucketDisplayContext.getFilterValue());
		Assert.assertEquals(frequency, bucketDisplayContext.getFrequency());
		Assert.assertFalse(bucketDisplayContext.isSelected());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			facetParam, assetTagsSearchFacetDisplayContext.getParameterValue());
		Assert.assertTrue(
			assetTagsSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(
			assetTagsSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTermWithPreviousSelection() throws Exception {
		String term = RandomTestUtil.randomString();
		int frequency = RandomTestUtil.randomInt();

		setUpTermCollector(
			_facetCollector, term, frequency);

		String facetParam = term;

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<BucketDisplayContext> bucketDisplayContexts =
			assetTagsSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(term, bucketDisplayContext.getBucketText());
		Assert.assertEquals(term, bucketDisplayContext.getFilterValue());
		Assert.assertEquals(frequency, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isSelected());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			facetParam, assetTagsSearchFacetDisplayContext.getParameterValue());
		Assert.assertFalse(
			assetTagsSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(
			assetTagsSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOrderByTermFrequencyAscending() throws Exception {
		_testOrderBy(
			"alpha:4|bravo:5|delta:5|charlie:6", new int[] {4, 5, 5, 6},
			"count:asc", new String[] {"alpha", "delta", "bravo", "charlie"});
	}

	@Test
	public void testOrderByTermFrequencyDescending() throws Exception {
		_testOrderBy(
			"charlie:6|bravo:5|delta:5|alpha:4", new int[] {4, 5, 5, 6},
			"count:desc", new String[] {"alpha", "delta", "bravo", "charlie"});
	}

	@Test
	public void testOrderByTermValueAscending() throws Exception {
		_testOrderBy(
			"alpha:4|alpha:3|bravo:6|delta:5", new int[] {6, 5, 4, 3},
			"key:asc", new String[] {"bravo", "delta", "alpha", "alpha"});
	}

	@Test
	public void testOrderByTermValueDescending() throws Exception {
		_testOrderBy(
			"charlie:6|bravo:3|alpha:5|alpha:4", new int[] {3, 4, 5, 6},
			"key:desc", new String[] {"bravo", "alpha", "alpha", "charlie"});
	}

	protected AssetTagsSearchFacetDisplayContext createDisplayContext(
			String facetParam)
		throws Exception {

		return createDisplayContext(facetParam, "count:desc");
	}

	protected AssetTagsSearchFacetDisplayContext createDisplayContext(
			String facetParam, String order)
		throws ConfigurationException {

		AssetTagsSearchFacetDisplayContextBuilder
			assetTagsSearchFacetDisplayContextBuilder =
				new AssetTagsSearchFacetDisplayContextBuilder(
					getRenderRequest(
						TagFacetPortletInstanceConfiguration.class));

		assetTagsSearchFacetDisplayContextBuilder.setDisplayStyle("cloud");
		assetTagsSearchFacetDisplayContextBuilder.setFacet(_facet);
		assetTagsSearchFacetDisplayContextBuilder.setFrequenciesVisible(true);
		assetTagsSearchFacetDisplayContextBuilder.setFrequencyThreshold(0);
		assetTagsSearchFacetDisplayContextBuilder.setMaxTerms(0);
		assetTagsSearchFacetDisplayContextBuilder.setOrder(order);
		assetTagsSearchFacetDisplayContextBuilder.setParameterName(
			_facet.getFieldId());
		assetTagsSearchFacetDisplayContextBuilder.setParameterValue(facetParam);

		return assetTagsSearchFacetDisplayContextBuilder.build();
	}

	private void _testOrderBy(
			String expected, int[] frequencies, String order, String[] terms)
		throws Exception {

		setUpTermCollectors(
			_facetCollector,
			getTermCollectors(
				terms, frequencies));

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext2 =
			createDisplayContext(StringPool.BLANK, order);

		List<BucketDisplayContext> bucketDisplayContexts2 =
			assetTagsSearchFacetDisplayContext2.getBucketDisplayContexts();

		String nameFrequencyString =
			buildNameFrequencyString(
				bucketDisplayContexts2);

		Assert.assertEquals(
			bucketDisplayContexts2.toString(), expected, nameFrequencyString);
	}

	private final Facet _facet = Mockito.mock(Facet.class);
	private final FacetCollector _facetCollector = Mockito.mock(
		FacetCollector.class);

}
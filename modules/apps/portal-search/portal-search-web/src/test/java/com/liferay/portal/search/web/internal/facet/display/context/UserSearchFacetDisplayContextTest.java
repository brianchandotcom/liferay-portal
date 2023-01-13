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
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.facet.display.context.builder.UserSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.user.facet.configuration.UserFacetPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Lino Alves
 */
public class UserSearchFacetDisplayContextTest extends BaseFacetDisplayContextTestCase {

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
		String paramValue = "";

		UserSearchFacetDisplayContext userSearchFacetDisplayContext =
			_createDisplayContext(paramValue);

		List<BucketDisplayContext> bucketDisplayContexts =
			userSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 0, bucketDisplayContexts.size());

		Assert.assertEquals(
			paramValue, userSearchFacetDisplayContext.getParamValue());
		Assert.assertTrue(userSearchFacetDisplayContext.isNothingSelected());
		Assert.assertTrue(userSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testEmptySearchResultsWithPreviousSelection() throws Exception {
		String userName = RandomTestUtil.randomString();

		String paramValue = userName;

		UserSearchFacetDisplayContext userSearchFacetDisplayContext =
			_createDisplayContext(paramValue);

		List<BucketDisplayContext> bucketDisplayContexts =
			userSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(userName, bucketDisplayContext.getBucketText());
		Assert.assertEquals(0, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());
		Assert.assertTrue(bucketDisplayContext.isSelected());

		Assert.assertEquals(
			paramValue, userSearchFacetDisplayContext.getParamValue());
		Assert.assertFalse(userSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(userSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTerm() throws Exception {
		String userName = RandomTestUtil.randomString();

		int count = RandomTestUtil.randomInt();

		setUpTermCollector(
			_facetCollector, userName, count);

		String paramValue = "";

		UserSearchFacetDisplayContext userSearchFacetDisplayContext =
			_createDisplayContext(paramValue);

		List<BucketDisplayContext> bucketDisplayContexts =
			userSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(userName, bucketDisplayContext.getBucketText());
		Assert.assertEquals(count, bucketDisplayContext.getFrequency());
		Assert.assertFalse(bucketDisplayContext.isSelected());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			paramValue, userSearchFacetDisplayContext.getParamValue());
		Assert.assertTrue(userSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(userSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTermWithPreviousSelection() throws Exception {
		String userName = RandomTestUtil.randomString();

		int count = RandomTestUtil.randomInt();

		setUpTermCollector(
			_facetCollector, userName, count);

		String paramValue = userName;

		UserSearchFacetDisplayContext userSearchFacetDisplayContext =
			_createDisplayContext(paramValue);

		List<BucketDisplayContext> bucketDisplayContexts =
			userSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(userName, bucketDisplayContext.getBucketText());
		Assert.assertEquals(count, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());
		Assert.assertTrue(bucketDisplayContext.isSelected());

		Assert.assertEquals(
			paramValue, userSearchFacetDisplayContext.getParamValue());
		Assert.assertFalse(userSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(userSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOrderByTermFrequencyAscending() throws Exception {
		String[] userNames = {"charlie", "delta", "bravo", "alpha"};

		setUpTermCollectors(
			_facetCollector,
			getTermCollectors(
				userNames, new int[] {6, 5, 5, 4}));

		UserSearchFacetDisplayContext userSearchFacetDisplayContext =
			_createDisplayContext(StringPool.BLANK, "count:asc");

		String nameFrequencyString =
			buildNameFrequencyString(
				userSearchFacetDisplayContext.getBucketDisplayContexts());

		Assert.assertEquals(
			"alpha:4|bravo:5|delta:5|charlie:6", nameFrequencyString);
	}

	@Test
	public void testOrderByTermFrequencyDescending() throws Exception {
		String[] userNames = {"alpha", "delta", "bravo", "charlie"};

		setUpTermCollectors(
			_facetCollector,
			getTermCollectors(
				userNames, new int[] {4, 5, 5, 6}));

		UserSearchFacetDisplayContext userSearchFacetDisplayContext =
			_createDisplayContext(StringPool.BLANK, "count:desc");

		String nameFrequencyString =
			buildNameFrequencyString(
				userSearchFacetDisplayContext.getBucketDisplayContexts());

		Assert.assertEquals(
			"charlie:6|bravo:5|delta:5|alpha:4", nameFrequencyString);
	}

	@Test
	public void testOrderByTermValueAscending() throws Exception {
		setUpTermCollectors(
			_facetCollector,
			getTermCollectors(
				new String[] {"bravo", "alpha", "bravo", "charlie"},
				new int[] {3, 4, 5, 6}));

		UserSearchFacetDisplayContext userSearchFacetDisplayContext =
			_createDisplayContext(StringPool.BLANK, "key:asc");

		String nameFrequencyString =
			buildNameFrequencyString(
				userSearchFacetDisplayContext.getBucketDisplayContexts());

		Assert.assertEquals(
			"alpha:4|bravo:5|bravo:3|charlie:6", nameFrequencyString);
	}

	@Test
	public void testOrderByTermValueDescending() throws Exception {
		setUpTermCollectors(
			_facetCollector,
			getTermCollectors(
				new String[] {"bravo", "alpha", "bravo", "charlie"},
				new int[] {3, 4, 5, 6}));

		UserSearchFacetDisplayContext userSearchFacetDisplayContext =
			_createDisplayContext(StringPool.BLANK, "key:desc");

		String nameFrequencyString =
			buildNameFrequencyString(
				userSearchFacetDisplayContext.getBucketDisplayContexts());

		Assert.assertEquals(
			"charlie:6|bravo:5|bravo:3|alpha:4", nameFrequencyString);
	}

	private UserSearchFacetDisplayContext _createDisplayContext(
			String paramValue)
		throws Exception {

		return _createDisplayContext(paramValue, "count:desc");
	}

	private UserSearchFacetDisplayContext _createDisplayContext(
			String paramValue, String order)
		throws Exception {

		UserSearchFacetDisplayContextBuilder
			userSearchFacetDisplayContextBuilder =
				new UserSearchFacetDisplayContextBuilder(
					getRenderRequest(
						UserFacetPortletInstanceConfiguration.class));

		userSearchFacetDisplayContextBuilder.setFacet(_facet);
		userSearchFacetDisplayContextBuilder.setFrequenciesVisible(true);
		userSearchFacetDisplayContextBuilder.setFrequencyThreshold(0);
		userSearchFacetDisplayContextBuilder.setMaxTerms(0);
		userSearchFacetDisplayContextBuilder.setOrder(order);
		userSearchFacetDisplayContextBuilder.setParamValue(paramValue);

		return userSearchFacetDisplayContextBuilder.build();
	}

	private final Facet _facet = Mockito.mock(Facet.class);
	private final FacetCollector _facetCollector = Mockito.mock(
		FacetCollector.class);

}
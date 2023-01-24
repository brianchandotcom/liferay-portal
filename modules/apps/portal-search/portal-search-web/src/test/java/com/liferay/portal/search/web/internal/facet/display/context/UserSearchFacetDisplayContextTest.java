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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.facet.display.context.builder.UserSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.user.facet.configuration.UserFacetPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Lino Alves
 */
public class UserSearchFacetDisplayContextTest
	extends BaseFacetDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Override
	public FacetDisplayContext createFacetDisplayContext(String parameterValue)
		throws Exception {

		return createFacetDisplayContext(parameterValue, "count:desc");
	}

	@Override
	public FacetDisplayContext createFacetDisplayContext(
			String parameterValue, String order)
		throws Exception {

		UserSearchFacetDisplayContextBuilder
			userSearchFacetDisplayContextBuilder =
				new UserSearchFacetDisplayContextBuilder(
					getRenderRequest(
						UserFacetPortletInstanceConfiguration.class));

		userSearchFacetDisplayContextBuilder.setFacet(facet);
		userSearchFacetDisplayContextBuilder.setFrequenciesVisible(true);
		userSearchFacetDisplayContextBuilder.setFrequencyThreshold(0);
		userSearchFacetDisplayContextBuilder.setMaxTerms(0);
		userSearchFacetDisplayContextBuilder.setOrder(order);
		userSearchFacetDisplayContextBuilder.setParamValue(parameterValue);

		return userSearchFacetDisplayContextBuilder.build();
	}

	@Test
	public void testEmptySearchResultsWithPreviousSelection() throws Exception {
		String userName = RandomTestUtil.randomString();

		String paramValue = userName;

		FacetDisplayContext facetDisplayContext = createFacetDisplayContext(
			paramValue);

		List<BucketDisplayContext> bucketDisplayContexts =
			facetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(userName, bucketDisplayContext.getBucketText());
		Assert.assertEquals(0, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());
		Assert.assertTrue(bucketDisplayContext.isSelected());

		Assert.assertEquals(
			paramValue, facetDisplayContext.getParameterValue());
		Assert.assertFalse(facetDisplayContext.isNothingSelected());
		Assert.assertFalse(facetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTerm() throws Exception {
		String userName = RandomTestUtil.randomString();

		int frequency = RandomTestUtil.randomInt();

		setUpTermCollectors(
			facetCollector,
			Collections.singletonList(
				createTermCollector(String.valueOf(userName), frequency)));

		String paramValue = "";

		FacetDisplayContext facetDisplayContext = createFacetDisplayContext(
			paramValue);

		List<BucketDisplayContext> bucketDisplayContexts =
			facetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(userName, bucketDisplayContext.getBucketText());
		Assert.assertEquals(frequency, bucketDisplayContext.getFrequency());
		Assert.assertFalse(bucketDisplayContext.isSelected());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			paramValue, facetDisplayContext.getParameterValue());
		Assert.assertTrue(facetDisplayContext.isNothingSelected());
		Assert.assertFalse(facetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTermWithPreviousSelection() throws Exception {
		String userName = RandomTestUtil.randomString();

		int frequency = RandomTestUtil.randomInt();

		setUpTermCollectors(
			facetCollector,
			Collections.singletonList(
				createTermCollector(String.valueOf(userName), frequency)));

		String paramValue = userName;

		FacetDisplayContext facetDisplayContext = createFacetDisplayContext(
			paramValue);

		List<BucketDisplayContext> bucketDisplayContexts =
			facetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(userName, bucketDisplayContext.getBucketText());
		Assert.assertEquals(frequency, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());
		Assert.assertTrue(bucketDisplayContext.isSelected());

		Assert.assertEquals(
			paramValue, facetDisplayContext.getParameterValue());
		Assert.assertFalse(facetDisplayContext.isNothingSelected());
		Assert.assertFalse(facetDisplayContext.isRenderNothing());
	}

	@Override
	protected void testOrderBy(
			String[] userNames, int[] frequencies, String order,
			String[] expectedUserNames, int[] expectedFrequencies)
		throws Exception {

		setUpTermCollectors(
			facetCollector, getTermCollectors(userNames, frequencies));

		FacetDisplayContext facetDisplayContext = createFacetDisplayContext(
			StringPool.BLANK, order);

		assertFacetOrder(
			facetDisplayContext.getBucketDisplayContexts(), expectedUserNames,
			expectedFrequencies);
	}

}
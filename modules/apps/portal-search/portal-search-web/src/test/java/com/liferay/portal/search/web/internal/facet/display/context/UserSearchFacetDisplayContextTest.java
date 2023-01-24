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
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.facet.display.context.builder.UserSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.user.facet.configuration.UserFacetPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;

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
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
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.facet.display.context.builder.AssetTagsSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.tag.facet.configuration.TagFacetPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author André de Oliveira
 */
public class AssetTagsSearchFacetDisplayContextTest
	extends BaseFacetDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Override
	public FacetDisplayContext createFacetDisplayContext(String parameterValue)
		throws ConfigurationException {

		return createFacetDisplayContext(parameterValue, "count:desc");
	}

	@Override
	public FacetDisplayContext createFacetDisplayContext(
			String parameterValue, String order)
		throws ConfigurationException {

		AssetTagsSearchFacetDisplayContextBuilder
			assetTagsSearchFacetDisplayContextBuilder =
				new AssetTagsSearchFacetDisplayContextBuilder(
					getRenderRequest(
						TagFacetPortletInstanceConfiguration.class));

		assetTagsSearchFacetDisplayContextBuilder.setDisplayStyle("cloud");
		assetTagsSearchFacetDisplayContextBuilder.setFacet(facet);
		assetTagsSearchFacetDisplayContextBuilder.setFrequenciesVisible(true);
		assetTagsSearchFacetDisplayContextBuilder.setFrequencyThreshold(0);
		assetTagsSearchFacetDisplayContextBuilder.setMaxTerms(0);
		assetTagsSearchFacetDisplayContextBuilder.setOrder(order);
		assetTagsSearchFacetDisplayContextBuilder.setParameterName(
			facet.getFieldId());
		assetTagsSearchFacetDisplayContextBuilder.setParameterValue(
			parameterValue);

		return assetTagsSearchFacetDisplayContextBuilder.build();
	}

	@Override
	protected void testOrderBy(
			String[] terms, int[] frequencies, String order,
			String[] expectedTerms, int[] expectedFrequencies)
		throws Exception {

		setUpTermCollectors(
			facetCollector, getTermCollectors(terms, frequencies));

		FacetDisplayContext facetDisplayContext = createFacetDisplayContext(
			StringPool.BLANK, order);

		assertFacetOrder(
			facetDisplayContext.getBucketDisplayContexts(), expectedTerms,
			expectedFrequencies);
	}

}
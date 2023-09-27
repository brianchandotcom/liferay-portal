/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.facet.display.context.builder.AssetTagsSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.tag.facet.configuration.TagFacetPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;

import org.mockito.Mockito;

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

		configurationProviderUtilMockedStatic.when(
			() -> ConfigurationProviderUtil.getPortletInstanceConfiguration(
				Mockito.any(), Mockito.any())
		).thenReturn(
			Mockito.mock(TagFacetPortletInstanceConfiguration.class)
		);

		AssetTagsSearchFacetDisplayContextBuilder
			assetTagsSearchFacetDisplayContextBuilder =
				new AssetTagsSearchFacetDisplayContextBuilder(
					getRenderRequest());

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
			int[] expectedFrequencies, String[] expectedTerms,
			int[] frequencies, String order, String[] terms)
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
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
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.facet.display.context.builder.AssetEntriesSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.type.facet.configuration.TypeFacetPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gustavo Lima
 */
public class AssetEntriesSearchFacetDisplayContextTest
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

	@Override
	@Test
	public void testEmptySearchResults() throws Exception {
	}

	@Override
	@Test
	public void testOrderByTermValueAscending() throws Exception {
		testOrderBy(
			new String[] {"bravo", "alpha", "charlie"}, new int[] {2, 3, 4},
			"key:asc", new String[] {"alpha", "bravo", "charlie"},
			new int[] {3, 2, 4});
	}

	@Override
	@Test
	public void testOrderByTermValueDescending() throws Exception {
		testOrderBy(
			new String[] {"bravo", "alpha", "charlie"}, new int[] {2, 3, 4},
			"key:desc", new String[] {"charlie", "bravo", "alpha"},
			new int[] {4, 2, 3});
	}

	@Override
	protected void testOrderBy(
			String[] classNames, int[] frequencies, String order,
			String[] expectedClassNames, int[] expectedFrequencies)
		throws Exception {

		_mockResourceActions(classNames);

		setUpTermCollectors(
			_facetCollector, getTermCollectors(classNames, frequencies));

		FacetDisplayContext facetDisplayContext = _createFacetDisplayContext(
			classNames, order);

		assertFacetOrder(
			facetDisplayContext.getBucketDisplayContexts(), expectedClassNames,
			expectedFrequencies);
	}

	private FacetDisplayContext _createFacetDisplayContext(
			String[] classNames, String order)
		throws Exception {

		AssetEntriesSearchFacetDisplayContextBuilder
			assetEntriesSearchFacetDisplayContextBuilder =
				new AssetEntriesSearchFacetDisplayContextBuilder(
					getRenderRequest(
						TypeFacetPortletInstanceConfiguration.class));

		assetEntriesSearchFacetDisplayContextBuilder.setClassNames(classNames);
		assetEntriesSearchFacetDisplayContextBuilder.setFacet(_facet);
		assetEntriesSearchFacetDisplayContextBuilder.setFrequenciesVisible(
			true);
		assetEntriesSearchFacetDisplayContextBuilder.setFrequencyThreshold(0);
		assetEntriesSearchFacetDisplayContextBuilder.setLocale(LocaleUtil.US);
		assetEntriesSearchFacetDisplayContextBuilder.setOrder(order);
		assetEntriesSearchFacetDisplayContextBuilder.setParameterName(
			_facet.getFieldId());
		assetEntriesSearchFacetDisplayContextBuilder.setParameterValue(
			StringPool.BLANK);

		return assetEntriesSearchFacetDisplayContextBuilder.build();
	}

	private void _mockResourceActions(String[] classNames) {
		ResourceActionsUtil resourceActionsUtil = new ResourceActionsUtil();

		ResourceActions resourceActions = Mockito.mock(ResourceActions.class);

		resourceActionsUtil.setResourceActions(resourceActions);

		for (String className : classNames) {
			Mockito.doReturn(
				className
			).when(
				resourceActions
			).getModelResource(
				Mockito.any(Locale.class), Mockito.eq(className)
			);
		}
	}

	private final Facet _facet = Mockito.mock(Facet.class);
	private final FacetCollector _facetCollector = Mockito.mock(
		FacetCollector.class);

}
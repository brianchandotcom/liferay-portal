/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.facet.display.context.builder.AssetEntriesSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.type.facet.configuration.TypeFacetPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

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

	@Override
	@Test
	public void testEmptySearchResults() throws Exception {
	}

	@Override
	@Test
	public void testEmptySearchResultsWithPreviousSelection() throws Exception {
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
	public void testOrderByTermValueAscending() throws Exception {
		testOrderBy(
			new int[] {3, 2, 4}, new String[] {"alpha", "bravo", "charlie"},
			new int[] {2, 3, 4}, "key:asc",
			new String[] {"bravo", "alpha", "charlie"});
	}

	@Override
	@Test
	public void testOrderByTermValueDescending() throws Exception {
		testOrderBy(
			new int[] {4, 2, 3}, new String[] {"charlie", "bravo", "alpha"},
			new int[] {2, 3, 4}, "key:desc",
			new String[] {"bravo", "alpha", "charlie"});
	}

	@Override
	protected void testOrderBy(
			int[] expectedFrequencies, String[] expectedClassNames,
			int[] frequencies, String order, String[] classNames)
		throws Exception {

		_mockResourceActions(classNames);

		setUpTermCollectors(
			facetCollector, getTermCollectors(classNames, frequencies));

		FacetDisplayContext facetDisplayContext = _createFacetDisplayContext(
			classNames, order);

		assertFacetOrder(
			facetDisplayContext.getBucketDisplayContexts(), expectedClassNames,
			expectedFrequencies);
	}

	private FacetDisplayContext _createFacetDisplayContext(
			String[] classNames, String order)
		throws Exception {

		configurationProviderUtilMockedStatic.when(
			() -> ConfigurationProviderUtil.getPortletInstanceConfiguration(
				Mockito.any(), Mockito.any())
		).thenReturn(
			Mockito.mock(TypeFacetPortletInstanceConfiguration.class)
		);

		AssetEntriesSearchFacetDisplayContextBuilder
			assetEntriesSearchFacetDisplayContextBuilder =
				new AssetEntriesSearchFacetDisplayContextBuilder(
					getRenderRequest());

		assetEntriesSearchFacetDisplayContextBuilder.setClassNames(classNames);
		assetEntriesSearchFacetDisplayContextBuilder.setFacet(facet);
		assetEntriesSearchFacetDisplayContextBuilder.setFrequenciesVisible(
			true);
		assetEntriesSearchFacetDisplayContextBuilder.setFrequencyThreshold(0);
		assetEntriesSearchFacetDisplayContextBuilder.setLocale(LocaleUtil.US);
		assetEntriesSearchFacetDisplayContextBuilder.setOrder(order);
		assetEntriesSearchFacetDisplayContextBuilder.setParameterName(
			facet.getFieldId());
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

}
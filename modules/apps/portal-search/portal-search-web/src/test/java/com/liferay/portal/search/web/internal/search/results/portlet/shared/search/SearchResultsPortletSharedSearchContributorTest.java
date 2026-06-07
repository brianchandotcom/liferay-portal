/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.search.results.portlet.shared.search;

import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderImpl;
import com.liferay.portal.search.internal.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Olivia Yu
 */
public class SearchResultsPortletSharedSearchContributorTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testContribute() {
		_testContribute(
			PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA,
			String.valueOf(PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA + 1));

		int paginationDelta = PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA - 1;

		_testContribute(
			paginationDelta, String.valueOf(paginationDelta));
	}

	private void _testContribute(
		int expectedPaginationDelta, String paginationDeltaParameterValue) {

		SearchResultsPortletSharedSearchContributor
			searchResultsPortletSharedSearchContributor =
				new SearchResultsPortletSharedSearchContributor();

		PortletSharedSearchSettings portletSharedSearchSettings =
			_createPortletSharedSearchSettings(paginationDeltaParameterValue);

		searchResultsPortletSharedSearchContributor.contribute(
			portletSharedSearchSettings);

		Mockito.verify(
			portletSharedSearchSettings
		).setPaginationDelta(
			expectedPaginationDelta
		);

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(null);

		SearchRequest searchRequest = searchRequestBuilder.build();

		Assert.assertEquals(
			Integer.valueOf(expectedPaginationDelta), searchRequest.getSize());
	}

	private PortletSharedSearchSettings _createPortletSharedSearchSettings(
		String paginationDeltaParameterValue) {

		PortletSharedSearchSettings portletSharedSearchSettings = Mockito.mock(
			PortletSharedSearchSettings.class);

		SearchRequestBuilder searchRequestBuilder =
			new SearchRequestBuilderImpl(new SearchRequestBuilderFactoryImpl());

		Mockito.doReturn(
			searchRequestBuilder
		).when(
			portletSharedSearchSettings
		).getFederatedSearchRequestBuilder(
			Mockito.any()
		);

		Mockito.doReturn(
			paginationDeltaParameterValue
		).when(
			portletSharedSearchSettings
		).getParameter(
			"delta"
		);

		Mockito.doReturn(
			Mockito.mock(PortletPreferences.class)
		).when(
			portletSharedSearchSettings
		).getPortletPreferences();

		return portletSharedSearchSettings;
	}

}
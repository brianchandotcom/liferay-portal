/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.searcher;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.BaseRankingsWebTestCase;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.searcher.helper.RankingSearchRequestHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingSearchRequestContributorTest
	extends BaseRankingsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_rankingSearchRequestContributor, "rankingIndexNameBuilder",
			rankingIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_rankingSearchRequestContributor, "rankingIndexReader",
			_rankingIndexReader);
		ReflectionTestUtil.setFieldValue(
			_rankingSearchRequestContributor, "rankingSearchRequestHelper",
			_rankingSearchRequestHelper);
		ReflectionTestUtil.setFieldValue(
			_rankingSearchRequestContributor, "searchEngineHelper",
			_searchEngineHelper);
		ReflectionTestUtil.setFieldValue(
			_rankingSearchRequestContributor, "searchRequestBuilderFactory",
			searchRequestBuilderFactory);
	}

	@Test
	public void testContributeIsSearchEngineSolrTrue() {
		SearchEngine searchEngine = Mockito.mock(SearchEngine.class);

		Mockito.doReturn(
			"Solr"
		).when(
			searchEngine
		).getVendor();

		Mockito.doReturn(
			searchEngine
		).when(
			_searchEngineHelper
		).getSearchEngine();

		SearchRequest searchRequest = Mockito.mock(SearchRequest.class);

		Assert.assertEquals(
			searchRequest,
			_rankingSearchRequestContributor.contribute(searchRequest));
	}

	@Test
	public void testContributeRankingIndexReaderIsExistsFalse() {
		_setUpContributorMocks(false);

		SearchRequest searchRequest = Mockito.mock(SearchRequest.class);

		Assert.assertEquals(
			searchRequest,
			_rankingSearchRequestContributor.contribute(searchRequest));
	}

	@Test
	public void testContributeRankingIndexReaderIsExistsTrue() {
		SearchRequestBuilder searchRequestBuilder = _setUpContributorMocks(
			true);

		Mockito.doReturn(
			Mockito.mock(List.class)
		).when(
			_rankingIndexReader
		).fetch(
			Mockito.anyString(), Mockito.anyString(), Mockito.any(),
			Mockito.anyString()
		);

		Mockito.doNothing(
		).when(
			_rankingSearchRequestHelper
		).contribute(
			Mockito.any(), Mockito.any()
		);

		SearchRequest searchRequest = Mockito.mock(SearchRequest.class);

		Mockito.doReturn(
			searchRequest
		).when(
			searchRequestBuilder
		).build();

		Mockito.doReturn(
			Mockito.mock(SearchContext.class)
		).when(
			searchRequestBuilder
		).withSearchContextGet(
			Function.identity()
		);

		Assert.assertEquals(
			searchRequest,
			_rankingSearchRequestContributor.contribute(searchRequest));
	}

	@SuppressWarnings("unchecked")
	private SearchRequestBuilder _setUpContributorMocks(
		boolean rankingIndexNameExist) {

		SearchRequestBuilder searchRequestBuilder = Mockito.mock(
			SearchRequestBuilder.class);

		Mockito.doReturn(
			searchRequestBuilder
		).when(
			searchRequestBuilder
		).withSearchContext(
			Mockito.any(Consumer.class)
		);

		setUpRankingIndexNameBuilder();
		_setUpSearchEngineHelper();
		setUpSearchRequestBuilderFactory(searchRequestBuilder);

		Mockito.doReturn(
			rankingIndexNameExist
		).when(
			_rankingIndexReader
		).isExists(
			Mockito.any()
		);

		return searchRequestBuilder;
	}

	private void _setUpSearchEngineHelper() {
		SearchEngine searchEngine = Mockito.mock(SearchEngine.class);

		Mockito.doReturn(
			"Elasticsearch"
		).when(
			searchEngine
		).getVendor();

		Mockito.doReturn(
			searchEngine
		).when(
			_searchEngineHelper
		).getSearchEngine();
	}

	private final RankingIndexReader _rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);
	private final RankingSearchRequestContributor
		_rankingSearchRequestContributor =
			new RankingSearchRequestContributor();
	private final RankingSearchRequestHelper _rankingSearchRequestHelper =
		Mockito.mock(RankingSearchRequestHelper.class);
	private final SearchEngineHelper _searchEngineHelper = Mockito.mock(
		SearchEngineHelper.class);

}
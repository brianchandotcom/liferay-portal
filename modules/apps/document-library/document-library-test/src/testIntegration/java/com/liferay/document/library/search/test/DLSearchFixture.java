/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.search.test;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.test.util.HitsAssert;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Eric Yan
 */
public class DLSearchFixture {

	public DLSearchFixture(
		IndexerRegistry indexerRegistry,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_indexerRegistry = indexerRegistry;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	public SearchContext getSearchContext(String keywords, Locale locale)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(TestPropsValues.getCompanyId());
		searchContext.setGroupIds(new long[] {_group.getGroupId()});
		searchContext.setKeywords(keywords);
		searchContext.setLocale(Objects.requireNonNull(locale));
		searchContext.setUserId(getUserId());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSelectedFieldNames(StringPool.STAR);

		return searchContext;
	}

	public Document searchOnlyOne(String keywords, Locale locale)
		throws Exception {

		return HitsAssert.assertOnlyOne(
			search(getSearchContext(keywords, locale)));
	}

	public SearchResponse searchOnlyOneSearchResponse(
			String keywords, Locale locale)
		throws Exception {

		SearchContext searchContext = getSearchContext(keywords, locale);

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(searchContext);

		searchRequestBuilder.fetchSource(true);

		searchRequestBuilder.build();

		search(searchContext);

		SearchResponse searchResponse =
			(SearchResponse)searchContext.getAttribute("search.response");

		HitsAssert.assertOnlyOne(searchResponse.getSearchHits());

		return searchResponse;
	}

	public void setGroup(Group group) {
		_group = group;
	}

	public void setIndexerClass(Class<?> clazz) {
		_indexer = _indexerRegistry.getIndexer(clazz);
	}

	public void setUser(User user) {
		_user = user;
	}

	protected long getUserId() throws Exception {
		if (_user != null) {
			return _user.getUserId();
		}

		return TestPropsValues.getUserId();
	}

	protected Hits search(SearchContext searchContext) throws Exception {
		return _indexer.search(searchContext);
	}

	private Group _group;
	private Indexer<?> _indexer;
	private final IndexerRegistry _indexerRegistry;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private User _user;

}
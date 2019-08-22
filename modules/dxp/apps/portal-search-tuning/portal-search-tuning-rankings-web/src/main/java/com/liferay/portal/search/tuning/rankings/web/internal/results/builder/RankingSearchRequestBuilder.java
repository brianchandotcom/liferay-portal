/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.results.builder;

import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;

/**
 * @author André de Oliveira
 * @author Bryan Engler
 */
public class RankingSearchRequestBuilder {

	public RankingSearchRequestBuilder(
		ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory,
		Queries queries,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_complexQueryPartBuilderFactory = complexQueryPartBuilderFactory;
		_queries = queries;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	public SearchRequestBuilder build() {
		return _searchRequestBuilderFactory.builder(
		).addComplexQueryPart(
			_complexQueryPartBuilderFactory.builder(
			).additive(
				true
			).query(
				getIdsQuery(_queryString)
			).occur(
				"should"
			).build()
		).from(
			_from
		).queryString(
			_queryString
		).size(
			_size
		).withSearchContext(
			searchContext -> searchContext.setCompanyId(_companyId)
		);
	}

	public RankingSearchRequestBuilder companyId(long companyId) {
		_companyId = companyId;

		return this;
	}

	public RankingSearchRequestBuilder from(int from) {
		_from = from;

		return this;
	}

	public RankingSearchRequestBuilder queryString(String queryString) {
		_queryString = queryString;

		return this;
	}

	public RankingSearchRequestBuilder size(int size) {
		_size = size;

		return this;
	}

	protected Query getIdsQuery(String id) {
		IdsQuery idsQuery = _queries.ids();

		idsQuery.addIds(id);

		return idsQuery;
	}

	private long _companyId;
	private final ComplexQueryPartBuilderFactory
		_complexQueryPartBuilderFactory;
	private int _from;
	private final Queries _queries;
	private String _queryString;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private int _size;

}
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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

import java.util.stream.Stream;

/**
 * @author André de Oliveira
 * @author Bryan Engler
 */
public class RankingGetSearchResultsBuilder {

	public RankingGetSearchResultsBuilder(
		ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory,
		Queries queries, Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_complexQueryPartBuilderFactory = complexQueryPartBuilderFactory;
		_queries = queries;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	public JSONArray build() {
		Stream<JSONObject> stream = getElements();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		stream.forEach(jsonArray::put);

		return jsonArray;
	}

	public RankingGetSearchResultsBuilder companyId(long companyId) {
		_companyId = companyId;

		return this;
	}

	public RankingGetSearchResultsBuilder from(int from) {
		_from = from;

		return this;
	}

	public RankingGetSearchResultsBuilder queryString(String queryString) {
		_queryString = queryString;

		return this;
	}

	public RankingGetSearchResultsBuilder size(int size) {
		_size = size;

		return this;
	}

	protected SearchRequest buildSearchRequest() {
		RankingSearchRequestBuilder rankingSearchRequestBuilder =
			new RankingSearchRequestBuilder(
				_complexQueryPartBuilderFactory, _queries,
				_searchRequestBuilderFactory);

		return rankingSearchRequestBuilder.companyId(
			_companyId
		).from(
			_from
		).size(
			_size
		).queryString(
			_queryString
		).build(
		).build();
	}

	protected Stream<JSONObject> getElements() {
		SearchRequest searchRequest = buildSearchRequest();

		SearchResponse searchResponse = _searcher.search(searchRequest);

		Stream<Document> stream = searchResponse.getDocumentsStream();

		return stream.map(this::translate);
	}

	protected JSONObject translate(Document document) {
		RankingJSONBuilder rankingJSONBuilder = new RankingJSONBuilder();

		return rankingJSONBuilder.document(
			document
		).build();
	}

	private long _companyId;
	private final ComplexQueryPartBuilderFactory
		_complexQueryPartBuilderFactory;
	private int _from;
	private final Queries _queries;
	private String _queryString;
	private final Searcher _searcher;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private int _size;

}
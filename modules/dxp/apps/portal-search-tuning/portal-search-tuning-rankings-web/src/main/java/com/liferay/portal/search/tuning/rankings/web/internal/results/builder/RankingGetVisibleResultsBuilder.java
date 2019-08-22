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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.searcher.RankingSearchRequestHelper;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 * @author Bryan Engler
 */
public class RankingGetVisibleResultsBuilder {

	public RankingGetVisibleResultsBuilder(
		ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory,
		RankingIndexReader rankingIndexReader,
		RankingSearchRequestHelper rankingSearchRequestHelper, Queries queries,
		Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_complexQueryPartBuilderFactory = complexQueryPartBuilderFactory;
		_rankingIndexReader = rankingIndexReader;
		_rankingSearchRequestHelper = rankingSearchRequestHelper;
		_queries = queries;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	public JSONArray build() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Optional<Ranking> optional = _rankingIndexReader.fetchOptional(
			_rankingId);

		Stream<JSONObject> stream = optional.map(
			this::getElements
		).orElse(
			Stream.empty()
		);

		stream.forEach(jsonArray::put);

		return jsonArray;
	}

	public RankingGetVisibleResultsBuilder companyId(long companyId) {
		_companyId = companyId;

		return this;
	}

	public RankingGetVisibleResultsBuilder from(int from) {
		_from = from;

		return this;
	}

	public RankingGetVisibleResultsBuilder queryString(String queryString) {
		_queryString = queryString;

		return this;
	}

	public RankingGetVisibleResultsBuilder rankingId(String rankingId) {
		_rankingId = rankingId;

		return this;
	}

	public RankingGetVisibleResultsBuilder size(int size) {
		_size = size;

		return this;
	}

	protected SearchRequest buildSearchRequest(Ranking ranking) {
		String queryStringOfUrl = _queryString;

		String queryString = queryStringOfUrl;

		if (Validator.isBlank(queryStringOfUrl)) {
			queryString = ranking.getQueryString();
		}

		RankingSearchRequestBuilder rankingSearchRequestBuilder =
			new RankingSearchRequestBuilder(
				_complexQueryPartBuilderFactory, _queries,
				_searchRequestBuilderFactory);

		SearchRequestBuilder searchRequestBuilder =
			rankingSearchRequestBuilder.companyId(
				_companyId
			).from(
				_from
			).queryString(
				queryString
			).size(
				_size
			).build();

		_rankingSearchRequestHelper.contribute(searchRequestBuilder, ranking);

		return searchRequestBuilder.build();
	}

	protected Stream<JSONObject> getElements(Ranking ranking) {
		SearchRequest searchRequest = buildSearchRequest(ranking);

		SearchResponse searchResponse = _searcher.search(searchRequest);

		Stream<Document> stream = searchResponse.getDocumentsStream();

		return stream.map(document -> translate(document, ranking));
	}

	protected JSONObject translate(Document document, Ranking ranking) {
		RankingJSONBuilder rankingJSONBuilder = new RankingJSONBuilder();

		return rankingJSONBuilder.document(
			document
		).pinned(
			ranking.isPinned(document.getString(Field.UID))
		).build();
	}

	private long _companyId;
	private final ComplexQueryPartBuilderFactory
		_complexQueryPartBuilderFactory;
	private int _from;
	private final Queries _queries;
	private String _queryString;
	private String _rankingId;
	private final RankingIndexReader _rankingIndexReader;
	private final RankingSearchRequestHelper _rankingSearchRequestHelper;
	private final Searcher _searcher;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private int _size;

}
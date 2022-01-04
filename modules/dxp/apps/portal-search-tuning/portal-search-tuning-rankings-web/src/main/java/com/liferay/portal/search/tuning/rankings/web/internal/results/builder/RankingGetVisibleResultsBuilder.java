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

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
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
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.searcher.helper.RankingSearchRequestHelper;
import com.liferay.portal.search.tuning.rankings.web.internal.util.RankingResultUtil;

import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author André de Oliveira
 * @author Bryan Engler
 */
public class RankingGetVisibleResultsBuilder {

	public RankingGetVisibleResultsBuilder(
		ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory,
		DLAppLocalService dlAppLocalService,
		FastDateFormatFactory fastDateFormatFactory,
		RankingIndexName rankingIndexName,
		RankingIndexReader rankingIndexReader,
		RankingSearchRequestHelper rankingSearchRequestHelper,
		ResourceActions resourceActions, ResourceRequest resourceRequest,
		ResourceResponse resourceResponse, Queries queries, Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_complexQueryPartBuilderFactory = complexQueryPartBuilderFactory;
		_dlAppLocalService = dlAppLocalService;
		_fastDateFormatFactory = fastDateFormatFactory;
		_rankingIndexName = rankingIndexName;
		_rankingIndexReader = rankingIndexReader;
		_rankingSearchRequestHelper = rankingSearchRequestHelper;
		_resourceActions = resourceActions;
		_resourceRequest = resourceRequest;
		_resourceResponse = resourceResponse;
		_queries = queries;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	public JSONObject build() {
		Optional<Ranking> optional = _rankingIndexReader.fetchOptional(
			_rankingIndexName, _rankingId);

		if (!optional.isPresent()) {
			return JSONUtil.put(
				"documents", JSONFactoryUtil.createJSONArray()
			).put(
				"total", 0
			);
		}

		Ranking ranking = optional.get();

		SearchResponse searchResponse = _getSearchResponse(ranking);

		return JSONUtil.put(
			"documents", buildDocuments(ranking, searchResponse)
		).put(
			"total", searchResponse.getTotalHits()
		);
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

	protected JSONArray buildDocuments(
		Ranking ranking, SearchResponse searchResponse) {

		Stream<Document> documentsStream = searchResponse.getDocumentsStream();

		Stream<JSONObject> jsonObjectStream = documentsStream.map(
			document -> translate(document, ranking));

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonObjectStream.forEach(jsonArray::put);

		return jsonArray;
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

	protected JSONObject translate(Document document, Ranking ranking) {
		RankingJSONBuilder rankingJSONBuilder = new RankingJSONBuilder(
			_dlAppLocalService, _fastDateFormatFactory, _resourceActions,
			_resourceRequest);

		return rankingJSONBuilder.deleted(
			_isAssetDeleted(document)
		).document(
			document
		).pinned(
			ranking.isPinned(document.getString(Field.UID))
		).viewURL(
			_getViewURL(document)
		).build();
	}

	private SearchResponse _getSearchResponse(Ranking ranking) {
		SearchRequest searchRequest = buildSearchRequest(ranking);

		return _searcher.search(searchRequest);
	}

	private String _getViewURL(Document document) {
		return RankingResultUtil.getRankingResultViewURL(
			document, _resourceRequest, _resourceResponse, true);
	}

	private boolean _isAssetDeleted(Document document) {
		return RankingResultUtil.isAssetDeleted(document);
	}

	private long _companyId;
	private final ComplexQueryPartBuilderFactory
		_complexQueryPartBuilderFactory;
	private final DLAppLocalService _dlAppLocalService;
	private final FastDateFormatFactory _fastDateFormatFactory;
	private int _from;
	private final Queries _queries;
	private String _queryString;
	private String _rankingId;
	private final RankingIndexName _rankingIndexName;
	private final RankingIndexReader _rankingIndexReader;
	private final RankingSearchRequestHelper _rankingSearchRequestHelper;
	private final ResourceActions _resourceActions;
	private final ResourceRequest _resourceRequest;
	private final ResourceResponse _resourceResponse;
	private final Searcher _searcher;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private int _size;

}
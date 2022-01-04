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
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.tuning.rankings.web.internal.util.RankingResultUtil;

import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author André de Oliveira
 * @author Bryan Engler
 */
public class RankingGetSearchResultsBuilder {

	public RankingGetSearchResultsBuilder(
		ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory,
		DLAppLocalService dlAppLocalService,
		FastDateFormatFactory fastDateFormatFactory, Queries queries,
		ResourceActions resourceActions, ResourceRequest resourceRequest,
		ResourceResponse resourceResponse, Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_complexQueryPartBuilderFactory = complexQueryPartBuilderFactory;
		_dlAppLocalService = dlAppLocalService;
		_fastDateFormatFactory = fastDateFormatFactory;
		_queries = queries;
		_resourceActions = resourceActions;
		_resourceRequest = resourceRequest;
		_resourceResponse = resourceResponse;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	public JSONObject build() {
		SearchRequest searchRequest = buildSearchRequest();

		SearchResponse searchResponse = _searcher.search(searchRequest);

		return JSONUtil.put(
			"documents", buildDocuments(searchResponse)
		).put(
			"total", searchResponse.getTotalHits()
		);
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

	protected JSONArray buildDocuments(SearchResponse searchResponse) {
		Stream<JSONObject> stream = _getElements(searchResponse);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		stream.forEach(jsonArray::put);

		return jsonArray;
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

	protected JSONObject translate(Document document) {
		RankingJSONBuilder rankingJSONBuilder = new RankingJSONBuilder(
			_dlAppLocalService, _fastDateFormatFactory, _resourceActions,
			_resourceRequest);

		return rankingJSONBuilder.deleted(
			_isAssetDeleted(document)
		).document(
			document
		).viewURL(
			_getViewURL(document)
		).build();
	}

	private Stream<JSONObject> _getElements(SearchResponse searchResponse) {
		Stream<Document> stream = searchResponse.getDocumentsStream();

		return stream.map(this::translate);
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
	private final ResourceActions _resourceActions;
	private final ResourceRequest _resourceRequest;
	private final ResourceResponse _resourceResponse;
	private final Searcher _searcher;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private int _size;

}
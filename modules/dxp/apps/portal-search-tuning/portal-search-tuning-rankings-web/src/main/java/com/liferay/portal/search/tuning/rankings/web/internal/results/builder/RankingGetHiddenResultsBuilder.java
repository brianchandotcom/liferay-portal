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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 * @author Bryan Engler
 */
public class RankingGetHiddenResultsBuilder {

	public RankingGetHiddenResultsBuilder(
		Queries queries, RankingIndexReader rankingIndexReader,
		SearchEngineAdapter searchEngineAdapter) {

		_queries = queries;
		_rankingIndexReader = rankingIndexReader;
		_searchEngineAdapter = searchEngineAdapter;
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

	public RankingGetHiddenResultsBuilder rankingId(String rankingId) {
		_rankingId = rankingId;

		return this;
	}

	protected Document getDocument(String index, String id, String type) {
		GetDocumentRequest getDocumentRequest = new GetDocumentRequest(
			index, id);

		getDocumentRequest.setType(type);
		getDocumentRequest.setFetchSourceInclude("*");

		GetDocumentResponse getDocumentResponse = _searchEngineAdapter.execute(
			getDocumentRequest);

		if (!getDocumentResponse.isExists()) {
			return null;
		}

		return getDocumentResponse.getDocument();
	}

	protected Stream<JSONObject> getElements(Ranking ranking) {
		List<String> ids = ranking.getBlockIds();

		Stream<String> stream = ids.stream();

		return stream.map(
			id -> getDocument(ranking.getIndex(), id, LIFERAY_DOCUMENT_TYPE)
		).filter(
			document -> document != null
		).map(
			this::translate
		);
	}

	protected Query getIdsQuery(List<String> ids) {
		IdsQuery idsQuery = _queries.ids();

		idsQuery.addIds(ArrayUtil.toStringArray(ids));

		return idsQuery;
	}

	protected JSONObject translate(Document document) {
		RankingJSONBuilder rankingJSONBuilder = new RankingJSONBuilder();

		return rankingJSONBuilder.document(
			document
		).hidden(
			true
		).build();
	}

	protected static final String LIFERAY_DOCUMENT_TYPE = "LiferayDocumentType";

	private final Queries _queries;
	private String _rankingId;
	private final RankingIndexReader _rankingIndexReader;
	private final SearchEngineAdapter _searchEngineAdapter;

}
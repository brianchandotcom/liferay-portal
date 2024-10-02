/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.tuning.rankings.constants.ResultRankingsConstants;
import com.liferay.portal.search.tuning.rankings.index.Ranking;
import com.liferay.portal.search.tuning.rankings.index.RankingBuilderFactory;
import com.liferay.portal.search.tuning.rankings.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexName;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 * @author Petteri Karttunen
 */
@Component(service = RankingIndexReader.class)
public class RankingIndexReaderImpl implements RankingIndexReader {

	@Override
	public List<Ranking> fetch(
		boolean excludeInactiveStatus, String groupExternalReferenceCode,
		String queryString, RankingIndexName rankingIndexName,
		String sxpBlueprintExternalReferenceCode) {

		if (rankingIndexName == null) {
			return null;
		}

		CountSearchRequest countSearchRequest = new CountSearchRequest();

		countSearchRequest.setIndexNames(rankingIndexName.getIndexName());

		BooleanQuery booleanQuery = _getBooleanQuery(
			excludeInactiveStatus, groupExternalReferenceCode, queryString,
			sxpBlueprintExternalReferenceCode);

		countSearchRequest.setQuery(booleanQuery);

		CountSearchResponse countSearchResponse = _searchEngineAdapter.execute(
			countSearchRequest);

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(rankingIndexName.getIndexName());
		searchSearchRequest.setQuery(booleanQuery);
		searchSearchRequest.setSize((int)countSearchResponse.getCount());

		return _getRankings(
			rankingIndexName,
			_searchEngineAdapter.execute(searchSearchRequest));
	}

	@Override
	public Ranking fetch(String id, RankingIndexName rankingIndexName) {
		Document document = _getDocument(rankingIndexName, id);

		if (document == null) {
			return null;
		}

		return translate(document, id);
	}

	@Override
	public List<Ranking> fetch(
		String groupExternalReferenceCode, String queryString,
		RankingIndexName rankingIndexName,
		String sxpBlueprintExternalReferenceCode) {

		if (Validator.isBlank(queryString)) {
			return null;
		}

		return fetch(
			true, groupExternalReferenceCode, queryString, rankingIndexName,
			sxpBlueprintExternalReferenceCode);
	}

	@Override
	public List<Ranking> fetchByGroupExternalReferenceCode(
		String groupExternalReferenceCode, RankingIndexName rankingIndexName) {

		return fetch(
			false, groupExternalReferenceCode, StringPool.BLANK,
			rankingIndexName, StringPool.BLANK);
	}

	@Override
	public List<Ranking> fetchBySXPBlueprintExternalReferenceCode(
		RankingIndexName rankingIndexName,
		String sxpBlueprintExternalReferenceCode) {

		return fetch(
			false, StringPool.BLANK, StringPool.BLANK, rankingIndexName,
			sxpBlueprintExternalReferenceCode);
	}

	@Override
	public boolean isExists(RankingIndexName rankingIndexName) {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(rankingIndexName.getIndexName());

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			_searchEngineAdapter.execute(indicesExistsIndexRequest);

		return indicesExistsIndexResponse.isExists();
	}

	protected Ranking translate(Document document, String id) {
		return DocumentToRankingTranslatorUtil.translate(
			_rankingBuilderFactory, document, id);
	}

	private BooleanQuery _getBooleanQuery(
		boolean excludeInactiveStatus, String groupExternalReferenceCode,
		String queryString, String sxpBlueprintExternalReferenceCode) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (!Validator.isBlank(sxpBlueprintExternalReferenceCode) &&
			FeatureFlagManagerUtil.isEnabled("LPD-6368")) {

			booleanQuery.addFilterQueryClauses(
				_queries.term(
					RankingFields.SXP_BLUEPRINT_EXTERNAL_REFERENCE_CODE,
					sxpBlueprintExternalReferenceCode));
		}
		else if (!Validator.isBlank(groupExternalReferenceCode) &&
				 FeatureFlagManagerUtil.isEnabled("LPD-6368")) {

			booleanQuery.addFilterQueryClauses(
				_queries.term(
					RankingFields.GROUP_EXTERNAL_REFERENCE_CODE,
					groupExternalReferenceCode));
		}
		else {
			booleanQuery.addMustNotQueryClauses(
				_queries.wildcard(
					RankingFields.SXP_BLUEPRINT_EXTERNAL_REFERENCE_CODE,
					StringPool.QUESTION + StringPool.STAR),
				_queries.wildcard(
					RankingFields.GROUP_EXTERNAL_REFERENCE_CODE,
					StringPool.QUESTION + StringPool.STAR));
		}

		if (!Validator.isBlank(queryString)) {
			booleanQuery.addFilterQueryClauses(
				_queries.term(
					RankingFields.QUERY_STRINGS_KEYWORD, queryString));
		}

		if (excludeInactiveStatus) {
			booleanQuery.addMustNotQueryClauses(
				_queries.term(
					RankingFields.STATUS,
					ResultRankingsConstants.STATUS_INACTIVE));
		}

		booleanQuery.addMustNotQueryClauses(
			_queries.term(
				RankingFields.STATUS,
				ResultRankingsConstants.STATUS_NOT_APPLICABLE));

		if (!FeatureFlagManagerUtil.isEnabled("LPD-6368")) {
			booleanQuery.addFilterQueryClauses(
				_queries.term(
					RankingFields.SXP_BLUEPRINT_EXTERNAL_REFERENCE_CODE,
					StringPool.BLANK),
				_queries.term(
					RankingFields.GROUP_EXTERNAL_REFERENCE_CODE,
					StringPool.BLANK));
		}

		return booleanQuery;
	}

	private Document _getDocument(
		RankingIndexName rankingIndexName, String id) {

		GetDocumentRequest getDocumentRequest = new GetDocumentRequest(
			rankingIndexName.getIndexName(), id);

		getDocumentRequest.setFetchSource(true);
		getDocumentRequest.setPreferLocalCluster(false);

		GetDocumentResponse getDocumentResponse = _searchEngineAdapter.execute(
			getDocumentRequest);

		if (getDocumentResponse.isExists()) {
			return getDocumentResponse.getDocument();
		}

		return null;
	}

	private List<Ranking> _getRankings(
		RankingIndexName rankingIndexName,
		SearchSearchResponse searchSearchResponse) {

		if (searchSearchResponse.getCount() == 0) {
			return null;
		}

		List<Ranking> rankings = new ArrayList<>();

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		for (SearchHit searchHit : searchHitsList) {
			Ranking ranking = fetch(searchHit.getId(), rankingIndexName);

			if (Validator.isBlank(ranking.getGroupExternalReferenceCode()) &&
				Validator.isBlank(
					ranking.getSXPBlueprintExternalReferenceCode())) {

				rankings.add(0, ranking);
			}
			else {
				rankings.add(ranking);
			}
		}

		return rankings;
	}

	@Reference
	private Queries _queries;

	@Reference
	private RankingBuilderFactory _rankingBuilderFactory;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}
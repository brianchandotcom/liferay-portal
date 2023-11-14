/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsConstants;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;
import com.liferay.search.experiences.model.SXPBlueprint;

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
	public List<Ranking> fetch(Group group) throws PortalException {
		return fetch(
			group.getExternalReferenceCode(), StringPool.BLANK,
			_rankingIndexNameBuilder.getRankingIndexName(group.getCompanyId()),
			StringPool.BLANK);
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

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(rankingIndexName.getIndexName());
		searchSearchRequest.setQuery(
			_getQuery(
				groupExternalReferenceCode, queryString,
				sxpBlueprintExternalReferenceCode));
		searchSearchRequest.setSize(1);

		return _getRankings(
			rankingIndexName,
			_searchEngineAdapter.execute(searchSearchRequest));
	}

	@Override
	public List<Ranking> fetch(SXPBlueprint sxpBlueprint)
		throws PortalException {

		return fetch(
			StringPool.BLANK, StringPool.BLANK,
			_rankingIndexNameBuilder.getRankingIndexName(
				sxpBlueprint.getCompanyId()),
			sxpBlueprint.getExternalReferenceCode());
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
		return _documentToRankingTranslator.translate(document, id);
	}

	private Document _getDocument(
		RankingIndexName rankingIndexName, String id) {

		GetDocumentRequest getDocumentRequest = new GetDocumentRequest(
			rankingIndexName.getIndexName(), id);

		getDocumentRequest.setFetchSource(true);
		getDocumentRequest.setFetchSourceInclude(StringPool.STAR);
		getDocumentRequest.setPreferLocalCluster(false);

		GetDocumentResponse getDocumentResponse = _searchEngineAdapter.execute(
			getDocumentRequest);

		if (getDocumentResponse.isExists()) {
			return getDocumentResponse.getDocument();
		}

		return null;
	}

	private BooleanQuery _getQuery(
		String groupExternalReferenceCode, String queryString,
		String sxpBlueprintExternalReferenceCode) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (!Validator.isBlank(sxpBlueprintExternalReferenceCode) &&
			FeatureFlagManagerUtil.isEnabled("LPS-159650")) {

			booleanQuery.addFilterQueryClauses(
				_queries.term(
					RankingFields.SXP_BLUEPRINT_EXTERNAL_REFERENCE_CODE,
					sxpBlueprintExternalReferenceCode));
		}
		else if (!Validator.isBlank(groupExternalReferenceCode) &&
				 FeatureFlagManagerUtil.isEnabled("LPS-157988")) {

			booleanQuery.addFilterQueryClauses(
				_queries.term(
					RankingFields.GROUP_EXTERNAL_REFERENCE_CODE,
					groupExternalReferenceCode));
		}

		if (!Validator.isBlank(queryString)) {
			booleanQuery.addFilterQueryClauses(
				_queries.term(
					RankingFields.QUERY_STRINGS_KEYWORD, queryString));
		}

		booleanQuery.addMustNotQueryClauses(
			_queries.term(
				RankingFields.STATUS, ResultRankingsConstants.INACTIVE));

		return booleanQuery;
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
	private DocumentToRankingTranslator _documentToRankingTranslator;

	@Reference
	private Queries _queries;

	@Reference
	private RankingIndexNameBuilder _rankingIndexNameBuilder;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}
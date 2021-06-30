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

package com.liferay.portal.search.tuning.rankings.web.internal.storage;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;
import com.liferay.portal.search.tuning.rankings.web.internal.index.DocumentToRankingTranslator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;
import com.liferay.search.tuning.rankings.service.STRankingsEntryLocalService;
import com.liferay.search.tuning.rankings.storage.RankingsDatabaseImporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = RankingsDatabaseImporter.class)
public class RankingsDatabaseImporterImpl implements RankingsDatabaseImporter {

	public void populateDatabase(long companyId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		RankingIndexName rankingIndexName =
			rankingIndexNameBuilder.getRankingIndexName(companyId);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Importing documents from " + rankingIndexName.getIndexName());
		}

		searchSearchRequest.setIndexNames(rankingIndexName.getIndexName());

		searchSearchRequest.setFetchSource(true);
		searchSearchRequest.setQuery(queries.matchAll());

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		for (SearchHit searchHit : searchHitsList) {
			Ranking ranking = documentToRankingTranslator.translate(
				searchHit.getDocument(), searchHit.getId());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Adding database entry for document with id_ " +
						ranking.getRankingDocumentId());
			}

			stRankingsEntryLocalService.addSTRankingsEntry(
				ranking.getAliases(), ranking.getHiddenDocumentIds(),
				ranking.isInactive(), ranking.getIndexName(), ranking.getName(),
				_getDocumentIdsMap(ranking.getPins()),
				ranking.getQueryString());
		}

		if (_log.isInfoEnabled()) {
			_log.info("Reindexing " + rankingIndexName.getIndexName());
		}

		try {
			indexReindexer.reindex(new long[] {companyId});
		}
		catch (Exception exception) {
			_log.error("Unable to reindex " + rankingIndexName.getIndexName());
		}
	}

	@Reference
	protected DocumentToRankingTranslator documentToRankingTranslator;

	@Reference(
		target = "(model.class.name=com.liferay.search.tuning.rankings.model.STRankingsEntry)"
	)
	protected IndexReindexer indexReindexer;

	@Reference
	protected Queries queries;

	@Reference
	protected RankingIndexNameBuilder rankingIndexNameBuilder;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	@Reference
	protected STRankingsEntryLocalService stRankingsEntryLocalService;

	private Map<Integer, String> _getDocumentIdsMap(List<Ranking.Pin> pins) {
		Map<Integer, String> documentIdsMap = new HashMap<>();

		for (Ranking.Pin pin : pins) {
			documentIdsMap.put(pin.getPosition(), pin.getDocumentId());
		}

		return documentIdsMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingsDatabaseImporterImpl.class);

}
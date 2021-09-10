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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.tuning.rankings.storage.RankingsDatabaseImporter;
import com.liferay.portal.search.tuning.rankings.web.internal.index.DocumentToRankingTranslator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReindexer;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = RankingsDatabaseImporter.class)
public class RankingsDatabaseImporterImpl implements RankingsDatabaseImporter {

	@Override
	public void populateDatabase(long companyId) {
		try {
			_populateDatabase(companyId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to import rankings from the index to the ",
						"database for company id ", companyId, ". Make sure ",
						"the search engine is connected, then run the ",
						"rankings database importer Groovy script"),
					exception);
			}
		}
	}

	@Reference
	protected DocumentToRankingTranslator documentToRankingTranslator;

	@Reference
	protected Queries queries;

	@Reference
	protected RankingIndexNameBuilder rankingIndexNameBuilder;

	@Reference
	protected RankingIndexReindexer rankingIndexReindexer;

	@Reference
	protected RankingJSONStorageHelper rankingJSONStorageHelper;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private boolean _isStandardFormat(String id) {
		String[] parts = StringUtil.split(id, "_PORTLET_");

		if (parts.length == 2) {
			return true;
		}

		return false;
	}

	private void _populateDatabase(long companyId) {
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
			if (_isStandardFormat(searchHit.getId())) {
				continue;
			}

			Ranking ranking = documentToRankingTranslator.translate(
				searchHit.getDocument(), searchHit.getId());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Adding database entry for document with ID " +
						ranking.getRankingDocumentId());
			}

			rankingJSONStorageHelper.addJSONStorageEntry(
				companyId, ranking.getAliases(), ranking.getHiddenDocumentIds(),
				ranking.isInactive(), ranking.getIndexName(), ranking.getName(),
				ranking.getPins(), ranking.getQueryString());
		}

		if (_log.isInfoEnabled()) {
			_log.info("Reindexing " + rankingIndexName.getIndexName());
		}

		try {
			rankingIndexReindexer.reindex(new long[] {companyId});
		}
		catch (Exception exception) {
			_log.error("Unable to reindex " + rankingIndexName.getIndexName());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingsDatabaseImporterImpl.class);

}
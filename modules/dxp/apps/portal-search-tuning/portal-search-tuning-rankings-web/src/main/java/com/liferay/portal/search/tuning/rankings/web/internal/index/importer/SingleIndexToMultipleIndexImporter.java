/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.importer;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.tuning.rankings.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreatorUtil;

import java.util.List;

/**
 * @author Wade Cao
 * @author Adam Brandizzi
 */
public class SingleIndexToMultipleIndexImporter {

	public SingleIndexToMultipleIndexImporter(
		IndexNameBuilder indexNameBuilder, Queries queries,
		RankingIndexReader rankingIndexReader,
		SearchEngineAdapter searchEngineAdapter) {

		_indexNameBuilder = indexNameBuilder;
		_queries = queries;
		_rankingIndexReader = rankingIndexReader;
		_searchEngineAdapter = searchEngineAdapter;
	}

	public void importRankings(long companyId) {
		try {
			_importCompanyDocumentsAndDeleteFromSingleIndex(
				_indexNameBuilder.getIndexName(companyId));

			_deleteSingleIndexIfEmpty();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to import result ranking documents for company " +
						companyId,
					exception);
			}
		}
	}

	public boolean needImport() {
		return _rankingIndexReader.isExists(SINGLE_INDEX_NAME);
	}

	protected static final String RANKINGS_INDEX_NAME_SUFFIX =
		"search-tuning-rankings";

	protected static final RankingIndexName SINGLE_INDEX_NAME =
		() -> "liferay-search-tuning-rankings";

	private void _deleteSingleIndexIfEmpty() {
		if (_getSingleIndexDocumentCount() == 0) {
			if (_log.isInfoEnabled()) {
				_log.info("Deleting index " + SINGLE_INDEX_NAME.getIndexName());
			}

			RankingIndexCreatorUtil.deleteIfExists(
				_searchEngineAdapter, SINGLE_INDEX_NAME);
		}
	}

	private String _getRankingIndexName(String companyIndexName) {
		return companyIndexName + StringPool.DASH + RANKINGS_INDEX_NAME_SUFFIX;
	}

	private long _getSingleIndexDocumentCount() {
		CountSearchRequest countSearchRequest = new CountSearchRequest();

		countSearchRequest.setIndexNames(SINGLE_INDEX_NAME.getIndexName());
		countSearchRequest.setQuery(_queries.matchAll());

		CountSearchResponse countSearchResponse = _searchEngineAdapter.execute(
			countSearchRequest);

		return countSearchResponse.getCount();
	}

	private List<Document> _getSingleIndexDocuments(String companyIndexName) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(SINGLE_INDEX_NAME.getIndexName());
		searchSearchRequest.setQuery(_queries.term("index", companyIndexName));
		searchSearchRequest.setFetchSource(true);
		searchSearchRequest.setSelectedFieldNames(StringPool.BLANK);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		return TransformUtil.transform(
			searchHits.getSearchHits(), SearchHit::getDocument);
	}

	private void _importCompanyDocumentsAndDeleteFromSingleIndex(
		String companyIndexName) {

		List<Document> documents = _getSingleIndexDocuments(companyIndexName);

		if (documents.isEmpty()) {
			return;
		}

		String rankingIndexName = _getRankingIndexName(companyIndexName);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Importing result ranking documents to index " +
					rankingIndexName);
		}

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		documents.forEach(
			document -> {
				bulkDocumentRequest.addBulkableDocumentRequest(
					new IndexDocumentRequest(rankingIndexName, document));
				bulkDocumentRequest.addBulkableDocumentRequest(
					new DeleteDocumentRequest(
						SINGLE_INDEX_NAME.getIndexName(),
						document.getString("uid")));
			});

		bulkDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(bulkDocumentRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SingleIndexToMultipleIndexImporter.class);

	private final IndexNameBuilder _indexNameBuilder;
	private final Queries _queries;
	private final RankingIndexReader _rankingIndexReader;
	private final SearchEngineAdapter _searchEngineAdapter;

}
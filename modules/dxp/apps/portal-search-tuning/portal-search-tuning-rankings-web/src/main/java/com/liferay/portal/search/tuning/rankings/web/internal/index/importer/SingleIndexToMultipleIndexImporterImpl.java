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

package com.liferay.portal.search.tuning.rankings.web.internal.index.importer;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author Adam Brandizzi
 */
@Component(service = SingleIndexToMultipleIndexImporter.class)
public class SingleIndexToMultipleIndexImporterImpl
	implements SingleIndexToMultipleIndexImporter {

	@Override
	public void importRankings() {
		try {
			createRankingIndices();

			importDocuments();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to create result ranking indices for existing " +
						"companies",
					exception);
			}
		}
	}

	protected static Map<String, List<Document>> groupDocumentByIndex(
		List<Document> documents) {

		Stream<Document> stream = documents.stream();

		return stream.collect(
			Collectors.groupingBy(document -> document.getString("index")));
	}

	protected boolean addDocuments(String indexName, List<Document> documents) {
		boolean successed = true;

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		documents.forEach(
			document -> {
				IndexDocumentRequest indexDocumentRequest =
					new IndexDocumentRequest(
						getRankingIndexName(indexName), document);

				bulkDocumentRequest.addBulkableDocumentRequest(
					indexDocumentRequest);
			});

		BulkDocumentResponse bulkDocumentResponse =
			_searchEngineAdapter.execute(bulkDocumentRequest);

		if (bulkDocumentResponse.hasErrors()) {
			successed = false;
		}

		return successed;
	}

	protected void createRankingIndices() {
		List<Company> companies = _companyService.getCompanies();

		Stream<Company> stream = companies.stream();

		stream.map(
			Company::getCompanyId
		).map(
			_rankingIndexNameBuilder::getRankingIndexName
		).filter(
			rankingIndexName -> !_rankingIndexReader.isExists(rankingIndexName)
		).forEach(
			_rankingIndexCreator::create
		);
	}

	protected List<Document> getDocuments(RankingIndexName singleIndexName) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(singleIndexName.getIndexName());
		searchSearchRequest.setQuery(_queries.matchAll());
		searchSearchRequest.setFetchSource(true);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		Stream<SearchHit> documentStream = searchHitsList.stream();

		return documentStream.map(
			SearchHit::getDocument
		).collect(
			Collectors.toList()
		);
	}

	protected String getRankingIndexName(String indexName) {
		return indexName + StringPool.DASH + RANKINGS_INDEX_NAME_SUFFIX;
	}

	protected void importDocuments() {
		if (!_rankingIndexReader.isExists(SINGLE_INDEX_NAME)) {
			return;
		}

		List<Document> documents = getDocuments(SINGLE_INDEX_NAME);

		if (documents.isEmpty()) {
			return;
		}

		Map<String, List<Document>> documentsMap = groupDocumentByIndex(
			documents);

		Set<Map.Entry<String, List<Document>>> entrySet =
			documentsMap.entrySet();

		Stream<Map.Entry<String, List<Document>>> stream = entrySet.stream();

		if (stream.map(
				entry -> addDocuments(entry.getKey(), entry.getValue())
			).reduce(
				true, Boolean::logicalAnd
			)) {

			_rankingIndexCreator.delete(SINGLE_INDEX_NAME);
		}
	}

	protected static final String RANKINGS_INDEX_NAME_SUFFIX =
		"search-tuning-rankings";

	protected static final RankingIndexName SINGLE_INDEX_NAME =
		() -> "liferay-search-tuning-rankings";

	private static final Log _log = LogFactoryUtil.getLog(
		SingleIndexToMultipleIndexImporterImpl.class);

	@Reference
	private CompanyService _companyService;

	@Reference
	private Queries _queries;

	@Reference
	private RankingIndexCreator _rankingIndexCreator;

	@Reference
	private RankingIndexNameBuilder _rankingIndexNameBuilder;

	@Reference
	private RankingIndexReader _rankingIndexReader;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}
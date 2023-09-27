/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.search.suggest.SpellCheckIndexWriter;
import com.liferay.portal.kernel.search.suggest.SuggestionConstants;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.search.elasticsearch7.internal.util.DocumentTypes;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.suggest.BaseGenericSpellCheckIndexWriter;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = SpellCheckIndexWriter.class
)
public class ElasticsearchSpellCheckIndexWriter
	extends BaseGenericSpellCheckIndexWriter {

	@Override
	public void clearQuerySuggestionDictionaryIndexes(
			SearchContext searchContext)
		throws SearchException {

		try {
			deleteDocuments(
				searchContext, SuggestionConstants.TYPE_QUERY_SUGGESTION);
		}
		catch (Exception exception) {
			throw new SearchException(
				"Unable to clear query suggestions", exception);
		}
	}

	@Override
	public void clearSpellCheckerDictionaryIndexes(SearchContext searchContext)
		throws SearchException {

		try {
			deleteDocuments(
				searchContext, SuggestionConstants.TYPE_SPELL_CHECKER);
		}
		catch (Exception exception) {
			throw new SearchException(
				"Unable to to clear spell checks", exception);
		}
	}

	@Override
	protected void addDocument(
		String documentType, SearchContext searchContext, Document document) {

		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			indexName, document);

		indexDocumentRequest.setType(DocumentTypes.LIFERAY);

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	@Override
	protected void addDocuments(
		String documentType, SearchContext searchContext,
		Collection<Document> documents) {

		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		documents.forEach(
			document -> {
				IndexDocumentRequest indexDocumentRequest =
					new IndexDocumentRequest(indexName, document);

				indexDocumentRequest.setType(DocumentTypes.LIFERAY);

				bulkDocumentRequest.addBulkableDocumentRequest(
					indexDocumentRequest);
			});

		_searchEngineAdapter.execute(bulkDocumentRequest);
	}

	@Override
	protected Document createDocument(
		long companyId, long groupId, String languageId, String keywords,
		float weight, String keywordFieldName, String typeFieldValue,
		int maxNGramLength) {

		Document document = createDocument();

		document.addKeyword(
			_localization.getLocalizedName(keywordFieldName, languageId),
			keywords);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.LANGUAGE_ID, languageId);
		document.addKeyword(Field.PRIORITY, String.valueOf(weight));
		document.addKeyword(Field.TYPE, typeFieldValue);
		document.addKeyword(
			Field.UID,
			getUID(companyId, keywordFieldName, languageId, keywords));

		return document;
	}

	protected void deleteDocuments(
		SearchContext searchContext, String typeFieldValue) {

		try {
			String indexName = _indexNameBuilder.getIndexName(
				searchContext.getCompanyId());

			Filter termFilter = new TermFilter(Field.TYPE, typeFieldValue);

			BooleanFilter booleanFilter = new BooleanFilter();

			booleanFilter.add(termFilter, BooleanClauseOccur.MUST);

			MatchAllQuery matchAllQuery = new MatchAllQuery();

			BooleanQuery booleanQuery = new BooleanQueryImpl();

			booleanQuery.setPreBooleanFilter(booleanFilter);

			booleanQuery.add(matchAllQuery, BooleanClauseOccur.MUST);

			DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
				new DeleteByQueryDocumentRequest(matchAllQuery, indexName);

			if (PortalRunMode.isTestMode() ||
				searchContext.isCommitImmediately()) {

				deleteByQueryDocumentRequest.setRefresh(true);
			}

			_searchEngineAdapter.execute(deleteByQueryDocumentRequest);
		}
		catch (ParseException parseException) {
			throw new SystemException(parseException);
		}
	}

	protected void setLocalization(Localization localization) {
		_localization = localization;
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Localization _localization;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private SearchEngineAdapter _searchEngineAdapter;

}
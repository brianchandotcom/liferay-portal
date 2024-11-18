/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.search.suggest.SpellCheckIndexWriter;
import com.liferay.portal.kernel.search.suggest.SuggestionConstants;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.PortalRunMode;
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
	property = "search.engine.impl=OpenSearch",
	service = SpellCheckIndexWriter.class
)
public class OpenSearchSpellCheckIndexWriter
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

		_searchEngineAdapter.execute(
			new IndexDocumentRequest(
				_indexNameBuilder.getIndexName(searchContext.getCompanyId()),
				document));
	}

	@Override
	protected void addDocuments(
		String documentType, SearchContext searchContext,
		Collection<Document> documents) {

		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		documents.forEach(
			document -> bulkDocumentRequest.addBulkableDocumentRequest(
				new IndexDocumentRequest(indexName, document)));

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
			BooleanQuery booleanQuery = new BooleanQueryImpl();

			booleanQuery.add(new MatchAllQuery(), BooleanClauseOccur.MUST);

			BooleanFilter booleanFilter = new BooleanFilter();

			booleanFilter.add(
				new TermFilter(Field.TYPE, typeFieldValue),
				BooleanClauseOccur.MUST);

			booleanQuery.setPreBooleanFilter(booleanFilter);

			DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
				new DeleteByQueryDocumentRequest(
					booleanQuery,
					_indexNameBuilder.getIndexName(
						searchContext.getCompanyId()));

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

	@Reference(target = "(search.engine.impl=OpenSearch)")
	private SearchEngineAdapter _searchEngineAdapter;

}
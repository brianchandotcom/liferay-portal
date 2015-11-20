/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch.internal;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.suggest.BaseGenericSpellCheckIndexWriter;
import com.liferay.portal.kernel.search.suggest.SpellCheckIndexWriter;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch.document.ElasticsearchUpdateDocumentCommand;
import com.liferay.portal.search.elasticsearch.internal.util.DocumentTypes;

import java.util.Collection;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = {"search.engine.impl=Elasticsearch"},
	service = SpellCheckIndexWriter.class
)
public class ElasticsearchSpellCheckIndexWriter
	extends BaseGenericSpellCheckIndexWriter {

	@Override
	public void clearQuerySuggestionDictionaryIndexes(
			SearchContext searchContext)
		throws SearchException {

		try {
			deleteIndices(searchContext, DocumentTypes.KEYWORD_QUERY);
		}
		catch (Exception e) {
			throw new SearchException("Unable to clear query suggestions", e);
		}
	}

	@Override
	public void clearSpellCheckerDictionaryIndexes(SearchContext searchContext)
		throws SearchException {

		try {
			deleteIndices(searchContext, DocumentTypes.SPELL_CHECK);
		}
		catch (Exception e) {
			throw new SearchException("Unable to to clear spell checks", e);
		}
	}

	@Override
	protected void addDocument(
			String documentType, SearchContext searchContext, Document document)
		throws SearchException {

		_elasticsearchUpdateDocumentCommand.updateDocument(
			documentType, searchContext, document, false);
	}

	@Override
	protected void addDocuments(
			String documentType, SearchContext searchContext,
			Collection<Document> documents)
		throws SearchException {

		_elasticsearchUpdateDocumentCommand.updateDocuments(
			documentType, searchContext, documents, false);
	}

	@Override
	protected Document createDocument(
		long companyId, long groupId, String languageId, String keywords,
		float weight, String keywordFieldName, String typeFieldValue,
		int maxNGramLength) {

		Document document = createDocument();

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.GROUP_ID, groupId);

		String localizedName = DocumentImpl.getLocalizedName(
			languageId, keywordFieldName);

		document.addKeyword(localizedName, keywords);

		document.addKeyword(Field.PRIORITY, String.valueOf(weight));
		document.addKeyword(Field.SPELL_CHECK_WORD, true);
		document.addKeyword(Field.UID, getUID(companyId, languageId, keywords));

		return document;
	}

	protected void deleteIndices(SearchContext searchContext, String indexType)
		throws Exception {

		if (_searchHitsProcessor == null) {
			throw new IllegalStateException("Module not properly initialized");
		}

		SearchResponseScroller searchResponseScroller = null;

		try {
			Client client = _elasticsearchConnectionManager.getClient();

			MatchAllQueryBuilder matchAllQueryBuilder =
				QueryBuilders.matchAllQuery();

			searchResponseScroller = new SearchResponseScroller(
				client, searchContext, matchAllQueryBuilder,
				TimeValue.timeValueSeconds(30), indexType);

			searchResponseScroller.prepare();

			searchResponseScroller.scroll(_searchHitsProcessor);
		}
		finally {
			if (searchResponseScroller != null) {
				searchResponseScroller.close();
			}
		}
	}

	@Reference(unbind = "-")
	protected void setElasticsearchConnectionManager(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchUpdateDocumentCommand(
		ElasticsearchUpdateDocumentCommand elasticsearchUpdateDocumentCommand) {

		_elasticsearchUpdateDocumentCommand =
			elasticsearchUpdateDocumentCommand;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(!(search.engine.impl=*))"
	)
	protected void setIndexWriter(IndexWriter indexWriter) {
		_searchHitsProcessor = new DeleteDocumentsSearchHitsProcessor(
			indexWriter);
	}

	protected void unsetIndexWriter(IndexWriter indexWriter) {
		_searchHitsProcessor = null;
	}

	private volatile ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private volatile ElasticsearchUpdateDocumentCommand
		_elasticsearchUpdateDocumentCommand;
	private volatile SearchHitsProcessor _searchHitsProcessor;

}
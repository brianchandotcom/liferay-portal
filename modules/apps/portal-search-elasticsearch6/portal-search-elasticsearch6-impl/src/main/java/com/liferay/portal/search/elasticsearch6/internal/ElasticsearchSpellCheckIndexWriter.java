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

package com.liferay.portal.search.elasticsearch6.internal;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.suggest.SpellCheckIndexWriter;
import com.liferay.portal.kernel.search.suggest.SuggestionConstants;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch6.internal.document.ElasticsearchUpdateDocumentCommand;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexNameBuilder;
import com.liferay.portal.search.elasticsearch6.internal.util.DocumentTypes;
import com.liferay.portal.search.suggest.BaseGenericSpellCheckIndexWriter;

import java.util.Collection;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
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
			deleteDocuments(
				searchContext, SuggestionConstants.TYPE_QUERY_SUGGESTION);
		}
		catch (Exception e) {
			throw new SearchException("Unable to clear query suggestions", e);
		}
	}

	@Override
	public void clearSpellCheckerDictionaryIndexes(SearchContext searchContext)
		throws SearchException {

		try {
			deleteDocuments(
				searchContext, SuggestionConstants.TYPE_SPELL_CHECKER);
		}
		catch (Exception e) {
			throw new SearchException("Unable to to clear spell checks", e);
		}
	}

	@Override
	protected void addDocument(
			String documentType, SearchContext searchContext, Document document)
		throws SearchException {

		elasticsearchUpdateDocumentCommand.updateDocument(
			DocumentTypes.LIFERAY, searchContext, document, false);
	}

	@Override
	protected void addDocuments(
			String documentType, SearchContext searchContext,
			Collection<Document> documents)
		throws SearchException {

		elasticsearchUpdateDocumentCommand.updateDocuments(
			DocumentTypes.LIFERAY, searchContext, documents, false);
	}

	@Override
	protected Document createDocument(
		long companyId, long groupId, String languageId, String keywords,
		float weight, String keywordFieldName, String typeFieldValue,
		int maxNGramLength) {

		Document document = createDocument();

		Localization localization = getLocalization();

		String localizedName = localization.getLocalizedName(
			keywordFieldName, languageId);

		document.addKeyword(localizedName, keywords);

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
			SearchContext searchContext, String typeFieldValue)
		throws Exception {

		if (_searchHitsProcessor == null) {
			throw new IllegalStateException("Module not properly initialized");
		}

		SearchResponseScroller searchResponseScroller = null;

		try {
			Client client = elasticsearchConnectionManager.getClient();

			MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(
				Field.TYPE, typeFieldValue);

			searchResponseScroller = new SearchResponseScroller(
				client, searchContext, indexNameBuilder, matchQueryBuilder,
				TimeValue.timeValueSeconds(30), DocumentTypes.LIFERAY);

			searchResponseScroller.prepare();

			searchResponseScroller.scroll(_searchHitsProcessor);
		}
		finally {
			if (searchResponseScroller != null) {
				searchResponseScroller.close();
			}
		}
	}

	protected Localization getLocalization() {

		// See LPS-72507 and LPS-76500

		if (localization != null) {
			return localization;
		}

		return LocalizationUtil.getLocalization();
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

	@Reference(unbind = "-")
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	@Reference(unbind = "-")
	protected ElasticsearchUpdateDocumentCommand
		elasticsearchUpdateDocumentCommand;

	@Reference(unbind = "-")
	protected IndexNameBuilder indexNameBuilder;

	protected Localization localization;

	private volatile SearchHitsProcessor _searchHitsProcessor;

}
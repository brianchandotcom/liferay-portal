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

package com.liferay.portal.search.solr7.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.kernel.search.suggest.SpellCheckIndexWriter;
import com.liferay.portal.kernel.search.suggest.SuggestionConstants;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.solr7.configuration.SolrConfiguration;
import com.liferay.portal.search.suggest.BaseGenericSpellCheckIndexWriter;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniela Zapata Riesco
 * @author David Gonzalez
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.solr7.configuration.SolrConfiguration",
	immediate = true, property = "search.engine.impl=Solr",
	service = SpellCheckIndexWriter.class
)
public class SolrSpellCheckIndexWriter
	extends BaseGenericSpellCheckIndexWriter {

	@Override
	public void clearQuerySuggestionDictionaryIndexes(
			SearchContext searchContext)
		throws SearchException {

		String deleteQuery = buildDeleteQuery(
			searchContext, SuggestionConstants.TYPE_QUERY_SUGGESTION);

		deleteByQuery(searchContext, deleteQuery);
	}

	@Override
	public void clearSpellCheckerDictionaryIndexes(SearchContext searchContext)
		throws SearchException {

		String deleteQuery = buildDeleteQuery(
			searchContext, SuggestionConstants.TYPE_SPELL_CHECKER);

		deleteByQuery(searchContext, deleteQuery);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		setDocumentPrototype(new DocumentImpl());

		_solrConfiguration = ConfigurableUtil.createConfigurable(
			SolrConfiguration.class, properties);

		_defaultCollection = _solrConfiguration.defaultCollection();
		_logExceptionsOnly = _solrConfiguration.logExceptionsOnly();
	}

	@Override
	protected void addDocument(
			String documentType, SearchContext searchContext, Document document)
		throws SearchException {

		try {
			IndexDocumentRequest indexDocumentRequest =
				new IndexDocumentRequest(_defaultCollection, document);

			if (PortalRunMode.isTestMode() ||
				searchContext.isCommitImmediately()) {

				indexDocumentRequest.setRefresh(true);
			}

			_searchEngineAdapter.execute(indexDocumentRequest);
		}
		catch (RuntimeException re) {
			if (_logExceptionsOnly) {
				_log.error(re, re);
			}
			else {
				throw re;
			}
		}
	}

	@Override
	protected void addDocuments(
			String documentType, SearchContext searchContext,
			Collection<Document> documents)
		throws SearchException {

		try {
			BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

			if (PortalRunMode.isTestMode() ||
				searchContext.isCommitImmediately()) {

				bulkDocumentRequest.setRefresh(true);
			}

			documents.forEach(
				document -> {
					IndexDocumentRequest indexDocumentRequest =
						new IndexDocumentRequest(_defaultCollection, document);

					bulkDocumentRequest.addBulkableDocumentRequest(
						indexDocumentRequest);
				});

			BulkDocumentResponse bulkDocumentResponse =
				_searchEngineAdapter.execute(bulkDocumentRequest);

			if (bulkDocumentResponse.hasErrors()) {
				if (_logExceptionsOnly) {
					_log.error("Bulk add failed");
				}
				else {
					throw new SystemException("Bulk add failed");
				}
			}
		}
		catch (RuntimeException re) {
			if (_logExceptionsOnly) {
				_log.error(re, re);
			}
			else {
				throw re;
			}
		}
	}

	protected void addQuerySeparator(StringBundler sb) {
		sb.append(StringPool.SPACE);
		sb.append(StringPool.PLUS);
	}

	protected void addQueryType(StringBundler sb, String type) {
		sb.append(Field.TYPE);
		sb.append(StringPool.COLON);
		sb.append(type);
	}

	protected String buildDeleteQuery(
		SearchContext searchContext, String type) {

		StringBundler sb = new StringBundler(14);

		sb.append(StringPool.PLUS);
		sb.append(Field.COMPANY_ID);
		sb.append(StringPool.COLON);
		sb.append(searchContext.getCompanyId());

		addQuerySeparator(sb);

		addQueryType(sb, type);

		return sb.toString();
	}

	protected void deleteByQuery(
			SearchContext searchContext, String deleteQuery)
		throws SearchException {

		try {
			DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
				new DeleteByQueryDocumentRequest(
					new StringQuery(deleteQuery), _defaultCollection);

			if (PortalRunMode.isTestMode() ||
				searchContext.isCommitImmediately()) {

				deleteByQueryDocumentRequest.setRefresh(true);
			}

			_searchEngineAdapter.execute(deleteByQueryDocumentRequest);
		}
		catch (RuntimeException re) {
			if (_logExceptionsOnly) {
				_log.error(re, re);
			}
			else {
				throw re;
			}
		}
	}

	@Reference(target = "(search.engine.impl=Solr)", unbind = "-")
	protected void setSearchEngineAdapter(
		SearchEngineAdapter searchEngineAdapter) {

		_searchEngineAdapter = searchEngineAdapter;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SolrSpellCheckIndexWriter.class);

	private String _defaultCollection;
	private boolean _logExceptionsOnly;
	private SearchEngineAdapter _searchEngineAdapter;
	private volatile SolrConfiguration _solrConfiguration;

}
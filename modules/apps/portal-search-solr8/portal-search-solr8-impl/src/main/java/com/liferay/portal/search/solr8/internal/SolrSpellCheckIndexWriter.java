/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal;

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
import com.liferay.portal.search.solr8.configuration.SolrConfiguration;
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
	configurationPid = "com.liferay.portal.search.solr8.configuration.SolrConfiguration",
	property = "search.engine.impl=Solr", service = SpellCheckIndexWriter.class
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
		catch (RuntimeException runtimeException) {
			if (_logExceptionsOnly) {
				_log.error(runtimeException);
			}
			else {
				throw runtimeException;
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
		catch (RuntimeException runtimeException) {
			if (_logExceptionsOnly) {
				_log.error(runtimeException);
			}
			else {
				throw runtimeException;
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
		catch (RuntimeException runtimeException) {
			if (_logExceptionsOnly) {
				_log.error(runtimeException);
			}
			else {
				throw runtimeException;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SolrSpellCheckIndexWriter.class);

	private volatile String _defaultCollection;
	private volatile boolean _logExceptionsOnly;

	@Reference(target = "(search.engine.impl=Solr)")
	private SearchEngineAdapter _searchEngineAdapter;

	private volatile SolrConfiguration _solrConfiguration;

}
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

package com.liferay.portal.search.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineConfigurator;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.search.queue.QueuingSearchEngine;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.PermissionThreadLocal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SearchEngineHelper.class)
public class SearchEngineHelperImpl implements SearchEngineHelper {

	@Override
	public void addDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException {

		if (isIndexReadOnly() || (document == null)) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Add document " + document.toString());
		}

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		_searchPermissionChecker.addPermissionFields(companyId, document);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		setCommitImmediately(searchContext, commitImmediately);

		indexWriter.addDocument(searchContext, document);
	}

	@Override
	public void addDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException {

		if (isIndexReadOnly() || (documents == null) || documents.isEmpty()) {
			return;
		}

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		for (Document document : documents) {
			if (_log.isDebugEnabled()) {
				_log.debug("Add document " + document.toString());
			}

			_searchPermissionChecker.addPermissionFields(companyId, document);
		}

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		setCommitImmediately(searchContext, commitImmediately);

		indexWriter.addDocuments(searchContext, documents);
	}

	@Override
	public void commit(String searchEngineId) throws SearchException {
		for (long companyId : _companyIds.keySet()) {
			commit(searchEngineId, companyId);
		}
	}

	@Override
	public void commit(String searchEngineId, long companyId)
		throws SearchException {

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.commit(searchContext);
	}

	@Override
	public void deleteDocument(
			String searchEngineId, long companyId, String uid,
			boolean commitImmediately)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		setCommitImmediately(searchContext, commitImmediately);

		indexWriter.deleteDocument(searchContext, uid);
	}

	@Override
	public void deleteDocuments(
			String searchEngineId, long companyId, Collection<String> uids,
			boolean commitImmediately)
		throws SearchException {

		if (isIndexReadOnly() || (uids == null) || uids.isEmpty()) {
			return;
		}

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		setCommitImmediately(searchContext, commitImmediately);

		indexWriter.deleteDocuments(searchContext, uids);
	}

	@Override
	public void deleteEntityDocuments(
			String searchEngineId, long companyId, String className,
			boolean commitImmediately)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		if (searchEngine == null) {
			return;
		}

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		setCommitImmediately(searchContext, commitImmediately);

		indexWriter.deleteEntityDocuments(searchContext, className);
	}

	@Override
	public void flushQueuedSearchEngine() {
		synchronized (_queuingSearchEngines) {
			for (QueuingSearchEngine queuingSearchEngine :
					_queuingSearchEngines.values()) {

				queuingSearchEngine.flush();
			}

			_queuingSearchEngines.clear();
		}
	}

	@Override
	public void flushQueuedSearchEngine(String searchEngineId) {
		QueuingSearchEngine queuingSearchEngine = null;

		synchronized (_queuingSearchEngines) {
			queuingSearchEngine = _queuingSearchEngines.remove(searchEngineId);
		}

		if (queuingSearchEngine != null) {
			queuingSearchEngine.flush();
		}
	}

	@Override
	public Collection<Long> getCompanyIds() {
		return _companyIds.keySet();
	}

	@Override
	public String getDefaultSearchEngineId() {
		if (_defaultSearchEngineId == null) {
			return SYSTEM_ENGINE_ID;
		}

		return _defaultSearchEngineId;
	}

	@Override
	public String[] getEntryClassNames() {
		Set<String> assetEntryClassNames = new HashSet<>();

		for (Indexer<?> indexer : IndexerRegistryUtil.getIndexers()) {
			for (String className : indexer.getSearchClassNames()) {
				if (!_excludedEntryClassNames.contains(className)) {
					assetEntryClassNames.add(className);
				}
			}
		}

		return assetEntryClassNames.toArray(
			new String[assetEntryClassNames.size()]);
	}

	@Override
	public String getQueryString(SearchContext searchContext, Query query) {
		SearchEngine searchEngine = getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		try {
			return indexSearcher.getQueryString(searchContext, query);
		}
		catch (ParseException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse query " + query, pe);
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public SearchEngine getSearchEngine(String searchEngineId) {
		PortalRuntimePermission.checkSearchEngine(searchEngineId);

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		if (searchEngine != null) {
			return searchEngine;
		}

		synchronized (_queuingSearchEngines) {
			searchEngine = _queuingSearchEngines.get(searchEngineId);

			if (searchEngine == null) {
				QueuingSearchEngine queuingSearchEngine =
					new QueuingSearchEngine(_queueCapacity);

				_queuingSearchEngines.put(searchEngineId, queuingSearchEngine);

				searchEngine = queuingSearchEngine;
			}

			return searchEngine;
		}
	}

	@Override
	public String getSearchEngineId(Collection<Document> documents) {
		if (!documents.isEmpty()) {
			Iterator<Document> iterator = documents.iterator();

			Document document = iterator.next();

			return getSearchEngineId(document);
		}

		return getDefaultSearchEngineId();
	}

	@Override
	public String getSearchEngineId(Document document) {
		String entryClassName = document.get("entryClassName");

		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(entryClassName);

		String searchEngineId = indexer.getSearchEngineId();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Search engine ID " + searchEngineId + " is associated with " +
					ClassUtil.getClassName(indexer));
		}

		return searchEngineId;
	}

	@Override
	public Set<String> getSearchEngineIds() {
		return _searchEngines.keySet();
	}

	@Override
	public Collection<SearchEngine> getSearchEngines() {
		return Collections.unmodifiableCollection(_searchEngines.values());
	}

	@Override
	public SearchEngine getSearchEngineSilent(String searchEngineId) {
		return _searchEngines.get(searchEngineId);
	}

	@Override
	public SearchPermissionChecker getSearchPermissionChecker() {
		return _searchPermissionChecker;
	}

	@Override
	public String getSearchReaderDestinationName(String searchEngineId) {
		return DestinationNames.SEARCH_READER.concat(StringPool.SLASH).concat(
			searchEngineId);
	}

	@Override
	public String getSearchWriterDestinationName(String searchEngineId) {
		return DestinationNames.SEARCH_WRITER.concat(StringPool.SLASH).concat(
			searchEngineId);
	}

	@Override
	public void indexKeyword(
			long companyId, String querySuggestion, float weight,
			String keywordType, Locale locale)
		throws SearchException {

		String searchEngineId = getDefaultSearchEngineId();

		indexKeyword(
			searchEngineId, companyId, querySuggestion, weight, keywordType,
			locale);
	}

	@Override
	public void indexKeyword(
			String searchEngineId, long companyId, String querySuggestion,
			float weight, String keywordType, Locale locale)
		throws SearchException {

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);
		searchContext.setKeywords(querySuggestion);
		searchContext.setLocale(locale);

		indexWriter.indexKeyword(searchContext, weight, keywordType);
	}

	@Override
	public void indexQuerySuggestionDictionaries(long companyId)
		throws SearchException {

		Set<String> searchEngineIds = getSearchEngineIds();

		for (String searchEngineId : searchEngineIds) {
			indexQuerySuggestionDictionaries(searchEngineId, companyId);
		}
	}

	@Override
	public void indexQuerySuggestionDictionaries(
			String searchEngineId, long companyId)
		throws SearchException {

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.indexQuerySuggestionDictionaries(searchContext);
	}

	@Override
	public void indexQuerySuggestionDictionary(long companyId, Locale locale)
		throws SearchException {

		String searchEngineId = getDefaultSearchEngineId();

		indexQuerySuggestionDictionary(searchEngineId, companyId, locale);
	}

	@Override
	public void indexQuerySuggestionDictionary(
			String searchEngineId, long companyId, Locale locale)
		throws SearchException {

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);
		searchContext.setLocale(locale);

		indexWriter.indexQuerySuggestionDictionary(searchContext);
	}

	@Override
	public void indexSpellCheckerDictionaries(long companyId)
		throws SearchException {

		String searchEngineId = getDefaultSearchEngineId();

		indexSpellCheckerDictionaries(searchEngineId, companyId);
	}

	@Override
	public void indexSpellCheckerDictionaries(
			String searchEngineId, long companyId)
		throws SearchException {

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.indexSpellCheckerDictionaries(searchContext);
	}

	@Override
	public void indexSpellCheckerDictionary(long companyId, Locale locale)
		throws SearchException {

		String searchEngineId = getDefaultSearchEngineId();

		indexSpellCheckerDictionary(searchEngineId, companyId, locale);
	}

	@Override
	public void indexSpellCheckerDictionary(
			String searchEngineId, long companyId, Locale locale)
		throws SearchException {

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);
		searchContext.setLocale(locale);

		indexWriter.indexSpellCheckerDictionary(searchContext);
	}

	@Override
	public synchronized void initialize(long companyId) {
		if (_companyIds.containsKey(companyId)) {
			return;
		}

		_companyIds.put(companyId, companyId);

		for (SearchEngine searchEngine : _searchEngines.values()) {
			searchEngine.initialize(companyId);
		}
	}

	@Override
	public boolean isIndexReadOnly() {
		return _indexReadOnly;
	}

	@Override
	public void partiallyUpdateDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException {

		if (isIndexReadOnly() || (document == null)) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + document.toString());
		}

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		_searchPermissionChecker.addPermissionFields(companyId, document);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		setCommitImmediately(searchContext, commitImmediately);

		indexWriter.partiallyUpdateDocument(searchContext, document);
	}

	@Override
	public void partiallyUpdateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException {

		if (isIndexReadOnly() || (documents == null) || documents.isEmpty()) {
			return;
		}

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		for (Document document : documents) {
			if (_log.isDebugEnabled()) {
				_log.debug("Document " + document.toString());
			}

			_searchPermissionChecker.addPermissionFields(companyId, document);
		}

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		setCommitImmediately(searchContext, commitImmediately);

		indexWriter.partiallyUpdateDocuments(searchContext, documents);
	}

	@Override
	public synchronized void removeCompany(long companyId) {
		if (!_companyIds.containsKey(companyId)) {
			return;
		}

		for (SearchEngine searchEngine : _searchEngines.values()) {
			searchEngine.removeCompany(companyId);
		}

		_companyIds.remove(companyId);
	}

	@Override
	public SearchEngine removeSearchEngine(String searchEngineId) {
		PortalRuntimePermission.checkSearchEngine(searchEngineId);

		return _searchEngines.remove(searchEngineId);
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + getQueryString(searchContext, query));
		}

		SearchEngine searchEngine = getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.search(searchContext, query);
	}

	@Override
	public long searchCount(SearchContext searchContext, Query query)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + getQueryString(searchContext, query));
		}

		SearchEngine searchEngine = getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.searchCount(searchContext, query);
	}

	@Override
	public void setDefaultSearchEngineId(String defaultSearchEngineId) {
		_defaultSearchEngineId = defaultSearchEngineId;
	}

	@Override
	public void setExcludedEntryClassNames(
		List<String> excludedEntryClassNames) {

		PortalRuntimePermission.checkSetBeanProperty(
			getClass(), "excludedEntryClassNames");

		_excludedEntryClassNames.addAll(excludedEntryClassNames);
	}

	@Override
	public void setIndexReadOnly(boolean indexReadOnly) {
		_indexReadOnly = indexReadOnly;
	}

	@Override
	public void setQueueCapacity(int queueCapacity) {
		_queueCapacity = queueCapacity;
	}

	@Override
	public void setSearchEngine(
		String searchEngineId, SearchEngine searchEngine) {

		PortalRuntimePermission.checkSearchEngine(searchEngineId);

		_searchEngines.put(searchEngineId, searchEngine);

		for (Long companyId : _companyIds.keySet()) {
			searchEngine.initialize(companyId);
		}

		synchronized (_queuingSearchEngines) {
			QueuingSearchEngine queuingSearchEngine = _queuingSearchEngines.get(
				searchEngineId);

			if (queuingSearchEngine != null) {
				try {
					queuingSearchEngine.invokeQueued(
						searchEngine.getIndexWriter());
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to execute pending write events for " +
								"engine: " + searchEngineId,
							e);
					}
				}
			}
		}
	}

	@Override
	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Spell checking " + searchContext.getKeywords());
		}

		SearchEngine searchEngine = getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.spellCheckKeywords(searchContext);
	}

	@Override
	public Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Spell checking " + searchContext.getKeywords());
		}

		SearchEngine searchEngine = getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.spellCheckKeywords(searchContext, max);
	}

	@Override
	public String[] suggestKeywordQueries(SearchContext searchContext, int max)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Suggesting keyword queries" + searchContext.getKeywords());
		}

		SearchEngine searchEngine = getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.suggestKeywordQueries(searchContext, max);
	}

	@Override
	public void updateDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException {

		if (isIndexReadOnly() || (document == null)) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + document.toString());
		}

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		_searchPermissionChecker.addPermissionFields(companyId, document);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		setCommitImmediately(
			searchContext,
			commitImmediately || ProxyModeThreadLocal.isForceSync());

		indexWriter.updateDocument(searchContext, document);
	}

	@Override
	public void updateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException {

		if (isIndexReadOnly() || (documents == null) || documents.isEmpty()) {
			return;
		}

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		for (Document document : documents) {
			if (_log.isDebugEnabled()) {
				_log.debug("Document " + document.toString());
			}

			_searchPermissionChecker.addPermissionFields(companyId, document);
		}

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		setCommitImmediately(searchContext, commitImmediately);

		indexWriter.updateDocuments(searchContext, documents);
	}

	@Override
	public void updatePermissionFields(String name, String primKey) {
		if (isIndexReadOnly()) {
			return;
		}

		if (PermissionThreadLocal.isFlushResourcePermissionEnabled(
				name, primKey)) {

			_searchPermissionChecker.updatePermissionFields(name, primKey);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeSearchEngineConfigurator"
	)
	protected void addSearchEngineConfigurator(
		SearchEngineConfigurator searchEngineConfigurator) {

		searchEngineConfigurator.afterPropertiesSet();
	}

	protected void removeSearchEngineConfigurator(
		SearchEngineConfigurator searchEngineConfigurator) {

		searchEngineConfigurator.destroy();
	}

	protected void setCommitImmediately(
		SearchContext searchContext, boolean commitImmediately) {

		if (!commitImmediately) {
			searchContext.setCommitImmediately(_INDEX_COMMIT_IMMEDIATELY);
		}
		else {
			searchContext.setCommitImmediately(true);
		}
	}

	@Reference(unbind = "-")
	protected void setSearchPermissionChecker(
		SearchPermissionChecker searchPermissionChecker) {

		_searchPermissionChecker = searchPermissionChecker;
	}

	private static final boolean _INDEX_COMMIT_IMMEDIATELY =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.INDEX_COMMIT_IMMEDIATELY));

	private static final Log _log = LogFactoryUtil.getLog(
		SearchEngineHelperImpl.class);

	private final Map<Long, Long> _companyIds = new ConcurrentHashMap<>();
	private String _defaultSearchEngineId;
	private final Set<String> _excludedEntryClassNames = new HashSet<>();
	private boolean _indexReadOnly = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INDEX_READ_ONLY));
	private int _queueCapacity = 200;
	private final Map<String, QueuingSearchEngine>
		_queuingSearchEngines = new HashMap<>();
	private final Map<String, SearchEngine> _searchEngines =
		new ConcurrentHashMap<>();
	private volatile SearchPermissionChecker _searchPermissionChecker;

}
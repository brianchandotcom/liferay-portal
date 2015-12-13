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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.search.queue.QueuingSearchEngine;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Michael C. Han
 */
public class SearchEngineUtil {

	public static final String GENERIC_ENGINE_ID = "GENERIC_ENGINE";

	public static final String SYSTEM_ENGINE_ID = "SYSTEM_ENGINE";

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addDocument(String, long,
	 *             Document, boolean)}
	 */
	@Deprecated
	public static void addDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		addDocument(searchEngineId, companyId, document, false);
	}

	public static void addDocument(
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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addDocuments(String, long,
	 *             Collection, boolean)}
	 */
	@Deprecated
	public static void addDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents)
		throws SearchException {

		addDocuments(searchEngineId, companyId, documents, false);
	}

	public static void addDocuments(
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

	public synchronized static void backup(long companyId, String backupName)
		throws SearchException {

		for (SearchEngine searchEngine : _searchEngines.values()) {
			searchEngine.backup(companyId, backupName);
		}
	}

	public synchronized static String backup(
			long companyId, String searchEngineId, String backupName)
		throws SearchException {

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		return searchEngine.backup(companyId, backupName);
	}

	public synchronized static void backup(String backupName)
		throws SearchException {

		for (SearchEngine searchEngine : _searchEngines.values()) {
			for (long companyId : _companyIds.keySet()) {
				searchEngine.backup(companyId, backupName);
			}
		}
	}

	public static void commit(String searchEngineId) throws SearchException {
		for (long companyId : _companyIds.keySet()) {
			commit(searchEngineId, companyId);
		}
	}

	public static void commit(String searchEngineId, long companyId)
		throws SearchException {

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.commit(searchContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #deleteDocument(String, long,
	 *             String, boolean)}
	 */
	@Deprecated
	public static void deleteDocument(
			String searchEngineId, long companyId, String uid)
		throws SearchException {

		deleteDocument(searchEngineId, companyId, uid, false);
	}

	public static void deleteDocument(
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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #deleteDocuments(String,
	 *             long, Collection, boolean)}
	 */
	@Deprecated
	public static void deleteDocuments(
			String searchEngineId, long companyId, Collection<String> uids)
		throws SearchException {

		deleteDocuments(searchEngineId, companyId, uids, false);
	}

	public static void deleteDocuments(
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

	public static void deleteEntityDocuments(
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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #deleteEntityDocuments(String, long, String, boolean)}
	 */
	@Deprecated
	public static void deletePortletDocuments(
			String searchEngineId, long companyId, String portletId)
		throws SearchException {

		deleteEntityDocuments(searchEngineId, companyId, portletId, false);
	}

	public static void flushQueuedSearchEngine() {
		synchronized (_queuingSearchEngines) {
			for (QueuingSearchEngine queuingSearchEngine :
					_queuingSearchEngines.values()) {

				queuingSearchEngine.flush();
			}

			_queuingSearchEngines.clear();
		}
	}

	public static void flushQueuedSearchEngine(String searchEngineId) {
		QueuingSearchEngine queuingSearchEngine = null;

		synchronized (_queuingSearchEngines) {
			queuingSearchEngine = _queuingSearchEngines.remove(searchEngineId);
		}

		if (queuingSearchEngine != null) {
			queuingSearchEngine.flush();
		}
	}

	public static String getDefaultSearchEngineId() {
		if (_defaultSearchEngineId == null) {
			return SYSTEM_ENGINE_ID;
		}

		return _defaultSearchEngineId;
	}

	public static String[] getEntryClassNames() {
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

	public static String getQueryString(
		SearchContext searchContext, Query query) {

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

	public static SearchEngine getSearchEngine(String searchEngineId) {
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

	public static String getSearchEngineId(Collection<Document> documents) {
		if (!documents.isEmpty()) {
			Iterator<Document> iterator = documents.iterator();

			Document document = iterator.next();

			return getSearchEngineId(document);
		}

		return getDefaultSearchEngineId();
	}

	public static String getSearchEngineId(Document document) {
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

	public static Set<String> getSearchEngineIds() {
		PortalRuntimePermission.checkGetBeanProperty(
			SearchEngineUtil.class, "searchEngineIds");

		return _searchEngines.keySet();
	}

	public static SearchEngine getSearchEngineSilent(String searchEngineId) {
		PortalRuntimePermission.checkSearchEngine(searchEngineId);

		return _searchEngines.get(searchEngineId);
	}

	public static SearchPermissionChecker getSearchPermissionChecker() {
		PortalRuntimePermission.checkGetBeanProperty(
			SearchEngineUtil.class, "searchPermissionChecker");

		return _searchPermissionChecker;
	}

	public static String getSearchReaderDestinationName(String searchEngineId) {
		return DestinationNames.SEARCH_READER.concat(StringPool.SLASH).concat(
			searchEngineId);
	}

	public static String getSearchWriterDestinationName(String searchEngineId) {
		return DestinationNames.SEARCH_WRITER.concat(StringPool.SLASH).concat(
			searchEngineId);
	}

	public static void indexKeyword(
			long companyId, String querySuggestion, float weight,
			String keywordType, Locale locale)
		throws SearchException {

		String searchEngineId = getDefaultSearchEngineId();

		indexKeyword(
			searchEngineId, companyId, querySuggestion, weight, keywordType,
			locale);
	}

	public static void indexKeyword(
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

	public static void indexQuerySuggestionDictionaries(long companyId)
		throws SearchException {

		Set<String> searchEngineIds = getSearchEngineIds();

		for (String searchEngineId : searchEngineIds) {
			indexQuerySuggestionDictionaries(searchEngineId, companyId);
		}
	}

	public static void indexQuerySuggestionDictionaries(
			String searchEngineId, long companyId)
		throws SearchException {

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.indexQuerySuggestionDictionaries(searchContext);
	}

	public static void indexQuerySuggestionDictionary(
			long companyId, Locale locale)
		throws SearchException {

		String searchEngineId = getDefaultSearchEngineId();

		indexQuerySuggestionDictionary(searchEngineId, companyId, locale);
	}

	public static void indexQuerySuggestionDictionary(
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

	public static void indexSpellCheckerDictionaries(long companyId)
		throws SearchException {

		String searchEngineId = getDefaultSearchEngineId();

		indexSpellCheckerDictionaries(searchEngineId, companyId);
	}

	public static void indexSpellCheckerDictionaries(
			String searchEngineId, long companyId)
		throws SearchException {

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.indexSpellCheckerDictionaries(searchContext);
	}

	public static void indexSpellCheckerDictionary(
			long companyId, Locale locale)
		throws SearchException {

		String searchEngineId = getDefaultSearchEngineId();

		indexSpellCheckerDictionary(searchEngineId, companyId, locale);
	}

	public static void indexSpellCheckerDictionary(
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

	public synchronized static void initialize(long companyId) {
		if (_companyIds.containsKey(companyId)) {
			return;
		}

		_companyIds.put(companyId, companyId);

		for (SearchEngine searchEngine : _searchEngines.values()) {
			searchEngine.initialize(companyId);
		}
	}

	public static boolean isIndexReadOnly() {
		PortalRuntimePermission.checkGetBeanProperty(
			SearchEngineUtil.class, "indexReadOnly");

		return _indexReadOnly;
	}

	public static void partiallyUpdateDocument(
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

	public static void partiallyUpdateDocuments(
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

	public synchronized static void removeBackup(
			long companyId, String backupName)
		throws SearchException {

		for (SearchEngine searchEngine : _searchEngines.values()) {
			searchEngine.removeBackup(companyId, backupName);
		}
	}

	public synchronized static void removeBackup(String backupName)
		throws SearchException {

		for (SearchEngine searchEngine : _searchEngines.values()) {
			for (long companyId : _companyIds.keySet()) {
				searchEngine.removeBackup(companyId, backupName);
			}
		}
	}

	public synchronized static void removeCompany(long companyId) {
		if (!_companyIds.containsKey(companyId)) {
			return;
		}

		for (SearchEngine searchEngine : _searchEngines.values()) {
			searchEngine.removeCompany(companyId);
		}

		_companyIds.remove(companyId);
	}

	public static SearchEngine removeSearchEngine(String searchEngineId) {
		PortalRuntimePermission.checkSearchEngine(searchEngineId);

		return _searchEngines.remove(searchEngineId);
	}

	public synchronized static void restore(long companyId, String backupName)
		throws SearchException {

		for (SearchEngine searchEngine : _searchEngines.values()) {
			searchEngine.restore(companyId, backupName);
		}
	}

	public synchronized static void restore(String backupName)
		throws SearchException {

		for (SearchEngine searchEngine : _searchEngines.values()) {
			for (long companyId : _companyIds.keySet()) {
				searchEngine.restore(companyId, backupName);
			}
		}
	}

	public static Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + getQueryString(searchContext, query));
		}

		SearchEngine searchEngine = getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.search(searchContext, query);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #search(SearchContext,
	 *             Query)}
	 */
	@Deprecated
	public static Hits search(
			String searchEngineId, long companyId, Query query, int start,
			int end)
		throws SearchException {

		return search(
			searchEngineId, companyId, query, SortFactoryUtil.getDefaultSorts(),
			start, end);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #search(SearchContext,
	 *             Query)}
	 */
	@Deprecated
	public static Hits search(
			String searchEngineId, long companyId, Query query, Sort sort,
			int start, int end)
		throws SearchException {

		return search(
			searchEngineId, companyId, query, new Sort[] {sort}, start, end);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #search(SearchContext,
	 *             Query)}
	 */
	@Deprecated
	public static Hits search(
			String searchEngineId, long companyId, Query query, Sort[] sorts,
			int start, int end)
		throws SearchException {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setSearchEngineId(searchEngineId);
		searchContext.setSorts(sorts);
		searchContext.setStart(start);

		return search(searchContext, query);
	}

	public static long searchCount(SearchContext searchContext, Query query)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + getQueryString(searchContext, query));
		}

		SearchEngine searchEngine = getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.searchCount(searchContext, query);
	}

	public static void setDefaultSearchEngineId(String defaultSearchEngineId) {
		PortalRuntimePermission.checkSetBeanProperty(
			SearchEngineUtil.class, "defaultSearchEngineId");

		_defaultSearchEngineId = defaultSearchEngineId;
	}

	public static void setIndexReadOnly(boolean indexReadOnly) {
		PortalRuntimePermission.checkSetBeanProperty(
			SearchEngineUtil.class, "indexReadOnly");

		_indexReadOnly = indexReadOnly;
	}

	public static void setSearchEngine(
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

	public static String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Spell checking " + searchContext.getKeywords());
		}

		SearchEngine searchEngine = getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.spellCheckKeywords(searchContext);
	}

	public static Map<String, List<String>> spellCheckKeywords(
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

	public static String[] suggestKeywordQueries(
			SearchContext searchContext, int max)
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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateDocument(String, long,
	 *             Document, boolean)}
	 */
	@Deprecated
	public static void updateDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		updateDocument(searchEngineId, companyId, document, false);
	}

	public static void updateDocument(
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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateDocuments(String,
	 *             long, Collection, boolean)}
	 */
	@Deprecated
	public static void updateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents)
		throws SearchException {

		updateDocuments(searchEngineId, companyId, documents, false);
	}

	public static void updateDocuments(
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

	public static void updatePermissionFields(String name, String primKey) {
		if (isIndexReadOnly()) {
			return;
		}

		if (PermissionThreadLocal.isFlushResourcePermissionEnabled(
				name, primKey)) {

			_searchPermissionChecker.updatePermissionFields(name, primKey);
		}
	}

	public void setExcludedEntryClassNames(
		List<String> excludedEntryClassNames) {

		PortalRuntimePermission.checkSetBeanProperty(
			getClass(), "excludedEntryClassNames");

		_excludedEntryClassNames.addAll(excludedEntryClassNames);
	}

	public void setQueueCapacity(int queueCapacity) {
		_queueCapacity = queueCapacity;
	}

	protected static void setCommitImmediately(
		SearchContext searchContext, boolean commitImmediately) {

		if (!commitImmediately) {
			searchContext.setCommitImmediately(_INDEX_COMMIT_IMMEDIATELY);
		}
		else {
			searchContext.setCommitImmediately(true);
		}
	}

	private SearchEngineUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			SearchEngineConfigurator.class,
			new SearchEngineConfiguratorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final boolean _INDEX_COMMIT_IMMEDIATELY =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.INDEX_COMMIT_IMMEDIATELY));

	private static final Log _log = LogFactoryUtil.getLog(
		SearchEngineUtil.class);

	private static final Map<Long, Long> _companyIds =
		new ConcurrentHashMap<>();
	private static String _defaultSearchEngineId;
	private static final Set<String> _excludedEntryClassNames = new HashSet<>();
	private static boolean _indexReadOnly = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INDEX_READ_ONLY));
	private static int _queueCapacity = 200;
	private static final Map<String, QueuingSearchEngine>
		_queuingSearchEngines = new HashMap<>();
	private static final Map<String, SearchEngine> _searchEngines =
		new ConcurrentHashMap<>();
	private static final SearchPermissionChecker _searchPermissionChecker =
		ProxyFactory.newServiceTrackedInstance(SearchPermissionChecker.class);

	private final ServiceTracker
		<SearchEngineConfigurator, SearchEngineConfigurator> _serviceTracker;

	private class SearchEngineConfiguratorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<SearchEngineConfigurator, SearchEngineConfigurator> {

		@Override
		public SearchEngineConfigurator addingService(
			ServiceReference<SearchEngineConfigurator> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			SearchEngineConfigurator searchEngineConfigurator =
				registry.getService(serviceReference);

			searchEngineConfigurator.afterPropertiesSet();

			return searchEngineConfigurator;
		}

		@Override
		public void modifiedService(
			ServiceReference<SearchEngineConfigurator> serviceReference,
			SearchEngineConfigurator searchEngineConfigurator) {
		}

		@Override
		public void removedService(
			ServiceReference<SearchEngineConfigurator> serviceReference,
			SearchEngineConfigurator searchEngineConfigurator) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			searchEngineConfigurator.destroy();
		}

	}

}
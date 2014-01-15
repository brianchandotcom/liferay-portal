/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.PermissionThreadLocal;

import java.util.Collection;
import java.util.HashSet;
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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}
	 */
	@Deprecated
	public static final int ALL_POS = -1;

	public static final String GENERIC_ENGINE_ID = "GENERIC_ENGINE";

	public static final String SYSTEM_ENGINE_ID = "SYSTEM_ENGINE";

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addDocument(String, long,
	 *             Document)}
	 */
	@Deprecated
	public static void addDocument(long companyId, Document document)
		throws SearchException {

		addDocument(_getSearchEngineId(document), companyId, document);
	}

	public static void addDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		if (isIndexReadOnly()) {
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

		indexWriter.addDocument(searchContext, document);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addDocuments(String, long,
	 *             Collection)}
	 */
	@Deprecated
	public static void addDocuments(
			long companyId, Collection<Document> documents)
		throws SearchException {

		addDocuments(_getSearchEngineId(documents), companyId, documents);
	}

	public static void addDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents)
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

		indexWriter.addDocuments(searchContext, documents);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #setSearchEngine(String,
	 *             SearchEngine)}
	 */
	@Deprecated
	public static void addSearchEngine(SearchEngine searchEngine) {
		String searchEngineId = getDefaultSearchEngineId();

		PortalRuntimePermission.checkSearchEngine(searchEngineId);

		_searchEngines.put(searchEngineId, searchEngine);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #deleteDocument(String, long,
	 *             String)}
	 */
	@Deprecated
	public static void deleteDocument(long companyId, String uid)
		throws SearchException {

		for (String searchEngineId : _searchEngines.keySet()) {
			deleteDocument(searchEngineId, companyId, uid);
		}
	}

	public static void deleteDocument(
			String searchEngineId, long companyId, String uid)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.deleteDocument(searchContext, uid);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #deleteDocuments(String,
	 *             long, Collection)}
	 */
	@Deprecated
	public static void deleteDocuments(long companyId, Collection<String> uids)
		throws SearchException {

		for (String searchEngineId : _searchEngines.keySet()) {
			deleteDocuments(searchEngineId, companyId, uids);
		}
	}

	public static void deleteDocuments(
			String searchEngineId, long companyId, Collection<String> uids)
		throws SearchException {

		if (isIndexReadOnly() || (uids == null) || uids.isEmpty()) {
			return;
		}

		SearchEngine searchEngine = getSearchEngine(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.deleteDocuments(searchContext, uids);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #deletePortletDocuments(String, long, String)}
	 */
	@Deprecated
	public static void deletePortletDocuments(long companyId, String portletId)
		throws SearchException {

		for (String searchEngineId : _searchEngines.keySet()) {
			deletePortletDocuments(searchEngineId, companyId, portletId);
		}
	}

	public static void deletePortletDocuments(
			String searchEngineId, long companyId, String portletId)
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

		indexWriter.deletePortletDocuments(searchContext, portletId);
	}

	public static String getDefaultSearchEngineId() {
		if (_defaultSearchEngineId == null) {
			return SYSTEM_ENGINE_ID;
		}

		return _defaultSearchEngineId;
	}

	public static String[] getEntryClassNames() {
		Set<String> assetEntryClassNames = new HashSet<String>();

		for (Indexer indexer : IndexerRegistryUtil.getIndexers()) {
			for (String className : indexer.getClassNames()) {
				if (!_excludedEntryClassNames.contains(className)) {
					assetEntryClassNames.add(className);
				}
			}
		}

		return assetEntryClassNames.toArray(
			new String[assetEntryClassNames.size()]);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getSearchEngine(String)}
	 */
	@Deprecated
	public static SearchEngine getSearchEngine() {
		return getSearchEngine(getDefaultSearchEngineId());
	}

	public static SearchEngine getSearchEngine(String searchEngineId) {
		PortalRuntimePermission.checkSearchEngine(searchEngineId);

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		if (searchEngine == null) {
			if (getDefaultSearchEngineId().equals(searchEngineId)) {
				throw new IllegalStateException(
					"There is no default search engine configured with ID " +
						getDefaultSearchEngineId());
			}

			if (_log.isWarnEnabled()) {
				_log.warn(
					"There is no search engine configured with ID " +
						searchEngineId);
			}
		}

		return searchEngine;
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

	public static boolean isIndexReadOnly() {
		PortalRuntimePermission.checkGetBeanProperty(
			SearchEngineUtil.class, "indexReadOnly");

		return _indexReadOnly;
	}

	public static SearchEngine removeSearchEngine(String searchEngineId) {
		PortalRuntimePermission.checkSearchEngine(searchEngineId);

		return _searchEngines.remove(searchEngineId);
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public static Hits search(
			long companyId, long[] groupIds, long userId, String className,
			Query query, int start, int end)
		throws SearchException {

		SearchContext searchContext = new SearchContext();

		searchContext.setSearchEngineId(getDefaultSearchEngineId());

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupIds, userId, className, query, searchContext);
		}

		return search(
			companyId, query, SortFactoryUtil.getDefaultSorts(), start, end);
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public static Hits search(
			long companyId, long[] groupIds, long userId, String className,
			Query query, Sort sort, int start, int end)
		throws SearchException {

		SearchContext searchContext = new SearchContext();

		searchContext.setSearchEngineId(getDefaultSearchEngineId());

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupIds, userId, className, query, searchContext);
		}

		return search(companyId, query, sort, start, end);
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public static Hits search(
			long companyId, long[] groupIds, long userId, String className,
			Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		SearchContext searchContext = new SearchContext();

		searchContext.setSearchEngineId(getDefaultSearchEngineId());

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupIds, userId, className, query, searchContext);
		}

		return search(companyId, query, sorts, start, end);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #search(String, long, Query,
	 *             int, int)}
	 */
	@Deprecated
	public static Hits search(long companyId, Query query, int start, int end)
		throws SearchException {

		return search(getDefaultSearchEngineId(), companyId, query, start, end);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #search(String, long, Query,
	 *             Sort, int, int)}
	 */
	@Deprecated
	public static Hits search(
			long companyId, Query query, Sort sort, int start, int end)
		throws SearchException {

		return search(
			getDefaultSearchEngineId(), companyId, query, sort, start, end);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #search(String, long, Query,
	 *             Sort[], int, int)}
	 */
	@Deprecated
	public static Hits search(
			long companyId, Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		return search(
			getDefaultSearchEngineId(), companyId, query, sorts, start, end);
	}

	public static Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + query.toString());
		}

		SearchEngine searchEngine = getSearchEngine(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.search(searchContext, query);
	}

	public static Hits search(
			String searchEngineId, long companyId, Query query, int start,
			int end)
		throws SearchException {

		return search(
			searchEngineId, companyId, query, SortFactoryUtil.getDefaultSorts(),
			start, end);
	}

	public static Hits search(
			String searchEngineId, long companyId, Query query, Sort sort,
			int start, int end)
		throws SearchException {

		return search(
			searchEngineId, companyId, query, new Sort[] {sort}, start, end);
	}

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
	 * @deprecated As of 6.2.0, replaced by {@link #updateDocument(String, long,
	 *             Document)}
	 */
	@Deprecated
	public static void updateDocument(long companyId, Document document)
		throws SearchException {

		updateDocument(_getSearchEngineId(document), companyId, document);
	}

	public static void updateDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		if (isIndexReadOnly()) {
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

		indexWriter.updateDocument(searchContext, document);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #updateDocuments(String,
	 *             long, Collection)}
	 */
	@Deprecated
	public static void updateDocuments(
			long companyId, Collection<Document> documents)
		throws SearchException {

		updateDocuments(_getSearchEngineId(documents), companyId, documents);
	}

	public static void updateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents)
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

		indexWriter.updateDocuments(searchContext, documents);
	}

	public static void updatePermissionFields(String name, String primKey) {
		if (isIndexReadOnly() || !PermissionThreadLocal.isFlushEnabled()) {
			return;
		}

		_searchPermissionChecker.updatePermissionFields(name, primKey);
	}

	public void setExcludedEntryClassNames(
		List<String> excludedEntryClassNames) {

		PortalRuntimePermission.checkSetBeanProperty(
			getClass(), "excludedEntryClassNames");

		_excludedEntryClassNames.addAll(excludedEntryClassNames);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #setSearchEngine(String,
	 *             SearchEngine)}
	 */
	@Deprecated
	public void setSearchEngine(SearchEngine searchEngine) {
		String searchEngineId = getDefaultSearchEngineId();

		PortalRuntimePermission.checkSearchEngine(searchEngineId);

		_searchEngines.put(searchEngineId, searchEngine);
	}

	public void setSearchPermissionChecker(
		SearchPermissionChecker searchPermissionChecker) {

		PortalRuntimePermission.checkSetBeanProperty(
			getClass(), "searchPermissionChecker");

		_searchPermissionChecker = searchPermissionChecker;
	}

	private static String _getSearchEngineId(Collection<Document> documents) {
		if (!documents.isEmpty()) {
			Document document = documents.iterator().next();

			return _getSearchEngineId(document);
		}

		return getDefaultSearchEngineId();
	}

	private static String _getSearchEngineId(Document document) {
		String entryClassName = document.get("entryClassName");

		Indexer indexer = IndexerRegistryUtil.getIndexer(entryClassName);

		String searchEngineId = indexer.getSearchEngineId();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Search engine ID for " + indexer.getClass() + " is " +
					searchEngineId);
		}

		return searchEngineId;
	}

	private static Log _log = LogFactoryUtil.getLog(SearchEngineUtil.class);

	private static String _defaultSearchEngineId;
	private static Set<String> _excludedEntryClassNames = new HashSet<String>();
	private static boolean _indexReadOnly = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INDEX_READ_ONLY));
	private static Map<String, SearchEngine> _searchEngines =
		new ConcurrentHashMap<String, SearchEngine>();
	private static SearchPermissionChecker _searchPermissionChecker;

}
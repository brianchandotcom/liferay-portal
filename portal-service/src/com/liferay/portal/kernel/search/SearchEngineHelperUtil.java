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

import com.liferay.portal.kernel.util.ProxyFactory;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class SearchEngineHelperUtil {

	public static void addDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException {

		_searchEngineHelper.addDocument(
			searchEngineId, companyId, document, commitImmediately);
	}

	public static void addDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException {

		_searchEngineHelper.addDocuments(
			searchEngineId, companyId, documents, commitImmediately);
	}

	public static void commit(String searchEngineId) throws SearchException {
		_searchEngineHelper.commit(searchEngineId);
	}

	public static void commit(String searchEngineId, long companyId)
		throws SearchException {

		_searchEngineHelper.commit(searchEngineId, companyId);
	}

	public static void deleteDocument(
			String searchEngineId, long companyId, String uid,
			boolean commitImmediately)
		throws SearchException {

		_searchEngineHelper.deleteDocument(
			searchEngineId, companyId, uid, commitImmediately);
	}

	public static void deleteDocuments(
			String searchEngineId, long companyId, Collection<String> uids,
			boolean commitImmediately)
		throws SearchException {

		_searchEngineHelper.deleteDocuments(
			searchEngineId, companyId, uids, commitImmediately);
	}

	public static void deleteEntityDocuments(
			String searchEngineId, long companyId, String className,
			boolean commitImmediately)
		throws SearchException {

		_searchEngineHelper.deleteEntityDocuments(
			searchEngineId, companyId, className, commitImmediately);
	}

	public static void flushQueuedSearchEngine() {
		_searchEngineHelper.flushQueuedSearchEngine();
	}

	public static void flushQueuedSearchEngine(String searchEngineId) {
		_searchEngineHelper.flushQueuedSearchEngine(searchEngineId);
	}

	public static String getDefaultSearchEngineId() {
		return _searchEngineHelper.getDefaultSearchEngineId();
	}

	public static String[] getEntryClassNames() {
		return _searchEngineHelper.getEntryClassNames();
	}

	public static String getQueryString(
		SearchContext searchContext, Query query) {

		return _searchEngineHelper.getQueryString(searchContext, query);
	}

	public static SearchEngine getSearchEngine(String searchEngineId) {
		return _searchEngineHelper.getSearchEngine(searchEngineId);
	}

	public static String getSearchEngineId(Collection<Document> documents) {
		return _searchEngineHelper.getSearchEngineId(documents);
	}

	public static String getSearchEngineId(Document document) {
		return _searchEngineHelper.getSearchEngineId(document);
	}

	public static Set<String> getSearchEngineIds() {
		return _searchEngineHelper.getSearchEngineIds();
	}

	public static SearchEngine getSearchEngineSilent(String searchEngineId) {
		return _searchEngineHelper.getSearchEngineSilent(searchEngineId);
	}

	public static SearchPermissionChecker getSearchPermissionChecker() {
		return _searchEngineHelper.getSearchPermissionChecker();
	}

	public static String getSearchReaderDestinationName(String searchEngineId) {
		return _searchEngineHelper.getSearchReaderDestinationName(
			searchEngineId);
	}

	public static void indexKeyword(
			long companyId, String querySuggestion, float weight,
			String keywordType, Locale locale)
		throws SearchException {

		_searchEngineHelper.indexKeyword(
			companyId, querySuggestion, weight, keywordType, locale);
	}

	public static void indexKeyword(
			String searchEngineId, long companyId, String querySuggestion,
			float weight, String keywordType, Locale locale)
		throws SearchException {

		_searchEngineHelper.indexKeyword(
			searchEngineId, companyId, querySuggestion, weight, keywordType,
			locale);
	}

	public static void indexQuerySuggestionDictionaries(long companyId)
		throws SearchException {

		_searchEngineHelper.indexQuerySuggestionDictionaries(companyId);
	}

	public static void indexQuerySuggestionDictionaries(
			String searchEngineId, long companyId)
		throws SearchException {

		_searchEngineHelper.indexQuerySuggestionDictionaries(
			searchEngineId, companyId);
	}

	public static void indexQuerySuggestionDictionary(
			long companyId, Locale locale)
		throws SearchException {

		_searchEngineHelper.indexQuerySuggestionDictionary(companyId, locale);
	}

	public static void indexQuerySuggestionDictionary(
			String searchEngineId, long companyId, Locale locale)
		throws SearchException {

		_searchEngineHelper.indexQuerySuggestionDictionary(
			searchEngineId, companyId, locale);
	}

	public static void indexSpellCheckerDictionaries(long companyId)
		throws SearchException {

		_searchEngineHelper.indexSpellCheckerDictionaries(companyId);
	}

	public static void indexSpellCheckerDictionaries(
			String searchEngineId, long companyId)
		throws SearchException {

		_searchEngineHelper.indexSpellCheckerDictionaries(
			searchEngineId, companyId);
	}

	public static void indexSpellCheckerDictionary(
			long companyId, Locale locale)
		throws SearchException {

		_searchEngineHelper.indexSpellCheckerDictionary(companyId, locale);
	}

	public static void indexSpellCheckerDictionary(
			String searchEngineId, long companyId, Locale locale)
		throws SearchException {

		_searchEngineHelper.indexSpellCheckerDictionary(
			searchEngineId, companyId, locale);
	}

	public static void initialize(long companyId) {
		_searchEngineHelper.initialize(companyId);
	}

	public static boolean isIndexReadOnly() {
		return _searchEngineHelper.isIndexReadOnly();
	}

	public static void partiallyUpdateDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException {

		_searchEngineHelper.partiallyUpdateDocument(
			searchEngineId, companyId, document, commitImmediately);
	}

	public static void partiallyUpdateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException {

		_searchEngineHelper.partiallyUpdateDocuments(
			searchEngineId, companyId, documents, commitImmediately);
	}

	public static void removeCompany(long companyId) {
		_searchEngineHelper.removeCompany(companyId);
	}

	public static Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		return _searchEngineHelper.search(searchContext, query);
	}

	public static long searchCount(SearchContext searchContext, Query query)
		throws SearchException {

		return _searchEngineHelper.searchCount(searchContext, query);
	}

	public static void setDefaultSearchEngineId(String defaultSearchEngineId) {
		_searchEngineHelper.setDefaultSearchEngineId(defaultSearchEngineId);
	}

	public static void setExcludedEntryClassNames(
		List<String> excludedEntryClassNames) {

		_searchEngineHelper.setExcludedEntryClassNames(excludedEntryClassNames);
	}

	public static void setIndexReadOnly(boolean indexReadOnly) {
		_searchEngineHelper.setIndexReadOnly(indexReadOnly);
	}

	public static void setQueueCapacity(int queueCapacity) {
		_searchEngineHelper.setQueueCapacity(queueCapacity);
	}

	public static void setSearchEngine(
		String searchEngineId, SearchEngine searchEngine) {

		_searchEngineHelper.setSearchEngine(searchEngineId, searchEngine);
	}

	public static String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		return _searchEngineHelper.spellCheckKeywords(searchContext);
	}

	public static Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max)
		throws SearchException {

		return _searchEngineHelper.spellCheckKeywords(searchContext, max);
	}

	public static String[] suggestKeywordQueries(
			SearchContext searchContext, int max)
		throws SearchException {

		return _searchEngineHelper.suggestKeywordQueries(searchContext, max);
	}

	public static void updateDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException {

		_searchEngineHelper.updateDocument(
			searchEngineId, companyId, document, commitImmediately);
	}

	public static void updateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException {

		_searchEngineHelper.updateDocuments(
			searchEngineId, companyId, documents, commitImmediately);
	}

	public static void updatePermissionFields(String name, String primKey) {
		_searchEngineHelper.updatePermissionFields(name, primKey);
	}

	public String getSearchWriterDestinationName(String searchEngineId) {
		return _searchEngineHelper.getSearchWriterDestinationName(
			searchEngineId);
	}

	public SearchEngine removeSearchEngine(String searchEngineId) {
		return _searchEngineHelper.removeSearchEngine(searchEngineId);
	}

	private static final SearchEngineHelper _searchEngineHelper =
		ProxyFactory.newServiceTrackedInstance(SearchEngineHelper.class);

}
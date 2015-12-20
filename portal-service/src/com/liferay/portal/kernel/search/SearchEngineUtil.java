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

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Michael C. Han
 * @deprecated As of 7.0.0, replaced by {@link IndexWriterHelperUtil,
 *             IndexSearcherHelperUtil, SearchEngineHelperUtil}
 */
@Deprecated
public class SearchEngineUtil extends SearchEngineHelperUtil {

	@Deprecated
	public static final String GENERIC_ENGINE_ID =
		SearchEngineHelper.GENERIC_ENGINE_ID;

	@Deprecated
	public static final String SYSTEM_ENGINE_ID =
		SearchEngineHelper.SYSTEM_ENGINE_ID;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#addDocument(
	 *             String, long, Document, boolean)}
	 */
	@Deprecated
	public static void addDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		IndexWriterHelperUtil.addDocument(
			searchEngineId, companyId, document, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#addDocuments(
	 *             String, long, Collection, boolean)}
	 */
	@Deprecated
	public static void addDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException {

		IndexWriterHelperUtil.addDocument(
			searchEngineId, companyId, document, commitImmediately);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#addDocuments(
	 *             String, long, Collection, boolean)}
	 */
	@Deprecated
	public static void addDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents)
		throws SearchException {

		IndexWriterHelperUtil.addDocuments(
			searchEngineId, companyId, documents, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#addDocuments(
	 *             String, long, Collection, boolean)}
	 */
	@Deprecated
	public static void addDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException {

		IndexWriterHelperUtil.addDocuments(
			searchEngineId, companyId, documents, commitImmediately);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#commit(String)}
	 */
	@Deprecated
	public static void commit(String searchEngineId) throws SearchException {
		IndexWriterHelperUtil.commit(searchEngineId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#commit(String, long)}
	 */
	@Deprecated
	public static void commit(String searchEngineId, long companyId)
		throws SearchException {

		IndexWriterHelperUtil.commit(searchEngineId, companyId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#deleteDocument(
	 *             String, long, String, boolean)}
	 */
	@Deprecated
	public static void deleteDocument(
			String searchEngineId, long companyId, String uid)
		throws SearchException {

		IndexWriterHelperUtil.deleteDocument(
			searchEngineId, companyId, uid, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#deleteDocument(
	 *             String, long, String, boolean)}
	 */
	@Deprecated
	public static void deleteDocument(
			String searchEngineId, long companyId, String uid,
			boolean commitImmediately)
		throws SearchException {

		IndexWriterHelperUtil.deleteDocument(
			searchEngineId, companyId, uid, commitImmediately);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#deleteDocuments(
	 *             String, long, Collection, boolean)}
	 */
	@Deprecated
	public static void deleteDocuments(
			String searchEngineId, long companyId, Collection<String> uids)
		throws SearchException {

		IndexWriterHelperUtil.deleteDocuments(
			searchEngineId, companyId, uids, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#deleteDocuments(
	 *             String, long, Collection, boolean)}
	 */
	@Deprecated
	public static void deleteDocuments(
			String searchEngineId, long companyId, Collection<String> uids,
			boolean commitImmediately)
		throws SearchException {

		IndexWriterHelperUtil.deleteDocuments(
			searchEngineId, companyId, uids, commitImmediately);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#deleteEntityDocuments(
	 *             String, long, String, boolean)}
	 */
	@Deprecated
	public static void deleteEntityDocuments(
			String searchEngineId, long companyId, String className,
			boolean commitImmediately)
		throws SearchException {

		IndexWriterHelperUtil.deleteEntityDocuments(
			searchEngineId, companyId, className, commitImmediately);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#deleteEntityDocuments(
	 *             String, long, String, boolean)}
	 */
	@Deprecated
	public static void deletePortletDocuments(
			String searchEngineId, long companyId, String portletId)
		throws SearchException {

		IndexWriterHelperUtil.deleteEntityDocuments(
			searchEngineId, companyId, portletId, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexSearcherHelperUtil#getQueryString(SearchContext, Query)}
	 */
	@Deprecated
	public static String getQueryString(
			SearchContext searchContext, Query query)
		throws SearchException {

		return IndexSearcherHelperUtil.getQueryString(searchContext, query);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#indexKeyword(
	 *             long, String, float, String, Locale)}
	 */
	@Deprecated
	public static void indexKeyword(
			long companyId, String querySuggestion, float weight,
			String keywordType, Locale locale)
		throws SearchException {

		IndexWriterHelperUtil.indexKeyword(
			companyId, querySuggestion, weight, keywordType, locale);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#indexKeyword(
	 *             String, long, String, float, String, Locale)}
	 */
	@Deprecated
	public static void indexKeyword(
			String searchEngineId, long companyId, String querySuggestion,
			float weight, String keywordType, Locale locale)
		throws SearchException {

		IndexWriterHelperUtil.indexKeyword(
			searchEngineId, companyId, querySuggestion, weight, keywordType,
			locale);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#indexQuerySuggestionDictionaries(long)}
	 */
	@Deprecated
	public static void indexQuerySuggestionDictionaries(long companyId)
		throws SearchException {

		IndexWriterHelperUtil.indexQuerySuggestionDictionaries(companyId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#indexQuerySuggestionDictionaries(
	 *             String, long)}
	 */
	@Deprecated
	public static void indexQuerySuggestionDictionaries(
			String searchEngineId, long companyId)
		throws SearchException {

		IndexWriterHelperUtil.indexQuerySuggestionDictionaries(
			searchEngineId, companyId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#indexQuerySuggestionDictionary(
	 *             long, Locale)}
	 */
	@Deprecated
	public static void indexQuerySuggestionDictionary(
			long companyId, Locale locale)
		throws SearchException {

		IndexWriterHelperUtil.indexQuerySuggestionDictionary(companyId, locale);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#indexQuerySuggestionDictionary(
	 *             String, long, Locale)}
	 */
	@Deprecated
	public static void indexQuerySuggestionDictionary(
			String searchEngineId, long companyId, Locale locale)
		throws SearchException {

		IndexWriterHelperUtil.indexQuerySuggestionDictionary(
			searchEngineId, companyId, locale);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#indexSpellCheckerDictionaries(long)}
	 */
	@Deprecated
	public static void indexSpellCheckerDictionaries(long companyId)
		throws SearchException {

		IndexWriterHelperUtil.indexSpellCheckerDictionaries(companyId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#indexSpellCheckerDictionaries(
	 *             String, long)}
	 */
	@Deprecated
	public static void indexSpellCheckerDictionaries(
			String searchEngineId, long companyId)
		throws SearchException {

		IndexWriterHelperUtil.indexSpellCheckerDictionaries(
			searchEngineId, companyId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#indexSpellCheckerDictionary(
	 *             long, Locale)}
	 */
	@Deprecated
	public static void indexSpellCheckerDictionary(
			long companyId, Locale locale)
		throws SearchException {

		IndexWriterHelperUtil.indexSpellCheckerDictionary(companyId, locale);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#indexSpellCheckerDictionary(
	 *             String, long, Locale)}
	 */
	@Deprecated
	public static void indexSpellCheckerDictionary(
			String searchEngineId, long companyId, Locale locale)
		throws SearchException {

		IndexWriterHelperUtil.indexSpellCheckerDictionary(
			searchEngineId, companyId, locale);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#isIndexReadOnly()}
	 */
	@Deprecated
	public static boolean isIndexReadOnly() {
		return IndexWriterHelperUtil.isIndexReadOnly();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#partiallyUpdateDocument(
	 *             String, long, Document, boolean)}
	 */
	@Deprecated
	public static void partiallyUpdateDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException {

		IndexWriterHelperUtil.partiallyUpdateDocument(
			searchEngineId, companyId, document, commitImmediately);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#partiallyUpdateDocuments(
	 *             String, long, Collection, boolean)}
	 */
	@Deprecated
	public static void partiallyUpdateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException {

		IndexWriterHelperUtil.partiallyUpdateDocuments(
			searchEngineId, companyId, documents, commitImmediately);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexSearcherHelperUtil#search(SearchContext, Query)}
	 */
	@Deprecated
	public static Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		return IndexSearcherHelperUtil.search(searchContext, query);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexSearcherHelperUtil#search(SearchContext, Query)}
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
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexSearcherHelperUtil#search(SearchContext, Query)}
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
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexSearcherHelperUtil#search(SearchContext, Query)}
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

		return IndexSearcherHelperUtil.search(searchContext, query);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexSearcherHelperUtil#searchCount(SearchContext, Query)}
	 */
	@Deprecated
	public static long searchCount(SearchContext searchContext, Query query)
		throws SearchException {

		return IndexSearcherHelperUtil.searchCount(searchContext, query);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#setIndexReadOnly(boolean)}
	 */
	@Deprecated
	public static void setIndexReadOnly(boolean readOnly) {
		IndexWriterHelperUtil.setIndexReadOnly(readOnly);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexSearcherHelperUtil#spellCheckKeywords(SearchContext)}
	 */
	@Deprecated
	public static String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		return IndexSearcherHelperUtil.spellCheckKeywords(searchContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexSearcherHelperUtil#spellCheckKeywords(
	 *             SearchContext, int)}
	 */
	@Deprecated
	public static Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max)
		throws SearchException {

		return IndexSearcherHelperUtil.spellCheckKeywords(searchContext, max);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexSearcherHelperUtil#suggestKeywordQueries(
	 *             SearchContext, int)}
	 */
	@Deprecated
	public static String[] suggestKeywordQueries(
			SearchContext searchContext, int max)
		throws SearchException {

		return IndexSearcherHelperUtil.suggestKeywordQueries(
			searchContext, max);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#updateDocument(
	 *             String, long, Document, boolean)}
	 */
	@Deprecated
	public static void updateDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		IndexWriterHelperUtil.updateDocument(
			searchEngineId, companyId, document, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#updateDocument(
	 *             String, long, Document, boolean)}
	 */
	@Deprecated
	public static void updateDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException {

		IndexWriterHelperUtil.updateDocument(
			searchEngineId, companyId, document, commitImmediately);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#updateDocuments(
	 *             String, long, Collection, boolean)}
	 */
	@Deprecated
	public static void updateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents)
		throws SearchException {

		IndexWriterHelperUtil.updateDocuments(
			searchEngineId, companyId, documents, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#updateDocuments(
	 *             String, long, Collection, boolean)}
	 */
	@Deprecated
	public static void updateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException {

		IndexWriterHelperUtil.updateDocuments(
			searchEngineId, companyId, documents, commitImmediately);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             IndexWriterHelperUtil#updatePermissionFields(String, String)}
	 */
	@Deprecated
	public static void updatePermissionFields(String name, String primKey) {
		IndexWriterHelperUtil.updatePermissionFields(name, primKey);
	}

}
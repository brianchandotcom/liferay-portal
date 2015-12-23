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
import java.util.Locale;

/**
 * @author Michael C. Han
 */
public class IndexWriterHelperUtil {

	public static void addDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException {

		_indexWriterHelper.addDocument(
			searchEngineId, companyId, document, commitImmediately);
	}

	public static void addDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException {

		_indexWriterHelper.addDocuments(
			searchEngineId, companyId, documents, commitImmediately);
	}

	public static void commit(String searchEngineId) throws SearchException {
		_indexWriterHelper.commit(searchEngineId);
	}

	public static void commit(String searchEngineId, long companyId)
		throws SearchException {

		_indexWriterHelper.commit(searchEngineId, companyId);
	}

	public static void deleteDocument(
			String searchEngineId, long companyId, String uid,
			boolean commitImmediately)
		throws SearchException {

		_indexWriterHelper.deleteDocument(
			searchEngineId, companyId, uid, commitImmediately);
	}

	public static void deleteDocuments(
			String searchEngineId, long companyId, Collection<String> uids,
			boolean commitImmediately)
		throws SearchException {

		_indexWriterHelper.deleteDocuments(
			searchEngineId, companyId, uids, commitImmediately);
	}

	public static void deleteEntityDocuments(
			String searchEngineId, long companyId, String className,
			boolean commitImmediately)
		throws SearchException {

		_indexWriterHelper.deleteEntityDocuments(
			searchEngineId, companyId, className, commitImmediately);
	}

	public static void indexKeyword(
			long companyId, String querySuggestion, float weight,
			String keywordType, Locale locale)
		throws SearchException {

		_indexWriterHelper.indexKeyword(
			companyId, querySuggestion, weight, keywordType, locale);
	}

	public static void indexKeyword(
			String searchEngineId, long companyId, String querySuggestion,
			float weight, String keywordType, Locale locale)
		throws SearchException {

		_indexWriterHelper.indexKeyword(
			searchEngineId, companyId, querySuggestion, weight, keywordType,
			locale);
	}

	public static void indexQuerySuggestionDictionaries(long companyId)
		throws SearchException {

		_indexWriterHelper.indexQuerySuggestionDictionaries(companyId);
	}

	public static void indexQuerySuggestionDictionaries(
			String searchEngineId, long companyId)
		throws SearchException {

		_indexWriterHelper.indexQuerySuggestionDictionaries(
			searchEngineId, companyId);
	}

	public static void indexQuerySuggestionDictionary(
			long companyId, Locale locale)
		throws SearchException {

		_indexWriterHelper.indexQuerySuggestionDictionary(companyId, locale);
	}

	public static void indexQuerySuggestionDictionary(
			String searchEngineId, long companyId, Locale locale)
		throws SearchException {

		_indexWriterHelper.indexQuerySuggestionDictionary(
			searchEngineId, companyId, locale);
	}

	public static void indexSpellCheckerDictionaries(long companyId)
		throws SearchException {

		_indexWriterHelper.indexSpellCheckerDictionaries(companyId);
	}

	public static void indexSpellCheckerDictionaries(
			String searchEngineId, long companyId)
		throws SearchException {

		_indexWriterHelper.indexSpellCheckerDictionaries(
			searchEngineId, companyId);
	}

	public static void indexSpellCheckerDictionary(
			long companyId, Locale locale)
		throws SearchException {

		_indexWriterHelper.indexSpellCheckerDictionary(companyId, locale);
	}

	public static void indexSpellCheckerDictionary(
			String searchEngineId, long companyId, Locale locale)
		throws SearchException {

		_indexWriterHelper.indexSpellCheckerDictionary(
			searchEngineId, companyId, locale);
	}

	public static boolean isIndexReadOnly() {
		return _indexWriterHelper.isIndexReadOnly();
	}

	public static void partiallyUpdateDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException {

		_indexWriterHelper.partiallyUpdateDocument(
			searchEngineId, companyId, document, commitImmediately);
	}

	public static void partiallyUpdateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException {

		_indexWriterHelper.partiallyUpdateDocuments(
			searchEngineId, companyId, documents, commitImmediately);
	}

	public static void setIndexReadOnly(boolean indexReadOnly) {
		_indexWriterHelper.setIndexReadOnly(indexReadOnly);
	}

	public static void updateDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException {

		_indexWriterHelper.updateDocument(
			searchEngineId, companyId, document, commitImmediately);
	}

	public static void updateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException {

		_indexWriterHelper.updateDocuments(
			searchEngineId, companyId, documents, commitImmediately);
	}

	public static void updatePermissionFields(String name, String primKey) {
		_indexWriterHelper.updatePermissionFields(name, primKey);
	}

	private static final IndexWriterHelper _indexWriterHelper =
		ProxyFactory.newServiceTrackedInstance(IndexWriterHelper.class);

}
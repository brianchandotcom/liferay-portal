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
import java.util.Locale;

/**
 * @author Michael C. Han
 */
public interface IndexWriterHelper {

	public void addDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException;

	public void addDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException;

	public void commit(String searchEngineId) throws SearchException;

	public void commit(String searchEngineId, long companyId)
		throws SearchException;

	public void deleteDocument(
			String searchEngineId, long companyId, String uid,
			boolean commitImmediately)
		throws SearchException;

	public void deleteDocuments(
			String searchEngineId, long companyId, Collection<String> uids,
			boolean commitImmediately)
		throws SearchException;

	public void deleteEntityDocuments(
			String searchEngineId, long companyId, String className,
			boolean commitImmediately)
		throws SearchException;

	public void indexKeyword(
			long companyId, String querySuggestion, float weight,
			String keywordType, Locale locale)
		throws SearchException;

	public void indexKeyword(
			String searchEngineId, long companyId, String querySuggestion,
			float weight, String keywordType, Locale locale)
		throws SearchException;

	public void indexQuerySuggestionDictionaries(long companyId)
		throws SearchException;

	public void indexQuerySuggestionDictionaries(
			String searchEngineId, long companyId)
		throws SearchException;

	public void indexQuerySuggestionDictionary(long companyId, Locale locale)
		throws SearchException;

	public void indexQuerySuggestionDictionary(
			String searchEngineId, long companyId, Locale locale)
		throws SearchException;

	public void indexSpellCheckerDictionaries(long companyId)
		throws SearchException;

	public void indexSpellCheckerDictionaries(
			String searchEngineId, long companyId)
		throws SearchException;

	public void indexSpellCheckerDictionary(long companyId, Locale locale)
		throws SearchException;

	public void indexSpellCheckerDictionary(
			String searchEngineId, long companyId, Locale locale)
		throws SearchException;

	public boolean isIndexReadOnly();

	public void partiallyUpdateDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException;

	public void partiallyUpdateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException;

	public void setIndexReadOnly(boolean indexReadOnly);

	public void updateDocument(
			String searchEngineId, long companyId, Document document,
			boolean commitImmediately)
		throws SearchException;

	public void updateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents, boolean commitImmediately)
		throws SearchException;

	public void updatePermissionFields(String name, String primKey);

}
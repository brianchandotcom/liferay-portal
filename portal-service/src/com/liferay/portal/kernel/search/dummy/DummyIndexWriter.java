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

package com.liferay.portal.kernel.search.dummy;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;

import java.util.Collection;

/**
 * @author Marcellus Tavares
 * @author Brian Wing Shun Chan
 */
public class DummyIndexWriter implements IndexWriter {

	@Override
	public void addDocument(SearchContext searchContext, Document document) {
	}

	@Override
	public void addDocuments(
		SearchContext searchContext, Collection<Document> documents) {
	}

	@Override
	public void clearQuerySuggestionDictionaryIndexes(
		SearchContext searchContext) {
	}

	@Override
	public void clearSpellCheckerDictionaryIndexes(
		SearchContext searchContext) {
	}

	@Override
	public void commit(long companyId) throws SearchException {
	}

	@Override
	public void deleteDocument(SearchContext searchContext, String uid) {
	}

	@Override
	public void deleteDocuments(
		SearchContext searchContext, Collection<String> uids) {
	}

	@Override
	public void deleteEntityDocuments(
		SearchContext searchContext, String className) {
	}

	@Override
	public void indexKeyword(
		SearchContext searchContext, float weight, String keywordType) {
	}

	@Override
	public void indexQuerySuggestionDictionaries(SearchContext searchContext) {
	}

	@Override
	public void indexQuerySuggestionDictionary(SearchContext searchContext) {
	}

	@Override
	public void indexSpellCheckerDictionaries(SearchContext searchContext) {
	}

	@Override
	public void indexSpellCheckerDictionary(SearchContext searchContext) {
	}

	@Override
	public void partiallyUpdateDocument(
		SearchContext searchContext, Document document) {
	}

	@Override
	public void partiallyUpdateDocuments(
		SearchContext searchContext, Collection<Document> documents) {
	}

	@Override
	public void updateDocument(SearchContext searchContext, Document document) {
	}

	@Override
	public void updateDocuments(
		SearchContext searchContext, Collection<Document> documents) {
	}

}
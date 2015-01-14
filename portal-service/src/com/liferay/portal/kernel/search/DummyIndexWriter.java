/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.kernel.search;

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
	public void deleteDocument(SearchContext searchContext, String uid) {
	}

	@Override
	public void deleteDocuments(
		SearchContext searchContext, Collection<String> uids) {
	}

	@Override
	public void deletePortletDocuments(
		SearchContext searchContext, String portletId) {
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
	public void updateDocument(SearchContext searchContext, Document document) {
	}

	@Override
	public void updateDocuments(
		SearchContext searchContext, Collection<Document> documents) {
	}

}
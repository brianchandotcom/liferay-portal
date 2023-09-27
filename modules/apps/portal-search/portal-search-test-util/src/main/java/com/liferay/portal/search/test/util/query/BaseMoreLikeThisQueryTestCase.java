/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.query;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.internal.document.DocumentBuilderImpl;
import com.liferay.portal.search.query.MoreLikeThisQuery;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public abstract class BaseMoreLikeThisQueryTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testLegacyMoreLikeThisWithFieldAndLikeText() throws Exception {
		addDocuments("java eclipse", "eclipse liferay", "java liferay eclipse");

		com.liferay.portal.kernel.search.generic.MoreLikeThisQuery
			moreLikeThisQuery =
				new com.liferay.portal.kernel.search.generic.MoreLikeThisQuery(
					getCompanyId());

		moreLikeThisQuery.addField(_FIELD_TITLE);
		moreLikeThisQuery.setLikeText("java");

		assertSearch(
			moreLikeThisQuery,
			Arrays.asList("java eclipse", "java liferay eclipse"));
	}

	@Test
	public void testMoreLikeThisWithDocumentIdentifier() throws Exception {
		String id = indexDocumentWithNoFieldsExceptTitle("java");

		try {
			addDocuments(
				"java eclipse", "eclipse liferay", "java liferay eclipse");

			MoreLikeThisQuery.DocumentIdentifier documentIdentifier =
				queries.documentIdentifier(
					String.valueOf(getCompanyId()), "_doc", id);

			MoreLikeThisQuery moreLikeThisQuery = queries.moreLikeThis(
				Collections.singleton(documentIdentifier));

			assertSearch(
				moreLikeThisQuery,
				Arrays.asList("java eclipse", "java liferay eclipse"));
		}
		finally {
			deleteDocumentById(id);
		}
	}

	@Test
	public void testMoreLikeThisWithFieldAndLikeText() throws Exception {
		addDocuments("java eclipse", "eclipse liferay", "java liferay eclipse");

		MoreLikeThisQuery moreLikeThisQuery = queries.moreLikeThis(
			Collections.singletonList(_FIELD_TITLE), "java");

		assertSearch(
			moreLikeThisQuery,
			Arrays.asList("java eclipse", "java liferay eclipse"));
	}

	@Test
	public void testMoreLikeThisWithMinDocFreq() throws Exception {
		addDocuments("Red Blue", "Red Dog", "Red Blue Color", "Color");

		MoreLikeThisQuery moreLikeThisQuery = queries.moreLikeThis(
			Collections.singletonList(_FIELD_TITLE), "Red Blue Color");

		moreLikeThisQuery.setMinDocFrequency(1);
		moreLikeThisQuery.setMinTermFrequency(1);

		assertSearch(
			null, moreLikeThisQuery,
			Arrays.asList("Red Blue Color", "Red Blue", "Color", "Red Dog"));

		moreLikeThisQuery.setMinDocFrequency(3);

		assertSearch(
			null, moreLikeThisQuery,
			Arrays.asList("Red Blue", "Red Dog", "Red Blue Color"));
	}

	@Test
	public void testMoreLikeThisWithMinimumShouldMatch() throws Exception {
		String[] texts = new String[10];

		for (int i = 0; i < 10; i++) {
			String text = "";

			for (int j = 1; j <= (10 - i); j++) {
				text += j + " ";
			}

			texts[i] = text.trim();
			addDocuments(text);
		}

		MoreLikeThisQuery moreLikeThisQuery = queries.moreLikeThis(
			new String[] {_FIELD_TITLE}, texts[0]);

		for (int i = 0; i <= 10; i++) {
			String minimumShouldMatch = (10 * i) + "%";

			moreLikeThisQuery.setMinShouldMatch(minimumShouldMatch);

			List<String> expected = new ArrayList<>();

			if (minimumShouldMatch.equals("0%")) {
				expected = Arrays.asList(texts);
			}
			else {
				for (int j = 0; j < (11 - i); j++) {
					expected.add(texts[j]);
				}
			}

			assertSearch(moreLikeThisQuery, expected);
		}
	}

	@Test
	public void testMoreLikeThisWithMultipleFields() throws Exception {
		addDocuments("alpha charlie", "delta echo");

		addDocuments(
			value -> DocumentCreationHelpers.singleText(
				_FIELD_DESCRIPTION, value),
			"bravo charlie");

		MoreLikeThisQuery moreLikeThisQuery = queries.moreLikeThis(
			new String[] {_FIELD_TITLE}, "alpha", "bravo");

		moreLikeThisQuery.addField(_FIELD_DESCRIPTION);

		assertSearch(
			moreLikeThisQuery, Arrays.asList("alpha charlie", "bravo charlie"));
	}

	@Test
	public void testMoreLikeThisWithoutFields() throws Exception {
		addDocuments("java eclipse", "eclipse liferay", "java liferay eclipse");

		MoreLikeThisQuery moreLikeThisQuery = queries.moreLikeThis(
			Collections.emptyList(), "java");

		assertSearch(moreLikeThisQuery, Collections.emptyList());
	}

	protected void addDocuments(String... values) throws Exception {
		addDocuments(
			value -> DocumentCreationHelpers.singleText(_FIELD_TITLE, value),
			values);
	}

	protected void assertSearch(
		com.liferay.portal.kernel.search.generic.MoreLikeThisQuery
			legacyMoreLikeThisQuery,
		List<String> expectedValues) {

		legacyMoreLikeThisQuery.setMinDocFrequency(Integer.valueOf(1));
		legacyMoreLikeThisQuery.setMinTermFrequency(Integer.valueOf(1));

		assertSearch(legacyMoreLikeThisQuery, null, expectedValues);
	}

	protected void assertSearch(
		com.liferay.portal.kernel.search.generic.MoreLikeThisQuery
			legacyMoreLikeThisQuery,
		MoreLikeThisQuery moreLikeThisQuery, List<String> expectedValues) {

		assertSearch(
			indexingTestHelper -> {
				SearchSearchRequest searchSearchRequest =
					createSearchSearchRequest();

				searchSearchRequest.setQuery(legacyMoreLikeThisQuery);
				searchSearchRequest.setQuery(moreLikeThisQuery);

				SearchEngineAdapter searchEngineAdapter =
					getSearchEngineAdapter();

				SearchSearchResponse searchSearchResponse =
					searchEngineAdapter.execute(searchSearchRequest);

				SearchHits searchHits = searchSearchResponse.getSearchHits();

				List<SearchHit> searchHitsList = searchHits.getSearchHits();

				List<String> actualValues = new ArrayList<>();

				searchHitsList.forEach(
					searchHit -> {
						Document document = searchHit.getDocument();

						String titleValue = document.getString(_FIELD_TITLE);

						if (titleValue != null) {
							actualValues.add(titleValue);
						}

						String descriptionValue = document.getString(
							_FIELD_DESCRIPTION);

						if (descriptionValue != null) {
							actualValues.add(descriptionValue);
						}
					});

				Assert.assertEquals(
					"Retrieved hits ->" + actualValues,
					expectedValues.toString(), actualValues.toString());

				Assert.assertEquals(
					"Total hits", expectedValues.size(),
					searchHits.getTotalHits());

				Assert.assertEquals(
					"Retrieved hits", expectedValues.size(),
					searchHitsList.size());
			});
	}

	protected void assertSearch(
		MoreLikeThisQuery moreLikeThisQuery, List<String> expectedValues) {

		moreLikeThisQuery.setMinDocFrequency(Integer.valueOf(1));
		moreLikeThisQuery.setMinTermFrequency(Integer.valueOf(1));

		assertSearch(null, moreLikeThisQuery, expectedValues);
	}

	protected SearchSearchRequest createSearchSearchRequest() {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(String.valueOf(getCompanyId()));
		searchSearchRequest.setSize(30);

		return searchSearchRequest;
	}

	protected void deleteDocumentById(String id) {
		SearchEngineAdapter searchEngineAdapter = getSearchEngineAdapter();

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			String.valueOf(getCompanyId()), id);

		deleteDocumentRequest.setType("_doc");

		searchEngineAdapter.execute(deleteDocumentRequest);
	}

	protected String indexDocumentWithNoFieldsExceptTitle(String title) {
		SearchEngineAdapter searchEngineAdapter = getSearchEngineAdapter();

		DocumentBuilder documentBuilder = new DocumentBuilderImpl();

		documentBuilder.setString(_FIELD_TITLE, title);

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			String.valueOf(getCompanyId()), documentBuilder.build());

		indexDocumentRequest.setType("_doc");

		IndexDocumentResponse indexDocumentResponse =
			searchEngineAdapter.execute(indexDocumentRequest);

		return indexDocumentResponse.getUid();
	}

	private static final String _FIELD_DESCRIPTION = "description";

	private static final String _FIELD_TITLE = "title";

}
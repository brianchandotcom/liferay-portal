/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.langchain4j.rag.content.retriever;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.highlight.FieldConfig;
import com.liferay.portal.search.highlight.FieldConfigBuilder;
import com.liferay.portal.search.highlight.FieldConfigBuilderFactory;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.highlight.HighlightBuilder;
import com.liferay.portal.search.highlight.HighlightBuilderFactory;
import com.liferay.portal.search.highlight.HighlightField;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.query.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Iliyan Peychev
 */
public class ElasticsearchContentRetrieverTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_searchEngineAdapter = Mockito.mock(SearchEngineAdapter.class);

		_elasticsearchContentRetriever = new ElasticsearchContentRetriever(
			_mockFieldConfigBuilderFactory(), _mockHighlightBuilderFactory(),
			new String[] {RandomTestUtil.randomString()}, _searchEngineAdapter,
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());
	}

	@Test
	public void testSearchFetchesURLSourceField() {
		try (MockedStatic<JSONUtil> jsonUtilMockedStatic = Mockito.mockStatic(
				JSONUtil.class)) {

			JSONObject jsonObject = Mockito.mock(JSONObject.class);

			Mockito.when(
				jsonObject.toString()
			).thenReturn(
				"{}"
			);

			jsonUtilMockedStatic.when(
				() -> JSONUtil.put(Mockito.anyString(), (Object)Mockito.any())
			).thenReturn(
				jsonObject
			);

			Content content = _retrieveSingleContent(
				"https://learn.liferay.com/w/dxp/low-code/objects",
				"Liferay Objects let you build no-code applications.");

			// The retrieved URL must reach the chunk metadata so the content
			// injector can append it at the end of the text segment for the
			// model to cite (LPD-93999).

			Assert.assertEquals(
				"https://learn.liferay.com/w/dxp/low-code/objects",
				content.textSegment(
				).metadata(
				).getString(
					"url"
				));

			// The request must enable source fetching and restrict it to the
			// "url" field. Reading "url" from the sources map while source
			// fetching is disabled silently drops the URL.

			SearchSearchRequest searchSearchRequest =
				_captureSearchSearchRequest();

			Assert.assertEquals(
				Boolean.TRUE, searchSearchRequest.getFetchSource());
			Assert.assertEquals(
				Arrays.toString(new String[] {"url"}),
				Arrays.toString(searchSearchRequest.getFetchSourceIncludes()));
		}
	}

	private SearchSearchRequest _captureSearchSearchRequest() {
		ArgumentCaptor<SearchSearchRequest> argumentCaptor =
			ArgumentCaptor.forClass(SearchSearchRequest.class);

		Mockito.verify(
			_searchEngineAdapter
		).execute(
			argumentCaptor.capture()
		);

		return argumentCaptor.getValue();
	}

	private FieldConfigBuilderFactory _mockFieldConfigBuilderFactory() {
		FieldConfigBuilderFactory fieldConfigBuilderFactory = Mockito.mock(
			FieldConfigBuilderFactory.class);

		FieldConfigBuilder fieldConfigBuilder = Mockito.mock(
			FieldConfigBuilder.class);

		Mockito.when(
			fieldConfigBuilderFactory.builder(Mockito.anyString())
		).thenReturn(
			fieldConfigBuilder
		);

		Mockito.when(
			fieldConfigBuilder.build()
		).thenReturn(
			Mockito.mock(FieldConfig.class)
		);

		return fieldConfigBuilderFactory;
	}

	private HighlightBuilderFactory _mockHighlightBuilderFactory() {
		HighlightBuilderFactory highlightBuilderFactory = Mockito.mock(
			HighlightBuilderFactory.class);

		HighlightBuilder highlightBuilder = Mockito.mock(
			HighlightBuilder.class);

		Mockito.when(
			highlightBuilderFactory.builder()
		).thenReturn(
			highlightBuilder
		);

		Mockito.when(
			highlightBuilder.addFieldConfig(Mockito.any())
		).thenReturn(
			highlightBuilder
		);

		Mockito.when(
			highlightBuilder.build()
		).thenReturn(
			Mockito.mock(Highlight.class)
		);

		return highlightBuilderFactory;
	}

	private Content _retrieveSingleContent(String url, String fragment) {
		HighlightField highlightField = Mockito.mock(HighlightField.class);

		Mockito.when(
			highlightField.getFragments()
		).thenReturn(
			List.of(fragment)
		);

		SearchHit searchHit = Mockito.mock(SearchHit.class);

		Mockito.when(
			searchHit.getHighlightFieldsMap()
		).thenReturn(
			Map.of("text_embedding", highlightField)
		);

		Mockito.when(
			searchHit.getSourcesMap()
		).thenReturn(
			Map.of("url", url)
		);

		SearchHits searchHits = Mockito.mock(SearchHits.class);

		Mockito.when(
			searchHits.getSearchHits()
		).thenReturn(
			List.of(searchHit)
		);

		SearchSearchResponse searchSearchResponse = Mockito.mock(
			SearchSearchResponse.class);

		Mockito.when(
			searchSearchResponse.getSearchHits()
		).thenReturn(
			searchHits
		);

		Mockito.doReturn(
			searchSearchResponse
		).when(
			_searchEngineAdapter
		).execute(
			Mockito.any(SearchSearchRequest.class)
		);

		Query query = Mockito.mock(Query.class);

		Mockito.when(
			query.text()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		List<Content> contents = _elasticsearchContentRetriever.search(query);

		Assert.assertEquals(contents.toString(), 1, contents.size());

		return contents.get(0);
	}

	private ElasticsearchContentRetriever _elasticsearchContentRetriever;
	private SearchEngineAdapter _searchEngineAdapter;

}
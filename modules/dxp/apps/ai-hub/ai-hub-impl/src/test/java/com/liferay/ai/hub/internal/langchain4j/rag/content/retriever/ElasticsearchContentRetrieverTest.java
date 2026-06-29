/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.langchain4j.rag.content.retriever;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.highlight.FieldConfigBuilder;
import com.liferay.portal.search.highlight.FieldConfigBuilderFactory;
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
		_setUpFieldConfigBuilderFactory();
		_setUpHighlightBuilderFactory();
		_setUpSearchEngineAdapter();
	}

	@Test
	public void testSearch() {
		ElasticsearchContentRetriever elasticsearchContentRetriever =
			new ElasticsearchContentRetriever(
				_fieldConfigBuilderFactory, _highlightBuilderFactory,
				new String[] {RandomTestUtil.randomString()},
				_searchEngineAdapter, RandomTestUtil.randomLong(),
				RandomTestUtil.randomLong());

		List<Content> contents = elasticsearchContentRetriever.search(
			Mockito.mock(Query.class));

		Assert.assertEquals(contents.toString(), 1, contents.size());

		Content content = contents.get(0);

		Assert.assertEquals(
			_URL,
			content.textSegment(
			).metadata(
			).getString(
				"url"
			));

		ArgumentCaptor<SearchSearchRequest> argumentCaptor =
			ArgumentCaptor.forClass(SearchSearchRequest.class);

		Mockito.verify(
			_searchEngineAdapter
		).execute(
			argumentCaptor.capture()
		);

		SearchSearchRequest searchSearchRequest = argumentCaptor.getValue();

		Assert.assertEquals(Boolean.TRUE, searchSearchRequest.getFetchSource());
		Assert.assertEquals(
			Arrays.toString(new String[] {"url"}),
			Arrays.toString(searchSearchRequest.getFetchSourceIncludes()));
	}

	private void _setUpFieldConfigBuilderFactory() {
		Mockito.when(
			_fieldConfigBuilderFactory.builder(Mockito.anyString())
		).thenReturn(
			Mockito.mock(FieldConfigBuilder.class)
		);
	}

	private void _setUpHighlightBuilderFactory() {
		HighlightBuilder highlightBuilder = Mockito.mock(
			HighlightBuilder.class);

		Mockito.when(
			_highlightBuilderFactory.builder()
		).thenReturn(
			highlightBuilder
		);

		Mockito.when(
			highlightBuilder.addFieldConfig(Mockito.any())
		).thenReturn(
			highlightBuilder
		);
	}

	private void _setUpSearchEngineAdapter() {
		HighlightField highlightField = Mockito.mock(HighlightField.class);

		Mockito.when(
			highlightField.getFragments()
		).thenReturn(
			List.of(RandomTestUtil.randomString())
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
			Map.of("url", _URL)
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
	}

	private static final String _URL = RandomTestUtil.randomString();

	private final FieldConfigBuilderFactory _fieldConfigBuilderFactory =
		Mockito.mock(FieldConfigBuilderFactory.class);
	private final HighlightBuilderFactory _highlightBuilderFactory =
		Mockito.mock(HighlightBuilderFactory.class);
	private final SearchEngineAdapter _searchEngineAdapter = Mockito.mock(
		SearchEngineAdapter.class);

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.langchain4j.rag.content.retriever;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.highlight.FieldConfigBuilderFactory;
import com.liferay.portal.search.highlight.HighlightBuilderFactory;
import com.liferay.portal.search.highlight.HighlightField;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.query.Query;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Iliyan Peychev
 */
public class ElasticsearchContentRetrieverTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSearchSkipsHitsScoringBelowMinScore() {
		String fragment = RandomTestUtil.randomString();

		HighlightField highlightField = Mockito.mock(HighlightField.class);

		Mockito.when(
			highlightField.getFragments()
		).thenReturn(
			List.of(fragment)
		);

		SearchHit highScoreSearchHit = Mockito.mock(SearchHit.class);

		Mockito.when(
			highScoreSearchHit.getHighlightFieldsMap()
		).thenReturn(
			Map.of("text_embedding", highlightField)
		);

		Mockito.when(
			highScoreSearchHit.getScore()
		).thenReturn(
			0.9F
		);

		SearchHit lowScoreSearchHit = Mockito.mock(SearchHit.class);

		Mockito.when(
			lowScoreSearchHit.getScore()
		).thenReturn(
			0.5F
		);

		SearchHits searchHits = Mockito.mock(SearchHits.class);

		Mockito.when(
			searchHits.getSearchHits()
		).thenReturn(
			List.of(lowScoreSearchHit, highScoreSearchHit)
		);

		SearchSearchResponse searchSearchResponse = Mockito.mock(
			SearchSearchResponse.class);

		Mockito.when(
			searchSearchResponse.getSearchHits()
		).thenReturn(
			searchHits
		);

		SearchEngineAdapter searchEngineAdapter = Mockito.mock(
			SearchEngineAdapter.class);

		Mockito.when(
			searchEngineAdapter.execute((SearchSearchRequest)Mockito.any())
		).thenReturn(
			searchSearchResponse
		);

		Query query = Mockito.mock(Query.class);

		Mockito.when(
			query.text()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		ElasticsearchContentRetriever elasticsearchContentRetriever =
			new ElasticsearchContentRetriever(
				Mockito.mock(
					FieldConfigBuilderFactory.class,
					Mockito.RETURNS_DEEP_STUBS),
				Mockito.mock(
					HighlightBuilderFactory.class, Mockito.RETURNS_DEEP_STUBS),
				new String[] {RandomTestUtil.randomString()},
				searchEngineAdapter, RandomTestUtil.randomLong(),
				RandomTestUtil.randomLong());

		List<Content> contents = elasticsearchContentRetriever.search(query);

		Assert.assertEquals(contents.toString(), 1, contents.size());

		Content content = contents.get(0);

		TextSegment textSegment = content.textSegment();

		Assert.assertEquals(fragment, textSegment.text());
	}

}
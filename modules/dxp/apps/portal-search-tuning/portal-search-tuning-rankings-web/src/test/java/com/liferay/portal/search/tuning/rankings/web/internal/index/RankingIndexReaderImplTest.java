/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingIndexReaderImplTest extends BaseRankingsIndexTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_rankingIndexReaderImpl = new RankingIndexReaderImpl();

		ReflectionTestUtil.setFieldValue(
			_rankingIndexReaderImpl, "_documentToRankingTranslator",
			_documentToRankingTranslator);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexReaderImpl, "_queries", queries);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexReaderImpl, "_searchEngineAdapter",
			searchEngineAdapter);
	}

	@Test
	public void testFetch() {
		setUpSearchEngineAdapter(
			setUpGetDocumentResponseGetDocument(
				setUpDocument(Arrays.asList("queryStrings")),
				setUpGetDocumentResponse()));

		Assert.assertEquals(
			_setUpDocumentToRankingTranslator(),
			_rankingIndexReaderImpl.fetch(
				"id", Mockito.mock(RankingIndexName.class)));
	}

	@Test
	public void testFetchBlankQueryString() {
		Assert.assertNull(
			_rankingIndexReaderImpl.fetch(
				null, StringPool.BLANK, Mockito.mock(RankingIndexName.class),
				null));
	}

	@Test
	public void testFetchQueryString() {
		setUpQueries();
		setUpSearchEngineAdapter(
			setUpGetDocumentResponseGetDocument(
				setUpDocument(Arrays.asList("queryStrings")),
				setUpGetDocumentResponse()));
		setUpSearchEngineAdapter(
			setUpSearchHits(Arrays.asList("queryStrings")));

		Ranking ranking = _setUpDocumentToRankingTranslator();

		List<Ranking> rankings = _rankingIndexReaderImpl.fetch(
			null, "queryString", Mockito.mock(RankingIndexName.class), null);

		Assert.assertEquals(ranking, rankings.get(0));
	}

	@Test
	public void testIsExistsFalse() {
		setUpSearchEngineAdapter(false);

		Assert.assertFalse(
			_rankingIndexReaderImpl.isExists(
				Mockito.mock(RankingIndexName.class)));
	}

	@Test
	public void testIsExistsTrue() {
		setUpSearchEngineAdapter(true);

		Assert.assertTrue(
			_rankingIndexReaderImpl.isExists(
				Mockito.mock(RankingIndexName.class)));
	}

	@Override
	protected SearchSearchResponse setUpSearchSearchResponse() {
		SearchSearchResponse searchSearchResponse = Mockito.mock(
			SearchSearchResponse.class);

		Mockito.doReturn(
			1L
		).when(
			searchSearchResponse
		).getCount();

		return searchSearchResponse;
	}

	private Ranking _setUpDocumentToRankingTranslator() {
		Ranking ranking = Mockito.mock(Ranking.class);

		Mockito.doReturn(
			ranking
		).when(
			_documentToRankingTranslator
		).translate(
			Mockito.any(), Mockito.nullable(String.class)
		);

		return ranking;
	}

	private final DocumentToRankingTranslator _documentToRankingTranslator =
		Mockito.mock(DocumentToRankingTranslator.class);
	private RankingIndexReaderImpl _rankingIndexReaderImpl;

}
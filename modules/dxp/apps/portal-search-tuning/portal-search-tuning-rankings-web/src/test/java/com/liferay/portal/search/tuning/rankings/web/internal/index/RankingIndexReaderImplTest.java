/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Optional;

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

		_rankingIndexReaderImpl.setQueries(queries);
		_rankingIndexReaderImpl.setSearchEngineAdapter(searchEngineAdapter);
	}

	@Test
	public void testFetchByQueryStringOptional() {
		_setUpDocumentToRankingTranslator();

		setUpQueries();
		setUpSearchEngineAdapter(
			setUpGetDocumentResponseGetDocument(
				setUpDocument(Arrays.asList("queryStrings")),
				setUpGetDocumentResponse()));
		setUpSearchEngineAdapter(
			setUpSearchHits(Arrays.asList("queryStrings")));

		Assert.assertEquals(
			Optional.of(_setUpDocumentToRankingTranslator()),
			_rankingIndexReaderImpl.fetchByQueryStringOptional(
				Mockito.mock(RankingIndexName.class), "queryString"));
	}

	@Test
	public void testFetchByQueryStringOptionalBlankQueryString() {
		Assert.assertEquals(
			Optional.empty(),
			_rankingIndexReaderImpl.fetchByQueryStringOptional(
				Mockito.mock(RankingIndexName.class), ""));
	}

	@Test
	public void testFetchOptional() {
		setUpSearchEngineAdapter(
			setUpGetDocumentResponseGetDocument(
				setUpDocument(Arrays.asList("queryStrings")),
				setUpGetDocumentResponse()));

		Assert.assertEquals(
			Optional.of(_setUpDocumentToRankingTranslator()),
			_rankingIndexReaderImpl.fetchOptional(
				Mockito.mock(RankingIndexName.class), "id"));
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
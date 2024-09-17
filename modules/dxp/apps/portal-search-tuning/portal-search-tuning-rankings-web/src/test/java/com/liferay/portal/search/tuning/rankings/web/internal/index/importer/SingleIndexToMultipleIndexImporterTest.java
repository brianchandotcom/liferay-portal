/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.importer;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.rankings.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.BaseRankingsWebTestCase;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreatorUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Wade Cao, Joshua Cords
 */
public class SingleIndexToMultipleIndexImporterTest
	extends BaseRankingsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_singleIndexToMultipleIndexImporterImpl =
			new SingleIndexToMultipleIndexImporter(
				_indexNameBuilder, queries, _rankingIndexReader,
				searchEngineAdapter);
	}

	@After
	public void tearDown() {
		_rankingIndexCreatorUtilMockedStatic.close();
	}

	@Test
	public void testImportRankings() throws Exception {
		long companyId = RandomTestUtil.randomLong();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			_indexNameBuilder
		).getIndexName(
			companyId
		);

		setUpSearchEngineAdapter(_setupSearchHits());

		_singleIndexToMultipleIndexImporterImpl.importRankings(companyId);

		Mockito.verify(
			searchEngineAdapter, Mockito.times(1)
		).execute(
			(BulkDocumentRequest)Mockito.any()
		);

		_rankingIndexCreatorUtilMockedStatic.verify(
			() -> RankingIndexCreatorUtil.deleteIfExists(
				Mockito.any(), Mockito.any()),
			Mockito.times(1));
	}

	@Test
	public void testNeedImport() throws Exception {
		Mockito.doReturn(
			true
		).when(
			_rankingIndexReader
		).isExists(
			Mockito.any()
		);

		Assert.assertTrue(_singleIndexToMultipleIndexImporterImpl.needImport());
	}

	@Override
	protected SearchHits setUpSearchEngineAdapter(SearchHits searchHits) {
		SearchSearchResponse searchSearchResponse = setUpSearchSearchResponse();

		Mockito.doReturn(
			searchHits
		).when(
			searchSearchResponse
		).getSearchHits();

		CountSearchResponse countSearchResponse = Mockito.mock(
			CountSearchResponse.class);

		Mockito.doReturn(
			0L
		).when(
			countSearchResponse
		).getCount();

		Mockito.doReturn(
			searchSearchResponse, countSearchResponse
		).when(
			searchEngineAdapter
		).execute(
			(SearchSearchRequest)Mockito.any()
		);

		return searchHits;
	}

	private SearchHits _setupSearchHits() {
		SearchHit searchHit = Mockito.mock(SearchHit.class);

		Document document = Mockito.mock(Document.class);

		Mockito.doReturn(
			"myIndex"
		).when(
			document
		).getString(
			Mockito.anyString()
		);

		Mockito.doReturn(
			document
		).when(
			searchHit
		).getDocument();

		SearchHits searchHits = Mockito.mock(SearchHits.class);

		Mockito.doReturn(
			Arrays.asList(searchHit)
		).when(
			searchHits
		).getSearchHits();

		return searchHits;
	}

	private final IndexNameBuilder _indexNameBuilder = Mockito.mock(
		IndexNameBuilder.class);
	private final MockedStatic<RankingIndexCreatorUtil>
		_rankingIndexCreatorUtilMockedStatic = Mockito.mockStatic(
			RankingIndexCreatorUtil.class);
	private final RankingIndexReader _rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);
	private SingleIndexToMultipleIndexImporter
		_singleIndexToMultipleIndexImporterImpl;

}
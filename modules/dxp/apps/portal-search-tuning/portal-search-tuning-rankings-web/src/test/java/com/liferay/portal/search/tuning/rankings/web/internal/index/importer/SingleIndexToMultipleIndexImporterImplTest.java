/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.importer;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DocumentRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.BaseRankingsWebTestCase;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao, Joshua Cords
 */
public class SingleIndexToMultipleIndexImporterImplTest
	extends BaseRankingsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_singleIndexToMultipleIndexImporterImpl =
			new SingleIndexToMultipleIndexImporterImpl();

		ReflectionTestUtil.setFieldValue(
			_singleIndexToMultipleIndexImporterImpl, "_indexNameBuilder",
			_indexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_singleIndexToMultipleIndexImporterImpl, "_queries", queries);
		ReflectionTestUtil.setFieldValue(
			_singleIndexToMultipleIndexImporterImpl, "_rankingIndexCreator",
			_rankingIndexCreator);
		ReflectionTestUtil.setFieldValue(
			_singleIndexToMultipleIndexImporterImpl, "_rankingIndexReader",
			_rankingIndexReader);
		ReflectionTestUtil.setFieldValue(
			_singleIndexToMultipleIndexImporterImpl, "_searchEngineAdapter",
			searchEngineAdapter);
	}

	@Test
	public void testImportRankings() throws Exception {
		Company company = Mockito.mock(Company.class);

		long companyId = RandomTestUtil.randomLong();

		Mockito.doReturn(
			companyId
		).when(
			company
		).getCompanyId();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			_indexNameBuilder
		).getIndexName(
			Mockito.anyLong()
		);

		Mockito.doNothing(
		).when(
			_rankingIndexCreator
		).create(
			Mockito.any()
		);

		Mockito.doReturn(
			true
		).when(
			_rankingIndexReader
		).isExists(
			Mockito.any()
		);

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

		setUpSearchEngineAdapter(searchHits);

		BulkDocumentResponse bulkDocumentResponse = Mockito.mock(
			BulkDocumentResponse.class);

		Mockito.doReturn(
			false
		).when(
			bulkDocumentResponse
		).hasErrors();

		Mockito.doReturn(
			bulkDocumentResponse
		).when(
			searchEngineAdapter
		).execute(
			(BulkDocumentRequest)Mockito.any()
		);

		_singleIndexToMultipleIndexImporterImpl.importRankings(companyId);

		Mockito.verify(
			searchEngineAdapter, Mockito.times(1)
		).execute(
			(BulkDocumentRequest)Mockito.any()
		);
		Mockito.verify(
			_rankingIndexCreator, Mockito.times(1)
		).delete(
			Mockito.any()
		);
	}

	@Override
	protected SearchHits setUpSearchEngineAdapter(SearchHits searchHits) {
		CountSearchResponse countSearchResponse = Mockito.mock(
			CountSearchResponse.class);

		Mockito.doReturn(
			0L
		).when(
			countSearchResponse
		).getCount();

		Mockito.doReturn(
			3L
		).when(
			searchHits
		).getTotalHits();

		SearchSearchResponse searchSearchResponse = setUpSearchSearchResponse();

		Mockito.doReturn(
			searchHits
		).when(
			searchSearchResponse
		).getSearchHits();

		Mockito.doReturn(
			searchSearchResponse, countSearchResponse
		).when(
			searchEngineAdapter
		).execute(
			(SearchSearchRequest)Mockito.any()
		);

		return searchHits;
	}

	@Test
	public void testNeedImport() throws Exception {
		Company company = Mockito.mock(Company.class);

		Mockito.doReturn(
			111L
		).when(
			company
		).getCompanyId();

		Mockito.doReturn(
			true
		).when(
			_rankingIndexReader
		).isExists(
			Mockito.any()
		);

		Assert.assertTrue(_singleIndexToMultipleIndexImporterImpl.needImport());
	}

	private final IndexNameBuilder _indexNameBuilder = Mockito.mock(
		IndexNameBuilder.class);
	private final RankingIndexCreator _rankingIndexCreator = Mockito.mock(
		RankingIndexCreator.class);
	private final RankingIndexReader _rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);
	private SingleIndexToMultipleIndexImporterImpl
		_singleIndexToMultipleIndexImporterImpl;

}
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

package com.liferay.portal.search.tuning.rankings.web.internal.index.importer;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
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
 * @author Wade Cao
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
			_singleIndexToMultipleIndexImporterImpl, "_companyService",
			_companyService);
		ReflectionTestUtil.setFieldValue(
			_singleIndexToMultipleIndexImporterImpl, "_queries", queries);
		ReflectionTestUtil.setFieldValue(
			_singleIndexToMultipleIndexImporterImpl, "_rankingIndexCreator",
			_rankingIndexCreator);
		ReflectionTestUtil.setFieldValue(
			_singleIndexToMultipleIndexImporterImpl, "_rankingIndexNameBuilder",
			rankingIndexNameBuilder);
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

		Mockito.doReturn(
			111L
		).when(
			company
		).getCompanyId();

		Mockito.doReturn(
			Arrays.asList(company)
		).when(
			_companyService
		).getCompanies();

		setUpRankingIndexNameBuilder();

		Mockito.doReturn(
			true
		).when(
			_rankingIndexReader
		).isExists(
			Mockito.any()
		);

		Mockito.doNothing(
		).when(
			_rankingIndexCreator
		).create(
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

		_singleIndexToMultipleIndexImporterImpl.importRankings();

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

	@Test
	public void testNeedImport() throws Exception {
		Company company = Mockito.mock(Company.class);

		Mockito.doReturn(
			111L
		).when(
			company
		).getCompanyId();

		Mockito.doReturn(
			Arrays.asList(company)
		).when(
			_companyService
		).getCompanies();

		setUpRankingIndexNameBuilder();

		Mockito.doReturn(
			true
		).when(
			_rankingIndexReader
		).isExists(
			Mockito.any()
		);

		Assert.assertTrue(_singleIndexToMultipleIndexImporterImpl.needImport());
	}

	private final CompanyService _companyService = Mockito.mock(
		CompanyService.class);
	private final RankingIndexCreator _rankingIndexCreator = Mockito.mock(
		RankingIndexCreator.class);
	private final RankingIndexReader _rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);
	private SingleIndexToMultipleIndexImporterImpl
		_singleIndexToMultipleIndexImporterImpl;

}
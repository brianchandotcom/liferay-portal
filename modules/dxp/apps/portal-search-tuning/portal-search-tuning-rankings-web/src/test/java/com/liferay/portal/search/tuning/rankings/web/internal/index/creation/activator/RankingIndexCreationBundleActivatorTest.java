/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.activator;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.importer.SingleIndexToMultipleIndexImporter;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingIndexCreationBundleActivatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_rankingIndexCreationBundleActivator =
			new RankingIndexCreationBundleActivator();

		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreationBundleActivator, "_rankingIndexCreator",
			_rankingIndexCreator);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreationBundleActivator, "_companyLocalService",
			_companyLocalService);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreationBundleActivator, "_rankingIndexReader",
			_rankingIndexReader);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreationBundleActivator, "_rankingIndexNameBuilder",
			_rankingIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreationBundleActivator, "_searchEngineInformation",
			_searchEngineInformation);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreationBundleActivator,
			"_singleIndexToMultipleIndexImporter",
			_singleIndexToMultipleIndexImporter);

		Mockito.doReturn(
			new RankingIndexName() {

				@Override
				public String getIndexName() {
					return null;
				}

			}
		).when(
			_rankingIndexNameBuilder
		).getRankingIndexName(
			Mockito.anyLong()
		);
	}

	@Test
	public void testActivatorSingleIndexToMultipleIndexImporterFalse()
		throws Exception {

		_setUpSingleIndexToMultipleIndexImporter(false);

		_rankingIndexCreationBundleActivator.activate();

		Mockito.verify(
			_singleIndexToMultipleIndexImporter, Mockito.times(1)
		).needImport();

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(0)
		).create(
			Mockito.any(RankingIndexName.class)
		);
	}

	@Test
	public void testActivatorSingleIndexToMultipleIndexImporterTrue()
		throws Exception {

		_setUpSingleIndexToMultipleIndexImporter(true);
		_setUpCompanyLocalService(1);

		_rankingIndexCreationBundleActivator.activate();

		Mockito.verify(
			_singleIndexToMultipleIndexImporter, Mockito.times(1)
		).needImport();

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(1)
		).create(
			Mockito.any(RankingIndexName.class)
		);
	}

	private void _setUpCompanyLocalService(int numberOfCompanies) {
		List<Company> companies = new ArrayList<>();
		Company company;

		for (int i = 0; i < numberOfCompanies; i++) {
			company = Mockito.mock(Company.class);

			Mockito.doReturn(
				RandomTestUtil.randomLong()
			).when(
				company
			).getCompanyId();
			companies.add(company);
		}

		Mockito.doReturn(
			companies
		).when(
			_companyLocalService
		).getCompanies();
	}

	private void _setUpSingleIndexToMultipleIndexImporter(boolean exist) {
		Mockito.doReturn(
			exist
		).when(
			_singleIndexToMultipleIndexImporter
		).needImport();
	}

	private final CompanyLocalService _companyLocalService = Mockito.mock(
		CompanyLocalService.class);
	private RankingIndexCreationBundleActivator
		_rankingIndexCreationBundleActivator;
	private final RankingIndexCreator _rankingIndexCreator = Mockito.mock(
		RankingIndexCreator.class);
	private final RankingIndexNameBuilder _rankingIndexNameBuilder =
		Mockito.mock(RankingIndexNameBuilder.class);
	private final RankingIndexReader _rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);
	private final SearchEngineInformation _searchEngineInformation =
		Mockito.mock(SearchEngineInformation.class);
	private final SingleIndexToMultipleIndexImporter
		_singleIndexToMultipleIndexImporter = Mockito.mock(
			SingleIndexToMultipleIndexImporter.class);

}
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.instance.lifecycle;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.tuning.rankings.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexNameBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreatorUtil;
import com.liferay.portal.search.tuning.rankings.web.internal.index.importer.SingleIndexToMultipleIndexImporter;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Wade Cao, Joshua Cords
 */
public class RankingIndexPortalInstanceLifecycleListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_rankingIndexPortalInstanceLifecycleListener =
			new RankingIndexPortalInstanceLifecycleListener();

		ReflectionTestUtil.setFieldValue(
			_rankingIndexPortalInstanceLifecycleListener,
			"_searchEngineAdapter", _searchEngineAdapter);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexPortalInstanceLifecycleListener,
			"_rankingIndexNameBuilder", _rankingIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexPortalInstanceLifecycleListener, "_rankingIndexReader",
			_rankingIndexReader);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexPortalInstanceLifecycleListener, "_searchCapabilities",
			_searchCapabilities);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexPortalInstanceLifecycleListener,
			"_singleIndexToMultipleIndexImporter",
			_singleIndexToMultipleIndexImporter);

		_setUpCompany();
		_setUpRankingIndexNameBuilder();
		_setUpSearchCapabilities();
	}

	@After
	public void tearDown() {
		_rankingIndexCreatorUtilMockedStatic.close();
	}

	@Test
	public void testSingleIndexExistsMultipleIndexExists() throws Exception {
		_setUpRankingIndexReader(true);
		_setUpSingleIndexToMultipleIndexImporter(true);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceRegistered(
			_company);

		_rankingIndexCreatorUtilMockedStatic.verify(
			() -> RankingIndexCreatorUtil.create(Mockito.any(), Mockito.any()),
			Mockito.times(0));

		Mockito.verify(
			_singleIndexToMultipleIndexImporter, Mockito.times(0)
		).importRankings(
			Mockito.anyLong()
		);
	}

	@Test
	public void testSingleIndexExistsMultipleIndexNotExists() throws Exception {
		_setUpRankingIndexReader(false);
		_setUpSingleIndexToMultipleIndexImporter(true);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceRegistered(
			_company);

		_rankingIndexCreatorUtilMockedStatic.verify(
			() -> RankingIndexCreatorUtil.create(Mockito.any(), Mockito.any()),
			Mockito.times(1));

		Mockito.verify(
			_singleIndexToMultipleIndexImporter, Mockito.times(1)
		).importRankings(
			Mockito.anyLong()
		);
	}

	@Test
	public void testSingleIndexNotExistsMultipleIndexExists() throws Exception {
		_setUpRankingIndexReader(true);
		_setUpSingleIndexToMultipleIndexImporter(false);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceRegistered(
			_company);

		_rankingIndexCreatorUtilMockedStatic.verify(
			() -> RankingIndexCreatorUtil.create(Mockito.any(), Mockito.any()),
			Mockito.times(0));

		Mockito.verify(
			_singleIndexToMultipleIndexImporter, Mockito.times(0)
		).importRankings(
			Mockito.anyLong()
		);
	}

	@Test
	public void testSingleIndexNotExistsMultipleIndexNotExists()
		throws Exception {

		_setUpRankingIndexReader(false);
		_setUpSingleIndexToMultipleIndexImporter(false);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceRegistered(
			_company);

		_rankingIndexCreatorUtilMockedStatic.verify(
			() -> RankingIndexCreatorUtil.create(Mockito.any(), Mockito.any()),
			Mockito.times(1));

		Mockito.verify(
			_singleIndexToMultipleIndexImporter, Mockito.times(0)
		).importRankings(
			Mockito.anyLong()
		);
	}

	private void _setUpCompany() {
		Mockito.doReturn(
			1L
		).when(
			_company
		).getCompanyId();
	}

	private void _setUpRankingIndexNameBuilder() {
		RankingIndexName rankingIndexName = Mockito.mock(
			RankingIndexName.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			rankingIndexName
		).getIndexName();

		Mockito.doReturn(
			Mockito.mock(RankingIndexName.class)
		).when(
			_rankingIndexNameBuilder
		).getRankingIndexName(
			Mockito.anyLong()
		);
	}

	private void _setUpRankingIndexReader(boolean exists) {
		Mockito.doReturn(
			exists
		).when(
			_rankingIndexReader
		).isExists(
			Mockito.any()
		);
	}

	private void _setUpSearchCapabilities() {
		Mockito.doReturn(
			true
		).when(
			_searchCapabilities
		).isResultRankingsSupported();
	}

	private void _setUpSingleIndexToMultipleIndexImporter(boolean needsImport) {
		Mockito.doReturn(
			needsImport
		).when(
			_singleIndexToMultipleIndexImporter
		).needImport();
	}

	private final Company _company = Mockito.mock(Company.class);
	private final MockedStatic<RankingIndexCreatorUtil>
		_rankingIndexCreatorUtilMockedStatic = Mockito.mockStatic(
			RankingIndexCreatorUtil.class);
	private final RankingIndexNameBuilder _rankingIndexNameBuilder =
		Mockito.mock(RankingIndexNameBuilder.class);
	private RankingIndexPortalInstanceLifecycleListener
		_rankingIndexPortalInstanceLifecycleListener;
	private final RankingIndexReader _rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);
	private final SearchCapabilities _searchCapabilities = Mockito.mock(
		SearchCapabilities.class);
	private final SearchEngineAdapter _searchEngineAdapter = Mockito.mock(
		SearchEngineAdapter.class);
	private final SingleIndexToMultipleIndexImporter
		_singleIndexToMultipleIndexImporter = Mockito.mock(
			SingleIndexToMultipleIndexImporter.class);

}
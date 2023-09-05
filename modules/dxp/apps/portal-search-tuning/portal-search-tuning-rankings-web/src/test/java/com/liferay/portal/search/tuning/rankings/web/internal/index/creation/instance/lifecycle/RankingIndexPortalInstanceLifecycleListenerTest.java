/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.instance.lifecycle;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.importer.SingleIndexToMultipleIndexImporter;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingIndexPortalInstanceLifecycleListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.doReturn(
			true
		).when(
			_searchCapabilities
		).isResultRankingsSupported();

		_rankingIndexPortalInstanceLifecycleListener =
			new RankingIndexPortalInstanceLifecycleListener();

		ReflectionTestUtil.setFieldValue(
			_rankingIndexPortalInstanceLifecycleListener,
			"_rankingIndexCreator", _rankingIndexCreator);
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
	}

	@Test
	public void testActivatorSingleIndexToMultipleIndexImporterTrue()
		throws Exception {

		_setUpSingleIndexToMultipleIndexImporter(true);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceRegistered(
			Mockito.mock(Company.class));

		Mockito.verify(
			_singleIndexToMultipleIndexImporter, Mockito.times(1)
		).importRankings(
			Mockito.anyLong()
		);
	}

	@Test
	public void testPortalInstanceRegisteredExistFalse() throws Exception {
		_setUpRankingIndexReader(false);
		_setUpSingleIndexToMultipleIndexImporter(false);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceRegistered(
			Mockito.mock(Company.class));

		Mockito.verify(
			_rankingIndexReader, Mockito.times(1)
		).isExists(
			Mockito.any()
		);

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(1)
		).create(
			Mockito.any()
		);
	}

	@Test
	public void testPortalInstanceRegisteredExistTrue() throws Exception {
		_setUpRankingIndexReader(true);
		_setUpSingleIndexToMultipleIndexImporter(false);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceRegistered(
			Mockito.mock(Company.class));

		Mockito.verify(
			_rankingIndexReader, Mockito.times(1)
		).isExists(
			Mockito.any()
		);

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(0)
		).create(
			Mockito.any()
		);
	}

	@Test
	public void testPortalInstanceUnregisteredExistsFalse() throws Exception {
		_setUpRankingIndexReader(false);
		_setUpSingleIndexToMultipleIndexImporter(false);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceUnregistered(
			Mockito.mock(Company.class));

		Mockito.verify(
			_rankingIndexReader, Mockito.times(1)
		).isExists(
			Mockito.any()
		);

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(0)
		).delete(
			Mockito.any()
		);
	}

	@Test
	public void testPortalInstanceUnregisteredExistsTrue() throws Exception {
		_setUpRankingIndexReader(true);
		_setUpSingleIndexToMultipleIndexImporter(false);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceUnregistered(
			Mockito.mock(Company.class));

		Mockito.verify(
			_rankingIndexReader, Mockito.times(1)
		).isExists(
			Mockito.any()
		);

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(1)
		).delete(
			Mockito.any()
		);
	}

	private void _setUpRankingIndexReader(boolean exist) {
		Mockito.doReturn(
			exist
		).when(
			_rankingIndexReader
		).isExists(
			Mockito.any()
		);
	}

	private void _setUpSingleIndexToMultipleIndexImporter(boolean exist) {
		Mockito.doReturn(
			exist
		).when(
			_singleIndexToMultipleIndexImporter
		).needImport();
	}

	private final RankingIndexCreator _rankingIndexCreator = Mockito.mock(
		RankingIndexCreator.class);
	private final RankingIndexNameBuilder _rankingIndexNameBuilder =
		Mockito.mock(RankingIndexNameBuilder.class);
	private RankingIndexPortalInstanceLifecycleListener
		_rankingIndexPortalInstanceLifecycleListener;
	private final RankingIndexReader _rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);
	private final SearchCapabilities _searchCapabilities = Mockito.mock(
		SearchCapabilities.class);
	private final SingleIndexToMultipleIndexImporter
		_singleIndexToMultipleIndexImporter = Mockito.mock(
			SingleIndexToMultipleIndexImporter.class);

}
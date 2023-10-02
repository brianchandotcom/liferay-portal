/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.instance.lifecycle;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.importer.SingleIndexToMultipleIndexImporter;
import com.liferay.portal.search.tuning.rankings.web.internal.index.lifecycle.RankingIndexLifecycleManager;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao, Joshua Cords
 */
public class RankingIndexLifecycleManagerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_rankingIndexLifecycleManager = new RankingIndexLifecycleManager();

		ReflectionTestUtil.setFieldValue(
			_rankingIndexLifecycleManager, "_rankingIndexCreator",
			_rankingIndexCreator);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexLifecycleManager, "_rankingIndexNameBuilder",
			_rankingIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexLifecycleManager, "_rankingIndexReader",
			_rankingIndexReader);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexLifecycleManager,
			"_singleIndexToMultipleIndexImporter",
			_singleIndexToMultipleIndexImporter);

		_setUpRankingIndexNameBuilder();
	}

	@Test
	public void testSingleIndexExistsMultipleIndexExists() throws Exception {
		_setUpRankingIndexReader(true);
		_setUpSingleIndexToMultipleIndexImporter(true);

		_rankingIndexLifecycleManager.createIndex(RandomTestUtil.randomLong());

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(0)
		).create(
			Mockito.any()
		);

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

		_rankingIndexLifecycleManager.createIndex(RandomTestUtil.randomLong());

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(1)
		).create(
			Mockito.any()
		);

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

		_rankingIndexLifecycleManager.createIndex(RandomTestUtil.randomLong());

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(0)
		).create(
			Mockito.any()
		);

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

		_rankingIndexLifecycleManager.createIndex(RandomTestUtil.randomLong());

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(1)
		).create(
			Mockito.any()
		);

		Mockito.verify(
			_singleIndexToMultipleIndexImporter, Mockito.times(0)
		).importRankings(
			Mockito.anyLong()
		);
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

	private void _setUpSingleIndexToMultipleIndexImporter(boolean needsImport) {
		Mockito.doReturn(
			needsImport
		).when(
			_singleIndexToMultipleIndexImporter
		).needImport();
	}

	private final RankingIndexCreator _rankingIndexCreator = Mockito.mock(
		RankingIndexCreator.class);
	private RankingIndexLifecycleManager _rankingIndexLifecycleManager;
	private final RankingIndexNameBuilder _rankingIndexNameBuilder =
		Mockito.mock(RankingIndexNameBuilder.class);
	private final RankingIndexReader _rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);
	private final SingleIndexToMultipleIndexImporter
		_singleIndexToMultipleIndexImporter = Mockito.mock(
			SingleIndexToMultipleIndexImporter.class);

}
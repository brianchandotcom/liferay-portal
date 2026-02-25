/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.index.ConcurrentReindexManager;
import com.liferay.portal.search.index.SyncReindexManager;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Felipe Lorenz
 */
public class SearchEngineInitializerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_searchEngineHelperUtilMockedStatic = Mockito.mockStatic(
			SearchEngineHelperUtil.class);
	}

	@After
	public void tearDown() {
		_searchEngineHelperUtilMockedStatic.close();
	}

	@Test
	public void testConcurrentReindex() throws Exception {
		_reindex("concurrent", Collections.emptyList());

		_verify(1, 0, 0, 1, 0, 1);
	}

	@Test
	public void testConcurrentReindexExceptionThrown() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SearchEngineInitializer.class.getName(),
				LoggerTestUtil.ERROR)) {

			_reindex("concurrent", null);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(NullPointerException.class, throwable.getClass());
		}

		_verify(1, 1, 0, 1, 0, 0);
	}

	@Test
	public void testRegularReindex() throws Exception {
		_reindex("regular", Collections.emptyList());

		_verify(0, 0, 0, 1, 1, 0);
	}

	@Test
	public void testSyncReindex() throws Exception {
		_reindex("sync", Collections.emptyList());

		_verify(0, 0, 1, 0, 0, 0);
	}

	private void _reindex(String executionMode, List<Indexer<?>> indexers) {
		SearchEngineInitializer searchEngineInitializer =
			new SearchEngineInitializer(
				RandomTestUtil.randomLong(), _concurrentReindexManager,
				executionMode, indexers, _portalExecutorManager,
				_syncReindexManager);

		searchEngineInitializer.reindex();
	}

	private void _verify(
			int createNextIndex, int deleteNextIndex, int deleteStaleDocuments,
			int initialize, int removeCompany,
			int replaceCurrentIndexWithNextIndex)
		throws Exception {

		Mockito.verify(
			_concurrentReindexManager, Mockito.times(createNextIndex)
		).createNextIndex(
			Mockito.anyLong()
		);

		Mockito.verify(
			_concurrentReindexManager, Mockito.times(deleteNextIndex)
		).deleteNextIndex(
			Mockito.anyLong()
		);

		Mockito.verify(
			_concurrentReindexManager,
			Mockito.times(replaceCurrentIndexWithNextIndex)
		).replaceCurrentIndexWithNextIndex(
			Mockito.anyLong()
		);

		_searchEngineHelperUtilMockedStatic.verify(
			() -> SearchEngineHelperUtil.initialize(Mockito.anyLong()),
			Mockito.times(initialize));
		_searchEngineHelperUtilMockedStatic.verify(
			() -> SearchEngineHelperUtil.removeCompany(Mockito.anyLong()),
			Mockito.times(removeCompany));

		Mockito.verify(
			_syncReindexManager, Mockito.times(deleteStaleDocuments)
		).deleteStaleDocuments(
			Mockito.anyLong(), Mockito.any(), Mockito.any()
		);
	}

	private final ConcurrentReindexManager _concurrentReindexManager =
		Mockito.mock(ConcurrentReindexManager.class);
	private final PortalExecutorManager _portalExecutorManager = Mockito.mock(
		PortalExecutorManager.class);
	private MockedStatic<SearchEngineHelperUtil>
		_searchEngineHelperUtilMockedStatic;
	private final SyncReindexManager _syncReindexManager = Mockito.mock(
		SyncReindexManager.class);

}
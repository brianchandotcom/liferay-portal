/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.ReindexCacheThreadLocal;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.search.index.ConcurrentReindexManager;
import com.liferay.portal.search.index.SyncReindexManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchEngineInitializer implements Runnable {

	public SearchEngineInitializer(
		long companyId, ConcurrentReindexManager concurrentReindexManager,
		String executionMode, ExecutorService executorService,
		List<Indexer<?>> indexers, SyncReindexManager syncReindexManager) {

		_companyId = companyId;
		_concurrentReindexManager = concurrentReindexManager;
		_executionMode = executionMode;
		_executorService = executorService;
		_indexers = indexers;
		_syncReindexManager = syncReindexManager;
	}

	public void halt() {
	}

	public boolean isFinished() {
		return _finished;
	}

	public void reindex() {
		reindex(0);
	}

	public void reindex(int delay) {
		_reindex(delay);
	}

	@Override
	public void run() {
		reindex(PropsValues.INDEX_ON_STARTUP_DELAY);
	}

	protected void reindex(Indexer<?> indexer) throws Exception {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Reindexing of " + indexer.getClassName() +
					" entities started");
		}

		indexer.reindex(new String[] {String.valueOf(_companyId)});

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Reindexing of ", indexer.getClassName(),
					" entities completed in ",
					stopWatch.getTime() / Time.SECOND, " seconds"));
		}
	}

	private boolean _isExecuteConcurrentReindex() {
		if ((_concurrentReindexManager != null) && (_executionMode != null) &&
			_executionMode.equals("concurrent") &&
			(_companyId != CompanyConstants.SYSTEM)) {

			return true;
		}

		return false;
	}

	private boolean _isExecuteSyncReindex() {
		if ((_syncReindexManager != null) && (_executionMode != null) &&
			_executionMode.equals("sync")) {

			return true;
		}

		return false;
	}

	private void _reindex(int delay) {
		if (IndexWriterHelperUtil.isIndexReadOnly()) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Reindexing started");
		}

		if (delay < 0) {
			delay = 0;
		}

		try {
			if (delay > 0) {
				Thread.sleep(Time.SECOND * delay);
			}
		}
		catch (InterruptedException interruptedException) {
			if (_log.isDebugEnabled()) {
				_log.debug(interruptedException);
			}
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			Date date = null;

			boolean fullMode = false;

			if (_isExecuteConcurrentReindex()) {
				SearchEngineHelperUtil.initialize(_companyId);

				_concurrentReindexManager.createNextIndex(_companyId);
			}
			else if (_isExecuteSyncReindex()) {
				date = new Date();

				Thread.sleep(1000);
			}
			else {
				fullMode = true;

				SearchEngineHelperUtil.removeCompany(_companyId);

				SearchEngineHelperUtil.initialize(_companyId);
			}

			boolean finalFullMode = fullMode;

			Set<String> indexerClassNames = new HashSet<>();
			Map<String, Object> sharedReindexCacheMap =
				new ConcurrentHashMap<>();

			if (_indexers.size() == 1) {
				Indexer<?> indexer = _indexers.get(0);

				indexerClassNames.add(indexer.getClassName());

				try (SafeCloseable safeCloseable1 =
						ReindexCacheThreadLocal.openReindexMode(
							finalFullMode, sharedReindexCacheMap);
					SafeCloseable safeCloseable2 = SearchContext.openBatchMode(
						false)) {

					reindex(indexer);
				}
			}
			else {
				long backgroundTaskId =
					BackgroundTaskThreadLocal.getBackgroundTaskId();
				List<FutureTask<Void>> futureTasks = new ArrayList<>();

				for (Indexer<?> indexer : _indexers) {
					indexerClassNames.add(indexer.getClassName());

					FutureTask<Void> futureTask = new FutureTask<>(
						new Callable<Void>() {

							@Override
							public Void call() throws Exception {
								try (SafeCloseable safeCloseable1 =
										BackgroundTaskThreadLocal.
											setBackgroundTaskIdWithSafeCloseable(
												backgroundTaskId);
									SafeCloseable safeCloseable2 =
										ReindexCacheThreadLocal.openReindexMode(
											finalFullMode,
											sharedReindexCacheMap);
									SafeCloseable safeCloseable3 =
										SearchContext.openBatchMode(false)) {

									reindex(indexer);

									return null;
								}
							}

						});

					_executorService.submit(futureTask);

					futureTasks.add(futureTask);
				}

				for (FutureTask<Void> futureTask : futureTasks) {
					futureTask.get();
				}
			}

			if (_isExecuteConcurrentReindex()) {
				_concurrentReindexManager.replaceCurrentIndexWithNextIndex(
					_companyId);
			}
			else if (_isExecuteSyncReindex()) {
				_syncReindexManager.deleteStaleDocuments(
					_companyId, date, indexerClassNames);
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Reindexing completed in " +
						(stopWatch.getTime() / Time.SECOND) + " seconds");
			}
		}
		catch (Exception exception) {
			if (_isExecuteConcurrentReindex()) {
				_concurrentReindexManager.deleteNextIndex(_companyId);
			}

			_log.error("Error encountered while reindexing", exception);

			if (_log.isInfoEnabled()) {
				_log.info("Reindexing failed");
			}
		}

		_finished = true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchEngineInitializer.class);

	private final long _companyId;
	private final ConcurrentReindexManager _concurrentReindexManager;
	private final String _executionMode;
	private final ExecutorService _executorService;
	private boolean _finished;
	private final List<Indexer<?>> _indexers;
	private final SyncReindexManager _syncReindexManager;

}
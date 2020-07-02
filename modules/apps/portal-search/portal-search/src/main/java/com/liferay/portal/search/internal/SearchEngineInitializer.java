/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.internal;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.search.ccr.CrossClusterReplicationHelper;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.framework.BundleContext;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchEngineInitializer implements Runnable {

	public SearchEngineInitializer(
		BundleContext bundleContext, long companyId,
		CrossClusterReplicationHelper crossClusterReplicationHelper,
		IndexNameBuilder indexNameBuilder,
		PortalExecutorManager portalExecutorManager) {

		_bundleContext = bundleContext;
		_companyId = companyId;
		_crossClusterReplicationHelper = crossClusterReplicationHelper;
		_indexNameBuilder = indexNameBuilder;
		_portalExecutorManager = portalExecutorManager;
	}

	public Set<String> getUsedSearchEngineIds() {
		return _usedSearchEngineIds;
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
		doReIndex(delay);
	}

	@Override
	public void run() {
		reindex(PropsValues.INDEX_ON_STARTUP_DELAY);
	}

	protected void doReIndex(int delay) {
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
		}

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				SearchEngineInitializer.class.getName());

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			if (_crossClusterReplicationHelper != null) {
				_crossClusterReplicationHelper.unfollow(
					_indexNameBuilder.getIndexName(_companyId));
			}

			SearchEngineHelperUtil.removeCompany(_companyId);

			SearchEngineHelperUtil.initialize(_companyId);

			if (_crossClusterReplicationHelper != null) {
				_crossClusterReplicationHelper.follow(
					_indexNameBuilder.getIndexName(_companyId));
			}

			long backgroundTaskId =
				BackgroundTaskThreadLocal.getBackgroundTaskId();
			List<FutureTask<Void>> futureTasks = new ArrayList<>();

			if (_companyId == CompanyConstants.SYSTEM) {
				_indexers = ServiceTrackerListFactory.open(
					_bundleContext, (Class<Indexer<?>>)(Class<?>)Indexer.class,
					"(system.index=true)");
			}
			else {
				_indexers = ServiceTrackerListFactory.open(
					_bundleContext, (Class<Indexer<?>>)(Class<?>)Indexer.class,
					"(!(system.index=true))");
			}

			for (Indexer<?> indexer : _indexers) {
				FutureTask<Void> futureTask = new FutureTask<>(
					new Callable<Void>() {

						@Override
						public Void call() throws Exception {
							try (SafeClosable safeClosable =
									BackgroundTaskThreadLocal.
										setBackgroundTaskIdWithSafeClosable(
											backgroundTaskId)) {

								reindex(indexer);

								return null;
							}
						}

					});

				executorService.submit(futureTask);

				futureTasks.add(futureTask);
			}

			_indexers.close();

			for (FutureTask<Void> futureTask : futureTasks) {
				futureTask.get();
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Reindexing completed in " +
						(stopWatch.getTime() / Time.SECOND) + " seconds");
			}
		}
		catch (Exception exception) {
			_log.error("Error encountered while reindexing", exception);

			if (_log.isInfoEnabled()) {
				_log.info("Reindexing failed");
			}
		}

		_finished = true;
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

		_usedSearchEngineIds.add(indexer.getSearchEngineId());

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Reindexing of ", indexer.getClassName(),
					" entities completed in ",
					stopWatch.getTime() / Time.SECOND, " seconds"));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchEngineInitializer.class);

	private final BundleContext _bundleContext;
	private final long _companyId;
	private final CrossClusterReplicationHelper _crossClusterReplicationHelper;
	private boolean _finished;
	private ServiceTrackerList<Indexer<?>, Indexer<?>> _indexers;
	private final IndexNameBuilder _indexNameBuilder;
	private final PortalExecutorManager _portalExecutorManager;
	private final Set<String> _usedSearchEngineIds = new HashSet<>();

}
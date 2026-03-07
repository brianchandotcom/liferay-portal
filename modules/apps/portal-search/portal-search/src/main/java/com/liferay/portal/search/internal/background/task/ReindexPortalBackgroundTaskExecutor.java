/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.background.task;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.index.ConcurrentReindexManager;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.index.SyncReindexManager;
import com.liferay.portal.search.internal.SearchEngineInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.portal.search.internal.background.task.ReindexPortalBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class ReindexPortalBackgroundTaskExecutor
	extends BaseReindexBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, (Class<Indexer<?>>)(Class<?>)Indexer.class, null,
			(serviceReference, emitter) -> emitter.emit(
				GetterUtil.getBoolean(
					serviceReference.getProperty("system.index"))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Override
	protected void reindex(
			String className, long[] companyIds, String executionMode)
		throws Exception {

		long backgroundTaskId = BackgroundTaskThreadLocal.getBackgroundTaskId();
		ExecutorService executorService =
			_searchEngineHelper.getDocumentsProducerExecutorService();
		List<Future<?>> futures = new ArrayList<>();

		try (SafeCloseable safeCloseable1 = SearchContext.openBatchMode()) {
			for (long companyId : companyIds) {
				List<Indexer<?>> indexers = _serviceTrackerMap.getService(
					companyId == CompanyConstants.SYSTEM);

				futures.add(
					executorService.submit(
						() -> {
							try (SafeCloseable safeCloseable2 =
									BackgroundTaskThreadLocal.
										setBackgroundTaskIdWithSafeCloseable(
											backgroundTaskId)) {

								_reindexCompany(
									companyId, companyIds, executionMode,
									executorService, indexers);

								return null;
							}
						}));
			}

			for (Future<?> future : futures) {
				future.get();
			}
		}
	}

	private void _reindexCompany(
		long companyId, long[] companyIds, String executionMode,
		ExecutorService executorService, List<Indexer<?>> indexers) {

		ReindexStatusMessageSenderUtil.sendStatusMessage(
			ReindexBackgroundTaskConstants.PORTAL_START, companyId, companyIds);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Start reindexing company ", companyId,
					" with execution mode ", executionMode));
		}

		try {
			SearchEngineInitializer searchEngineInitializer =
				new SearchEngineInitializer(
					companyId, _concurrentReindexManagerSnapshot.get(),
					executionMode, executorService, _indexNameBuilder, indexers,
					_jsonFactory, _searchEngineAdapter,
					_syncReindexManagerSnapshot.get());

			searchEngineInitializer.reindex();
		}
		catch (Exception exception) {
			_log.error(exception);
		}
		finally {
			ReindexStatusMessageSenderUtil.sendStatusMessage(
				ReindexBackgroundTaskConstants.PORTAL_END, companyId,
				companyIds);

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Finished reindexing company ", companyId,
						" with execution mode ", executionMode));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReindexPortalBackgroundTaskExecutor.class);

	private static final Snapshot<ConcurrentReindexManager>
		_concurrentReindexManagerSnapshot = new Snapshot<>(
			ReindexPortalBackgroundTaskExecutor.class,
			ConcurrentReindexManager.class, null, true);
	private static final Snapshot<SyncReindexManager>
		_syncReindexManagerSnapshot = new Snapshot<>(
			ReindexPortalBackgroundTaskExecutor.class, SyncReindexManager.class,
			null, true);

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private SearchEngineHelper _searchEngineHelper;

	private ServiceTrackerMap<Boolean, List<Indexer<?>>> _serviceTrackerMap;

}
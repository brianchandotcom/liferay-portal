/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.background.task;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.index.ConcurrentReindexManager;
import com.liferay.portal.search.index.SyncReindexManager;
import com.liferay.portal.search.internal.SearchEngineInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
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
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_parallelIndexing = GetterUtil.getBoolean(
			properties.get("parallelIndexing"), true);
	}

	@Override
	protected void reindex(
			String className, long[] companyIds, String executionMode)
		throws Exception {

		if (_parallelIndexing) {
			ExecutorService executorService =
				_portalExecutorManager.getPortalExecutor(
					ReindexPortalBackgroundTaskExecutor.class.getName());

			List<Future<?>> futures = new ArrayList<>();

			try (SafeCloseable safeCloseable = SearchContext.openBatchMode()) {
				for (long companyId : companyIds) {
					futures.add(
						executorService.submit(
							() -> {
								_reindex(companyId, companyIds, executionMode);

								return null;
							}));
				}

				for (Future<?> future : futures) {
					future.get();
				}
			}
		}
		else {
			for (long companyId : companyIds) {
				_reindex(companyId, companyIds, executionMode);
			}
		}
	}

	private void _reindex(
			long companyId, long[] companyIds, String executionMode)
		throws Exception {

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
					_bundleContext, companyId,
					_concurrentReindexManagerSnapshot.get(), executionMode,
					_portalExecutorManager, _syncReindexManagerSnapshot.get());

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

	private BundleContext _bundleContext;
	private boolean _parallelIndexing;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

}
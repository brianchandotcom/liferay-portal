/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.background.task;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;
import com.liferay.portal.search.index.ConcurrentReindexManager;
import com.liferay.portal.search.index.SyncReindexManager;
import com.liferay.portal.search.internal.SearchEngineInitializer;

import org.osgi.framework.BundleContext;

/**
 * @author Andrew Betts
 */
public class ReindexPortalBackgroundTaskExecutor
	extends BaseReindexBackgroundTaskExecutor {

	public ReindexPortalBackgroundTaskExecutor(
		BundleContext bundleContext,
		ConcurrentReindexManager concurrentReindexManager,
		PortalExecutorManager portalExecutorManager,
		SyncReindexManager syncReindexManager) {

		_bundleContext = bundleContext;
		_concurrentReindexManager = concurrentReindexManager;
		_portalExecutorManager = portalExecutorManager;
		_syncReindexManager = syncReindexManager;
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return new ReindexPortalBackgroundTaskExecutor(
			_bundleContext, _concurrentReindexManager, _portalExecutorManager,
			_syncReindexManager);
	}

	@Override
	protected void reindex(
			String className, long[] companyIds, String executionMode)
		throws Exception {

		for (long companyId : companyIds) {
			ReindexStatusMessageSenderUtil.sendStatusMessage(
				ReindexBackgroundTaskConstants.PORTAL_START, companyId,
				companyIds);

			if (_log.isInfoEnabled()) {
				if (FeatureFlagManagerUtil.isEnabled("LPS-183661")) {
					_log.info(
						StringBundler.concat(
							"Start reindexing company ", companyId,
							" with execution mode ", executionMode));
				}
				else {
					_log.info("Start reindexing company " + companyId);
				}
			}

			try {
				SearchEngineInitializer searchEngineInitializer =
					new SearchEngineInitializer(
						_bundleContext, companyId, _concurrentReindexManager,
						executionMode, _portalExecutorManager,
						_syncReindexManager);

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
					if (FeatureFlagManagerUtil.isEnabled("LPS-183661")) {
						_log.info(
							StringBundler.concat(
								"Finished reindexing company ", companyId,
								" with execution mode ", executionMode));
					}
					else {
						_log.info("Finished reindexing company " + companyId);
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReindexPortalBackgroundTaskExecutor.class);

	private final BundleContext _bundleContext;
	private final ConcurrentReindexManager _concurrentReindexManager;
	private final PortalExecutorManager _portalExecutorManager;
	private final SyncReindexManager _syncReindexManager;

}
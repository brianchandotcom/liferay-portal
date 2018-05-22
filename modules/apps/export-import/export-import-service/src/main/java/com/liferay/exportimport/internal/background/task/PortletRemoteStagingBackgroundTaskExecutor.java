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

package com.liferay.exportimport.internal.background.task;

import com.liferay.exportimport.internal.background.task.display.PortletExportImportBackgroundTaskDisplay;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManagerUtil;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portlet.exportimport.service.http.StagingServiceHttp;

import java.io.File;
import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Akos Thurzo
 */
public class PortletRemoteStagingBackgroundTaskExecutor
	extends BaseStagingBackgroundTaskExecutor {

	public PortletRemoteStagingBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new PortletStagingBackgroundTaskStatusMessageTranslator());
	}

	@Override
	public BackgroundTaskExecutor clone() {
		PortletRemoteStagingBackgroundTaskExecutor
			portletRemoteStagingBackgroundTaskExecutor =
				new PortletRemoteStagingBackgroundTaskExecutor();

		portletRemoteStagingBackgroundTaskExecutor.
			setBackgroundTaskStatusMessageTranslator(
				getBackgroundTaskStatusMessageTranslator());
		portletRemoteStagingBackgroundTaskExecutor.setIsolationLevel(
			getIsolationLevel());

		return portletRemoteStagingBackgroundTaskExecutor;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		File file = null;
		HttpPrincipal httpPrincipal = null;
		MissingReferences missingReferences = null;
		long stagingRequestId = 0L;

		try {
			currentThread.setContextClassLoader(
				ClassLoaderUtil.getPortalClassLoader());

			ExportImportThreadLocal.setPortletStagingInProcess(true);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_PORTLET_REMOTE_STARTED,
				ExportImportLifecycleConstants.
					PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS,
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				exportImportConfiguration);

			Map<String, Serializable> taskContextMap =
				backgroundTask.getTaskContextMap();

			httpPrincipal = (HttpPrincipal)taskContextMap.get("httpPrincipal");

			Map<String, Serializable> settingsMap =
				exportImportConfiguration.getSettingsMap();

			long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

			PortletStagingCallable layoutStagingCallable =
				new PortletStagingCallable(
					backgroundTask.getBackgroundTaskId(),
					exportImportConfiguration, httpPrincipal, targetGroupId);

			missingReferences = TransactionInvokerUtil.invoke(
				transactionConfig, layoutStagingCallable);

			file = layoutStagingCallable.getFile();
			stagingRequestId = layoutStagingCallable.getStagingRequestId();

			ExportImportThreadLocal.setPortletStagingInProcess(false);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_PORTLET_REMOTE_SUCCEEDED,
				ExportImportLifecycleConstants.
					PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS,
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				exportImportConfiguration);
		}
		catch (Throwable t) {
			ExportImportThreadLocal.setPortletStagingInProcess(false);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_PORTLET_REMOTE_FAILED,
				ExportImportLifecycleConstants.
					PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS,
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				exportImportConfiguration);

			deleteTempLarOnFailure(file);

			throw new SystemException(t);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);

			if ((stagingRequestId > 0) && (httpPrincipal != null)) {
				try {
					StagingServiceHttp.cleanUpStagingRequest(
						httpPrincipal, stagingRequestId);
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to clean up the remote live site", pe);
					}
				}
			}
		}

		deleteTempLarOnSuccess(file);

		return processMissingReferences(
			backgroundTask.getBackgroundTaskId(), missingReferences);
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return new PortletExportImportBackgroundTaskDisplay(backgroundTask);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletRemoteStagingBackgroundTaskExecutor.class);

	private class PortletStagingCallable
		implements Callable<MissingReferences> {

		public PortletStagingCallable(
			long backgroundTaskId,
			ExportImportConfiguration exportImportConfiguration,
			HttpPrincipal httpPrincipal, long targetGroupId) {

			_backgroundTaskId = backgroundTaskId;
			_exportImportConfiguration = exportImportConfiguration;
			_httpPrincipal = httpPrincipal;
			_targetGroupId = targetGroupId;
		}

		@Override
		public MissingReferences call() throws Exception {
			_file = ExportImportLocalServiceUtil.exportPortletInfoAsFile(
				_exportImportConfiguration);

			String checksum = FileUtil.getMD5Checksum(_file);

			_stagingRequestId = StagingServiceHttp.createStagingRequest(
				_httpPrincipal, _targetGroupId, checksum);

			StagingUtil.transferFileToRemoteLive(
				_file, _stagingRequestId, _httpPrincipal);

			markBackgroundTask(_backgroundTaskId, "exported");

			return StagingServiceHttp.publishStagingRequest(
				_httpPrincipal, _stagingRequestId, _exportImportConfiguration);
		}

		public File getFile() {
			return _file;
		}

		public long getStagingRequestId() {
			return _stagingRequestId;
		}

		private final long _backgroundTaskId;
		private final ExportImportConfiguration _exportImportConfiguration;
		private File _file;
		private final HttpPrincipal _httpPrincipal;
		private long _stagingRequestId;
		private final long _targetGroupId;

	}

}
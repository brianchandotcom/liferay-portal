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

import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_PORTLET_LOCAL_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_PORTLET_LOCAL_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_PORTLET_LOCAL_SUCCEEDED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS;

import com.liferay.exportimport.internal.background.task.display.PortletExportImportBackgroundTaskDisplay;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManagerUtil;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;

import java.io.File;

import java.util.concurrent.Callable;

/**
 * @author Julio Camarero
 * @author Daniel Kocsis
 * @author Akos Thurzo
 */
public class PortletStagingBackgroundTaskExecutor
	extends BaseStagingBackgroundTaskExecutor {

	public PortletStagingBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new PortletStagingBackgroundTaskStatusMessageTranslator());
	}

	@Override
	public BackgroundTaskExecutor clone() {
		PortletStagingBackgroundTaskExecutor
			portletStagingBackgroundTaskExecutor =
				new PortletStagingBackgroundTaskExecutor();

		portletStagingBackgroundTaskExecutor.
			setBackgroundTaskStatusMessageTranslator(
				getBackgroundTaskStatusMessageTranslator());
		portletStagingBackgroundTaskExecutor.setIsolationLevel(
			getIsolationLevel());

		return portletStagingBackgroundTaskExecutor;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		File file = null;
		MissingReferences missingReferences = null;

		try {
			ExportImportThreadLocal.setPortletStagingInProcess(true);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				EVENT_PUBLICATION_PORTLET_LOCAL_STARTED,
				PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS,
				exportImportConfiguration);

			file = ExportImportLocalServiceUtil.exportPortletInfoAsFile(
				exportImportConfiguration);

			markBackgroundTask(
				backgroundTask.getBackgroundTaskId(), "exported");

			missingReferences = TransactionInvokerUtil.invoke(
				transactionConfig,
				new PortletStagingCallable(
					backgroundTask.getBackgroundTaskId(),
					exportImportConfiguration, file));

			ExportImportThreadLocal.setPortletStagingInProcess(false);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				EVENT_PUBLICATION_PORTLET_LOCAL_SUCCEEDED,
				PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS,
				exportImportConfiguration);
		}
		catch (Throwable t) {
			ExportImportThreadLocal.setPortletStagingInProcess(false);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				EVENT_PUBLICATION_PORTLET_LOCAL_FAILED,
				PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS,
				exportImportConfiguration);

			if (_log.isDebugEnabled()) {
				_log.debug(t, t);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn("Unable to publish portlet: " + t.getMessage());
			}

			deleteTempLarOnFailure(file);

			throw new SystemException(t);
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
		PortletStagingBackgroundTaskExecutor.class);

	private class PortletStagingCallable
		implements Callable<MissingReferences> {

		public PortletStagingCallable(
			long backgroundTaskId,
			ExportImportConfiguration exportImportConfiguration, File file) {

			_backgroundTaskId = backgroundTaskId;
			_exportImportConfiguration = exportImportConfiguration;
			_file = file;
		}

		@Override
		public MissingReferences call() throws PortalException {
			ExportImportLocalServiceUtil.importPortletDataDeletions(
				_exportImportConfiguration, _file);

			MissingReferences missingReferences =
				ExportImportLocalServiceUtil.validateImportPortletInfo(
					_exportImportConfiguration, _file);

			markBackgroundTask(_backgroundTaskId, "validated");

			ExportImportLocalServiceUtil.importPortletInfo(
				_exportImportConfiguration, _file);

			return missingReferences;
		}

		private final long _backgroundTaskId;
		private final ExportImportConfiguration _exportImportConfiguration;
		private final File _file;

	}

}
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

package com.liferay.portal.lar.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleConstants;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.spring.transaction.TransactionHandlerUtil;

import java.io.File;
import java.io.Serializable;

import java.util.Map;
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
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		MissingReferences missingReferences = null;

		try {
			ExportImportThreadLocal.setPortletStagingInProcess(true);

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_PORTLET_LOCAL_STARTED,
				exportImportConfiguration);

			missingReferences = TransactionHandlerUtil.invoke(
				transactionAttribute,
				new PortletStagingCallable(
					backgroundTask.getBackgroundTaskId(),
					exportImportConfiguration));

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_PORTLET_LOCAL_SUCCEEDED,
				exportImportConfiguration);
		}
		catch (Throwable t) {
			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_PORTLET_LOCAL_FAILED,
				exportImportConfiguration);

			if (_log.isDebugEnabled()) {
				_log.debug(t, t);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn("Unable to publish portlet: " + t.getMessage());
			}

			throw new SystemException(t);
		}
		finally {
			ExportImportThreadLocal.setPortletStagingInProcess(false);
		}

		return processMissingReferences(
			backgroundTask.getBackgroundTaskId(), missingReferences);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletStagingBackgroundTaskExecutor.class);

	private class PortletStagingCallable
		implements Callable<MissingReferences> {

		public PortletStagingCallable(
			long backgroundTaskId,
			ExportImportConfiguration exportImportConfiguration) {

			_backgroundTaskId = backgroundTaskId;
			_exportImportConfiguration = exportImportConfiguration;
		}

		@Override
		public MissingReferences call() throws PortalException {
			Map<String, Serializable> settingsMap =
				_exportImportConfiguration.getSettingsMap();

			long userId = MapUtil.getLong(settingsMap, "userId");
			long targetPlid = MapUtil.getLong(settingsMap, "targetPlid");
			long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
			String portletId = MapUtil.getString(settingsMap, "portletId");
			Map<String, String[]> parameterMap =
				(Map<String, String[]>)settingsMap.get("parameterMap");

			long sourcePlid = MapUtil.getLong(settingsMap, "sourcePlid");
			long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");
			DateRange dateRange = ExportImportDateUtil.getDateRange(
				_exportImportConfiguration);

			File larFile = null;
			MissingReferences missingReferences = null;

			try {
				larFile = LayoutLocalServiceUtil.exportPortletInfoAsFile(
					sourcePlid, sourceGroupId, portletId, parameterMap,
					dateRange.getStartDate(), dateRange.getEndDate());

				markBackgroundTask(_backgroundTaskId, "exported");

				LayoutLocalServiceUtil.importPortletDataDeletions(
					userId, targetPlid, targetGroupId, portletId, parameterMap,
					larFile);

				missingReferences =
					LayoutLocalServiceUtil.validateImportPortletInfo(
						userId, targetPlid, targetGroupId, portletId,
						parameterMap, larFile);

				markBackgroundTask(_backgroundTaskId, "validated");

				LayoutLocalServiceUtil.importPortletInfo(
					userId, targetPlid, targetGroupId, portletId, parameterMap,
					larFile);
			}
			finally {
				larFile.delete();
			}

			return missingReferences;
		}

		private final long _backgroundTaskId;
		private final ExportImportConfiguration _exportImportConfiguration;

	}

}
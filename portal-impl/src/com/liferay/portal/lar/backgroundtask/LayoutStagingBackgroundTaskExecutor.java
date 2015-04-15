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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.StagingLocalServiceUtil;
import com.liferay.portal.spring.transaction.TransactionHandlerUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Julio Camarero
 */
public class LayoutStagingBackgroundTaskExecutor
	extends BaseStagingBackgroundTaskExecutor {

	public LayoutStagingBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new LayoutStagingBackgroundTaskStatusMessageTranslator());
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long userId = MapUtil.getLong(settingsMap, "userId");
		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
		long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");

		clearBackgroundTaskStatus(backgroundTask);

		MissingReferences missingReferences = null;

		try {
			ExportImportThreadLocal.setLayoutStagingInProcess(true);

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_LOCAL_STARTED,
				exportImportConfiguration);

			missingReferences = TransactionHandlerUtil.invoke(
				transactionAttribute,
				new LayoutStagingCallable(
					backgroundTask.getBackgroundTaskId(),
					exportImportConfiguration, sourceGroupId, targetGroupId,
					userId));

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_LOCAL_SUCCEEDED,
				exportImportConfiguration);
		}
		catch (Throwable t) {
			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_LOCAL_FAILED,
				exportImportConfiguration, t);

			if (_log.isDebugEnabled()) {
				_log.debug(t, t);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn("Unable to publish layout: " + t.getMessage());
			}

			Group sourceGroup = GroupLocalServiceUtil.getGroup(sourceGroupId);

			if (sourceGroup.hasStagingGroup()) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setUserId(userId);

				StagingLocalServiceUtil.disableStaging(
					sourceGroup, serviceContext);
			}

			throw new SystemException(t);
		}
		finally {
			ExportImportThreadLocal.setLayoutStagingInProcess(false);
		}

		return processMissingReferences(
			backgroundTask.getBackgroundTaskId(), missingReferences);
	}

	protected void initLayoutSetBranches(
			long userId, long sourceGroupId, long targetGroupId)
		throws PortalException {

		Group sourceGroup = GroupLocalServiceUtil.getGroup(sourceGroupId);

		if (!sourceGroup.hasStagingGroup()) {
			return;
		}

		LayoutSetBranchLocalServiceUtil.deleteLayoutSetBranches(
			targetGroupId, true);

		UnicodeProperties typeSettingsProperties =
			sourceGroup.getTypeSettingsProperties();

		boolean branchingPrivate = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("branchingPrivate"));
		boolean branchingPublic = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("branchingPublic"));

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(userId);

		StagingLocalServiceUtil.checkDefaultLayoutSetBranches(
			userId, sourceGroup, branchingPublic, branchingPrivate,
			serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutStagingBackgroundTaskExecutor.class);

	private class LayoutStagingCallable implements Callable<MissingReferences> {

		public LayoutStagingCallable(
			long backgroundTaskId,
			ExportImportConfiguration exportImportConfiguration,
			long sourceGroupId, long targetGroupId, long userId) {

			_backgroundTaskId = backgroundTaskId;
			_exportImportConfiguration = exportImportConfiguration;
			_sourceGroupId = sourceGroupId;
			_targetGroupId = targetGroupId;
			_userId = userId;
		}

		@Override
		public MissingReferences call() throws PortalException {
			File file = null;
			MissingReferences missingReferences = null;

			try {
				Map<String, Serializable> settingsMap =
					_exportImportConfiguration.getSettingsMap();

				initThreadLocals(_sourceGroupId);

				long[] layoutIds = GetterUtil.getLongValues(
					settingsMap.get("layoutIds"));
				Map<String, String[]> parameterMap =
					(Map<String, String[]>)settingsMap.get("parameterMap");
				DateRange dateRange = ExportImportDateUtil.getDateRange(
					_exportImportConfiguration);

				file = LayoutLocalServiceUtil.exportLayoutsAsFile(
					_sourceGroupId, layoutIds, parameterMap,
					dateRange.getStartDate(), dateRange.getEndDate());

				markBackgroundTask(_backgroundTaskId, "exported");

				LayoutLocalServiceUtil.importLayoutsDataDeletions(
					_userId, _targetGroupId, parameterMap, file);

				missingReferences =
					LayoutLocalServiceUtil.validateImportLayoutsFile(
						_userId, _targetGroupId, parameterMap, file);

				markBackgroundTask(_backgroundTaskId, "validated");

				LayoutLocalServiceUtil.importLayouts(
					_userId, _targetGroupId, parameterMap, file);

				initLayoutSetBranches(_userId, _sourceGroupId, _targetGroupId);
			}
			catch (Exception e) {
				if (PropsValues.STAGING_DELETE_TEMP_LAR_ON_FAILURE) {
					FileUtil.delete(file);
				}
				else if ((file != null) && _log.isErrorEnabled()) {
					_log.error(
						"Kept temporary LAR file " + file.getAbsolutePath());
				}

				throw e;
			}

			if (PropsValues.STAGING_DELETE_TEMP_LAR_ON_SUCCESS) {
				FileUtil.delete(file);
			}
			else if ((file != null) && _log.isDebugEnabled()) {
				_log.debug("Kept temporary LAR file " + file.getAbsolutePath());
			}

			return missingReferences;
		}

		private final long _backgroundTaskId;
		private final ExportImportConfiguration _exportImportConfiguration;
		private final long _sourceGroupId;
		private final long _targetGroupId;
		private final long _userId;

	}

}
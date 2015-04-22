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

import static com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_LOCAL_FAILED;
import static com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_LOCAL_STARTED;
import static com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_LOCAL_SUCCEEDED;
import static com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

		File file = null;
		MissingReferences missingReferences = null;

		try {
			ExportImportThreadLocal.setLayoutStagingInProcess(true);

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PUBLICATION_LAYOUT_LOCAL_STARTED,
				PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS,
				exportImportConfiguration);

			boolean privateLayout = MapUtil.getBoolean(
				settingsMap, "privateLayout");

			initThreadLocals(sourceGroupId, privateLayout);

			Map<String, String[]> parameterMap =
				(Map<String, String[]>)settingsMap.get("parameterMap");

			file = LayoutLocalServiceUtil.exportLayoutsAsFile(
				exportImportConfiguration);

			markBackgroundTask(
				backgroundTask.getBackgroundTaskId(), "exported");

			missingReferences = TransactionHandlerUtil.invoke(
				transactionAttribute,
				new LayoutStagingImportCallable(
					backgroundTask.getBackgroundTaskId(),
					exportImportConfiguration, file, parameterMap,
					privateLayout, sourceGroupId, targetGroupId, userId));

			ExportImportThreadLocal.setLayoutStagingInProcess(false);

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PUBLICATION_LAYOUT_LOCAL_SUCCEEDED,
				PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS,
				exportImportConfiguration);
		}
		catch (Throwable t) {
			ExportImportThreadLocal.setLayoutStagingInProcess(false);

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PUBLICATION_LAYOUT_LOCAL_FAILED,
				PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS,
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

			deleteTempLarOnFailure(file);

			throw new SystemException(t);
		}

		deleteTempLarOnSuccess(file);

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
			targetGroupId, false, true);
		LayoutSetBranchLocalServiceUtil.deleteLayoutSetBranches(
			targetGroupId, true, true);

		UnicodeProperties typeSettingsProperties =
			sourceGroup.getTypeSettingsProperties();

		boolean branchingPrivate = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("branchingPrivate"));
		boolean branchingPublic = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("branchingPublic"));

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(userId);

		StagingLocalServiceUtil.checkDefaultLayoutSetBranches(
			userId, sourceGroup, branchingPublic, branchingPrivate, false,
			serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutStagingBackgroundTaskExecutor.class);

	private class LayoutStagingImportCallable
		implements Callable<MissingReferences> {

		public LayoutStagingImportCallable(
			long backgroundTaskId,
			ExportImportConfiguration exportImportConfiguration, File file,
			Map<String, String[]> parameterMap, boolean privateLayout,
			long sourceGroupId, long targetGroupId, long userId) {

			_backgroundTaskId = backgroundTaskId;
			_exportImportConfiguration = exportImportConfiguration;
			_file = file;
			_parameterMap = parameterMap;
			_privateLayout = privateLayout;
			_sourceGroupId = sourceGroupId;
			_targetGroupId = targetGroupId;
			_userId = userId;
		}

		@Override
		public MissingReferences call() throws PortalException {
			LayoutLocalServiceUtil.importLayoutsDataDeletions(
				_exportImportConfiguration, _file);

			MissingReferences missingReferences =
				LayoutLocalServiceUtil.validateImportLayoutsFile(
					_userId, _targetGroupId, _privateLayout, _parameterMap,
					_file);

			markBackgroundTask(_backgroundTaskId, "validated");

			LayoutLocalServiceUtil.importLayouts(
				_userId, _targetGroupId, _privateLayout, _parameterMap, _file);

			initLayoutSetBranches(_userId, _sourceGroupId, _targetGroupId);

			return missingReferences;
		}

		private final long _backgroundTaskId;
		private final ExportImportConfiguration _exportImportConfiguration;
		private final File _file;
		private final Map<String, String[]> _parameterMap;
		private final boolean _privateLayout;
		private final long _sourceGroupId;
		private final long _targetGroupId;
		private final long _userId;

	}

}
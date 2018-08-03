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

import com.liferay.document.library.kernel.service.DLAppHelperLocalServiceUtil;
import com.liferay.exportimport.internal.background.task.display.LayoutStagingBackgroundTaskDisplay;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManagerUtil;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.trash.service.TrashEntryLocalServiceUtil;

import java.io.File;
import java.io.Serializable;

import java.util.List;
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
	public BackgroundTaskExecutor clone() {
		LayoutStagingBackgroundTaskExecutor
			layoutStagingBackgroundTaskExecutor =
				new LayoutStagingBackgroundTaskExecutor();

		layoutStagingBackgroundTaskExecutor.
			setBackgroundTaskStatusMessageTranslator(
				getBackgroundTaskStatusMessageTranslator());
		layoutStagingBackgroundTaskExecutor.setIsolationLevel(
			getIsolationLevel());

		return layoutStagingBackgroundTaskExecutor;
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

			Group targetGroup = GroupLocalServiceUtil.fetchGroup(targetGroupId);

			if (targetGroup == null) {
				throw new NoSuchGroupException(
					"Target group does not exists with the primary key " +
						targetGroupId);
			}

			Group sourceGroup = GroupLocalServiceUtil.getGroup(sourceGroupId);

			if (sourceGroup.hasStagingGroup()) {
				Group stagingGroup = sourceGroup.getStagingGroup();

				if (stagingGroup.getGroupId() == targetGroupId) {
					DLAppHelperLocalServiceUtil.cancelCheckOuts(sourceGroupId);

					ExportImportThreadLocal.setInitialLayoutStagingInProcess(
						true);

					TrashEntryLocalServiceUtil.deleteEntries(
						sourceGroupId, true);
				}
			}

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_LOCAL_STARTED,
				ExportImportLifecycleConstants.
					PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS,
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				exportImportConfiguration);

			boolean privateLayout = MapUtil.getBoolean(
				settingsMap, "privateLayout");

			initThreadLocals(sourceGroupId, privateLayout);

			file = ExportImportLocalServiceUtil.exportLayoutsAsFile(
				exportImportConfiguration);

			markBackgroundTask(
				backgroundTask.getBackgroundTaskId(), "exported");

			missingReferences = TransactionInvokerUtil.invoke(
				transactionConfig,
				new LayoutStagingImportCallable(
					backgroundTask.getBackgroundTaskId(),
					exportImportConfiguration, file, sourceGroupId,
					targetGroupId, userId));

			deleteExportedChangesetEntries();

			ExportImportThreadLocal.setInitialLayoutStagingInProcess(false);
			ExportImportThreadLocal.setLayoutStagingInProcess(false);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_LOCAL_SUCCEEDED,
				ExportImportLifecycleConstants.
					PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS,
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				exportImportConfiguration);

			ExportImportHelperUtil.processBackgroundTaskManifestSummary(
				userId, sourceGroupId, backgroundTask, file);
		}
		catch (Throwable t) {
			ExportImportThreadLocal.setInitialLayoutStagingInProcess(false);
			ExportImportThreadLocal.setLayoutStagingInProcess(false);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_LOCAL_FAILED,
				ExportImportLifecycleConstants.
					PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS,
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				exportImportConfiguration, t);

			Group sourceGroup = GroupLocalServiceUtil.getGroup(sourceGroupId);

			if (sourceGroup.hasStagingGroup()) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setUserId(userId);

				StagingLocalServiceUtil.disableStaging(
					sourceGroup, serviceContext);

				List<BackgroundTask> queuedBackgroundTasks =
					BackgroundTaskManagerUtil.getBackgroundTasks(
						sourceGroupId,
						LayoutStagingBackgroundTaskExecutor.class.getName(),
						BackgroundTaskConstants.STATUS_QUEUED);

				for (BackgroundTask queuedBackgroundTask :
						queuedBackgroundTasks) {

					BackgroundTaskManagerUtil.amendBackgroundTask(
						queuedBackgroundTask.getBackgroundTaskId(), null,
						BackgroundTaskConstants.STATUS_CANCELLED,
						new ServiceContext());
				}
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

		return new LayoutStagingBackgroundTaskDisplay(backgroundTask);
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

	private class LayoutStagingImportCallable
		implements Callable<MissingReferences> {

		public LayoutStagingImportCallable(
			long backgroundTaskId,
			ExportImportConfiguration exportImportConfiguration, File file,
			long sourceGroupId, long targetGroupId, long userId) {

			_backgroundTaskId = backgroundTaskId;
			_exportImportConfiguration = exportImportConfiguration;
			_file = file;
			_sourceGroupId = sourceGroupId;
			_targetGroupId = targetGroupId;
			_userId = userId;
		}

		@Override
		public MissingReferences call() throws PortalException {
			ExportImportLocalServiceUtil.importLayoutsDataDeletions(
				_exportImportConfiguration, _file);

			MissingReferences missingReferences =
				ExportImportLocalServiceUtil.validateImportLayoutsFile(
					_exportImportConfiguration, _file);

			markBackgroundTask(_backgroundTaskId, "validated");

			ExportImportLocalServiceUtil.importLayouts(
				_exportImportConfiguration, _file);

			initLayoutSetBranches(_userId, _sourceGroupId, _targetGroupId);

			return missingReferences;
		}

		private final long _backgroundTaskId;
		private final ExportImportConfiguration _exportImportConfiguration;
		private final File _file;
		private final long _sourceGroupId;
		private final long _targetGroupId;
		private final long _userId;

	}

}
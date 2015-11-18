/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.StagingLocalServiceUtil;
import com.liferay.portal.spring.transaction.TransactionalCallableUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
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
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long userId = MapUtil.getLong(taskContextMap, "userId");
		long targetGroupId = MapUtil.getLong(taskContextMap, "targetGroupId");

		StagingUtil.lockGroup(userId, targetGroupId);

		long sourceGroupId = MapUtil.getLong(taskContextMap, "sourceGroupId");

		clearBackgroundTaskStatus(backgroundTask);

		MissingReferences missingReferences = null;

		try {
			ExportImportThreadLocal.setLayoutStagingInProcess(true);

			missingReferences = TransactionalCallableUtil.call(
				transactionAttribute,
				new LayoutStagingCallable(
					backgroundTask, sourceGroupId, targetGroupId,
					taskContextMap, userId));
		}
		catch (Throwable t) {
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

			if (t instanceof Exception) {
				throw (Exception)t;
			}
			else {
				throw new SystemException(t);
			}
		}
		finally {
			ExportImportThreadLocal.setLayoutStagingInProcess(false);

			StagingUtil.unlockGroup(targetGroupId);
		}

		return processMissingReferences(backgroundTask, missingReferences);
	}

	protected void initLayoutSetBranches(
			long userId, long sourceGroupId, long targetGroupId)
		throws Exception {

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

	private static Log _log = LogFactoryUtil.getLog(
		LayoutStagingBackgroundTaskExecutor.class);

	private class LayoutStagingCallable implements Callable<MissingReferences> {

		public LayoutStagingCallable(
			BackgroundTask backgroundTask, long sourceGroupId,
			long targetGroupId, Map<String, Serializable> taskContextMap,
			long userId) {

			_backgroundTask = backgroundTask;
			_sourceGroupId = sourceGroupId;
			_targetGroupId = targetGroupId;
			_taskContextMap = taskContextMap;
			_userId = userId;
		}

		@Override
		public MissingReferences call() throws Exception {
			File file = null;
			MissingReferences missingReferences = null;

			try {
				boolean privateLayout = MapUtil.getBoolean(
					_taskContextMap, "privateLayout");
				long[] layoutIds = GetterUtil.getLongValues(
					_taskContextMap.get("layoutIds"));
				Map<String, String[]> parameterMap =
					(Map<String, String[]>)_taskContextMap.get("parameterMap");
				Date startDate = (Date)_taskContextMap.get("startDate");
				Date endDate = (Date)_taskContextMap.get("endDate");

				file = LayoutLocalServiceUtil.exportLayoutsAsFile(
					_sourceGroupId, privateLayout, layoutIds, parameterMap,
					startDate, endDate);

				_backgroundTask = markBackgroundTask(
					_backgroundTask, "exported");

				missingReferences =
					LayoutLocalServiceUtil.validateImportLayoutsFile(
						_userId, _targetGroupId, privateLayout, parameterMap,
						file);

				_backgroundTask = markBackgroundTask(
					_backgroundTask, "validated");

				LayoutLocalServiceUtil.importLayouts(
					_userId, _targetGroupId, privateLayout, parameterMap, file);

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

		private BackgroundTask _backgroundTask;
		private long _sourceGroupId;
		private long _targetGroupId;
		private Map<String, Serializable> _taskContextMap;
		private long _userId;

	}

}
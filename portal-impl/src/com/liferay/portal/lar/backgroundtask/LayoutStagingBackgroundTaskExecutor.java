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
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.transaction.Propagation;
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
import com.liferay.portal.spring.transaction.TransactionAttributeBuilder;
import com.liferay.portal.spring.transaction.TransactionalCallableUtil;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Julio Camarero
 */
public class LayoutStagingBackgroundTaskExecutor
	extends BaseStagingBackgroundTaskExecutor {

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long userId = MapUtil.getLong(taskContextMap, "userId");
		long targetGroupId = MapUtil.getLong(taskContextMap, "targetGroupId");

		StagingUtil.lockGroup(userId, targetGroupId);

		long sourceGroupId = MapUtil.getLong(taskContextMap, "sourceGroupId");
		boolean initialImport = MapUtil.getBoolean(
			taskContextMap, "initialImport");
		ServiceContext serviceContext = (ServiceContext)taskContextMap.get(
			"serviceContext");

		Group sourceGroup = GroupLocalServiceUtil.getGroup(sourceGroupId);

		clearBackgroundTaskStatus(backgroundTask);

		MissingReferences missingReferences = null;

		boolean failed = false;

		try {
			Callable layoutStagingCallable = new LayoutStagingCallable(
				backgroundTask, sourceGroupId, targetGroupId, taskContextMap,
				userId);

			missingReferences =
				(MissingReferences)TransactionalCallableUtil.call(
					_transactionAttribute, layoutStagingCallable);
		}
		catch (Throwable t) {
			failed = true;

			if (t instanceof Exception) {
				throw (Exception)t;
			}
			else {
				throw new SystemException(t);
			}
		}
		finally {
			StagingUtil.unlockGroup(targetGroupId);

			if (failed && initialImport) {
				StagingUtil.disableStaging(sourceGroup, serviceContext);
			}
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

		StagingUtil.checkDefaultLayoutSetBranches(
			userId, sourceGroup, branchingPublic, branchingPrivate, false,
			serviceContext);
	}

	private TransactionAttribute _transactionAttribute =
		TransactionAttributeBuilder.build(
			Propagation.REQUIRED, new Class<?>[]{Exception.class});

	private class LayoutStagingCallable implements Callable {

		private LayoutStagingCallable(
			BackgroundTask backgroundTask, long sourceGroupId,
			long targetGroupId, Map<String, Serializable> taskContextMap,
			long userId) {

			_backgroundTask = backgroundTask;
			_sourceGroupId = sourceGroupId;
			_targetGroupId = targetGroupId;
			_taskContextMap = taskContextMap;
			_userId = userId;
		}

		public Object call() throws Exception {
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

				Date lastPublishDate = endDate;

				if (lastPublishDate == null) {
					lastPublishDate = new Date();
				}

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

				boolean updateLastPublishDate = MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

				if (updateLastPublishDate) {
					Group sourceGroup = GroupLocalServiceUtil.getGroup(
						_sourceGroupId);

					if (!sourceGroup.hasStagingGroup()) {
						StagingUtil.updateLastPublishDate(
							_sourceGroupId, privateLayout, lastPublishDate);
					}
					else {
						StagingUtil.updateLastPublishDate(
							_targetGroupId, privateLayout, lastPublishDate);
					}
				}
			}
			finally {
				FileUtil.delete(file);
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
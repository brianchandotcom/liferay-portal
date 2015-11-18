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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.spring.transaction.TransactionalCallableUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Julio Camarero
 * @author Daniel Kocsis
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

		MissingReferences missingReferences = null;

		try {
			ExportImportThreadLocal.setPortletStagingInProcess(true);

			missingReferences = TransactionalCallableUtil.call(
				transactionAttribute,
				new PortletStagingCallable(backgroundTask));
		}
		catch (Throwable t) {
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

		return processMissingReferences(backgroundTask, missingReferences);
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletStagingBackgroundTaskExecutor.class);

	private class PortletStagingCallable
		implements Callable<MissingReferences> {

		public PortletStagingCallable(BackgroundTask backgroundTask) {
			_backgroundTask = backgroundTask;
		}

		@Override
		public MissingReferences call() throws Exception {
			Map<String, Serializable> taskContextMap =
				_backgroundTask.getTaskContextMap();

			long userId = MapUtil.getLong(taskContextMap, "userId");
			long targetPlid = MapUtil.getLong(taskContextMap, "targetPlid");
			long targetGroupId = MapUtil.getLong(
				taskContextMap, "targetGroupId");
			String portletId = MapUtil.getString(taskContextMap, "portletId");
			Map<String, String[]> parameterMap =
				(Map<String, String[]>)taskContextMap.get("parameterMap");

			long sourcePlid = MapUtil.getLong(taskContextMap, "sourcePlid");
			long sourceGroupId = MapUtil.getLong(
				taskContextMap, "sourceGroupId");
			Date startDate = (Date)taskContextMap.get("startDate");
			Date endDate = (Date)taskContextMap.get("endDate");

			File larFile = null;
			MissingReferences missingReferences = null;

			try {
				larFile = LayoutLocalServiceUtil.exportPortletInfoAsFile(
					sourcePlid, sourceGroupId, portletId, parameterMap,
					startDate, endDate);

				_backgroundTask = markBackgroundTask(
					_backgroundTask, "exported");

				missingReferences =
					LayoutLocalServiceUtil.validateImportPortletInfo(
						userId, targetPlid, targetGroupId, portletId,
						parameterMap, larFile);

				_backgroundTask = markBackgroundTask(
					_backgroundTask, "validated");

				LayoutLocalServiceUtil.importPortletInfo(
					userId, targetPlid, targetGroupId, portletId, parameterMap,
					larFile);
			}
			catch (Exception e) {
				if (PropsValues.STAGING_DELETE_TEMP_LAR_ON_FAILURE) {
					FileUtil.delete(larFile);
				}
				else if ((larFile != null) && _log.isErrorEnabled()) {
					_log.error(
						"Kept temporary LAR file " + larFile.getAbsolutePath());
				}

				throw e;
			}

			if (PropsValues.STAGING_DELETE_TEMP_LAR_ON_SUCCESS) {
				FileUtil.delete(larFile);
			}
			else if ((larFile != null) && _log.isDebugEnabled()) {
				_log.debug(
					"Kept temporary LAR file " + larFile.getAbsolutePath());
			}

			return missingReferences;
		}

		private BackgroundTask _backgroundTask;

	}

}
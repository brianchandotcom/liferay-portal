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

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.spring.transaction.TransactionHandlerUtil;

import java.io.File;
import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Daniel Kocsis
 * @author Akos Thurzo
 */
public class LayoutImportBackgroundTaskExecutor
	extends BaseExportImportBackgroundTaskExecutor {

	public LayoutImportBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new LayoutExportImportBackgroundTaskStatusMessageTranslator());

		// Isolation level guarantees this will be serial in a group

		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_GROUP);
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long userId = MapUtil.getLong(settingsMap, "userId");
		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		List<FileEntry> attachmentsFileEntries =
			backgroundTask.getAttachmentsFileEntries();

		File file = null;

		for (FileEntry attachmentsFileEntry : attachmentsFileEntries) {
			try {
				file = FileUtil.createTempFile("lar");

				FileUtil.write(file, attachmentsFileEntry.getContentStream());

				TransactionHandlerUtil.invoke(
					transactionAttribute,
					new LayoutImportCallable(
						file, parameterMap, targetGroupId, userId));
			}
			catch (Throwable t) {
				if (_log.isDebugEnabled()) {
					_log.debug(t, t);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn("Unable to import layouts: " + t.getMessage());
				}

				throw new SystemException(t);
			}
			finally {
				FileUtil.delete(file);
			}
		}

		return BackgroundTaskResult.SUCCESS;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutImportBackgroundTaskExecutor.class);

	private class LayoutImportCallable implements Callable<Void> {

		public LayoutImportCallable(
			File file, Map<String, String[]> parameterMap, long targetGroupId,
			long userId) {

			_file = file;
			_parameterMap = parameterMap;
			_targetGroupId = targetGroupId;
			_userId = userId;
		}

		@Override
		public Void call() throws PortalException {
			LayoutLocalServiceUtil.importLayoutsDataDeletions(
				_userId, _targetGroupId, _parameterMap, _file);

			LayoutLocalServiceUtil.importLayouts(
				_userId, _targetGroupId, _parameterMap, _file);

			return null;
		}

		private final File _file;
		private final Map<String, String[]> _parameterMap;
		private final long _targetGroupId;
		private final long _userId;

	}

}
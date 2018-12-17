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

package com.liferay.document.library.internal.background.task;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionBackgroundActionExecutorConsumer;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.document.library.bulk.selection.FileEntryBulkSelectionBackgroundActionExecutor;
import com.liferay.document.library.internal.bulk.selection.FileEntryBulkSelectionBackgroundActionExecutorImpl;
import com.liferay.document.library.internal.constants.BulkSelectionBackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserLocalService;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "background.task.executor.class.name=com.liferay.document.library.internal.background.task.FileEntryBulkSelectionBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class FileEntryBulkSelectionBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public FileEntryBulkSelectionBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new UpdateFileEntryTagsBackgroundTaskStatusMessageTranslator());
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_TASK_NAME);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		BulkSelectionBackgroundActionExecutorConsumer consumer =
			(BulkSelectionBackgroundActionExecutorConsumer)taskContextMap.get(
				BulkSelectionBackgroundTaskConstants.
					BULK_SELECTION_BACKGROUND_ACTION_EXECUTOR_CONSUMER);

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)taskContextMap.get(
				BulkSelectionBackgroundTaskConstants.
					BULK_SELECTION_PARAMETER_MAP);

		BulkSelection<FileEntry, FileEntryBulkSelectionBackgroundActionExecutor>
			bulkSelection = _fileEntryBulkSelectionFactory.create(parameterMap);

		long userId = (long)taskContextMap.get(
			BulkSelectionBackgroundTaskConstants.USER_ID);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(
				_userLocalService.getUser(userId));

		consumer.accept(
			new FileEntryBulkSelectionBackgroundActionExecutorImpl(
				bulkSelection, permissionChecker,
				_fileEntryModelResourcePermission, _assetEntryLocalService));

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private BulkSelectionFactory
		<FileEntry, FileEntryBulkSelectionBackgroundActionExecutor>
			_fileEntryBulkSelectionFactory;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;

	@Reference
	private UserLocalService _userLocalService;

}
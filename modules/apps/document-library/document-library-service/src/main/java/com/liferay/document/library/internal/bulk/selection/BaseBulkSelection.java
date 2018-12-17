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

package com.liferay.document.library.internal.bulk.selection;

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionBackgroundActionExecutorConsumer;
import com.liferay.document.library.bulk.selection.FileEntryBulkSelectionBackgroundActionExecutor;
import com.liferay.document.library.internal.background.task.FileEntryBulkSelectionBackgroundTaskExecutor;
import com.liferay.document.library.internal.constants.BulkSelectionBackgroundTaskConstants;
import com.liferay.portal.background.task.constants.BackgroundTaskContextMapConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseBulkSelection
	implements BulkSelection
		<FileEntry, FileEntryBulkSelectionBackgroundActionExecutor> {

	public BaseBulkSelection(
		Map<String, String[]> parameterMap,
		BackgroundTaskManager backgroundTaskManager) {

		_parameterMap = parameterMap;
		_backgroundTaskManager = backgroundTaskManager;
	}

	@Override
	public
			<U extends BulkSelectionBackgroundActionExecutorConsumer
				<FileEntryBulkSelectionBackgroundActionExecutor>>
					void runBackgroundAction(U consumer)
				throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(
			BulkSelectionBackgroundTaskConstants.
				BULK_SELECTION_BACKGROUND_ACTION_EXECUTOR_CONSUMER,
			consumer);
		taskContextMap.put(
			BulkSelectionBackgroundTaskConstants.BULK_SELECTION_PARAMETER_MAP,
			(Serializable)_parameterMap);
		taskContextMap.put(
			BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS, true);
		taskContextMap.put(
			BulkSelectionBackgroundTaskConstants.USER_ID,
			PrincipalThreadLocal.getUserId());

		_backgroundTaskManager.addBackgroundTask(
			PrincipalThreadLocal.getUserId(), CompanyConstants.SYSTEM,
			getBackgroundJobName(),
			FileEntryBulkSelectionBackgroundTaskExecutor.class.getName(),
			taskContextMap, new ServiceContext());
	}

	protected abstract String getBackgroundJobName();

	private final BackgroundTaskManager _backgroundTaskManager;
	private final Map<String, String[]> _parameterMap;

}
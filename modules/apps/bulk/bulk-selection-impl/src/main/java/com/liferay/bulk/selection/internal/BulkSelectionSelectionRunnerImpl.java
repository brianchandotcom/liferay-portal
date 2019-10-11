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

package com.liferay.bulk.selection.internal;

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.bulk.selection.internal.constants.BulkSelectionBackgroundTaskConstants;
import com.liferay.portal.background.task.constants.BackgroundTaskContextMapConstants;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = BulkSelectionRunner.class)
public class BulkSelectionSelectionRunnerImpl implements BulkSelectionRunner {

	@Override
	public boolean isBusy(User user) {
		List<BackgroundTask> backgroundTasks =
			_backgroundTaskLocalService.getBackgroundTasks(
				BulkSelectionBackgroundTaskExecutor.class.getName(),
				BackgroundTaskConstants.STATUS_IN_PROGRESS);

		Stream<BackgroundTask> stream = backgroundTasks.stream();

		long userId = user.getUserId();

		if (stream.anyMatch(
				backgroundTask -> backgroundTask.getUserId() == userId)) {

			return true;
		}

		return false;
	}

	@Override
	public <T> void run(
			User user, BulkSelection<T> bulkSelection,
			BulkSelectionAction<T> bulkSelectionAction,
			Map<String, Serializable> inputMap)
		throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		Class<? extends BulkSelectionAction> bulkSelectionActionClass =
			bulkSelectionAction.getClass();

		taskContextMap.put(
			BulkSelectionBackgroundTaskConstants.
				BULK_SELECTION_ACTION_CLASS_NAME,
			bulkSelectionActionClass.getName());

		taskContextMap.put(
			BulkSelectionBackgroundTaskConstants.
				BULK_SELECTION_ACTION_INPUT_MAP,
			new HashMap<>(inputMap));

		Class<? extends BulkSelectionFactory> bulkSelectionFactoryClass =
			bulkSelection.getBulkSelectionFactoryClass();

		taskContextMap.put(
			BulkSelectionBackgroundTaskConstants.
				BULK_SELECTION_FACTORY_CLASS_NAME,
			bulkSelectionFactoryClass.getName());

		taskContextMap.put(
			BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS, true);
		taskContextMap.put(
			BulkSelectionBackgroundTaskConstants.BULK_SELECTION_PARAMETER_MAP,
			new HashMap<>(bulkSelection.getParameterMap()));

		_backgroundTaskLocalService.addBackgroundTask(
			user.getUserId(), CompanyConstants.SYSTEM,
			bulkSelectionActionClass.getName(),
			BulkSelectionBackgroundTaskExecutor.class.getName(), taskContextMap,
			new ServiceContext());
	}

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

}
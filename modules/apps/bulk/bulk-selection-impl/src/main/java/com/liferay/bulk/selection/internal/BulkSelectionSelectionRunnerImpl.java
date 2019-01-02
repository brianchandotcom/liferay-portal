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
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = BulkSelectionRunner.class)
public class BulkSelectionSelectionRunnerImpl implements BulkSelectionRunner {

	@Override
	public <T> void run(
			BulkSelection<T> bulkSelection,
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
			BulkSelectionBackgroundTaskConstants.BULK_SELECTION_PARAMETER_MAP,
			new HashMap<>(bulkSelection.getParameterMap()));
		taskContextMap.put(
			BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS, true);

		_backgroundTaskManager.addBackgroundTask(
			PrincipalThreadLocal.getUserId(), CompanyConstants.SYSTEM,
			bulkSelectionActionClass.getName(),
			BulkSelectionBackgroundTaskExecutor.class.getName(), taskContextMap,
			new ServiceContext());
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

}
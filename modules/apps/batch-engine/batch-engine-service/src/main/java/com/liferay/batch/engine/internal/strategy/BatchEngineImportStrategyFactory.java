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

package com.liferay.batch.engine.internal.strategy;

import com.liferay.batch.engine.action.ImportTaskPostAction;
import com.liferay.batch.engine.action.ImportTaskPreAction;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.strategy.BatchEngineImportStrategy;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Matija Petanjek
 */
@Component(service = BatchEngineImportStrategyFactory.class)
public class BatchEngineImportStrategyFactory {

	public BatchEngineImportStrategy create(
		BatchEngineImportTask batchEngineImportTask) {

		if (batchEngineImportTask.getImportStrategy() ==
				BatchEngineImportTaskConstants.
					IMPORT_STRATEGY_ON_ERROR_CONTINUE) {

			return new OnErrorContinueBatchEngineImportStrategy(
				batchEngineImportTask, _importTaskPostActions.toList(),
				_importTaskPreActions.toList());
		}

		return new OnErrorFailBatchEngineImportStrategy(
			batchEngineImportTask, _importTaskPostActions.toList(),
			_importTaskPreActions.toList());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_importTaskPostActions = ServiceTrackerListFactory.open(
			bundleContext, ImportTaskPostAction.class);
		_importTaskPreActions = ServiceTrackerListFactory.open(
			bundleContext, ImportTaskPreAction.class);
	}

	@Deactivate
	protected void deactivate() {
		_importTaskPostActions.close();
		_importTaskPreActions.close();
	}

	private ServiceTrackerList<ImportTaskPostAction> _importTaskPostActions;
	private ServiceTrackerList<ImportTaskPreAction> _importTaskPreActions;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
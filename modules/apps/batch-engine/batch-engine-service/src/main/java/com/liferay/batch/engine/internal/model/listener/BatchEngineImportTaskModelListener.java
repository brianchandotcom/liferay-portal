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

package com.liferay.batch.engine.internal.model.listener;

import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.observer.BatchEngineTaskObserver;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Matija Petanjek
 */
@Component(immediate = true, service = ModelListener.class)
public class BatchEngineImportTaskModelListener
	extends BaseModelListener<BatchEngineImportTask> {

	@Override
	public void onAfterCreate(BatchEngineImportTask batchEngineImportTask) {
		for (BatchEngineTaskObserver batchEngineTaskObserver :
				_batchEngineTaskObservers) {

			batchEngineTaskObserver.onChange(batchEngineImportTask);
		}
	}

	@Override
	public void onAfterUpdate(
		BatchEngineImportTask originalBatchEngineImportTask,
		BatchEngineImportTask batchEngineImportTask) {

		for (BatchEngineTaskObserver batchEngineTaskObserver :
				_batchEngineTaskObservers) {

			batchEngineTaskObserver.onChange(batchEngineImportTask);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addBatchEngineTaskObserver(
		BatchEngineTaskObserver batchEngineTaskObserver) {

		_batchEngineTaskObservers.add(batchEngineTaskObserver);
	}

	protected void removeBatchEngineTaskObserver(
		BatchEngineTaskObserver batchEngineTaskObserver) {

		_batchEngineTaskObservers.remove(batchEngineTaskObserver);
	}

	private final List<BatchEngineTaskObserver> _batchEngineTaskObservers =
		new ArrayList<>();

}
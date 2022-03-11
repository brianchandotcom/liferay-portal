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

package com.liferay.batch.engine.service.impl;

import com.liferay.batch.engine.model.BatchEngineImportTaskError;
import com.liferay.batch.engine.service.base.BatchEngineImportTaskErrorLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "model.class.name=com.liferay.batch.engine.model.BatchEngineImportTaskError",
	service = AopService.class
)
public class BatchEngineImportTaskErrorLocalServiceImpl
	extends BatchEngineImportTaskErrorLocalServiceBaseImpl {

	@Override
	public BatchEngineImportTaskError addBatchEngineImportTaskError(
		long companyId, long userId, long batchEngineImportTaskId, String item,
		int itemIndex, String message) {

		BatchEngineImportTaskError batchEngineImportTaskError =
			batchEngineImportTaskErrorPersistence.create(
				counterLocalService.increment());

		batchEngineImportTaskError.setCompanyId(companyId);
		batchEngineImportTaskError.setUserId(userId);
		batchEngineImportTaskError.setBatchEngineImportTaskId(
			batchEngineImportTaskId);
		batchEngineImportTaskError.setItem(item);
		batchEngineImportTaskError.setItemIndex(itemIndex);
		batchEngineImportTaskError.setMessage(message);

		return batchEngineImportTaskErrorPersistence.update(
			batchEngineImportTaskError);
	}

	@Override
	public List<BatchEngineImportTaskError> getBatchEngineImportTaskErrors(
		long batchEngineImportTaskId) {

		return batchEngineImportTaskErrorPersistence.
			findByBatchEngineImportTaskId(batchEngineImportTaskId);
	}

	@Override
	public int getBatchEngineImportTaskErrorsCount(
		long batchEngineImportTaskId) {

		return batchEngineImportTaskErrorPersistence.
			countByBatchEngineImportTaskId(batchEngineImportTaskId);
	}

}
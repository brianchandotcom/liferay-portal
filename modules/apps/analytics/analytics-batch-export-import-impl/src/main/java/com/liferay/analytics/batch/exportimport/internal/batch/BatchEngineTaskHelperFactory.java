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

package com.liferay.analytics.batch.exportimport.internal.batch;

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = BatchEngineTaskHelperFactory.class)
public class BatchEngineTaskHelperFactory {

	public BatchEngineExportTaskHelper getBatchEngineExportTaskHelper(
		String batchEngineTaskItemDelegateName, long companyId,
		List<String> fieldList, Map<String, Serializable> parameters,
		String resourceName, long userId) {

		return new BatchEngineExportTaskHelper(
			batchEngineExportTaskExecutor, batchEngineExportTaskLocalService,
			batchEngineTaskItemDelegateName, companyId, fieldList, parameters,
			resourceName, userId);
	}

	public BatchEngineImportTaskHelper getBatchEngineImportTaskHelper(
			String batchEngineTaskItemDelegateName, long companyId,
			Map<String, String> fieldMapping, File resourceFile,
			String resourceName, long userId)
		throws IOException, PortalException {

		return new BatchEngineImportTaskHelper(
			batchEngineImportTaskExecutor, batchEngineImportTaskLocalService,
			batchEngineTaskItemDelegateName, _DEFAULT_BATCH_SIZE, companyId,
			fieldMapping, resourceFile, resourceName, userId);
	}

	@Reference
	protected BatchEngineExportTaskExecutor batchEngineExportTaskExecutor;

	@Reference
	protected BatchEngineExportTaskLocalService
		batchEngineExportTaskLocalService;

	@Reference
	protected BatchEngineImportTaskExecutor batchEngineImportTaskExecutor;

	@Reference
	protected BatchEngineImportTaskLocalService
		batchEngineImportTaskLocalService;

	private static final long _DEFAULT_BATCH_SIZE = 50;

}
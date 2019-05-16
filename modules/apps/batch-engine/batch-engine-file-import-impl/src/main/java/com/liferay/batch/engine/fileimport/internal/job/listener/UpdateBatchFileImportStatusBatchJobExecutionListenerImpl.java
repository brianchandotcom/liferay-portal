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

package com.liferay.batch.engine.fileimport.internal.job.listener;

import com.liferay.batch.engine.BatchStatus;
import com.liferay.batch.engine.core.BatchJobExecutionListener;
import com.liferay.batch.engine.core.constants.BatchConstants;
import com.liferay.batch.engine.exception.NoSuchFileImportException;
import com.liferay.batch.engine.model.BatchFileImport;
import com.liferay.batch.engine.model.BatchJobExecution;
import com.liferay.batch.engine.service.BatchFileImportLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	name = "UpdateBatchFileImportStatusBatchJobExecutionListener",
	service = BatchJobExecutionListener.class
)
public class UpdateBatchFileImportStatusBatchJobExecutionListenerImpl
	implements BatchJobExecutionListener {

	@Override
	public void afterJob(BatchJobExecution batchJobExecution) {
		_updateBatchFileImportStatus(batchJobExecution);
	}

	@Override
	public void beforeJob(BatchJobExecution batchJobExecution) {
		UnicodeProperties jobSettingsProperties =
			batchJobExecution.getJobSettingsProperties();

		try {
			BatchFileImport batchFileImport =
				_batchFileImportLocalService.getBatchFileImport(
					GetterUtil.getLong(
						jobSettingsProperties.getProperty(
							BatchConstants.BATCH_FILE_IMPORT_ID)));

			batchFileImport.setBatchJobExecutionId(
				batchJobExecution.getBatchJobExecutionId());

			batchFileImport.setStatus(BatchStatus.STARTED.toString());

			_batchFileImportLocalService.updateBatchFileImport(batchFileImport);
		}
		catch (PortalException pe) {
			throw new IllegalStateException(pe.getMessage(), pe);
		}
	}

	private void _updateBatchFileImportStatus(
		BatchJobExecution batchJobExecution) {

		BatchFileImport batchFileImport = null;

		try {
			batchFileImport = _batchFileImportLocalService.getBatchFileImports(
				batchJobExecution.getBatchJobExecutionId());
		}
		catch (NoSuchFileImportException nsfie) {
			throw new IllegalStateException(nsfie.getMessage(), nsfie);
		}

		batchFileImport.setStatus(batchJobExecution.getStatus());

		_batchFileImportLocalService.updateBatchFileImport(batchFileImport);
	}

	@Reference
	private BatchFileImportLocalService _batchFileImportLocalService;

}
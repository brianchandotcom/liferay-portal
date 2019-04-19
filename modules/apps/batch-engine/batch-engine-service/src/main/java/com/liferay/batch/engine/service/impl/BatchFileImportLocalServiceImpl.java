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

import com.liferay.batch.engine.BatchFileImportOperation;
import com.liferay.batch.engine.BatchStatus;
import com.liferay.batch.engine.exception.NoSuchFileImportException;
import com.liferay.batch.engine.internal.order.comparator.BatchFileImportIdComparator;
import com.liferay.batch.engine.model.BatchFileImport;
import com.liferay.batch.engine.service.base.BatchFileImportLocalServiceBaseImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import java.util.List;
import java.util.Objects;

/**
 * @author Ivica Cardic
 */
public class BatchFileImportLocalServiceImpl
	extends BatchFileImportLocalServiceBaseImpl {

	@Override
	public BatchFileImport addBatchFileImport(
		long fileEntryId, String domainName, String version,
		BatchFileImportOperation batchFileImportOperation, String callbackURL,
		String columnNames, BatchStatus batchStatus) {

		_validate(domainName, version, batchFileImportOperation);

		BatchFileImport batchFileImport = batchFileImportPersistence.create(
			counterLocalService.increment(BatchFileImport.class.getName()));

		batchFileImport.setCompanyId(_getCompanyId());

		batchFileImport.setFileEntryId(fileEntryId);
		batchFileImport.setDomainName(domainName);
		batchFileImport.setVersion(version);
		batchFileImport.setOperation(batchFileImportOperation.toString());
		batchFileImport.setCallbackURL(callbackURL);
		batchFileImport.setColumnNames(columnNames);
		batchFileImport.setStatus(batchStatus.toString());

		return batchFileImportPersistence.update(batchFileImport);
	}

	@Override
	public int countBatchFileImports(BatchStatus batchStatus) {
		return batchFileImportPersistence.countByStatus(batchStatus.toString());
	}

	@Override
	public BatchFileImport fetchFirstBatchFileImport(BatchStatus batchStatus) {
		BatchFileImport batchFileImport = null;

		try {
			batchFileImport = batchFileImportPersistence.findByStatus_First(
				batchStatus.toString(), new BatchFileImportIdComparator(true));

			return batchFileImport;
		}
		catch (NoSuchFileImportException nsfie) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfie.getMessage());
			}
		}

		return null;
	}

	@Override
	public List<BatchFileImport> getBatchFileImports(BatchStatus batchStatus) {
		return batchFileImportPersistence.findByStatus(batchStatus.toString());
	}

	@Override
	public BatchFileImport getBatchFileImports(long batchJobExecutionId)
		throws NoSuchFileImportException {

		return batchFileImportPersistence.findByBatchJobExecutionId(
			batchJobExecutionId);
	}

	private long _getCompanyId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		return serviceContext.getCompanyId();
	}

	private void _validate(
		String domainName, String version,
		BatchFileImportOperation batchFileImportOperation) {

		Objects.requireNonNull(domainName);
		Objects.requireNonNull(version);
		Objects.requireNonNull(batchFileImportOperation);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchFileImportLocalServiceImpl.class);

}
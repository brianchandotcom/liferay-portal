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

import com.liferay.batch.engine.BatchStatus;
import com.liferay.batch.engine.exception.JobExecutionAlreadyRunningException;
import com.liferay.batch.engine.exception.JobInstanceAlreadyCompleteException;
import com.liferay.batch.engine.exception.JobRestartException;
import com.liferay.batch.engine.exception.NoSuchJobExecutionException;
import com.liferay.batch.engine.exception.NoSuchJobInstanceException;
import com.liferay.batch.engine.internal.order.comparator.BatchJobExecutionIdComparator;
import com.liferay.batch.engine.model.BatchJobExecution;
import com.liferay.batch.engine.model.BatchJobInstance;
import com.liferay.batch.engine.service.base.BatchJobExecutionLocalServiceBaseImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;
import java.util.Objects;

/**
 * @author Matija Petanjek
 * @author Ivica Cardic
 */
public class BatchJobExecutionLocalServiceImpl
	extends BatchJobExecutionLocalServiceBaseImpl {

	@Override
	public BatchJobExecution addBatchJobExecution(
			String jobName, UnicodeProperties jobSettingsProperties)
		throws JobExecutionAlreadyRunningException,
			   JobInstanceAlreadyCompleteException, JobRestartException {

		_validate(jobName, jobSettingsProperties);

		String jobKey = _getJobKey(jobSettingsProperties);

		BatchJobInstance batchJobInstance =
			batchJobInstanceLocalService.fetchBatchJobInstance(jobName, jobKey);

		if (batchJobInstance == null) {
			batchJobInstance = batchJobInstanceLocalService.addBatchJobInstance(
				jobName, jobKey);
		}
		else {
			List<BatchJobExecution> batchJobExecutions =
				batchJobExecutionPersistence.findBybatchJobInstanceId(
					batchJobInstance.getBatchJobInstanceId());

			for (BatchJobExecution batchJobExecution : batchJobExecutions) {
				BatchStatus batchStatus = BatchStatus.valueOf(
					batchJobExecution.getStatus());

				if (batchStatus == BatchStatus.COMPLETED) {
					throw new JobInstanceAlreadyCompleteException(
						String.format(
							"A job execution for job %s with parameters %s " +
								"is already complete	",
							jobName, jobSettingsProperties));
				}
				else if (batchStatus == BatchStatus.STARTED) {
					throw new JobExecutionAlreadyRunningException(
						String.format(
							"A job execution for job %s with parameters %s " +
								"is already running",
							jobName, jobSettingsProperties));
				}
				else if (batchStatus == BatchStatus.UNKNOWN) {
					throw new JobRestartException(
						String.format(
							"A job %s with parameters %s cannot be restarted " +
								"from UNKNOWN status",
							jobName, jobSettingsProperties));
				}
			}
		}

		BatchJobExecution batchJobExecution =
			batchJobExecutionPersistence.create(
				counterLocalService.increment(
					BatchJobExecution.class.getName()));

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		batchJobExecution.setCompanyId(serviceContext.getCompanyId());

		batchJobExecution.setBatchJobInstanceId(
			batchJobInstance.getBatchJobInstanceId());
		batchJobExecution.setJobSettingsProperties(jobSettingsProperties);
		batchJobExecution.setStatus(BatchStatus.UNKNOWN.toString());

		return batchJobExecutionPersistence.update(batchJobExecution);
	}

	@Override
	public BatchJobExecution fetchLastBatchJobExecution(
		long batchJobInstanceId) {

		BatchJobExecution batchJobExecution = null;

		try {
			batchJobExecution =
				batchJobExecutionPersistence.findBybatchJobInstanceId_Last(
					batchJobInstanceId,
					new BatchJobExecutionIdComparator(true));

			return batchJobExecution;
		}
		catch (NoSuchJobExecutionException nsjee) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsjee.getMessage());
			}
		}

		return null;
	}

	@Override
	public BatchJobExecution fetchLastBatchJobExecution(
		String batchJobName, UnicodeProperties jobSettingsProperties) {

		_validate(batchJobName, jobSettingsProperties);

		BatchJobInstance batchJobInstance = null;

		try {
			batchJobInstance = batchJobInstancePersistence.findByJN_JK(
				batchJobName, _getJobKey(jobSettingsProperties));
		}
		catch (NoSuchJobInstanceException nsjie) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsjie.getMessage());
			}

			return null;
		}

		try {
			return batchJobExecutionPersistence.findBybatchJobInstanceId_Last(
				batchJobInstance.getBatchJobInstanceId(),
				new BatchJobExecutionIdComparator(true));
		}
		catch (NoSuchJobExecutionException nsjee) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsjee.getMessage());
			}

			return null;
		}
	}

	@Override
	public List<BatchJobExecution> getBatchJobExecutions(String status) {
		return batchJobExecutionPersistence.findBystatus(status);
	}

	private String _getJobKey(UnicodeProperties jobSettingsProperties) {
		return String.valueOf(jobSettingsProperties.hashCode());
	}

	private void _validate(
		String jobName, UnicodeProperties jobSettingsProperties) {

		Objects.requireNonNull(jobName);
		Objects.requireNonNull(jobSettingsProperties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchJobExecutionLocalServiceImpl.class);

}
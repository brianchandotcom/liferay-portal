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

package com.liferay.batch.engine.core.internal;

import com.liferay.batch.engine.BatchStatus;
import com.liferay.batch.engine.core.BatchJob;
import com.liferay.batch.engine.core.launch.BatchJobLauncher;
import com.liferay.batch.engine.exception.JobExecutionAlreadyRunningException;
import com.liferay.batch.engine.exception.JobInstanceAlreadyCompleteException;
import com.liferay.batch.engine.exception.JobRestartException;
import com.liferay.batch.engine.model.BatchJobExecution;
import com.liferay.batch.engine.service.BatchJobExecutionLocalService;
import com.liferay.petra.concurrent.NoticeableExecutorService;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(immediate = true, service = BatchJobLauncher.class)
public class BatchJobLauncherImpl implements BatchJobLauncher {

	@Override
	public BatchJobExecution run(
			BatchJob batchJob, UnicodeProperties jobSettingsProperties)
		throws JobExecutionAlreadyRunningException,
			   JobInstanceAlreadyCompleteException, JobRestartException {

		BatchJobExecution batchJobExecution = _addBatchJobExecution(
			batchJob, jobSettingsProperties);

		JobRunnable jobRunnable = new JobRunnable(batchJob, batchJobExecution);

		jobRunnable.run();

		return batchJobExecution;
	}

	@Override
	public BatchJobExecution runAsync(
			BatchJob batchJob, UnicodeProperties jobSettingsProperties)
		throws JobExecutionAlreadyRunningException,
			   JobInstanceAlreadyCompleteException, JobRestartException {

		BatchJobExecution batchJobExecution = _addBatchJobExecution(
			batchJob, jobSettingsProperties);

		NoticeableExecutorService noticeableExecutorService =
			_portalExecutorManager.getPortalExecutor(
				BatchJobLauncherImpl.class.getName());

		noticeableExecutorService.submit(
			new JobRunnable(batchJob, batchJobExecution));

		return batchJobExecution;
	}

	private BatchJobExecution _addBatchJobExecution(
			BatchJob batchJob, UnicodeProperties jobSettingsProperties)
		throws JobExecutionAlreadyRunningException,
			   JobInstanceAlreadyCompleteException, JobRestartException {

		Objects.requireNonNull(batchJob);

		BatchJobExecution lastBatchJobExecution =
			_batchJobExecutionLocalService.fetchLastBatchJobExecution(
				batchJob.getName(), jobSettingsProperties);

		if (lastBatchJobExecution != null) {
			if (!batchJob.isRestartable()) {
				throw new JobRestartException(
					String.format(
						"BatchJobInstance %s already exists and is not " +
							"restartable",
						batchJob.getName()));
			}

			BatchStatus batchStatus = BatchStatus.valueOf(
				lastBatchJobExecution.getStatus());

			if (batchStatus == BatchStatus.STARTED) {
				throw new JobExecutionAlreadyRunningException(
					String.format(
						"A job execution for job %s with parameters %s is " +
							"already running",
						batchJob.getName(), jobSettingsProperties));
			}
			else if (batchStatus == BatchStatus.UNKNOWN) {
				throw new JobRestartException(
					String.format(
						"A job %s with parameters %s cannot be restarted " +
							"from UNKNOWN status",
						batchJob.getName(), jobSettingsProperties));
			}
		}

		return _batchJobExecutionLocalService.addBatchJobExecution(
			batchJob.getName(), jobSettingsProperties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchJobLauncherImpl.class);

	@Reference
	private BatchJobExecutionLocalService _batchJobExecutionLocalService;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	private class JobRunnable implements Runnable {

		@Override
		public void run() {
			try {
				if (_log.isInfoEnabled()) {
					_log.info(
						String.format(
							"Batch Job %s launched with the following " +
								"parameters %s",
							_batchJob.getName(),
							_batchJobExecution.getJobSettings()));
				}

				_batchJob.execute(_batchJobExecution);

				if (_log.isInfoEnabled()) {
					_log.info(
						String.format(
							"Batch Job %s completed with the following " +
								"parameters %s",
							_batchJob.getName(),
							_batchJobExecution.getJobSettings()));
				}
			}
			catch (Throwable throwable) {
				_log.error(
					String.format(
						"Batch Job %s failed unexpectedly and fatally with " +
							"the following parameters %s",
						_batchJob.getName(),
						_batchJobExecution.getJobSettings()),
					throwable);
			}
		}

		private JobRunnable(
			BatchJob batchJob, BatchJobExecution batchJobExecution) {

			_batchJob = batchJob;
			_batchJobExecution = batchJobExecution;
		}

		private final BatchJob _batchJob;
		private final BatchJobExecution _batchJobExecution;

	}

}
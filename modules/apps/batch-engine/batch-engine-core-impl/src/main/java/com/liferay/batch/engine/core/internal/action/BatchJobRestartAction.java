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

package com.liferay.batch.engine.core.internal.action;

import com.liferay.portal.kernel.lock.LockManager;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(immediate = true, service = BatchJobRestartAction.class)
public class BatchJobRestartAction {

	@Activate
	public void activate() {
		/*if (_lock()) {
			return;
		}

		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

		Date jvmStartTime = new Date(runtimeMXBean.getStartTime());

		List<BatchJobExecution> batchJobExecutions =
			_batchJobExecutionLocalService.getBatchJobExecutions(
				BatchStatus.STARTED.toString());

		for (BatchJobExecution batchJobExecution : batchJobExecutions) {
			BatchStatus batchStatus = BatchStatus.valueOf(
				batchJobExecution.getStatus());

			if ((batchStatus == BatchStatus.STARTED) &&
				jvmStartTime.after(batchJobExecution.getCreateDate())) {

				batchJobExecution.setEndTime(new Date());
				batchJobExecution.setStatus(BatchStatus.FAILED.toString());

				_batchJobExecutionLocalService.updateBatchJobExecution(
					batchJobExecution);

				try {
					BatchJobInstance batchJobInstance =
						_batchJobInstanceLocalService.getBatchJobInstance(
							batchJobExecution.getBatchJobInstanceId());

					BatchJob batchJob = _batchJobRegistry.getBatchJob(
						batchJobInstance.getName());

					if (!batchJob.isRestartable()) {
						return;
					}

					_batchJobLauncher.runAsync(
						batchJob, batchJobExecution.getJobSettings());
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Could not resume batch job execution " +
								batchJobExecution,
							e);
					}
				}
			}
		}

		_unlock();*/
	}

	//	private boolean _lock() {
	//		if (_lockManager.isLocked(
	//				BatchJobRestartAction.class.getName(),
	//				BatchJobRestartAction.class.getName())) {
	//
	//			return true;
	//		}
	//
	//		_lockManager.lock(
	//			BatchJobRestartAction.class.getName(),
	//			BatchJobRestartAction.class.getName(), "system");
	//
	//		return false;
	//	}

	//	private void _unlock() {
	//		_lockManager.unlock(
	//			BatchJobRestartAction.class.getName(),
	//			BatchJobRestartAction.class.getName(), "system");
	//	}

	//	private static final Log _log = LogFactoryUtil.getLog(
	//		BatchJobRestartAction.class);

	//	@Reference
	//	private BatchJobExecutionLocalService _batchJobExecutionLocalService;
	//
	//	@Reference
	//	private BatchJobInstanceLocalService _batchJobInstanceLocalService;
	//
	//	@Reference
	//	private BatchJobLauncher _batchJobLauncher;
	//
	//	@Reference
	//	private BatchJobRegistry _batchJobRegistry;

	@Reference
	private LockManager _lockManager;

}
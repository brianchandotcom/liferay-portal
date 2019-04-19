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

import com.liferay.batch.engine.exception.NoSuchJobInstanceException;
import com.liferay.batch.engine.model.BatchJobInstance;
import com.liferay.batch.engine.service.base.BatchJobInstanceLocalServiceBaseImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import java.util.Objects;

/**
 * @author Ivica Cardic
 */
public class BatchJobInstanceLocalServiceImpl
	extends BatchJobInstanceLocalServiceBaseImpl {

	@Override
	public BatchJobInstance addBatchJobInstance(String jobName, String jobKey) {
		_validate(jobName, jobKey);

		BatchJobInstance batchJobInstance = batchJobInstancePersistence.create(
			counterLocalService.increment(BatchJobInstance.class.getName()));

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		batchJobInstance.setCompanyId(serviceContext.getCompanyId());

		batchJobInstance.setJobName(jobName);
		batchJobInstance.setJobKey(jobKey);

		return batchJobInstancePersistence.update(batchJobInstance);
	}

	@Override
	public BatchJobInstance fetchBatchJobInstance(
		String jobName, String jobKey) {

		_validate(jobName, jobKey);

		try {
			return batchJobInstancePersistence.findByJN_JK(jobName, jobKey);
		}
		catch (NoSuchJobInstanceException nsjie) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsjie.getMessage());
			}

			return null;
		}
	}

	private void _validate(String jobName, String jobKey) {
		Objects.requireNonNull(jobName);
		Objects.requireNonNull(jobKey);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchJobInstanceLocalServiceImpl.class);

}
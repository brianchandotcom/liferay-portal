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

import com.liferay.batch.engine.BatchContentType;
import com.liferay.batch.engine.BatchOperation;
import com.liferay.batch.engine.BatchStatus;
import com.liferay.batch.engine.model.BatchTask;
import com.liferay.batch.engine.service.base.BatchTaskLocalServiceBaseImpl;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "model.class.name=com.liferay.batch.engine.model.BatchTask",
	service = AopService.class
)
public class BatchTaskLocalServiceImpl extends BatchTaskLocalServiceBaseImpl {

	@Override
	public BatchTask addBatchTask(
		String className, String version, byte[] batchContent,
		BatchContentType batchContentType, BatchOperation batchOperation,
		long batchSize) {

		BatchTask batchTask = batchTaskPersistence.create(
			counterLocalService.increment(BatchTask.class.getName()));

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		batchTask.setCompanyId(serviceContext.getCompanyId());

		batchTask.setClassName(className);
		batchTask.setVersion(version);

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(batchContent);

		OutputBlob outputBlob = new OutputBlob(
			unsyncByteArrayInputStream, batchContent.length);

		batchTask.setContent(outputBlob);

		batchTask.setContentType(batchContentType.toString());
		batchTask.setOperation(batchOperation.toString());
		batchTask.setBatchSize(batchSize);
		batchTask.setStatus(BatchStatus.INITIAL.toString());

		return batchTaskPersistence.update(batchTask);
	}

}
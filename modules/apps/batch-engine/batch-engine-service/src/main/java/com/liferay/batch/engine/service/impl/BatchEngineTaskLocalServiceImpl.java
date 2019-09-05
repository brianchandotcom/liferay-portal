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

import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.model.BatchEngineTask;
import com.liferay.batch.engine.service.base.BatchEngineTaskLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the batch engine task local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.batch.engine.service.BatchEngineTaskLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineTaskLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.batch.engine.model.BatchEngineTask",
	service = AopService.class
)
public class BatchEngineTaskLocalServiceImpl
	extends BatchEngineTaskLocalServiceBaseImpl {

	@Override
	public BatchEngineTask addBatchEngineTask(
		String className, String version, byte[] content,
		BatchEngineTaskContentType batchEngineTaskContentType,
		BatchEngineTaskOperation batchEngineTaskOperation, long batchSize) {

		BatchEngineTask batchEngineTask = batchEngineTaskPersistence.create(
			counterLocalService.increment(BatchEngineTask.class.getName()));

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		batchEngineTask.setCompanyId(serviceContext.getCompanyId());

		batchEngineTask.setClassName(className);
		batchEngineTask.setVersion(version);

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(content);

		OutputBlob outputBlob = new OutputBlob(
			unsyncByteArrayInputStream, content.length);

		batchEngineTask.setContent(outputBlob);

		batchEngineTask.setBatchSize(batchSize);
		batchEngineTask.setContentType(batchEngineTaskContentType.toString());
		batchEngineTask.setExecuteStatus(
			BatchEngineTaskExecuteStatus.INITIAL.toString());
		batchEngineTask.setOperation(batchEngineTaskOperation.toString());

		return batchEngineTaskPersistence.update(batchEngineTask);
	}

}
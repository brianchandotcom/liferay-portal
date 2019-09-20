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

package com.liferay.batch.engine.internal.writer;

import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.internal.BatchEngineTaskMethodServiceTracker;
import com.liferay.batch.engine.model.BatchEngineTask;
import com.liferay.petra.function.UnsafeBiFunction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

/**
 * @author Ivica cardic
 */
public class BatchEngineTaskItemWriterFactory {

	public BatchEngineTaskItemWriterFactory(
		BatchEngineTaskMethodServiceTracker batchEngineTaskMethodServiceTracker,
		CompanyLocalService companyLocalService,
		UserLocalService userLocalService) {

		_batchEngineTaskMethodServiceTracker =
			batchEngineTaskMethodServiceTracker;
		_companyLocalService = companyLocalService;
		_userLocalService = userLocalService;
	}

	public BatchEngineTaskItemWriter create(BatchEngineTask batchEngineTask)
		throws Exception {

		BatchEngineTaskOperation batchEngineTaskOperation =
			BatchEngineTaskOperation.valueOf(batchEngineTask.getOperation());

		UnsafeBiFunction
			<Company, User, BatchEngineTaskItemWriter,
			 ReflectiveOperationException> unsafeBiFunction =
				_batchEngineTaskMethodServiceTracker.
					getBatchEngineTaskItemWriterFactory(
						StringBundler.concat(
							batchEngineTaskOperation, StringPool.POUND,
							batchEngineTask.getClassName(), StringPool.POUND,
							batchEngineTask.getVersion()));

		if (unsafeBiFunction == null) {
			StringBundler sb = new StringBundler(4);

			sb.append("No resource available for batch engine task operation ");
			sb.append(batchEngineTask.getOperation());
			sb.append(" and class name ");
			sb.append(batchEngineTask.getClassName());

			throw new IllegalStateException(sb.toString());
		}

		return unsafeBiFunction.apply(
			_companyLocalService.getCompany(batchEngineTask.getCompanyId()),
			_userLocalService.getUser(batchEngineTask.getUserId()));
	}

	private final BatchEngineTaskMethodServiceTracker
		_batchEngineTaskMethodServiceTracker;
	private final CompanyLocalService _companyLocalService;
	private final UserLocalService _userLocalService;

}
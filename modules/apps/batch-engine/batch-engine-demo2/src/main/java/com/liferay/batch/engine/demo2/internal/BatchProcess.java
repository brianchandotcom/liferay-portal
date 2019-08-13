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

package com.liferay.batch.engine.demo2.internal;

import com.liferay.batch.engine.BatchContentType;
import com.liferay.batch.engine.BatchOperation;
import com.liferay.batch.engine.BatchTaskExecutor;
import com.liferay.batch.engine.BatchTaskExecutorFactory;
import com.liferay.batch.engine.demo2.internal.model.Product;
import com.liferay.batch.engine.demo2.internal.model.Sku;
import com.liferay.batch.engine.service.BatchTaskLocalService;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(service = {})
public class BatchProcess {

	@Activate
	public void activate() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_portal.getDefaultCompanyId());

		Company company = _companyLocalService.getCompany(
			_portal.getDefaultCompanyId());

		serviceContext.setScopeGroupId(company.getGroupId());

		User user = _userLocalService.getDefaultUser(
			_portal.getDefaultCompanyId());

		serviceContext.setUserId(user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			_process(Product.class, "dependencies/json/Product.json");
			_process(Sku.class, "dependencies/json/Sku.json");
			_process(Product.class, "dependencies/csv/Product.csv");
			_process(Product.class, "dependencies/xlsx/Product.xlsx");
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private void _process(Class<?> domainClass, String resourcePath)
		throws Exception {

		BatchTaskExecutor batchJob = _batchJobFactory.create(domainClass);

		batchJob.execute(
			_batchTaskLocalService.addBatchTask(
				domainClass.getName(), _VERSION,
				StreamUtil.toByteArray(
					BatchProcess.class.getResourceAsStream(resourcePath)),
				BatchContentType.valueOf(
					StringUtil.upperCase(_file.getExtension(resourcePath))),
				BatchOperation.CREATE, _BATCH_SIZE));
	}

	private static final int _BATCH_SIZE = 100;

	private static final String _VERSION = "v0.0";

	@Reference
	private BatchTaskExecutorFactory _batchJobFactory;

	@Reference
	private BatchTaskLocalService _batchTaskLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private File _file;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}
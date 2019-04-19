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

import com.liferay.batch.engine.BatchFileImportOperation;
import com.liferay.batch.engine.demo2.internal.model.Product;
import com.liferay.batch.engine.demo2.internal.model.Sku;
import com.liferay.batch.engine.fileimport.BatchFileImportHelper;
import com.liferay.batch.engine.fileimport.BatchFileImportType;
import com.liferay.batch.engine.fileimport.BatchJobConfiguration;
import com.liferay.batch.engine.fileimport.configuration.BatchFileImportConfigurator;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(immediate = true, service = {})
public class BatchFileImportConfiguration {

	@Activate
	public void activate() throws Exception {
		_initBatchJobConfigurations();

		_batchFileImportConfigurator.register(_batchJobConfigurations);

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
			_batchFileImportHelper.process(
				"Product.json",
				_getFileResource("dependencies/json/Product.json"),
				Product.class.getSimpleName(), _VERSION,
				BatchFileImportOperation.CREATE, null, null);

			_batchFileImportHelper.process(
				"Sku.json", _getFileResource("dependencies/json/Sku.json"),
				Sku.class.getSimpleName(), _VERSION,
				BatchFileImportOperation.CREATE, null, null);

			_batchFileImportHelper.process(
				"Product.csv", _getFileResource("dependencies/csv/Product.csv"),
				Product.class.getSimpleName(), _VERSION,
				BatchFileImportOperation.CREATE, null, null);

			_batchFileImportHelper.process(
				"Product.xlsx",
				_getFileResource("dependencies/xlsx/Product.xlsx"),
				Product.class.getSimpleName(), _VERSION,
				BatchFileImportOperation.CREATE, null, null);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Deactivate
	public void deactivate() {
		_batchFileImportConfigurator.unregister(_batchJobConfigurations);
	}

	private InputStream _getFileResource(String fileName) {
		return BatchFileImportConfiguration.class.getResourceAsStream(fileName);
	}

	private void _initBatchJobConfigurations() {
		_batchJobConfigurations = new ArrayList<BatchJobConfiguration>() {
			{
				add(
					new BatchJobConfiguration(
						Product.class, _VERSION,
						BatchFileImportOperation.CREATE,
						BatchFileImportType.CSV,
						_productBatchItemWriterComponentFactory));
				add(
					new BatchJobConfiguration(
						Product.class, _VERSION,
						BatchFileImportOperation.CREATE,
						BatchFileImportType.JSON,
						_productBatchItemWriterComponentFactory));
				add(
					new BatchJobConfiguration(
						Product.class, _VERSION,
						BatchFileImportOperation.CREATE,
						BatchFileImportType.XLSX,
						_productBatchItemWriterComponentFactory));
				add(
					new BatchJobConfiguration(
						Sku.class, _VERSION, BatchFileImportOperation.CREATE,
						BatchFileImportType.JSON,
						_skuBatchItemWriterComponentFactory));
			}
		};
	}

	private static final String _VERSION = "v0.0";

	@Reference
	private BatchFileImportConfigurator _batchFileImportConfigurator;

	@Reference
	private BatchFileImportHelper _batchFileImportHelper;

	private List<BatchJobConfiguration> _batchJobConfigurations;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Portal _portal;

	@Reference(target = "(component.factory=ProductBatchFileImportWriter)")
	private ComponentFactory _productBatchItemWriterComponentFactory;

	@Reference(target = "(component.factory=SkuBatchFileImportWriter)")
	private ComponentFactory _skuBatchItemWriterComponentFactory;

	@Reference
	private UserLocalService _userLocalService;

}
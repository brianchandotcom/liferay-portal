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

package com.liferay.batch.engine.internal.bundle;

import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.portal.kernel.model.Company;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Alejandro Tardín
 */
public class CompanyBatchEngineUnitWrapper implements BatchEngineUnit {

	public CompanyBatchEngineUnitWrapper(
		BatchEngineUnit batchEngineUnit, Company company) {

		_batchEngineUnit = batchEngineUnit;
		_company = company;
	}

	@Override
	public BatchEngineUnitConfiguration getBatchEngineUnitConfiguration()
		throws IOException {

		BatchEngineUnitConfiguration batchEngineUnitConfiguration =
			_batchEngineUnit.getBatchEngineUnitConfiguration();

		return new BatchEngineUnitConfiguration() {
			{
				setCallbackURL(batchEngineUnitConfiguration.getCallbackURL());
				setClassName(batchEngineUnitConfiguration.getClassName());
				setCompanyId(_company.getCompanyId());
				setFieldNameMappingMap(
					batchEngineUnitConfiguration.getFieldNameMappingMap());
				setMultiCompany(batchEngineUnitConfiguration.isMultiCompany());
				setParameters(batchEngineUnitConfiguration.getParameters());
				setTaskItemDelegateName(
					batchEngineUnitConfiguration.getTaskItemDelegateName());
				setUserId(batchEngineUnitConfiguration.getUserId());
				setVersion(batchEngineUnitConfiguration.getVersion());
			}
		};
	}

	@Override
	public InputStream getConfigurationInputStream() throws IOException {
		return _batchEngineUnit.getConfigurationInputStream();
	}

	@Override
	public String getDataFileName() {
		return _batchEngineUnit.getDataFileName();
	}

	@Override
	public InputStream getDataInputStream() throws IOException {
		return _batchEngineUnit.getDataInputStream();
	}

	@Override
	public String getFileName() {
		return _batchEngineUnit.getFileName();
	}

	@Override
	public boolean isValid() {
		return _batchEngineUnit.isValid();
	}

	private final BatchEngineUnit _batchEngineUnit;
	private final Company _company;

}
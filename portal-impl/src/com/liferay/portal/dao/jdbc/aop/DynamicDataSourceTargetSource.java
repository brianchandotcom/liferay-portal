/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.jdbc.aop;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.sql.DataSource;

import org.springframework.aop.TargetSource;

/**
 * @author Michael Young
 */
public class DynamicDataSourceTargetSource implements TargetSource {

	public Object getTarget() throws Exception {
		Operation operationType =
			_dynamicDataSourceOperationSource.getOperation();

		if (operationType == Operation.READ) {
			if (_log.isTraceEnabled()) {
				_log.trace("Returning read data source");
			}

			return _readDataSource;
		}
		else {
			if (_log.isTraceEnabled()) {
				_log.trace("Returning write data source");
			}

			return _writeDataSource;
		}
	}

	public Class<DataSource> getTargetClass() {
		return DataSource.class;
	}

	public boolean isStatic() {
		return false;
	}

	public void releaseTarget(Object target) throws Exception {
	}

	public void setDynamicDataSourceOperationSource(
		DynamicDataSourceOperationSource dynamicDataSourceOperationSource) {

		_dynamicDataSourceOperationSource = dynamicDataSourceOperationSource;
	}

	public void setReadDataSource(DataSource readDataSource) {
		_readDataSource = readDataSource;
	}

	public void setWriteDataSource(DataSource writeDataSource) {
		_writeDataSource = writeDataSource;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DynamicDataSourceTargetSource.class);

	private DynamicDataSourceOperationSource _dynamicDataSourceOperationSource;
	private DataSource _readDataSource;
	private DataSource _writeDataSource;

}
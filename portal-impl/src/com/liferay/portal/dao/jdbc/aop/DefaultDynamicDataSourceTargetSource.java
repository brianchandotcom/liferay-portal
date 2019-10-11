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

package com.liferay.portal.dao.jdbc.aop;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.dao.jdbc.aop.DynamicDataSourceTargetSource;
import com.liferay.portal.kernel.dao.jdbc.aop.Operation;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Deque;
import java.util.LinkedList;

import javax.sql.DataSource;

import org.springframework.aop.TargetSource;

/**
 * @author Michael Young
 */
public class DefaultDynamicDataSourceTargetSource
	implements DynamicDataSourceTargetSource, TargetSource {

	@Override
	public Operation getOperation() {
		Deque<Operation> operations = _operations.get();

		Operation operation = operations.peek();

		if (operation == null) {
			operation = Operation.WRITE;
		}

		return operation;
	}

	@Override
	public DataSource getReadDataSource() {
		return _readDataSource;
	}

	@Override
	public Object getTarget() {
		Operation operationType = getOperation();

		if (operationType == Operation.READ) {
			if (_log.isTraceEnabled()) {
				_log.trace("Returning read data source");
			}

			return _readDataSource;
		}

		if (_log.isTraceEnabled()) {
			_log.trace("Returning write data source");
		}

		return _writeDataSource;
	}

	@Override
	public Class<DataSource> getTargetClass() {
		return DataSource.class;
	}

	@Override
	public DataSource getWriteDataSource() {
		return _writeDataSource;
	}

	@Override
	public boolean isStatic() {
		return false;
	}

	@Override
	public Operation popOperation() {
		Deque<Operation> operations = _operations.get();

		return operations.pop();
	}

	@Override
	public void pushOperation(Operation operation) {
		Deque<Operation> operations = _operations.get();

		operations.push(operation);
	}

	@Override
	public void releaseTarget(Object target) throws Exception {
	}

	@Override
	public void setReadDataSource(DataSource readDataSource) {
		_readDataSource = readDataSource;
	}

	@Override
	public void setWriteDataSource(DataSource writeDataSource) {
		_writeDataSource = writeDataSource;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected boolean inOperation() {
		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultDynamicDataSourceTargetSource.class);

	private static final ThreadLocal<Deque<Operation>> _operations =
		new CentralizedThreadLocal<>(
			DefaultDynamicDataSourceTargetSource.class + "._operations",
			LinkedList::new);

	private DataSource _readDataSource;
	private DataSource _writeDataSource;

}
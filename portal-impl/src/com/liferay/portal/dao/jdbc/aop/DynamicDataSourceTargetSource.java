/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.Stack;

import javax.sql.DataSource;

import org.springframework.aop.TargetSource;

/**
 * @author Michael Young
 */
public class DynamicDataSourceTargetSource implements TargetSource {

	public Stack<String> getMethodStack() {
		Stack<String> methodStack = _methodStack.get();

		if (methodStack == null) {
			methodStack = new Stack<String>();

			_methodStack.set(methodStack);
		}

		return methodStack;
	}

	public Operation getOperation() {
		Operation operation = _operationType.get();

		if (operation == null) {
			operation = Operation.WRITE;

			_operationType.set(operation);
		}

		return operation;
	}

	@Override
	public Object getTarget() throws Exception {
		Operation operationType = getOperation();

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

	@Override
	public Class<DataSource> getTargetClass() {
		return DataSource.class;
	}

	@Override
	public boolean isStatic() {
		return false;
	}

	public String popMethod() {
		Stack<String> methodStack = getMethodStack();

		String method = methodStack.pop();

		setOperation(Operation.WRITE);

		return method;
	}

	public void pushMethod(String method) {
		Stack<String> methodStack = getMethodStack();

		methodStack.push(method);
	}

	@Override
	public void releaseTarget(Object target) throws Exception {
	}

	public void setOperation(Operation operation) {
		if (_log.isDebugEnabled()) {
			_log.debug("Method stack " + getMethodStack());
		}

		if (!inOperation() || (operation == Operation.WRITE)) {
			_operationType.set(operation);
		}
	}

	public void setReadDataSource(DataSource readDataSource) {
		_readDataSource = readDataSource;
	}

	public void setWriteDataSource(DataSource writeDataSource) {
		_writeDataSource = writeDataSource;
	}

	protected boolean inOperation() {
		Stack<String> methodStack = getMethodStack();

		return !methodStack.empty();
	}

	private static Log _log = LogFactoryUtil.getLog(
		DynamicDataSourceTargetSource.class);

	private static ThreadLocal<Stack<String>> _methodStack =
		new ThreadLocal<Stack<String>>();
	private static ThreadLocal<Operation> _operationType =
		new ThreadLocal<Operation>();

	private DataSource _readDataSource;
	private DataSource _writeDataSource;

}
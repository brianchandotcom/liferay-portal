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

import java.util.Stack;

/**
 * @author Michael Young
 */
public class DynamicDataSourceOperationSource {

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

	public void setOperation(Operation operation) {
		if (_log.isDebugEnabled()) {
			_log.debug("Method stack " + getMethodStack());
		}

		if (!inOperation() || (operation == Operation.WRITE)) {
			_operationType.set(operation);
		}
	}

	protected boolean inOperation() {
		Stack<String> methodStack = getMethodStack();

		return !methodStack.empty();
	}

	private static Log _log = LogFactoryUtil.getLog(
		DynamicDataSourceOperationSource.class);

	private static ThreadLocal<Stack<String>> _methodStack =
		new ThreadLocal<Stack<String>>();
	private static ThreadLocal<Operation> _operationType =
		new ThreadLocal<Operation>();

}
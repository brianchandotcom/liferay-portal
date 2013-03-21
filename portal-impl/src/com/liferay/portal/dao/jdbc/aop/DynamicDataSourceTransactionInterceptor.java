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

import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

/**
 * @author Michael Young
 */
public class DynamicDataSourceTransactionInterceptor
	implements MethodInterceptor {

	public void afterPropertiesSet() {
		if (_dynamicDataSourceTargetSource == null) {
			_dynamicDataSourceTargetSource =
				(DynamicDataSourceTargetSource)InfrastructureUtil.
					getDynamicDataSourceTargetSource();
		}
	}

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (_dynamicDataSourceTargetSource == null) {
			return _transactionInterceptor.invoke(methodInvocation);
		}

		Class<?> targetClass = null;

		if (methodInvocation.getThis() != null) {
			Object thisObject = methodInvocation.getThis();

			targetClass = thisObject.getClass();
		}

		Method targetMethod = methodInvocation.getMethod();

		TransactionAttribute transactionAttribute =
			_transactionAttributeSource.getTransactionAttribute(
				targetMethod, targetClass);

		if ((transactionAttribute != null) &&
			transactionAttribute.isReadOnly()) {

			_dynamicDataSourceTargetSource.setOperation(Operation.READ);
		}
		else {
			_dynamicDataSourceTargetSource.setOperation(Operation.WRITE);
		}

		_dynamicDataSourceTargetSource.pushMethod(
			targetClass.getName().concat(StringPool.PERIOD).concat(
				targetMethod.getName()));

		Object returnValue = null;

		try {
			returnValue = _transactionInterceptor.invoke(methodInvocation);
		}
		finally {
			_dynamicDataSourceTargetSource.popMethod();
		}

		return returnValue;
	}

	public void setDynamicDataSourceTargetSource(
		DynamicDataSourceTargetSource dynamicDataSourceTargetSource) {

		_dynamicDataSourceTargetSource = dynamicDataSourceTargetSource;
	}

	public void setTransactionAttributeSource(
		TransactionAttributeSource transactionAttributeSource) {

		_transactionAttributeSource = transactionAttributeSource;
	}

	public void setTransactionInterceptor(
		MethodInterceptor transactionInterceptor) {

		_transactionInterceptor = transactionInterceptor;
	}

	private DynamicDataSourceTargetSource _dynamicDataSourceTargetSource;
	private TransactionAttributeSource _transactionAttributeSource;
	private MethodInterceptor _transactionInterceptor;

}
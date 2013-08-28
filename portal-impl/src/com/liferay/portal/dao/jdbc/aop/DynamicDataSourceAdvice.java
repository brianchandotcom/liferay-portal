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

import com.liferay.portal.kernel.dao.jdbc.UseDefaultDataSource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

/**
 * @author László Csontos
 */
public class DynamicDataSourceAdvice
	extends AnnotationChainableMethodAdvice<UseDefaultDataSource> {

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		if (skipAdvice(methodInvocation)) {
			return null;
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

		Operation operation = Operation.WRITE;

		UseDefaultDataSource useDefaultDataSource = findAnnotation(
			methodInvocation);

		if ((transactionAttribute != null) &&
			transactionAttribute.isReadOnly() &&
			(useDefaultDataSource == _nullUseDefaultDataSource)) {

			operation = Operation.READ;
		}

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(4);

			sb.append("Changing dynamic data source from ");
			sb.append(_dynamicDataSourceTargetSource.getOperation());
			sb.append(" to ");
			sb.append(operation);
			sb.append(" for ");
			sb.append(methodInvocation.toString());

			_log.debug(sb.toString());
		}

		_dynamicDataSourceTargetSource.setOperation(operation);

		_dynamicDataSourceTargetSource.pushMethod(
			targetClass.getName().concat(StringPool.PERIOD).concat(
				targetMethod.getName()));

		return null;
	}

	@Override
	public void duringFinally(MethodInvocation methodInvocation) {
		if (_dynamicDataSourceTargetSource != null) {
			_dynamicDataSourceTargetSource.popMethod();
		}
	}

	@Override
	public UseDefaultDataSource getNullAnnotation() {
		return _nullUseDefaultDataSource;
	}

	public void setDynamicDataSourceTargetSource(
		DynamicDataSourceTargetSource dynamicDataSourceTargetSource) {

		_dynamicDataSourceTargetSource = dynamicDataSourceTargetSource;
	}

	public void setTransactionAttributeSource(
		TransactionAttributeSource transactionAttributeSource) {

		_transactionAttributeSource = transactionAttributeSource;
	}

	protected boolean skipAdvice(MethodInvocation methodInvocation) {
		boolean skip = false;

		if (_dynamicDataSourceTargetSource == null) {
			skip = true;

			serviceBeanAopCacheManager.removeMethodInterceptor(
				methodInvocation, this);

			if (_log.isDebugEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append(this.getClass());
				sb.append(" for ");
				sb.append(methodInvocation.toString());
				sb.append(" has been removed");

				_log.debug(sb.toString());
			}
		}

		return skip;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DynamicDataSourceAdvice.class);

	private static UseDefaultDataSource _nullUseDefaultDataSource =
		new UseDefaultDataSource() {

		@Override
		public Class<? extends Annotation> annotationType() {
			return UseDefaultDataSource.class;
		}
	};

	private DynamicDataSourceTargetSource _dynamicDataSourceTargetSource;
	private TransactionAttributeSource _transactionAttributeSource;

}
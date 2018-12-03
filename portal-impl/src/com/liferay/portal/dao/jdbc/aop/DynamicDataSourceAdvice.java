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

import com.liferay.portal.kernel.dao.jdbc.aop.DynamicDataSourceTargetSource;
import com.liferay.portal.kernel.dao.jdbc.aop.MasterDataSource;
import com.liferay.portal.kernel.dao.jdbc.aop.Operation;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManager;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;
import com.liferay.portal.spring.transaction.TransactionInterceptor;

import java.lang.reflect.Method;

import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Shuyang Zhou
 * @author László Csontos
 */
public class DynamicDataSourceAdvice
	extends AnnotationChainableMethodAdvice<MasterDataSource> {

	public DynamicDataSourceAdvice() {
		super(MasterDataSource.class);
	}

	@Override
	public Object before(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation)
		throws Throwable {

		Operation operation = Operation.WRITE;

		MasterDataSource masterDataSource = findAnnotation(
			serviceBeanMethodInvocation);

		if (masterDataSource == null) {
			TransactionAttribute transactionAttribute =
				_transactionInterceptor.getTransactionAttribute(
					serviceBeanMethodInvocation);

			if (transactionAttribute.isReadOnly()) {
				operation = Operation.READ;
			}
		}

		_dynamicDataSourceTargetSource.pushOperation(operation);

		return null;
	}

	@Override
	public void duringFinally(
		ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

		_dynamicDataSourceTargetSource.popOperation();
	}

	@Override
	public boolean isEnabled(
		Class<?> targetClass, Method method,
		AnnotationHelper annotationHelper) {

		if (!_transactionInterceptor.isEnabled(
				targetClass, method, annotationHelper)) {

			return false;
		}

		super.isEnabled(targetClass, method, annotationHelper);

		return true;
	}

	public void setDynamicDataSourceTargetSource(
		DynamicDataSourceTargetSource dynamicDataSourceTargetSource) {

		_dynamicDataSourceTargetSource = dynamicDataSourceTargetSource;
	}

	public void setTransactionInterceptor(
		TransactionInterceptor transactionInterceptor) {

		_transactionInterceptor = transactionInterceptor;
	}

	@Override
	protected void setServiceBeanAopCacheManager(
		ServiceBeanAopCacheManager serviceBeanAopCacheManager) {

		super.setServiceBeanAopCacheManager(serviceBeanAopCacheManager);
	}

	private DynamicDataSourceTargetSource _dynamicDataSourceTargetSource;
	private TransactionInterceptor _transactionInterceptor;

}
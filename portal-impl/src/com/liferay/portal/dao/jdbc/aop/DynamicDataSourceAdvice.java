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

import com.liferay.portal.kernel.dao.jdbc.aop.MasterDataSource;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManager;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

/**
 * @author Shuyang Zhou
 * @author László Csontos
 */
public class DynamicDataSourceAdvice extends ChainableMethodAdvice {

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		Operation operation = Operation.WRITE;

		Object targetBean = methodInvocation.getThis();

		Class<?> targetClass = targetBean.getClass();

		Method targetMethod = methodInvocation.getMethod();

		MasterDataSource masterDataSource =
			ServiceBeanAopCacheManager.getAnnotation(
				methodInvocation, MasterDataSource.class,
				_nullMasterDataSource);

		if (masterDataSource == _nullMasterDataSource) {
			TransactionAttribute transactionAttribute =
				_transactionAttributeSource.getTransactionAttribute(
					targetMethod, targetClass);

			if ((transactionAttribute != null) &&
				transactionAttribute.isReadOnly()) {

				operation = Operation.READ;
			}
		}

		_dynamicDataSourceTargetSource.setOperation(operation);

		String targetClassName = targetClass.getName();

		_dynamicDataSourceTargetSource.pushMethod(
			targetClassName.concat(StringPool.PERIOD).concat(
				targetMethod.getName()));

		return null;
	}

	@Override
	public void duringFinally(MethodInvocation methodInvocation) {
		_dynamicDataSourceTargetSource.popMethod();
	}

	public void setDynamicDataSourceTargetSource(
		DynamicDataSourceTargetSource dynamicDataSourceTargetSource) {

		_dynamicDataSourceTargetSource = dynamicDataSourceTargetSource;
	}

	public void setTransactionAttributeSource(
		TransactionAttributeSource transactionAttributeSource) {

		_transactionAttributeSource = transactionAttributeSource;
	}

	@Override
	protected void setServiceBeanAopCacheManager(
		ServiceBeanAopCacheManager serviceBeanAopCacheManager) {

		if (this.serviceBeanAopCacheManager != null) {
			return;
		}

		this.serviceBeanAopCacheManager = serviceBeanAopCacheManager;

		serviceBeanAopCacheManager.registerAnnotationChainableMethodAdvice(
			MasterDataSource.class, null);
	}

	private static final MasterDataSource _nullMasterDataSource =
		new MasterDataSource() {

			@Override
			public Class<? extends MasterDataSource> annotationType() {
				return MasterDataSource.class;
			}

		};

	private DynamicDataSourceTargetSource _dynamicDataSourceTargetSource;
	private TransactionAttributeSource _transactionAttributeSource;

}
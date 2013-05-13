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

package com.liferay.portal.spring.transaction;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;

/**
 * @author Shuyang Zhou
 */
public class TransactionInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();

		Class<?> targetClass = null;

		Object targetBean = methodInvocation.getThis();

		if (targetBean != null) {
			targetClass = targetBean.getClass();
		}

		TransactionAttribute transactionAttribute =
			transactionAttributeSource.getTransactionAttribute(
				method, targetClass);

		if (transactionAttribute == null) {
			return methodInvocation.proceed();
		}

		return transactionExecutor.execute(
			platformTransactionManager, transactionAttribute, methodInvocation);
	}

	public void setPlatformTransactionManager(
		PlatformTransactionManager platformTransactionManager) {

		if (platformTransactionManager instanceof
				CallbackPreferringPlatformTransactionManager) {

			transactionExecutor = new CallbackPreferringTransactionExecutor();
		}
		else {
			transactionExecutor = new DefaultTransactionExecutor();
		}

		this.platformTransactionManager = platformTransactionManager;
	}

	public void setTransactionAttributeSource(
		TransactionAttributeSource transactionAttributeSource) {

		this.transactionAttributeSource = transactionAttributeSource;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link
	 *             #setPlatformTransactionManager(PlatformTransactionManager)}
	 */
	public void setTransactionManager(
		PlatformTransactionManager platformTransactionManager) {

		setPlatformTransactionManager(platformTransactionManager);
	}

	protected PlatformTransactionManager platformTransactionManager;
	protected TransactionAttributeSource transactionAttributeSource;
	protected TransactionExecutor transactionExecutor;

}
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.cache.transactional.TransactionalPortalCacheHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

/**
 * @author Shuyang Zhou
 */
public class TransactionInterceptor
	extends TransactionAspectSupport implements MethodInterceptor {

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();

		Class<?> targetClass = null;

		Object targetBean = methodInvocation.getThis();

		if (targetBean != null) {
			targetClass = targetBean.getClass();
		}

		TransactionAttributeSource transactionAttributeSource =
			getTransactionAttributeSource();

		TransactionAttribute transactionAttribute =
			transactionAttributeSource.getTransactionAttribute(
				method, targetClass);

		if (transactionAttribute == null) {
			return methodInvocation.proceed();
		}

		Class<?> declaringClass = method.getDeclaringClass();

		String joinPointIdentification = StringPool.BLANK;

		if (_log.isDebugEnabled()) {
			joinPointIdentification =
				declaringClass.getName().concat(StringPool.PERIOD).concat(
					method.getName());
		}

		TransactionInfo transactionInfo = createTransactionIfNecessary(
			getTransactionManager(), transactionAttribute,
			joinPointIdentification);

		TransactionStatus transactionStatus =
			transactionInfo.getTransactionStatus();

		boolean newTransaction = transactionStatus.isNewTransaction();

		if (newTransaction) {
			TransactionalPortalCacheHelper.begin();
		}

		Object returnValue = null;

		try {
			returnValue = methodInvocation.proceed();
		}
		catch (Throwable throwable) {
			if (newTransaction) {
				TransactionalPortalCacheHelper.rollback();
			}

			completeTransactionAfterThrowing(transactionInfo, throwable);

			throw throwable;
		}
		finally {
			cleanupTransactionInfo(transactionInfo);
		}

		commitTransactionAfterReturning(transactionInfo);

		if (newTransaction) {
			TransactionalPortalCacheHelper.commit();
		}

		return returnValue;
	}

	private static Log _log = LogFactoryUtil.getLog(
		TransactionInterceptor.class);

}
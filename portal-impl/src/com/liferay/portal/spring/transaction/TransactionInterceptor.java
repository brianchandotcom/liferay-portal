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

package com.liferay.portal.spring.transaction;

import com.liferay.portal.cache.transactional.TransactionalPortalCacheHelper;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.spring.hibernate.LastSessionRecorderUtil;

import java.lang.reflect.Method;

import java.util.List;
import java.util.concurrent.Callable;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

/**
 * @author Shuyang Zhou
 */
public class TransactionInterceptor implements MethodInterceptor {

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

		TransactionStatus transactionStatus =
			_platformTransactionManager.getTransaction(transactionAttribute);

		boolean newTransaction = transactionStatus.isNewTransaction();

		if (newTransaction) {
			TransactionalPortalCacheHelper.begin();

			TransactionCommitCallbackUtil.pushCallbackList();
		}

		Object returnValue = null;

		try {
			if (newTransaction) {
				LastSessionRecorderUtil.syncLastSessionState();
			}

			returnValue = methodInvocation.proceed();
		}
		catch (Throwable throwable) {
			processThrowable(
				throwable, transactionAttribute, transactionStatus);
		}

		processCommit(transactionStatus);

		return returnValue;
	}

	public void setPlatformTransactionManager(
		PlatformTransactionManager platformTransactionManager) {

		_platformTransactionManager = platformTransactionManager;
	}

	public void setTransactionAttributeSource(
		TransactionAttributeSource transactionAttributeSource) {

		this.transactionAttributeSource = transactionAttributeSource;
	}

	/**
	 * @deprecated {@link
	 *             #setPlatformTransactionManager(PlatformTransactionManager)}
	 */
	public void setTransactionManager(
		PlatformTransactionManager platformTransactionManager) {

		_platformTransactionManager = platformTransactionManager;
	}

	protected void invokeCallbacks() {
		List<Callable<?>> callables =
			TransactionCommitCallbackUtil.popCallbackList();

		for (Callable<?> callable : callables) {
			try {
				callable.call();
			}
			catch (Exception e) {
				_log.error("Failed to execute transaction commit callback", e);
			}
		}
	}

	protected void processCommit(TransactionStatus transactionStatus) {
		boolean hasError = false;

		try {
			_platformTransactionManager.commit(transactionStatus);
		}
		catch (TransactionSystemException tse) {
			_log.error(
				"Application exception overridden by commit exception", tse);

			hasError = true;

			throw tse;
		}
		catch (RuntimeException re) {
			_log.error(
				"Application exception overridden by commit exception", re);

			hasError = true;

			throw re;
		}
		catch (Error e) {
			_log.error("Application exception overridden by commit error", e);

			hasError = true;

			throw e;
		}
		finally {
			if (transactionStatus.isNewTransaction()) {
				if (hasError) {
					TransactionalPortalCacheHelper.rollback();

					TransactionCommitCallbackUtil.popCallbackList();

					EntityCacheUtil.clearLocalCache();
					FinderCacheUtil.clearLocalCache();
				}
				else {
					TransactionalPortalCacheHelper.commit();

					invokeCallbacks();
				}
			}
		}
	}

	protected void processThrowable(
			Throwable throwable, TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus)
		throws Throwable {

		if (transactionAttribute.rollbackOn(throwable)) {
			try {
				_platformTransactionManager.rollback(transactionStatus);
			}
			catch (TransactionSystemException tse) {
				_log.error(
					"Application exception overridden by rollback exception",
					tse);

				throw tse;
			}
			catch (RuntimeException re) {
				_log.error(
					"Application exception overridden by rollback exception",
					re);

				throw re;
			}
			catch (Error e) {
				_log.error(
					"Application exception overridden by rollback error", e);

				throw e;
			}
			finally {
				if (transactionStatus.isNewTransaction()) {
					TransactionalPortalCacheHelper.rollback();

					TransactionCommitCallbackUtil.popCallbackList();

					EntityCacheUtil.clearLocalCache();
					FinderCacheUtil.clearLocalCache();
				}
			}
		}
		else {
			processCommit(transactionStatus);
		}

		throw throwable;
	}

	protected TransactionAttributeSource transactionAttributeSource;

	private static Log _log = LogFactoryUtil.getLog(
		TransactionInterceptor.class);

	private PlatformTransactionManager _platformTransactionManager;

}
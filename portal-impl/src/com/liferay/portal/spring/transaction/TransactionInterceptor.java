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
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;

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

		if (_doCallback) {
			return _doCallbackInvoke(transactionAttribute, methodInvocation);
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

		if (_platformTransactionManager instanceof
			CallbackPreferringPlatformTransactionManager) {

			_doCallback = true;
		}
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

	private Object _doCallbackInvoke(
			final TransactionAttribute transactionAttribute,
			final MethodInvocation methodInvocation)
		throws Throwable {

		CallbackPreferringPlatformTransactionManager
			callbackPreferringPlatformTransactionManager =
				(CallbackPreferringPlatformTransactionManager)
					_platformTransactionManager;

		Object result = callbackPreferringPlatformTransactionManager.execute(
			transactionAttribute, new TransactionCallback<Object>() {

				public Object doInTransaction(
					TransactionStatus transactionStatus) {

					boolean newTransaction =
						transactionStatus.isNewTransaction();

					if (newTransaction) {
						TransactionalPortalCacheHelper.begin();

						TransactionCommitCallbackUtil.pushCallbackList();
					}

					boolean rollback = false;

					try {
						if (newTransaction) {
							LastSessionRecorderUtil.syncLastSessionState();
						}

						return methodInvocation.proceed();
					}
					catch (Throwable throwable) {
						if (transactionAttribute.rollbackOn(throwable)) {
							if (newTransaction) {
								TransactionalPortalCacheHelper.rollback();

								TransactionCommitCallbackUtil.popCallbackList();

								EntityCacheUtil.clearLocalCache();
								FinderCacheUtil.clearLocalCache();

								rollback = true;
							}

							if (throwable instanceof RuntimeException) {
								throw (RuntimeException)throwable;
							}
							else {
								throw new RuntimeException(throwable);
							}
						}
						else {
							return new ThrowableHolder(throwable);
						}
					}
					finally {
						if (newTransaction && !rollback) {
							TransactionalPortalCacheHelper.commit();

							invokeCallbacks();
						}
					}
				}
			});

		if (result instanceof ThrowableHolder) {
			ThrowableHolder throwableHolder = (ThrowableHolder)result;

			throw throwableHolder.getThrowable();
		}
		else {
			return result;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		TransactionInterceptor.class);

	private boolean _doCallback;

	private PlatformTransactionManager _platformTransactionManager;

	private static class ThrowableHolder {

		public ThrowableHolder(Throwable throwable) {
			_throwable = throwable;
		}

		public Throwable getThrowable() {
			return _throwable;
		}

		private final Throwable _throwable;

	}

}
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

package com.liferay.portal.spring.transaction;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.lang.reflect.Method;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Shuyang Zhou
 */
public class TransactionInterceptor
	extends AnnotationChainableMethodAdvice<Transactional> {

	public TransactionInterceptor() {
		super(Transactional.class);
	}

	public TransactionAttribute getTransactionAttribute(
		ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

		Object target = serviceBeanMethodInvocation.getThis();

		return _transactionAttributes.get(
			new CacheKey(
				target.getClass(), serviceBeanMethodInvocation.getMethod()));
	}

	@Override
	public Object invoke(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation)
		throws Throwable {

		TransactionAttribute transactionAttribute = getTransactionAttribute(
			serviceBeanMethodInvocation);

		if (transactionAttribute == null) {
			return serviceBeanMethodInvocation.proceed();
		}

		TransactionAttributeAdapter transactionAttributeAdapter =
			new TransactionAttributeAdapter(transactionAttribute);

		return transactionExecutor.execute(
			transactionAttributeAdapter, serviceBeanMethodInvocation);
	}

	@Override
	public boolean isEnabled(
		Class<?> targetClass, Method method,
		AnnotationHelper annotationHelper) {

		Transactional transactional = annotationHelper.findAnnotation(
			targetClass, method, Transactional.class);

		if (transactional == null) {
			return false;
		}

		TransactionAttribute transactionAttribute =
			TransactionAttributeBuilder.build(transactional);

		if (transactionAttribute == null) {
			return false;
		}

		_transactionAttributes.put(
			new CacheKey(targetClass, method), transactionAttribute);

		return true;
	}

	public void setTransactionExecutor(
		TransactionExecutor transactionExecutor) {

		this.transactionExecutor = transactionExecutor;
	}

	protected TransactionExecutor transactionExecutor;

	private final ConcurrentMap<CacheKey, TransactionAttribute>
		_transactionAttributes = new ConcurrentHashMap<>();

	private static class CacheKey {

		@Override
		public boolean equals(Object obj) {
			CacheKey cacheKey = (CacheKey)obj;

			if (Objects.equals(_targetClass, cacheKey._targetClass) &&
				Objects.equals(_method, cacheKey._method)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _targetClass);

			return HashUtil.hash(hash, _method);
		}

		private CacheKey(Class<?> targetClass, Method method) {
			_targetClass = targetClass;
			_method = method;
		}

		private final Method _method;
		private final Class<?> _targetClass;

	}

}
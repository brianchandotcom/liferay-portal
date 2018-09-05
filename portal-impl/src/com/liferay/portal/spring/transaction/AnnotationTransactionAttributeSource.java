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

import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.portal.kernel.transaction.Transactional;

import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

/**
 * @author Shuyang Zhou
 */
public class AnnotationTransactionAttributeSource
	implements TransactionAttributeSource {

	@Override
	public TransactionAttribute getTransactionAttribute(
		Method method, Class<?> targetClass) {

		Map<Method, TransactionAttribute> transactionAttributes =
			_transactionAttributes.get(targetClass);

		if (transactionAttributes == null) {
			transactionAttributes = new ConcurrentHashMap<>();

			Map<Method, TransactionAttribute> previousTransactionAttributes =
				_transactionAttributes.putIfAbsent(
					targetClass, transactionAttributes);

			if (previousTransactionAttributes != null) {
				transactionAttributes = previousTransactionAttributes;
			}
		}

		TransactionAttribute transactionAttribute = transactionAttributes.get(
			method);

		if (transactionAttribute != null) {
			if (transactionAttribute == _nullTransactionAttribute) {
				return null;
			}

			return transactionAttribute;
		}

		Transactional transactional = AnnotationLocator.locate(
			method, targetClass, Transactional.class);

		transactionAttribute = TransactionAttributeBuilder.build(transactional);

		if (transactionAttribute == null) {
			transactionAttributes.put(method, _nullTransactionAttribute);
		}
		else {
			transactionAttributes.put(method, transactionAttribute);
		}

		return transactionAttribute;
	}

	private static final TransactionAttribute _nullTransactionAttribute =
		new DefaultTransactionAttribute();

	private final ConcurrentMap<Class<?>, Map<Method, TransactionAttribute>>
		_transactionAttributes = new ConcurrentHashMap<>();

}
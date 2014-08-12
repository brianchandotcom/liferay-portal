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

import com.liferay.portal.kernel.annotation.AnnotationLocator;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.MethodTargetClassKey;

import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

		MethodTargetClassKey methodTargetClassKey = new MethodTargetClassKey(
			method, targetClass);

		TransactionAttribute transactionAttribute = _transactionAttributes.get(
			methodTargetClassKey);

		if (transactionAttribute != null) {
			if (transactionAttribute == _nullTransactionAttribute) {
				return null;
			}
			else {
				return transactionAttribute;
			}
		}

		Transactional transactional = AnnotationLocator.locate(
			method, targetClass, Transactional.class);

		transactionAttribute = TransactionAttributeBuilder.build(transactional);

		if (transactionAttribute == null) {
			_transactionAttributes.put(
				methodTargetClassKey, _nullTransactionAttribute);
		}
		else {
			_transactionAttributes.put(
				methodTargetClassKey, transactionAttribute);
		}

		return transactionAttribute;
	}

	private static final TransactionAttribute _nullTransactionAttribute =
		new DefaultTransactionAttribute();

	private final Map<MethodTargetClassKey, TransactionAttribute>
		_transactionAttributes =
			new ConcurrentHashMap<MethodTargetClassKey, TransactionAttribute>();

}
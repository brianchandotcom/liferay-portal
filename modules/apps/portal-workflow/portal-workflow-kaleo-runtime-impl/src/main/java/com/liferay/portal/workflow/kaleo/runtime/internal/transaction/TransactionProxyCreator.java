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

package com.liferay.portal.workflow.kaleo.runtime.internal.transaction;

import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class TransactionProxyCreator {

	public static Object createProxy(Object target) {
		return ProxyUtil.newProxyInstance(
			TransactionProxyCreator.class.getClassLoader(),
			ReflectionUtil.getInterfaces(target),
			(proxy, method, args) -> {
				TransactionConfig transactionConfig = _build(
					method, target.getClass());

				if (transactionConfig == null) {
					return _invoke(method, target, args);
				}

				return TransactionInvokerUtil.invoke(
					transactionConfig, () -> _invoke(method, target, args));
			});
	}

	private static TransactionConfig _build(Method method, Class<?> clazz) {
		Transactional transactional = AnnotationLocator.locate(
			method, clazz, Transactional.class);

		if ((transactional == null) || !transactional.enabled()) {
			return null;
		}

		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setIsolation(transactional.isolation());
		builder.setPropagation(transactional.propagation());
		builder.setReadOnly(transactional.readOnly());
		builder.setRollbackForClasses(transactional.rollbackFor());
		builder.setRollbackForClassNames(transactional.rollbackForClassName());
		builder.setNoRollbackForClasses(transactional.noRollbackFor());
		builder.setNoRollbackForClassNames(
			transactional.noRollbackForClassName());
		builder.setTimeout(transactional.timeout());

		return builder.build();
	}

	private static Object _invoke(Method method, Object target, Object[] args)
		throws Exception {

		try {
			return method.invoke(target, args);
		}
		catch (InvocationTargetException invocationTargetException) {
			return ReflectionUtil.throwException(
				invocationTargetException.getCause());
		}
	}

}
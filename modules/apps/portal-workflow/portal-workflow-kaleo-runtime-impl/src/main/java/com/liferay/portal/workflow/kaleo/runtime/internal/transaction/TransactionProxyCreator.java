/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
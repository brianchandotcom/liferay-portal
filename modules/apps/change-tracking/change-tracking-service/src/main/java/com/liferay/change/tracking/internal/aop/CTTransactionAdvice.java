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

package com.liferay.change.tracking.internal.aop;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTTransactionException;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.spring.transaction.TransactionExecutorThreadLocal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 */
@Component(service = ChainableMethodAdvice.class)
public class CTTransactionAdvice extends ChainableMethodAdvice {

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		Transactional transactional = (Transactional)annotations.get(
			Transactional.class);

		if ((transactional == null) || !transactional.enabled()) {
			return null;
		}

		CTAware ctAware = (CTAware)annotations.get(CTAware.class);

		if (ctAware != null) {
			if (ctAware.onProduction()) {
				return CTMode.REQUIRES_NEW;
			}

			return null;
		}

		if (transactional.readOnly()) {
			return CTMode.READ_ONLY;
		}

		if (transactional.propagation() == Propagation.REQUIRES_NEW) {
			return CTMode.REQUIRES_NEW;
		}

		return CTMode.STRICT;
	}

	@Override
	public Object invoke(
			AopMethodInvocation aopMethodInvocation, Object[] arguments)
		throws Throwable {

		if (CTCollectionThreadLocal.isProductionMode()) {
			return aopMethodInvocation.proceed(arguments);
		}

		CTMode ctMode = aopMethodInvocation.getAdviceMethodContext();

		if ((ctMode == CTMode.REQUIRES_NEW) ||
			(TransactionExecutorThreadLocal.getCurrentTransactionExecutor() ==
				null)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return aopMethodInvocation.proceed(arguments);
			}
		}
		else if (ctMode == CTMode.READ_ONLY) {
			return aopMethodInvocation.proceed(arguments);
		}

		throw new CTTransactionException(
			"CT transaction validation failure. Nested operation using " +
				aopMethodInvocation.getThis() +
					" can only be performed in production mode.");
	}

	private enum CTMode {

		READ_ONLY, REQUIRES_NEW, STRICT

	}

}
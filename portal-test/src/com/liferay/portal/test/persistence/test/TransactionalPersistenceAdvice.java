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

package com.liferay.portal.test.persistence.test;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.persistence.BasePersistence;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Miguel Pastor
 */
public class TransactionalPersistenceAdvice implements MethodInterceptor {

	public Map<Serializable, BasePersistence<?>> getBasePersistences() {
		return _basePersistences;
	}

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		BaseModel<?> baseModel = (BaseModel<?>)methodInvocation.proceed();

		BasePersistence<?> basePersistence =
			(BasePersistence<?>)methodInvocation.getThis();

		_basePersistences.put(baseModel.getPrimaryKeyObj(), basePersistence);

		return baseModel;
	}

	public void reset() {
		_basePersistences.clear();
	}

	private final Map<Serializable, BasePersistence<?>> _basePersistences =
		new HashMap<Serializable, BasePersistence<?>>();

}
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

package com.liferay.portal.test.persistence;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.persistence.BasePersistence;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * This advices stores the references to all the entities created during
 * the execution of a persistence test.
 *
 * @author Miguel Pastor
 */
public class TransactionalPersistenceAdvice implements MethodInterceptor {

	public Map<Serializable, BasePersistence<?>> getPersistedEntities() {
		return _persistedEntities;
	}

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {

		BaseModel<?> entity = (BaseModel<?>) methodInvocation.proceed();

		BasePersistence<?> persistence =
			(BasePersistence<?>) methodInvocation.getThis();

		addEntity(entity, persistence);

		return entity;
	}

	public void reset() {
		_persistedEntities.clear();
	}

	protected void addEntity(
		BaseModel<?> entity, BasePersistence<?> persistence) {

		_persistedEntities.put(entity.getPrimaryKeyObj(), persistence);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"The entity " + entity +
				" has been registered for further deletion");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		TransactionalPersistenceAdvice.class);

	private Map<Serializable, BasePersistence<?>> _persistedEntities =
		new HashMap<Serializable, BasePersistence<?>>();

}
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

package com.liferay.portal.cache.internal.dao.orm;

import com.liferay.portal.kernel.util.PropsKeys;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Tina Tian
 * @author Preston Crary
 */
public class PropsInvocationHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		String methodName = method.getName();

		if (methodName.equals("get")) {
			String key = (String)args[0];

			if (key.equals(PropsKeys.VALUE_OBJECT_ENTITY_BLOCKING_CACHE) ||
				key.equals(PropsKeys.VALUE_OBJECT_ENTITY_CACHE_ENABLED) ||
				key.equals(PropsKeys.VALUE_OBJECT_FINDER_CACHE_ENABLED) ||
				key.equals(PropsKeys.VALUE_OBJECT_MVCC_ENTITY_CACHE_ENABLED)) {

				return "true";
			}

			if (PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD.equals(
					key)) {

				return "-1";
			}
		}

		return null;
	}

}
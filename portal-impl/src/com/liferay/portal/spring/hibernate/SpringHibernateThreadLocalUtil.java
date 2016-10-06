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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.NamedThreadLocal;
import org.springframework.transaction.support.ResourceHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Shuyang Zhou
 */
public class SpringHibernateThreadLocalUtil {

	public static <T> T getResource(Object key) {
		Map<Object, Object> resources = _resourcesThreadLocal.get();

		if (resources == null) {
			return null;
		}

		Object resource = resources.get(key);

		if (resource instanceof ResourceHolder) {
			ResourceHolder resourceHolder = (ResourceHolder)resource;

			if (resourceHolder.isVoid()) {
				resources.remove(key);

				if (resources.isEmpty()) {
					_resourcesThreadLocal.remove();
				}

				return null;
			}
		}

		return (T)resource;
	}

	public static <T> T setResource(Object key, Object resource) {
		Map<Object, Object> resources = _resourcesThreadLocal.get();

		Object oldResource = null;

		if (resource == null) {
			if (resources == null) {
				return null;
			}

			oldResource = resources.remove(key);

			if (resources.isEmpty()) {
				_resourcesThreadLocal.remove();
			}
		}
		else {
			if (resources == null) {
				resources = new HashMap<>();

				_resourcesThreadLocal.set(resources);
			}

			oldResource = resources.put(key, resource);
		}

		if (oldResource instanceof ResourceHolder) {
			ResourceHolder resourceHolder = (ResourceHolder)oldResource;

			if (resourceHolder.isVoid()) {
				oldResource = null;
			}
		}

		return (T)oldResource;
	}

	private static final ThreadLocal<Map<Object, Object>> _resourcesThreadLocal;

	static {
		try {
			Field nameField = ReflectionUtil.getDeclaredField(
				NamedThreadLocal.class, "name");

			ThreadLocal<?> resourcesThreadLocal = null;

			for (Field field : ReflectionUtil.getDeclaredFields(
					TransactionSynchronizationManager.class)) {

				if (Modifier.isStatic(field.getModifiers()) &&
					ThreadLocal.class.isAssignableFrom(field.getType())) {

					ThreadLocal<Object> threadLocal =
						(ThreadLocal<Object>)field.get(null);

					Object value = threadLocal.get();

					if (threadLocal instanceof NamedThreadLocal) {
						threadLocal = new InitialThreadLocal<>(
							(String)nameField.get(threadLocal), null);
					}
					else {
						threadLocal = new CentralizedThreadLocal<>(false);
					}

					if (value != null) {
						threadLocal.set(value);
					}

					field.set(null, threadLocal);

					String name = field.getName();

					if (name.equals("resources")) {
						resourcesThreadLocal = threadLocal;
					}
				}
			}

			if (resourcesThreadLocal == null) {
				throw new ExceptionInInitializerError(
					"Unable to locate \"resources\" thread local field from " +
						TransactionSynchronizationManager.class);
			}

			_resourcesThreadLocal =
				(ThreadLocal<Map<Object, Object>>)resourcesThreadLocal;
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.hibernate;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.reflect.ReflectionUtil;

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

	public static boolean isCurrentTransactionReadOnly() {
		Boolean currentTransactionReadOnly =
			_currentTransactionReadOnlyThreadLocal.get();

		// Spring only saves TRUE or null into this thread local

		if (currentTransactionReadOnly == null) {
			return false;
		}

		return true;
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

	private static final ThreadLocal<Boolean>
		_currentTransactionReadOnlyThreadLocal;
	private static final ThreadLocal<Map<Object, Object>> _resourcesThreadLocal;

	static {
		try {
			Field nameField = ReflectionUtil.getDeclaredField(
				NamedThreadLocal.class, "name");

			ThreadLocal<?> currentTransactionReadOnlyThreadLocal = null;
			ThreadLocal<?> resourcesThreadLocal = null;

			for (Field field :
					ReflectionUtil.getDeclaredFields(
						TransactionSynchronizationManager.class)) {

				if (Modifier.isStatic(field.getModifiers()) &&
					ThreadLocal.class.isAssignableFrom(field.getType())) {

					ThreadLocal<Object> threadLocal =
						(ThreadLocal<Object>)field.get(null);

					Object value = threadLocal.get();

					if (threadLocal instanceof NamedThreadLocal) {
						threadLocal = new CentralizedThreadLocal<>(
							(String)nameField.get(threadLocal), () -> null,
							false);
					}
					else {
						threadLocal = new CentralizedThreadLocal<>(false);
					}

					if (value != null) {
						threadLocal.set(value);
					}

					field.set(null, threadLocal);

					String name = field.getName();

					if (name.equals("currentTransactionReadOnly")) {
						currentTransactionReadOnlyThreadLocal = threadLocal;
					}
					else if (name.equals("resources")) {
						resourcesThreadLocal = threadLocal;
					}
				}
			}

			if (currentTransactionReadOnlyThreadLocal == null) {
				throw new ExceptionInInitializerError(
					"Unable to locate \"currentTransactionReadOnly\" thread " +
						"local field from " +
							TransactionSynchronizationManager.class);
			}

			if (resourcesThreadLocal == null) {
				throw new ExceptionInInitializerError(
					"Unable to locate \"resources\" thread local field from " +
						TransactionSynchronizationManager.class);
			}

			_currentTransactionReadOnlyThreadLocal =
				(ThreadLocal<Boolean>)currentTransactionReadOnlyThreadLocal;

			_resourcesThreadLocal =
				(ThreadLocal<Map<Object, Object>>)resourcesThreadLocal;
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

}
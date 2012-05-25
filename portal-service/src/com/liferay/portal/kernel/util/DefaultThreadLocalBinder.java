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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class DefaultThreadLocalBinder implements ThreadLocalBinder {

	public void afterPropertiesSet() throws Exception {
		if (_threadLocalSourceMap == null) {
			throw new IllegalArgumentException("Missing ThreadLocalSourceMap");
		}

		init(getClassLoader());
	}

	public void bind() {
		Map<ThreadLocal<?>, ?> threadLocalValueMap = _threadLocalValueMap.get();

		for (Map.Entry<ThreadLocal<?>, ?> threadLocalValueEntry :
			threadLocalValueMap.entrySet()) {

			ThreadLocal<Object> threadLocal =
				(ThreadLocal<Object>)threadLocalValueEntry.getKey();
			Object value = threadLocalValueEntry.getValue();

			threadLocal.set(value);
		}
	}

	public void cleanup() {
		for (ThreadLocal<?> threadLocal : _threadLocalSet) {
			threadLocal.remove();
		}
	}

	public ClassLoader getClassLoader() {
		if (_classLoader == null) {
			Thread currentThread = Thread.currentThread();

			_classLoader = currentThread.getContextClassLoader();
		}

		return _classLoader;
	}

	public void init(ClassLoader classLoader) throws Exception {
		for (Map.Entry<String, String> threadLocalSourceEntry :
			_threadLocalSourceMap.entrySet()) {

			String className = threadLocalSourceEntry.getKey();
			String fieldName = threadLocalSourceEntry.getValue();

			Class<?> clazz = classLoader.loadClass(className);

			Field field = ReflectionUtil.getDeclaredField(clazz, fieldName);

			if (!ThreadLocal.class.isAssignableFrom(field.getType())) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						field.toString() +
							" is not type of ThreadLocal. Skip binding.");
				}

				continue;
			}

			if (!Modifier.isStatic(field.getModifiers())) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						field.toString() +
							" is not a static ThreadLocal. Skip binding.");
				}

				continue;
			}

			ThreadLocal<?> threadLocal = (ThreadLocal<?>)field.get(null);

			if (threadLocal == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						field.toString() +
							" has not initialized yet. Skip binding.");
				}

				continue;
			}

			_threadLocalSet.add(threadLocal);
		}

	}

	public void record() {
		Map<ThreadLocal<?>, Object> threadLocalValueMap =
			new HashMap<ThreadLocal<?>, Object>();

		for (ThreadLocal<?> threadLocal : _threadLocalSet) {
			Object value = threadLocal.get();

			threadLocalValueMap.put(threadLocal, value);
		}

		_threadLocalValueMap.set(threadLocalValueMap);
	}

	public void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	public void setThreadLocalSourceMap(
		Map<String, String> threadLocalSourceMap) {

		_threadLocalSourceMap = threadLocalSourceMap;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultThreadLocalBinder.class);

	private static ThreadLocal<Map<ThreadLocal<?>, ?>> _threadLocalValueMap =
		new AutoResetThreadLocal<Map<ThreadLocal<?>, ?>>(
			DefaultThreadLocalBinder.class + "._threadLocalValueMap") {

			@Override
			protected Map<ThreadLocal<?>, ?> copy(
				Map<ThreadLocal<?>, ?> threadLocalValueMap) {

				return threadLocalValueMap;
			}

		};

	private ClassLoader _classLoader;
	private Set<ThreadLocal<?>> _threadLocalSet = new HashSet<ThreadLocal<?>>();
	private Map<String, String> _threadLocalSourceMap;

}
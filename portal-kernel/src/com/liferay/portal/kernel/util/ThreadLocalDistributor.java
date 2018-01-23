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

package com.liferay.portal.kernel.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalDistributor implements Externalizable {

	public void afterPropertiesSet() throws Exception {
		if (_threadLocalSources == null) {
			throw new IllegalArgumentException("Thread local sources is null");
		}

		ClassLoader classLoader = getClassLoader();

		for (KeyValuePair keyValuePair : _threadLocalSources) {
			String className = keyValuePair.getKey();
			String fieldName = keyValuePair.getValue();

			Class<?> clazz = classLoader.loadClass(className);

			Field field = ReflectionUtil.getDeclaredField(clazz, fieldName);

			if (!ThreadLocal.class.isAssignableFrom(field.getType())) {
				if (_log.isWarnEnabled()) {
					_log.warn(fieldName + " is not of type ThreadLocal");
				}

				continue;
			}

			if (!Modifier.isStatic(field.getModifiers())) {
				if (_log.isWarnEnabled()) {
					_log.warn(fieldName + " is not a static ThreadLocal");
				}

				continue;
			}

			ThreadLocal<Serializable> threadLocal =
				(ThreadLocal<Serializable>)field.get(null);

			if (threadLocal == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(fieldName + " is not initialized");
				}

				continue;
			}

			_threadLocals.add(threadLocal);
		}

		_threadLocalValues = new Serializable[_threadLocals.size()];

		_index = ThreadLocalDistributorRegistry.addThreadLocalDistributor(this);
	}

	public void capture() {
		for (int i = 0; i < _threadLocalValues.length; i++) {
			ThreadLocal<Serializable> threadLocal = _threadLocals.get(i);

			_threadLocalValues[i] = threadLocal.get();
		}
	}

	public ClassLoader getClassLoader() {
		if (_classLoader == null) {
			Thread currentThread = Thread.currentThread();

			_classLoader = currentThread.getContextClassLoader();
		}

		return _classLoader;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_index = objectInput.readInt();
		_threadLocalValues = (Serializable[])objectInput.readObject();

		ThreadLocalDistributor threadLocalDistributor =
			ThreadLocalDistributorRegistry.getThreadLocalDistributor(_index);

		_threadLocals = threadLocalDistributor._threadLocals;
	}

	public void restore() {
		for (int i = 0; i < _threadLocalValues.length; i++) {
			ThreadLocal<Serializable> threadLocal = _threadLocals.get(i);

			threadLocal.set(_threadLocalValues[i]);
		}
	}

	public void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	public void setThreadLocalSources(List<KeyValuePair> threadLocalSources) {
		_threadLocalSources = threadLocalSources;
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeInt(_index);
		objectOutput.writeObject(_threadLocalValues);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThreadLocalDistributor.class);

	private ClassLoader _classLoader;
	private int _index;
	private List<ThreadLocal<Serializable>> _threadLocals = new ArrayList<>();
	private List<KeyValuePair> _threadLocalSources;
	private Serializable[] _threadLocalValues;

}
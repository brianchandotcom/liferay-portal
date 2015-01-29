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

package com.liferay.portal.test.util;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ModelListenerRegistrationUtil;
import com.liferay.portal.tools.DBUpgrader;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cristina González
 */
public class InitPersistenceTestImpl implements InitPersistenceTest {

	@Override
	public Object init() {
		initialize();

		Object instance = ReflectionTestUtil.getFieldValue(
			ModelListenerRegistrationUtil.class, "_instance");

		Object modelListeners = ReflectionTestUtil.getFieldValue(
			instance, "_modelListeners");

		ReflectionTestUtil.setFieldValue(
			instance, "_modelListeners",
			new ConcurrentHashMap<Class<?>, List<ModelListener<?>>>());

		CacheRegistryUtil.setActive(false);

		return modelListeners;
	}

	@Override
	public void release(Object modelListeners) {
		Object instance = ReflectionTestUtil.getFieldValue(
			ModelListenerRegistrationUtil.class, "_instance");

		CacheRegistryUtil.setActive(true);

		ReflectionTestUtil.setFieldValue(
			instance, "_modelListeners", modelListeners);
	}

	private static void initialize() {
		if (_initialized) {
			return;
		}

		try {
			DBUpgrader.upgrade();
		}
		catch (Throwable t) {
			throw new ExceptionInInitializerError(t);
		}
		finally {
			CacheRegistryUtil.setActive(true);
		}

		_initialized = true;
	}

	private static boolean _initialized;

}
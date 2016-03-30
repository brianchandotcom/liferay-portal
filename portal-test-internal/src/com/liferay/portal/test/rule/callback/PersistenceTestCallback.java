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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ModelListenerRegistrationUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.tools.DBUpgrader;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class PersistenceTestCallback extends BaseTestCallback<Object, Object> {

	public static final PersistenceTestCallback INSTANCE =
		new PersistenceTestCallback();

	@Override
	public void afterMethod(
		Description description, Object modelListeners, Object target) {

		Object instance = ReflectionTestUtil.getFieldValue(
			ModelListenerRegistrationUtil.class, "_instance");

		CacheRegistryUtil.setActive(true);

		ReflectionTestUtil.setFieldValue(
			instance, "_modelListeners", modelListeners);
	}

	@Override
	public Object beforeMethod(Description description, Object target)
		throws Exception {

		_initialize();

		Object instance = ReflectionTestUtil.getFieldValue(
			ModelListenerRegistrationUtil.class, "_instance");

		Object modelListeners = ReflectionTestUtil.getFieldValue(
			instance, "_modelListeners");

		ReflectionTestUtil.setFieldValue(
			instance, "_modelListeners",
			new ConcurrentHashMap<Class<?>, List<ModelListener<?>>>());

		CacheRegistryUtil.setActive(false);

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		return modelListeners;
	}

	private static void _initialize() {
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

	private PersistenceTestCallback() {
	}

	private static boolean _initialized;

}
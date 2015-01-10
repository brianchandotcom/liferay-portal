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

package com.liferay.portal.test;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.test.BaseTestRule;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ModelListenerRegistrationUtil;
import com.liferay.portal.tools.DBUpgrader;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class PersistenceTestRule extends BaseTestRule<Object, Object> {

	public static final PersistenceTestRule INSTANCE =
		new PersistenceTestRule();

	@Override
	protected void afterMethod(Description description, Object modelListeners) {
		Object instance = ReflectionTestUtil.getFieldValue(
			ModelListenerRegistrationUtil.class, "_instance");

		CacheRegistryUtil.setActive(true);

		ReflectionTestUtil.setFieldValue(
			instance, "_modelListeners", modelListeners);
	}

	@Override
	protected Object beforeMethod(Description description) {
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

	private PersistenceTestRule() {
	}

	private static boolean _initialized;

}
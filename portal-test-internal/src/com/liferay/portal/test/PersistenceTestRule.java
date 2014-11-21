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
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ModelListenerRegistrationUtil;
import com.liferay.portal.tools.DBUpgrader;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class PersistenceTestRule implements TestRule {

	@Override
	public Statement apply(final Statement statement, Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				Object instance = ReflectionTestUtil.getFieldValue(
					ModelListenerRegistrationUtil.class, "_instance");

				Object modelListeners = ReflectionTestUtil.getFieldValue(
					instance, "_modelListeners");

				ReflectionTestUtil.setFieldValue(
					instance, "_modelListeners",
					new ConcurrentHashMap<Class<?>, List<ModelListener<?>>>());

				CacheRegistryUtil.setActive(false);

				try {
					statement.evaluate();
				}
				finally {
					CacheRegistryUtil.setActive(true);

					ReflectionTestUtil.setFieldValue(
						instance, "_modelListeners", modelListeners);
				}
			}

		};
	}

	static {
		try {
			DBUpgrader.upgrade();

			TemplateManagerUtil.init();
		}
		catch (Throwable t) {
			throw new ExceptionInInitializerError(t);
		}
		finally {
			CacheRegistryUtil.setActive(true);
		}
	}

}
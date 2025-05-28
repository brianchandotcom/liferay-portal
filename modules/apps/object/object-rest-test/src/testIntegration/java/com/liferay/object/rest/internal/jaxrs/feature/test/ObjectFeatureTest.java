/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.jaxrs.feature.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class ObjectFeatureTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(ObjectFeatureTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testConfigure() {
		MockFeatureContext mockFeatureContext1 = new MockFeatureContext();

		_objectFeature.configure(mockFeatureContext1);

		int priority1 = mockFeatureContext1.getPriority(
			"com.liferay.object.rest.internal.jaxrs.container.request.filter." +
				"NestedFieldsContainerRequestFilter");

		MockFeatureContext mockFeatureContext2 = new MockFeatureContext();

		_vulcanFeature.configure(mockFeatureContext2);

		int priority2 = mockFeatureContext2.getPriority(
			"com.liferay.portal.vulcan.internal.jaxrs.container.request." +
				"filter.NestedFieldsContainerRequestFilter");

		Assert.assertTrue(priority1 > priority2);
	}

	private BundleContext _bundleContext;

	@Inject(filter = "osgi.jaxrs.name=Liferay.Object", type = Feature.class)
	private Feature _objectFeature;

	@Inject(filter = "osgi.jaxrs.name=Liferay.Vulcan", type = Feature.class)
	private Feature _vulcanFeature;

	private class MockFeatureContext implements FeatureContext {

		@Override
		public Configuration getConfiguration() {
			return null;
		}

		public Integer getPriority(String className) {
			return _classNamePriorities.get(className);
		}

		@Override
		public FeatureContext property(String s, Object object) {
			return null;
		}

		@Override
		public FeatureContext register(Class<?> clazz) {
			_classNamePriorities.put(
				clazz.getCanonicalName(), _DEFAULT_PRIORITY);

			return this;
		}

		@Override
		public FeatureContext register(Class<?> clazz, Class<?>... classes) {
			return null;
		}

		@Override
		public FeatureContext register(Class<?> clazz, int priority) {
			_classNamePriorities.put(clazz.getCanonicalName(), priority);

			return this;
		}

		@Override
		public FeatureContext register(
			Class<?> clazz, Map<Class<?>, Integer> map) {

			return null;
		}

		@Override
		public FeatureContext register(Object object) {
			Class<?> clazz = object.getClass();

			_classNamePriorities.put(
				clazz.getCanonicalName(), _DEFAULT_PRIORITY);

			return this;
		}

		@Override
		public FeatureContext register(Object object, Class<?>... classes) {
			return null;
		}

		@Override
		public FeatureContext register(Object object, int priority) {
			Class<?> clazz = object.getClass();

			_classNamePriorities.put(clazz.getCanonicalName(), priority);

			return this;
		}

		@Override
		public FeatureContext register(
			Object object, Map<Class<?>, Integer> map) {

			return null;
		}

		private static final Integer _DEFAULT_PRIORITY = Priorities.USER;

		private final Map<String, Integer> _classNamePriorities =
			new HashMap<>();

	}

}
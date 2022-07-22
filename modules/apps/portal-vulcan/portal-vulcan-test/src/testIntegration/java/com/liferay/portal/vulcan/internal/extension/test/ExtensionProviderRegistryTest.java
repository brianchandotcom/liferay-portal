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

package com.liferay.portal.vulcan.internal.extension.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.extension.ExtensionProvider;
import com.liferay.portal.vulcan.extension.ExtensionProviderRegistry;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Javier de Arcos
 */
@RunWith(Arquillian.class)
public class ExtensionProviderRegistryTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			ExtensionProviderRegistryTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			ExtensionProvider.class, _extensionProvider, null);
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetExtensionProviders() {
		Assert.assertEquals(
			Collections.singletonList(_extensionProvider),
			_extensionProviderRegistry.getExtensionProviders(
				_COMPANY_ID, _CLASS_NAME));

		Assert.assertEquals(
			Collections.emptyList(),
			_extensionProviderRegistry.getExtensionProviders(
				22222, RandomTestUtil.randomString()));
	}

	private static final String _CLASS_NAME = "com.liferay.test.model.Test";

	private static final long _COMPANY_ID = 11111;

	private final ExtensionProvider _extensionProvider =
		new TestExtensionProvider();

	@Inject
	private ExtensionProviderRegistry _extensionProviderRegistry;

	private ServiceRegistration<ExtensionProvider> _serviceRegistration;

	private static class TestExtensionProvider implements ExtensionProvider {

		@Override
		public Map<String, Serializable> getExtendedProperties(
			long companyId, Object entity) {

			return null;
		}

		@Override
		public Map<String, PropertyDefinition> getExtendedPropertyDefinitions(
			long companyId, String className) {

			return null;
		}

		@Override
		public Collection<String> getFilteredPropertyNames(
			long companyId, Object entity) {

			return null;
		}

		@Override
		public boolean isApplicableExtension(long companyId, String className) {
			if ((companyId == _COMPANY_ID) &&
				Objects.equals(className, _CLASS_NAME)) {

				return true;
			}

			return false;
		}

		@Override
		public void setExtendedProperties(
			long companyId, Object entity,
			Map<String, Serializable> extendedProperties) {
		}

	}

}
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

package com.liferay.scr.component.test.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.scr.component.test.configuration.TestComponent;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.runtime.ServiceComponentRuntime;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class OptionalConfigurationsComponentTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(TestComponent.class);

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceTracker.close();
	}

	@Test
	public void testApplyExistingConfigurationOnComponentActivation()
		throws Exception {

		Assert.assertEquals("empty", _testComponent.getSecond());

		String pid =
			"com.liferay.scr.component.test.configuration.SecondConfiguration";

		ConfigurationTestUtil.saveConfiguration(
			pid,
			HashMapDictionaryBuilder.<String, Object>put(
				"second", "second-value"
			).build());

		Assert.assertEquals("second-value", _testComponent.getSecond());

		_refreshBundle("com.liferay.scr.component.test.configuration");

		Assert.assertEquals("second-value", _getTestComponent().getSecond());
	}

	private TestComponent _getTestComponent() throws Exception {
		return _serviceTracker.waitForService(10_000L);
	}

	private void _refreshBundle(String bundleSymbolicName) throws Exception {
		Bundle currentBundle = FrameworkUtil.getBundle(
			OptionalConfigurationsComponentTest.class);

		BundleContext bundleContext = currentBundle.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			if (bundleSymbolicName.equals(bundle.getSymbolicName())) {
				bundle.stop();
				bundle.start();

				return;
			}
		}
	}

	@Inject
	private static ServiceComponentRuntime _serviceComponentRuntime;

	private static ServiceTracker<TestComponent, TestComponent> _serviceTracker;

	@Inject
	private TestComponent _testComponent;

}
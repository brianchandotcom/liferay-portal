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

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class UpgradeRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		if (!PropsValues.UPGRADE_DATABASE_AUTO_RUN) {
			_upgradeDatabaseAutoRunField = ReflectionUtil.getDeclaredField(
				PropsValues.class, "UPGRADE_DATABASE_AUTO_RUN");

			_upgradeDatabaseAutoRunField.setAccessible(true);

			_modifiersField = Field.class.getDeclaredField("modifiers");

			_modifiersField.setAccessible(true);
			_modifiersField.setInt(
				_upgradeDatabaseAutoRunField,
				_upgradeDatabaseAutoRunField.getModifiers() & ~Modifier.FINAL);

			_upgradeDatabaseAutoRunField.set(null, true);
		}
	}

	@After
	public void tearDown() throws Exception {
		if (!_PREVIOUS_UPGRADE_DATABASE_AUTO_RUN) {
			_upgradeDatabaseAutoRunField.set(
				null, _PREVIOUS_UPGRADE_DATABASE_AUTO_RUN);

			_modifiersField.setAccessible(false);

			_upgradeDatabaseAutoRunField.setAccessible(false);
		}

		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		Release release = _releaseLocalService.fetchRelease(
			"com.liferay.portal.upgrade.test");

		if (release != null) {
			_releaseLocalService.deleteRelease(release);
		}
	}

	@Ignore
	@Test
	public void testUpgradeRegistryFollowsShortestPath() {
		_releaseLocalService.addRelease(
			"com.liferay.portal.upgrade.test", "1.0.0");

		Bundle bundle = FrameworkUtil.getBundle(UpgradeRegistryTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		TestUpgradeStep[] testUpgradeSteps = new TestUpgradeStep[4];

		testUpgradeSteps[0] = new TestUpgradeStep("1.0.0", "2.0.0");
		testUpgradeSteps[1] = new TestUpgradeStep("2.0.0", "3.0.0");
		testUpgradeSteps[2] = new TestUpgradeStep("3.0.0", "4.0.0");
		testUpgradeSteps[3] = new TestUpgradeStep("1.0.0", "4.0.0");

		TestUpgradeStepRegistrator testUpgradeStepRegistrator =
			new TestUpgradeStepRegistrator(testUpgradeSteps);

		_serviceRegistration = bundleContext.registerService(
			UpgradeStepRegistrator.class, testUpgradeStepRegistrator, null);

		Assert.assertTrue(testUpgradeStepRegistrator._registratorCalled);

		Assert.assertFalse(testUpgradeSteps[0]._upgradeCalled);
		Assert.assertFalse(testUpgradeSteps[1]._upgradeCalled);
		Assert.assertFalse(testUpgradeSteps[2]._upgradeCalled);
		Assert.assertTrue(testUpgradeSteps[3]._upgradeCalled);
	}

	private static final boolean _PREVIOUS_UPGRADE_DATABASE_AUTO_RUN =
		PropsValues.UPGRADE_DATABASE_AUTO_RUN;

	private static Field _modifiersField;

	@Inject
	private static ReleaseLocalService _releaseLocalService;

	private static Field _upgradeDatabaseAutoRunField;

	private ServiceRegistration<UpgradeStepRegistrator> _serviceRegistration;

	private static class TestUpgradeStep implements UpgradeStep {

		@Override
		public void upgrade() {
			_upgradeCalled = true;
		}

		private TestUpgradeStep(
			String fromSchemaVersionString, String toSchemaVersionString) {

			_fromSchemaVersionString = fromSchemaVersionString;
			_toSchemaVersionString = toSchemaVersionString;
		}

		private final String _fromSchemaVersionString;
		private final String _toSchemaVersionString;
		private boolean _upgradeCalled;

	}

	private static class TestUpgradeStepRegistrator
		implements UpgradeStepRegistrator {

		@Override
		public void register(Registry registry) {
			_registratorCalled = true;

			for (TestUpgradeStep testUpgradeStep : _testUpgradeSteps) {
				registry.register(
					testUpgradeStep._fromSchemaVersionString,
					testUpgradeStep._toSchemaVersionString, testUpgradeStep);
			}
		}

		private TestUpgradeStepRegistrator(TestUpgradeStep[] testUpgradeSteps) {
			_testUpgradeSteps = testUpgradeSteps;
		}

		private boolean _registratorCalled;
		private final TestUpgradeStep[] _testUpgradeSteps;

	}

}
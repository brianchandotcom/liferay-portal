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

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.VerifyProcess;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class VerifyProcessTrackerOSGiCommandsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			VerifyProcessTrackerOSGiCommandsTest.class);

		_symbolicName = bundle.getSymbolicName();

		_bundleContext = bundle.getBundleContext();

		_upgrading = StartupHelperUtil.isUpgrading();

		StartupHelperUtil.setUpgrading(false);
	}

	@After
	public void tearDown() {
		StartupHelperUtil.setUpgrading(_upgrading);

		Release release = _releaseLocalService.fetchRelease(_symbolicName);

		if (release != null) {
			_releaseLocalService.deleteRelease(release);
		}

		_verifyProcessRun = false;
	}

	@Test
	public void testRegisterInitialDeploymentVerifyProcessAfterInitialDeploymentUpgradeProcess() {
		try (SafeCloseable safeCloseable1 = _executeInitialUpgradeProcess();
			SafeCloseable safeCloseable2 = _registerInitialVerifyProcess()) {

			Assert.assertTrue(_verifyProcessRun);
		}
	}

	@Test
	public void testRegisterInitialDeploymentVerifyProcessAfterModuleUpgrade() {
		_simulateUpgradeProcessExecution();

		try (SafeCloseable safeCloseable2 = _registerInitialVerifyProcess()) {
			Assert.assertTrue(_verifyProcessRun);
		}
	}

	@Test
	public void testRegisterInitialDeploymentVerifyProcessDuringInitialDeployment() {
		try (SafeCloseable safeCloseable = _registerInitialVerifyProcess()) {
			Assert.assertTrue(_verifyProcessRun);
		}
	}

	@Test
	public void testRegisterInitialDeploymentVerifyProcessDuringPortalUpgrade() {
		try (SafeCloseable safeCloseable1 = _upgradePortal();
			SafeCloseable safeCloseable2 = _registerInitialVerifyProcess()) {

			Assert.assertFalse(_verifyProcessRun);
		}
	}

	@Test
	public void testRegisterRunOnPortalUpgradeVerifyProcessAfterInitialDeploymentUpgradeProcess() {
		try (SafeCloseable safeCloseable1 = _executeInitialUpgradeProcess();
			SafeCloseable safeCloseable2 =
				_registerRunOnPortalUpgradeVerifyProcess()) {

			Assert.assertFalse(_verifyProcessRun);
		}
	}

	@Test
	public void testRegisterRunOnPortalUpgradeVerifyProcessAfterModuleUpgrade() {
		_simulateUpgradeProcessExecution();

		try (SafeCloseable safeCloseable2 =
				_registerRunOnPortalUpgradeVerifyProcess()) {

			Assert.assertTrue(_verifyProcessRun);
		}
	}

	@Test
	public void testRegisterRunOnPortalUpgradeVerifyProcessDuringInitialDeployment() {
		try (SafeCloseable safeCloseable =
				_registerRunOnPortalUpgradeVerifyProcess()) {

			Assert.assertFalse(_verifyProcessRun);
		}
	}

	@Test
	public void testRegisterRunOnPortalUpgradeVerifyProcessDuringPortalUpgrade() {
		try (SafeCloseable safeCloseable1 = _upgradePortal();
			SafeCloseable safeCloseable2 =
				_registerRunOnPortalUpgradeVerifyProcess()) {

			Assert.assertTrue(_verifyProcessRun);
		}
	}

	@Test
	public void testRegisterVerifyProcessAfterInitialDeploymentUpgradeProcess() {
		try (SafeCloseable safeCloseable1 = _executeInitialUpgradeProcess();
			SafeCloseable safeCloseable2 = _registerVerifyProcess()) {

			Assert.assertFalse(_verifyProcessRun);
		}
	}

	@Test
	public void testRegisterVerifyProcessAfterModuleUpgrade() {
		_simulateUpgradeProcessExecution();

		try (SafeCloseable safeCloseable2 = _registerVerifyProcess()) {
			Assert.assertTrue(_verifyProcessRun);
		}
	}

	@Test
	public void testRegisterVerifyProcessDuringInitialDeployment() {
		try (SafeCloseable safeCloseable = _registerVerifyProcess()) {
			Assert.assertFalse(_verifyProcessRun);
		}
	}

	@Test
	public void testRegisterVerifyProcessDuringUpgradePortal() {
		try (SafeCloseable safeCloseable1 = _upgradePortal();
			SafeCloseable safeCloseable2 = _registerVerifyProcess()) {

			Assert.assertFalse(_verifyProcessRun);
		}
	}

	private SafeCloseable _executeInitialUpgradeProcess() {
		ServiceRegistration<UpgradeStep> upgradeStepServiceRegistration =
			_bundleContext.registerService(
				UpgradeStep.class, new DummyUpgrade(),
				HashMapDictionaryBuilder.<String, Object>put(
					"upgrade.bundle.symbolic.name", _symbolicName
				).put(
					"upgrade.from.schema.version", "0.0.0"
				).put(
					"upgrade.to.schema.version", "1.0.0"
				).build());

		return () -> upgradeStepServiceRegistration.unregister();
	}

	private SafeCloseable _registerInitialVerifyProcess() {
		ServiceRegistration<VerifyProcess>
			initialDeploymentVerifyProcessRegistration =
				_bundleContext.registerService(
					VerifyProcess.class, _initialDeploymentVerifyProcess,
					HashMapDictionaryBuilder.<String, Object>put(
						"initial.deployment", true
					).put(
						"verify.process.name", _symbolicName
					).build());

		return () -> initialDeploymentVerifyProcessRegistration.unregister();
	}

	private SafeCloseable _registerRunOnPortalUpgradeVerifyProcess() {
		ServiceRegistration<VerifyProcess>
			runOnPortalUpgradeVerifyProcessRegistration =
				_bundleContext.registerService(
					VerifyProcess.class, _runOnPortalUpgradeVerifyProcess,
					HashMapDictionaryBuilder.<String, Object>put(
						"run.on.portal.upgrade", true
					).put(
						"verify.process.name", _symbolicName
					).build());

		return () -> runOnPortalUpgradeVerifyProcessRegistration.unregister();
	}

	private SafeCloseable _registerVerifyProcess() {
		ServiceRegistration<VerifyProcess> verifyProcessRegistration =
			_bundleContext.registerService(
				VerifyProcess.class, _verifyProcess,
				MapUtil.singletonDictionary(
					"verify.process.name", _symbolicName));

		return () -> verifyProcessRegistration.unregister();
	}

	private void _simulateUpgradeProcessExecution() {
		Release release = _releaseLocalService.createRelease(
			_counterLocalService.increment());

		release.setServletContextName(_symbolicName);
		release.setSchemaVersion("1.0.0");
		release.setVerified(false);

		_releaseLocalService.updateRelease(release);
	}

	private SafeCloseable _upgradePortal() {
		Release release = _releaseLocalService.createRelease(
			_counterLocalService.increment());

		release.setServletContextName(_symbolicName);
		release.setSchemaVersion("0.0.1");
		release.setVerified(true);

		_releaseLocalService.updateRelease(release);

		StartupHelperUtil.setUpgrading(true);

		return () -> StartupHelperUtil.setUpgrading(false);
	}

	private static BundleContext _bundleContext;
	private static String _symbolicName;
	private static boolean _upgrading;

	@Inject
	private CounterLocalService _counterLocalService;

	private final VerifyProcessTest _initialDeploymentVerifyProcess =
		new VerifyProcessTest();

	@Inject
	private ReleaseLocalService _releaseLocalService;

	private final VerifyProcessTest _runOnPortalUpgradeVerifyProcess =
		new VerifyProcessTest();
	private final VerifyProcessTest _verifyProcess = new VerifyProcessTest();
	private boolean _verifyProcessRun;

	private class DummyUpgrade extends DummyUpgradeStep {
	}

	private class VerifyProcessTest extends VerifyProcess {

		@Override
		protected void doVerify() throws Exception {
			_verifyProcessRun = true;
		}

	}

}
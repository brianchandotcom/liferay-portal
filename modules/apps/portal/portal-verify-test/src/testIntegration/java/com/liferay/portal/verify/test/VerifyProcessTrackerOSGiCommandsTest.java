/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
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

		_upgrading = ReflectionTestUtil.getAndSetFieldValue(
			StartupHelperUtil.class, "_upgrading", false);
	}

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			StartupHelperUtil.class, "_upgrading", _upgrading);

		Release release = _releaseLocalService.fetchRelease(_symbolicName);

		if (release != null) {
			_releaseLocalService.deleteRelease(release);
		}

		_forceFailure = false;
		_verifyProcessRun = false;
	}

	@Test
	public void testRegisterFailedVerifyProcess() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.verify.extender.internal.osgi.commands." +
					"VerifyProcessTrackerOSGiCommands",
				LoggerTestUtil.OFF)) {

			_forceFailure = true;

			try (SafeCloseable safeCloseable = _registerVerifyProcess(
					true, true)) {

				_assertVerify(true);
			}
		}
	}

	@Test
	public void testRegisterInitialDeploymentAndRunOnPortalUpgradeVerifyProcessAfterInitialDeploymentUpgradeProcess() {
		try (SafeCloseable safeCloseable1 = _executeInitialUpgradeProcess();
			SafeCloseable safeCloseable2 = _registerVerifyProcess(true, true)) {

			_assertVerify(true);
		}
	}

	@Test
	public void testRegisterInitialDeploymentAndRunOnPortalUpgradeVerifyProcessAfterModuleUpgrade() {
		_simulateUpgradeProcessExecution();

		try (SafeCloseable safeCloseable2 = _registerVerifyProcess(
				true, true)) {

			_assertVerify(true);
		}
	}

	@Test
	public void testRegisterInitialDeploymentAndRunOnPortalUpgradeVerifyProcessDuringInitialDeployment() {
		try (SafeCloseable safeCloseable = _registerVerifyProcess(true, true)) {
			_assertVerify(true);
		}
	}

	@Test
	public void testRegisterInitialDeploymentAndRunOnPortalUpgradeVerifyProcessDuringPortalUpgrade() {
		try (SafeCloseable safeCloseable1 = _upgradePortal();
			SafeCloseable safeCloseable2 = _registerVerifyProcess(true, true)) {

			_assertVerify(true);
		}
	}

	@Test
	public void testRegisterInitialDeploymentVerifyProcessAfterInitialDeploymentUpgradeProcess() {
		try (SafeCloseable safeCloseable1 = _executeInitialUpgradeProcess();
			SafeCloseable safeCloseable2 = _registerVerifyProcess(
				true, false)) {

			_assertVerify(true);
		}
	}

	@Test
	public void testRegisterInitialDeploymentVerifyProcessAfterModuleUpgrade() {
		_simulateUpgradeProcessExecution();

		try (SafeCloseable safeCloseable2 = _registerVerifyProcess(
				true, false)) {

			_assertVerify(true);
		}
	}

	@Test
	public void testRegisterInitialDeploymentVerifyProcessDuringInitialDeployment() {
		try (SafeCloseable safeCloseable = _registerVerifyProcess(
				true, false)) {

			_assertVerify(true);
		}
	}

	@Test
	public void testRegisterInitialDeploymentVerifyProcessDuringPortalUpgrade() {
		try (SafeCloseable safeCloseable1 = _upgradePortal();
			SafeCloseable safeCloseable2 = _registerVerifyProcess(
				true, false)) {

			_assertVerify(false);
		}
	}

	@Test
	public void testRegisterRunOnPortalUpgradeVerifyProcessAfterInitialDeploymentUpgradeProcess() {
		try (SafeCloseable safeCloseable1 = _executeInitialUpgradeProcess();
			SafeCloseable safeCloseable2 = _registerVerifyProcess(
				false, true)) {

			_assertVerify(false);
		}
	}

	@Test
	public void testRegisterRunOnPortalUpgradeVerifyProcessAfterModuleUpgrade() {
		_simulateUpgradeProcessExecution();

		try (SafeCloseable safeCloseable2 = _registerVerifyProcess(
				false, true)) {

			_assertVerify(true);
		}
	}

	@Test
	public void testRegisterRunOnPortalUpgradeVerifyProcessDuringInitialDeployment() {
		try (SafeCloseable safeCloseable = _registerVerifyProcess(
				false, true)) {

			_assertVerify(false);
		}
	}

	@Test
	public void testRegisterRunOnPortalUpgradeVerifyProcessDuringPortalUpgrade() {
		try (SafeCloseable safeCloseable1 = _upgradePortal();
			SafeCloseable safeCloseable2 = _registerVerifyProcess(
				false, true)) {

			_assertVerify(true);
		}
	}

	@Test
	public void testRegisterVerifyProcessAfterInitialDeploymentUpgradeProcess() {
		try (SafeCloseable safeCloseable1 = _executeInitialUpgradeProcess();
			SafeCloseable safeCloseable2 = _registerVerifyProcess(
				false, false)) {

			_assertVerify(false);
		}
	}

	@Test
	public void testRegisterVerifyProcessAfterModuleUpgrade() {
		_simulateUpgradeProcessExecution();

		try (SafeCloseable safeCloseable2 = _registerVerifyProcess(
				false, false)) {

			_assertVerify(true);
		}
	}

	@Test
	public void testRegisterVerifyProcessDuringInitialDeployment() {
		try (SafeCloseable safeCloseable = _registerVerifyProcess(
				false, false)) {

			_assertVerify(false);
		}
	}

	@Test
	public void testRegisterVerifyProcessDuringUpgradePortal() {
		try (SafeCloseable safeCloseable1 = _upgradePortal();
			SafeCloseable safeCloseable2 = _registerVerifyProcess(
				false, false)) {

			_assertVerify(false);
		}
	}

	private void _assertVerify(boolean verifyProcessRun) {
		Assert.assertEquals(verifyProcessRun, _verifyProcessRun);

		if (!verifyProcessRun) {
			return;
		}

		Release release = _releaseLocalService.fetchRelease(_symbolicName);

		Assert.assertNotNull(release);

		if (_forceFailure) {
			Assert.assertFalse(release.isVerified());
		}
		else {
			Assert.assertTrue(release.isVerified());
		}
	}

	private SafeCloseable _executeInitialUpgradeProcess() {
		ServiceRegistration<UpgradeStep> upgradeStepServiceRegistration =
			_bundleContext.registerService(
				UpgradeStep.class, new DummyUpgradeProcess(),
				HashMapDictionaryBuilder.<String, Object>put(
					"upgrade.bundle.symbolic.name", _symbolicName
				).put(
					"upgrade.from.schema.version", "0.0.0"
				).put(
					"upgrade.to.schema.version", "1.0.0"
				).build());

		return upgradeStepServiceRegistration::unregister;
	}

	private SafeCloseable _registerVerifyProcess(
		boolean initialDeployment, boolean runOnPortalUpgrade) {

		ServiceRegistration<VerifyProcess> verifyProcessServiceRegistration =
			_bundleContext.registerService(
				VerifyProcess.class, _verifyProcess,
				HashMapDictionaryBuilder.<String, Object>put(
					"initial.deployment", initialDeployment
				).put(
					"run.on.portal.upgrade", runOnPortalUpgrade
				).build());

		return verifyProcessServiceRegistration::unregister;
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

		ReflectionTestUtil.setFieldValue(
			StartupHelperUtil.class, "_upgrading", true);

		return () -> ReflectionTestUtil.setFieldValue(
			StartupHelperUtil.class, "_upgrading", false);
	}

	private static BundleContext _bundleContext;
	private static String _symbolicName;
	private static boolean _upgrading;

	@Inject
	private CounterLocalService _counterLocalService;

	private boolean _forceFailure;

	@Inject
	private ReleaseLocalService _releaseLocalService;

	private final VerifyProcessTest _verifyProcess = new VerifyProcessTest();
	private boolean _verifyProcessRun;

	private class DummyUpgradeProcess extends DummyUpgradeStep {
	}

	private class VerifyProcessTest extends VerifyProcess {

		@Override
		protected void doVerify() throws Exception {
			_verifyProcessRun = true;

			if (_forceFailure) {
				throw new Exception();
			}
		}

	}

}
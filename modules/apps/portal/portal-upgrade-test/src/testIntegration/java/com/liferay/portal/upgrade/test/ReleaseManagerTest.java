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
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.ReleaseManager;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class ReleaseManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void testSuccessfulUpgrade() throws Exception {
		Assert.assertTrue(_releaseManager.isUpgraded());
		Assert.assertTrue(
			Validator.isBlank(_releaseManager.getShortStatusMessage(false)));
		Assert.assertTrue(
			Validator.isBlank(_releaseManager.getStatusMessage(false)));
	}

	@Test
	public void testUnsuccessfulUpgradeByMissingModuleUpgrade()
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(ReleaseManagerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			UpgradeStepRegistrator.class,
			new ReleaseManagerTest.TestUpgradeStepRegistrator(), null);

		Release release = _releaseLocalService.fetchRelease(
			bundle.getSymbolicName());

		try {
			release.setSchemaVersion("0.0.0");

			release = _releaseLocalService.updateRelease(release);

			Assert.assertFalse(_releaseManager.isUpgraded());
			Assert.assertFalse(
				Validator.isBlank(
					_releaseManager.getShortStatusMessage(false)));
			Assert.assertFalse(
				Validator.isBlank(_releaseManager.getStatusMessage(false)));
		}
		finally {
			_releaseLocalService.deleteRelease(release);
		}
	}

	@Test
	public void testUnsuccessfulUpgradeByMissingPortalUpgrade()
		throws Exception {

		Release release = _releaseLocalService.fetchRelease(
			ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

		String currentSchemaVersion = release.getSchemaVersion();

		try {
			release.setSchemaVersion("0.0.0");

			release = _releaseLocalService.updateRelease(release);

			Assert.assertFalse(_releaseManager.isUpgraded());
			Assert.assertFalse(
				Validator.isBlank(
					_releaseManager.getShortStatusMessage(false)));
			Assert.assertFalse(
				Validator.isBlank(_releaseManager.getStatusMessage(false)));
		}
		finally {
			release.setSchemaVersion(currentSchemaVersion);

			_releaseLocalService.updateRelease(release);
		}
	}

	@Inject
	private ReleaseLocalService _releaseLocalService;

	@Inject
	private volatile ReleaseManager _releaseManager;

	private ServiceRegistration<UpgradeStepRegistrator> _serviceRegistration;

	private static class TestUpgradeStepRegistrator
		implements UpgradeStepRegistrator {

		@Override
		public void register(Registry registry) {
			registry.registerInitialization();
		}

	}

}
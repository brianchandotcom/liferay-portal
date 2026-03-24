/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;

import java.util.Objects;

import net.bytebuddy.agent.builder.ResettableClassFileTransformer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Kevin Lee
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class DXPModuleLicenseTest extends BaseLicenseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		Assume.assumeTrue(isReleaseBundle());
	}

	@BeforeClass
	public static void setUpClass() {
		_disableKeyValidatorResettableClassFileTransformer = disableValidate();
		_setVersionResettableClassFileTransformer = setVersion("2026.Q1.0 LTS");
	}

	@AfterClass
	public static void tearDownClass() {
		resetClassFileTransformer(
			_disableKeyValidatorResettableClassFileTransformer);
		resetClassFileTransformer(_setVersionResettableClassFileTransformer);
	}

	@After
	public void tearDown() throws Exception {
		resetLicenseData();
		resetLifecycleAction();
	}

	@Test
	public void testEmptyBundlesFile() throws Exception {
		assertPortalInvalidatedWithEmptyFile(getProperty("bundles.file.path"));
	}

	@Test
	public void testFreeTierLicense() throws Exception {
		assertLicensePropertiesNotExisted(getPortalProductId());

		assertBundlesExisted(
			_getDxpOnlyModuleSymbolicName(), _getEnterpriseAppSymbolicName());

		assertPortalLicenseNotRegistered();

		assertBundlesExisted(
			_getDxpOnlyModuleSymbolicName(), _getEnterpriseAppSymbolicName());

		File binaryFile = deployFreeTierPortalLicense(Time.HOUR);

		assertLicensePropertiesExisted(getPortalProductId());

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(
			_getDxpOnlyModuleSymbolicName(), _getEnterpriseAppSymbolicName());

		binaryFile.delete();

		LicenseManagerUtil.checkLicense(getPortalProductId());

		assertLicensePropertiesNotExisted(getPortalProductId());

		resetLifecycleAction();

		assertBundlesExisted(
			_getDxpOnlyModuleSymbolicName(), _getEnterpriseAppSymbolicName());

		assertPortalLicenseNotRegistered();
	}

	@Test
	public void testFreeTierLicenseManualDeploy() throws Exception {
		assertLicensePropertiesNotExisted(getPortalProductId());

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Bundle dxpOnlyBundle = null;
		Bundle enterpriseAppBundle = null;

		for (Bundle bundle : bundleContext.getBundles()) {
			if (Objects.equals(
					bundle.getSymbolicName(),
					_getDxpOnlyModuleSymbolicName())) {

				dxpOnlyBundle = bundle;

				continue;
			}

			if (Objects.equals(
					bundle.getSymbolicName(),
					_getEnterpriseAppSymbolicName())) {

				enterpriseAppBundle = bundle;
			}
		}

		Assert.assertNotNull(dxpOnlyBundle);
		Assert.assertNotNull(enterpriseAppBundle);

		Assert.assertEquals(Bundle.ACTIVE, dxpOnlyBundle.getState());
		Assert.assertEquals(Bundle.ACTIVE, enterpriseAppBundle.getState());

		assertPortalLicenseNotRegistered();

		Assert.assertEquals(Bundle.ACTIVE, dxpOnlyBundle.getState());
		Assert.assertEquals(Bundle.ACTIVE, enterpriseAppBundle.getState());

		deployFreeTierPortalLicense(Time.HOUR);

		assertLicensePropertiesExisted(getPortalProductId());

		assertPortalLicenseRegistered();

		Assert.assertEquals(Bundle.UNINSTALLED, dxpOnlyBundle.getState());
		Assert.assertEquals(Bundle.UNINSTALLED, enterpriseAppBundle.getState());

		dxpOnlyBundle = bundleContext.installBundle(
			dxpOnlyBundle.getLocation());
		enterpriseAppBundle = bundleContext.installBundle(
			enterpriseAppBundle.getLocation());

		try {
			dxpOnlyBundle.start();
			enterpriseAppBundle.start();

			Assert.assertEquals(Bundle.ACTIVE, dxpOnlyBundle.getState());
			Assert.assertEquals(Bundle.ACTIVE, enterpriseAppBundle.getState());

			resetCheckInterval();

			assertPortalLicenseInvalid();
		}
		finally {
			dxpOnlyBundle.uninstall();
			enterpriseAppBundle.uninstall();
		}
	}

	@Test
	public void testMissingBundlesFile() throws Exception {
		assertPortalInvalidatedWithMissingFile(
			getProperty("bundles.file.path"));
	}

	private String _getDxpOnlyModuleSymbolicName() {
		return getProperty("dxp.only.module.symbolic.name");
	}

	private String _getEnterpriseAppSymbolicName() {
		return getProperty("enterprise.app.symbolic.name");
	}

	private static ResettableClassFileTransformer
		_disableKeyValidatorResettableClassFileTransformer;
	private static ResettableClassFileTransformer
		_setVersionResettableClassFileTransformer;

}
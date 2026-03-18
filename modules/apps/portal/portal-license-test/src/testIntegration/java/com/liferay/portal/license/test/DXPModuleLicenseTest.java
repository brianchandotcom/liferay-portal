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
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.InputStream;

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
		_setVersionResettableClassFileTransformer = setVersion("2026.Q1.0");
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
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		PortalClassLoaderUtil.setClassLoader(
			new WrapperClassLoader(classLoader) {

				@Override
				public InputStream getResourceAsStream(String name) {
					if (name.equals(_getBundlesFilePath())) {
						return InputStream.nullInputStream();
					}

					return classLoader.getResourceAsStream(name);
				}

			});

		try {
			assertLicenseNotRegistered(hitHomePage("localhost", 8080));

			deployFreeTierLicense(Time.HOUR);

			assertLicenseInvalid(hitHomePage("localhost", 8080));
		}
		finally {
			PortalClassLoaderUtil.setClassLoader(classLoader);
		}
	}

	@Test
	public void testFreeTierLicense() throws Exception {
		assertLicensePropertiesNotExisted(getPortalProductId());

		assertBundlesExisted(
			_getDxpOnlyModuleSymbolicName(), _getEnterpriseAppSymbolicName());

		assertLicenseNotRegistered(hitHomePage("localhost", 8080));

		assertBundlesExisted(
			_getDxpOnlyModuleSymbolicName(), _getEnterpriseAppSymbolicName());

		File binaryFile = deployFreeTierLicense(Time.HOUR);

		assertLicensePropertiesExisted(getPortalProductId());

		assertLicenseRegistered(hitHomePage("localhost", 8080));

		assertBundlesNotExisted(
			_getDxpOnlyModuleSymbolicName(), _getEnterpriseAppSymbolicName());

		binaryFile.delete();

		LicenseManagerUtil.checkLicense(getPortalProductId());

		assertLicensePropertiesNotExisted(getPortalProductId());

		resetLifecycleAction();

		assertBundlesExisted(
			_getDxpOnlyModuleSymbolicName(), _getEnterpriseAppSymbolicName());

		assertLicenseNotRegistered(hitHomePage("localhost", 8080));
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

		assertLicenseNotRegistered(hitHomePage("localhost", 8080));

		Assert.assertEquals(Bundle.ACTIVE, dxpOnlyBundle.getState());
		Assert.assertEquals(Bundle.ACTIVE, enterpriseAppBundle.getState());

		deployFreeTierLicense(Time.HOUR);

		assertLicensePropertiesExisted(getPortalProductId());

		assertLicenseRegistered(hitHomePage("localhost", 8080));

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

			assertLicenseInvalid(hitHomePage("localhost", 8080));
		}
		finally {
			dxpOnlyBundle.uninstall();
			enterpriseAppBundle.uninstall();
		}
	}

	@Test
	public void testMissingBundlesFile() throws Exception {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		PortalClassLoaderUtil.setClassLoader(
			new WrapperClassLoader(classLoader) {

				@Override
				public InputStream getResourceAsStream(String name) {
					if (name.equals(_getBundlesFilePath())) {
						return null;
					}

					return classLoader.getResourceAsStream(name);
				}

			});

		try {
			assertLicenseNotRegistered(hitHomePage("localhost", 8080));

			deployFreeTierLicense(Time.HOUR);

			assertLicenseInvalid(hitHomePage("localhost", 8080));
		}
		finally {
			PortalClassLoaderUtil.setClassLoader(classLoader);
		}
	}

	private String _getBundlesFilePath() {
		return getProperty("bundles.file.path");
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

	private static class WrapperClassLoader extends ClassLoader {

		public WrapperClassLoader(ClassLoader classLoader) {
			_classLoader = classLoader;
		}

		@Override
		public boolean equals(Object object) {
			return _classLoader.equals(object);
		}

		@Override
		public int hashCode() {
			return _classLoader.hashCode();
		}

		private final ClassLoader _classLoader;

	}

}
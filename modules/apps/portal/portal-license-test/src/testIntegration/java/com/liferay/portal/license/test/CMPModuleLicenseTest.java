/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.File;
import java.io.InputStream;

import net.bytebuddy.agent.builder.ResettableClassFileTransformer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kevin Lee
 */
@RunWith(Arquillian.class)
public class CMPModuleLicenseTest extends BaseLicenseTestCase {

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
	public void testEmptyCMPFile() throws Exception {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		PortalClassLoaderUtil.setClassLoader(
			new WrapperClassLoader(classLoader) {

				@Override
				public InputStream getResourceAsStream(String name) {
					if (name.equals(_getCmpFilePath())) {
						return InputStream.nullInputStream();
					}

					return classLoader.getResourceAsStream(name);
				}

			});

		try {
			assertPortalLicenseNotRegistered();

			deployEnterprisePortalLicense(Time.HOUR);

			assertPortalLicenseInvalid();
		}
		finally {
			PortalClassLoaderUtil.setClassLoader(classLoader);
		}
	}

	@Test
	public void testEnterpriseLicense() throws Exception {
		assertLicensePropertiesNotExisted(getCMPProductId());

		assertBundlesExisted(_getCmpSymbolicNames());

		assertPortalLicenseNotRegistered();

		assertBundlesExisted(_getCmpSymbolicNames());

		deployEnterprisePortalLicense(Time.HOUR);

		assertLicensePropertiesNotExisted(getCMPProductId());

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_getCmpSymbolicNames());

		File binaryFile = deployCMPLicense(Time.HOUR);

		assertLicensePropertiesExisted(getCMPProductId());

		assertPortalLicenseRegistered();

		assertBundlesExisted(_getCmpSymbolicNames());

		binaryFile.delete();

		LicenseManagerUtil.checkLicense(getCMPProductId());

		assertLicensePropertiesNotExisted(getCMPProductId());

		resetLifecycleAction();

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_getCmpSymbolicNames());
	}

	@Test
	public void testFreeTierLicense() throws Exception {
		assertLicensePropertiesNotExisted(getCMPProductId());

		assertBundlesExisted(_getCmpSymbolicNames());

		assertPortalLicenseNotRegistered();

		assertBundlesExisted(_getCmpSymbolicNames());

		deployFreeTierPortalLicense(Time.HOUR);

		assertLicensePropertiesNotExisted(getCMPProductId());

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_getCmpSymbolicNames());

		File binaryFile = deployCMPLicense(Time.HOUR);

		assertLicensePropertiesExisted(getCMPProductId());

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_getCmpSymbolicNames());

		binaryFile.delete();

		LicenseManagerUtil.checkLicense(getCMPProductId());

		assertLicensePropertiesNotExisted(getCMPProductId());

		resetLifecycleAction();

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_getCmpSymbolicNames());
	}

	@Test
	public void testMissingCMPFile() throws Exception {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		PortalClassLoaderUtil.setClassLoader(
			new WrapperClassLoader(classLoader) {

				@Override
				public InputStream getResourceAsStream(String name) {
					if (name.equals(_getCmpFilePath())) {
						return null;
					}

					return classLoader.getResourceAsStream(name);
				}

			});

		try {
			assertPortalLicenseNotRegistered();

			deployEnterprisePortalLicense(Time.HOUR);

			assertPortalLicenseInvalid();
		}
		finally {
			PortalClassLoaderUtil.setClassLoader(classLoader);
		}
	}

	private String _getCmpFilePath() {
		return getProperty("cmp.file.path");
	}

	private String[] _getCmpSymbolicNames() {
		String property = getProperty("cmp.symbolic.names");

		return property.split(StringPool.COMMA);
	}

	private static ResettableClassFileTransformer
		_disableKeyValidatorResettableClassFileTransformer;
	private static ResettableClassFileTransformer
		_setVersionResettableClassFileTransformer;

}
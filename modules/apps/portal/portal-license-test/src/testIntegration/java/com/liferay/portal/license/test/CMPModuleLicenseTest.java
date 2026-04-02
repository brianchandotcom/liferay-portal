/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Kevin Lee
 */
@RunWith(Arquillian.class)
public class CMPModuleLicenseTest extends BaseLicenseTestCase {

	@BeforeClass
	public static void setUpClass() {
		_disableKeyValidatorSafeCloseable = disableValidateWithSafeCloseable();
		_setVersionSafeCloseable = setVersionWithSafeCloseable("2026.Q1.0 LTS");
	}

	@AfterClass
	public static void tearDownClass() {
		_disableKeyValidatorSafeCloseable.close();
		_setVersionSafeCloseable.close();
	}

	@Before
	public void setUp() throws Exception {
		_safeCloseable = resetLicenseDataWithSafeCloseble();

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		List<String> symbolicNames = new ArrayList<>();

		for (Bundle bundle : bundleContext.getBundles()) {
			String symbolicName = bundle.getSymbolicName();

			if (symbolicName.contains(".cmp.")) {
				symbolicNames.add(symbolicName);
			}
		}

		_cmpSymbolicNames = ArrayUtil.toStringArray(symbolicNames);
	}

	@After
	public void tearDown() {
		_safeCloseable.close();
	}

	@Test
	public void testBrokenCMPFile() throws Exception {
		assertPortalInvalidatedWithBrokenFile(
			"CMP", "CMP bundle list is corrupted");
	}

	@Test
	public void testEnterpriseLicense() throws Exception {
		assertLicensePropertiesNotExisted(getCMPProductId());

		assertBundlesExisted(_cmpSymbolicNames);

		assertPortalLicenseNotRegistered();

		assertBundlesExisted(_cmpSymbolicNames);

		deployEnterprisePortalLicense(Time.HOUR);

		assertLicensePropertiesNotExisted(getCMPProductId());

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_cmpSymbolicNames);

		File binaryFile = deployCMPLicense(Time.HOUR);

		assertLicensePropertiesExisted(getCMPProductId());

		assertPortalLicenseRegistered();

		assertBundlesExisted(_cmpSymbolicNames);

		binaryFile.delete();

		checkLicense(getCMPProductId());

		assertLicensePropertiesNotExisted(getCMPProductId());

		resetLifecycleAction();

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_cmpSymbolicNames);
	}

	@Test
	public void testFreeTierLicense() throws Exception {
		assertLicensePropertiesNotExisted(getCMPProductId());

		assertBundlesExisted(_cmpSymbolicNames);

		assertPortalLicenseNotRegistered();

		assertBundlesExisted(_cmpSymbolicNames);

		deployFreeTierPortalLicense(Time.HOUR);

		assertLicensePropertiesNotExisted(getCMPProductId());

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_cmpSymbolicNames);

		File binaryFile = deployCMPLicense(Time.HOUR);

		assertLicensePropertiesExisted(getCMPProductId());

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_cmpSymbolicNames);

		binaryFile.delete();

		checkLicense(getCMPProductId());

		assertLicensePropertiesNotExisted(getCMPProductId());

		resetLifecycleAction();

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_cmpSymbolicNames);
	}

	private static String[] _cmpSymbolicNames;
	private static SafeCloseable _disableKeyValidatorSafeCloseable;
	private static SafeCloseable _setVersionSafeCloseable;

	private SafeCloseable _safeCloseable;

}
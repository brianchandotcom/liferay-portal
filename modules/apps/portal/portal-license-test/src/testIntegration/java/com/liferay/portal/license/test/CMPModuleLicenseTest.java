/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.File;

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
	public void testBrokenCMPFile() throws Exception {
		assertPortalInvalidatedWithBrokenFile(getProperty("cmp.file.path"));
	}

	@Test
	public void testEnterpriseLicense() throws Exception {
		assertLicensePropertiesNotExisted(getCMPProductId());

		assertBundlesExisted(_getCMPSymbolicNames());

		assertPortalLicenseNotRegistered();

		assertBundlesExisted(_getCMPSymbolicNames());

		deployEnterprisePortalLicense(Time.HOUR);

		assertLicensePropertiesNotExisted(getCMPProductId());

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_getCMPSymbolicNames());

		File binaryFile = deployCMPLicense(Time.HOUR);

		assertLicensePropertiesExisted(getCMPProductId());

		assertPortalLicenseRegistered();

		assertBundlesExisted(_getCMPSymbolicNames());

		binaryFile.delete();

		LicenseManagerUtil.checkLicense(getCMPProductId());

		assertLicensePropertiesNotExisted(getCMPProductId());

		resetLifecycleAction();

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_getCMPSymbolicNames());
	}

	@Test
	public void testFreeTierLicense() throws Exception {
		assertLicensePropertiesNotExisted(getCMPProductId());

		assertBundlesExisted(_getCMPSymbolicNames());

		assertPortalLicenseNotRegistered();

		assertBundlesExisted(_getCMPSymbolicNames());

		deployFreeTierPortalLicense(Time.HOUR);

		assertLicensePropertiesNotExisted(getCMPProductId());

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_getCMPSymbolicNames());

		File binaryFile = deployCMPLicense(Time.HOUR);

		assertLicensePropertiesExisted(getCMPProductId());

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_getCMPSymbolicNames());

		binaryFile.delete();

		LicenseManagerUtil.checkLicense(getCMPProductId());

		assertLicensePropertiesNotExisted(getCMPProductId());

		resetLifecycleAction();

		assertPortalLicenseRegistered();

		assertBundlesNotExisted(_getCMPSymbolicNames());
	}

	private String[] _getCMPSymbolicNames() {
		String property = getProperty("cmp.symbolic.names");

		return property.split(StringPool.COMMA);
	}

	private static ResettableClassFileTransformer
		_disableKeyValidatorResettableClassFileTransformer;
	private static ResettableClassFileTransformer
		_setVersionResettableClassFileTransformer;

}
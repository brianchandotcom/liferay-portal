/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.util.Time;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class ExpiredLicenseTest extends BaseLicenseTestCase {

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

	@After
	public void tearDown() throws Exception {
		resetLicenseData();
		resetLifecycleAction();
	}

	@Test
	public void testCMPLicenseExpired() throws Exception {
		assertLicensePropertiesNotExisted(getCMPProductId());

		deployCMPLicense(_GRACE_PEIROD + _VALIDTY_PERIOD);

		assertLicensePropertiesExisted(getCMPProductId());

		Assert.assertTrue(LicenseManagerUtil.isCMPEnabled());

		Thread.sleep(_VALIDTY_PERIOD);

		assertLicensePropertiesExisted(getCMPProductId());

		Assert.assertFalse(LicenseManagerUtil.isCMPEnabled());
	}

	@Test
	public void testEnterpriseLicenseExpired() throws Exception {
		assertLicensePropertiesNotExisted(getPortalProductId());

		assertPortalLicenseNotRegistered();

		deployEnterprisePortalLicense(_GRACE_PEIROD + _VALIDTY_PERIOD);

		assertLicensePropertiesExisted(getPortalProductId());

		assertPortalLicenseRegistered();

		Thread.sleep(_VALIDTY_PERIOD);

		assertLicensePropertiesExisted(getPortalProductId());

		assertPortalLicenseExpired();
	}

	@Test
	public void testFreeAndEnterpriseExpired() throws Exception {
		assertLicensePropertiesNotExisted(getPortalProductId());

		assertPortalLicenseNotRegistered();

		deployFreeTierPortalLicense(Time.HOUR);

		assertLicensePropertiesExisted(getPortalProductId());

		assertPortalLicenseRegistered();

		Assert.assertTrue(LicenseManagerUtil.isFreeTier());

		deployEnterprisePortalLicense(_GRACE_PEIROD + _VALIDTY_PERIOD);

		assertLicensePropertiesExisted(getPortalProductId());

		assertPortalLicenseRegistered();

		Assert.assertFalse(LicenseManagerUtil.isFreeTier());

		Thread.sleep(_VALIDTY_PERIOD);

		assertLicensePropertiesExisted(getPortalProductId());

		assertPortalLicenseExpired();

		resetLifecycleAction();

		LicenseManagerUtil.checkLicense(getPortalProductId());

		assertLicensePropertiesExisted(getPortalProductId());

		assertPortalLicenseRegistered();

		Assert.assertTrue(LicenseManagerUtil.isFreeTier());
	}

	@Test
	public void testFreeTierLicenseExpired() throws Exception {
		assertLicensePropertiesNotExisted(getPortalProductId());

		assertPortalLicenseNotRegistered();

		deployFreeTierPortalLicense(_GRACE_PEIROD + _VALIDTY_PERIOD);

		assertLicensePropertiesExisted(getPortalProductId());

		assertPortalLicenseRegistered();

		Thread.sleep(_VALIDTY_PERIOD);

		assertLicensePropertiesExisted(getPortalProductId());

		assertPortalLicenseExpired();
	}

	private static final long _GRACE_PEIROD = -2 * Time.DAY;

	private static final long _VALIDTY_PERIOD = 15 * Time.SECOND;

	private static SafeCloseable _disableKeyValidatorSafeCloseable;
	private static SafeCloseable _setVersionSafeCloseable;

}
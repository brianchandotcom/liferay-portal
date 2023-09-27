/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.email.otp.web.internal.checker;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Stian Sigvartsen
 */
public class EmailOTPBrowserMFACheckerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Mockito.when(
			FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_frameworkUtilMockedStatic.close();
	}

	@Test
	public void testObfuscateEmailAddress() throws Exception {
		Assert.assertEquals(
			"*@liferay.com",
			EmailOTPBrowserMFAChecker.obfuscateEmailAddress("t@liferay.com"));
		Assert.assertEquals(
			"**@liferay.com",
			EmailOTPBrowserMFAChecker.obfuscateEmailAddress("te@liferay.com"));
		Assert.assertEquals(
			"***@liferay.com",
			EmailOTPBrowserMFAChecker.obfuscateEmailAddress("tes@liferay.com"));
		Assert.assertEquals(
			"t***@liferay.com",
			EmailOTPBrowserMFAChecker.obfuscateEmailAddress(
				"test@liferay.com"));
		Assert.assertEquals(
			"t***1@liferay.com",
			EmailOTPBrowserMFAChecker.obfuscateEmailAddress(
				"test1@liferay.com"));
		Assert.assertEquals(
			"te***1@liferay.com",
			EmailOTPBrowserMFAChecker.obfuscateEmailAddress(
				"test11@liferay.com"));
	}

	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);

}
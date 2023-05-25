/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
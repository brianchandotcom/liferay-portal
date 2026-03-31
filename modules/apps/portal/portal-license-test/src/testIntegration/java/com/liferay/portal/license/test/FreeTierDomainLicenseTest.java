/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.net.InetAddress;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kevin Lee
 */
@RunWith(Arquillian.class)
public class FreeTierDomainLicenseTest extends BaseLicenseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		Assume.assumeTrue(isReleaseBundle());
	}

	@Test
	public void testFreeTierLicenseValidateDomain() throws Exception {
		InetAddress inetAddress = InetAddress.getLocalHost();

		try (SafeCloseable safeCloseable1 = disableValidateWithSafeCloseable();
			SafeCloseable safeCloseable2 = setVersionWithSafeCloseable(
				"2026.Q1.0 LTS")) {

			_assertDomainIsValid("localhost");
			_assertDomainIsValid("LOCALHOST");
			_assertDomainIsValid(inetAddress.getCanonicalHostName());
			_assertDomainIsValid(inetAddress.getHostName());

			_assertDomainIsInvalid(RandomTestUtil.randomString());
		}
	}

	private void _assertDomainIsInvalid(String domain) throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				getLicenseManagerClassName(), LoggerTestUtil.ERROR)) {

			deployFreeTierPortalLicense(Time.HOUR, domain);

			assertLicenseValidationFailedLog(
				logCapture,
				"Current domain is not allowed, allowed domains are: " +
					domain);

			assertPortalLicenseInvalid();
		}
		finally {
			resetLicenseData();
		}
	}

	private void _assertDomainIsValid(String domain) throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				getLicenseManagerClassName(), LoggerTestUtil.ERROR)) {

			deployFreeTierPortalLicense(Time.HOUR, domain);

			assertPortalLicenseRegistered();

			Assert.assertTrue(ListUtil.isEmpty(logCapture.getLogEntries()));
		}
		finally {
			resetLicenseData();
		}
	}

}
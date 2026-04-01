/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.net.InetAddress;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		_disableKeyValidatorSafeCloseable = disableValidateWithSafeCloseable();
		_setVersionSafeCloseable = setVersionWithSafeCloseable("2026.Q1.0 LTS");

		InetAddress inetAddress = InetAddress.getLocalHost();

		Object cachedLocalHost = ReflectionTestUtil.getFieldValue(
			inetAddress, "cachedLocalHost");

		long originalExpiryTime = ReflectionTestUtil.getAndSetFieldValue(
			cachedLocalHost, "expiryTime",
			System.currentTimeMillis() + Time.HOUR);

		_expiryTimeSafeCloseable = () -> ReflectionTestUtil.setFieldValue(
			cachedLocalHost, "expiryTime", originalExpiryTime);
	}

	@AfterClass
	public static void tearDownClass() {
		_disableKeyValidatorSafeCloseable.close();
		_expiryTimeSafeCloseable.close();
		_setVersionSafeCloseable.close();
	}

	@Test
	public void testLocalHostAddressWithCustomDomain() throws Exception {
		String domain = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable1 = _setInetAddressWithSafeCloseable(
				InetAddress.getLoopbackAddress(),
				RandomTestUtil.randomString());
			SafeCloseable safeCloseable2 = _setInetAddressWithSafeCloseable(
				InetAddress.getLocalHost(), domain)) {

			_testDomain(domain, false);
		}
	}

	@Test
	public void testLocalHostAddressWithCustomSubdomain() throws Exception {
		String domain = RandomTestUtil.randomString();

		String subdomain = StringBundler.concat(
			RandomTestUtil.randomString(), StringPool.PERIOD,
			RandomTestUtil.randomString(), StringPool.PERIOD, domain);

		try (SafeCloseable safeCloseable1 = _setInetAddressWithSafeCloseable(
				InetAddress.getLoopbackAddress(),
				RandomTestUtil.randomString());
			SafeCloseable safeCloseable2 = _setInetAddressWithSafeCloseable(
				InetAddress.getLocalHost(), subdomain)) {

			_testDomain(domain, false);
		}
	}

	@Test
	public void testLocalHostAddressWithLocalHost() throws Exception {
		try (SafeCloseable safeCloseable1 = _setInetAddressWithSafeCloseable(
				InetAddress.getLoopbackAddress(),
				RandomTestUtil.randomString());
			SafeCloseable safeCloseable2 = _setInetAddressWithSafeCloseable(
				InetAddress.getLocalHost(), "localhost")) {

			_testDomain(RandomTestUtil.randomString(), true);
		}
	}

	@Test
	public void testLoopbackAddressWithCustomDomain() throws Exception {
		String domain = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable = _setInetAddressWithSafeCloseable(
				InetAddress.getLoopbackAddress(), domain)) {

			_testDomain(domain, false);
		}
	}

	@Test
	public void testLoopbackAddressWithCustomSubdomain() throws Exception {
		String domain = RandomTestUtil.randomString();

		String subdomain = StringBundler.concat(
			RandomTestUtil.randomString(), StringPool.PERIOD,
			RandomTestUtil.randomString(), StringPool.PERIOD, domain);

		try (SafeCloseable safeCloseable = _setInetAddressWithSafeCloseable(
				InetAddress.getLoopbackAddress(), subdomain)) {

			_testDomain(domain, false);
		}
	}

	@Test
	public void testLoopbackAddressWithLocalHost() throws Exception {
		try (SafeCloseable safeCloseable = _setInetAddressWithSafeCloseable(
				InetAddress.getLoopbackAddress(), "localhost")) {

			_testDomain(RandomTestUtil.randomString(), true);
		}
	}

	private void _assertDomainIsInvalid(String domain) throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				getLicenseManagerClassName(), LoggerTestUtil.ERROR)) {

			deployFreeTierPortalLicense(domain, Time.HOUR);

			assertLicenseValidationFailedLog(
				logCapture,
				"Current domain is not allowed, allowed domains are: " +
					StringUtil.merge(new String[] {domain, "localhost"}));
		}
		finally {
			resetLicenseData();
		}
	}

	private void _assertDomainIsValid(String domain) throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				getLicenseManagerClassName(), LoggerTestUtil.ERROR)) {

			deployFreeTierPortalLicense(domain, Time.HOUR);

			Assert.assertTrue(ListUtil.isEmpty(logCapture.getLogEntries()));
		}
		finally {
			resetLicenseData();
		}
	}

	private SafeCloseable _setInetAddressWithSafeCloseable(
		InetAddress inetAddress, String hostName) {

		String originalCanonicalHostName =
			ReflectionTestUtil.getAndSetFieldValue(
				inetAddress, "canonicalHostName", hostName);

		Object holder = ReflectionTestUtil.getFieldValue(inetAddress, "holder");

		String originalHostName = ReflectionTestUtil.getAndSetFieldValue(
			holder, "hostName", hostName);

		return () -> {
			ReflectionTestUtil.setFieldValue(
				inetAddress, "canonicalHostName", originalCanonicalHostName);
			ReflectionTestUtil.setFieldValue(
				holder, "hostName", originalHostName);
		};
	}

	private void _testDomain(String domain, boolean localhost)
		throws Exception {

		_assertDomainIsValid(domain);
		_assertDomainIsValid(StringUtil.toUpperCase(domain));
		_assertDomainIsValid(StringUtil.toLowerCase(domain));

		if (localhost) {
			_assertDomainIsValid(RandomTestUtil.randomString());
		}
		else {
			_assertDomainIsInvalid(RandomTestUtil.randomString());
		}
	}

	private static SafeCloseable _disableKeyValidatorSafeCloseable;
	private static SafeCloseable _expiryTimeSafeCloseable;
	private static SafeCloseable _setVersionSafeCloseable;

}
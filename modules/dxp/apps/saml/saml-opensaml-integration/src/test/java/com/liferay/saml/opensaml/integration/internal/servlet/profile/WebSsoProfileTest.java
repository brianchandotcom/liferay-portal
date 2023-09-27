/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.servlet.profile;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mika Koivisto
 */
public class WebSsoProfileTest extends BaseSamlTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ReflectionTestUtil.setFieldValue(
			_webSsoProfileImpl, "samlProviderConfigurationHelper",
			samlProviderConfigurationHelper);
	}

	@Test
	public void testVerifyNotBeforeDateTimeLessThanSkew()
		throws PortalException {

		DateTime dateTime = new DateTime(DateTimeZone.UTC);

		_webSsoProfileImpl.verifyNotBeforeDateTime(
			dateTime, 3000, dateTime.minusMillis(50000));
	}

	@Test
	public void testVerifyNotBeforeDateTimeMoreThanNow() {
		DateTime dateTime = new DateTime(DateTimeZone.UTC);

		try {
			_webSsoProfileImpl.verifyNotBeforeDateTime(
				dateTime, 3000, dateTime.plusMillis(4000));

			Assert.fail("Date verification failed");
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}
	}

	@Test
	public void testVerifyNotBeforeDateTimeMoreThanNowSmallerSkew()
		throws PortalException {

		DateTime dateTime = new DateTime(DateTimeZone.UTC);

		_webSsoProfileImpl.verifyNotBeforeDateTime(
			dateTime, 3000, dateTime.plusMillis(300));
	}

	@Test
	public void testVerifyNotBeforeDateTimeMoreThanSkew()
		throws PortalException {

		DateTime dateTime = new DateTime(DateTimeZone.UTC);

		_webSsoProfileImpl.verifyNotBeforeDateTime(
			dateTime, 3000, dateTime.minusMillis(200));
	}

	@Test
	public void testVerifyNotOnOrAfterDateTimeLessThanNow() {
		DateTime dateTime = new DateTime(DateTimeZone.UTC);

		try {
			_webSsoProfileImpl.verifyNotOnOrAfterDateTime(
				dateTime, 3000, dateTime.minusMillis(4000));

			Assert.fail("Date verification failed");
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}
	}

	@Test
	public void testVerifyNotOnOrAfterDateTimeLessThanNowSmallerSkew()
		throws PortalException {

		DateTime dateTime = new DateTime(DateTimeZone.UTC);

		_webSsoProfileImpl.verifyNotOnOrAfterDateTime(
			dateTime, 3000, dateTime.minusMillis(300));
	}

	@Test
	public void testVerifyNotOnOrAfterDateTimeLessThanSkew()
		throws PortalException {

		DateTime dateTime = new DateTime(DateTimeZone.UTC);

		_webSsoProfileImpl.verifyNotOnOrAfterDateTime(
			dateTime, 3000, dateTime.plusMillis(200));
	}

	@Test
	public void testVerifyNotOnOrAfterDateTimeMoreThanSkew()
		throws PortalException {

		DateTime dateTime = new DateTime(DateTimeZone.UTC);

		_webSsoProfileImpl.verifyNotOnOrAfterDateTime(
			dateTime, 3000, dateTime.plusMillis(50000));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebSsoProfileTest.class);

	private final WebSsoProfileImpl _webSsoProfileImpl =
		new WebSsoProfileImpl();

}
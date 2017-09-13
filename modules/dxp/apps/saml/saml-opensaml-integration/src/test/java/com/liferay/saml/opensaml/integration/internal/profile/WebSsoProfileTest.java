/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.saml.opensaml.integration.internal.profile;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mika Koivisto
 */
public class WebSsoProfileTest extends BaseSamlTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_webSsoProfileImpl.setSamlProviderConfigurationHelper(
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
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
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
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
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
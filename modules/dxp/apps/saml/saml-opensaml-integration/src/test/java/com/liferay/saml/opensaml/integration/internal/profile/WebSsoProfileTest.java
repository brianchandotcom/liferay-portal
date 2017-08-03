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
	public void testVerifyNotOnOrAfterDateTimeLessThanNow() {
		DateTime dateTime = new DateTime(DateTimeZone.UTC);

		dateTime = dateTime.minusMillis(4000);

		try {
			_webSsoProfileImpl.verifyNotOnOrAfterDateTime(3000, dateTime);

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

		dateTime = dateTime.minusMillis(300);

		_webSsoProfileImpl.verifyNotOnOrAfterDateTime(3000, dateTime);
	}

	@Test
	public void testVerifyNotOnOrAfterDateTimeLessThanSkew()
		throws PortalException {

		DateTime dateTime = new DateTime(DateTimeZone.UTC);

		dateTime = dateTime.plusMillis(200);

		_webSsoProfileImpl.verifyNotOnOrAfterDateTime(3000, dateTime);
	}

	@Test
	public void testVerifyNotOnOrAfterDateTimeMoreThanSkew()
		throws PortalException {

		DateTime dateTime = new DateTime(DateTimeZone.UTC);

		dateTime = dateTime.plusMillis(50000);

		_webSsoProfileImpl.verifyNotOnOrAfterDateTime(3000, dateTime);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebSsoProfileTest.class);

	private final WebSsoProfileImpl _webSsoProfileImpl =
		new WebSsoProfileImpl();

}
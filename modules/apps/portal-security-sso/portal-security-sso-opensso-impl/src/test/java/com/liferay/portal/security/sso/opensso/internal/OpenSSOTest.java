/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.opensso.internal;

import com.liferay.portal.kernel.security.sso.OpenSSO;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Lucas Miranda
 */
public class OpenSSOTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_openSSO = new OpenSSOImpl();
	}

	@Test
	public void testIsValidServiceUrlWithLocalAddress() throws Exception {
		Assert.assertFalse(
			_openSSO.isValidServiceUrl("http://localhost:8008/redirect"));
	}

	private OpenSSO _openSSO;

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.helper;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuele Castro
 */
public class RelayStateHelperTest extends BaseSamlTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ReflectionTestUtil.invoke(
			_relayStateHelperImpl, "activate", new Class<?>[0]);
	}

	@Test
	public void testGetRedirectFromRelayStateTokenIdpInitiatedSso() {
		Assert.assertEquals(
			RELAY_STATE,
			_relayStateHelperImpl.getRedirectFromRelayStateToken(RELAY_STATE));
	}

	@Test
	public void testGetRedirectFromRelayStateTokenSpInitiatedSso() {
		String relayState =
			_relayStateHelperImpl.getRelayStateTokenFromRedirect(RELAY_STATE);

		Assert.assertNotEquals(RELAY_STATE, relayState);

		Assert.assertEquals(
			RELAY_STATE,
			_relayStateHelperImpl.getRedirectFromRelayStateToken(relayState));
	}

	private final RelayStateHelperImpl _relayStateHelperImpl =
		new RelayStateHelperImpl();

}
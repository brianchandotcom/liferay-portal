/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.struts;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Manuele Castro
 */
public class AssertionConsumerServiceActionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_assertionConsumerServiceAction = Mockito.spy(
			new AssertionConsumerServiceAction());

		SamlProviderConfigurationHelper samlProviderConfigurationHelper =
			Mockito.mock(SamlProviderConfigurationHelper.class);

		Mockito.when(
			samlProviderConfigurationHelper.isEnabled()
		).thenReturn(
			true
		);

		Mockito.when(
			samlProviderConfigurationHelper.isRoleIdp()
		).thenReturn(
			false
		);

		ReflectionTestUtil.setFieldValue(
			_assertionConsumerServiceAction, "_samlProviderConfigurationHelper",
			samlProviderConfigurationHelper);
	}

	@Test
	public void testExecute() throws Exception {
		Assert.assertEquals(
			"/common/referer_js.jsp",
			_assertionConsumerServiceAction.execute(
				new MockHttpServletRequest(
					"GET", "http://localhost:8080/c/portal/saml/acs"),
				new MockHttpServletResponse()));

		Mockito.verify(
			_assertionConsumerServiceAction, Mockito.times(0)
		).doExecute(
			Mockito.any(), Mockito.any()
		);
	}

	private AssertionConsumerServiceAction _assertionConsumerServiceAction;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.template.servlet;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public class RESTClientHttpRequestDelegateTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_portalUtilMockedStatic.close();
	}

	@Test
	public void testGetParameter() {
		_portalUtilMockedStatic.when(
			() -> PortalUtil.getLocale(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			LocaleUtil.US
		);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		String parameterName = RandomTestUtil.randomString();

		mockHttpServletRequest.setParameter(
			parameterName, RandomTestUtil.randomString());

		RESTClientHttpRequestDelegate restClientHttpRequestDelegate =
			new RESTClientHttpRequestDelegate(
				new HashMap<>(), mockHttpServletRequest,
				RandomTestUtil.randomString());

		Assert.assertNull(
			restClientHttpRequestDelegate.getParameter(parameterName));
	}

	private static final MockedStatic<PortalUtil> _portalUtilMockedStatic =
		Mockito.mockStatic(PortalUtil.class);

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.template.servlet;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

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

	@Test
	public void testGetParameter() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(Mockito.mock(Portal.class));

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

}
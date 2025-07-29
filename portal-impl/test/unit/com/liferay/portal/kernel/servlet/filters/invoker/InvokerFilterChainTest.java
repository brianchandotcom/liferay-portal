/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.filters.invoker;

import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

import jakarta.servlet.Filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Jorge Avalos
 */
public class InvokerFilterChainTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpPortalUtil();
	}

	@Test
	public void testInvokerFilterChainSetCompanyId() throws Exception {
		InvokerFilterChain invokerFilterChain = new InvokerFilterChain(
			new MockFilterChain());

		long companyId = RandomTestUtil.randomLong();

		invokerFilterChain.addFilter(Mockito.mock(Filter.class));

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.COMPANY_ID, companyId);

		invokerFilterChain.doFilter(
			mockHttpServletRequest, new MockHttpServletResponse());

		Assert.assertEquals(
			CompanyThreadLocal.getCompanyId(), companyId,
			CompanyThreadLocal.getCompanyId());
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

}
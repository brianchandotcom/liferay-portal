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

package com.liferay.portal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class PortalImplHostTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetHostWithIPv6Address() {
		_assertGetHost("[::1]", "::1");
		_assertGetHost("[::1]:80", "::1");
		_assertGetHost("[0:0:0:0:0:0:0:1]", "0:0:0:0:0:0:0:1");
		_assertGetHost("[0:0:0:0:0:0:0:1]:80", "0:0:0:0:0:0:0:1");
	}

	private void _assertGetHost(String httpHostHeader, String host) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("Host", httpHostHeader);

		Assert.assertEquals(host, _portal.getHost(mockHttpServletRequest));
	}

	@Inject
	private Portal _portal;

}
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet.filters.invoker;

import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.util.HttpImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Mika Koivisto
 */
@RunWith(PowerMockRunner.class)
public class InvokerFilterTest extends PowerMockito {

	@Before
	public void setUp() {
		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());
	}

	@Test
	public void testGetURIWithJSessionId() {
		InvokerFilter invokerFilter = new InvokerFilter();

		String uri = "/c/portal/login;jsessionid=ae01b0f2af.worker1";

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(HttpMethods.GET, uri);

		Assert.assertEquals(
			"/c/portal/login", invokerFilter.getURI(mockHttpServletRequest));
	}

}
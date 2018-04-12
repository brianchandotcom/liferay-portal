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

package com.liferay.oauth2.provider.rest.internal.request.scope.checker.filter;

import static junit.framework.TestCase.assertTrue;

import com.liferay.oauth2.provider.rest.spi.request.scope.checker.filter.RequestScopeCheckerFilter;

import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Request;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(PowerMockRunner.class)
public class HttpMethodRequestScopeCheckerTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		methodAllowedChecker = new HttpMethodRequestScopeChecker();
		resourceInfo = Mockito.mock(ResourceInfo.class);
	}

	@Test
	public void testHttpMethodAllowed() throws NoSuchMethodException {
		TestScopeChecker testScopeChecker = new TestScopeChecker("GET");

		Request request = Mockito.mock(Request.class);

		when(
			request.getMethod()
		).thenReturn(
			"GET"
		);

		assertTrue(
			methodAllowedChecker.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	protected RequestScopeCheckerFilter methodAllowedChecker;
	protected ResourceInfo resourceInfo;

}
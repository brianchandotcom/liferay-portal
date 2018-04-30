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

package com.liferay.oauth2.provider.rest.internal.spi.request.scope.checker.filter;

import static junit.framework.TestCase.assertTrue;

import com.liferay.oauth2.provider.rest.spi.request.scope.checker.filter.RequestScopeCheckerFilter;
import com.liferay.oauth2.provider.rest.spi.request.scope.checker.filter.TestScopeChecker;

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
public class HttpMethodRequestScopeCheckerFilterTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		requestScopeCheckerFilter = new HttpMethodRequestScopeCheckerFilter();
		resourceInfo = Mockito.mock(ResourceInfo.class);
	}

	@Test
	public void testIsAllowed() throws NoSuchMethodException {
		TestScopeChecker testScopeChecker = new TestScopeChecker("GET");

		Request request = Mockito.mock(Request.class);

		when(
			request.getMethod()
		).thenReturn(
			"GET"
		);

		assertTrue(
			requestScopeCheckerFilter.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	protected RequestScopeCheckerFilter requestScopeCheckerFilter;
	protected ResourceInfo resourceInfo;

}
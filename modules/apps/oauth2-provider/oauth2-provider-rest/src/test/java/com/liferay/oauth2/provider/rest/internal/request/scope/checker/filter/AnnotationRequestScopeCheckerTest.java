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

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import com.liferay.oauth2.provider.scope.RequiresScope;

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
public class AnnotationRequestScopeCheckerTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		annotationRequestScopeChecker = new AnnotationRequestScopeChecker();
		request = Mockito.mock(Request.class);
	}

	@Test
	public void testIsAllowed() throws NoSuchMethodException {
		TestScopeChecker testScopeChecker = new TestScopeChecker("READ");

		ResourceInfo resourceInfo = Mockito.mock(ResourceInfo.class);

		when(
			resourceInfo.getResourceMethod()
		).thenReturn(
			TestEndpointSample.class.getMethod("hello", new Class<?>[0])
		);

		assertTrue(
			annotationRequestScopeChecker.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	@Test
	public void testMethodAllowed() throws NoSuchMethodException {
		TestScopeChecker testScopeChecker = new TestScopeChecker("WRITE");

		ResourceInfo resourceInfo = Mockito.mock(ResourceInfo.class);

		when(
			resourceInfo.getResourceMethod()
		).thenReturn(
			TestEndpointSample.class.getMethod("modify", new Class<?>[0])
		);

		assertTrue(
			annotationRequestScopeChecker.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	@Test
	public void testMethodAllowedWithMultipleAllRequired()
		throws NoSuchMethodException {

		TestScopeChecker testScopeChecker = new TestScopeChecker(
			"READ", "WRITE");

		ResourceInfo resourceInfo = Mockito.mock(ResourceInfo.class);

		when(
			resourceInfo.getResourceMethod()
		).thenReturn(
			TestEndpointSample.class.getMethod("requiresAll", new Class<?>[0])
		);

		assertTrue(
			annotationRequestScopeChecker.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	@Test
	public void testMethodAllowedWithMultipleAllRequiredAndNotGranted()
		throws NoSuchMethodException {

		TestScopeChecker testScopeChecker = new TestScopeChecker(
			"READ", "WRITE");

		ResourceInfo resourceInfo = Mockito.mock(ResourceInfo.class);

		when(
			resourceInfo.getResourceMethod()
		).thenReturn(
			TestEndpointSample.class.getMethod(
				"requiresTooMany", new Class<?>[0])
		);

		assertFalse(
			annotationRequestScopeChecker.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	@Test
	public void testMethodAllowedWithMultipleSomeRequired()
		throws NoSuchMethodException {

		TestScopeChecker testScopeChecker = new TestScopeChecker("READ");

		ResourceInfo resourceInfo = Mockito.mock(ResourceInfo.class);

		when(
			resourceInfo.getResourceMethod()
		).thenReturn(
			TestEndpointSample.class.getMethod("requiresAny", new Class<?>[0])
		);

		assertTrue(
			annotationRequestScopeChecker.isAllowed(
				testScopeChecker, request, resourceInfo));

		testScopeChecker = new TestScopeChecker("WRITE");

		assertTrue(
			annotationRequestScopeChecker.isAllowed(
				testScopeChecker, request, resourceInfo));

		testScopeChecker = new TestScopeChecker("RANDOM");

		assertFalse(
			annotationRequestScopeChecker.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	protected AnnotationRequestScopeChecker annotationRequestScopeChecker;
	protected Request request;

	private static class TestEndpointSample {

		@RequiresScope("READ")
		public String hello() {
			return "hello";
		}

		@RequiresScope("WRITE")
		public void modify() {
		}

		@RequiresScope({"READ", "WRITE"})
		public void requiresAll() {
		}

		@RequiresScope(allNeeded = false, value = {"READ", "WRITE"})
		public void requiresAny() {
		}

		@RequiresScope({"READ", "WRITE", "NOTGRANTED"})
		public void requiresTooMany() {
		}

	}

}
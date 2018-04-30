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

import com.liferay.oauth2.provider.rest.spi.request.scope.checker.filter.RequestScopeCheckerFilter;
import com.liferay.oauth2.provider.scope.RequiresNoScope;
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
public class AnnotationRequestScopeCheckerFilterTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		requestScopeCheckerFilter = new AnnotationRequestScopeCheckerFilter();
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
			requestScopeCheckerFilter.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	@Test
	public void testIsAllowedWithAnnotationInheritance()
		throws NoSuchMethodException {

		TestScopeChecker testScopeChecker = new TestScopeChecker("SUPER");

		ResourceInfo resourceInfo = Mockito.mock(ResourceInfo.class);

		doReturn(
			TestEndpointSample.class
		).when(
			resourceInfo
		).getResourceClass();

		when(
			resourceInfo.getResourceMethod()
		).thenReturn(
			TestEndpointSample.class.getMethod(
				"annotationOnSuperType", new Class<?>[0])
		);

		assertTrue(
			requestScopeCheckerFilter.isAllowed(
				testScopeChecker, request, resourceInfo));

		testScopeChecker = new TestScopeChecker("RANDOM");

		assertFalse(
			requestScopeCheckerFilter.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	@Test(expected = RuntimeException.class)
	public void testIsAllowedWithWrongAnnotationsCombination()
		throws NoSuchMethodException {

		TestScopeChecker testScopeChecker = new TestScopeChecker("RANDOM");

		ResourceInfo resourceInfo = Mockito.mock(ResourceInfo.class);

		when(
			resourceInfo.getResourceMethod()
		).thenReturn(
			TestEndpointSample.class.getMethod(
				"badAnnotationsCombination", new Class<?>[0])
		);

		requestScopeCheckerFilter.isAllowed(
			testScopeChecker, request, resourceInfo);
	}

	@Test
	public void testMethodIsAllowed() throws NoSuchMethodException {
		TestScopeChecker testScopeChecker = new TestScopeChecker("WRITE");

		ResourceInfo resourceInfo = Mockito.mock(ResourceInfo.class);

		when(
			resourceInfo.getResourceMethod()
		).thenReturn(
			TestEndpointSample.class.getMethod("modify", new Class<?>[0])
		);

		assertTrue(
			requestScopeCheckerFilter.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	@Test
	public void testMethodIsAllowedWithMultipleWhenAllRequired()
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
			requestScopeCheckerFilter.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	@Test
	public void testMethodIsAllowedWithMultipleWhenAllRequiredAndNotGranted()
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
			requestScopeCheckerFilter.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	@Test
	public void testMethodIsAllowedWithMultipleWhenSomeRequired()
		throws NoSuchMethodException {

		TestScopeChecker testScopeChecker = new TestScopeChecker("READ");

		ResourceInfo resourceInfo = Mockito.mock(ResourceInfo.class);

		when(
			resourceInfo.getResourceMethod()
		).thenReturn(
			TestEndpointSample.class.getMethod("requiresAny", new Class<?>[0])
		);

		assertTrue(
			requestScopeCheckerFilter.isAllowed(
				testScopeChecker, request, resourceInfo));

		testScopeChecker = new TestScopeChecker("WRITE");

		assertTrue(
			requestScopeCheckerFilter.isAllowed(
				testScopeChecker, request, resourceInfo));

		testScopeChecker = new TestScopeChecker("RANDOM");

		assertFalse(
			requestScopeCheckerFilter.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	@Test
	public void testMethodIsAllowedWithoutAnnotation()
		throws NoSuchMethodException {

		TestScopeChecker testScopeChecker = new TestScopeChecker("RANDOM");

		ResourceInfo resourceInfo = Mockito.mock(ResourceInfo.class);

		doReturn(
			TestEndpointSample.class
		).when(
			resourceInfo
		).getResourceClass();

		when(
			resourceInfo.getResourceMethod()
		).thenReturn(
			TestEndpointSample.class.getMethod("noAnnotation", new Class<?>[0])
		);

		assertFalse(
			requestScopeCheckerFilter.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	@Test
	public void testMethodIsAllowedWithRequiresNoScope()
		throws NoSuchMethodException {

		TestScopeChecker testScopeChecker = new TestScopeChecker("RANDOM");

		ResourceInfo resourceInfo = Mockito.mock(ResourceInfo.class);

		doReturn(
			TestEndpointSample.class
		).when(
			resourceInfo
		).getResourceClass();

		when(
			resourceInfo.getResourceMethod()
		).thenReturn(
			TestEndpointSample.class.getMethod(
				"requiresNoScope", new Class<?>[0])
		);

		assertTrue(
			requestScopeCheckerFilter.isAllowed(
				testScopeChecker, request, resourceInfo));
	}

	@RequiresScope("SUPER")
	public static class TestEndpointSampleSuperType {
	}

	protected Request request;
	protected RequestScopeCheckerFilter requestScopeCheckerFilter;

	private static class TestEndpointSample
		extends TestEndpointSampleSuperType {

		public void annotationOnSuperType() {
		}

		@RequiresNoScope
		@RequiresScope("READ")
		public void badAnnotationsCombination() {
		}

		@RequiresScope("READ")
		public String hello() {
			return "hello";
		}

		@RequiresScope("WRITE")
		public void modify() {
		}

		public void noAnnotation() {
		}

		@RequiresScope({"READ", "WRITE"})
		public void requiresAll() {
		}

		@RequiresScope(allNeeded = false, value = {"READ", "WRITE"})
		public void requiresAny() {
		}

		@RequiresNoScope
		public void requiresNoScope() {
		}

		@RequiresScope({"READ", "WRITE", "NOTGRANTED"})
		public void requiresTooMany() {
		}

	}

}
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

package com.liferay.httpservice.internal.http;

import com.liferay.httpservice.internal.servlet.BundleServletContext;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Miguel Pastor
 */
public abstract class TestTracker<S, T extends ServiceTrackerCustomizer<S, S>>
	extends PowerMockito {

	@Before
	public void setUp() {
		_service = buildService();
		_tracker = buildTracker();
	}

	@Test
	public void testAddingServiceWithEmptyInitParameters() throws Exception {

		mockAddingService(new String[] {});

		_tracker.addingService(_serviceReference);

		verifyAddingService();
	}

	@Test
	public void testAddingServiceWithInitParameters() throws Exception {

		mockAddingService(new String[] {"init.a", "init.b", "foo.c"});

		_tracker.addingService(_serviceReference);

		verifyAddingService();
	}

	@Test
	public void testRemovedService() throws Exception {
		mockRemovedService();

		_tracker.removedService(_serviceReference, _service);

		verifyRemovedService();
	}

	abstract protected S buildService();

	abstract protected T buildTracker();

	protected void mockAction(String[] initParameters)
		throws InvalidSyntaxException {

		when(
			_bundleContext.getService(_serviceReference)
		).thenReturn(
			_service
		);

		when(
			_httpSupport.getBundleServletContext(_bundle)
		).thenReturn(
			_bundleServletContext
		);

		when(
			_httpSupport.getHttpContext(Mockito.anyString())
		).thenReturn(
			_httpContext
		);

		when(
			_serviceReference.getBundle()
		).thenReturn(
			_bundle
		);
	}

	protected void mockAddingService(String[] initParameters) throws Exception {
		when(
			_httpSupport.getBundleContext()
		).thenReturn(
			_bundleContext
		);

		when(
			_serviceReference.getPropertyKeys()
		).thenReturn(
			initParameters
		);

		mockAction(initParameters);
	}

	protected void mockRemovedService() throws Exception {
		mockAction(null);
	}

	protected void verifyAddingService() throws Exception {
		BundleContext bundleContext = Mockito.verify(_bundleContext);

		bundleContext.getService(_serviceReference);

		HttpSupport httpSupport = Mockito.verify(_httpSupport);

		httpSupport.getBundleContext();
		httpSupport.getBundleServletContext(_bundle);
		httpSupport.getHttpContext(Mockito.anyString());

		verifyRegisterServiceAction();
		verifyServiceReference();
	}

	protected abstract void verifyRegisterServiceAction() throws Exception;

	protected void verifyRemovedService() throws Exception {
		verifyServiceReference();
		verifyUnRegisterServiceAction();
	}

	protected void verifyServiceReference() throws InvalidSyntaxException {
		ServiceReference<S> serviceReference = Mockito.verify(
			_serviceReference);

		serviceReference.getBundle();
		serviceReference.getPropertyKeys();
	}

	protected abstract void verifyUnRegisterServiceAction() throws Exception;

	@Mock
	protected Bundle _bundle;

	@Mock
	protected BundleContext _bundleContext;

	@Mock
	protected BundleServletContext _bundleServletContext;

	@Mock
	protected HttpContext _httpContext;

	@Mock
	protected HttpSupport _httpSupport;

	protected S _service;

	@Mock
	protected ServiceReference<S> _serviceReference;

	protected T _tracker;

}
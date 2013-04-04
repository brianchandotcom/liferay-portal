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

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.osgi.util.tracker.ServiceTrackerCustomizer;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Miguel Pastor
 */
@RunWith(PowerMockRunner.class)
public class ServletTrackerTest
	extends TestTracker<Servlet, ServiceTrackerCustomizer<Servlet, Servlet>> {

	@Override
	protected Servlet buildService() {
		return new MockServlet();
	}

	@Override
	protected ServiceTrackerCustomizer<Servlet, Servlet> buildTracker() {
		return new ServletTracker(_httpSupport);
	}

	@Override
	protected void verifyRegisterServiceAction() throws Exception {
		BundleServletContext bundleServletContext = Mockito.verify(
			_bundleServletContext);

		bundleServletContext.registerServlet(
			Mockito.anyString(), Mockito.anyString(), Mockito.eq(_service),
			Mockito.anyMap(), Mockito.eq(_httpContext));
	}

	@Override
	protected void verifyUnRegisterServiceAction() throws Exception {
		BundleServletContext bundleServletContext = Mockito.verify(
			_bundleServletContext);

		bundleServletContext.unregisterServlet(Mockito.anyString());
	}

	private class MockServlet extends GenericServlet {

		@Override
		public void service(
				ServletRequest servletRequest, ServletResponse servletResponse)
			throws IOException, ServletException {
		}
	}

}
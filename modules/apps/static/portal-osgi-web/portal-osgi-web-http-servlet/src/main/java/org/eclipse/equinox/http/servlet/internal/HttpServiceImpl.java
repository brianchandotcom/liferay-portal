/*******************************************************************************
 * Copyright (c) 2005, 2015 Cognos Incorporated, IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cognos Incorporated - initial API and implementation
 *     IBM Corporation - bug fixes and enhancements
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *******************************************************************************/

package org.eclipse.equinox.http.servlet.internal;

import java.io.IOException;
import java.net.URL;
import java.security.*;
import java.util.Dictionary;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.equinox.http.servlet.ExtendedHttpService;
import org.osgi.framework.Bundle;
import org.osgi.service.http.*;

public class HttpServiceImpl implements HttpService, ExtendedHttpService {

	final Bundle bundle; //The bundle associated with this instance of http service
	final HttpServiceRuntimeImpl httpServiceRuntime;
	private boolean shutdown = false; // We prevent use of this instance if HttpServiceFactory.ungetService has called unregisterAliases.

	class DefaultHttpContext implements HttpContext {
		/**
		 * @throws IOException  
		 */
		@Override
		public boolean handleSecurity(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
			return true;
		}

		@Override
		public URL getResource(String name) {
			if (name != null) {
				if (name.startsWith("/")) { //$NON-NLS-1$
					name = name.substring(1);
				}

				return bundle.getEntry(name);
			}
			return null;
		}

		@Override
		public String getMimeType(String name) {
			return null;
		}
		
	}

	public HttpServiceImpl(
		Bundle bundle, HttpServiceRuntimeImpl httpServiceRuntime) {

		this.bundle = bundle;
		this.httpServiceRuntime = httpServiceRuntime;
	}

	/**
	 * @see HttpService#createDefaultHttpContext()
	 */
	public synchronized HttpContext createDefaultHttpContext() {
		checkShutdown();

		return new DefaultHttpContext();
	}

	/**
	 * @throws ServletException 
	 * @see ExtendedHttpService#registerFilter(String, Filter, Dictionary, HttpContext)
	 */
	public synchronized void registerFilter(
			final String alias, final Filter filter, 
			final Dictionary<String, String> initparams,
			HttpContext httpContext)
		throws ServletException {

		checkShutdown();

		final HttpContext finalHttpContext = httpContext == null ? createDefaultHttpContext() : httpContext;
		try {
			AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
				@Override
				public Void run() throws ServletException {
					httpServiceRuntime.registerHttpServiceFilter(bundle, alias, filter, initparams, finalHttpContext);
					return null;
				}
			});
		}
		catch (PrivilegedActionException e) {
			unchecked(e.getException());
		}

	}

	/**
	 * @throws NamespaceException 
	 * @see HttpService#registerResources(String, String, HttpContext)
	 */
	public synchronized void registerResources(
			final String alias, final String name, HttpContext httpContext)
		throws NamespaceException {

		checkShutdown();
		final HttpContext finalHttpContext = httpContext == null ? createDefaultHttpContext() : httpContext;
		try {
			AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
				@Override
				public Void run() throws NamespaceException {
					httpServiceRuntime.registerHttpServiceResources(bundle, alias, name, finalHttpContext);
					return null;
				}
			});
		} catch (PrivilegedActionException e) {
			unchecked(e.getException());
		}

	}

	/**
	 * @throws ServletException 
	 * @throws NamespaceException 
	 * @see HttpService#registerServlet(String, Servlet, Dictionary, HttpContext)
	 */
	public synchronized void registerServlet(
			final String alias, final Servlet servlet, 
			final Dictionary initparams, HttpContext httpContext)
		throws ServletException, NamespaceException {

		checkShutdown();
		final HttpContext finalHttpContext = httpContext == null ? createDefaultHttpContext() : httpContext;
		try {
			AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
				@Override
				public Void run() throws NamespaceException, ServletException {
					httpServiceRuntime.registerHttpServiceServlet(bundle, alias, servlet, initparams, finalHttpContext);
					return null;
				}
			});
		} catch (PrivilegedActionException e) {
			unchecked(e.getException());
		}
	}

	/**
	 * @see HttpService#unregister(String)
	 */
	public synchronized void unregister(String alias) {
		checkShutdown();

		httpServiceRuntime.unregisterHttpServiceAlias(bundle, alias);
	}

	/**
	 * @see ExtendedHttpService#unregisterFilter(Filter)
	 */
	public synchronized void unregisterFilter(Filter filter) {
		checkShutdown();

		httpServiceRuntime.unregisterHttpServiceFilter(bundle, filter);
	}

	//Clean up method
	synchronized void shutdown() {
		httpServiceRuntime.unregisterHttpServiceObjects(bundle);

		shutdown = true;
	}

	private void checkShutdown() {
		if (shutdown) {
			throw new IllegalStateException(
				"Service instance is already shutdown"); //$NON-NLS-1$
		}
	}

	static <T> T unchecked(Exception exception) {
		return HttpServiceImpl.<T, RuntimeException> unchecked0(exception);
	}

	@SuppressWarnings("unchecked")
	private static <T, E extends Exception> T unchecked0(Exception exception) throws E {
		throw (E) exception;
	}
}

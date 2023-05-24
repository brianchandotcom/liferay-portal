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

package com.liferay.portal.osgi.web.http.servlet.internal;

import com.liferay.portal.osgi.web.http.servlet.ExtendedHttpService;

import java.net.URL;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import java.util.Dictionary;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;

/**
 * @author Cognos Incorporated
 * @author IBM Corporation
 * @author Raymond Augé
 */
public class HttpServiceImpl implements ExtendedHttpService, HttpService {

	public HttpServiceImpl(
		Bundle bundle, HttpServiceRuntimeImpl httpServiceRuntimeImpl) {

		_bundle = bundle;
		_httpServiceRuntimeImpl = httpServiceRuntimeImpl;
	}

	@Override
	public synchronized HttpContext createDefaultHttpContext() {
		_checkShutdown();

		return new DefaultHttpContext();
	}

	@Override
	public synchronized void registerFilter(
		String alias, Filter filter, Dictionary<String, String> initParams,
		HttpContext httpContext) {

		_checkShutdown();

		HttpContext finalHttpContext =
			(httpContext == null) ? createDefaultHttpContext() : httpContext;

		try {
			AccessController.doPrivileged(
				(PrivilegedExceptionAction<Void>)() -> {
					_httpServiceRuntimeImpl.registerHttpServiceFilter(
						_bundle, alias, filter, initParams, finalHttpContext);

					return null;
				});
		}
		catch (PrivilegedActionException privilegedActionException) {
			unchecked(privilegedActionException.getException());
		}
	}

	@Override
	public synchronized void registerResources(
		String alias, String name, HttpContext httpContext) {

		_checkShutdown();

		HttpContext finalHttpContext =
			(httpContext == null) ? createDefaultHttpContext() : httpContext;

		try {
			AccessController.doPrivileged(
				(PrivilegedExceptionAction<Void>)() -> {
					_httpServiceRuntimeImpl.registerHttpServiceResources(
						_bundle, alias, name, finalHttpContext);

					return null;
				});
		}
		catch (PrivilegedActionException privilegedActionException) {
			unchecked(privilegedActionException.getException());
		}
	}

	@Override
	public synchronized void registerServlet(
		String alias, Servlet servlet, Dictionary initParams,
		HttpContext httpContext) {

		_checkShutdown();

		HttpContext finalHttpContext =
			(httpContext == null) ? createDefaultHttpContext() : httpContext;

		try {
			AccessController.doPrivileged(
				(PrivilegedExceptionAction<Void>)() -> {
					_httpServiceRuntimeImpl.registerHttpServiceServlet(
						_bundle, alias, servlet, initParams, finalHttpContext);

					return null;
				});
		}
		catch (PrivilegedActionException privilegedActionException) {
			unchecked(privilegedActionException.getException());
		}
	}

	@Override
	public synchronized void unregister(String alias) {
		_checkShutdown();

		_httpServiceRuntimeImpl.unregisterHttpServiceAlias(_bundle, alias);
	}

	@Override
	public synchronized void unregisterFilter(Filter filter) {
		_checkShutdown();

		_httpServiceRuntimeImpl.unregisterHttpServiceFilter(_bundle, filter);
	}

	protected static <T> void unchecked(Exception exception) {
		HttpServiceImpl.<T, RuntimeException>_unchecked(exception);
	}

	protected synchronized void shutdown() {
		_httpServiceRuntimeImpl.unregisterHttpServiceObjects(_bundle);

		_shutdown = true;
	}

	@SuppressWarnings("unchecked")
	private static <T, E extends Exception> void _unchecked(Exception exception)
		throws E {

		throw (E)exception;
	}

	private void _checkShutdown() {
		if (_shutdown) {
			throw new IllegalStateException(
				"Service instance is already shutdown");
		}
	}

	private final Bundle _bundle;
	private final HttpServiceRuntimeImpl _httpServiceRuntimeImpl;
	private boolean _shutdown;

	private class DefaultHttpContext implements HttpContext {

		@Override
		public String getMimeType(String name) {
			return null;
		}

		@Override
		public URL getResource(String name) {
			if (name != null) {
				if (name.startsWith("/")) {
					name = name.substring(1);
				}

				return _bundle.getEntry(name);
			}

			return null;
		}

		@Override
		public boolean handleSecurity(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

			return true;
		}

	}

}
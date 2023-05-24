/*******************************************************************************
 * Copyright (c) Nov 21, 2014 Liferay, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Liferay, Inc. - initial API and implementation and/or initial
 *                    documentation
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Liferay, Inc.
 */
public class HttpContextHelperFactory
	implements ServiceFactory<ServletContextHelper> {

	public HttpContextHelperFactory(HttpContext httpContext) {
		_httpContext = httpContext;
	}

	public long decrementUseCount() {
		long result = _useCount.decrementAndGet();

		if (result == 0) {
			ServiceRegistration<ServletContextHelper> serviceRegistration =
				_serviceRegistrationAtomicReference.get();

			if (serviceRegistration != null) {
				try {
					serviceRegistration.unregister();
				}
				catch (IllegalStateException illegalStateException) {
					if (_log.isDebugEnabled()) {
						_log.debug(illegalStateException);
					}
				}
			}
		}

		return result;
	}

	public String getFilter() {
		return _filterAtomicReference.get();
	}

	public Object getHttpContext() {
		return _httpContext;
	}

	@Override
	public ServletContextHelper getService(
		Bundle bundle,
		ServiceRegistration<ServletContextHelper> serviceRegistration) {

		setRegistration(serviceRegistration);

		return new HttpContextHelper(bundle);
	}

	public ServiceReference<ServletContextHelper> getServiceReference() {
		ServiceRegistration<ServletContextHelper> serviceRegistration =
			_serviceRegistrationAtomicReference.get();

		if (serviceRegistration == null) {
			return null;
		}

		try {
			return serviceRegistration.getReference();
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException);
			}
		}

		return null;
	}

	public long incrementUseCount() {
		return _useCount.incrementAndGet();
	}

	public void setRegistration(
		ServiceRegistration<ServletContextHelper> serviceRegistration) {

		if (_serviceRegistrationAtomicReference.compareAndSet(
				null, serviceRegistration)) {

			StringBundler sb = new StringBundler(5);

			sb.append('(');
			sb.append(Constants.SERVICE_ID);
			sb.append('=');

			ServiceReference<ServletContextHelper> serviceReference =
				serviceRegistration.getReference();

			sb.append(serviceReference.getProperty(Constants.SERVICE_ID));

			sb.append(')');

			_filterAtomicReference.compareAndSet(null, sb.toString());
		}
	}

	@Override
	public void ungetService(
		Bundle bundle,
		ServiceRegistration<ServletContextHelper> serviceRegistration,
		ServletContextHelper servletContextHelper) {
	}

	public class HttpContextHelper extends ServletContextHelper {

		public HttpContextHelper(Bundle bundle) {
			_bundle = bundle;
		}

		@Override
		public String getMimeType(String name) {
			return _httpContext.getMimeType(name);
		}

		@Override
		public URL getResource(String name) {
			return _httpContext.getResource(name);
		}

		@Override
		public Set<String> getResourcePaths(String path) {
			if ((path == null) || (_bundle == null)) {
				return null;
			}

			Enumeration<URL> enumeration = _bundle.findEntries(
				path, null, false);

			if (enumeration == null) {
				return null;
			}

			Set<String> result = new HashSet<>();

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				result.add(url.getPath());
			}

			return result;
		}

		@Override
		public boolean handleSecurity(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
			throws IOException {

			return _httpContext.handleSecurity(
				httpServletRequest, httpServletResponse);
		}

		private final Bundle _bundle;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		HttpContextHelperFactory.class.getName());

	private final AtomicReference<String> _filterAtomicReference =
		new AtomicReference<>();
	private final HttpContext _httpContext;
	private final AtomicReference<ServiceRegistration<ServletContextHelper>>
		_serviceRegistrationAtomicReference = new AtomicReference<>();
	private final AtomicLong _useCount = new AtomicLong(0);

}
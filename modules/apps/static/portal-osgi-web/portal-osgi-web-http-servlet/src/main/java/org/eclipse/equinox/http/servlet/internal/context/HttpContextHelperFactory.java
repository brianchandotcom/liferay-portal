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

package org.eclipse.equinox.http.servlet.internal.context;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.osgi.framework.*;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.context.ServletContextHelper;

public class HttpContextHelperFactory
	implements ServiceFactory<ServletContextHelper> {

	final HttpContext httpContext;
	final AtomicReference<ServiceRegistration<ServletContextHelper>> registrationRef =
		new AtomicReference<ServiceRegistration<ServletContextHelper>>();
	final AtomicReference<String> filterRef = new AtomicReference<String>();
	final AtomicLong useCount = new AtomicLong(0);
	
	public HttpContextHelperFactory(HttpContext httpContext) {
		this.httpContext = httpContext;
	}

	@Override
	public ServletContextHelper getService(
		Bundle bundle, ServiceRegistration<ServletContextHelper> registration) {
		setRegistration(registration);
		return new HttpContextHelper(bundle);
	}

	@Override
	public void ungetService(
		Bundle bundle, ServiceRegistration<ServletContextHelper> registration,
		ServletContextHelper service) {
		// nothing to do
	}

	public void setRegistration(ServiceRegistration<ServletContextHelper> registration) {
		if (this.registrationRef.compareAndSet(null, registration)) {
			StringBuilder filterBuilder = new StringBuilder();
			filterBuilder.append('(');
			filterBuilder.append(Constants.SERVICE_ID);
			filterBuilder.append('=');
			filterBuilder.append(registration.getReference().getProperty(Constants.SERVICE_ID));
			filterBuilder.append(')');
			filterRef.compareAndSet(null, filterBuilder.toString());
		}
	}

	public ServiceReference<ServletContextHelper> getServiceReference() {
		ServiceRegistration<ServletContextHelper> reg = registrationRef.get();
		if (reg != null) {
			try {
				return reg.getReference();
			} catch (IllegalStateException e) {
				// do nothing
			}
		}
		return null;
	}

	public String getFilter() {
		return filterRef.get();
	}


	public long incrementUseCount() {
		return useCount.incrementAndGet();
	}

	public long decrementUseCount() {
		long result = useCount.decrementAndGet();
		if (result == 0) {
			ServiceRegistration<ServletContextHelper> registration = registrationRef.get();
			if (registration != null) {
				try {
					registration.unregister();
				} catch (IllegalStateException e) {
					// ignore; already unregistered
				}
			}
		}
		return result;
	}

	public Object getHttpContext() {
		return httpContext;
	}

	public class HttpContextHelper extends ServletContextHelper {
		private final Bundle bundle;
		
		public HttpContextHelper(Bundle bundle) {
			this.bundle = bundle;
		}

		@Override
		public boolean handleSecurity(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
			return httpContext.handleSecurity(request, response);
		}

		@Override
		public URL getResource(String name) {
			return httpContext.getResource(name);
		}

		@Override
		public String getMimeType(String name) {
			return httpContext.getMimeType(name);
		}

		@Override
		public Set<String> getResourcePaths(String path) {
			if ((path == null) || (bundle == null)) {
				return null;
			}

			final Enumeration<URL> enumeration = bundle.findEntries(
				path, null, false);

			if (enumeration == null) {
				return null;
			}

			final Set<String> result = new HashSet<String>();

			while (enumeration.hasMoreElements()) {
				result.add(enumeration.nextElement().getPath());
			}

			return result;
		}
	}
}

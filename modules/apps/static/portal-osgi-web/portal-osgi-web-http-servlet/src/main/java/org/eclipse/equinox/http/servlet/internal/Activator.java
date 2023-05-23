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

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import org.eclipse.equinox.http.servlet.ExtendedHttpService;
import org.eclipse.equinox.http.servlet.internal.servlet.ProxyServlet;
import org.eclipse.equinox.http.servlet.internal.util.HttpTuple;
import org.eclipse.equinox.http.servlet.internal.util.UMDictionaryMap;
import org.osgi.framework.*;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.runtime.HttpServiceRuntime;
import org.osgi.service.http.runtime.HttpServiceRuntimeConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator
	implements BundleActivator, ServiceTrackerCustomizer<HttpServlet, HttpTuple> {

	private static final String DEFAULT_SERVICE_DESCRIPTION = "Equinox Servlet Bridge"; //$NON-NLS-1$
	private static final String DEFAULT_SERVICE_VENDOR = "Eclipse.org"; //$NON-NLS-1$
	private static final String PROP_GLOBAL_WHITEBOARD = "equinox.http.global.whiteboard"; //$NON-NLS-1$
	public static final String UNIQUE_SERVICE_ID = "equinox.http.id"; //$NON-NLS-1$
	private static final String[] HTTP_SERVICES_CLASSES = new String[] {
		HttpService.class.getName(), ExtendedHttpService.class.getName()
	};

	private static volatile BundleContext context;
	private static ConcurrentMap<ProxyServlet, Object> registrations =
		new ConcurrentHashMap<ProxyServlet, Object>();

	private ServiceTracker<HttpServlet, HttpTuple> serviceTracker;

	public static void addProxyServlet(ProxyServlet proxyServlet) {
		Object previousRegistration = registrations.putIfAbsent(
			proxyServlet, proxyServlet);

		if (!(previousRegistration instanceof ServiceRegistration) &&
			(context != null)) {

			ServiceRegistration<HttpServlet> serviceRegistration =
				context.registerService(
					HttpServlet.class, proxyServlet,
					new Hashtable<String, Object>());

			registrations.put(proxyServlet, serviceRegistration);
		}
	}

	public static void unregisterHttpService(ProxyServlet proxyServlet) {
		Object registration = registrations.remove(proxyServlet);

		if (registration instanceof ServiceRegistration) {
			ServiceRegistration<?> serviceRegistration =
				(ServiceRegistration<?>)registration;

			serviceRegistration.unregister();
		}
	}

	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;

		processRegistrations();

		serviceTracker = new ServiceTracker<HttpServlet, HttpTuple>(
			context, HttpServlet.class, this);

		serviceTracker.open();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		serviceTracker.close();
		serviceTracker = null;
		context = null;
	}

	public HttpTuple addingService(
		ServiceReference<HttpServlet> serviceReference) {

		HttpServlet httpServlet = context.getService(serviceReference);

		if (!(httpServlet instanceof ProxyServlet)) {
			context.ungetService(serviceReference);
			return null;
		}

		ProxyServlet proxyServlet = (ProxyServlet)httpServlet;

		ServletConfig servletConfig = proxyServlet.getServletConfig();
		ServletContext servletContext = servletConfig.getServletContext();

		String[] httpServiceEndpoints = getHttpServiceEndpoints(
			servletContext, servletConfig.getServletName());

		Dictionary<String, Object> serviceProperties =
			new Hashtable<String, Object>(3);

		Enumeration<String> initparameterNames =
			servletConfig.getInitParameterNames();

		while (initparameterNames.hasMoreElements()) {
			String name = initparameterNames.nextElement();

			serviceProperties.put(
				name, servletConfig.getInitParameter(name));
		}

		if (serviceProperties.get(Constants.SERVICE_VENDOR) == null) {
			serviceProperties.put(
				Constants.SERVICE_VENDOR, DEFAULT_SERVICE_VENDOR);
		}

		if (serviceProperties.get(Constants.SERVICE_DESCRIPTION) == null) {
			serviceProperties.put(
				Constants.SERVICE_DESCRIPTION, DEFAULT_SERVICE_DESCRIPTION);
		}

		if (serviceProperties.get(
				HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT) == null) {

			serviceProperties.put(
				HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT,
				httpServiceEndpoints);
		}

		// need a unique id for our service to match old HttpService HttpContext
		serviceProperties.put(UNIQUE_SERVICE_ID, new Random().nextLong());
		// white board support
		// determine if the system bundle context should be used:
		boolean useSystemContext = Boolean.valueOf(context.getProperty(PROP_GLOBAL_WHITEBOARD));
		BundleContext trackingContext = useSystemContext ? context.getBundle(Constants.SYSTEM_BUNDLE_LOCATION).getBundleContext() : context;
		HttpServiceRuntimeImpl httpServiceRuntime = new HttpServiceRuntimeImpl(
			trackingContext, context, servletContext,
			new UMDictionaryMap<String, Object>(serviceProperties));

		proxyServlet.setHttpServiceRuntimeImpl(httpServiceRuntime);

		// imperative API support;
		// the http service must be registered first so we can get its service id
		HttpServiceFactory httpServiceFactory = new HttpServiceFactory(
			httpServiceRuntime);
		ServiceRegistration<?> hsfRegistration = context.registerService(
			HTTP_SERVICES_CLASSES, httpServiceFactory, serviceProperties);

		serviceProperties.put(HttpServiceRuntimeConstants.HTTP_SERVICE_ID, Collections.singletonList(hsfRegistration.getReference().getProperty(Constants.SERVICE_ID)));
		ServiceRegistration<HttpServiceRuntime> hsrRegistration =
			context.registerService(
				HttpServiceRuntime.class, httpServiceRuntime,
				serviceProperties);
		return new HttpTuple(
			proxyServlet, httpServiceFactory, hsfRegistration,
			httpServiceRuntime, hsrRegistration);
	}

	public void modifiedService(
		ServiceReference<HttpServlet> serviceReference, HttpTuple httpTuple) {

		removedService(serviceReference, httpTuple);
		addingService(serviceReference);
	}

	public void removedService(
		ServiceReference<HttpServlet> serviceReference, HttpTuple httpTuple) {

		context.ungetService(serviceReference);

		httpTuple.destroy();
	}

	private String[] getHttpServiceEndpoints(
		ServletContext servletContext, String servletName) {

		int majorVersion = servletContext.getMajorVersion();

		if (majorVersion < 3) {
			servletContext.log(
				"The http container does not support servlet 3.0+. " +
					"Therefore, the value of " +
						HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT +
							" cannot be calculated.");

			return new String[0];
		}

		String contextPath = servletContext.getContextPath();

		ServletRegistration servletRegistration = null;
		try {
			servletRegistration = servletContext.getServletRegistration(servletName);
		} catch (UnsupportedOperationException e) {
			servletContext.log("Could not find the servlet registration for the servlet: " + servletName, e); //$NON-NLS-1$
		}

		if (servletRegistration == null) {
			return new String[0];
		}

		Collection<String> mappings = servletRegistration.getMappings();

		List<String> httpServiceEndpoints = new ArrayList<String>();

		for (String mapping : mappings) {
			if (mapping.indexOf('/') == 0) {
				if (mapping.charAt(mapping.length() - 1) == '*') {
					mapping = mapping.substring(0, mapping.length() - 2);

					if ((mapping.length() > 1) &&
						(mapping.charAt(mapping.length() - 1) != '/')) {

						mapping += '/';
					}
				}

				httpServiceEndpoints.add(contextPath + mapping);
			}
		}

		return httpServiceEndpoints.toArray(
			new String[httpServiceEndpoints.size()]);
	}

	private void processRegistrations() {
		Iterator<Entry<ProxyServlet, Object>> iterator =
			registrations.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry<ProxyServlet, Object> entry = iterator.next();

			ProxyServlet proxyServlet = entry.getKey();
			Object value = entry.getValue();

			if (!(value instanceof ServiceRegistration)) {
				ServiceRegistration<HttpServlet> serviceRegistration =
					context.registerService(
						HttpServlet.class, proxyServlet,
						new Hashtable<String, Object>());

				entry.setValue(serviceRegistration);
			}
		}
	}

}
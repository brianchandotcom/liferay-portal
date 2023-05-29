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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.osgi.web.http.servlet.ExtendedHttpService;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.ProxyServlet;
import com.liferay.portal.osgi.web.http.servlet.internal.util.HttpTuple;
import com.liferay.portal.osgi.web.http.servlet.internal.util.UMDictionaryMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.runtime.HttpServiceRuntime;
import org.osgi.service.http.runtime.HttpServiceRuntimeConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Cognos Incorporated
 * @author IBM Corporation
 * @author Raymond Augé
 */
public class HttpServletBundleActivator
	implements BundleActivator,
			   ServiceTrackerCustomizer<HttpServlet, HttpTuple> {

	public static final String UNIQUE_SERVICE_ID = "equinox.http.id";

	public static void addProxyServlet(ProxyServlet proxyServlet) {
		Object previousRegistration = _registrationsMap.putIfAbsent(
			proxyServlet, proxyServlet);

		if (!(previousRegistration instanceof ServiceRegistration) &&
			(_bundleContext != null)) {

			_registrationsMap.put(
				proxyServlet,
				_bundleContext.registerService(
					HttpServlet.class, proxyServlet,
					new HashMapDictionary<>()));
		}
	}

	public static void unregisterHttpService(ProxyServlet proxyServlet) {
		Object registration = _registrationsMap.remove(proxyServlet);

		if (registration instanceof ServiceRegistration) {
			ServiceRegistration<?> serviceRegistration =
				(ServiceRegistration<?>)registration;

			serviceRegistration.unregister();
		}
	}

	@Override
	public HttpTuple addingService(
		ServiceReference<HttpServlet> serviceReference) {

		HttpServlet httpServlet = _bundleContext.getService(serviceReference);

		if (!(httpServlet instanceof ProxyServlet)) {
			_bundleContext.ungetService(serviceReference);

			return null;
		}

		ProxyServlet proxyServlet = (ProxyServlet)httpServlet;

		ServletConfig servletConfig = proxyServlet.getServletConfig();

		ServletContext servletContext = servletConfig.getServletContext();

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		Enumeration<String> initParameterNamesEnumeration =
			servletConfig.getInitParameterNames();

		while (initParameterNamesEnumeration.hasMoreElements()) {
			String name = initParameterNamesEnumeration.nextElement();

			dictionary.put(name, servletConfig.getInitParameter(name));
		}

		if (dictionary.get(Constants.SERVICE_VENDOR) == null) {
			dictionary.put(Constants.SERVICE_VENDOR, _DEFAULT_SERVICE_VENDOR);
		}

		if (dictionary.get(Constants.SERVICE_DESCRIPTION) == null) {
			dictionary.put(
				Constants.SERVICE_DESCRIPTION, _DEFAULT_SERVICE_DESCRIPTION);
		}

		Object httpServiceEndpointObject = dictionary.get(
			HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT);

		if (httpServiceEndpointObject == null) {
			dictionary.put(
				HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT,
				_getHttpServiceEndpoints(
					servletContext, servletConfig.getServletName()));
		}

		Random random = new Random();

		dictionary.put(UNIQUE_SERVICE_ID, random.nextLong());

		BundleContext trackingBundleContext = _bundleContext;

		if (Boolean.parseBoolean(
				_bundleContext.getProperty(_PROP_GLOBAL_WHITEBOARD))) {

			Bundle systemBundle = _bundleContext.getBundle(
				Constants.SYSTEM_BUNDLE_LOCATION);

			trackingBundleContext = systemBundle.getBundleContext();
		}

		HttpServiceRuntimeImpl httpServiceRuntimeImpl =
			new HttpServiceRuntimeImpl(
				trackingBundleContext, _bundleContext, servletContext,
				new UMDictionaryMap<>(dictionary));

		proxyServlet.setHttpServiceRuntimeImpl(httpServiceRuntimeImpl);

		HttpServiceFactory httpServiceFactory = new HttpServiceFactory(
			httpServiceRuntimeImpl);

		ServiceRegistration<?> httpServiceFactoryServiceRegistration =
			_bundleContext.registerService(
				_HTTP_SERVICES_CLASSES, httpServiceFactory, dictionary);

		ServiceReference<?> httpServiceFactoryServiceReference =
			httpServiceFactoryServiceRegistration.getReference();

		dictionary.put(
			HttpServiceRuntimeConstants.HTTP_SERVICE_ID,
			Collections.singletonList(
				httpServiceFactoryServiceReference.getProperty(
					Constants.SERVICE_ID)));

		ServiceRegistration<HttpServiceRuntime>
			httpServiceRuntimeServiceRegistration =
				_bundleContext.registerService(
					HttpServiceRuntime.class, httpServiceRuntimeImpl,
					dictionary);

		return new HttpTuple(
			proxyServlet, httpServiceFactory,
			httpServiceFactoryServiceRegistration, httpServiceRuntimeImpl,
			httpServiceRuntimeServiceRegistration);
	}

	@Override
	public void modifiedService(
		ServiceReference<HttpServlet> serviceReference, HttpTuple httpTuple) {

		removedService(serviceReference, httpTuple);

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<HttpServlet> serviceReference, HttpTuple httpTuple) {

		_bundleContext.ungetService(serviceReference);

		httpTuple.destroy();
	}

	@Override
	public void start(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_processRegistrations();

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, HttpServlet.class, this);

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_serviceTracker.close();

		_serviceTracker = null;
		_bundleContext = null;
	}

	private String[] _getHttpServiceEndpoints(
		ServletContext servletContext, String servletName) {

		int majorVersion = servletContext.getMajorVersion();

		if (majorVersion < 3) {
			_log.error(
				StringBundler.concat(
					"The http container does not support servlet 3.0+. ",
					"Therefore, the value of ",
					HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT,
					" cannot be calculated."));

			return new String[0];
		}

		ServletRegistration servletRegistration = null;

		try {
			servletRegistration = servletContext.getServletRegistration(
				servletName);
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			_log.error(
				"Could not find the servlet registration for the servlet: " +
					servletName,
				unsupportedOperationException);
		}

		if (servletRegistration == null) {
			return new String[0];
		}

		List<String> httpServiceEndpoints = new ArrayList<>();

		for (String mapping : servletRegistration.getMappings()) {
			if (mapping.indexOf('/') == 0) {
				if (mapping.charAt(mapping.length() - 1) == '*') {
					mapping = mapping.substring(0, mapping.length() - 2);

					if ((mapping.length() > 1) &&
						(mapping.charAt(mapping.length() - 1) != '/')) {

						mapping += '/';
					}
				}

				httpServiceEndpoints.add(
					servletContext.getContextPath() + mapping);
			}
		}

		return httpServiceEndpoints.toArray(new String[0]);
	}

	private void _processRegistrations() {
		for (Map.Entry<ProxyServlet, Object> entry :
				_registrationsMap.entrySet()) {

			Object value = entry.getValue();

			if (value instanceof ServiceRegistration) {
				continue;
			}

			ServiceRegistration<HttpServlet> serviceRegistration =
				_bundleContext.registerService(
					HttpServlet.class, entry.getKey(),
					new HashMapDictionary<>());

			entry.setValue(serviceRegistration);
		}
	}

	private static final String _DEFAULT_SERVICE_DESCRIPTION =
		"Equinox Servlet Bridge";

	private static final String _DEFAULT_SERVICE_VENDOR = "Eclipse.org";

	private static final String[] _HTTP_SERVICES_CLASSES = {
		HttpService.class.getName(), ExtendedHttpService.class.getName()
	};

	private static final String _PROP_GLOBAL_WHITEBOARD =
		"equinox.http.global.whiteboard";

	private static final Log _log = LogFactoryUtil.getLog(
		HttpServletBundleActivator.class.getName());

	private static volatile BundleContext _bundleContext;
	private static final ConcurrentMap<ProxyServlet, Object> _registrationsMap =
		new ConcurrentHashMap<>();

	private ServiceTracker<HttpServlet, HttpTuple> _serviceTracker;

}
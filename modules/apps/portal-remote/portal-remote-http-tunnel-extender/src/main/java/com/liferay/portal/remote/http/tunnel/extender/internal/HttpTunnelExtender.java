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

package com.liferay.portal.remote.http.tunnel.extender.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.remote.http.tunnel.extender.configuration.HttpTunnelExtenderConfiguration;
import com.liferay.portal.servlet.TunnelServlet;
import com.liferay.portal.servlet.filters.authverifier.AuthVerifierFilter;

import java.net.URL;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Miguel Pastor
 */
@Component(
	configurationPid = "com.liferay.portal.remote.http.tunnel.configuration.HttpTunnelExtenderConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = {}
)
public class HttpTunnelExtender
	implements BundleTrackerCustomizer
		<HttpTunnelExtender.TunnelServletExtension> {

	@Override
	public TunnelServletExtension addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (headers.get("Http-Tunnel") == null) {
			return null;
		}

		TunnelServletExtension tunnelServletExtension =
			new TunnelServletExtension(bundle);

		tunnelServletExtension.start();

		return tunnelServletExtension;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		TunnelServletExtension tunnelServletExtension) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		TunnelServletExtension tunnelServletExtension) {

		tunnelServletExtension.destroy();
	}

	public class TunnelServletExtension {

		public TunnelServletExtension(Bundle bundle) {
			_bundle = bundle;
		}

		public void destroy() {
			ServiceRegistration<Filter> authVerifierFilterServiceRegistration =
				_serviceRegistrations._authVerifierFilterServiceRegistration;

			authVerifierFilterServiceRegistration.unregister();

			ServiceRegistration<ServletContextHelper>
				servletContextHelperServiceRegistration =
					_serviceRegistrations.
						_servletContextHelperServiceRegistration;

			servletContextHelperServiceRegistration.unregister();

			ServiceRegistration<Servlet> tunnelServletServiceRegistration =
				_serviceRegistrations._tunnelServletServiceRegistration;

			tunnelServletServiceRegistration.unregister();
		}

		public void start() {
			BundleContext bundleContext = _bundle.getBundleContext();

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				"liferay.http.tunnel." + _bundle.getSymbolicName());
			properties.put(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_FILTER_INIT_PARAM_PREFIX +
						"auth.verifier.TunnelingServletAuthVerifier.hosts." +
							"allowed",
				StringUtil.merge(
					_httpTunnelExtenderConfiguration.hostsAllowed()));
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_NAME,
				AuthVerifierFilter.class.getName());
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN,
				"/api/liferay/do");

			ServiceRegistration<Filter> authVerifierFilterServiceRegistration =
				bundleContext.registerService(
					Filter.class, new AuthVerifierFilter(), properties);

			properties = new Hashtable<>();

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
				"liferay.http.tunnel." + _bundle.getSymbolicName());
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
				"/" + _bundle.getSymbolicName());

			ServiceRegistration<ServletContextHelper>
				servletContextHelperServiceRegistration =
					bundleContext.registerService(
						ServletContextHelper.class,
						new ServletContextHelper(_bundle) {

							@Override
							public URL getResource(String name) {
								if (name.startsWith("/")) {
									name = name.substring(1);
								}

								return _bundle.getResource(name);
							}

						},
						properties);

			properties = new Hashtable<>();

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				"liferay.http.tunnel." + _bundle.getSymbolicName());
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
				TunnelServlet.class.getName());
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
				"/api/liferay/do");
			properties.put("servlet.init.httpMethods", "POST");

			ServiceRegistration<Servlet> tunnelServletServiceRegistration =
				bundleContext.registerService(
					Servlet.class, new TunnelServlet(), properties);

			_serviceRegistrations = new ServiceRegistrations(
				authVerifierFilterServiceRegistration,
				servletContextHelperServiceRegistration,
				tunnelServletServiceRegistration);
		}

		private final Bundle _bundle;
		private ServiceRegistrations _serviceRegistrations;

	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_httpTunnelExtenderConfiguration = ConfigurableUtil.createConfigurable(
			HttpTunnelExtenderConfiguration.class, properties);

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE | Bundle.STARTING, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	@Modified
	protected void modified(
		BundleContext bundleContext, Map<String, Object> properties) {

		deactivate();

		activate(bundleContext, properties);
	}

	private BundleTracker<?> _bundleTracker;
	private HttpTunnelExtenderConfiguration _httpTunnelExtenderConfiguration;

	private final class ServiceRegistrations {

		public ServiceRegistrations(
			ServiceRegistration<Filter> authVerifierFilterServiceRegistration,
			ServiceRegistration<ServletContextHelper>
				servletContextHelperServiceRegistration,
			ServiceRegistration<Servlet> tunnelServletServiceRegistration) {

			_authVerifierFilterServiceRegistration =
				authVerifierFilterServiceRegistration;
			_servletContextHelperServiceRegistration =
				servletContextHelperServiceRegistration;
			_tunnelServletServiceRegistration =
				tunnelServletServiceRegistration;
		}

		private final ServiceRegistration<Filter>
			_authVerifierFilterServiceRegistration;
		private final ServiceRegistration<ServletContextHelper>
			_servletContextHelperServiceRegistration;
		private final ServiceRegistration<Servlet>
			_tunnelServletServiceRegistration;

	}

}
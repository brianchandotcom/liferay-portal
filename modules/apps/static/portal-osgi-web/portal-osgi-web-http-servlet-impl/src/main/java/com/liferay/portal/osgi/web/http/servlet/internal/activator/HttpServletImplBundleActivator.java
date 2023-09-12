/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.activator;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.servlet.PortletSessionListenerManager;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.osgi.web.http.servlet.HttpServletEndpoint;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.HttpServletEndpointServlet;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.eclipse.equinox.http.servlet.internal.Activator;
import org.eclipse.equinox.http.servlet.internal.HttpServiceFactory;
import org.eclipse.equinox.http.servlet.internal.HttpServiceRuntimeImpl;
import org.eclipse.equinox.http.servlet.internal.servlet.HttpSessionTracker;

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
 * @author Dante Wang
 */
public class HttpServletImplBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		PortletSessionListenerManager.addHttpSessionListener(
			_HTTP_SESSION_LISTENER);

		_serviceTracker = new ServiceTracker<>(
			bundleContext, HttpServletEndpoint.class,
			new HttpServletServiceServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_serviceTracker.close();

		PortletSessionListenerManager.removeHttpSessionListener(
			_HTTP_SESSION_LISTENER);
	}

	private static String[] _getHttpServiceEndpoints(
		ServletContext servletContext, String servletName) {

		ServletRegistration servletRegistration = null;

		try {
			servletRegistration = servletContext.getServletRegistration(
				servletName);
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			_log.error(
				"Unable to get the servlet registration for servlet name " +
					servletName,
				unsupportedOperationException);
		}

		if (servletRegistration == null) {
			return new String[0];
		}

		return TransformUtil.transformToArray(
			servletRegistration.getMappings(),
			mapping -> {
				if (mapping.indexOf('/') != 0) {
					return null;
				}

				if (mapping.charAt(mapping.length() - 1) == '*') {
					mapping = mapping.substring(0, mapping.length() - 2);

					if ((mapping.length() > 1) &&
						(mapping.charAt(mapping.length() - 1) != '/')) {

						mapping += '/';
					}
				}

				return servletContext.getContextPath() + mapping;
			},
			String.class);
	}

	private static final HttpSessionListener _HTTP_SESSION_LISTENER =
		new HttpSessionListener() {

			@Override
			public void sessionCreated(HttpSessionEvent httpSessionEvent) {
			}

			@Override
			public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
				HttpSession httpSession = httpSessionEvent.getSession();

				HttpSessionTracker.clearHttpSessionAdaptors(
					httpSession.getId());
			}

		};

	private static final Log _log = LogFactoryUtil.getLog(
		HttpServletImplBundleActivator.class.getName());

	private ServiceTracker<HttpServletEndpoint, ServiceRegistrationsBag>
		_serviceTracker;

	private static class HttpServletServiceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<HttpServletEndpoint, ServiceRegistrationsBag> {

		@Override
		public ServiceRegistrationsBag addingService(
			ServiceReference<HttpServletEndpoint> serviceReference) {

			HttpServletEndpoint httpServletEndpoint = _bundleContext.getService(
				serviceReference);

			ServletConfig servletConfig =
				httpServletEndpoint.getServletConfig();

			ServletContext servletContext = servletConfig.getServletContext();

			Map<String, Object> attributesMap =
				HashMapBuilder.<String, Object>put(
					Activator.UNIQUE_SERVICE_ID, SecureRandomUtil.nextLong()
				).put(
					ListUtil.fromEnumeration(
						servletConfig.getInitParameterNames()),
					servletConfig::getInitParameter
				).put(
					HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT,
					() -> {
						Object httpServiceEndpoint =
							servletConfig.getInitParameter(
								HttpServiceRuntimeConstants.
									HTTP_SERVICE_ENDPOINT);

						if (httpServiceEndpoint != null) {
							return null;
						}

						return _getHttpServiceEndpoints(
							servletContext, servletConfig.getServletName());
					}
				).build();

			HttpServiceRuntimeImpl httpServiceRuntimeImpl =
				new HttpServiceRuntimeImpl(
					_bundleContext, _bundleContext, servletContext,
					Collections.unmodifiableMap(attributesMap));

			return new ServiceRegistrationsBag(
				httpServiceRuntimeImpl,
				_bundleContext.registerService(
					HttpServlet.class,
					new HttpServletEndpointServlet(
						httpServiceRuntimeImpl, servletConfig),
					httpServletEndpoint.getProperties()),
				_bundleContext.registerService(
					HttpService.class,
					new HttpServiceFactory(httpServiceRuntimeImpl),
					HashMapDictionaryBuilder.putAll(
						attributesMap
					).build()),
				_bundleContext.registerService(
					HttpServiceRuntime.class, httpServiceRuntimeImpl,
					HashMapDictionaryBuilder.putAll(
						attributesMap
					).put(
						HttpServiceRuntimeConstants.HTTP_SERVICE_ID,
						() -> {
							Collection<ServiceReference<HttpService>>
								serviceReferences =
									_bundleContext.getServiceReferences(
										HttpService.class,
										StringBundler.concat(
											"(", Activator.UNIQUE_SERVICE_ID,
											"=",
											attributesMap.get(
												Activator.UNIQUE_SERVICE_ID),
											")"));

							Iterator<ServiceReference<HttpService>> iterator =
								serviceReferences.iterator();

							ServiceReference<?>
								httpServiceFactoryServiceReference =
									iterator.next();

							return Collections.singletonList(
								httpServiceFactoryServiceReference.getProperty(
									Constants.SERVICE_ID));
						}
					).build()));
		}

		@Override
		public void modifiedService(
			ServiceReference<HttpServletEndpoint> serviceReference,
			ServiceRegistrationsBag serviceRegistrationsBag) {
		}

		@Override
		public void removedService(
			ServiceReference<HttpServletEndpoint> serviceReference,
			ServiceRegistrationsBag serviceRegistrationsBag) {

			for (ServiceRegistration<?> serviceRegistration :
					serviceRegistrationsBag._serviceRegistrations) {

				serviceRegistration.unregister();
			}

			serviceRegistrationsBag._httpServiceRuntimeImpl.destroy();

			_bundleContext.ungetService(serviceReference);
		}

		private HttpServletServiceServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

	private static class ServiceRegistrationsBag {

		private ServiceRegistrationsBag(
			HttpServiceRuntimeImpl httpServiceRuntimeImpl,
			ServiceRegistration<?>... serviceRegistrations) {

			_httpServiceRuntimeImpl = httpServiceRuntimeImpl;
			_serviceRegistrations = serviceRegistrations;
		}

		private final HttpServiceRuntimeImpl _httpServiceRuntimeImpl;
		private final ServiceRegistration<?>[] _serviceRegistrations;

	}

}
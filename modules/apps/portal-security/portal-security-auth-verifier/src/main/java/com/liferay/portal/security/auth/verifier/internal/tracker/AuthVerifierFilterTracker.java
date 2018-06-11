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

package com.liferay.portal.security.auth.verifier.internal.tracker;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.osgi.util.StringPlus;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.servlet.filters.authverifier.AuthVerifierFilter;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Marta Medio
 */
@Component(
	immediate = true,
	property = {
		"default.registration.property=filter.init.auth.verifier.OAuth2RestAuthVerifier.urls.includes=*",
		"default.registration.property=filter.init.guest.allowed=false",
		"default.whiteboard.property=" + HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET + "=cxf-servlet",
		"servlet.context.helper.select.filter=(&(!(liferay.auth.verifier=false))(osgi.jaxrs.name=*))"
	}
)
public class AuthVerifierFilterTracker {

	public static final String AUTH_VERIFIER_PROPERTY_PREFIX = "auth.verifier.";

	public static final int AUTH_VERIFIER_PROPERTY_PREFIX_LENGTH =
		AUTH_VERIFIER_PROPERTY_PREFIX.length();

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_defaultRegistrationProperties = processDefaultProperties(
			StringPlus.asList(properties.get("default.registration.property")));

		_defaultWhiteboardProperties = processDefaultProperties(
			StringPlus.asList(properties.get("default.whiteboard.property")));

		String servletContextHelperSelectFilterString = MapUtil.getString(
			properties, "servlet.context.helper.select.filter");

		String filterString = StringBundler.concat(
			"(&" + servletContextHelperSelectFilterString + "(",
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, "=*)",
			"(objectClass=", ServletContextHelper.class.getName(), "))");

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, filterString,
			new ServletContextHelperServiceTrackerCustomizer());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	protected Dictionary<String, Object> processDefaultProperties(
		List<String> defaultProperties) {

		Dictionary<String, Object> registrationProperties =
			new HashMapDictionary<>();

		for (String defaultRegistrationProperty : defaultProperties) {
			int indexOf = defaultRegistrationProperty.indexOf(StringPool.EQUAL);

			if (indexOf == -1) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Invalid property string " +
							defaultRegistrationProperty);
				}

				continue;
			}

			String propertyKey = defaultRegistrationProperty.substring(
				0, indexOf);

			String propertyValue;

			if (indexOf < defaultRegistrationProperty.length()) {
				propertyValue = defaultRegistrationProperty.substring(
					indexOf + 1);
			}
			else {
				propertyValue = StringPool.BLANK;
			}

			registrationProperties.put(propertyKey, propertyValue);
		}

		return registrationProperties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AuthVerifierFilterTracker.class);

	private BundleContext _bundleContext;
	private Dictionary<String, Object> _defaultRegistrationProperties;
	private Dictionary<String, Object> _defaultWhiteboardProperties;
	private ServiceTracker<?, ?> _serviceTracker;

	private class ServletContextHelperServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ServletContextHelper, ServiceRegistration<?>> {

		@Override
		public ServiceRegistration<?> addingService(
			ServiceReference<ServletContextHelper> serviceReference) {

			return _bundleContext.registerService(
				Filter.class, new AuthVerifierFilter(),
				_buildProperties(serviceReference));
		}

		@Override
		public void modifiedService(
			ServiceReference<ServletContextHelper> serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			serviceRegistration.setProperties(
				_buildProperties(serviceReference));
		}

		@Override
		public void removedService(
			ServiceReference<ServletContextHelper> serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			serviceRegistration.unregister();
		}

		private Dictionary<String, ?> _buildProperties(
			ServiceReference<ServletContextHelper> serviceReference) {

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			Enumeration<String> registrationKeys =
				_defaultRegistrationProperties.keys();

			Enumeration<String> whiteboardKeys =
				_defaultWhiteboardProperties.keys();

			while (registrationKeys.hasMoreElements()) {
				String key = registrationKeys.nextElement();

				properties.put(key, _defaultRegistrationProperties.get(key));
			}

			while (whiteboardKeys.hasMoreElements()) {
				String key = whiteboardKeys.nextElement();

				properties.put(key, _defaultWhiteboardProperties.get(key));
			}

			for (String key : serviceReference.getPropertyKeys()) {
				if (key.startsWith(AUTH_VERIFIER_PROPERTY_PREFIX)) {
					properties.put(
						"filter.init." +
							key.substring(AUTH_VERIFIER_PROPERTY_PREFIX_LENGTH),
						serviceReference.getProperty(key));
				}

				if (key.startsWith("osgi.http.whiteboard")) {
					properties.put(key, serviceReference.getProperty(key));
				}
			}

			String contextName = GetterUtil.getString(
				serviceReference.getProperty(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME));

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				StringBundler.concat(
					"(", HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
					"=", contextName, ")"));

			return properties;
		}

	}

}
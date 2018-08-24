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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;
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

		_defaultRegistrationProperties = toDictionary(
			StringPlus.asList(properties.get("default.registration.property")));
		_defaultWhiteboardProperties = toDictionary(
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

	protected Dictionary<String, Object> toDictionary(
		List<String> propertiesList) {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		for (String property : propertiesList) {
			int index = property.indexOf(StringPool.EQUAL);

			if (index == -1) {
				if (_log.isWarnEnabled()) {
					_log.warn("Invalid property " + property);
				}

				continue;
			}

			String propertyKey = property.substring(0, index);

			String propertyValue = StringPool.BLANK;

			if (index < property.length()) {
				propertyValue = property.substring(index + 1);
			}

			dictionary.put(propertyKey, propertyValue);
		}

		return dictionary;
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

			Enumeration<String> enumeration =
				_defaultRegistrationProperties.keys();

			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement();

				properties.put(key, _defaultRegistrationProperties.get(key));
			}

			enumeration = _defaultWhiteboardProperties.keys();

			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement();

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
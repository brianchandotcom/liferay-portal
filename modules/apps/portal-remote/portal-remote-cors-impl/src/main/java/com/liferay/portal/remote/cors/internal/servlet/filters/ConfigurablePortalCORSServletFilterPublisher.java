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

package com.liferay.portal.remote.cors.internal.servlet.filters;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration;
import com.liferay.portal.remote.cors.internal.CORSSupport;

import java.util.Dictionary;
import java.util.Map;

import javax.servlet.Filter;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = {}
)
public class ConfigurablePortalCORSServletFilterPublisher {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		PortalCORSConfiguration portalCorsConfiguration =
			ConfigurableUtil.createConfigurable(
				PortalCORSConfiguration.class, properties);

		if (!portalCorsConfiguration.enabled()) {
			return;
		}

		Map<String, String> corsHeaders = CORSSupport.buildCorsHeaders(
			portalCorsConfiguration.headers());

		CORSServletFilter corsServletFilter = new CORSServletFilter();

		corsServletFilter.setCorsHeaders(corsHeaders);

		Dictionary<String, Object> filterProperties = new HashMapDictionary<>();

		filterProperties.put("before-filter", "Auto Login Filter");
		filterProperties.put("dispatcher", new String[] {"FORWARD", "REQUEST"});
		filterProperties.put("servlet-context-name", "");
		filterProperties.put(
			"servlet-filter-name",
			"CORS Servlet Filter for " + portalCorsConfiguration.name());
		filterProperties.put(
			"url-pattern", portalCorsConfiguration.filterMappingURLPatterns());

		_serviceRegistration = bundleContext.registerService(
			Filter.class, corsServletFilter, filterProperties);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		_serviceRegistration = null;
	}

	private ServiceRegistration<Filter> _serviceRegistration;

}
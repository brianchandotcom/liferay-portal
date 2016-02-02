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

package com.liferay.portal.init.servlet.filter;

import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import javax.servlet.Filter;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true)
public class InitFilterTracker {

	@Activate
	protected void activate(BundleContext bundleContext) {
		InitFilter initFilter = new InitFilter();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("dispatcher", new String[] {"FORWARD", "REQUEST"});
		properties.put("servlet-context-name", "");
		properties.put("servlet-filter-name", "Init Filter");
		properties.put("url-pattern", "/c/*");

		_serviceRegistration = bundleContext.registerService(
			Filter.class, initFilter, properties);

		initFilter.setServiceRegistration(_serviceRegistration);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	private ServiceRegistration<Filter> _serviceRegistration;

}
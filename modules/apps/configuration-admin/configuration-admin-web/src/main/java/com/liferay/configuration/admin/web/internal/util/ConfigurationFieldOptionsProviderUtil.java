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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class ConfigurationFieldOptionsProviderUtil {

	public static ConfigurationFieldOptionsProvider
		getConfigurationFieldOptionsProvider(
			String configurationPid, String fieldName) {

		if (_serviceTrackerMap == null) {
			return null;
		}

		return _serviceTrackerMap.getService(
			_getKey(configurationPid, fieldName));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			(ServiceTrackerMap<String, ConfigurationFieldOptionsProvider>)
				(ServiceTrackerMap)ServiceTrackerMapFactory.openSingleValueMap(
					bundleContext, ConfigurationFieldOptionsProvider.class,
					null,
					(serviceReference, emitter) -> emitter.emit(
						_getKey(
							(String)serviceReference.getProperty(
								"configuration.pid"),
							(String)serviceReference.getProperty(
								"configuration.field.name"))));
	}

	@Deactivate
	protected synchronized void deactivate() {
		_serviceTrackerMap.close();
	}

	private static String _getKey(String configurationPid, String fieldName) {
		return StringBundler.concat(
			configurationPid, StringPool.POUND, fieldName);
	}

	private static volatile ServiceTrackerMap
		<String, ConfigurationFieldOptionsProvider> _serviceTrackerMap;

}
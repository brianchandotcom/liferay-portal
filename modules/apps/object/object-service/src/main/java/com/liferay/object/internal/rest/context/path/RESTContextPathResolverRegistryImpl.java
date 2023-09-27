/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.rest.context.path;

import com.liferay.object.rest.context.path.RESTContextPathResolver;
import com.liferay.object.rest.context.path.RESTContextPathResolverRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(service = RESTContextPathResolverRegistry.class)
public class RESTContextPathResolverRegistryImpl
	implements RESTContextPathResolverRegistry {

	@Override
	public RESTContextPathResolver getRESTContextPathResolver(
		String className) {

		RESTContextPathResolver restContextPathResolver =
			_serviceTrackerMap.getService(className);

		if (restContextPathResolver == null) {
			throw new IllegalArgumentException(
				"No REST context path resolver found with class name " +
					className);
		}

		return restContextPathResolver;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, RESTContextPathResolver.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, RESTContextPathResolver>
		_serviceTrackerMap;

}
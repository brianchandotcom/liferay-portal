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

package com.liferay.frontend.taglib.clay.attribute.provider.internal;

import com.liferay.frontend.taglib.clay.attribute.provider.ClayComponentAttributeProvider;
import com.liferay.frontend.taglib.clay.attribute.provider.ClayComponentAttributeProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	immediate = true, service = ClayComponentAttributeProviderRegistry.class
)
public class ClayComponentAttributeProviderRegistryImpl
	implements ClayComponentAttributeProviderRegistry {

	@Override
	public ClayComponentAttributeProvider get(String key) {
		ServiceWrapper<ClayComponentAttributeProvider> service =
			_serviceTrackerMap.getService(key);

		if (service == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No ClayComponentAttributeProvider registered with key " +
						key);
			}

			return null;
		}

		return service.getService();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ClayComponentAttributeProvider.class,
			"clay.component.attribute.provider.key",
			ServiceTrackerCustomizerFactory.serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClayComponentAttributeProviderRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<ClayComponentAttributeProvider>>
		_serviceTrackerMap;

}
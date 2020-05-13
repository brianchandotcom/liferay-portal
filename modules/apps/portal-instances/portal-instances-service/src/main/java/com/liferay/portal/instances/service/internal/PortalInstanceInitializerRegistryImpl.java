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

package com.liferay.portal.instances.service.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.instances.initalizer.PortalInstanceInitializer;
import com.liferay.portal.instances.initalizer.PortalInstanceInitializerRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Ivica Cardic
 */
@Component(immediate = true, service = PortalInstanceInitializerRegistry.class)
public class PortalInstanceInitializerRegistryImpl
	implements PortalInstanceInitializerRegistry {

	@Override
	public PortalInstanceInitializer getPortalInstanceInitializer(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<PortalInstanceInitializer> serviceWrapper =
			_serviceTrackerMap.getService(key);

		if (serviceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No portal instance initializer registered with key " +
						key);
			}

			return null;
		}

		return serviceWrapper.getService();
	}

	@Override
	public List<PortalInstanceInitializer> getPortalInstanceInitializers() {
		return getPortalInstanceInitializers(false);
	}

	@Override
	public List<PortalInstanceInitializer> getPortalInstanceInitializers(
		boolean active) {

		List<PortalInstanceInitializer> portalInstanceInitializers =
			new ArrayList<>();

		List<ServiceWrapper<PortalInstanceInitializer>> serviceWrappers =
			ListUtil.fromCollection(_serviceTrackerMap.values());

		for (ServiceWrapper<PortalInstanceInitializer> serviceWrapper :
				serviceWrappers) {

			PortalInstanceInitializer portalInstanceInitializer =
				serviceWrapper.getService();

			if (!active || (active && portalInstanceInitializer.isActive())) {
				portalInstanceInitializers.add(portalInstanceInitializer);
			}
		}

		return Collections.unmodifiableList(portalInstanceInitializers);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PortalInstanceInitializer.class,
			"portal.instance.initializer.key",
			ServiceTrackerCustomizerFactory.
				<PortalInstanceInitializer>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstanceInitializerRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<PortalInstanceInitializer>>
		_serviceTrackerMap;

}
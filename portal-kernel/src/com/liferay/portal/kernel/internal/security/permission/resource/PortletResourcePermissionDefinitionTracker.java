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

package com.liferay.portal.kernel.internal.security.permission.resource;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.definition.PortletResourcePermissionDefinition;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
public class PortletResourcePermissionDefinitionTracker {

	public void afterPropertiesSet() {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext, PortletResourcePermissionDefinition.class,
			new PortletResourcePermissionDefinitionServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void destroy() {
		_serviceTracker.close();
	}

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private ServiceTracker
		<PortletResourcePermissionDefinition, ServiceRegistration<?>>
			_serviceTracker;

	private class PortletResourcePermissionDefinitionServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<PortletResourcePermissionDefinition, ServiceRegistration<?>> {

		@Override
		public ServiceRegistration<?> addingService(
			ServiceReference<PortletResourcePermissionDefinition>
				serviceReference) {

			PortletResourcePermissionDefinition
				portletResourcePermissionDefinition = _bundleContext.getService(
					serviceReference);

			PortletResourcePermission portletResourcePermission =
				new DefaultPortletResourcePermission(
					portletResourcePermissionDefinition.getResourceName(),
					portletResourcePermissionDefinition.
						getPortletResourcePermissionLogics());

			return _bundleContext.registerService(
				PortletResourcePermission.class, portletResourcePermission,
				HashMapDictionaryBuilder.<String, Object>put(
					"resource.name",
					portletResourcePermissionDefinition.getResourceName()
				).put(
					"service.ranking",
					() -> serviceReference.getProperty("service.ranking")
				).build());
		}

		@Override
		public void modifiedService(
			ServiceReference<PortletResourcePermissionDefinition>
				serviceReference,
			ServiceRegistration<?> serviceRegistration) {
		}

		@Override
		public void removedService(
			ServiceReference<PortletResourcePermissionDefinition>
				serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			serviceRegistration.unregister();

			_bundleContext.ungetService(serviceReference);
		}

	}

}
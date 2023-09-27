/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;

import java.io.Closeable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Raymond Augé
 */
public class ResourceBundleTracker implements Closeable {

	public ResourceBundleTracker(String portletId) {
		_portletId = portletId;

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=" + _portletId + ")(objectClass=" +
				ResourceBundle.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new ResourceBundleServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void clear() {
		Set<Entry<String, ServiceRegistration<ResourceBundle>>> set =
			_serviceRegistrations.entrySet();

		Iterator<Entry<String, ServiceRegistration<ResourceBundle>>> iterator =
			set.iterator();

		while (iterator.hasNext()) {
			Entry<String, ServiceRegistration<ResourceBundle>> entry =
				iterator.next();

			ServiceRegistration<ResourceBundle> serviceRegistration =
				entry.getValue();

			serviceRegistration.unregister();

			iterator.remove();
		}
	}

	@Override
	public void close() {
		clear();

		_serviceTracker.close();
	}

	public ResourceBundle getResouceBundle(String languageId) {
		if (languageId == null) {
			languageId = StringPool.BLANK;
		}

		return _resourceBundles.get(languageId);
	}

	public void register(String languageId, ResourceBundle resourceBundle) {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("javax.portlet.name", _portletId);
		properties.put("language.id", languageId);

		ServiceRegistration<ResourceBundle> serviceRegistration =
			registry.registerService(
				ResourceBundle.class, resourceBundle, properties);

		_serviceRegistrations.put(languageId, serviceRegistration);
	}

	public void unregister(String languageId) {
		ServiceRegistration<ResourceBundle> serviceRegistration =
			_serviceRegistrations.remove(languageId);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private final String _portletId;
	private final Map<String, ResourceBundle> _resourceBundles =
		new ConcurrentHashMap<String, ResourceBundle>();
	private final StringServiceRegistrationMap<ResourceBundle>
		_serviceRegistrations =
			new StringServiceRegistrationMap<ResourceBundle>();
	private final ServiceTracker<ResourceBundle, ResourceBundle>
		_serviceTracker;

	private class ResourceBundleServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<ResourceBundle, ResourceBundle> {

		@Override
		public ResourceBundle addingService(
			ServiceReference<ResourceBundle> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ResourceBundle resourceBundle = registry.getService(
				serviceReference);

			String languageId = GetterUtil.getString(
				(String)serviceReference.getProperty("language.id"));

			_resourceBundles.put(languageId, resourceBundle);

			return resourceBundle;
		}

		@Override
		public void modifiedService(
			ServiceReference<ResourceBundle> serviceReference,
			ResourceBundle resourceBundle) {
		}

		@Override
		public void removedService(
			ServiceReference<ResourceBundle> serviceReference,
			ResourceBundle resourceBundle) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String languageId = GetterUtil.getString(
				(String)serviceReference.getProperty("language.id"));

			_resourceBundles.remove(languageId);
		}

	}

}
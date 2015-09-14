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

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;
import com.liferay.registry.collections.StringServiceRegistrationMapImpl;

import java.io.Closeable;

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

	public ResourceBundleTracker(ClassLoader classLoader, Portlet portlet) {
		_classLoader = classLoader;
		_portlet = portlet;

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=" + portlet.getPortletId() +
				")(language.id=*)(objectClass=" +
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

	public ResourceBundle getResourceBundle(String languageId) {
		if (languageId == null) {
			languageId = StringPool.BLANK;
		}

		ResourceBundle resourceBundle = _resourceBundles.get(languageId);

		if (resourceBundle != null) {
			return resourceBundle;
		}

		return ResourceBundleUtil.getBundle(
			_portlet.getResourceBundle(), LocaleUtil.fromLanguageId(languageId),
			_classLoader);
	}

	private final ClassLoader _classLoader;
	private final Portlet _portlet;
	private final Map<String, ResourceBundle> _resourceBundles =
		new ConcurrentHashMap<>();
	private final StringServiceRegistrationMap<ResourceBundle>
		_serviceRegistrations = new StringServiceRegistrationMapImpl<>();
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

			_resourceBundles.put(
				(String)serviceReference.getProperty("language.id"),
				resourceBundle);

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

			_resourceBundles.remove(
				serviceReference.getProperty("language.id"));
		}

	}

}
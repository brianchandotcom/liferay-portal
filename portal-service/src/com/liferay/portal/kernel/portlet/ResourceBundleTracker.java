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

import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Raymond Augé
 * @author Tomas Polesovsky
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

		ResourceBundleWrapper resourceBundleWrapper =
			_resourceBundleWrappers.get(languageId);

		if (resourceBundleWrapper == null) {
			resourceBundleWrapper = new ResourceBundleWrapper(languageId, null);

			ResourceBundleWrapper previousResourceBundleWrapper =
				_resourceBundleWrappers.putIfAbsent(
					languageId, resourceBundleWrapper);

			if (previousResourceBundleWrapper != null) {
				resourceBundleWrapper = previousResourceBundleWrapper;
			}
		}

		return resourceBundleWrapper;
	}

	private final ClassLoader _classLoader;
	private final Portlet _portlet;
	private final ConcurrentMap<String, ResourceBundleWrapper>
		_resourceBundleWrappers = new ConcurrentHashMap<>();
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

			String languageId = (String)serviceReference.getProperty(
				"language.id");

			ResourceBundleWrapper resourceBundleWrapper =
				_resourceBundleWrappers.get(languageId);

			if (resourceBundleWrapper == null) {
				resourceBundleWrapper = new ResourceBundleWrapper(
					languageId, resourceBundle);

				ResourceBundleWrapper previousResourceBundleWrapper =
					_resourceBundleWrappers.putIfAbsent(
						languageId, resourceBundleWrapper);

				if (previousResourceBundleWrapper == null) {
					return resourceBundle;
				}
				else {
					resourceBundleWrapper = previousResourceBundleWrapper;
				}
			}

			resourceBundleWrapper._addResourceBundle(resourceBundle);

			return resourceBundle;
		}

		@Override
		public void modifiedService(
			ServiceReference<ResourceBundle> serviceReference,
			ResourceBundle resourceBundle) {

			removedService(serviceReference, resourceBundle);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<ResourceBundle> serviceReference,
			ResourceBundle resourceBundle) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String languageId = (String)serviceReference.getProperty(
				"language.id");

			ResourceBundleWrapper resourceBundleWrapper =
				_resourceBundleWrappers.get(languageId);

			if (resourceBundleWrapper != null) {
				resourceBundleWrapper._removeResourceBundle(resourceBundle);
			}
		}

	}

	private class ResourceBundleWrapper extends ResourceBundle {

		@Override
		public Enumeration<String> getKeys() {
			ResourceBundle resourceBundle = _resourceBundle;

			if (resourceBundle == null) {
				return Collections.emptyEnumeration();
			}

			return resourceBundle.getKeys();
		}

		@Override
		protected Object handleGetObject(String key) {
			ResourceBundle resourceBundle = _resourceBundle;

			if ((resourceBundle != null) && resourceBundle.containsKey(key)) {
				return resourceBundle.getObject(key);
			}

			return null;
		}

		@Override
		protected Set<String> handleKeySet() {
			Set<String> keySet = _keySet;

			if (keySet == null) {
				ResourceBundle resourceBundle = _resourceBundle;

				if (resourceBundle == null) {
					keySet = Collections.emptySet();
				}
				else {
					keySet = new HashSet<>();

					Enumeration<String> enumeration = resourceBundle.getKeys();

					while (enumeration.hasMoreElements()) {
						String key = enumeration.nextElement();

						if (resourceBundle.containsKey(key)) {
							keySet.add(key);
						}
					}
				}

				_keySet = keySet;
			}

			return keySet;
		}

		private ResourceBundleWrapper(
			String languageId, ResourceBundle resourceBundle) {

			_resourceBundle = resourceBundle;

			if (resourceBundle != null) {
				_resourceBundles.add(resourceBundle);
			}

			if (languageId.isEmpty()) {
				setParent(
					ResourceBundleUtil.getBundle(
						_portlet.getResourceBundle(),
						LocaleUtil.fromLanguageId(languageId), _classLoader));
			}
			else {
				String parentLanguageId = StringPool.BLANK;

				int index = languageId.lastIndexOf(CharPool.UNDERLINE);

				if (index > 0) {
					parentLanguageId = languageId.substring(0, index);
				}

				setParent(getResourceBundle(parentLanguageId));
			}
		}

		private void _addResourceBundle(ResourceBundle resourceBundle) {
			_resourceBundles.add(resourceBundle);

			_update();
		}

		private void _removeResourceBundle(ResourceBundle resourceBundle) {
			if (_resourceBundles.remove(resourceBundle)) {
				_update();
			}
		}

		private void _update() {
			ResourceBundle[] resourceBundles = _resourceBundles.toArray(
				new ResourceBundle[_resourceBundles.size()]);

			if (resourceBundles.length == 0) {
				_resourceBundle = null;
			}
			else if (resourceBundles.length == 1) {
				_resourceBundle = resourceBundles[0];
			}
			else {
				ArrayUtil.reverse(resourceBundles);

				_resourceBundle = new AggregateResourceBundle(resourceBundles);
			}

			_keySet = null;
		}

		private volatile Set<String> _keySet;
		private volatile ResourceBundle _resourceBundle;
		private final List<ResourceBundle> _resourceBundles =
			new CopyOnWriteArrayList<>();

	}

}
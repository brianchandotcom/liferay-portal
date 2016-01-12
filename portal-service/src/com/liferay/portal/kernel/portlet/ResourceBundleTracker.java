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
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil.ResourceBundleLoader;
import com.liferay.registry.collections.ServiceReferenceMapperFactory;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.ServiceTrackerMapListener;

import java.io.Closeable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Raymond Augé
 * @author Tomas Polesovsky
 */
public class ResourceBundleTracker implements Closeable {

	public ResourceBundleTracker(String portletId) {
		_serviceTrackerMap = ServiceTrackerCollections.openMultiValueMap(
			ResourceBundle.class,
			"(&(javax.portlet.name=" + portletId + ")(language.id=*))",
			ServiceReferenceMapperFactory.<String, Object>create("language.id"),
			new ResourceBundleTrackerServiceTrackerMapListener(
				_resourceBundles));
	}

	@Override
	public void close() {
		_serviceTrackerMap.close();
	}

	public ResourceBundle getResourceBundle(String languageId) {
		ResourceBundle resourceBundle = _resourceBundles.get(languageId);

		if (resourceBundle != null) {
			return resourceBundle;
		}

		synchronized (_resourceBundles) {
			resourceBundle = _resourceBundles.get(languageId);

			if (resourceBundle == null) {
				ResourceBundleUtil.loadResourceBundles(
					_resourceBundles, languageId,
					new ResourceBundleLoader() {

						@Override
						public ResourceBundle loadResourceBundle(
							String languageId) {

							List<ResourceBundle> resourceBundles =
								_serviceTrackerMap.getService(languageId);

							if ((resourceBundles == null) ||
								resourceBundles.isEmpty()) {

								return null;
							}

							int size = resourceBundles.size();

							if (size == 1) {
								return resourceBundles.get(0);
							}

							return new AggregateResourceBundle(
								resourceBundles.toArray(
									new ResourceBundle[size]));
						}

					});

				resourceBundle = _resourceBundles.get(languageId);
			}
		}

		return resourceBundle;
	}

	private final Map<String, ResourceBundle> _resourceBundles =
		new ConcurrentHashMap<>();
	private final ServiceTrackerMap<String, List<ResourceBundle>>
		_serviceTrackerMap;

	private static class ResourceBundleTrackerServiceTrackerMapListener
		implements ServiceTrackerMapListener
			<String, ResourceBundle, List<ResourceBundle>> {

		public ResourceBundleTrackerServiceTrackerMapListener(
			Map<String, ResourceBundle> resourceBundles) {

			_resourceBundles = resourceBundles;
		}

		@Override
		public void keyEmitted(
			ServiceTrackerMap<String, List<ResourceBundle>>
				serviceTrackerMap,
			String languageId, ResourceBundle resourceBundle,
			List<ResourceBundle> content) {
		}

		@Override
		public void keyRemoved(
			ServiceTrackerMap<String, List<ResourceBundle>>
				serviceTrackerMap,
			String languageId, ResourceBundle service,
			List<ResourceBundle> content) {

			synchronized (_resourceBundles) {
				Set<String> keySet = _resourceBundles.keySet();
				Iterator<String> iterator = keySet.iterator();

				while (iterator.hasNext()) {
					String subLanguageId = iterator.next();

					if (subLanguageId.startsWith(languageId)) {
						iterator.remove();
					}
				}
			}
		}

		private final Map<String, ResourceBundle> _resourceBundles;

	}

}
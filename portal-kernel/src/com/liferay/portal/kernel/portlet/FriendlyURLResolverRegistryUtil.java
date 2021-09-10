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

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Eduardo García
 * @author Raymond Augé
 */
public class FriendlyURLResolverRegistryUtil {

	public static FriendlyURLResolver getFriendlyURLResolver(
		String urlSeparator) {

		for (String key : _serviceTrackerMap.keySet()) {
			FriendlyURLResolver friendlyURLResolver =
				_serviceTrackerMap.getService(key);

			if ((friendlyURLResolver != null) &&
				Objects.equals(
					friendlyURLResolver.getURLSeparator(), urlSeparator)) {

				return friendlyURLResolver;
			}
		}

		return null;
	}

	public static Collection<FriendlyURLResolver>
		getFriendlyURLResolversAsCollection() {

		List<FriendlyURLResolver> friendlyURLResolvers = new ArrayList<>();

		for (String key : _serviceTrackerMap.keySet()) {
			FriendlyURLResolver friendlyURLResolver =
				_serviceTrackerMap.getService(key);

			if (friendlyURLResolver != null) {
				friendlyURLResolvers.add(friendlyURLResolver);
			}
		}

		return friendlyURLResolvers;
	}

	public static String[] getURLSeparators() {
		List<String> urlSeparators = new ArrayList<>();

		for (String key : _serviceTrackerMap.keySet()) {
			FriendlyURLResolver friendlyURLResolver =
				_serviceTrackerMap.getService(key);

			if (friendlyURLResolver != null) {
				urlSeparators.add(friendlyURLResolver.getURLSeparator());
			}
		}

		return urlSeparators.toArray(new String[0]);
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static final ServiceTrackerMap<String, FriendlyURLResolver>
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, FriendlyURLResolver.class, null,
			new ServiceReferenceMapper<String, FriendlyURLResolver>() {

				@Override
				public void map(
					ServiceReference<FriendlyURLResolver> serviceReference,
					ServiceReferenceMapper.Emitter<String> emitter) {

					FriendlyURLResolver friendlyURLResolver =
						_bundleContext.getService(serviceReference);

					Class<?> friendlyURLResolverClass =
						friendlyURLResolver.getClass();

					emitter.emit(friendlyURLResolverClass.getName());

					_bundleContext.ungetService(serviceReference);
				}

			});

}
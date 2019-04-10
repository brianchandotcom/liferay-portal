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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Eduardo García
 * @author Raymond Augé
 */
@ProviderType
public class FriendlyURLResolverRegistryUtil {

	public static FriendlyURLResolver getFriendlyURLResolver(
		String urlSeparator) {

		return _instance._getFriendlyURLResolver(urlSeparator);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getFriendlyURLResolversAsCollection()}
	 */
	@Deprecated
	public static List<FriendlyURLResolver> getFriendlyURLResolvers() {
		return new ArrayList<>(getFriendlyURLResolversAsCollection());
	}

	public static Collection<FriendlyURLResolver>
		getFriendlyURLResolversAsCollection() {

		return _instance._getFriendlyURLResolversAsCollection();
	}

	public static String[] getURLSeparators() {
		return _instance._getURLSeparators();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static void register(FriendlyURLResolver friendlyURLResolver) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<FriendlyURLResolver> serviceRegistration =
			registry.registerService(
				FriendlyURLResolver.class, friendlyURLResolver);

		_serviceRegistrations.put(friendlyURLResolver, serviceRegistration);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static void unregister(FriendlyURLResolver friendlyURLResolver) {
		ServiceRegistration<FriendlyURLResolver> serviceRegistration =
			_serviceRegistrations.remove(friendlyURLResolver);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private FriendlyURLResolverRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			FriendlyURLResolver.class,
			new FriendlyURLResolverServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private FriendlyURLResolver _getFriendlyURLResolver(String urlSeparator) {
		return _friendlyURLResolvers.get(urlSeparator);
	}

	private Collection<FriendlyURLResolver>
		_getFriendlyURLResolversAsCollection() {

		return _friendlyURLResolvers.values();
	}

	private String[] _getURLSeparators() {
		Set<String> urlSeparators = _friendlyURLResolvers.keySet();

		return urlSeparators.toArray(new String[urlSeparators.size()]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLResolverRegistryUtil.class);

	private static final FriendlyURLResolverRegistryUtil _instance =
		new FriendlyURLResolverRegistryUtil();

	private static final ServiceRegistrationMap<FriendlyURLResolver>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();

	private final Map<String, FriendlyURLResolver> _friendlyURLResolvers =
		new ConcurrentHashMap<>();
	private final ServiceTracker<FriendlyURLResolver, FriendlyURLResolver>
		_serviceTracker;

	private class FriendlyURLResolverServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<FriendlyURLResolver, FriendlyURLResolver> {

		@Override
		public FriendlyURLResolver addingService(
			ServiceReference<FriendlyURLResolver> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			FriendlyURLResolver friendlyURLResolver = registry.getService(
				serviceReference);

			if (_friendlyURLResolvers.containsKey(
					friendlyURLResolver.getURLSeparator())) {

				_log.error(
					StringBundler.concat(
						"Unable to register friendly url resolver because ",
						"there is another friendly url resolver with the same ",
						"url separator ",
						friendlyURLResolver.getURLSeparator()));

				return null;
			}

			_friendlyURLResolvers.put(
				friendlyURLResolver.getURLSeparator(), friendlyURLResolver);

			return friendlyURLResolver;
		}

		@Override
		public void modifiedService(
			ServiceReference<FriendlyURLResolver> serviceReference,
			FriendlyURLResolver friendlyURLResolver) {
		}

		@Override
		public void removedService(
			ServiceReference<FriendlyURLResolver> serviceReference,
			FriendlyURLResolver friendlyURLResolver) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_friendlyURLResolvers.remove(friendlyURLResolver.getURLSeparator());
		}

	}

}
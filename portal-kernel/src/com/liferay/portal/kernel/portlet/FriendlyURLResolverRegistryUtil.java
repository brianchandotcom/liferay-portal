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

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Eduardo Garcia
 */
@ProviderType
public class FriendlyURLResolverRegistryUtil {

	public static FriendlyURLResolver getFriendlyURLResolver(
		String urlSeparator) {

		return _instance._getFriendlyURLResolver(urlSeparator);
	}

	public static List<FriendlyURLResolver> getFriendlyURLResolvers() {
		return _instance._getFriendlyURLResolvers();
	}

	public static String[] getURLSeparators() {
		return _instance._getURLSeparators();
	}

	public static void register(FriendlyURLResolver friendlyURLResolver) {
		_instance._register(friendlyURLResolver);
	}

	public static void unregister(FriendlyURLResolver friendlyURLResolver) {
		_instance._unregister(friendlyURLResolver);
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

	private List<FriendlyURLResolver> _getFriendlyURLResolvers() {
		return ListUtil.fromMapValues(_friendlyURLResolvers);
	}

	private String[] _getURLSeparators() {
		Set<String> urlSeparators = _friendlyURLResolvers.keySet();

		return urlSeparators.toArray(new String[urlSeparators.size()]);
	}

	private void _register(FriendlyURLResolver friendlyURLResolver) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<FriendlyURLResolver> serviceRegistration =
			registry.registerService(
				FriendlyURLResolver.class, friendlyURLResolver);

		_serviceRegistrations.put(friendlyURLResolver, serviceRegistration);
	}

	private void _unregister(FriendlyURLResolver friendlyURLResolver) {
		ServiceRegistration<FriendlyURLResolver> serviceRegistration =
			_serviceRegistrations.remove(friendlyURLResolver);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final FriendlyURLResolverRegistryUtil _instance =
		new FriendlyURLResolverRegistryUtil();

	private final Map<String, FriendlyURLResolver> _friendlyURLResolvers =
		new ConcurrentHashMap<>();
	private final ServiceRegistrationMap<FriendlyURLResolver>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
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
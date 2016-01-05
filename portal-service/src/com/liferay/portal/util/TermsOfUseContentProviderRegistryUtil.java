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

package com.liferay.portal.util;

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
public class TermsOfUseContentProviderRegistryUtil {

	public static String[] getClassNames() {
		return _instance._getClassNames();
	}

	public static TermsOfUseContentProvider getTermsOfUseContentProvider() {
		return _instance._getTermsOfUseContentProvider();
	}

	public static TermsOfUseContentProvider getTermsOfUseContentProvider(
		String className) {

		return _instance._getTermsOfUseContentProvider(className);
	}

	public static List<TermsOfUseContentProvider>
		getTermsOfUseContentProviders() {

		return _instance._getTermsOfUseContentProviders();
	}

	public static void register(
		TermsOfUseContentProvider termsOfUseContentProvider) {

		_instance._register(termsOfUseContentProvider);
	}

	public static void unregister(
		TermsOfUseContentProvider termsOfUseContentProvider) {

		_instance._unregister(termsOfUseContentProvider);
	}

	private TermsOfUseContentProviderRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			TermsOfUseContentProvider.class,
			new TermsOfUseContentProviderServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private String[] _getClassNames() {
		Set<String> classNames = _termsOfUseContentProviders.keySet();

		return classNames.toArray(new String[classNames.size()]);
	}

	private TermsOfUseContentProvider _getTermsOfUseContentProvider() {
		List<TermsOfUseContentProvider> termsOfUseContentProviders =
			_getTermsOfUseContentProviders();

		if (termsOfUseContentProviders.isEmpty()) {
			return null;
		}

		return termsOfUseContentProviders.get(0);
	}

	private TermsOfUseContentProvider _getTermsOfUseContentProvider(
		String className) {

		return _termsOfUseContentProviders.get(className);
	}

	private List<TermsOfUseContentProvider> _getTermsOfUseContentProviders() {
		return ListUtil.fromMapValues(_termsOfUseContentProviders);
	}

	private void _register(
		TermsOfUseContentProvider termsOfUseContentProvider) {

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<TermsOfUseContentProvider> serviceRegistration =
			registry.registerService(
				TermsOfUseContentProvider.class, termsOfUseContentProvider);

		_serviceRegistrations.put(
			termsOfUseContentProvider, serviceRegistration);
	}

	private void _unregister(
		TermsOfUseContentProvider termsOfUseContentProvider) {

		ServiceRegistration<TermsOfUseContentProvider> serviceRegistration =
			_serviceRegistrations.remove(termsOfUseContentProvider);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final TermsOfUseContentProviderRegistryUtil _instance =
		new TermsOfUseContentProviderRegistryUtil();

	private final ServiceRegistrationMap<TermsOfUseContentProvider>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
	private final ServiceTracker
		<TermsOfUseContentProvider, TermsOfUseContentProvider> _serviceTracker;
	private final Map<String, TermsOfUseContentProvider>
		_termsOfUseContentProviders = new ConcurrentHashMap<>();

	private class TermsOfUseContentProviderServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<TermsOfUseContentProvider, TermsOfUseContentProvider> {

		@Override
		public TermsOfUseContentProvider addingService(
			ServiceReference<TermsOfUseContentProvider> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			TermsOfUseContentProvider termsOfUseContentProvider =
				registry.getService(serviceReference);

			_termsOfUseContentProviders.put(
				termsOfUseContentProvider.getClassName(),
				termsOfUseContentProvider);

			return termsOfUseContentProvider;
		}

		@Override
		public void modifiedService(
			ServiceReference<TermsOfUseContentProvider> serviceReference,
			TermsOfUseContentProvider termsOfUseContentProvider) {
		}

		@Override
		public void removedService(
			ServiceReference<TermsOfUseContentProvider> serviceReference,
			TermsOfUseContentProvider termsOfUseContentProvider) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_termsOfUseContentProviders.remove(
				termsOfUseContentProvider.getClassName());
		}

	}

}
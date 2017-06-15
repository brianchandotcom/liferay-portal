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

package com.liferay.vulcan.wiring.osgi;

import com.liferay.vulcan.provider.Provider;
import com.liferay.vulcan.wiring.osgi.internal.ServiceReferenceServiceTuple;

import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = ProviderManager.class)
public class ProviderManager {

	public <T> Optional<T> provide(
		Class<T> clazz, HttpServletRequest httpServletRequest) {

		TreeSet<ServiceReferenceServiceTuple<Provider<?>>>
			serviceReferenceServiceTuples = _providers.get(clazz.getName());

		Optional<TreeSet<ServiceReferenceServiceTuple<Provider<?>>>> optional =
			Optional.ofNullable(serviceReferenceServiceTuples);

		return optional.filter(
			treeSet -> !treeSet.isEmpty()
		).map(
			TreeSet::first
		).map(
			serviceReferenceServiceTuple ->
				(Provider<T>)
					serviceReferenceServiceTuple.getService()
		).map(
			provider -> provider.createContext(httpServletRequest)
		);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected <T> void setServiceReference(
		ServiceReference<Provider<T>> serviceReference) {

		Provider<T> provider = _bundleContext.getService(serviceReference);

		Class<T> modelClass = GenericUtil.getGenericClass(
			provider, Provider.class);

		_providers.computeIfAbsent(
			modelClass.getName(), name -> new TreeSet<>());

		ServiceReferenceServiceTuple<Provider<T>> serviceReferenceServiceTuple =
			new ServiceReferenceServiceTuple<>(serviceReference, provider);

		TreeSet<ServiceReferenceServiceTuple<Provider<?>>>
			serviceReferenceServiceTuples = _providers.get(
				modelClass.getName());

		serviceReferenceServiceTuples.add(
			(ServiceReferenceServiceTuple)serviceReferenceServiceTuple);
	}

	protected <T> void unsetServiceReference(
		ServiceReference<Provider<T>> serviceReference) {

		Provider<T> resource = _bundleContext.getService(serviceReference);

		Class<T> modelClass = GenericUtil.getGenericClass(
			resource, Provider.class);

		TreeSet<ServiceReferenceServiceTuple<Provider<?>>>
			serviceReferenceServiceTuples = _providers.get(
				modelClass.getName());

		serviceReferenceServiceTuples.removeIf(
			modelRepresentorMapperTuple -> {
				if (modelRepresentorMapperTuple.getService() == resource) {
					return true;
				}

				return false;
			});
	}

	private final BundleContext _bundleContext = FrameworkUtil.getBundle(
		ProviderManager.class).getBundleContext();
	private final Map<String,
		TreeSet<ServiceReferenceServiceTuple<Provider<?>>>>
			_providers = new ConcurrentHashMap<>();

}
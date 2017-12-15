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

package com.liferay.apio.architect.wiring.osgi.internal.manager;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.apio.architect.provider.Provider;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class ProviderManagerImpl
	extends BaseManager<Provider> implements ProviderManager {

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<T> provideOptional(
		Class<T> clazz, HttpServletRequest httpServletRequest) {

		Optional<Provider> optional = getServiceOptional(clazz);

		return optional.map(
			service -> (Provider<T>)service
		).map(
			provider -> provider.createContext(httpServletRequest)
		);
	}

	@Override
	public <T> T provideOrNull(
		Class<T> clazz, HttpServletRequest httpServletRequest) {

		Optional<T> optional = provideOptional(clazz, httpServletRequest);

		return optional.orElse(null);
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<Provider> serviceReference) {

		addService(serviceReference, Provider.class);
	}

	@SuppressWarnings("unused")
	protected void unsetServiceReference(
		ServiceReference<Provider> serviceReference) {

		removeService(serviceReference, Provider.class);
	}

}
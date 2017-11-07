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

package com.liferay.vulcan.wiring.osgi.manager;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.vulcan.provider.Provider;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Manages services that have a {@link Provider}.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = ProviderManager.class)
public class ProviderManager extends BaseManager<Provider> {

	/**
	 * Returns the instance of type {@code T} if a valid {@code Provider} can be
	 * found. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  clazz the class type {@code T}
	 * @param  httpServletRequest the current request
	 * @return the instance of {@code T}, if a valid {@code Provider} is
	 *         present; {@code Optional#empty()} otherwise
	 */
	public <T> Optional<T> provide(
		Class<T> clazz, HttpServletRequest httpServletRequest) {

		Optional<Provider> optional = getServiceOptional(clazz);

		return optional.map(
			service -> (Provider<T>)service
		).map(
			provider -> provider.createContext(httpServletRequest)
		);
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<Provider> serviceReference) {

		addService(serviceReference);
	}

	@SuppressWarnings("unused")
	protected void unsetServiceReference(
		ServiceReference<Provider> serviceReference) {

		removeService(serviceReference);
	}

}
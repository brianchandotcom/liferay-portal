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

import static com.liferay.apio.architect.unsafe.Unsafe.unsafeCast;

import static org.osgi.service.component.annotations.ReferenceCardinality.OPTIONAL;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.logger.ApioLogger;
import com.liferay.apio.architect.provider.Provider;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.ClassNameBaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class ProviderManagerImpl
	extends ClassNameBaseManager<Provider> implements ProviderManager {

	public ProviderManagerImpl() {
		super(Provider.class, 0);
	}

	@Override
	public List<String> getMissingProviders(
		Collection<String> neededProviders) {

		Set<String> providedClassNames = serviceTrackerMap.keySet();

		List<String> list = new ArrayList<>(neededProviders);

		list.removeAll(providedClassNames);

		return list;
	}

	@Override
	public <T> T provideMandatory(
		HttpServletRequest httpServletRequest, Class<T> clazz) {

		Optional<T> optional = provideOptional(httpServletRequest, clazz);

		if (clazz.equals(Credentials.class) && !optional.isPresent()) {
			return unsafeCast((Credentials)() -> "");
		}

		return optional.orElseGet(
			() -> {
				if (_apioLogger != null) {
					_apioLogger.warning(
						"Missing provider for mandatory class: " + clazz);
				}

				throw new NotFoundException();
			});
	}

	@Override
	public <T> Optional<T> provideOptional(
		HttpServletRequest httpServletRequest, Class<T> clazz) {

		Optional<Provider<T>> optional = unsafeCast(getServiceOptional(clazz));

		return optional.map(
			provider -> provider.createContext(httpServletRequest));
	}

	@Reference(cardinality = OPTIONAL, policyOption = GREEDY)
	private ApioLogger _apioLogger;

}
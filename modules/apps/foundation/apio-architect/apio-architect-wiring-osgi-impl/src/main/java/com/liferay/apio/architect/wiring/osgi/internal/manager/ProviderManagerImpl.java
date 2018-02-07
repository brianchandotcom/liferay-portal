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

import com.liferay.apio.architect.provider.Provider;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.SimpleBaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class ProviderManagerImpl
	extends SimpleBaseManager<Provider> implements ProviderManager {

	public ProviderManagerImpl() {
		super(Provider.class);
	}

	@Override
	public <T> Optional<T> provideOptional(
		HttpServletRequest httpServletRequest, Class<T> clazz) {

		Optional<Provider<T>> optional = unsafeCast(getServiceOptional(clazz));

		return optional.map(
			provider -> provider.createContext(httpServletRequest));
	}

}
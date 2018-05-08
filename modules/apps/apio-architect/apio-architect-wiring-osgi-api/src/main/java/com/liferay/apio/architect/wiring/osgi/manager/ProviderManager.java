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

package com.liferay.apio.architect.wiring.osgi.manager;

import aQute.bnd.annotation.ProviderType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * Manages services that have a {@link
 * com.liferay.apio.architect.provider.Provider}.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@ProviderType
public interface ProviderManager {

	/**
	 * Returns the list of missing providers' class names.
	 *
	 * @param  neededProviders the list of needed providers
	 * @return the list of missing providers
	 */
	public List<String> getMissingProviders(Collection<String> neededProviders);

	/**
	 * Returns an instance of type {@code T} if a valid {@code Provider} is
	 * found; throws a {@code NotFoundException} otherwise.
	 *
	 * @param  httpServletRequest the current request
	 * @param  clazz the class type {@code T}
	 * @return the {@code T} instance, if a valid {@code Provider} is found;
	 *         throws {@code NotFoundException} otherwise
	 */
	public <T> T provideMandatory(
		HttpServletRequest httpServletRequest, Class<T> clazz);

	/**
	 * Returns the instance of type {@code T} if a valid {@code Provider} can be
	 * found. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  httpServletRequest the current request
	 * @param  clazz the class type {@code T}
	 * @return the instance of {@code T}, if a valid {@code Provider} is
	 *         present; {@code Optional#empty()} otherwise
	 */
	public <T> Optional<T> provideOptional(
		HttpServletRequest httpServletRequest, Class<T> clazz);

}
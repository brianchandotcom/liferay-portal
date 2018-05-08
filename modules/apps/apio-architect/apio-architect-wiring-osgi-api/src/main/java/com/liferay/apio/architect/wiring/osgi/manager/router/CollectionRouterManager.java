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

package com.liferay.apio.architect.wiring.osgi.manager.router;

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.routes.CollectionRoutes;

import java.util.List;
import java.util.Optional;

/**
 * Provides methods to retrieve the routes information provided by the different
 * {@link com.liferay.apio.architect.router.CollectionRouter} instances.
 *
 * @author Alejandro Hernández
 * @see    com.liferay.apio.architect.router.CollectionRouter
 */
@ProviderType
public interface CollectionRouterManager {

	/**
	 * Returns the collection routes for the collection resource's name.
	 *
	 * @param  name the collection resource's name
	 * @return the collection routes
	 */
	public <T> Optional<CollectionRoutes<T>> getCollectionRoutesOptional(
		String name);

	/**
	 * Returns a list containing the names of the resources with routes in this
	 * manager.
	 *
	 * @return a list containing the names of the resources with routes in this
	 *         manager
	 */
	public List<String> getResourceNames();

}
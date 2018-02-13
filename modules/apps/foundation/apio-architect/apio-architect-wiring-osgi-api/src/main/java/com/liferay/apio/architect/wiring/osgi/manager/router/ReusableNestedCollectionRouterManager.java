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

import com.liferay.apio.architect.routes.NestedCollectionRoutes;

import java.util.Optional;

/**
 * Provides methods to retrieve the routes information provided by the different
 * {@link com.liferay.apio.architect.router.ReusableNestedCollectionRouter}
 * instances.
 *
 * @author Alejandro Hernández
 * @see    com.liferay.apio.architect.router.ReusableNestedCollectionRouter
 */
@ProviderType
public interface ReusableNestedCollectionRouterManager {

	/**
	 * Returns the nested collection routes for the reusable nested collection
	 * resource's name.
	 *
	 * @param  name the reusable nested collection resource's name
	 * @return the nested collection routes
	 */
	public <T, S> Optional<NestedCollectionRoutes<T, S>>
		getNestedCollectionRoutesOptional(String name);

}
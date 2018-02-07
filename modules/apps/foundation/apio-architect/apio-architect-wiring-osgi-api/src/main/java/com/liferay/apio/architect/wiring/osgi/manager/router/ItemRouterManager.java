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

import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.routes.ItemRoutes;

import java.util.List;
import java.util.Optional;

/**
 * Provides methods to retrieve the routes information provided by the different
 * {@link com.liferay.apio.architect.router.ItemRouter} instances.
 *
 * @author Alejandro Hernández
 * @see    com.liferay.apio.architect.router.ItemRouter
 */
@ProviderType
public interface ItemRouterManager {

	/**
	 * Returns the item routes for the item resource's name.
	 *
	 * @param  name the item resource's name
	 * @return the item routes
	 */
	public <T> Optional<ItemRoutes<T>> getItemRoutesOptional(String name);

	/**
	 * Returns the operations for the item resource's name.
	 *
	 * @param  name the item resource's name
	 * @return the list of operations
	 * @review
	 */
	public List<Operation> getOperations(String name);

}
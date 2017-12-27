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

package com.liferay.apio.architect.router;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.CollectionRoutes.Builder;

/**
 * Instances of this interface represent the mapping between HTTP requests for a
 * collection resource and the functions that perform that requests.
 *
 * <p>
 * You can add the different supported routes for the collection resource via
 * the {@link #collectionRoutes(CollectionRoutes.Builder)} method.
 * </p>
 *
 * <p>
 * The union of an instance of this interface with a {@link
 * com.liferay.apio.architect.representor.Representable} creates a complete
 * resource that will behave as its own API.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 * @see    CollectionRoutes.Builder
 * @review
 */
@ConsumerType
public interface CollectionRouter<T> {

	/**
	 * Creates the {@link CollectionRoutes} supported by the collection
	 * resource. Use the provided routes builder to create the {@code
	 * CollectionRoutes} instance.
	 *
	 * @param builder the routes builder to use to create the {@code
	 *        CollectionRoutes} instance
	 * @see   CollectionRoutes.Builder
	 */
	public CollectionRoutes<T> collectionRoutes(Builder<T> builder);

}
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

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.CollectionRoutes.Builder;

/**
 * Represents the mapping between HTTP requests for a collection resource and
 * the functions that perform that requests.
 *
 * <p>
 * You can add the different routes supported for the collection resource via
 * the {@link #collectionRoutes(CollectionRoutes.Builder)} method.
 * </p>
 *
 * <p>
 * The union of an instance of this interface with a {@link
 * com.liferay.apio.architect.representor.Representable} creates a complete
 * resource that behaves as its own API.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 * @param  <S> the type of the resource's identifier. It must be a subclass of
 *         {@code Identifier}.
 * @see    CollectionRoutes.Builder
 */
@ConsumerType
@SuppressWarnings("unused")
public interface CollectionRouter<T, S extends Identifier> {

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
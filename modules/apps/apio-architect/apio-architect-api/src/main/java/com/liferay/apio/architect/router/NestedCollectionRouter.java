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
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder;

/**
 * Represents the mapping between HTTP requests for a nested collection resource
 * and the functions that perform those requests.
 *
 * <p>
 * The type parameter provided for the resource ID must be unique in the
 * application.
 * </p>
 *
 * <p>
 * You can add the different routes supported for the nested collection resource
 * via the {@link #collectionRoutes(NestedCollectionRoutes.Builder)} method.
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
 * @param  <U> the type of the parent model's identifier (e.g., {@code Long},
 *         {@code String}, etc.)
 * @param  <V> the type of the parent resource's identifier. It must be a
 *         subclass of {@code Identifier<V>}.
 * @see    NestedCollectionRoutes.Builder
 */
@ConsumerType
@SuppressWarnings("unused")
public interface NestedCollectionRouter
	<T, S extends Identifier, U, V extends Identifier<U>> {

	/**
	 * Creates the {@link NestedCollectionRoutes} supported by the nested
	 * collection resource. Use the provided routes builder to create the {@code
	 * NestedCollectionRoutes} instance.
	 *
	 * @param builder the routes builder to use to create the {@code
	 *        NestedCollectionRoutes} instance
	 * @see   NestedCollectionRoutes.Builder
	 */
	public NestedCollectionRoutes<T, U> collectionRoutes(Builder<T, U> builder);

}
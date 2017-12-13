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

package com.liferay.apio.architect.resource;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.routes.Routes;

/**
 * Maps your domain models to resources that Apio can understand.
 *
 * <p>
 * Resources behave like an API so you must add the API's name via the {@link
 * #getName()} method.
 * </p>
 *
 * <p>
 * Representors created by the {@link #representor(Representor.Builder)} method
 * hold all the information needed to write your domain models' hypermedia
 * representations.
 * </p>
 *
 * <p>
 * You can add the different supported routes for the resource via the {@link
 * #routes(Routes.Builder)} method.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @see    Representor.Builder
 * @see    Routes.Builder
 */
@ConsumerType
public interface CollectionResource<T, U extends Identifier> {

	/**
	 * Returns the resource's name.
	 *
	 * @return the resource's name
	 */
	public String getName();

	/**
	 * Creates a {@link Representor} for a certain domain model from the
	 * provided {@link Representor.Builder}.
	 *
	 * <p>
	 * To construct a representor, you must call {@link
	 * Representor.Builder.FirstStep#build()} ()}. Before calling this method,
	 * you must call the other representor builder methods to populate the
	 * builder with data. This ensures that the resulting representor contains
	 * the data.
	 * </p>
	 *
	 * @param builder the representor builder used to create the representor
	 * @see   Representor.Builder
	 */
	public Representor<T, U> representor(Representor.Builder<T, U> builder);

	/**
	 * Creates the {@link Routes} supported by the {@code CollectionResource}.
	 * Use the provided routes builder to create the {@code Routes} instance.
	 *
	 * @param builder the routes builder to use to create the {@code Routes}
	 *        instance
	 * @see   Routes.Builder
	 */
	public Routes<T> routes(Routes.Builder<T, U> builder);

}
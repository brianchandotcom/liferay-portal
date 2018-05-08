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
import com.liferay.apio.architect.router.NestedCollectionRouter;

/**
 * Maps your domain models to nested collection resources that Apio can
 * understand.
 *
 * <p>
 * Resources behave like an API, so you must add the API's name via the {@link
 * #getName()} method.
 * </p>
 *
 * <p>
 * The type param provided for the resource ID must be unique in the
 * application.
 * </p>
 *
 * <p>
 * Representors created by the method {@link
 * #representor(com.liferay.apio.architect.representor.Representor.Builder)}
 * hold all the information needed to write your domain models' hypermedia
 * representations.
 * </p>
 *
 * <p>
 * You can add the different supported routes for the collection resource via
 * the method {@link
 * #collectionRoutes(
 * com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder)}.
 * </p>
 *
 * <p>
 * You can add the different supported routes for the single resource via the
 * {@link #itemRoutes(com.liferay.apio.architect.routes.ItemRoutes.Builder)}
 * method.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 * @param  <S> the type of the model's identifier (e.g., {@code Long}, {@code
 *         String}, etc.)
 * @param  <U> the type of the resource's identifier. It must be a subclass of
 *         {@code Identifier<S>}.
 * @param  <V> the type of the parent model's identifier (e.g., {@code Long},
 *         {@code String}, etc.)
 * @param  <W> the type of the parent resource's identifier. It must be a
 *         subclass of {@code Identifier<V>}.
 * @see    com.liferay.apio.architect.representor.Representor.Builder
 * @see    com.liferay.apio.architect.routes.ItemRoutes.Builder
 * @see    com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder
 */
@ConsumerType
public interface NestedCollectionResource
	<T, S, U extends Identifier<S>, V, W extends Identifier<V>>
		extends ItemResource<T, S, U>, NestedCollectionRouter<T, U, V, W> {
}
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

package com.liferay.screens.architect.resource;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.screens.architect.identifier.Identifier;
import com.liferay.screens.architect.representor.Representable;
import com.liferay.screens.architect.router.ItemRouter;

/**
 * Maps your domain models to item resources that Apio can understand.
 *
 * <p>
 * Resources behave like an API, so you must add the API's name via the {@link
 * #getName()} method.
 * </p>
 *
 * <p>
 * The type param provided for the resource ID must be unique in the whole
 * application.
 * </p>
 *
 * <p>
 * Representors created by the {@link
 * #representor(com.liferay.screens.architect.representor.Representor.Builder)}
 * method hold all the information needed to write your domain models'
 * hypermedia representations.
 * </p>
 *
 * <p>
 * You can add the different supported routes via the {@link
 * #itemRoutes(ItemRoutes.Builder)} method.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 * @param  <S> the type of the model's identifier (e.g., {@code Long}, {@code
 *         String}, etc.)
 * @param  <U> the type of the resource's identifier. It must be a subclass of
 *         {@code Identifier<S>}.
 * @see    com.liferay.screens.architect.representor.Representor.Builder
 * @see    ItemRoutes.Builder
 * @review
 */
@ConsumerType
public interface ItemResource<T, S, U extends Identifier<S>>
	extends Representable<T, S, U>, ItemRouter<T, S, U> {
}
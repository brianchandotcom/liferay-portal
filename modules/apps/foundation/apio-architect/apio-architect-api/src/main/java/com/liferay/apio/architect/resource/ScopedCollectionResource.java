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

/**
 * Maps your domain models to resources that Apio can understand.
 *
 * <p>
 * {@code ScopedCollectionResources} are a special type of {@code
 * CollectionResource} that are meant to be exposed dependent of another {@code
 * CollectionResource}.
 * </p>
 *
 * <p>
 * Resources behave like an API so you must add the API's name via the {@link
 * #getName()} method.
 * </p>
 *
 * <p>
 * Representors created by the method {@link
 * CollectionResource#representor(
 * com.liferay.apio.architect.representor.Representor.Builder)} hold all the
 * information needed to write your domain models' hypermedia representations.
 * </p>
 *
 * <p>
 * Finally, you can add the different supported routes for the resource via the
 * method {@link
 * CollectionResource#routes(com.liferay.apio.architect.routes.Routes.Builder)}.
 * </p>
 *
 * @author Alejandro Hernández
 * @see    com.liferay.apio.architect.representor.Representor.Builder
 * @see    com.liferay.apio.architect.routes.Routes.Builder
 */
@ConsumerType
public interface ScopedCollectionResource<T, U extends Identifier>
	extends CollectionResource<T, U> {
}
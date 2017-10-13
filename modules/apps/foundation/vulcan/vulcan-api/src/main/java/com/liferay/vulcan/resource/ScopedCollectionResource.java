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

package com.liferay.vulcan.resource;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.vulcan.resource.identifier.Identifier;

/**
 * Maps your domain models to resources that Vulcan can understand.
 *
 * <p>
 * {@code ScopedCollectionResources} are a special type of {@code
 * CollectionResource} that are meant to be exposed dependant of another {@code
 * CollectionResource}.
 * </p>
 *
 * <p>
 * Resources behave like an API so you must add the name for the API via the
 * {@link #getName()} method.
 * </p>
 *
 * <p>
 * Representors created by the {@link #buildRepresentor} method hold all the
 * information needed to write your domain models' hypermedia representations.
 * </p>
 *
 * <p>
 * Finally you can add the different supported routes for the resource via the
 * {@link #routes(RoutesBuilder)} method.
 * </p>
 *
 * @author Alejandro Hernández
 * @see    RepresentorBuilder
 * @see    RoutesBuilder
 * @review
 */
@ConsumerType
public interface ScopedCollectionResource<T, U extends Identifier>
	extends CollectionResource<T, U> {
}
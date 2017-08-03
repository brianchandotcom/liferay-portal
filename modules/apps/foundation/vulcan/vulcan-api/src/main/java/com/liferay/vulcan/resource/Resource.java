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

import com.liferay.vulcan.resource.builder.RepresentorBuilder;
import com.liferay.vulcan.resource.builder.RoutesBuilder;

/**
 * Maps your domain models to resources that Vulcan can understand.
 *
 * <p>
 * Resources behave like an independent API so you must add the path for the API
 * via the {@link #getPath()} method.
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
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @see    RepresentorBuilder
 * @see    RoutesBuilder
 */
public interface Resource<T> {

	/**
	 * Creates a representor for a certain domain model from the provided {@link
	 * RepresentorBuilder}.
	 *
	 * <p>
	 * Note that this builder doesn't construct a <code>Representor</code>
	 * object (such objects don't actually exist). You need to call the builder
	 * methods providing as much information as possible.
	 * </p>
	 *
	 * @param representorBuilder the builder used to create the representor.
	 * @see   RepresentorBuilder
	 */
	public void buildRepresentor(RepresentorBuilder<T> representorBuilder);

	/**
	 * Returns the path for this resource.
	 *
	 * @return the path for this resource.
	 */
	public String getPath();

	/**
	 * Creates the {@link Routes} supported by the Resource.
	 *
	 * <p>
	 * To create the instance of {@link Routes} use the provided {@link
	 * RoutesBuilder}.
	 * </p>
	 *
	 * @param routesBuilder the builder used to create the routes.
	 * @see   RoutesBuilder
	 */
	public Routes<T> routes(RoutesBuilder<T> routesBuilder);

}
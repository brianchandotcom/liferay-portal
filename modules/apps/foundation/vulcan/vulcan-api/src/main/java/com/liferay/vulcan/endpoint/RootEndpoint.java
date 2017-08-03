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

package com.liferay.vulcan.endpoint;

import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.resource.Routes;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Declares the endpoint from which all of your APIs originate.
 *
 * <p>
 * There should only be one RootEndpoint in the application.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public interface RootEndpoint {

	/**
	 * Returns the {@link SingleModel} for a given path.
	 *
	 * @param  path the path from the URL.
	 * @return the single model at the path.
	 */
	@GET
	@Path("/p/{path}/{id}")
	public default <T> SingleModel<T> getCollectionItemSingleModel(
		@PathParam("path") String path, @PathParam("id") String id) {

		Routes<T> routes = getRoutes(path);

		Optional<Function<String, SingleModel<T>>> optional =
			routes.getSingleModelFunctionOptional();

		Function<String, SingleModel<T>> singleModelFunction =
			optional.orElseThrow(NotFoundException::new);

		return singleModelFunction.apply(id);
	}

	/**
	 * Returns the collection {@link Page} for a given path.
	 *
	 * @param  path the path from the URL.
	 * @return the collection page at the path.
	 */
	@GET
	@Path("/p/{path}")
	public default <T> Page<T> getCollectionPage(
		@PathParam("path") String path) {

		Routes<T> routes = getRoutes(path);

		Optional<Supplier<Page<T>>> optional = routes.getPageSupplierOptional();

		Supplier<Page<T>> pageSupplier = optional.orElseThrow(
			NotFoundException::new);

		return pageSupplier.get();
	}

	/**
	 * Returns the {@link Routes} instance for a given path. The result of this
	 * method may vary depending on implementation.
	 *
	 * @param  path the path from the URL.
	 * @return the {@link Routes} instance for the path.
	 */
	public <T> Routes<T> getRoutes(String path);

}
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

package com.liferay.apio.architect.endpoint;

import static com.liferay.apio.architect.endpoint.ExceptionSupplierUtil.notFound;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Declares the endpoint for form operations.
 *
 * @author Alejandro Hernández
 */
public class FormEndpoint {

	public FormEndpoint(
		Function<String, Optional<CollectionRoutes<Object>>>
			collectionRoutesFunction,
		Function<String, Optional<ItemRoutes<Object, Object>>>
			itemRoutesFunction,
		BiFunction<String, String, Optional
			<NestedCollectionRoutes<Object, Object>>>
				nestedCollectionRoutesFunction) {

		_collectionRoutesFunction = collectionRoutesFunction;
		_itemRoutesFunction = itemRoutesFunction;
		_nestedCollectionRoutesFunction = nestedCollectionRoutesFunction;
	}

	/**
	 * Returns the creator {@link Form} for the specified resource.
	 *
	 * @param  name the resource's name, extracted from the URL
	 * @return the {@link Form} for the specified resource, or an exception if
	 *         an error occurred
	 */
	@GET
	@Path("c/{name}")
	public Try<Form> creatorForm(@PathParam("name") String name) {
		return Try.fromOptional(
			() -> _collectionRoutesFunction.apply(
				name
			).flatMap(
				CollectionRoutes::getFormOptional
			),
			notFound(name));
	}

	/**
	 * Returns the nested creator {@link Form} for the specified resource.
	 *
	 * @param  name the parent resource's name, extracted from the URL
	 * @param  nestedName the nested resource's name, extracted from the URL
	 * @return the {@link Form} for the specified resource, or an exception if
	 *         an error occurred
	 */
	@GET
	@Path("c/{name}/{nestedName}")
	public Try<Form> nestedCreatorForm(
		@PathParam("name") String name,
		@PathParam("nestedName") String nestedName) {

		return Try.fromOptional(
			() -> _nestedCollectionRoutesFunction.apply(
				name, nestedName
			).flatMap(
				NestedCollectionRoutes::getFormOptional
			),
			notFound(name, nestedName));
	}

	/**
	 * Returns the updater {@link Form} for the specified resource.
	 *
	 * @param  name the resource's name, extracted from the URL
	 * @return the {@link Form} for the specified resource, or an exception if
	 *         an error occurred
	 */
	@GET
	@Path("u/{name}")
	public Try<Form> updaterForm(@PathParam("name") String name) {
		return Try.fromOptional(
			() -> _itemRoutesFunction.apply(
				name
			).flatMap(
				ItemRoutes::getFormOptional
			),
			notFound(name));
	}

	private final Function<String, Optional<CollectionRoutes<Object>>>
		_collectionRoutesFunction;
	private final Function<String, Optional<ItemRoutes<Object, Object>>>
		_itemRoutesFunction;
	private final BiFunction<String, String,
		Optional<NestedCollectionRoutes<Object, Object>>>
			_nestedCollectionRoutesFunction;

}
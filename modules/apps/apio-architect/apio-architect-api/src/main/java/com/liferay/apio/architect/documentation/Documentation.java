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

package com.liferay.apio.architect.documentation;

import com.liferay.apio.architect.alias.RequestFunction;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Represents the API's auto-documentation.
 *
 * @author Alejandro Hernández
 */
public class Documentation {

	public Documentation(
		RequestFunction<Optional<APITitle>> apiTitleRequestFunction,
		RequestFunction<Optional<APIDescription>> apiDescriptionRequestFunction,
		Supplier<Map<String, Representor>> representorMapSupplier,
		Supplier<Map<String, CollectionRoutes>> collectionRoutesMapSupplier,
		Supplier<Map<String, ItemRoutes>> itemRoutesMapSupplier) {

		_apiTitleRequestFunction = apiTitleRequestFunction;
		_apiDescriptionRequestFunction = apiDescriptionRequestFunction;
		_representorMapSupplier = representorMapSupplier;
		_routesMapSupplier = collectionRoutesMapSupplier;
		_itemRoutesMapSupplier = itemRoutesMapSupplier;
	}

	/**
	 * Returns the function that calculates the API's description, if present.
	 * Returns {@code Optional#empty()} otherwise.
	 *
	 * @return the API's description, if present; {@code Optional#empty()}
	 *         otherwise
	 */
	public RequestFunction<Optional<String>>
		getAPIDescriptionRequestFunction() {

		return httpServletRequest -> _apiDescriptionRequestFunction.apply(
			httpServletRequest
		).map(
			APIDescription::get
		);
	}

	/**
	 * Returns the function that calculates the API's description, if present.
	 * Returns {@code Optional#empty()} otherwise.
	 *
	 * @return the API's title, if present; {@code Optional#empty()} otherwise
	 */
	public RequestFunction<Optional<String>> getAPITitleRequestFunction() {
		return httpServletRequest -> _apiTitleRequestFunction.apply(
			httpServletRequest
		).map(
			APITitle::get
		);
	}

	public Supplier<Map<String, ItemRoutes>> getItemRoutesMapSupplier() {
		return _itemRoutesMapSupplier;
	}

	public Supplier<Map<String, Representor>> getRepresentorMapSupplier() {
		return _representorMapSupplier;
	}

	public Supplier<Map<String, CollectionRoutes>> getRoutesMapSupplier() {
		return _routesMapSupplier;
	}

	private final RequestFunction<Optional<APIDescription>>
		_apiDescriptionRequestFunction;
	private final RequestFunction<Optional<APITitle>> _apiTitleRequestFunction;
	private final Supplier<Map<String, ItemRoutes>> _itemRoutesMapSupplier;
	private final Supplier<Map<String, Representor>> _representorMapSupplier;
	private final Supplier<Map<String, CollectionRoutes>> _routesMapSupplier;

}
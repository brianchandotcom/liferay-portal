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

package com.liferay.apio.architect.impl.documentation;

import com.liferay.apio.architect.documentation.APIDescription;
import com.liferay.apio.architect.documentation.APITitle;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;

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
		Supplier<Optional<APITitle>> apiTitleSupplier,
		Supplier<Optional<APIDescription>> apiDescriptionSupplier,
		Supplier<Map<String, Representor>> representorMapSupplier,
		Supplier<Map<String, CollectionRoutes>> collectionRoutesMapSupplier,
		Supplier<Map<String, ItemRoutes>> itemRoutesMapSupplier,
		Supplier<Map<String, NestedCollectionRoutes>>
			nestedCollectionRoutesMapSupplier) {

		_apiTitleSupplier = apiTitleSupplier;
		_apiDescriptionSupplier = apiDescriptionSupplier;
		_representorMapSupplier = representorMapSupplier;
		_routesMapSupplier = collectionRoutesMapSupplier;
		_itemRoutesMapSupplier = itemRoutesMapSupplier;
		_nestedCollectionRoutesMapSupplier = nestedCollectionRoutesMapSupplier;
	}

	/**
	 * Returns the API's description, if present. Returns {@code
	 * Optional#empty()} otherwise.
	 *
	 * @return the API's description, if present; {@code Optional#empty()}
	 *         otherwise
	 * @review
	 */
	public Optional<String> getAPIDescriptionOptional() {
		Optional<APIDescription> optional = _apiDescriptionSupplier.get();

		return optional.map(APIDescription::get);
	}

	/**
	 * Returns the API's title, if present. Returns {@code Optional#empty()}
	 * otherwise.
	 *
	 * @return the API's title, if present; {@code Optional#empty()} otherwise
	 * @review
	 */
	public Optional<String> getAPITitleOptional() {
		Optional<APITitle> optional = _apiTitleSupplier.get();

		return optional.map(APITitle::get);
	}

	/**
	 * Returns a map containing the resources names as keys, and their {@link
	 * CollectionRoutes} as values.
	 *
	 * @return a map with the item routes
	 * @review
	 */
	public Map<String, CollectionRoutes> getCollectionRoutes() {
		return _routesMapSupplier.get();
	}

	/**
	 * Returns a map containing the resources names as keys, and their {@link
	 * ItemRoutes} as values.
	 *
	 * @return a map with the item routes
	 * @review
	 */
	public Map<String, ItemRoutes> getItemRoutes() {
		return _itemRoutesMapSupplier.get();
	}

	/**
	 * Returns a map containing the resources names as keys, and their {@link
	 * NestedCollectionRoutes} as values.
	 *
	 * @return a map with the item routes
	 * @review
	 */
	public Map<String, NestedCollectionRoutes> getNestedCollectionRoutes() {
		return _nestedCollectionRoutesMapSupplier.get();
	}

	/**
	 * Returns a map containing the resources names as keys, and their {@link
	 * Representor} as values.
	 *
	 * @return a map with the item routes
	 * @review
	 */
	public Map<String, Representor> getRepresentors() {
		return _representorMapSupplier.get();
	}

	private final Supplier<Optional<APIDescription>> _apiDescriptionSupplier;
	private final Supplier<Optional<APITitle>> _apiTitleSupplier;
	private final Supplier<Map<String, ItemRoutes>> _itemRoutesMapSupplier;
	private final Supplier<Map<String, NestedCollectionRoutes>>
		_nestedCollectionRoutesMapSupplier;
	private final Supplier<Map<String, Representor>> _representorMapSupplier;
	private final Supplier<Map<String, CollectionRoutes>> _routesMapSupplier;

}
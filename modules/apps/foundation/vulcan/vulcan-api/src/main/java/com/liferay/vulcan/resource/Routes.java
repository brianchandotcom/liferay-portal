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

import aQute.bnd.annotation.ProviderType;

import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.resource.identifier.Identifier;
import com.liferay.vulcan.uri.Path;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Instances of this interface will hold information about the routes supported
 * for a certain {@link CollectionResource}.
 *
 * <p>
 * All of the methods in this interface returns functions to get the different
 * endpoints of the {@link CollectionResource}.
 * </p>
 *
 * <p>
 * Instances of this interface should always be created by using a {@link
 * com.liferay.vulcan.resource.builder.RoutesBuilder}.
 * </p>
 *
 * @author Alejandro Hernández
 * @see    com.liferay.vulcan.resource.builder.RoutesBuilder
 * @review
 */
@ProviderType
public interface Routes<T> {

	/**
	 * Returns the function used to remove a single model of a {@link
	 * CollectionResource}. Returns {@code Optional#empty()} if the endpoint
	 * wasn't added through the {@link
	 * com.liferay.vulcan.resource.builder.RoutesBuilder}.
	 *
	 * @return the function used to remove a single model, if present; {@code
	 *         Optional#empty()} otherwise.
	 * @review
	 */
	public Optional<Consumer<Path>> getDeleteSingleModelConsumerOptional();

	/**
	 * Returns the function used to create the page of a {@link
	 * CollectionResource}. Returns {@code Optional#empty()} if the endpoint
	 * wasn't added through the {@link
	 * com.liferay.vulcan.resource.builder.RoutesBuilder}.
	 *
	 * @return the supplier used to create the page, if present; {@code
	 *         Optional#empty()} otherwise.
	 * @review
	 */
	public Optional<Function<Path, Function<Identifier, Page<T>>>>
		getPageFunctionOptional();

	/**
	 * Returns the function used to create the single model of a {@link
	 * CollectionResource}. Returns {@code Optional#empty()} if the endpoint
	 * wasn't added through the {@link
	 * com.liferay.vulcan.resource.builder.RoutesBuilder}.
	 *
	 * @return the function used to create the single model, if present; {@code
	 *         Optional#empty()} otherwise.
	 * @review
	 */
	public Optional<Function<Identifier, Function<Map<String, Object>,
		SingleModel<T>>>> getPostSingleModelFunctionOptional();

	/**
	 * Returns the function used to create the single model of a {@link
	 * CollectionResource}. Returns {@code Optional#empty()} if the endpoint
	 * wasn't added through the {@link
	 * com.liferay.vulcan.resource.builder.RoutesBuilder}.
	 *
	 * @return the function used to create the single model, if present; {@code
	 *         Optional#empty()} otherwise.
	 * @review
	 */
	public Optional<Function<Path, SingleModel<T>>>
		getSingleModelFunctionOptional();

	/**
	 * Returns the function used to update a single model of a {@link
	 * CollectionResource}. Returns {@code Optional#empty()} if the endpoint
	 * wasn't added through the {@link
	 * com.liferay.vulcan.resource.builder.RoutesBuilder}.
	 *
	 * @return the function used to update a single model, if present; {@code
	 *         Optional#empty()} otherwise.
	 * @review
	 */
	public Optional<Function<Path, Function<Map<String, Object>,
		SingleModel<T>>>> getUpdateSingleModelFunctionOptional();

}
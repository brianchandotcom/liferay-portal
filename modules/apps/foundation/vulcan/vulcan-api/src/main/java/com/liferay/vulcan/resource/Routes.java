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

import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.pagination.SingleModel;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Instances of this interface will hold information about the routes supported
 * for a certain {@link Resource}.
 *
 * <p>
 * All of the methods in this interface returns functions to get the different
 * endpoints of the {@link Resource}.
 * </p>
 *
 * <p>
 * Instances of this interface should always be created by using a {@link
 * com.liferay.vulcan.resource.builder.RoutesBuilder}.
 * </p>
 *
 * @author Alejandro Hernández
 * @see    com.liferay.vulcan.resource.builder.RoutesBuilder
 */
public interface Routes<T> {

	/**
	 * Returns the supplier used to create the page of a {@link Resource}.
	 * Returns <code>Optional#empty()</code> if the endpoint wasn't added
	 * through the {@link com.liferay.vulcan.resource.builder.RoutesBuilder}.
	 *
	 * @return the supplier used to create the page, if present;
	 *         <code>Optional#empty()</code> otherwise.
	 */
	public Optional<Supplier<Page<T>>> getPageSupplierOptional();

	/**
	 * Returns the function used to create the single model of a {@link
	 * Resource}. Returns <code>Optional#empty()</code> if the endpoint wasn't
	 * added through the {@link
	 * com.liferay.vulcan.resource.builder.RoutesBuilder}.
	 *
	 * @return the function used to create the single model, if present;
	 *         <code>Optional#empty()</code> otherwise.
	 */
	public Optional<Function<String, SingleModel<T>>>
		getSingleModelFunctionOptional();

}
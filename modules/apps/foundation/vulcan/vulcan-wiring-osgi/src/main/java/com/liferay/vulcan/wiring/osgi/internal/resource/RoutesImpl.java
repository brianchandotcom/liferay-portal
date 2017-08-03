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

package com.liferay.vulcan.wiring.osgi.internal.resource;

import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.resource.Routes;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Alejandro Hernández
 */
public class RoutesImpl<T> implements Routes<T> {

	@Override
	public Optional<Supplier<Page<T>>> getPageSupplierOptional() {
		return Optional.ofNullable(_pageSupplier);
	}

	@Override
	public Optional<Function<String, SingleModel<T>>>
		getSingleModelFunctionOptional() {

		return Optional.ofNullable(_singleModelFunction);
	}

	public void setPageSupplier(Supplier<Page<T>> pageSupplier) {
		_pageSupplier = pageSupplier;
	}

	public void setSingleModelFunction(
		Function<String, SingleModel<T>> singleModelFunction) {

		_singleModelFunction = singleModelFunction;
	}

	private Supplier<Page<T>> _pageSupplier;
	private Function<String, SingleModel<T>> _singleModelFunction;

}
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

package com.liferay.vulcan.wiring.osgi.internal;

import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.representor.Routes;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 */
public class RoutesImpl<T> implements Routes<T> {

	public RoutesImpl(
		Function<Function<Class<?>, Optional<?>>, PageItems<T>>
			pageItemsFunction,
		Function<Function<Class<?>, Optional<?>>, Function<String, T>>
			modelFunction) {

		_pageItemsFunction = pageItemsFunction;
		_modelFunction = modelFunction;
	}

	public Function<Function<Class<?>, Optional<?>>, Function<String, T>>
		getModelFunction() {

		return _modelFunction;
	}

	public Function<Function<Class<?>, Optional<?>>, PageItems<T>>
		getPageItemsFunction() {

		return _pageItemsFunction;
	}

	private final Function<Function<Class<?>, Optional<?>>, Function<String, T>>
		_modelFunction;
	private final Function<Function<Class<?>, Optional<?>>, PageItems<T>>
		_pageItemsFunction;

}
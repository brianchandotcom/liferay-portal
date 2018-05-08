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

package com.liferay.apio.architect.alias.routes;

import com.liferay.apio.architect.alias.RequestFunction;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.Page;

/**
 * Defines a type alias for a function that can be used to get a collection
 * page.
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 */
@FunctionalInterface
public interface GetPageFunction<T> extends RequestFunction<Try<Page<T>>> {
}
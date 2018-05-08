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

package com.liferay.apio.architect.writer.alias;

import com.liferay.apio.architect.uri.Path;

import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Defines a type alias for a function that receives the resource name and the
 * identifier. The function returns an optional {@link Path} for the resource.
 *
 * @author Alejandro Hernández
 */
@FunctionalInterface
public interface PathFunction
	extends BiFunction<String, Object, Optional<Path>> {
}
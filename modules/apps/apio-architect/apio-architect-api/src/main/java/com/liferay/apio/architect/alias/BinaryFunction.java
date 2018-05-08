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

package com.liferay.apio.architect.alias;

import com.liferay.apio.architect.file.BinaryFile;

import java.util.function.Function;

/**
 * Defines a type alias for a function that receives a model of type {@code T}
 * and returns a binary representation of that model in an {@code InputStream}.
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 */
@FunctionalInterface
public interface BinaryFunction<T> extends Function<T, BinaryFile> {
}
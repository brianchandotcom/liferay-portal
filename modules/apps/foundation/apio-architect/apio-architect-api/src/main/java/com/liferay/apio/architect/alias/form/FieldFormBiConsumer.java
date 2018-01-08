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

package com.liferay.apio.architect.alias.form;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Defines a type alias for a consumer that receives a {@code String} (the key)
 * and a function that receives a {@code T} (the type of the form store) and
 * returns a consumer of {@code S} (the type of field).
 *
 * @author Alejandro Hernández
 * @param  <T> the form's type
 * @param  <S> the field's type
 * @review
 */
public interface FieldFormBiConsumer<T, S>
	extends BiConsumer<String, Function<T, Consumer<S>>> {
}
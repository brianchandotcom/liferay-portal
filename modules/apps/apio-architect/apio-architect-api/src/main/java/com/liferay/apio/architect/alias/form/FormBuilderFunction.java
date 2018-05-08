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

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

import java.util.function.Function;

/**
 * Defines a type alias for a function that receives a {@link Form.Builder} of
 * type {@code T} and returns the constructed {@link Form} of type {@code T}.
 *
 * @author Alejandro Hernández
 * @param  <T> the form's type
 */
public interface FormBuilderFunction<T> extends Function<Builder<T>, Form<T>> {
}
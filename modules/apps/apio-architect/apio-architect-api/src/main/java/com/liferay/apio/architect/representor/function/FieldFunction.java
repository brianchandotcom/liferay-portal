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

package com.liferay.apio.architect.representor.function;

import java.util.function.Function;

/**
 * Instances of this class represent the key which represents a model's field
 * and the function to calculate that field.
 *
 * @author Alejandro Hernández
 * @review
 */
public class FieldFunction<T, S> {

	public FieldFunction(String key, Function<T, S> function) {
		this.key = key;
		this.function = function;
	}

	/**
	 * The function that transforms the model into the field
	 *
	 * @review
	 */
	public final Function<T, S> function;

	/**
	 * The field key
	 *
	 * @review
	 */
	public final String key;

}
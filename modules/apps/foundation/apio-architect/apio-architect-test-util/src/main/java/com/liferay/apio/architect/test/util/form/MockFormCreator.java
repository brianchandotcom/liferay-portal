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

package com.liferay.apio.architect.test.util.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

import java.util.Arrays;
import java.util.function.BiConsumer;

/**
 * Provides methods that create {@link Form} objects.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class MockFormCreator {

	/**
	 * Creates a mock {@code Form}.
	 *
	 * @param  paths the list of paths for the form
	 * @return the mock {@code Form}
	 */
	public static Form createForm(String... paths) {
		Builder<Object> builder = new Builder<>(Arrays.asList(paths));

		return builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			Object::new
		).addOptionalBoolean(
			"boolean1", _emptyBiConsumer()
		).addOptionalDate(
			"date1", _emptyBiConsumer()
		).addOptionalDouble(
			"double1", _emptyBiConsumer()
		).addOptionalLong(
			"long1", _emptyBiConsumer()
		).addOptionalString(
			"string1", _emptyBiConsumer()
		).addRequiredBoolean(
			"boolean2", _emptyBiConsumer()
		).addRequiredDate(
			"date2", _emptyBiConsumer()
		).addRequiredDouble(
			"double2", _emptyBiConsumer()
		).addRequiredLong(
			"long2", _emptyBiConsumer()
		).addRequiredString(
			"string2", _emptyBiConsumer()
		).build();
	}

	private static <T> BiConsumer<Object, T> _emptyBiConsumer() {
		return (object, t) -> {
		};
	}

	private MockFormCreator() {
		throw new UnsupportedOperationException();
	}

}
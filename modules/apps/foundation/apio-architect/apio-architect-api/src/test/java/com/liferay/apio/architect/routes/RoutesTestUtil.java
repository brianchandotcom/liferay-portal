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

package com.liferay.apio.architect.routes;

import com.liferay.apio.architect.alias.IdentifierFunction;
import com.liferay.apio.architect.alias.ProvideFunction;
import com.liferay.apio.architect.alias.form.FormBuilderFunction;
import com.liferay.apio.architect.auth.Auth;
import com.liferay.apio.architect.pagination.Pagination;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Provides utilities for testing routes classes.
 *
 * @author Alejandro Hernández
 */
public class RoutesTestUtil {

	/**
	 * A collection permission function that always returns {@code true}.
	 *
	 * @review
	 */
	public static final Function<Auth, Boolean> COLLECTION_PERMISSION_FUNCTION =
		__ -> true;

	/**
	 * A {@code FormBuilderFunction} that creates a {@code Map<String, Object>}
	 * {@code Form}.
	 *
	 * @review
	 */
	public static final FormBuilderFunction<Map<String, Object>>
		FORM_BUILDER_FUNCTION = builder -> builder.title(
			__ -> ""
		).description(
			__ -> ""
		).constructor(
			HashMap::new
		).addRequiredString(
			"key", (map, value) -> map.put("key", value)
		).build();

	/**
	 * An {@code IdentifierFunction} that returns the {@code Path} ID long's
	 * value.
	 *
	 * @review
	 */
	public static final IdentifierFunction<Long> IDENTIFIER_FUNCTION =
		path -> Long.valueOf(path.getId());

	/**
	 * An item permission function that always returns {@code true}.
	 *
	 * @review
	 */
	public static final BiFunction<Auth, Long, Boolean>
		ITEM_PERMISSION_FUNCTION = (auth, aLong) -> true;

	/**
	 * Mocked {@code Pagination}.
	 *
	 * @review
	 */
	public static final Pagination PAGINATION = new Pagination(4, 2);

	/**
	 * A function that is able to provide instances of {@code String}, {@code
	 * Long}, {@code Integer}, {@code Boolean}, {@code Float}, {@code
	 * Pagination} and {@code Auth}.
	 *
	 * @review
	 */
	public static final Function<Class<?>, Optional<?>> PROVIDE_FUNCTION =
		aClass -> {
			if (aClass.equals(String.class)) {
				return Optional.of("Apio");
			}
			else if (aClass.equals(Long.class)) {
				return Optional.of(42L);
			}
			else if (aClass.equals(Integer.class)) {
				return Optional.of(2017);
			}
			else if (aClass.equals(Boolean.class)) {
				return Optional.of(true);
			}
			else if (aClass.equals(Float.class)) {
				return Optional.of(0.1F);
			}
			else if (aClass.equals(Pagination.class)) {
				return Optional.of(PAGINATION);
			}
			else if (aClass.equals(Auth.class)) {
				return Optional.of((Auth)() -> "auth");
			}
			else {
				return Optional.empty();
			}
		};

	/**
	 * A {@code ProvideFunction} that is able to provide instances of {@code
	 * String}, {@code Long}, {@code Integer}, {@code Boolean}, {@code Float}
	 * and {@code Pagination}.
	 *
	 * @review
	 */
	public static final ProvideFunction REQUEST_PROVIDE_FUNCTION =
		__ -> PROVIDE_FUNCTION;

	/**
	 * Returns a nested collection permission function that always returns
	 * {@code true}.
	 *
	 * @review
	 */
	public static <S> BiFunction<Auth, S, Boolean>
		getNestedCollectionPermissionFunction() {

		return (auth, s) -> true;
	}

}
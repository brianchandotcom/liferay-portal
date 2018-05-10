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

package com.liferay.apio.architect.representor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.related.RelatedModel;
import com.liferay.apio.architect.representor.dummy.Dummy;
import com.liferay.apio.architect.representor.function.FieldFunction;

import java.util.List;
import java.util.function.Function;

/**
 * Provides utilities for testing the {@code Representor} class.
 *
 * @author Alejandro Hernández
 */
public class RepresentorTestUtil {

	/**
	 * Tests that a {@code List<FieldFunction<T, S>>} has a key list equal to
	 * the provided key list, and a value list (obtained by applying {@code t}
	 * to each function and then applying the provided function) equal to the
	 * provided value list.
	 *
	 * @param  t the {@code t} instance
	 * @param  list the list to test
	 * @param  function the function to transform each value
	 * @param  keys the expected key list
	 * @param  values the expected value list
	 * @review
	 */
	public static <T, S, U> void testFields(
		T t, List<FieldFunction<T, S>> list, Function<S, U> function,
		List<String> keys, List<U> values) {

		if (keys.size() != values.size()) {
			throw new AssertionError(
				"The keys and values lists should have equal size");
		}

		assertThat(list.size(), is(keys.size()));

		for (int i = 0; i < keys.size(); i++) {
			FieldFunction<T, S> fieldFunction = list.get(i);

			assertThat(fieldFunction.key, is(keys.get(i)));

			U u = fieldFunction.function.andThen(
				function
			).apply(
				t
			);

			assertThat(u, is(values.get(i)));
		}
	}

	/**
	 * Tests that a {@code List<FieldFunction<T, S>>} has a key list equal to
	 * the provided key list, and a value list (obtained by applying {@code t}
	 * to each function) equal to the provided value list.
	 *
	 * @param  t the {@code t} instance
	 * @param  list the list to test
	 * @param  keys the expected key list
	 * @param  values the expected value list
	 * @review
	 */
	public static <T, S> void testFields(
		T t, List<FieldFunction<T, S>> list, List<String> keys,
		List<S> values) {

		testFields(t, list, Function.identity(), keys, values);
	}

	/**
	 * Tests a {@code RelatedModel} with the provided {@link Dummy}, key,
	 * identifier class, and ID.
	 *
	 * @param relatedModel the related model
	 * @param dummy the dummy instance
	 * @param key the related model's key
	 * @param identifierClass the related model's identifier class
	 * @param value the value of the related model's ID
	 */
	public static void testRelatedModel(
		RelatedModel<Dummy, ?> relatedModel, Dummy dummy, String key,
		Class<? extends Identifier> identifierClass, Integer value) {

		assertThat(relatedModel.getKey(), is(key));
		assertThat(relatedModel.getIdentifierClass(), is(identifierClass));

		Function<Dummy, ?> function = relatedModel.getIdentifierFunction();

		Integer id = function.andThen(
			Integer.class::cast
		).apply(
			dummy
		);

		assertThat(id, is(value));
	}

}
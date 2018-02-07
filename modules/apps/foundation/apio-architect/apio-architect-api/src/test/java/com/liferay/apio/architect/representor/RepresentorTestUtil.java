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

import static java.util.function.Function.identity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.related.RelatedModel;
import com.liferay.apio.architect.representor.dummy.Dummy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides utilities for testing the {@code Representor} class.
 *
 * @author Alejandro Hernández
 */
public class RepresentorTestUtil {

	/**
	 * Tests that a {@code Map<String, Function<T, S>>} has a key list equals to
	 * the provided key list and a value list (obtained from applying {@code t}
	 * to each actual value), equals to the provided value list.
	 *
	 * @param  t the t instance
	 * @param  map the map to test
	 * @param  keyList the expected key list
	 * @param  valueList the expected value list
	 * @review
	 */
	public static <T, S> void testFieldFunctions(
		T t, Map<String, ? extends Function<T, S>> map, List<String> keyList,
		List<S> valueList) {

		testFields(t, map, identity(), keyList, valueList);
	}

	/**
	 * Tests that a {@code Map<String, Function<T, S>>} has a key list equals to
	 * the provided key list and a value list (obtained from applying {@code t}
	 * to each actual value and transformed with the provided function), equals
	 * to the provided value list.
	 *
	 * @param  t the t instance
	 * @param  map the map to test
	 * @param  function the function to transform the map's values
	 * @param  keyList the expected key list
	 * @param  valueList the expected value list
	 * @review
	 */
	public static <T, S, U> void testFields(
		T t, Map<String, ? extends Function<T, S>> map, Function<S, U> function,
		List<String> keyList, List<U> valueList) {

		testMap(
			map,
			f -> f.andThen(
				function
			).apply(
				t
			),
			keyList, valueList);
	}

	/**
	 * Tests that a {@code Map<String, U>} has a key list equals to the provided
	 * key list and a value list (transformed with the provided function),
	 * equals to the provided value list.
	 *
	 * @param  map the map to test
	 * @param  function the function to transform the map's values
	 * @param  keyList the expected key list
	 * @param  valueList the expected value list
	 * @review
	 */
	public static <T, S> void testMap(
		Map<String, T> map, Function<T, S> function, List<String> keyList,
		List<S> valueList) {

		assertThat(map.keySet(), contains(keyList.toArray()));

		Collection<T> values = map.values();

		Stream<T> stream = values.stream();

		List<S> list = stream.map(
			function
		).collect(
			Collectors.toList()
		);

		assertThat(list, contains(valueList.toArray()));
	}

	/**
	 * Test a {@code RelatedModel} with the information provided.
	 *
	 * @param  relatedModel the related model
	 * @param  dummy the dummy instance
	 * @param  key the key that the related model should have
	 * @param  identifierClass the identifier class that the related model
	 *         should have
	 * @param  value the value of the ID that the related model should have
	 * @review
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
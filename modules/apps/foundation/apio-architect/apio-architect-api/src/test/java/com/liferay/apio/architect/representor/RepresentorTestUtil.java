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

import static java.nio.charset.StandardCharsets.UTF_8;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.language.Language;
import com.liferay.apio.architect.related.RelatedModel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
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
	 * @param  modelClass the model class that the related model should have
	 * @param  value the value of the ID that the related model should have
	 * @review
	 */
	public static void testRelatedModel(
		RelatedModel<Dummy, ?> relatedModel, Dummy dummy, String key,
		Class<? extends DummyIdentified> modelClass, Integer value) {

		assertThat(relatedModel.getKey(), is(key));
		assertThat(relatedModel.getModelClass(), is(modelClass));

		Function<Dummy, ? extends Optional<?>> function =
			relatedModel.getModelFunction();

		DummyIdentified dummyIdentified = function.apply(
			dummy
		).map(
			object -> (DummyIdentified)object
		).get();

		assertThat(dummyIdentified.getId(), is(value));
	}

	/**
	 * Dummy class that can be used to {@link Representor}.
	 *
	 * @review
	 */
	public static class Dummy implements DummyIdentified {

		public Dummy(int id) {
			_id = id;
		}

		public Optional<DummyLinked> getDummyLinked1Optional() {
			return Optional.of(new DummyLinked(3));
		}

		public Optional<DummyLinked> getDummyLinked2Optional() {
			return Optional.of(new DummyLinked(4));
		}

		public Optional<DummyParent> getDummyParent1Optional() {
			return Optional.of(new DummyParent(1));
		}

		public Optional<DummyParent> getDummyParent2Optional() {
			return Optional.of(new DummyParent(2));
		}

		@Override
		public int getId() {
			return _id;
		}

		public String getLocalizedString1(Language language) {
			Locale locale = language.getPreferredLocale();

			return locale.getLanguage() + "1";
		}

		public String getLocalizedString2(Language language) {
			Locale locale = language.getPreferredLocale();

			return locale.getLanguage() + "2";
		}

		public String getString1() {
			return "String 1";
		}

		public String getString2() {
			return "String 2";
		}

		public Boolean boolean1 = true;
		public Boolean boolean2 = false;
		public List<Boolean> booleanList1 = asList(true, false, false, true);
		public List<Boolean> booleanList2 = asList(false, false, true, false);
		public final Date date1 = new Date(1465981200000L);
		public final Date date2 = new Date(1491244560000L);
		public final InputStream inputStream1 = new ByteArrayInputStream(
			"Input Stream 1".getBytes(UTF_8));
		public final InputStream inputStream2 = new ByteArrayInputStream(
			"Input Stream 2".getBytes(UTF_8));
		public final Number number1 = 1L;
		public final Number number2 = 2L;
		public List<Number> numberList1 = asList(1, 2, 3, 4, 5);
		public List<Number> numberList2 = asList(6, 7, 8, 9, 10);
		public List<String> stringList1 = asList("a", "b", "c", "d");
		public List<String> stringList2 = asList("e", "f", "g", "h");

		private final int _id;

	}

	/**
	 * Instances of this class can be used to represent a dummy model's related
	 * model.
	 *
	 * @review
	 */
	public static class DummyLinked implements DummyIdentified {

		public DummyLinked(int id) {
			_id = id;
		}

		@Override
		public int getId() {
			return _id;
		}

		private final int _id;

	}

	/**
	 * Instances of this class can be used to represent a dummy model's parent.
	 *
	 * @review
	 */
	public static class DummyParent implements DummyIdentified {

		public DummyParent(int id) {
			_id = id;
		}

		@Override
		public int getId() {
			return _id;
		}

		private final int _id;

	}

	/**
	 * Instances of this interface can be identified with an {@code Integer} via
	 * {@link #getId()} method.
	 *
	 * @review
	 */
	public interface DummyIdentified {

		public int getId();

	}

}
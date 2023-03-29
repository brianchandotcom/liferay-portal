/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.util;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Matthew Kong
 */
public class StreamUtil {

	public static <T, R> List<R> toList(List<T> values, Function<T, R> mapper) {
		Stream<T> stream = values.stream();

		return stream.map(
			mapper
		).collect(
			Collectors.toList()
		);
	}

	public static <T, R> List<R> toList(
		List<T> values, Predicate<T> filter, Function<T, R> mapper) {

		Stream<T> stream = values.stream();

		return stream.filter(
			filter
		).map(
			mapper
		).collect(
			Collectors.toList()
		);
	}

	public static <T, R> Map<String, R> toMap(
		List<T> values, Function<T, String> keyMapper,
		Function<T, R> valueMapper) {

		Stream<T> stream = values.stream();

		return stream.collect(
			Collectors.toMap(keyMapper, valueMapper, (key1, key2) -> key1));
	}

	public static <T, R> Map<String, R> toMap(
		List<T> values, Predicate<T> filter, Function<T, String> keyMapper,
		Function<T, R> valueMapper) {

		Stream<T> stream = values.stream();

		return stream.filter(
			filter
		).collect(
			Collectors.toMap(keyMapper, valueMapper, (key1, key2) -> key1)
		);
	}

}
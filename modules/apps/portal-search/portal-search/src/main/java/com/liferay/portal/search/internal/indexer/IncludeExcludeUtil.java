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

package com.liferay.portal.search.internal.indexer;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class IncludeExcludeUtil {

	public static <T> Stream<T> stream(
		Stream<T> stream, Collection<String> includeIds,
		Collection<String> excludeIds, Function<T, String> function) {

		return exclude(
			include(stream, includeIds, function), excludeIds, function);
	}

	protected static <T> Stream<T> exclude(
		Stream<T> stream, Collection<String> ids,
		Function<T, String> function) {

		return filter(stream, ids, t -> !isPresent(t, ids, function));
	}

	protected static <T> Stream<T> filter(
		Stream<T> stream, Collection<String> ids,
		Predicate<? super T> predicate) {

		if ((ids == null) || ids.isEmpty()) {
			return stream;
		}

		return stream.filter(predicate);
	}

	protected static <T> Stream<T> include(
		Stream<T> stream, Collection<String> ids,
		Function<T, String> function) {

		return filter(stream, ids, t -> isPresent(t, ids, function));
	}

	protected static <T> boolean isPresent(
		T t, Collection<String> ids, Function<T, String> function) {

		return ids.contains(function.apply(t));
	}

}
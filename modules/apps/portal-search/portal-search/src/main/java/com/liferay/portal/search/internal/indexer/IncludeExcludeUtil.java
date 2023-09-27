/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author André de Oliveira
 */
public class IncludeExcludeUtil {

	public static <T> List<T> filter(
		List<T> list, Collection<String> includeIds,
		Collection<String> excludeIds, Function<T, String> function) {

		return _exclude(
			_include(list, includeIds, function), excludeIds, function);
	}

	protected static <T> boolean isPresent(
		T t, Collection<String> ids, Function<T, String> function) {

		return ids.contains(function.apply(t));
	}

	private static <T> List<T> _exclude(
		List<T> list, Collection<String> ids, Function<T, String> function) {

		return _filter(list, ids, t -> !isPresent(t, ids, function));
	}

	private static <T> List<T> _filter(
		List<T> list, Collection<String> ids, Predicate<? super T> predicate) {

		if ((ids == null) || ids.isEmpty()) {
			return list;
		}

		list.removeIf(cur -> !predicate.test(cur));

		return list;
	}

	private static <T> List<T> _include(
		List<T> list, Collection<String> ids, Function<T, String> function) {

		return _filter(list, ids, t -> isPresent(t, ids, function));
	}

}
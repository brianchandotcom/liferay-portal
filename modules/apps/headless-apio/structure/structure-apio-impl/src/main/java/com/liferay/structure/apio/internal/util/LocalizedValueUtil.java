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

package com.liferay.structure.apio.internal.util;

import com.liferay.apio.architect.functional.Try;
import com.liferay.dynamic.data.mapping.model.Value;

import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Paulo Cruz
 */
public final class LocalizedValueUtil {

	public static <T> BiFunction<T, Locale, String> getLocalizedString(
		Function<T, Value> function) {

		return (value, locale) -> Try.fromFallible(
			() -> function.apply(value)
		).toOptional(
		).map(
			localizedValue -> localizedValue.getString(locale)
		).orElse(
			null
		);
	}

}
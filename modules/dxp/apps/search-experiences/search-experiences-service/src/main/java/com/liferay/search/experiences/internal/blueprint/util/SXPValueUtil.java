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

package com.liferay.search.experiences.internal.blueprint.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public class SXPValueUtil {

	public static String mapToString(Map<?, ?> map) {
		Set<?> set = map.keySet();

		Stream<?> stream = set.stream();

		return stream.map(
			key -> key + "=" + map.get(key)
		).collect(
			Collectors.joining(", ", "[", "]")
		);
	}

	public static Optional<Double> stringToDoubleOptional(String s) {
		if (Validator.isNull(s)) {
			return Optional.empty();
		}

		try {
			double value = Double.valueOf(s);

			return Optional.of(value);
		}
		catch (NumberFormatException numberFormatException) {
			_log.error(
				numberFormatException.getMessage(), numberFormatException);
		}

		return Optional.empty();
	}

	public static Optional<Float> stringToFloatOptional(String s) {
		if (Validator.isNull(s)) {
			return Optional.empty();
		}

		try {
			float value = Float.valueOf(s);

			return Optional.of(value);
		}
		catch (NumberFormatException numberFormatException) {
			_log.error(
				numberFormatException.getMessage(), numberFormatException);
		}

		return Optional.empty();
	}

	public static Optional<Integer> stringToIntegerOptional(String s) {
		if (Validator.isNull(s)) {
			return Optional.empty();
		}

		try {
			int value = Integer.valueOf(s);

			return Optional.of(value);
		}
		catch (NumberFormatException numberFormatException) {
			_log.error(
				numberFormatException.getMessage(), numberFormatException);
		}

		return Optional.empty();
	}

	public static Optional<Long> stringToLongOptional(String s) {
		if (Validator.isNull(s)) {
			return Optional.empty();
		}

		try {
			long value = Long.valueOf(s);

			return Optional.of(value);
		}
		catch (NumberFormatException numberFormatException) {
			_log.error(
				numberFormatException.getMessage(), numberFormatException);
		}

		return Optional.empty();
	}

	public static Optional<String> toStringOptional(String s) {
		s = StringUtil.trim(s);

		if (Validator.isBlank(s)) {
			return Optional.empty();
		}

		return Optional.of(s);
	}

	private static final Log _log = LogFactoryUtil.getLog(SXPValueUtil.class);

}
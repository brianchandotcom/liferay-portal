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

package com.liferay.portal.search.similar.results.web.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class SearchStringUtil {

	public static Optional<String> maybe(String s) {
		s = StringUtil.trim(s);

		if (Validator.isBlank(s)) {
			return Optional.empty();
		}

		return Optional.of(s);
	}

	public static String requireEquals(String expected, String actual) {
		if (!Objects.equals(expected, actual)) {
			throw new RuntimeException(actual + " != " + expected);
		}

		return actual;
	}

	public static String requireStartsWith(String expected, String actual) {
		if (!StringUtil.startsWith(actual, expected)) {
			throw new RuntimeException(actual + " /= " + expected);
		}

		return actual;
	}

	public static String[] splitAndUnquote(Optional<String> optional) {
		return optional.map(
			SearchStringUtil::splitAndUnquote
		).orElse(
			new String[0]
		);
	}

	public static String[] splitAndUnquote(String s) {
		return Stream.of(
			StringUtil.split(s.trim(), CharPool.COMMA)
		).map(
			String::trim
		).map(
			StringUtil::unquote
		).toArray(
			String[]::new
		);
	}

}
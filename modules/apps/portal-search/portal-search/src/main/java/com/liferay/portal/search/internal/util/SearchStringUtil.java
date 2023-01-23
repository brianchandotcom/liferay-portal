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

package com.liferay.portal.search.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author André de Oliveira
 */
public class SearchStringUtil {

	public static String maybe(String string) {
		string = StringUtil.trim(string);

		if (Validator.isBlank(string)) {
			return null;
		}

		return string;
	}

	public static String requireEquals(String expected, String actual) {
		if (!Objects.equals(expected, actual)) {
			throw new RuntimeException(actual + " != " + expected);
		}

		return actual;
	}

	public static String[] splitAndUnquote(String string) {
		if (string == null) {
			return new String[0];
		}

		List<String> list = new ArrayList<>();

		for (String part : StringUtil.split(string.trim(), CharPool.COMMA)) {
			list.add(StringUtil.unquote(part.trim()));
		}

		return list.toArray(new String[0]);
	}

}
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

package com.liferay.redirect.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class URLUtil {

	public static String[] splitURL(String url) {
		Set<String> terms = new HashSet<>();

		int pos = url.indexOf("://");

		if (pos == -1) {
			pos = 0;
		}
		else {
			pos += 3;
		}

		String[] parts = _pattern.split(url.substring(pos));

		Collections.addAll(terms, parts);

		if (terms.isEmpty()) {
			return new String[0];
		}

		String domain = parts[0];

		terms.addAll(StringUtil.split(domain, CharPool.PERIOD));

		pos = domain.length();

		while (pos != -1) {
			pos = domain.lastIndexOf(StringPool.PERIOD, pos - 1);

			if (pos > 0) {
				terms.add(domain.substring(pos + 1));
			}
		}

		return terms.toArray(new String[0]);
	}

	private static final Pattern _pattern = Pattern.compile("[/?=&]+");

}
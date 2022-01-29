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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Map;

/**
 * @author Peter Shin
 */
public class GradlePropertiesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				sb.append(_fixValue(line));
				sb.append("\n");
			}
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private final String _fixValue(String line) {
		String[] array = line.split(StringPool.EQUAL, 2);

		if (array.length != 2) {
			return line;
		}

		String regex = _keysRegexMap.get(StringUtil.trim(array[0]));

		if (regex == null) {
			return line;
		}

		String value = StringUtil.trim(array[1]);

		String strippedValue = StringUtil.removeChars(
			value, CharPool.APOSTROPHE, CharPool.QUOTE);

		if (strippedValue.matches(regex)) {
			return StringUtil.replaceLast(
				line, value, "\"" + strippedValue + "\"");
		}

		return line;
	}

	private static final Map<String, String> _keysRegexMap = MapUtil.fromArray(
		new String[] {
			"sourceCompatibility", "[0-9]+\\.[0-9]+", "targetCompatibility",
			"[0-9]+\\.[0-9]+"
		});

}
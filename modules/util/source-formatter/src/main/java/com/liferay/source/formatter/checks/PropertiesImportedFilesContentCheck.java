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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileReader;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Peter Shin
 */
public class PropertiesImportedFilesContentCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith("/imported-files.properties")) {
			return content;
		}

		return _checkClasses(absolutePath, content);
	}

	private String _checkClasses(String absolutePath, String content)
		throws Exception {

		Map<String, Set<String>> map = new TreeMap<>();

		try (FileReader fileReader = new FileReader(new File(absolutePath));
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(fileReader)) {

			String key = null;
			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = line.trim();

				if (Validator.isNull(line)) {
					continue;
				}

				if (line.indexOf('=') >= 0) {
					key = line.substring(0, line.indexOf('='));

					String value = line.substring(line.indexOf('=') + 1);

					if (!Objects.isNull(value) && !value.equals("\\")) {
						Set<String> set = map.get(key);

						if (set == null) {
							set = new TreeSet<>();
						}

						set.add(value);

						map.put(key, set);
					}
				}
				else {
					String value = line;

					if (value.endsWith(",\\")) {
						value = value.substring(0, value.length() - 2);
					}

					if (key == null) {
						return content;
					}

					Set<String> set = map.get(key);

					if (set == null) {
						set = new TreeSet<>();
					}

					set.add(value);

					map.put(key, set);
				}
			}
		}

		StringBundler sb = new StringBundler(map.size() * 4);

		for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=\\\n");
			sb.append(_merge(entry.getValue()));
			sb.append("\n\n");
		}

		return StringUtil.trim(sb.toString());
	}

	private String _merge(Set<String> classes) {
		StringBundler sb = new StringBundler(3 * classes.size());

		for (String s : classes) {
			sb.append(StringPool.FOUR_SPACES);
			sb.append(s);
			sb.append(",\\\n");
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

}
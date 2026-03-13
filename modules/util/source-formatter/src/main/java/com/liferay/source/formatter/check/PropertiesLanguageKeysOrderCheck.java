/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Peter Shin
 */
public class PropertiesLanguageKeysOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("/content/Language.properties")) {
			return content;
		}

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			Map<String, String> map = new TreeMap<>(
				new NaturalOrderStringComparator(true, true));

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				String[] array = line.split("=", 2);

				if (array.length > 1) {
					map.put(array[0], array[1]);

					continue;
				}

				sb.append(_merge(map));
				sb.append(line);
				sb.append("\n");
			}

			sb.append(_merge(map));
		}

		return StringUtil.trim(sb.toString());
	}

	private String _merge(Map<String, String> map) {
		StringBundler sb = new StringBundler(map.size() * 4);

		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(
				StringUtil.replace(
					entry.getKey(), new String[] {"–", "—"},
					new String[] {"-", "-"}));
			sb.append(StringPool.EQUAL);
			sb.append(entry.getValue());
			sb.append("\n");
		}

		if (!map.isEmpty()) {
			map.clear();
		}

		return sb.toString();
	}

}
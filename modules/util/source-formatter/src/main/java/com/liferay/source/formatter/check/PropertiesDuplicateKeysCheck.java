/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Huang
 */
public class PropertiesDuplicateKeysCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		List<String> propertyKeys = new ArrayList<>();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousLine = null;
			String propertyKey = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = line.trim();

				if (Validator.isBlank(line) ||
					line.startsWith(StringPool.POUND) ||
					((previousLine != null) && previousLine.endsWith("\\"))) {

					previousLine = line;

					continue;
				}

				int x = line.indexOf('=');

				if (x == -1) {
					previousLine = line;

					continue;
				}

				propertyKey = line.substring(0, x);

				if (propertyKey.equals("include-and-override")) {
					previousLine = line;

					continue;
				}

				if (propertyKeys.contains(propertyKey)) {
					addMessage(
						fileName,
						"Do not add duplicate property key \"" + propertyKey +
							"\"");
				}

				previousLine = line;

				propertyKeys.add(propertyKey);
			}
		}

		return content;
	}

}
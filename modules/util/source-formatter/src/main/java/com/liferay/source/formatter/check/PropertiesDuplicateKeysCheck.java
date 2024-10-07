/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.io.IOException;
import java.io.StringReader;

import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PropertiesDuplicateKeysCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		Enumeration<String> enumeration =
			(Enumeration<String>)properties.propertyNames();

		while (enumeration.hasMoreElements()) {
			int count = 0;

			String key = enumeration.nextElement();

			Pattern pattern = Pattern.compile(
				"^ *" + Pattern.quote(key) + " *=", Pattern.MULTILINE);

			Matcher matcher = pattern.matcher(content);

			while (matcher.find()) {
				count++;

				if (count == 2) {
					addMessage(
						fileName,
						"Do not add duplicate property key '" + key + "'");

					break;
				}
			}
		}

		return content;
	}

}
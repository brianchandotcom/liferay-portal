/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class PoshiPropertiesOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith(".testcase") || SourceUtil.isXML(content)) {
			return content;
		}

		Matcher matcher = _customPropertiesPattern.matcher(content);

		outerLoop:
		while (matcher.find()) {
			String[] properties = StringUtil.split(
				matcher.group(1), "${line.separator}");

			if (properties.length == 1) {
				continue;
			}

			Map<String, String> propertiesMap = new TreeMap<>(
				new NaturalOrderStringComparator());

			for (String property : properties) {
				int index = property.indexOf(StringPool.EQUAL);

				if (index == -1) {
					continue outerLoop;
				}

				propertiesMap.put(property.substring(0, index), property);
			}

			String newProperties = StringUtil.merge(
				propertiesMap.values(), "${line.separator}");

			if (!StringUtil.equals(matcher.group(1), newProperties)) {
				return StringUtil.replaceFirst(
					content, matcher.group(1), newProperties, matcher.start(1));
			}
		}

		return content;
	}

	private static final Pattern _customPropertiesPattern = Pattern.compile(
		"\t+property custom.properties = \"(.+)\"");

}
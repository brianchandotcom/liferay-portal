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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.comparator.PropertyValueComparator;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PropertiesMultiLineValuesOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		String shortFileName = fileName.substring(pos + 1);

		if (!shortFileName.matches(
				"(ci|compatibility|system(-ext)?|test)\\.properties")) {

			return content;
		}

		return _sortValues(content);
	}

	private String _sortValues(String content) throws IOException {
		Matcher matcher1 = _keyValuesPattern.matcher(content);

		while (matcher1.find()) {
			String match = matcher1.group();

			Matcher matcher2 = _multiLineValuesPattern.matcher(match);

			while (matcher2.find()) {
				String originalValues = matcher2.group();

				String s = originalValues;

				if (originalValues.endsWith("\\") &&
					!originalValues.endsWith(",\\")) {

					s = StringUtil.replaceLast(
						s, CharPool.BACK_SLASH, StringPool.BLANK);
				}

				List<String> valuesList = ListUtil.fromArray(s.split(",\\\\"));

				if (StringUtil.count(originalValues, "\n") !=
						valuesList.size()) {

					break;
				}

				Collections.sort(valuesList, new PropertyValueComparator());

				String newValues = _splitValues(originalValues, valuesList);

				if (!originalValues.equals(newValues)) {
					return StringUtil.replaceFirst(
						content, originalValues, newValues, matcher1.start());
				}
			}
		}

		return content;
	}

	private String _splitValues(
		String originalValues, List<String> valuesList) {

		StringBundler sb = new StringBundler(valuesList.size() * 2);

		for (String value : valuesList) {
			sb.append(value);
			sb.append(",\\");
		}

		if (!originalValues.endsWith(",\\")) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private static final Pattern _keyValuesPattern = Pattern.compile(
		"(\n|\\A)( *).+=\\\\(\n\\2    .+){2,}");
	private static final Pattern _multiLineValuesPattern = Pattern.compile(
		"(\n +(?![\\\\# ]).*){2,}");

}
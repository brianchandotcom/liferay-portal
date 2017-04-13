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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class CSSPropertiesOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _sortProperties(content);
	}

	private String _sortProperties(String content) {
		Matcher matcher = _propertiesPattern.matcher(content);

		while (matcher.find()) {
			String parameters = StringUtil.trimTrailing(matcher.group());

			String newParameters = StringUtil.removeChar(
				parameters, CharPool.TAB);

			List<String> parameterList = ListUtil.fromArray(
				StringUtil.splitLines(newParameters));

			Collections.sort(parameterList, new PropertyComparator());

			String tabs = matcher.group(2);

			StringBundler sb = new StringBundler(parameterList.size() * 3);

			for (String parameter : parameterList) {
				sb.append(tabs);
				sb.append(parameter);
				sb.append("\n");
			}

			newParameters = sb.toString();

			newParameters = newParameters.substring(
				0, newParameters.length() - 1);

			content = StringUtil.replaceFirst(
				content, parameters, newParameters, matcher.start() - 1);
		}

		return content;
	}

	private final Pattern _propertiesPattern = Pattern.compile(
		"(^(\t*)[a-z]\\S*: .+;\n)+", Pattern.MULTILINE);

	private class PropertyComparator extends NaturalOrderStringComparator {

		@Override
		public int compare(String s1, String s2) {
			int pos1 = s1.indexOf(CharPool.COLON);
			int pos2 = s2.indexOf(CharPool.COLON);

			return super.compare(s1.substring(0, pos1), s2.substring(0, pos2));
		}

	}

}
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

import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.ParseException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class LocaleUtilCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException, ParseException {

		if (isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES, absolutePath)) {
			return content;
		}

		Matcher matcher = _localePattern.matcher(content);

		while (matcher.find()) {
			List<String> localeUtilTermNames = _getLocaleUtilTermNames(
				absolutePath);

			if (localeUtilTermNames.contains(matcher.group(1))) {
				addMessage(
					fileName, "Use 'LocaleUtil." + matcher.group(1) + "'",
					getLineNumber(content, matcher.start()));
			}
		}

		return content;
	}

	private synchronized List<String> _getLocaleUtilTermNames(
			String absolutePath)
		throws IOException, ParseException {

		if (_localeUtilTermNames != null) {
			return _localeUtilTermNames;
		}

		_localeUtilTermNames = new ArrayList<>();

		String localeUtilClassContent = getPortalContent(
			_LOCALE_UTIL_FILE_NAME, absolutePath);

		if (localeUtilClassContent == null) {
			return _localeUtilTermNames;
		}

		JavaClass javaClass = JavaClassParser.parseJavaClass(
			_LOCALE_UTIL_FILE_NAME, localeUtilClassContent);

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (javaTerm.getAccessModifier() ==
					JavaTerm.ACCESS_MODIFIER_PUBLIC) {

				_localeUtilTermNames.add(javaTerm.getName());
			}
		}

		return _localeUtilTermNames;
	}

	private static final String _LOCALE_UTIL_FILE_NAME =
		"portal-kernel/src/com/liferay/portal/kernel/util/LocaleUtil.java";

	private static final Pattern _localePattern = Pattern.compile(
		"\\WLocale\\.(\\w+)\\W");

	private List<String> _localeUtilTermNames;

}
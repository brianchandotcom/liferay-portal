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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.SourceFormatterMessage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaPackagePathCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (hasGeneratedTag(content)) {
			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		String packagePath = StringPool.BLANK;

		Matcher matcher = _packagePattern.matcher(content);

		if (matcher.find()) {
			packagePath = matcher.group(2);
		}

		_checkPackagePath(sourceFormatterMessages, fileName, packagePath);

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkPackagePath(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		String packagePath) {

		if (Validator.isNull(packagePath)) {
			addMessage(sourceFormatterMessages, fileName, "Missing package");

			return;
		}

		int pos = fileName.lastIndexOf(CharPool.SLASH);

		String filePath = StringUtil.replace(
			fileName.substring(0, pos), CharPool.SLASH, CharPool.PERIOD);

		if (!filePath.endsWith(packagePath)) {
			addMessage(
				sourceFormatterMessages, fileName,
				"The declared package '" + packagePath +
					"' does not match the expected package",
				"package.markdown");

			return;
		}

		if (packagePath.matches(".*\\.internal\\.([\\w.]+\\.)?impl")) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Do not use 'impl' inside 'internal', see LPS-70113");
		}
	}

	private final Pattern _packagePattern = Pattern.compile(
		"(\n|^)\\s*package (.*);\n");

}
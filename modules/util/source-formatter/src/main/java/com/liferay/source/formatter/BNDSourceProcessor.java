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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.BNDBundleNameCheck;
import com.liferay.source.formatter.checks.BNDCapabilityCheck;
import com.liferay.source.formatter.checks.BNDDefinitionKeysCheck;
import com.liferay.source.formatter.checks.BNDDirectoryNameCheck;
import com.liferay.source.formatter.checks.BNDExportsCheck;
import com.liferay.source.formatter.checks.BNDImportsCheck;
import com.liferay.source.formatter.checks.BNDIncludeResourceCheck;
import com.liferay.source.formatter.checks.BNDLineBreaksCheck;
import com.liferay.source.formatter.checks.BNDRangeCheck;
import com.liferay.source.formatter.checks.BNDSchemaVersionCheck;
import com.liferay.source.formatter.checks.BNDWebContextPathCheck;
import com.liferay.source.formatter.checks.BNDWhitespaceCheck;
import com.liferay.source.formatter.checks.FileCheck;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		content = StringUtil.replace(
			content, new String[] {"/\n", "/,\\\n"},
			new String[] {"\n", ",\\\n"});

		content = StringUtil.replace(content, " \\\n", "\\\n");

		content = StringUtil.replaceFirst(
			content, "Conditional-Package:", "-conditionalpackage:");

		Matcher matcher = _trailingSemiColonPattern.matcher(content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, StringPool.SEMICOLON, StringPool.BLANK,
				matcher.start());
		}

		matcher = _incorrectTabPattern.matcher(content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.TAB, matcher.start());
		}

		matcher = _singleValueOnMultipleLinesPattern.matcher(content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.SPACE, matcher.start());
		}

		return content;
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<FileCheck> getFileChecks() {
		return _fileChecks;
	}

	@Override
	protected void populateFileChecks() {
		_fileChecks.add(new BNDWhitespaceCheck());

		_fileChecks.add(new BNDCapabilityCheck());
		_fileChecks.add(new BNDDefinitionKeysCheck());
		_fileChecks.add(new BNDImportsCheck());
		_fileChecks.add(new BNDLineBreaksCheck());
		_fileChecks.add(new BNDRangeCheck());
		_fileChecks.add(new BNDSchemaVersionCheck());
	}

	@Override
	protected void populateModuleFileChecks() throws Exception {
		_fileChecks.add(new BNDBundleNameCheck(subrepository));
		_fileChecks.add(new BNDDirectoryNameCheck(subrepository));
		_fileChecks.add(new BNDExportsCheck(subrepository));
		_fileChecks.add(new BNDIncludeResourceCheck(subrepository));
		_fileChecks.add(new BNDWebContextPathCheck(subrepository));
	}

	private static final String[] _INCLUDES = new String[] {"**/*.bnd"};

	private final List<FileCheck> _fileChecks = new ArrayList<>();
	private final Pattern _incorrectTabPattern = Pattern.compile(
		"\n[^\t].*:\\\\\n(\t{2,})[^\t]");
	private final Pattern _singleValueOnMultipleLinesPattern = Pattern.compile(
		"\n.*:(\\\\\n\t).*(\n[^\t]|\\Z)");
	private final Pattern _trailingSemiColonPattern = Pattern.compile(
		";(\n|\\Z)");

}
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

import aQute.bnd.osgi.Constants;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.BNDBundleNameCheck;
import com.liferay.source.formatter.checks.BNDCapabilityCheck;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

		return sortDefinitions(fileName, content, new DefinitionComparator());
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
	protected String formatDefinitionKey(
		String fileName, String content, String definitionKey) {

		Map<String, String> generalDefinitionKeysMap = getDefinitionKeysMap();

		String lowerCaseDefinitionKey = StringUtil.toLowerCase(definitionKey);

		String correctKey = generalDefinitionKeysMap.get(
			lowerCaseDefinitionKey);

		if (correctKey == null) {
			int pos = fileName.lastIndexOf(StringPool.SLASH);

			String shortFileName = fileName.substring(pos + 1);

			Map<String, Map<String, String>> fileSpecificDefinitionKeysMap =
				getFileSpecificDefinitionKeysMap();

			Map<String, String> definitionKeysMap =
				fileSpecificDefinitionKeysMap.get(shortFileName);

			if (definitionKeysMap != null) {
				correctKey = definitionKeysMap.get(lowerCaseDefinitionKey);
			}
		}

		if (correctKey == null) {
			processMessage(fileName, "Unknown key \"" + definitionKey + "\"");

			return content;
		}

		if (correctKey.equals(definitionKey)) {
			return StringUtil.replace(
				content, definitionKey + "=", definitionKey + ":");
		}

		if (content.startsWith(definitionKey)) {
			return StringUtil.replaceFirst(content, definitionKey, correctKey);
		}

		return StringUtil.replace(
			content, "\n" + definitionKey + ":", "\n" + correctKey + ":");
	}

	protected Map<String, String> getDefinitionKeysMap() {
		if (_definitionKeysMap != null) {
			return _definitionKeysMap;
		}

		Map<String, String> definitionKeysMap = new HashMap<>();

		definitionKeysMap = populateDefinitionKeysMap(
			ArrayUtil.append(
				Constants.BUNDLE_SPECIFIC_HEADERS, Constants.headers,
				Constants.options));

		_definitionKeysMap = definitionKeysMap;

		return _definitionKeysMap;
	}

	protected String getDefinitionValue(String content, String key) {
		Pattern pattern = Pattern.compile(
			"^" + key + ": (.*)(\n|\\Z)", Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	@Override
	protected List<FileCheck> getFileChecks() {
		return _fileChecks;
	}

	protected Map<String, Map<String, String>>
		getFileSpecificDefinitionKeysMap() {

		if (_fileSpecificDefinitionKeysMap != null) {
			return _fileSpecificDefinitionKeysMap;
		}

		Map<String, Map<String, String>> fileSpecificDefinitionKeysMap =
			new HashMap<>();

		fileSpecificDefinitionKeysMap.put(
			"app.bnd",
			populateDefinitionKeysMap(
				"Liferay-Releng-App-Description", "Liferay-Releng-App-Title",
				"Liferay-Releng-Bundle", "Liferay-Releng-Category",
				"Liferay-Releng-Demo-Url", "Liferay-Releng-Deprecated",
				"Liferay-Releng-Labs", "Liferay-Releng-Marketplace",
				"Liferay-Releng-Portal-Required", "Liferay-Releng-Public",
				"Liferay-Releng-Restart-Required", "Liferay-Releng-Support-Url",
				"Liferay-Releng-Supported"));
		fileSpecificDefinitionKeysMap.put(
			"bnd.bnd",
			populateDefinitionKeysMap(
				"-metatype-inherit", "Can-Redefine-Classes",
				"Can-Retransform-Classes", "Implementation-Version",
				"JPM-Command", "Liferay-Configuration-Path",
				"Liferay-Export-JS-Submodules", "Liferay-JS-Config",
				"Liferay-Releng-App-Description",
				"Liferay-Releng-Module-Group-Description",
				"Liferay-Releng-Module-Group-Title",
				"Liferay-Require-SchemaVersion", "Liferay-Service",
				"Liferay-Theme-Contributor-Type",
				"Liferay-Theme-Contributor-Weight", "Main-Class",
				"Premain-Class", "Web-ContextPath"));
		fileSpecificDefinitionKeysMap.put(
			"common.bnd",
			populateDefinitionKeysMap(
				"Git-Descriptor", "Git-SHA", "Javac-Compiler", "Javac-Debug",
				"Javac-Deprecation", "Javac-Encoding",
				"Liferay-Portal-Build-Date", "Liferay-Portal-Build-Number",
				"Liferay-Portal-Build-Time", "Liferay-Portal-Code-Name",
				"Liferay-Portal-Parent-Build-Number",
				"Liferay-Portal-Release-Info", "Liferay-Portal-Server-Info",
				"Liferay-Portal-Version"));

		_fileSpecificDefinitionKeysMap = fileSpecificDefinitionKeysMap;

		return _fileSpecificDefinitionKeysMap;
	}

	protected Map<String, String> populateDefinitionKeysMap(String... keys) {
		Map<String, String> definitionKeysMap = new HashMap<>();

		for (String key : keys) {
			definitionKeysMap.put(StringUtil.toLowerCase(key), key);
		}

		return definitionKeysMap;
	}

	@Override
	protected void populateFileChecks() {
		_fileChecks.add(new BNDWhitespaceCheck());

		_fileChecks.add(new BNDCapabilityCheck());
		_fileChecks.add(new BNDImportsCheck());
		_fileChecks.add(new BNDLineBreaksCheck());
		_fileChecks.add(new BNDRangeCheck());
		_fileChecks.add(new BNDSchemaVersionCheck());
		_fileChecks.add(new BNDWebContextPathCheck());
	}

	@Override
	protected void populateModuleFileChecks() throws Exception {
		_fileChecks.add(new BNDBundleNameCheck());
		_fileChecks.add(new BNDDirectoryNameCheck());
		_fileChecks.add(new BNDExportsCheck());
		_fileChecks.add(new BNDIncludeResourceCheck());
	}

	private static final String[] _INCLUDES = new String[] {"**/*.bnd"};

	private Map<String, String> _definitionKeysMap;
	private final List<FileCheck> _fileChecks = new ArrayList<>();
	private Map<String, Map<String, String>> _fileSpecificDefinitionKeysMap;
	private final Pattern _incorrectTabPattern = Pattern.compile(
		"\n[^\t].*:\\\\\n(\t{2,})[^\t]");
	private final Pattern _singleValueOnMultipleLinesPattern = Pattern.compile(
		"\n.*:(\\\\\n\t).*(\n[^\t]|\\Z)");
	private final Pattern _trailingSemiColonPattern = Pattern.compile(
		";(\n|\\Z)");

	private static class DefinitionComparator implements Comparator<String> {

		@Override
		public int compare(String definition1, String definition2) {
			if (definition1.startsWith(StringPool.DASH) ^
				definition2.startsWith(StringPool.DASH)) {

				return -definition1.compareTo(definition2);
			}

			return definition1.compareTo(definition2);
		}

	}

}
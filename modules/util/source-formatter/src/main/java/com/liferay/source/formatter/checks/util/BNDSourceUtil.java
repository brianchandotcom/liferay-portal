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

package com.liferay.source.formatter.checks.util;

import aQute.bnd.osgi.Constants;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDSourceUtil {

	public static Map<String, String> getDefinitionKeysMap() {
		return _populateDefinitionKeysMap(
			ArrayUtil.append(
				Constants.BUNDLE_SPECIFIC_HEADERS, Constants.headers,
				Constants.options));
	}

	public static String getDefinitionValue(String content, String key) {
		Pattern pattern = Pattern.compile(
			"^" + key + ": (.*)(\n|\\Z)", Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	public static List<String> getDefinitionValues(String content, String key) {
		List<String> definitionValues = new ArrayList<>();

		if (!content.contains(key + ":")) {
			return definitionValues;
		}

		String definitionValue = getDefinitionValue(content, key);

		if (definitionValue != null) {
			definitionValues.add(definitionValue);

			return definitionValues;
		}

		int x = content.indexOf(key + ":\\\n");

		if (x == -1) {
			return definitionValues;
		}

		int lineNumber = SourceUtil.getLineNumber(content, x);

		for (int i = lineNumber + 1;; i++) {
			String line = StringUtil.trim(SourceUtil.getLine(content, i));

			if (line.endsWith(",\\")) {
				definitionValues.add(
					StringUtil.replaceLast(line, ",\\", StringPool.BLANK));
			}
			else if (!line.endsWith(StringPool.BACK_SLASH)) {
				definitionValues.add(line);

				return definitionValues;
			}
		}
	}

	public static Map<String, Map<String, String>>
		getFileSpecificDefinitionKeysMap() {

		Map<String, Map<String, String>> fileSpecificDefinitionKeysMap =
			new HashMap<>();

		fileSpecificDefinitionKeysMap.put(
			"app.bnd", _populateDefinitionKeysMap(_APP_BND_DEFINITION_KEYS));
		fileSpecificDefinitionKeysMap.put(
			"bnd.bnd", _populateDefinitionKeysMap(_BND_BND_DEFINITION_KEYS));
		fileSpecificDefinitionKeysMap.put(
			"common.bnd",
			_populateDefinitionKeysMap(_COMMON_BND_DEFINITION_KEYS));
		fileSpecificDefinitionKeysMap.put(
			"subsystem.bnd",
			_populateDefinitionKeysMap(_SUBSYSTEM_BND_DEFINITION_KEYS));
		fileSpecificDefinitionKeysMap.put(
			"suite.bnd",
			_populateDefinitionKeysMap(_SUITE_BND_DEFINITION_KEYS));

		return fileSpecificDefinitionKeysMap;
	}

	public static String getModuleName(String absolutePath) {
		int x = absolutePath.lastIndexOf(StringPool.SLASH);

		int y = absolutePath.lastIndexOf(StringPool.SLASH, x - 1);

		return absolutePath.substring(y + 1, x);
	}

	public static String updateInstruction(
		String content, String header, String value) {

		String instruction = header + StringPool.COLON;

		if (Validator.isNotNull(value)) {
			instruction = instruction + StringPool.SPACE + value;
		}

		if (!content.contains(header)) {
			return content + StringPool.NEW_LINE + instruction;
		}

		String[] lines = StringUtil.splitLines(content);

		for (String line : lines) {
			if (line.contains(header)) {
				content = StringUtil.replaceFirst(content, line, instruction);
			}
		}

		return content;
	}

	private static Map<String, String> _populateDefinitionKeysMap(
		String[] keys) {

		Map<String, String> definitionKeysMap = new HashMap<>();

		for (String key : keys) {
			definitionKeysMap.put(StringUtil.toLowerCase(key), key);
		}

		return definitionKeysMap;
	}

	private static final String[] _APP_BND_DEFINITION_KEYS = {
		"Liferay-Releng-App-Description", "Liferay-Releng-App-Title",
		"Liferay-Releng-Bundle", "Liferay-Releng-Category",
		"Liferay-Releng-Demo-Url", "Liferay-Releng-Deprecated",
		"Liferay-Releng-Fix-Delivery-Method", "Liferay-Releng-Labs",
		"Liferay-Releng-Marketplace", "Liferay-Releng-Portal-Required",
		"Liferay-Releng-Public", "Liferay-Releng-Restart-Required",
		"Liferay-Releng-Suite", "Liferay-Releng-Support-Url",
		"Liferay-Releng-Supported"
	};

	private static final String[] _BND_BND_DEFINITION_KEYS = {
		"-jsp", "-liferay-aggregate-resource-bundles", "-metatype",
		"-metatype-inherit", "-sass", "Bundle-ActivationPolicy",
		"Can-Redefine-Classes", "Can-Retransform-Classes",
		"Eclipse-PlatformFilter", "Implementation-Version", "JPM-Command",
		"Liferay-Configuration-Path", "Liferay-JS-Config",
		"Liferay-JS-Resources-Top-Head-Authenticated",
		"Liferay-JS-Resources-Top-Head", "Liferay-JS-Submodules-Bridge",
		"Liferay-JS-Submodules-Export", "Liferay-Modules-Compat-Adapters",
		"Liferay-Releng-App-Description",
		"Liferay-Releng-Module-Group-Description",
		"Liferay-Releng-Module-Group-Title", "Liferay-Require-SchemaVersion",
		"Liferay-RTL-Support-Required", "Liferay-Service",
		"Liferay-Theme-Contributor-Type", "Liferay-Theme-Contributor-Weight",
		"Liferay-Versions", "Main-Class", "Premain-Class", "Web-ContextPath"
	};

	private static final String[] _COMMON_BND_DEFINITION_KEYS = {
		"Git-Descriptor", "Git-SHA", "Javac-Compiler", "Javac-Debug",
		"Javac-Deprecation", "Javac-Encoding", "Liferay-Portal-Build-Date",
		"Liferay-Portal-Build-Number", "Liferay-Portal-Build-Time",
		"Liferay-Portal-Code-Name", "Liferay-Portal-Parent-Build-Number",
		"Liferay-Portal-Release-Info", "Liferay-Portal-Server-Info",
		"Liferay-Portal-Version"
	};

	private static final String[] _SUBSYSTEM_BND_DEFINITION_KEYS = {
		"Liferay-Releng-Marketplace", "Liferay-Releng-Subsystem-Title"
	};

	private static final String[] _SUITE_BND_DEFINITION_KEYS = {
		"Liferay-Releng-Suite-Description", "Liferay-Releng-Suite-Title"
	};

}
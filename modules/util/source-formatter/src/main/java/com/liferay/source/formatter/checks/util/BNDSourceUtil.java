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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
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

		return fileSpecificDefinitionKeysMap;
	}

	public static String getModuleName(String absolutePath) {
		int x = absolutePath.lastIndexOf(StringPool.SLASH);

		int y = absolutePath.lastIndexOf(StringPool.SLASH, x - 1);

		return absolutePath.substring(y + 1, x);
	}

	private static Map<String, String> _populateDefinitionKeysMap(
		String[] keys) {

		Map<String, String> definitionKeysMap = new HashMap<>();

		for (String key : keys) {
			definitionKeysMap.put(StringUtil.toLowerCase(key), key);
		}

		return definitionKeysMap;
	}

	private static final String[] _APP_BND_DEFINITION_KEYS = new String[] {
		"Liferay-Releng-App-Description", "Liferay-Releng-App-Title",
		"Liferay-Releng-Bundle", "Liferay-Releng-Category",
		"Liferay-Releng-Demo-Url", "Liferay-Releng-Deprecated",
		"Liferay-Releng-Labs", "Liferay-Releng-Marketplace",
		"Liferay-Releng-Portal-Required", "Liferay-Releng-Public",
		"Liferay-Releng-Restart-Required", "Liferay-Releng-Support-Url",
		"Liferay-Releng-Supported"
	};

	private static final String[] _BND_BND_DEFINITION_KEYS = new String[] {
		"-jsp", "-metatype-inherit", "-sass", "Can-Redefine-Classes",
		"Can-Retransform-Classes", "Implementation-Version", "JPM-Command",
		"Liferay-Configuration-Path", "Liferay-Export-JS-Submodules",
		"Liferay-JS-Config", "Liferay-Releng-App-Description",
		"Liferay-Releng-Module-Group-Description",
		"Liferay-Releng-Module-Group-Title", "Liferay-Require-SchemaVersion",
		"Liferay-RTL-Support-Required", "Liferay-Service",
		"Liferay-Theme-Contributor-Type", "Liferay-Theme-Contributor-Weight",
		"Main-Class", "Premain-Class", "Web-ContextPath"
	};

	private static final String[] _COMMON_BND_DEFINITION_KEYS = new String[] {
		"Git-Descriptor", "Git-SHA", "Javac-Compiler", "Javac-Debug",
		"Javac-Deprecation", "Javac-Encoding", "Liferay-Portal-Build-Date",
		"Liferay-Portal-Build-Number", "Liferay-Portal-Build-Time",
		"Liferay-Portal-Code-Name", "Liferay-Portal-Parent-Build-Number",
		"Liferay-Portal-Release-Info", "Liferay-Portal-Server-Info",
		"Liferay-Portal-Version"
	};

}
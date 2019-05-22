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

package com.liferay.source.formatter.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class SourceFormatterCheckUtil {

	public static JSONObject addPropertiesAttributes(
		JSONObject attributesJSONObject, Map<String, Properties> propertiesMap,
		CheckType checkType, String checkName) {

		String keyPrefix = _getKeyPrefix(checkType, checkName);

		for (Map.Entry<String, Properties> entry : propertiesMap.entrySet()) {
			JSONObject propertiesAttributesJSONObject = new JSONObjectImpl();

			Properties properties = entry.getValue();

			for (Object obj : properties.keySet()) {
				String key = (String)obj;

				if (!key.startsWith(keyPrefix)) {
					continue;
				}

				String attributeName = StringUtil.replaceFirst(
					key, keyPrefix, StringPool.BLANK);

				JSONArray jsonArray = new JSONArrayImpl();

				for (String value :
						StringUtil.split(
							properties.getProperty(key), StringPool.COMMA)) {

					jsonArray.put(value);
				}

				propertiesAttributesJSONObject.put(attributeName, jsonArray);
			}

			if (propertiesAttributesJSONObject.length() != 0) {
				attributesJSONObject.put(
					entry.getKey(), propertiesAttributesJSONObject);
			}
		}

		return attributesJSONObject;
	}

	public static JSONObject addPropertiesAttributes(
		JSONObject attributesJSONObject, Map<String, Properties> propertiesMap,
		String... keys) {

		for (Map.Entry<String, Properties> entry : propertiesMap.entrySet()) {
			JSONObject propertiesAttributesJSONObject = new JSONObjectImpl();

			Properties properties = entry.getValue();

			for (Object obj : properties.keySet()) {
				String key = (String)obj;

				if (!ArrayUtil.contains(keys, key)) {
					continue;
				}

				JSONArray jsonArray = new JSONArrayImpl();

				for (String value :
						StringUtil.split(
							properties.getProperty(key), StringPool.COMMA)) {

					jsonArray.put(value);
				}

				propertiesAttributesJSONObject.put(key, jsonArray);
			}

			if (propertiesAttributesJSONObject.length() != 0) {
				attributesJSONObject.put(
					entry.getKey(), propertiesAttributesJSONObject);
			}
		}

		return attributesJSONObject;
	}

	public static List<String> getAttributeNames(
		CheckType checkType, String checkName,
		Map<String, Properties> propertiesMap) {

		String keyPrefix = _getKeyPrefix(checkType, checkName);

		Set<String> attributeNames = new HashSet<>();

		for (Map.Entry<String, Properties> entry : propertiesMap.entrySet()) {
			Properties properties = entry.getValue();

			for (Object key : properties.keySet()) {
				String s = (String)key;

				if (s.startsWith(keyPrefix)) {
					String attributeName = StringUtil.replaceFirst(
						s, keyPrefix, StringPool.BLANK);

					attributeNames.add(attributeName);
				}
			}
		}

		return ListUtil.fromCollection(attributeNames);
	}

	public static String getJSONObjectValue(
		JSONObject jsonObject, Map<String, String> cachedValuesMap, String key,
		String defaultValue, String absolutePath, String baseDirName) {

		if (jsonObject == null) {
			return defaultValue;
		}

		String value = cachedValuesMap.get(key);

		if (value != null) {
			return value;
		}

		value = cachedValuesMap.get(absolutePath + ":" + key);

		if (value != null) {
			return value;
		}

		String closestPropertiesFileLocation = null;
		boolean hasSubdirectoryValue = false;

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String fileLocation = keys.next();

			String curValue = _getJSONObjectValue(
				jsonObject.getJSONObject(fileLocation), key);

			if (curValue == null) {
				continue;
			}

			if (fileLocation.equals(
					SourceFormatterUtil.CONFIGURATION_FILE_LOCATION)) {

				if (value == null) {
					value = curValue;
				}

				continue;
			}

			String baseDirNameAbsolutePath = SourceUtil.getAbsolutePath(
				baseDirName);

			if (fileLocation.length() > baseDirNameAbsolutePath.length()) {
				hasSubdirectoryValue = true;
			}

			if (!absolutePath.startsWith(fileLocation) &&
				!fileLocation.equals(baseDirNameAbsolutePath)) {

				continue;
			}

			if ((closestPropertiesFileLocation == null) ||
				(closestPropertiesFileLocation.length() <
					fileLocation.length())) {

				value = curValue;

				closestPropertiesFileLocation = fileLocation;
			}
		}

		if (value == null) {
			value = defaultValue;
		}

		cachedValuesMap.put(absolutePath + ":" + key, value);

		if (!hasSubdirectoryValue) {
			cachedValuesMap.put(key, value);
		}

		return value;
	}

	public static List<String> getJSONObjectValues(
		JSONObject jsonObject, String key,
		Map<String, List<String>> cachedValuesMap, String absolutePath,
		String baseDirName) {

		if (jsonObject == null) {
			return Collections.emptyList();
		}

		List<String> values = cachedValuesMap.get(key);

		if (values != null) {
			return values;
		}

		values = cachedValuesMap.get(absolutePath + ":" + key);

		if (values != null) {
			return values;
		}

		values = new ArrayList<>();

		boolean hasSubdirectoryValues = false;

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String fileLocation = keys.next();

			List<String> curValues = _getJSONObjectValues(
				jsonObject.getJSONObject(fileLocation), key);

			if (curValues.isEmpty()) {
				continue;
			}

			if (!fileLocation.equals(
					SourceFormatterUtil.CONFIGURATION_FILE_LOCATION)) {

				String baseDirNameAbsolutePath = SourceUtil.getAbsolutePath(
					baseDirName);

				if (fileLocation.length() > baseDirNameAbsolutePath.length()) {
					hasSubdirectoryValues = true;
				}

				if (!absolutePath.startsWith(fileLocation)) {
					continue;
				}
			}

			values.addAll(curValues);
		}

		if (!hasSubdirectoryValues) {
			cachedValuesMap.put(key, values);
		}
		else {
			cachedValuesMap.put(absolutePath + ":" + key, values);
		}

		return values;
	}

	public static String getPropertyValue(
		String attributeName, CheckType checkType, String checkName,
		Map<String, Properties> propertiesMap) {

		checkName = checkName.replaceAll("([a-z])([A-Z])", "$1.$2");

		checkName = checkName.replaceAll("([A-Z])([A-Z][a-z])", "$1.$2");

		String key = StringBundler.concat(
			StringUtil.toLowerCase(checkName), ".", attributeName);

		if (checkType != null) {
			String checkTypeName = checkType.getValue();

			checkTypeName = checkTypeName.replaceAll("([a-z])([A-Z])", "$1.$2");

			checkTypeName = checkTypeName.replaceAll(
				"([A-Z])([A-Z][a-z])", "$1.$2");

			key = StringUtil.toLowerCase(checkTypeName) + "." + key;
		}

		return getPropertyValue(key, propertiesMap);
	}

	public static String getPropertyValue(
		String propertyName, Map<String, Properties> propertiesMap) {

		return getPropertyValue(propertyName, propertiesMap, null);
	}

	public static String getPropertyValue(
		String propertyName, Map<String, Properties> propertiesMap,
		String excludedPropertiesFileLocation) {

		StringBundler sb = new StringBundler(propertiesMap.size() * 2);

		for (Map.Entry<String, Properties> entry : propertiesMap.entrySet()) {
			if (Objects.equals(
					entry.getKey(), excludedPropertiesFileLocation)) {

				continue;
			}

			Properties properties = entry.getValue();

			String value = properties.getProperty(propertyName);

			if (value == null) {
				continue;
			}

			if (Validator.isBoolean(value) || Validator.isNumber(value) ||
				propertyName.equals(
					SourceFormatterUtil.GIT_LIFERAY_PORTAL_BRANCH)) {

				return value;
			}

			sb.append(value);
			sb.append(CharPool.COMMA);
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private static String _getJSONObjectValue(
		JSONObject jsonObject, String key) {

		JSONArray jsonArray = jsonObject.getJSONArray(key);

		if ((jsonArray == null) || (jsonArray.length() != 1)) {
			return null;
		}

		return jsonArray.getString(0);
	}

	private static List<String> _getJSONObjectValues(
		JSONObject jsonObject, String key) {

		List<String> values = new ArrayList<>();

		JSONArray jsonArray = jsonObject.getJSONArray(key);

		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				values.add(jsonArray.getString(i));
			}
		}

		return values;
	}

	private static String _getKeyPrefix(CheckType checkType, String checkName) {
		checkName = checkName.replaceAll("([a-z])([A-Z])", "$1.$2");

		checkName = checkName.replaceAll("([A-Z])([A-Z][a-z])", "$1.$2");

		String keyPrefix = StringUtil.toLowerCase(checkName) + ".";

		String checkTypeName = checkType.getValue();

		checkTypeName = checkTypeName.replaceAll("([a-z])([A-Z])", "$1.$2");

		checkTypeName = checkTypeName.replaceAll(
			"([A-Z])([A-Z][a-z])", "$1.$2");

		return StringUtil.toLowerCase(checkTypeName) + "." + keyPrefix;
	}

}
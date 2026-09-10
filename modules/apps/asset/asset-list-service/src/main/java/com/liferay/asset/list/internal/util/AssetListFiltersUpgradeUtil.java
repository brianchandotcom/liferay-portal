/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Joshua Cords
 */
public class AssetListFiltersUpgradeUtil {

	public static String toUpgradedTypeSettings(String typeSettings) {
		if (Validator.isNull(typeSettings)) {
			return null;
		}

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.fastLoad(
			typeSettings
		).build();

		if (!_hasLegacyQueryRules(unicodeProperties)) {
			return null;
		}

		Map<String, JSONObject> filtersMap = new LinkedHashMap<>();

		for (int i = 0; true; i++) {
			String[] queryValues = StringUtil.split(
				unicodeProperties.getProperty("queryValues" + i, null));

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryAndOperator = GetterUtil.getBoolean(
				unicodeProperties.getProperty(
					"queryAndOperator" + i, StringPool.BLANK));
			boolean queryContains = GetterUtil.getBoolean(
				unicodeProperties.getProperty(
					"queryContains" + i, StringPool.BLANK));

			String queryName = unicodeProperties.getProperty(
				"queryName" + i, StringPool.BLANK);

			String propertyName = _toPropertyName(queryName);

			String filterKey = StringUtil.merge(
				new String[] {
					propertyName, String.valueOf(queryContains),
					String.valueOf(queryAndOperator)
				},
				StringPool.POUND);

			filtersMap.put(
				filterKey,
				JSONUtil.put(
					"operatorName", _toOperatorName(queryContains)
				).put(
					"propertyName", propertyName
				).put(
					"quantifier", _toQuantifier(queryAndOperator)
				).put(
					"value", _toValue(propertyName, queryValues)
				));
		}

		JSONArray filtersJSONArray = _getFiltersJSONArray(unicodeProperties);

		for (JSONObject filterJSONObject : filtersMap.values()) {
			filtersJSONArray.put(filterJSONObject);
		}

		unicodeProperties.setProperty("filters", filtersJSONArray.toString());

		_removeLegacyQueryRules(unicodeProperties);

		return unicodeProperties.toString();
	}

	private static JSONArray _getFiltersJSONArray(
		UnicodeProperties unicodeProperties) {

		String filtersJSON = unicodeProperties.getProperty("filters");

		if (Validator.isNull(filtersJSON)) {
			return JSONFactoryUtil.createJSONArray();
		}

		try {
			return JSONFactoryUtil.createJSONArray(filtersJSON);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return JSONFactoryUtil.createJSONArray();
		}
	}

	private static boolean _hasLegacyQueryRules(
		UnicodeProperties unicodeProperties) {

		for (String key : unicodeProperties.keySet()) {
			if (StringUtil.startsWith(key, "queryName")) {
				return true;
			}
		}

		return false;
	}

	private static void _removeLegacyQueryRules(
		UnicodeProperties unicodeProperties) {

		for (String key :
				unicodeProperties.keySet(
				).toArray(
					new String[0]
				)) {

			if (StringUtil.startsWith(key, "queryAndOperator") ||
				StringUtil.startsWith(key, "queryContains") ||
				StringUtil.startsWith(key, "queryName") ||
				StringUtil.startsWith(key, "queryValues")) {

				unicodeProperties.remove(key);
			}
		}
	}

	private static String _toOperatorName(boolean queryContains) {
		if (queryContains) {
			return "contains";
		}

		return "not-contains";
	}

	private static String _toPropertyName(String queryName) {
		if (Objects.equals(queryName, "assetCategories") ||
			Objects.equals(queryName, "keywords")) {

			return queryName;
		}

		return "assetTags";
	}

	private static String _toQuantifier(boolean queryAndOperator) {
		if (queryAndOperator) {
			return "all";
		}

		return "any";
	}

	private static Object _toValue(String propertyName, String[] queryValues) {
		if (Objects.equals(propertyName, "keywords")) {
			String[] keywords = new String[queryValues.length];

			for (int i = 0; i < queryValues.length; i++) {
				String keyword = queryValues[i];

				if (keyword.contains(StringPool.SPACE)) {
					keyword = StringUtil.quote(keyword, CharPool.QUOTE);
				}

				keywords[i] = keyword;
			}

			return StringUtil.merge(keywords, StringPool.SPACE);
		}

		JSONArray valueJSONArray = JSONFactoryUtil.createJSONArray();

		for (String queryValue : queryValues) {
			valueJSONArray.put(JSONUtil.put("value", queryValue));
		}

		return valueJSONArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListFiltersUpgradeUtil.class);

}
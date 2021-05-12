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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.style.book.model.StyleBookEntry;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class StyleBookEntryUtil {

	public static Map<String, Object> getFrontendTokensValues(
			FrontendTokenDefinition frontendTokenDefinition, Locale locale,
			StyleBookEntry styleBookEntry)
		throws Exception {

		Map<String, Object> frontendTokensValues = new LinkedHashMap<>();

		if (frontendTokenDefinition == null) {
			return frontendTokensValues;
		}

		JSONObject frontendTokenValuesJSONObject =
			_getFrontendTokenValuesJSONObject(styleBookEntry);

		JSONObject frontendTokenDefinitionJSONObject =
			JSONFactoryUtil.createJSONObject(
				frontendTokenDefinition.getJSON(locale));

		JSONArray frontendTokenCategoriesJSONArray =
			frontendTokenDefinitionJSONObject.getJSONArray(
				"frontendTokenCategories");

		for (int i = 0; i < frontendTokenCategoriesJSONArray.length(); i++) {
			JSONObject frontendTokenCategoryJSONObject =
				frontendTokenCategoriesJSONArray.getJSONObject(i);

			JSONArray frontendTokenSetsJSONArray =
				frontendTokenCategoryJSONObject.getJSONArray(
					"frontendTokenSets");

			for (int j = 0; j < frontendTokenSetsJSONArray.length(); j++) {
				JSONObject frontendTokenSetJSONObject =
					frontendTokenSetsJSONArray.getJSONObject(j);

				JSONArray frontendTokensJSONArray =
					frontendTokenSetJSONObject.getJSONArray("frontendTokens");

				for (int k = 0; k < frontendTokensJSONArray.length(); k++) {
					JSONObject frontendTokenJSONObject =
						frontendTokensJSONArray.getJSONObject(k);

					frontendTokensValues.put(
						frontendTokenJSONObject.getString("name"),
						_getProcessedFrontendTokenValue(
							frontendTokenJSONObject,
							frontendTokenValuesJSONObject));
				}
			}
		}

		return frontendTokensValues;
	}

	private static JSONObject _getFrontendTokenValuesJSONObject(
			StyleBookEntry styleBookEntry)
		throws Exception {

		if (styleBookEntry != null) {
			return JSONFactoryUtil.createJSONObject(
				styleBookEntry.getFrontendTokensValues());
		}

		return JSONFactoryUtil.createJSONObject();
	}

	private static Map<String, Object> _getProcessedFrontendTokenValue(
		JSONObject frontendTokenJSONObject,
		JSONObject frontendTokenValuesJSONObject) {

		String name = frontendTokenJSONObject.getString("name");

		JSONObject valueJSONObject =
			frontendTokenValuesJSONObject.getJSONObject(name);

		String value = StringPool.BLANK;

		if (valueJSONObject != null) {
			value = valueJSONObject.getString("value");
		}
		else {
			value = frontendTokenJSONObject.getString("defaultValue");
		}

		return HashMapBuilder.<String, Object>put(
			FrontendTokenMapping.TYPE_CSS_VARIABLE,
			() -> {
				JSONArray mappingsJSONArray =
					frontendTokenJSONObject.getJSONArray("mappings");

				for (int l = 0; l < mappingsJSONArray.length(); l++) {
					JSONObject mappingJSONObject =
						mappingsJSONArray.getJSONObject(l);

					if (Objects.equals(
							mappingJSONObject.getString("type"),
							FrontendTokenMapping.TYPE_CSS_VARIABLE)) {

						return mappingJSONObject.getString("value");
					}
				}

				return null;
			}
		).put(
			"editorType", frontendTokenJSONObject.get("editorType")
		).put(
			"label", frontendTokenJSONObject.get("label")
		).put(
			"name", name
		).put(
			"value", value
		).build();
	}

}
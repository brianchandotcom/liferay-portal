/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.test;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.ElementInstance;
import com.liferay.search.experiences.rest.dto.v1_0.Field;
import com.liferay.search.experiences.rest.dto.v1_0.FieldSet;
import com.liferay.search.experiences.rest.dto.v1_0.UiConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementDefinitionUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPElementUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.UnpackUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author Wade Cao
 */
public class SXPBlueprintSearchResultTestUtil {

	public static final String[] FIELDS = {
		"localized_title_${context.language_id}^2",
		"content_${context.language_id}^1"
	};

	public static String getElementInstancesJSON(
			Object[] configurationValuesArray, String[] sxpElementNames,
			List<SXPElement> sxpElements)
		throws Exception {

		ElementInstance[] elementInstances =
			new ElementInstance[sxpElementNames.length];

		for (int i = 0; i < sxpElementNames.length; i++) {
			SXPElement sxpElement = null;

			for (SXPElement sxpE : sxpElements) {
				if (StringUtil.equalsIgnoreCase(
						LanguageUtil.get(
							LocaleUtil.US, sxpE.getTitle(LocaleUtil.US)),
						sxpElementNames[i])) {

					sxpElement = sxpE;

					break;
				}
			}

			if (sxpElement == null) {
				throw new NoSuchElementException();
			}

			ElementDefinition elementDefinition = ElementDefinitionUtil.unpack(
				ElementDefinition.toDTO(sxpElement.getElementDefinitionJSON()));

			String configurationJSON = String.valueOf(
				elementDefinition.getConfiguration());

			Map<String, Object> configurationValues = null;

			if ((configurationValuesArray != null) &&
				(configurationValuesArray[i] != null)) {

				configurationValues =
					(Map<String, Object>)configurationValuesArray[i];

				for (Map.Entry<String, Object> entry :
						configurationValues.entrySet()) {

					Object configurationValue = UnpackUtil.unpack(
						entry.getValue());

					Map<String, String> values = HashMapBuilder.put(
						entry.getKey(), configurationValue.toString()
					).build();

					if (configurationValue instanceof String) {
						configurationJSON = StringUtil.replace(
							configurationJSON, "${configuration.", "}", values);
						configurationJSON = StringUtil.replace(
							configurationJSON, "${", "}", values);
					}
					else {
						configurationJSON = StringUtil.replace(
							configurationJSON, "\"${configuration.", "}\"",
							values);
					}
				}
			}

			ElementInstance elementInstance = ElementInstance.unsafeToDTO("{}");

			elementInstance.setConfigurationEntry(
				Configuration.toDTO(configurationJSON));
			elementInstance.setSxpElement(
				SXPElementUtil.toSXPElement(
					JSONUtil.put(
						"elementDefinition",
						JSONFactoryUtil.createJSONObject(
							elementDefinition.toString())
					).toString()));

			Map<String, Object> uiConfigurationValues = new HashMap<>();

			UiConfiguration uiConfiguration =
				elementDefinition.getUiConfiguration();

			if ((configurationValues != null) && (uiConfiguration != null) &&
				(uiConfiguration.getFieldSets() != null)) {

				FieldSet[] fieldSets = uiConfiguration.getFieldSets();

				if (!ArrayUtil.isEmpty(fieldSets)) {
					for (FieldSet fieldSet : fieldSets) {
						Field[] fields = fieldSet.getFields();

						if (fields == null) {
							continue;
						}

						for (Field field : fields) {
							String fieldName = field.getName();

							Object value = configurationValues.get(fieldName);

							if (value == null) {
								continue;
							}

							uiConfigurationValues.put(
								fieldName, UnpackUtil.unpack(value));
						}
					}
				}
			}

			elementInstance.setUiConfigurationValues(uiConfigurationValues);

			elementInstances[i] = elementInstance;
		}

		return Arrays.toString(elementInstances);
	}

	public static JSONObject getMatchQueryJSONObject(int boost, String query) {
		return JSONUtil.put(
			"match",
			JSONUtil.put(
				"content_en_US",
				JSONUtil.put(
					"boost", boost
				).put(
					"query", query
				)));
	}

	public static JSONObject getMultiMatchQueryJSONObject(
		int boost, String[] fields, String fuzziness, String operator,
		String query, String type) {

		return JSONUtil.put(
			"multi_match",
			JSONUtil.put(
				"boost", boost
			).put(
				"fields", fields
			).put(
				"fuzziness", () -> fuzziness
			).put(
				"operator", () -> operator
			).put(
				"query", query
			).put(
				"type", type
			));
	}

}
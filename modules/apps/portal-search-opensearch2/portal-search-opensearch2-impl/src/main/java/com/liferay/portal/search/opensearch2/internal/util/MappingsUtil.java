/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import jakarta.json.spi.JsonProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensearch.client.json.JsonpMapper;
import org.opensearch.client.opensearch._types.mapping.DynamicTemplate;
import org.opensearch.client.opensearch._types.mapping.Property;

/**
 * @author Petteri Karttunen
 */
public class MappingsUtil {

	public static List<Map<String, DynamicTemplate>> getDynamicTemplatesMap(
		JSONObject mappingsJSONObject) {

		JSONArray dynamicTemplatesJSONArray = mappingsJSONObject.getJSONArray(
			"dynamic_templates");

		if (dynamicTemplatesJSONArray == null) {
			return null;
		}

		JsonpMapper jsonpMapper = JsonpUtil.getJsonpMapper();

		JsonProvider jsonProvider = jsonpMapper.jsonProvider();

		List<Map<String, DynamicTemplate>> dynamicTemplates = new ArrayList<>();

		for (int i = 0; i < dynamicTemplatesJSONArray.length(); i++) {
			JSONObject dynamicTemplateJSONObject =
				dynamicTemplatesJSONArray.getJSONObject(i);

			for (String dynamicTemplateName :
					dynamicTemplateJSONObject.keySet()) {

				JSONObject templateJSONObject =
					dynamicTemplateJSONObject.getJSONObject(
						dynamicTemplateName);

				_convertElasticsearchDynamicTemplate(templateJSONObject);

				String dynamicTemplateString = templateJSONObject.toString();

				try (InputStream inputStream = new ByteArrayInputStream(
						dynamicTemplateString.getBytes(
							StandardCharsets.UTF_8))) {

					dynamicTemplates.add(
						HashMapBuilder.<String, DynamicTemplate>put(
							dynamicTemplateName,
							DynamicTemplate._DESERIALIZER.deserialize(
								jsonProvider.createParser(inputStream),
								jsonpMapper)
						).build());
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}
		}

		return dynamicTemplates;
	}

	public static Map<String, Property> getPropertiesMap(
		JSONObject mappingsJSONObject) {

		JSONObject propertiesJSONObject = mappingsJSONObject.getJSONObject(
			"properties");

		if (propertiesJSONObject == null) {
			return null;
		}

		JsonpMapper jsonpMapper = JsonpUtil.getJsonpMapper();

		JsonProvider jsonProvider = jsonpMapper.jsonProvider();

		Map<String, Property> properties = new HashMap<>();

		for (String fieldName : propertiesJSONObject.keySet()) {
			String fieldProperties = String.valueOf(
				propertiesJSONObject.get(fieldName));

			try (InputStream inputStream = new ByteArrayInputStream(
					fieldProperties.getBytes(StandardCharsets.UTF_8))) {

				properties.put(
					fieldName,
					Property._DESERIALIZER.deserialize(
						jsonProvider.createParser(inputStream), jsonpMapper));
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}

		return properties;
	}

	private static void _convertElasticsearchDynamicTemplate(
		JSONObject templateJSONObject) {

		JSONObject mappingJSONObject = templateJSONObject.getJSONObject(
			"mapping");

		if (mappingJSONObject.has("dims")) {
			int dims = mappingJSONObject.getInt("dims");

			mappingJSONObject.remove("dims");
			mappingJSONObject.put("dimension", dims);
		}

		String type = mappingJSONObject.getString("type");

		if (StringUtil.equals(type, "dense_vector")) {
			mappingJSONObject.put("type", "knn_vector");
		}
	}

}
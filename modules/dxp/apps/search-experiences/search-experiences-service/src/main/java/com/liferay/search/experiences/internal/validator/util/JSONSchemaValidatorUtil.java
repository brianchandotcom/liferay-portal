/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.validator.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.util.Set;
import java.util.stream.Stream;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaClient;
import org.everit.json.schema.loader.SchemaLoader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author Petteri Karttunen
 */
public class JSONSchemaValidatorUtil {

	public static void validate(String json, InputStream inputStream)
		throws ValidationException {

		if (Validator.isNull(json)) {
			return;
		}

		try {
			JSONObject validationJSONObject = _removeEmptyKeys(
				new JSONObject(json));

			JSONObject schemaJSONObject = new JSONObject(
				new JSONTokener(inputStream));

			SchemaLoader schemaLoader = SchemaLoader.builder(
			).schemaClient(
				SchemaClient.classPathAwareClient()
			).schemaJson(
				schemaJSONObject
			).resolutionScope(
				"classpath://com/liferay/search/experiences/internal" +
					"/validator/dependencies/"
			).build();

			Schema.Builder<?> builder = schemaLoader.load();

			Schema schema = builder.build();

			schema.validate(validationJSONObject);
		}
		catch (JSONException jsonException) {
			_log.error(jsonException.getMessage(), jsonException);

			throw new RuntimeException(jsonException);
		}
	}

	private static boolean _isEmpty(String key, JSONObject jsonObject) {
		Object object = jsonObject.get(key);

		if (object instanceof JSONObject) {
			JSONObject rootJSONObject = (JSONObject)object;

			if (rootJSONObject.length() == 0) {
				return true;
			}
		}

		return false;
	}

	private static JSONObject _removeEmptyKeys(JSONObject originalJSONObject) {
		JSONObject cleanedJSONObject = new JSONObject();

		Set<String> keySet = originalJSONObject.keySet();

		Stream<String> stream = keySet.stream();

		stream.filter(
			key -> !_isEmpty(key, originalJSONObject)
		).forEach(
			key -> cleanedJSONObject.put(key, originalJSONObject.get(key))
		);

		return cleanedJSONObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONSchemaValidatorUtil.class);

}
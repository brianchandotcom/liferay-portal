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

package com.liferay.fragment.internal.validator;

import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = FragmentEntryValidator.class)
public class FragmentEntryValidatorImpl implements FragmentEntryValidator {

	@Override
	public void validateConfiguration(String configuration)
		throws FragmentEntryConfigurationException {

		if (Validator.isNull(configuration)) {
			return;
		}

		InputStream configurationJSONSchemaInputStream =
			FragmentEntryValidatorImpl.class.getResourceAsStream(
				"dependencies/configuration-json-schema.json");

		try {
			JSONObject jsonObject = new JSONObject(
				new JSONTokener(configurationJSONSchemaInputStream));

			Schema schema = SchemaLoader.load(jsonObject);

			JSONObject configurationJSONObject = new JSONObject(configuration);

			schema.validate(configurationJSONObject);

			JSONArray fieldSetsJSONArray = configurationJSONObject.getJSONArray(
				"fieldSets");

			Set<String> fieldNames = new HashSet<>();

			for (int fieldSetIndex = 0;
				 fieldSetIndex < fieldSetsJSONArray.length(); fieldSetIndex++) {

				JSONObject fieldSetJSONObject =
					fieldSetsJSONArray.getJSONObject(fieldSetIndex);

				JSONArray fieldsJSONArray = fieldSetJSONObject.getJSONArray(
					"fields");

				for (int fieldIndex = 0; fieldIndex < fieldsJSONArray.length();
					 fieldIndex++) {

					JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(
						fieldIndex);

					if (fieldNames.contains(
							fieldJSONObject.getString("name"))) {

						throw new FragmentEntryConfigurationException(
							"Field names must be unique");
					}

					fieldNames.add(fieldJSONObject.getString("name"));
				}
			}
		}
		catch (Exception exception) {
			if (exception instanceof JSONException) {
				JSONException jsonException = (JSONException)exception;

				throw new FragmentEntryConfigurationException(
					jsonException.getMessage(), jsonException);
			}
			else if (exception instanceof ValidationException) {
				ValidationException validationException =
					(ValidationException)exception;

				String errorMessage = validationException.getErrorMessage();

				List<String> messages = validationException.getAllMessages();

				if (!messages.isEmpty()) {
					List<String> formattedMessages = new ArrayList<>();

					messages.forEach(
						message -> {
							if (message.startsWith("#: ")) {
								message = message.substring(3);
							}
							else if (message.startsWith("#")) {
								message = message.substring(1);
							}

							formattedMessages.add(message);
						});

					errorMessage = StringUtil.merge(
						formattedMessages, StringPool.NEW_LINE);
				}

				throw new FragmentEntryConfigurationException(
					errorMessage, exception);
			}

			throw new FragmentEntryConfigurationException(exception);
		}
	}

}
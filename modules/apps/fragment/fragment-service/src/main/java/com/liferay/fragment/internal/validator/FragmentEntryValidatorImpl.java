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
import com.liferay.petra.json.validator.JSONValidator;
import com.liferay.petra.json.validator.JSONValidatorException;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = FragmentEntryValidator.class)
public class FragmentEntryValidatorImpl implements FragmentEntryValidator {

	@Override
	public void validateConfiguration(String configuration)
		throws FragmentEntryConfigurationException {

		validateConfigurationValues(configuration, null);
	}

	@Override
	public void validateConfigurationValues(
			String configuration, JSONObject valuesJSONObject)
		throws FragmentEntryConfigurationException {

		if (Validator.isNull(configuration)) {
			return;
		}

		try {
			_jsonValidator.validate(configuration);

			JSONObject configurationJSONObject =
				JSONFactoryUtil.createJSONObject(configuration);

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

					String fieldName = fieldJSONObject.getString("name");

					if (fieldNames.contains(fieldName)) {
						throw new FragmentEntryConfigurationException(
							"Field names must be unique");
					}

					JSONObject typeOptionsJSONObject =
						fieldJSONObject.getJSONObject("typeOptions");

					if (typeOptionsJSONObject != null) {
						String defaultValue = fieldJSONObject.getString(
							"defaultValue");

						if (!_checkValidationRules(
								defaultValue,
								typeOptionsJSONObject.getJSONObject(
									"validation"))) {

							throw new FragmentEntryConfigurationException(
								"Invalid default configuration value for " +
									"field " + fieldName);
						}

						if (valuesJSONObject != null) {
							String value = valuesJSONObject.getString(
								fieldName);

							if (!_checkValidationRules(
									value,
									typeOptionsJSONObject.getJSONObject(
										"validation"))) {

								throw new FragmentEntryConfigurationException(
									"Invalid configuration value for field " +
										fieldName);
							}
						}
					}

					fieldNames.add(fieldName);
				}
			}
		}
		catch (JSONException jsonException) {
			throw new FragmentEntryConfigurationException(
				_getMessage(jsonException.getMessage()), jsonException);
		}
		catch (JSONValidatorException jsonValidatorException) {
			throw new FragmentEntryConfigurationException(
				_getMessage(jsonValidatorException.getMessage()),
				jsonValidatorException);
		}
	}

	private boolean _checkValidationRules(
		String value, JSONObject validationJSONObject) {

		if (Validator.isNull(value) || (validationJSONObject == null)) {
			return true;
		}

		String type = validationJSONObject.getString("type");

		if (Objects.equals(type, "email")) {
			return Validator.isEmailAddress(value);
		}
		else if (Objects.equals(type, "number")) {
			long max = validationJSONObject.getLong("max", Long.MAX_VALUE);
			long min = validationJSONObject.getLong("min", Long.MIN_VALUE);

			boolean valid = false;

			if (Validator.isNumber(value) &&
				(GetterUtil.getLong(value) <= max) &&
				(GetterUtil.getLong(value) >= min)) {

				valid = true;
			}

			return valid;
		}
		else if (Objects.equals(type, "pattern")) {
			String regexp = validationJSONObject.getString("regexp");

			return value.matches(regexp);
		}
		else if (Objects.equals(type, "url")) {
			return Validator.isUrl(value);
		}

		long maxLength = validationJSONObject.getLong(
			"maxLength", Long.MAX_VALUE);
		long minLength = validationJSONObject.getLong(
			"minLength", Long.MIN_VALUE);

		if ((value.length() <= maxLength) && (value.length() >= minLength)) {
			return true;
		}

		return false;
	}

	private String _getMessage(String message) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		return StringBundler.concat(
			LanguageUtil.get(
				resourceBundle, "fragment-configuration-is-invalid"),
			System.lineSeparator(), message);
	}

	private static final JSONValidator _jsonValidator = new JSONValidator(
		FragmentEntryValidatorImpl.class.getResourceAsStream(
			"dependencies/configuration-json-schema.json"));

}
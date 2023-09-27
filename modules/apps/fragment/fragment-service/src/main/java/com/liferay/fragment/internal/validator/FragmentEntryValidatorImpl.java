/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.validator;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.fragment.exception.FragmentEntryFieldTypesException;
import com.liferay.fragment.exception.FragmentEntryTypeOptionsException;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.validator.JSONValidator;
import com.liferay.portal.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = FragmentEntryValidator.class)
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
			_configurationJSONValidator.validate(configuration);

			JSONObject configurationJSONObject = _jsonFactory.createJSONObject(
				configuration);

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

	@Override
	public void validateTypeOptions(int fragmentEntryType, String typeOptions)
		throws FragmentEntryTypeOptionsException {

		if (Validator.isNull(typeOptions)) {
			return;
		}

		try {
			_typeOptionsJSONValidator.validate(typeOptions);

			JSONObject configurationJSONObject = _jsonFactory.createJSONObject(
				typeOptions);

			JSONArray fieldTypesJSONArray =
				configurationJSONObject.getJSONArray("fieldTypes");

			if (!Objects.equals(
					FragmentConstants.TYPE_INPUT, fragmentEntryType)) {

				if (!JSONUtil.isEmpty(fieldTypesJSONArray)) {
					throw new FragmentEntryFieldTypesException(
						"Only fragment type input can have field types");
				}

				return;
			}

			if (JSONUtil.isEmpty(fieldTypesJSONArray)) {
				throw new FragmentEntryFieldTypesException(
					"Fragment type input must have at least one field type");
			}

			if ((fieldTypesJSONArray.length() > 1) &&
				JSONUtil.hasValue(fieldTypesJSONArray, "captcha")) {

				throw new FragmentEntryFieldTypesException(
					"Captcha field type cannot be mixed with other field " +
						"types");
			}
		}
		catch (JSONException jsonException) {
			throw new FragmentEntryTypeOptionsException(
				_getMessage(jsonException.getMessage()), jsonException);
		}
		catch (JSONValidatorException jsonValidatorException) {
			throw new FragmentEntryTypeOptionsException(jsonValidatorException);
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
		return StringBundler.concat(
			_language.get(
				LocaleUtil.getDefault(), "fragment-configuration-is-invalid"),
			System.lineSeparator(), message);
	}

	private static final JSONValidator _configurationJSONValidator =
		new JSONValidator(
			FragmentEntryValidatorImpl.class.getResource(
				"dependencies/configuration-json-schema.json"));
	private static final JSONValidator _typeOptionsJSONValidator =
		new JSONValidator(
			FragmentEntryValidatorImpl.class.getResource(
				"dependencies/type-options-json-schema.json"));

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}
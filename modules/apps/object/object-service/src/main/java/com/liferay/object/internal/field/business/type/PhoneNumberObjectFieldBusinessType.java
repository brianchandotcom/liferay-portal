/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.business.type;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_PHONE_NUMBER,
	service = ObjectFieldBusinessType.class
)
public class PhoneNumberObjectFieldBusinessType
	extends BaseObjectFieldBusinessType {

	@Override
	public Set<String> getAllowedObjectFieldSettingsNames() {
		return SetUtil.fromArray(
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
			ObjectFieldSettingConstants.NAME_PREFIX,
			ObjectFieldSettingConstants.NAME_PREFIX_TYPE,
			ObjectFieldSettingConstants.NAME_UNIQUE_VALUES);
	}

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_STRING;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return ObjectDDMFormFieldTypeConstants.PHONE_NUMBER;
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(
			locale, "add-a-phone-number-with-country-code-support");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "phone-number");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_PHONE_NUMBER;
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.TEXT;
	}

	@Override
	public Set<String> getRequiredObjectFieldSettingsNames(
		ObjectField objectField) {

		return Collections.singleton(
			ObjectFieldSettingConstants.NAME_PREFIX_TYPE);
	}

	@Override
	public Set<String> getUnmodifiableObjectFieldSettingsNames() {
		return Collections.singleton(
			ObjectFieldSettingConstants.NAME_UNIQUE_VALUES);
	}

	@Override
	public boolean isVisible(ObjectDefinition objectDefinition) {
		return FeatureFlagManagerUtil.isEnabled(
			objectDefinition.getCompanyId(), "LPD-83570");
	}

	@Override
	public Serializable processValue(
			ObjectField objectField, Serializable serializable)
		throws PortalException {

		String value = GetterUtil.getString(serializable);

		if (Validator.isNull(value)) {
			return value;
		}

		String normalizedValue = _normalize(value);

		if (!Objects.equals(
				ObjectFieldSettingUtil.getValue(
					ObjectFieldSettingConstants.NAME_PREFIX_TYPE, objectField),
				ObjectFieldSettingConstants.VALUE_DEFINED_BY_USER)) {

			String prefix = ObjectFieldSettingUtil.getValue(
				ObjectFieldSettingConstants.NAME_PREFIX, objectField);

			if (StringUtil.startsWith(normalizedValue, StringPool.PLUS)) {
				if (!StringUtil.startsWith(normalizedValue, prefix) ||
					(normalizedValue.length() <= prefix.length())) {

					throw new ObjectEntryValuesException.InvalidPhoneNumber(
						objectField.getName(), value);
				}
			}
			else {
				normalizedValue = _normalize(prefix + value);
			}
		}

		if (!_isValid(normalizedValue)) {
			throw new ObjectEntryValuesException.InvalidPhoneNumber(
				objectField.getName(), value);
		}

		return normalizedValue;
	}

	@Override
	public void validateObjectFieldSettings(
			ObjectField objectField,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		super.validateObjectFieldSettings(objectField, objectFieldSettings);

		Map<String, String> objectFieldSettingsValues =
			getObjectFieldSettingsValues(objectFieldSettings);

		validateBooleanObjectFieldSetting(
			objectField.getName(),
			ObjectFieldSettingConstants.NAME_UNIQUE_VALUES,
			objectFieldSettingsValues);

		String prefixType = objectFieldSettingsValues.get(
			ObjectFieldSettingConstants.NAME_PREFIX_TYPE);

		if (Objects.equals(
				prefixType,
				ObjectFieldSettingConstants.VALUE_DEFINED_BY_USER)) {

			validateNotAllowedObjectFieldSettingNames(
				SetUtil.fromArray(ObjectFieldSettingConstants.NAME_PREFIX),
				objectField.getName(), objectFieldSettingsValues);
		}
		else if (Objects.equals(
					prefixType, ObjectFieldSettingConstants.VALUE_FIXED)) {

			String prefix = objectFieldSettingsValues.get(
				ObjectFieldSettingConstants.NAME_PREFIX);

			if (Validator.isNull(prefix)) {
				throw new ObjectFieldSettingValueException.
					MissingRequiredValues(
						objectField.getName(),
						Collections.singleton(
							ObjectFieldSettingConstants.NAME_PREFIX));
			}

			Matcher matcher = _prefixPattern.matcher(prefix);

			if (!matcher.matches()) {
				throw new ObjectFieldSettingValueException.InvalidValue(
					objectField.getName(),
					ObjectFieldSettingConstants.NAME_PREFIX, prefix);
			}
		}
		else {
			throw new ObjectFieldSettingValueException.InvalidValue(
				objectField.getName(),
				ObjectFieldSettingConstants.NAME_PREFIX_TYPE, prefixType);
		}
	}

	@Override
	public void validateObjectFieldSettingsDefaultValue(
			ObjectField objectField,
			Map<String, String> objectFieldSettingsValuesMap)
		throws PortalException {

		if (objectFieldSettingsValuesMap.isEmpty()) {
			return;
		}

		super.validateObjectFieldSettingsDefaultValue(
			objectField, objectFieldSettingsValuesMap);

		String defaultValue = objectFieldSettingsValuesMap.get(
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE);

		if (Validator.isNull(defaultValue)) {
			return;
		}

		String normalizedDefaultValue = _normalize(defaultValue);

		if (Objects.equals(
				objectFieldSettingsValuesMap.get(
					ObjectFieldSettingConstants.NAME_PREFIX_TYPE),
				ObjectFieldSettingConstants.VALUE_FIXED)) {

			String prefix = objectFieldSettingsValuesMap.get(
				ObjectFieldSettingConstants.NAME_PREFIX);

			if (StringUtil.startsWith(
					normalizedDefaultValue, StringPool.PLUS)) {

				if (!StringUtil.startsWith(normalizedDefaultValue, prefix) ||
					(normalizedDefaultValue.length() <= prefix.length())) {

					throw new ObjectFieldSettingValueException.InvalidValue(
						objectField.getName(),
						ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
						defaultValue);
				}
			}
			else {
				normalizedDefaultValue = _normalize(prefix + defaultValue);
			}
		}

		if (!_isValid(normalizedDefaultValue)) {
			throw new ObjectFieldSettingValueException.InvalidValue(
				objectField.getName(),
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE, defaultValue);
		}
	}

	private boolean _isValid(String phoneNumber) {
		if (Validator.isNull(phoneNumber)) {
			return false;
		}

		Matcher matcher = _phoneNumberPattern.matcher(phoneNumber);

		return matcher.matches();
	}

	private String _normalize(String phoneNumber) {
		if (Validator.isNull(phoneNumber)) {
			return phoneNumber;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < phoneNumber.length(); i++) {
			char c = phoneNumber.charAt(i);

			if ((c == CharPool.PLUS) && (sb.length() == 0)) {
				sb.append(c);
			}
			else if (Character.isDigit(c)) {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	private static final Pattern _phoneNumberPattern = Pattern.compile(
		"^\\+[0-9]{7,15}$");
	private static final Pattern _prefixPattern = Pattern.compile(
		"^\\+[1-9][0-9]{0,2}$");

	@Reference
	private Language _language;

}
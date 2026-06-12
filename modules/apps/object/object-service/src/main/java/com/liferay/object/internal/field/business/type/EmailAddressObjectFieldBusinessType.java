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
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTableUtil;
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
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nathaly Gomes
 */
@Component(
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_EMAIL_ADDRESS,
	service = ObjectFieldBusinessType.class
)
public class EmailAddressObjectFieldBusinessType
	extends BaseObjectFieldBusinessType {

	@Override
	public Set<String> getAllowedObjectFieldSettingsNames() {
		return SetUtil.fromArray(
			ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS,
			ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED,
			ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS,
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
			ObjectFieldSettingConstants.NAME_UNIQUE_VALUES);
	}

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_STRING;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return ObjectDDMFormFieldTypeConstants.EMAIL_ADDRESS;
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(locale, "store-and-validate-email-addresses");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "email-address");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_EMAIL_ADDRESS;
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.TEXT;
	}

	@Override
	public Set<String> getUnmodifiableObjectFieldSettingsNames() {
		return Collections.singleton(
			ObjectFieldSettingConstants.NAME_UNIQUE_VALUES);
	}

	@Override
	public boolean isVisible(ObjectDefinition objectDefinition) {
		return FeatureFlagManagerUtil.isEnabled(
			objectDefinition.getCompanyId(), "LPD-70673");
	}

	@Override
	public void predefineObjectFieldSettings(
			ObjectField newObjectField, ObjectField oldObjectField,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		Set<String> lowercaseSettingNames = SetUtil.fromArray(
			ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS,
			ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS,
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE);

		for (ObjectFieldSetting objectFieldSetting : objectFieldSettings) {
			if (lowercaseSettingNames.contains(objectFieldSetting.getName()) &&
				Validator.isNotNull(objectFieldSetting.getValue())) {

				objectFieldSetting.setValue(
					StringUtil.toLowerCase(objectFieldSetting.getValue()));
			}
		}
	}

	@Override
	public Serializable processValue(
			ObjectField objectField, Serializable serializable)
		throws PortalException {

		String value = GetterUtil.getString(serializable);

		if (Validator.isNull(value)) {
			return value;
		}

		String normalizedValue = StringUtil.toLowerCase(value);

		int maxLength = DynamicObjectDefinitionTableUtil.getMaxLength(
			objectField.getBusinessType());

		if (normalizedValue.length() > maxLength) {
			throw new ObjectEntryValuesException.ExceedsTextMaxLength(
				maxLength, objectField.getName());
		}

		if (!Validator.isEmailAddress(normalizedValue)) {
			throw new ObjectEntryValuesException.InvalidEmailAddress(
				value, objectField.getName());
		}

		String blockedDomain = _getBlockedDomain(
			ObjectFieldSettingUtil.getValue(
				ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS, objectField),
			normalizedValue);

		if (Validator.isNotNull(blockedDomain)) {
			throw new ObjectEntryValuesException.BlockedEmailAddressDomain(
				blockedDomain, objectField.getName());
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
			ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED,
			objectFieldSettingsValues);

		if (StringUtil.equalsIgnoreCase(
				objectFieldSettingsValues.get(
					ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED),
				StringPool.TRUE)) {

			_validateEmailAddressDomains(
				objectField.getName(),
				ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS,
				objectFieldSettingsValues);
		}
		else {
			validateNotAllowedObjectFieldSettingNames(
				SetUtil.fromArray(
					ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS),
				objectField.getName(), objectFieldSettingsValues);
		}

		_validateEmailAddressDomains(
			objectField.getName(),
			ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS,
			objectFieldSettingsValues);
		validateBooleanObjectFieldSetting(
			objectField.getName(),
			ObjectFieldSettingConstants.NAME_UNIQUE_VALUES,
			objectFieldSettingsValues);
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

		String normalizedDefaultValue = StringUtil.toLowerCase(defaultValue);

		if ((normalizedDefaultValue.length() >
				DynamicObjectDefinitionTableUtil.getMaxLength(
					objectField.getBusinessType())) ||
			!Validator.isEmailAddress(normalizedDefaultValue) ||
			Validator.isNotNull(
				_getBlockedDomain(
					objectFieldSettingsValuesMap.get(
						ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS),
					normalizedDefaultValue))) {

			throw new ObjectFieldSettingValueException.InvalidValue(
				objectField.getName(),
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE, defaultValue);
		}
	}

	private String _getBlockedDomain(
		String blockedDomains, String normalizedEmailAddress) {

		if (Validator.isNull(blockedDomains)) {
			return null;
		}

		String domain = normalizedEmailAddress.substring(
			normalizedEmailAddress.lastIndexOf(CharPool.AT));

		for (String blockedDomain :
				StringUtil.split(blockedDomains, CharPool.COMMA)) {

			if (StringUtil.equalsIgnoreCase(domain, blockedDomain.trim())) {
				return domain;
			}
		}

		return null;
	}

	private void _validateEmailAddressDomains(
			String objectFieldName, String objectFieldSettingName,
			Map<String, String> objectFieldSettingsValues)
		throws PortalException {

		String value = objectFieldSettingsValues.get(objectFieldSettingName);

		if (Validator.isNull(value)) {
			return;
		}

		for (String domain : StringUtil.split(value, CharPool.COMMA)) {
			String trimmedDomain = domain.trim();

			String normalizedDomain = StringUtil.toLowerCase(trimmedDomain);

			if (!StringUtil.startsWith(normalizedDomain, CharPool.AT) ||
				!Validator.isDomain(
					StringUtil.extractLast(normalizedDomain, CharPool.AT))) {

				throw new ObjectFieldSettingValueException.InvalidValue(
					objectFieldName, objectFieldSettingName, trimmedDomain);
			}
		}
	}

	@Reference
	private Language _language;

}
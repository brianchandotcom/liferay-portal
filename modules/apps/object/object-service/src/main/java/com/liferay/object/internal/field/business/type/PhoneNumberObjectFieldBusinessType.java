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
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Activate;
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
			ObjectFieldSettingConstants.NAME_COUNTRY,
			ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE,
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
	public Map<String, Object> getRenderingProperties() {
		return HashMapBuilder.<String, Object>put(
			"countries", _getCountries()
		).build();
	}

	@Override
	public Set<String> getRequiredObjectFieldSettingsNames(
		ObjectField objectField) {

		return Collections.singleton(
			ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE);
	}

	@Override
	public Set<String> getUnmodifiableObjectFieldSettingsNames() {
		return Collections.singleton(
			ObjectFieldSettingConstants.NAME_UNIQUE_VALUES);
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
					ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE,
					objectField),
				ObjectFieldSettingConstants.VALUE_DEFINED_BY_USER)) {

			Country country = _countryLocalService.fetchCountryByA2(
				objectField.getCompanyId(),
				StringUtil.toUpperCase(
					ObjectFieldSettingUtil.getValue(
						ObjectFieldSettingConstants.NAME_COUNTRY,
						objectField)));

			if (country == null) {
				throw new ObjectEntryValuesException.InvalidPhoneNumber(
					objectField.getName(), value);
			}

			String prefix = StringPool.PLUS + country.getIdd();

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

		String countrySource = objectFieldSettingsValues.get(
			ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE);

		if (Objects.equals(
				countrySource,
				ObjectFieldSettingConstants.VALUE_DEFINED_BY_USER)) {

			validateNotAllowedObjectFieldSettingNames(
				SetUtil.fromArray(ObjectFieldSettingConstants.NAME_COUNTRY),
				objectField.getName(), objectFieldSettingsValues);
		}
		else if (Objects.equals(
					countrySource, ObjectFieldSettingConstants.VALUE_FIXED)) {

			String countryA2 = objectFieldSettingsValues.get(
				ObjectFieldSettingConstants.NAME_COUNTRY);

			if (Validator.isNull(countryA2)) {
				throw new ObjectFieldSettingValueException.
					MissingRequiredValues(
						objectField.getName(),
						Collections.singleton(
							ObjectFieldSettingConstants.NAME_COUNTRY));
			}

			Country country = _countryLocalService.fetchCountryByA2(
				objectField.getCompanyId(), StringUtil.toUpperCase(countryA2));

			if ((country == null) || Validator.isNull(country.getIdd())) {
				throw new ObjectFieldSettingValueException.InvalidValue(
					objectField.getName(),
					ObjectFieldSettingConstants.NAME_COUNTRY, countryA2);
			}
		}
		else {
			throw new ObjectFieldSettingValueException.InvalidValue(
				objectField.getName(),
				ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE, countrySource);
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
					ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE),
				ObjectFieldSettingConstants.VALUE_FIXED)) {

			Country country = _countryLocalService.fetchCountryByA2(
				objectField.getCompanyId(),
				StringUtil.toUpperCase(
					objectFieldSettingsValuesMap.get(
						ObjectFieldSettingConstants.NAME_COUNTRY)));

			if (country == null) {
				throw new ObjectFieldSettingValueException.InvalidValue(
					objectField.getName(),
					ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
					defaultValue);
			}

			String prefix = StringPool.PLUS + country.getIdd();

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

	@Activate
	protected void activate() {
		Set<String> a2s = new HashSet<>();

		for (String languageId : PropsValues.LOCALES) {
			Locale locale = LocaleUtil.fromLanguageId(languageId, false);

			String a2 = locale.getCountry();

			if (Validator.isNotNull(a2)) {
				a2s.add(a2);
			}
		}

		_a2s = Collections.unmodifiableSet(a2s);
	}

	private List<Map<String, String>> _getCountries() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return Collections.emptyList();
		}

		long companyId = serviceContext.getCompanyId();

		if (companyId == 0) {
			return Collections.emptyList();
		}

		List<Map<String, String>> countries = new ArrayList<>();

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		if (locale == null) {
			locale = LocaleUtil.getDefault();
		}

		String languageId = LocaleUtil.toLanguageId(locale);

		for (Country country :
				_countryLocalService.getCompanyCountries(companyId, true)) {

			String a2 = country.getA2();

			if (!_a2s.contains(a2)) {
				continue;
			}

			String idd = country.getIdd();

			if (Validator.isNull(idd)) {
				continue;
			}

			countries.add(
				HashMapBuilder.put(
					"a2", a2
				).put(
					"idd", idd
				).put(
					"name", country.getTitle(languageId)
				).build());
		}

		return ListUtil.sort(
			countries, Comparator.comparing(country -> country.get("name")));
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

	private Set<String> _a2s;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private Language _language;

}
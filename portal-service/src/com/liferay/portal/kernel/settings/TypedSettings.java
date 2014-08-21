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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
 * @author Iván Zaera
 * @author Jorge Ferrer
 */
public class TypedSettings {

	public TypedSettings(Settings settings) {
		this(
			settings, LocaleUtil.getSiteDefault(),
			LanguageUtil.getAvailableLocales());
	}

	public TypedSettings(
		Settings settings, Locale defaultLocale, Locale... availableLocales) {

		_settings = settings;
		_defaultLocale = defaultLocale;
		_availableLocales = availableLocales;
	}

	public boolean getBooleanValue(String key) {
		return getBooleanValue(key, false);
	}

	public boolean getBooleanValue(String key, boolean defaultValue) {
		String value = getValue(key, null);

		return GetterUtil.getBoolean(value, defaultValue);
	}

	public double getDoubleValue(String key) {
		return getDoubleValue(key, 0);
	}

	public double getDoubleValue(String key, double defaultValue) {
		String value = getValue(key, null);

		return GetterUtil.getDouble(value, defaultValue);
	}

	public float getFloatValue(String key) {
		return getFloatValue(key, 0);
	}

	public float getFloatValue(String key, float defaultValue) {
		String value = getValue(key, null);

		return GetterUtil.getFloat(value, defaultValue);
	}

	public int getIntegerValue(String key) {
		return getIntegerValue(key, 0);
	}

	public int getIntegerValue(String key, int defaultValue) {
		String value = getValue(key, null);

		return GetterUtil.getInteger(value, defaultValue);
	}

	public LocalizedValuesMap getLocalizedValuesMap(String key) {
		LocalizedValuesMap localizedValuesMap = new LocalizedValuesMap(
			key, _defaultLocale, _availableLocales);

		for (Locale locale : _availableLocales) {
			String localizedPreference = LocalizationUtil.getLocalizedName(
				key, LocaleUtil.toLanguageId(locale));

			localizedValuesMap.put(locale, getValue(localizedPreference, null));
		}

		String defaultValue = localizedValuesMap.get(_defaultLocale);

		if (Validator.isNotNull(defaultValue)) {
			return localizedValuesMap;
		}

		localizedValuesMap.put(_defaultLocale, getValue(key, null));

		return localizedValuesMap;
	}

	public long getLongValue(String key) {
		return getLongValue(key, 0);
	}

	public long getLongValue(String key, long defaultValue) {
		String value = getValue(key, null);

		return GetterUtil.getLong(value, defaultValue);
	}

	public String getValue(String key) {
		return getValue(key, StringPool.BLANK);
	}

	public String getValue(String key, String defaultValue) {
		return _settings.getValue(key, defaultValue);
	}

	public String[] getValues(String key) {
		return getValues(key, StringPool.EMPTY_ARRAY);
	}

	public String[] getValues(String key, String[] defaultValue) {
		return _settings.getValues(key, defaultValue);
	}

	public Settings getWrappedSettings() {
		return _settings;
	}

	public void reset(String key) {
		ModifiableSettings modifiableSettings =
			_settings.getModifiableSettings();

		modifiableSettings.reset(key);
	}

	public void setBooleanValue(String key, boolean value) {
		setValue(key, String.valueOf(value));
	}

	public void setIntegerValue(String key, int value) {
		setValue(key, String.valueOf(value));
	}

	public void setLongValue(String key, long value) {
		setValue(key, String.valueOf(value));
	}

	public void setValue(String key, String value) {
		ModifiableSettings modifiableSettings =
			_settings.getModifiableSettings();

		modifiableSettings.setValue(key, value);
	}

	public void setValues(String key, String[] values) {
		ModifiableSettings modifiableSettings =
			_settings.getModifiableSettings();

		modifiableSettings.setValues(key, values);
	}

	private final Locale[] _availableLocales;
	private final Locale _defaultLocale;
	private final Settings _settings;

}
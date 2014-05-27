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

	public TypedSettings(Settings decoratedSettings) {
		this(
			decoratedSettings, LocaleUtil.getSiteDefault(),
			LanguageUtil.getAvailableLocales());
	}

	public TypedSettings(
		Settings decoratedSettings, Locale defaultLocale,
		Locale... availableLocales) {

		_decoratedSettings = decoratedSettings;
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

	public Settings getDecoratedSettings() {
		return _decoratedSettings;
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
		return _decoratedSettings.getValue(key, defaultValue);
	}

	public String[] getValues(String key) {
		return getValues(key, StringPool.EMPTY_ARRAY);
	}

	public String[] getValues(String key, String[] defaultValue) {
		return _decoratedSettings.getValues(key, defaultValue);
	}

	public void reset(String key) {
		_decoratedSettings.reset(key);
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
		_decoratedSettings.setValue(key, value);
	}

	public void setValues(String key, String[] values) {
		_decoratedSettings.setValues(key, values);
	}

	private Locale[] _availableLocales;
	private Settings _decoratedSettings;
	private Locale _defaultLocale;

}
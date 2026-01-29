/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.util;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.exception.ConfigurationValidationException;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import java.util.Dictionary;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Thiago Buarque
 */
public class ConfigurationUtil {

	public static void validateProperties(
			Class<?> configurationBeanClass, Locale locale,
			Dictionary<String, Object> properties)
		throws ConfigurationValidationException {

		for (Method method : configurationBeanClass.getMethods()) {
			Meta.AD ad = method.getAnnotation(Meta.AD.class);

			if (ad == null) {
				continue;
			}

			String fieldName = LanguageUtil.get(locale, ad.name());

			String key = method.getName();

			if (!_isNull(ad.id())) {
				key = ad.id();
			}

			Object value = properties.get(key);

			if (ad.required() && (value == null)) {
				throw new ConfigurationValidationException(
					StringBundler.concat("Property \"", key, "\" is required"),
					"x-field-is-required", fieldName);
			}
			else if (value == null) {
				continue;
			}

			_validateMinValue(fieldName, ad.min(), key, value);
			_validateMaxValue(fieldName, ad.max(), key, value);
		}
	}

	private static boolean _isNull(String value) {
		if (Objects.equals(Meta.NULL, value) || Validator.isNull(value)) {
			return true;
		}

		return false;
	}

	private static void _validateMaxValue(
			String fieldName, String max, String propertyKey, Object value)
		throws ConfigurationValidationException {

		if (_isNull(max)) {
			return;
		}

		if (GetterUtil.getLong(value) > GetterUtil.getLong(max)) {
			throw new ConfigurationValidationException(
				StringBundler.concat(
					"The maximum value for property \"", propertyKey, "\" is ",
					max),
				"value-exceeds-maximum-length-of-x-for-field-x", max,
				fieldName);
		}
	}

	private static void _validateMinValue(
			String fieldName, String min, String propertyKey, Object value)
		throws ConfigurationValidationException {

		if (_isNull(min)) {
			return;
		}

		if (GetterUtil.getLong(value) < GetterUtil.getLong(min)) {
			throw new ConfigurationValidationException(
				StringBundler.concat(
					"The minimum value for property \"", propertyKey, "\" is ",
					min),
				"value-falls-bellow-the-minimum-value-of-x-for-field-x", min,
				fieldName);
		}
	}

}
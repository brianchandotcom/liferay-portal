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

package com.liferay.info.localized.bundle;

import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class ResourceBundleInfoLocalizedValue
	implements InfoLocalizedValue<String> {

	public ResourceBundleInfoLocalizedValue(Class<?> clazz, String valueKey) {
		_class = clazz;
		_symbolicName = null;
		_valueKey = valueKey;
	}

	public ResourceBundleInfoLocalizedValue(
		String symbolicName, String valueKey) {

		_class = null;
		_symbolicName = symbolicName;
		_valueKey = valueKey;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ResourceBundleInfoLocalizedValue)) {
			return false;
		}

		ResourceBundleInfoLocalizedValue resourceBundleInfoLocalizedValue =
			(ResourceBundleInfoLocalizedValue)obj;

		return Objects.equals(
			resourceBundleInfoLocalizedValue._valueKey, _valueKey);
	}

	@Override
	public Set<Locale> getAvailableLocales() {
		return LanguageUtil.getAvailableLocales();
	}

	@Override
	public Locale getDefaultLocale() {
		return LocaleUtil.getDefault();
	}

	@Override
	public String getValue() {
		return getValue(getDefaultLocale());
	}

	@Override
	public String getValue(Locale locale) {
		ResourceBundle resourceBundle = null;

		try {
			if (_class != null) {
				resourceBundle = ResourceBundleUtil.getBundle(locale, _class);
			}
			else {
				resourceBundle = ResourceBundleUtil.getBundle(
					locale, _symbolicName);
			}
		}
		catch (MissingResourceException missingResourceException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find resource bundle for " + locale +
						", reverting to default resource bundle",
					missingResourceException);
			}

			return LanguageUtil.get(locale, _valueKey);
		}

		return LanguageUtil.get(resourceBundle, _valueKey);
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, _valueKey);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceBundleInfoLocalizedValue.class);

	private final Class<?> _class;
	private final String _symbolicName;
	private final String _valueKey;

}
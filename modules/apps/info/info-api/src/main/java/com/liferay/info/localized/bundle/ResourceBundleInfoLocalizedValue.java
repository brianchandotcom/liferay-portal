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
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class ResourceBundleInfoLocalizedValue
	implements InfoLocalizedValue<String> {

	public ResourceBundleInfoLocalizedValue(Class<?> clazz, String valueKey) {
		_clazz = clazz;
		_valueKey = valueKey;

		_symbolicName = null;
	}

	public ResourceBundleInfoLocalizedValue(
		String symbolicName, String valueKey) {

		_symbolicName = symbolicName;
		_valueKey = valueKey;

		_clazz = null;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ResourceBundleInfoLocalizedValue)) {
			return false;
		}

		ResourceBundleInfoLocalizedValue resourceBundleInfoLocalizedValue =
			(ResourceBundleInfoLocalizedValue)object;

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

		if (_clazz != null) {
			resourceBundle = ResourceBundleUtil.getBundle(locale, _clazz);
		}
		else {
			ResourceBundleLoader resourceBundleLoader =
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName(_symbolicName);

			if (resourceBundleLoader == null) {
				resourceBundleLoader =
					ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
			}

			resourceBundle = resourceBundleLoader.loadResourceBundle(locale);
		}

		return LanguageUtil.get(resourceBundle, _valueKey);
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, _valueKey);
	}

	private final Class<?> _clazz;
	private final String _symbolicName;
	private final String _valueKey;

}
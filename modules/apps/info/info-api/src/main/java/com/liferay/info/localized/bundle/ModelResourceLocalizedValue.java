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
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class ModelResourceLocalizedValue implements InfoLocalizedValue<String> {

	public ModelResourceLocalizedValue(String name) {
		_name = name;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ModelResourceLocalizedValue)) {
			return false;
		}

		ModelResourceLocalizedValue modelResourceLocalizedValue =
			(ModelResourceLocalizedValue)object;

		return Objects.equals(modelResourceLocalizedValue._name, _name);
	}

	@Override
	public Set<Locale> getAvailableLocales() {
		return LanguageUtil.getAvailableLocales();
	}

	@Override
	public Locale getDefaultLocale() {
		return LocaleUtil.getSiteDefault();
	}

	@Override
	public String getValue() {
		return getValue(getDefaultLocale());
	}

	@Override
	public String getValue(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, _name);
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, _name);
	}

	private final String _name;

}
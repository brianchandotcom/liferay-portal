/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
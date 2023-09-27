/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.CountryConstants;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
@JSON(strict = true)
public class CountryImpl extends CountryBaseImpl {

	@Override
	public String getName(Locale locale) {
		String name = LanguageUtil.get(
			locale, CountryConstants.NAME_PREFIX + getName());

		if (!name.startsWith(CountryConstants.NAME_PREFIX)) {
			return name;
		}

		return getName();
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _nameCurrentLanguageId;
	}

	@JSON
	@Override
	public String getNameCurrentValue() {
		return getTitle(getLocale(_nameCurrentLanguageId));
	}

	@Override
	public String getTitle(Locale locale) {
		return getTitle(LanguageUtil.getLanguageId(locale));
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		String title = super.getTitle(languageId, useDefault);

		if (Validator.isNotNull(title)) {
			return title;
		}

		return getName(getLocale(languageId));
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_nameCurrentLanguageId = languageId;
	}

	private String _nameCurrentLanguageId;

}
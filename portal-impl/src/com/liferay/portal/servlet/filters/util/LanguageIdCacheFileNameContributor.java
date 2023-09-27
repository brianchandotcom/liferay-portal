/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet.filters.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Sierra Andrés
 */
public class LanguageIdCacheFileNameContributor
	implements CacheFileNameContributor {

	@Override
	public String getParameterName() {
		return "languageId";
	}

	@Override
	public String getParameterValue(HttpServletRequest httpServletRequest) {
		String languageId = httpServletRequest.getParameter(getParameterName());

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		if (availableLocales.contains(LocaleUtil.fromLanguageId(languageId))) {
			return languageId;
		}

		return null;
	}

}
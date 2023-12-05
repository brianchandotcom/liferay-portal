/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet.internal.filters.util;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.servlet.filters.util.CacheFileNameContributor;

import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = CacheFileNameContributor.class)
public class LanguageIdCacheFileNameContributor
	implements CacheFileNameContributor {

	@Override
	public String getParameterName() {
		return "languageId";
	}

	@Override
	public String getParameterValue(HttpServletRequest httpServletRequest) {
		String languageId = httpServletRequest.getParameter(getParameterName());

		Set<Locale> availableLocales = _language.getAvailableLocales();

		if (availableLocales.contains(LocaleUtil.fromLanguageId(languageId))) {
			return languageId;
		}

		return null;
	}

	@Reference
	private Language _language;

}
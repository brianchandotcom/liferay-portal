/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.ManagedBean;
import javax.annotation.Priority;

import javax.mvc.locale.LocaleResolver;
import javax.mvc.locale.LocaleResolverContext;

/**
 * @author Neil Griffin
 */
@ManagedBean
@Priority(0)
public class LocaleResolverImpl implements LocaleResolver {

	@Override
	public Locale resolveLocale(LocaleResolverContext localeResolverContext) {
		List<Locale> acceptableLanguages =
			localeResolverContext.getAcceptableLanguages();

		for (Locale acceptableLanguage : acceptableLanguages) {
			if (!Objects.equals(acceptableLanguage.getLanguage(), "*")) {
				return acceptableLanguage;
			}
		}

		return LocaleUtil.getDefault();
	}

}
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
 * @author  Neil Griffin
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
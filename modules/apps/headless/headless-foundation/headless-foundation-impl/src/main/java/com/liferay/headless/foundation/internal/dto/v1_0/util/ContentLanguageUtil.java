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

package com.liferay.headless.foundation.internal.dto.v1_0.util;

import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.HttpHeaders;

/**
 * @author Víctor Galán
 */
public class ContentLanguageUtil {

	public static void addContentLanguageHeader(
		String[] availableLocaleIds, String defaultLocaleId,
		Locale requestedLocale, HttpServletResponse httpServletResponse) {

		Locale contentLocale = Stream.of(
			availableLocaleIds
		).map(
			LocaleUtil::fromLanguageId
		).filter(
			locale -> LocaleUtil.equals(locale, requestedLocale)
		).findFirst(
		).orElse(
			LocaleUtil.fromLanguageId(defaultLocaleId)
		);

		httpServletResponse.addHeader(
			HttpHeaders.CONTENT_LANGUAGE,
			LocaleUtil.toW3cLanguageId(contentLocale));
	}

}
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

package com.liferay.portal.kernel.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class LocaleThreadLocal {

	public static Locale getDefaultLocale() {
		return _defaultLocale.get();
	}

	public static Locale getSiteDefaultLocale() {
		return _siteDefaultLocale.get();
	}

	public static Locale getThemeDisplayLocale() {
		return _themeDisplayLocale.get();
	}

	public static void setDefaultLocale(Locale locale) {
		_defaultLocale.set(locale);
	}

	public static void setSiteDefaultLocale(Locale locale) {
		_siteDefaultLocale.set(locale);
	}

	public static void setThemeDisplayLocale(Locale locale) {
		_themeDisplayLocale.set(locale);
	}

	private static final ThreadLocal<Locale> _defaultLocale =
		new CentralizedThreadLocal<>(
			LocaleThreadLocal.class + "._defaultLocale");
	private static final ThreadLocal<Locale> _siteDefaultLocale =
		new CentralizedThreadLocal<>(
			LocaleThreadLocal.class + "._siteDefaultLocale");
	private static final ThreadLocal<Locale> _themeDisplayLocale =
		new CentralizedThreadLocal<>(
			LocaleThreadLocal.class + "._themeDisplayLocale");

}
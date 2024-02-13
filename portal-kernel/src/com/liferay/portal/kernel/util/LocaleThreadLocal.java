/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

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

	public static void removeDefaultLocale() {
		_defaultLocale.remove();
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
			LocaleThreadLocal.class + "._defaultLocale",
			() -> {
				User guestUser = CompanyThreadLocal.fetchGuestUser();

				if (guestUser == null) {
					return null;
				}

				return guestUser.getLocale();
			});

	private static final ThreadLocal<Locale> _siteDefaultLocale =
		new CentralizedThreadLocal<>(
			LocaleThreadLocal.class + "._siteDefaultLocale");
	private static final ThreadLocal<Locale> _themeDisplayLocale =
		new CentralizedThreadLocal<>(
			LocaleThreadLocal.class + "._themeDisplayLocale");

}
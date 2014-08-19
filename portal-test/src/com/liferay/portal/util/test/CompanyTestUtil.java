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

package com.liferay.portal.util.test;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.Locale;

import javax.portlet.PortletPreferences;

/**
 * @author Manuel de la Peña
 */
public class CompanyTestUtil {

	public static Company addCompany() throws Exception {
		return addCompany(RandomTestUtil.randomString());
	}

	public static Company addCompany(String name) throws Exception {
		String virtualHostname = name + "." +  RandomTestUtil.randomString(3);
		String shardDefaultName = GetterUtil.getString(
			PropsUtil.get(PropsKeys.SHARD_DEFAULT_NAME));

		return CompanyLocalServiceUtil.addCompany(
			name, virtualHostname, virtualHostname, shardDefaultName, false, 0,
			true);
	}

	public static void resetCompanyLocales(
			long companyId, Locale[] locales, Locale defaultLocale)
		throws Exception {

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		String languageIds = ArrayUtil.toString(locales, _accessor);

		resetCompanyLocales(companyId, languageIds, defaultLanguageId);
	}

	public static void resetCompanyLocales(
			long companyId, String languageIds, String defaultLanguageId)
		throws Exception {

		// Reset company default locale and timezone

		User user = UserLocalServiceUtil.loadGetDefaultUser(companyId);

		user.setLanguageId(defaultLanguageId);
		user.setTimeZoneId(TimeZoneUtil.getDefault().getID());

		UserLocalServiceUtil.updateUser(user);

		// Reset company supported locales

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		preferences.setValue(PropsKeys.LOCALES, languageIds);

		preferences.store();

		// Reset company locales cache

		LanguageUtil.resetAvailableLocales(companyId);

		// Reset thread locals

		CompanyThreadLocal.setCompanyId(companyId);
	}

	private static final Accessor<Locale, String> _accessor =
		new Accessor<Locale, String>() {

			@Override
			public String get(Locale locale) {
				return LocaleUtil.toLanguageId(locale);
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<Locale> getTypeClass() {
				return Locale.class;
			}

		};

}
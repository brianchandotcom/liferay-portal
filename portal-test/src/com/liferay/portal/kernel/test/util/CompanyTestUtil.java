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

package com.liferay.portal.kernel.test.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.util.Collection;
import java.util.Locale;
import java.util.TimeZone;

import javax.portlet.PortletPreferences;

/**
 * @author Manuel de la Peña
 */
public class CompanyTestUtil {

	public static Company addCompany() throws Exception {
		return addCompany(RandomTestUtil.randomString());
	}

	public static Company addCompany(String name) throws Exception {
		String virtualHostname = name + "." + RandomTestUtil.randomString(3);

		return CompanyLocalServiceUtil.addCompany(
			name, virtualHostname, virtualHostname, false, 0, true);
	}

	public static void resetCompanyLocales(
			long companyId, Collection<Locale> locales, Locale defaultLocale)
		throws Exception {

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		String languageIds = StringUtil.merge(
			LocaleUtil.toLanguageIds(locales));

		resetCompanyLocales(companyId, languageIds, defaultLanguageId);
	}

	public static void resetCompanyLocales(
			long companyId, String languageIds, String defaultLanguageId)
		throws Exception {

		// Reset company default locale and timezone

		User user = UserLocalServiceUtil.loadGetDefaultUser(companyId);

		user.setLanguageId(defaultLanguageId);

		TimeZone timeZone = TimeZoneUtil.getDefault();

		user.setTimeZoneId(timeZone.getID());

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

		LocaleThreadLocal.setDefaultLocale(
			LocaleUtil.fromLanguageId(defaultLanguageId));
	}

}
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;

import java.util.Locale;

import javax.portlet.PortletPreferences;

/**
 * @author Manuel de la Peña
 */
public class CompanyTestUtil {

	public static Company addCompany() throws Exception {
		return addCompany(ServiceTestUtil.randomString());
	}

	public static Company addCompany(String name) throws Exception {
		String virtualHostname = name + "." +  ServiceTestUtil.randomString(3);

		return CompanyLocalServiceUtil.addCompany(
			name, virtualHostname, virtualHostname,
			PropsValues.SHARD_DEFAULT_NAME, false, 0, true);
	}

	public static void resetCompanyLocales(long companyId, Locale[] locales)
		throws Exception {

		StringBundler sb = new StringBundler();

		for (int i = 0; i < locales.length; i++) {
			sb.append(LanguageUtil.getLanguageId(locales[i]));

			if ((i + 1) < locales.length) {
				sb.append(StringPool.COMMA);
			}
		}

		resetCompanyLocales(companyId, sb.toString());
	}

	public static void resetCompanyLocales(long companyId, String languageIds)
		throws Exception {

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		LanguageUtil.resetAvailableLocales(companyId);

		preferences.setValue(PropsKeys.LOCALES, languageIds);

		preferences.store();

		CompanyThreadLocal.setCompanyId(companyId);
	}

}
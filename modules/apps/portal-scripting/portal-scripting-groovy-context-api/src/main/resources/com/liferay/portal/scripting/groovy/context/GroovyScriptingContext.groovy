/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scripting.groovy.context;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Michael C. Han
 */
class GroovyScriptingContext {

	static Map<Locale, String> getLocalizationMap(String value) {
		Map<Locale, String> localizationMap = new HashMap<>();

		localizationMap.put(LocaleUtil.getDefault(), value);

		return localizationMap;
	}

	GroovyScriptingContext() {
		serviceContext = new ServiceContext();

		long defaultCompanyId = PortalUtil.getDefaultCompanyId();

		serviceContext.setCompanyId(defaultCompanyId);

		guestUserId = UserLocalServiceUtil.getGuestUserId(defaultCompanyId);
	}

	GroovyScriptingContext(long companyId) {
		serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);

		guestUserId = UserLocalServiceUtil.getGuestUserId(companyId);
	}

	long getCompanyId() {
		return serviceContext.getCompanyId();
	}

	long guestUserId;
	ServiceContext serviceContext;

}
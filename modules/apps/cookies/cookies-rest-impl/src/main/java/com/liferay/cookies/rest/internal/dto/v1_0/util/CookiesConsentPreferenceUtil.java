/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.rest.internal.dto.v1_0.util;

import com.liferay.cookies.rest.dto.v1_0.CookiesConsentPreference;

/**
 * @author Christopher Kian
 */
public class CookiesConsentPreferenceUtil {

	public static CookiesConsentPreference toCookiesConsentPreference(
		com.liferay.cookies.model.CookiesConsentPreference
			serviceBuilderCookiesConsentPreference) {

		return new CookiesConsentPreference() {
			{
				setDomain(serviceBuilderCookiesConsentPreference::getDomain);
				setExpirationDate(
					serviceBuilderCookiesConsentPreference::getExpirationDate);
				setId(
					serviceBuilderCookiesConsentPreference::
						getCookiesConsentPreferenceId);
				setName(serviceBuilderCookiesConsentPreference::getName);
				setUserId(serviceBuilderCookiesConsentPreference::getUserId);
				setValue(serviceBuilderCookiesConsentPreference::getValue);
			}
		};
	}

}
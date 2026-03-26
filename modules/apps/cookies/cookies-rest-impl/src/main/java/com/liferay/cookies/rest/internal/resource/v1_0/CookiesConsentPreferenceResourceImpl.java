/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.rest.internal.resource.v1_0;

import com.liferay.cookies.rest.resource.v1_0.CookiesConsentPreferenceResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Christopher Kian
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/cookies-consent-preference.properties",
	scope = ServiceScope.PROTOTYPE,
	service = CookiesConsentPreferenceResource.class
)
public class CookiesConsentPreferenceResourceImpl
	extends BaseCookiesConsentPreferenceResourceImpl {
}
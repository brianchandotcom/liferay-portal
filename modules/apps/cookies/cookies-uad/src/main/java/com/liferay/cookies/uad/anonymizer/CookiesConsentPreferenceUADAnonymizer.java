/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.uad.anonymizer;

import com.liferay.user.associated.data.anonymizer.UADAnonymizer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Christopher Kian
 */
@Component(service = UADAnonymizer.class)
public class CookiesConsentPreferenceUADAnonymizer
	extends BaseCookiesConsentPreferenceUADAnonymizer {
}
// LIFERAY-SERVICE-BUILDER-HASH:-1939853248
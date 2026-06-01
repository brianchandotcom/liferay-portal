/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.banner.web.internal.configuration.admin.definition;

import com.liferay.configuration.admin.definition.ConfigurationDDMFormDeclaration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Álvaro Saugar
 */
@Component(
	property = "configurationPid=com.liferay.cookies.configuration.consent.CookiesConsentConfiguration",
	service = ConfigurationDDMFormDeclaration.class
)
public class CookiesConsentConfigurationDDMFormDeclaration
	implements ConfigurationDDMFormDeclaration {

	@Override
	public Class<?> getDDMFormClass() {
		return CookiesConsentConfigurationForm.class;
	}

}
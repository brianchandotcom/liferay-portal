/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.banner.web.internal.configuration.admin.definition;

import com.liferay.configuration.admin.definition.ConfigurationDDMFormDeclaration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Álvaro Saugar
 */
@Component(
	property = "configurationPid=com.liferay.cookies.configuration.banner.CookiesBannerConfiguration",
	service = ConfigurationDDMFormDeclaration.class
)
public class CookiesBannerConfigurationDDMFormDeclaration
	implements ConfigurationDDMFormDeclaration {

	@Override
	public Class<?> getDDMFormClass() {
		return CookiesBannerConfigurationForm.class;
	}

}
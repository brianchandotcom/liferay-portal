/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.spi.application.descriptor;

import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Represents the localization information for OAuth2 applications.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
@ProviderType
public interface ApplicationDescriptor {

	/**
	 * Localize an application for a given locale.
	 *
	 * @param  locale the locale requested for the description.
	 * @return a description for the applicationName in the requested locale.
	 * @review
	 */
	public String describeApplication(Locale locale);

}